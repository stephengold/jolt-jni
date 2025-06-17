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
package testjoltjni.app.samples.shapes;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt-Physics offset center-of-mass 
 * shape test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Shapes/OffsetCenterOfMassShapeTest.cpp
 */
public class OffsetCenterOfMassShapeTest extends Test{

public void Initialize()
{
	// Floor
	Body floor = CreateFloor();
	floor.setFriction(1.0f);

	ShapeSettings sphere = new SphereShapeSettings(1.0f);
	OffsetCenterOfMassShapeSettings left = new OffsetCenterOfMassShapeSettings(new Vec3(-1, 0, 0), sphere);
	OffsetCenterOfMassShapeSettings right = new OffsetCenterOfMassShapeSettings(new Vec3(1, 0, 0), sphere);

	// Sphere with center of mass moved to the left side
	Body body_left = mBodyInterface.createBody(new BodyCreationSettings(left,new RVec3(-5, 5, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
	body_left.setFriction(1.0f);
	mBodyInterface.addBody(body_left.getId(), EActivation.Activate);

	// Sphere with center of mass centered
	Body body_center = mBodyInterface.createBody(new BodyCreationSettings(sphere,new RVec3(0, 5, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
	body_center.setFriction(1.0f);
	mBodyInterface.addBody(body_center.getId(), EActivation.Activate);

	// Sphere with center of mass moved to the right side
	Body body_right = mBodyInterface.createBody(new BodyCreationSettings(right,new RVec3(5, 5, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
	body_right.setFriction(1.0f);
	mBodyInterface.addBody(body_right.getId(), EActivation.Activate);

	// Create body and apply a large angular impulse so see that it spins around the COM
	BodyCreationSettings bcs=new BodyCreationSettings(new OffsetCenterOfMassShapeSettings(new Vec3(-3, 0, 0), new SphereShapeSettings(1.0f)),new RVec3(-5, 5, 10), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
	bcs.setGravityFactor ( 0.0f);
	bcs.setLinearDamping ( 0.0f);
	bcs.setAngularDamping ( 0.0f);
	Body body_rotating1 = mBodyInterface.createBody(bcs);
	mBodyInterface.addBody(body_rotating1.getId(), EActivation.Activate);
	body_rotating1.addAngularImpulse(new Vec3(0, 1.0e6f, 0));

	// Create the same body but this time apply a torque
	bcs.setPosition (new RVec3(5, 5, 10));
	Body body_rotating2 = mBodyInterface.createBody(bcs);
	mBodyInterface.addBody(body_rotating2.getId(), EActivation.Activate);
	body_rotating2.addTorque(new Vec3(0, 1.0e6f * 60.0f, 0)); // Assuming physics sim is at 60Hz here, otherwise the bodies won't rotate with the same speed
}
}