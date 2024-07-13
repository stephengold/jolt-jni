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
package testjoltjni;

import com.github.stephengold.joltjni.ConstJoltPhysicsObject;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.QuatArg;
import com.github.stephengold.joltjni.RVec3Arg;
import com.github.stephengold.joltjni.Vec3Arg;
import com.jme3.system.NativeLibraryLoader;
import java.io.File;
import org.junit.Assert;

/**
 * Utility methods for automated testing of jolt-jni libraries.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class Utils {
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private Utils() {
        // do nothing
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Verify that 2 quaternions are equal to within some tolerance.
     *
     * @param x the expected X component
     * @param y the expected Y component
     * @param z the expected Z component
     * @param w the expected W component
     * @param actual the Quaternion to test (not null, unaffected)
     * @param tolerance the allowable difference for each component (&ge;0)
     */
    public static void assertEquals(float x, float y, float z, float w,
            QuatArg actual, float tolerance) {
        Assert.assertEquals("x component", x, actual.getX(), tolerance);
        Assert.assertEquals("y component", y, actual.getY(), tolerance);
        Assert.assertEquals("z component", z, actual.getZ(), tolerance);
        Assert.assertEquals("w component", w, actual.getW(), tolerance);
    }

    /**
     * Verify that 2 position-precision vectors are equal to within some
     * tolerance.
     *
     * @param x the expected X component
     * @param y the expected Y component
     * @param z the expected Z component
     * @param actual the vector to test (not null, unaffected)
     * @param tolerance the allowable difference for each component (&ge;0)
     */
    public static void assertEquals(
            float x, float y, float z, RVec3Arg actual, float tolerance) {
        Assert.assertEquals("x component", x, actual.x(), tolerance);
        Assert.assertEquals("y component", y, actual.y(), tolerance);
        Assert.assertEquals("z component", z, actual.z(), tolerance);
    }

    /**
     * Verify that 2 single-precision vectors are equal to within some
     * tolerance.
     *
     * @param x the expected X component
     * @param y the expected Y component
     * @param z the expected Z component
     * @param actual the vector to test (not null, unaffected)
     * @param tolerance the allowable difference for each component (&ge;0)
     */
    public static void assertEquals(
            float x, float y, float z, Vec3Arg actual, float tolerance) {
        Assert.assertEquals("x component", x, actual.getX(), tolerance);
        Assert.assertEquals("y component", y, actual.getY(), tolerance);
        Assert.assertEquals("z component", z, actual.getZ(), tolerance);
    }

    /**
     * Load and initialize some flavor of native library.
     */
    public static void loadAndInitializeNativeLibrary() {
        loadNativeLibrary();

        String buildType = Jolt.buildType();
        if (!buildType.equals("Release")) {
            System.out.print(buildType + "-");
        }
        if (Jolt.isDoublePrecision()) {
            System.out.print("Dp-");
        }
        System.out.println(
                "jolt-jni version " + Jolt.versionString() + " initializing");

        Jolt.registerDefaultAllocator();
        Jolt.installDefaultTraceCallback();
        Jolt.installDefaultAssertCallback();
        Jolt.newFactory();
        Jolt.registerTypes();
    }

    /**
     * Load some flavor of Debug native library.
     * <p>
     * The search order is:
     * <ol>
     * <li>DebugSp</li>
     * <li>DebugDp</li>
     * </ol>
     */
    public static void loadNativeLibrary() {
        File directory = new File("build/libs/joltjni/shared");

        boolean success
                = NativeLibraryLoader.loadJoltJni(directory, "Debug", "Sp");
        if (success) {
            Assert.assertFalse(Jolt.isDoublePrecision());
        } else {
            success = NativeLibraryLoader.loadJoltJni(directory, "Debug", "Dp");
            if (success) {
                Assert.assertTrue(Jolt.isDoublePrecision());
            }
        }
        Assert.assertTrue(success);
    }

    /**
     * Return the recommended number of worker threads.
     *
     * @return the count (&ge;1)
     */
    public static int numThreads() {
        int numCpus = Runtime.getRuntime().availableProcessors();
        int result = (int) Math.floor(0.9 * numCpus);
        if (result < 1) {
            result = 1;
        }

        return result;
    }

    /**
     * Test the {@code close()} method of the specified physics object.
     *
     * @param object the object to test (not null)
     */
    public static void testClose(ConstJoltPhysicsObject object) {
        boolean wasOwner = object.ownsNativeObject();
        object.close();
        if (wasOwner) {
            Assert.assertFalse(object.hasAssignedNativeObject());
        }
        Assert.assertFalse(object.ownsNativeObject());
    }
}
