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
package testjoltjni.app.samples.general;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.operator.Op;
import com.github.stephengold.joltjni.readonly.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
/**
 * A line-for-line Java translation of the Jolt Physics conveyor-belt test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/ConveyorBeltTest.cpp
 */
public class ConveyorBeltTest extends Test{
BodyIdVector mLinearBelts=new BodyIdVector();
BodyId mAngularBelt=new BodyId();

public void Initialize()
{
	// Floor
	CreateFloor();

	// Create conveyor belts
	final float cBeltWidth = 10.0f;
	final float cBeltLength = 50.0f;
	BodyCreationSettings belt_settings=new BodyCreationSettings(new BoxShape(new Vec3(cBeltWidth, 0.1f, cBeltLength)), RVec3.sZero(), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
	for (int i = 0; i < 4; ++i)
	{
		belt_settings.setFriction ( 0.25f * (i + 1));
		belt_settings.setRotation ( Op.multiply(Quat.sRotation(Vec3.sAxisY(), 0.5f * JPH_PI * i) , Quat.sRotation(Vec3.sAxisX(), degreesToRadians(1.0f))));
		belt_settings.setPosition (new RVec3(Op.rotate(belt_settings.getRotation() ,new Vec3(cBeltLength, 6.0f, cBeltWidth))));
		mLinearBelts.pushBack(mBodyInterface.createAndAddBody(belt_settings, EActivation.DontActivate));
	}

	// Bodies with decreasing friction
	BodyCreationSettings cargo_settings=new BodyCreationSettings(new BoxShape(Vec3.sReplicate(2.0f)), RVec3.sZero(), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
	for (int i = 0; i <= 10; ++i)
	{
		cargo_settings.setPosition (new RVec3(-cBeltLength + i * 10.0f, 10.0f, -cBeltLength));
		cargo_settings.setFriction ( Math.max(0.0f, 1.0f - 0.1f * i));
		mBodyInterface.createAndAddBody(cargo_settings, EActivation.Activate);
	}

	// Create 2 cylinders
	BodyCreationSettings cylinder_settings=new BodyCreationSettings(new CylinderShape(6.0f, 1.0f),new RVec3(-25.0f, 1.0f, -20.0f), Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI), EMotionType.Dynamic, Layers.MOVING);
	mBodyInterface.createAndAddBody(cylinder_settings, EActivation.Activate);
	cylinder_settings.setPosition(cylinder_settings.getPosition().setZ(20.0f));
	mBodyInterface.createAndAddBody(cylinder_settings, EActivation.Activate);

	// Let a dynamic belt rest on it
	BodyCreationSettings dynamic_belt=new BodyCreationSettings(new BoxShape(new Vec3(5.0f, 0.1f, 25.0f), 0.0f),new RVec3(-25.0f, 3.0f, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
	mLinearBelts.pushBack(mBodyInterface.createAndAddBody(dynamic_belt, EActivation.Activate));

	// Create cargo on the dynamic belt
	cargo_settings.setPosition (new RVec3(-25.0f, 6.0f, 15.0f));
	cargo_settings.setFriction ( 1.0f);
	mBodyInterface.createAndAddBody(cargo_settings, EActivation.Activate);

	// Create an angular belt
	BodyCreationSettings angular_belt=new BodyCreationSettings(new BoxShape(new Vec3(20.0f, 0.1f, 20.0f), 0.0f),new RVec3(10.0f, 3.0f, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
	mAngularBelt = mBodyInterface.createAndAddBody(angular_belt, EActivation.Activate);

	// Bodies with decreasing friction dropping on the angular belt
	for (int i = 0; i <= 6; ++i)
	{
		cargo_settings.setPosition (new RVec3(10.0f, 10.0f, -15.0f + 5.0f * i));
		cargo_settings.setFriction ( Math.max(0.0f, 1.0f - 0.1f * i));
		mBodyInterface.createAndAddBody(cargo_settings, EActivation.Activate);
	}
}

void OnContactAdded(ConstBody inBody1, ConstBody inBody2, ConstContactManifold inManifold, ContactSettings ioSettings)
{
	// Linear belts
	boolean body1_linear_belt = mLinearBelts.find(inBody1.getId()) != -1;
	boolean body2_linear_belt = mLinearBelts.find(inBody2.getId()) != -1;
	if (body1_linear_belt || body2_linear_belt)
	{
		// Determine the world space surface velocity of both bodies
		Vec3Arg cLocalSpaceVelocity=new Vec3(0, 0, -10.0f);
		Vec3 body1_linear_surface_velocity = body1_linear_belt? Op.rotate(inBody1.getRotation() , cLocalSpaceVelocity) : Vec3.sZero();
		Vec3 body2_linear_surface_velocity = body2_linear_belt? Op.rotate(inBody2.getRotation() , cLocalSpaceVelocity) : Vec3.sZero();

		// Calculate the relative surface velocity
		ioSettings.setRelativeLinearSurfaceVelocity (Op.subtract( body2_linear_surface_velocity , body1_linear_surface_velocity));
	}

	// Angular belt
	boolean body1_angular = Op.equals(inBody1.getId() , mAngularBelt);
	boolean body2_angular = Op.equals(inBody2.getId() , mAngularBelt);
	if (body1_angular || body2_angular)
	{
		// Determine the world space angular surface velocity of both bodies
		Vec3Arg cLocalSpaceAngularVelocity=new Vec3(0, degreesToRadians(10.0f), 0);
		Vec3 body1_angular_surface_velocity = body1_angular? Op.rotate(inBody1.getRotation() , cLocalSpaceAngularVelocity) : Vec3.sZero();
		Vec3 body2_angular_surface_velocity = body2_angular? Op.rotate(inBody2.getRotation() , cLocalSpaceAngularVelocity) : Vec3.sZero();

		// Note that the angular velocity is the angular velocity around body 1's center of mass, so we need to add the linear velocity of body 2's center of mass
		Vec3 body2_linear_surface_velocity = body2_angular? body2_angular_surface_velocity.cross(new Vec3(Op.subtract(inBody1.getCenterOfMassPosition() , inBody2.getCenterOfMassPosition()))) : Vec3.sZero();

		// Calculate the relative angular surface velocity
		ioSettings.setRelativeLinearSurfaceVelocity ( body2_linear_surface_velocity);
		ioSettings.setRelativeAngularSurfaceVelocity ( Op.subtract(body2_angular_surface_velocity , body1_angular_surface_velocity));
	}
}

void OnContactPersisted(ConstBody inBody1, ConstBody inBody2, ConstContactManifold inManifold, ContactSettings ioSettings)
{
	// Same behavior as contact added
	OnContactAdded(inBody1, inBody2, inManifold, ioSettings);
}
}
