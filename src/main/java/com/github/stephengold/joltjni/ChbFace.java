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
 * A triangular face in a {@code ConvexHullBuilder}. (native type:
 * {@code ConvexHullBuilder::Face})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ChbFace extends NonCopyable {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a face with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param faceVa the virtual address of the native object to assign (not
     * zero)
     */
    ChbFace(JoltPhysicsObject container, long faceVa) {
        super(container, faceVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the centroid of the face. The face is unaffected. (native
     * attribute: mCentroid)
     *
     * @return a new location vector
     */
    public Vec3 getCentroid() {
        long faceVa = va();
        float[] storeFloats = new float[3];
        getCentroid(faceVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Access the first edge of the face. The face is unaffected. (native
     * attribute: mFirstEdge)
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if none
     */
    public ChbEdge getFirstEdge() {
        long faceVa = va();
        long edgeVa = getFirstEdge(faceVa);
        ChbEdge result;
        if (edgeVa == 0L) {
            result = null;
        } else {
            JoltPhysicsObject container = getContainingObject();
            result = new ChbEdge(container, edgeVa);
        }

        return result;
    }

    /**
     * Return the normal of the face. The face is unaffected. (native attribute:
     * mNormal)
     *
     * @return a new vector whose length is 2 times the area of the face
     */
    public Vec3 getNormal() {
        long faceVa = va();
        float[] storeFloats = new float[3];
        getNormal(faceVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Test whether the face is facing the specified point. The face is
     * unaffected.
     *
     * @param point the location to test (not null, unaffected)
     * @return {@code true} if facing the test point, otherwise {@code false}
     */
    public boolean isFacing(Vec3Arg point) {
        long faceVa = va();
        float x = point.getX();
        float y = point.getY();
        float z = point.getZ();
        boolean result = isFacing(faceVa, x, y, z);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void getCentroid(long faceVa, float[] storeFloats);

    native private static long getFirstEdge(long faceVa);

    native private static void getNormal(long faceVa, float[] storeFloats);

    native private static boolean isFacing(
            long faceVa, float x, float y, float z);
}
