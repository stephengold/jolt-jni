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
package testjoltjni.app;
import com.github.stephengold.joltjni.*;
import testjoltjni.Utils;

/**
 * A straightforward Java translation of the Jolt Physics "convex vs mesh scene"
 * performance test.
 * <p>
 * Derived from PerformanceTest/ConvexVsMeshScene.h by Jorrit Rouwe.
 *
 * @author Stephen Gold sgold@sonic.net
 */
// A scene that drops a number of convex shapes on a sloping terrain made out of a mesh shape
public class ConvexVsMeshScene {
	private static final int OBJ_LAYER_NON_MOVING = 0;
	private static final int OBJ_LAYER_MOVING = 1;
	private static final int OBJ_NUM_LAYERS = 2;
	private static final int BP_LAYER_NON_MOVING = 0;
	private static final int BP_LAYER_MOVING = 1;
	private static final int BP_NUM_LAYERS = 2;
	public static void main(String[] argv) {
		Utils.loadAndInitializeNativeLibrary();
		TempAllocatorImpl temp_allocator = new TempAllocatorImpl(32 << 20); // 32 MiB
		JobSystemThreadPool job_system = new JobSystemThreadPool(Jolt.cMaxPhysicsJobs, Jolt.cMaxPhysicsBarriers, Utils.numThreads());
		final int cMaxBodies = 1_800;
		final int cNumBodyMutexes = 0;
		final int cMaxBodyPairs = 65_536;
		final int cMaxContactConstraints = 20_480;
		MapObj2Bp broad_phase_layer_interface = new MapObj2Bp(OBJ_NUM_LAYERS, BP_NUM_LAYERS)
			.add(OBJ_LAYER_NON_MOVING, BP_LAYER_NON_MOVING)
			.add(OBJ_LAYER_MOVING, BP_LAYER_MOVING);
		ObjVsBpFilter object_vs_broadphase_layer_filter = new ObjVsBpFilter(OBJ_NUM_LAYERS, BP_NUM_LAYERS)
			.disablePair(OBJ_LAYER_NON_MOVING, BP_LAYER_NON_MOVING);
		ObjVsObjFilter object_vs_object_layer_filter = new ObjVsObjFilter(OBJ_NUM_LAYERS)
			.disablePair(OBJ_LAYER_NON_MOVING, OBJ_LAYER_NON_MOVING);
		PhysicsSystem physics_system = new PhysicsSystem();
		physics_system.init(cMaxBodies, cNumBodyMutexes, cMaxBodyPairs, cMaxContactConstraints, broad_phase_layer_interface, object_vs_broadphase_layer_filter, object_vs_object_layer_filter);
		Load();
		StartTest(physics_system, EMotionQuality.LinearCast);
		final float cDeltaTime = 1.0f / 60.0f;
		physics_system.optimizeBroadPhase();
		int step = 0;
		for (;;) {
			++step;
			int numActive = physics_system.getNumActiveBodies(EBodyType.RigidBody);
			System.out.println("Step " + step + ":  numActive = " + numActive);
			if (numActive < 800) break;
			final int cCollisionSteps = 1;
			physics_system.update(cDeltaTime, cCollisionSteps, temp_allocator, job_system);
		}
	}

        static boolean Load()
	{
		final int n = 100;
		final float cell_size = 3.0f;
		final float max_height = 5.0f;
		float center = n * cell_size / 2;

		// Create vertices
		final int num_vertices = (n + 1) * (n + 1);
		VertexList vertices = new VertexList();
		vertices.resize(num_vertices);
		for (int x = 0; x <= n; ++x)
			for (int z = 0; z <= n; ++z)
			{
				float height = (float) (Math.sin((float)(x) * 50.0f / n) * Math.cos((float)(z) * 50.0f / n));
				vertices.set(z * (n + 1) + x, new Float3(cell_size * x, max_height * height, cell_size * z));
			}

		// Create regular grid of triangles
		final int num_triangles = n * n * 2;
		IndexedTriangleList indices = new IndexedTriangleList();
		indices.resize(num_triangles);
		int next = 0;
		for (int x = 0; x < n; ++x)
			for (int z = 0; z < n; ++z)
			{
				int start = (n + 1) * z + x;

				IndexedTriangle it = indices.get(next++);
				it.setIdx(0, start);
				it.setIdx(1, start + n + 1);
				it.setIdx(2, start + 1);

				it = indices.get(next++);
				it.setIdx(0, start + 1);
				it.setIdx(1, start + n + 1);
				it.setIdx(2, start + n + 2);
			}

		// Create mesh shape settings
		MeshShapeSettings mesh_shape_settings = new MeshShapeSettings(vertices, indices);
		mesh_shape_settings.setMaxTrianglesPerLeaf(4);

		// Create mesh shape creation settings
		mMeshSettings = new BodyCreationSettings();
		mMeshSettings.setMotionType(EMotionType.Static);
		mMeshSettings.setObjectLayer(OBJ_LAYER_NON_MOVING);
		mMeshSettings.setPosition(new RVec3(-center, max_height, -center));
		mMeshSettings.setFriction(0.5f);
		mMeshSettings.setRestitution(0.6f);
		mMeshSettings.setShapeSettings(mesh_shape_settings);

		// Create other shapes
		mShapes = new ShapeRefC[] {
			new BoxShape(new Vec3(0.5f, 0.75f, 1.0f)).toRefC(),
			new SphereShape(0.5f).toRefC(),
			new CapsuleShape(0.75f, 0.5f).toRefC(),
			new ConvexHullShapeSettings(new Vec3(0, 1, 0), new Vec3(1, 0, 0), new Vec3(-1, 0, 0), new Vec3(0, 0, 1), new Vec3(0, 0, -1)).create().get(),
		};

		return true;
	}

	static void			StartTest(PhysicsSystem inPhysicsSystem, EMotionQuality inMotionQuality)
	{
		// Reduce the solver iteration count, the scene doesn't have any constraints so we don't need the default amount of iterations
		PhysicsSettings settings = inPhysicsSystem.getPhysicsSettings();
		settings.setNumVelocitySteps(4);
		settings.setNumPositionSteps(1);
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
					creation_settings.setMotionType(EMotionType.Dynamic);
					creation_settings.setMotionQuality(inMotionQuality);
					creation_settings.setObjectLayer(OBJ_LAYER_MOVING);
					creation_settings.setPosition(new RVec3(7.5 * x, 15.0 + 2.0 * y, 7.5 * z));
					creation_settings.setFriction(0.5f);
					creation_settings.setRestitution(0.6f);
					creation_settings.setShape(mShapes[y]);
					bi.createAndAddBody(creation_settings, EActivation.Activate);
				}
	}

	static BodyCreationSettings	mMeshSettings;
	static ShapeRefC[]		mShapes;
}