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

import com.github.stephengold.joltjni.enumerate.EResult;
import java.nio.FloatBuffer;
import java.util.List;

/**
 * Construct a 3-D convex hull for specified points.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ConvexHullBuilder extends NonCopyable {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a builder for the specified points.
     *
     * @param points a list of locations (not null, unaffected)
     */
    public ConvexHullBuilder(List<Vec3> points) {
        int numPoints = points.size();
        int numFloats = 3 * numPoints;
        FloatBuffer pointBuffer = Jolt.newDirectFloatBuffer(numFloats);
        for (Vec3 point : points) {
            point.put(pointBuffer);
        }
        long builderVa = create(pointBuffer);
        setVirtualAddress(builderVa, () -> free(builderVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Determine the largest error in the constructed hull. The builder is
     * unaffected.
     *
     * @param storeFaceWithMaxError storage for the face with the largest error
     * (not null, length&ge;1, modified)
     * @param storeMaxError storage for the magnitude of the largest error (not
     * null, length&ge;1, modified)
     * @param storePositionIndex storage for the index (not null, length&ge;1,
     * modified)
     * @param storeCoplanarDistance storage for the coplanar distance (not null,
     * length&ge;1, modified)
     */
    public void determineMaxError(ChbFace[] storeFaceWithMaxError,
            float[] storeMaxError, int[] storePositionIndex,
            float[] storeCoplanarDistance) {
        long builderVa = va();
        long[] storeFaceVa = new long[1];
        determineMaxError(builderVa, storeFaceVa, storeMaxError,
                storePositionIndex, storeCoplanarDistance);
        if (storeFaceVa[0] == 0L) {
            storeFaceWithMaxError[0] = null;
        } else {
            ChbFace face = new ChbFace(this, storeFaceVa[0]);
            storeFaceWithMaxError[0] = face;
        }
    }

    /**
     * Calculate the center of mass and volume of the resulting hull. The
     * builder is unaffected.
     *
     * @param storeCom storage for the center of mass (not null, modified)
     * @param storeVolume storage for the volume (not null, length&ge;1,
     * modified)
     */
    public void getCenterOfMassAndVolume(Vec3 storeCom, float[] storeVolume) {
        long builderVa = va();
        float[] storeFloats = new float[4];
        getCenterOfMassAndVolume(builderVa, storeFloats);
        storeCom.set(storeFloats[0], storeFloats[1], storeFloats[2]);
        storeVolume[0] = storeFloats[3];
    }

    /**
     * Enumerate the faces of the hull.
     *
     * @return a new array of JVM objects with pre-existing native objects
     * assigned
     */
    public ChbFace[] getFaces() {
        long builderVa = va();
        int numFaces = countFaces(builderVa);
        long[] storeVas = new long[numFaces];
        getFaces(builderVa, storeVas);
        ChbFace[] result = new ChbFace[numFaces];
        for (int i = 0; i < numFaces; ++i) {
            long faceVa = storeVas[i];
            result[i] = new ChbFace(this, faceVa);
        }

        return result;
    }

    /**
     * Build a convex hull.
     *
     * @param maxVertices the maximum number of vertices in the hull, or
     * {@code MAX_VALUE} for no limit
     * @param tolerance how far a point can be outside the hull
     * @param storeMessage error message if the build fails
     * @return the outcome of the build
     */
    public EResult initialize(
            int maxVertices, float tolerance, String[] storeMessage) {
        long builderVa = va();
        int ordinal
                = initialize(builderVa, maxVertices, tolerance, storeMessage);
        EResult result = EResult.values()[ordinal];

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static int countFaces(long builderVa);

    native private static long create(FloatBuffer pointBuffer);

    native private static void determineMaxError(
            long builderVa, long[] storeFaceVa, float[] storeMaxError,
            int[] storePositionIndex, float[] storeCoplanarDistance);

    native private static void free(long builderVa);

    native private static void getCenterOfMassAndVolume(
            long builderVa, float[] storeFloats);

    native private static void getFaces(long builderVa, long[] storeVas);

    native private static int initialize(long builderVa, int maxVertices,
            float tolerance, String[] storeMessage);
}
