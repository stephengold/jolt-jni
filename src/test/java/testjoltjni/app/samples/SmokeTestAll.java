/*
Copyright (c) 2024-2026 Stephen Gold

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

import com.beust.jcommander.JCommander;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.EPhysicsUpdateError;
import com.github.stephengold.joltjni.std.OfStream;
import testjoltjni.MyString;
import testjoltjni.TestUtils;
import testjoltjni.app.samples.broadphase.*;
import testjoltjni.app.samples.character.*;
import testjoltjni.app.samples.constraints.*;
import testjoltjni.app.samples.convexcollision.*;
import testjoltjni.app.samples.general.*;
import testjoltjni.app.samples.hair.*;
import testjoltjni.app.samples.rig.*;
import testjoltjni.app.samples.scaledshapes.*;
import testjoltjni.app.samples.shapes.*;
import testjoltjni.app.samples.softbody.*;
import testjoltjni.app.samples.tools.*;
import testjoltjni.app.samples.vehicle.*;
import testjoltjni.app.samples.water.*;

/**
 * Console app to perform a "smoke test" on each of the JoltPhysics "Samples"
 * tests.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class SmokeTestAll {
    // *************************************************************************
    // fields

    /**
     * compute queue shared by all test objects
     */
    private static ComputeQueue queue;
    /**
     * compute system shared by all test objects
     */
    private static ComputeSystem computeSystem;
    /**
     * physics debug renderer shared by all test objects
     */
    private static DebugRenderer renderer;
    /**
     * count invocations of {@code smokeTest()}
     */
    private static int numTests;
    /**
     * allocator shared by all physics test objects
     */
    private static TempAllocator tempAllocator;
    /**
     * parameters parsed from the command line
     */
    final private static TestParameters globalParameters = new TestParameters();
    // *************************************************************************
    // new methods exposed

    /**
     * Main entry point for the SmokeTestAll application.
     *
     * @param arguments the command-line arguments (not {@code null})
     */
    public static void main(String... arguments) {
        // Parse the command-line arguments:
        JCommander jCommander = new JCommander(globalParameters);
        jCommander.parse(arguments);
        jCommander.setProgramName("SmokeTestAll");
        if (globalParameters.helpOnly()) {
            jCommander.usage();
            System.exit(0);
        }

        TestUtils.traceAllocations = globalParameters.traceAllocations();
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        // Log the configuration:
        System.out.println(Jolt.getConfigurationString());
        System.out.print(" built-in compute systems:");
        System.out.print(Jolt.implementsComputeCpu() ? " CPU" : "");
        System.out.print(Jolt.implementsComputeDx12() ? " DX12" : "");
        System.out.print(Jolt.implementsComputeMtl() ? " Metal" : "");
        System.out.print(Jolt.implementsComputeVk() ? " Vulkan" : "");
        System.out.println();

        createSharedObjects();

        smokeTestAll();
    }
    // *************************************************************************
    // private methods

    /**
     * Allocate and initialize the shared DebugRenderer, TempAllocator,
     * ComputeSystem, and ComputeQueue.
     */
    private static void createSharedObjects() {
        // All tests share a single DebugRenderer:
        assert Jolt.implementsDebugRendering();
        String fileName = "SmokeTestAll.jor";
        int mode = StreamOutWrapper.out()
                | StreamOutWrapper.binary() | StreamOutWrapper.trunc();
        OfStream ofStream = new OfStream(fileName, mode);
        StreamOut streamOut = new StreamOutWrapper(ofStream);
        renderer = new DebugRendererRecorder(streamOut);

        // All tests share a single TempAllocator:
        int numBytes = 1 << 24; // 16 MiB
        tempAllocator = new TempAllocatorImpl(numBytes);

        // All tests share a single ComputeSystem:
        ComputeSystemResult csResult = ComputeSystem.createComputeSystem();
        if (csResult.hasError()) {
            System.out.println(csResult.getError());
            // If no GPU or driver, fall back upon a CPU compute system:
            csResult = ComputeSystem.createComputeSystemCpu();
        }
        assert !csResult.hasError();
        computeSystem = csResult.get().getPtr();
        Rtti rtti = computeSystem.getRtti();
        String systemName = rtti.getName();
        systemName = systemName.replace("ComputeSystem", "");
        systemName = systemName.replace("Impl", "");
        systemName = systemName.replace("MTL", "Metal");
        systemName = systemName.replace("VK", "Vulkan");
        System.out.printf("  using a %s compute system%n%n", systemName);

        switch (systemName) {
            case "CPU":
                // Register CPU compute shaders:
                ComputeSystem.hairRegisterShaders(computeSystem);
                break;

            case "Metal":
                // Assign a loader for Metal compute shaders:
                Loader mtlLoader = CustomLoader.newLoader(
                        "/mtl/com/github/stephengold");
                computeSystem.setShaderLoader(mtlLoader);
                break;

            case "Vulkan":
                // Assign a loader for Vulkan compute shaders:
                Loader vkLoader = CustomLoader.newLoader(
                        "/vk/com/github/stephengold");
                computeSystem.setShaderLoader(vkLoader);
                break;

            default:
                throw new RuntimeException("systemName = " + systemName);
        }

        // All tests share a single ComputeQueue:
        ComputeQueueResult queueResult = computeSystem.createComputeQueue();
        assert !queueResult.hasError();
        ComputeQueueRef queueRef = queueResult.get();
        queue = queueRef.getPtr();
    }

    /**
     * Allocate and initialize a {@code PhysicsSystem} in the customary
     * configuration.
     *
     * @param maxBodies the desired number of bodies (&ge;1)
     * @return a new system
     */
    private static PhysicsSystem newPhysicsSystem(int maxBodies) {
        BPLayerInterfaceImpl mapObj2Bp = new BPLayerInterfaceImpl();
        ObjectVsBroadPhaseLayerFilterImpl objVsBpFilter
                = new ObjectVsBroadPhaseLayerFilterImpl();
        ObjectLayerPairFilterImpl objVsObjFilter
                = new ObjectLayerPairFilterImpl();

        int numBodyMutexes = 0; // 0 means "use the default value"
        int maxBodyPairs = 5_000;
        int maxContacts = 9_000;
        PhysicsSystem result = new PhysicsSystem();
        result.init(maxBodies, numBodyMutexes, maxBodyPairs, maxContacts,
                mapObj2Bp, objVsBpFilter, objVsObjFilter);

        return result;
    }

    /**
     * Invoke key methods of the specified Test (with no test settings) to see
     * whether they crash.
     *
     * @param test the Test object to use (not {@code null})
     */
    private static void smokeTest(Test test) {
        String arguments = null;
        smokeTest(test, arguments);
    }

    /**
     * Invoke key methods of the specified Test (with the specified settings) to
     * see whether they crash.
     *
     * @param test the Test object to use (not {@code null})
     * @param settings a textual description of the test settings, or
     * {@code null} for none
     */
    private static void smokeTest(Test test, String settings) {
        int numSteps = globalParameters.numSteps();
        smokeTest(test, settings, numSteps);
    }

    /**
     * Invoke key methods of the specified Test (with the specified settings) to
     * see whether they crash.
     *
     * @param test the Test object to use (not {@code null})
     * @param settings a textual description of the test settings, or
     * {@code null} for none
     * @param numSteps the number of physics steps to simulate (&ge;0,
     * default=defaultNumSteps)
     */
    private static void smokeTest(Test test, String settings, int numSteps) {
        ++numTests;

        // Describe the test to System.out:
        String description = test.getClass().getSimpleName();
        if (settings != null && !settings.isBlank()) {
            description += " with " + settings;
        }
        System.out.printf("=== Test #%d:  %s for %d step%s%n",
                numTests, description, numSteps, (numSteps == 1) ? "" : "s");
        System.out.flush();

        test.SetDebugRenderer(renderer);
        test.SetTempAllocator(tempAllocator);
        test.SetComputeSystem(computeSystem, queue);

        // Create new job/physics systems for each test:
        int numThreads = -1; // autodetect
        JobSystem jobSystem = new JobSystemThreadPool(
                Jolt.cMaxPhysicsJobs, Jolt.cMaxPhysicsBarriers, numThreads);
        test.SetJobSystem(jobSystem);

        int maxBodies = 6_600; // for HighSpeedTest "Convex Hull On Terrain1"
        PhysicsSystem physicsSystem = newPhysicsSystem(maxBodies);
        test.SetPhysicsSystem(physicsSystem);

        // Create a new contact listener for each test:
        ContactListener listener = new SamplesContactListener(test);
        physicsSystem.setContactListener(listener);

        test.Initialize();

        // Single-step the physics numSteps times:
        for (int stepIndex = 1; stepIndex <= numSteps; ++stepIndex) {
            if (globalParameters.verboseLogging()) {
                System.out.printf(
                        "---- step #%d%n      pre-update%n", stepIndex);
                System.out.flush();
            }
            PreUpdateParams params = new PreUpdateParams();
            params.mDeltaTime = 1f / 60;
            test.PrePhysicsUpdate(params);

            if (globalParameters.verboseLogging()) {
                System.out.printf("      update%n");
                System.out.flush();
            }
            int collisionSteps = 1;
            int errors = physicsSystem.update(params.mDeltaTime, collisionSteps,
                    tempAllocator, jobSystem);
            assert errors == EPhysicsUpdateError.None : errors;

            if (globalParameters.verboseLogging()) {
                System.out.printf("      post-update%n");
                System.out.flush();
            }
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
        for (CharacterBaseTest.EType shape : CharacterBaseTest.EType.values()) {
            for (String scene : CharacterBaseTest.sScenes) {
                String settings
                        = "shape=" + shape + " scene=" + MyString.quote(scene);

                CharacterTest test = new CharacterTest();
                test.sShapeType = shape;
                test.sSceneName = scene;
                smokeTest(test, settings);

                CharacterVirtualTest cvTest = new CharacterVirtualTest();
                cvTest.sShapeType = shape;
                cvTest.sSceneName = scene;
                smokeTest(cvTest, settings);
            }
        }

        // constraints package:
        smokeTestConstraints();

        // convex-collision package:
        smokeTest(new CapsuleVsBoxTest());
        smokeTest(new ClosestPointTest());
        smokeTest(new ConvexHullShrinkTest());
        smokeTest(new ConvexHullTest());
        smokeTest(new EPATest());
        smokeTest(new InteractivePairsTest());
        // TODO RandomRayTest (uses templates)

        smokeTestGeneral();

        // hair package:
        smokeTest(new HairCollisionTest());
        for (String scene : HairGravityPreloadTest.sScenes) {
            String settings = "scene=" + MyString.quote(scene);

            HairGravityPreloadTest test = new HairGravityPreloadTest();
            test.sSceneName = scene;
            smokeTest(test, settings);
        }
        smokeTest(new HairTest());

        smokeTestRig();
        smokeTestScaledShapes();
        smokeTestShapes();
        smokeTestSoftBody();

        // tools package:
        smokeTest(new LoadSnapshotTest());

        // vehicle package:
        for (String scene : VehicleTest.sScenes) {
            String settings = "scene=" + MyString.quote(scene);

            MotorcycleTest mcTest = new MotorcycleTest();
            mcTest.sSceneName = scene;
            smokeTest(mcTest, settings);

            TankTest tankTest = new TankTest();
            tankTest.sSceneName = scene;
            smokeTest(tankTest, settings);

            VehicleConstraintTest vcTest = new VehicleConstraintTest();
            vcTest.sSceneName = scene;
            smokeTest(vcTest, settings);

            VehicleSixDOFTest vsdTest = new VehicleSixDOFTest();
            vsdTest.sSceneName = scene;
            smokeTest(vsdTest, settings);

            VehicleStressTest vsTest = new VehicleStressTest();
            vsTest.sSceneName = scene;
            smokeTest(vsTest, settings);
        }

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

        for (int sceneI = 0; sceneI < HighSpeedTest.sScenes.length; ++sceneI) {
            String settings
                    = "scene=" + MyString.quote(HighSpeedTest.sScenes[sceneI]);

            HighSpeedTest test = new HighSpeedTest();
            test.sSelectedScene = sceneI;
            smokeTest(test, settings);
        }

        smokeTest(new IslandTest());
        smokeTest(new KinematicTest());
        smokeTest(new LoadSaveBinaryTest());
        smokeTest(new LoadSaveSceneTest());
        smokeTest(new ManifoldReductionTest());
        smokeTest(new ModifyMassTest());
        // TODO MultithreadedTest
        smokeTest(new PyramidTest());
        smokeTest(new RestitutionTest());
        smokeTest(new SensorTest());
        smokeTest(new ShapeFilterTest());
        // TODO SimCollideBodyVsBodyTest (uses templates)
        // TODO SimShapeFilterTest
        smokeTest(new SimpleTest());
        smokeTest(new StackTest());
        smokeTest(new TwoDFunnelTest());
        smokeTest(new WallTest());
    }

    /**
     * Smoke test the "rig" package.
     */
    private static void smokeTestRig() {
        smokeTest(new BigWorldTest());
        smokeTest(new CreateRigTest());

        for (String animation : KinematicRigTest.sAnimations) {
            String settings = "animation=" + MyString.quote(animation);

            KinematicRigTest test = new KinematicRigTest();
            test.sAnimationName = animation;
            smokeTest(test, settings);
        }

        for (EConstraintOverride override : EConstraintOverride.values()) {
            if (override == EConstraintOverride.TypeSlider) {
                continue; // native assert in CalculateConstraintProperties()
            }
            String settings = "override=" + override;

            LoadRigTest test = new LoadRigTest();
            test.sConstraintType = override;
            smokeTest(test, settings);
        }

        smokeTest(new LoadSaveBinaryRigTest());
        smokeTest(new LoadSaveRigTest());

        for (String animation : PoweredRigTest.sAnimations) {
            if (animation.equals("dead_pose3")) {
                continue; // issue #2059
            }
            String settings = "animation=" + MyString.quote(animation);

            PoweredRigTest test = new PoweredRigTest();
            test.sAnimationName = animation;
            smokeTest(test, settings);
        }

        for (String scene : RigPileTest.sScenes) {
            String settings = "scene=" + MyString.quote(scene);

            RigPileTest test = new RigPileTest();
            test.sSceneName = scene;
            smokeTest(test, settings);
        }

        smokeTest(new SkeletonMapperTest());
        smokeTest(new SoftKeyframedRigTest());
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

        for (int tt = 0; tt < HeightFieldShapeTest.sTerrainTypes.length; ++tt) {
            String terrain = HeightFieldShapeTest.sTerrainTypes[tt];
            String settings = "terrain=" + MyString.quote(terrain);

            HeightFieldShapeTest test = new HeightFieldShapeTest();
            test.sTerrainType = tt;
            smokeTest(test, settings);
        }

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

        for (String scene : SoftBodyStressTest.sScenes) {
            String settings = "scene=" + MyString.quote(scene);

            SoftBodyStressTest test = new SoftBodyStressTest();
            test.sSceneName = scene;
            smokeTest(test, settings);
        }

        smokeTest(new SoftBodyUpdatePositionTest());
        smokeTest(new SoftBodyVertexRadiusTest());
        smokeTest(new SoftBodyVsFastMovingTest());
    }
}
