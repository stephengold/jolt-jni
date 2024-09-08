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
import com.github.stephengold.joltjni.readonly.ConstPhysicsMaterial;

/**
 * Settings used to construct a {@code SphereShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SphereShapeSettings extends ConvexShapeSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    SphereShapeSettings(long settingsVa) {
        super(settingsVa);
        setSubType(EShapeSubType.Sphere);
    }

    /**
     * Instantiate settings for the specified radius.
     *
     * @param radius the desired radius (&ge;0)
     */
    public SphereShapeSettings(float radius) {
        this(radius, null);
    }

    /**
     * Instantiate settings for the specified radius and material.
     *
     * @param radius the desired radius (&ge;0)
     * @param material the desired surface properties (not null, unaffected) or
     * {@code null} for default properties (default=null)
     */
    public SphereShapeSettings(float radius, ConstPhysicsMaterial material) {
        long materialVa = (material == null) ? 0L : material.va();
        long settingsVa = createSphereShapeSettings(radius, materialVa);
        setVirtualAddress(settingsVa, null); // not owner due to ref counting
        setSubType(EShapeSubType.Sphere);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the radius of the sphere. The settings are unaffected. (native
     * field: mRadius)
     *
     *
     * @return the radius (&ge;0)
     */
    public float getRadius() {
        long settingsVa = va();
        float result = getRadius(settingsVa);

        return result;
    }

    /**
     * Alter the radius of the sphere. (native field: mRadius)
     *
     * @param radius the desired radius
     */
    public void setRadius(float radius) {
        long settingsVa = va();
        setRadius(settingsVa, radius);
    }
    // *************************************************************************
    // native private methods

    native private static long createSphereShapeSettings(
            float radius, long materialVa);

    native private static float getRadius(long settingsVa);

    native private static void setRadius(long settingsVa, float radius);
}
