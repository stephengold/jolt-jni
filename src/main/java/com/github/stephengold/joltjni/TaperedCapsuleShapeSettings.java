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

import com.github.stephengold.joltjni.enumerate.EShapeSubType;

/**
 * Settings used to construct a {@code TaperedCylinderShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class TaperedCapsuleShapeSettings extends ConvexShapeSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public TaperedCapsuleShapeSettings() {
        long settingsVa = createDefault();
        setVirtualAddress(settingsVa); // not owner due to ref counting
        setSubType(EShapeSubType.TaperedCapsule);
    }

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    TaperedCapsuleShapeSettings(long settingsVa) {
        super(settingsVa);
        setSubType(EShapeSubType.TaperedCapsule);
    }

    /**
     * Instantiate settings for the specified dimensions.
     *
     * @param halfHeight half the desired height of the tapered cylinder
     * @param topRadius the desired top radius
     * @param bottomRadius the desired bottom radius
     */
    public TaperedCapsuleShapeSettings(
            float halfHeight, float topRadius, float bottomRadius) {
        this(halfHeight, topRadius, bottomRadius, null);
    }

    /**
     * Instantiate settings for the specified dimensions and material.
     *
     * @param halfHeight half the desired height of the tapered cylinder
     * @param topRadius the desired top radius
     * @param bottomRadius the desired bottom radius
     * @param material the desired material (default=null)
     */
    public TaperedCapsuleShapeSettings(float halfHeight, float topRadius,
            float bottomRadius, PhysicsMaterial material) {
        long materialVa = (material == null) ? 0L : material.va();
        long settingsVa = createShapeSettings(
                halfHeight, topRadius, bottomRadius, materialVa);
        setVirtualAddress(settingsVa); // not owner due to ref counting
        setSubType(EShapeSubType.TaperedCapsule);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public TaperedCapsuleShapeSettings(TaperedCapsuleShapeSettings original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa); // not owner due to ref counting
        setSubType(EShapeSubType.TaperedCapsule);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the bottom radius of the cylinder. The settings are unaffected.
     * (native attribute: mBottomRadius)
     *
     * @return the bottom radius (&ge;0)
     */
    public float getBottomRadius() {
        long settingsVa = va();
        float result = getBottomRadius(settingsVa);

        return result;
    }

    /**
     * Return the half height of the tapered cylinder. The settings are
     * unaffected. (native attribute: mHalfHeightOfTaperedCylinder)
     *
     * @return half the height
     */
    public float getHalfHeightOfTaperedCylinder() {
        long settingsVa = va();
        float result = getHalfHeightOfTaperedCylinder(settingsVa);

        return result;
    }

    /**
     * Return the top radius of the cylinder. The settings are unaffected.
     * (native attribute: mTopRadius)
     *
     * @return the top radius (&ge;0)
     */
    public float getTopRadius() {
        long settingsVa = va();
        float result = getTopRadius(settingsVa);

        return result;
    }

    /**
     * Alter the bottom radius of the cylinder. (native attribute:
     * mBottomRadius)
     *
     * @param radius the desired bottom radius (&ge;0)
     */
    public void setBottomRadius(float radius) {
        long settingsVa = va();
        setBottomRadius(settingsVa, radius);
    }

    /**
     * Alter the half height of the tapered cylinder. (native attribute:
     * mHalfHeightOfTaperedCylinder)
     *
     * @param halfHeight one half of the desired height (&ge;0)
     */
    public void setHalfHeightOfTaperedCylinder(float halfHeight) {
        long settingsVa = va();
        setHalfHeightOfTaperedCylinder(settingsVa, halfHeight);
    }

    /**
     * Alter the top radius of the cylinder. (native attribute: mTopRadius)
     *
     * @param radius the desired top radius (&ge;0)
     */
    public void setTopRadius(float radius) {
        long settingsVa = va();
        setTopRadius(settingsVa, radius);
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static long createShapeSettings(float halfHeight,
            float topRadius, float bottomRadius, long materialVa);

    native private static float getBottomRadius(long settingsVa);

    native private static float getHalfHeightOfTaperedCylinder(long settingsVa);

    native private static float getTopRadius(long settingsVa);

    native private static void setBottomRadius(long settingsVa, float radius);

    native private static void setHalfHeightOfTaperedCylinder(
            long settingsVa, float halfHeight);

    native private static void setTopRadius(long settingsVa, float radius);
}
