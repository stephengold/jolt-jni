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
package testjoltjni.app.samples;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.operator.Op;
import com.github.stephengold.joltjni.readonly.*;
import testjoltjni.app.testframework.*;

/**
 * A line-for-line Java translation of the Jolt Physics tank test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Vehicle/TankTest.cpp
 */
class TankTest extends VehicleTest{
Body mTankBody;
Body mTurretBody;
Body mBarrelBody;
VehicleConstraint mVehicleConstraint;
HingeConstraint mTurretHinge;
HingeConstraint mBarrelHinge;
float mReloadTime = 0.0f;
RVec3 mCameraPivot = RVec3.sZero();
float mForward = 0.0f;
float mPreviousForward = 1.0f;
float mLeftRatio = 0.0f;
float mRightRatio = 0.0f;
float mBrake = 0.0f;
float mTurretHeading = 0.0f;
float mBarrelPitch = 0.0f;
boolean mFire = false;

void Initialize()
{
	super.Initialize();

	final float wheel_radius = 0.3f;
	final float wheel_width = 0.1f;
	final float half_vehicle_length = 3.2f;
	final float half_vehicle_width = 1.7f;
	final float half_vehicle_height = 0.5f;
	final float suspension_min_length = 0.3f;
	final float suspension_max_length = 0.5f;
	final float suspension_frequency = 1.0f;

	final float half_turret_width = 1.4f;
	final float	half_turret_length = 2.0f;
	final float half_turret_height = 0.4f;

	final float half_barrel_length = 1.5f;
	final float barrel_radius = 0.1f;
	final float barrel_rotation_offset = 0.2f;

	Vec3 wheel_pos[] = {
		new Vec3(0.0f, -0.0f, 2.95f),
		new Vec3(0.0f, -0.3f, 2.1f),
		new Vec3(0.0f, -0.3f, 1.4f),
		new Vec3(0.0f, -0.3f, 0.7f),
		new Vec3(0.0f, -0.3f, 0.0f),
		new Vec3(0.0f, -0.3f, -0.7f),
		new Vec3(0.0f, -0.3f, -1.4f),
		new Vec3(0.0f, -0.3f, -2.1f),
		new Vec3(0.0f, -0.0f, -2.75f),
	};

	// Create filter to prevent body, turret and barrel from colliding
	GroupFilter filter = new GroupFilterTable();

	// Create tank body
	RVec3 body_position=new RVec3(0, 2, 0);
	ShapeRefC tank_body_shape =new OffsetCenterOfMassShapeSettings(new Vec3(0, -half_vehicle_height, 0), new BoxShape(new Vec3(half_vehicle_width, half_vehicle_height, half_vehicle_length)).toRefC()).create().get();
	BodyCreationSettings tank_body_settings=new BodyCreationSettings(tank_body_shape, body_position, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
	tank_body_settings.getCollisionGroup().setGroupFilter ( filter);
	tank_body_settings.getCollisionGroup().setGroupId ( 0);
	tank_body_settings.getCollisionGroup().setSubGroupId ( 0);
	tank_body_settings.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
	tank_body_settings.getMassPropertiesOverride().setMass ( 4000.0f);
	mTankBody = mBodyInterface.createBody(tank_body_settings);
	mBodyInterface.addBody(mTankBody.getId(), EActivation.Activate);

	// Create vehicle constraint
	VehicleConstraintSettings vehicle=new VehicleConstraintSettings();
	vehicle.setDrawConstraintSize ( 0.1f);
	vehicle.setMaxPitchRollAngle ( Jolt.degreesToRadians(60.0f));

	TrackedVehicleControllerSettings controller = new TrackedVehicleControllerSettings();
	vehicle.setController ( controller);

	for (int t = 0; t < 2; ++t)
	{
		VehicleTrackSettings track = controller.getTrack(t);

		// Last wheel is driven wheel
		track.setDrivenWheel ( (int)(vehicle.getNumWheels() + wheel_pos.length - 1));

		for (int wheel = 0; wheel < wheel_pos.length; ++wheel)
		{
			WheelSettingsTv w = new WheelSettingsTv();
			w.setPosition ( wheel_pos[wheel]);
			w.getPosition().setX(t == 0? half_vehicle_width : -half_vehicle_width);
			w.setRadius ( wheel_radius);
			w.setWidth ( wheel_width);
			w.setSuspensionMinLength ( suspension_min_length);
			w.setSuspensionMaxLength ( wheel == 0 || wheel == wheel_pos.length - 1? suspension_min_length : suspension_max_length);
			w.getSuspensionSpring().setFrequency ( suspension_frequency);

			// Add the wheel to the vehicle
			track.addWheel((int)vehicle.getNumWheels());
			vehicle.addWheels(w);
		}
	}

	mVehicleConstraint = new VehicleConstraint(mTankBody, vehicle);
	mVehicleConstraint.setVehicleCollisionTester(new VehicleCollisionTesterRay(Layers.MOVING));
if (Jolt.implementsDebugRendering()){
	((TrackedVehicleController )(mVehicleConstraint.getController())).setRpmMeter(new Vec3(0, 2, 0), 0.5f);
} // JPH_DEBUG_RENDERER
	mPhysicsSystem.addConstraint(mVehicleConstraint);
	mPhysicsSystem.addStepListener(mVehicleConstraint);

	// Create turret
	RVec3 turret_position = Op.add(body_position , new Vec3(0, half_vehicle_height + half_turret_height, 0));
	BodyCreationSettings turret_body_setings=new BodyCreationSettings(new BoxShape(new Vec3(half_turret_width, half_turret_height, half_turret_length)), turret_position, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
	turret_body_setings.getCollisionGroup().setGroupFilter(filter);
	turret_body_setings.getCollisionGroup().setGroupId(0);
	turret_body_setings.getCollisionGroup().setSubGroupId(0);
	turret_body_setings.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
	turret_body_setings.getMassPropertiesOverride().setMass ( 2000.0f);
	mTurretBody = mBodyInterface.createBody(turret_body_setings);
	mBodyInterface.addBody(mTurretBody.getId(), EActivation.Activate);

	// Attach turret to body
	HingeConstraintSettings turret_hinge=new HingeConstraintSettings();
	turret_hinge.setPoint1 ( turret_hinge.setPoint2 ( Op.add( body_position , new Vec3(0, half_vehicle_height, 0))));
	turret_hinge.setHingeAxis1 ( turret_hinge.setHingeAxis2 ( Vec3.sAxisY()));
	turret_hinge.setNormalAxis1 ( turret_hinge.setNormalAxis2 ( Vec3.sAxisZ()));
	turret_hinge.setMotorSettings (new MotorSettings(0.5f, 1.0f));
	mTurretHinge = (HingeConstraint)(turret_hinge.create(mTankBody, mTurretBody));
	mTurretHinge.setMotorState(EMotorState.Position);
	mPhysicsSystem.addConstraint(mTurretHinge);

	// Create barrel
	RVec3 barrel_position = Op.add(turret_position , new Vec3(0, 0, half_turret_length + half_barrel_length - barrel_rotation_offset));
	BodyCreationSettings barrel_body_setings=new BodyCreationSettings(new CylinderShape(half_barrel_length, barrel_radius), barrel_position, Quat.sRotation(Vec3.sAxisX(), 0.5f * Jolt.JPH_PI), EMotionType.Dynamic, Layers.MOVING);
	barrel_body_setings.getCollisionGroup().setGroupFilter(filter);
	barrel_body_setings.getCollisionGroup().setGroupId(0);
	barrel_body_setings.getCollisionGroup().setSubGroupId(0);
	barrel_body_setings.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
	barrel_body_setings.getMassPropertiesOverride().setMass ( 200.0f);
	mBarrelBody = mBodyInterface.createBody(barrel_body_setings);
	mBodyInterface.addBody(mBarrelBody.getId(), EActivation.Activate);

	// Attach barrel to turret
	HingeConstraintSettings barrel_hinge=new HingeConstraintSettings();
	barrel_hinge.setPoint1 ( barrel_hinge.setPoint2 ( Op.subtract(barrel_position , new Vec3(0, 0, half_barrel_length))));
	barrel_hinge.setHingeAxis1 ( barrel_hinge.setHingeAxis2 ( Op.negate(Vec3.sAxisX())));
	barrel_hinge.setNormalAxis1 ( barrel_hinge.setNormalAxis2 ( Vec3.sAxisZ()));
	barrel_hinge.setLimitsMin ( Jolt.degreesToRadians(-10.0f));
	barrel_hinge.setLimitsMax ( Jolt.degreesToRadians(40.0f));
	barrel_hinge.setMotorSettings (new MotorSettings(10.0f, 1.0f));
	mBarrelHinge = (HingeConstraint )(barrel_hinge.create(mTurretBody, mBarrelBody));
	mBarrelHinge.setMotorState(EMotorState.Position);
	mPhysicsSystem.addConstraint(mBarrelHinge);

	// Update camera pivot
	mCameraPivot = mTankBody.getPosition();
}

/*TODO
void TankTest::ProcessInput(const ProcessInputParams &inParams)
{
	const float min_velocity_pivot_turn = 1.0f;

	// Determine acceleration and brake
	mForward = 0.0f;
	mBrake = 0.0f;
	if (inParams.mKeyboard->IsKeyPressed(DIK_RSHIFT))
		mBrake = 1.0f;
	else if (inParams.mKeyboard->IsKeyPressed(DIK_UP))
		mForward = 1.0f;
	else if (inParams.mKeyboard->IsKeyPressed(DIK_DOWN))
		mForward = -1.0f;

	// Steering
	mLeftRatio = 1.0f;
	mRightRatio = 1.0f;
	float velocity = (mTankBody->GetRotation().Conjugated() * mTankBody->GetLinearVelocity()).GetZ();
	if (inParams.mKeyboard->IsKeyPressed(DIK_LEFT))
	{
		if (mBrake == 0.0f && mForward == 0.0f && abs(velocity) < min_velocity_pivot_turn)
		{
			// Pivot turn
			mLeftRatio = -1.0f;
			mForward = 1.0f;
		}
		else
			mLeftRatio = 0.6f;
	}
	else if (inParams.mKeyboard->IsKeyPressed(DIK_RIGHT))
	{
		if (mBrake == 0.0f && mForward == 0.0f && abs(velocity) < min_velocity_pivot_turn)
		{
			// Pivot turn
			mRightRatio = -1.0f;
			mForward = 1.0f;
		}
		else
			mRightRatio = 0.6f;
	}

	// Check if we're reversing direction
	if (mPreviousForward * mForward < 0.0f)
	{
		// Get vehicle velocity in local space to the body of the vehicle
		if ((mForward > 0.0f && velocity < -0.1f) || (mForward < 0.0f && velocity > 0.1f))
		{
			// Brake while we've not stopped yet
			mForward = 0.0f;
			mBrake = 1.0f;
		}
		else
		{
			// When we've come to a stop, accept the new direction
			mPreviousForward = mForward;
		}
	}

	// Cast ray to find target
	RRayCast ray { inParams.mCameraState.mPos, 1000.0f * inParams.mCameraState.mForward };
	RayCastSettings ray_settings;
	ClosestHitCollisionCollector<CastRayCollector> collector;
	IgnoreMultipleBodiesFilter body_filter;
	body_filter.Reserve(3);
	body_filter.IgnoreBody(mTankBody->GetID());
	body_filter.IgnoreBody(mTurretBody->GetID());
	body_filter.IgnoreBody(mBarrelBody->GetID());
	mPhysicsSystem->GetNarrowPhaseQuery().CastRay(ray, ray_settings, collector, {}, {}, body_filter);
	RVec3 hit_pos = collector.HadHit()? ray.GetPointOnRay(collector.mHit.mFraction) : ray.mOrigin + ray.mDirection;
	mDebugRenderer->DrawMarker(hit_pos, Color::sGreen, 1.0f);

	// Orient the turret towards the hit position
	RMat44 turret_to_world = mTankBody->GetCenterOfMassTransform() * mTurretHinge->GetConstraintToBody1Matrix();
	Vec3 hit_pos_in_turret = Vec3(turret_to_world.InversedRotationTranslation() * hit_pos);
	mTurretHeading = ATan2(hit_pos_in_turret.GetZ(), hit_pos_in_turret.GetY());

	// Orient barrel towards the hit position
	RMat44 barrel_to_world = mTurretBody->GetCenterOfMassTransform() * mBarrelHinge->GetConstraintToBody1Matrix();
	Vec3 hit_pos_in_barrel = Vec3(barrel_to_world.InversedRotationTranslation() * hit_pos);
	mBarrelPitch = ATan2(hit_pos_in_barrel.GetZ(), hit_pos_in_barrel.GetY());

	// If user wants to fire
	mFire = inParams.mKeyboard->IsKeyPressed(DIK_RETURN);
}
*/

void PrePhysicsUpdate(PreUpdateParams inParams)
{
	super.PrePhysicsUpdate(inParams);

	final float bullet_radius = 0.061f; // 120 mm
	final Vec3Arg bullet_pos =new Vec3(0, 1.6f, 0);
	final Vec3Arg bullet_velocity =new Vec3(0, 400.0f, 0); // Normal exit velocities are around 1100-1700 m/s, use a lower variable as we have a limit to max velocity (See: https://tanks-encyclopedia.com/coldwar-usa-120mm-gun-tank-m1e1-abrams/)
	final float bullet_mass = 40.0f; // Normal projectile weight is around 7 kg, use an increased value so the momentum is more realistic (with the lower exit velocity)
	final float bullet_reload_time = 2.0f;

	// Update camera pivot
	mCameraPivot = mTankBody.getPosition();

	// Assure the tank stays active as we're controlling the turret with the mouse
	mBodyInterface.activateBody(mTankBody.getId());

	// Pass the input on to the constraint
	((TrackedVehicleController)(mVehicleConstraint.getController())).setDriverInput(mForward, mLeftRatio, mRightRatio, mBrake);
	mTurretHinge.setTargetAngle(mTurretHeading);
	mBarrelHinge.setTargetAngle(mBarrelPitch);

	// Update reload time
	mReloadTime = Math.max(0.0f, mReloadTime - inParams.mDeltaTime);

	// Shoot bullet
	if (mReloadTime == 0.0f && mFire)
	{
		// Create bullet
		BodyCreationSettings bullet_creation_settings=new BodyCreationSettings(new SphereShape(bullet_radius), Op.multiply(mBarrelBody.getCenterOfMassTransform() , bullet_pos), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
		bullet_creation_settings.setMotionQuality ( EMotionQuality.LinearCast);
		bullet_creation_settings.setFriction ( 1.0f);
		bullet_creation_settings.setRestitution ( 0.0f);
		bullet_creation_settings.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
		bullet_creation_settings.getMassPropertiesOverride().setMass ( bullet_mass);
		Body bullet = mBodyInterface.createBody(bullet_creation_settings);
		bullet.setLinearVelocity(Op.rotate(mBarrelBody.getRotation() , bullet_velocity));
		mBodyInterface.addBody(bullet.getId(), EActivation.Activate);

		// Start reloading
		mReloadTime = bullet_reload_time;

		// Apply opposite impulse to turret body
		mBodyInterface.addImpulse(mTurretBody.getId(), Op.multiply(bullet.getLinearVelocity() , -bullet_mass));
	}

	// Draw our wheels (this needs to be done in the pre update since we draw the bodies too in the state before the step)
	for (int w = 0; w < mVehicleConstraint.countWheels(); ++w)
	{
		ConstWheelSettings settings = mVehicleConstraint.getWheel(w).getSettings();
		RMat44 wheel_transform = mVehicleConstraint.getWheelWorldTransform(w, Vec3.sAxisY(), Vec3.sAxisX()); // The cylinder we draw is aligned with Y so we specify that as rotational axis
		mDebugRenderer.drawCylinder(wheel_transform, 0.5f * settings.getWidth(), settings.getRadius(), Color.sGreen);
	}
}

void SaveState(StateRecorder inStream)
{
	inStream.write(mReloadTime);
}

void RestoreState(StateRecorder inStream)
{
	mReloadTime=inStream.readFloat(mReloadTime);
}

void SaveInputState(StateRecorder inStream)
{
	inStream.write(mForward);
	inStream.write(mPreviousForward);
	inStream.write(mLeftRatio);
	inStream.write(mRightRatio);
	inStream.write(mBrake);
	inStream.write(mTurretHeading);
	inStream.write(mBarrelPitch);
	inStream.write(mFire);
}

void RestoreInputState(StateRecorder inStream)
{
	mForward=inStream.readFloat(mForward);
	mPreviousForward=inStream.readFloat(mPreviousForward);
	mLeftRatio=inStream.readFloat(mLeftRatio);
	mRightRatio=inStream.readFloat(mRightRatio);
	mBrake=inStream.readFloat(mBrake);
	mTurretHeading=inStream.readFloat(mTurretHeading);
	mBarrelPitch=inStream.readFloat(mBarrelPitch);
	mFire=inStream.readBoolean(mFire);
}

void GetInitialCamera(CameraState ioState)
{
	// Position camera behind tank
	ioState.mPos =new RVec3(0, 4.0f, 0);
	ioState.mForward =new Vec3(0, -2.0f, 10.0f).normalized();
}

RMat44 GetCameraPivot(float inCameraHeading, float inCameraPitch)
{
	// Pivot is center of tank + a distance away from the tank based on the heading and pitch of the camera
	Vec3 fwd =new Vec3(Math.cos(inCameraPitch) * Math.cos(inCameraHeading), Math.sin(inCameraPitch), Math.cos(inCameraPitch) * Math.sin(inCameraHeading));
	return RMat44.sTranslation(Op.subtract(mCameraPivot , Op.multiply(10.0f , fwd)));
}
}
