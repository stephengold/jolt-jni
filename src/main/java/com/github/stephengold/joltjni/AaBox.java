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

import com.github.stephengold.joltjni.readonly.ConstAaBox;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * An axis-aligned box.
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
        long boxVa = createAaBox();
        setVirtualAddress(boxVa, () -> free(boxVa));
    }

    /**
     * Instantiate a box with the specified native object assigned.
     *
     * @param boxVa the virtual address of the native object to assign (not
     * zero)
     * @param owner true &rarr; make the current object the owner, false &rarr;
     * the current object isn't the owner
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
     * Instantiate the biggest finite box.
     *
     * @return a new JVM object with a new native object assigned
     */
    public static AaBox sBiggest() {
        long boxVa = sBiggest(true);
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
    // *************************************************************************
    // ConstAaBox methods

    /**
     * Test whether the box contains the specified point. The box is unaffected.
     *
     * @param point the point to test (not null, unaffected)
     *
     * @return true if contained, otherwise false
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
     * Return the (half) extent of the box. The box is unaffected.
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
     * Return the maximum contained coordinate on each axis. The box is
     * unaffected.
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
     * Return the minimum contained coordinate on each axis. The box is
     * unaffected.
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
     * Return the size (full extent) on each axis. The box is unaffected.
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
     * Test whether the box is valid. The box is unaffected.
     *
     * @return true if valid, otherwise false
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

    native private static long createAaBox();

    native private static long createAaBox(float minX, float minY, float minZ,
            float maxX, float maxY, float maxZ);

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

    native private static long sBiggest(boolean unused);

    native private static void setEmpty(long boxVa);
}
