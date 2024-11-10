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
package testjoltjni.app.samples.water;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.operator.Op;
import com.github.stephengold.joltjni.readonly.*;
import testjoltjni.app.samples.*;
import testjoltjni.app.testframework.CameraState;
/**
 * A line-for-line Java translation of the Jolt Physics boat test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Water/BoatTest.cpp
 */
public class BoatTest extends Test{
float cMaxWaterHeight = 5.0f;
float cMinWaterHeight=3f;
float cWaterWidth=100f;
float cHalfBoatLength=4f;
float cHalfBoatTopWidth=1.5f;
float cHalfBoatBottomWidth=1.2f;
float cBoatBowLength=2f;
float cHalfBoatHeight=0.75f;
float cBoatMass=1000f;
float cBoatBuoyancy=3f;
float cBoatLinearDrag=0.5f;
float cBoatAngularDrag=0.7f;
float cBarrelMass=50f;
float cBarrelBuoyancy=1.5f;
float cBarrelLinearDrag=0.5f;
float cBarrelAngularDrag=0.1f;
float cForwardAcceleration=15f;
float cSteerAcceleration=1.5f;
Body mBoatBody;
BodyId mWaterSensor=new BodyId();
RMat44 mCameraPivot=RMat44.sIdentity();
Mutex mBodiesInWaterMutex=new Mutex();
BodyIdVector mBodiesInWater=new BodyIdVector();
float mForward, mRight, mTime;

public void Initialize()
{
	// Create boat
	ConvexHullShapeSettings boat_hull = new ConvexHullShapeSettings();
	boat_hull.setPoints (
		new Vec3(-cHalfBoatTopWidth, cHalfBoatHeight, -cHalfBoatLength),
		new Vec3(cHalfBoatTopWidth, cHalfBoatHeight, -cHalfBoatLength),
		new Vec3(-cHalfBoatTopWidth, cHalfBoatHeight, cHalfBoatLength),
		new Vec3(cHalfBoatTopWidth, cHalfBoatHeight, cHalfBoatLength),
		new Vec3(-cHalfBoatBottomWidth, -cHalfBoatHeight, -cHalfBoatLength),
		new Vec3(cHalfBoatBottomWidth, -cHalfBoatHeight, -cHalfBoatLength),
		new Vec3(-cHalfBoatBottomWidth, -cHalfBoatHeight, cHalfBoatLength),
		new Vec3(cHalfBoatBottomWidth, -cHalfBoatHeight, cHalfBoatLength),
		new Vec3(0, cHalfBoatHeight, cHalfBoatLength + cBoatBowLength)
        );
	boat_hull.setEmbedded();
	OffsetCenterOfMassShapeSettings com_offset=new OffsetCenterOfMassShapeSettings(new Vec3(0, -cHalfBoatHeight, 0), boat_hull);
	com_offset.setEmbedded();
	RVec3 position=new RVec3(0, cMaxWaterHeight + 2, 0);
	BodyCreationSettings boat=new BodyCreationSettings(com_offset, position, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
	boat.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
	boat.getMassPropertiesOverride().setMass ( cBoatMass);
	mBoatBody = mBodyInterface.createBody(boat);
	mBodyInterface.addBody(mBoatBody.getId(), EActivation.Activate);

	// Create water sensor. We use this to detect which bodies entered the water (in this sample we could have assumed everything is in the water)
	BodyCreationSettings water_sensor=new BodyCreationSettings(new BoxShape(new Vec3(cWaterWidth, cMaxWaterHeight, cWaterWidth)), RVec3.sZero(), Quat.sIdentity(), EMotionType.Static, Layers.SENSOR);
	water_sensor.setIsSensor ( true);
	mWaterSensor = mBodyInterface.createAndAddBody(water_sensor, EActivation.Activate);

	// Create some barrels to float in the water
	DefaultRandomEngine random=new DefaultRandomEngine();
	BodyCreationSettings barrel=new BodyCreationSettings(new CylinderShape(1.0f, 0.7f), RVec3.sZero(), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
	barrel.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
	barrel.getMassPropertiesOverride().setMass ( cBarrelMass);
	for (int i = 0; i < 10; ++i)
	{
		barrel.setPosition (new RVec3(-10.0f + i * 2.0f, cMaxWaterHeight + 2, 10));
		barrel.setRotation ( Quat.sRandom(random));
		mBodyInterface.createAndAddBody(barrel, EActivation.Activate);
	}

	UpdateCameraPivot();
}
/*TODO

void BoatTest::ProcessInput(const ProcessInputParams &inParams)
{
	// Determine acceleration and brake
	mForward = 0.0f;
	if (inParams.mKeyboard->IsKeyPressed(DIK_UP))
		mForward = 1.0f;
	else if (inParams.mKeyboard->IsKeyPressed(DIK_DOWN))
		mForward = -1.0f;

	// Steering
	mRight = 0.0f;
	if (inParams.mKeyboard->IsKeyPressed(DIK_LEFT))
		mRight = -1.0f;
	else if (inParams.mKeyboard->IsKeyPressed(DIK_RIGHT))
		mRight = 1.0f;
}
*/

RVec3 GetWaterSurfacePosition(RVec3Arg inXZPosition)
{
	return new RVec3(inXZPosition.xx(), cMinWaterHeight + Jolt.sin(0.1f * (float)(inXZPosition.getZ()) + mTime) * (cMaxWaterHeight - cMinWaterHeight), inXZPosition.zz());
}

public void PrePhysicsUpdate(PreUpdateParams inParams)
{
	// Update time
	mTime += inParams.mDeltaTime;

	// Draw the water surface
	final float step = 1.0f;
	for (float z = -cWaterWidth; z < cWaterWidth; z += step)
	{
		RVec3 p1 = GetWaterSurfacePosition(new RVec3(-cWaterWidth, 0, z));
		RVec3 p2 = GetWaterSurfacePosition(new RVec3(-cWaterWidth, 0, z + step));
		RVec3 p3 = GetWaterSurfacePosition(new RVec3(cWaterWidth, 0, z));
		RVec3 p4 = GetWaterSurfacePosition(new RVec3(cWaterWidth, 0, z + step));
		mDebugRenderer.drawTriangle(p1, p2, p3, Color.sBlue);
		mDebugRenderer.drawTriangle(p2, p4, p3, Color.sBlue);
	}

	// Apply buoyancy to all bodies in the water
	{
		mBodiesInWaterMutex.lock();
		for (ConstBodyId id : mBodiesInWater.toList())
		{
			BodyLockWrite body_lock=new BodyLockWrite(mPhysicsSystem.getBodyLockInterface(), id);
			Body body = body_lock.getBody();
			if (body.isActive())
			{
				// Use center of mass position to determine water surface position (you could test multiple points on the actual shape of the boat to get a more accurate result)
				RVec3 surface_position = GetWaterSurfacePosition(body.getCenterOfMassPosition());

				// Crude way of approximating the surface normal
				RVec3 p2 = GetWaterSurfacePosition(Op.add(body.getCenterOfMassPosition() , new Vec3(0, 0, 1)));
				RVec3 p3 = GetWaterSurfacePosition(Op.add(body.getCenterOfMassPosition() , new Vec3(1, 0, 0)));
				Vec3 surface_normal = Op.subtract(p2 , surface_position).cross(Op.subtract(p3 , surface_position)).toVec3().normalized();

				// Determine buoyancy and drag
				float buoyancy, linear_drag, angular_drag;
				if (id == mBoatBody.getId())
				{
					buoyancy = cBoatBuoyancy;
					linear_drag = cBoatLinearDrag;
					angular_drag = cBoatAngularDrag;
				}
				else
				{
					buoyancy = cBarrelBuoyancy;
					linear_drag = cBarrelLinearDrag;
					angular_drag = cBarrelAngularDrag;
				}

				// Apply buoyancy to the body
				body.applyBuoyancyImpulse(surface_position, surface_normal, buoyancy, linear_drag, angular_drag, Vec3.sZero(), mPhysicsSystem.getGravity(), inParams.mDeltaTime);
			}
		}
		mBodiesInWaterMutex.unlock();
	}

	// On user input, assure that the boat is active
	if (mRight != 0.0f || mForward != 0.0f)
		mBodyInterface.activateBody(mBoatBody.getId());

	// Apply forces to rear of boat where the propeller would be but only when the propeller is under water
	RVec3 propeller_position =Op.multiply( mBoatBody.getWorldTransform() , new Vec3(0, -cHalfBoatHeight, -cHalfBoatLength));
	RVec3 propeller_surface_position = GetWaterSurfacePosition(propeller_position);
	if (propeller_surface_position.yy() > propeller_position.yy())
	{
		Vec3 forward = mBoatBody.getRotation().rotateAxisZ();
		Vec3 right = mBoatBody.getRotation().rotateAxisX();
		mBoatBody.addImpulse(Op.multiply(Op.add(Op.multiply(forward , mForward * cForwardAcceleration) , Op.multiply(right , Jolt.sign(mForward) * mRight * cSteerAcceleration)) , cBoatMass * inParams.mDeltaTime), propeller_position);
	}

	UpdateCameraPivot();
}

void SaveInputState(StateRecorder inStream)
{
	inStream.write(mForward);
	inStream.write(mRight);
}

void RestoreInputState(StateRecorder inStream)
{
	mForward=inStream.readFloat(mForward);
	mRight=inStream.readFloat(mRight);
}

public void SaveState(StateRecorder inStream)
{
	inStream.write(mTime);
	inStream.write(mBodiesInWater);
}

public void RestoreState(StateRecorder inStream)
{
	mTime=inStream.readFloat(mTime);
	inStream.readBodyIdVector(mBodiesInWater);
}

public void GetInitialCamera(CameraState ioState)
{
	// Position camera behind boat
	RVec3 cam_tgt =new RVec3(0, 0, 5);
	ioState.mPos =new RVec3(0, 5, -10);
	ioState.mForward = Op.subtract( cam_tgt , ioState.mPos).toVec3().normalized();
}

void UpdateCameraPivot()
{
	// Pivot is center of boat and rotates with boat around Y axis only
	Vec3 fwd = mBoatBody.getRotation().rotateAxisZ();
	fwd.setY(0.0f);
	float len = fwd.length();
	if (len != 0.0f)
		Op.divideEquals(fwd , len);
	else
		fwd = Vec3.sAxisZ();
	Vec3 up = Vec3.sAxisY();
	Vec3 right = up.cross(fwd);
	mCameraPivot =new RMat44(new Vec4(right, 0), new Vec4(up, 0), new Vec4(fwd, 0), mBoatBody.getPosition());
}

void OnContactAdded(ConstBody inBody1, ConstBody inBody2, ConstContactManifold inManifold, ContactSettings ioSettings)
{
	// When a body enters the water add it to the list of bodies in the water
	mBodiesInWaterMutex.lock();
	if (inBody1.getId() == mWaterSensor)
		mBodiesInWater.pushBack(inBody2.getId().copy());
	else if (inBody2.getId() == mWaterSensor)
		mBodiesInWater.pushBack(inBody1.getId().copy());
	mBodiesInWater.sort(); // Sort to make deterministic (OnContactAdded is called from multiple threads and the order is not guaranteed)
        mBodiesInWaterMutex.unlock();
}

void OnContactRemoved(ConstSubShapeIdPair inSubShapePair)
{
	// When a body leaves the water remove it from the list of bodies in the water
	mBodiesInWaterMutex.lock();
	if (inSubShapePair.getBody1Id() == mWaterSensor)
		mBodiesInWater.erase(mBodiesInWater.find(inSubShapePair.getBody2Id()));
	else if (inSubShapePair.getBody2Id() == mWaterSensor)
		mBodiesInWater.erase(mBodiesInWater.find(inSubShapePair.getBody1Id()));
      	mBodiesInWaterMutex.unlock();
}
}