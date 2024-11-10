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
import static com.github.stephengold.joltjni.Jolt.*;
/**
 * A line-for-line Java translation of the Jolt Physics hinge-constraint test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/HingeConstraintTest.cpp
 */
public class HingeConstraintTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	float box_size = 4.0f;
	ShapeRefC box = new BoxShape(Vec3.sReplicate(0.5f * box_size)).toRefC();

	final int cChainLength = 15;
	final float cMinAngle = degreesToRadians(-10.0f);
	final float cMaxAngle = degreesToRadians(20.0f);

	// Build a collision group filter that disables collision between adjacent bodies
	GroupFilterTableRef group_filter = new GroupFilterTable(cChainLength).toRef();
	for (int i = 0; i < cChainLength - 1; ++i)
		group_filter.disableCollision(i, i + 1);

	// Bodies attached through hinge constraints
	for (int randomness = 0; randomness < 2; ++randomness)
	{
		int group_id = (randomness);

		RVec3 position=new RVec3(0, 50, -randomness * 20.0f);
		Body top = mBodyInterface.createBody(new BodyCreationSettings(box, position, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
		top.setCollisionGroup(new CollisionGroup(group_filter, group_id, 0));
		mBodyInterface.addBody(top.getId(), EActivation.DontActivate);

		DefaultRandomEngine random=new DefaultRandomEngine();
		UniformRealDistribution displacement=new UniformRealDistribution(-1.0f, 1.0f);

		Body prev = top;
		for (int i = 1; i < cChainLength; ++i)
		{
			Quat rotation;
			if (randomness == 0)
			{
				Op.plusEquals(position ,new Vec3(box_size, 0, 0));
				rotation = Quat.sIdentity();
			}
			else
			{
				Op.plusEquals(position ,new Vec3(box_size + Math.abs(displacement.nextFloat(random)), displacement.nextFloat(random), displacement.nextFloat(random)));
				rotation = Quat.sRandom(random);
			}

			Body segment = mBodyInterface.createBody(new BodyCreationSettings(box, position, rotation, EMotionType.Dynamic, Layers.MOVING));
			segment.setCollisionGroup(new CollisionGroup(group_filter, group_id, (i)));
			mBodyInterface.addBody(segment.getId(), EActivation.Activate);

			HingeConstraintSettings settings=new HingeConstraintSettings();
			if ((i & 1) == 0)
			{
				settings.setPoint1 ( settings.setPoint2 ( Op.add(position ,new Vec3(-0.5f * box_size, 0, 0.5f * box_size))));
				settings.setHingeAxis1 ( settings.setHingeAxis2 ( Vec3.sAxisY()));
				settings.setNormalAxis1 ( settings.setNormalAxis2 ( Vec3.sAxisX()));
			}
			else
			{
				settings.setPoint1 ( settings.setPoint2 ( Op.add(position ,new Vec3(-0.5f * box_size, -0.5f * box_size, 0))));
				settings.setHingeAxis1 ( settings.setHingeAxis2 ( Vec3.sAxisZ()));
				settings.setNormalAxis1 ( settings.setNormalAxis2 ( Vec3.sAxisX()));
			}
			settings.setLimitsMin ( cMinAngle);
			settings.setLimitsMax ( cMaxAngle);
			mPhysicsSystem.addConstraint(settings.create(prev, segment));

			prev = segment;
		}
	}

	{
		// Two bodies connected with a hard hinge
		Body body1 = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(1.0f)),new RVec3(4, 5, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
		body1.setCollisionGroup(new CollisionGroup(group_filter, 0, 0));
		mBodyInterface.addBody(body1.getId(), EActivation.DontActivate);
		Body body2 = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(1.0f)),new RVec3(6, 5, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		body2.setCollisionGroup(new CollisionGroup(group_filter, 0, 1));
		mBodyInterface.addBody(body2.getId(), EActivation.Activate);

		HingeConstraintSettings hinge=new HingeConstraintSettings();
		hinge.setPoint1 ( hinge.setPoint2 (new RVec3(5, 4, 0)));
		hinge.setHingeAxis1 ( hinge.setHingeAxis2 ( Vec3.sAxisZ()));
		hinge.setNormalAxis1 ( hinge.setNormalAxis2 ( Vec3.sAxisY()));
		hinge.setLimitsMin ( degreesToRadians(-10.0f));
		hinge.setLimitsMax ( degreesToRadians(110.0f));
		mPhysicsSystem.addConstraint(hinge.create(body1, body2));
	}

	{
		// Two bodies connected with a soft hinge
		Body body1 = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(1.0f)),new RVec3(10, 5, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
		body1.setCollisionGroup(new CollisionGroup(group_filter, 0, 0));
		mBodyInterface.addBody(body1.getId(), EActivation.DontActivate);
		Body body2 = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(1.0f)),new RVec3(12, 5, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		body2.setCollisionGroup(new CollisionGroup(group_filter, 0, 1));
		mBodyInterface.addBody(body2.getId(), EActivation.Activate);

		HingeConstraintSettings hinge=new HingeConstraintSettings();
		hinge.setPoint1 ( hinge.setPoint2 (new RVec3(11, 4, 0)));
		hinge.setHingeAxis1 ( hinge.setHingeAxis2 ( Vec3.sAxisZ()));
		hinge.setNormalAxis1 ( hinge.setNormalAxis2 ( Vec3.sAxisY()));
		hinge.setLimitsMin ( degreesToRadians(-10.0f));
		hinge.setLimitsMax ( degreesToRadians(110.0f));
		hinge.getLimitsSpringSettings().setFrequency ( 1.0f);
		hinge.getLimitsSpringSettings().setDamping ( 0.5f);
		mPhysicsSystem.addConstraint(hinge.create(body1, body2));
	}
}
}
