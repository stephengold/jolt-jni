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
import java.nio.FloatBuffer;

/**
 * A non-indexed triangle with a material index.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class Triangle extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default triangle.
     */
    public Triangle() {
        long triangleVa = createDefaultTriangle();
        setVirtualAddress(triangleVa, () -> free(triangleVa));
    }

    /**
     * Instantiate a triangle from {@code Float3} locations.
     *
     * @param v1 the desired location of the first vertex (not null, unaffected)
     * @param v2 the desired location of the 2nd vertex (not null, unaffected)
     * @param v3 the desired location of the 3rd vertex (not null, unaffected)
     */
    public Triangle(Float3 v1, Float3 v2, Float3 v3) {
        this(v1, v2, v3, 0);
    }

    /**
     * Instantiate the specified triangle
     *
     * @param v1 the desired location of the first vertex (not null, unaffected)
     * @param v2 the desired location of the 2nd vertex (not null, unaffected)
     * @param v3 the desired location of the 3rd vertex (not null, unaffected)
     * @param materialIndex the desired material index
     */
    public Triangle(Float3 v1, Float3 v2, Float3 v3, int materialIndex) {
        this(v1, v2, v3, materialIndex, 0);
    }

    /**
     * Instantiate the specified triangle
     *
     * @param v1 the desired location of the first vertex (not null, unaffected)
     * @param v2 the desired location of the 2nd vertex (not null, unaffected)
     * @param v3 the desired location of the 3rd vertex (not null, unaffected)
     * @param materialIndex the desired material index
     * @param userData the desired user data
     */
    public Triangle(
            Float3 v1, Float3 v2, Float3 v3, int materialIndex, int userData) {
        long triangleVa = createTriangle(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z,
                v3.x, v3.y, v3.z, materialIndex, userData);
        setVirtualAddress(triangleVa, () -> free(triangleVa));
    }

    /**
     * Instantiate a triangle from {@code Float3} locations.
     *
     * @param v1 the desired location of the first vertex (not null, unaffected)
     * @param v2 the desired location of the 2nd vertex (not null, unaffected)
     * @param v3 the desired location of the 3rd vertex (not null, unaffected)
     */
    public Triangle(Vec3Arg v1, Vec3Arg v2, Vec3Arg v3) {
        this(v1, v2, v3, 0);
    }

    /**
     * Instantiate the specified triangle
     *
     * @param v1 the desired location of the first vertex (not null, unaffected)
     * @param v2 the desired location of the 2nd vertex (not null, unaffected)
     * @param v3 the desired location of the 3rd vertex (not null, unaffected)
     * @param materialIndex the desired material index
     */
    public Triangle(Vec3Arg v1, Vec3Arg v2, Vec3Arg v3, int materialIndex) {
        this(v1, v2, v3, materialIndex, 0);
    }

    /**
     * Instantiate the specified triangle
     *
     * @param v1 the desired location of the first vertex (not null, unaffected)
     * @param v2 the desired location of the 2nd vertex (not null, unaffected)
     * @param v3 the desired location of the 3rd vertex (not null, unaffected)
     * @param materialIndex the desired material index
     * @param userData the desired user data
     */
    public Triangle(Vec3Arg v1, Vec3Arg v2, Vec3Arg v3, int materialIndex,
            int userData) {
        float v1x = v1.getX();
        float v1y = v1.getY();
        float v1z = v1.getZ();
        float v2x = v2.getX();
        float v2y = v2.getY();
        float v2z = v2.getZ();
        float v3x = v3.getX();
        float v3y = v3.getY();
        float v3z = v3.getZ();
        long triangleVa = createTriangle(v1x, v1y, v1z, v2x, v2y, v2z,
                v3x, v3y, v3z, materialIndex, userData);
        setVirtualAddress(triangleVa, () -> free(triangleVa));
    }
    // *************************************************************************
    // new public methods

    /**
     * Return the triangle's material index. The triangle is unaffected. (native
     * attribute: mMaterialIndex)
     *
     * @return the index
     */
    public int getMaterialIndex() {
        long triangleVa = va();
        int result = getMaterialIndex(triangleVa);

        return result;
    }

    /**
     * Return the triangle's user data. The triangle is unaffected. (native
     * attribute: mUserData)
     *
     * @return the value
     */
    public int getUserData() {
        long triangleVa = va();
        int result = getUserData(triangleVa);

        return result;
    }

    /**
     * Write the vertex locations to a buffer and advance the buffer's position
     * by 9.
     *
     * @param storeBuffer the destination buffer (not null)
     */
    public void putVertices(FloatBuffer storeBuffer) {
        long triangleVa = va();
        for (int vertexIndex = 0; vertexIndex < 3; ++vertexIndex) {
            for (int axisIndex = 0; axisIndex < 3; ++axisIndex) {
                float f = getCoordinate(triangleVa, vertexIndex, axisIndex);
                storeBuffer.put(f);
            }
        }
    }

    /**
     * Alter the triangle's material index. (native attribute: mMaterialIndex)
     *
     * @param materialIndex the desired material index
     */
    public void setMaterialIndex(int materialIndex) {
        long triangleVa = va();
        setMaterialIndex(triangleVa, materialIndex);
    }

    /**
     * Alter the triangle's user data. (native attribute: mUserData)
     *
     * @param value the desired value
     */
    public void setUserData(int value) {
        long triangleVa = va();
        setUserData(triangleVa, value);
    }
    // *************************************************************************
    // native private methods

    native private static long createDefaultTriangle();

    native private static long createTriangle(float v1x, float v1y, float v1z,
            float v2x, float v2y, float v2z, float v3x, float v3y, float v3z,
            int materialIndex, int userData);

    native private static void free(long triangleVa);

    native private static float getCoordinate(
            long triangleVa, int vertexIndex, int axisIndex);

    native private static int getMaterialIndex(long triangleVa);

    native private static int getUserData(long triangleVa);

    native private static void setMaterialIndex(
            long triangleVa, int materialIndex);

    native private static void setUserData(long triangleVa, int userData);
}
