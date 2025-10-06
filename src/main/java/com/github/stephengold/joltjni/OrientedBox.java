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

import com.github.stephengold.joltjni.readonly.ConstOrientedBox;
import com.github.stephengold.joltjni.readonly.Mat44Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * A 3-D box that has been rotated and translated.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class OrientedBox
        extends JoltPhysicsObject
        implements ConstOrientedBox {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default box.
     */
    public OrientedBox() {
        long boxVa = createDefault();
        setVirtualAddress(boxVa, () -> free(boxVa));
    }

    /**
     * Instantiate a copy of the specified box.
     *
     * @param original the box to copy (not {@code null}, unaffected)
     */
    public OrientedBox(ConstOrientedBox original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa, () -> free(copyVa));
    }

    /**
     * Instantiate a box with the specified transform and half extents.
     *
     * @param orientation the desired rotation and translation (not null,
     * unaffected)
     * @param halfExtents the desired half-extent on each local axis (not null,
     * unaffected)
     */
    public OrientedBox(Mat44Arg orientation, Vec3Arg halfExtents) {
        long orientationVa = orientation.targetVa();
        float hx = halfExtents.getX();
        float hy = halfExtents.getY();
        float hz = halfExtents.getZ();
        long boxVa = createBox(orientationVa, hx, hy, hz);
        setVirtualAddress(boxVa, () -> free(boxVa));
    }
    // *************************************************************************
    // ConstOrientedBox methods

    /**
     * Copy the (half) extent of the box on each local axis. The box is
     * unaffected. (native attribute: mHalfExtents)
     *
     * @return a new vector
     */
    @Override
    public Vec3 getHalfExtents() {
        long boxVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getHalfExtents(boxVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the transform that translates and rotates the local-space axes to
     * world space. The box is unaffected. (native attribute: mOrientation)
     *
     * @return a new matrix
     */
    @Override
    public Mat44 getOrientation() {
        long boxVa = va();
        long resultVa = getOrientation(boxVa);
        Mat44 result = new Mat44(resultVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createBox(
            long transformVa, float hx, float hy, float hz);

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static void free(long boxVa);

    native private static void getHalfExtents(
            long boxVa, FloatBuffer storeFloats);

    native private static long getOrientation(long boxVa);
}
