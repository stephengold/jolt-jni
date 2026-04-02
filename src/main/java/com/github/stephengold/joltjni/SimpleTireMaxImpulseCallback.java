/*
Copyright (c) 2026 Stephen Gold

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
 * An efficient implementation of {@code TireMaxImpulseCallback} for use cases
 * where the lateral impulse is proportional to lateral friction times the
 * suspension impulse and the longitudinal impulse is proportional to
 * longitudinal friction times the suspension impulse.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SimpleTireMaxImpulseCallback extends TireMaxImpulseCallback {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a simple callback.
     *
     * @param lateral the desired multiplier for calculating lateral impulses
     * @param longitudinal the desired multiplier for calculating longitudinal
     * impulses
     */
    public SimpleTireMaxImpulseCallback(float lateral, float longitudinal) {
        long callbackVa = create(lateral, longitudinal);
        setVirtualAddressAsOwner(callbackVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the multiplier used to calculate lateral impulses.
     *
     * @return the multiplier value
     */
    public float getLateralMultiplier() {
        long callbackVa = va();
        float result = getLateralMultiplier(callbackVa);
        return result;
    }

    /**
     * Return the multiplier used to calculate longitudinal impulses.
     *
     * @return the multiplier value
     */
    public float getLongitudinalMultiplier() {
        long callbackVa = va();
        float result = getLongitudinalMultiplier(callbackVa);
        return result;
    }

    /**
     * Alter the multiplier used to calculate lateral impulses.
     *
     * @param multiplier the desired multiplier
     */
    public void setLateralMultiplier(float multiplier) {
        long callbackVa = va();
        setLateralMultiplier(callbackVa, multiplier);
    }

    /**
     * Alter the multiplier used to calculate longitudinal impulses.
     *
     * @param multiplier the desired multiplier
     */
    public void setLongitudinalMultiplier(float multiplier) {
        long callbackVa = va();
        setLongitudinalMultiplier(callbackVa, multiplier);
    }
    // *************************************************************************
    // native private methods

    native private static long create(float lateral, float longitudinal);

    native private static float getLateralMultiplier(long callbackVa);

    native private static float getLongitudinalMultiplier(long callbackVa);

    native private static void setLateralMultiplier(
            long callbackVa, float multiplier);

    native private static void setLongitudinalMultiplier(
            long callbackVa, float multiplier);
}
