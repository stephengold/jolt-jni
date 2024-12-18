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
/**
 * A line-for-line Java translation of the Jolt Physics powered slider-constraint test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/PoweredSliderConstraintTest.cpp
 */
public class PoweredSliderConstraintTest extends Test{
Body mBody2;
float sDamping=1;
float sFrequency=2;
float sMaxFrictionAcceleration;
float sMaxMotorAcceleration=250;
SliderConstraint mConstraint;

public void Initialize()
{
	// Floor
	CreateFloor();

	// Create group filter
	GroupFilterTableRef group_filter = new GroupFilterTable().toRef();

	// Create box
	float box_size = 4.0f;
	ShapeRefC box = new BoxShape(Vec3.sReplicate(0.5f * box_size)).toRefC();

	RVec3 position=new RVec3(0, 10, 0);
	Body body1 = mBodyInterface.createBody(new BodyCreationSettings(box, position, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
	body1.setCollisionGroup(new CollisionGroup(group_filter, 0, 0));
	mBodyInterface.addBody(body1.getId(), EActivation.DontActivate);

	Op.plusEquals(position ,new Vec3(box_size + 10.0f, 0, 0));

	mBody2 = mBodyInterface.createBody(new BodyCreationSettings(box, position, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
	mBody2.setCollisionGroup(new CollisionGroup(group_filter, 0, 0));
	mBody2.getMotionProperties().setLinearDamping(0.0f);
	mBody2.setAllowSleeping(false);
	mBodyInterface.addBody(mBody2.getId(), EActivation.Activate);

	SliderConstraintSettings settings=new SliderConstraintSettings();
	settings.setAutoDetectPoint ( true);
	settings.setSliderAxis(Vec3.sAxisX());
	settings.setLimitsMin ( -5.0f);
	settings.setLimitsMax ( 100.0f);
	mConstraint = (SliderConstraint)(settings.create(body1, mBody2));
	mConstraint.setMotorState(EMotorState.Velocity);
	mConstraint.setTargetVelocity(1);
	mPhysicsSystem.addConstraint(mConstraint);
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	MotorSettings motor_settings = mConstraint.getMotorSettings();
	motor_settings.setForceLimit(sMaxMotorAcceleration / mBody2.getMotionProperties().getInverseMass()); // F = m * a
	motor_settings.getSpringSettings().setFrequency ( sFrequency);
	motor_settings.getSpringSettings().setDamping ( sDamping);
	mConstraint.setMaxFrictionForce(sMaxFrictionAcceleration / mBody2.getMotionProperties().getInverseMass());
}
/*TODO

void PoweredSliderConstraintTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI->CreateComboBox(inSubMenu, "Motor", { "Off", "Velocity", "Position" }, (int)mConstraint->GetMotorState(), [this](int inItem) { mConstraint->SetMotorState((EMotorState)inItem); });
	inUI->CreateSlider(inSubMenu, "Target Velocity (m/s)", mConstraint->GetTargetVelocity(), -10.0f, 10.0f, 0.1f, [this](float inValue) { mConstraint->SetTargetVelocity(inValue); });
	inUI->CreateSlider(inSubMenu, "Target Position (m)", mConstraint->GetTargetPosition(), -5.0f, 20.0f, 0.1f, [this](float inValue) { mConstraint->SetTargetPosition(inValue); });
	inUI->CreateSlider(inSubMenu, "Max Acceleration (m/s^2)", sMaxMotorAcceleration, 0.0f, 250.0f, 1.0f, [](float inValue) { sMaxMotorAcceleration = inValue; });
	inUI->CreateSlider(inSubMenu, "Frequency (Hz)", sFrequency, 0.0f, 20.0f, 0.1f, [](float inValue) { sFrequency = inValue; });
	inUI->CreateSlider(inSubMenu, "Damping", sDamping, 0.0f, 2.0f, 0.01f, [](float inValue) { sDamping = inValue; });
	inUI->CreateSlider(inSubMenu, "Max Friction Acceleration (m/s^2)", sMaxFrictionAcceleration, 0.0f, 10.0f, 0.1f, [](float inValue) { sMaxFrictionAcceleration = inValue; });
}
*/}
