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
package com.github.stephengold.joltjni.std;

import java.io.PrintStream;

/**
 * Java equivalents for features of the {@code std::} namespace.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class Std {
    // *************************************************************************
    // constants

    /**
     * difference between 1 and the smallest float greater than 1
     */
    final public static float FLT_EPSILON = 1.1920929e-7f;
    /**
     * largest finite value of type {@code float}
     */
    final public static float FLT_MAX = Float.MAX_VALUE;
    /**
     * largest value of type {@code int}
     */
    final public static int INT_MAX = Integer.MAX_VALUE;
    /**
     * standard error stream (to expedite translation of C++ code)
     */
    final public static PrintStream cerr = System.err;
    /**
     * standard output stream (to expedite translation of C++ code)
     */
    final public static PrintStream cout = System.out;
    /**
     * line separator (to expedite translation of C++ code)
     */
    final public static String endl = System.lineSeparator();
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private Std() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the inverse cosine of the specified single-precision ratio.
     *
     * @param ratio the input cosine ratio (&ge;-1, &le;1)
     * @return the angle (in radians)
     */
    native public static float acos(float ratio);

    /**
     * Return the inverse tangent of the specified single-precision ratio.
     *
     * @param ratio the input tangent ratio
     * @return the angle (in radians)
     */
    native public static float atan(float ratio);

    /**
     * Return the cosine of the specified single-precision angle.
     *
     * @param angle the input angle (in radians)
     * @return the cosine ratio
     */
    native public static float cos(float angle);

    /**
     * Return the exponential of the specified single-precision value.
     *
     * @param value the input exponent
     * @return the exponential
     */
    native public static float exp(float value);

    /**
     * Return the remainder when {@code numerator} is divided by
     * {@code denominator}.
     *
     * @param numerator the numerator
     * @param denominator the denominator
     * @return the remainder (with the same sign as {@code numerator})
     */
    native public static float fmod(float numerator, float denominator);

    /**
     * Return the specified power of the specified single-precision base.
     *
     * @param base the base value
     * @param exponent the exponent value
     * @return the power value
     */
    native public static float pow(float base, float exponent);

    /**
     * Return the sine of the specified single-precision angle.
     *
     * @param angle the input angle (in radians)
     * @return the sine ratio
     */
    native public static float sin(float angle);

    /**
     * Return the square root of the specified single-precision value.
     *
     * @param value the input value
     * @return the square root
     */
    native public static float sqrt(float value);

    /**
     * Compare the specified strings lexicographically.
     *
     * @param lhs the first string to compare (not null)
     * @param rhs the 2nd string to compare (not null)
     * @return a negative value if {@code lhs} precedes {@code rhs} in
     * lexicographical order, or greater than zero if {@code lhs} follows
     * {@code rhs}, or zero if they are equal
     */
    public static int strcmp(String lhs, String rhs) {
        int result = lhs.compareTo(rhs);
        return result;
    }
}
