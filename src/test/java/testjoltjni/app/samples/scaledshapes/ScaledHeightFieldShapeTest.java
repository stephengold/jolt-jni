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
 * A line-for-line Java translation of the Jolt-Physics scaled height-field shape test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/ScaledShapes/ScaledHeightFieldShapeTest.cpp
 */
public class ScaledHeightFieldShapeTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	final int n = 64;
	final float cell_size = 0.25f;
	final float max_height = 4.0f;

	// Create height samples
	float[] heights=new float[n * n];
	for (int y = 0; y < n; ++y)
		for (int x = 0; x < n; ++x)
			heights[y * n + x] = max_height * perlinNoise3(((float)x) * 2.0f / n, 0, ((float)y) * 2.0f / n, 256, 256, 256);

	// Create 'wall' around height field
	for (int x = 0; x < n; ++x)
	{
		heights[x] += 2.0f;
		heights[x + n * (n - 1)] += 2.0f;
	}

	for (int y = 1; y < n - 1; ++y)
	{
		heights[n * y] += 2.0f;
		heights[n - 1 + n * y] += 2.0f;
	}

	// Create height field
	ConstShapeSettings height_field = new HeightFieldShapeSettings(heights,new Vec3(-0.5f * cell_size * n, 0.0f, -0.5f * cell_size * n),new Vec3(cell_size, 1.0f, cell_size), n);

	// Original shape
	mBodyInterface.createAndAddBody(new BodyCreationSettings(height_field,new RVec3(-60, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Uniformly scaled shape < 1
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(height_field, Vec3.sReplicate(0.5f)),new RVec3(-40, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Uniformly scaled shape > 1
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(height_field, Vec3.sReplicate(1.5f)),new RVec3(-20, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Non-uniform scaled shape
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(height_field,new Vec3(0.5f, 1.0f, 1.5f)),new RVec3(0, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Flipped in 2 axis
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(height_field,new Vec3(-0.5f, 1.0f, -1.5f)),new RVec3(20, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Inside out
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(height_field,new Vec3(-0.5f, 1.0f, 1.5f)),new RVec3(40, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Upside down
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(height_field,new Vec3(0.5f, -1.0f, 1.5f)),new RVec3(60, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Create a number of balls above the height fields
	ConstShape sphere_shape = new SphereShape(0.2f);
	ConstShape box_shape = new BoxShape(new Vec3(0.2f, 0.2f, 0.4f), 0.01f);
	for (int i = 0; i < 7; ++i)
		for (int j = 0; j < 5; ++j)
			mBodyInterface.createAndAddBody(new BodyCreationSettings(((j & 1)!=0)? box_shape : sphere_shape,new RVec3(-60.0f + 20.0f * i, 10.0f + max_height + 0.5f * j, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
}
}
