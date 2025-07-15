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

import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * A triangle for convex collision detection.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class TriangleConvexSupport extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a copy of the specified triangle.
     *
     * @param original the triangle to copy (not {@code null}, unaffected)
     */
    public TriangleConvexSupport(TriangleConvexSupport original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa, () -> free(copyVa));
    }

    /**
     * Instantiate a triangle from vertex locations.
     *
     * @param v1 the desired location of the first vertex (not null, unaffected)
     * @param v2 the desired location of the 2nd vertex (not null, unaffected)
     * @param v3 the desired location of the 3rd vertex (not null, unaffected)
     */
    public TriangleConvexSupport(Vec3Arg v1, Vec3Arg v2, Vec3Arg v3) {
        float v1x = v1.getX();
        float v1y = v1.getY();
        float v1z = v1.getZ();
        float v2x = v2.getX();
        float v2y = v2.getY();
        float v2z = v2.getZ();
        float v3x = v3.getX();
        float v3y = v3.getY();
        float v3z = v3.getZ();
        long triangleVa = create(v1x, v1y, v1z, v2x, v2y, v2z, v3x, v3y, v3z);
        setVirtualAddress(triangleVa, () -> free(triangleVa));
    }
    // *************************************************************************
    // new public methods

    /**
     * Copy the first vertex. The triangle is unaffected.
     *
     * @return a new vector
     */
    public Vec3 getV1() {
        long triangleVa = va();
        FloatBuffer storeFloats = Jolt.newDirectFloatBuffer(3);
        getVertex(triangleVa, 0, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the 2nd vertex. The triangle is unaffected.
     *
     * @return a new vector
     */
    public Vec3 getV2() {
        long triangleVa = va();
        FloatBuffer storeFloats = Jolt.newDirectFloatBuffer(3);
        getVertex(triangleVa, 1, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the 3rd vertex. The triangle is unaffected.
     *
     * @return a new vector
     */
    public Vec3 getV3() {
        long triangleVa = va();
        FloatBuffer storeFloats = Jolt.newDirectFloatBuffer(3);
        getVertex(triangleVa, 2, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long create(float v1x, float v1y, float v1z,
            float v2x, float v2y, float v2z, float v3x, float v3y, float v3z);

    native private static void free(long triangleVa);

    native private static void getVertex(
            long triangleVa, int vertexIndex, FloatBuffer storeFloats);
}
