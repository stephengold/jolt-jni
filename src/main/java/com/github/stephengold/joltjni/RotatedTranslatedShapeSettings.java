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
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * Settings used to construct a {@code RotatedTranslatedShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class RotatedTranslatedShapeSettings extends DecoratedShapeSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param rtssVa the virtual address of the native object to assign (not
     * zero)
     */
    RotatedTranslatedShapeSettings(long rtssVa) {
        setVirtualAddressAsCoOwner(rtssVa, EShapeSubType.RotatedTranslated);
    }

    /**
     * Instantiate a settings object with the specified rotation and base shape.
     *
     * @param rotation (not null, not zero, unaffected)
     * @param baseShape the base shape (not null, unaffected)
     */
    public RotatedTranslatedShapeSettings(
            QuatArg rotation, ConstShape baseShape) {
        float offsetX = 0f;
        float offsetY = 0f;
        float offsetZ = 0f;
        float rotW = rotation.getW();
        float rotX = rotation.getX();
        float rotY = rotation.getY();
        float rotZ = rotation.getZ();
        long baseShapeVa = baseShape.targetVa();
        long rtssVa = createSettingsFromShape(offsetX, offsetY,
                offsetZ, rotX, rotY, rotZ, rotW, baseShapeVa);
        setVirtualAddressAsCoOwner(rtssVa, EShapeSubType.RotatedTranslated);
    }

    /**
     * Instantiate a settings object with the specified rotation and base
     * settings.
     *
     * @param rotation (not null, not zero, unaffected)
     * @param baseShapeSettings settings for the base shape (not null)
     */
    public RotatedTranslatedShapeSettings(QuatArg rotation,
            ConstShapeSettings baseShapeSettings) {
        float offsetX = 0f;
        float offsetY = 0f;
        float offsetZ = 0f;
        float rotW = rotation.getW();
        float rotX = rotation.getX();
        float rotY = rotation.getY();
        float rotZ = rotation.getZ();
        long baseShapeSettingsVa = baseShapeSettings.targetVa();
        long rtssVa = createSettingsFromShapeSettings(offsetX, offsetY,
                offsetZ, rotX, rotY, rotZ, rotW, baseShapeSettingsVa);
        setVirtualAddressAsCoOwner(rtssVa, EShapeSubType.RotatedTranslated);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public RotatedTranslatedShapeSettings(
            RotatedTranslatedShapeSettings original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddressAsCoOwner(copyVa, EShapeSubType.RotatedTranslated);
    }

    /**
     * Instantiate a settings object with the specified offset, rotation, and
     * base shape.
     *
     * @param offset (not null, unaffected)
     * @param rotation (not null, not zero, unaffected)
     * @param baseShape the base shape (not null, unaffected)
     */
    public RotatedTranslatedShapeSettings(
            Vec3Arg offset, QuatArg rotation, ConstShape baseShape) {
        float offsetX = offset.getX();
        float offsetY = offset.getY();
        float offsetZ = offset.getZ();
        float rotW = rotation.getW();
        float rotX = rotation.getX();
        float rotY = rotation.getY();
        float rotZ = rotation.getZ();
        long baseShapeVa = baseShape.targetVa();
        long rtssVa = createSettingsFromShape(offsetX, offsetY,
                offsetZ, rotX, rotY, rotZ, rotW, baseShapeVa);
        setVirtualAddressAsCoOwner(rtssVa, EShapeSubType.RotatedTranslated);
    }

    /**
     * Instantiate a settings object with the specified offset, rotation, and
     * base settings.
     *
     * @param offset (not null, unaffected)
     * @param rotation (not null, not zero, unaffected)
     * @param baseShapeSettings settings for the base shape (not null)
     */
    public RotatedTranslatedShapeSettings(Vec3Arg offset, QuatArg rotation,
            ConstShapeSettings baseShapeSettings) {
        float offsetX = offset.getX();
        float offsetY = offset.getY();
        float offsetZ = offset.getZ();
        float rotW = rotation.getW();
        float rotX = rotation.getX();
        float rotY = rotation.getY();
        float rotZ = rotation.getZ();
        long baseShapeSettingsVa = baseShapeSettings.targetVa();
        long rtssVa = createSettingsFromShapeSettings(offsetX, offsetY,
                offsetZ, rotX, rotY, rotZ, rotW, baseShapeSettingsVa);
        setVirtualAddressAsCoOwner(rtssVa, EShapeSubType.RotatedTranslated);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the offset relative to the base shape. The settings are unaffected.
     * (native attribute: mPosition)
     *
     * @return a new, mutable offset vector
     */
    public Vec3 getPosition() {
        long rtssVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getPosition(rtssVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the rotation relative to the base shape. The settings are
     * unaffected. (native attribute: mRotation)
     *
     * @return a new, mutable rotation quaternion
     */
    public Quat getRotation() {
        long rtssVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getRotation(rtssVa, storeFloats);
        Quat result = new Quat(storeFloats);

        return result;
    }

    /**
     * Alter the offset relative to the base shape. (native attribute:
     * mPosition)
     *
     * @param offset the desired offset vector (not null, unaffected,
     * default=(0,0,0))
     */
    public void setPosition(Vec3Arg offset) {
        long rtssVa = va();
        float offsetX = offset.getX();
        float offsetY = offset.getY();
        float offsetZ = offset.getZ();
        setPosition(rtssVa, offsetX, offsetY, offsetZ);
    }

    /**
     * Alter the rotation relative to the base shape. (native attribute:
     * mRotation)
     *
     * @param rotation the desired rotation quaternion (not null, unaffected)
     */
    public void setRotation(QuatArg rotation) {
        long rtssVa = va();
        float rotW = rotation.getW();
        float rotX = rotation.getX();
        float rotY = rotation.getY();
        float rotZ = rotation.getZ();
        setRotation(rtssVa, rotX, rotY, rotZ, rotW);
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createSettingsFromShape(
            float offsetX, float offsetY, float offsetZ, float rotX, float rotY,
            float rotZ, float rotW, long baseShapeVa);

    native private static long createSettingsFromShapeSettings(
            float offsetX, float offsetY, float offsetZ, float rotX, float rotY,
            float rotZ, float rotW, long baseShapeSettingsVa);

    native private static void getPosition(
            long rtssVa, FloatBuffer storeFloats);

    native private static void getRotation(
            long rtssVa, FloatBuffer storeFloats);

    native private static void setPosition(
            long rtssVa, float offsetX, float offsetY, float offsetZ);

    native private static void setRotation(
            long rtssVa, float rotX, float rotY, float rotZ, float rotW);
}
