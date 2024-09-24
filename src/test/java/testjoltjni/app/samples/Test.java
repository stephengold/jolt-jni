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
package testjoltjni.app.samples;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import java.util.*;
import testjoltjni.app.testframework.*;

/**
 * A straightforward Java translation of the Jolt Physics abstract test class.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Test.cpp
 */
abstract class Test {
JobSystem mJobSystem = null;
PhysicsSystem mPhysicsSystem = null;
BodyInterface mBodyInterface = null;
DebugRenderer mDebugRenderer = null;
TempAllocator mTempAllocator = null;
float GetWorldScale() { return 1.0f; }

Body CreateFloor() {return CreateFloor(200.0f);}
Body CreateFloor(float inSize)
{
	final float scale = GetWorldScale();

	Body floor = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(Vec3.multiply(scale, new Vec3(0.5f * inSize, 1.0f, 0.5f * inSize)), 0.0f), new RVec3(Vec3.multiply(scale, new Vec3(0.0f, -1.0f, 0.0f))), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
	mBodyInterface.addBody(floor.getId(), EActivation.DontActivate);
	return floor;
}

Body CreateLargeTriangleFloor()
{
	Triangle[] triangles = {
		new Triangle(new Float3(427.941376f, 0.000027f, -456.470642f), new Float3(427.941376f, 0.000024f, -399.411774f), new Float3(512.0f, 0.000031f, -511.999969f)),
		new Triangle(new Float3(0.0f, 0.000031f, -511.999969f), new Float3(28.529310f, 0.000027f, -456.470642f), new Float3(427.941376f, 0.000027f, -456.470642f)),
		new Triangle(new Float3(427.941376f, 0.000027f, -456.470642f), new Float3(512.0f, 0.000031f, -511.999969f), new Float3(0.0f, 0.000031f, -511.999969f)),
		new Triangle(new Float3(285.294067f, 0.000027f, -456.470642f), new Float3(399.411804f, 0.000024f, -399.411774f), new Float3(313.823395f, 0.000027f, -456.470642f)),
		new Triangle(new Float3(313.823395f, 0.000027f, -456.470642f), new Float3(399.411804f, 0.000024f, -399.411774f), new Float3(342.352936f, 0.000027f, -456.470642f)),
		new Triangle(new Float3(342.352936f, 0.000027f, -456.470642f), new Float3(399.411804f, 0.000024f, -399.411774f), new Float3(370.882507f, 0.000027f, -456.470642f)),
		new Triangle(new Float3(399.411804f, 0.000024f, -399.411774f), new Float3(427.941376f, 0.000024f, -399.411774f), new Float3(370.882507f, 0.000027f, -456.470642f)),
		new Triangle(new Float3(370.882507f, 0.000027f, -456.470642f), new Float3(427.941376f, 0.000024f, -399.411774f), new Float3(399.411804f, 0.000027f, -456.470642f)),
		new Triangle(new Float3(399.411804f, 0.000027f, -456.470642f), new Float3(427.941376f, 0.000024f, -399.411774f), new Float3(427.941376f, 0.000027f, -456.470642f)),
		new Triangle(new Float3(256.764771f, 0.000027f, -456.470642f), new Float3(399.411804f, 0.000024f, -399.411774f), new Float3(285.294067f, 0.000027f, -456.470642f)),
		new Triangle(new Float3(85.588173f, 0.000027f, -456.470642f), new Float3(399.411804f, 0.000024f, -399.411774f), new Float3(114.117729f, 0.000027f, -456.470642f)),
		new Triangle(new Float3(114.117729f, 0.000027f, -456.470642f), new Float3(399.411804f, 0.000024f, -399.411774f), new Float3(142.647034f, 0.000027f, -456.470642f)),
		new Triangle(new Float3(142.647034f, 0.000027f, -456.470642f), new Float3(399.411804f, 0.000024f, -399.411774f), new Float3(171.176590f, 0.000027f, -456.470642f)),
		new Triangle(new Float3(171.176590f, 0.000027f, -456.470642f), new Float3(399.411804f, 0.000024f, -399.411774f), new Float3(199.705902f, 0.000027f, -456.470642f)),
		new Triangle(new Float3(199.705902f, 0.000027f, -456.470642f), new Float3(399.411804f, 0.000024f, -399.411774f), new Float3(228.235214f, 0.000027f, -456.470642f)),
		new Triangle(new Float3(228.235214f, 0.000027f, -456.470642f), new Float3(399.411804f, 0.000024f, -399.411774f), new Float3(256.764771f, 0.000027f, -456.470642f)),
		new Triangle(new Float3(85.588173f, 0.000024f, -399.411774f), new Float3(399.411804f, 0.000024f, -399.411774f), new Float3(85.588173f, 0.000027f, -456.470642f)),
		new Triangle(new Float3(427.941376f, 0.000024f, -399.411774f), new Float3(512.0f, 0.000019f, -313.823364f), new Float3(512.0f, 0.000031f, -511.999969f)),
		new Triangle(new Float3(399.411804f, 0.000024f, -399.411774f), new Float3(512.0f, 0.000019f, -313.823364f), new Float3(427.941376f, 0.000024f, -399.411774f)),
		new Triangle(new Float3(285.294067f, 0.000024f, -399.411774f), new Float3(512.0f, 0.000019f, -313.823364f), new Float3(313.823395f, 0.000024f, -399.411774f)),
		new Triangle(new Float3(313.823395f, 0.000024f, -399.411774f), new Float3(512.0f, 0.000019f, -313.823364f), new Float3(342.352936f, 0.000024f, -399.411774f)),
		new Triangle(new Float3(342.352936f, 0.000024f, -399.411774f), new Float3(512.0f, 0.000019f, -313.823364f), new Float3(370.882507f, 0.000024f, -399.411774f)),
		new Triangle(new Float3(370.882507f, 0.000024f, -399.411774f), new Float3(512.0f, 0.000019f, -313.823364f), new Float3(399.411804f, 0.000024f, -399.411774f)),
		new Triangle(new Float3(256.764771f, 0.000024f, -399.411774f), new Float3(512.0f, 0.000019f, -313.823364f), new Float3(285.294067f, 0.000024f, -399.411774f)),
		new Triangle(new Float3(228.235214f, 0.000024f, -399.411774f), new Float3(512.0f, 0.000019f, -313.823364f), new Float3(256.764771f, 0.000024f, -399.411774f)),
		new Triangle(new Float3(199.705902f, 0.000024f, -399.411774f), new Float3(512.0f, 0.000019f, -313.823364f), new Float3(228.235214f, 0.000024f, -399.411774f)),
		new Triangle(new Float3(228.235214f, 0.000019f, -313.823364f), new Float3(512.0f, 0.000019f, -313.823364f), new Float3(199.705902f, 0.000024f, -399.411774f)),
		new Triangle(new Float3(142.647034f, 0.000024f, -399.411774f), new Float3(228.235214f, 0.000019f, -313.823364f), new Float3(171.176590f, 0.000024f, -399.411774f)),
		new Triangle(new Float3(171.176590f, 0.000024f, -399.411774f), new Float3(228.235214f, 0.000019f, -313.823364f), new Float3(199.705902f, 0.000024f, -399.411774f)),
		new Triangle(new Float3(85.588173f, 0.000022f, -370.882477f), new Float3(228.235214f, 0.000019f, -313.823364f), new Float3(142.647034f, 0.000024f, -399.411774f)),
		new Triangle(new Float3(85.588173f, 0.000022f, -370.882477f), new Float3(199.705902f, 0.000019f, -313.823364f), new Float3(228.235214f, 0.000019f, -313.823364f)),
		new Triangle(new Float3(114.117729f, 0.000024f, -399.411774f), new Float3(85.588173f, 0.000022f, -370.882477f), new Float3(142.647034f, 0.000024f, -399.411774f)),
		new Triangle(new Float3(85.588173f, 0.000024f, -399.411774f), new Float3(85.588173f, 0.000022f, -370.882477f), new Float3(114.117729f, 0.000024f, -399.411774f)),
		new Triangle(new Float3(28.529310f, 0.000019f, -313.823364f), new Float3(199.705902f, 0.000019f, -313.823364f), new Float3(85.588173f, 0.000022f, -370.882477f)),
		new Triangle(new Float3(57.058865f, 0.000019f, -313.823364f), new Float3(0.0f, 0.000017f, -285.294037f), new Float3(85.588173f, 0.000019f, -313.823364f)),
		new Triangle(new Float3(28.529310f, 0.000019f, -313.823364f), new Float3(0.0f, 0.000017f, -285.294037f), new Float3(57.058865f, 0.000019f, -313.823364f)),
		new Triangle(new Float3(28.529310f, 0.000027f, -456.470642f), new Float3(0.0f, 0.000017f, -285.294037f), new Float3(57.058865f, 0.000027f, -456.470642f)),
		new Triangle(new Float3(0.0f, 0.000017f, -285.294037f), new Float3(28.529310f, 0.000027f, -456.470642f), new Float3(0.0f, 0.000031f, -511.999969f)),
		new Triangle(new Float3(0.0f, 0.000017f, -285.294037f), new Float3(85.588173f, 0.000022f, -370.882477f), new Float3(85.588173f, 0.000024f, -399.411774f)),
		new Triangle(new Float3(0.0f, 0.000017f, -285.294037f), new Float3(85.588173f, 0.000024f, -399.411774f), new Float3(57.058865f, 0.000027f, -456.470642f)),
		new Triangle(new Float3(57.058865f, 0.000027f, -456.470642f), new Float3(85.588173f, 0.000024f, -399.411774f), new Float3(85.588173f, 0.000027f, -456.470642f)),
		new Triangle(new Float3(399.411804f, 0.000019f, -313.823364f), new Float3(512.0f, 0.000003f, -57.058861f), new Float3(456.470673f, 0.000019f, -313.823364f)),
		new Triangle(new Float3(456.470673f, 0.000019f, -313.823364f), new Float3(512.0f, 0.000003f, -57.058861f), new Float3(512.0f, 0.000019f, -313.823364f)),
		new Triangle(new Float3(228.235214f, 0.000019f, -313.823364f), new Float3(512.0f, 0.000003f, -57.058861f), new Float3(256.764771f, 0.000019f, -313.823364f)),
		new Triangle(new Float3(256.764771f, 0.000019f, -313.823364f), new Float3(512.0f, 0.000003f, -57.058861f), new Float3(285.294067f, 0.000019f, -313.823364f)),
		new Triangle(new Float3(285.294067f, 0.000019f, -313.823364f), new Float3(512.0f, 0.000003f, -57.058861f), new Float3(313.823395f, 0.000019f, -313.823364f)),
		new Triangle(new Float3(313.823395f, 0.000019f, -313.823364f), new Float3(512.0f, 0.000003f, -57.058861f), new Float3(342.352936f, 0.000019f, -313.823364f)),
		new Triangle(new Float3(342.352936f, 0.000019f, -313.823364f), new Float3(512.0f, 0.000003f, -57.058861f), new Float3(370.882507f, 0.000019f, -313.823364f)),
		new Triangle(new Float3(370.882507f, 0.000019f, -313.823364f), new Float3(512.0f, 0.000003f, -57.058861f), new Float3(399.411804f, 0.000019f, -313.823364f)),
		new Triangle(new Float3(0.0f, 0.000017f, -285.294037f), new Float3(0.0f, 0.000009f, -142.647018f), new Float3(512.0f, 0.000003f, -57.058861f)),
		new Triangle(new Float3(199.705902f, 0.000019f, -313.823364f), new Float3(512.0f, 0.000003f, -57.058861f), new Float3(228.235214f, 0.000019f, -313.823364f)),
		new Triangle(new Float3(171.176590f, 0.000019f, -313.823364f), new Float3(512.0f, 0.000003f, -57.058861f), new Float3(199.705902f, 0.000019f, -313.823364f)),
		new Triangle(new Float3(0.0f, 0.000017f, -285.294037f), new Float3(512.0f, 0.000003f, -57.058861f), new Float3(85.588173f, 0.000019f, -313.823364f)),
		new Triangle(new Float3(85.588173f, 0.000019f, -313.823364f), new Float3(512.0f, 0.000003f, -57.058861f), new Float3(142.647034f, 0.000019f, -313.823364f)),
		new Triangle(new Float3(142.647034f, 0.000019f, -313.823364f), new Float3(512.0f, 0.000003f, -57.058861f), new Float3(171.176590f, 0.000019f, -313.823364f)),
		new Triangle(new Float3(485.0f, 0.000002f, -28.529308f), new Float3(512.0f, 0.0f, 0.0f), new Float3(512.0f, 0.000002f, -28.529308f)),
		new Triangle(new Float3(512.0f, 0.0f, 0.0f), new Float3(427.941376f, 0.000002f, -28.529308f), new Float3(285.294067f, 0.000002f, -28.529308f)),
		new Triangle(new Float3(456.470673f, 0.000002f, -28.529308f), new Float3(512.0f, 0.0f, 0.0f), new Float3(485.0f, 0.000002f, -28.529308f)),
		new Triangle(new Float3(427.941376f, 0.000002f, -28.529308f), new Float3(512.0f, 0.0f, 0.0f), new Float3(456.470673f, 0.000002f, -28.529308f)),
		new Triangle(new Float3(171.176590f, 0.0f, 0.0f), new Float3(512.0f, 0.0f, 0.0f), new Float3(285.294067f, 0.000002f, -28.529308f)),
		new Triangle(new Float3(285.294067f, 0.000002f, -28.529308f), new Float3(512.0f, 0.000002f, -28.529308f), new Float3(512.0f, 0.000003f, -57.058861f)),
		new Triangle(new Float3(0.0f, 0.000009f, -142.647018f), new Float3(285.294067f, 0.000002f, -28.529308f), new Float3(512.0f, 0.000003f, -57.058861f)),
		new Triangle(new Float3(0.0f, 0.000007f, -114.117722f), new Float3(171.176590f, 0.0f, 0.0f), new Float3(0.0f, 0.000009f, -142.647018f)),
		new Triangle(new Float3(0.0f, 0.0f, 0.0f), new Float3(171.176590f, 0.0f, 0.0f), new Float3(0.0f, 0.000007f, -114.117722f)),
		new Triangle(new Float3(0.0f, 0.000009f, -142.647018f), new Float3(171.176590f, 0.0f, 0.0f), new Float3(285.294067f, 0.000002f, -28.529308f))
	};
	MeshShapeSettings mesh_settings = new MeshShapeSettings(triangles);
	mesh_settings.setEmbedded();
	BodyCreationSettings floor_settings = new BodyCreationSettings(mesh_settings, new RVec3(-256.0f, 0.0f, 256.0f), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
	Body floor = mBodyInterface.createBody(floor_settings);
	mBodyInterface.addBody(floor.getId(), EActivation.DontActivate);
	return floor;
}

Body CreateMeshTerrain()
{
	final float scale = GetWorldScale();

	int n;
	float cell_size;
if (Jolt.buildType().equals("DEBUG")) {
	n = 50;
	cell_size = scale * 2.0f;
} else {
	n = 100;
	cell_size = scale * 1.0f;
}
	final float max_height = scale * 3.0f;

	// Create heights
	float[][] heights = new float[n + 1][n + 1];
	for (int x = 0; x <= n; ++x)
		for (int z = 0; z <= n; ++z)
			heights[x][z] = max_height * Perlin.Noise3((float)(x) * 8.0f / n, 0, (float)(z) * 8.0f / n, 256, 256, 256);

	// Create regular grid of triangles
	List<Triangle> triangles = new ArrayList<>(2 * n * n);
	for (int x = 0; x < n; ++x)
		for (int z = 0; z < n; ++z)
		{
			float center = n * cell_size / 2;

			float x1 = cell_size * x - center;
			float z1 = cell_size * z - center;
			float x2 = x1 + cell_size;
			float z2 = z1 + cell_size;

			Float3 v1 = new Float3(x1, heights[x][z], z1);
			Float3 v2 = new Float3(x2, heights[x + 1][z], z1);
			Float3 v3 = new Float3(x1, heights[x][z + 1], z2);
			Float3 v4 = new Float3(x2, heights[x + 1][z + 1], z2);

			triangles.add(new Triangle(v1, v3, v4));
			triangles.add(new Triangle(v1, v4, v2));
		}

	// Floor
	Body floor = mBodyInterface.createBody(new BodyCreationSettings(new MeshShapeSettings(triangles), RVec3.sZero(), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
	mBodyInterface.addBody(floor.getId(), EActivation.DontActivate);
	return floor;
}

Body CreateHeightFieldTerrain()
{
	final float scale = GetWorldScale();

	final int n = 128;
	final float cell_size = scale * 1.0f;
	final float max_height = scale * 5.0f;

	// Create height samples
	float[] heights = new float[n * n];
	for (int y = 0; y < n; ++y)
		for (int x = 0; x < n; ++x)
			heights[y * n + x] = max_height * Perlin.Noise3((float)(x) * 8.0f / n, 0, (float)(y) * 8.0f / n, 256, 256, 256);

	// Create height field
	ShapeSettings height_field = new HeightFieldShapeSettings(heights, new Vec3(-0.5f * cell_size * n, 0.0f, -0.5f * cell_size * n), new Vec3(cell_size, 1.0f, cell_size), n);

	// Floor
	Body floor = mBodyInterface.createBody(new BodyCreationSettings(height_field, RVec3.sZero(), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
	mBodyInterface.addBody(floor.getId(), EActivation.DontActivate);
	return floor;
}
}