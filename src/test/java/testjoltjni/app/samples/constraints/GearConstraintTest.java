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
 * A line-for-line Java translation of the Jolt Physics gear-constraint test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/GearConstraintTest.cpp
 */
public class GearConstraintTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	final float cGearHalfWidth = 0.05f;

	final float cGear1Radius = 0.5f;
	final int cGear1NumTeeth = 100;

	final float cGear2Radius = 2.0f;
	final int cGear2NumTeeth = (int)(cGear1NumTeeth * cGear2Radius / cGear1Radius);

	final float cToothThicknessBottom = 0.01f;
	final float cToothThicknessTop = 0.005f;
	final float cToothHeight = 0.02f;

	// Create a tooth
	Vec3[] tooth_points = {
		new Vec3(0, cGearHalfWidth, cToothThicknessBottom),
		new Vec3(0, -cGearHalfWidth, cToothThicknessBottom),
		new Vec3(0, cGearHalfWidth, -cToothThicknessBottom),
		new Vec3(0, -cGearHalfWidth, -cToothThicknessBottom),
		new Vec3(cToothHeight, -cGearHalfWidth, cToothThicknessTop),
		new Vec3(cToothHeight, cGearHalfWidth, cToothThicknessTop),
		new Vec3(cToothHeight, -cGearHalfWidth, -cToothThicknessTop),
		new Vec3(cToothHeight, cGearHalfWidth, -cToothThicknessTop),
	};
	ConvexHullShapeSettings tooth_settings=new ConvexHullShapeSettings(tooth_points);
	tooth_settings.setEmbedded();

	// Create gear 1
	CylinderShapeSettings gear1_cylinder=new CylinderShapeSettings(cGearHalfWidth, cGear1Radius);
	gear1_cylinder.setEmbedded();

	StaticCompoundShapeSettings gear1_settings=new StaticCompoundShapeSettings();
	gear1_settings.setEmbedded();

	gear1_settings.addShape(Vec3.sZero(), Quat.sIdentity(), gear1_cylinder);
	for (int i = 0; i < cGear1NumTeeth; ++i)
	{
		Quat rotation = Quat.sRotation(Vec3.sAxisY(), 2.0f * Jolt.JPH_PI * i / cGear1NumTeeth);
		gear1_settings.addShape(Op.rotate(rotation ,new Vec3(cGear1Radius, 0, 0)), rotation, tooth_settings);
	}

	RVec3 gear1_initial_p=new RVec3(0, 3.0f, 0);
	Quat gear1_initial_r = Quat.sRotation(Vec3.sAxisX(), 0.5f * Jolt.JPH_PI);
	Body gear1 = mBodyInterface.createBody(new BodyCreationSettings(gear1_settings, gear1_initial_p, gear1_initial_r, EMotionType.Dynamic, Layers.MOVING));
	mBodyInterface.addBody(gear1.getId(), EActivation.Activate);

	// Create gear 2
	CylinderShapeSettings gear2_cylinder=new CylinderShapeSettings(cGearHalfWidth, cGear2Radius);
	gear2_cylinder.setEmbedded();

	StaticCompoundShapeSettings gear2_settings=new StaticCompoundShapeSettings();
	gear2_settings.setEmbedded();

	gear2_settings.addShape(Vec3.sZero(), Quat.sIdentity(), gear2_cylinder);
	for (int i = 0; i < cGear2NumTeeth; ++i)
	{
		Quat rotation = Quat.sRotation(Vec3.sAxisY(), 2.0f * Jolt.JPH_PI * (i + 0.5f) / cGear2NumTeeth);
		gear2_settings.addShape(Op.rotate(rotation ,new Vec3(cGear2Radius, 0, 0)), rotation, tooth_settings);
	}

	RVec3 gear2_initial_p = Op.add(gear1_initial_p ,new Vec3(cGear1Radius + cGear2Radius + cToothHeight, 0, 0));
	Quat gear2_initial_r = gear1_initial_r;
	Body gear2 = mBodyInterface.createBody(new BodyCreationSettings(gear2_settings, gear2_initial_p, gear2_initial_r, EMotionType.Dynamic, Layers.MOVING));
	mBodyInterface.addBody(gear2.getId(), EActivation.Activate);

	// Create a hinge for gear 1
	HingeConstraintSettings hinge1=new HingeConstraintSettings();
	hinge1.setPoint1 ( hinge1.setPoint2 ( gear1_initial_p));
	hinge1.setHingeAxis1 ( hinge1.setHingeAxis2 ( Vec3.sAxisZ()));
	hinge1.setNormalAxis1 ( hinge1.setNormalAxis2 ( Vec3.sAxisX()));
	Constraint hinge1_constraint = hinge1.create(Body.sFixedToWorld(), gear1);
	mPhysicsSystem.addConstraint(hinge1_constraint);

	// Create a hinge for gear 1
	HingeConstraintSettings hinge2=new HingeConstraintSettings();
	hinge2.setPoint1 ( hinge2.setPoint2 ( gear2_initial_p));
	hinge2.setHingeAxis1 ( hinge2.setHingeAxis2 ( Vec3.sAxisZ()));
	hinge2.setNormalAxis1 ( hinge2.setNormalAxis2 ( Vec3.sAxisX()));
	Constraint hinge2_constraint = hinge2.create(Body.sFixedToWorld(), gear2);
	mPhysicsSystem.addConstraint(hinge2_constraint);

	// Disable collision between gears
	GroupFilterTableRef group_filter = new GroupFilterTable(2).toRef();
	group_filter.disableCollision(0, 1);
	gear1.setCollisionGroup(new CollisionGroup(group_filter.getPtr(), 0, 0));
	gear2.setCollisionGroup(new CollisionGroup(group_filter.getPtr(), 0, 1));

	// Create gear constraint to constrain the two bodies
	GearConstraintSettings gear=new GearConstraintSettings();
	gear.setHingeAxis1 ( hinge1.getHingeAxis1());
	gear.setHingeAxis2 ( hinge2.getHingeAxis1());
	gear.setRatio(cGear1NumTeeth, cGear2NumTeeth);
	GearConstraint gear_constraint = (GearConstraint)(gear.create(gear1, gear2));
	gear_constraint.setConstraints(hinge1_constraint, hinge2_constraint);
	mPhysicsSystem.addConstraint(gear_constraint);

	// Give the gear a spin
	gear2.setAngularVelocity(new Vec3(0, 0, 3.0f));
}
}
