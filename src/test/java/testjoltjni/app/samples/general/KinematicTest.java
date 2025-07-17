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
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt-Physics kinematic test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/KinematicTest.cpp
 */
public class KinematicTest extends Test{
Body[] mKinematic=new Body[2];

public void Initialize()
{
	// Floor
	CreateFloor();

	// Wall
	ShapeRefC box_shape = new BoxShape(new Vec3(1.0f, 1.0f, 1.0f)).toRefC();
	for (int i = 0; i < 3; ++i)
		for (int j = i / 2; j < 10 - (i + 1) / 2; ++j)
		{
			RVec3 position=new RVec3(-10.0f + j * 2.0f + ((i & 1)!=0? 1.0f : 0.0f), 1.0f + i * 2.0f, 0);
			mBodyInterface.createAndAddBody(new BodyCreationSettings(box_shape, position, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.DontActivate);
		}

	// Kinematic object
	for (int i = 0; i < 2; ++i)
	{
		mKinematic[i] = mBodyInterface.createBody(new BodyCreationSettings(new SphereShape(1.0f),new RVec3(-10.0f, 2.0f, i == 0? 5.0f : -5.0f), Quat.sIdentity(), EMotionType.Kinematic, Layers.MOVING));
		mBodyInterface.addBody(mKinematic[i].getId(), EActivation.Activate);
	}
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	for (int i = 0; i < 2; ++i)
	{
		RVec3 com = mKinematic[i].getCenterOfMassPosition();
		if (com.z() >= 5.0f)
			mKinematic[i].setLinearVelocity(new Vec3(2.0f, 0, -10.0f));
		else if (com.z() <= -5.0f)
			mKinematic[i].setLinearVelocity(new Vec3(2.0f, 0, 10.0f));
	}
}
}
