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

import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Record the state of a physics system. Can also compare against an expected
 * state.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class StateRecorder extends NonCopyable {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a recorder with no native object assigned.
     */
    StateRecorder() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether the recorder is validating.
     *
     * @return true if validating, otherwise false
     */
    public boolean isValidating() {
        long recorderVa = va();
        boolean result = isValidating(recorderVa);

        return result;
    }

    /**
     * Read a boolean.
     *
     * @param b the value for validation
     * @return the value that was read
     */
    public boolean readBoolean(boolean b) {
        long recorderVa = va();
        boolean result = readBoolean(recorderVa, b);

        return result;
    }

    /**
     * Read a single-precision floating-point value.
     *
     * @param f the value for validation
     * @return the value that was read
     */
    public float readFloat(float f) {
        long recorderVa = va();
        float result = readFloat(recorderVa, f);

        return result;
    }

    /**
     * Read a vector.
     *
     * @param inOut the value for validation (not null, modified)
     */
    public void readVec3(Vec3 inOut) {
        long recorderVa = va();
        float[] storeFloats = inOut.toArray();
        readVec3(recorderVa, storeFloats);
        inOut.set(storeFloats[0], storeFloats[1], storeFloats[2]);
    }

    /**
     * Alter whether the recorder is validating.
     *
     * @param setting true to begin validating, false to stop validating
     */
    public void setValidating(boolean setting) {
        long recorderVa = va();
        setValidating(recorderVa, setting);
    }

    /**
     * Write the specified boolean.
     *
     * @param b the value to write
     */
    public void write(boolean b) {
        long recorderVa = va();
        writeBoolean(recorderVa, b);
    }

    /**
     * Write the specified single-precision floating-point value.
     *
     * @param f the value to write
     */
    public void write(float f) {
        long recorderVa = va();
        writeFloat(recorderVa, f);
    }

    /**
     * Write the value of the specified vector.
     *
     * @param v the vector to write (not null, unaffected)
     */
    public void write(Vec3Arg v) {
        long recorderVa = va();
        float x = v.getX();
        float y = v.getY();
        float z = v.getZ();
        writeVec3(recorderVa, x, y, z);
    }
    // *************************************************************************
    // native private methods

    native private static boolean isValidating(long recorderVa);

    native private static boolean readBoolean(long recorderVa, boolean b);

    native private static float readFloat(long recorderVa, float f);

    native private static void readVec3(long recorderVa, float[] storeFloats);

    native private static void setValidating(long recorderVa, boolean setting);

    native private static void writeBoolean(long recorderVa, boolean b);

    native private static void writeFloat(long recorderVa, float f);

    native private static void writeVec3(
            long recorderVa, float x, float y, float z);
}
