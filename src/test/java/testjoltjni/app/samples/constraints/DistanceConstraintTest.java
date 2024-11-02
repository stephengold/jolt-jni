/*
Copyright (c) 2024 Stephen Gold

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
package testjoltjni.app.samples.constraints;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.operator.Op;
import testjoltjni.app.samples.*;

/**
 * A line-for-line Java translation of the Jolt Physics distance-constraint test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/DistanceConstraintTest.cpp
 */
class DistanceConstraintTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	float half_cylinder_height = 2.5f;

	// Variation 0: Fixed distance
	// Variation 1: Min/max distance
	for (int variation = 0; variation < 2; ++variation)
	{
		// Bodies attached through distance constraints
		Quat rotation = Quat.sRotation(Vec3.sAxisZ(), 0.5f * Jolt.JPH_PI);
		RVec3 position=new RVec3(0, 75, 10.0f * variation);
		Body top = mBodyInterface.createBody(new BodyCreationSettings(new CapsuleShape(half_cylinder_height, 1), position, rotation, EMotionType.Static, Layers.NON_MOVING));
		mBodyInterface.addBody(top.getId(), EActivation.DontActivate);

		Body prev = top;
		for (int i = 1; i < 15; ++i)
		{
			Op.plusEquals(position ,new Vec3(5.0f + 2.0f * half_cylinder_height, 0, 0));

			Body segment = mBodyInterface.createBody(new BodyCreationSettings(new CapsuleShape(half_cylinder_height, 1), position, rotation, EMotionType.Dynamic, Layers.MOVING));
			mBodyInterface.addBody(segment.getId(), EActivation.Activate);

			DistanceConstraintSettings settings=new DistanceConstraintSettings();
			settings.setPoint1 ( Op.subtract(position ,new Vec3(5.0f + half_cylinder_height, 0, 0)));
			settings.setPoint2 ( Op.subtract(position ,new Vec3(half_cylinder_height, 0, 0)));

			if (variation == 1)
			{
				// Default distance is 5, override min/max range
				settings.setMinDistance ( 4.0f);
				settings.setMaxDistance ( 8.0f);
			}

			mPhysicsSystem.addConstraint(settings.create(prev, segment));

			prev = segment;
		}
	}
}
}
