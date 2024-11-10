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
 * A line-for-line Java translation of the Jolt Physics constraint-singularity test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/ConstraintSingularityTest.cpp
 */
public class ConstraintSingularityTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	float box_size = 4.0f;
	ShapeRefC box = new BoxShape(Vec3.sReplicate(0.5f * box_size)).toRefC();

	final int num_constraint_types = 2;
	final int num_configurations = 4;

	// Create group filter
	GroupFilterTableRef group_filter = new GroupFilterTable().toRef();
	int group_id = 0;

	for (int constraint_type = 0; constraint_type < num_constraint_types; ++constraint_type)
		for (int configuration = 0; configuration < num_configurations; ++configuration)
		{
			RVec3 test_position=new RVec3(10.0f * constraint_type, 10.0f + 10.0f * configuration, 0);

			Body body1 = mBodyInterface.createBody(new BodyCreationSettings(box, test_position, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
			body1.setCollisionGroup(new CollisionGroup(group_filter, group_id, 0));
			mBodyInterface.addBody(body1.getId(), EActivation.DontActivate);

			Body body2 = mBodyInterface.createBody(new BodyCreationSettings(box, Op.add(test_position ,new Vec3(box_size, 0, 0)), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
			body2.setCollisionGroup(new CollisionGroup(group_filter, group_id, 0));
			mBodyInterface.addBody(body2.getId(), EActivation.Activate);

			Constraint constraint;
			switch (constraint_type)
			{
			case 0:
				{
					HingeConstraintSettings settings=new HingeConstraintSettings();
					settings.setPoint1 ( settings.setPoint2 ( Op.add(test_position ,new Vec3(0.5f * box_size, 0, 0.5f * box_size))));
					settings.setHingeAxis1 ( settings.setHingeAxis2 ( Vec3.sAxisY()));
					settings.setNormalAxis1 ( settings.setNormalAxis2 ( Vec3.sAxisX()));
					settings.setLimitsMin ( -0.01f);
					settings.setLimitsMax ( 0.01f);
					constraint = settings.create(body1, body2);
					break;
				}

			default:
				{
					FixedConstraintSettings settings=new FixedConstraintSettings();
					settings.setAutoDetectPoint ( true);
					constraint = settings.create(body1, body2);
					break;
				}
			}

			mPhysicsSystem.addConstraint(constraint);

			RVec3 position;
			Quat orientation;
			switch (configuration)
			{
			case 0:
				position = Op.add(test_position ,new Vec3(0, 0, box_size));
				orientation = Quat.sRotation(Vec3.sAxisY(), degreesToRadians(180.0f));
				break;

			case 1:
				position = Op.add(test_position ,new Vec3(0, 0, box_size));
				orientation = Op.multiply(Quat.sRotation(Vec3.sAxisY(), degreesToRadians(-90.0f)) , Quat.sRotation(Vec3.sAxisX(), degreesToRadians(180.0f)));
				break;

			case 2:
				position = Op.add(test_position ,new Vec3(box_size, 0, 0));
				orientation = Op.multiply(Quat.sRotation(Vec3.sAxisY(), degreesToRadians(90.0f)) , Quat.sRotation(Vec3.sAxisZ(), degreesToRadians(90.0f)));
				break;

			default:
				assert(configuration == 3);
				position = Op.add(test_position ,new Vec3(-box_size, 0, 0));
				orientation = Op.multiply(Quat.sRotation(Vec3.sAxisY(), degreesToRadians(90.0f)) , Quat.sRotation(Vec3.sAxisZ(), degreesToRadians(90.0f)));
				break;
			}

			mBodyInterface.setPositionAndRotation(body2.getId(), position, orientation, EActivation.DontActivate);

			++group_id;
		}
}
}
