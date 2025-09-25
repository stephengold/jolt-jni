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

/**
 * Settings used to construct an {@code OffsetCenterOfMassShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class OffsetCenterOfMassShapeSettings extends DecoratedShapeSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param ocomssVa the virtual address of the native object to assign (not
     * zero)
     */
    OffsetCenterOfMassShapeSettings(long ocomssVa) {
        setVirtualAddressAsCoOwner(ocomssVa, EShapeSubType.OffsetCenterOfMass);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public OffsetCenterOfMassShapeSettings(
            OffsetCenterOfMassShapeSettings original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddressAsCoOwner(copyVa, EShapeSubType.OffsetCenterOfMass);
    }

    /**
     * Instantiate a settings object with the specified offset and base shape.
     *
     * @param offset (not null, unaffected)
     * @param baseShape the desired base shape (not null)
     */
    public OffsetCenterOfMassShapeSettings(
            Vec3Arg offset, ConstShape baseShape) {
        float offsetX = offset.getX();
        float offsetY = offset.getY();
        float offsetZ = offset.getZ();
        long baseShapeVa = baseShape.targetVa();
        long ocomssVa = createSettingsFromShape(
                offsetX, offsetY, offsetZ, baseShapeVa);
        setVirtualAddressAsCoOwner(ocomssVa, EShapeSubType.OffsetCenterOfMass);
    }

    /**
     * Instantiate a settings object with the specified offset and base-shape
     * settings.
     *
     * @param offset (not null, unaffected)
     * @param baseShapeSettings settings to create the base shape (not null)
     */
    public OffsetCenterOfMassShapeSettings(
            Vec3Arg offset, ConstShapeSettings baseShapeSettings) {
        float offsetX = offset.getX();
        float offsetY = offset.getY();
        float offsetZ = offset.getZ();
        long baseShapeSettingsVa = baseShapeSettings.targetVa();
        long ocomssVa = createSettingsFromSettings(
                offsetX, offsetY, offsetZ, baseShapeSettingsVa);
        setVirtualAddressAsCoOwner(ocomssVa, EShapeSubType.OffsetCenterOfMass);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the offset relative to the base shape. The settings are unaffected.
     * (native attribute: mOffset)
     *
     * @return a new, mutable offset vector
     */
    public Vec3 getOffset() {
        long ocomssVa = va();
        float offsetX = getOffsetX(ocomssVa);
        float offsetY = getOffsetY(ocomssVa);
        float offsetZ = getOffsetZ(ocomssVa);
        Vec3 result = new Vec3(offsetX, offsetY, offsetZ);

        return result;
    }

    /**
     * Alter the offset relative to the base shape. (native attribute: mOffset)
     *
     * @param offset the desired offset vector (not null, unaffected,
     * default=(0,0,0))
     */
    public void setOffset(Vec3Arg offset) {
        long ocomssVa = va();
        float offsetX = offset.getX();
        float offsetY = offset.getY();
        float offsetZ = offset.getZ();
        setOffset(ocomssVa, offsetX, offsetY, offsetZ);
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createSettingsFromSettings(float offsetX,
            float offsetY, float offsetZ, long baseShapeSettingsVa);

    native private static long createSettingsFromShape(
            float offsetX, float offsetY, float offsetZ, long baseShapeVa);

    native private static float getOffsetX(long ocomssVa);

    native private static float getOffsetY(long ocomssVa);

    native private static float getOffsetZ(long ocomssVa);

    native private static void setOffset(
            long ocomssVa, float offsetX, float offsetY, float offsetZ);
}
