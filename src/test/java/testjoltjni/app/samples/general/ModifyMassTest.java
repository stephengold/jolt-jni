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
package testjoltjni.app.samples.general;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.readonly.*;
import static com.github.stephengold.joltjni.Jolt.*;
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt-Physics modify-mass test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/ModifyMassTest.cpp
 */
public class ModifyMassTest extends Test{
float mTime;
int[] mBodies=new int[]{cInvalidBodyId,cInvalidBodyId};

static final float cMaxHeight = 4.0f;


void ResetBodies(int inCycle)
{
	mBodyInterface.setPositionAndRotation(mBodies[0],new RVec3(-5, 5, 0), Quat.sIdentity(), EActivation.Activate);
	mBodyInterface.setLinearAndAngularVelocity(mBodies[0],new Vec3(10, 0, 0), Vec3.sZero());
	mBodyInterface.setUserData(mBodies[0], inCycle << 1);

	mBodyInterface.setPositionAndRotation(mBodies[1],new RVec3(5, 5, 0), Quat.sIdentity(), EActivation.Activate);
	mBodyInterface.setLinearAndAngularVelocity(mBodies[1],new Vec3(-10, 0, 0), Vec3.sZero());
	mBodyInterface.setUserData(mBodies[1], (inCycle << 1) + 1);
}

public void Initialize()
{
	// Floor
	CreateFloor();

	// Create two spheres on a collision course
	BodyCreationSettings bcs=new BodyCreationSettings(new SphereShape(1.0f), RVec3.sZero(), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
	bcs.setRestitution ( 1.0f);
	mBodies[0] = mBodyInterface.createAndAddBody(bcs, EActivation.Activate);
	mBodies[1] = mBodyInterface.createAndAddBody(bcs, EActivation.Activate);
	ResetBodies(0);
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	final float cTimeBetweenTests = 2.0f;

	int old_cycle = (int)(mTime / cTimeBetweenTests);
	mTime += inParams.mDeltaTime;
	int new_cycle = (int)(mTime / cTimeBetweenTests);
	if (old_cycle != new_cycle)
		ResetBodies(new_cycle);
}

public void PostPhysicsUpdate(float inDeltaTime)
{
	// Draw the mass scale
	for (int id : mBodies)
	{
		BodyLockRead body_lock=new BodyLockRead(mPhysicsSystem.getBodyLockInterface(), id);
		if (body_lock.succeeded())
		{
			ConstBody body = body_lock.getBody();
			DebugRenderer.sInstance().drawText3D(body.getPosition(), String.format("Inv mass scale: %.1f\nVelocity X: %.1f", (double)sGetInvMassScale(body), (double)body.getLinearVelocity().getX()), Color.sWhite);
		}
                body_lock.releaseLock();
	}
}

float sGetInvMassScale(ConstBody inBody)
{
	long ud = inBody.getUserData();
	int index = (int)((ud & 1) != 0? (ud >> 1) : (ud >> 3)) & 0b11;
	float mass_overrides[] = { 1.0f, 0.0f, 0.5f, 2.0f };
	return mass_overrides[index];
}

void OnContactAdded(ConstBody inBody1, ConstBody inBody2, ConstContactManifold inManifold, ContactSettings ioSettings)
{
	// We're only concerned with dynamic bodies (floor gets normal collision response)
	if (!inBody1.isDynamic() || !inBody2.isDynamic())
		return;

	// Override the mass of body 1
	float scale1 = sGetInvMassScale(inBody1);
	ioSettings.setInvMassScale1 ( scale1);
	ioSettings.setInvInertiaScale1 ( scale1);

	// Override the mass of body 2
	float scale2 = sGetInvMassScale(inBody2);
	ioSettings.setInvMassScale2 ( scale2);
	ioSettings.setInvInertiaScale2 ( scale2);
}

void OnContactPersisted(ConstBody inBody1, ConstBody inBody2, ConstContactManifold inManifold, ContactSettings ioSettings)
{
	OnContactAdded(inBody1, inBody2, inManifold, ioSettings);
}

protected void SaveState(StateRecorder inStream)
{
	super.SaveState(inStream);

	inStream.write(mTime);
}

protected void RestoreState(StateRecorder inStream)
{
	super.RestoreState(inStream);

	mTime=inStream.readFloat(mTime);
}
}
