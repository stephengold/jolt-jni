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
package testjoltjni.app.samples.general;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.readonly.*;
import java.util.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt-Physics sensor test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/SensorTest.cpp
 */
public class SensorTest extends Test{
class BodyAndCount implements Comparable<BodyAndCount>{
    int mBodyID,mCount;
    BodyAndCount(){}
    BodyAndCount(int id,int cnt){mBodyID=id;mCount=cnt;}
    public int compareTo(BodyAndCount other){return Integer.compare(mBodyID, other.mBodyID);}
}
float mTime;
final int StaticAttractor=0,StaticSensor=1,KinematicSensor=2,SensorDetectingStatic=3,NumSensors=4;
int mKinematicBodyID;
int[] mSensorID = new int[NumSensors];
List<BodyAndCount>mBodiesInSensor=List.of(new BodyAndCount(),new BodyAndCount(),new BodyAndCount(),new BodyAndCount());
Mutex mMutex=new Mutex();
RagdollRef mRagdoll=new RagdollRef();

public void Cleanup()
{
	// Destroy the ragdoll
	mRagdoll.removeFromPhysicsSystem();
	mRagdoll = null;
}

public void Initialize()
{
	// Floor
	CreateFloor(400.0f);

	{
		// A static sensor that attracts dynamic bodies that enter its area
		BodyCreationSettings sensor_settings=new BodyCreationSettings(new SphereShape(10.0f),new RVec3(0, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.SENSOR);
		sensor_settings.setIsSensor ( true);
		mSensorID[StaticAttractor] = mBodyInterface.createAndAddBody(sensor_settings, EActivation.DontActivate);
		SetBodyLabel(mSensorID[StaticAttractor], "Static sensor that attracts dynamic bodies");
	}

	{
		// A static sensor that only detects active bodies
		BodyCreationSettings sensor_settings=new BodyCreationSettings(new BoxShape(Vec3.sReplicate(5.0f)),new RVec3(-10, 5.1f, 0), Quat.sIdentity(), EMotionType.Static, Layers.SENSOR);
		sensor_settings.setIsSensor ( true);
		mSensorID[StaticSensor] = mBodyInterface.createAndAddBody(sensor_settings, EActivation.DontActivate);
		SetBodyLabel(mSensorID[StaticSensor], "Static sensor that detects active dynamic bodies");
	}

	{
		// A kinematic sensor that also detects sleeping bodies
		BodyCreationSettings sensor_settings=new BodyCreationSettings(new BoxShape(Vec3.sReplicate(5.0f)),new RVec3(10, 5.1f, 0), Quat.sIdentity(), EMotionType.Kinematic, Layers.SENSOR);
		sensor_settings.setIsSensor ( true);
		mSensorID[KinematicSensor] = mBodyInterface.createAndAddBody(sensor_settings, EActivation.Activate);
		SetBodyLabel(mSensorID[KinematicSensor], "Kinematic sensor that also detects sleeping bodies");
	}

	{
		// A kinematic sensor that also detects static bodies
		BodyCreationSettings sensor_settings=new BodyCreationSettings(new BoxShape(Vec3.sReplicate(5.0f)),new RVec3(25, 5.1f, 0), Quat.sIdentity(), EMotionType.Kinematic, Layers.MOVING); // Put in a layer that collides with static
		sensor_settings.setIsSensor ( true);
		sensor_settings.setCollideKinematicVsNonDynamic ( true);
		mSensorID[SensorDetectingStatic] = mBodyInterface.createAndAddBody(sensor_settings, EActivation.Activate);
		SetBodyLabel(mSensorID[SensorDetectingStatic], "Kinematic sensor that also detects sleeping and static bodies");
	}

	// Dynamic bodies
	for (int i = 0; i < 15; ++i)
		mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(0.1f, 0.5f, 0.2f)),new RVec3(-15.0f + i * 3.0f, 25, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Static bodies
	RVec3 static_positions[] = {new RVec3(-14, 1, 4),new RVec3(6, 1, 4),new RVec3(21, 1, 4) };
	for ( RVec3 p : static_positions)
		mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(0.5f)), p, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.Activate);

RagdollSettingsRef ragdoll_settings;
if (Jolt.supportsObjectStream()){
	// Load ragdoll
	ragdoll_settings = RagdollLoader.sLoad("Assets/Human.tof", EMotionType.Dynamic).toRef();
	if (ragdoll_settings == nullptr)
		FatalError("Could not load ragdoll");
}else{
	ragdoll_settings = RagdollLoader.sCreate().toRef();
} // JPH_OBJECT_STREAM

	// Create pose
	SkeletonPose ragdoll_pose=new SkeletonPose();
	ragdoll_pose.setSkeleton(ragdoll_settings.getSkeleton());
	{
if (Jolt.supportsObjectStream()){
		SkeletalAnimationRef animation=new SkeletalAnimationRef();
		if (!ObjectStreamIn.sReadObject("Assets/Human/dead_pose1.tof", animation))
			FatalError("Could not open animation");
		animation.sample(0.0f, ragdoll_pose);
}else{
		RagdollRef temp_ragdoll = ragdoll_settings.createRagdoll(0, 0, mPhysicsSystem).toRef();
		temp_ragdoll.getPose(ragdoll_pose);
		ragdoll_pose.calculateJointStates();
} // JPH_OBJECT_STREAM
	}
	ragdoll_pose.setRootOffset(new RVec3(0, 30, 0));
	ragdoll_pose.calculateJointMatrices();

	// Create ragdoll
	mRagdoll = ragdoll_settings.createRagdoll(1, 0, mPhysicsSystem).toRef();
	mRagdoll.setPose(ragdoll_pose);
	mRagdoll.addToPhysicsSystem(EActivation.Activate);

	// Create kinematic body
	BodyCreationSettings kinematic_settings=new BodyCreationSettings(new BoxShape(new Vec3(0.25f, 0.5f, 1.0f)),new RVec3(-20, 10, 0), Quat.sIdentity(), EMotionType.Kinematic, Layers.MOVING);
	Body kinematic = mBodyInterface.createBody(kinematic_settings);
	mKinematicBodyID = kinematic.getId();
	mBodyInterface.addBody(kinematic.getId(), EActivation.Activate);
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	// Update time
	mTime += inParams.mDeltaTime;

	// Move kinematic body
	RVec3 kinematic_pos =new RVec3(-20.0f * cos(mTime), 10, 0);
	mBodyInterface.moveKinematic(mKinematicBodyID, kinematic_pos, Quat.sIdentity(), inParams.mDeltaTime);

	// Draw if body is in sensor
	ConstColor sensor_color[] = { Color.sRed, Color.sGreen, Color.sBlue, Color.sPurple };
	for (int sensor = 0; sensor < NumSensors; ++sensor)
		{
			BodyAndCount body_and_count = mBodiesInSensor.get(sensor);
			AaBox bounds = mBodyInterface.getTransformedShape(body_and_count.mBodyID).getWorldSpaceBounds();
			bounds.expandBy(Vec3.sReplicate(0.01f * sensor));
			mDebugRenderer.drawWireBox(bounds, sensor_color[sensor]);
		}

	// Apply forces to dynamic bodies in sensor
	mMutex. lock();

	RVec3 center=new RVec3(0, 10, 0);
	float centrifugal_force = 10.0f;
	Vec3 gravity = mPhysicsSystem.getGravity();

	for (int oneTime=0; oneTime == 0;++oneTime)
	{
		BodyAndCount body_and_count = mBodiesInSensor.get(StaticAttractor);
		BodyLockWrite body_lock=new BodyLockWrite(mPhysicsSystem.getBodyLockInterface(), body_and_count.mBodyID);
		if (body_lock.succeeded())
		{
			Body body = body_lock.getBody();
			if (body.isKinematic())
				continue;

			// Calculate centrifugal acceleration
			Vec3 acceleration =new Vec3(minus(center , body.getPosition()));
			float length = acceleration.length();
			if (length > 0.0f)
				starEquals(acceleration , centrifugal_force / length);
			else
				acceleration = Vec3.sZero();

			// Draw acceleration
			mDebugRenderer.drawArrow(body.getPosition(), plus(body.getPosition() , acceleration), Color.sGreen, 0.1f);

			// Cancel gravity
			minusEquals(acceleration , gravity);

			// Apply as force
			body.addForce(slash(acceleration , body.getMotionProperties().getInverseMass()));
		}
                body_lock.releaseLock();
	}
        mMutex.unlock();
}

public void OnContactAdded(ConstBody  inBody1, ConstBody  inBody2, ConstContactManifold  inManifold, ContactSettings ioSettings)
{
	for (int sensor = 0; sensor < NumSensors; ++sensor)
	{
		int sensor_id = mSensorID[sensor];

		// Check which body is the sensor
		int body_id;
		if (inBody1.getId() == sensor_id)
			body_id = inBody2.getId();
		else if (inBody2.getId() == sensor_id)
			body_id = inBody1.getId();
		else
			continue;

		mMutex. lock();

		// Add to list and make sure that the list remains sorted for determinism (contacts can be added from multiple threads)
		BodyAndCount body_and_count=new BodyAndCount( body_id, 1 );
		List<BodyAndCount> bodies_in_sensor = new ArrayList<BodyAndCount>();bodies_in_sensor.add(mBodiesInSensor.get(sensor));
		BodyAndCount b = null;for(BodyAndCount bb:mBodiesInSensor){ if(bb.mBodyID==body_id) b=bb;}
		if (b != null && b.mBodyID == body_id)
		{
			// This is the right body, increment reference
			b.mCount++;
			mMutex.unlock();
			return;
		}
		bodies_in_sensor.add(body_and_count);Collections.sort(bodies_in_sensor);
		mMutex.unlock();
	}
}

public void OnContactRemoved( ConstSubShapeIdPair inSubShapePair)
{
	for (int sensor = 0; sensor < NumSensors; ++sensor)
	{
		int sensor_id = mSensorID[sensor];

		// Check which body is the sensor
		int body_id;
		if (inSubShapePair.getBody1Id() == sensor_id)
			body_id = inSubShapePair.getBody2Id();
		else if (inSubShapePair.getBody2Id() == sensor_id)
			body_id = inSubShapePair.getBody1Id();
		else
			continue;

		mMutex .lock();

		// Remove from list
		BodyAndCount body_and_count=new BodyAndCount( body_id, 1 );
		List<BodyAndCount> bodies_in_sensor = List.of(mBodiesInSensor.get(sensor));
                BodyAndCount b = null;for(BodyAndCount bb:mBodiesInSensor){ if(bb.mBodyID==body_id) b=bb;}
		if (b != null && b.mBodyID == body_id)
		{
			// This is the right body, increment reference
			assert(b.mCount > 0);
			b.mCount--;

			// When last reference remove from the list
			if (b.mCount == 0)
				bodies_in_sensor.remove(b);
			mMutex.unlock();
			return;
		}
		assert false: "Body pair not found";
		mMutex.unlock();
	}
}

/*
void SensorTest::SaveState(StateRecorder &inStream) const
{
	inStream.Write(mTime);
	for (const BodiesInSensor &b : mBodiesInSensor)
		inStream.Write(b);
}

void SensorTest::RestoreState(StateRecorder &inStream)
{
	inStream.Read(mTime);
	for (BodiesInSensor &b : mBodiesInSensor)
		inStream.Read(b);
}*/
}