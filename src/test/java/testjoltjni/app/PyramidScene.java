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
import com.github.stephengold.joltjni.enumerate.*;
import testjoltjni.TestUtils;

/**
 * A straightforward Java translation of the Jolt Physics "pyramid scene"
 * performance test.
 * <p>
 * Derived from PerformanceTest/PyramidScene.h by Jorrit Rouwe.
 *
 * @author Stephen Gold sgold@sonic.net
 */
// A scene that creates a pyramid of boxes to create a very large island
public class PyramidScene
{
	private static final int OBJ_LAYER_NON_MOVING = 0;
	private static final int OBJ_LAYER_MOVING = 1;
	private static final int OBJ_NUM_LAYERS = 2;
	private static final int BP_LAYER_NON_MOVING = 0;
	private static final int BP_LAYER_MOVING = 1;
	private static final int BP_NUM_LAYERS = 2;
	public static void main(String[] argv) {
		TestUtils.loadAndInitializeNativeLibrary();
		TempAllocatorImpl temp_allocator = new TempAllocatorImpl(32 << 20); // 32 MiB
		JobSystemThreadPool job_system = new JobSystemThreadPool(Jolt.cMaxPhysicsJobs, Jolt.cMaxPhysicsBarriers, TestUtils.numThreads());
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
		StartTest(physics_system, EMotionQuality.LinearCast);
		final float cDeltaTime = 1.0f / 60.0f;
		physics_system.optimizeBroadPhase();
		int step = 0;
		for (;;) {
			++step;
			if (step < 300) break;
			final int cCollisionSteps = 1;
			physics_system.update(cDeltaTime, cCollisionSteps, temp_allocator, job_system);
		}
		TestUtils.cleanup();
	}

	static void			StartTest(PhysicsSystem inPhysicsSystem, EMotionQuality inMotionQuality)
	{
		BodyInterface bi = inPhysicsSystem.getBodyInterface();

		// Floor
		bi.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(50.0f, 1.0f, 50.0f), 0.0f), new RVec3(new Vec3(0.0f, -1.0f, 0.0f)), Quat.sIdentity(), EMotionType.Static, OBJ_LAYER_NON_MOVING), EActivation.DontActivate);

		final float cBoxSize = 2.0f;
		final float cBoxSeparation = 0.5f;
		final float cHalfBoxSize = 0.5f * cBoxSize;
		final int cPyramidHeight = 15;

		ShapeRef box_shape = new BoxShape(Vec3.sReplicate(cHalfBoxSize), 0.0f).toRef(); // No convex radius to force more collisions

		// Pyramid
		for (int i = 0; i < cPyramidHeight; ++i)
			for (int j = i / 2; j < cPyramidHeight - (i + 1) / 2; ++j)
				for (int k = i / 2; k < cPyramidHeight - (i + 1) / 2; ++k)
				{
					RVec3 position = new RVec3(-cPyramidHeight + cBoxSize * j + (((i & 1)!=0)? cHalfBoxSize : 0.0f), 1.0f + (cBoxSize + cBoxSeparation) * i, -cPyramidHeight + cBoxSize * k + (((i & 1)!=0)? cHalfBoxSize : 0.0f));
					BodyCreationSettings settings = new BodyCreationSettings(box_shape, position, Quat.sIdentity(), EMotionType.Dynamic, OBJ_LAYER_MOVING);
					settings.setAllowSleeping(false); // No sleeping to force the large island to stay awake
					bi.createAndAddBody(settings, EActivation.Activate);
				}
	}
}
