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
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt Physics cone-constraint test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/ConeConstraintTest.cpp
 */
public class ConeConstraintTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	float half_cylinder_height = 2.5f;

	final int cChainLength = 5;

	// Build a collision group filter that disables collision between adjacent bodies
	GroupFilterTable group_filter = new GroupFilterTable(cChainLength);
	for (int i = 0; i < cChainLength - 1; ++i)
		group_filter.disableCollision(i, i + 1);

	// Bodies attached through cone constraints
	for (int j = 0; j < 2; ++j)
	{
		Body prev = null;
		Quat rotation = Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI);
		RVec3 position=new RVec3(0, 20.0f, 10.0f * j);
		for (int i = 0; i < cChainLength; ++i)
		{
			plusEquals(position ,new Vec3(2.0f * half_cylinder_height, 0, 0));

			Body segment = mBodyInterface.createBody(new BodyCreationSettings(new CapsuleShape(half_cylinder_height, 1), position, star(Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI * i) , rotation), i == 0? EMotionType.Static : EMotionType.Dynamic, i == 0? Layers.NON_MOVING : Layers.MOVING));
			segment.setCollisionGroup(new CollisionGroup(group_filter, j, i));
			mBodyInterface.addBody(segment.getId(), EActivation.Activate);

			if (prev != nullptr)
			{
				ConeConstraintSettings settings=new ConeConstraintSettings();
				settings.setPoint1 ( settings.setPoint2 ( plus(position ,new Vec3(-half_cylinder_height, 0, 0))));
				settings.setTwistAxis1 ( settings.setTwistAxis2 (new Vec3(1, 0, 0)));
				if (j == 0)
					settings.setHalfConeAngle ( 0.0f);
				else
					settings.setHalfConeAngle ( degreesToRadians(20));

				mPhysicsSystem.addConstraint(settings.create(prev, segment));
			}

			prev = segment;
		}
	}
}
}