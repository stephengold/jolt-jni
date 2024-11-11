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
 * A line-for-line Java translation of the Jolt Physics powered hinge-constraint test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/PoweredHingeConstraintTest.cpp
 */
public class PoweredHingeConstraintTest extends Test{
float sMaxAngularAcceleration = Jolt.degreesToRadians(3600.0f);
float sMaxFrictionAngularAcceleration = 0.0f;
float sFrequency = 2.0f;
float sDamping = 1.0f;
HingeConstraint mConstraint = null;
float mInertiaBody2AsSeenFromConstraint;

public void Initialize()
{
	// Floor
	CreateFloor();

	// Create group filter
	GroupFilterTableRef group_filter = new GroupFilterTable().toRef();

	// Create box
	float box_size = 4.0f;
	ShapeRefC box = new BoxShape(Vec3.sReplicate(0.5f * box_size)).toRefC();

	RVec3 body1_position=new RVec3(0, 10, 0);
	Body body1 = mBodyInterface.createBody(new BodyCreationSettings(box, body1_position, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
	body1.setCollisionGroup(new CollisionGroup(group_filter, 0, 0));
	mBodyInterface.addBody(body1.getId(), EActivation.DontActivate);

	RVec3 body2_position = Op.add(body1_position ,new Vec3(box_size, 0, 0));
	Body body2 = mBodyInterface.createBody(new BodyCreationSettings(box, body2_position, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
	body2.setCollisionGroup(new CollisionGroup(group_filter, 0, 0));
	body2.getMotionProperties().setLinearDamping(0.0f);
	body2.getMotionProperties().setAngularDamping(0.0f);
	body2.setAllowSleeping(false);
	mBodyInterface.addBody(body2.getId(), EActivation.Activate);

	RVec3 constraint_position = Op.add(body1_position ,new Vec3(0.5f * box_size, 0, 0.5f * box_size));

	HingeConstraintSettings settings = new HingeConstraintSettings();
	settings.setPoint1 ( settings.setPoint2 ( constraint_position));
	settings.setHingeAxis1 ( settings.setHingeAxis2 ( Vec3.sAxisY()));
	settings.setNormalAxis1 ( settings.setNormalAxis2 ( Vec3.sAxisX()));
	mConstraint = (HingeConstraint)(settings.create(body1, body2));
	mConstraint.setMotorState(EMotorState.Velocity);
	mConstraint.setTargetAngularVelocity(Jolt.degreesToRadians(25));
	mPhysicsSystem.addConstraint(mConstraint);

	// Calculate inertia of body 2 as seen from the constraint
	MassProperties body2_inertia_from_constraint=new MassProperties();
	body2_inertia_from_constraint.setMass ( 1.0f / body2.getMotionProperties().getInverseMass());
	body2_inertia_from_constraint.setInertia ( body2.getMotionProperties().getLocalSpaceInverseInertia().inversed3x3());
	body2_inertia_from_constraint.translate(Op.subtract(body2_position , constraint_position).toVec3());
	mInertiaBody2AsSeenFromConstraint = Op.multiply(body2_inertia_from_constraint.getInertia() , Vec3.sAxisY()).length();
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	// Torque = Inertia * Angular Acceleration (alpha)
	MotorSettings motor_settings = mConstraint.getMotorSettings();
	motor_settings.setTorqueLimit(mInertiaBody2AsSeenFromConstraint * sMaxAngularAcceleration);
	motor_settings.getSpringSettings().setFrequency ( sFrequency);
	motor_settings.getSpringSettings().setDamping ( sDamping);
	mConstraint.setMaxFrictionTorque(mInertiaBody2AsSeenFromConstraint * sMaxFrictionAngularAcceleration);
}
/*TODO

void PoweredHingeConstraintTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI->CreateComboBox(inSubMenu, "Motor", { "Off", "Velocity", "Position" }, (int)mConstraint->GetMotorState(), [this](int inItem) { mConstraint->SetMotorState((EMotorState)inItem); });
	inUI->CreateSlider(inSubMenu, "Target Angular Velocity (deg/s)", RadiansToDegrees(mConstraint->GetTargetAngularVelocity()), -90.0f, 90.0f, 1.0f, [this](float inValue) { mConstraint->SetTargetAngularVelocity(DegreesToRadians(inValue)); });
	inUI->CreateSlider(inSubMenu, "Target Angle (deg)", RadiansToDegrees(mConstraint->GetTargetAngle()), -180.0f, 180.0f, 1.0f, [this](float inValue) { mConstraint->SetTargetAngle(DegreesToRadians(inValue)); });
	inUI->CreateSlider(inSubMenu, "Max Angular Acceleration (deg/s^2)", RadiansToDegrees(sMaxAngularAcceleration), 0.0f, 3600.0f, 10.0f, [](float inValue) { sMaxAngularAcceleration = DegreesToRadians(inValue); });
	inUI->CreateSlider(inSubMenu, "Frequency (Hz)", sFrequency, 0.0f, 20.0f, 0.1f, [](float inValue) { sFrequency = inValue; });
	inUI->CreateSlider(inSubMenu, "Damping", sDamping, 0.0f, 2.0f, 0.01f, [](float inValue) { sDamping = inValue; });
	inUI->CreateSlider(inSubMenu, "Max Friction Angular Acceleration (deg/s^2)", RadiansToDegrees(sMaxFrictionAngularAcceleration), 0.0f, 90.0f, 1.0f, [](float inValue) { sMaxFrictionAngularAcceleration = DegreesToRadians(inValue); });
}
*/}