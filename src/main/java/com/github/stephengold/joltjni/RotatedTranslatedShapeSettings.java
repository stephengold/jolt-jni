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
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Settings used to construct a {@code RotatedTranslatedShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class RotatedTranslatedShapeSettings extends DecoratedShapeSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param virtualAddress the virtual address of the native object to assign
     * (not zero)
     */
    RotatedTranslatedShapeSettings(long virtualAddress) {
        super(virtualAddress);
        setSubType(EShapeSubType.RotatedTranslated);
    }

    /**
     * Instantiate a settings object with the specified offset, rotation, and
     * base shape.
     *
     * @param offset (not null, unaffected)
     * @param rotation (not null, not zero, unaffected)
     * @param baseShapeRef a reference to the base shape (not null)
     */
    public RotatedTranslatedShapeSettings(
            Vec3Arg offset, QuatArg rotation, ShapeRefC baseShapeRef) {
        float offsetX = offset.getX();
        float offsetY = offset.getY();
        float offsetZ = offset.getZ();
        float rotW = rotation.getW();
        float rotX = rotation.getX();
        float rotY = rotation.getY();
        float rotZ = rotation.getZ();
        long baseShapeRefVa = baseShapeRef.va();
        long scaledShapeVa = createSettingsFromShapeRef(offsetX, offsetY,
                offsetZ, rotX, rotY, rotZ, rotW, baseShapeRefVa);
        setVirtualAddress(scaledShapeVa, null); // no owner due to ref counting
        setSubType(EShapeSubType.RotatedTranslated);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the offset relative to the base shape. The settings are unaffected.
     * (native field=mPosition)
     *
     * @return a new, mutable offset vector
     */
    public Vec3 getPosition() {
        long rtsVa = va();
        float offsetX = getPositionX(rtsVa);
        float offsetY = getPositionY(rtsVa);
        float offsetZ = getPositionZ(rtsVa);
        Vec3 result = new Vec3(offsetX, offsetY, offsetZ);

        return result;
    }

    /**
     * Copy the rotation relative to the base shape. The settings are
     * unaffected. (native field=mRotation)
     *
     * @return a new, mutable rotation quaternion
     */
    public Quat getRotation() {
        long rtsVa = va();
        float rotW = getRotationW(rtsVa);
        float rotX = getRotationX(rtsVa);
        float rotY = getRotationY(rtsVa);
        float rotZ = getRotationZ(rtsVa);
        Quat result = new Quat(rotX, rotY, rotZ, rotW);

        return result;
    }

    /**
     * Alter the offset relative to the base shape. (native field=mPosition)
     *
     * @param offset the desired offset vector (not null, unaffected,
     * default=(0,0,0))
     */
    public void setPosition(Vec3Arg offset) {
        long rtsVa = va();
        float offsetX = offset.getX();
        float offsetY = offset.getY();
        float offsetZ = offset.getZ();
        setPosition(rtsVa, offsetX, offsetY, offsetZ);
    }

    /**
     * Alter the rotation relative to the base shape. (native field=mRotation)
     *
     * @param rotation the desired rotation quaternion (not null, unaffected)
     */
    public void setRotation(QuatArg rotation) {
        long rtsVa = va();
        float rotW = rotation.getW();
        float rotX = rotation.getX();
        float rotY = rotation.getY();
        float rotZ = rotation.getZ();
        setRotation(rtsVa, rotX, rotY, rotZ, rotW);
    }
    // *************************************************************************
    // native private methods

    native private static long createSettingsFromShapeRef(
            float offsetX, float offsetY, float offsetZ, float rotX, float rotY,
            float rotZ, float rotW, long baseShapeRefVa);

    native private static float getPositionX(long rtsVa);

    native private static float getPositionY(long rtsVa);

    native private static float getPositionZ(long rtsVa);

    native private static float getRotationW(long rtsVa);

    native private static float getRotationX(long rtsVa);

    native private static float getRotationY(long rtsVa);

    native private static float getRotationZ(long rtsVa);

    native private static void setPosition(
            long rtsVa, float offsetX, float offsetY, float offsetZ);

    native private static void setRotation(
            long rtsVa, float rotX, float rotY, float rotZ, float rotW);
}
