/*
Copyright (c) 2025 Stephen Gold

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

import com.github.stephengold.joltjni.Support;
import com.github.stephengold.joltjni.SupportBuffer;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.ESupportMode;

/**
 * Read-only access to a {@code ConvexShape}. (native type: const ConvexShape)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstConvexShape extends ConstShape {
    /**
     * Return the density.
     *
     * @return the density
     */
    float getDensity();

    /**
     * Calculate the shape's volume, submerged volume, and center of buoyancy.
     *
     * @param comTransform the local-to-system transform (not null, unaffected)
     * @param scale the scale vector (not null, unaffected)
     * @param surface the boundary of the submerged region (not null,
     * unaffected)
     * @param storeTotalVolume storage for the total volume, or {@code null}
     * @param storeSubmergedVolume storage for the submerged volume, or
     * {@code null}
     * @param storeCenterOfBuoyancy storage for the center of buoyancy, or
     * {@code null}
     * @param baseOffset the base offset to use (ignored if the native library
     * doesn't implement debug rendering)
     */
    void getSubmergedVolume(
            Mat44Arg comTransform, Vec3Arg scale, ConstPlane surface,
            float[] storeTotalVolume, float[] storeSubmergedVolume,
            Vec3 storeCenterOfBuoyancy, RVec3Arg baseOffset);

    /**
     * Generate a support function for the shape.
     *
     * @param supportMode how to handle convex radius (not null)
     * @param buffer buffer storage (not null)
     * @param scale scale factors to apply (in local coordinates, not null,
     * unaffected)
     * @return a new JVM object with the pre-existing native object assigned
     */
    Support getSupportFunction(
            ESupportMode supportMode, SupportBuffer buffer, Vec3Arg scale);
}
