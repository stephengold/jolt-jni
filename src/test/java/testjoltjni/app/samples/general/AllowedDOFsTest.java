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
package testjoltjni.app.samples.general;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.readonly.*;
import static com.github.stephengold.joltjni.Jolt.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import static com.github.stephengold.joltjni.std.Std.*;
/**
 * A line-for-line Java translation of the Jolt-Physics allowed DOFs test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/AllowedDOFsTest.cpp
 */
public class AllowedDOFsTest extends Test{
BodyIdVector mBodies=new BodyIdVector();

public void Initialize()
{
	// Floor
	CreateFloor();

	Vec3 box_size=new Vec3(0.5f, 1.0f, 2.0f);
	ShapeRefC box_shape = new BoxShape(box_size).toRefC();

	for (int allowed_dofs = 1; allowed_dofs <= 0b111111; ++allowed_dofs)
	{
		float x = -35.0f + 10.0f * (allowed_dofs & 0b111);
		float z = -35.0f + 10.0f * ((allowed_dofs >> 3) & 0b111);

		// Create body
		BodyCreationSettings bcs=new BodyCreationSettings(box_shape,new RVec3(x, 10, z), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
		bcs.setAllowedDofs ( (int)allowed_dofs);
		int id = mBodyInterface.createAndAddBody(bcs, EActivation.Activate);
		mBodies.pushBack(id);

		// Create a constraint
		DistanceConstraintSettings dcs=new DistanceConstraintSettings();
		dcs.setPoint1 ( plus(bcs.getPosition() ,new Vec3(5, 5, 5)));
		dcs.setPoint2 ( plus(bcs.getPosition() , box_size));
		dcs.setMinDistance ( 0.0f);
		dcs.setMaxDistance ( sqrt(3.0f) * 5.0f + 1.0f);
		mPhysicsSystem.addConstraint(mBodyInterface.createConstraint(dcs,cInvalidBodyId, id));
	}
}

public void PostPhysicsUpdate(float inDeltaTime)
{
	// Draw degrees of freedom
	for (int id : mBodies.toList())
	{
		BodyLockRead body_lock=new BodyLockRead(mPhysicsSystem.getBodyLockInterface(), id);
		if (body_lock.succeeded())
		{
			ConstBody body = body_lock.getBody();
			String allowed_dofs_str = "";
			int allowed_dofs = body.getMotionProperties().getAllowedDofs();
			if ((allowed_dofs & EAllowedDofs.TranslationX) == EAllowedDofs.TranslationX)
				allowed_dofs_str += "X ";
			if ((allowed_dofs & EAllowedDofs.TranslationY) == EAllowedDofs.TranslationY)
				allowed_dofs_str += "Y ";
			if ((allowed_dofs & EAllowedDofs.TranslationZ) == EAllowedDofs.TranslationZ)
				allowed_dofs_str += "Z ";
			if ((allowed_dofs & EAllowedDofs.RotationX) == EAllowedDofs.RotationX)
				allowed_dofs_str += "RX ";
			if ((allowed_dofs & EAllowedDofs.RotationY) == EAllowedDofs.RotationY)
				allowed_dofs_str += "RY ";
			if ((allowed_dofs & EAllowedDofs.RotationZ) == EAllowedDofs.RotationZ)
				allowed_dofs_str += "RZ ";
			DebugRenderer.sInstance().drawText3D(body.getPosition(), allowed_dofs_str, Color.sWhite);
		}
		body_lock.releaseLock();
	}
}
}
