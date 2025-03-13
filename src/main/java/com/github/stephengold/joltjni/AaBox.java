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
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

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
        float minX = center.getX();
        float minY = center.getY();
        float minZ = center.getZ();
        long boxVa = createCubic(minX, minY, minZ, halfExtent);
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
     * Alter the maximum coordinates. (native attribute: mMax)
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
     * Alter the minimum coordinates. (native attribute: mMin)
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
        float x = getCenterX(boxVa);
        float y = getCenterY(boxVa);
        float z = getCenterZ(boxVa);
        Vec3 result = new Vec3(x, y, z);

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
        float x = getExtentX(boxVa);
        float y = getExtentY(boxVa);
        float z = getExtentZ(boxVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Copy the maximum contained coordinate on each axis. The box is
     * unaffected. (native attribute: mMax)
     *
     * @return a new vector
     */
    @Override
    public Vec3 getMax() {
        long boxVa = va();
        float x = getMaxX(boxVa);
        float y = getMaxY(boxVa);
        float z = getMaxZ(boxVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Copy the minimum contained coordinate on each axis. The box is
     * unaffected. (native attribute: mMin)
     *
     * @return a new vector
     */
    @Override
    public Vec3 getMin() {
        long boxVa = va();
        float x = getMinX(boxVa);
        float y = getMinY(boxVa);
        float z = getMinZ(boxVa);
        Vec3 result = new Vec3(x, y, z);

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
        float x = getSizeX(boxVa);
        float y = getSizeY(boxVa);
        float z = getSizeZ(boxVa);
        Vec3 result = new Vec3(x, y, z);

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
    // *************************************************************************
    // native private methods

    native private static boolean contains(
            long boxVa, float x, float y, float z);

    native private static long createAaBox(float minX, float minY, float minZ,
            float maxX, float maxY, float maxZ);

    native private static long createCubic(
            float centerX, float centerY, float centerZ, float halfExtent);

    native private static long createDefault();

    native private static void encapsulate(
            long boxVa, float locX, float locY, float locZ);

    native private static void expandBy(
            long boxVa, float dx, float dy, float dz);

    native private static void free(long boxVa);

    native private static float getCenterX(long boxVa);

    native private static float getCenterY(long boxVa);

    native private static float getCenterZ(long boxVa);

    native private static float getExtentX(long boxVa);

    native private static float getExtentY(long boxVa);

    native private static float getExtentZ(long boxVa);

    native private static float getMaxX(long boxVa);

    native private static float getMaxY(long boxVa);

    native private static float getMaxZ(long boxVa);

    native private static float getMinX(long boxVa);

    native private static float getMinY(long boxVa);

    native private static float getMinZ(long boxVa);

    native private static float getSizeX(long boxVa);

    native private static float getSizeY(long boxVa);

    native private static float getSizeZ(long boxVa);

    native private static float getVolume(long boxVa);

    native private static boolean isValid(long boxVa);

    native private static long createBiggest();

    native private static void setEmpty(long boxVa);

    native private static void setMax(long boxVa, float x, float y, float z);

    native private static void setMin(long boxVa, float x, float y, float z);

    native private static void translate(long boxVa, float x, float y, float z);
}
