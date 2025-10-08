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

import com.github.stephengold.joltjni.readonly.ConstCollideShapeResult;
import java.nio.FloatBuffer;

/**
 * Information about a narrow-phase collision by a shape.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CollideShapeResult
        extends JoltPhysicsObject
        implements ConstCollideShapeResult {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param shapeResultVa the virtual address of the native object to assign
     * (not zero)
     */
    CollideShapeResult(JoltPhysicsObject container, long shapeResultVa) {
        super(container, shapeResultVa);
    }

    /**
     * Instantiate a shape result with the specified native object assigned but
     * not owned.
     * <p>
     * For use in custom collectors.
     *
     * @param shapeResultVa the virtual address of the native object to assign
     * (not zero)
     */
    public CollideShapeResult(long shapeResultVa) {
        setVirtualAddress(shapeResultVa);
    }
    // *************************************************************************
    // ConstCollideShapeResult methods

    /**
     * Identify the body to which shape 2 belongs. The result object is
     * unaffected. (native attribute: mBodyID2)
     *
     * @return the BodyID value
     */
    @Override
    public int getBodyId2() {
        long shapeResultVa = va();
        int result = getBodyId2(shapeResultVa);

        return result;
    }

    /**
     * Copy the contact location on the surface of shape 1. The result object is
     * unaffected. (native attribute: mContactPointOn1)
     *
     * @return a new location vector
     */
    @Override
    public Vec3 getContactPointOn1() {
        long shapeResultVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getContactPointOn1(shapeResultVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the contact location on the surface of shape 2. The result object is
     * unaffected. (native attribute: mContactPointOn2)
     *
     * @return a new location vector
     */
    @Override
    public Vec3 getContactPointOn2() {
        long shapeResultVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getContactPointOn2(shapeResultVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the direction to move shape 2 out of collision along the shortest
     * path. The result object is unaffected. (native attribute:
     * mPenetrationAxis)
     *
     * @return a new direction vector in system coordinates
     */
    @Override
    public Vec3 getPenetrationAxis() {
        long shapeResultVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getPenetrationAxis(shapeResultVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Return the distance to move shape 2 to resolve the collision. The result
     * object is unaffected. (native attribute: mPenetrationDepth)
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
     * Access the colliding face on shape 1. (native attribute: mShape1Face)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public CsrFace getShape1Face() {
        long shapeResultVa = va();
        long faceVa = getShape1Face(shapeResultVa);
        CsrFace result = new CsrFace(this, faceVa);

        return result;
    }

    /**
     * Access the colliding face on shape 2. (native attribute: mShape2Face)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public CsrFace getShape2Face() {
        long shapeResultVa = va();
        long faceVa = getShape2Face(shapeResultVa);
        CsrFace result = new CsrFace(this, faceVa);

        return result;
    }

    /**
     * Identify the face on shape 1 where the collision occurred. The result is
     * unaffected. (native attribute: mSubShapeID1)
     *
     * @return a {@code SubShapeID} value (typically negative)
     */
    @Override
    public int getSubShapeId1() {
        long shapeResultVa = va();
        int result = getSubShapeId1(shapeResultVa);

        return result;
    }

    /**
     * Identify the sub-shape on shape 2 where the collision occurred. The
     * object is unaffected. (native attribute: mSubShapeID2)
     *
     * @return a {@code SubShapeID} value (typically negative)
     */
    @Override
    public int getSubShapeId2() {
        long shapeResultVa = va();
        int result = getSubShapeId2(shapeResultVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static int getBodyId2(long shapeResultVa);

    native private static void getContactPointOn1(
            long shapeResultVa, FloatBuffer storeFloats);

    native private static void getContactPointOn2(
            long shapeResultVa, FloatBuffer storeFloats);

    native private static void getPenetrationAxis(
            long shapeResultVa, FloatBuffer storeFloats);

    native private static float getPenetrationDepth(long shapeResultVa);

    native private static long getShape1Face(long shapeResultVa);

    native private static long getShape2Face(long shapeResultVa);

    native private static int getSubShapeId1(long shapeResultVa);

    native private static int getSubShapeId2(long shapeResultVa);
}
