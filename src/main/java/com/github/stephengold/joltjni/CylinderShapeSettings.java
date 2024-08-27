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

import com.github.stephengold.joltjni.enumerate.EShapeSubType;

/**
 * Settings used to construct a {@code CylinderShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CylinderShapeSettings extends ConvexShapeSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param virtualAddress the virtual address of the native object to assign
     * (not zero)
     */
    CylinderShapeSettings(long virtualAddress) {
        super(virtualAddress);
        setSubType(EShapeSubType.Cylinder);
    }

    /**
     * Instantiate settings for the specified dimensions.
     *
     * @param halfHeight half the desired height
     * @param radius the desired radius
     */
    public CylinderShapeSettings(float halfHeight, float radius) {
        long settingsVa = createCylinderShapeSettings(halfHeight, radius);
        setVirtualAddress(settingsVa, null); // not owner due to ref counting
        setSubType(EShapeSubType.Cylinder);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the convex radius.
     *
     * @return the convex radius (&ge;0)
     */
    public float getConvexRadius() {
        long settingsVa = va();
        float result = getConvexRadius(settingsVa);

        return result;
    }

    /**
     * Return the half height. The settings are unaffected. (native field:
     * mHalfHeight)
     *
     * @return half the height
     *
     */
    public float getHalfHeight() {
        long settingsVa = va();
        float result = getHalfHeight(settingsVa);

        return result;
    }

    /**
     * Return the radius of the cylinder. The settings are unaffected (native
     * field: mRadius)
     *
     * @return the radius (&ge;0)
     */
    public float getRadius() {
        long settingsVa = va();
        float result = getRadius(settingsVa);

        return result;
    }

    /**
     * Alter the convex radius. (native field: mConvexRadius)
     *
     * @param radius the desired convex radius (&ge;0)
     */
    public void setConvexRadius(float radius) {
        long settingsVa = va();
        setConvexRadius(settingsVa, radius);
    }

    /**
     * Alter the half height. (native field: mHalfHeight)
     *
     * @param halfHeight one half of the desired height (&ge;0)
     *
     */
    public void setHalfHeight(float halfHeight) {
        long settingsVa = va();
        setHalfHeight(settingsVa, halfHeight);
    }

    /**
     * Return the radius of the cylinder. (native field: mRadius)
     *
     * @param radius the desired radius (&ge;0)
     */
    public void setRadius(float radius) {
        long settingsVa = va();
        setRadius(settingsVa, radius);
    }
    // *************************************************************************
    // native private methods

    native private static long createCylinderShapeSettings(
            float halfHeight, float radius);

    native private static float getConvexRadius(long settingsVa);

    native private static float getHalfHeight(long settingsVa);

    native private static float getRadius(long settingsVa);

    native private static void setConvexRadius(long settingsVa, float radius);

    native private static void setHalfHeight(long settingsVa, float halfHeight);

    native private static void setRadius(long settingsVa, float radius);
}
