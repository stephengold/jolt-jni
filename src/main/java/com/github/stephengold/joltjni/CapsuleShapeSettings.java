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
 * Settings used to construct a {@code CapsuleShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CapsuleShapeSettings extends ConvexShapeSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    CapsuleShapeSettings(long settingsVa) {
        super(settingsVa);
        setSubType(EShapeSubType.Capsule);
    }

    /**
     * Instantiate settings for the specified dimensions.
     *
     * @param halfHeight half the desired height of the cylindrical portion
     * @param radius the desired radius
     */
    public CapsuleShapeSettings(float halfHeight, float radius) {
        this(halfHeight, radius, null);
    }

    /**
     * Instantiate settings for the specified dimensions and material.
     *
     * @param halfHeight half the desired height of the cylindrical portion
     * @param radius the desired radius
     * @param material the desired material (default=null)
     */
    public CapsuleShapeSettings(
            float halfHeight, float radius, PhysicsMaterial material) {
        long materialVa = (material == null) ? 0L : material.va();
        long settingsVa = createShapeSettings(halfHeight, radius, materialVa);
        setVirtualAddress(settingsVa, null); // not owner due to ref counting
        setSubType(EShapeSubType.Capsule);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the half height of the cylindrical portion. The settings are
     * unaffected. (native field: mHalfHeightOfCylinder)
     *
     * @return half the height
     */
    public float getHalfHeightOfCylinder() {
        long settingsVa = va();
        float result = getHalfHeightOfCylinder(settingsVa);

        return result;
    }

    /**
     * Return the radius. The settings are unaffected. (native field: mRadius)
     *
     * @return the radius (&ge;0)
     */
    public float getRadius() {
        long settingsVa = va();
        float result = getRadius(settingsVa);

        return result;
    }

    /**
     * Alter the half height of the cylindrical portion. (native field:
     * mHalfHeightOfCylinder)
     *
     * @param halfHeight one half of the desired height of the cylindrical
     * portion (&ge;0)
     */
    public void setHalfHeightOfCylinder(float halfHeight) {
        long settingsVa = va();
        setHalfHeightOfCylinder(settingsVa, halfHeight);
    }

    /**
     * Alter the radius. (native field: mRadius)
     *
     * @param radius the radius (&ge;0)
     */
    public void setRadius(float radius) {
        long settingsVa = va();
        setRadius(settingsVa, radius);
    }
    // *************************************************************************
    // native private methods

    native private static long createShapeSettings(
            float halfHeight, float radius, long materialVa);

    native private static float getHalfHeightOfCylinder(long settingsVa);

    native private static float getRadius(long settingsVa);

    native private static void setHalfHeightOfCylinder(
            long settingsVa, float halfHeight);

    native private static void setRadius(long settingsVa, float radius);
}
