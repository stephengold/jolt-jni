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
package testjoltjni.app.samples.constraints;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.operator.Op.*;

/**
 * A line-for-line Java translation of the Jolt-Physics point-constraint test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/PointConstraintTest.cpp
 */
public class PointConstraintTest extends Test{
public void Initialize()
{
	// Floor
	CreateFloor();

	float half_cylinder_height = 2.5f;

	final int cChainLength = 15;

	// Build a collision group filter that disables collision between adjacent bodies
	GroupFilterTableRef group_filter = new GroupFilterTable(cChainLength).toRef();
	for (int i = 0; i < cChainLength - 1; ++i)
		group_filter.disableCollision(i, i + 1);

	// Bodies attached through point constraints
	Quat rotation = Quat.sRotation(Vec3.sAxisZ(), 0.5f * Jolt.JPH_PI);
	RVec3 position=new RVec3(0, 50, 0);
	Body top = mBodyInterface.createBody(new BodyCreationSettings(new CapsuleShape(half_cylinder_height, 1), position, rotation, EMotionType.Static, Layers.NON_MOVING));
	top.setCollisionGroup(new CollisionGroup(group_filter, 0, 0));
	mBodyInterface.addBody(top.getId(), EActivation.DontActivate);

	Body prev = top;
	for (int i = 1; i < cChainLength; ++i)
	{
		plusEquals(position , new Vec3(2.0f * half_cylinder_height, 0, 0));

		Body segment = mBodyInterface.createBody(new BodyCreationSettings(new CapsuleShape(half_cylinder_height, 1), position, rotation, EMotionType.Dynamic, Layers.MOVING));
		segment.setCollisionGroup(new CollisionGroup(group_filter, 0, i));
		mBodyInterface.addBody(segment.getId(), EActivation.Activate);

		PointConstraintSettings settings=new PointConstraintSettings();
		settings.setPoint1 ( plus(position , new Vec3(-half_cylinder_height, 0, 0)));
		settings.setPoint2 ( plus(position , new Vec3(-half_cylinder_height, 0, 0)));
		mPhysicsSystem.addConstraint(settings.create(prev, segment));

		prev = segment;
	}
}
}
