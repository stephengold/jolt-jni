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

import com.github.stephengold.joltjni.readonly.ConstSphere;
import com.github.stephengold.joltjni.readonly.Mat44Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * A sphere combined with a coordinate transform. (native type:
 * {@code TransformedConvexObject<Sphere>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class TransformedSphere extends TransformedConvexObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an object for the specified transform and sphere.
     *
     * @param transform the desired coordinate transform (not null, uniform
     * scaling, unaffected)
     * @param sphere the desired base sphere (not null, unaffected)
     */
    public TransformedSphere(Mat44Arg transform, ConstSphere sphere) {
        long transformVa = transform.targetVa();
        long sphereVa = sphere.targetVa();
        long objectVa = create(transformVa, sphereVa);
        setVirtualAddress(objectVa, () -> free(objectVa));
    }
    // *************************************************************************
    // ConstTransformedConvexObject methods

    /**
     * Calculate the support vector for the specified direction. The convex
     * object is unaffected.
     *
     * @param direction the direction to use (not null, unaffected)
     * @return a new location vector
     */
    @Override
    public Vec3 getSupport(Vec3Arg direction) {
        long objectVa = va();
        FloatBuffer floatBuffer = Temporaries.floatBuffer1.get();
        direction.copyTo(floatBuffer);
        getSupport(objectVa, floatBuffer);
        Vec3 result = new Vec3(floatBuffer);

        return result;
    }

    /**
     * Copy the coordinate transform. The convex object is unaffected. (native
     * member: mTransform)
     *
     * @return a new transform matrix
     */
    @Override
    public Mat44 getTransform() {
        long objectVa = va();
        long resultVa = getTransform(objectVa);
        Mat44 result = new Mat44(resultVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long create(long transformVa, long sphereVa);

    native private static void free(long objectVa);

    native private static void getSupport(
            long objectVa, FloatBuffer floatBuffer);

    native private static long getTransform(long objectVa);
}
