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
 * A line-for-line Java translation of the Jolt Physics slider-constraint test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/SliderConstraintTest.cpp
 */
public class SliderConstraintTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	final int cChainLength = 10;

	// Create group filter
	GroupFilterTableRef group_filter = new GroupFilterTable(cChainLength).toRef();
	for (int i = 0; i < cChainLength - 1; ++i)
		group_filter.disableCollision(i, i + 1);
	int group_id = 0;

	// Create box
	float box_size = 4.0f;
	ShapeRefC box = new BoxShape(Vec3.sReplicate(0.5f * box_size)).toRefC();

	// Bodies attached through slider constraints
	for (int randomness = 0; randomness < 2; ++randomness)
	{
		RVec3 position=new RVec3(0, 25.0f, -randomness * 20.0f);
		Body top = mBodyInterface.createBody(new BodyCreationSettings(box, position, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
		top.setCollisionGroup(new CollisionGroup(group_filter, group_id, 0));
		mBodyInterface.addBody(top.getId(), EActivation.DontActivate);

		DefaultRandomEngine random=new DefaultRandomEngine();
		UniformRealDistribution displacement=new UniformRealDistribution(-1.0f, 1.0f);

		Body prev = top;
		for (int i = 1; i < cChainLength; ++i)
		{
			Quat rotation;
			Vec3 slider_axis;
			if (randomness == 0)
			{
				Op.plusEquals(position ,new Vec3(box_size, 0, 0));
				rotation = Quat.sIdentity();
				slider_axis = Quat.sRotation(Vec3.sAxisZ(), -degreesToRadians(10)).rotateAxisX();
			}
			else
			{
				Op.plusEquals(position ,new Vec3(box_size + Math.abs(displacement.nextFloat(random)), displacement.nextFloat(random), displacement.nextFloat(random)));
				rotation = Quat.sRandom(random);
				slider_axis = Op.star( Quat.sRotation(Vec3.sAxisY(), displacement.nextFloat(random) * degreesToRadians(20)) , Quat.sRotation(Vec3.sAxisZ(), -degreesToRadians(10)).rotateAxisX());
			}

			Body segment = mBodyInterface.createBody(new BodyCreationSettings(box, position, rotation, EMotionType.Dynamic, Layers.MOVING));
			segment.setCollisionGroup(new CollisionGroup(group_filter, group_id, i));
			mBodyInterface.addBody(segment.getId(), EActivation.Activate);

			SliderConstraintSettings settings=new SliderConstraintSettings();
			settings.setAutoDetectPoint ( true);
			settings.setSliderAxis(slider_axis);
			settings.setLimitsMin ( -5.0f);
			settings.setLimitsMax ( 10.0f);
			mPhysicsSystem.addConstraint(settings.create(prev, segment));

			prev = segment;
		}

		group_id++;
	}

	{
		// Two light bodies attached to a heavy body (gives issues if the wrong anchor point is chosen)
		Body light1 = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(0.1f)),new RVec3(-5.0f, 7.0f, -5.2f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		light1.setCollisionGroup(new CollisionGroup(group_filter, group_id, 0));
		mBodyInterface.addBody(light1.getId(), EActivation.Activate);
		Body heavy = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(5.0f)),new RVec3(-5.0f, 7.0f, 0.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		heavy.setCollisionGroup(new CollisionGroup(group_filter, group_id, 1));
		mBodyInterface.addBody(heavy.getId(), EActivation.Activate);
		Body light2 = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(0.1f)),new RVec3(-5.0f, 7.0f, 5.2f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		light2.setCollisionGroup(new CollisionGroup(group_filter, group_id, 2));
		mBodyInterface.addBody(light2.getId(), EActivation.Activate);
		++group_id;

		// Note: This violates the recommendation that body 1 is heavier than body 2, therefore this constraint will not work well (the rotation constraint will not be solved accurately)
		SliderConstraintSettings slider1=new SliderConstraintSettings();
		slider1.setAutoDetectPoint ( true);
		slider1.setSliderAxis(Vec3.sAxisZ());
		slider1.setLimitsMin ( 0.0f);
		slider1.setLimitsMax ( 1.0f);
		mPhysicsSystem.addConstraint(slider1.create(light1, heavy));

		// This constraint has the heavy body as body 1 so will work fine
		SliderConstraintSettings slider2=new SliderConstraintSettings();
		slider2.setAutoDetectPoint ( true);
		slider2.setSliderAxis(Vec3.sAxisZ());
		slider2.setLimitsMin ( 0.0f);
		slider2.setLimitsMax ( 1.0f);
		mPhysicsSystem.addConstraint(slider2.create(heavy, light2));
	}

	{
		// Two bodies vertically stacked with a slider constraint
		Body vert1 = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(1.0f)),new RVec3(5, 9, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		vert1.setCollisionGroup(new CollisionGroup(group_filter, group_id, 0));
		mBodyInterface.addBody(vert1.getId(), EActivation.Activate);
		Body vert2 = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(1.0f)),new RVec3(5, 3, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		vert2.setCollisionGroup(new CollisionGroup(group_filter, group_id, 1));
		mBodyInterface.addBody(vert2.getId(), EActivation.Activate);
		++group_id;

		SliderConstraintSettings slider=new SliderConstraintSettings();
		slider.setAutoDetectPoint ( true);
		slider.setSliderAxis(Vec3.sAxisY());
		slider.setLimitsMin ( 0.0f);
		slider.setLimitsMax ( 2.0f);
		mPhysicsSystem.addConstraint(slider.create(vert1, vert2));
	}

	{
		// Two bodies vertically stacked with a slider constraint using soft limits
		Body vert1 = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(1.0f)),new RVec3(10, 9, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		vert1.setCollisionGroup(new CollisionGroup(group_filter, group_id, 0));
		mBodyInterface.addBody(vert1.getId(), EActivation.Activate);
		Body vert2 = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(1.0f)),new RVec3(10, 3, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		vert2.setCollisionGroup(new CollisionGroup(group_filter, group_id, 1));
		mBodyInterface.addBody(vert2.getId(), EActivation.Activate);
		++group_id;

		SliderConstraintSettings slider=new SliderConstraintSettings();
		slider.setAutoDetectPoint ( true);
		slider.setSliderAxis(Vec3.sAxisY());
		slider.setLimitsMin ( 0.0f);
		slider.setLimitsMax ( 2.0f);
		slider.getLimitsSpringSettings().setFrequency ( 1.0f);
		slider.getLimitsSpringSettings().setDamping ( 0.5f);
		mPhysicsSystem.addConstraint(slider.create(vert1, vert2));
	}
}
}
