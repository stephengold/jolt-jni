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

import com.github.stephengold.joltjni.readonly.ConstContactManifold;

/**
 * The contact surface between two bodies.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class ContactManifold
        extends JoltPhysicsObject
        implements ConstContactManifold {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a manifold with the specified native object assigned but not
     * owned.
     *
     * @param pairVa the virtual address of the native object to assign (not
     * zero)
     */
    public ContactManifold(long pairVa) {
        setVirtualAddress(pairVa, null);
    }
    // *************************************************************************
    // ConstContactManifolds methods

    /**
     * Return the location from which all contact points are measured. The
     * manifold is unaffected. (native attribute: mBaseOffset)
     *
     * @return a new location vector
     */
    @Override
    public RVec3 getBaseOffset() {
        long pairVa = va();
        double x = getBaseOffsetX(pairVa);
        double y = getBaseOffsetY(pairVa);
        double z = getBaseOffsetZ(pairVa);
        RVec3 result = new RVec3(x, y, z);

        return result;
    }

    /**
     * Return the penetration depth: the distance to move body 2 out of
     * collision. The manifold is unaffected. (native attribute:
     * mPenetrationDepth)
     *
     * @return the signed distance (negative for a speculative contact)
     */
    @Override
    public float getPenetrationDepth() {
        long pairVa = va();
        float result = getPenetrationDepth(pairVa);
        return result;
    }

    /**
     * Return the ID of the first subshape that formed the manifold. The
     * manifold is unaffected. (native attribute: mSubShapeID1)
     *
     * @return a new object
     */
    @Override
    public SubShapeId getSubShapeId1() {
        long pairVa = va();
        long idVa = getSubShapeId1(pairVa);
        SubShapeId result = new SubShapeId(idVa, true);

        return result;
    }

    /**
     * Return the ID of the 2nd subshape that formed the manifold. The manifold
     * is unaffected. (native attribute: mSubShapeID2)
     *
     * @return a new object
     */
    @Override
    public SubShapeId getSubShapeId2() {
        long pairVa = va();
        long idVa = getSubShapeId2(pairVa);
        SubShapeId result = new SubShapeId(idVa, true);

        return result;
    }

    /**
     * Return the normal: the direction to move body 2 out of collision. The
     * manifold is unaffected. (native attribute: mWorldSpaceNormal)
     *
     * @return a new direction vector (in system coordinates)
     */
    @Override
    public Vec3 getWorldSpaceNormal() {
        long pairVa = va();
        float x = getWorldSpaceNormalX(pairVa);
        float y = getWorldSpaceNormalY(pairVa);
        float z = getWorldSpaceNormalZ(pairVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static double getBaseOffsetX(long pairVa);

    native private static double getBaseOffsetY(long pairVa);

    native private static double getBaseOffsetZ(long pairVa);

    native private static float getPenetrationDepth(long pairVa);

    native private static long getSubShapeId1(long pairVa);

    native private static long getSubShapeId2(long pairVa);

    native private static float getWorldSpaceNormalX(long pairVa);

    native private static float getWorldSpaceNormalY(long pairVa);

    native private static float getWorldSpaceNormalZ(long pairVa);
}
