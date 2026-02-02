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

import com.github.stephengold.joltjni.std.OfStream;
import com.github.stephengold.joltjni.std.StringStream;

/**
 * A wrapper around an {@code std::ofstream}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class StreamOutWrapper extends StreamOut {
    // *************************************************************************
    // fields

    /**
     * protect the wrapped stream from garbage collection
     */
    private JoltPhysicsObject stream;
    // *************************************************************************
    // constructors

    /**
     * Instantiate using an already-open file stream.
     *
     * @param data the underlying stream (not null)
     */
    public StreamOutWrapper(OfStream data) {
        this.stream = data;
        long dataVa = data.va();
        long streamVa = createFromOfStream(dataVa);
        setVirtualAddressAsOwner(streamVa);
    }

    /**
     * Open a {@code StringStream} for output.
     *
     * @param data the underlying stream (not null)
     */
    public StreamOutWrapper(StringStream data) {
        this.stream = data;
        long dataVa = data.va();
        long streamVa = createFromStringStream(dataVa);
        setVirtualAddressAsOwner(streamVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the mode bit for a stream that appends to existing content.
     *
     * @return the value of {@code std::ofstream::app}
     */
    native public static int app();

    /**
     * Return the mode bit for a stream whose output position starts at the end
     * of the file.
     *
     * @return the value of {@code std::ofstream::ate}
     */
    native public static int ate();

    /**
     * Return the mode bit for a binary-mode stream.
     *
     * @return the value of {@code std::ofstream::binary}
     */
    native public static int binary();

    /**
     * Return the mode bit for a stream that supports input operations.
     *
     * @return the value of {@code std::ofstream::in}
     */
    native public static int in();

    /**
     * Return the mode bit for a stream that supports output operations.
     *
     * @return the value of {@code std::ofstream::out}
     */
    native public static int out();

    /**
     * Return the mode bit for a stream that discards pre-existing content when
     * opened.
     *
     * @return the value of {@code std::ofstream::trunc}
     */
    native public static int trunc();
    // *************************************************************************
    // native private methods

    native private static long createFromOfStream(long dataVa);

    native private static long createFromStringStream(long dataVa);
}
