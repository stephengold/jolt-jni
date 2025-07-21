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
package com.github.stephengold.joltjni.readonly;

import java.nio.FloatBuffer;

/**
 * Read-only access to an {@code Triangle}. (native type: const Triangle)
 *
 * @author wil
 */
public interface ConstTriangle extends ConstJoltPhysicsObject {
    /**
     * Return the triangle's material index. The triangle is unaffected.
     *
     * @return the index
     */
    int getMaterialIndex();

    /**
     * Return the triangle's user data. The triangle is unaffected.
     *
     * @return the value
     */
    int getUserData();

    /**
     * Write the vertex locations to the specified buffer and advance the
     * buffer's position by 9. The triangle is unaffected.
     *
     * @param storeBuffer the destination buffer (not null)
     */
    void putVertices(FloatBuffer storeBuffer);
}
