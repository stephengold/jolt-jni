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
package com.github.stephengold.joltjni;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

/**
 * Utility methods providing JNI access to Jolt Physics.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class Jolt {
    // *************************************************************************
    // constants

    /**
     * default rounding of corners in convex shapes (in meters)
     * <p>
     * value should match Jolt/Physics/PhysicsSettings.h
     */
    final public static float cDefaultConvexRadius = 0.05f;
    /**
     * empty sub-shape ID
     * <p>
     * value should match Jolt/Physics/Collision/Shape/SubShapeID.h
     */
    final public static int cEmptySubShapeId = 0xFFFF_FFFF;
    /**
     * invalid body ID
     * <p>
     * value should match Jolt/Physics/Body/BodyID.h
     */
    final public static int cInvalidBodyId = 0xFFFF_FFFF;
    /**
     * invalid character ID
     * <p>
     * value should match Jolt/Physics/Character/CharacterID.h
     */
    final public static int cInvalidCharacterId = 0xFFFF_FFFF;
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
    /**
     * index of the X component, for swizzling
     */
    final public static int SWIZZLE_X = 0;
    /**
     * index of the Y component, for swizzling
     */
    final public static int SWIZZLE_Y = 1;
    /**
     * index of the Z component, for swizzling
     */
    final public static int SWIZZLE_Z = 2;
    /**
     * generic null pointer (to expedite porting of C++ code)
     */
    final public static Object nullptr = null;
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
     * Return the Jolt-JNI build-type string that's hard-coded in the native
     * library.
     *
     * @return either "Debug" or "Release"
     */
    native public static String buildType();

    /**
     * Return the cumulative number of {@code delete} operations in glue code,
     * in Debug native libraries.
     *
     * @return the count (&ge;0)
     */
    native public static int countDeletes();

    /**
     * Return the cumulative number of {@code new} operations in glue code, in
     * Debug native libraries, excluding ref targets.
     *
     * @return the count (&ge;0)
     */
    native public static int countNews();

    /**
     * Destroy the factory used for collision dispatch and object-stream
     * serialization.
     *
     * @see #newFactory()
     */
    native public static void destroyFactory();

    /**
     * Append a message to the determinism log. (native macro: JPH_DET_LOG)
     *
     * @param message (not {@code null})
     */
    native public static void detLog(String message);

    /**
     * Return the installed assert callback.
     *
     * @return the virtual address of the callback, or zero if none
     * @see #installAssertCallback(long)
     */
    native public static long getAssertCallback();

    /**
     * Return a string describing some important configuration settings of the
     * native library. (see Jolt/ConfigurationString.h)
     *
     * @return the string value
     */
    native public static String getConfigurationString();

    /**
     * Test whether the native library implements CPU-based compute systems,
     * which are intended mainly for debugging. (native macro:
     * JPH_USE_CPU_COMPUTE)
     *
     * @return {@code true} if implemented, otherwise {@code false}
     */
    native public static boolean implementsComputeCpu();

    /**
     * Test whether the native library implements compute systems based on
     * Microsoft's DirectX 12 API. (native macro: JPH_USE_DX12)
     *
     * @return {@code true} if implemented, otherwise {@code false}
     */
    native public static boolean implementsComputeDx12();

    /**
     * Test whether the native library implements compute systems based on
     * Apple's Metal API. (native macro: JPH_USE_MTL)
     *
     * @return {@code true} if implemented, otherwise {@code false}
     */
    native public static boolean implementsComputeMtl();

    /**
     * Test whether the native library implements compute systems based on
     * Khronos's Vulkan API. (native macro: JPH_USE_VK)
     *
     * @return {@code true} if implemented, otherwise {@code false}
     */
    native public static boolean implementsComputeVk();

    /**
     * Test whether the native library implements debug rendering. (native
     * macro: JPH_DEBUG_RENDERER)
     *
     * @return {@code true} if implemented, otherwise {@code false}
     */
    native public static boolean implementsDebugRendering();

    /**
     * Test whether the native library implements extra logging to help debug
     * determinism issues. (native macro: JPH_ENABLE_DETERMINISM_LOG)
     *
     * @return {@code true} if implemented, otherwise {@code false}
     */
    native public static boolean implementsDeterminismLog();

    /**
     * Install an alternative trace callback that sends to the Android log with
     * the specified priority and tag.
     *
     * @param priority the desired priority of trace output in the log
     * @param tag the log tag to identify trace output (may be {@code null})
     */
    native public static void installAndroidTraceCallback(
            int priority, String tag);

    /**
     * Install the specified assert callback.
     *
     * @param callbackVa the virtual address of the desired callback
     * @see #getAssertCallback()
     */
    native public static void installAssertCallback(long callbackVa);

    /**
     * Install an alternative trace callback that writes to {@code cerr}.
     */
    native public static void installCerrTraceCallback();

    /**
     * Install an alternative assert callback that crashes the JVM with an
     * EXCEPTION_ACCESS_VIOLATION or SIGILL. Intended for testing only!
     */
    native public static void installCrashAssertCallback();

    /**
     * Install the default assert callback, which writes to {@code cout} and
     * triggers a native breakpoint.
     */
    native public static void installDefaultAssertCallback();

    /**
     * Install the default trace callback, which writes to {@code cout}.
     */
    native public static void installDefaultTraceCallback();

    /**
     * Install an alternative assert callback that silently ignores assertions.
     */
    native public static void installIgnoreAssertCallback();

    /**
     * Install an alternative trace callback that writes to the specified
     * {@code PrintStream}.
     *
     * @param stream where to send trace output (not {@code null})
     */
    native public static void installJavaTraceCallback(PrintStream stream);

    /**
     * Test whether the native library uses double-precision location vectors.
     * (native macro: JPH_DOUBLE_PRECISION)
     *
     * @return {@code true} if double-precision, otherwise {@code false}
     */
    native public static boolean isDoublePrecision();

    /**
     * Load raw bytes from the named classpath resource.
     *
     * @param resourceName the name of the resource (not {@code null})
     * @return a new direct buffer
     */
    public static ByteBuffer loadResourceAsBytes(String resourceName) {
        // Read the resource to determine its size in bytes:
        InputStream inputStream
                = Jolt.class.getResourceAsStream(resourceName);
        if (inputStream == null) {
            throw new RuntimeException("resource not found:  " + resourceName);
        }
        int totalBytes = 0;
        byte[] tmpArray = new byte[4096];
        try {
            while (true) {
                int numBytesRead = inputStream.read(tmpArray);
                if (numBytesRead < 0) {
                    break;
                }
                totalBytes += numBytesRead;
            }
            inputStream.close();

        } catch (IOException exception) {
            throw new RuntimeException(
                    "failed to read resource " + resourceName);
        }
        ByteBuffer result = Jolt.newDirectByteBuffer(totalBytes);

        // Read the resource again to fill the buffer with data:
        inputStream = Jolt.class.getResourceAsStream(resourceName);
        if (inputStream == null) {
            throw new RuntimeException("resource not found:  " + resourceName);
        }
        try {
            while (true) {
                int numBytesRead = inputStream.read(tmpArray);
                if (numBytesRead < 0) {
                    break;

                } else if (numBytesRead == tmpArray.length) {
                    result.put(tmpArray);

                } else {
                    for (int i = 0; i < numBytesRead; ++i) {
                        byte b = tmpArray[i];
                        result.put(b);
                    }
                }
            }
            inputStream.close();

        } catch (IOException exception) {
            throw new RuntimeException(
                    "failed to read resource " + resourceName);
        }

        result.flip();
        return result;
    }

    /**
     * Create a direct {@code ByteBuffer} with native byte order and the
     * specified capacity.
     *
     * @param numBytes the desired capacity (in bytes)
     * @return a new direct buffer, zeroed and rewound but not flipped
     */
    public static ByteBuffer newDirectByteBuffer(int numBytes) {
        ByteBuffer result = ByteBuffer.allocateDirect(numBytes);
        result.order(ByteOrder.nativeOrder());

        assert result.capacity() == numBytes : result.capacity();
        assert result.isDirect();
        assert result.limit() == numBytes : result.limit();
        assert result.position() == 0 : result.position();
        return result;
    }

    /**
     * Create a direct {@code DoubleBuffer} with native byte order and the
     * specified capacity.
     *
     * @param numDoubles the desired capacity (in doubles)
     * @return a new direct buffer, zeroed and rewound but not flipped
     */
    public static DoubleBuffer newDirectDoubleBuffer(int numDoubles) {
        ByteBuffer byteBuffer
                = ByteBuffer.allocateDirect(numDoubles * Double.BYTES);
        byteBuffer.order(ByteOrder.nativeOrder());
        DoubleBuffer result = byteBuffer.asDoubleBuffer();

        assert result.capacity() == numDoubles : result.capacity();
        assert result.isDirect();
        assert result.limit() == numDoubles : result.limit();
        assert result.position() == 0 : result.position();
        return result;
    }

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
        assert result.isDirect();
        assert result.limit() == numFloats : result.limit();
        assert result.position() == 0 : result.position();
        return result;
    }

    /**
     * Create a direct {@code IntBuffer} with native byte order and the
     * specified capacity.
     *
     * @param numInts the desired capacity (in ints)
     * @return a new direct buffer, zeroed and rewound but not flipped
     */
    public static IntBuffer newDirectIntBuffer(int numInts) {
        ByteBuffer byteBuffer
                = ByteBuffer.allocateDirect(numInts * Integer.BYTES);
        byteBuffer.order(ByteOrder.nativeOrder());
        IntBuffer result = byteBuffer.asIntBuffer();

        assert result.capacity() == numInts : result.capacity();
        assert result.isDirect();
        assert result.limit() == numInts : result.limit();
        assert result.position() == 0 : result.position();
        return result;
    }

    /**
     * Create a direct {@code LongBuffer} with native byte order and the
     * specified capacity.
     *
     * @param numLongs the desired capacity (in longs)
     * @return a new direct buffer, zeroed and rewound but not flipped
     */
    public static LongBuffer newDirectLongBuffer(int numLongs) {
        ByteBuffer byteBuffer
                = ByteBuffer.allocateDirect(numLongs * Long.BYTES);
        byteBuffer.order(ByteOrder.nativeOrder());
        LongBuffer result = byteBuffer.asLongBuffer();

        assert result.capacity() == numLongs : result.capacity();
        assert result.isDirect();
        assert result.limit() == numLongs : result.limit();
        assert result.position() == 0 : result.position();
        return result;
    }

    /**
     * Create a direct {@code ShortBuffer} with native byte order and the
     * specified capacity.
     *
     * @param numShorts the desired capacity (in shorts)
     * @return a new direct buffer, zeroed and rewound but not flipped
     */
    public static ShortBuffer newDirectShortBuffer(int numShorts) {
        ByteBuffer byteBuffer
                = ByteBuffer.allocateDirect(numShorts * Short.BYTES);
        byteBuffer.order(ByteOrder.nativeOrder());
        ShortBuffer result = byteBuffer.asShortBuffer();

        assert result.capacity() == numShorts : result.capacity();
        assert result.isDirect();
        assert result.limit() == numShorts : result.limit();
        assert result.position() == 0 : result.position();
        return result;
    }

    /**
     * Create a factory for collision dispatch and object-stream serialization.
     *
     * @return {@code true} if successful, otherwise {@code false}
     * @see #destroyFactory()
     */
    native public static boolean newFactory();

    /**
     * Dump profiler data. (native macro: JPH_PROFILE_DUMP)
     *
     * @param message (not {@code null})
     */
    native public static void profileDump(String message);

    /**
     * Terminate the profiler. (native macro: JPH_PROFILE_END)
     */
    native public static void profileEnd();

    /**
     * Increment the profiler's frame counter. (native macro:
     * JPH_PROFILE_NEXTFRAME)
     */
    native public static void profileNextFrame();

    /**
     * Start an instrumented code section. (native macro: JPH_PROFILE_START)
     *
     * @param name the section name (not {@code null})
     */
    native public static void profileStart(String name);

    /**
     * Register the allocation hook to use the specified functions. This must be
     * done before any other Jolt function is called.
     *
     * @param allocateVa the virtual address of the desired Allocate function
     * @param reallocateVa the virtual address of the desired Reallocate
     * function
     * @param freeVa the virtual address of the desired Free function
     * @param alignedAllocateVa the virtual address of the desired
     * AlignedAllocate function
     * @param alignedFreeVa the virtual address of the desired AlignedFree
     * function
     */
    native public static void registerCustomAllocator(
            long allocateVa, long reallocateVa, long freeVa,
            long alignedAllocateVa, long alignedFreeVa);

    /**
     * Register the allocation hook to use malloc/free. This must be done before
     * any other Jolt function is called. (see Jolt/Core/Memory.cpp)
     */
    native public static void registerDefaultAllocator();

    /**
     * Register {@code HairSettings} with the factory. (see
     * Jolt/RegisterTypes.h)
     *
     * @see #unregisterTypes()
     */
    native public static void registerHair();

    /**
     * Register all physics types except {@code HairSettings} with the factory
     * and install their collision handlers. (see Jolt/RegisterTypes.h)
     *
     * @see #registerHair()
     * @see #unregisterTypes()
     */
    native public static void registerTypes();

    /**
     * Enable or disable allocation tracing in Debug native libraries.
     *
     * @param setting {@code true} to enable tracing, {@code false} to disable
     * it (default=false)
     */
    native public static void setTraceAllocations(boolean setting);

    /**
     * Test whether the native library supports the ObjectStream format. (native
     * macro: JPH_OBJECT_STREAM)
     *
     * @return {@code true} if supported, otherwise {@code false}
     */
    native public static boolean supportsObjectStream();

    /**
     * Execute a test written in native code. Intended for testing only.
     *
     * @param args command-line arguments
     */
    native public static void test000(String... args);

    /**
     * Unregister all physics types with the factory. (see Jolt/RegisterTypes.h)
     *
     * @see #registerTypes()
     */
    native public static void unregisterTypes();

    /**
     * Return the Jolt-JNI version string that's hard-coded into the native
     * library.
     *
     * @return the version string (not {@code null}, not empty)
     */
    native public static String versionString();
}
