/*
Copyright (c) 2024-2025 Stephen Gold

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
import com.github.stephengold.joltjni.enumerate.EPhysicsUpdateError;
import testjoltjni.TestUtils;
import testjoltjni.app.samples.broadphase.*;
import testjoltjni.app.samples.character.*;
import testjoltjni.app.samples.constraints.*;
import testjoltjni.app.samples.convexcollision.*;
import testjoltjni.app.samples.general.*;
import testjoltjni.app.samples.rig.*;
import testjoltjni.app.samples.scaledshapes.*;
import testjoltjni.app.samples.shapes.*;
import testjoltjni.app.samples.softbody.*;
import testjoltjni.app.samples.vehicle.*;
import testjoltjni.app.samples.water.*;

/**
 * Console app to perform a "smoke test" on each of the Samples tests.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class SmokeTestAll {
    // *************************************************************************
    // constants

    /**
     * default number of physics steps to simulate during each invocation of
     * {@code smokeTest()}
     */
    final private static int defaultNumSteps = 1;
    // *************************************************************************
    // fields

    /**
     * renderer shared by all test objects
     */
    private static DebugRenderer renderer;
    /**
     * count invocations of {@code smokeTest()}
     */
    private static int numTests;
    /**
     * allocator shared by all test objects
     */
    private static TempAllocator tempAllocator;
    // *************************************************************************
    // new methods exposed

    /**
     * Main entry point for the SmokeTestAll application.
     *
     * @param arguments array of command-line arguments (not null)
     */
    public static void main(String... arguments) {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();
        System.out.println(Jolt.getConfigurationString());
        System.out.println();
        assert Jolt.implementsDebugRendering();

        String fileName = "SmokeTestAll.jor";
        int mode = StreamOutWrapper.out()
                | StreamOutWrapper.binary() | StreamOutWrapper.trunc();
        StreamOut streamOut = new StreamOutWrapper(fileName, mode);
        renderer = new DebugRendererRecorder(streamOut);

        int numBytes = 1 << 24; // 16 MiB
        tempAllocator = new TempAllocatorImpl(numBytes);

        smokeTestAll();
    }
    // *************************************************************************
    // private methods

    /**
     * Allocate and initialize a {@code PhysicsSystem} in the customary
     * configuration.
     *
     * @param maxBodies the desired number of bodies (&ge;1)
     * @return a new system
     */
    static private PhysicsSystem newPhysicsSystem(int maxBodies) {
        BPLayerInterfaceImpl mapObj2Bp = new BPLayerInterfaceImpl();
        ObjectVsBroadPhaseLayerFilterImpl objVsBpFilter
                = new ObjectVsBroadPhaseLayerFilterImpl();
        ObjectLayerPairFilterImpl objVsObjFilter
                = new ObjectLayerPairFilterImpl();

        int numBodyMutexes = 0; // 0 means "use the default value"
        int maxBodyPairs = 5_000;
        int maxContacts = 8_000;
        PhysicsSystem result = new PhysicsSystem();
        result.init(maxBodies, numBodyMutexes, maxBodyPairs, maxContacts,
                mapObj2Bp, objVsBpFilter, objVsObjFilter);

        return result;
    }

    /**
     * Invoke key methods of the specified Test to see whether they crash.
     *
     * @param test the Test instance to use (not null)
     */
    static private void smokeTest(Test test) {
        smokeTest(test, defaultNumSteps);
    }

    /**
     * Invoke key methods of the specified Test to see whether they crash.
     *
     * @param test the Test instance to use (not null)
     * @param numSteps the number of physics steps to simulate (&ge;0,
     * default=defaultNumSteps)
     */
    static private void smokeTest(Test test, int numSteps) {
        ++numTests;

        // Log the name of the test:
        String testName = test.getClass().getSimpleName();
        System.out.printf("=== Test #%d:  %s for %d step%s%n",
                numTests, testName, numSteps, (numSteps == 1) ? "" : "s");
        System.out.flush();

        test.SetDebugRenderer(renderer);
        test.SetTempAllocator(tempAllocator);

        // Create new job/physics systems for each test:
        int numThreads = -1; // autodetect
        JobSystem jobSystem = new JobSystemThreadPool(
                Jolt.cMaxPhysicsJobs, Jolt.cMaxPhysicsBarriers, numThreads);
        test.SetJobSystem(jobSystem);

        int maxBodies = 1_300;
        PhysicsSystem physicsSystem = newPhysicsSystem(maxBodies);
        test.SetPhysicsSystem(physicsSystem);

        ContactListener listener = new SamplesContactListener(test);
        physicsSystem.setContactListener(listener);

        test.Initialize();

        // Single-step the physics numSteps times:
        for (int i = 0; i < numSteps; ++i) {
            PreUpdateParams params = new PreUpdateParams();
            params.mDeltaTime = 0.02f;
            test.PrePhysicsUpdate(params);

            int collisionSteps = 1;
            int errors = physicsSystem.update(params.mDeltaTime, collisionSteps,
                    tempAllocator, jobSystem);
            assert errors == EPhysicsUpdateError.None : errors;

            test.PostPhysicsUpdate(params.mDeltaTime);
        }

        test.Cleanup();
        physicsSystem.forgetMe();
        System.gc();
    }

    /**
     * Smoke test all the packages.
     */
    private static void smokeTestAll() {
        // broadphase package:
        smokeTest(new BroadPhaseCastRayTest());
        smokeTest(new BroadPhaseInsertionTest());

        // character package:
        smokeTest(new CharacterPlanetTest());
        smokeTest(new CharacterSpaceShipTest());
        smokeTest(new CharacterTest());
        smokeTest(new CharacterVirtualTest());

        smokeTestConstraints();

        // convex-collision package:
        smokeTest(new CapsuleVsBoxTest());
        smokeTest(new ClosestPointTest());
        smokeTest(new ConvexHullShrinkTest());
        smokeTest(new ConvexHullTest());
        smokeTest(new EPATest());
        smokeTest(new InteractivePairsTest());

        smokeTestGeneral();
        smokeTestRig();
        smokeTestScaledShapes();
        smokeTestShapes();
        smokeTestSoftBody();

        // vehicle package:
        smokeTest(new MotorcycleTest());
        smokeTest(new TankTest());
        smokeTest(new VehicleConstraintTest());
        smokeTest(new VehicleSixDOFTest());
        smokeTest(new VehicleStressTest());

        // water package:
        smokeTest(new BoatTest());
        smokeTest(new WaterShapeTest());
    }

    /**
     * Smoke test the "constraints" package.
     */
    private static void smokeTestConstraints() {
        smokeTest(new ConeConstraintTest());
        smokeTest(new ConstraintPriorityTest());
        smokeTest(new ConstraintSingularityTest());
        smokeTest(new ConstraintVsCOMChangeTest());
        smokeTest(new DistanceConstraintTest());
        smokeTest(new FixedConstraintTest());
        smokeTest(new GearConstraintTest());
        smokeTest(new HingeConstraintTest());
        smokeTest(new PathConstraintTest());
        smokeTest(new PointConstraintTest());
        smokeTest(new PoweredHingeConstraintTest());
        smokeTest(new PoweredSliderConstraintTest());
        smokeTest(new PoweredSwingTwistConstraintTest());
        smokeTest(new PulleyConstraintTest());
        smokeTest(new RackAndPinionConstraintTest());
        smokeTest(new SixDOFConstraintTest());
        smokeTest(new SliderConstraintTest());
        smokeTest(new SpringTest());
        smokeTest(new SwingTwistConstraintFrictionTest());
        smokeTest(new SwingTwistConstraintTest());
    }

    /**
     * Smoke test the "general" package.
     */
    private static void smokeTestGeneral() {
        smokeTest(new ActivateDuringUpdateTest());
        smokeTest(new ActiveEdgesTest());
        smokeTest(new AllowedDOFsTest());
        smokeTest(new BigVsSmallTest());
        smokeTest(new CenterOfMassTest());
        smokeTest(new ChangeMotionQualityTest());
        smokeTest(new ChangeMotionTypeTest());
        smokeTest(new ChangeObjectLayerTest());
        smokeTest(new ChangeShapeTest());
        smokeTest(new ContactListenerTest());
        smokeTest(new ContactManifoldTest());
        smokeTest(new ConveyorBeltTest());
        smokeTest(new DampingTest());
        smokeTest(new DynamicMeshTest());
        smokeTest(new EnhancedInternalEdgeRemovalTest());
        smokeTest(new FrictionPerTriangleTest());
        smokeTest(new FrictionTest());
        smokeTest(new FunnelTest());
        smokeTest(new GravityFactorTest());
        smokeTest(new GyroscopicForceTest());
        smokeTest(new HeavyOnLightTest());
        smokeTest(new HighSpeedTest());
        smokeTest(new IslandTest());
        smokeTest(new KinematicTest());
        smokeTest(new LoadSaveBinaryTest());
        smokeTest(new LoadSaveSceneTest());
        smokeTest(new ManifoldReductionTest());
        smokeTest(new ModifyMassTest());
        smokeTest(new PyramidTest());
        smokeTest(new RestitutionTest());
        smokeTest(new SensorTest());
    }

    /**
     * Smoke test the "rig" package.
     */
    private static void smokeTestRig() {
        smokeTest(new BigWorldTest());
        smokeTest(new CreateRigTest());
        smokeTest(new KinematicRigTest());
        smokeTest(new LoadRigTest());
        smokeTest(new LoadSaveBinaryRigTest());
        smokeTest(new LoadSaveRigTest());
        smokeTest(new PoweredRigTest());
        smokeTest(new RigPileTest());
        smokeTest(new SkeletonMapperTest());
    }

    /**
     * Smoke test the "scaledshapes" package.
     */
    private static void smokeTestScaledShapes() {
        smokeTest(new DynamicScaledShape());
        smokeTest(new ScaledBoxShapeTest());
        smokeTest(new ScaledCapsuleShapeTest());
        smokeTest(new ScaledConvexHullShapeTest());
        smokeTest(new ScaledCylinderShapeTest());
        smokeTest(new ScaledHeightFieldShapeTest());
        smokeTest(new ScaledMeshShapeTest());
        smokeTest(new ScaledMutableCompoundShapeTest());
        smokeTest(new ScaledOffsetCenterOfMassShapeTest());
        smokeTest(new ScaledPlaneShapeTest());
        smokeTest(new ScaledSphereShapeTest());
        smokeTest(new ScaledStaticCompoundShapeTest());
        smokeTest(new ScaledTaperedCapsuleShapeTest());
        smokeTest(new ScaledTaperedCylinderShapeTest());
        smokeTest(new ScaledTriangleShapeTest());
    }

    /**
     * Smoke test the "shapes" package.
     */
    private static void smokeTestShapes() {
        smokeTest(new BoxShapeTest());
        smokeTest(new CapsuleShapeTest());
        smokeTest(new ConvexHullShapeTest());
        smokeTest(new CylinderShapeTest());
        smokeTest(new DeformedHeightFieldShapeTest());
        smokeTest(new EmptyShapeTest());
        smokeTest(new HeightFieldShapeTest());
        smokeTest(new MeshShapeTest());
        smokeTest(new MeshShapeUserDataTest());
        smokeTest(new MutableCompoundShapeTest());
        smokeTest(new OffsetCenterOfMassShapeTest());
        smokeTest(new PlaneShapeTest());
        smokeTest(new RotatedTranslatedShapeTest());
        smokeTest(new SphereShapeTest());
        smokeTest(new StaticCompoundShapeTest());
        smokeTest(new TaperedCapsuleShapeTest());
        smokeTest(new TaperedCylinderShapeTest());
        smokeTest(new TriangleShapeTest());
    }

    /**
     * Smoke test the "softbody" package.
     */
    private static void smokeTestSoftBody() {
        smokeTest(new SoftBodyBendConstraintTest());
        smokeTest(new SoftBodyContactListenerTest());
        smokeTest(new SoftBodyCosseratRodConstraintTest());
        smokeTest(new SoftBodyCustomUpdateTest());
        smokeTest(new SoftBodyForceTest());
        smokeTest(new SoftBodyFrictionTest());
        smokeTest(new SoftBodyGravityFactorTest());
        smokeTest(new SoftBodyKinematicTest());
        smokeTest(new SoftBodyLRAConstraintTest());
        smokeTest(new SoftBodyPressureTest());
        smokeTest(new SoftBodyRestitutionTest());
        smokeTest(new SoftBodySensorTest());
        smokeTest(new SoftBodyShapesTest());
        smokeTest(new SoftBodySkinnedConstraintTest());
        smokeTest(new SoftBodyStressTest());
        smokeTest(new SoftBodyUpdatePositionTest());
        smokeTest(new SoftBodyVertexRadiusTest());
        smokeTest(new SoftBodyVsFastMovingTest());
    }
}
