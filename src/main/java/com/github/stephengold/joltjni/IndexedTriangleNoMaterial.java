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

/**
 * A triangle composed of three 32-bit mesh-vertex indices.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class IndexedTriangleNoMaterial extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a triangle with all indices zero.
     */
    public IndexedTriangleNoMaterial() {
        this(0, 0, 0);
    }

    /**
     * Instantiate a triangle with no native object assigned.
     *
     * @param unused to distinguish from the no-arg constructor
     */
    protected IndexedTriangleNoMaterial(boolean unused) {
    }

    /**
     * Instantiate a triangle with the specified indices.
     *
     * @param vi0 the desired first mesh-vertex index
     * @param vi1 the desired 2nd mesh-vertex index
     * @param vi2 the desired 3rd mesh-vertex index
     */
    public IndexedTriangleNoMaterial(int vi0, int vi1, int vi2) {
        long triangleVa = createIndexedTriangleNoMaterial(vi0, vi1, vi2);
        setVirtualAddress(triangleVa, true);
    }

    /**
     * Instantiate a triangle with the specified native object assigned.
     *
     * @param triangleVa the virtual address of the native object to assign (not
     * zero)
     */
    protected IndexedTriangleNoMaterial(long triangleVa) {
        super(triangleVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the mesh-vertex index of the specified corner.
     *
     * @param cornerIndex which corner to access (0, 1, or 2)
     * @return the mesh-vertex index
     */
    public int getIdx(int cornerIndex) {
        long triangleVa = va();
        int result = getIdx(triangleVa, cornerIndex);
        return result;
    }

    /**
     * Alter the mesh-vertex index of the specified corner.
     *
     * @param cornerIndex which corner to modify (0, 1, or 2)
     * @param meshVertexIndex the desired mesh-vertex index
     */
    public void setIdx(int cornerIndex, int meshVertexIndex) {
        long triangleVa = va();
        setIdx(triangleVa, cornerIndex, meshVertexIndex);
    }
    // *************************************************************************
    // JoltPhysicsObject methods

    /**
     * Unassign the assigned native object, assuming there is one. Free the
     * native object if the current triangle owns it.
     */
    @Override
    public void close() {
        if (ownsNativeObject()) {
            long triangleVa = va();
            free(triangleVa);
        }

        unassignNativeObject();
    }
    // *************************************************************************
    // native private methods

    native private static long createIndexedTriangleNoMaterial(
            int vi0, int vi1, int vi2);

    native private static void free(long triangleVa);

    native private static int getIdx(long triangleVa, int cornerIndex);

    native private static void setIdx(
            long triangleVa, int cornerIndex, int meshVertexIndex);
}
