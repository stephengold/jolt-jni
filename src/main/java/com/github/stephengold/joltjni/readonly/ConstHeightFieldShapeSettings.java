/*
Copyright (c) 2026 Stephen Gold

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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.PhysicsMaterialRefC;
import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to a {@code HeightFieldShapeSettings} object. (native type:
 * const HeightFieldShapeSettings)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstHeightFieldShapeSettings extends ConstShapeSettings {
    /**
     * Calculate the minimum bits per sample that will provide the specified
     * error tolerance. The settings are unaffected.
     *
     * @param maxError the allowable tolerance
     * @return the (hypothetical) bits per sample
     */
    int calculateBitsPerSampleForError(float maxError);

    /**
     * Count the height samples, including {@code cNoCollisionValue}. The
     * settings are unaffected.
     *
     * @return the actual number of samples in the array (&ge;0)
     */
    int countHeights();

    /**
     * Count the references in the material list, including null refs. The
     * settings are unaffected.
     *
     * @return the actual number of references in the list (&ge;0)
     */
    int countMaterials();

    /**
     * Count the squares. The settings are unaffected.
     *
     * @return the actual number of material indices in the array (&ge;0)
     */
    int countSquares();

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
    void determineMinAndMaxSample(
            float[] storeMin, float[] storeMax, float[] storeQuantScale);

    /**
     * Return the cosine of the active-edge threshold angle. The settings are
     * unaffected.
     *
     * @return the cosine
     */
    float getActiveEdgeCosThresholdAngle();

    /**
     * Return the number of bits per sample to use during compression. The
     * settings are unaffected.
     *
     * @return the number of bits (&ge;1, &le;16)
     */
    int getBitsPerSample();

    /**
     * Return the block size. The settings are unaffected.
     * <p>
     * For culling purposes, the height field is organized in blocks of
     * {@code 2 * mBlockSize * mBlockSize} triangles.
     * <p>
     * Sensible values range from 2 to 8.
     *
     * @return the block size (in rows)
     */
    int getBlockSize();

    /**
     * Return the height of the specified sample. The settings are unaffected.
     *
     * @param sampleIndex the array index (&ge;0, &lt;sampleCount^2)
     * @return the sample height or {@code cNoCollisionValue}
     */
    float getHeightSample(int sampleIndex);

    /**
     * Return the height of the specified sample. The settings are unaffected.
     *
     * @param x the X index of the sample (&ge;0, &lt;sampleCount)
     * @param y the Y index of the sample (&ge;0, &lt;sampleCount)
     * @return the sample height or {@code cNoCollisionValue}
     */
    float getHeightSample(int x, int y);

    /**
     * Return the material index for the specified square. The settings are
     * unaffected.
     *
     * @param squareIndex the index of the square (&ge;0, &lt;(sampleCount-1)^2)
     * @return an index into the material list (&ge;0)
     */
    int getMaterialIndex(int squareIndex);

    /**
     * Return the material index for the specified square. The settings are
     * unaffected.
     *
     * @param x the X index of the square (&ge;0, &lt;sampleCount-1)
     * @param y the Y index of the square (&ge;0, &lt;sampleCount-1)
     * @return an index into the material list (&ge;0)
     */
    int getMaterialIndex(int x, int y);

    /**
     * Return a reference to the material for the specified material index.
     *
     * @param materialIndex the index in the local table (&ge;0)
     * @return a new counted reference to the existing material
     */
    PhysicsMaterialRefC getMaterialRef(int materialIndex);

    /**
     * Return a reference to the material for the specified square.
     *
     * @param x the X index of the square (&ge;0, &lt;sampleCount-1)
     * @param y the Y index of the square (&ge;0, &lt;sampleCount-1)
     * @return a new counted reference to the existing material
     */
    PhysicsMaterialRefC getMaterialRef(int x, int y);

    /**
     * Return a reference to the material for the specified square.
     *
     * @param squareIndex the index of the square (&ge;0, &lt;(sampleCount-1)^2)
     * @return a new counted reference to the existing material
     */
    PhysicsMaterialRefC getMaterialRefForSquare(int squareIndex);

    /**
     * Return the artificial minimum capacity for the created shape's material
     * list. The settings are unaffected.
     *
     * @return the capacity (in refs)
     */
    int getMaterialsCapacity();

    /**
     * Return the artificial maximum height. The settings are unaffected.
     *
     * @return the height value
     */
    float getMaxHeightValue();

    /**
     * Return the artificial minimum height. The settings are unaffected.
     *
     * @return the height value
     */
    float getMinHeightValue();

    /**
     * Copy the offset of the first sample. The settings are unaffected.
     *
     * @return a new offset vector
     */
    Vec3 getOffset();

    /**
     * Count the samples along each edge. The settings are unaffected.
     *
     * @return the number of samples (&ge;0)
     */
    int getSampleCount();

    /**
     * Copy the scale factors. The settings are unaffected.
     *
     * @return a new scaling vector
     */
    Vec3 getScale();
}
