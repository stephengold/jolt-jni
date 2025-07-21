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

import com.github.stephengold.joltjni.readonly.ConstIndexedTriangle;

/**
 * A triangle composed of three 32-bit mesh-vertex indices and a material index.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class IndexedTriangle
        extends IndexedTriangleNoMaterial
        implements ConstIndexedTriangle {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a triangle with all indices zero.
     */
    public IndexedTriangle() {
        this(0, 0, 0, 0);
    }

    /**
     * Instantiate a triangle with the specified indices.
     *
     * @param vi0 the desired first mesh-vertex index
     * @param vi1 the desired 2nd mesh-vertex index
     * @param vi2 the desired 3rd mesh-vertex index
     */
    public IndexedTriangle(int vi0, int vi1, int vi2) {
        this(vi0, vi1, vi2, 0);
    }

    /**
     * Instantiate a triangle with the specified indices.
     *
     * @param vi0 the desired first mesh-vertex index
     * @param vi1 the desired 2nd mesh-vertex index
     * @param vi2 the desired 3rd mesh-vertex index
     * @param materialIndex the desired material index
     */
    public IndexedTriangle(int vi0, int vi1, int vi2, int materialIndex) {
        super(false);
        long triangleVa = createIndexedTriangle(vi0, vi1, vi2, materialIndex);
        setVirtualAddressAsOwner(triangleVa);
    }

    /**
     * Instantiate a triangle with the specified native object assigned but not
     * owned.
     *
     * @param triangleVa the virtual address of the native object to assign (not
     * zero)
     */
    IndexedTriangle(long triangleVa) {
        super(triangleVa);
    }
    // *************************************************************************
    // new public methods

    /**
     * Alter the triangle's material index. (native attribute: mMaterialIndex)
     *
     * @param materialIndex the desired material index
     */
    public void setMaterialIndex(int materialIndex) {
        long triangleVa = va();
        setMaterialIndex(triangleVa, materialIndex);
    }
    // *************************************************************************
    // ConstIndexedTriangle methods.

    /**
     * Return the triangle's material index. The triangle is unaffected. (native
     * attribute: mMaterialIndex)
     *
     * @return the index
     */
    @Override
    public int getMaterialIndex() {
        long triangleVa = va();
        int result = getMaterialIndex(triangleVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createIndexedTriangle(
            int vi0, int vi1, int vi2, int materialIndex);

    native private static int getMaterialIndex(long triangleVa);

    native private static void setMaterialIndex(
            long triangleVa, int materialIndex);
}
