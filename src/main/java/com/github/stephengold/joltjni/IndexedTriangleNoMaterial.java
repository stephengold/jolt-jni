/*
Copyright (c) 2024-2026 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.ConstIndexedTriangleNoMaterial;
import java.nio.IntBuffer;

/**
 * A triangle composed of three 32-bit mesh-vertex indices.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class IndexedTriangleNoMaterial
        extends JoltPhysicsObject
        implements ConstIndexedTriangleNoMaterial {
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
     * @param dummy unused argument to distinguish from the zero-arg constructor
     */
    IndexedTriangleNoMaterial(boolean dummy) {
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
        setVirtualAddress(triangleVa, () -> free(triangleVa));
    }

    /**
     * Instantiate a triangle with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param triangleVa the virtual address of the native object to assign (not
     * zero)
     */
    IndexedTriangleNoMaterial(JoltPhysicsObject container, long triangleVa) {
        super(container, triangleVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the mesh-vertex index of the specified corner.
     *
     * @param cornerIndex which corner to modify (0, 1, or 2)
     * @param meshVertexIndex the index of the desired mesh vertex
     */
    public void setIdx(int cornerIndex, int meshVertexIndex) {
        long triangleVa = va();
        setIdx(triangleVa, cornerIndex, meshVertexIndex);
    }
    // *************************************************************************
    // ConstIndexedTriangleNoMaterial methods

    /**
     * Return the mesh-vertex index of the specified corner.
     *
     * @param cornerIndex which corner to access (0, 1, or 2)
     * @return the index of the vertex in the mesh
     */
    @Override
    public int getIdx(int cornerIndex) {
        long triangleVa = va();
        int result = getIdx(triangleVa, cornerIndex);
        return result;
    }

    /**
     * Write all 3 indices to the specified buffer and advance the buffer's
     * position by 3. The triangle is unaffected.
     *
     * @param storeBuffer the destination buffer (not {@code null})
     */
    @Override
    public void put(IntBuffer storeBuffer) {
        long triangleVa = va();
        int vi0 = getIdx(triangleVa, 0);
        storeBuffer.put(vi0);
        int vi1 = getIdx(triangleVa, 1);
        storeBuffer.put(vi1);
        int vi2 = getIdx(triangleVa, 2);
        storeBuffer.put(vi2);
    }
    // *************************************************************************
    // new protected methods

    /**
     * Assign a native object (assuming there's none already assigned) and
     * designate the JVM object as the owner.
     *
     * @param triangleVa the virtual address of the native object to assign (not
     * zero)
     */
    final void setVirtualAddressAsOwner(long triangleVa) {
        Runnable freeingAction = () -> free(triangleVa);
        setVirtualAddress(triangleVa, freeingAction);
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
