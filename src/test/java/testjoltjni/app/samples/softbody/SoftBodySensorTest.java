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
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt-Physics soft-body sensor test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/SoftBody/SoftBodySensorTest.cpp
 */
public class SoftBodySensorTest extends Test{

public void Initialize()
{
	// Install contact listener for soft bodies
	mPhysicsSystem.setSoftBodyContactListener(new CustomSoftBodyContactListener(){
            public void onSoftBodyContactAdded(long bodyVa,long manifoldVa) {
                SoftBodySensorTest.this.OnSoftBodyContactAdded(new Body(bodyVa), new SoftBodyManifold(manifoldVa));
            }
            public int onSoftBodyContactValidate(long softBodyVa,long otherBodyVa,long settingsVa){return SoftBodyValidateResult.AcceptContact.ordinal();}
        });

	// Floor
	CreateFloor();

	// Create cloth that's fixated at the corners
	SoftBodyCreationSettings cloth=new SoftBodyCreationSettings(SoftBodyCreator.CreateClothWithFixatedCorners(),new RVec3(0, 10.0f, 0), Quat.sIdentity(), Layers.MOVING);
	mBodyInterface.createAndAddSoftBody(cloth, EActivation.Activate);

	// Some sensors to detect the cloth
	BodyCreationSettings cylinder_sensor=new BodyCreationSettings(new TaperedCylinderShapeSettings(4.0f, 1.0f, 2.0f),new RVec3(0, 6, 0), Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI), EMotionType.Static, Layers.SENSOR);
	cylinder_sensor.setIsSensor ( true);
	mBodyInterface.createAndAddBody(cylinder_sensor, EActivation.DontActivate);

	BodyCreationSettings sphere_sensor=new BodyCreationSettings(new SphereShape(4.0f),new RVec3(4, 5, 0), Quat.sIdentity(), EMotionType.Static, Layers.SENSOR);
	sphere_sensor.setIsSensor ( true);
	mBodyInterface.createAndAddBody(sphere_sensor, EActivation.DontActivate);

	// Sphere that falls on the cloth to check that we don't ignore this collision
	BodyCreationSettings bcs=new BodyCreationSettings(new SphereShape(1.0f),new RVec3(0, 15, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
	bcs.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
	bcs.getMassPropertiesOverride().setMass ( 500.0f);
	mBodyInterface.createAndAddBody(bcs, EActivation.Activate);
}

void OnSoftBodyContactAdded(ConstBody inSoftBody, SoftBodyManifold inManifold)
{
	// Draw the vertices that are in contact
	RMat44 com = inSoftBody.getCenterOfMassTransform();
	for (ConstSoftBodyVertex v : inManifold.getVertices())
		if (inManifold.hasContact(v))
			DebugRenderer.sInstance().drawMarker(star(com , v.getPosition()), Color.sGreen, 0.1f);

	// Draw the sensors that are in contact with the soft body
	for (int i = 0; i < inManifold.getNumSensorContacts(); ++i)
	{
		int sensor_id = inManifold.getSensorContactBodyId(i);
		BodyLockRead lock=new BodyLockRead(mPhysicsSystem.getBodyLockInterfaceNoLock(), sensor_id); // Can't lock in a callback
		if (lock.succeededAndIsInBroadPhase())
		{
			ConstAaBox bounds = lock.getBody().getWorldSpaceBounds();
			DebugRenderer.sInstance().drawWireBox(bounds, Color.sGreen);
		}
                lock.releaseLock();
	}
}
}