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
package testjoltjni.junit;

import com.github.stephengold.joltjni.BodyCreationSettings;
import com.github.stephengold.joltjni.BodyInterface;
import com.github.stephengold.joltjni.BoxShape;
import com.github.stephengold.joltjni.CapsuleShape;
import com.github.stephengold.joltjni.ConvexHullShapeSettings;
import com.github.stephengold.joltjni.EActivation;
import com.github.stephengold.joltjni.EMotionQuality;
import com.github.stephengold.joltjni.EMotionType;
import com.github.stephengold.joltjni.EPhysicsUpdateError;
import com.github.stephengold.joltjni.Float3;
import com.github.stephengold.joltjni.IndexedTriangle;
import com.github.stephengold.joltjni.IndexedTriangleList;
import com.github.stephengold.joltjni.JobSystemThreadPool;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.MapObj2Bp;
import com.github.stephengold.joltjni.MeshShapeSettings;
import com.github.stephengold.joltjni.ObjVsBpFilter;
import com.github.stephengold.joltjni.ObjVsObjFilter;
import com.github.stephengold.joltjni.PhysicsSettings;
import com.github.stephengold.joltjni.PhysicsSystem;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.ShapeRefC;
import com.github.stephengold.joltjni.SphereShape;
import com.github.stephengold.joltjni.TempAllocatorImpl;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.VertexList;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.Utils;

/**
 * Automated JUnit4 tests for jolt-jni.
 * <p>
 * Derived from PerformanceTest/ConvexVsMeshScene.h by Jorrit Rouwe.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test002 {
    // *************************************************************************
    // constants

    /**
     * object layer for non-moving objects
     */
    final private static int objLayerNonMoving = 0;
    /**
     * object layer for moving objects
     */
    final private static int objLayerMoving = 1;
    /**
     * number of object layers
     */
    final private static int numObjLayers = 2;
    // *************************************************************************
    // fields

    /**
     * body-creation settings for the mesh
     */
    private static BodyCreationSettings meshBodySettings;
    /**
     * convex shapes for dynamic bodies
     */
    private static ShapeRefC[] dynamicShapes;
    // *************************************************************************
    // new methods exposed

    /**
     * Drop 1,764 convex shapes onto a static mesh shape.
     * <p>
     * Derived from PerformanceTest/ConvexVsMeshScene.h by Jorrit Rouwe.
     */
    @Test
    public void test002() {
        Utils.loadAndInitializeNativeLibrary();

        // broadphase layers:
        int bpLayerNonMoving = 0;
        int bpLayerMoving = 1;
        int numBpLayers = 2;

        MapObj2Bp mapObj2Bp = new MapObj2Bp(numObjLayers, numBpLayers)
                .add(objLayerNonMoving, bpLayerNonMoving)
                .add(objLayerMoving, bpLayerMoving);
        ObjVsBpFilter objVsBpFilter
                = new ObjVsBpFilter(numObjLayers, numBpLayers)
                        .disablePair(objLayerNonMoving, bpLayerNonMoving);
        ObjVsObjFilter objVsObjFilter = new ObjVsObjFilter(numObjLayers)
                .disablePair(objLayerNonMoving, objLayerNonMoving);

        int maxBodies = 1_800;
        int numBodyMutexes = 0; // 0 means "use the default value"
        int maxBodyPairs = 65_536;
        int maxContacts = 20_480;
        PhysicsSystem physicsSystem = new PhysicsSystem();
        physicsSystem.init(maxBodies, numBodyMutexes, maxBodyPairs, maxContacts,
                mapObj2Bp, objVsBpFilter, objVsObjFilter);

        load();
        startTest(physicsSystem, EMotionQuality.LinearCast);

        physicsSystem.optimizeBroadPhase();

        int numBytes = 32 << 20; // 32 MiB
        TempAllocatorImpl tempAllocator = new TempAllocatorImpl(numBytes);

        int numThreads = Utils.numThreads();
        JobSystemThreadPool jobSystem = new JobSystemThreadPool(
                Jolt.cMaxPhysicsJobs, Jolt.cMaxPhysicsBarriers, numThreads);

        // Simulate physics for half a second:
        for (int stepCounter = 0; stepCounter < 30; ++stepCounter) {
            final float deltaTime = 1f / 60;
            final int numCollisionSteps = 1;
            int errors = physicsSystem.update(
                    deltaTime, numCollisionSteps, tempAllocator, jobSystem);
            Assert.assertEquals(EPhysicsUpdateError.None, errors);
        }

        for (ShapeRefC ref : dynamicShapes) {
            Utils.testClose(ref);
        }
        Utils.testClose(meshBodySettings);

        Utils.testClose(jobSystem);
        Utils.testClose(tempAllocator);

        Assert.assertEquals(1_765, physicsSystem.getNumBodies());

        Utils.testClose(physicsSystem);
        Utils.testClose(objVsObjFilter);
        Utils.testClose(objVsBpFilter);
        Utils.testClose(mapObj2Bp);

        Jolt.unregisterTypes();
        Jolt.destroyFactory();
    }
    // *************************************************************************
    // private methods

    /**
     * Generate the shapes to test.
     */
    private static void load() {
        // Create a 101x101 grid of vertices with various heights:
        final int n = 100;
        final float cellSize = 3f;
        final float maxHeight = 5f;
        final int numVertices = (n + 1) * (n + 1);
        VertexList vertices = new VertexList();
        vertices.resize(numVertices);
        for (int x = 0; x <= n; ++x) {
            for (int z = 0; z <= n; ++z) {
                double sinX = Math.sin(x * 50. / n);
                double cosZ = Math.cos(z * 50. / n);
                float height = (float) (sinX * cosZ);
                Float3 vertex = new Float3(
                        cellSize * x, maxHeight * height, cellSize * z);
                vertices.set(z * (n + 1) + x, vertex);
            }
        }

        // Create a triangle mesh:
        final int numTriangles = n * n * 2;
        IndexedTriangleList indices = new IndexedTriangleList();
        indices.resize(numTriangles);
        int nextTriangle = 0;
        for (int x = 0; x < n; ++x) {
            for (int z = 0; z < n; ++z) {
                int start = (n + 1) * z + x;

                IndexedTriangle it = indices.get(nextTriangle);
                ++nextTriangle;
                it.setIdx(0, start);
                it.setIdx(1, start + n + 1);
                it.setIdx(2, start + 1);

                it = indices.get(nextTriangle);
                ++nextTriangle;
                it.setIdx(0, start + 1);
                it.setIdx(1, start + n + 1);
                it.setIdx(2, start + n + 2);
            }
        }

        MeshShapeSettings meshShapeSettings
                = new MeshShapeSettings(vertices, indices);
        for (int i = 0; i < numTriangles; ++i) {
            IndexedTriangle it = indices.get(i);
            Utils.testClose(it);
        }
        Utils.testClose(indices);
        meshShapeSettings.setMaxTrianglesPerLeaf(4);

        meshBodySettings = new BodyCreationSettings();
        meshBodySettings.setMotionType(EMotionType.Static);
        meshBodySettings.setObjectLayer(objLayerNonMoving);
        float center = n * cellSize / 2;
        RVec3 rvec3 = new RVec3(-center, maxHeight, -center);
        meshBodySettings.setPosition(rvec3);
        meshBodySettings.setFriction(0.5f);
        meshBodySettings.setRestitution(0.6f);
        meshBodySettings.setShapeSettings(meshShapeSettings);

        // Generate 4 convex shapes for dynamic bodies:
        Vec3[] hullVertices = {new Vec3(0f, 1f, 0f), new Vec3(1f, 0f, 0f),
            new Vec3(-1f, 0f, 0f), new Vec3(0f, 0f, 1f), new Vec3(0f, 0f, -1f)};

        dynamicShapes = new ShapeRefC[]{
            new BoxShape(new Vec3(0.5f, 0.75f, 1f)).toRefC(),
            new SphereShape(0.5f).toRefC(),
            new CapsuleShape(0.75f, 0.5f).toRefC(),
            new ConvexHullShapeSettings(hullVertices).create().get()
        };
    }

    /**
     * Configuration that occurs after the {@code PhysicsSystem} is created, but
     * before advancing the simulation.
     *
     * @param physicsSystem the {@code PhysicsSystem} to configure (not null)
     * @param motionQuality the desired motion quality for dynamic bodies (not
     * null)
     */
    private static void startTest(
            PhysicsSystem physicsSystem, EMotionQuality motionQuality) {
        PhysicsSettings settings = physicsSystem.getPhysicsSettings();
        settings.setNumVelocitySteps(4);
        settings.setNumPositionSteps(1);
        physicsSystem.setPhysicsSettings(settings);
        Utils.testClose(settings);

        BodyInterface bi = physicsSystem.getBodyInterface();
        bi.createAndAddBody(meshBodySettings, EActivation.DontActivate);

        for (int x = -10; x <= 10; ++x) {
            for (int y = 0; y < dynamicShapes.length; ++y) {
                for (int z = -10; z <= 10; ++z) {
                    BodyCreationSettings bodySettings
                            = new BodyCreationSettings();
                    bodySettings.setFriction(0.5f);
                    bodySettings.setMotionQuality(motionQuality);
                    bodySettings.setMotionType(EMotionType.Dynamic);
                    bodySettings.setObjectLayer(objLayerMoving);
                    RVec3 rvec3 = new RVec3(7.5 * x, 15. + 2. * y, 7.5 * z);
                    bodySettings.setPosition(rvec3);
                    bodySettings.setRestitution(0.6f);
                    bodySettings.setShape(dynamicShapes[y]);

                    bi.createAndAddBody(bodySettings, EActivation.Activate);
                }
            }
        }
        Utils.testClose(bi);
    }
}
