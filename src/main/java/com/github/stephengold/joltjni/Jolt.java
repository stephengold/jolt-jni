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

import com.github.stephengold.joltjni.readonly.ConstBody;
import com.github.stephengold.joltjni.readonly.ConstContactManifold;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

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
     * single-precision value of Pi
     */
    final public static float JPH_PI = (float) Math.PI;
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
     * Return the inverse cosine of the specified single-precision ratio. (see
     * Jolt/Math/Trigonometry.h)
     *
     * @param ratio the input cosine ratio (&ge;-1, &le;1)
     * @return the angle (in radians)
     */
    native public static float aCos(float ratio);

    /**
     * Return the inverse tangent of the specified single-precision ratio. (see
     * Jolt/Math/Trigonometry.h)
     *
     * @param ratio the input tangent ratio
     * @return the angle (in radians)
     */
    native public static float aTan(float ratio);

    /**
     * Return the angle of the specified single-precision right triangle. (see
     * Jolt/Math/Trigonometry.h)
     *
     * @param opposite the signed length of the opposite side
     * @param adjacent the signed length of the adjacent side
     * @return the angle (in radians)
     */
    native public static float aTan2(float opposite, float adjacent);

    /**
     * Return the Jolt-JNI build-type string that's hard-coded in the native
     * library.
     *
     * @return either "Debug" or "Release"
     */
    native public static String buildType();

    /**
     * Return the cosine of the specified single-precision angle. (see
     * Jolt/Math/Trigonometry.h)
     *
     * @param angle the input angle (in radians)
     * @return the cosine ratio
     */
    native public static float cos(float angle);

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
     * Convert the specified angle from degrees to radians.
     *
     * @param degrees the angle to convert (in degrees)
     * @return the converted angle (in radians)
     */
    public static float degreesToRadians(float degrees) {
        float result = degrees * (JPH_PI / 180f);
        return result;
    }

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
     * Estimate the contact impulses and body velocity changes resulting from a
     * collision. (see Physics/Collision/EstimateCollisionResponse.cpp)
     *
     * @param body1 the first colliding body (not {@code null}, unaffected)
     * @param body2 the 2nd colliding body (not {@code null}, unaffected)
     * @param manifold the collision manifold (not {@code null}, unaffected)
     * @param storeResult storage for impulses and velocities (not {@code null},
     * modified)
     * @param combinedFriction the combined friction of the 2 bodies
     * @param combinedRestitution the combined restitution of the 2 bodies
     */
    public static void estimateCollisionResponse(
            ConstBody body1, ConstBody body2, ConstContactManifold manifold,
            CollisionEstimationResult storeResult, float combinedFriction,
            float combinedRestitution) {
        estimateCollisionResponse(body1, body2, manifold, storeResult,
                combinedFriction, combinedRestitution, 1f);
    }

    /**
     * Estimate the contact impulses and body velocity changes resulting from a
     * collision. (see Physics/Collision/EstimateCollisionResponse.cpp)
     *
     * @param body1 the first colliding body (not {@code null}, unaffected)
     * @param body2 the 2nd colliding body (not {@code null}, unaffected)
     * @param manifold the collision manifold (not {@code null}, unaffected)
     * @param storeResult storage for impulses and velocities (not {@code null},
     * modified)
     * @param combinedFriction the combined friction of the 2 bodies
     * @param combinedRestitution the combined restitution of the 2 bodies
     * @param minVelocity the minimum speed for restitution to be applied (in
     * meters per second, default=1)
     */
    public static void estimateCollisionResponse(
            ConstBody body1, ConstBody body2, ConstContactManifold manifold,
            CollisionEstimationResult storeResult, float combinedFriction,
            float combinedRestitution, float minVelocity) {
        estimateCollisionResponse(body1, body2, manifold, storeResult,
                combinedFriction, combinedRestitution, minVelocity, 10);
    }

    /**
     * Estimate the contact impulses and body velocity changes resulting from a
     * collision. (see Physics/Collision/EstimateCollisionResponse.cpp)
     *
     * @param body1 the first colliding body (not {@code null}, unaffected)
     * @param body2 the 2nd colliding body (not {@code null}, unaffected)
     * @param manifold the collision manifold (not {@code null}, unaffected)
     * @param storeResult storage for impulses and velocities (not {@code null},
     * modified)
     * @param combinedFriction the combined friction of the 2 bodies
     * @param combinedRestitution the combined restitution of the 2 bodies
     * @param minVelocity the minimum speed for restitution to be applied (in
     * meters per second, default=1)
     * @param numIterations the number of iterations the impulse solver to use
     * (default=10)
     */
    public static void estimateCollisionResponse(
            ConstBody body1, ConstBody body2, ConstContactManifold manifold,
            CollisionEstimationResult storeResult, float combinedFriction,
            float combinedRestitution, float minVelocity, int numIterations) {
        long body1Va = body1.targetVa();
        long body2Va = body2.targetVa();
        long manifoldVa = manifold.targetVa();
        long resultVa = storeResult.va();
        estimateCollisionResponse(body1Va, body2Va, manifoldVa, resultVa,
                combinedFriction, combinedRestitution,
                minVelocity, numIterations);
    }

    /**
     * Return a string describing some important configuration settings of the
     * native library. (see Jolt/ConfigurationString.h)
     *
     * @return the string value
     */
    native public static String getConfigurationString();

    /**
     * Return a hash code for the specified data bytes. (see
     * Jolt/Core/HashCombine.h)
     *
     * @param dataVa the virtual address of the data, or 0 for no data
     * @param inSize the number of data bytes, or 0 for no data
     * @return the hash code
     */
    native public static long hashBytes(long dataVa, int inSize);

    /**
     * Combine the specified quaternion with the specified hash code.
     *
     * @param quaternion the quaternion to combine (not {@code null},
     * unaffected)
     * @param oldHash the old hash code
     * @return the new hash code
     */
    public static long hashBytes(QuatArg quaternion, long oldHash) {
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
     * @param vector the vector to combine (not {@code null}, unaffected)
     * @param oldHash the old hash code
     * @return the new hash code
     */
    public static long hashBytes(RVec3Arg vector, long oldHash) {
        double xx = vector.xx();
        double yy = vector.yy();
        double zz = vector.zz();
        long result = hashBytes(xx, yy, zz, oldHash);

        return result;
    }

    /**
     * Combine the specified 32-bit integer with the specified hash code. (see
     * Jolt/Core/HashCombine.h)
     *
     * @param oldHash the old hash code
     * @param iValue the integer value to combine
     * @return the new hash code
     */
    native public static long hashCombine(long oldHash, int iValue);

    /**
     * Combine the specified 64-bit integer with the specified hash code.
     *
     * @param oldHash the old hash code
     * @param lValue the integer value to combine
     * @return the new hash code
     */
    native public static long hashCombine(long oldHash, long lValue);

    /**
     * Combine the specified location vector with the specified hash code.
     *
     * @param vector the vector to combine (not {@code null}, unaffected)
     * @param oldHash the old hash code
     * @return the new hash code
     */
    public static long hashCombine(long oldHash, RVec3Arg vector) {
        double xx = vector.xx();
        double yy = vector.yy();
        double zz = vector.zz();
        long result = hashCombineRVec3(oldHash, xx, yy, zz);

        return result;
    }

    /**
     * Combine the specified vector with the specified hash code.
     *
     * @param vector the vector to combine (not {@code null}, unaffected)
     * @param oldHash the old hash code
     * @return the new hash code
     */
    public static long hashCombine(long oldHash, Vec3Arg vector) {
        float x = vector.getX();
        float y = vector.getY();
        float z = vector.getZ();
        long result = hashCombineVec3(oldHash, x, y, z);

        return result;
    }

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
     * Install an alternative trace callback that writes to {@code cerr}.
     */
    native public static void installCerrTraceCallback();

    /**
     * Install the default assert callback.
     */
    native public static void installDefaultAssertCallback();

    /**
     * Install the default trace callback, which writes to {@code cout}.
     */
    native public static void installDefaultTraceCallback();

    /**
     * Install a trace callback that writes to the specified
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
     * List all classes in the specified Jolt-JNI package.
     *
     * @param packageName the name of the package (must start with
     * "com.github.stephengold.joltjni")
     * @return a new collection
     */
    public static Set<Class<?>> listClasses(String packageName) {
        ClassLoader loader = Jolt.class.getClassLoader();
        String resourcePath = packageName.replaceAll("[.]", "/");
        InputStream stream = loader.getResourceAsStream(resourcePath);
        if (stream == null) {
            System.err.println("resourcePath = " + resourcePath);
            System.exit(1);
        }
        InputStreamReader isr
                = new InputStreamReader(stream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(isr);

        Set<Class<?>> result = new HashSet<>();
        while (true) {
            try {
                String line = reader.readLine();
                if (line == null) {
                    break;

                } else if (line.endsWith(".class")) {
                    int dotPos = line.lastIndexOf('.');
                    String className = line.substring(0, dotPos);
                    try {
                        Class<?> clas
                                = Class.forName(packageName + "." + className);
                        result.add(clas);
                    } catch (ClassNotFoundException exception) {
                        System.err.println("className = " + className);
                        System.exit(0);
                    }
                }
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }

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
     * Generate 3-D Perlin noise. (see TestFramework/External/Perlin.cpp)
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     * @param z the Z coordinate
     * @param xWrap the wraparound interval for the X coordinate (power of 2) or
     * 0 for don't care
     * @param yWrap the wraparound interval for the Y coordinate (power of 2) or
     * 0 for don't care
     * @param zWrap the wraparound interval for the Z coordinate (power of 2) or
     * 0 for don't care
     * @return a sample value
     */
    native public static float perlinNoise3(
            float x, float y, float z, int xWrap, int yWrap, int zWrap);

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
     * Convert the specified angle from radians to degrees.
     *
     * @param radians the angle to convert (in radians)
     * @return the converted angle (in degrees)
     */
    public static float radiansToDegrees(float radians) {
        float result = radians * (180f / JPH_PI);
        return result;
    }

    /**
     * Intersect the specified axis-aligned box with the specified ray. (see
     * Jolt/Geometry/RayAABox.h)
     *
     * @param rayOrigin the origin of the ray (not {@code null}, unaffected)
     * @param invDirection the inverse direction of the ray (not {@code null},
     * unaffected)
     * @param minimum the minimum coordinates of the box (not {@code null},
     * unaffected)
     * @param maximum the maximum coordinates of the box (not {@code null},
     * unaffected)
     * @return the minimum distance along ray, or {@code FLT_MAX} if the ray
     * misses the box
     */
    public static float rayAaBox(Vec3Arg rayOrigin,
            RayInvDirection invDirection, Vec3Arg minimum, Vec3Arg maximum) {
        float originX = rayOrigin.getX();
        float originY = rayOrigin.getY();
        float originZ = rayOrigin.getZ();
        long invDirVa = invDirection.va();
        float minX = minimum.getX();
        float minY = minimum.getY();
        float minZ = minimum.getZ();
        float maxX = maximum.getX();
        float maxY = maximum.getY();
        float maxZ = maximum.getZ();
        float result = rayAaBox(originX, originY, originZ, invDirVa,
                minX, minY, minZ, maxX, maxY, maxZ);

        return result;
    }

    /**
     * Intersect the specified axis-aligned box with the specified ray. (see
     * Jolt/Geometry/RayAABox.h)
     *
     * @param startLocation the desired start location of the ray (not
     * {@code null}, unaffected)
     * @param offset the desired end offset from the start location (not
     * {@code null}, unaffected)
     * @param minimum the minimum coordinates of the box (not {@code null},
     * unaffected)
     * @param maximum the maximum coordinates of the box (not {@code null},
     * unaffected)
     * @return {@code true} if there is a hit, otherwise {@code false}
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
     * Intersect the specified capsule with the specified ray. (see
     * Jolt/Geometry/RayCapsule.h)
     *
     * @param rayOrigin the origin of the ray (not {@code null}, unaffected)
     * @param rayDirection the direction of the ray (not {@code null},
     * unaffected)
     * @param capsuleHalfHeight half the height of the capsule
     * @param capsuleRadius the radius of the capsule
     * @return the minimum distance along ray, or {@code FLT_MAX} if the ray
     * misses the capsule
     */
    public static float rayCapsule(Vec3Arg rayOrigin, Vec3Arg rayDirection,
            float capsuleHalfHeight, float capsuleRadius) {
        float originX = rayOrigin.getX();
        float originY = rayOrigin.getY();
        float originZ = rayOrigin.getZ();
        float directionX = rayDirection.getX();
        float directionY = rayDirection.getY();
        float directionZ = rayDirection.getZ();
        float result = rayCapsule(originX, originY, originZ, directionX,
                directionY, directionZ, capsuleHalfHeight, capsuleRadius);

        return result;
    }

    /**
     * Intersect the specified infinite cylinder with the specified ray. (see
     * Jolt/Geometry/RayCylinder.h)
     *
     * @param rayOrigin the origin of the ray (not {@code null}, unaffected)
     * @param rayDirection the direction of the ray (not {@code null},
     * unaffected)
     * @param cylinderRadius the radius of the cylinder
     * @return the minimum distance along ray, or {@code FLT_MAX} if the ray
     * misses the cylinder
     */
    public static float rayCylinder(
            Vec3Arg rayOrigin, Vec3Arg rayDirection, float cylinderRadius) {
        float originX = rayOrigin.getX();
        float originY = rayOrigin.getY();
        float originZ = rayOrigin.getZ();
        float directionX = rayDirection.getX();
        float directionY = rayDirection.getY();
        float directionZ = rayDirection.getZ();
        float result = rayInfiniteCylinder(originX, originY, originZ,
                directionX, directionY, directionZ, cylinderRadius);

        return result;
    }

    /**
     * Intersect the specified finite cylinder with the specified ray. (see
     * Jolt/Geometry/RayCylinder.h)
     *
     * @param rayOrigin the origin of the ray (not {@code null}, unaffected)
     * @param rayDirection the direction of the ray (not {@code null},
     * unaffected)
     * @param cylinderHalfHeight half of the height of the cylinder
     * @param cylinderRadius the radius of the cylinder
     * @return the minimum distance along ray, or {@code FLT_MAX} if the ray
     * misses the cylinder
     */
    public static float rayCylinder(Vec3Arg rayOrigin, Vec3Arg rayDirection,
            float cylinderHalfHeight, float cylinderRadius) {
        float originX = rayOrigin.getX();
        float originY = rayOrigin.getY();
        float originZ = rayOrigin.getZ();
        float directionX = rayDirection.getX();
        float directionY = rayDirection.getY();
        float directionZ = rayDirection.getZ();
        float result = rayFiniteCylinder(originX, originY, originZ, directionX,
                directionY, directionZ, cylinderHalfHeight, cylinderRadius);

        return result;
    }

    /**
     * Intersect the specified sphere with the specified ray. (see
     * Jolt/Geometry/RaySphere.h)
     *
     * @param rayOrigin the origin of the ray (not {@code null}, unaffected)
     * @param rayDirection the direction of the ray (not {@code null},
     * unaffected)
     * @param sphereCenter the center of the sphere (not {@code null},
     * unaffected)
     * @param sphereRadius the radius of the sphere
     * @return the minimum distance along ray, or {@code FLT_MAX} if the ray
     * misses the sphere
     */
    public static float raySphere(Vec3Arg rayOrigin, Vec3Arg rayDirection,
            Vec3Arg sphereCenter, float sphereRadius) {
        float originX = rayOrigin.getX();
        float originY = rayOrigin.getY();
        float originZ = rayOrigin.getZ();
        float directionX = rayDirection.getX();
        float directionY = rayDirection.getY();
        float directionZ = rayDirection.getZ();
        float centerX = sphereCenter.getX();
        float centerY = sphereCenter.getY();
        float centerZ = sphereCenter.getZ();
        float result = raySphere(
                originX, originY, originZ, directionX, directionY, directionZ,
                centerX, centerY, centerZ, sphereRadius);

        return result;
    }

    /**
     * Intersect the specified triangle with the specified ray. (see
     * Jolt/Geometry/RayTriangle.h)
     *
     * @param rayOrigin the origin of the ray (not {@code null}, unaffected)
     * @param rayDirection the direction of the ray (not {@code null},
     * unaffected)
     * @param v0 the first vertex of the triangle (not {@code null}, unaffected)
     * @param v1 the 2nd vertex of the triangle (not {@code null}, unaffected)
     * @param v2 the 3rd vertex of the triangle (not {@code null}, unaffected)
     * @return the minimum distance along ray, or {@code FLT_MAX} if the ray
     * misses the triangle
     */
    public static float rayTriangle(Vec3Arg rayOrigin, Vec3Arg rayDirection,
            Vec3Arg v0, Vec3Arg v1, Vec3Arg v2) {
        float originX = rayOrigin.getX();
        float originY = rayOrigin.getY();
        float originZ = rayOrigin.getZ();
        float directionX = rayDirection.getX();
        float directionY = rayDirection.getY();
        float directionZ = rayDirection.getZ();
        float v0x = v0.getX();
        float v0y = v0.getY();
        float v0z = v0.getZ();
        float v1x = v1.getX();
        float v1y = v1.getY();
        float v1z = v1.getZ();
        float v2x = v2.getX();
        float v2y = v2.getY();
        float v2z = v2.getZ();
        float result = rayTriangle(
                originX, originY, originZ, directionX, directionY, directionZ,
                v0x, v0y, v0z, v1x, v1y, v1z, v2x, v2y, v2z);

        return result;
    }

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
     * Return the (binary) sign of the specified single-precision value. (see
     * Jolt/Math/Math.h)
     *
     * @param input the input value
     * @return -1 if the input is negative, otherwise +1
     */
    public static float sign(float input) {
        float result = (input < 0) ? -1f : 1f;
        return result;
    }

    /**
     * Return the sine of the specified single-precision angle. (see
     * Jolt/Math/Trigonometry.h)
     *
     * @param angle the input angle (in radians)
     * @return the sine ratio
     */
    native public static float sin(float angle);

    /**
     * Return the square of the specified single-precision value.
     *
     * @param value the input value
     * @return the square
     */
    public static float square(float value) {
        float result = value * value;
        return result;
    }

    /**
     * Return the square of the specified integer value.
     *
     * @param value the input value
     * @return the square
     */
    public static int square(int value) {
        int result = value * value;
        return result;
    }

    /**
     * Set the next available character ID to 1. (see
     * Jolt/Physics/Character/CharacterID.h)
     */
    public static void sSetNextCharacterId() {
        sSetNextCharacterId(1);
    }

    /**
     * Set the next available character ID to the specified value. (native
     * function: {@code CharacterID::sSetNextCharacterID})
     *
     * @param nextValue the desired next ID
     */
    native public static void sSetNextCharacterId(int nextValue);

    /**
     * Test whether the native library supports the ObjectStream format. (native
     * macro: JPH_OBJECT_STREAM)
     *
     * @return {@code true} if supported, otherwise {@code false}
     */
    native public static boolean supportsObjectStream();

    /**
     * Return the tangent ratio of the specified single-precision angle. (see
     * Jolt/Math/Trigonometry.h)
     *
     * @param angle the input angle (in radians)
     * @return the tangent ratio
     */
    native public static float tan(float angle);

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
    // *************************************************************************
    // native private methods

    native private static void estimateCollisionResponse(
            long body1Va, long body2Va, long manifoldVa, long resultVa,
            float combinedFriction, float combinedRestitution,
            float minVelocity, int numIterations);

    native private static long hashBytes(
            double xx, double yy, double zz, long oldHash);

    native private static long hashBytes(
            float qx, float qy, float qz, float qw, long oldHash);

    native private static long hashCombineRVec3(
            long oldHash, double xx, double yy, double zz);

    native private static long hashCombineVec3(
            long oldHash, float x, float y, float z);

    native private static float rayAaBox(float originX, float originY,
            float originZ, long invDirVa, float minX, float minY, float minZ,
            float maxX, float maxY, float maxZ);

    native private static boolean rayAaBoxHits(
            float startX, float startY, float startZ, float offsetX,
            float offsetY, float offsetZ, float minX, float minY, float minZ,
            float maxX, float maxY, float maxZ);

    native private static float rayCapsule(float originX, float originY,
            float originZ, float directionX, float directionY, float directionZ,
            float capsuleHalfHeight, float capsuleRadius);

    native private static float rayFiniteCylinder(float originX, float originY,
            float originZ, float directionX, float directionY, float directionZ,
            float cylinderHalfheight, float cylinderRadius);

    native private static float rayInfiniteCylinder(
            float originX, float originY, float originZ, float directionX,
            float directionY, float directionZ, float cylinderRadius);

    native private static float raySphere(float originX, float originY,
            float originZ, float directionX, float directionY, float directionZ,
            float centerX, float centerY, float centerZ, float sphereRadius);

    native private static float rayTriangle(
            float originX, float originY, float originZ, float directionX,
            float directionY, float directionZ, float v0x, float v0y, float v0z,
            float v1x, float v1y, float v1z, float v2x, float v2y, float v2z);
}
