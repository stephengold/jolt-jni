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
 * Settings used to construct a {@code ScaledShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ScaledShapeSettings extends DecoratedShapeSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a settings based on the specified shape and scale factors.
     *
     * @param baseShape the unscaled base shape (not null)
     * @param scaleFactors the desired scale factors (not null)
     */
    public ScaledShapeSettings(Shape baseShape, Vec3Arg scaleFactors) {
        long baseShapeVa = baseShape.va();
        float scaleX = scaleFactors.getX();
        float scaleY = scaleFactors.getY();
        float scaleZ = scaleFactors.getZ();
        long settingsVa = createScaledShapeSettingsFromShape(
                baseShapeVa, scaleX, scaleY, scaleZ);
        setVirtualAddress(settingsVa, null); // no owner due to ref counting
    }

    /**
     * Instantiate a settings based on the specified shape reference and scale
     * factors.
     *
     * @param baseShapeRef a reference to the unscaled base shape (not null)
     * @param scaleFactors the desired scale factors (not null)
     */
    public ScaledShapeSettings(ShapeRefC baseShapeRef, Vec3Arg scaleFactors) {
        long baseShapeRefVa = baseShapeRef.va();
        float scaleX = scaleFactors.getX();
        float scaleY = scaleFactors.getY();
        float scaleZ = scaleFactors.getZ();
        long settingsVa = createScaledShapeSettingsFromRef(
                baseShapeRefVa, scaleX, scaleY, scaleZ);
        setVirtualAddress(settingsVa, null); // no owner due to ref counting
    }

    /**
     * Instantiate a settings based on the specified settings and scale factors.
     *
     * @param baseSettings the unscaled base shape settings (not null)
     * @param scaleFactors the desired scale factors (not null)
     */
    public ScaledShapeSettings(
            ShapeSettings baseSettings, Vec3Arg scaleFactors) {
        long baseSettingsVa = baseSettings.va();
        float scaleX = scaleFactors.getX();
        float scaleY = scaleFactors.getY();
        float scaleZ = scaleFactors.getZ();
        long settingsVa = createScaledShapeSettingsFromSettings(
                baseSettingsVa, scaleX, scaleY, scaleZ);
        setVirtualAddress(settingsVa, null); // no owner due to ref counting
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the shape's scale factors.
     *
     * @return a new scaling vector
     */
    public Vec3 getScale() {
        long scaledVa = va();
        float scaleX = getScaleX(scaledVa);
        float scaleY = getScaleY(scaledVa);
        float scaleZ = getScaleZ(scaledVa);
        Vec3 result = new Vec3(scaleX, scaleY, scaleZ);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createScaledShapeSettingsFromRef(
            long baseShapeRefVa, float scaleX, float scaleY, float scaleZ);

    native private static long createScaledShapeSettingsFromSettings(
            long baseSettingsVa, float scaleX, float scaleY, float scaleZ);

    native private static long createScaledShapeSettingsFromShape(
            long baseShapeVa, float scaleX, float scaleY, float scaleZ);

    native private static float getScaleX(long scaledVa);

    native private static float getScaleY(long scaledVa);

    native private static float getScaleZ(long scaledVa);
}
