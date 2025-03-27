/*
Copyright (c) 2025 Stephen Gold

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
import com.github.stephengold.joltjni.readonly.*;
import testjoltjni.app.samples.*;
import testjoltjni.app.testframework.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import static com.github.stephengold.joltjni.std.Std.*;
/**
 * A line-for-line Java translation of the Jolt Physics vehicle-constraint test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Vehicle/VehicleConstraintTest.cpp
 */
public class VehicleConstraintTest extends VehicleTest{
float sInitialRollAngle;
float sMaxRollAngle = degreesToRadians(60f);
float sMaxSteeringAngle = degreesToRadians(30f);
int sCollisionMode=2;
boolean sAntiRollbar=true;
boolean sLimitedSlipDifferentials=true;
boolean sFourWheelDrive,sOverrideGravity;
float sMaxEngineTorque=500f;
float sClutchStrength=10f;
float sFrontCamber,sFrontCasterAngle,sFrontKingPinAngle,sFrontToe;
float sFrontSuspensionForwardAngle,sFrontSuspensionSidewaysAngle;
float sFrontSuspensionMinLength=.3f;
float sFrontSuspensionMaxLength=.5f;
float sFrontSuspensionFrequency=1.5f;
float sFrontSuspensionDamping=.5f;
float sRearCamber,sRearCasterAngle,sRearKingPinAngle;
float sRearSuspensionForwardAngle,sRearSuspensionSidewaysAngle,sRearToe;
float sRearSuspensionMinLength=.3f;
float sRearSuspensionMaxLength=.5f;
float sRearSuspensionFrequency=1.5f;
float sRearSuspensionDamping=.5f;
Body mCarBody;
VehicleConstraintRef mVehicleConstraint=new VehicleConstraintRef();
VehicleCollisionTester[]mTesters=new VehicleCollisionTester[3];
RMat44 mCameraPivot=RMat44.sIdentity();
float mBrake,mForward,mHandBrake,mRight;
float mPreviousForward=1f;

public void Cleanup()
{
	mPhysicsSystem.removeStepListener(mVehicleConstraint.getPtr().getStepListener());
}

public void Initialize()
{
	super.Initialize();

	final float wheel_radius = 0.3f;
	final float wheel_width = 0.1f;
	final float half_vehicle_length = 2.0f;
	final float half_vehicle_width = 0.9f;
	final float half_vehicle_height = 0.2f;

	// Create collision testers
	mTesters[0] = new VehicleCollisionTesterRay(Layers.MOVING);
	mTesters[1] = new VehicleCollisionTesterCastSphere(Layers.MOVING, 0.5f * wheel_width);
	mTesters[2] = new VehicleCollisionTesterCastCylinder(Layers.MOVING);

	// Create vehicle body
	RVec3 position=new RVec3(0, 2, 0);
	ShapeRefC car_shape =new OffsetCenterOfMassShapeSettings(new Vec3(0, -half_vehicle_height, 0), new BoxShape(new Vec3(half_vehicle_width, half_vehicle_height, half_vehicle_length)).toRefC()).create().get();
	BodyCreationSettings car_body_settings=new BodyCreationSettings(car_shape, position, Quat.sRotation(Vec3.sAxisZ(), sInitialRollAngle), EMotionType.Dynamic, Layers.MOVING);
	car_body_settings.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
	car_body_settings.getMassPropertiesOverride().setMass ( 1500.0f);
	mCarBody = mBodyInterface.createBody(car_body_settings);
	mBodyInterface.addBody(mCarBody.getId(), EActivation.Activate);

	// Create vehicle constraint
	VehicleConstraintSettings vehicle=new VehicleConstraintSettings();
	vehicle.setDrawConstraintSize ( 0.1f);
	vehicle.setMaxPitchRollAngle ( sMaxRollAngle);

	// Suspension direction
	Vec3 front_suspension_dir =new Vec3(tan(sFrontSuspensionSidewaysAngle), -1, tan(sFrontSuspensionForwardAngle)).normalized();
	Vec3 front_steering_axis =new Vec3(-tan(sFrontKingPinAngle), 1, -tan(sFrontCasterAngle)).normalized();
	Vec3 front_wheel_up =new Vec3(sin(sFrontCamber), cos(sFrontCamber), 0);
	Vec3 front_wheel_forward =new Vec3(-sin(sFrontToe), 0, cos(sFrontToe));
	Vec3 rear_suspension_dir =new Vec3(tan(sRearSuspensionSidewaysAngle), -1, tan(sRearSuspensionForwardAngle)).normalized();
	Vec3 rear_steering_axis =new Vec3(-tan(sRearKingPinAngle), 1, -tan(sRearCasterAngle)).normalized();
	Vec3 rear_wheel_up =new Vec3(sin(sRearCamber), cos(sRearCamber), 0);
	Vec3 rear_wheel_forward =new Vec3(-sin(sRearToe), 0, cos(sRearToe));
	Vec3 flip_x=new Vec3(-1, 1, 1);

	// Wheels, left front
	WheelSettingsWv w1 = new WheelSettingsWv();
	w1.setPosition (new Vec3(half_vehicle_width, -0.9f * half_vehicle_height, half_vehicle_length - 2.0f * wheel_radius));
	w1.setSuspensionDirection ( front_suspension_dir);
	w1.setSteeringAxis ( front_steering_axis);
	w1.setWheelUp ( front_wheel_up);
	w1.setWheelForward ( front_wheel_forward);
	w1.setSuspensionMinLength ( sFrontSuspensionMinLength);
	w1.setSuspensionMaxLength ( sFrontSuspensionMaxLength);
	w1.getSuspensionSpring().setFrequency ( sFrontSuspensionFrequency);
	w1.getSuspensionSpring().setDamping ( sFrontSuspensionDamping);
	w1.setMaxSteerAngle ( sMaxSteeringAngle);
	w1.setMaxHandBrakeTorque ( 0.0f); // Front wheel doesn't have hand brake

	// Right front
	WheelSettingsWv w2 = new WheelSettingsWv();
	w2.setPosition (new Vec3(-half_vehicle_width, -0.9f * half_vehicle_height, half_vehicle_length - 2.0f * wheel_radius));
	w2.setSuspensionDirection ( star(flip_x , front_suspension_dir));
	w2.setSteeringAxis ( star(flip_x , front_steering_axis));
	w2.setWheelUp ( star(flip_x , front_wheel_up));
	w2.setWheelForward ( star(flip_x , front_wheel_forward));
	w2.setSuspensionMinLength ( sFrontSuspensionMinLength);
	w2.setSuspensionMaxLength ( sFrontSuspensionMaxLength);
	w2.getSuspensionSpring().setFrequency ( sFrontSuspensionFrequency);
	w2.getSuspensionSpring().setDamping ( sFrontSuspensionDamping);
	w2.setMaxSteerAngle ( sMaxSteeringAngle);
	w2.setMaxHandBrakeTorque ( 0.0f); // Front wheel doesn't have hand brake

	// Left rear
	WheelSettingsWv w3 = new WheelSettingsWv();
	w3.setPosition (new Vec3(half_vehicle_width, -0.9f * half_vehicle_height, -half_vehicle_length + 2.0f * wheel_radius));
	w3.setSuspensionDirection ( rear_suspension_dir);
	w3.setSteeringAxis ( rear_steering_axis);
	w3.setWheelUp ( rear_wheel_up);
	w3.setWheelForward ( rear_wheel_forward);
	w3.setSuspensionMinLength ( sRearSuspensionMinLength);
	w3.setSuspensionMaxLength ( sRearSuspensionMaxLength);
	w3.getSuspensionSpring().setFrequency ( sRearSuspensionFrequency);
	w3.getSuspensionSpring().setDamping ( sRearSuspensionDamping);
	w3.setMaxSteerAngle ( 0.0f);

	// Right rear
	WheelSettingsWv w4 = new WheelSettingsWv();
	w4.setPosition (new Vec3(-half_vehicle_width, -0.9f * half_vehicle_height, -half_vehicle_length + 2.0f * wheel_radius));
	w4.setSuspensionDirection ( star(flip_x , rear_suspension_dir));
	w4.setSteeringAxis ( star(flip_x , rear_steering_axis));
	w4.setWheelUp ( star(flip_x , rear_wheel_up));
	w4.setWheelForward ( star(flip_x , rear_wheel_forward));
	w4.setSuspensionMinLength ( sRearSuspensionMinLength);
	w4.setSuspensionMaxLength ( sRearSuspensionMaxLength);
	w4.getSuspensionSpring().setFrequency ( sRearSuspensionFrequency);
	w4.getSuspensionSpring().setDamping ( sRearSuspensionDamping);
	w4.setMaxSteerAngle ( 0.0f);

	vehicle.addWheels  ( w1, w2, w3, w4 );

	for (WheelSettings w : vehicle.getWheels())
	{
		w.setRadius ( wheel_radius);
		w.setWidth ( wheel_width);
	}

	WheeledVehicleControllerSettings controller = new WheeledVehicleControllerSettings();
	vehicle.setController ( controller);

	// Differential
	controller.setNumDifferentials(sFourWheelDrive? 2 : 1);
	controller.getDifferential(0).setLeftWheel ( 0);
	controller.getDifferential(0).setRightWheel ( 1);
	if (sFourWheelDrive)
	{
		controller.getDifferential(1).setLeftWheel ( 2);
		controller.getDifferential(1).setRightWheel ( 3);

		// Split engine torque
		controller.getDifferential(0).setEngineTorqueRatio ( controller.getDifferential(1).setEngineTorqueRatio ( 0.5f));
	}

	// Anti rollbars
	if (sAntiRollbar)
	{
		vehicle.setNumAntiRollBars(2);
		vehicle.getAntiRollBar(0).setLeftWheel ( 0);
		vehicle.getAntiRollBar(0).setRightWheel ( 1);
		vehicle.getAntiRollBar(1).setLeftWheel ( 2);
		vehicle.getAntiRollBar(1).setRightWheel ( 3);
	}

	mVehicleConstraint = new VehicleConstraint(mCarBody, vehicle).toRef();


	mPhysicsSystem.addConstraint(mVehicleConstraint.getPtr());
	mPhysicsSystem.addStepListener(mVehicleConstraint.getPtr().getStepListener());

	UpdateCameraPivot();
}
/*TODO

void VehicleConstraintTest::ProcessInput(const ProcessInputParams &inParams)
{
	// Determine acceleration and brake
	mForward = 0.0f;
	if (inParams.mKeyboard->IsKeyPressed(EKey::Up))
		mForward = 1.0f;
	else if (inParams.mKeyboard->IsKeyPressed(EKey::Down))
		mForward = -1.0f;

	// Check if we're reversing direction
	mBrake = 0.0f;
	if (mPreviousForward * mForward < 0.0f)
	{
		// Get vehicle velocity in local space to the body of the vehicle
		float velocity = (mCarBody->GetRotation().Conjugated() * mCarBody->GetLinearVelocity()).GetZ();
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

	// Hand brake will cancel gas pedal
	mHandBrake = 0.0f;
	if (inParams.mKeyboard->IsKeyPressed(EKey::Z))
	{
		mForward = 0.0f;
		mHandBrake = 1.0f;
	}

	// Steering
	mRight = 0.0f;
	if (inParams.mKeyboard->IsKeyPressed(EKey::Left))
		mRight = -1.0f;
	else if (inParams.mKeyboard->IsKeyPressed(EKey::Right))
		mRight = 1.0f;
}
*/

public void PrePhysicsUpdate(PreUpdateParams inParams)
{
	super.PrePhysicsUpdate(inParams);

	UpdateCameraPivot();

	// On user input, assure that the car is active
	if (mRight != 0.0f || mForward != 0.0f || mBrake != 0.0f || mHandBrake != 0.0f)
		mBodyInterface.activateBody(mCarBody.getId());

	WheeledVehicleController controller = (WheeledVehicleController) (mVehicleConstraint.getPtr().getController());

	// Update vehicle statistics
	controller.getEngine().setMaxTorque ( sMaxEngineTorque);
	controller.getTransmission().setClutchStrength ( sClutchStrength);

	// Set slip ratios to the same for everything
	float limited_slip_ratio = sLimitedSlipDifferentials? 1.4f : FLT_MAX;
	controller.setDifferentialLimitedSlipRatio(limited_slip_ratio);
	for (VehicleDifferentialSettings d : controller.getDifferentials())
		d.setLimitedSlipRatio ( limited_slip_ratio);

	// Pass the input on to the constraint
	controller.setDriverInput(mForward, mRight, mBrake, mHandBrake);

	// Set the collision tester
	mVehicleConstraint.getPtr().setVehicleCollisionTester(mTesters[sCollisionMode]);

	if (sOverrideGravity)
	{
		// When overriding gravity is requested, we cast a sphere downwards (opposite to the previous up position) and use the contact normal as the new gravity direction
		SphereShape sphere=new SphereShape(0.5f);
		sphere.setEmbedded();
		RShapeCast shape_cast=new RShapeCast(sphere, Vec3.sReplicate(1.0f), RMat44.sTranslation(mCarBody.getPosition()), star(-3.0f , mVehicleConstraint.getWorldUp()));
		ShapeCastSettings settings=new ShapeCastSettings();
		ClosestHitCastShapeCollector collector=new ClosestHitCastShapeCollector();
		mPhysicsSystem.getNarrowPhaseQuery().castShape(shape_cast, settings, mCarBody.getPosition(), collector,new SpecifiedBroadPhaseLayerFilter(BroadPhaseLayers.NON_MOVING),new SpecifiedObjectLayerFilter(Layers.NON_MOVING));
		if (collector.hadHit())
			mVehicleConstraint.overrideGravity(star(9.81f , collector.getHit().getPenetrationAxis().normalized()));
		else
			mVehicleConstraint.resetGravityOverride();
	}

	// Draw our wheels (this needs to be done in the pre update since we draw the bodies too in the state before the step)
	for (int w = 0; w < 4; ++w)
	{
		ConstWheelSettings settings = mVehicleConstraint.getPtr().getWheel(w).getSettings();
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
	inStream.write(mHandBrake);
}

public void RestoreInputState(StateRecorder inStream)
{
	mForward=inStream.readFloat(mForward);
	mPreviousForward=inStream.readFloat(mPreviousForward);
	mRight=inStream.readFloat(mRight);
	mBrake=inStream.readFloat(mBrake);
	mHandBrake=inStream.readFloat(mHandBrake);
}

public void GetInitialCamera(CameraState ioState)
{
	// Position camera behind car
	RVec3 cam_tgt = new RVec3(0, 0, 5);
	ioState.mPos = new RVec3(0, 2.5f, -5);
	ioState.mForward = minus(cam_tgt , ioState.mPos).toVec3().normalized();
}

void UpdateCameraPivot()
{
	// Pivot is center of car and rotates with car around Y axis only
	Vec3 fwd = mCarBody.getRotation().rotateAxisZ();
	fwd.setY(0.0f);
	float len = fwd.length();
	if (len != 0.0f)
		slashEquals(fwd , len);
	else
		fwd = Vec3.sAxisZ();
	Vec3 up = Vec3.sAxisY();
	Vec3 right = up.cross(fwd);
	mCameraPivot =new RMat44(new Vec4(right, 0), new Vec4(up, 0), new Vec4(fwd, 0), mCarBody.getPosition());
}
/*TODO

void VehicleConstraintTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	VehicleTest::CreateSettingsMenu(inUI, inSubMenu);

	inUI->CreateSlider(inSubMenu, "Initial Roll Angle", RadiansToDegrees(sInitialRollAngle), 0.0f, 90.0f, 1.0f, [](float inValue) { sInitialRollAngle = DegreesToRadians(inValue); });
	inUI->CreateSlider(inSubMenu, "Max Roll Angle", RadiansToDegrees(sMaxRollAngle), 0.0f, 90.0f, 1.0f, [](float inValue) { sMaxRollAngle = DegreesToRadians(inValue); });
	inUI->CreateSlider(inSubMenu, "Max Steering Angle", RadiansToDegrees(sMaxSteeringAngle), 0.0f, 90.0f, 1.0f, [](float inValue) { sMaxSteeringAngle = DegreesToRadians(inValue); });
	inUI->CreateComboBox(inSubMenu, "Collision Mode", { "Ray", "Cast Sphere", "Cast Cylinder" }, sCollisionMode, [](int inItem) { sCollisionMode = inItem; });
	inUI->CreateCheckBox(inSubMenu, "4 Wheel Drive", sFourWheelDrive, [](UICheckBox::EState inState) { sFourWheelDrive = inState == UICheckBox::STATE_CHECKED; });
	inUI->CreateCheckBox(inSubMenu, "Anti Rollbars", sAntiRollbar, [](UICheckBox::EState inState) { sAntiRollbar = inState == UICheckBox::STATE_CHECKED; });
	inUI->CreateCheckBox(inSubMenu, "Limited Slip Differentials", sLimitedSlipDifferentials, [](UICheckBox::EState inState) { sLimitedSlipDifferentials = inState == UICheckBox::STATE_CHECKED; });
	inUI->CreateCheckBox(inSubMenu, "Override Gravity", sOverrideGravity, [](UICheckBox::EState inState) { sOverrideGravity = inState == UICheckBox::STATE_CHECKED; });
	inUI->CreateSlider(inSubMenu, "Max Engine Torque", float(sMaxEngineTorque), 100.0f, 2000.0f, 10.0f, [](float inValue) { sMaxEngineTorque = inValue; });
	inUI->CreateSlider(inSubMenu, "Clutch Strength", float(sClutchStrength), 1.0f, 40.0f, 1.0f, [](float inValue) { sClutchStrength = inValue; });
	inUI->CreateSlider(inSubMenu, "Front Caster Angle", RadiansToDegrees(sFrontCasterAngle), -89.0f, 89.0f, 1.0f, [](float inValue) { sFrontCasterAngle = DegreesToRadians(inValue); });
	inUI->CreateSlider(inSubMenu, "Front King Pin Angle", RadiansToDegrees(sFrontKingPinAngle), -89.0f, 89.0f, 1.0f, [](float inValue) { sFrontKingPinAngle = DegreesToRadians(inValue); });
	inUI->CreateSlider(inSubMenu, "Front Camber", RadiansToDegrees(sFrontCamber), -89.0f, 89.0f, 1.0f, [](float inValue) { sFrontCamber = DegreesToRadians(inValue); });
	inUI->CreateSlider(inSubMenu, "Front Toe", RadiansToDegrees(sFrontToe), -89.0f, 89.0f, 1.0f, [](float inValue) { sFrontToe = DegreesToRadians(inValue); });
	inUI->CreateSlider(inSubMenu, "Front Suspension Forward Angle", RadiansToDegrees(sFrontSuspensionForwardAngle), -89.0f, 89.0f, 1.0f, [](float inValue) { sFrontSuspensionForwardAngle = DegreesToRadians(inValue); });
	inUI->CreateSlider(inSubMenu, "Front Suspension Sideways Angle", RadiansToDegrees(sFrontSuspensionSidewaysAngle), -89.0f, 89.0f, 1.0f, [](float inValue) { sFrontSuspensionSidewaysAngle = DegreesToRadians(inValue); });
	inUI->CreateSlider(inSubMenu, "Front Suspension Min Length", sFrontSuspensionMinLength, 0.0f, 3.0f, 0.01f, [](float inValue) { sFrontSuspensionMinLength = inValue; });
	inUI->CreateSlider(inSubMenu, "Front Suspension Max Length", sFrontSuspensionMaxLength, 0.0f, 3.0f, 0.01f, [](float inValue) { sFrontSuspensionMaxLength = inValue; });
	inUI->CreateSlider(inSubMenu, "Front Suspension Frequency", sFrontSuspensionFrequency, 0.1f, 5.0f, 0.01f, [](float inValue) { sFrontSuspensionFrequency = inValue; });
	inUI->CreateSlider(inSubMenu, "Front Suspension Damping", sFrontSuspensionDamping, 0.0f, 2.0f, 0.01f, [](float inValue) { sFrontSuspensionDamping = inValue; });
	inUI->CreateSlider(inSubMenu, "Rear Caster Angle", RadiansToDegrees(sRearCasterAngle), -89.0f, 89.0f, 1.0f, [](float inValue) { sRearCasterAngle = DegreesToRadians(inValue); });
	inUI->CreateSlider(inSubMenu, "Rear King Pin Angle", RadiansToDegrees(sRearKingPinAngle), -89.0f, 89.0f, 1.0f, [](float inValue) { sRearKingPinAngle = DegreesToRadians(inValue); });
	inUI->CreateSlider(inSubMenu, "Rear Camber", RadiansToDegrees(sRearCamber), -89.0f, 89.0f, 1.0f, [](float inValue) { sRearCamber = DegreesToRadians(inValue); });
	inUI->CreateSlider(inSubMenu, "Rear Toe", RadiansToDegrees(sRearToe), -89.0f, 89.0f, 1.0f, [](float inValue) { sRearToe = DegreesToRadians(inValue); });
	inUI->CreateSlider(inSubMenu, "Rear Suspension Forward Angle", RadiansToDegrees(sRearSuspensionForwardAngle), -89.0f, 89.0f, 1.0f, [](float inValue) { sRearSuspensionForwardAngle = DegreesToRadians(inValue); });
	inUI->CreateSlider(inSubMenu, "Rear Suspension Sideways Angle", RadiansToDegrees(sRearSuspensionSidewaysAngle), -89.0f, 89.0f, 1.0f, [](float inValue) { sRearSuspensionSidewaysAngle = DegreesToRadians(inValue); });
	inUI->CreateSlider(inSubMenu, "Rear Suspension Min Length", sRearSuspensionMinLength, 0.0f, 3.0f, 0.01f, [](float inValue) { sRearSuspensionMinLength = inValue; });
	inUI->CreateSlider(inSubMenu, "Rear Suspension Max Length", sRearSuspensionMaxLength, 0.0f, 3.0f, 0.01f, [](float inValue) { sRearSuspensionMaxLength = inValue; });
	inUI->CreateSlider(inSubMenu, "Rear Suspension Frequency", sRearSuspensionFrequency, 0.1f, 5.0f, 0.01f, [](float inValue) { sRearSuspensionFrequency = inValue; });
	inUI->CreateSlider(inSubMenu, "Rear Suspension Damping", sRearSuspensionDamping, 0.0f, 2.0f, 0.01f, [](float inValue) { sRearSuspensionDamping = inValue; });
	inUI->CreateTextButton(inSubMenu, "Accept", [this]() { RestartTest(); });
}
*/}
