/*
Copyright (c) 2024-2026 Stephen Gold

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
import com.github.stephengold.joltjni.readonly.ConstHeightFieldShapeSettings;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * Settings used to construct a {@code HeightFieldShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class HeightFieldShapeSettings
        extends ShapeSettings
        implements ConstHeightFieldShapeSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate the default settings.
     */
    public HeightFieldShapeSettings() {
        long settingsVa = createDefault();
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.HeightField);
    }

    /**
     * Instantiate settings for the specified samples.
     *
     * @param samples array of height values (not {@code null}, length&ge;4,
     * unaffected)
     * @param offsetX the desired X offset
     * @param offsetY the desired Y offset
     * @param offsetZ the desired Z offset
     * @param scaleX the desired scale factor on the local X axis
     * @param scaleY the desired scale factor on the local Y axis
     * @param scaleZ the desired scale factor on the local Z axis
     * @param sampleCount the number of height values along each edge
     * (&ge;2*blockSize)
     * @param materialIndices a material index for each sample (default=null)
     * @param materialList the list of materials
     */
    public HeightFieldShapeSettings(float[] samples,
            float offsetX, float offsetY, float offsetZ,
            float scaleX, float scaleY, float scaleZ, int sampleCount,
            byte[] materialIndices, PhysicsMaterialList materialList) {
        long listVa = materialList.va();
        long settingsVa = createSettingsFromArray(
                samples, offsetX, offsetY, offsetZ, scaleX, scaleY, scaleZ,
                sampleCount, materialIndices, listVa);
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.HeightField);
    }

    /**
     * Instantiate settings for the specified samples.
     *
     * @param samples array of height values (not {@code null}, length&ge;4,
     * unaffected)
     * @param offset (not {@code null}, unaffected)
     * @param scale (not {@code null}, unaffected)
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
     * @param samples array of height values (not {@code null}, length&ge;4,
     * unaffected)
     * @param offset (not {@code null}, unaffected)
     * @param scale (not {@code null}, unaffected)
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
     * @param samples array of height values (not {@code null}, length&ge;4,
     * unaffected)
     * @param offset (not {@code null}, unaffected)
     * @param scale (not {@code null}, unaffected)
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
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.HeightField);
    }

    /**
     * Instantiate settings for the specified samples.
     *
     * @param samples array of height values (not {@code null}, capacity&ge;4,
     * unaffected)
     * @param offsetX the desired X offset
     * @param offsetY the desired Y offset
     * @param offsetZ the desired Z offset
     * @param scaleX the desired scale factor on the local X axis
     * @param scaleY the desired scale factor on the local Y axis
     * @param scaleZ the desired scale factor on the local Z axis
     * @param sampleCount the number of height values along each edge
     * (&ge;2*blockSize)
     */
    public HeightFieldShapeSettings(FloatBuffer samples,
            float offsetX, float offsetY, float offsetZ,
            float scaleX, float scaleY, float scaleZ, int sampleCount) {
        long settingsVa = createFromBufferNoMats(samples, offsetX, offsetY,
                offsetZ, scaleX, scaleY, scaleZ, sampleCount);
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.HeightField);
    }

    /**
     * Instantiate settings for the specified samples.
     *
     * @param samples array of height values (not {@code null}, capacity&ge;4,
     * unaffected)
     * @param offsetX the desired X offset
     * @param offsetY the desired Y offset
     * @param offsetZ the desired Z offset
     * @param scaleX the desired scale factor on the local X axis
     * @param scaleY the desired scale factor on the local Y axis
     * @param scaleZ the desired scale factor on the local Z axis
     * @param sampleCount the number of height values along each edge
     * (&ge;2*blockSize)
     * @param materialIndices a material index for each sample (default=null)
     * @param materialList the list of materials
     */
    public HeightFieldShapeSettings(FloatBuffer samples,
            float offsetX, float offsetY, float offsetZ,
            float scaleX, float scaleY, float scaleZ, int sampleCount,
            byte[] materialIndices, PhysicsMaterialList materialList) {
        long listVa = materialList.va();
        long settingsVa = createSettingsFromBuffer(
                samples, offsetX, offsetY, offsetZ, scaleX, scaleY, scaleZ,
                sampleCount, materialIndices, listVa);
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.HeightField);
    }

    /**
     * Instantiate settings for the specified samples.
     *
     * @param samples array of height values (not {@code null}, capacity&ge;4,
     * unaffected)
     * @param offset (not {@code null}, unaffected)
     * @param scale (not {@code null}, unaffected)
     * @param sampleCount the number of height values along each edge
     * (&ge;2*blockSize)
     */
    public HeightFieldShapeSettings(FloatBuffer samples, Vec3Arg offset,
            Vec3Arg scale, int sampleCount) {
        float offsetX = offset.getX();
        float offsetY = offset.getY();
        float offsetZ = offset.getZ();
        float scaleX = scale.getX();
        float scaleY = scale.getY();
        float scaleZ = scale.getZ();
        long settingsVa = createFromBufferNoMats(samples, offsetX, offsetY,
                offsetZ, scaleX, scaleY, scaleZ, sampleCount);
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.HeightField);
    }

    /**
     * Instantiate settings for the specified samples.
     *
     * @param samples array of height values (not {@code null}, capacity&ge;4,
     * unaffected)
     * @param offset (not {@code null}, unaffected)
     * @param scale (not {@code null}, unaffected)
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
     * @param samples array of height values (not {@code null}, capacity&ge;4,
     * unaffected)
     * @param offset (not {@code null}, unaffected)
     * @param scale (not {@code null}, unaffected)
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
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.HeightField);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public HeightFieldShapeSettings(ConstHeightFieldShapeSettings original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        setVirtualAddressAsCoOwner(copyVa, EShapeSubType.HeightField);
    }

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    HeightFieldShapeSettings(long settingsVa) {
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.HeightField);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the material list. (native attribute: mMaterials)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public PhysicsMaterialList getMaterials() {
        long settingsVa = va();
        long listVa = getMaterials(settingsVa);
        PhysicsMaterialList result = new PhysicsMaterialList(this, listVa);

        return result;
    }

    /**
     * Copy the argument to the current settings.
     *
     * @param source the settings to copy (not {@code null}, unaffected)
     */
    public void set(ConstHeightFieldShapeSettings source) {
        long targetVa = va();
        long sourceVa = source.targetVa();
        assign(targetVa, sourceVa);
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
     * @param numBits the number of bits (&ge;1, &le;16, default=8)
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
     * Alter the height of the specified sample.
     *
     * @param sampleIndex the array index (&ge;0, &lt;sampleCount^2)
     * @param height the desired height, or {@code cNoCollisionValue} for no
     * collision
     */
    public void setHeightSample(int sampleIndex, float height) {
        long settingsVa = va();
        setHeightForSi(settingsVa, sampleIndex, height);
    }

    /**
     * Alter the height of the specified sample.
     *
     * @param x the X index of the sample (&ge;0, &lt;sampleCount)
     * @param y the Y index of the sample (&ge;0, &lt;sampleCount)
     * @param height the desired height, or {@code cNoCollisionValue} for no
     * collision
     */
    public void setHeightSample(int x, int y, float height) {
        long settingsVa = va();
        setHeightForXY(settingsVa, x, y, height);
    }

    /**
     * Alter the material reference for the specified list index.
     *
     * @param materialIndex the list index at which to put the reference (&ge;0)
     * @param reference the reference to store (not {@code null})
     */
    public void setMaterial(int materialIndex, PhysicsMaterialRef reference) {
        long settingsVa = va();
        long refVa = reference.va();
        setMaterialForMi(settingsVa, materialIndex, refVa);
    }

    /**
     * Alter the material index of the specified square.
     *
     * @param squareIndex the index of the square (&ge;0, &lt;(sampleCount-1)^2)
     * @param materialIndex the desired material index
     */
    public void setMaterialIndex(int squareIndex, int materialIndex) {
        long settingsVa = va();
        setMaterialForSi(settingsVa, squareIndex, materialIndex);
    }

    /**
     * Alter the material index for the specified square.
     *
     * @param x the X index of the square (&ge;0, &lt;sampleCount-1)
     * @param y the Y index of the square (&ge;0, &lt;sampleCount-1)
     * @param materialIndex the desired index into the material list (&ge;0)
     */
    public void setMaterialIndex(int x, int y, int materialIndex) {
        long settingsVa = va();
        setMaterialIndexForXY(settingsVa, x, y, materialIndex);
    }

    /**
     * Replace the existing material list with a copy of the specified list.
     * (native attribute: mMaterials)
     *
     * @param list the desired material for each material index
     */
    public void setMaterials(PhysicsMaterialList list) {
        long settingsVa = va();
        long listVa = list.va();
        setMaterials(settingsVa, listVa);
    }

    /**
     * Alter the artificial minimum capacity for the created shape's material
     * list. (native attribute: mMaterialsCapacity)
     *
     * @param numRefs the desired minimum capacity (in refs, default=0)
     */
    public void setMaterialsCapacity(int numRefs) {
        long settingsVa = va();
        setMaterialsCapacity(settingsVa, numRefs);
    }

    /**
     * Alter the artificial maximum height. (native attribute: mMaxHeightValue)
     *
     * @param maxHeight the desired height value (default=-1e15)
     */
    public void setMaxHeightValue(float maxHeight) {
        long settingsVa = va();
        setMaxHeightValue(settingsVa, maxHeight);
    }

    /**
     * Alter the artificial minimum height. (native attribute: mMinHeightValue)
     *
     * @param minHeight the desired height value (default=1e15)
     */
    public void setMinHeightValue(float minHeight) {
        long settingsVa = va();
        setMinHeightValue(settingsVa, minHeight);
    }

    /**
     * Alter the offset of the first sample. (native attribute: mOffset)
     *
     * @param offset the desired offset vector (not {@code null}, unaffected,
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
     * Alter the number of samples along each dimension. (native attribute:
     * mSampleCount)
     *
     * @param sampleCount the desired number of samples (&ge;0, default=0)
     */
    public void setSampleCount(int sampleCount) {
        long settingsVa = va();
        setSampleCount(settingsVa, sampleCount);
    }

    /**
     * Alter the scale factors. (native attribute: mScale)
     *
     * @param scale the desired scale factors (not {@code null}, unaffected,
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
    // ConstHeightFieldShapeSettings methods

    /**
     * Calculate the minimum bits per sample that will provide the specified
     * error tolerance. The settings are unaffected.
     *
     * @param maxError the allowable tolerance
     * @return the (hypothetical) bits per sample
     */
    @Override
    public int calculateBitsPerSampleForError(float maxError) {
        long settingsVa = va();
        int result = calculateBitsPerSampleForError(settingsVa, maxError);

        return result;
    }

    /**
     * Count the height samples, including {@code cNoCollisionValue}. The
     * settings are unaffected.
     *
     * @return the actual number of samples in the array (&ge;0)
     */
    @Override
    public int countHeights() {
        long settingsVa = va();
        int result = countHeights(settingsVa);

        return result;
    }

    /**
     * Count the references in the material list, including null refs. The
     * settings are unaffected.
     *
     * @return the actual number of references in the list (&ge;0)
     */
    @Override
    public int countMaterials() {
        long settingsVa = va();
        int result = countMaterials(settingsVa);

        return result;
    }

    /**
     * Count the squares. The settings are unaffected.
     *
     * @return the actual number of material indices in the array (&ge;0)
     */
    @Override
    public int countSquares() {
        long settingsVa = va();
        int result = countSquares(settingsVa);

        return result;
    }

    /**
     * Calculate 3 quantization parameters of the current settings. The settings
     * are unaffected.
     *
     * @param storeMin storage for the minimum height (ignoring
     * {@code cNoCollisionValue}) or {@code MIN_VALUE} if no samples have
     * collision
     * @param storeMax storage for the maximum height (ignoring
     * {@code cNoCollisionValue}) or {@code MAX_VALUE} if no samples have
     * collision
     * @param storeQuantScale storage for the quantization scale factor
     */
    @Override
    public void determineMinAndMaxSample(
            float[] storeMin, float[] storeMax, float[] storeQuantScale) {
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        determineMinAndMaxSample(settingsVa, storeFloats);

        if (storeMin != null && storeMin.length > 0) {
            storeMin[0] = storeFloats.get(0);
        }
        if (storeMax != null && storeMax.length > 0) {
            storeMax[0] = storeFloats.get(1);
        }
        if (storeQuantScale != null && storeQuantScale.length > 0) {
            storeQuantScale[0] = storeFloats.get(2);
        }
    }

    /**
     * Return the cosine of the active-edge threshold angle. The settings are
     * unaffected. (native attribute: mActiveEdgeCosThresholdAngle)
     *
     * @return the cosine
     */
    @Override
    public float getActiveEdgeCosThresholdAngle() {
        long settingsVa = va();
        float result = getActiveEdgeCosThresholdAngle(settingsVa);

        return result;
    }

    /**
     * Return the number of bits per sample to use during compression. The
     * settings are unaffected. (native attribute: mBitsPerSample)
     *
     * @return the number of bits (&ge;1, &le;16)
     */
    @Override
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
    @Override
    public int getBlockSize() {
        long settingsVa = va();
        int result = getBlockSize(settingsVa);

        return result;
    }

    /**
     * Return the height of the specified sample. The settings are unaffected.
     *
     * @param sampleIndex the array index (&ge;0, &lt;sampleCount^2)
     * @return the sample height or {@code cNoCollisionValue}
     */
    @Override
    public float getHeightSample(int sampleIndex) {
        long settingsVa = va();
        float result = getHeightForSi(settingsVa, sampleIndex);

        return result;
    }

    /**
     * Return the height of the specified sample. The settings are unaffected.
     *
     * @param x the X index of the sample (&ge;0, &lt;sampleCount)
     * @param y the Y index of the sample (&ge;0, &lt;sampleCount)
     * @return the sample height or {@code cNoCollisionValue}
     */
    @Override
    public float getHeightSample(int x, int y) {
        long settingsVa = va();
        float result = getHeightForXY(settingsVa, x, y);

        return result;
    }

    /**
     * Return the material index for the specified square. The settings are
     * unaffected.
     *
     * @param squareIndex the index of the square (&ge;0, &lt;(sampleCount-1)^2)
     * @return an index into the material list (&ge;0)
     */
    @Override
    public int getMaterialIndex(int squareIndex) {
        long settingsVa = va();
        int result = getMaterialIndexForSi(settingsVa, squareIndex);

        return result;
    }

    /**
     * Return the material index for the specified square. The settings are
     * unaffected.
     *
     * @param x the X index of the square (&ge;0, &lt;sampleCount-1)
     * @param y the Y index of the square (&ge;0, &lt;sampleCount-1)
     * @return an index into the material list (&ge;0)
     */
    @Override
    public int getMaterialIndex(int x, int y) {
        long settingsVa = va();
        int result = getMaterialIndexForXY(settingsVa, x, y);

        return result;
    }

    /**
     * Return a reference to the material for the specified material index.
     *
     * @param materialIndex the index in the local table (&ge;0)
     * @return a new counted reference to the existing material
     */
    @Override
    public PhysicsMaterialRefC getMaterialRef(int materialIndex) {
        long settingsVa = va();
        long refVa = getMaterialRefForMi(settingsVa, materialIndex);
        long targetVa = PhysicsMaterialRef.getPtr(refVa);

        PhysicsMaterialRefC result;
        if (targetVa == 0L) {
            result = new PhysicsMaterialRefC(refVa);
        } else {
            PhysicsMaterial target = new PhysicsMaterial(targetVa);
            result = new PhysicsMaterialRefC(refVa, target);
        }

        return result;
    }

    /**
     * Return a reference to the material for the specified square.
     *
     * @param x the X index of the square (&ge;0, &lt;sampleCount-1)
     * @param y the Y index of the square (&ge;0, &lt;sampleCount-1)
     * @return a new counted reference to the existing material
     */
    @Override
    public PhysicsMaterialRefC getMaterialRef(int x, int y) {
        long settingsVa = va();
        long refVa = getMaterialRefForXY(settingsVa, x, y);
        long targetVa = PhysicsMaterialRef.getPtr(refVa);

        PhysicsMaterialRefC result;
        if (targetVa == 0L) {
            result = new PhysicsMaterialRefC(refVa);
        } else {
            PhysicsMaterial target = new PhysicsMaterial(targetVa);
            result = new PhysicsMaterialRefC(refVa, target);
        }

        return result;
    }

    /**
     * Return a reference to the material for the specified square.
     *
     * @param squareIndex the index of the square (&ge;0, &lt;(sampleCount-1)^2)
     * @return a new counted reference to the existing material
     */
    @Override
    public PhysicsMaterialRefC getMaterialRefForSquare(int squareIndex) {
        long settingsVa = va();
        long refVa = getMaterialRefForSi(settingsVa, squareIndex);
        long targetVa = PhysicsMaterialRef.getPtr(refVa);

        PhysicsMaterialRefC result;
        if (targetVa == 0L) {
            result = new PhysicsMaterialRefC(refVa);
        } else {
            PhysicsMaterial target = new PhysicsMaterial(targetVa);
            result = new PhysicsMaterialRefC(refVa, target);
        }

        return result;
    }

    /**
     * Return the artificial minimum capacity for the created shape's material
     * list. The settings are unaffected. (native attribute: mMaterialsCapacity)
     *
     * @return the capacity (in refs)
     */
    @Override
    public int getMaterialsCapacity() {
        long settingsVa = va();
        int result = getMaterialsCapacity(settingsVa);

        return result;
    }

    /**
     * Return the artificial maximum height. The settings are unaffected.
     * (native attribute: mMaxHeightValue)
     *
     * @return the height value
     */
    @Override
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
    @Override
    public float getMinHeightValue() {
        long settingsVa = va();
        float result = getMinHeightValue(settingsVa);

        return result;
    }

    /**
     * Copy the offset of the first sample. The settings are unaffected. (native
     * attribute: mOffset)
     *
     * @return a new offset vector
     */
    @Override
    public Vec3 getOffset() {
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getOffset(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Count the samples along each edge. The settings are unaffected. (native
     * attribute: mSampleCount)
     *
     * @return the number of samples (&ge;0)
     */
    @Override
    public int getSampleCount() {
        long settingsVa = va();
        int result = getSampleCount(settingsVa);

        return result;
    }

    /**
     * Copy the scale factors. The settings are unaffected. (native attribute:
     * mScale)
     *
     * @return a new scaling vector
     */
    @Override
    public Vec3 getScale() {
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getScale(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void assign(long targetVa, long sourceVa);

    native private static int calculateBitsPerSampleForError(
            long settingsVa, float maxError);

    native private static int countHeights(long settingsVa);

    native private static int countMaterials(long settingsVa);

    native private static int countSquares(long settingsVa);

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static long createFromBufferNoMats(
            FloatBuffer samples, float offsetX, float offsetY, float offsetZ,
            float scaleX, float scaleY, float scaleZ, int sampleCount);

    native private static long createSettingsFromArray(
            float[] samples, float offsetX, float offsetY, float offsetZ,
            float scaleX, float scaleY, float scaleZ, int sampleCount,
            byte[] materialIndices, long listVa);

    native private static long createSettingsFromBuffer(
            FloatBuffer samples, float offsetX, float offsetY, float offsetZ,
            float scaleX, float scaleY, float scaleZ, int sampleCount,
            byte[] materialIndices, long listVa);

    native private static void determineMinAndMaxSample(
            long settingsVa, FloatBuffer storeFloats);

    native private static float getActiveEdgeCosThresholdAngle(long settingsVa);

    native private static int getBitsPerSample(long settingsVa);

    native private static int getBlockSize(long settingsVa);

    native private static float getHeightForSi(
            long settingsVa, int sampleIndex);

    native private static float getHeightForXY(long settingsVa, int x, int y);

    native private static int getMaterialIndexForSi(
            long settingsVa, int squareIndex);

    native private static int getMaterialIndexForXY(
            long settingsVa, int x, int y);

    native private static long getMaterialRefForMi(
            long settingsVa, int materialIndex);

    native private static long getMaterialRefForSi(
            long settingsVa, int squareIndex);

    native private static long getMaterialRefForXY(
            long settingsVa, int x, int y);

    native private static long getMaterials(long settingsVa);

    native private static int getMaterialsCapacity(long settingsVa);

    native private static float getMaxHeightValue(long settingsVa);

    native private static float getMinHeightValue(long settingsVa);

    native private static void getOffset(
            long settingsVa, FloatBuffer storeFloats);

    native private static int getSampleCount(long settingsVa);

    native private static void getScale(
            long settingsVa, FloatBuffer storeFloats);

    native private static void setActiveEdgeCosThresholdAngle(
            long settingsVa, float cosine);

    native private static void setBitsPerSample(long settingsVa, int numBits);

    native private static void setBlockSize(long settingsVa, int numRows);

    native private static void setHeightForSi(
            long settingsVa, int sampleIndex, float height);

    native private static void setHeightForXY(
            long settingsVa, int x, int y, float height);

    native private static void setMaterialForMi(
            long settingsVa, int materialIndex, long refVa);

    native private static void setMaterialForSi(
            long settingsVa, int squareIndex, int materialIndex);

    native private static void setMaterialIndexForXY(
            long settingsVa, int x, int y, int materialIndex);

    native private static void setMaterials(long settingsVa, long listVa);

    native private static void setMaterialsCapacity(
            long settingsVa, int numRefs);

    native private static void setMaxHeightValue(
            long settingsVa, float maxHeight);

    native private static void setMinHeightValue(
            long settingsVa, float minHeight);

    native private static void setOffset(
            long settingsVa, float dx, float dy, float dz);

    native private static void setSampleCount(long settingsVa, int sampleCount);

    native private static void setScale(
            long settingsVa, float sx, float sy, float sz);
}
