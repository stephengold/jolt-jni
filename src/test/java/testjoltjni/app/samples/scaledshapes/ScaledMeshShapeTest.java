/*
Copyright (c) 2025-2026 Stephen Gold

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
import java.util.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
/**
 * A line-for-line Java translation of the Jolt-Physics scaled mesh-shape test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/ScaledShapes/ScaledMeshShapeTest.cpp
 */
public class ScaledMeshShapeTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	final int n = 10;
	final float cell_size = 2.0f;
	final float max_height = 4.0f;

	// Create heights
	float[][]heights=new float[n + 1][n + 1];
	for (int x = 0; x <= n; ++x)
		for (int z = 0; z <= n; ++z)
			heights[x][z] = max_height * perlinNoise3(((float)x) / n, 0, ((float)z) / n, 256, 256, 256);

	// Create 'wall' around grid
	for (int x = 0; x <= n; ++x)
	{
		heights[x][0] += 2.0f;
		heights[x][n] += 2.0f;
	}

	for (int y = 1; y < n; ++y)
	{
		heights[0][y] += 2.0f;
		heights[n][y] += 2.0f;
	}

	// Create regular grid of triangles
	List<Triangle> triangles=new ArrayList<>(2*n*n);
	for (int x = 0; x < n; ++x)
		for (int z = 0; z < n; ++z)
		{
			float center = n * cell_size / 2;

			float x1 = cell_size * x - center;
			float z1 = cell_size * z - center;
			float x2 = x1 + cell_size;
			float z2 = z1 + cell_size;

			ConstFloat3 v1 =new Float3(x1, heights[x][z], z1);
			ConstFloat3 v2 =new Float3(x2, heights[x + 1][z], z1);
			ConstFloat3 v3 =new Float3(x1, heights[x][z + 1], z2);
			ConstFloat3 v4 =new Float3(x2, heights[x + 1][z + 1], z2);

			triangles.add(new Triangle(v1, v3, v4));
			triangles.add(new Triangle(v1, v4, v2));
		}

	ConstShapeSettings mesh_shape = new MeshShapeSettings(triangles);

	// Original shape
	mBodyInterface.createAndAddBody(new BodyCreationSettings(mesh_shape,new RVec3(-60, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Uniformly scaled shape < 1
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(mesh_shape, Vec3.sReplicate(0.5f)),new RVec3(-40, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Uniformly scaled shape > 1
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(mesh_shape, Vec3.sReplicate(1.5f)),new RVec3(-20, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Non-uniform scaled shape
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(mesh_shape,new Vec3(0.5f, 1.0f, 1.5f)),new RVec3(0, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Flipped in 2 axis
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(mesh_shape,new Vec3(-0.5f, 1.0f, -1.5f)),new RVec3(20, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Inside out
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(mesh_shape,new Vec3(-0.5f, 1.0f, 1.5f)),new RVec3(40, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Upside down
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(mesh_shape,new Vec3(0.5f, -1.0f, 1.5f)),new RVec3(60, 10, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Create a number of balls above the meshes
	ConstShape sphere_shape = new SphereShape(0.2f);
	ConstShape box_shape = new BoxShape(new Vec3(0.2f, 0.2f, 0.4f), 0.01f);
	for (int i = 0; i < 7; ++i)
		for (int j = 0; j < 5; ++j)
			mBodyInterface.createAndAddBody(new BodyCreationSettings(((j & 1)!=0)? box_shape : sphere_shape,new RVec3(-60.0f + 20.0f * i, 10.0f + max_height + 0.5f * j, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
}
}
