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
package testjoltjni.app.samples.scaledshapes;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.readonly.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
/**
 * A line-for-line Java translation of the Jolt-Physics scaled offset center-of-mass shape test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/ScaledShapes/ScaledOffsetCenterOfMassShapeTest.cpp
 */
public class ScaledOffsetCenterOfMassShapeTest extends Test{

public void Initialize()
{
	// Floor
	Body floor = CreateFloor();
	floor.setFriction(1.0f);

	ShapeSettings cylinder = new CylinderShapeSettings(1.0f, 0.1f);
	OffsetCenterOfMassShapeSettings top = new OffsetCenterOfMassShapeSettings(new Vec3(0, 1, 0), cylinder);
	OffsetCenterOfMassShapeSettings bottom = new OffsetCenterOfMassShapeSettings(new Vec3(0, -1, 0), cylinder);

	// Initial body rotation
	Quat rotation = Quat.sRotation(Vec3.sAxisZ(), 0.4f * JPH_PI);

	// Cylinder with center of mass moved to the top side
	Body body_top = mBodyInterface.createBody(new BodyCreationSettings(new ScaledShapeSettings(top,new Vec3(2.0f, 1.0f, 2.0f)),new RVec3(-5, 5, 0), rotation, EMotionType.Dynamic, Layers.MOVING));
	body_top.setFriction(1.0f);
	mBodyInterface.addBody(body_top.getId(), EActivation.Activate);

	// Cylinder with center of mass centered
	Body body_center = mBodyInterface.createBody(new BodyCreationSettings(new ScaledShapeSettings(cylinder,new Vec3(2.0f, 1.0f, 2.0f)),new RVec3(0, 5, 0), rotation, EMotionType.Dynamic, Layers.MOVING));
	body_center.setFriction(1.0f);
	mBodyInterface.addBody(body_center.getId(), EActivation.Activate);

	// Cylinder with center of mass moved to the bottom side
	Body body_bottom = mBodyInterface.createBody(new BodyCreationSettings(new ScaledShapeSettings(bottom,new Vec3(2.0f, 1.0f, 2.0f)),new RVec3(5, 5, 0), rotation, EMotionType.Dynamic, Layers.MOVING));
	body_bottom.setFriction(1.0f);
	mBodyInterface.addBody(body_bottom.getId(), EActivation.Activate);

	// Shape that is scaled before the offset center of mass offset is applied
	ConstShape pre_scaled =new OffsetCenterOfMassShapeSettings(new Vec3(0, 0, 5.0f), new ScaledShape(new SphereShape(1.0f), Vec3.sReplicate(2.0f))).create().get();
	mBodyInterface.createAndAddBody(new BodyCreationSettings(pre_scaled,new RVec3(0, 5, -15), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Shape that is scaled after the offset center of mass offset is applied
	ConstShape post_scaled = new ScaledShape(new OffsetCenterOfMassShapeSettings(new Vec3(0, 0, 5.0f), new SphereShape(1.0f)).create().get(), Vec3.sReplicate(2.0f));
	mBodyInterface.createAndAddBody(new BodyCreationSettings(post_scaled,new RVec3(5, 5, -15), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
}
}
