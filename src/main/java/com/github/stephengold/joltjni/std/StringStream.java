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
package com.github.stephengold.joltjni.std;

import com.github.stephengold.joltjni.JoltPhysicsObject;

/**
 * Access a string of text as a stream. (native type: std::stringstream)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class StringStream extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty stream.
     */
    public StringStream() {
        long streamVa = createDefault();
        setVirtualAddress(streamVa, () -> free(streamVa));
    }

    /**
     * Instantiate a stream from the specified string of text.
     *
     * @param string the desired initial content (not null)
     */
    public StringStream(String string) {
        long streamVa = createFromString(string);
        setVirtualAddress(streamVa, () -> free(streamVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Convert the stream to a string of text.
     *
     * @return the string value
     */
    public String str() {
        long streamVa = va();
        String result = str(streamVa);

        return result;
    }

    /**
     * Return the input position.
     *
     * @return buffer position relative to the first character, or -1 to
     * indicate a failure
     */
    public int tellg() {
        long streamVa = va();
        int result = tellg(streamVa);

        return result;
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

    native private static long createDefault();

    native private static long createFromString(String string);

    native private static void free(long streamVa);

    native private static String str(long streamVa);

    native private static int tellg(long streamVa);

    native private static int tellp(long streamVa);
}
