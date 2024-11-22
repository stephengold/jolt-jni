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
package testjoltjni.app.samples.vehicle;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.operator.Op;
import com.github.stephengold.joltjni.readonly.*;
import testjoltjni.app.samples.*;
import testjoltjni.app.testframework.*;
import static com.github.stephengold.joltjni.Jolt.*;
/**
 * A line-for-line Java translation of the Jolt Physics motorcycle test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Vehicle/MotorcycleTest.cpp
 */
public class MotorcycleTest extends VehicleTest{
boolean sOverrideFrontSuspensionForcePoint,sOverrideRearSuspensionForcePoint;
boolean sEnableLeanController=true;
boolean sOverrideGravity;
Body mMotorcycleBody;
VehicleConstraint mVehicleConstraint;
RMat44 mCameraPivot=RMat44.sIdentity();
float mBrake,mForward,mRight;
float mPreviousForward=1f;

public void Initialize()
{
	super.Initialize();

	// Loosely based on: https://www.whitedogbikes.com/whitedogblog/yamaha-xj-900-specs/
	final float back_wheel_radius = 0.31f;
	final float back_wheel_width = 0.05f;
	final float back_wheel_pos_z = -0.75f;
	final float back_suspension_min_length = 0.3f;
	final float back_suspension_max_length = 0.5f;
	final float back_suspension_freq = 2.0f;
	final float back_brake_torque = 250.0f;

	final float front_wheel_radius = 0.31f;
	final float front_wheel_width = 0.05f;
	final float front_wheel_pos_z = 0.75f;
	final float front_suspension_min_length = 0.3f;
	final float front_suspension_max_length = 0.5f;
	final float front_suspension_freq = 1.5f;
	final float front_brake_torque = 500.0f;

	final float half_vehicle_length = 0.4f;
	final float half_vehicle_width = 0.2f;
	final float half_vehicle_height = 0.3f;

	final float max_steering_angle = degreesToRadians(30);

	// Angle of the front suspension
	final float caster_angle = degreesToRadians(30);

	// Create vehicle body
	RVec3 position=new RVec3(0, 2, 0);
	ShapeRefC motorcycle_shape =new OffsetCenterOfMassShapeSettings(new Vec3(0, -half_vehicle_height, 0), new BoxShape(new Vec3(half_vehicle_width, half_vehicle_height, half_vehicle_length)).toRefC()).create().get();
	BodyCreationSettings motorcycle_body_settings=new BodyCreationSettings(motorcycle_shape, position, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
	motorcycle_body_settings.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
	motorcycle_body_settings.getMassPropertiesOverride().setMass ( 240.0f);
	mMotorcycleBody = mBodyInterface.createBody(motorcycle_body_settings);
	mBodyInterface.addBody(mMotorcycleBody.getId(), EActivation.Activate);

	// Create vehicle constraint
	VehicleConstraintSettings vehicle=new VehicleConstraintSettings();
	vehicle.setDrawConstraintSize ( 0.1f);
	vehicle.setMaxPitchRollAngle ( degreesToRadians(60.0f));

	// Wheels
	WheelSettingsWv front = new WheelSettingsWv();
	front.setPosition (new Vec3(0.0f, -0.9f * half_vehicle_height, front_wheel_pos_z));
	front.setMaxSteerAngle ( max_steering_angle);
	front.setSuspensionDirection (new Vec3(0, -1, Math.tan(caster_angle)).normalized());
	front.setSteeringAxis ( Op.minus(front.getSuspensionDirection()));
	front.setRadius ( front_wheel_radius);
	front.setWidth ( front_wheel_width);
	front.setSuspensionMinLength ( front_suspension_min_length);
	front.setSuspensionMaxLength ( front_suspension_max_length);
	front.getSuspensionSpring().setFrequency ( front_suspension_freq);
	front.setMaxBrakeTorque ( front_brake_torque);

	WheelSettingsWv back = new WheelSettingsWv();
	back.setPosition (new Vec3(0.0f, -0.9f * half_vehicle_height, back_wheel_pos_z));
	back.setMaxSteerAngle ( 0.0f);
	back.setRadius ( back_wheel_radius);
	back.setWidth ( back_wheel_width);
	back.setSuspensionMinLength ( back_suspension_min_length);
	back.setSuspensionMaxLength ( back_suspension_max_length);
	back.getSuspensionSpring().setFrequency ( back_suspension_freq);
	back.setMaxBrakeTorque ( back_brake_torque);

	if (sOverrideFrontSuspensionForcePoint)
	{
		front.setEnableSuspensionForcePoint ( true);
		front.setSuspensionForcePoint ( Op.plus(front.getPosition() , Op.star(front.getSuspensionDirection() , front.getSuspensionMinLength())));
	}

	if (sOverrideRearSuspensionForcePoint)
	{
		back.setEnableSuspensionForcePoint ( true);
		back.setSuspensionForcePoint ( Op.plus(back.getPosition() , Op.star(back.getSuspensionDirection() , back.getSuspensionMinLength())));
	}

	vehicle.addWheels ( front, back );

	MotorcycleControllerSettings controller = new MotorcycleControllerSettings();
	controller.getEngine().setMaxTorque ( 150.0f);
	controller.getEngine().setMinRpm ( 1000.0f);
	controller.getEngine().setMaxRpm ( 10000.0f);
	controller.getTransmission().setShiftDownRpm ( 2000.0f);
	controller.getTransmission().setShiftUpRpm ( 8000.0f);
	controller.getTransmission().setGearRatios (  2.27f, 1.63f, 1.3f, 1.09f, 0.96f, 0.88f ); // From: https://www.blocklayer.com/rpm-gear-bikes
	controller.getTransmission().setReverseGearRatios (  -4.0f );
	controller.getTransmission().setClutchStrength ( 2.0f);
	vehicle.setController ( controller);

	// Differential (not really applicable to a motorcycle but we need one anyway to drive it)
	controller.setNumDifferentials(1);
	controller.getDifferential(0).setLeftWheel ( -1);
	controller.getDifferential(0).setRightWheel ( 1);
	controller.getDifferential(0).setDifferentialRatio ( 1.93f * 40.0f / 16.0f); // Combining primary and final drive (back divided by front sprockets) from: https://www.blocklayer.com/rpm-gear-bikes

	mVehicleConstraint = new VehicleConstraint(mMotorcycleBody, vehicle);
	mVehicleConstraint.setVehicleCollisionTester(new VehicleCollisionTesterCastCylinder(Layers.MOVING, 1.0f)); // Use half wheel width as convex radius so we get a rounded cylinder
	mPhysicsSystem.addConstraint(mVehicleConstraint);
	mPhysicsSystem.addStepListener(mVehicleConstraint);

	UpdateCameraPivot();
}
/*TODO

void MotorcycleTest::ProcessInput(const ProcessInputParams &inParams)
{
	// Determine acceleration and brake
	mForward = 0.0f;
	mBrake = 0.0f;
	if (inParams.mKeyboard->IsKeyPressed(DIK_Z))
		mBrake = 1.0f;
	else if (inParams.mKeyboard->IsKeyPressed(DIK_UP))
		mForward = 1.0f;
	else if (inParams.mKeyboard->IsKeyPressed(DIK_DOWN))
		mForward = -1.0f;

	// Check if we're reversing direction
	if (mPreviousForward * mForward < 0.0f)
	{
		// Get vehicle velocity in local space to the body of the vehicle
		float velocity = (mMotorcycleBody->GetRotation().Conjugated() * mMotorcycleBody->GetLinearVelocity()).GetZ();
		if ((mForward > 0.0f && velocity < -0.1f) || (mForward < 0.0f && velocity > 0.1f))
		{
			// Brake while we've not stopped yet
			mForward = 0.0f;
			mBrake = 1.0f;
		}
		else
		{
			// When we've come to a stop, accept the new direction
			mPreviousForward = mForward;
		}
	}

	// Steering
	float right = 0.0f;
	if (inParams.mKeyboard->IsKeyPressed(DIK_LEFT))
		right = -1.0f;
	else if (inParams.mKeyboard->IsKeyPressed(DIK_RIGHT))
		right = 1.0f;
	const float steer_speed = 4.0f;
	if (right > mRight)
		mRight = min(mRight + steer_speed * inParams.mDeltaTime, right);
	else if (right < mRight)
		mRight = max(mRight - steer_speed * inParams.mDeltaTime, right);

	// When leaned, we don't want to use the brakes fully as we'll spin out
	if (mBrake > 0.0f)
	{
		Vec3 world_up = -mPhysicsSystem->GetGravity().Normalized();
		Vec3 up = mMotorcycleBody->GetRotation() * mVehicleConstraint->GetLocalUp();
		Vec3 fwd = mMotorcycleBody->GetRotation() * mVehicleConstraint->GetLocalForward();
		float sin_lean_angle = abs(world_up.Cross(up).Dot(fwd));
		float brake_multiplier = Square(1.0f - sin_lean_angle);
		mBrake *= brake_multiplier;
	}
}
*/

public void PrePhysicsUpdate(PreUpdateParams inParams)
{
	super.PrePhysicsUpdate(inParams);

	UpdateCameraPivot();

	// On user input, assure that the motorcycle is active
	if (mRight != 0.0f || mForward != 0.0f || mBrake != 0.0f)
		mBodyInterface.activateBody(mMotorcycleBody.getId());

	// Pass the input on to the constraint
	MotorcycleController controller = (MotorcycleController)(mVehicleConstraint.getController());
	controller.setDriverInput(mForward, mRight, mBrake, 0f);
	controller.enableLeanController(sEnableLeanController);

	if (sOverrideGravity)
	{
		// When overriding gravity is requested, we cast a sphere downwards (opposite to the previous up position) and use the contact normal as the new gravity direction
		SphereShape sphere=new SphereShape(0.5f);
		sphere.setEmbedded();
		RShapeCast shape_cast=new RShapeCast(sphere, Vec3.sReplicate(1.0f), RMat44.sTranslation(mMotorcycleBody.getPosition()), Op.star(-3.0f , mVehicleConstraint.getWorldUp()));
		ShapeCastSettings settings=new ShapeCastSettings();
		ClosestHitCastShapeCollector collector=new ClosestHitCastShapeCollector();
		mPhysicsSystem.getNarrowPhaseQuery().castShape(shape_cast, settings, mMotorcycleBody.getPosition(), collector,new SpecifiedBroadPhaseLayerFilter(BroadPhaseLayers.NON_MOVING),new SpecifiedObjectLayerFilter(Layers.NON_MOVING));
		if (collector.hadHit())
			mVehicleConstraint.overrideGravity(Op.star(9.81f , collector.getHit().getPenetrationAxis().normalized()));
		else
			mVehicleConstraint.resetGravityOverride();
	}

	// Draw our wheels (this needs to be done in the pre update since we draw the bodies too in the state before the step)
	for (int w = 0; w < 2; ++w)
	{
		ConstWheelSettings  settings = mVehicleConstraint.getWheel(w).getSettings();
		RMat44 wheel_transform = mVehicleConstraint.getWheelWorldTransform(w, Vec3.sAxisY(), Vec3.sAxisX()); // The cylinder we draw is aligned with Y so we specify that as rotational axis
		mDebugRenderer.drawCylinder(wheel_transform, 0.5f * settings.getWidth(), settings.getRadius(), Color.sGreen);
	}
}

public void SaveInputState(StateRecorder inStream)
{
	inStream.write(mForward);
	inStream.write(mPreviousForward);
	inStream.write(mRight);
	inStream.write(mBrake);
}

public void RestoreInputState(StateRecorder inStream)
{
	mForward=inStream.readFloat(mForward);
	mPreviousForward=inStream.readFloat(mPreviousForward);
	mRight=inStream.readFloat(mRight);
	mBrake=inStream.readFloat(mBrake);
}

public void GetInitialCamera(CameraState ioState)
{
	// Position camera behind motorcycle
	RVec3 cam_tgt = new RVec3(0, 0, 5);
	ioState.mPos = new RVec3(0, 2.5f, -5);
	ioState.mForward = Op.minus(cam_tgt , ioState.mPos).toVec3().normalized();
}

void UpdateCameraPivot()
{
	// Pivot is center of motorcycle and rotates with motorcycle around Y axis only
	Vec3 fwd = mMotorcycleBody.getRotation().rotateAxisZ();
	fwd.setY(0.0f);
	float len = fwd.length();
	if (len != 0.0f)
		Op.slashEquals(fwd , len);
	else
		fwd = Vec3.sAxisZ();
	Vec3 up = Vec3.sAxisY();
	Vec3 right = up.cross(fwd);
	mCameraPivot =new RMat44(new Vec4(right, 0), new Vec4(up, 0), new Vec4(fwd, 0), mMotorcycleBody.getPosition());
}
/*TODO

void MotorcycleTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	VehicleTest::CreateSettingsMenu(inUI, inSubMenu);

	inUI->CreateCheckBox(inSubMenu, "Override Front Suspension Force Point", sOverrideFrontSuspensionForcePoint, [](UICheckBox.EState inState) { sOverrideFrontSuspensionForcePoint = inState == UICheckBox.STATE_CHECKED; });
	inUI->CreateCheckBox(inSubMenu, "Override Rear Suspension Force Point", sOverrideRearSuspensionForcePoint, [](UICheckBox.EState inState) { sOverrideRearSuspensionForcePoint = inState == UICheckBox.STATE_CHECKED; });
	inUI->CreateCheckBox(inSubMenu, "Enable Lean Controller", sEnableLeanController, [](UICheckBox.EState inState) { sEnableLeanController = inState == UICheckBox.STATE_CHECKED; });
	inUI->CreateCheckBox(inSubMenu, "Override Gravity", sOverrideGravity, [](UICheckBox.EState inState) { sOverrideGravity = inState == UICheckBox.STATE_CHECKED; });
	inUI->CreateTextButton(inSubMenu, "Accept", [this]() { RestartTest(); });
}
*/}
