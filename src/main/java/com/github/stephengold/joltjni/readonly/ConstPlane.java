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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.Vec3;
import java.nio.FloatBuffer;

/**
 * Read-only access to an {@code Plane}. (native type: const Plane)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstPlane {
    /**
     * Write all 4 components to the start of the specified buffer. The plane is
     * unaffected.
     *
     * @param storeFloats the destination buffer (not null, capacity&ge;4)
     */
    void copyTo(FloatBuffer storeFloats);

    /**
     * Return the constant in single precision. The plane is unaffected.
     *
     * @return the constant value
     */
    float getConstant();

    /**
     * Copy the normal direction. The plane is unaffected.
     *
     * @return a new direction vector
     */
    Vec3 getNormal();

    /**
     * Return the first (X) component of the normal direction. The plane is
     * unaffected.
     *
     * @return the component value
     */
    float getNormalX();

    /**
     * Return the 2nd (Y) component of the normal direction. The plane is
     * unaffected.
     *
     * @return the component value
     */
    float getNormalY();

    /**
     * Return the 3rd (Z) component of the normal direction. The plane is
     * unaffected.
     *
     * @return the component value
     */
    float getNormalZ();

    /**
     * Write all 4 components to the specified buffer and advance the buffer's
     * position by 4. The plane is unaffected.
     *
     * @param storeBuffer the destination buffer (not null)
     */
    void put(FloatBuffer storeBuffer);

    /**
     * Return the signed distance of the specified point.
     *
     * @param point the point to measure (not null, unaffected)
     * @return the signed distance
     */
    float signedDistance(Vec3Arg point);
}
