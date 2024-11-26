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
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    HeightFieldShapeSettings(long settingsVa) {
        super(settingsVa);
        setSubType(EShapeSubType.HeightField);
    }

    /**
     * Instantiate settings for the specified samples.
     *
     * @param samples array of height values (not null, length&ge;4, unaffected)
     * @param offset (not null, unaffected)
     * @param scale (not null, unaffected)
     * @param sampleCount the number of height values along each edge
     * (&ge;2*blockSize)
     */
    public HeightFieldShapeSettings(
            float[] samples, Vec3Arg offset, Vec3Arg scale, int sampleCount) {
        this(samples, offset, scale, sampleCount, null);
    }

    /**
     * Instantiate settings for the specified samples.
     *
     * @param samples array of height values (not null, length&ge;4, unaffected)
     * @param offset (not null, unaffected)
     * @param scale (not null, unaffected)
     * @param sampleCount the number of height values along each edge
     * (&ge;2*blockSize)
     * @param materialIndices a material index for each sample (default=null)
     */
    public HeightFieldShapeSettings(float[] samples, Vec3Arg offset,
            Vec3Arg scale, int sampleCount, byte[] materialIndices) {
        this(samples, offset, scale, sampleCount, materialIndices,
                new PhysicsMaterialList());
    }

    /**
     * Instantiate settings for the specified samples.
     *
     * @param samples array of height values (not null, length&ge;4, unaffected)
     * @param offset (not null, unaffected)
     * @param scale (not null, unaffected)
     * @param sampleCount the number of height values along each edge
     * (&ge;2*blockSize)
     * @param materialIndices a material index for each sample (default=null)
     * @param materialList the list of materials
     */
    public HeightFieldShapeSettings(
            float[] samples, Vec3Arg offset, Vec3Arg scale, int sampleCount,
            byte[] materialIndices, PhysicsMaterialList materialList) {
        float offsetX = offset.getX();
        float offsetY = offset.getY();
        float offsetZ = offset.getZ();
        float scaleX = scale.getX();
        float scaleY = scale.getY();
        float scaleZ = scale.getZ();
        long listVa = materialList.va();
        long settingsVa = createSettingsFromArray(
                samples, offsetX, offsetY, offsetZ, scaleX, scaleY, scaleZ,
                sampleCount, materialIndices, listVa);
        setVirtualAddress(settingsVa, null); // not owner due to ref counting
        setSubType(EShapeSubType.HeightField);
    }

    /**
     * Instantiate settings for the specified samples.
     *
     * @param samples array of height values (not null, capacity&ge;4,
     * unaffected)
     * @param offset (not null, unaffected)
     * @param scale (not null, unaffected)
     * @param sampleCount the number of height values along each edge
     * (&ge;2*blockSize)
     */
    public HeightFieldShapeSettings(FloatBuffer samples, Vec3Arg offset,
            Vec3Arg scale, int sampleCount) {
        this(samples, offset, scale, sampleCount, null);
    }

    /**
     * Instantiate settings for the specified samples.
     *
     * @param samples array of height values (not null, capacity&ge;4,
     * unaffected)
     * @param offset (not null, unaffected)
     * @param scale (not null, unaffected)
     * @param sampleCount the number of height values along each edge
     * (&ge;2*blockSize)
     * @param materialIndices a material index for each sample (default=null)
     */
    public HeightFieldShapeSettings(FloatBuffer samples, Vec3Arg offset,
            Vec3Arg scale, int sampleCount, byte[] materialIndices) {
        this(samples, offset, scale, sampleCount, materialIndices,
                new PhysicsMaterialList());
    }

    /**
     * Instantiate settings for the specified samples.
     *
     * @param samples array of height values (not null, capacity&ge;4,
     * unaffected)
     * @param offset (not null, unaffected)
     * @param scale (not null, unaffected)
     * @param sampleCount the number of height values along each edge
     * (&ge;2*blockSize)
     * @param materialIndices a material index for each sample (default=null)
     * @param materialList the list of materials
     */
    public HeightFieldShapeSettings(
            FloatBuffer samples, Vec3Arg offset, Vec3Arg scale, int sampleCount,
            byte[] materialIndices, PhysicsMaterialList materialList) {
        float offsetX = offset.getX();
        float offsetY = offset.getY();
        float offsetZ = offset.getZ();
        float scaleX = scale.getX();
        float scaleY = scale.getY();
        float scaleZ = scale.getZ();
        long listVa = materialList.va();
        long settingsVa = createSettingsFromBuffer(
                samples, offsetX, offsetY, offsetZ, scaleX, scaleY, scaleZ,
                sampleCount, materialIndices, listVa);
        setVirtualAddress(settingsVa, null); // not owner due to ref counting
        setSubType(EShapeSubType.HeightField);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the cosine of the active-edge threshold angle. The settings are
     * unaffected. (native attribute: mActiveEdgeCosThresholdAngle)
     *
     * @return the cosine
     */
    public float getActiveEdgeCosThresholdAngle() {
        long settingsVa = va();
        float result = getActiveEdgeCosThresholdAngle(settingsVa);

        return result;
    }

    /**
     * Return the number of bits per sample to use during compression. The
     * settings are unaffected. (native attribute: mBitsPerSample)
     *
     * @return the number of bits (&ge;1, &le;8)
     */
    public int getBitsPerSample() {
        long settingsVa = va();
        int result = getBitsPerSample(settingsVa);

        return result;
    }

    /**
     * Return the block size. The settings are unaffected. (native attribute:
     * mBlockSize)
     * <p>
     * For culling purposes, the height field is organized in blocks of
     * {@code 2 * mBlockSize * mBlockSize} triangles.
     * <p>
     * Sensible values range from 2 to 8.
     *
     * @return the block size (in rows)
     */
    public int getBlockSize() {
        long settingsVa = va();
        int result = getBlockSize(settingsVa);

        return result;
    }

    /**
     * Return the artificial maximum height. The settings are unaffected.
     * (native attribute: mMaxHeightValue)
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
     * (native attribute: mMinHeightValue)
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
     * (native attribute: mOffset)
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
     * Count the samples. The settings are unaffected. (native attribute:
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
     * Return the scale factors. The settings are unaffected. (native attribute:
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
     * Alter the active-edge threshold angle. (native attribute:
     * mActiveEdgeCosThresholdAngle)
     *
     * @param cosine the cosine of the desired angle (default=0.996195)
     */
    public void setActiveEdgeCosThresholdAngle(float cosine) {
        long settingsVa = va();
        setActiveEdgeCosThresholdAngle(settingsVa, cosine);
    }

    /**
     * Alter the number of bits per sample to use during compression. (native
     * attribute: mBitsPerSample)
     *
     * @param numBits the number of bits (&ge;1, &le;8, default=8)
     */
    public void setBitsPerSample(int numBits) {
        long settingsVa = va();
        setBitsPerSample(settingsVa, numBits);
    }

    /**
     * Alter the block size. (native attribute: mBlockSize)
     * <p>
     * For culling purposes, the height field is organized in blocks of
     * {@code 2 * mBlockSize * mBlockSize} triangles.
     * <p>
     * Sensible values range from 2 to 8.
     *
     * @param numRows the desired block size (in rows, default=2)
     */
    public void setBlockSize(int numRows) {
        long settingsVa = va();
        setBlockSize(settingsVa, numRows);
    }

    /**
     * Alter the artificial maximum height. (native attribute: mMaxHeightValue)
     *
     * @param maxHeight the desired height value (default=-MAX_VALUE)
     */
    public void setMaxHeightValue(float maxHeight) {
        long settingsVa = va();
        setMaxHeightValue(settingsVa, maxHeight);
    }

    /**
     * Alter the artificial minimum height. (native attribute: mMinHeightValue)
     *
     * @param minHeight the desired height value (default=MAX_VALUE)
     */
    public void setMinHeightValue(float minHeight) {
        long settingsVa = va();
        setMinHeightValue(settingsVa, minHeight);
    }

    /**
     * Alter the offset of the first sample. (native attribute: mOffset)
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
     * Alter the scale factors. (native attribute: mScale)
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

    native private static long createSettingsFromArray(
            float[] samples, float offsetX, float offsetY, float offsetZ,
            float scaleX, float scaleY, float scaleZ, int sampleCount,
            byte[] materialIndices, long listVa);

    native private static long createSettingsFromBuffer(
            FloatBuffer samples, float offsetX, float offsetY, float offsetZ,
            float scaleX, float scaleY, float scaleZ, int sampleCount,
            byte[] materialIndices, long listVa);

    native private static float getActiveEdgeCosThresholdAngle(long settingsVa);

    native private static int getBitsPerSample(long settingsVa);

    native private static int getBlockSize(long settingsVa);

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

    native private static void setBitsPerSample(long settingsVa, int numBits);

    native private static void setBlockSize(long settingsVa, int numRows);

    native private static void setMaxHeightValue(
            long settingsVa, float maxHeight);

    native private static void setMinHeightValue(
            long settingsVa, float minHeight);

    native private static void setOffset(
            long settingsVa, float dx, float dy, float dz);

    native private static void setScale(
            long settingsVa, float sx, float sy, float sz);
}
