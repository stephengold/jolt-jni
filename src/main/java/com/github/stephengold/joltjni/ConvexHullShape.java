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

/**
 * A {@code Shape} to represent a convex hull defined by a collection of
 * vertices.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ConvexHullShape extends ConvexShape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a shape with the specified native object assigned but not
     * owned.
     *
     * @param shapeVa the virtual address of the native object to assign (not
     * zero)
     */
    ConvexHullShape(long shapeVa) {
        super(shapeVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the convex radius. The shape is unaffected.
     *
     * @return the radius (&ge;0)
     */
    public float getConvexRadius() {
        long shapeVa = va();
        float result = getConvexRadius(shapeVa);

        return result;
    }

    /**
     * Enumerate the vertices in the specified face. The shape is unaffected.
     *
     * @param faceIndex the index of the face to query (&ge;0, &lt;numFaces)
     * @param storeIndices storage for the result (not null, modified)
     * @return the number of vertices in the face
     */
    public int getFaceVertices(int faceIndex, int[] storeIndices) {
        int result = getFaceVertices(
                faceIndex, storeIndices.length, storeIndices);
        return result;
    }

    /**
     * Enumerate the vertices in the specified face. The shape is unaffected.
     *
     * @param faceIndex the index of the face to query (&ge;0, &lt;numFaces)
     * @param maxVertices the maximum number of vertices to return (&ge;0,
     * default=storeIndices.length)
     * @param storeIndices storage for the result (not null,
     * length&ge;maxVertices, modified)
     * @return the number of vertices in the face (&ge;0)
     */
    public int getFaceVertices(
            int faceIndex, int maxVertices, int[] storeIndices) {
        long shapeVa = va();
        int result = getFaceVertices(
                shapeVa, faceIndex, maxVertices, storeIndices);

        return result;
    }

    /**
     * Count the faces in the convex hull. The shape is unaffected.
     *
     * @return the count (&ge;0)
     */
    public int getNumFaces() {
        long shapeVa = va();
        int result = getNumFaces(shapeVa);

        return result;
    }

    /**
     * Count the vertices of the convex hull. The shape is unaffected.
     *
     * @return the count (&ge;0)
     */
    public int getNumPoints() {
        long shapeVa = va();
        int result = getNumPoints(shapeVa);

        return result;
    }

    /**
     * Count the vertices in the specified face. The shape is unaffected.
     *
     * @param faceIndex the index of the face to query (&ge;0, &lt;numFaces)
     * @return the count (&ge;0)
     */
    public int getNumVerticesInFace(int faceIndex) {
        long shapeVa = va();
        int result = getNumVerticesInFace(shapeVa, faceIndex);

        return result;
    }

    /**
     * Locate the specified vertex of the convex hull relative to its center of
     * mass. The shape is unaffected.
     *
     * @param pointIndex the index of the point to locate (&ge;0, &lt;numPoints)
     * @return a new location vector
     */
    public Vec3 getPoint(int pointIndex) {
        long shapeVa = va();
        float x = getPointX(shapeVa, pointIndex);
        float y = getPointY(shapeVa, pointIndex);
        float z = getPointZ(shapeVa, pointIndex);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static float getConvexRadius(long shapeVa);

    native private static int getFaceVertices(
            long shapeVa, int faceIndex, int maxVertices, int[] storeIndices);

    native private static int getNumFaces(long shapeVa);

    native private static int getNumPoints(long shapeVa);

    native private static int getNumVerticesInFace(long shapeVa, int faceIndex);

    native private static float getPointX(long shapeVa, int pointIndex);

    native private static float getPointY(long shapeVa, int pointIndex);

    native private static float getPointZ(long shapeVa, int pointIndex);
}
