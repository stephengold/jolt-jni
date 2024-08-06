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

import com.github.stephengold.joltjni.readonly.ConstCollideShapeResult;

/**
 * Properties of a collision between 2 shapes.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CollideShapeResult
        extends JoltPhysicsObject
        implements ConstCollideShapeResult {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a shape result with the specified native object assigned but
     * not owned.
     *
     * @param shapeResultVa the virtual address of the native object to assign
     * (not zero)
     */
    public CollideShapeResult(long shapeResultVa) {
        setVirtualAddress(shapeResultVa, null);
    }
    // *************************************************************************
    // ConstCollideShapeResult methods

    /**
     * Identify the body to which shape 2 belongs. The object is unaffected.
     * (native field: mBodyID2)
     *
     * @return a new ID
     */
    @Override
    public BodyId getBodyId2() {
        long shapeResultVa = va();
        long idVa = getBodyId2(shapeResultVa);
        BodyId result = new BodyId(idVa, true);

        return result;
    }

    /**
     * Copy the contact location on the surface of shape 1. The object is
     * unaffected. (native field: mContactPointOn1)
     *
     * @return a new location vector
     */
    @Override
    public Vec3 getContactPointOn1() {
        long shapeResultVa = va();
        float x = getContactPointOn1X(shapeResultVa);
        float y = getContactPointOn1Y(shapeResultVa);
        float z = getContactPointOn1Z(shapeResultVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Copy the contact location on the surface of shape 2. The object is
     * unaffected. (native field: mContactPointOn2)
     *
     * @return a new location vector
     */
    @Override
    public Vec3 getContactPointOn2() {
        long shapeResultVa = va();
        float x = getContactPointOn2X(shapeResultVa);
        float y = getContactPointOn2Y(shapeResultVa);
        float z = getContactPointOn2Z(shapeResultVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Copy the direction to move shape 2 out of collision along the shortest
     * path. The object is unaffected. (native field: mPenetrationAxis)
     *
     * @return a new direction vector in system coordinates
     */
    @Override
    public Vec3 getPenetrationAxis() {
        long shapeResultVa = va();
        float x = getPenetrationAxisX(shapeResultVa);
        float y = getPenetrationAxisY(shapeResultVa);
        float z = getPenetrationAxisZ(shapeResultVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the distance to move shape 2 to resolve the collision. The object
     * is unaffected. (native field: mPenetrationDepth)
     *
     * @return the signed distance
     */
    @Override
    public float getPenetrationDepth() {
        long shapeResultVa = va();
        float result = getPenetrationDepth(shapeResultVa);

        return result;
    }

    /**
     * Identify the face on shape 1 where the collision occurred. The object is
     * unaffected. (native field: mSubShapeID1)
     *
     * @return a new ID
     */
    @Override
    public SubShapeId getSubShapeId1() {
        long shapeResultVa = va();
        long idVa = getSubShapeId1(shapeResultVa);
        SubShapeId result = new SubShapeId(idVa, true);

        return result;
    }

    /**
     * Identify the face on shape 2 where the collision occurred. The object is
     * unaffected. (native field: mSubShapeID2)
     *
     * @return a new ID
     */
    @Override
    public SubShapeId getSubShapeId2() {
        long shapeResultVa = va();
        long idVa = getSubShapeId2(shapeResultVa);
        SubShapeId result = new SubShapeId(idVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long getBodyId2(long shapeResultVa);

    native private static float getContactPointOn1X(long shapeResultVa);

    native private static float getContactPointOn1Y(long shapeResultVa);

    native private static float getContactPointOn1Z(long shapeResultVa);

    native private static float getContactPointOn2X(long shapeResultVa);

    native private static float getContactPointOn2Y(long shapeResultVa);

    native private static float getContactPointOn2Z(long shapeResultVa);

    native private static float getPenetrationAxisX(long shapeResultVa);

    native private static float getPenetrationAxisY(long shapeResultVa);

    native private static float getPenetrationAxisZ(long shapeResultVa);

    native private static float getPenetrationDepth(long shapeResultVa);

    native private static long getSubShapeId1(long shapeResultVa);

    native private static long getSubShapeId2(long shapeResultVa);
}
