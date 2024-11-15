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

import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Add convex radius to a support function. (native type:
 * {@code AddConvexRadius<ConvexShape::Support>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class AddConvexRadiusSupport extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate for the specified support function and convex radius.
     *
     * @param support the support function to use (not null)
     * @param convexRadius the desired convex radius
     */
    public AddConvexRadiusSupport(Support support, float convexRadius) {
        long supportVa = support.va();
        long addVa = createAdd(supportVa, convexRadius);
        setVirtualAddress(addVa, () -> free(addVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Evaluate the augmented support function for the specified direction.
     *
     * @param direction the direction to use (not null, unaffected)
     * @return a new offset vector
     */
    public Vec3 getSupport(Vec3Arg direction) {
        long addVa = va();
        float dx = direction.getX();
        float dy = direction.getY();
        float dz = direction.getZ();
        float[] storeFloats = new float[3];
        getSupport(addVa, dx, dy, dz, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createAdd(long supportVa, float radius);

    native private static void free(long addVa);

    native private static void getSupport(
            long addVa, float dx, float dy, float dz, float[] storeFloats);
}
