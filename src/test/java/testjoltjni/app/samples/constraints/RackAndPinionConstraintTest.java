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
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt Physics rack-and-pinion constraint test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/RackAndPinionConstraintTest.cpp
 */
public class RackAndPinionConstraintTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	final float cGearRadius = 0.5f;
	final float cGearHalfWidth = 0.05f;
	final int cGearNumTeeth = 100;

	final float cRackLength = 10.0f;
	final float cRackHalfHeight = 0.1f;
	final float cRackHalfWidth = 0.05f;
	final int cRackNumTeeth = (int)(cRackLength * cGearNumTeeth / (2.0f * JPH_PI * cGearRadius));

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

	// Create gear
	CylinderShapeSettings gear_cylinder=new CylinderShapeSettings(cGearHalfWidth, cGearRadius);
	gear_cylinder.setEmbedded();

	StaticCompoundShapeSettings gear_settings=new StaticCompoundShapeSettings();
	gear_settings.setEmbedded();

	gear_settings.addShape(Vec3.sZero(), Quat.sIdentity(), gear_cylinder);
	for (int i = 0; i < cGearNumTeeth; ++i)
	{
		Quat rotation = Quat.sRotation(Vec3.sAxisY(), 2.0f * JPH_PI * i / cGearNumTeeth);
		gear_settings.addShape(star(rotation ,new Vec3(cGearRadius, 0, 0)), rotation, tooth_settings);
	}

	RVec3 gear_initial_p=new RVec3(0, 2.0f, 0);
	Quat gear_initial_r = Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI);
	Body gear = mBodyInterface.createBody(new BodyCreationSettings(gear_settings, gear_initial_p, gear_initial_r, EMotionType.Dynamic, Layers.MOVING));
	mBodyInterface.addBody(gear.getId(), EActivation.Activate);

	// Create rack
	BoxShapeSettings rack_box=new BoxShapeSettings(new Vec3(cRackHalfHeight, cRackHalfWidth, 0.5f * cRackLength), 0.0f);
	rack_box.setEmbedded();

	StaticCompoundShapeSettings rack_settings=new StaticCompoundShapeSettings();
	rack_settings.setEmbedded();

	rack_settings.addShape(Vec3.sZero(), Quat.sIdentity(), rack_box);
	for (int i = 0; i < cRackNumTeeth; ++i)
		rack_settings.addShape(new Vec3(cRackHalfHeight, 0, -0.5f * cRackLength + (i + 0.5f) * cRackLength / cRackNumTeeth), Quat.sIdentity(), tooth_settings);

	RVec3 slider_initial_p = minus(gear_initial_p ,new Vec3(0, cGearRadius + cRackHalfHeight + cToothHeight, 0));
	Quat slider_initial_r = star(Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI) , gear_initial_r);
	Body rack = mBodyInterface.createBody(new BodyCreationSettings(rack_settings, slider_initial_p, slider_initial_r, EMotionType.Dynamic, Layers.MOVING));
	mBodyInterface.addBody(rack.getId(), EActivation.Activate);

	// Create a hinge for the gear
	HingeConstraintSettings hinge=new HingeConstraintSettings();
	hinge.setPoint1 ( hinge.setPoint2 ( gear_initial_p));
	hinge.setHingeAxis1 ( hinge.setHingeAxis2 ( Vec3.sAxisZ()));
	hinge.setNormalAxis1 ( hinge.setNormalAxis2 ( Vec3.sAxisX()));
	Constraint hinge_constraint = hinge.create(Body.sFixedToWorld(), gear);
	mPhysicsSystem.addConstraint(hinge_constraint);

	// Create a slider for the rack
	SliderConstraintSettings slider=new SliderConstraintSettings();
	slider.setPoint1 ( slider.setPoint2 ( gear_initial_p));
	slider.setSliderAxis1 ( slider.setSliderAxis2 ( Vec3.sAxisX()));
	slider.setNormalAxis1 ( slider.setNormalAxis2 ( Vec3.sAxisZ()));
	slider.setLimitsMin ( -0.5f * cRackLength);
	slider.setLimitsMax ( 0.5f * cRackLength);
	Constraint slider_constraint = slider.create(Body.sFixedToWorld(), rack);
	mPhysicsSystem.addConstraint(slider_constraint);

	// Disable collision between rack and gear (we want the rack and pinion constraint to take care of the relative movement)
	GroupFilterTableRef group_filter = new GroupFilterTable(2).toRef();
	group_filter.disableCollision(0, 1);
	gear.setCollisionGroup(new CollisionGroup(group_filter, 0, 0));
	rack.setCollisionGroup(new CollisionGroup(group_filter, 0, 1));

	// Create rack and pinion constraint to constrain the two bodies
	RackAndPinionConstraintSettings randp=new RackAndPinionConstraintSettings();
	randp.setHingeAxis ( hinge.getHingeAxis1());
	randp.setSliderAxis ( slider.getSliderAxis2());
	randp.setRatio(cRackNumTeeth, cRackLength, cGearNumTeeth);
	RackAndPinionConstraint randp_constraint = (RackAndPinionConstraint) (randp.create(gear, rack));
	randp_constraint.setConstraints(hinge_constraint, slider_constraint);
	mPhysicsSystem.addConstraint(randp_constraint);

	// Give the gear a spin
	gear.setAngularVelocity(new Vec3(0, 0, 6.0f));
}
}
