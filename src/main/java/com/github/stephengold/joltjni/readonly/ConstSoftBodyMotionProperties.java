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

import java.nio.FloatBuffer;

/**
 * Read-only access to {@code SoftBodyMotionProperties}. (native type: const
 * SoftBodyMotionProperties)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstSoftBodyMotionProperties extends ConstMotionProperties {
    /**
     * Test whether skinning constraints are enabled. The properties are
     * unaffected.
     *
     * @return {@code true} if enabled, {@code false} if disabled
     */
    boolean getEnableSkinConstraints();

    /**
     * Access the specified face.
     *
     * @param index the index of the face (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstFace getFace(int index);

    /**
     * Enumerate all faces in the soft body.
     *
     * @return a new array of new JVM objects
     */
    ConstFace[] getFaces();

    /**
     * Return the number of solver iterations. The properties are unaffected.
     *
     * @return the number of iterations (&ge;0)
     */
    int getNumIterations();

    /**
     * Access the shared settings.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstSoftBodySharedSettings getSettings();

    /**
     * Return the maximum distance multiplier for skinned vertices. The
     * properties are unaffected.
     *
     * @return the multiplier
     */
    float getSkinnedMaxDistanceMultiplier();

    /**
     * Access the specified vertex.
     *
     * @param index the index of the vertex (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstSoftBodyVertex getVertex(int index);

    /**
     * Enumerate all vertices in the soft body.
     *
     * @return a new array of new JVM objects with pre-existing native objects
     * assigned
     */
    ConstSoftBodyVertex[] getVertices();

    /**
     * Write the locations of all pinned vertices to the specified buffer and
     * advance the buffer's position. The properties are unaffected.
     *
     * @param comLocation the location of the body's center of mass (not null)
     * @param storeFloats the destination buffer (not null, modified)
     */
    void putPinLocations(RVec3Arg comLocation, FloatBuffer storeFloats);

    /**
     * Write the locations of all vertices to the specified buffer and advance
     * the buffer's position. The properties are unaffected.
     *
     * @param comLocation the location of the body's center of mass (not null)
     * @param storeFloats the destination buffer (not null, modified)
     */
    void putVertexLocations(
            RVec3Arg comLocation, FloatBuffer storeFloats);
}
