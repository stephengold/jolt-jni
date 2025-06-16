/*
Copyright (c) 2025 Stephen Gold

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

import java.nio.FloatBuffer;

/**
 * Implement the Expanding Polytope Algorithm. (native type:
 * EPAPenetrationDepth)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class EpaPenetrationDepth extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default implementation.
     *
     */
    public EpaPenetrationDepth() {
        long epaVa = createDefault();
        setVirtualAddress(epaVa, () -> free(epaVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Perform the GJK and EPA steps on a pair of points.
     *
     * @param aExcluding point A without convex radius (not null, unaffected)
     * @param aIncluding point A with convex radius (not null, unaffected)
     * @param convexRadiusA the convex radius of point A
     * @param bExcluding point B without convex radius (not null, unaffected)
     * @param bIncluding point B with convex radius (not null, unaffected)
     * @param convexRadiusB the convex radius of point B
     * @param collisionToleranceSq the square of the collision tolerance
     * @param penetrationTolerance the penetration tolerance
     * @param iov the direction vector (not null, modified)
     * @param storePointA storage for the point on point A (not null, modified)
     * @param storePointB storage for the point on point B (not null, modified)
     * @return {@code true} if the points intersect, otherwise {@code false}
     */
    public boolean getPenetrationDepth(PointConvexSupport aExcluding,
            Sphere aIncluding, float convexRadiusA,
            PointConvexSupport bExcluding, Sphere bIncluding,
            float convexRadiusB, float collisionToleranceSq,
            float penetrationTolerance, Vec3 iov, Vec3 storePointA,
            Vec3 storePointB) {
        final long epaVa = va();
        final long aExcludingVa = aExcluding.targetVa();
        final long aIncludingVa = aIncluding.targetVa();
        final long bExcludingVa = bExcluding.targetVa();
        final long bIncludingVa = bIncluding.targetVa();
        FloatBuffer fBuf = Jolt.newDirectFloatBuffer(11);
        fBuf.put(collisionToleranceSq);
        fBuf.put(penetrationTolerance);
        iov.put(fBuf);
        final boolean result = getPenetrationDepthPoints(
                epaVa, aExcludingVa, aIncludingVa, convexRadiusA,
                bExcludingVa, bIncludingVa, convexRadiusB, fBuf);

        iov.set(fBuf.get(2), fBuf.get(3), fBuf.get(4));
        storePointA.set(fBuf.get(5), fBuf.get(6), fBuf.get(7));
        storePointB.set(fBuf.get(8), fBuf.get(9), fBuf.get(10));

        return result;
    }

    /**
     * Perform the GJK and EPA steps on a transformed box and a transformed
     * sphere.
     *
     * @param aExcluding the box without convex radius (not null, unaffected)
     * @param aIncluding the box with convex radius (not null, unaffected)
     * @param convexRadiusA the convex radius of the box
     * @param bExcluding the sphere without convex radius (not null, unaffected)
     * @param bIncluding the sphere with convex radius (not null, unaffected)
     * @param convexRadiusB the convex radius of the sphere
     * @param collisionToleranceSq the square of the collision tolerance
     * @param penetrationTolerance the penetration tolerance
     * @param iov the direction vector (not null, modified)
     * @param storePointA storage for the point on the box (not null, modified)
     * @param storePointB storage for the point on the sphere (not null,
     * modified)
     * @return {@code true} if the box and sphere intersect, otherwise
     * {@code false}
     */
    public boolean getPenetrationDepth(TransformedAaBox aExcluding,
            TransformedAaBox aIncluding, float convexRadiusA,
            TransformedSphere bExcluding, TransformedSphere bIncluding,
            float convexRadiusB, float collisionToleranceSq,
            float penetrationTolerance, Vec3 iov, Vec3 storePointA,
            Vec3 storePointB) {
        final long epaVa = va();
        final long aExcludingVa = aExcluding.targetVa();
        final long aIncludingVa = aIncluding.targetVa();
        final long bExcludingVa = bExcluding.targetVa();
        final long bIncludingVa = bIncluding.targetVa();
        FloatBuffer fBuf = Jolt.newDirectFloatBuffer(11);
        fBuf.put(collisionToleranceSq);
        fBuf.put(penetrationTolerance);
        iov.put(fBuf);
        final boolean result = getPenetrationDepth(
                epaVa, aExcludingVa, aIncludingVa, convexRadiusA,
                bExcludingVa, bIncludingVa, convexRadiusB, fBuf);

        iov.set(fBuf.get(2), fBuf.get(3), fBuf.get(4));
        storePointA.set(fBuf.get(5), fBuf.get(6), fBuf.get(7));
        storePointB.set(fBuf.get(8), fBuf.get(9), fBuf.get(10));

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createDefault();

    native private static void free(long epaVa);

    native private static boolean getPenetrationDepth(long epaVa,
            long aExcludingVa, long aIncludingVa, float convexRadiusA,
            long bExcludingVa, long bIncludingVa, float convexRadiusB,
            FloatBuffer floatBuffer);

    native private static boolean getPenetrationDepthPoints(long epaVa,
            long aExcludingVa, long aIncludingVa, float convexRadiusA,
            long bExcludingVa, long bIncludingVa, float convexRadiusB,
            FloatBuffer floatBuffer);
}
