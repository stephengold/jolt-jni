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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Utility methods providing JNI access to Jolt Physics.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class Jolt {
    // *************************************************************************
    // constants

    /**
     * single-precision value of Pi
     */
    final public static float JPH_PI = (float) Math.PI;
    /**
     * standard 2nd argument to the {@code JobSystemThreadPool} constructor
     * <p>
     * value should match Jolt/Physics/PhysicsSettings.h
     */
    final public static int cMaxPhysicsBarriers = 8;
    /**
     * standard first argument to the {@code JobSystemThreadPool} constructor
     * <p>
     * value should match Jolt/Physics/PhysicsSettings.h
     */
    final public static int cMaxPhysicsJobs = 2_048;
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private Jolt() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the jolt-jni build-type string.
     *
     * @return either "Debug" or "Release"
     */
    native public static String buildType();

    /**
     * Convert the specified angle from degrees to radians.
     *
     * @param degrees the angle to convert (in degrees)
     * @return the converted angle (in radians)
     */
    public static float degreesToRadians(float degrees) {
        return degrees * (JPH_PI / 180f);
    }

    /**
     * Destroy the factory for deserialization of saved data.
     *
     * @see newFactory
     */
    native public static void destroyFactory();

    /**
     * Append a message to the determinism log.
     *
     * @param message (not null)
     */
    native public static void detLog(String message);

    /**
     * Return a string containing important configuration settings.
     *
     * @return the string value
     */
    native public static String getConfigurationString();

    /**
     * Return a hash code for the specified data bytes.
     *
     * @param dataVa the virtual address of the data, or 0 for no data
     * @param inSize the number of data bytes, or 0 for no data
     * @return the hash code
     */
    native public static long hashBytes(long dataVa, int inSize);

    /**
     * Combine the specified quaternion with the specified hash code.
     *
     * @param quaternion the quaternion to combine (not null, unaffected)
     * @param oldHash the old hash code
     * @return the new hash code
     */
    public static long hashBytes(Quat quaternion, long oldHash) {
        float qw = quaternion.getW();
        float qx = quaternion.getX();
        float qy = quaternion.getY();
        float qz = quaternion.getZ();
        long result = hashBytes(qx, qy, qz, qw, oldHash);

        return result;
    }

    /**
     * Combine the specified vector with the specified hash code.
     *
     * @param vector the vector to combine (not null, unaffected)
     * @param oldHash the old hash code
     * @return the new hash code
     */
    public static long hashBytes(RVec3 vector, long oldHash) {
        double xx = vector.xx();
        double yy = vector.yy();
        double zz = vector.zz();
        long result = hashBytes(xx, yy, zz, oldHash);

        return result;
    }

    /**
     * Test whether the native library implements debug rendering. (native
     * macro: JPH_DEBUG_RENDERER)
     *
     * @return true if implemented, otherwise false
     */
    native public static boolean implementsDebugRendering();

    /**
     * Test whether the native library implements extra logging to help debug
     * determinism issues. (native macro: JPH_DET_LOG)
     *
     * @return true if implemented, otherwise false
     */
    native public static boolean implementsDeterminismLog();

    /**
     * Install the default assert callback.
     */
    native public static void installDefaultAssertCallback();

    /**
     * Install the default trace callback.
     */
    native public static void installDefaultTraceCallback();

    /**
     * Test whether the native library uses double-precision location vectors.
     * (native macro: JPH_DOUBLE_PRECISION)
     *
     * @return true if double-precision, otherwise false
     */
    native public static boolean isDoublePrecision();

    /**
     * Create a direct {@code FloatBuffer} with native byte order and the
     * specified capacity.
     *
     * @param numFloats the desired capacity (in floats)
     * @return a new direct buffer, zeroed and rewound but not flipped
     */
    public static FloatBuffer newDirectFloatBuffer(int numFloats) {
        ByteBuffer byteBuffer
                = ByteBuffer.allocateDirect(numFloats * Float.BYTES);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer result = byteBuffer.asFloatBuffer();

        assert result.capacity() == numFloats : result.capacity();
        assert result.limit() == numFloats : result.limit();
        assert result.position() == 0 : result.position();
        return result;
    }

    /**
     * Create a direct {@code IntBuffer} with native byte order and the
     * specified capacity.
     *
     * @param numInts the desired capacity (in floats)
     * @return a new direct buffer, zeroed and rewound but not flipped
     */
    public static IntBuffer newDirectIntBuffer(int numInts) {
        ByteBuffer byteBuffer
                = ByteBuffer.allocateDirect(numInts * Integer.BYTES);
        byteBuffer.order(ByteOrder.nativeOrder());
        IntBuffer result = byteBuffer.asIntBuffer();

        assert result.capacity() == numInts : result.capacity();
        assert result.limit() == numInts : result.limit();
        assert result.position() == 0 : result.position();
        return result;
    }

    /**
     * Dump profiler data.
     *
     * @param message (not null)
     */
    native public static void profileDump(String message);

    /**
     * Terminate the profiler.
     */
    native public static void profileEnd();

    /**
     * Increment profiler's frame counter.
     */
    native public static void profileNextFrame();

    /**
     * Start an instrumented code section.
     *
     * @param name the section name (not null)
     */
    native public static void profileStart(String name);

    /**
     * Create a factory for deserialization of saved data.
     *
     * @see destroyFactory
     */
    native public static void newFactory();

    /**
     * Convert the specified angle from radians to degrees.
     *
     * @param radians the angle to convert (in radians)
     * @return the converted angle (in degrees)
     */
    public static float radiansToDegrees(float radians) {
        return radians * (180f / JPH_PI);
    }

    /**
     * Intersect the specified axis-aligned box with the specified ray.
     *
     * @param startLocation the desired start location (not null, unaffected)
     * @param offset the desired end offset from the start (not null,
     * unaffected)
     * @param minimum the minimum coordinates of the box (not null, unaffected)
     * @param maximum the maximum coordinates of the box (not null, unaffected)
     * @return true if there is a hit
     */
    public static boolean rayAaBoxHits(Vec3Arg startLocation, Vec3Arg offset,
            Vec3Arg minimum, Vec3Arg maximum) {
        float startX = startLocation.getX();
        float startY = startLocation.getY();
        float startZ = startLocation.getZ();
        float offsetX = offset.getX();
        float offsetY = offset.getY();
        float offsetZ = offset.getZ();
        float minX = minimum.getX();
        float minY = minimum.getY();
        float minZ = minimum.getZ();
        float maxX = maximum.getX();
        float maxY = maximum.getY();
        float maxZ = maximum.getZ();
        boolean result = rayAaBoxHits(startX, startY, startZ,
                offsetX, offsetY, offsetZ, minX, minY, minZ, maxX, maxY, maxZ);

        return result;
    }

    /**
     * Register the allocation hook to use malloc/free. This must be done before
     * any other Jolt function is called.
     */
    native public static void registerDefaultAllocator();

    /**
     * Register all physics types with the factory and install their collision
     * handlers.
     *
     * @see unregisterTypes
     */
    native public static void registerTypes();

    /**
     * Enable or disable allocation tracing in Debug native libraries.
     *
     * @param setting true to enable tracing, false to disable it
     * (default=false)
     */
    native public static void setTraceAllocations(boolean setting);

    /**
     * Return the (binary) sign of the specified single-precision value.
     *
     * @param input the input value
     * @return -1 if the input is negative, otherwise +1
     */
    public static float sign(float input) {
        float result = (input < 0) ? -1f : 1f;
        return result;
    }

    /**
     * Test whether the native library supports the ObjectStream format. (native
     * macro: JPH_OBJECT_STREAM)
     *
     * @return true if supported, otherwise false
     */
    native public static boolean supportsObjectStream();

    /**
     * Unregister all physics types with the factory.
     *
     * @see registerTypes
     */
    native public static void unregisterTypes();

    /**
     * Return the jolt-jni version string.
     *
     * @return the version string (not null, not empty)
     */
    native public static String versionString();
    // *************************************************************************
    // native private methods

    native private static long hashBytes(
            double xx, double yy, double zz, long oldHash);

    native private static long hashBytes(
            float qx, float qy, float qz, float qw, long oldHash);

    native private static boolean rayAaBoxHits(float startX, float startY,
            float startZ, float offsetX, float offsetY, float offsetZ,
            float minX, float minY, float minZ, float maxX, float maxY,
            float maxZ);
}
