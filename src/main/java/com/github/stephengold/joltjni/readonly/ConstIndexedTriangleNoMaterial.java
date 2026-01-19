/*
Copyright (c) 2025-2026 Stephen Gold

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

import java.nio.IntBuffer;

/**
 * Read-only access to an {@code IndexedTriangleNoMaterial}. (native type: const
 * IndexedTriangleNoMaterial)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstIndexedTriangleNoMaterial extends ConstJoltPhysicsObject {
    /**
     * Return the mesh-vertex index of the specified corner.
     *
     * @param cornerIndex which corner to access (0, 1, or 2)
     * @return the mesh-vertex index
     */
    int getIdx(int cornerIndex);

    /**
     * Write all 3 indices to the specified buffer and advance the buffer's
     * position by 3. The triangle is unaffected.
     *
     * @param storeBuffer the destination buffer (not {@code null})
     */
    void put(IntBuffer storeBuffer);
}
