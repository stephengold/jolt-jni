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
import testjoltjni.app.testframework.CameraState;
/**
 * A line-for-line Java translation of the Jolt Physics 6DOF constraint test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/SixDOFConstraintTest.cpp
 */
public class SixDOFConstraintTest extends Test{
TwoBodyConstraintRef mConstraint;

float []sLimitMin = { 0, 0, 0, 0, 0, 0 };
float []sLimitMax = { 0, 0, 0, 0, 0, 0 };
boolean []sEnableLimits = { true, true, true, true, true, true };


static TwoBodyConstraintSettingsRef sSettings; static {
		SixDofConstraintSettings settings=new SixDofConstraintSettings();
		settings.setEmbedded();
		settings.setAxisX1 ( settings.setAxisX2 ( Op.minus(Vec3.sAxisY())));
		settings.setAxisY1 ( settings.setAxisY2 ( Vec3.sAxisZ()));
		for (int i = 0; i < 6; ++i)
			settings.setMotorSettings(i, new MotorSettings(10.0f, 2.0f));
		sSettings=settings.toRef();
	};

public void Initialize()
{
	// Floor
	CreateFloor();

	// Convert limits to settings class
	for (int i = 0; i < EAxis.Num.ordinal(); ++i)
		if (sEnableLimits[i])
			((SixDofConstraintSettings)sSettings.getPtr()).setLimitedAxis(i, sLimitMin[i], sLimitMax[i]);
		else
			((SixDofConstraintSettings)sSettings.getPtr()).makeFreeAxis(i);

	// Create group filter
	GroupFilterTableRef group_filter = new GroupFilterTable().toRef();

	// Create box
	float half_box_height = 1.5f;
	RVec3 position=new RVec3(0, 25, 0);
	ShapeRefC box = new BoxShape(new Vec3(0.5f, half_box_height, 0.25f)).toRefC();

	// Create static body
	Body body1 = mBodyInterface.createBody(new BodyCreationSettings(box, position, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
	body1.setCollisionGroup(new CollisionGroup(group_filter, 0, 0));
	mBodyInterface.addBody(body1.getId(), EActivation.DontActivate);

	// Create dynamic body
	Body body2 = mBodyInterface.createBody(new BodyCreationSettings(box, Op.minus(position ,new Vec3(0, 2.0f * half_box_height, 0)), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
	body2.setCollisionGroup(new CollisionGroup(group_filter, 0, 0));
	body2.setAllowSleeping(false);
	mBodyInterface.addBody(body2.getId(), EActivation.Activate);

	// Set constraint position
	((SixDofConstraintSettings)sSettings.getPtr()).setPosition1 ( ((SixDofConstraintSettings)sSettings.getPtr()).setPosition2 ( Op.minus(position ,new Vec3(0, half_box_height, 0))));

	// Set force limits
	final float max_acceleration = 1.0f;
	for (int i = 0; i < 3; ++i)
		((SixDofConstraintSettings)sSettings.getPtr()).getMotorSettings(i).setForceLimit(max_acceleration / body2.getMotionProperties().getInverseMass());

	// Create constraint
	mConstraint = sSettings.create(body1, body2).toRef();
	mPhysicsSystem.addConstraint(mConstraint);
}

void GetInitialCamera(CameraState ioState)
{
	ioState.mPos =new RVec3(4, 30, 4);
	ioState.mForward =new Vec3(-1, -1, -1).normalized();
}
/*TODO

void SixDOFConstraintTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	Array<String> labels = { "Translation X", "Translation Y", "Translation Z", "Rotation X", "Rotation Y", "Rotation Z" };
	Array<String> motor_states = { "Off", "Velocity", "Position" };
	Array<String> swing_types = { "Cone", "Pyramid" };

	inUI->CreateTextButton(inSubMenu, "Configuration Settings (Limits)", [this, inUI, labels, swing_types]() {
		UIElement *configuration_settings = inUI->CreateMenu();

		inUI->CreateComboBox(configuration_settings, "Swing Type", swing_types, (int)sSettings->mSwingType, [](int inItem) { sSettings->mSwingType = (ESwingType)inItem; });

		for (int i = 0; i < 3; ++i)
		{
			inUI->CreateCheckBox(configuration_settings, "Enable Limits " + labels[i], sEnableLimits[i], [=](UICheckBox::EState inState) { sEnableLimits[i] = inState == UICheckBox::STATE_CHECKED; });
			inUI->CreateSlider(configuration_settings, "Limit Min", sLimitMin[i], -5.0f, 5.0f, 0.1f, [=](float inValue) { sLimitMin[i] = inValue; });
			inUI->CreateSlider(configuration_settings, "Limit Max", sLimitMax[i], -5.0f, 5.0f, 0.1f, [=](float inValue) { sLimitMax[i] = inValue; });
			inUI->CreateSlider(configuration_settings, "Limit Frequency (Hz)", sSettings->mLimitsSpringSettings[i].mFrequency, 0.0f, 20.0f, 0.1f, [=](float inValue) { sSettings->mLimitsSpringSettings[i].mFrequency = inValue; });
			inUI->CreateSlider(configuration_settings, "Limit Damping", sSettings->mLimitsSpringSettings[i].mDamping, 0.0f, 2.0f, 0.01f, [=](float inValue) { sSettings->mLimitsSpringSettings[i].mDamping = inValue; });
		}

		for (int i = 3; i < 6; ++i)
		{
			inUI->CreateCheckBox(configuration_settings, "Enable Limits " + labels[i], sEnableLimits[i], [=](UICheckBox::EState inState) { sEnableLimits[i] = inState == UICheckBox::STATE_CHECKED; });
			inUI->CreateSlider(configuration_settings, "Limit Min", RadiansToDegrees(sLimitMin[i]), -180.0f, 180.0f, 1.0f, [=](float inValue) { sLimitMin[i] = DegreesToRadians(inValue); });
			inUI->CreateSlider(configuration_settings, "Limit Max", RadiansToDegrees(sLimitMax[i]), -180.0f, 180.0f, 1.0f, [=](float inValue) { sLimitMax[i] = DegreesToRadians(inValue); });
		}

		inUI->CreateTextButton(configuration_settings, "Accept Changes", [this]() { RestartTest(); });

		inUI->ShowMenu(configuration_settings);
	});

	inUI->CreateTextButton(inSubMenu, "Configuration Settings (Other)", [this, inUI, labels]() {
		UIElement *configuration_settings = inUI->CreateMenu();

		for (int i = 0; i < 6; ++i)
			inUI->CreateSlider(configuration_settings, "Max Friction " + labels[i], sSettings->mMaxFriction[i], 0.0f, 500.0f, 1.0f, [=](float inValue) { sSettings->mMaxFriction[i] = inValue; });

		for (int i = 0; i < 6; ++i)
			inUI->CreateSlider(configuration_settings, "Motor Frequency " + labels[i] + " (Hz)", sSettings->mMotorSettings[i].mSpringSettings.mFrequency, 0.0f, 20.0f, 0.1f, [i](float inValue) { sSettings->mMotorSettings[i].mSpringSettings.mFrequency = inValue; });

		for (int i = 0; i < 6; ++i)
			inUI->CreateSlider(configuration_settings, "Motor Damping " + labels[i], sSettings->mMotorSettings[i].mSpringSettings.mDamping, 0.0f, 2.0f, 0.01f, [i](float inValue) { sSettings->mMotorSettings[i].mSpringSettings.mDamping = inValue; });

		inUI->CreateTextButton(configuration_settings, "Accept Changes", [this]() { RestartTest(); });

		inUI->ShowMenu(configuration_settings);
	});

	inUI->CreateTextButton(inSubMenu, "Runtime Settings", [this, inUI, labels, motor_states]() {
		UIElement *runtime_settings = inUI->CreateMenu();

		for (int i = 0; i < 3; ++i)
		{
			EAxis axis = EAxis(EAxis::TranslationX + i);

			UIComboBox *combo = inUI->CreateComboBox(runtime_settings, "Motor " + labels[i], motor_states, (int)mConstraint->GetMotorState(axis), [this, axis](int inItem) { mConstraint->SetMotorState(axis, (EMotorState)inItem); });
			combo->SetDisabled(sSettings->IsFixedAxis(axis));

			UISlider *velocity = inUI->CreateSlider(runtime_settings, "Target Velocity", mConstraint->GetTargetVelocityCS()[i], -10.0f, 10.0f, 0.1f, [this, i](float inValue) {
				Vec3 v = mConstraint->GetTargetVelocityCS();
				v.SetComponent(i, inValue);
				mConstraint->SetTargetVelocityCS(v); });
			velocity->SetDisabled(sSettings->IsFixedAxis(axis));

			UISlider *position = inUI->CreateSlider(runtime_settings, "Target Position", mConstraint->GetTargetPositionCS()[i], -10.0f, 10.0f, 0.1f, [this, i](float inValue) {
				Vec3 v = mConstraint->GetTargetPositionCS();
				v.SetComponent(i, inValue);
				mConstraint->SetTargetPositionCS(v); });
			position->SetDisabled(sSettings->IsFixedAxis(axis));
		}

		for (int i = 0; i < 3; ++i)
		{
			EAxis axis = EAxis(EAxis::RotationX + i);

			inUI->CreateComboBox(runtime_settings, "Motor " + labels[axis], motor_states, (int)mConstraint->GetMotorState(axis), [this, axis](int inItem) { mConstraint->SetMotorState(axis, (EMotorState)inItem); });

			inUI->CreateSlider(runtime_settings, "Target Velocity", RadiansToDegrees(mConstraint->GetTargetAngularVelocityCS()[i]), -90.0f, 90.0f, 1.0f, [this, i](float inValue) {
				Vec3 v = mConstraint->GetTargetAngularVelocityCS();
				v.SetComponent(i, DegreesToRadians(inValue));
				mConstraint->SetTargetAngularVelocityCS(v); });

			inUI->CreateSlider(runtime_settings, "Target Position", RadiansToDegrees(mTargetOrientationCS[i]), -180.0f, 180.0f, 1.0f, [this, i](float inValue) {
				mTargetOrientationCS.SetComponent(i, DegreesToRadians(Clamp(inValue, -179.99f, 179.99f))); // +/- 180 degrees is ambiguous, so add a little bit of a margin
				mConstraint->SetTargetOrientationCS(Quat::sEulerAngles(mTargetOrientationCS)); });
		}

		inUI->ShowMenu(runtime_settings);
	});
}
*/}
