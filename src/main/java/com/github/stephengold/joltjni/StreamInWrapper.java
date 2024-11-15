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
        setVirtualAddress(streamVa, true);
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
     * Open a new stream.
     *
     * @param fileName the name of the file to open (not null)
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
     * Read 3 floats from the stream.
     *
     * @param storeFloats storage for the values that were read (not null,
     * modified)
     */
    public void readFloat3(Float3 storeFloats) {
        long streamVa = va();
        float[] data = new float[3];
        readFloat3(streamVa, data);
        storeFloats.set(data);
    }

    /**
     * Read an integer from the stream.
     *
     * @return the value that was read
     */
    public int readInt() {
        long streamVa = va();
        int result = readInt(streamVa);

        return result;
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

    native private static long createStreamInWrapper(
            String fileName, int streamMode);

    native private static boolean isEof(long streamVa);

    native private static void readFloat3(long streamVa, float[] data);

    native private static int readInt(long streamVa);
}
