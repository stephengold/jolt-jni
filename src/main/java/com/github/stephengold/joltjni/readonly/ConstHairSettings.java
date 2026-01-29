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

import com.github.stephengold.joltjni.Float3;
import com.github.stephengold.joltjni.StreamOut;
import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to a {@code HairSettings} object. (native type: const
 * HairSettings)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstHairSettings extends ConstJoltPhysicsObject {
    /**
     * Return the number of materials. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    int countMaterials();

    /**
     * Return the number of render strands. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    int countRenderStrands();

    /**
     * Return the number of render vertices. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    int countRenderVertices();

    /**
     * Return the number of triangles in the scalp mesh. The settings are
     * unaffected.
     *
     * @return the count (&ge;0)
     */
    int countScalpTriangles();

    /**
     * Return the number of vertices in the scalp mesh. The settings are
     * unaffected.
     *
     * @return the count (&ge;0)
     */
    int countScalpVertices();

    /**
     * Return the number of simulation strands. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    int countSimStrands();

    /**
     * Copy the initial gravity vector. The settings are unaffected.
     *
     * @return a new vector (in meters per second squared)
     */
    Vec3 getInitialGravity();

    /**
     * Access the specified material. The settings are unaffected.
     *
     * @param index the index of the material to access (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstHairMaterial getMaterial(int index);

    /**
     * Return the iteration rate. The settings are unaffected.
     *
     * @return the number of iterations per second (&ge;1)
     */
    int getNumIterationsPerSecond();

    /**
     * Access the specified render strand. The settings are unaffected.
     *
     * @param strandIndex the index of the strand to access (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstRStrand getRenderStrand(int strandIndex);

    /**
     * Alter the number of skinning weights per vertex. The settings are
     * unaffected.
     *
     * @return the number of weights per vertex
     */
    int getScalpNumSkinWeightsPerVertex();

    /**
     * Access the vertex indices of the specified triangle in the scalp mesh.
     * The settings are unaffected.
     *
     * @param triangleIndex the index of the triangle to access (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstIndexedTriangleNoMaterial getScalpTriangle(int triangleIndex);

    /**
     * Copy the specified vertex in the scalp mesh. The settings are unaffected.
     *
     * @param vertexIndex the index of the vertex to access (&ge;0)
     * @return a new vector
     */
    Float3 getScalpVertex(int vertexIndex);

    /**
     * Access the specified simulation strand. The settings are unaffected.
     *
     * @param strandIndex the index of the strand to access (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstSStrand getSimStrand(int strandIndex);

    /**
     * Access the simulation bounds. The settings are unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstAaBox getSimulationBounds();

    /**
     * Write the state of this object to the specified stream, excluding the
     * compute buffers. The settings are unaffected.
     *
     * @param streamOut where to write objects (not {@code null})
     */
    void saveBinaryState(StreamOut streamOut);
}
