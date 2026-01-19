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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.readonly.ConstFloat3;
import com.github.stephengold.joltjni.readonly.ConstSVertex;
import java.nio.FloatBuffer;

/**
 * A single vertex in a strand of hair. (native type:
 * {@code HairSettings::SVertex})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SVertex extends JoltPhysicsObject implements ConstSVertex {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default vertex.
     */
    public SVertex() {
        long vertexVa = createDefault();
        setVirtualAddress(vertexVa, () -> free(vertexVa));
    }

    /**
     * Instantiate a vertex with the specified location and inverse mass.
     *
     * @param location the desired coordinates (not {@code null}, unaffected,
     * default=(0,0,0))
     * @param inverseMass the inverse of the desired mass (default=1)
     */
    public SVertex(ConstFloat3 location, float inverseMass) {
        float x = location.x();
        float y = location.y();
        float z = location.z();
        long vertexVa = create(x, y, z, inverseMass);
        setVirtualAddress(vertexVa, () -> free(vertexVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the mass. (native attribute: mInvMass)
     *
     * @param inverseMass the inverse of the desired mass (default=1)
     */
    public void setInvMass(float inverseMass) {
        long vertexVa = va();
        setInvMass(vertexVa, inverseMass);
    }

    /**
     * Alter the initial location of the vertex in its modeled pose. (native
     * attribute: mPosition)
     *
     * @param location the desired coordinates (not {@code null}, unaffected,
     * default=(0,0,0))
     */
    public void setPosition(ConstFloat3 location) {
        long vertexVa = va();
        float x = location.get(0);
        float y = location.get(1);
        float z = location.get(2);
        setPosition(vertexVa, x, y, z);
    }
    // *************************************************************************
    // ConstSVertex methods

    /**
     * Copy the bishop frame of the strand in its modeled pose, computed by
     * {@code init()}. The vertex is unaffected. (native attribute: mBishop)
     *
     * @return a new vector
     */
    @Override
    public Float4 getBishop() {
        long vertexVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getBishop(vertexVa, storeFloats);
        Float4 result = new Float4(storeFloats);

        return result;
    }

    /**
     * Return the inverse of the mass. The vertex is unaffected. (native
     * attribute: mInvMass)
     *
     * @return the value
     */
    @Override
    public float getInvMass() {
        long vertexVa = va();
        float result = getInvMass(vertexVa);

        return result;
    }

    /**
     * Return the initial distance between this vertex from the next one in the
     * unloaded strand, computed by {@code init()}. The vertex is unaffected.
     * (native attribute: mLength)
     *
     * @return the distance
     */
    @Override
    public float getLength() {
        long vertexVa = va();
        float result = getLength(vertexVa);

        return result;
    }

    /**
     * Copy the rotation between the previous rod and this one in the unloaded
     * strand, computed by {@code init()}. The vertex is unaffected. (native
     * attribute: mOmega0)
     *
     * @return a new vector
     */
    @Override
    public Float4 getOmega0() {
        long vertexVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getOmega0(vertexVa, storeFloats);
        Float4 result = new Float4(storeFloats);

        return result;
    }

    /**
     * Copy the initial location of the vertex in its modeled pose. The vertex
     * is unaffected. (native attribute: mPosition)
     *
     * @return a new vector
     */
    @Override
    public Float3 getPosition() {
        long vertexVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getPosition(vertexVa, storeFloats);
        Float3 result = new Float3(storeFloats);

        return result;
    }

    /**
     * Return the fraction of the way along the strand, computed by
     * {@code init()}. The vertex is unaffected. (native attribute:
     * mStrandFraction)
     *
     * @return the fractional distance (0=start, 1=end)
     */
    @Override
    public float getStrandFraction() {
        long vertexVa = va();
        float result = getStrandFraction(vertexVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long create(
            float x, float y, float z, float inverseMass);

    native private static long createDefault();

    native private static void free(long vertexVa);

    native private static void getBishop(
            long vertexVa, FloatBuffer storeFloats);

    native private static float getInvMass(long vertexVa);

    native private static float getLength(long vertexVa);

    native private static void getOmega0(
            long vertexVa, FloatBuffer storeFloats);

    native private static void getPosition(
            long vertexVa, FloatBuffer storeFloats);

    native private static float getStrandFraction(long vertexVa);

    native private static void setInvMass(long vertexVa, float inverseMass);

    native private static void setPosition(
            long vertexVa, float x, float y, float z);
}
