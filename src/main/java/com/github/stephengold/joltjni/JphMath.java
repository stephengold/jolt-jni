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

/**
 * Utility methods providing Jolt Physics math functions.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class JphMath {
    // *************************************************************************
    // constants

    /**
     * single-precision value of Pi
     */
    final public static float JPH_PI = (float) Math.PI;
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private JphMath() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the inverse cosine of the specified single-precision ratio. (see
     * Jolt/Math/Trigonometry.h)
     *
     * @param ratio the input cosine ratio (&ge;-1, &le;1)
     * @return the angle (in radians)
     */
    native public static float aCos(float ratio);

    /**
     * Return the inverse tangent of the specified single-precision ratio. (see
     * Jolt/Math/Trigonometry.h)
     *
     * @param ratio the input tangent ratio
     * @return the angle (in radians)
     */
    native public static float aTan(float ratio);

    /**
     * Return the angle of the specified single-precision right triangle. (see
     * Jolt/Math/Trigonometry.h)
     *
     * @param opposite the signed length of the opposite side
     * @param adjacent the signed length of the adjacent side
     * @return the angle (in radians)
     */
    native public static float aTan2(float opposite, float adjacent);

    /**
     * Return the cosine of the specified single-precision angle. (see
     * Jolt/Math/Trigonometry.h)
     *
     * @param angle the input angle (in radians)
     * @return the cosine ratio
     */
    native public static float cos(float angle);

    /**
     * Convert the specified angle from degrees to radians.
     *
     * @param degrees the angle to convert (in degrees)
     * @return the converted angle (in radians)
     */
    public static float degreesToRadians(float degrees) {
        float result = degrees * (JPH_PI / 180f);
        return result;
    }

    /**
     * Convert the specified angle from radians to degrees.
     *
     * @param radians the angle to convert (in radians)
     * @return the converted angle (in degrees)
     */
    public static float radiansToDegrees(float radians) {
        float result = radians * (180f / JPH_PI);
        return result;
    }

    /**
     * Return the (binary) sign of the specified single-precision value. (see
     * Jolt/Math/Math.h)
     *
     * @param input the input value
     * @return -1 if the input is negative, otherwise +1
     */
    public static float sign(float input) {
        float result = (input < 0) ? -1f : 1f;
        return result;
    }

    /**
     * Return the sine of the specified single-precision angle. (see
     * Jolt/Math/Trigonometry.h)
     *
     * @param angle the input angle (in radians)
     * @return the sine ratio
     */
    native public static float sin(float angle);

    /**
     * Return the square root of the specified single-precision value but don't
     * set {@code errno}. (see Jolt/Math/Math.h)
     *
     * @param value the input value
     * @return the square root
     */
    native public static float sqrt(float value);

    /**
     * Return the square of the specified single-precision value.
     *
     * @param value the input value
     * @return the square
     */
    public static float square(float value) {
        float result = value * value;
        return result;
    }

    /**
     * Return the square of the specified integer value.
     *
     * @param value the input value
     * @return the square
     */
    public static int square(int value) {
        int result = value * value;
        return result;
    }

    /**
     * Return the tangent ratio of the specified single-precision angle. (see
     * Jolt/Math/Trigonometry.h)
     *
     * @param angle the input angle (in radians)
     * @return the tangent ratio
     */
    native public static float tan(float angle);
}
