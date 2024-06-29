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
 * A read-only interface to {@code RVec3} instances.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface RVec3Arg {
    // *************************************************************************
    // new methods exposed

    /**
     * Return the first (X) component at positional precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    Object getX();

    /**
     * Return the 2nd (Y) component at positional precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    Object getY();

    /**
     * Return the 3rd (Z) component at positional precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    Object getZ();

    /**
     * Return the first (X) component in single precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    float x();

    /**
     * Return the first (X) component in double precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    double xx();

    /**
     * Return the 2nd (Y) component in single precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    float y();

    /**
     * Return the 2nd (Y) component in double precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    double yy();

    /**
     * Return the 3rd (Z) component in single precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    float z();

    /**
     * Return the 3rd (Z) component in double precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    double zz();
}
