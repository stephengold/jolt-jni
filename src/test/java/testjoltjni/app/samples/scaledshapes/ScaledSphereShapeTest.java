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
 * A line-for-line Java translation of the Jolt-Physics scaled sphere-shape
 * test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/ScaledShapes/ScaledSphereShapeTest.cpp
 */
public class ScaledSphereShapeTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	// Create sphere
	ConstShape sphere_shape = new SphereShape(2.0f);

	// Original shape
	mBodyInterface.createAndAddBody(new BodyCreationSettings(sphere_shape,new RVec3(-20, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Uniformly scaled shape < 1
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShape(sphere_shape, Vec3.sReplicate(0.25f)),new RVec3(-10, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Uniformly scaled shape > 1
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShape(sphere_shape, Vec3.sReplicate(2.0f)),new RVec3(0, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Flipped in 2 axis
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShape(sphere_shape,new Vec3(-0.25f, 0.25f, -0.25f)),new RVec3(10, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Inside out
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShape(sphere_shape, Vec3.sReplicate(-0.25f)),new RVec3(20, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
}
}
