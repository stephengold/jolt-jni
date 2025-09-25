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
 * Settings used to construct a {@code CylinderShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CylinderShapeSettings extends ConvexShapeSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public CylinderShapeSettings() {
        long settingsVa = createDefault();
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.Cylinder);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public CylinderShapeSettings(CylinderShapeSettings original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddressAsCoOwner(copyVa, EShapeSubType.Cylinder);
    }

    /**
     * Instantiate settings for the specified dimensions.
     *
     * @param halfHeight half the desired height
     * @param radius the desired radius of the cylinder
     */
    public CylinderShapeSettings(float halfHeight, float radius) {
        this(halfHeight, radius, 0.05f);
    }

    /**
     * Instantiate settings for the specified dimensions.
     *
     * @param halfHeight half the desired height of the cylinder
     * @param radius the desired radius of the cylinder
     * @param convexRadius the desired convex radius (default=0.05)
     */
    public CylinderShapeSettings(
            float halfHeight, float radius, float convexRadius) {
        this(halfHeight, radius, convexRadius, null);
    }

    /**
     * Instantiate settings for the specified dimensions and material.
     *
     * @param halfHeight half the desired height of the cylinder
     * @param radius the desired radius of the cylinder
     * @param convexRadius the desired convex radius (default=0.05)
     * @param material the desired material (default=null)
     */
    public CylinderShapeSettings(float halfHeight, float radius,
            float convexRadius, PhysicsMaterial material) {
        long materialVa = (material == null) ? 0L : material.va();
        long settingsVa = createShapeSettings(
                halfHeight, radius, convexRadius, materialVa);
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.Cylinder);
    }

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    CylinderShapeSettings(long settingsVa) {
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.Cylinder);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the convex radius. The settings are unaffected. (native attribute:
     * mConvexRadius)
     *
     * @return the convex radius (&ge;0)
     */
    public float getConvexRadius() {
        long settingsVa = va();
        float result = getConvexRadius(settingsVa);

        return result;
    }

    /**
     * Return the half height. The settings are unaffected. (native attribute:
     * mHalfHeight)
     *
     * @return half the height
     */
    public float getHalfHeight() {
        long settingsVa = va();
        float result = getHalfHeight(settingsVa);

        return result;
    }

    /**
     * Return the radius of the cylinder. The settings are unaffected. (native
     * attribute: mRadius)
     *
     * @return the radius (&ge;0)
     */
    public float getRadius() {
        long settingsVa = va();
        float result = getRadius(settingsVa);

        return result;
    }

    /**
     * Alter the convex radius. (native attribute: mConvexRadius)
     *
     * @param radius the desired convex radius (&ge;0, default=0.05)
     */
    public void setConvexRadius(float radius) {
        long settingsVa = va();
        setConvexRadius(settingsVa, radius);
    }

    /**
     * Alter the half height. (native attribute: mHalfHeight)
     *
     * @param halfHeight one half of the desired height (&ge;0)
     */
    public void setHalfHeight(float halfHeight) {
        long settingsVa = va();
        setHalfHeight(settingsVa, halfHeight);
    }

    /**
     * Alter the radius of the cylinder. (native attribute: mRadius)
     *
     * @param radius the desired radius (&ge;0)
     */
    public void setRadius(float radius) {
        long settingsVa = va();
        setRadius(settingsVa, radius);
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static long createShapeSettings(float halfHeight,
            float radius, float convexRadius, long materialVa);

    native private static float getConvexRadius(long settingsVa);

    native private static float getHalfHeight(long settingsVa);

    native private static float getRadius(long settingsVa);

    native private static void setConvexRadius(long settingsVa, float radius);

    native private static void setHalfHeight(long settingsVa, float halfHeight);

    native private static void setRadius(long settingsVa, float radius);
}
