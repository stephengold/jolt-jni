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
 * A line-for-line Java translation of the Jolt Physics fixed-constraint test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/FixedConstraintTest.cpp
 */
public class FixedConstraintTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	float box_size = 4.0f;
	ShapeRefC box = new BoxShape(Vec3.sReplicate(0.5f * box_size)).toRefC();

	final int num_bodies = 10;

	// Build a collision group filter that disables collision between adjacent bodies
	GroupFilterTableRef group_filter = new GroupFilterTable(num_bodies).toRef();
	for (int i = 0; i < num_bodies - 1; ++i)
		group_filter.disableCollision(i, i + 1);

	// Bodies attached through fixed constraints
	for (int randomness = 0; randomness < 2; ++randomness)
	{
		int group_id = (randomness);

		RVec3 position=new RVec3(0, 25.0f, -randomness * 20.0f);
		Body top = mBodyInterface.createBody(new BodyCreationSettings(box, position, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
		top.setCollisionGroup(new CollisionGroup(group_filter, group_id, 0));
		mBodyInterface.addBody(top.getId(), EActivation.DontActivate);

		DefaultRandomEngine random=new DefaultRandomEngine();
		UniformRealDistribution displacement=new UniformRealDistribution(-1.0f, 1.0f);

		Body prev = top;
		for (int i = 1; i < num_bodies; ++i)
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

			FixedConstraintSettings settings=new FixedConstraintSettings();
			settings.setAutoDetectPoint (true);
			TwoBodyConstraint c = settings.create(prev, segment);
			mPhysicsSystem.addConstraint(c);

			prev = segment;
		}
	}

	{
		// Two light bodies attached to a heavy body (gives issues if the wrong anchor point is chosen)
		Body light1 = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(0.1f)),new RVec3(-5.0f, 7.0f, -5.2f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		mBodyInterface.addBody(light1.getId(), EActivation.Activate);
		Body heavy = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(5.0f)),new RVec3(-5.0f, 7.0f, 0.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		mBodyInterface.addBody(heavy.getId(), EActivation.Activate);
		Body light2 = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(0.1f)),new RVec3(-5.0f, 7.0f, 5.2f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		mBodyInterface.addBody(light2.getId(), EActivation.Activate);

		FixedConstraintSettings light1_heavy=new FixedConstraintSettings();
		light1_heavy.setAutoDetectPoint ( true);
		mPhysicsSystem.addConstraint(light1_heavy.create(light1, heavy));

		FixedConstraintSettings heavy_light2=new FixedConstraintSettings();
		heavy_light2.setAutoDetectPoint ( true);
		mPhysicsSystem.addConstraint(heavy_light2.create(heavy, light2));
	}

	{
		// A tower of beams and crossbeams (note that it is not recommended to make constructs with this many fixed constraints, this is not always stable)
		RVec3 base_position=new RVec3(0, 25, -40.0f);
		Quat base_rotation = Quat.sRotation(Vec3.sAxisZ(), -0.5f * JPH_PI);

		ShapeRef pillar = new BoxShape(new Vec3(0.1f, 1.0f, 0.1f), 0.0f).toRef();
		ShapeRef beam = new BoxShape(new Vec3(0.01f, 1.5f, 0.1f), 0.0f).toRef();

		Body[] prev_pillars = { Body.sFixedToWorld(), Body.sFixedToWorld(), Body.sFixedToWorld(), Body.sFixedToWorld() };

		Vec3 center = Vec3.sZero();
		for (int y = 0; y < 10; ++y)
		{
			// Create pillars
			Body[] pillars=new Body[4];
			for (int i = 0; i < 4; ++i)
			{
				Quat rotation = Quat.sRotation(Vec3.sAxisY(), i * 0.5f * JPH_PI);

				pillars[i] = mBodyInterface.createBody(new BodyCreationSettings(pillar, Op.plus(base_position , Op.star(base_rotation , Op.plus(center , Op.star(rotation ,new Vec3(1.0f, 1.0f, 1.0f))))), base_rotation, EMotionType.Dynamic, Layers.MOVING));
				pillars[i].setCollisionGroup(new CollisionGroup(group_filter, 0, 0)); // For convenience, we disable collisions between all objects in the tower
				mBodyInterface.addBody(pillars[i].getId(), EActivation.Activate);
			}

			for (int i = 0; i < 4; ++i)
			{
				Quat rotation = Quat.sRotation(Vec3.sAxisY(), i * 0.5f * JPH_PI);

				// Create cross beam
				Body cross = mBodyInterface.createBody(new BodyCreationSettings(beam, Op.plus(base_position , Op.star(base_rotation , Op.plus(center , Op.star(rotation ,new Vec3(1.105f, 1.0f, 0.0f))))), Op.star(base_rotation , Op.star(rotation , Quat.sRotation(Vec3.sAxisX(), 0.3f * JPH_PI))), EMotionType.Dynamic, Layers.MOVING));
				cross.setCollisionGroup(new CollisionGroup(group_filter, 0, 0));
				mBodyInterface.addBody(cross.getId(), EActivation.Activate);

				// Attach cross beam to pillars
				for (int j = 0; j < 2; ++j)
				{
					FixedConstraintSettings constraint=new FixedConstraintSettings();
					constraint.setAutoDetectPoint ( true);
					constraint.setNumVelocityStepsOverride ( 64); // This structure needs more solver steps to be stable
					constraint.setNumPositionStepsOverride ( buildType().equals("Release")? (64) :(8)); // In debug mode use less steps to preserve framerate (at the cost of stability)
					mPhysicsSystem.addConstraint(constraint.create(pillars[(i + j) % 4], cross));
				}

				// Attach to previous pillar
				if (prev_pillars[i] != nullptr)
				{
					FixedConstraintSettings constraint=new FixedConstraintSettings();
					constraint.setAutoDetectPoint ( true);
					constraint.setNumVelocityStepsOverride ( 64);
					constraint.setNumPositionStepsOverride ( buildType().equals("Release")? (64) :(8));
					mPhysicsSystem.addConstraint(constraint.create(prev_pillars[i], pillars[i]));
				}

				prev_pillars[i] = pillars[i];
			}

			Op.plusEquals(center ,new Vec3(0.0f, 2.0f, 0.0f));
		}

		// Create top
		Body top = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(new Vec3(1.2f, 0.1f, 1.2f)), Op.plus(base_position , Op.star(base_rotation , Op.plus(center ,new Vec3(0.0f, 0.1f, 0.0f)))), base_rotation, EMotionType.Dynamic, Layers.MOVING));
		top.setCollisionGroup(new CollisionGroup(group_filter, 0, 0));
		mBodyInterface.addBody(top.getId(), EActivation.Activate);

		// Attach top to pillars
		for (int i = 0; i < 4; ++i)
		{
			FixedConstraintSettings constraint=new FixedConstraintSettings();
			constraint.setAutoDetectPoint ( true);
			mPhysicsSystem.addConstraint(constraint.create(prev_pillars[i], top));
		}
	}
}
}
