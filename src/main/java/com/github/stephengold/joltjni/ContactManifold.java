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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.readonly.ConstContactManifold;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

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
     * @param manifoldVa the virtual address of the native object to assign (not
     * zero)
     */
    public ContactManifold(long manifoldVa) {
        setVirtualAddress(manifoldVa);
    }
    // *************************************************************************
    // ConstContactManifolds methods

    /**
     * Copy the location from which all contact points are measured. The
     * manifold is unaffected. (native attribute: mBaseOffset)
     *
     * @return a new location vector
     */
    @Override
    public RVec3 getBaseOffset() {
        long manifoldVa = va();
        DoubleBuffer storeDoubles = Temporaries.doubleBuffer1.get();
        getBaseOffset(manifoldVa, storeDoubles);
        RVec3 result = new RVec3(storeDoubles);

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
        long manifoldVa = va();
        float result = getPenetrationDepth(manifoldVa);
        return result;
    }

    /**
     * Return the ID of the first sub-shape that formed the manifold. The
     * manifold is unaffected. (native attribute: mSubShapeID1)
     *
     * @return a {@code SubShapeID} value
     */
    @Override
    public int getSubShapeId1() {
        long manifoldVa = va();
        int result = getSubShapeId1(manifoldVa);

        return result;
    }

    /**
     * Return the ID of the 2nd sub-shape that formed the manifold. The manifold
     * is unaffected. (native attribute: mSubShapeID2)
     *
     * @return a {@code SubShapeID} value
     */
    @Override
    public int getSubShapeId2() {
        long manifoldVa = va();
        int result = getSubShapeId2(manifoldVa);

        return result;
    }

    /**
     * Copy the normal vector: the direction to move body 2 out of collision.
     * The manifold is unaffected. (native attribute: mWorldSpaceNormal)
     *
     * @return a new direction vector (in system coordinates)
     */
    @Override
    public Vec3 getWorldSpaceNormal() {
        long manifoldVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getWorldSpaceNormal(manifoldVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void getBaseOffset(
            long manifoldVa, DoubleBuffer storeFloats);

    native private static float getPenetrationDepth(long manifoldVa);

    native private static int getSubShapeId1(long manifoldVa);

    native private static int getSubShapeId2(long manifoldVa);

    native private static void getWorldSpaceNormal(
            long manifoldVa, FloatBuffer storeFloats);
}
