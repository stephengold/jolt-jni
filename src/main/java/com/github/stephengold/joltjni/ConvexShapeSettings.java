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

import com.github.stephengold.joltjni.readonly.ConstConvexShapeSettings;
import com.github.stephengold.joltjni.readonly.ConstPhysicsMaterial;

/**
 * Settings used to construct a {@code ConvexShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class ConvexShapeSettings
        extends ShapeSettings
        implements ConstConvexShapeSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate settings with no native object assigned.
     */
    ConvexShapeSettings() {
    }

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param virtualAddress the virtual address of the native object to assign
     * (not zero)
     */
    ConvexShapeSettings(long virtualAddress) {
        super(virtualAddress);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the shape's density.
     *
     * @param density the desired density (in kilograms per cubic meter,
     * default=1000)
     */
    public void setDensity(float density) {
        long settingsVa = va();
        setDensity(settingsVa, density);
    }

    /**
     * Replace the material. (native field: mMaterial)
     *
     * @param material the desired material, or null for none (default=null)
     */
    public void setMaterial(ConstPhysicsMaterial material) {
        long settingsVa = va();
        long materialVa = (material == null) ? 0L : material.va();
        setMaterial(settingsVa, materialVa);
    }
    // *************************************************************************
    // ConstConvexShapeSettings methods

    /**
     * Return the density. The settings are unaffected. (native field: mDensity)
     *
     * @return the value
     */
    @Override
    public float getDensity() {
        long settingsVa = va();
        float result = getDensity(settingsVa);

        return result;
    }

    /**
     * Return the material. The settings are unaffected. (native field:
     * mMaterial)
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if none
     */
    @Override
    public ConstPhysicsMaterial getMaterial() {
        long settingsVa = va();
        long materialVa = getMaterial(settingsVa);
        ConstPhysicsMaterial result;
        if (materialVa == 0L) {
            result = null;
        } else {
            result = new PhysicsMaterial(materialVa);
        }

        return result;
    }
    // *************************************************************************
    // native private methods

    native private float getDensity(long settingsVa);

    native private long getMaterial(long settingsVa);

    native private static void setDensity(long settingsVa, float density);

    native private static void setMaterial(long settingsVa, long materialVa);
}
