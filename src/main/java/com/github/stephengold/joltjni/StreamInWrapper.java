/*
Copyright (c) 2024-2026 Stephen Gold

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

import com.github.stephengold.joltjni.std.StringStream;
import java.nio.FloatBuffer;

/**
 * A wrapper around an {@code std::ifstream}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class StreamInWrapper extends StreamIn {
    // *************************************************************************
    // constructors

    /**
     * Open a new stream.
     *
     * @param streamVa the virtual address of the native object to assign (not
     * zero)
     */
    private StreamInWrapper(long streamVa) {
        setVirtualAddressAsOwner(streamVa);
    }

    /**
     * Open a {@code StringStream} for input.
     *
     * @param data the underlying stream (not {@code null})
     */
    public StreamInWrapper(StringStream data) {
        long dataVa = data.va();
        long streamVa = createFromStringStream(dataVa);
        setVirtualAddressAsOwner(streamVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the mode bit for a stream that appends to existing content.
     *
     * @return the value of {@code std::ifstream::app}
     */
    native public static int app();

    /**
     * Return the mode bit for a stream whose output position starts at the end
     * of the file.
     *
     * @return the value of {@code std::ifstream::ate}
     */
    native public static int ate();

    /**
     * Return the mode bit for a binary-mode stream.
     *
     * @return the value of {@code std::ifstream::binary}
     */
    native public static int binary();

    /**
     * Return the mode bit for a stream that supports input operations.
     *
     * @return the value of {@code std::ifstream::in}
     */
    native public static int in();

    /**
     * Test whether the stream has reached its end.
     *
     * @return {@code true} if it has reached its end, otherwise {@code false}
     */
    public boolean isEof() {
        long streamVa = va();
        boolean result = isEof(streamVa);

        return result;
    }

    /**
     * Open a file for input.
     *
     * @param fileName the name of the file to open (not {@code null})
     * @param streamMode the desired mode bits or-ed together
     * @return a new object, or {@code null} if the file wasn't opened
     * successfully
     */
    public static StreamInWrapper open(String fileName, int streamMode) {
        long streamVa = createStreamInWrapper(fileName, streamMode);
        StreamInWrapper result;
        if (streamVa == 0L) {
            result = null;
        } else {
            result = new StreamInWrapper(streamVa);
        }

        return result;
    }

    /**
     * Read groups of 3 floats from the stream. TODO rename
     *
     * @param storeFloats storage for the values that will be read (not
     * {@code null}, modified)
     */
    public void readBytes(Float3[] storeFloats) {
        long streamVa = va();
        int numGroups = storeFloats.length;
        FloatBuffer data = Temporaries.floatBuffer1.get();
        for (int i = 0; i < numGroups; ++i) {
            readFloat3(streamVa, data);
            storeFloats[i].set(data);
        }
    }

    /**
     * Read 3 floats from the stream.
     *
     * @param storeFloats storage for the values that will be read (not
     * {@code null}, modified)
     */
    public void readFloat3(Float3 storeFloats) {
        long streamVa = va();
        FloatBuffer buffer = Temporaries.floatBuffer1.get();
        readFloat3(streamVa, buffer);
        storeFloats.set(buffer);
    }

    /**
     * Read hair skinning weights from the stream.
     *
     * @param storeResult storage for the values that will be read (not
     * {@code null}, modified)
     */
    public void readHairSkinWeights(HairSkinWeight[] storeResult) {
        long streamVa = va();
        int numMats = storeResult.length;
        for (int i = 0; i < numMats; ++i) {
            long storeWeightVa = storeResult[i].va();
            readHairSkinWeight(streamVa, storeWeightVa);
        }
    }

    /**
     * Read indexed triangles from the stream.
     *
     * @param storeResult storage for the values that will be read (not
     * {@code null}, modified)
     */
    public void readIndexedTriangles(IndexedTriangleNoMaterial[] storeResult) {
        long streamVa = va();
        int numTriangles = storeResult.length;
        for (int i = 0; i < numTriangles; ++i) {
            long storeTriangleVa = storeResult[i].va();
            readTriangle(streamVa, storeTriangleVa);
        }
    }

    /**
     * Read a 32-bit integer from the stream.
     *
     * @return the value that was read
     */
    @Override
    public int readInt() {
        long streamVa = va();
        int result = readInt(streamVa);

        return result;
    }

    /**
     * Read matrices from the stream.
     *
     * @param storeResult storage for the values that will be read (not
     * {@code null}, modified)
     */
    public void readMatrices(Mat44[] storeResult) {
        long streamVa = va();
        int numMats = storeResult.length;
        for (int i = 0; i < numMats; ++i) {
            long storeMatrixVa = storeResult[i].va();
            readMat44(streamVa, storeMatrixVa);
        }
    }

    /**
     * Read soft-body skinning weights from the stream.
     *
     * @param storeResult storage for the values that will be read (not
     * {@code null}, modified)
     */
    public void readSkinWeights(SkinWeight[] storeResult) {
        long streamVa = va();
        int numMats = storeResult.length;
        for (int i = 0; i < numMats; ++i) {
            long storeWeightVa = storeResult[i].va();
            readSkinWeight(streamVa, storeWeightVa);
        }
    }

    /**
     * Read a vector from the stream.
     *
     * @param storeVec storage for the value that will be read (not
     * {@code null}, modified)
     */
    public void readVec3(Vec3 storeVec) {
        long streamVa = va();
        FloatBuffer buffer = Temporaries.floatBuffer1.get();
        readFloat3(streamVa, buffer);
        storeVec.set(buffer);
    }

    /**
     * Return the mode bit for a stream that supports output operations.
     *
     * @return the value of {@code std::ifstream::out}
     */
    native public static int out();

    /**
     * Return the mode bit for a stream that discards pre-existing content when
     * opened.
     *
     * @return the value of {@code std::ifstream::trunc}
     */
    native public static int trunc();
    // *************************************************************************
    // native private methods

    native private static long createFromStringStream(long dataVa);

    native private static long createStreamInWrapper(
            String fileName, int streamMode);

    native private static boolean isEof(long streamVa);

    native private static void readFloat3(long streamVa, FloatBuffer buffer);

    native private static void readHairSkinWeight(
            long streamVa, long storeWeightVa);

    native private static int readInt(long streamVa);

    native private static void readMat44(long streamVa, long storeMatrixVa);

    native private static void readSkinWeight(
            long streamVa, long storeWeightVa);

    native private static void readTriangle(
            long streamVa, long storeTriangleVa);
}
