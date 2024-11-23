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
import static com.github.stephengold.joltjni.std.Std.*;
/**
 * A line-for-line Java translation of the Jolt Physics swing-twist constraint friction test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/SwingTwistConstraintFrictionTest.cpp
 */
public class SwingTwistConstraintFrictionTest extends Test{
float mTime;
SwingTwistConstraint mConstraint;

public void Initialize()
{
	// Floor
	CreateFloor();

	// Create group filter
	GroupFilterTableRef group_filter = new GroupFilterTable().toRef();

	float half_cylinder_height = 1.5f;
	ShapeRefC capsule = new CapsuleShape(half_cylinder_height, 0.5f).toRefC();

	RVec3 body1_position=new RVec3(0, 10, 0);
	Body body1 = mBodyInterface.createBody(new BodyCreationSettings(capsule, body1_position, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
	body1.setCollisionGroup(new CollisionGroup(group_filter, 0, 0));
	mBodyInterface.addBody(body1.getId(), EActivation.DontActivate);

	RVec3 body2_position = plus(body1_position ,new Vec3(0, -2.0f * half_cylinder_height, 0));
	Body body2 = mBodyInterface.createBody(new BodyCreationSettings(capsule, body2_position, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
	body2.setCollisionGroup(new CollisionGroup(group_filter, 0, 0));
	body2.getMotionProperties().setLinearDamping(0.0f);
	body2.getMotionProperties().setAngularDamping(0.0f);
	body2.setAllowSleeping(false);
	mBodyInterface.addBody(body2.getId(), EActivation.Activate);

	SwingTwistConstraintSettings settings=new SwingTwistConstraintSettings();
	settings.setPosition1 ( settings.setPosition2 ( plus(body1_position ,new Vec3(0, -half_cylinder_height, 0))));
	settings.setTwistAxis1 ( settings.setTwistAxis2 (new Vec3(0, -1, 0)));
	settings.setPlaneAxis1 ( settings.setPlaneAxis2 (Vec3.sAxisX()));
	settings.setNormalHalfConeAngle ( degreesToRadians(90));
	settings.setPlaneHalfConeAngle ( degreesToRadians(90));
	settings.setTwistMinAngle ( -JPH_PI);
	settings.setTwistMaxAngle ( JPH_PI);

	float body2_inertia = star(body2.getMotionProperties().getLocalSpaceInverseInertia().inversed3x3() , Vec3.sAxisY()).length();
	final float max_angular_acceleration = degreesToRadians(90.0f); // rad/s^2
	settings.setMaxFrictionTorque ( body2_inertia * max_angular_acceleration);

	settings.setTwistMotorSettings ( settings.setSwingMotorSettings (new MotorSettings(10.0f, 2.0f)));

	mConstraint = (SwingTwistConstraint) (settings.create(body1, body2));
	mPhysicsSystem.addConstraint(mConstraint);
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	mTime += inParams.mDeltaTime;

	boolean pause = fmod(mTime, 5.0f) > 2.5f;

	if (pause)
	{
		mConstraint.setSwingMotorState(EMotorState.Off);
		mConstraint.setTwistMotorState(EMotorState.Off);
	}
	else
	{
		mConstraint.setSwingMotorState(EMotorState.Velocity);
		mConstraint.setTwistMotorState(EMotorState.Velocity);
		mConstraint.setTargetAngularVelocityCs(new Vec3(degreesToRadians(180.0f), 0, 0));
	}
}

void SaveState(StateRecorder inStream)
{
	inStream.write(mTime);
}

void RestoreState(StateRecorder inStream)
{
	mTime=inStream.readFloat(mTime);
}
}
