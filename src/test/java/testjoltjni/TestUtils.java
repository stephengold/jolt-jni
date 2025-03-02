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
package testjoltjni;

import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.JoltPhysicsObject;
import com.github.stephengold.joltjni.MapObj2Bp;
import com.github.stephengold.joltjni.ObjVsBpFilter;
import com.github.stephengold.joltjni.ObjVsObjFilter;
import com.github.stephengold.joltjni.PhysicsSystem;
import com.github.stephengold.joltjni.UVec4;
import com.github.stephengold.joltjni.readonly.ConstBroadPhaseLayerInterface;
import com.github.stephengold.joltjni.readonly.ConstJoltPhysicsObject;
import com.github.stephengold.joltjni.readonly.ConstObjectLayerPairFilter;
import com.github.stephengold.joltjni.readonly.ConstObjectVsBroadPhaseLayerFilter;
import com.github.stephengold.joltjni.readonly.Mat44Arg;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.RMat44Arg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import electrostatic4j.snaploader.platform.util.NativeVariant;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Assert;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

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
     * customary object layer for moving objects
     */
    final public static int objLayerMoving = 1;
    /**
     * customary object layer for non-moving objects
     */
    final public static int objLayerNonMoving = 0;
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
     * Verify the components of a quaternion to within some tolerance.
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
     * Verify the components of a location-precision vector to within some
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
     * Verify the elements of a single-precision matrix to within some
     * tolerance.
     *
     * @param e00 the expected element in row 0 of column 0
     * @param e01 the expected element in row 0 of column 1
     * @param e02 the expected element in row 0 of column 2
     * @param e03 the expected element in row 0 of column 3
     * @param e10 the expected element in row 1 of column 0
     * @param e11 the expected element in row 1 of column 1
     * @param e12 the expected element in row 1 of column 2
     * @param e13 the expected element in row 1 of column 3
     * @param e20 the expected element in row 2 of column 0
     * @param e21 the expected element in row 2 of column 1
     * @param e22 the expected element in row 2 of column 2
     * @param e23 the expected element in row 2 of column 3
     * @param e30 the expected element in row 3 of column 0
     * @param e31 the expected element in row 3 of column 1
     * @param e32 the expected element in row 3 of column 2
     * @param e33 the expected element in row 3 of column 3
     * @param actual the matrix to test (not null, unaffected)
     * @param tolerance the allowable difference for each element (&ge;0)
     */
    public static void assertEquals(float e00, float e01, float e02, float e03,
            float e10, float e11, float e12, float e13,
            float e20, float e21, float e22, float e23,
            float e30, float e31, float e32, float e33,
            Mat44Arg actual, float tolerance) {
        Assert.assertEquals("e00", e00, actual.getElement(0, 0), tolerance);
        Assert.assertEquals("e01", e01, actual.getElement(0, 1), tolerance);
        Assert.assertEquals("e02", e02, actual.getElement(0, 2), tolerance);
        Assert.assertEquals("e03", e03, actual.getElement(0, 3), tolerance);
        Assert.assertEquals("e10", e10, actual.getElement(1, 0), tolerance);
        Assert.assertEquals("e11", e11, actual.getElement(1, 1), tolerance);
        Assert.assertEquals("e12", e12, actual.getElement(1, 2), tolerance);
        Assert.assertEquals("e13", e13, actual.getElement(1, 3), tolerance);
        Assert.assertEquals("e20", e20, actual.getElement(2, 0), tolerance);
        Assert.assertEquals("e21", e21, actual.getElement(2, 1), tolerance);
        Assert.assertEquals("e22", e22, actual.getElement(2, 2), tolerance);
        Assert.assertEquals("e23", e23, actual.getElement(2, 3), tolerance);
        Assert.assertEquals("e30", e30, actual.getElement(3, 0), tolerance);
        Assert.assertEquals("e31", e31, actual.getElement(3, 1), tolerance);
        Assert.assertEquals("e32", e32, actual.getElement(3, 2), tolerance);
        Assert.assertEquals("e33", e33, actual.getElement(3, 3), tolerance);
    }

    /**
     * Verify the elements of a location-precision matrix to within some
     * tolerance.
     *
     * @param e00 the expected element in row 0 of column 0
     * @param e01 the expected element in row 0 of column 1
     * @param e02 the expected element in row 0 of column 2
     * @param e03 the expected element in row 0 of column 3
     * @param e10 the expected element in row 1 of column 0
     * @param e11 the expected element in row 1 of column 1
     * @param e12 the expected element in row 1 of column 2
     * @param e13 the expected element in row 1 of column 3
     * @param e20 the expected element in row 2 of column 0
     * @param e21 the expected element in row 2 of column 1
     * @param e22 the expected element in row 2 of column 2
     * @param e23 the expected element in row 2 of column 3
     * @param e30 the expected element in row 3 of column 0
     * @param e31 the expected element in row 3 of column 1
     * @param e32 the expected element in row 3 of column 2
     * @param e33 the expected element in row 3 of column 3
     * @param actual the matrix to test (not null, unaffected)
     * @param tolerance the allowable difference for each element (&ge;0)
     */
    public static void assertEquals(float e00, float e01, float e02, float e03,
            float e10, float e11, float e12, float e13,
            float e20, float e21, float e22, float e23,
            float e30, float e31, float e32, float e33,
            RMat44Arg actual, float tolerance) {
        Assert.assertEquals("e00", e00, actual.getElement(0, 0), tolerance);
        Assert.assertEquals("e01", e01, actual.getElement(0, 1), tolerance);
        Assert.assertEquals("e02", e02, actual.getElement(0, 2), tolerance);
        Assert.assertEquals("e03", e03, actual.getElement(0, 3), tolerance);
        Assert.assertEquals("e10", e10, actual.getElement(1, 0), tolerance);
        Assert.assertEquals("e11", e11, actual.getElement(1, 1), tolerance);
        Assert.assertEquals("e12", e12, actual.getElement(1, 2), tolerance);
        Assert.assertEquals("e13", e13, actual.getElement(1, 3), tolerance);
        Assert.assertEquals("e20", e20, actual.getElement(2, 0), tolerance);
        Assert.assertEquals("e21", e21, actual.getElement(2, 1), tolerance);
        Assert.assertEquals("e22", e22, actual.getElement(2, 2), tolerance);
        Assert.assertEquals("e23", e23, actual.getElement(2, 3), tolerance);
        Assert.assertEquals("e30", e30, actual.getElement(3, 0), tolerance);
        Assert.assertEquals("e31", e31, actual.getElement(3, 1), tolerance);
        Assert.assertEquals("e32", e32, actual.getElement(3, 2), tolerance);
        Assert.assertEquals("e33", e33, actual.getElement(3, 3), tolerance);
    }

    /**
     * Verify the components of a single-precision vector to within some
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
     * Verify the components of an integer vector.
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
     * Verify the equality of 2 quaternions to within some tolerance.
     *
     * @param expected the expected value (not null, unaffected)
     * @param actual the vector to test (not null, unaffected)
     * @param tolerance the allowable difference for each component (&ge;0)
     */
    public static void assertEquals(
            QuatArg expected, QuatArg actual, float tolerance) {
        assertEquals(expected.getX(), expected.getY(), expected.getZ(),
                expected.getW(), actual, tolerance);
    }

    /**
     * Verify the equality of 2 single-precision vectors to within some
     * tolerance.
     *
     * @param expected the expected value (not null, unaffected)
     * @param actual the vector to test (not null, unaffected)
     * @param tolerance the allowable difference for each component (&ge;0)
     */
    public static void assertEquals(
            Vec3Arg expected, Vec3Arg actual, float tolerance) {
        assertEquals(expected.getX(), expected.getY(), expected.getZ(),
                actual, tolerance);
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
     * Initialize the loaded native library.
     */
    public static void initializeNativeLibrary() {
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
        boolean success = Jolt.newFactory();
        assert success;
        Jolt.registerTypes();
    }

    /**
     * Load some flavor of native library, preferably a Debug build.
     * <p>
     * The search order is:
     * <ol>
     * <li>DebugSp</li>
     * <li>DebugDp</li>
     * <li>ReleaseSp</li>
     * <li>ReleaseDp</li>
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
            } else {
                success = loadNativeLibrary(directory, "Release", "Sp");
                if (success) {
                    Assert.assertFalse(Jolt.isDoublePrecision());
                } else {
                    success = loadNativeLibrary(directory, "Release", "Dp");
                    if (success) {
                        Assert.assertTrue(Jolt.isDoublePrecision());
                    }
                }
            }
        }
        Assert.assertTrue(success);
    }

    /**
     * Load a specific jolt-jni native library from the filesystem.
     *
     * @param directory (not null, readable directory, unaffected)
     * @param buildType "Debug" or "Release"
     * @param flavor "Sp" or "Dp"
     * @return true after successful load, otherwise false
     */
    public static boolean loadNativeLibrary(
            File directory, String buildType, String flavor) {
        assert directory.exists() : directory;
        assert directory.isDirectory() : directory;
        assert directory.canRead() : directory;
        assert buildType.equals("Debug") || buildType.equals("Release") :
                buildType;
        assert flavor.equals("Sp") || flavor.equals("Dp") : flavor;

        String name;
        String subdirectory;
        if (NativeVariant.Os.isLinux()) {
            name = "libjoltjni.so";
            if (NativeVariant.Cpu.isARM()) {
                if (NativeVariant.Cpu.is64()) {
                    subdirectory = "linux_ARM64";
                } else {
                    subdirectory = "linux_ARM32hf";
                }
            } else if (hasFmaFeatures()) {
                subdirectory = "linux64_fma";
            } else {
                subdirectory = "linux64";
            }

        } else if (NativeVariant.Os.isMac()) {
            name = "libjoltjni.dylib";
            if (NativeVariant.Cpu.isARM()) {
                subdirectory = "macOSX_ARM64";
            } else {
                subdirectory = "macOSX64";
            }

        } else if (NativeVariant.Os.isWindows()) {
            name = "joltjni.dll";
            if (hasAvx2Features()) {
                subdirectory = "windows64_avx2";
            } else {
                subdirectory = "windows64";
            }

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

        if (!success && (subdirectory.endsWith("_avx2")
                || subdirectory.endsWith("_fma"))) {
            // retry loading without that _avx2/_fma infix

            subdirectory = subdirectory.replace("_avx2", "");
            subdirectory = subdirectory.replace("_fma", "");
            file = new File(directory, subdirectory);
            file = new File(file, bt);
            file = new File(file, f);
            file = new File(file, name);
            absoluteFilename = file.getAbsolutePath();
            if (file.exists() && file.canRead()) {
                System.load(absoluteFilename);
                success = true;
            }
        }

        return success;
    }

    /**
     * Load some flavor of native library, preferably a Release build.
     * <p>
     * The search order is:
     * <ol>
     * <li>ReleaseSp</li>
     * <li>ReleaseDp</li>
     * <li>DebugSp</li>
     * <li>DebugDp</li>
     * </ol>
     */
    public static void loadNativeLibraryRelease() {
        File directory = new File("build/libs/joltjni/shared");

        boolean success = loadNativeLibrary(directory, "Release", "Sp");
        if (success) {
            Assert.assertFalse(Jolt.isDoublePrecision());
        } else {
            success = loadNativeLibrary(directory, "Release", "Dp");
            if (success) {
                Assert.assertTrue(Jolt.isDoublePrecision());
            } else {
                success = loadNativeLibrary(directory, "Debug", "Sp");
                if (success) {
                    Assert.assertFalse(Jolt.isDoublePrecision());
                } else {
                    success = loadNativeLibrary(directory, "Debug", "Dp");
                    if (success) {
                        Assert.assertTrue(Jolt.isDoublePrecision());
                    }
                }
            }
        }
        Assert.assertTrue(success);

        // Print native-library details on the console:
        System.out.print("jolt-jni version ");
        System.out.print(Jolt.versionString());
        String buildType = Jolt.buildType();
        System.out.print('-');
        System.out.print(buildType);
        if (Jolt.isDoublePrecision()) {
            System.out.print("Dp");
        } else {
            System.out.print("Sp");
        }
        System.out.println(" initializing");
        System.out.println();
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
     * However, if freeing is automated, {@code close()} is neither invoked nor
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

    /**
     * Test for the presence of four x86_64 ISA extensions that Jolt Physics can
     * exploit, including AVX2.
     *
     * @return {@code true} if those ISA extensions are present, otherwise
     * {@code false}
     */
    private static boolean hasAvx2Features() {
        boolean result = hasCpuFeatures("avx", "avx2", "sse4_1", "sse4_2");
        return result;
    }

    /**
     * Test whether all of the named CPU features are present.
     *
     * @param requiredFeatures the names of the features to test for
     * @return {@code true} if all are present, otherwise {@code false}
     */
    private static boolean hasCpuFeatures(String... requiredFeatures) {
        // Obtain the list of CPU feature strings from OSHI:
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor cpu = hal.getProcessor();
        List<String> oshiList = cpu.getFeatureFlags();

        Pattern pattern = Pattern.compile("[a-z][a-z0-9_]*");

        // Convert the list to a collection of feature names:
        Collection<String> presentFeatures = new TreeSet<>();
        for (String oshiString : oshiList) {
            String lcString = oshiString.toLowerCase(Locale.ROOT);
            Matcher matcher = pattern.matcher(lcString);
            while (matcher.find()) {
                String featureName = matcher.group();
                presentFeatures.add(featureName);
            }
        }

        // Test for each required CPU feature:
        for (String featureName : requiredFeatures) {
            String linuxName = featureName.toLowerCase(Locale.ROOT);
            String windowsName = "pf_" + linuxName + "_instructions_available";
            boolean isPresent = presentFeatures.contains(linuxName)
                    || presentFeatures.contains(windowsName);
            if (!isPresent) {
                return false;
            }
        }

        return true;
    }

    /**
     * Test for the presence of seven x86_64 ISA extensions that Jolt Physics
     * can exploit, including AVX2 and FMA.
     *
     * @return {@code true} if those ISA extensions are present, otherwise
     * {@code false}
     */
    private static boolean hasFmaFeatures() {
        boolean result = hasCpuFeatures(
                "avx", "avx2", "bmi1", "f16c", "fma", "sse4_1", "sse4_2");
        return result;
    }
}
