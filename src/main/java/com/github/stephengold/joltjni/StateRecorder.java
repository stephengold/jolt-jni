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

import com.github.stephengold.joltjni.readonly.RMat44Arg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

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
     * @return {@code true} if validating, otherwise {@code false}
     */
    public boolean isValidating() {
        long recorderVa = va();
        boolean result = isValidating(recorderVa);

        return result;
    }

    /**
     * Read an ID vector.
     *
     * @param storeResult storage for the result (not null, modified)
     */
    public void readBodyIdVector(BodyIdVector storeResult) {
        long recorderVa = va();
        long vectorVa = storeResult.va();
        readBodyIdVector(recorderVa, vectorVa);
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
     * Read a 32-bit integer value.
     *
     * @param i the value for validation
     * @return the value that was read
     */
    public int readInt(int i) {
        long recorderVa = va();
        int result = readInt(recorderVa, i);

        return result;
    }

    /**
     * Read a matrix.
     *
     * @param inOut the value for validation (not null, modified)
     */
    public void readRMat44(RMat44 inOut) {
        long recorderVa = va();
        long matrixVa = inOut.va();
        readRMat44(recorderVa, matrixVa);
    }

    /**
     * Read a location vector.
     *
     * @param inOut the value for validation (not null, modified)
     */
    public void readRVec3(RVec3 inOut) {
        long recorderVa = va();
        double[] tmpDoubles = inOut.toArray();
        readRVec3(recorderVa, tmpDoubles);
        inOut.set(tmpDoubles);
    }

    /**
     * Read a string of text.
     *
     * @param javaString the value for validation (not null)
     * @return the string that was read
     */
    public String readString(String javaString) {
        long recorderVa = va();
        String result = readString(recorderVa, javaString);

        return result;
    }

    /**
     * Read a vector.
     *
     * @param inOut the value for validation (not null, modified)
     */
    public void readVec3(Vec3 inOut) {
        long recorderVa = va();
        FloatBuffer floatBuffer = Temporaries.floatBuffer1.get();
        inOut.copyTo(floatBuffer);
        readVec3(recorderVa, floatBuffer);
        inOut.set(floatBuffer);
    }

    /**
     * Alter whether the recorder is validating.
     *
     * @param setting {@code true} to begin validating, {@code false} to stop
     * validating
     */
    public void setValidating(boolean setting) {
        long recorderVa = va();
        setValidating(recorderVa, setting);
    }

    /**
     * Write the specified ID vector.
     *
     * @param vector the vector to write (not null, unaffected)
     */
    public void write(BodyIdVector vector) {
        long recorderVa = va();
        long vectorVa = vector.va();
        writeBodyIdVector(recorderVa, vectorVa);
    }

    /**
     * Write the specified boolean value.
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
     * Write the specified 32-bit integer value.
     *
     * @param i the value to write
     */
    public void write(int i) {
        long recorderVa = va();
        writeInt(recorderVa, i);
    }

    /**
     * Write the value of the specified matrix.
     *
     * @param matrix the matrix to write (not null, unaffected)
     */
    public void write(RMat44Arg matrix) {
        long recorderVa = va();
        long matrixVa = matrix.targetVa();
        writeRMat44(recorderVa, matrixVa);
    }

    /**
     * Write the value of the specified vector.
     *
     * @param v the vector to write (not null, unaffected)
     */
    public void write(RVec3Arg v) {
        long recorderVa = va();
        double xx = v.xx();
        double yy = v.yy();
        double zz = v.zz();
        writeRVec3(recorderVa, xx, yy, zz);
    }

    /**
     * Write the specified text string.
     *
     * @param javaString the string to write (not null)
     */
    public void write(String javaString) {
        long recorderVa = va();
        writeString(recorderVa, javaString);
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
    // protected methods

    /**
     * Assign a native object (assuming there's none already assigned) and
     * designate the JVM object as the owner.
     *
     * @param recorderVa the virtual address of the native object to assign (not
     * zero)
     */
    final void setVirtualAddressAsOwner(long recorderVa) {
        Runnable freeingAction = () -> free(recorderVa);
        setVirtualAddress(recorderVa, freeingAction);
    }
    // *************************************************************************
    // native private methods

    native private static void free(long recorderVa);

    native private static boolean isValidating(long recorderVa);

    native private static void readBodyIdVector(long recorderVa, long vectorVa);

    native private static boolean readBoolean(long recorderVa, boolean b);

    native private static float readFloat(long recorderVa, float f);

    native private static int readInt(long recorderVa, int i);

    native private static void readRMat44(long recorderVa, long matrixVa);

    native private static void readRVec3(long recorderVa, double[] tmpDoubles);

    native private static String readString(long recorderVa, String javaString);

    native private static void readVec3(
            long recorderVa, FloatBuffer floatBuffer);

    native private static void setValidating(long recorderVa, boolean setting);

    native private static void writeBodyIdVector(
            long recorderVa, long vectorVa);

    native private static void writeBoolean(long recorderVa, boolean b);

    native private static void writeFloat(long recorderVa, float f);

    native private static void writeInt(long recorderVa, int i);

    native private static void writeRMat44(long recorderVa, long matrixVa);

    native private static void writeRVec3(
            long recorderVa, double xx, double yy, double zz);

    native private static void writeString(long recorderVa, String javaString);

    native private static void writeVec3(
            long recorderVa, float x, float y, float z);
}
