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

/**
 * An abstract binary output stream.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class StreamOut extends NonCopyable {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with no native object assigned.
     * <p>
     * This no-arg constructor was made explicit to avoid javadoc warnings from
     * JDK 18+.
     */
    StreamOut() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Write an {@code int}.
     *
     * @param iValue the value to write
     */
    public void writeInt(int iValue) {
        long streamVa = va();
        writeInt(streamVa, iValue);
    }
    // *************************************************************************
    // protected methods

    /**
     * Assign a native object (assuming there's none already assigned) and
     * designate the JVM object as the owner.
     *
     * @param streamVa the virtual address of the native object to assign (not
     * zero)
     */
    final void setVirtualAddressAsOwner(long streamVa) {
        Runnable freeingAction = () -> free(streamVa);
        setVirtualAddress(streamVa, freeingAction);
    }
    // *************************************************************************
    // native private methods

    native private static void free(long streamVa);

    native private static void writeInt(long streamVa, int iValue);
}
