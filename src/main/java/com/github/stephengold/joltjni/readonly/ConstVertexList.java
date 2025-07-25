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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.Float3;
import java.nio.FloatBuffer;

/**
 * Read-only access to an {@code VertexList}. (native type: const VertexList)
 *
 * @author wil
 */
public interface ConstVertexList {
    /**
     * Count how many vertices can be held in the currently allocated storage.
     * The list is unaffected.
     *
     * @return the number of vertices (&ge;0)
     */
    int capacity();

    /**
     * Test whether the list contains no vertices. The list is unaffected.
     *
     * @return {@code true} if empty, otherwise {@code false}
     */
    boolean empty();

    /**
     * Copy the vertex at the specified index. The list is unaffected.
     *
     * @param listIndex the index from which to get the vertex
     * @return the vertex
     */
    Float3 get(int listIndex);

    /**
     * Count how many vertices are in the list. The list is unaffected.
     *
     * @return the number of vertices (&ge;0)
     */
    int size();

    /**
     * Access a direct buffer containing all vertices in the list, in order. No
     * further {@code resize()} or {@code set()} is allowed.
     *
     * @return the pre-existing Buffer, flipped but possibly not rewound
     */
    FloatBuffer toDirectBuffer();
}
