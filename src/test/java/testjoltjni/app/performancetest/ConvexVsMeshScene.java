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
package testjoltjni.app.performancetest;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import static com.github.stephengold.joltjni.std.Std.*;

/**
 * A line-for-line Java translation of the Jolt Physics "convex vs mesh scene" performance test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/PerformanceTest/ConvexVsMeshScene.h
 */
// A scene that drops a number of convex shapes on a sloping terrain made out of a mesh shape
class ConvexVsMeshScene implements PerformanceTestScene {
public
	String GetName()
	{
		return "ConvexVsMesh";
	}

	public boolean			Load()
	{
		final int n = 100;
		final float cell_size = 3.0f;
		final float max_height = 5.0f;
		float center = n * cell_size / 2;

		// Create vertices
		final int num_vertices = (n + 1) * (n + 1);
		VertexList vertices=new VertexList();
		vertices.resize(num_vertices);
		for (int x = 0; x <= n; ++x)
			for (int z = 0; z <= n; ++z)
			{
				float height = sin((float)(x) * 50.0f / n) * cos((float)(z) * 50.0f / n);
				vertices.set(z * (n + 1) + x, new Float3(cell_size * x, max_height * height, cell_size * z));
			}

		// Create regular grid of triangles
		final int num_triangles = n * n * 2;
		IndexedTriangleList indices=new IndexedTriangleList();
		indices.resize(num_triangles);
		int next = 0;
		for (int x = 0; x < n; ++x)
			for (int z = 0; z < n; ++z)
			{
				int start = (n + 1) * z + x;

				IndexedTriangle it = indices.get(next++);
				it.setIdx(0,  start);
				it.setIdx(1,  start + n + 1);
				it.setIdx(2, start + 1);

				it = indices.get(next++);
				it.setIdx(0,  start + 1);
				it.setIdx(1,  start + n + 1);
				it.setIdx(2,  start + n + 2);
			}

		// Create mesh shape settings
		MeshShapeSettings mss = new MeshShapeSettings(vertices, indices);
		mss.setMaxTrianglesPerLeaf(4);
		ShapeSettingsRef mesh_shape_settings = mss.toRef();

		// Create mesh shape creation settings
		mMeshSettings.setMotionType ( EMotionType.Static);
		mMeshSettings.setObjectLayer ( Layers.NON_MOVING);
		mMeshSettings.setPosition (new RVec3(-center, max_height, -center));
		mMeshSettings.setFriction ( 0.5f);
		mMeshSettings.setRestitution ( 0.6f);
		mMeshSettings.setShapeSettings(mesh_shape_settings);

		// Create other shapes
		mShapes = new ShapeRefC[] {
			new BoxShape(new Vec3(0.5f, 0.75f, 1.0f)).toRefC(),
			new SphereShape(0.5f).toRefC(),
			new CapsuleShape(0.75f, 0.5f).toRefC(),
			new ConvexHullShapeSettings(new Vec3(0, 1, 0), new Vec3(1, 0, 0), new Vec3(-1, 0, 0), new Vec3(0, 0, 1), new Vec3(0, 0, -1) ).create().get(),
		};

		return true;
	}

	public void			StartTest(PhysicsSystem inPhysicsSystem, EMotionQuality inMotionQuality)
	{
		// Reduce the solver iteration count, the scene doesn't have any constraints so we don't need the default amount of iterations
		PhysicsSettings settings = inPhysicsSystem.getPhysicsSettings();
		settings.setNumVelocitySteps ( 4);
		settings.setNumPositionSteps ( 1);
		inPhysicsSystem.setPhysicsSettings(settings);

		// Create background
		BodyInterface bi = inPhysicsSystem.getBodyInterface();
		bi.createAndAddBody(mMeshSettings, EActivation.DontActivate);

		// Construct bodies
		for (int x = -10; x <= 10; ++x)
			for (int y = 0; y < (int)mShapes.length; ++y)
				for (int z = -10; z <= 10; ++z)
				{
					BodyCreationSettings creation_settings = new BodyCreationSettings();
					creation_settings.setMotionType ( EMotionType.Dynamic);
					creation_settings.setMotionQuality ( inMotionQuality);
					creation_settings.setObjectLayer ( Layers.MOVING);
					creation_settings.setPosition (new RVec3(7.5 * x, 15.0 + 2.0 * y, 7.5 * z));
					creation_settings.setFriction ( 0.5f);
					creation_settings.setRestitution ( 0.6f);
					creation_settings.setShape(mShapes[y]);
					bi.createAndAddBody(creation_settings, EActivation.Activate);
				}
	}

private
	static BodyCreationSettings	mMeshSettings=new BodyCreationSettings();
	static ShapeRefC[]		mShapes;
	public void StopTest(PhysicsSystem inPhysicsSystem){}
}