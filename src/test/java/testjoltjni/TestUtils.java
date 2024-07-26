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

import com.github.stephengold.joltjni.ConstBroadPhaseLayerInterface;
import com.github.stephengold.joltjni.ConstJoltPhysicsObject;
import com.github.stephengold.joltjni.ConstObjectLayerPairFilter;
import com.github.stephengold.joltjni.ConstObjectVsBroadPhaseLayerFilter;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.JoltPhysicsObject;
import com.github.stephengold.joltjni.MapObj2Bp;
import com.github.stephengold.joltjni.ObjVsBpFilter;
import com.github.stephengold.joltjni.ObjVsObjFilter;
import com.github.stephengold.joltjni.PhysicsSystem;
import com.github.stephengold.joltjni.QuatArg;
import com.github.stephengold.joltjni.RVec3Arg;
import com.github.stephengold.joltjni.UVec4;
import com.github.stephengold.joltjni.Vec3Arg;
import electrostatic.snaploader.platform.util.NativeVariant;
import java.io.File;
import java.util.Locale;
import org.junit.Assert;

/**
 * Utility methods for automated testing of jolt-jni libraries.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class TestUtils {
    // *************************************************************************
    // constants

    /**
     * false to explicitly free native objects via {@code testClose()}, true to
     * rely on the automatic {@code java.lang.ref.Cleaner} instead
     */
    final static public boolean automateFreeing = true;
    /**
     * customary number of object layers
     */
    final public static int numObjLayers = 2;
    /**
     * customary object layer for non-moving objects
     */
    final public static int objLayerNonMoving = 0;
    /**
     * customary object layer for moving objects
     */
    final public static int objLayerMoving = 1;
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private TestUtils() {
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
     * Verify that the specified vector has the expected component values.
     *
     * @param x the expected X component
     * @param y the expected Y component
     * @param z the expected Z component
     * @param w the expected W component
     * @param actual the vector to test (not null, unaffected)
     */
    public static void assertEquals(int x, int y, int z, int w, UVec4 actual) {
        Assert.assertEquals("x component", x, actual.getX());
        Assert.assertEquals("y component", y, actual.getY());
        Assert.assertEquals("z component", z, actual.getZ());
        Assert.assertEquals("w component", w, actual.getW());
    }

    /**
     * Clean up after a test.
     */
    public static void cleanup() {
        Jolt.unregisterTypes();
        Jolt.destroyFactory();

        System.gc();
    }

    /**
     * Clean up the specified {@code PhysicsSystem}.
     *
     * @param physicsSystem the system to clean up (not null)
     */
    static public void cleanupPhysicsSystem(PhysicsSystem physicsSystem) {
        ConstBroadPhaseLayerInterface mapObj2Bp
                = physicsSystem.getBroadPhaseLayerInterface();
        ConstObjectLayerPairFilter ovoFilter = physicsSystem.getOvoFilter();
        ConstObjectVsBroadPhaseLayerFilter ovbFilter
                = physicsSystem.getOvbFilter();

        testClose(physicsSystem, ovbFilter, ovoFilter, mapObj2Bp);
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

        //Jolt.setTraceAllocations(true); // to debug native memory allocation
        if (automateFreeing) {
            JoltPhysicsObject.startCleaner();
        }

        Jolt.registerDefaultAllocator();
        Jolt.installDefaultAssertCallback();
        Jolt.installDefaultTraceCallback();
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

        boolean success = loadNativeLibrary(directory, "Debug", "Sp");
        if (success) {
            Assert.assertFalse(Jolt.isDoublePrecision());
        } else {
            success = loadNativeLibrary(directory, "Debug", "Dp");
            if (success) {
                Assert.assertTrue(Jolt.isDoublePrecision());
            }
        }
        Assert.assertTrue(success);
    }

    /**
     * Load a specific jolt-jni native library from the filesystem.
     *
     * @param directory (not null, readable, unaffected)
     * @param buildType "Debug" or "Release"
     * @param flavor "Sp" or "Dp"
     * @return true after successful load, otherwise false
     */
    public static boolean loadNativeLibrary(
            File directory, String buildType, String flavor) {
        assert buildType.equals("Debug") || buildType.equals("Release") :
                buildType;
        assert flavor.equals("Sp") || flavor.equals("Dp") : flavor;

        String name;
        String subdirectory;
        if (NativeVariant.Os.isLinux()) {
            name = "libjoltjni.so";
            subdirectory = "linux64";

        } else if (NativeVariant.Os.isMac()) {
            name = "libjoltjni.dylib";
            if (NativeVariant.Cpu.isARM()) {
                subdirectory = "macOSX_ARM64";
            } else {
                subdirectory = "macOSX64";
            }

        } else if (NativeVariant.Os.isWindows()) {
            name = "joltjni.dll";
            subdirectory = "windows64";

        } else {
            String osName = NativeVariant.OS_NAME.getProperty();
            throw new RuntimeException("osName = " + osName);
        }

        File file = new File(directory, subdirectory);
        String bt = firstToLower(buildType);
        file = new File(file, bt);

        String f = firstToLower(flavor);
        file = new File(file, f);

        file = new File(file, name);
        String absoluteFilename = file.getAbsolutePath();
        boolean success = false;
        if (file.exists() && file.canRead()) {
            System.load(absoluteFilename);
            success = true;
        }

        return success;
    }

    /**
     * Allocate and initialize a {@code PhysicsSystem} in the customary
     * configuration.
     *
     * @param maxBodies the desired number of bodies (&ge;1)
     * @return a new instance
     */
    static public PhysicsSystem newPhysicsSystem(int maxBodies) {
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

        int numBodyMutexes = 0; // 0 means "use the default value"
        int maxBodyPairs = 65_536;
        int maxContacts = 20_480;
        PhysicsSystem result = new PhysicsSystem();
        result.init(maxBodies, numBodyMutexes, maxBodyPairs, maxContacts,
                mapObj2Bp, objVsBpFilter, objVsObjFilter);

        return result;
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
     * Test the {@code close()} methods of the specified physics objects.
     * However, if freeing is automated, {@code close()} is neither invoked or
     * tested.
     *
     * @param objects the objects to test (not null)
     */
    public static void testClose(ConstJoltPhysicsObject... objects) {
        if (!automateFreeing) {
            for (ConstJoltPhysicsObject object : objects) {
                boolean wasOwner = object.ownsNativeObject();
                object.close();

                if (wasOwner) {
                    Assert.assertFalse(object.hasAssignedNativeObject());
                }
                Assert.assertFalse(object.ownsNativeObject());
            }
        }
    }
    // *************************************************************************
    // private methods

    /**
     * Convert the first character of the specified text to lower case.
     *
     * @param input the input text to convert (not null)
     * @return the converted text (not null)
     */
    private static String firstToLower(String input) {
        String result = input;
        if (!input.isEmpty()) {
            String first = input.substring(0, 1);
            first = first.toLowerCase(Locale.ROOT);
            String rest = input.substring(1);
            result = first + rest;
        }

        return result;
    }
}
