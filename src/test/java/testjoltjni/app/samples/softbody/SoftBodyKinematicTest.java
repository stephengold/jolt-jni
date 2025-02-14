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
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt Physics soft-body kinematic test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/SoftBody/SoftBodyKinematicTest.cpp
 */
public class SoftBodyKinematicTest extends Test{
BodyId mSphereID=new BodyId();

public void Initialize()
{
	// Floor
	CreateFloor();

	// A sphere
	SoftBodySharedSettingsRef sphere_settings = SoftBodyCreator.CreateSphere();
	sphere_settings.getPtr().getVertex(0).setInvMass ( 0.0f);
	sphere_settings.getPtr().getVertex(0).setVelocity (new Float3(0, 0, 5));
	SoftBodyCreationSettings sphere=new SoftBodyCreationSettings(sphere_settings,new RVec3(0, 5, 0), Quat.sIdentity(), Layers.MOVING);
	sphere.setPressure ( 2000.0f);
	mSphereID = mBodyInterface.createAndAddSoftBody(sphere, EActivation.Activate);
}

public void PrePhysicsUpdate(PreUpdateParams inParams)
{
	// Update the velocity of the first vertex
	BodyLockWrite body_lock=new BodyLockWrite(mPhysicsSystem.getBodyLockInterface(), mSphereID);
	if (body_lock.succeeded())
	{
		Body body = body_lock.getBody();
		SoftBodyMotionProperties mp = (SoftBodyMotionProperties )(body.getMotionProperties());
		RVec3 com = body.getCenterOfMassPosition();
		if (com.z() >= 10.0f)
			mp.getVertex(0).setVelocity (new Vec3(0, 0, -5));
		else if (com.z() <= -10.0f)
			mp.getVertex(0).setVelocity (new Vec3(0, 0, 5));
	}
        body_lock.releaseLock();
}
}
