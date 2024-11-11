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
import java.util.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
/**
 * A line-for-line Java translation of the Jolt Physics swing-twist constraint test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/SwingTwistConstraintTest.cpp
 */
public class SwingTwistConstraintTest extends Test{
float sNormalHalfConeAngle=Jolt.degreesToRadians(60);
float sPlaneHalfConeAngle=Jolt.degreesToRadians(20);
float sTwistMinAngle=Jolt.degreesToRadians(-10);
float sTwistMaxAngle=Jolt.degreesToRadians(20);
List<TwoBodyConstraintRef>mConstraints=new ArrayList<>();

public void Initialize()
{
	// Floor
	CreateFloor();

	float half_cylinder_height = 1.5f;

	final int cChainLength = 10;

	// Build a collision group filter that disables collision between adjacent bodies
	GroupFilterTableRef group_filter = new GroupFilterTable(cChainLength).toRef();
	for (int i = 0; i < cChainLength - 1; ++i)
		group_filter.disableCollision(i, i + 1);

	Body prev = null;
	Quat rotation = Quat.sRotation(Vec3.sAxisZ(), 0.5f * Jolt.JPH_PI);
	RVec3 position=new RVec3(0, 25, 0);
	for (int i = 0; i < cChainLength; ++i)
	{
		Op.plusEquals(position ,new Vec3(2.0f * half_cylinder_height, 0, 0));

		Body segment = mBodyInterface.createBody(new BodyCreationSettings(new CapsuleShape(half_cylinder_height, 0.5f), position, Op.multiply(Quat.sRotation(Vec3.sAxisX(), 0.25f * Jolt.JPH_PI * i) , rotation), i == 0? EMotionType.Static : EMotionType.Dynamic, i == 0? Layers.NON_MOVING : Layers.MOVING));
		segment.setCollisionGroup(new CollisionGroup(group_filter, 0, (i)));
		mBodyInterface.addBody(segment.getId(), EActivation.Activate);
		if (i != 0)
			segment.setAllowSleeping(false);

		if (prev != nullptr)
		{
			SwingTwistConstraintSettings settings = new SwingTwistConstraintSettings();
			settings.setPosition1 ( settings.setPosition2 ( Op.add(position ,new Vec3(-half_cylinder_height, 0, 0))));
			settings.setTwistAxis1 ( settings.setTwistAxis2 ( Vec3.sAxisX()));
			settings.setPlaneAxis1 ( settings.setPlaneAxis2 ( Vec3.sAxisY()));
			settings.setNormalHalfConeAngle ( sNormalHalfConeAngle);
			settings.setPlaneHalfConeAngle ( sPlaneHalfConeAngle);
			settings.setTwistMinAngle ( sTwistMinAngle);
			settings.setTwistMaxAngle ( sTwistMaxAngle);

			TwoBodyConstraintRef constraint = settings.create(prev, segment).toRef();
			mPhysicsSystem.addConstraint(constraint);
			mConstraints.add(constraint);
		}

		prev = segment;
	}
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	for (TwoBodyConstraintRef cr : mConstraints)
	{
		SwingTwistConstraint c=(SwingTwistConstraint)cr.getPtr();
                c.setNormalHalfConeAngle(sNormalHalfConeAngle);
		c.setPlaneHalfConeAngle(sPlaneHalfConeAngle);
		c.setTwistMinAngle(sTwistMinAngle);
		c.setTwistMaxAngle(sTwistMaxAngle);
	}
}
/* TODO

void SwingTwistConstraintTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI->CreateSlider(inSubMenu, "Min Twist Angle (deg)", RadiansToDegrees(sTwistMinAngle), -180.0f, 180.0f, 1.0f, [=](float inValue) { sTwistMinAngle = DegreesToRadians(inValue); });
	inUI->CreateSlider(inSubMenu, "Max Twist Angle (deg)", RadiansToDegrees(sTwistMaxAngle), -180.0f, 180.0f, 1.0f, [=](float inValue) { sTwistMaxAngle = DegreesToRadians(inValue); });
	inUI->CreateSlider(inSubMenu, "Normal Half Cone Angle (deg)", RadiansToDegrees(sNormalHalfConeAngle), 0.0f, 180.0f, 1.0f, [=](float inValue) { sNormalHalfConeAngle = DegreesToRadians(inValue); });
	inUI->CreateSlider(inSubMenu, "Plane Half Cone Angle (deg)", RadiansToDegrees(sPlaneHalfConeAngle), 0.0f, 180.0f, 1.0f, [=](float inValue) { sPlaneHalfConeAngle = DegreesToRadians(inValue); });

	inUI->ShowMenu(inSubMenu);
}
*/}
