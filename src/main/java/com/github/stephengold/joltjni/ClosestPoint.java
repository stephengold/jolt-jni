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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Utilities to locate the closest point on a line segment, triangle or
 * tetrahedron. (native namespace: ClosestPoint)
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class ClosestPoint {
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private ClosestPoint() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Calculate the barycentric coordinates of the point on the specified
     * (infinite) line that's closest to the origin.
     *
     * @param a the location of a reference point on the line (not null,
     * unaffected)
     * @param b the location of another reference point on the line (not null,
     * unaffected)
     * @param storeUv storage for the coordinates (not null, length&ge;2,
     * modified)
     * @return {@code true} if successful, {@code false} if the points do not
     * define a line
     */
    public static boolean getBaryCentricCoordinates(
            Vec3Arg a, Vec3Arg b, float[] storeUv) {
        float ax = a.getX();
        float ay = a.getY();
        float az = a.getZ();
        float bx = b.getX();
        float by = b.getY();
        float bz = b.getZ();
        boolean result
                = getBaryCentricCoordinates2(ax, ay, az, bx, by, bz, storeUv);

        return result;
    }

    /**
     * Calculate the barycentric coordinates of the point on the specified plane
     * that's closest to the origin.
     *
     * @param a the location of a reference point on the plane (not null,
     * unaffected)
     * @param b the location of another reference point on the plane (not null,
     * unaffected)
     * @param c the location of a 3rd reference point on the plane (not null,
     * unaffected)
     * @param storeUvw storage for the coordinates (not null, length&ge;3,
     * modified)
     * @return {@code true} if successful, {@code false} if the points do not
     * define a plane
     */
    public static boolean getBaryCentricCoordinates(
            Vec3Arg a, Vec3Arg b, Vec3Arg c, float[] storeUvw) {
        float ax = a.getX();
        float ay = a.getY();
        float az = a.getZ();
        float bx = b.getX();
        float by = b.getY();
        float bz = b.getZ();
        float cx = c.getX();
        float cy = c.getY();
        float cz = c.getZ();
        boolean result = getBaryCentricCoordinates3(
                ax, ay, az, bx, by, bz, cx, cy, cz, storeUvw);

        return result;
    }

    /**
     * Locate the point on the specified line segment that's closest to the
     * origin.
     *
     * @param a the location of the first end-point of the segment (not null,
     * unaffected)
     * @param b the location of the 2nd end-point of the segment (not null,
     * unaffected)
     * @param storeSet storage for the closest feature(s) (not null,
     * length&ge;1, modified)
     * @return a new location vector
     */
    public static Vec3 getClosestPointOnLine(
            Vec3Arg a, Vec3Arg b, int[] storeSet) {
        float ax = a.getX();
        float ay = a.getY();
        float az = a.getZ();
        float bx = b.getX();
        float by = b.getY();
        float bz = b.getZ();
        float[] storePoint = new float[3];
        getClosestPointOnLine(ax, ay, az, bx, by, bz, storeSet, storePoint);
        Vec3 result = new Vec3(storePoint);

        return result;
    }

    /**
     * Locate the point on the specified tetrahedron that's closest to the
     * origin.
     *
     * @param a the location of the first vertex of the tetrahedron (not null,
     * unaffected)
     * @param b the location of the 2nd vertex of the tetrahedron (not null,
     * unaffected)
     * @param c the location of the 3rd vertex of the tetrahedron (not null,
     * unaffected)
     * @param d the location of the 4th vertex of the tetrahedron (not null,
     * unaffected)
     * @param storeSet storage for the closest feature(s) (not null,
     * length&ge;1, modified)
     * @return a new location vector
     */
    public static Vec3 getClosestPointOnTetrahedron(Vec3Arg a, Vec3Arg b,
            Vec3Arg c, Vec3Arg d, int[] storeSet) {
        float ax = a.getX();
        float ay = a.getY();
        float az = a.getZ();
        float bx = b.getX();
        float by = b.getY();
        float bz = b.getZ();
        float cx = c.getX();
        float cy = c.getY();
        float cz = c.getZ();
        float dx = d.getX();
        float dy = d.getY();
        float dz = d.getZ();
        float[] storePoint = new float[3];
        getClosestPointOnTetrahedron(ax, ay, az, bx, by, bz, cx, cy, cz,
                dx, dy, dz, storeSet, storePoint);
        Vec3 result = new Vec3(storePoint);

        return result;
    }

    /**
     * Locate the point on the specified triangle that's closest to the origin.
     *
     * @param a the location of the first vertex of the triangle (not null,
     * unaffected)
     * @param b the location of the 2nd vertex of the triangle (not null,
     * unaffected)
     * @param c the location of the 3rd vertex of the triangle (not null,
     * unaffected)
     * @param storeSet storage for the closest feature(s) (not null,
     * length&ge;1, modified)
     * @return a new location vector
     */
    public static Vec3 getClosestPointOnTriangle(
            Vec3Arg a, Vec3Arg b, Vec3Arg c, int[] storeSet) {
        float ax = a.getX();
        float ay = a.getY();
        float az = a.getZ();
        float bx = b.getX();
        float by = b.getY();
        float bz = b.getZ();
        float cx = c.getX();
        float cy = c.getY();
        float cz = c.getZ();
        float[] storePoint = new float[3];
        getClosestPointOnTriangle(
                ax, ay, az, bx, by, bz, cx, cy, cz, storeSet, storePoint);
        Vec3 result = new Vec3(storePoint);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static boolean getBaryCentricCoordinates2(float ax, float ay,
            float az, float bx, float by, float bz, float[] storeUvw);

    native private static boolean getBaryCentricCoordinates3(
            float ax, float ay, float az, float bx, float by, float bz,
            float cx, float cy, float cz, float[] storeUvw);

    native private static void getClosestPointOnLine(
            float ax, float ay, float az, float bx, float by, float bz,
            int[] storeSet, float[] storePoint);

    native private static void getClosestPointOnTetrahedron(
            float ax, float ay, float az, float bx, float by, float bz,
            float cx, float cy, float cz, float dx, float dy, float dz,
            int[] storeSet, float[] storePoint);

    native private static void getClosestPointOnTriangle(
            float ax, float ay, float az, float bx, float by, float bz,
            float cx, float cy, float cz, int[] storeSet, float[] storePoint);
}
