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
 * A {@code TwoBodyConstraint} that holds 2 points at a fixed distance from each
 * other.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class DistanceConstraint extends TwoBodyConstraint {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a constraint with the specified native object assigned but
     * not owned.
     *
     * @param constraintVa the virtual address of the native object to assign
     * (not zero)
     */
    DistanceConstraint(long constraintVa) {
        super(constraintVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the spring settings. The constraint settings are unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public SpringSettings getLimitsSpringSettings() {
        long constraintSettingsVa = va();
        long springSettingsVa
                = getLimitsSpringSettings(constraintSettingsVa);
        SpringSettings result = new SpringSettings(this, springSettingsVa);

        return result;
    }

    /**
     * Return the maximum distance. The constraint is unaffected.
     *
     * @return the distance
     */
    public float getMaxDistance() {
        long constraintVa = va();
        float result = getMaxDistance(constraintVa);

        return result;
    }

    /**
     * Return the minimum distance. The constraint is unaffected.
     *
     * @return the distance
     */
    public float getMinDistance() {
        long constraintVa = va();
        float result = getMinDistance(constraintVa);

        return result;
    }

    /**
     * Alter the limits.
     *
     * @param min the desired lower limit (in meters)
     * @param max the desired upper limit (in meters)
     */
    public void setDistance(float min, float max) {
        long constraintVa = va();
        setDistance(constraintVa, min, max);
    }
    // *************************************************************************
    // native private methods

    native private static long getLimitsSpringSettings(long constraintVa);

    native private static float getMaxDistance(long constraintVa);

    native private static float getMinDistance(long constraintVa);

    native private static void setDistance(
            long constraintVa, float min, float max);
}
