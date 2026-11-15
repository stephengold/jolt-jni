/*
Copyright (c) 2026 Stephen Gold

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
package com.github.stephengold.joltjni.readonly;

/**
 * Read-only access to a {@code MeshShapeSettings} object. (native type: const
 * MeshShapeSettings)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstMeshShapeSettings extends ConstShapeSettings {
    /**
     * Count the triangles in the mesh. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    int countTriangles();

    /**
     * Count the vertices in the mesh. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    int countTriangleVertices();

    /**
     * Return the cosine of the active-edge threshold angle. The settings are
     * unaffected.
     *
     * @return the cosine
     */
    float getActiveEdgeCosThresholdAngle();

    /**
     * Return the maximum number of triangles per leaf. The settings are
     * unaffected.
     *
     * @return the maximum number
     */
    int getMaxTrianglesPerLeaf();

    /**
     * Test whether each triangle will include user data. The settings are
     * unaffected.
     *
     * @return {@code true} if per-triangle data is included, otherwise
     * {@code false}
     */
    boolean getPerTriangleUserData();
}
