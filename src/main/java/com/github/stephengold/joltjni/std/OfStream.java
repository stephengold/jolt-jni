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
package com.github.stephengold.joltjni.std;

import com.github.stephengold.joltjni.JoltPhysicsObject;

/**
 * An output file stream. (native type: std::ofstream)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class OfStream extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default file stream.
     */
    public OfStream() {
        long streamVa = createDefault();
        setVirtualAddress(streamVa, () -> free(streamVa));
    }

    /**
     * Open a file for output.
     *
     * @param fileName the name of the file to open (not null)
     * @param streamMode the desired mode bits or-ed together (see
     * {@code StreamOutWrapper})
     */
    public OfStream(String fileName, int streamMode) {
        long streamVa = create(fileName, streamMode);
        setVirtualAddress(streamVa, () -> free(streamVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Close the stream. (native function: {@code close()})
     */
    public void closeStream() {
        long streamVa = va();
        closeStream(streamVa);
    }

    /**
     * Open a file for output.
     *
     * @param fileName the name of the file to open (not null)
     * @param streamMode the desired mode bits or-ed together (see
     * {@code StreamOutWrapper})
     */
    public void open(String fileName, int streamMode) {
        long streamVa = va();
        open(streamVa, fileName, streamMode);
    }

    /**
     * Return the output position.
     *
     * @return buffer position relative to the first character, or -1 to
     * indicate a failure
     */
    public int tellp() {
        long streamVa = va();
        int result = tellp(streamVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void closeStream(long streamVa);

    native private static long create(String fileName, int streamMode);

    native private static long createDefault();

    native private static void free(long streamVa);

    native private static void open(
            long streamVa, String fileName, int streamMode);

    native private static int tellp(long streamVa);
}
