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
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.ConstShapeSettings;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

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
    public ScaledShapeSettings(ConstShape baseShape, Vec3Arg scaleFactors) {
        long baseShapeVa = baseShape.targetVa();
        float scaleX = scaleFactors.getX();
        float scaleY = scaleFactors.getY();
        float scaleZ = scaleFactors.getZ();
        long settingsVa = createScaledShapeSettingsFromShape(
                baseShapeVa, scaleX, scaleY, scaleZ);
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.Scaled);
    }

    /**
     * Instantiate a settings based on the specified settings and scale factors.
     *
     * @param baseSettings the unscaled base shape settings (not null)
     * @param scaleFactors the desired scale factors (not null)
     */
    public ScaledShapeSettings(
            ConstShapeSettings baseSettings, Vec3Arg scaleFactors) {
        long baseSettingsVa = baseSettings.targetVa();
        float scaleX = scaleFactors.getX();
        float scaleY = scaleFactors.getY();
        float scaleZ = scaleFactors.getZ();
        long settingsVa = createScaledShapeSettingsFromSettings(
                baseSettingsVa, scaleX, scaleY, scaleZ);
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.Scaled);
    }

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    ScaledShapeSettings(long settingsVa) {
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.Scaled);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public ScaledShapeSettings(ScaledShapeSettings original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddressAsCoOwner(copyVa, EShapeSubType.Scaled);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the scale factors. The settings are unaffected.
     *
     * @return a new scaling vector
     */
    public Vec3 getScale() {
        long scaledVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getScale(scaledVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createScaledShapeSettingsFromSettings(
            long baseSettingsVa, float scaleX, float scaleY, float scaleZ);

    native private static long createScaledShapeSettingsFromShape(
            long baseShapeVa, float scaleX, float scaleY, float scaleZ);

    native private static void getScale(long scaledVa, FloatBuffer storeFloats);
}
