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
package testjoltjni.app.samples;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.operator.Op;
import java.util.ArrayList;
import java.util.List;

/**
 * A line-for-line Java translation of the Jolt Physics constraint-priority test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/ConstraintPriorityTest.cpp
 */
class ConstraintPriorityTest extends Test{
List<TwoBodyConstraintRef> mConstraints=new ArrayList<>();

void Initialize()
{
	float box_size = 1.0f;
	ShapeRefC box = new BoxShape(new Vec3(0.5f * box_size, 0.2f, 0.2f)).toRefC();

	final int num_bodies = 20;

	// Bodies attached through fixed constraints
	for (int priority = 0; priority < 2; ++priority)
	{
		RVec3 position=new RVec3(0, 10.0f, 0.2f * priority);
		Body top = mBodyInterface.createBody(new BodyCreationSettings(box, position, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
		mBodyInterface.addBody(top.getId(), EActivation.DontActivate);

		Body prev = top;
		for (int i = 1; i < num_bodies; ++i)
		{
			Op.plusEquals(position ,new Vec3(box_size, 0, 0));

			Body segment = mBodyInterface.createBody(new BodyCreationSettings(box, position, Quat.sIdentity(), EMotionType.Dynamic, Layers.NON_MOVING)); // Putting all bodies in the NON_MOVING layer so they won't collide
			mBodyInterface.addBody(segment.getId(), EActivation.Activate);

			FixedConstraintSettings settings=new FixedConstraintSettings();
			settings.setAutoDetectPoint ( true);
			settings.setConstraintPriority ( priority == 0? i : num_bodies - i); // Priority is reversed for one chain compared to the other
			TwoBodyConstraintRef c = ((TwoBodyConstraint)settings.create(prev, segment)).toRef();
			mPhysicsSystem.addConstraint(c.getPtr());
			mConstraints.add((c));

			prev = segment;
		}
	}
}

void PostPhysicsUpdate(float inDeltaTime)
{
	for (TwoBodyConstraintRef c : mConstraints)
		mDebugRenderer.drawText3D(Op.multiply(0.5f , Op.add(c.getBody1().getCenterOfMassPosition() , c.getBody2().getCenterOfMassPosition())), String.format("Priority: %d", c.getConstraintPriority()), Color.sWhite, 0.2f);
}
}