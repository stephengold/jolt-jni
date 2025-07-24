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

import com.github.stephengold.joltjni.readonly.ConstAaBox;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * An axis-aligned box translating along a line segment. (native type:
 * AABoxCast)
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class AaBoxCast extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an invalid box cast.
     */
    public AaBoxCast() {
        long boxCastVa = createDefault();
        setVirtualAddress(boxCastVa, () -> free(boxCastVa));
    }

    /**
     * Instantiate a copy of the specified box cast.
     *
     * @param original the box to copy (not {@code null}, unaffected)
     */
    public AaBoxCast(AaBoxCast original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa, () -> free(copyVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the box at its starting location. (native member: mBox)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public AaBox getBox() {
        long boxCastVa = va();
        long boxVa = getBox(boxCastVa);
        AaBox result = new AaBox(this, boxVa);

        return result;
    }

    /**
     * Copy the length and direction of the cast. The box cast is unaffected.
     * (native member: mDirection)
     *
     * @return a new vector
     */
    public Vec3 getDirection() {
        long boxCastVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getDirection(boxCastVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Alter the box and its starting location. (native member: mBox)
     *
     * @param box the desired extents and starting location (not null,
     * unaffected)
     */
    public void setBox(ConstAaBox box) {
        long boxCastVa = va();
        long boxVa = box.targetVa();
        setBox(boxCastVa, boxVa);
    }

    /**
     * Alter the length and direction of the cast. (native member: mDirection)
     *
     * @param offset the desired direction and length of the cast.
     */
    public void setDirection(Vec3Arg offset) {
        long boxCastVa = va();
        float dx = offset.getX();
        float dy = offset.getY();
        float dz = offset.getZ();
        setDirection(boxCastVa, dx, dy, dz);
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static void free(long boxCastVa);

    native private static long getBox(long boxCastVa);

    native private static void getDirection(
            long boxCastVa, FloatBuffer storeFloats);

    native private static void setBox(long boxCastVa, long boxVa);

    native private static void setDirection(
            long boxCastVa, float dx, float dy, float dz);
}
