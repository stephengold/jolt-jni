/*
Copyright (c) 2025 Stephen Gold

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
package testjoltjni.app.samples.scaledshapes;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.readonly.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
/**
 * A line-for-line Java translation of the Jolt Physics dynamic scaled shape test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/ScaledShapes/DynamicScaledShape.cpp
 */
public class DynamicScaledShape extends Test{
float mTime;
int mBodyID=cInvalidBodyId;

public void Initialize()
{
	// Floor
	CreateHeightFieldTerrain();

	// Create scaled sphere
	ShapeRefC scaled_sphere_shape = new ScaledShape(new SphereShape(2.0f), Vec3.sOne()).toRefC();
	mBodyID = mBodyInterface.createAndAddBody(new BodyCreationSettings(scaled_sphere_shape,new RVec3(0, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
}

public void PrePhysicsUpdate(PreUpdateParams inParams)
{
	// Update time
	mTime += inParams.mDeltaTime;

	BodyLockWrite lock=new BodyLockWrite(mPhysicsSystem.getBodyLockInterface(), mBodyID);
	if (lock.succeeded())
	{
		Body body = lock.getBody();

		// Fetch the inner shape
		// Note that we know here that the inner shape is the original shape, but if you're scaling a CompoundShape non-uniformly the inner shape
		// may be a new compound shape with the scale baked into the children. In this case you need to keep track of your original shape yourself.
		assert(body.getShape().getSubType() == EShapeSubType.Scaled);
		ScaledShape scaled_shape = (ScaledShape )(body.getShape());
		ConstShape non_scaled_shape = scaled_shape.getInnerShape();

		// Rescale the sphere
		float new_scale = 1.0f + 0.5f * sin(mTime);
		ShapeResult new_shape = non_scaled_shape.scaleShape(Vec3.sReplicate(new_scale));
		assert(new_shape.isValid()); // We're uniformly scaling a sphere, this should always succeed

		// Note: Using non-locking interface here because we already have the lock
		// Also note that scaling shapes may cause simulation issues as the bodies can get stuck when they get bigger.
		// Recalculating mass every frame can also be an expensive operation.
		mPhysicsSystem.getBodyInterfaceNoLock().setShape(body.getId(), new_shape.get(), true, EActivation.Activate);
	}
        lock.releaseLock();
}

protected void SaveState(StateRecorder inStream)
{
	inStream.write(mTime);
}

protected void RestoreState(StateRecorder inStream)
{
	mTime=inStream.readFloat(mTime);
}
}
