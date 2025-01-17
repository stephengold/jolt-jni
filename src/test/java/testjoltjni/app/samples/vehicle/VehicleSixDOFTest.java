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
import testjoltjni.app.samples.*;
import testjoltjni.app.testframework.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt Physics vehicle 6-DOF test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Vehicle/VehicleSixDOFTest.cpp
 */
public class VehicleSixDOFTest extends VehicleTest{
float cMaxSteeringAngle=degreesToRadians(30);
enum EWheel{LeftFront,RightFront,LeftRear,RightRear,Num};
boolean sIsFrontWheel(EWheel inWheel){return inWheel==EWheel.LeftFront||inWheel==EWheel.RightFront;}
boolean sIsLeftWheel(EWheel inWheel){return inWheel==EWheel.LeftFront||inWheel==EWheel.LeftRear;}
Body mCarBody;
TwoBodyConstraintRef[] mWheels=new TwoBodyConstraintRef[EWheel.Num.ordinal()];
RMat44 mCameraPivot=RMat44.sIdentity();
float mSpeed,mSteeringAngle;

public void Initialize()
{
	super.Initialize();

	final float half_vehicle_length = 2.0f;
	final float half_vehicle_width = 0.9f;
	final float half_vehicle_height = 0.2f;

	final float half_wheel_height = 0.3f;
	final float half_wheel_width = 0.05f;
	final float half_wheel_travel = 0.5f;

	Vec3 wheel_position[] =
	{
		new Vec3(-half_vehicle_width, -half_vehicle_height, half_vehicle_length - 2.0f * half_wheel_height),
		new Vec3(half_vehicle_width, -half_vehicle_height, half_vehicle_length - 2.0f * half_wheel_height),
		new Vec3(-half_vehicle_width, -half_vehicle_height, -half_vehicle_length + 2.0f * half_wheel_height),
		new Vec3(half_vehicle_width, -half_vehicle_height, -half_vehicle_length + 2.0f * half_wheel_height),
	};

	RVec3 position=new RVec3(0, 2, 0);

	ShapeRefC body_shape = new BoxShape(new Vec3(half_vehicle_width, half_vehicle_height, half_vehicle_length)).toRefC();

	ConvexShape wheel_shape = new CylinderShape(half_wheel_width, half_wheel_height);
	wheel_shape.setDensity(1.0e4f);

	// Create group filter
	GroupFilterTableRef group_filter = new GroupFilterTable().toRef();

	// Create vehicle body
	mCarBody = mBodyInterface.createBody(new BodyCreationSettings(body_shape, position, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
	mCarBody.setCollisionGroup(new CollisionGroup(group_filter, 0, 0));
	mBodyInterface.addBody(mCarBody.getId(), EActivation.Activate);

	// Create wheels
	for (int i = 0; i < EWheel.Num.ordinal(); ++i)
	{
		boolean is_front = sIsFrontWheel(EWheel.values()[i]);
		boolean is_left = sIsLeftWheel(EWheel.values()[i]);

		RVec3 wheel_pos1 = plus(position , wheel_position[i]);
		RVec3 wheel_pos2 = minus(wheel_pos1 ,new Vec3(0, half_wheel_travel, 0));

		// Create body
		Body wheel = mBodyInterface.createBody(new BodyCreationSettings(wheel_shape, wheel_pos2, Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI), EMotionType.Dynamic, Layers.MOVING));
		wheel.setFriction(1.0f);
		wheel.setCollisionGroup(new CollisionGroup(group_filter, 0, 0));
		mBodyInterface.addBody(wheel.getId(), EActivation.Activate);

		// Create constraint
		SixDofConstraintSettings settings=new SixDofConstraintSettings();
		settings.setPosition1 ( wheel_pos1);
		settings.setPosition2 ( wheel_pos2);
		settings.setAxisX1 ( settings.setAxisX2 ( is_left? minus(Vec3.sAxisX()) : Vec3.sAxisX()));
		settings.setAxisY1 ( settings.setAxisY2 (Vec3.sAxisY()));

		// The suspension works in the Y translation axis only
		settings.makeFixedAxis(EAxis.TranslationX);
		settings.setLimitedAxis(EAxis.TranslationY, -half_wheel_travel, half_wheel_travel);
		settings.makeFixedAxis(EAxis.TranslationZ);
		settings.setMotorSettings(EAxis.TranslationY ,new MotorSettings(2.0f, 1.0f, 1.0e5f, 0.0f));

		// Front wheel can rotate around the Y axis
		if (is_front)
			settings.setLimitedAxis(EAxis.RotationY, -cMaxSteeringAngle, cMaxSteeringAngle);
		else
			settings.makeFixedAxis(EAxis.RotationY);

		// The Z axis is static
		settings.makeFixedAxis(EAxis.RotationZ);

		// The main engine drives the X axis
		settings.makeFreeAxis(EAxis.RotationX);
		settings.setMotorSettings(EAxis.RotationX ,new MotorSettings(2.0f, 1.0f, 0.0f, 0.5e4f));

		// The front wheel needs to be able to steer around the Y axis
		// However the motors work in the constraint space of the wheel, and since this rotates around the
		// X axis we need to drive both the Y and Z to steer
		if (is_front)
			settings.setMotorSettings(EAxis.RotationY , settings.setMotorSettings(EAxis.RotationZ, new MotorSettings(10.0f, 1.0f, 0.0f, 1.0e6f)));

		SixDofConstraint wheel_constraint = (SixDofConstraint) (settings.create(mCarBody, wheel));
		mPhysicsSystem.addConstraint(wheel_constraint);
		mWheels[i] = wheel_constraint.toRef();

		// Drive the suspension
		wheel_constraint.setTargetPositionCs(new Vec3(0, -half_wheel_travel, 0));
		wheel_constraint.setMotorState(EAxis.TranslationY, EMotorState.Position);

		// The front wheels steer around the Y axis, but in constraint space of the wheel this means we need to drive
		// both Y and Z (see comment above)
		if (is_front)
		{
			wheel_constraint.setTargetOrientationCs(Quat.sIdentity());
			wheel_constraint.setMotorState(EAxis.RotationY, EMotorState.Position);
			wheel_constraint.setMotorState(EAxis.RotationZ, EMotorState.Position);
		}
	}

	UpdateCameraPivot();
}
/*TODO

void VehicleSixDOFTest::ProcessInput(const ProcessInputParams &inParams)
{
	const float max_rotation_speed = 10.0f * JPH_PI;

	// Determine steering and speed
	mSteeringAngle = 0.0f;
	mSpeed = 0.0f;
	if (inParams.mKeyboard->IsKeyPressed(EKey::Left))	mSteeringAngle = cMaxSteeringAngle;
	if (inParams.mKeyboard->IsKeyPressed(EKey::Right))	mSteeringAngle = -cMaxSteeringAngle;
	if (inParams.mKeyboard->IsKeyPressed(EKey::Up))		mSpeed = max_rotation_speed;
	if (inParams.mKeyboard->IsKeyPressed(EKey::Down))	mSpeed = -max_rotation_speed;
}
*/

public void PrePhysicsUpdate(PreUpdateParams inParams)
{
	super.PrePhysicsUpdate(inParams);

	UpdateCameraPivot();

	// On user input, assure that the car is active
	if (mSteeringAngle != 0.0f || mSpeed != 0.0f)
		mBodyInterface.activateBody(mCarBody.getId());

	// Brake if current velocity is in the opposite direction of the desired velocity
	float car_speed = mCarBody.getLinearVelocity().dot(mCarBody.getRotation().rotateAxisZ());
	boolean brake = mSpeed != 0.0f && car_speed != 0.0f && sign(mSpeed) != sign(car_speed);

	// Front wheels
	EWheel front_wheels[] = { EWheel.LeftFront, EWheel.RightFront };
	for (EWheel w : front_wheels)
	{
		SixDofConstraint wheel_constraint = (SixDofConstraint)mWheels[w.ordinal()].getPtr();
		if (wheel_constraint == nullptr)
			continue;

		// Steer front wheels
		Quat steering_rotation = Quat.sRotation(Vec3.sAxisY(), mSteeringAngle);
		wheel_constraint.setTargetOrientationCs(steering_rotation);

		if (brake)
		{
			// Brake on front wheels
			wheel_constraint.setTargetAngularVelocityCs(Vec3.sZero());
			wheel_constraint.setMotorState(EAxis.RotationX, EMotorState.Velocity);
		}
		else if (mSpeed != 0.0f)
		{
			// Front wheel drive, since the motors are applied in the constraint space of the wheel
			// it is always applied on the X axis
			wheel_constraint.setTargetAngularVelocityCs(new Vec3(sIsLeftWheel(w)? -mSpeed : mSpeed, 0, 0));
			wheel_constraint.setMotorState(EAxis.RotationX, EMotorState.Velocity);
		}
		else
		{
			// Free spin
			wheel_constraint.setMotorState(EAxis.RotationX, EMotorState.Off);
		}
	}

	// Rear wheels
	final EWheel rear_wheels[] = { EWheel.LeftRear, EWheel.RightRear };
	for (EWheel w : rear_wheels)
	{
		SixDofConstraint wheel_constraint = (SixDofConstraint)mWheels[w.ordinal()].getPtr();
		if (wheel_constraint == nullptr)
			continue;

		if (brake)
		{
			// Brake on rear wheels
			wheel_constraint.setTargetAngularVelocityCs(Vec3.sZero());
			wheel_constraint.setMotorState(EAxis.RotationX, EMotorState.Velocity);
		}
		else
		{
			// Free spin
			wheel_constraint.setMotorState(EAxis.RotationX, EMotorState.Off);
		}
	}
}

public void GetInitialCamera(CameraState ioState)
{
	// Position camera behind car
	RVec3 cam_tgt =new RVec3(0, 0, 5);
	ioState.mPos =new RVec3(0, 2.5, -5);
	ioState.mForward =new Vec3(minus(cam_tgt , ioState.mPos)).normalized();
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
	mCameraPivot =new RMat44(new Vec4(right, 0),new Vec4(up, 0),new Vec4(fwd, 0), mCarBody.getPosition());
}

void SaveInputState(StateRecorder inStream)
{
	inStream.write(mSteeringAngle);
	inStream.write(mSpeed);
}

void RestoreInputState(StateRecorder inStream)
{
	mSteeringAngle=inStream.readFloat(mSteeringAngle);
	mSpeed=inStream.readFloat(mSpeed);
}
}
