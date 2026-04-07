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
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * Seldom-used utility methods. Most of these relate to character IDs, collision
 * estimation, hashing, and ray intersections.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class MiscUtil {
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private MiscUtil() {
    }
    // *************************************************************************
    // new methods exposed

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
        estimateCollisionResponse(
                body1Va, body2Va, manifoldVa, resultVa, combinedFriction,
                combinedRestitution, minVelocity, numIterations);
    }

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
            float cylinderHalfHeight, float cylinderRadius);

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
