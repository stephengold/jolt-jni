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
 * Full implementation of {@code StateRecorder}, using {@code stringstream}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class StateRecorderImpl extends StateRecorder {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a recorder.
     */
    public StateRecorderImpl() {
        long recorderVa = createStateRecorderImpl();
        setVirtualAddress(recorderVa, true);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Read binary data.
     *
     * @return the bytes that were read
     */
    public byte[] getData() {
        long recorderVa = va();
        int numBytes = countBytes(recorderVa);
        byte[] result = new byte[numBytes];
        getData(recorderVa, result, numBytes);

        return result;
    }

    /**
     * Write binary data.
     *
     * @param data the bytes to write (not null, unaffected)
     */
    public void writeBytes(byte[] data) {
        writeBytes(data, data.length);
    }

    /**
     * Write binary data.
     *
     * @param data the bytes to write (not nullm unaffected)
     * @param numBytes the number of byte to write (&ge;0)
     */
    public void writeBytes(byte[] data, int numBytes) {
        long recorderVa = va();
        writeBytes(recorderVa, data, numBytes);
    }
    // *************************************************************************
    // native private methods

    native private static int countBytes(long recorderVa);

    native private static long createStateRecorderImpl();

    native private static void getData(
            long recorderVa, byte[] data, int numBytes);

    native private static void writeBytes(
            long recorderVa, byte[] data, int numBytes);
}
