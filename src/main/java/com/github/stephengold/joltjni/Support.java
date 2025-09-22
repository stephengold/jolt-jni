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

import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * Interface to the Gilbert–Johnson–Keerthi (GJK) distance algorithm. (native
 * type: {@code ConvexShape::Support})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Support extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param supportVa the virtual address of the native object to assign (not
     * zero)
     */
    Support(JoltPhysicsObject container, long supportVa) {
        super(container, supportVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the convex radius.
     *
     * @return the added distance (in meters)
     */
    public float getConvexRadius() {
        long supportVa = va();
        float result = getConvexRadius(supportVa);

        return result;
    }

    /**
     * Calculate a support vector for the specified direction.
     *
     * @param dx the X component of the input direction
     * @param dy the Y component of the input direction
     * @param dz the Z component of the input direction
     * @return a new location vector relative to the shape's center of mass
     */
    public Vec3 getSupport(float dx, float dy, float dz) {
        long supportVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getSupport(supportVa, dx, dy, dz, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Calculate a support vector for the specified direction.
     *
     * @param direction the input direction (not null, unaffected)
     * @return a new location vector relative to the shape's center of mass
     */
    public Vec3 getSupport(Vec3Arg direction) {
        float dx = direction.getX();
        float dy = direction.getY();
        float dz = direction.getZ();
        Vec3 result = getSupport(dx, dy, dz);

        return result;
    }

    /**
     * Calculate support vectors for the specified directions.
     *
     * @param directions the input directions (not null, capacity a multiple of
     * 3, unaffected)
     * @param storePoints storage for location vectors relative to the shape's
     * center of mass (not null, same capacity as {@code directions})
     */
    public void getSupportBulk(
            FloatBuffer directions, FloatBuffer storePoints) {
        long supportVa = va();
        int capacity = directions.capacity();
        assert (capacity % 3) == 0 : "capacity = " + capacity;
        assert storePoints.capacity() == capacity;
        getSupportBulk(supportVa, directions, storePoints);
    }
    // *************************************************************************
    // native private methods

    native private static float getConvexRadius(long supportVa);

    native private static void getSupport(long supportVa,
            float dx, float dy, float dz, FloatBuffer storeFloats);

    native private static void getSupportBulk(
            long supportVa, FloatBuffer directions, FloatBuffer storePoints);
}
