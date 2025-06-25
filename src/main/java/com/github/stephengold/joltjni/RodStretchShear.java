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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.readonly.ConstRodStretchShear;
import com.github.stephengold.joltjni.readonly.QuatArg;
import java.nio.FloatBuffer;

/**
 * Join 2 soft-body vertices with a discrete Cosserat rod. (native type:
 * {@code SoftBodySharedSettings::RodStretchShear})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class RodStretchShear
        extends JoltPhysicsObject
        implements ConstRodStretchShear {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default rod.
     */
    public RodStretchShear() {
        long rodVa = createDefault();
        setVirtualAddress(rodVa, () -> free(rodVa));
    }

    /**
     * Instantiate a rod joining the specified vertices with infinite stiffness.
     *
     * @param vertex0 the mesh index of the first vertex (&ge;0)
     * @param vertex1 the mesh index of the 2nd vertex (&ge;0)
     */
    public RodStretchShear(int vertex0, int vertex1) {
        this(vertex0, vertex1, 0f);
    }

    /**
     * Instantiate a rod joining the specified vertices with the specified
     * stiffness.
     *
     * @param vertex0 the mesh index of the first vertex (&ge;0)
     * @param vertex1 the mesh index of the 2nd vertex (&ge;0)
     * @param compliance the inverse of the desired stiffness (default=0)
     */
    public RodStretchShear(int vertex0, int vertex1, float compliance) {
        long rodVa = create(vertex0, vertex1, compliance);
        setVirtualAddress(rodVa, () -> free(rodVa));
    }

    /**
     * Instantiate a rod with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param rodVa the virtual address of the native object to assign (not
     * zero)
     */
    RodStretchShear(JoltPhysicsObject container, long rodVa) {
        super(container, rodVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the Bishop frame of the rod. (native attribute: mBishop)
     *
     * @param bishop the desired orientation (not null, unaffected)
     * @return the modified settings, for chaining
     */
    public RodStretchShear setBishop(QuatArg bishop) {
        long rodVa = va();
        float qx = bishop.getX();
        float qy = bishop.getY();
        float qz = bishop.getZ();
        float qw = bishop.getW();
        setBishop(rodVa, qx, qy, qz, qw);

        return this;
    }

    /**
     * Alter the stiffness of the rod. (native attribute: mCompliance)
     *
     * @param compliance the inverse of the desired stiffness (default=0)
     * @return the modified settings, for chaining
     */
    public RodStretchShear setCompliance(float compliance) {
        long rodVa = va();
        setCompliance(rodVa, compliance);

        return this;
    }

    /**
     * Alter the inverse mass of the rod. (native attribute: mInvMass)
     *
     * @param invMass the desired inverse mass (in 1/kilograms, default=1, 0 for
     * a static rod)
     * @return the modified settings, for chaining
     */
    public RodStretchShear setInvMass(float invMass) {
        long rodVa = va();
        setInvMass(rodVa, invMass);

        return this;
    }

    /**
     * Alter the length of the rod. (native attribute: mLength)
     *
     * @param length the desired length (in meters, default=1)
     * @return the modified settings, for chaining
     */
    public RodStretchShear setLength(float length) {
        long rodVa = va();
        setLength(rodVa, length);

        return this;
    }

    /**
     * Assign the specified mesh vertex to the rod. (native attribute: mVertex)
     *
     * @param indexInRod which end of the rod (0 or 1)
     * @param indexInMesh the index of the vertex to assign (&ge;0)
     * @return the modified settings, for chaining
     */
    public RodStretchShear setVertex(int indexInRod, int indexInMesh) {
        long rodVa = va();
        setVertex(rodVa, indexInRod, indexInMesh);

        return this;
    }
    // *************************************************************************
    // ConstRodStretchShear methods

    /**
     * Return the Bishop frame of the rod. The rod is unaffected. (native
     * attribute: mBishop)
     *
     * @return orientation of the rod
     */
    @Override
    public Quat getBishop() {
        long rodVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getBishop(rodVa, storeFloats);
        Quat result = new Quat(storeFloats);

        return result;
    }

    /**
     * Return the inverse of the spring's stiffness. The rod is unaffected.
     * (native attribute: mCompliance)
     *
     * @return the compliance value
     */
    @Override
    public float getCompliance() {
        long rodVa = va();
        float result = getCompliance(rodVa);

        return result;
    }

    /**
     * Return the inverse mass of the rod. The rod is unaffected. (native
     * attribute: mInvMass)
     *
     * @return the inverse of the mass (in 1/kilograms)
     */
    public float getInvMass() {
        long rodVa = va();
        float result = getInvMass(rodVa);

        return result;
    }

    /**
     * Return the length of the rod. The rod is unaffected. (native attribute:
     * mLength)
     *
     * @return the length (in meters)
     */
    @Override
    public float getLength() {
        long rodVa = va();
        float result = getLength(rodVa);

        return result;
    }

    /**
     * Return the mesh vertex at the specified end. (native attribute: mVertex)
     *
     * @param indexInRod which end of the rod (0 or 1)
     * @return the mesh index of the vertex (&ge;0)
     */
    @Override
    public int getVertex(int indexInRod) {
        long rodVa = va();
        int result = getVertex(rodVa, indexInRod);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long create(
            int vertex0, int vertex1, float compliance);

    native private static long createDefault();

    native private static void free(long rodVa);

    native private static void getBishop(long rodVa, FloatBuffer storeFloats);

    native private static float getCompliance(long rodVa);

    native private static float getInvMass(long rodVa);

    native private static float getLength(long rodVa);

    native private static int getVertex(long rodVa, int indexInRod);

    native private static void setBishop(
            long rodVa, float qx, float qy, float qz, float qw);

    native private static void setCompliance(long rodVa, float compliance);

    native private static void setInvMass(long rodVa, float invMass);

    native private static void setLength(long rodVa, float length);

    native private static void setVertex(
            long rodVa, int indexInRod, int indexInMesh);
}
