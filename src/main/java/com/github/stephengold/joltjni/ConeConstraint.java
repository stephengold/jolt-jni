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
 * A {@code TwoBodyConstraint} that limits the swing of the twist axes to a cone
 * and disables translation.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ConeConstraint extends TwoBodyConstraint {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a constraint with the specified native object assigned.
     *
     * @param constraintVa the virtual address of the native object to assign
     * (not zero)
     */
    ConeConstraint(long constraintVa) {
        setVirtualAddressAsCoOwner(constraintVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the cosine of half the cone angle. The constraint is unaffected.
     *
     * @return the cosine ratio
     */
    public float getCosHalfConeAngle() {
        long constraintVa = va();
        float result = getCosHalfConeAngle(constraintVa);

        return result;
    }

    /**
     * Alter the cone angle.
     *
     * @param halfAngle the desired half angle (in radians)
     */
    public void setHalfConeAngle(float halfAngle) {
        long constraintVa = va();
        setHalfConeAngle(constraintVa, halfAngle);
    }
    // *************************************************************************
    // native private methods

    native private static float getCosHalfConeAngle(long constraintVa);

    native private static void setHalfConeAngle(
            long constraintVa, float halfAngle);
}
