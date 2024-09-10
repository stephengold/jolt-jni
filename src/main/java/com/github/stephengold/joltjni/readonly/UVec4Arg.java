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
package com.github.stephengold.joltjni.readonly;

/**
 * Read-only access to a {@code UVec4}. (native type: const UVec4)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface UVec4Arg {
    // *************************************************************************
    // new methods exposed

    /**
     * Return the 4th (W) component. The vector is unaffected.
     *
     * @return the component value
     */
    int getW();

    /**
     * Return the first (X) component. The vector is unaffected.
     *
     * @return the component value
     */
    int getX();

    /**
     * Return the 2nd (Y) component. The vector is unaffected.
     *
     * @return the component value
     */
    int getY();

    /**
     * Return the 3rd (Z) component. The vector is unaffected.
     *
     * @return the component value
     */
    int getZ();
}
