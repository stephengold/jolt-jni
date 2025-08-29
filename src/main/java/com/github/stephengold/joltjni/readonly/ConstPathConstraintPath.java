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

import com.github.stephengold.joltjni.StreamOut;
import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to a {@code PathConstraintPath}. (native type: const
 * PathConstraintPath)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstPathConstraintPath extends ConstSerializableObject {
    /**
     * Return the path amount of the location on the path that's closest to the
     * specified location. The path is unaffected.
     *
     * @param location the input location (in system coordinates, not null,
     * unaffected)
     * @param fractionHint where to start searching
     * @return the path amount (&ge;0)
     */
    float getClosestPoint(Vec3Arg location, float fractionHint);

    /**
     * Return the path amount of the end of the path. The path is unaffected.
     *
     * @return the path amount (&ge;0)
     */
    float getPathMaxFraction();

    /**
     * Calculate the location, normal, and binormal of the location on the path
     * with the specified path amount. The path is unaffected.
     *
     * @param amount the path amount (&ge;0)
     * @param storeLocation storage for the location (in system coordinates)
     * @param storeTangent storage for the tangent direction (in system
     * coordinates)
     * @param storeNormal storage for the normal direction (in system
     * coordinates)
     * @param storeBinormal storage for the binormal direction (in system
     * coordinates)
     */
    void getPointOnPath(float amount, Vec3 storeLocation,
            Vec3 storeTangent, Vec3 storeNormal, Vec3 storeBinormal);

    /**
     * Test whether the path is looping. The path is unaffected.
     *
     * @return {@code true} if looping, otherwise {@code false}
     */
    boolean isLooping();

    /**
     * Save the path to the specified binary stream. The path is unaffected.
     *
     * @param stream the stream to write to (not null)
     */
    void saveBinaryState(StreamOut stream);
}
