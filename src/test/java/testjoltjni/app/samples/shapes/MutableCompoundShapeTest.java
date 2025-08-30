/*
Copyright (c) 2024-2025 Stephen Gold

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package testjoltjni.app.samples.shapes;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.readonly.*;
import com.github.stephengold.joltjni.std.*;
import java.nio.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt-Physics mutable compound shape 
 * test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Shapes/MutableCompoundShapeTest.cpp
 */
public class MutableCompoundShapeTest extends Test{
BodyIdVector mBodyIDs=new BodyIdVector();
int mFrameNumber;
ShapeRefC mSubCompound=new ShapeRefC();

public void Initialize()
{
	// Floor (extra thick because we can randomly add sub shapes that then may stick out underneath the floor and cause objects to be pushed through)
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(100.0f, 10.0f, 100.0f), 0.0f), new RVec3(0.0f, -10.0f, 0.0f), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Compound with sub compound and rotation
	StaticCompoundShapeSettings sub_compound_settings=new StaticCompoundShapeSettings();
	sub_compound_settings.addShape(new Vec3(0, 1.5f, 0), Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI), new BoxShape(new Vec3(1.5f, 0.25f, 0.2f)));
	sub_compound_settings.addShape(new Vec3(1.5f, 0, 0), Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI), new CylinderShape(1.5f, 0.2f));
	sub_compound_settings.addShape(new Vec3(0, 0, 1.5f), Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI), new TaperedCapsuleShapeSettings(1.5f, 0.25f, 0.2f));
	mSubCompound = sub_compound_settings.create().get();

	for (int i = 0; i < 10; ++i)
	{
		// Create a mutable compound per body and fill it up with 2 shapes initially
		MutableCompoundShapeSettings compound_shape = new MutableCompoundShapeSettings();
		compound_shape.addShape(Vec3.sZero(), star(Quat.sRotation(Vec3.sAxisX(), -0.25f * JPH_PI) , Quat.sRotation(Vec3.sAxisZ(), 0.25f * JPH_PI)), mSubCompound);
		compound_shape.addShape(Vec3.sZero(), star(Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI) , Quat.sRotation(Vec3.sAxisZ(), -0.75f * JPH_PI)), mSubCompound);

		// Create a body
		int body_id = mBodyInterface.createAndAddBody(new BodyCreationSettings(compound_shape, new RVec3(0, 10.0f + 5.0f * i, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
		mBodyIDs.pushBack(body_id);
	}
}

public void PrePhysicsUpdate(PreUpdateParams inParams)
{
	BodyInterface no_lock = mPhysicsSystem.getBodyInterfaceNoLock();

	UniformFloatDistribution roll_distribution=new UniformFloatDistribution(0, 1);

	for (int id : mBodyIDs.toList())
	{
		BodyLockWrite lock=new BodyLockWrite(mPhysicsSystem.getBodyLockInterface(), id);
		if (lock.succeeded())
		{
			Body body = lock.getBody();

			// Get the shape
			MutableCompoundShape shape = (MutableCompoundShape)(body.getShape());

			// Remember center of mass from before changes
			Vec3 old_com = shape.getCenterOfMass();

			// Consistently seeded random engine so that bodies move in a predictable way
			DefaultRandomEngine consistent_random=new DefaultRandomEngine();

			// Simulate an engine data structure with strided positions/rotations

			// Animate sub shapes
			int count = shape.getNumSubShapes();
			ByteBuffer mPosition=newDirectByteBuffer(32*count);
			FloatBuffer floatBuffer=mPosition.asFloatBuffer();
			for (int i = 0; i < count; ++i)
			{
				ConstSubShape sub_shape = shape.getSubShape(i);
				Vec3.sZero().put(floatBuffer);
                                floatBuffer.put(0f);
                                star(Quat.sRotation( Vec3.sRandom(consistent_random), degreesToRadians(10.0f) * inParams.mDeltaTime) , sub_shape.getRotation().normalized()).put(floatBuffer);
			}
                        mPosition.position(16);
			ByteBuffer mRotation=mPosition.slice();

			// Set the new rotations/orientations on the sub shapes
			shape.modifyShapes(0, count, mPosition, mRotation, 32, 32);

			// Initialize frame dependent random number generator
			// Note: Explicitly using the Mersenne Twister random generator as on some platforms you get the seed back as the first random number
			Mt19937 frame_random=new Mt19937(mFrameNumber++);

			// Roll the dice
			float roll = roll_distribution.nextFloat(frame_random);
			if (roll < 0.001f && count > 1)
			{
				// Remove a random shape
				UniformIntDistribution index_distribution=new UniformIntDistribution(0, count - 1);
				shape.removeShape(index_distribution.nextInt(frame_random));
			}
			else if (roll < 0.002f && count < 10)
			{
				// Add a shape in a random rotation
				shape.addShape(Vec3.sZero(), Quat.sRandom(frame_random), mSubCompound);
			}

			// Ensure that the center of mass is updated
			shape.adjustCenterOfMass();

			// Since we're already locking the body, we don't need to lock it again
			// We always update the mass properties of the shape because we're reorienting them every frame
			no_lock.notifyShapeChanged(id, old_com, true, EActivation.Activate);
		}
		lock.releaseLock();
	}
}

public void SaveState(StateRecorder inStream)
{
	inStream.write(mFrameNumber);

	for (int id : mBodyIDs.toList())
	{
		BodyLockRead lock=new BodyLockRead(mPhysicsSystem.getBodyLockInterface(), id);
		if (lock.succeeded())
		{
			final ConstBody body = lock.getBody();

			// Write the shape as a binary string
			StringStream data=new StringStream();
			StreamOutWrapper stream_out=new StreamOutWrapper(data);
			body.getShape().saveBinaryState(stream_out);
			inStream.write(data.str());
		}
                lock.releaseLock();
	}
}

public void RestoreState(StateRecorder inStream)
{
	mFrameNumber=inStream.readInt(mFrameNumber);

	for (int id : mBodyIDs.toList())
	{
		BodyLockWrite lock=new BodyLockWrite(mPhysicsSystem.getBodyLockInterface(), id);
		if (lock.succeeded())
		{
			Body body = lock.getBody();

			// Read the shape as a binary string
			String str=null;
			if (inStream.isValidating())
			{
				StringStream data=new StringStream();
				StreamOutWrapper stream_out=new StreamOutWrapper(data);
				body.getShape().saveBinaryState(stream_out);
				str = data.str();
			}
			str=inStream.readString(str);

			// Deserialize the shape
			StringStream data=new StringStream(str);
			StreamInWrapper stream_in=new StreamInWrapper(data);
			ShapeResult result = Shape.sRestoreFromBinaryState(stream_in);
			MutableCompoundShape shape = (MutableCompoundShape)(result.get().getPtr());

			// Restore the pointers to the sub compound
			ShapeList sub_shapes=new ShapeList(mSubCompound);
			shape.restoreSubShapeState(sub_shapes);

			// Update the shape (we're under lock protection, so use the no lock interface)
			mPhysicsSystem.getBodyInterfaceNoLock().setShape(id, shape, false, EActivation.DontActivate);
		}
		lock.releaseLock();
	}
}
}