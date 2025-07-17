/*
Copyright (c) 2024-2025 Stephen Gold

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
import java.util.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt-Physics path-constraint test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/PathConstraintTest.cpp
 */
public class PathConstraintTest extends Test{
EPathRotationConstraintType sOrientationType = EPathRotationConstraintType.Free;
PathConstraintPathRef[]mPaths=new PathConstraintPathRef[2];
TwoBodyConstraintRef[]mConstraints=new TwoBodyConstraintRef[2];
static float sMaxMotorAcceleration=20;
static float sMaxFrictionAcceleration;
static float sFrequency=2;
static float sDamping=1;

void Initialize()
{
	// Floor
	CreateFloor();

	{
		// Create spiral path
		PathConstraintPathHermite path = new PathConstraintPathHermite();
		Vec3 normal=new Vec3(0, 1, 0);
		List<Vec3> positions=new ArrayList<>();
		for (float a = -0.1f * JPH_PI; a < 4.0f * JPH_PI; a += 0.1f * JPH_PI)
			positions.add(new Vec3(5.0f * cos(a), -a, 5.0f * sin(a)));
		for (int i = 1; i < (int)(positions.size() - 1); ++i)
		{
			Vec3 tangent = star(0.5f , minus(positions.get(i + 1) , positions.get(i - 1)));
			path.addPoint(positions.get(i), tangent, normal);
		}
		mPaths[0] = path.toRef();

		// Dynamic base plate to which the path attaches
		Body body1 = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(new Vec3(5, 0.5f, 5)),new RVec3(-10, 1, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		mBodyInterface.addBody(body1.getId(), EActivation.Activate);

		// Dynamic body attached to the path
		Body body2 = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(new Vec3(0.5f, 1.0f, 2.0f)),new RVec3(-5, 15, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		body2.setAllowSleeping(false);
		mBodyInterface.addBody(body2.getId(), EActivation.Activate);

		// Create path constraint
		PathConstraintSettings settings=new PathConstraintSettings();
		settings.setPath ( path);
		settings.setPathPosition (new Vec3(0, 15, 0));
		settings.setRotationConstraintType ( sOrientationType);
		mConstraints[0] =  (settings.create(body1, body2).toRef());
		mPhysicsSystem.addConstraint(mConstraints[0]);
	}

	{
		// Create circular path
		PathConstraintPathHermite path = new PathConstraintPathHermite();
		path.setIsLooping(true);
		Vec3 normal=new Vec3(0, 1, 0);
		List<Vec3> positions=new ArrayList<>(12);
		for (int i = -1; i < 11; ++i)
		{
			float a = 2.0f * JPH_PI * i / 10.0f;
			positions.add(new Vec3(5.0f * cos(a), 0.0f, 5.0f * sin(a)));
		}
		for (int i = 1; i < (int)(positions.size() - 1); ++i)
		{
			Vec3 tangent = star(0.5f , minus(positions.get(i + 1) , positions.get(i - 1)));
			path.addPoint(positions.get(i), tangent, normal);
		}
		mPaths[1] = path.toRef();

		// Dynamic base plate to which the path attaches
		Body body1 = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(new Vec3(5, 0.5f, 5)),new RVec3(10, 1, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		mBodyInterface.addBody(body1.getId(), EActivation.Activate);

		// Dynamic body attached to the path
		Body body2 = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(new Vec3(0.5f, 1.0f, 2.0f)),new RVec3(15, 5, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		body2.setAllowSleeping(false);
		mBodyInterface.addBody(body2.getId(), EActivation.Activate);

		// Create path constraint
		PathConstraintSettings settings=new PathConstraintSettings();
		settings.setPath ( path);
		settings.setPathPosition (new Vec3(0, 5, 0));
		settings.setPathRotation ( Quat.sRotation(Vec3.sAxisX(), -0.1f * JPH_PI));
		settings.setRotationConstraintType ( sOrientationType);
		mConstraints[1] =  (settings.create(body1, body2)).toRef();
		mPhysicsSystem.addConstraint(mConstraints[1]);
	}

}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	for (TwoBodyConstraintRef cr : mConstraints)
	{
		PathConstraint c=(PathConstraint)(cr.getPtr());
		MotorSettings motor_settings = c.getPositionMotorSettings();
		motor_settings.setForceLimit(sMaxMotorAcceleration / c.getBody2().getMotionProperties().getInverseMass()); // F = m * a
		motor_settings.getSpringSettings().setFrequency ( sFrequency);
		motor_settings.getSpringSettings().setDamping ( sDamping);
		c.setMaxFrictionForce(sMaxFrictionAcceleration / c.getBody2().getMotionProperties().getInverseMass());
	}
}
/*TODO

void PathConstraintTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	static Array<String> constraint_types = { "Free", "Tangent", "Normal", "Binormal", "Path", "Full" };

	inUI->CreateTextButton(inSubMenu, "Configuration Settings", [this, inUI]() {
		UIElement *configuration_settings = inUI->CreateMenu();
		inUI->CreateComboBox(configuration_settings, "Rotation Constraint", constraint_types, (int)sOrientationType, [=](int inItem) { sOrientationType = (EPathRotationConstraintType)inItem; });
		inUI->CreateTextButton(configuration_settings, "Accept Changes", [this]() { RestartTest(); });
		inUI->ShowMenu(configuration_settings);
	});

	inUI->CreateTextButton(inSubMenu, "Runtime Settings", [this, inUI]() {
		UIElement *runtime_settings = inUI->CreateMenu();
		inUI->CreateComboBox(runtime_settings, "Motor", { "Off", "Velocity", "Position" }, (int)mConstraints[0]->GetPositionMotorState(), [this](int inItem) { for (PathConstraint *c : mConstraints) c->SetPositionMotorState((EMotorState)inItem); });
		inUI->CreateSlider(runtime_settings, "Target Velocity (m/s)", mConstraints[0]->GetTargetVelocity(), -10.0f, 10.0f, 0.1f, [this](float inValue) { for (PathConstraint *c : mConstraints) c->SetTargetVelocity(inValue); });
		inUI->CreateSlider(runtime_settings, "Target Path Fraction", mConstraints[0]->GetTargetPathFraction(), 0, mPaths[0]->GetPathMaxFraction(), 0.1f, [this](float inValue) { for (PathConstraint *c : mConstraints) c->SetTargetPathFraction(inValue); });
		inUI->CreateSlider(runtime_settings, "Max Acceleration (m/s^2)", sMaxMotorAcceleration, 0.0f, 100.0f, 1.0f, [](float inValue) { sMaxMotorAcceleration = inValue; });
		inUI->CreateSlider(runtime_settings, "Frequency (Hz)", sFrequency, 0.0f, 20.0f, 0.1f, [](float inValue) { sFrequency = inValue; });
		inUI->CreateSlider(runtime_settings, "Damping", sDamping, 0.0f, 2.0f, 0.01f, [](float inValue) { sDamping = inValue; });
		inUI->CreateSlider(runtime_settings, "Max Friction Acceleration (m/s^2)", sMaxFrictionAcceleration, 0.0f, 10.0f, 0.1f, [](float inValue) { sMaxFrictionAcceleration = inValue; });
		inUI->ShowMenu(runtime_settings);
	});

	inUI->ShowMenu(inSubMenu);
}
*/
}
