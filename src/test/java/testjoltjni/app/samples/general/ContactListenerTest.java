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
 * A line-for-line Java translation of the Jolt Physics contact-listener test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/ContactListenerTest.cpp
 */
public class ContactListenerTest extends Test{
Body mBody[]=new Body[5];
class PredictedVelocity{int mBodyID;Vec3 mLinearVelocity=new Vec3(),mAngularVelocity=new Vec3();
PredictedVelocity(int id,Vec3Arg lv,Vec3 av){mBodyID=id;mLinearVelocity.set(lv);mAngularVelocity.set(av);}}
Mutex mPredictedVelocitiesMutex=new Mutex();
List<PredictedVelocity> mPredictedVelocities=new ArrayList<>();

public void Initialize()
{
	// Floor
	CreateFloor();

	ShapeRefC box_shape = new BoxShape(new Vec3(0.5f, 1.0f, 2.0f)).toRefC();

	// Dynamic body 1, this body will have restitution 1 for new contacts and restitution 0 for persisting contacts
	Body body1 = mBodyInterface.createBody(new BodyCreationSettings(box_shape,new RVec3(0, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
	body1.setAllowSleeping(false);
	mBodyInterface.addBody(body1.getId(), EActivation.Activate);

	// Dynamic body 2
	Body body2 = mBodyInterface.createBody(new BodyCreationSettings(box_shape,new RVec3(5, 10, 0), Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI), EMotionType.Dynamic, Layers.MOVING));
	body2.setAllowSleeping(false);
	mBodyInterface.addBody(body2.getId(), EActivation.Activate);

	// Dynamic body 3
	Body body3 = mBodyInterface.createBody(new BodyCreationSettings(new SphereShape(2.0f),new RVec3(10, 10, 0), Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI), EMotionType.Dynamic, Layers.MOVING));
	body3.setAllowSleeping(false);
	mBodyInterface.addBody(body3.getId(), EActivation.Activate);

	// Dynamic body 4
	StaticCompoundShapeSettings compound_shape = new StaticCompoundShapeSettings();
	compound_shape.addShape(Vec3.sZero(), Quat.sIdentity(), new CapsuleShape(5, 1));
	compound_shape.addShape(new Vec3(0, -5, 0), Quat.sIdentity(), new SphereShape(2));
	compound_shape.addShape(new Vec3(0, 5, 0), Quat.sIdentity(), new SphereShape(2));
	Body body4 = mBodyInterface.createBody(new BodyCreationSettings(compound_shape,new RVec3(15, 10, 0), Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI), EMotionType.Dynamic, Layers.MOVING));
	body4.setAllowSleeping(false);
	mBodyInterface.addBody(body4.getId(), EActivation.Activate);

	// Dynamic body 5, a cube with a bigger cube surrounding it that acts as a sensor
	StaticCompoundShapeSettings compound_shape2 = new StaticCompoundShapeSettings();
	compound_shape2.addShape(Vec3.sZero(), Quat.sIdentity(), new BoxShape(Vec3.sReplicate(1)));
	compound_shape2.addShape(Vec3.sZero(), Quat.sIdentity(), new BoxShape(Vec3.sReplicate(2))); // This will become a sensor in the contact callback
	BodyCreationSettings bcs5=new BodyCreationSettings(compound_shape2,new RVec3(20, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
	bcs5.setUseManifoldReduction ( false); // Needed in order to prevent the physics system from combining contacts between sensor and non-sensor sub shapes
	Body body5 = mBodyInterface.createBody(bcs5);
	body5.setAllowSleeping(false);
	mBodyInterface.addBody(body5.getId(), EActivation.Activate);

	// Store bodies for later use
	mBody[0] = body1;
	mBody[1] = body2;
	mBody[2] = body3;
	mBody[3] = body4;
	mBody[4] = body5;
}

public void PostPhysicsUpdate(float inDeltaTime)
{
	// Check that predicted velocities match actual velocities
	mPredictedVelocitiesMutex.lock();
	for ( PredictedVelocity v : mPredictedVelocities)
	{
		BodyLockRead body_lock=new BodyLockRead(mPhysicsSystem.getBodyLockInterface(), v.mBodyID);
		if (body_lock.succeeded())
		{
			ConstBody body = body_lock.getBody();
			Vec3 linear_velocity = body.getLinearVelocity();
			Vec3 angular_velocity = body.getAngularVelocity();
			float diff_v = minus(v.mLinearVelocity , linear_velocity).length();
			float diff_w = minus(v.mAngularVelocity , angular_velocity).length();
			if (diff_v > 1.0e-3f || diff_w > 1.0e-3f)
				Trace("Mispredicted collision for body: %08x, v=%s, w=%s, predicted_v=%s, predicted_w=%s, diff_v=%f, diff_w=%f",
					body.getId(),
					(linear_velocity), (angular_velocity),
					(v.mLinearVelocity), (v.mAngularVelocity),
					(double)diff_v,
					(double)diff_w);
		}
                body_lock.releaseLock();
	}
	mPredictedVelocities.clear();
        mPredictedVelocitiesMutex.unlock();
}

public ValidateResult OnContactValidate(ConstBody inBody1, ConstBody inBody2, RVec3Arg inBaseOffset, CollideShapeResult inCollisionResult)
{
	// Body 1 and 2 should never collide
	return ((inBody1 == mBody[0] && inBody2 == mBody[1]) || (inBody1 == mBody[1] && inBody2 == mBody[0]))? ValidateResult.RejectAllContactsForThisBodyPair : ValidateResult.AcceptAllContactsForThisBodyPair;
}

void MakeBody5PartialSensor(ConstBody inBody1, ConstBody inBody2, ConstContactManifold inManifold, ContactSettings ioSettings)
{
	// Make the 2nd shape of body 5 a sensor
	int body5_subshape_1 = ((CompoundShape)mBody[4].getShape()).getSubShapeIdFromIndex(1,new SubShapeIdCreator()).getId();
	if ((inBody1 == mBody[4] && inManifold.getSubShapeId1() == body5_subshape_1)
		|| (inBody2 == mBody[4] && inManifold.getSubShapeId2() == body5_subshape_1))
	{
		Trace("Sensor contact detected between body %08x and body %08x", inBody1.getId(), inBody2.getId());
		ioSettings.setIsSensor ( true);
	}
}

public void OnContactAdded(ConstBody inBody1, ConstBody inBody2, ConstContactManifold inManifold, ContactSettings ioSettings)
{
	// Make body 1 bounce only when a new contact point is added but not when it is persisted (its restitution is normally 0)
	if (inBody1 == mBody[0] || inBody2 == mBody[0])
	{
		assert(ioSettings.getCombinedRestitution() == 0.0f);
		ioSettings.setCombinedRestitution ( 1.0f);
	}

	MakeBody5PartialSensor(inBody1, inBody2, inManifold, ioSettings);

	// Estimate the contact impulses.
	CollisionEstimationResult result=new CollisionEstimationResult();
	estimateCollisionResponse(inBody1, inBody2, inManifold, result, ioSettings.getCombinedFriction(), ioSettings.getCombinedRestitution());

	// Trace the result
	String impulses_str="";
	for (Impulse impulse : result.getImpulses())
		impulses_str += String.format("(%f, %f, %f) ", (double)impulse.getContactImpulse(), (double)impulse.getFrictionImpulse1(), (double)impulse.getFrictionImpulse2());

	Trace("Estimated velocity after collision, body1: %08x, v=%s, w=%s, body2: %08x, v=%s, w=%s, impulses: %s",
		inBody1.getId(), (result.getLinearVelocity1()), (result.getAngularVelocity1()),
		inBody2.getId(), (result.getLinearVelocity2()), (result.getAngularVelocity2()),
		impulses_str);

	// Log predicted velocities
	{
		mPredictedVelocitiesMutex.lock();
		mPredictedVelocities.add(new PredictedVelocity(inBody1.getId(), result.getLinearVelocity1(), result.getAngularVelocity1() ));
		mPredictedVelocities.add(new PredictedVelocity(inBody2.getId(), result.getLinearVelocity2(), result.getAngularVelocity2() ));
		mPredictedVelocitiesMutex.unlock();
	}
}

public void OnContactPersisted(ConstBody inBody1, ConstBody inBody2,  ConstContactManifold inManifold, ContactSettings ioSettings)
{
	MakeBody5PartialSensor(inBody1, inBody2, inManifold, ioSettings);
}
}
