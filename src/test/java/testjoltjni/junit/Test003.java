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

import com.github.stephengold.joltjni.AaBox;
import com.github.stephengold.joltjni.BodyCreationSettings;
import com.github.stephengold.joltjni.BoxShape;
import com.github.stephengold.joltjni.BoxShapeSettings;
import com.github.stephengold.joltjni.EMotionQuality;
import com.github.stephengold.joltjni.EMotionType;
import com.github.stephengold.joltjni.JobSystem;
import com.github.stephengold.joltjni.JobSystemSingleThreaded;
import com.github.stephengold.joltjni.JobSystemThreadPool;
import com.github.stephengold.joltjni.JobSystemWithBarrier;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.NonCopyable;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.Shape;
import com.github.stephengold.joltjni.ShapeSettings;
import com.github.stephengold.joltjni.SphereShape;
import com.github.stephengold.joltjni.TempAllocator;
import com.github.stephengold.joltjni.TempAllocatorImpl;
import com.github.stephengold.joltjni.TempAllocatorMalloc;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.Vec3Arg;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Automated JUnit4 tests for jolt-jni object creation, destruction, accessors,
 * and defaults.
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
        TestUtils.loadAndInitializeNativeLibrary();

        // AaBox:
        {
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
            TestUtils.assertEquals(
                    Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY,
                    Float.POSITIVE_INFINITY, box.getExtent(), 0f);
            TestUtils.assertEquals(Float.MAX_VALUE, Float.MAX_VALUE,
                    Float.MAX_VALUE, box.getMax(), 0f);
            TestUtils.assertEquals(-Float.MAX_VALUE, -Float.MAX_VALUE,
                    -Float.MAX_VALUE, box.getMin(), 0f);
            TestUtils.assertEquals(
                    Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY,
                    Float.POSITIVE_INFINITY, box.getSize(), 0f);
            Assert.assertTrue(box.isValid());

            TestUtils.testClose(box);
        }
        {
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

        // BodyCreationSettings:
        {
            BodyCreationSettings bcs = new BodyCreationSettings();

            Assert.assertNull(bcs.getShape());
            testBcsDefaults(bcs);
            testBcsSetters(bcs);

            TestUtils.testClose(bcs);
        }
        {
            ShapeSettings ss = new BoxShapeSettings(new Vec3(1f, 1f, 1f));
            int objectLayer = 0;
            BodyCreationSettings bcs = new BodyCreationSettings(ss,
                    new RVec3(), new Quat(), EMotionType.Dynamic, objectLayer);

            Assert.assertNotNull(bcs.getShape());
            Assert.assertTrue(bcs.getShape() instanceof BoxShape);
            testBcsDefaults(bcs);
            testBcsSetters(bcs);

            TestUtils.testClose(bcs);
        }
        {
            Shape shape = new SphereShape(1f);
            int objectLayer = 0;
            BodyCreationSettings bcs = new BodyCreationSettings(shape,
                    new RVec3(), new Quat(), EMotionType.Dynamic, objectLayer);

            Assert.assertEquals(shape, bcs.getShape());
            testBcsDefaults(bcs);
            testBcsSetters(bcs);

            TestUtils.testClose(bcs);
        }

        // JobSystemSingleThreaded:
        {
            JobSystem jobSystem
                    = new JobSystemSingleThreaded(Jolt.cMaxPhysicsJobs);

            Assert.assertEquals(1, jobSystem.getMaxConcurrency());
            Assert.assertTrue(jobSystem.hasAssignedNativeObject());
            Assert.assertTrue(jobSystem instanceof NonCopyable);
            Assert.assertTrue(jobSystem.ownsNativeObject());
            Assert.assertNotEquals(0L, jobSystem.va());

            TestUtils.testClose(jobSystem);
        }

        // JobSystemThreadPool:
        {
            JobSystemThreadPool jobSystem = new JobSystemThreadPool(
                    Jolt.cMaxPhysicsJobs, Jolt.cMaxPhysicsBarriers);

            int numCpus = Runtime.getRuntime().availableProcessors();
            Assert.assertEquals(numCpus, jobSystem.getMaxConcurrency());

            Assert.assertTrue(jobSystem.hasAssignedNativeObject());
            Assert.assertTrue(jobSystem instanceof JobSystem);
            Assert.assertTrue(jobSystem instanceof JobSystemWithBarrier);
            Assert.assertTrue(jobSystem instanceof NonCopyable);
            Assert.assertTrue(jobSystem.ownsNativeObject());
            Assert.assertNotEquals(0L, jobSystem.va());

            jobSystem.setNumThreads(3);
            Assert.assertEquals(4, jobSystem.getMaxConcurrency());

            TestUtils.testClose(jobSystem);
        }

        // TempAllocatorImpl:
        {
            int numBytes = 1 << 8;
            TempAllocator tempAllocator = new TempAllocatorImpl(numBytes);

            Assert.assertTrue(tempAllocator.hasAssignedNativeObject());
            Assert.assertTrue(tempAllocator.ownsNativeObject());
            Assert.assertNotEquals(0L, tempAllocator.va());

            TestUtils.testClose(tempAllocator);
        }

        // TempAllocatorMalloc:
        {
            TempAllocator tempAllocator = new TempAllocatorMalloc();

            Assert.assertTrue(tempAllocator.hasAssignedNativeObject());
            Assert.assertTrue(tempAllocator.ownsNativeObject());
            Assert.assertNotEquals(0L, tempAllocator.va());

            TestUtils.testClose(tempAllocator);
        }

        TestUtils.cleanup();
    }
    // *************************************************************************
    // Java private methods

    /**
     * Test the getters and defaults of the specified
     * {@code BodyCreationSettings}.
     *
     * @param bcs the settings to test (not null, unaffected)
     */
    private static void testBcsDefaults(BodyCreationSettings bcs) {
        Assert.assertTrue(bcs.hasAssignedNativeObject());
        Assert.assertTrue(bcs.ownsNativeObject());

        Assert.assertTrue(bcs.getAllowSleeping());
        Assert.assertEquals(0.05f, bcs.getAngularDamping(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, bcs.getAngularVelocity(), 0f);
        Assert.assertEquals(0.2f, bcs.getFriction(), 0f);
        Assert.assertEquals(1f, bcs.getGravityFactor(), 0f);
        Assert.assertEquals(0.05f, bcs.getLinearDamping(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, bcs.getLinearVelocity(), 0f);
        Assert.assertEquals(15 * Math.PI, bcs.getMaxAngularVelocity(), 1e-6f);
        Assert.assertEquals(500f, bcs.getMaxLinearVelocity(), 0f);
        Assert.assertEquals(EMotionQuality.Discrete, bcs.getMotionQuality());
        Assert.assertEquals(EMotionType.Dynamic, bcs.getMotionType());
        Assert.assertEquals(0, bcs.getObjectLayer());
        TestUtils.assertEquals(0f, 0f, 0f, bcs.getPosition(), 0f);
        Assert.assertEquals(0f, bcs.getRestitution(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, 1f, bcs.getRotation(), 0f);
        Assert.assertTrue(bcs.hasMassProperties());
    }

    /**
     * Test the setters of the specified {@code BodyCreationSettings}.
     *
     * @param bcs the settings to test (not null, modified)
     */
    private static void testBcsSetters(BodyCreationSettings bcs) {
        bcs.setAllowSleeping(false);
        Assert.assertFalse(bcs.getAllowSleeping());

        bcs.setAngularDamping(0.01f);
        Assert.assertEquals(0.01f, bcs.getAngularDamping(), 0f);

        bcs.setAngularVelocity(new Vec3(0.02f, 0.03f, 0.04f));
        TestUtils.assertEquals(
                0.02f, 0.03f, 0.04f, bcs.getAngularVelocity(), 0f);

        bcs.setFriction(0.05f);
        Assert.assertEquals(0.05f, bcs.getFriction(), 0f);

        bcs.setGravityFactor(0.06f);
        Assert.assertEquals(0.06f, bcs.getGravityFactor(), 0f);

        bcs.setLinearDamping(0.07f);
        Assert.assertEquals(0.07f, bcs.getLinearDamping(), 0f);

        bcs.setLinearVelocity(new Vec3(0.08f, 0.09f, 0.1f));
        TestUtils.assertEquals(0.08f, 0.09f, 0.1f, bcs.getLinearVelocity(), 0f);

        bcs.setMaxAngularVelocity(0.101f);
        Assert.assertEquals(0.101f, bcs.getMaxAngularVelocity(), 0f);

        bcs.setMaxLinearVelocity(0.102f);
        Assert.assertEquals(0.102f, bcs.getMaxLinearVelocity(), 0f);

        bcs.setMotionQuality(EMotionQuality.LinearCast);
        Assert.assertEquals(EMotionQuality.LinearCast, bcs.getMotionQuality());

        bcs.setMotionType(EMotionType.Kinematic);
        Assert.assertEquals(EMotionType.Kinematic, bcs.getMotionType());

        bcs.setObjectLayer(11);
        Assert.assertEquals(11, bcs.getObjectLayer());

        bcs.setPosition(new RVec3(0.12, 0.13, 0.14));
        TestUtils.assertEquals(0.12f, 0.13f, 0.14f, bcs.getPosition(), 0f);

        bcs.setRestitution(0.15f);
        Assert.assertEquals(0.15f, bcs.getRestitution(), 0f);

        bcs.setRotation(new Quat(0.6f, 0f, 0f, 0.8f));
        TestUtils.assertEquals(0.6f, 0f, 0f, 0.8f, bcs.getRotation(), 0f);
    }
}
