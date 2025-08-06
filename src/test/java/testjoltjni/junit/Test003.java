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
package testjoltjni.junit;

import com.github.stephengold.joltjni.AaBox;
import com.github.stephengold.joltjni.AaBoxCast;
import com.github.stephengold.joltjni.BodyCreationSettings;
import com.github.stephengold.joltjni.BodyIdArray;
import com.github.stephengold.joltjni.BoxShape;
import com.github.stephengold.joltjni.BoxShapeSettings;
import com.github.stephengold.joltjni.CapsuleShape;
import com.github.stephengold.joltjni.CharacterSettings;
import com.github.stephengold.joltjni.CharacterVirtual;
import com.github.stephengold.joltjni.CharacterVirtualSettings;
import com.github.stephengold.joltjni.CollisionGroup;
import com.github.stephengold.joltjni.ContactSettings;
import com.github.stephengold.joltjni.GroupFilterTable;
import com.github.stephengold.joltjni.JobSystem;
import com.github.stephengold.joltjni.JobSystemSingleThreaded;
import com.github.stephengold.joltjni.JobSystemThreadPool;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.MassProperties;
import com.github.stephengold.joltjni.Mat44;
import com.github.stephengold.joltjni.MotionProperties;
import com.github.stephengold.joltjni.PhysicsSystem;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.SkinWeight;
import com.github.stephengold.joltjni.SoftBodyCreationSettings;
import com.github.stephengold.joltjni.SoftBodyMotionProperties;
import com.github.stephengold.joltjni.SoftBodySharedSettings;
import com.github.stephengold.joltjni.SphereShape;
import com.github.stephengold.joltjni.SpringSettings;
import com.github.stephengold.joltjni.TempAllocator;
import com.github.stephengold.joltjni.TempAllocatorImpl;
import com.github.stephengold.joltjni.TempAllocatorImplWithMallocFallback;
import com.github.stephengold.joltjni.TempAllocatorMalloc;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.VehicleConstraintSettings;
import com.github.stephengold.joltjni.Vertex;
import com.github.stephengold.joltjni.WheelSettings;
import com.github.stephengold.joltjni.WheelSettingsTv;
import com.github.stephengold.joltjni.WheelSettingsWv;
import com.github.stephengold.joltjni.WheeledVehicleControllerSettings;
import com.github.stephengold.joltjni.enumerate.EAllowedDofs;
import com.github.stephengold.joltjni.enumerate.EMotionQuality;
import com.github.stephengold.joltjni.enumerate.EMotionType;
import com.github.stephengold.joltjni.enumerate.EOverrideMassProperties;
import com.github.stephengold.joltjni.enumerate.ESpringMode;
import com.github.stephengold.joltjni.readonly.ConstBodyCreationSettings;
import com.github.stephengold.joltjni.readonly.ConstBoxShapeSettings;
import com.github.stephengold.joltjni.readonly.ConstCollisionGroup;
import com.github.stephengold.joltjni.readonly.ConstMassProperties;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.ConstSoftBodyCreationSettings;
import com.github.stephengold.joltjni.readonly.ConstSoftBodySharedSettings;
import com.github.stephengold.joltjni.readonly.ConstVertex;
import com.github.stephengold.joltjni.readonly.ConstWheelSettings;
import com.github.stephengold.joltjni.readonly.ConstWheelSettingsTv;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.vhacd.FillMode;
import com.github.stephengold.joltjni.vhacd.Parameters;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Automated JUnit4 tests for creation, destruction, accessors, and defaults of
 * various Jolt-Physics objects.
 * <p>
 * For {@code ShapeSettings} subclasses, see Test006.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test003 {
    // *************************************************************************
    // new methods exposed

    /**
     * Test object creation, destruction, accessors, and defaults.
     */
    @Test
    public void test003() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        doAaBox();
        doAaBoxCast();
        doBodyCreationSettings();
        doBodyIdArray();
        doCharacter();
        doCharacterVirtual();
        doCollisionGroup();
        doContactSettings();
        doJobSystemSingleThreaded();
        doJobSystemThreadPool();
        doMassProperties();
        doMotionProperties();
        doParameters();
        doSkinWeight();
        doSoftBodyCreationSettings();
        doSoftBodyMotionProperties();
        doSpringSettings();
        doTempAllocatorImpl();
        doTempAllocatorImplWithMallocFallback();
        doTempAllocatorMalloc();
        doVehicleConstraintSettings();
        doVertex();
        doWvControllerSettings();
        doWheelSettingsTv();
        doWheelSettingsWv();

        TestUtils.cleanup();
    }
    // *************************************************************************
    // Java private methods

    /**
     * Test the {@code AaBox} class.
     */
    private static void doAaBox() {
        { // no-arg constructor:
            AaBox box = new AaBox();

            Assert.assertTrue(box.hasAssignedNativeObject());
            Assert.assertTrue(box.ownsNativeObject());
            Assert.assertNotEquals(0L, box.va());

            Assert.assertFalse(box.isValid());

            TestUtils.testClose(box);
        }
        {
            AaBox box = AaBox.sBiggest();

            Assert.assertTrue(box.hasAssignedNativeObject());
            Assert.assertTrue(box.ownsNativeObject());
            Assert.assertNotEquals(0L, box.va());

            TestUtils.assertEquals(0f, 0f, 0f, box.getCenter(), 0f);
            float halfMax = 0.5f * Float.MAX_VALUE;
            TestUtils.assertEquals(
                    halfMax, halfMax, halfMax, box.getExtent(), 0f);
            TestUtils.assertEquals(halfMax, halfMax, halfMax, box.getMax(), 0f);
            TestUtils.assertEquals(
                    -halfMax, -halfMax, -halfMax, box.getMin(), 0f);
            TestUtils.assertEquals(Float.MAX_VALUE, Float.MAX_VALUE,
                    Float.MAX_VALUE, box.getSize(), 0f);
            Assert.assertTrue(box.isValid());

            TestUtils.testClose(box);
        }
        { // min-max constructor:
            Vec3Arg max = new Vec3(4f, 5f, 6f);
            Vec3Arg min = new Vec3(1f, 2f, 3f);
            AaBox box = new AaBox(min, max);

            Assert.assertTrue(box.hasAssignedNativeObject());
            Assert.assertTrue(box.ownsNativeObject());
            Assert.assertNotEquals(0L, box.va());

            TestUtils.assertEquals(2.5f, 3.5f, 4.5f, box.getCenter(), 0f);
            TestUtils.assertEquals(4f, 5f, 6f, box.getMax(), 0f);
            TestUtils.assertEquals(1.5f, 1.5f, 1.5f, box.getExtent(), 0f);
            TestUtils.assertEquals(1f, 2f, 3f, box.getMin(), 0f);
            TestUtils.assertEquals(3f, 3f, 3f, box.getSize(), 0f);
            Assert.assertTrue(box.isValid());

            TestUtils.testClose(box);
        }
        { // center-and-extent constructor:
            Vec3Arg center = new Vec3(4f, 3f, 2f);
            float radius = 5f;
            AaBox box = new AaBox(center, radius);

            Assert.assertTrue(box.hasAssignedNativeObject());
            Assert.assertTrue(box.ownsNativeObject());
            Assert.assertNotEquals(0L, box.va());

            Equivalent.vec3(center, box.getCenter(), 0f);
            TestUtils.assertEquals(9f, 8f, 7f, box.getMax(), 0f);
            TestUtils.assertEquals(radius, radius, radius, box.getExtent(), 0f);
            TestUtils.assertEquals(-1f, -2f, -3f, box.getMin(), 0f);
            TestUtils.assertEquals(10f, 10f, 10f, box.getSize(), 0f);
            Assert.assertTrue(box.isValid());

            TestUtils.testClose(box);
        }

        System.gc();
    }

    /**
     * Test the {@code AaBoxCast} class.
     */
    private static void doAaBoxCast() {
        AaBoxCast cast = new AaBoxCast();

        Assert.assertTrue(cast.hasAssignedNativeObject());
        Assert.assertTrue(cast.ownsNativeObject());
        Assert.assertNotEquals(0L, cast.va());

        Assert.assertFalse(cast.getBox().isValid());
        TestUtils.assertEquals(0f, 0f, 0f, cast.getDirection(), 0f);

        cast.setBox(new AaBox(new Vec3(1f, 2f, 3f), new Vec3(4f, 5f, 6f)));
        cast.setDirection(new Vec3(7f, 8f, 9f));

        TestUtils.assertEquals(1f, 2f, 3f, cast.getBox().getMin(), 0f);
        TestUtils.assertEquals(4f, 5f, 6f, cast.getBox().getMax(), 0f);
        TestUtils.assertEquals(7f, 8f, 9f, cast.getDirection(), 0f);

        TestUtils.testClose(cast);

        System.gc();
    }

    /**
     * Test the {@code BodyCreationSettings} class.
     */
    private static void doBodyCreationSettings() {
        { // no-arg constructor:
            BodyCreationSettings bcs = new BodyCreationSettings();

            Assert.assertNull(bcs.getMassProperties());
            Assert.assertNull(bcs.getShape());
            Assert.assertNull(bcs.getShapeSettings());
            testBcsDefaults(bcs);
            testBcsSetters(bcs);

            TestUtils.testClose(bcs);
        }
        { // copy constructor:
            ConstBodyCreationSettings original = new BodyCreationSettings();
            BodyCreationSettings copy = new BodyCreationSettings(original);

            Assert.assertNull(copy.getMassProperties());
            Assert.assertNull(copy.getShape());
            Assert.assertNull(copy.getShapeSettings());
            testBcsDefaults(copy);
            testBcsSetters(copy);

            TestUtils.testClose(copy, original);
        }
        { // constructed from a ShapeSettings:
            ConstBoxShapeSettings ss = new BoxShapeSettings(1f, 1f, 1f);
            int objectLayer = 0;
            BodyCreationSettings bcs = new BodyCreationSettings(ss,
                    new RVec3(), new Quat(), EMotionType.Dynamic, objectLayer);

            Assert.assertNotNull(bcs.getMassProperties());
            Assert.assertNotNull(bcs.getShape());
            Assert.assertEquals(ss, bcs.getShapeSettings());
            Assert.assertTrue(bcs.getShape() instanceof BoxShape);
            testBcsDefaults(bcs);
            testBcsSetters(bcs);

            TestUtils.testClose(bcs, ss);
        }
        { // constructed from a Shape:
            ConstShape shape = new SphereShape(1f);
            int objectLayer = 0;
            BodyCreationSettings bcs = new BodyCreationSettings(shape,
                    new RVec3(), new Quat(), EMotionType.Dynamic, objectLayer);

            Assert.assertNotNull(bcs.getMassProperties());
            Assert.assertEquals(shape, bcs.getShape());
            Assert.assertNull(bcs.getShapeSettings());
            testBcsDefaults(bcs);
            testBcsSetters(bcs);

            TestUtils.testClose(bcs, shape);
        }

        System.gc();
    }

    /**
     * Test the {@code BodyIdArray} class.
     */
    private static void doBodyIdArray() {
        { // int constructor:
            BodyIdArray bodyIdArray = new BodyIdArray(3);
            Assert.assertEquals(3, bodyIdArray.length());
            Assert.assertTrue(bodyIdArray.hasAssignedNativeObject());
            Assert.assertTrue(bodyIdArray.ownsNativeObject());
            Assert.assertNotEquals(0L, bodyIdArray.va());

            bodyIdArray.set(0, 2);
            bodyIdArray.set(1, 4);
            bodyIdArray.set(2, 10);

            Assert.assertEquals(2, bodyIdArray.get(0));
            Assert.assertEquals(4, bodyIdArray.get(1));
            Assert.assertEquals(10, bodyIdArray.get(2));

            TestUtils.testClose(bodyIdArray);
        }
        { // int[] constructor:
            int[] idArray = {1, 5, 7, 19, 28, 49};
            BodyIdArray bodyIdArray = new BodyIdArray(idArray);
            int bodyIdArrayLength = bodyIdArray.length();

            Assert.assertEquals(idArray.length, bodyIdArrayLength);
            Assert.assertTrue(bodyIdArray.hasAssignedNativeObject());
            Assert.assertTrue(bodyIdArray.ownsNativeObject());
            Assert.assertNotEquals(0L, bodyIdArray.va());

            for (int i = 0; i < bodyIdArrayLength; i += 1) {
                int bodyId = idArray[i];
                int bodyIdRecovered = bodyIdArray.get(i);

                Assert.assertEquals(bodyId, bodyIdRecovered);
            }

            bodyIdArray.set(3, 501);
            bodyIdArray.set(2, 689);
            bodyIdArray.set(0, 600);

            Assert.assertEquals(501, bodyIdArray.get(3));
            Assert.assertEquals(689, bodyIdArray.get(2));
            Assert.assertEquals(600, bodyIdArray.get(0));

            TestUtils.testClose(bodyIdArray);
        }
        { // IntBuffer constructor:
            IntBuffer idBuffer = Jolt.newDirectIntBuffer(3);
            idBuffer.put(2);
            idBuffer.put(5);
            idBuffer.put(6);

            BodyIdArray bodyIdArray = new BodyIdArray(idBuffer);
            Assert.assertTrue(bodyIdArray.hasAssignedNativeObject());
            Assert.assertTrue(bodyIdArray.ownsNativeObject());
            Assert.assertNotEquals(0L, bodyIdArray.va());

            Assert.assertEquals(3, bodyIdArray.length());
            Assert.assertEquals(2, bodyIdArray.get(0));
            Assert.assertEquals(5, bodyIdArray.get(1));
            Assert.assertEquals(6, bodyIdArray.get(2));

            bodyIdArray.set(0, 20);
            bodyIdArray.set(1, 65);

            Assert.assertEquals(20, bodyIdArray.get(0));
            Assert.assertEquals(65, bodyIdArray.get(1));

            TestUtils.testClose(bodyIdArray);
        }
        { // List<Integer> constructor:
            List<Integer> idList = new ArrayList<>();
            idList.addAll(Arrays.asList(3, 4, 6, 7, 3, 4, 6, 102));

            BodyIdArray bodyIdArray = new BodyIdArray(idList);
            int bodyIdArrayLength = bodyIdArray.length();

            Assert.assertEquals(idList.size(), bodyIdArrayLength);
            Assert.assertTrue(bodyIdArray.hasAssignedNativeObject());
            Assert.assertTrue(bodyIdArray.ownsNativeObject());
            Assert.assertNotEquals(0L, bodyIdArray.va());

            for (int i = 0; i < bodyIdArrayLength; i += 1) {
                int bodyId = idList.get(i);
                int bodyIdRecovered = bodyIdArray.get(i);

                Assert.assertEquals(bodyId, bodyIdRecovered);
            }

            bodyIdArray.set(0, 501);
            bodyIdArray.set(2, 689);
            bodyIdArray.set(5, 600);

            Assert.assertEquals(501, bodyIdArray.get(0));
            Assert.assertEquals(689, bodyIdArray.get(2));
            Assert.assertEquals(600, bodyIdArray.get(5));

            TestUtils.testClose(bodyIdArray);
        }

        System.gc();
    }

    /**
     * Test the {@code Character} class.
     */
    private static void doCharacter() {
        float radius = 1f; // meters
        float height = 2f; // meters
        ConstShape shape = new CapsuleShape(height / 2f, radius);

        CharacterSettings settings = new CharacterSettings();
        settings.setShape(shape);

        int maxBodies = 1;
        PhysicsSystem system = TestUtils.newPhysicsSystem(maxBodies);
        com.github.stephengold.joltjni.Character character
                = new com.github.stephengold.joltjni.Character(
                        settings, new RVec3(), new Quat(), 0L, system);

        testCharacterDefaults(character);

        TestUtils.testClose(character, system, settings, shape);
        System.gc();
    }

    /**
     * Test the {@code CharacterVirtual} class.
     */
    private static void doCharacterVirtual() {
        CharacterVirtualSettings settings = new CharacterVirtualSettings();
        PhysicsSystem system = new PhysicsSystem();
        CharacterVirtual character = new CharacterVirtual(
                settings, new RVec3(), new Quat(), 0L, system);

        testCharacterVirtualDefaults(character);

        TestUtils.testClose(character, system, settings);
        System.gc();
    }

    /**
     * Test the {@code CollisionGroup} class.
     */
    private static void doCollisionGroup() {
        CollisionGroup group = new CollisionGroup();

        testCollisionGroupDefaults(group);
        testCollisionGroupSetters(group);

        TestUtils.testClose(group);
        System.gc();
    }

    /**
     * Test the {@code ContactSettings} class.
     */
    private static void doContactSettings() {
        ContactSettings settings = new ContactSettings();

        testContactSettingsDefaults(settings);
        testContactSettingsSetters(settings);

        TestUtils.testClose(settings);
        System.gc();
    }

    /**
     * Test the {@code JobSystemSingleThreaded} class.
     */
    private static void doJobSystemSingleThreaded() {
        JobSystem jobSystem = new JobSystemSingleThreaded(Jolt.cMaxPhysicsJobs);

        Assert.assertEquals(1, jobSystem.getMaxConcurrency());
        Assert.assertTrue(jobSystem.hasAssignedNativeObject());
        Assert.assertTrue(jobSystem.ownsNativeObject());
        Assert.assertNotEquals(0L, jobSystem.va());

        TestUtils.testClose(jobSystem);
        System.gc();
    }

    /**
     * Test the {@code JobSystemThreadPool} class.
     */
    private static void doJobSystemThreadPool() {
        JobSystemThreadPool jobSystem = new JobSystemThreadPool(
                Jolt.cMaxPhysicsJobs, Jolt.cMaxPhysicsBarriers);

        int numCpus = Runtime.getRuntime().availableProcessors();
        Assert.assertEquals(numCpus, jobSystem.getMaxConcurrency());

        Assert.assertTrue(jobSystem.hasAssignedNativeObject());
        Assert.assertTrue(jobSystem.ownsNativeObject());
        Assert.assertNotEquals(0L, jobSystem.va());

        jobSystem.setNumThreads(3);
        Assert.assertEquals(4, jobSystem.getMaxConcurrency());

        TestUtils.testClose(jobSystem);
        System.gc();
    }

    /**
     * Test the {@code MassProperties} class.
     */
    private static void doMassProperties() {
        MassProperties props = new MassProperties();

        testMpDefaults(props);
        testMpSetters(props);

        TestUtils.testClose(props);
        System.gc();
    }

    /**
     * Test the {@code MotionProperties} class.
     */
    private static void doMotionProperties() {
        MotionProperties props = new MotionProperties();

        testMotionDefaults(props);
        testMotionSetters(props);

        TestUtils.testClose(props);
        System.gc();
    }

    /**
     * Test the {@code Parameters} class.
     */
    private static void doParameters() {
        Parameters parameters = new Parameters();

        testParametersDefaults(parameters);
        testParametersSetters(parameters);

        TestUtils.testClose(parameters);
        System.gc();
    }

    /**
     * Test the {@code SkinWeight} class.
     */
    private static void doSkinWeight() {
        SkinWeight weight = new SkinWeight();
        testSkinWeightDefaults(weight);

        TestUtils.testClose(weight);
        System.gc();
    }

    /**
     * Test the {@code SoftBodyCreationSettings} class.
     */
    private static void doSoftBodyCreationSettings() {
        { // no-arg constructor:
            SoftBodyCreationSettings sbcs = new SoftBodyCreationSettings();

            Assert.assertNull(sbcs.getSettings());
            testSbcsDefaults(sbcs);
            testSbcsSetters(sbcs);

            TestUtils.testClose(sbcs);
        }
        { // copy constructor:
            ConstSoftBodyCreationSettings original
                    = new SoftBodyCreationSettings();
            SoftBodyCreationSettings copy
                    = new SoftBodyCreationSettings(original);

            Assert.assertNull(copy.getSettings());
            testSbcsDefaults(copy);
            testSbcsSetters(copy);

            TestUtils.testClose(copy, original);
        }
        { // constructed from a SoftBodySharedSettings:
            ConstSoftBodySharedSettings sbss = new SoftBodySharedSettings();
            RVec3Arg location = new RVec3();
            QuatArg orientation = new Quat();
            int objectLayer = 0;
            SoftBodyCreationSettings sbcs = new SoftBodyCreationSettings(
                    sbss, location, orientation, objectLayer);

            Assert.assertEquals(sbss.targetVa(), sbcs.getSettings().targetVa());
            testSbcsDefaults(sbcs);
            testSbcsSetters(sbcs);

            TestUtils.testClose(sbcs, sbss);
        }

        System.gc();
    }

    /**
     * Test the {@code SoftBodyMotionProperties} class.
     */
    private static void doSoftBodyMotionProperties() {
        SoftBodyMotionProperties properties = new SoftBodyMotionProperties();

        testSoftBodyMotionPropertiesDefaults(properties);
        testSoftBodyMotionPropertiesSetters(properties);

        TestUtils.testClose(properties);
        System.gc();
    }

    /**
     * Test the {@code SpringSettings} class.
     */
    private static void doSpringSettings() {
        SpringSettings ss = new SpringSettings();

        testSpringSettingsDefaults(ss);
        testSpringSettingsSetters(ss);

        TestUtils.testClose(ss);
        System.gc();
    }

    /**
     * Test the {@code TempAllocatorImpl} class.
     */
    private static void doTempAllocatorImpl() {
        int numBytes = 1 << 8;
        TempAllocator tempAllocator = new TempAllocatorImpl(numBytes);

        Assert.assertTrue(tempAllocator.hasAssignedNativeObject());
        Assert.assertTrue(tempAllocator.ownsNativeObject());
        Assert.assertNotEquals(0L, tempAllocator.va());

        TestUtils.testClose(tempAllocator);
        System.gc();
    }

    /**
     * Test the {@code TempAllocatorImplWithMallocFallback} class.
     */
    private static void doTempAllocatorImplWithMallocFallback() {
        int numBytes = 1 << 8;
        TempAllocator tempAllocator
                = new TempAllocatorImplWithMallocFallback(numBytes);

        Assert.assertTrue(tempAllocator.hasAssignedNativeObject());
        Assert.assertTrue(tempAllocator.ownsNativeObject());
        Assert.assertNotEquals(0L, tempAllocator.va());

        TestUtils.testClose(tempAllocator);
        System.gc();
    }

    /**
     * Test the {@code TempAllocatorMalloc} class.
     */
    private static void doTempAllocatorMalloc() {
        TempAllocator tempAllocator = new TempAllocatorMalloc();

        Assert.assertTrue(tempAllocator.hasAssignedNativeObject());
        Assert.assertTrue(tempAllocator.ownsNativeObject());
        Assert.assertNotEquals(0L, tempAllocator.va());

        TestUtils.testClose(tempAllocator);
        System.gc();
    }

    /**
     * Test the {@code VehicleConstraintSettings} class.
     */
    private static void doVehicleConstraintSettings() {
        VehicleConstraintSettings vcs = new VehicleConstraintSettings();

        testVehicleConstraintSettingsDefaults(vcs);
        testVehicleConstraintSettingsSetters(vcs);

        TestUtils.testClose(vcs);
        System.gc();
    }

    /**
     * Test the {@code Vertex} class.
     */
    private static void doVertex() {
        Vertex vertex = new Vertex();

        testVertexDefaults(vertex);
        testVertexSetters(vertex);

        TestUtils.testClose(vertex);
        System.gc();
    }

    /**
     * Test the {@code WheelSettingsTv} class.
     */
    private static void doWheelSettingsTv() {
        WheelSettingsTv wstv = new WheelSettingsTv();

        testWheelSettingsTvDefaults(wstv);
        testWheelSettingsTvSetters(wstv);

        TestUtils.testClose(wstv);
        System.gc();
    }

    /**
     * Test the {@code WheelSettingsWv} class.
     */
    private static void doWheelSettingsWv() {
        WheelSettingsWv wswv = new WheelSettingsWv();

        testWheelSettingsWvDefaults(wswv);
        testWheelSettingsWvSetters(wswv);

        TestUtils.testClose(wswv);
        System.gc();
    }

    /**
     * Test the {@code WheeledVehicleControllerSettings} class.
     */
    private static void doWvControllerSettings() {
        WheeledVehicleControllerSettings wvcs
                = new WheeledVehicleControllerSettings();

        testWvControllerSettingsDefaults(wvcs);
        testWvControllerSettingsSetters(wvcs);

        TestUtils.testClose(wvcs);
        System.gc();
    }

    /**
     * Test the getters and defaults of the specified
     * {@code BodyCreationSettings}.
     *
     * @param bcs the settings to test (not null, unaffected)
     */
    private static void testBcsDefaults(ConstBodyCreationSettings bcs) {
        Assert.assertTrue(bcs.hasAssignedNativeObject());
        Assert.assertTrue(bcs.ownsNativeObject());

        Assert.assertFalse(bcs.getAllowDynamicOrKinematic());
        Assert.assertEquals(EAllowedDofs.All, bcs.getAllowedDofs());
        Assert.assertTrue(bcs.getAllowSleeping());
        Assert.assertEquals(0.05f, bcs.getAngularDamping(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, bcs.getAngularVelocity(), 0f);
        Assert.assertFalse(bcs.getApplyGyroscopicForce());
        Assert.assertFalse(bcs.getCollideKinematicVsNonDynamic());
        Assert.assertNull(bcs.getCollisionGroup().getGroupFilter());
        Assert.assertEquals(CollisionGroup.cInvalidGroup,
                bcs.getCollisionGroup().getGroupId());
        Assert.assertEquals(CollisionGroup.cInvalidSubGroup,
                bcs.getCollisionGroup().getSubGroupId());
        Assert.assertFalse(bcs.getEnhancedInternalEdgeRemoval());
        Assert.assertEquals(0.2f, bcs.getFriction(), 0f);
        Assert.assertEquals(1f, bcs.getGravityFactor(), 0f);
        Assert.assertEquals(1f, bcs.getInertiaMultiplier(), 0f);
        Assert.assertFalse(bcs.getIsSensor());
        Assert.assertEquals(0.05f, bcs.getLinearDamping(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, bcs.getLinearVelocity(), 0f);
        // caller should test getMassProperties()
        testMpDefaults(bcs.getMassPropertiesOverride());
        Assert.assertEquals(15 * Math.PI, bcs.getMaxAngularVelocity(), 1e-6f);
        Assert.assertEquals(500f, bcs.getMaxLinearVelocity(), 0f);
        Assert.assertEquals(EMotionQuality.Discrete, bcs.getMotionQuality());
        Assert.assertEquals(0, bcs.getNumPositionStepsOverride());
        Assert.assertEquals(0, bcs.getNumVelocityStepsOverride());
        Assert.assertEquals(EMotionType.Dynamic, bcs.getMotionType());
        Assert.assertEquals(0, bcs.getObjectLayer());
        Assert.assertEquals(EOverrideMassProperties.CalculateMassAndInertia,
                bcs.getOverrideMassProperties());
        TestUtils.assertEquals(0f, 0f, 0f, bcs.getPosition(), 0f);
        Assert.assertEquals(0f, bcs.getRestitution(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, 1f, bcs.getRotation(), 0f);
        // caller should test getShape() and getShapeSettings()
        Assert.assertTrue(bcs.getUseManifoldReduction());
        Assert.assertEquals(0L, bcs.getUserData());
        Assert.assertTrue(bcs.hasMassProperties());
    }

    /**
     * Test the setters of the specified {@code BodyCreationSettings}.
     *
     * @param bcs the settings to test (not null, modified)
     */
    private static void testBcsSetters(BodyCreationSettings bcs) {
        bcs.setAllowDynamicOrKinematic(true);
        bcs.setAllowedDofs(EAllowedDofs.Plane2D);
        bcs.setAllowSleeping(false);
        bcs.setAngularDamping(0.01f);
        bcs.setAngularVelocity(0.02f, 0.03f, 0.04f);
        bcs.setApplyGyroscopicForce(true);
        bcs.setCollideKinematicVsNonDynamic(true);

        GroupFilterTable filter = new GroupFilterTable();
        CollisionGroup group = new CollisionGroup(filter, 2, 3);
        bcs.setCollisionGroup(group);

        bcs.setEnhancedInternalEdgeRemoval(true);
        bcs.setFriction(0.05f);
        bcs.setGravityFactor(0.06f);
        bcs.setInertiaMultiplier(0.065f);
        bcs.setIsSensor(true);
        bcs.setLinearDamping(0.07f);
        bcs.setLinearVelocity(0.08f, 0.09f, 0.1f);
        bcs.setMaxAngularVelocity(0.101f);
        bcs.setMaxLinearVelocity(0.102f);
        bcs.setMotionQuality(EMotionQuality.LinearCast);
        bcs.setMotionType(EMotionType.Kinematic);
        bcs.getNumPositionStepsOverride(103);
        bcs.getNumVelocityStepsOverride(105);
        bcs.setObjectLayer(65_535);
        bcs.setOverrideMassProperties(
                EOverrideMassProperties.MassAndInertiaProvided);
        bcs.setPosition(new RVec3(0.12, 0.13, 0.14));
        bcs.setRestitution(0.15f);
        bcs.setRotation(new Quat(0.6f, 0f, 0f, 0.8f));
        bcs.setUseManifoldReduction(false);
        bcs.setUserData(20L);

        // Verify the new parameter values:
        Assert.assertTrue(bcs.getAllowDynamicOrKinematic());
        Assert.assertEquals(EAllowedDofs.Plane2D, bcs.getAllowedDofs());
        Assert.assertFalse(bcs.getAllowSleeping());
        Assert.assertEquals(0.01f, bcs.getAngularDamping(), 0f);
        TestUtils.assertEquals(
                0.02f, 0.03f, 0.04f, bcs.getAngularVelocity(), 0f);
        Assert.assertTrue(bcs.getApplyGyroscopicForce());
        Assert.assertTrue(bcs.getCollideKinematicVsNonDynamic());
        Equivalent.collisionGroup(group, bcs.getCollisionGroup());
        Assert.assertTrue(bcs.getEnhancedInternalEdgeRemoval());
        Assert.assertEquals(0.05f, bcs.getFriction(), 0f);
        Assert.assertEquals(0.06f, bcs.getGravityFactor(), 0f);
        Assert.assertEquals(0.065f, bcs.getInertiaMultiplier(), 0f);
        Assert.assertTrue(bcs.getIsSensor());
        Assert.assertEquals(0.07f, bcs.getLinearDamping(), 0f);
        TestUtils.assertEquals(0.08f, 0.09f, 0.1f, bcs.getLinearVelocity(), 0f);
        Assert.assertEquals(0.101f, bcs.getMaxAngularVelocity(), 0f);
        Assert.assertEquals(0.102f, bcs.getMaxLinearVelocity(), 0f);
        Assert.assertEquals(EMotionQuality.LinearCast, bcs.getMotionQuality());
        Assert.assertEquals(EMotionType.Kinematic, bcs.getMotionType());
        Assert.assertEquals(103, bcs.getNumPositionStepsOverride());
        Assert.assertEquals(105, bcs.getNumVelocityStepsOverride());
        Assert.assertEquals(65_535, bcs.getObjectLayer());
        Assert.assertEquals(EOverrideMassProperties.MassAndInertiaProvided,
                bcs.getOverrideMassProperties());
        TestUtils.assertEquals(0.12f, 0.13f, 0.14f, bcs.getPosition(), 0f);
        Assert.assertEquals(0.15f, bcs.getRestitution(), 0f);
        TestUtils.assertEquals(0.6f, 0f, 0f, 0.8f, bcs.getRotation(), 0f);
        Assert.assertFalse(bcs.getUseManifoldReduction());
        Assert.assertEquals(20L, bcs.getUserData());
    }

    /**
     * Test the getters and defaults of the specified {@code Character}.
     *
     * @param character the character to test (not null, unaffected)
     */
    private static void testCharacterDefaults(
            com.github.stephengold.joltjni.Character character) {
        TestUtils.assertEquals(
                0f, 0f, 0f, character.getCenterOfMassPosition(), 0f);
        Assert.assertEquals(0, character.getLayer());
        TestUtils.assertEquals(0f, 0f, 0f, character.getLinearVelocity(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, character.getPosition(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, 1f, character.getRotation(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code CharacterVirtual}.
     *
     * @param character the character to test (not null, unaffected)
     */
    private static void testCharacterVirtualDefaults(
            CharacterVirtual character) {
        Assert.assertFalse(character.getEnhancedInternalEdgeRemoval());
        Assert.assertEquals(1f, character.getHitReductionCosMaxAngle(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, character.getLinearVelocity(), 0f);
        Assert.assertEquals(70f, character.getMass(), 0f);
        Assert.assertEquals(256, character.getMaxNumHits());
        Assert.assertEquals(100f, character.getMaxStrength(), 0f);
        Assert.assertEquals(1f, character.getPenetrationRecoverySpeed(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, character.getPosition(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, 1f, character.getRotation(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, character.getShapeOffset(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code CollisionGroup}.
     *
     * @param group the group to test (not null, unaffected)
     */
    private static void testCollisionGroupDefaults(ConstCollisionGroup group) {
        Assert.assertNull(group.getGroupFilter());
        Assert.assertEquals(CollisionGroup.cInvalidGroup, group.getGroupId());
        Assert.assertEquals(
                CollisionGroup.cInvalidGroup, group.getSubGroupId());
    }

    /**
     * Test the setters of the specified {@code CollisionGroup}.
     *
     * @param group the group to test (not null)
     */
    private static void testCollisionGroupSetters(CollisionGroup group) {
        GroupFilterTable filter = new GroupFilterTable();
        group.setGroupFilter(filter);
        group.setGroupId(101);
        group.setSubGroupId(102);

        Assert.assertEquals(filter.va(), group.getGroupFilter().targetVa());
        Assert.assertEquals(101, group.getGroupId());
        Assert.assertEquals(102, group.getSubGroupId());
    }

    /**
     * Test the getters and defaults of the specified {@code ContactSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testContactSettingsDefaults(ContactSettings settings) {
        Assert.assertEquals(0f, settings.getCombinedFriction(), 0f);
        Assert.assertEquals(0f, settings.getCombinedRestitution(), 0f);
        Assert.assertEquals(1f, settings.getInvInertiaScale1(), 0f);
        Assert.assertEquals(1f, settings.getInvInertiaScale2(), 0f);
        Assert.assertEquals(1f, settings.getInvMassScale1(), 0f);
        Assert.assertEquals(1f, settings.getInvMassScale2(), 0f);
        Assert.assertFalse(settings.getIsSensor());
        TestUtils.assertEquals(0f, 0f, 0f,
                settings.getRelativeAngularSurfaceVelocity(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f,
                settings.getRelativeLinearSurfaceVelocity(), 0f);
    }

    /**
     * Test the setters of the specified {@code ContactSettings}.
     *
     * @param settings the settings to test (not null)
     */
    private static void testContactSettingsSetters(ContactSettings settings) {
        settings.setCombinedFriction(11f);
        settings.setCombinedRestitution(12f);
        settings.setInvInertiaScale1(13f);
        settings.setInvInertiaScale2(14f);
        settings.setInvMassScale1(15f);
        settings.setInvMassScale2(16f);
        settings.setIsSensor(true);
        settings.setRelativeAngularSurfaceVelocity(new Vec3(17f, 18f, 19f));
        settings.setRelativeLinearSurfaceVelocity(new Vec3(20f, 21f, 22f));

        Assert.assertEquals(11f, settings.getCombinedFriction(), 0f);
        Assert.assertEquals(12f, settings.getCombinedRestitution(), 0f);
        Assert.assertEquals(13f, settings.getInvInertiaScale1(), 0f);
        Assert.assertEquals(14f, settings.getInvInertiaScale2(), 0f);
        Assert.assertEquals(15f, settings.getInvMassScale1(), 0f);
        Assert.assertEquals(16f, settings.getInvMassScale2(), 0f);
        Assert.assertTrue(settings.getIsSensor());
        TestUtils.assertEquals(17f, 18f, 19f,
                settings.getRelativeAngularSurfaceVelocity(), 0f);
        TestUtils.assertEquals(20f, 21f, 22f,
                settings.getRelativeLinearSurfaceVelocity(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code MotionProperties}.
     *
     * @param props the properties to test (not null, unaffected)
     */
    private static void testMotionDefaults(MotionProperties props) {
        Assert.assertTrue(props.hasAssignedNativeObject());
        Assert.assertTrue(props.ownsNativeObject());

        TestUtils.assertEquals(0f, 0f, 0f, props.getAccumulatedForce(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, props.getAccumulatedTorque(), 0f);
        Assert.assertEquals(EAllowedDofs.All, props.getAllowedDofs());
        Assert.assertFalse(props.getAllowSleeping());
        Assert.assertEquals(0f, props.getAngularDamping(), 0f);
        TestUtils.assertEquals(-1, -1, -1, 0, props.getAngularDofsMask());
        TestUtils.assertEquals(0f, 0f, 0f, props.getAngularVelocity(), 0f);
        Assert.assertEquals(0f, props.getGravityFactor(), 0f);
        Assert.assertEquals(0f, props.getInverseMassUnchecked(), 0f);
        Assert.assertEquals(0f, props.getLinearDamping(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, props.getLinearVelocity(), 0f);
        Assert.assertEquals(0f, props.getMaxAngularVelocity(), 0f);
        Assert.assertEquals(0f, props.getMaxLinearVelocity(), 0f);
        Assert.assertEquals(EMotionQuality.Discrete, props.getMotionQuality());
        Assert.assertEquals(0, props.getNumPositionStepsOverride());
        Assert.assertEquals(0, props.getNumVelocityStepsOverride());
    }

    /**
     * Test the setters of the specified {@code MotionProperties}.
     *
     * @param props the properties to test (not null, modified)
     */
    private static void testMotionSetters(MotionProperties props) {
        props.setAngularDamping(0.01f);
        Assert.assertEquals(0.01f, props.getAngularDamping(), 0f);

        props.setMaxAngularVelocity(10f);
        Assert.assertEquals(10f, props.getMaxAngularVelocity(), 0f);

        props.setAngularVelocity(new Vec3(0.02f, 0.03f, 0.04f));
        TestUtils.assertEquals(
                0.02f, 0.03f, 0.04f, props.getAngularVelocity(), 0f);

        props.setGravityFactor(0.05f);
        Assert.assertEquals(0.05f, props.getGravityFactor(), 0f);

        props.setLinearDamping(0.06f);
        Assert.assertEquals(0.06f, props.getLinearDamping(), 0f);

        props.setMaxLinearVelocity(11f);
        Assert.assertEquals(11f, props.getMaxLinearVelocity(), 0f);

        props.setLinearVelocity(new Vec3(0.07f, 0.08f, 0.09f));
        TestUtils.assertEquals(
                0.07f, 0.08f, 0.09f, props.getLinearVelocity(), 0f);

        props.setNumPositionStepsOverride(12);
        Assert.assertEquals(12, props.getNumPositionStepsOverride());

        props.setNumVelocityStepsOverride(13);
        Assert.assertEquals(13, props.getNumVelocityStepsOverride());
    }

    /**
     * Test the getters and defaults of the specified {@code MassProperties}.
     *
     * @param props the properties to test (not null, unaffected)
     */
    private static void testMpDefaults(ConstMassProperties props) {
        Assert.assertTrue(props.hasAssignedNativeObject());
        Assert.assertEquals(0f, props.getMass(), 0f);
        Assert.assertTrue(props.getInertia().isEqual(Mat44.sZero()));
    }

    /**
     * Test the setters of the specified {@code MassProperties}.
     *
     * @param props the properties to test (not null, modified)
     */
    private static void testMpSetters(MassProperties props) {
        props.setMass(2f);
        Assert.assertEquals(2f, props.getMass(), 0f);

        props.setInertia(Mat44.sIdentity());
        Assert.assertTrue(props.getInertia().isEqual(Mat44.sIdentity()));
    }

    /**
     * Test the getters and defaults of the specified {@code Parameters}.
     *
     * @param params the parameters to test (not null, unaffected)
     */
    private static void testParametersDefaults(Parameters params) {
        Assert.assertTrue(params.hasAssignedNativeObject());
        Assert.assertTrue(params.ownsNativeObject());

        Assert.assertTrue(params.getAsyncAcd());
        Assert.assertFalse(params.isDebugOutputEnabled());
        Assert.assertEquals(FillMode.FloodFill, params.getFillMode());
        Assert.assertFalse(params.getFindBestPlane());
        Assert.assertEquals(64, params.getMaxConvexHulls());
        Assert.assertEquals(64, params.getMaxNumVerticesPerCh());
        Assert.assertEquals(10, params.getMaxRecursionDepth());
        Assert.assertEquals(2, params.getMinEdgeLength());
        Assert.assertEquals(
                1., params.getMinimumVolumePercentErrorAllowed(), 0.);
        Assert.assertEquals(400_000, params.getResolution());
        Assert.assertTrue(params.getShrinkWrap());
    }

    /**
     * Test the setters of the specified {@code Parameters}.
     *
     * @param params the parameters to test (not null, modified)
     */
    private static void testParametersSetters(Parameters params) {
        params.setAsyncAcd(false);
        params.setDebugOutputEnabled(true);
        params.setFillMode(FillMode.RaycastFill);
        params.setFindBestPlane(true);
        params.setMaxConvexHulls(123);
        params.setMaxNumVerticesPerCh(67);
        params.setMaxRecursionDepth(8);
        params.setMinEdgeLength(9);
        params.setMinimumVolumePercentErrorAllowed(12.);
        params.setResolution(345_678);
        params.setShrinkWrap(false);

        Assert.assertFalse(params.getAsyncAcd());
        Assert.assertTrue(params.isDebugOutputEnabled());
        Assert.assertEquals(FillMode.RaycastFill, params.getFillMode());
        Assert.assertTrue(params.getFindBestPlane());
        Assert.assertEquals(123, params.getMaxConvexHulls());
        Assert.assertEquals(67, params.getMaxNumVerticesPerCh());
        Assert.assertEquals(8, params.getMaxRecursionDepth());
        Assert.assertEquals(9, params.getMinEdgeLength());
        Assert.assertEquals(
                12., params.getMinimumVolumePercentErrorAllowed(), 0.);
        Assert.assertEquals(345_678, params.getResolution());
        Assert.assertFalse(params.getShrinkWrap());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code SoftBodyCreationSettings}.
     *
     * @param sbcs the settings to test (not null, unaffected)
     */
    private static void testSbcsDefaults(ConstSoftBodyCreationSettings sbcs) {
        Assert.assertTrue(sbcs.hasAssignedNativeObject());
        Assert.assertTrue(sbcs.ownsNativeObject());

        Assert.assertTrue(sbcs.getAllowSleeping());
        Assert.assertNotNull(sbcs.getCollisionGroup());
        Assert.assertEquals(0.2f, sbcs.getFriction(), 0f);
        Assert.assertEquals(1f, sbcs.getGravityFactor(), 0f);
        Assert.assertEquals(0.1f, sbcs.getLinearDamping(), 0f);
        Assert.assertTrue(sbcs.getMakeRotationIdentity());
        Assert.assertEquals(500f, sbcs.getMaxLinearVelocity(), 0f);
        Assert.assertEquals(0, sbcs.getObjectLayer());
        TestUtils.assertEquals(0f, 0f, 0f, sbcs.getPosition(), 0f);
        Assert.assertEquals(0f, sbcs.getPressure(), 0f);
        Assert.assertEquals(0f, sbcs.getRestitution(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, 1f, sbcs.getRotation(), 0f);
        Assert.assertTrue(sbcs.getUpdatePosition());
    }

    /**
     * Test the setters of the specified {@code SoftBodyCreationSettings}.
     *
     * @param sbcs the settings to test (not null, modified)
     */
    private static void testSbcsSetters(SoftBodyCreationSettings sbcs) {
        sbcs.setAllowSleeping(false);
        sbcs.setCollisionGroup(new CollisionGroup());
        sbcs.setFriction(0.02f);
        sbcs.setGravityFactor(0.06f);
        sbcs.setLinearDamping(0.07f);
        sbcs.setMakeRotationIdentity(false);
        sbcs.setMaxLinearVelocity(0.102f);
        sbcs.setObjectLayer(65_535);
        sbcs.setPosition(new RVec3(3., 4., 5.));
        sbcs.setPressure(0.09f);
        sbcs.setRestitution(0.15f);
        sbcs.setRotation(new Quat(0.5f, 0.5f, -0.5f, -0.5f));
        sbcs.setUpdatePosition(false);

        SoftBodySharedSettings newSs = new SoftBodySharedSettings();
        sbcs.setSettings(newSs);

        Assert.assertFalse(sbcs.getAllowSleeping());
        Assert.assertEquals(0.02f, sbcs.getFriction(), 0f);
        Assert.assertEquals(0.06f, sbcs.getGravityFactor(), 0f);
        Assert.assertEquals(0.07f, sbcs.getLinearDamping(), 0f);
        Assert.assertFalse(sbcs.getMakeRotationIdentity());
        Assert.assertEquals(0.102f, sbcs.getMaxLinearVelocity(), 0f);
        Assert.assertEquals(65_535, sbcs.getObjectLayer());
        TestUtils.assertEquals(3f, 4f, 5f, sbcs.getPosition(), 0f);
        Assert.assertEquals(0.09f, sbcs.getPressure(), 0f);
        Assert.assertEquals(0.15f, sbcs.getRestitution(), 0f);
        TestUtils.assertEquals(
                0.5f, 0.5f, -0.5f, -0.5f, sbcs.getRotation(), 0f);
        Assert.assertEquals(newSs, sbcs.getSettings());
        Assert.assertFalse(sbcs.getUpdatePosition());
    }

    /**
     * Test the getters and defaults of the specified {@code SkinWeight}.
     *
     * @param weight the weight to test (not null, unaffected)
     */
    private static void testSkinWeightDefaults(SkinWeight weight) {
        Assert.assertTrue(weight.hasAssignedNativeObject());
        Assert.assertTrue(weight.ownsNativeObject());

        Assert.assertEquals(0, weight.getInvBindIndex());
        Assert.assertEquals(0., weight.getWeight(), 0.);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code SoftBodyMotionProperties}.
     *
     * @param properties the properties to test (not null, unaffected)
     */
    private static void testSoftBodyMotionPropertiesDefaults(
            SoftBodyMotionProperties properties) {
        Assert.assertTrue(properties.hasAssignedNativeObject());
        Assert.assertTrue(properties.ownsNativeObject());

        Assert.assertTrue(properties.getEnableSkinConstraints());
        Assert.assertEquals(0, properties.getNumIterations());
        Assert.assertEquals(
                1f, properties.getSkinnedMaxDistanceMultiplier(), 0f);
    }

    /**
     * Test the setters of the specified {@code SoftBodyMotionProperties}.
     *
     * @param properties the properties to test (not null, modified)
     */
    private static void testSoftBodyMotionPropertiesSetters(
            SoftBodyMotionProperties properties) {
        properties.setEnableSkinConstraints(false);
        properties.setNumIterations(2);
        properties.setSkinnedMaxDistanceMultiplier(9f);

        Assert.assertFalse(properties.getEnableSkinConstraints());
        Assert.assertEquals(2, properties.getNumIterations());
        Assert.assertEquals(
                9f, properties.getSkinnedMaxDistanceMultiplier(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code SpringSettings}.
     *
     * @param ss the settings to test (not null, unaffected)
     */
    private static void testSpringSettingsDefaults(SpringSettings ss) {
        Assert.assertNull(ss.getConstraint());
        Assert.assertNull(ss.getConstraintSettings());
        Assert.assertEquals(0f, ss.getDamping(), 0f);
        Assert.assertEquals(0f, ss.getFrequency(), 0f);
        Assert.assertEquals(ESpringMode.FrequencyAndDamping, ss.getMode());
        Assert.assertEquals(0f, ss.getStiffness(), 0f);
        Assert.assertFalse(ss.hasStiffness());
    }

    /**
     * Test the setters of the specified {@code SpringSettings}.
     *
     * @param ss the settings to test (not null, modified)
     */
    private static void testSpringSettingsSetters(SpringSettings ss) {
        ss.setFrequency(2f);
        Assert.assertEquals(2f, ss.getFrequency(), 0f);

        ss.setDamping(1f);
        ss.setMode(ESpringMode.StiffnessAndDamping);
        ss.setStiffness(3f);

        Assert.assertEquals(1f, ss.getDamping(), 0f);
        Assert.assertEquals(3f, ss.getFrequency(), 0f);
        Assert.assertEquals(ESpringMode.StiffnessAndDamping, ss.getMode());
        Assert.assertEquals(3f, ss.getStiffness(), 0f);
        Assert.assertTrue(ss.hasStiffness());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code VehicleConstraintSettings}.
     *
     * @param vcs the settings to test (not null, unaffected)
     */
    private static void testVehicleConstraintSettingsDefaults(
            VehicleConstraintSettings vcs) {
        Assert.assertEquals(0, vcs.getConstraintPriority());
        Assert.assertNull(vcs.getController());
        Assert.assertEquals(0, vcs.getControllerType());
        Assert.assertEquals(1f, vcs.getDrawConstraintSize(), 0f);
        Assert.assertTrue(vcs.getEnabled());
        TestUtils.assertEquals(0f, 0f, 1f, vcs.getForward(), 0f);
        Assert.assertEquals(Jolt.JPH_PI, vcs.getMaxPitchRollAngle(), 0f);
        Assert.assertEquals(0, vcs.getNumAntiRollBars());
        Assert.assertEquals(0, vcs.getNumPositionStepsOverride());
        Assert.assertEquals(0, vcs.getNumVelocityStepsOverride());
        Assert.assertEquals(0, vcs.getNumWheels());
        TestUtils.assertEquals(0f, 1f, 0f, vcs.getUp(), 0f);
    }

    /**
     * Test the setters of the specified {@code VehicleConstraintSettings}.
     *
     * @param vcs the settings to test (not null, modified)
     */
    private static void testVehicleConstraintSettingsSetters(
            VehicleConstraintSettings vcs) {
        vcs.addWheels(new WheelSettingsWv());
        vcs.setConstraintPriority(9);
        vcs.setController(new WheeledVehicleControllerSettings());
        vcs.setDrawConstraintSize(8f);
        vcs.setEnabled(false);
        vcs.setForward(new Vec3(0.6f, 0.8f, 0f));
        vcs.setMaxPitchRollAngle(1.2f);
        vcs.setNumAntiRollBars(1);
        vcs.setNumPositionStepsOverride(4);
        vcs.setNumVelocityStepsOverride(5);
        vcs.setUp(new Vec3(0f, 0f, -1f));

        Assert.assertEquals(9, vcs.getConstraintPriority());
        Assert.assertNotNull(vcs.getController());
        Assert.assertEquals(4, vcs.getControllerType());
        Assert.assertEquals(8f, vcs.getDrawConstraintSize(), 0f);
        Assert.assertFalse(vcs.getEnabled());
        TestUtils.assertEquals(0.6f, 0.8f, 0f, vcs.getForward(), 0f);
        Assert.assertEquals(1.2f, vcs.getMaxPitchRollAngle(), 0f);
        Assert.assertEquals(1, vcs.getNumAntiRollBars());
        Assert.assertEquals(4, vcs.getNumPositionStepsOverride());
        Assert.assertEquals(5, vcs.getNumVelocityStepsOverride());
        Assert.assertEquals(1, vcs.getNumWheels());
        TestUtils.assertEquals(0f, 0f, -1f, vcs.getUp(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code Vertex}.
     *
     * @param vertex the vertex to test (not null, unaffected)
     */
    private static void testVertexDefaults(ConstVertex vertex) {
        Assert.assertEquals(1f, vertex.getInvMass(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, vertex.getPosition(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, vertex.getVelocity(), 0f);
    }

    /**
     * Test the setters of the specified {@code Vertex}.
     *
     * @param vertex the vertex to test (not null, modified)
     */
    private static void testVertexSetters(Vertex vertex) {
        vertex.setInvMass(3f);
        vertex.setPosition(4f, 5f, 6f);
        vertex.setVelocity(7f, 8f, 9f);

        Assert.assertEquals(3f, vertex.getInvMass(), 0f);
        TestUtils.assertEquals(4f, 5f, 6f, vertex.getPosition(), 0f);
        TestUtils.assertEquals(7f, 8f, 9f, vertex.getVelocity(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code WheelSettings}.
     *
     * @param ws the settings to test (not null, unaffected)
     */
    private static void testWheelSettingsDefaults(ConstWheelSettings ws) {
        Assert.assertFalse(ws.getEnableSuspensionForcePoint());
        TestUtils.assertEquals(0f, 0f, 0f, ws.getPosition(), 0f);
        Assert.assertEquals(0.3f, ws.getRadius(), 0f);
        TestUtils.assertEquals(0f, 1f, 0f, ws.getSteeringAxis(), 0f);
        TestUtils.assertEquals(0f, -1f, 0f, ws.getSuspensionDirection(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, ws.getSuspensionForcePoint(), 0f);
        Assert.assertEquals(0.5f, ws.getSuspensionMaxLength(), 0f);
        Assert.assertEquals(0.3f, ws.getSuspensionMinLength(), 0f);
        Assert.assertEquals(0f, ws.getSuspensionPreloadLength(), 0f);
        Assert.assertNotNull(ws.getSuspensionSpring());
        TestUtils.assertEquals(0f, 0f, 1f, ws.getWheelForward(), 0f);
        TestUtils.assertEquals(0f, 1f, 0f, ws.getWheelUp(), 0f);
        Assert.assertEquals(0.1f, ws.getWidth(), 0f);
    }

    /**
     * Test the setters of the specified {@code WheelSettings}.
     *
     * @param ws the settings to test (not null, modified)
     */
    private static void testWheelSettingsSetters(WheelSettings ws) {
        ws.setEnableSuspensionForcePoint(true);
        ws.setPosition(new Vec3(1f, 2f, 3f));
        ws.setRadius(0.4f);
        ws.setSteeringAxis(new Vec3(0f, 0f, 1f));
        ws.setSuspensionDirection(new Vec3(0f, -0.8f, 0.6f));
        ws.setSuspensionForcePoint(new Vec3(4f, 2f, 1f));
        ws.setSuspensionMaxLength(1f);
        ws.setSuspensionMinLength(0.2f);
        ws.setSuspensionPreloadLength(0.5f);
        ws.setWheelForward(new Vec3(0f, 0f, -1f));
        ws.setWidth(0.14f);

        Assert.assertTrue(ws.getEnableSuspensionForcePoint());
        TestUtils.assertEquals(1f, 2f, 3f, ws.getPosition(), 0f);
        Assert.assertEquals(0.4f, ws.getRadius(), 0f);
        TestUtils.assertEquals(0f, 0f, 1f, ws.getSteeringAxis(), 0f);
        TestUtils.assertEquals(
                0f, -0.8f, 0.6f, ws.getSuspensionDirection(), 0f);
        TestUtils.assertEquals(4f, 2f, 1f, ws.getSuspensionForcePoint(), 0f);
        Assert.assertEquals(1f, ws.getSuspensionMaxLength(), 0f);
        Assert.assertEquals(0.2f, ws.getSuspensionMinLength(), 0f);
        Assert.assertEquals(0.5f, ws.getSuspensionPreloadLength(), 0f);
        TestUtils.assertEquals(0f, 0f, -1f, ws.getWheelForward(), 0f);
        Assert.assertEquals(0.14f, ws.getWidth(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code WheelSettingsTv}.
     *
     * @param wstv the settings to test (not null, unaffected)
     */
    private static void testWheelSettingsTvDefaults(ConstWheelSettingsTv wstv) {
        testWheelSettingsDefaults(wstv);

        Assert.assertEquals(2f, wstv.getLateralFriction(), 0f);
        Assert.assertEquals(4f, wstv.getLongitudinalFriction(), 0f);
    }

    /**
     * Test the setters of the specified {@code WheelSettingsTv}.
     *
     * @param wstv the settings to test (not null, modified)
     */
    private static void testWheelSettingsTvSetters(WheelSettingsTv wstv) {
        testWheelSettingsSetters(wstv);

        wstv.setLateralFriction(1f);
        wstv.setLongitudinalFriction(5f);

        Assert.assertEquals(1f, wstv.getLateralFriction(), 0f);
        Assert.assertEquals(5f, wstv.getLongitudinalFriction(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code WheelSettingsWv}.
     *
     * @param wswv the settings to test (not null, unaffected)
     */
    private static void testWheelSettingsWvDefaults(WheelSettingsWv wswv) {
        testWheelSettingsDefaults(wswv);

        Assert.assertEquals(1_500f, wswv.getMaxBrakeTorque(), 0f);
        Assert.assertEquals(4_000f, wswv.getMaxHandBrakeTorque(), 0f);
        Assert.assertEquals(
                Jolt.degreesToRadians(70f), wswv.getMaxSteerAngle(), 1e-7f);
    }

    /**
     * Test the setters of the specified {@code WheelSettingsWv}.
     *
     * @param wswv the settings to test (not null, modified)
     */
    private static void testWheelSettingsWvSetters(WheelSettingsWv wswv) {
        testWheelSettingsSetters(wswv);

        wswv.setMaxBrakeTorque(333f);
        wswv.setMaxHandBrakeTorque(456f);
        wswv.setMaxSteerAngle(0.1f);

        Assert.assertEquals(333f, wswv.getMaxBrakeTorque(), 0f);
        Assert.assertEquals(456f, wswv.getMaxHandBrakeTorque(), 0f);
        Assert.assertEquals(0.1f, wswv.getMaxSteerAngle(), 0f);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code WheeledVehicleControllerSettings}.
     *
     * @param wvcs the settings to test (not null, unaffected)
     */
    private static void testWvControllerSettingsDefaults(
            WheeledVehicleControllerSettings wvcs) {
        Assert.assertNotNull(wvcs.getEngine());
        Assert.assertEquals(0, wvcs.getNumDifferentials());
        Assert.assertNotNull(wvcs.getTransmission());
    }

    /**
     * Test the setters of the specified
     * {@code WheeledVehicleControllerSettings}.
     *
     * @param wvcs the settings to test (not null, modified)
     */
    private static void testWvControllerSettingsSetters(
            WheeledVehicleControllerSettings wvcs) {
        wvcs.setNumDifferentials(2);

        Assert.assertEquals(2, wvcs.getNumDifferentials());
        Assert.assertNotNull(wvcs.getDifferential(0));
        Assert.assertNotNull(wvcs.getDifferential(1));
    }
}
