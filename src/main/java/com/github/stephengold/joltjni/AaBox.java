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

import com.github.stephengold.joltjni.readonly.ConstAaBox;
import com.github.stephengold.joltjni.readonly.ConstIndexedTriangle;
import com.github.stephengold.joltjni.readonly.ConstPlane;
import com.github.stephengold.joltjni.readonly.ConstTriangle;
import com.github.stephengold.joltjni.readonly.ConstVertexList;
import com.github.stephengold.joltjni.readonly.Mat44Arg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * An axis-aligned box. (native type: AABox)
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class AaBox extends JoltPhysicsObject implements ConstAaBox {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an invalid box.
     */
    public AaBox() {
        long boxVa = createDefault();
        setVirtualAddress(boxVa, () -> free(boxVa));
    }

    /**
     * Instantiate a copy of the specified box.
     *
     * @param original the box to copy (not {@code null}, unaffected)
     */
    public AaBox(ConstAaBox original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa, () -> free(copyVa));
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param boxVa the virtual address of the native object to assign (not
     * zero)
     */
    AaBox(JoltPhysicsObject container, long boxVa) {
        super(container, boxVa);
    }

    /**
     * Instantiate a box with the specified native object assigned.
     *
     * @param boxVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    AaBox(long boxVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(boxVa) : null;
        setVirtualAddress(boxVa, freeingAction);
    }

    /**
     * Instantiate a box with the specified minimum and maximum coordinates.
     *
     * @param minimum the desired minimum coordinates (not null, unaffected)
     * @param maximum the desired maximum coordinates (not null, unaffected)
     */
    public AaBox(RVec3Arg minimum, RVec3Arg maximum) {
        float minX = minimum.x();
        float minY = minimum.y();
        float minZ = minimum.z();
        float maxX = maximum.x();
        float maxY = maximum.y();
        float maxZ = maximum.z();
        long boxVa = createAaBox(minX, minY, minZ, maxX, maxY, maxZ);
        setVirtualAddress(boxVa, () -> free(boxVa));
    }

    /**
     * Instantiate a cubic box with the specified center coordinates and half
     * extent.
     *
     * @param center the desired center coordinates (not null, unaffected)
     * @param halfExtent the desired half extent
     */
    public AaBox(Vec3Arg center, float halfExtent) {
        float cx = center.getX();
        float cy = center.getY();
        float cz = center.getZ();
        long boxVa = createCubic(cx, cy, cz, halfExtent);
        setVirtualAddress(boxVa, () -> free(boxVa));
    }

    /**
     * Instantiate a box with the specified minimum and maximum coordinates.
     *
     * @param minimum the desired minimum coordinates (not null, unaffected)
     * @param maximum the desired maximum coordinates (not null, unaffected)
     */
    public AaBox(Vec3Arg minimum, Vec3Arg maximum) {
        float minX = minimum.getX();
        float minY = minimum.getY();
        float minZ = minimum.getZ();
        float maxX = maximum.getX();
        float maxY = maximum.getY();
        float maxZ = maximum.getZ();
        long boxVa = createAaBox(minX, minY, minZ, maxX, maxY, maxZ);
        setVirtualAddress(boxVa, () -> free(boxVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Enlarge the current box to include the argument box.
     *
     * @param includeBox the box to include (not null, unaffected)
     */
    public void encapsulate(ConstAaBox includeBox) {
        long boxVa = va();
        long includeBoxVa = includeBox.targetVa();

        encapsulateBox(boxVa, includeBoxVa);
    }

    /**
     * Enlarge the box to include the specified triangle.
     *
     * @param triangle the triangle to include (not null, unaffected)
     */
    public void encapsulate(ConstTriangle triangle) {
        long boxVa = va();
        long triangleVa = triangle.targetVa();

        encapsulateTriangle(boxVa, triangleVa);
    }

    /**
     * Enlarge the box to include the specified location.
     *
     * @param location the location to include (not null, unaffected)
     */
    public void encapsulate(Vec3Arg location) {
        long boxVa = va();
        float locX = location.getX();
        float locY = location.getY();
        float locZ = location.getZ();
        encapsulate(boxVa, locX, locY, locZ);
    }

    /**
     * Enlarge the box to include the specified triangle.
     *
     * @param vertices array of vertex locations (not null, unaffected)
     * @param triangle the triangle to include (not null, unaffected)
     */
    public void encapsulate(ConstVertexList vertices,
            ConstIndexedTriangle triangle) {
        long boxVa = va();
        long triangleVa = triangle.targetVa();

        int numVertices = vertices.size();
        FloatBuffer directBuffer = vertices.toDirectBuffer();

        encapsulatedTriangleFromVertices(
                boxVa, numVertices, directBuffer, triangleVa);
    }

    /**
     * Enlarge the box so that each edge has at least the specified length.
     *
     * @param minEdgeLength the desired minimum edge length
     */
    public void ensureMinimalEdgeLength(float minEdgeLength) {
        long boxVa = va();
        ensureMinimalEdgeLength(boxVa, minEdgeLength);
    }

    /**
     * Enlarge the box on all sides by the specified amounts.
     *
     * @param deltas the amount to increase the half extent on each axis (not
     * null, unaffected)
     */
    public void expandBy(Vec3Arg deltas) {
        long boxVa = va();
        float dx = deltas.getX();
        float dy = deltas.getY();
        float dz = deltas.getZ();
        expandBy(boxVa, dx, dy, dz);
    }

    /**
     * Instantiate the biggest finite box.
     *
     * @return a new JVM object with a new native object assigned
     */
    public static AaBox sBiggest() {
        long boxVa = createBiggest();
        AaBox result = new AaBox(boxVa, true);

        return result;
    }

    /**
     * Alter the box to be empty.
     */
    public void setEmpty() {
        long boxVa = va();
        setEmpty(boxVa);
    }

    /**
     * Alter the maximum coordinates. (native member: mMax)
     *
     * @param max the desired maximum coordinate for each axis (not null,
     * unaffected)
     */
    public void setMax(Vec3Arg max) {
        long boxVa = va();
        float x = max.getX();
        float y = max.getY();
        float z = max.getZ();
        setMax(boxVa, x, y, z);
    }

    /**
     * Alter the minimum coordinates. (native member: mMin)
     *
     * @param min the desired minimum coordinate for each axis (not null,
     * unaffected)
     */
    public void setMin(Vec3Arg min) {
        long boxVa = va();
        float x = min.getX();
        float y = min.getY();
        float z = min.getZ();
        setMin(boxVa, x, y, z);
    }

    /**
     * Move by the specified offset.
     *
     * @param offset the amount to move (not null, unaffected)
     */
    public void translate(Vec3Arg offset) {
        long boxVa = va();
        float x = offset.getX();
        float y = offset.getY();
        float z = offset.getZ();
        translate(boxVa, x, y, z);
    }
    // *************************************************************************
    // ConstAaBox methods

    /**
     * Test whether the current box contains the argument box. The current box
     * is unaffected.
     *
     * @param other the other box to check against (not null, unaffected)
     *
     * @return {@code true} if contained, otherwise {@code false}
     */
    @Override
    public boolean contains(ConstAaBox other) {
        long boxVa = va();
        long otherBoxVa = other.targetVa();
        boolean result = containsAaBox(boxVa, otherBoxVa);

        return result;
    }

    /**
     * Test whether the box contains the specified point. The box is unaffected.
     *
     * @param point the point to test (not null, unaffected)
     *
     * @return {@code true} if contained, otherwise {@code false}
     */
    @Override
    public boolean contains(Vec3Arg point) {
        long boxVa = va();
        float x = point.getX();
        float y = point.getY();
        float z = point.getZ();
        boolean result = contains(boxVa, x, y, z);

        return result;
    }

    /**
     * Locate the center of the box. The box is unaffected.
     *
     * @return a new location vector
     */
    @Override
    public Vec3 getCenter() {
        long boxVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getCenter(boxVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Locate the closest point on or in the box for the specified location. The
     * box is unaffected.
     *
     * @param location the starting location (not null, unaffected)
     * @return a new vector
     */
    @Override
    public Vec3 getClosestPoint(Vec3Arg location) {
        long boxVa = va();
        FloatBuffer floatBuffer = Temporaries.floatBuffer1.get();
        location.copyTo(floatBuffer);
        getClosestPoint(boxVa, floatBuffer);
        Vec3 result = new Vec3(floatBuffer);

        return result;
    }

    /**
     * Copy the (half) extent of the box. The box is unaffected.
     *
     * @return a new vector
     */
    @Override
    public Vec3 getExtent() {
        long boxVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getExtent(boxVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the maximum contained coordinate on each axis. The box is
     * unaffected. (native member: mMax)
     *
     * @return a new location vector
     */
    @Override
    public Vec3 getMax() {
        long boxVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getMax(boxVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the minimum contained coordinate on each axis. The box is
     * unaffected. (native member: mMin)
     *
     * @return a new location vector
     */
    @Override
    public Vec3 getMin() {
        long boxVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getMin(boxVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the size (full extent) on each axis. The box is unaffected.
     *
     * @return a new vector
     */
    @Override
    public Vec3 getSize() {
        long boxVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getSize(boxVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Get the squared distance between the box and the specified point.
     *
     * @param point the point to measure from (not null, unaffected)
     *
     * @return the distance, or zero if {@code point} lies inside the box
     */
    @Override
    public float getSqDistanceTo(Vec3Arg point) {
        long boxVa = va();
        float px = point.getX();
        float py = point.getY();
        float pz = point.getZ();

        float result = getSqDistanceTo(boxVa, px, py, pz);
        return result;
    }

    /**
     * Calculate the support vector for this convex shape.
     *
     * @param direction the direction vector
     *
     * @return the support vector
     */
    @Override
    public Vec3 getSupport(Vec3Arg direction) {
        long boxVa = va();
        FloatBuffer tmpFloats = Temporaries.floatBuffer1.get();
        direction.copyTo(tmpFloats);
        getSupport(boxVa, tmpFloats);
        Vec3 vec3 = new Vec3(tmpFloats);

        return vec3;
    }

    /**
     * Get surface area of bounding box.
     *
     * @return the area
     */
    @Override
    public float getSurfaceArea() {
        long boxVa = va();
        float result = getSurfaceArea(boxVa);
        return result;
    }

    /**
     * Return the volume of the box. The box is unaffected.
     *
     * @return the volume
     */
    @Override
    public float getVolume() {
        long boxVa = va();
        float result = getVolume(boxVa);

        return result;
    }

    /**
     * Test whether the box is valid. It is unaffected.
     *
     * @return {@code true} if valid, otherwise {@code false}
     */
    @Override
    public boolean isValid() {
        long boxVa = va();
        boolean result = isValid(boxVa);

        return result;
    }

    /**
     * Check if this box overlaps with another box.
     *
     * @param other the other box to check
     *
     * @return {@code true} if they overlap, otherwise {@code false}
     */
    @Override
    public boolean overlaps(ConstAaBox other) {
        long boxVa = va();
        long otherVa = other.targetVa();

        boolean result = overlapsAaBox(boxVa, otherVa);
        return result;
    }

    /**
     * Check if this box overlaps with a plane.
     *
     * @param plane the {@code Plane} object to be checked
     *
     * @return {@code true} if they overlap, otherwise {@code false}
     */
    @Override
    public boolean overlaps(ConstPlane plane) {
        long boxVa = va();
        float pc = plane.getConstant();
        float pnx = plane.getNormalX();
        float pny = plane.getNormalY();
        float pnz = plane.getNormalZ();

        boolean result = overlaps(boxVa, pc, pnx, pny, pnz);
        return result;
    }

    /**
     * Return a scaled copy of the box. The current box is unaffected.
     *
     * @param factors the scale factors to apply (not null, unaffected)
     * @return a new object
     */
    @Override
    public AaBox scaled(Vec3Arg factors) {
        long boxVa = va();
        float sx = factors.getX();
        float sy = factors.getY();
        float sz = factors.getZ();
        long resultVa = scaled(boxVa, sx, sy, sz);
        AaBox result = new AaBox(resultVa, true);

        return result;
    }

    /**
     * Return a transformed copy of the box. The current box is unaffected.
     *
     * @param matrix the transformation to apply (not null, unaffected)
     * @return a new object
     */
    @Override
    public AaBox transformed(Mat44Arg matrix) {
        long boxVa = va();
        long matrixVa = matrix.targetVa();
        long resultVa = transformed(boxVa, matrixVa);
        AaBox result = new AaBox(resultVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static boolean contains(
            long boxVa, float x, float y, float z);

    native private static boolean containsAaBox(long boxVa, long otherBoxVa);

    native private static long createAaBox(float minX, float minY, float minZ,
            float maxX, float maxY, float maxZ);

    native private static long createBiggest();

    native private static long createCubic(
            float centerX, float centerY, float centerZ, float halfExtent);

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static void encapsulate(
            long boxVa, float locX, float locY, float locZ);

    native private static void encapsulateBox(long boxVa, long includeBoxVa);

    native private static void encapsulateTriangle(long boxVa, long triangleVa);

    native private static void encapsulatedTriangleFromVertices(
            long boxVa, int numVertices, FloatBuffer vertices, long triangleVa);

    native private static void ensureMinimalEdgeLength(
            long boxVa, float minEdgeLength);

    native private static void expandBy(
            long boxVa, float dx, float dy, float dz);

    native private static void free(long boxVa);

    native private static void getCenter(long boxVa, FloatBuffer storeFloats);

    native private static void getClosestPoint(long boxVa, FloatBuffer buffer);

    native private static void getExtent(long boxVa, FloatBuffer storeFloats);

    native private static void getMax(long boxVa, FloatBuffer storeFloats);

    native private static void getMin(long boxVa, FloatBuffer storeFloats);

    native private static void getSize(long boxVa, FloatBuffer storeFloats);

    native private static float getSqDistanceTo(
            long boxVa, float px, float py, float pz);

    native private static float getSurfaceArea(long boxVa);

    native private static void getSupport(long boxVa, FloatBuffer tmpFloats);

    native private static float getVolume(long boxVa);

    native private static boolean overlaps(
            long boxVa, float pc, float pnx, float pny, float pnz);

    native private static boolean overlapsAaBox(long boxVa, long otherVa);

    native private static boolean isValid(long boxVa);

    native private static long scaled(long boxVa, float sx, float sy, float sz);

    native private static void setEmpty(long boxVa);

    native private static void setMax(long boxVa, float x, float y, float z);

    native private static void setMin(long boxVa, float x, float y, float z);

    native private static long transformed(long boxVa, long matrixVa);

    native private static void translate(long boxVa, float x, float y, float z);
}
