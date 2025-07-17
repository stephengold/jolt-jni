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
/**
 * A line-for-line Java translation of the Jolt-Physics scaled plane-shape test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/ScaledShapes/ScaledPlaneShapeTest.cpp
 */
public class ScaledPlaneShapeTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	ConstShapeSettings plane_shape = new PlaneShapeSettings(new Plane(new Vec3(0.1f, 1.0f, 0.1f).normalized(), -0.5f), null, 5.0f);

	// Original shape
	mBodyInterface.createAndAddBody(new BodyCreationSettings(plane_shape,new RVec3(-60, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Uniformly scaled shape < 1
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(plane_shape, Vec3.sReplicate(0.5f)),new RVec3(-40, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Uniformly scaled shape > 1
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(plane_shape, Vec3.sReplicate(1.5f)),new RVec3(-20, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Non-uniform scaled shape
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(plane_shape,new Vec3(0.5f, 1.0f, 1.5f)),new RVec3(0, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Flipped in 2 axis
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(plane_shape,new Vec3(-0.5f, 1.0f, -1.5f)),new RVec3(20, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Inside out
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(plane_shape,new Vec3(-0.5f, 1.0f, 1.5f)),new RVec3(40, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Upside down
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(plane_shape,new Vec3(0.5f, -1.0f, 1.5f)),new RVec3(60, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Create a number of balls above the planes
	ConstShape sphere_shape = new SphereShape(0.2f);
	ConstShape box_shape = new BoxShape(new Vec3(0.2f, 0.2f, 0.4f), 0.01f);
	for (int i = 0; i < 7; ++i)
		for (int j = 0; j < 5; ++j)
			mBodyInterface.createAndAddBody(new BodyCreationSettings(((j & 1)!=0)? box_shape : sphere_shape,new RVec3(-60.0f + 20.0f * i, 15.0f + 0.5f * j, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
}
}
