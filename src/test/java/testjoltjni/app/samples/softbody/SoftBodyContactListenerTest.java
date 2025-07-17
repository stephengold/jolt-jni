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
package testjoltjni.app.samples.softbody;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.readonly.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt-Physics soft-body
 * contact-listener test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/SoftBody/SoftBodyContactListenerTest.cpp
 */
public class SoftBodyContactListenerTest extends Test{
int mOtherBodyID=cInvalidBodyId,mSoftBodyID=cInvalidBodyId;
float mTime;
int mCycle;

void Initialize()
{
	// Install contact listener for soft bodies
	mPhysicsSystem.setSoftBodyContactListener(new CustomSoftBodyContactListener(){
            public void onSoftBodyContactAdded(long bodyVa,long manifoldVa){OnSoftBodyContactAdded(new Body(bodyVa),new SoftBodyManifold(manifoldVa));}
            public int onSoftBodyContactValidate(long softBodyVa,long otherBodyVa,long settingsVa){return OnSoftBodyContactValidate(new Body(softBodyVa),new Body(otherBodyVa),new SoftBodyContactSettings(settingsVa)).ordinal();}
        });

	// Floor
	CreateFloor();

	// Start the 1st cycle
	StartCycle();
}

void UpdateLabel()
{
	// Draw current state
	String  cycle_names[] = { "Accept contact", "Sphere 10x mass", "Cloth 10x mass", "Sphere infinite mass", "Cloth infinite mass", "Sensor contact", "Reject contact", "Kinematic Sphere", "Kinematic Sphere, cloth infinite mass", "Kinematic sphere, sensor contact", "Kinematic Sphere, reject contact" };
	mDebugRenderer.drawText3D(mBodyInterface.getPosition(mOtherBodyID), cycle_names[mCycle], Color.sWhite, 1.0f);
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	mTime += inParams.mDeltaTime;
	if (mTime > 2.5f)
	{
		// Next cycle
		mCycle = (mCycle + 1) % 10;
		mTime = 0.0f;

		// Remove the old scene
		mBodyInterface.removeBody(mOtherBodyID);
		mBodyInterface.destroyBody(mOtherBodyID);
		mBodyInterface.removeBody(mSoftBodyID);
		mBodyInterface.destroyBody(mSoftBodyID);

		// Start the new
		StartCycle();
	}

	UpdateLabel();
}

void StartCycle()
{
	// Create the cloth
	SoftBodySharedSettingsRef cloth_settings = SoftBodyCreator.CreateClothWithFixatedCorners(15, 15, 0.75f);

	// Create cloth that's fixated at the corners
	SoftBodyCreationSettings cloth=new SoftBodyCreationSettings(cloth_settings,new RVec3(0, 5, 0), Quat.sRotation(Vec3.sAxisY(), 0.25f * JPH_PI), Layers.MOVING);
	cloth.setUpdatePosition ( false); // Don't update the position of the cloth as it is fixed to the world
	cloth.setMakeRotationIdentity ( false); // Test explicitly checks if soft bodies with a rotation collide with shapes properly
	mSoftBodyID = mBodyInterface.createAndAddSoftBody(cloth, EActivation.Activate);

	// If we want a kinematic sphere
	boolean kinematic = mCycle > 6;

	// Create sphere
	BodyCreationSettings bcs=new BodyCreationSettings(new SphereShape(1.0f),new RVec3(0, 7, 0), Quat.sIdentity(), kinematic? EMotionType.Kinematic : EMotionType.Dynamic, Layers.MOVING);
	bcs.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
	bcs.getMassPropertiesOverride().setMass ( 100.0f);
	if (kinematic)
		bcs.setLinearVelocity (new Vec3(0, -2.5f, 0));
	mOtherBodyID = mBodyInterface.createAndAddBody(bcs, EActivation.Activate);

	UpdateLabel();
}

SoftBodyValidateResult OnSoftBodyContactValidate( ConstBody inSoftBody, ConstBody  inOtherBody, SoftBodyContactSettings ioSettings)
{
	switch (mCycle)
	{
	case 0:
		// Normal
		return SoftBodyValidateResult.AcceptContact;

	case 1:
		// Makes the sphere 10x as heavy
		ioSettings.setInvMassScale2 ( 0.1f);
		ioSettings.setInvInertiaScale2 ( 0.1f);
		return SoftBodyValidateResult.AcceptContact;

	case 2:
		// Makes the cloth 10x as heavy
		ioSettings.setInvMassScale1 ( 0.1f);
		return SoftBodyValidateResult.AcceptContact;

	case 3:
		// Makes the sphere have infinite mass
		ioSettings.setInvMassScale2 ( 0.0f);
		ioSettings.setInvInertiaScale2 ( 0.0f);
		return SoftBodyValidateResult.AcceptContact;

	case 4:
		// Makes the cloth have infinite mass
		ioSettings.setInvMassScale1 ( 0.0f);
		return SoftBodyValidateResult.AcceptContact;

	case 5:
		// Sensor contact
		ioSettings.setIsSensor ( true);
		return SoftBodyValidateResult.AcceptContact;

	case 6:
		// No contacts
		return SoftBodyValidateResult.RejectContact;

	case 7:
		// Kinematic sphere
		return SoftBodyValidateResult.AcceptContact;

	case 8:
		// Kinematic sphere, cloth infinite mass
		ioSettings.setInvMassScale1 ( 0.0f);
		return SoftBodyValidateResult.AcceptContact;

	case 9:
		// Kinematic sphere, sensor contact
		ioSettings.setIsSensor ( true);
		return SoftBodyValidateResult.AcceptContact;

	default:
		// No contacts
		return SoftBodyValidateResult.RejectContact;
	}
}

void OnSoftBodyContactAdded(ConstBody  inSoftBody,  ConstSoftBodyManifold inManifold)
{
	// Draw contacts
	RMat44 com = inSoftBody.getCenterOfMassTransform();
	for (ConstSoftBodyVertex vertex : inManifold.getVertices())
		if (inManifold.hasContact(vertex))
		{
			RVec3 position = star(com , inManifold.getLocalContactPoint(vertex));
			Vec3 normal = inManifold.getContactNormal(vertex);
			mDebugRenderer.drawMarker(position, Color.sRed, 0.1f);
			mDebugRenderer.drawArrow(position, plus(position , normal), Color.sGreen, 0.1f);
		}
}
}
