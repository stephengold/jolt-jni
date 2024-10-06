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

/**
 * A line-for-line Java translation of the Jolt Physics spring test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/SpringTest.cpp
 */
class SpringTest extends Test{
void Initialize()
{
	// Floor
	CreateFloor();

	// Top fixed body
	RVec3 position=new RVec3(0, 75, 0);
	Body top = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(new Vec3(100.0f, 1.0f, 1.0f)), position, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
	mBodyInterface.addBody(top.getId(), EActivation.DontActivate);

	// Bodies attached with spring with different string lengths, same frequency and no damping
	for (int i = 0; i < 10; ++i)
	{
		// Create body
		RVec3 attachment_point = Op.add(position , new Vec3(-100.0f + i * 5.0f, 0, 0));
		RVec3 body_position = Op.subtract(attachment_point , new Vec3(0, 10.0f + i * 2.5f, 0));
		Body body = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(0.75f)), body_position, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		body.getMotionProperties().setAngularDamping(0.0f);
		body.getMotionProperties().setLinearDamping(0.0f);
		mBodyInterface.addBody(body.getId(), EActivation.Activate);

		// Attach spring
		DistanceConstraintSettings settings = new DistanceConstraintSettings();
		settings.setPoint1 ( attachment_point);
		settings.setPoint2 ( body_position);
		settings.getLimitsSpringSettings().setFrequency ( 0.33f);
		mPhysicsSystem.addConstraint(settings.create(top, body));

		// Move the body up so that it can start oscillating
		mBodyInterface.setPositionAndRotation(body.getId(), Op.subtract(attachment_point , new Vec3(0, 5, 0)), Quat.sIdentity(), EActivation.DontActivate);
	}

	// Bodies attached with spring with different frequency and no damping
	for (int i = 0; i < 10; ++i)
	{
		// Create body
		RVec3 attachment_point = Op.add(position , new Vec3(-25.0f + i * 5.0f, 0, 0));
		RVec3 body_position = Op.add(attachment_point , new Vec3(0, 25.0f, 0));
		Body body = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(0.75f)), body_position, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		body.getMotionProperties().setAngularDamping(0.0f);
		body.getMotionProperties().setLinearDamping(0.0f);
		mBodyInterface.addBody(body.getId(), EActivation.Activate);

		// Attach spring
		DistanceConstraintSettings settings=new DistanceConstraintSettings();
		settings.setPoint1 ( attachment_point);
		settings.setPoint2 ( body_position);
		settings.getLimitsSpringSettings().setFrequency ( 0.1f + 0.1f * i);
		mPhysicsSystem.addConstraint(settings.create(top, body));

		// Move the body up so that it can start oscillating
		mBodyInterface.setPositionAndRotation(body.getId(), Op.subtract(attachment_point , new Vec3(0, 5, 0)), Quat.sIdentity(), EActivation.DontActivate);
	}

	// Bodies attached with spring with same spring length, same frequency and different damping
	for (int i = 0; i < 10; ++i)
	{
		// Create body
		RVec3 attachment_point = Op.add(position , new Vec3(50.0f + i * 5.0f, 0, 0));
		RVec3 body_position = Op.subtract(attachment_point , new Vec3(0, 25.0f, 0));
		Body body = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(0.75f)), body_position, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		body.getMotionProperties().setAngularDamping(0.0f);
		body.getMotionProperties().setLinearDamping(0.0f);
		mBodyInterface.addBody(body.getId(), EActivation.Activate);

		// Attach spring
		DistanceConstraintSettings settings=new DistanceConstraintSettings();
		settings.setPoint1 ( attachment_point);
		settings.setPoint2 ( body_position);
		settings.getLimitsSpringSettings().setFrequency ( 0.33f);
		settings.getLimitsSpringSettings().setDamping ( (1.0f / 9.0f) * i);
		mPhysicsSystem.addConstraint(settings.create(top, body));

		// Move the body up so that it can start oscillating
		mBodyInterface.setPositionAndRotation(body.getId(), Op.subtract(attachment_point , new Vec3(0, 5, 0)), Quat.sIdentity(), EActivation.DontActivate);
	}
}
}