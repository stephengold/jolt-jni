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
import testjoltjni.app.samples.*;
import testjoltjni.app.testframework.CameraState;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt Physics powered swing-twist constraint test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/PoweredSwingTwistConstraintTest.cpp
 */
public class PoweredSwingTwistConstraintTest extends Test{
EMotorState sSwingMotorState=EMotorState.Velocity;
EMotorState sTwistMotorState=EMotorState.Velocity;
float mInertiaBody2AsSeenFromConstraint;
float sDamping=2;
float sFrequency=10;
float sMaxAngularAcceleration=degreesToRadians(36000);
float sMaxFrictionAngularAcceleration;
float sNormalHalfConeAngle=degreesToRadians(60);
float sPlaneHalfConeAngle=degreesToRadians(45);
float sTwistMinAngle=degreesToRadians(-180);
float sTwistMaxAngle=degreesToRadians(180);
SwingTwistConstraint mConstraint;
Vec3 sTargetOrientationCS=Vec3.sZero();
Vec3 sTargetVelocityCS=new Vec3(degreesToRadians(90),0,0);

Vec3[]sBodyRotation = { Vec3.sZero(), Vec3.sZero() };

public void Initialize()
{
	// Floor
	CreateFloor();

	// Create group filter
	GroupFilterTableRef group_filter = new GroupFilterTable().toRef();

	float half_box_height = 1.5f;
	ShapeRefC box = new BoxShape(new Vec3(0.25f, half_box_height, 0.5f)).toRefC();
	Quat body1_rotation = Quat.sEulerAngles(sBodyRotation[0]);
	Quat body2_rotation = star(Quat.sEulerAngles(sBodyRotation[1]) , body1_rotation);

	RVec3 body1_position=new RVec3(0, 20, 0);
	Body body1 = mBodyInterface.createBody(new BodyCreationSettings(box, body1_position, body1_rotation, EMotionType.Static, Layers.NON_MOVING));
	body1.setCollisionGroup(new CollisionGroup(group_filter, 0, 0));
	mBodyInterface.addBody(body1.getId(), EActivation.DontActivate);

	RVec3 constraint_position = plus(body1_position , star(body1_rotation ,new Vec3(0, -half_box_height, 0)));

	RVec3 body2_position = plus(constraint_position , star(body2_rotation ,new Vec3(0, -half_box_height, 0)));
	Body body2 = mBodyInterface.createBody(new BodyCreationSettings(box, body2_position, body2_rotation, EMotionType.Dynamic, Layers.MOVING));
	body2.setCollisionGroup(new CollisionGroup(group_filter, 0, 0));
	body2.getMotionProperties().setLinearDamping(0.0f);
	body2.getMotionProperties().setAngularDamping(0.0f);
	body2.setAllowSleeping(false);
	mBodyInterface.addBody(body2.getId(), EActivation.Activate);

	SwingTwistConstraintSettings settings = new SwingTwistConstraintSettings();
	settings.setNormalHalfConeAngle ( sNormalHalfConeAngle);
	settings.setPlaneHalfConeAngle ( sPlaneHalfConeAngle);
	settings.setTwistMinAngle ( sTwistMinAngle);
	settings.setTwistMaxAngle ( sTwistMaxAngle);

	settings.setPosition1 ( settings.setPosition2 ( constraint_position));
	settings.setTwistAxis1 ( settings.setTwistAxis2 ( minus(body1_rotation.rotateAxisY())));
	settings.setPlaneAxis1 ( settings.setPlaneAxis2 ( body1_rotation.rotateAxisX()));

	mConstraint = (SwingTwistConstraint) (settings.create(body1, body2));
	mPhysicsSystem.addConstraint(mConstraint);

	// Calculate inertia along the axis of the box, so that the acceleration of the motor / friction are correct for twist
	Mat44 body2_inertia = body2.getMotionProperties().getLocalSpaceInverseInertia().inversed3x3();
	mInertiaBody2AsSeenFromConstraint = star(body2_inertia , Vec3.sAxisY()).length();
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	// Torque = Inertia * Angular Acceleration (alpha)
	mConstraint.setMaxFrictionTorque(mInertiaBody2AsSeenFromConstraint * sMaxFrictionAngularAcceleration);

	mConstraint.setNormalHalfConeAngle(sNormalHalfConeAngle);
	mConstraint.setPlaneHalfConeAngle(sPlaneHalfConeAngle);
	mConstraint.setTwistMinAngle(sTwistMinAngle);
	mConstraint.setTwistMaxAngle(sTwistMaxAngle);

	mConstraint.setSwingMotorState(sSwingMotorState);
	mConstraint.setTwistMotorState(sTwistMotorState);
	mConstraint.setTargetAngularVelocityCs(sTargetVelocityCS);
	mConstraint.setTargetOrientationCs(Quat.sEulerAngles(sTargetOrientationCS));

	MotorSettings swing = mConstraint.getSwingMotorSettings();
	swing.setTorqueLimit(mInertiaBody2AsSeenFromConstraint * sMaxAngularAcceleration);
	swing.getSpringSettings().setFrequency ( sFrequency);
	swing.getSpringSettings().setDamping ( sDamping);

	MotorSettings twist = mConstraint.getTwistMotorSettings();
	twist.setTorqueLimit(mInertiaBody2AsSeenFromConstraint * sMaxAngularAcceleration);
	twist.getSpringSettings().setFrequency ( sFrequency);
	twist.getSpringSettings().setDamping ( sDamping);
}

void getInitialCamera(CameraState ioState)
{
	ioState.mPos =new RVec3(4, 25, 4);
	ioState.mForward =new Vec3(-1, -1, -1).normalized();
}
/*TODO

void PoweredSwingTwistConstraintTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	Array<String> axis_label = { "X", "Y", "Z" };
	Array<String> constraint_label = { "Twist", "Plane", "Normal" };

	inUI->CreateTextButton(inSubMenu, "Configuration Settings", [this, inUI, axis_label]() {
		UIElement *configuration_settings = inUI->CreateMenu();

		for (int body = 0; body < 2; ++body)
			for (int axis = 0; axis < 3; ++axis)
				inUI->CreateSlider(configuration_settings, "Body " + ConvertToString(body + 1) + " Rotation " + axis_label[axis] + " (deg)", RadiansToDegrees(sBodyRotation[body][axis]), -180.0f, 180.0f, 1.0f, [=](float inValue) { sBodyRotation[body].SetComponent(axis, DegreesToRadians(inValue)); });

		inUI->CreateTextButton(configuration_settings, "Accept Changes", [this]() { RestartTest(); });

		inUI->ShowMenu(configuration_settings);
	});

	inUI->CreateTextButton(inSubMenu, "Runtime Settings", [=]() {
		UIElement *runtime_settings = inUI->CreateMenu();

		inUI->CreateSlider(runtime_settings, "Min Twist Angle (deg)", RadiansToDegrees(sTwistMinAngle), -180.0f, 180.0f, 1.0f, [=](float inValue) { sTwistMinAngle = DegreesToRadians(inValue); });
		inUI->CreateSlider(runtime_settings, "Max Twist Angle (deg)", RadiansToDegrees(sTwistMaxAngle), -180.0f, 180.0f, 1.0f, [=](float inValue) { sTwistMaxAngle = DegreesToRadians(inValue); });
		inUI->CreateSlider(runtime_settings, "Normal Half Cone Angle (deg)", RadiansToDegrees(sNormalHalfConeAngle), 0.0f, 180.0f, 1.0f, [=](float inValue) { sNormalHalfConeAngle = DegreesToRadians(inValue); });
		inUI->CreateSlider(runtime_settings, "Plane Half Cone Angle (deg)", RadiansToDegrees(sPlaneHalfConeAngle), 0.0f, 180.0f, 1.0f, [=](float inValue) { sPlaneHalfConeAngle = DegreesToRadians(inValue); });

		inUI->CreateComboBox(runtime_settings, "Swing Motor", { "Off", "Velocity", "Position" }, (int)sSwingMotorState, [=](int inItem) { sSwingMotorState = (EMotorState)inItem; });
		inUI->CreateComboBox(runtime_settings, "Twist Motor", { "Off", "Velocity", "Position" }, (int)sTwistMotorState, [=](int inItem) { sTwistMotorState = (EMotorState)inItem; });

		for (int i = 0; i < 3; ++i)
			inUI->CreateSlider(runtime_settings, "Target Angular Velocity " + constraint_label[i] + " (deg/s)", RadiansToDegrees(sTargetVelocityCS[i]), -90.0f, 90.0f, 1.0f, [i](float inValue) { sTargetVelocityCS.SetComponent(i, DegreesToRadians(inValue)); });

		for (int i = 0; i < 3; ++i)
			inUI->CreateSlider(runtime_settings, "Target Angle " + constraint_label[i] + " (deg)", RadiansToDegrees(sTargetOrientationCS[i]), -180, 180.0f, 1.0f, [i](float inValue) {
				sTargetOrientationCS.SetComponent(i, DegreesToRadians(Clamp(inValue, -179.99f, 179.99f))); // +/- 180 degrees is ambiguous, so add a little bit of a margin
			});

		inUI->CreateSlider(runtime_settings, "Max Angular Acceleration (deg/s^2)", RadiansToDegrees(sMaxAngularAcceleration), 0.0f, 36000.0f, 100.0f, [=](float inValue) { sMaxAngularAcceleration = DegreesToRadians(inValue); });
		inUI->CreateSlider(runtime_settings, "Frequency (Hz)", sFrequency, 0.0f, 20.0f, 0.1f, [=](float inValue) { sFrequency = inValue; });
		inUI->CreateSlider(runtime_settings, "Damping", sDamping, 0.0f, 2.0f, 0.01f, [=](float inValue) { sDamping = inValue; });
		inUI->CreateSlider(runtime_settings, "Max Friction Angular Acceleration (deg/s^2)", RadiansToDegrees(sMaxFrictionAngularAcceleration), 0.0f, 900.0f, 1.0f, [=](float inValue) { sMaxFrictionAngularAcceleration = DegreesToRadians(inValue); });

		inUI->ShowMenu(runtime_settings);
	});
}
*/}
