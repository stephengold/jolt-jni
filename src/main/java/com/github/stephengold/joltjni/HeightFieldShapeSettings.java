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
     * Instantiate settings for the specified samples.
     *
     * @param samples array of height values (not null, capacity&ge;4,
     * unaffected)
     * @param offset (not null, unaffected);
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
    // native private methods

    native private static long createHeightFieldShapeSettings(
            FloatBuffer samples, float offsetX, float offsetY, float offsetZ,
            float scaleX, float scaleY, float scaleZ, int sampleCount);
}
