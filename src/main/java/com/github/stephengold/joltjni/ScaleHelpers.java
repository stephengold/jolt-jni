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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Utilities to test a scaling vector for various properties. (native namespace:
 * ScaleHelpers)
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class ScaleHelpers {
    // *************************************************************************
    // constants

    /**
     * The smallest valid scale factor.
     */
    final public static float cMinScale = 1e-6f;
    /**
     * The tolerance used when comparing components of a scale vector.
     */
    final public static float cScaleToleranceSq = 1e-8f;
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private ScaleHelpers() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether the specified scale turns 3-D objects inside-out.
     *
     * @param scale the scale vector to test (not null, unaffected)
     * @return {@code true} if it turns objects inside-out, otherwise
     * {@code false}
     */
    public static boolean isInsideOut(Vec3Arg scale) {
        int numNegativeFactors = (scale.getX() < 0f) ? 1 : 0;
        numNegativeFactors += (scale.getY() < 0f) ? 1 : 0;
        numNegativeFactors += (scale.getZ() < 0f) ? 1 : 0;

        if ((numNegativeFactors & 0x1) == 0x0) {
            return false; // an even number of negative factors
        } else {
            return true; // an odd number of negative factors
        }
    }

    /**
     * Test whether the specified scale is identity.
     *
     * @param scale the scale vector to test (not null, unaffected)
     * @return {@code true} if identity, otherwise {@code false}
     */
    public static boolean isNotScaled(Vec3Arg scale) {
        boolean result = scale.isClose(Vec3.sOne(), cScaleToleranceSq);
        return result;
    }
}
