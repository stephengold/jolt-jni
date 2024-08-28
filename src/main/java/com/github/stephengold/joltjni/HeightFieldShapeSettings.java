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
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * Settings used to construct a {@code HeightFieldShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class HeightFieldShapeSettings extends ShapeSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param virtualAddress the virtual address of the native object to assign
     * (not zero)
     */
    HeightFieldShapeSettings(long virtualAddress) {
        super(virtualAddress);
        setSubType(EShapeSubType.HeightField);
    }

    /**
     * Instantiate settings for the specified samples.
     *
     * @param samples array of height values (not null, capacity&ge;4,
     * unaffected)
     * @param offset (not null, unaffected)
     * @param scale (not null, unaffected)
     * @param sampleCount the number of height values along each edge (&ge;2)
     */
    public HeightFieldShapeSettings(FloatBuffer samples, Vec3Arg offset,
            Vec3Arg scale, int sampleCount) {
        float offsetX = offset.getX();
        float offsetY = offset.getY();
        float offsetZ = offset.getZ();
        float scaleX = scale.getX();
        float scaleY = scale.getY();
        float scaleZ = scale.getZ();
        long settingsVa = createHeightFieldShapeSettings(samples, offsetX,
                offsetY, offsetZ, scaleX, scaleY, scaleZ, sampleCount);
        setVirtualAddress(settingsVa, null); // not owner due to ref counting
        setSubType(EShapeSubType.HeightField);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the cosine of the active edge threshold angle. The settings are
     * unaffected. (native field: mActiveEdgeCosThresholdAngle)
     *
     * @return the cosine
     */
    public float getActiveEdgeCosThresholdAngle() {
        long settingsVa = va();
        float result = getActiveEdgeCosThresholdAngle(settingsVa);

        return result;
    }

    /**
     * Return the artificial maximum height. The settings are unaffected.
     * (native field: mMaxHeightValue)
     *
     * @return the height value
     */
    public float getMaxHeightValue() {
        long settingsVa = va();
        float result = getMaxHeightValue(settingsVa);

        return result;
    }

    /**
     * Return the artificial minimum height. The settings are unaffected.
     * (native field: mMinHeightValue)
     *
     * @return the height value
     */
    public float getMinHeightValue() {
        long settingsVa = va();
        float result = getMinHeightValue(settingsVa);

        return result;
    }

    /**
     * Return the offset of the first sample. The settings are unaffected.
     * (native field: mOffset)
     *
     * @return a new offset vector
     */
    public Vec3 getOffset() {
        long settingsVa = va();
        float dx = getOffsetX(settingsVa);
        float dy = getOffsetY(settingsVa);
        float dz = getOffsetZ(settingsVa);
        Vec3 result = new Vec3(dx, dy, dz);

        return result;
    }

    /**
     * Count the samples. The settings are unaffected. (native field:
     * mSampleCount)
     *
     * @return the number of samples (&ge;0)
     */
    public int getSampleCount() {
        long settingsVa = va();
        int result = getSampleCount(settingsVa);

        return result;
    }

    /**
     * Return the scale factors. The settings are unaffected. (native field:
     * mScale)
     *
     * @return a new scaling vector
     */
    public Vec3 getScale() {
        long settingsVa = va();
        float sx = getScaleX(settingsVa);
        float sy = getScaleY(settingsVa);
        float sz = getScaleZ(settingsVa);
        Vec3 result = new Vec3(sx, sy, sz);

        return result;
    }

    /**
     * Alter the active edge threshold angle. (native field:
     * mActiveEdgeCosThresholdAngle)
     *
     * @param cosine the cosine of the desired angle (default=0.996195)
     */
    public void setActiveEdgeCosThresholdAngle(float cosine) {
        long settingsVa = va();
        setActiveEdgeCosThresholdAngle(settingsVa, cosine);
    }

    /**
     * Alter the artificial maximum height. (native field: mMaxHeightValue)
     *
     * @param maxHeight the desired height value (default=-MAX_VALUE)
     */
    public void setMaxHeightValue(float maxHeight) {
        long settingsVa = va();
        setMaxHeightValue(settingsVa, maxHeight);
    }

    /**
     * Alter the artificial minimum height. (native field: mMinHeightValue)
     *
     * @param minHeight the desired height value (default=MAX_VALUE)
     */
    public void setMinHeightValue(float minHeight) {
        long settingsVa = va();
        setMinHeightValue(settingsVa, minHeight);
    }

    /**
     * Alter the offset of the first sample. (native field: mOffset)
     *
     * @param offset the desired offset vector (not null, unaffected,
     * default=(0,0,0))
     */
    public void setOffset(Vec3Arg offset) {
        long settingsVa = va();
        float dx = offset.getX();
        float dy = offset.getY();
        float dz = offset.getZ();
        setOffset(settingsVa, dx, dy, dz);
    }

    /**
     * Alter the scale factors. (native field: mScale)
     *
     * @param scale the desired scale factors (not null, unaffected,
     * default=(1,1,1))
     */
    public void setScale(Vec3Arg scale) {
        long settingsVa = va();
        float sx = scale.getX();
        float sy = scale.getY();
        float sz = scale.getZ();
        setScale(settingsVa, sx, sy, sz);
    }
    // *************************************************************************
    // native private methods

    native private static long createHeightFieldShapeSettings(
            FloatBuffer samples, float offsetX, float offsetY, float offsetZ,
            float scaleX, float scaleY, float scaleZ, int sampleCount);

    native private static float getActiveEdgeCosThresholdAngle(long settingsVa);

    native private static float getMaxHeightValue(long settingsVa);

    native private static float getMinHeightValue(long setingsVa);

    native private static float getOffsetX(long settingsVa);

    native private static float getOffsetY(long settingsVa);

    native private static float getOffsetZ(long settingsVa);

    native private static int getSampleCount(long settingsVa);

    native private static float getScaleX(long settingsVa);

    native private static float getScaleY(long settingsVa);

    native private static float getScaleZ(long settingsVa);

    native private static void setActiveEdgeCosThresholdAngle(
            long settingsVa, float cosine);

    native private static void setMaxHeightValue(
            long settingsVa, float maxHeight);

    native private static void setMinHeightValue(
            long settingsVa, float minHeight);

    native private static void setOffset(
            long settingsVa, float dx, float dy, float dz);

    native private static void setScale(
            long settingsVa, float sx, float sy, float sz);
}
