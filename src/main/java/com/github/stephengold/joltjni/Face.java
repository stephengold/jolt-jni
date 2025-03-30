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

import com.github.stephengold.joltjni.readonly.ConstFace;

/**
 * A triangular element on the surface of a soft body. (native type:
 * {@code SoftBodySharedSettings::Face})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Face extends JoltPhysicsObject implements ConstFace {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default face.
     */
    public Face() {
        long faceVa = createDefault();
        setVirtualAddress(faceVa, () -> free(faceVa));
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param faceVa the virtual address of the native object to assign (not
     * zero)
     */
    Face(JoltPhysicsObject container, long faceVa) {
        super(container, faceVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Assign the specified material to the face. (native attribute:
     * mMaterialIndex)
     *
     * @param material the index of the desired material (&ge;0, default=0)
     */
    public void setMaterialIndex(int material) {
        long faceVa = va();
        setMaterialIndex(faceVa, material);
    }

    /**
     * Assign the specified mesh vertex to the face. (native attribute: mVertex)
     *
     * @param indexInFace which corner of the triangle (0 or 1 or 2)
     * @param indexInMesh the index of the vertex to assign (&ge;0)
     */
    public void setVertex(int indexInFace, int indexInMesh) {
        long faceVa = va();
        setVertex(faceVa, indexInFace, indexInMesh);
    }
    // *************************************************************************
    // ConstFace methods

    /**
     * Return the material index. The face is unaffected. (native attribute:
     * mMaterialIndex)
     *
     * @return the index of the material in the body's shared settings (&ge;0)
     */
    @Override
    public int getMaterialIndex() {
        long faceVa = va();
        int result = getMaterialIndex(faceVa);

        return result;
    }

    /**
     * Return the mesh vertex at the specified corner. The face is unaffected.
     * (native attribute: mVertex)
     *
     * @param indexInFace which corner of the face (0 or 1 or 2)
     * @return the mesh index of the vertex (&ge;0)
     */
    @Override
    public int getVertex(int indexInFace) {
        long faceVa = va();
        int result = getVertex(faceVa, indexInFace);

        return result;
    }

    /**
     * Test whether the face is degenerate. The face is unaffected.
     *
     * @return {@code true} if degenerate, otherwise {@code false}
     */
    @Override
    public boolean isDegenerate() {
        long faceVa = va();
        boolean result = isDegenerate(faceVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createDefault();

    native private static void free(long faceVa);

    native private static int getMaterialIndex(long faceVa);

    native private static int getVertex(long faceVa, int indexInFace);

    native private static boolean isDegenerate(long faceVa);

    native private static void setMaterialIndex(long faceVa, int material);

    native private static void setVertex(
            long faceVa, int indexInFace, int indexInMesh);
}
