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

import java.nio.FloatBuffer;

/**
 * A variable-length list of {@code Float3} vertices, implemented using a direct
 * {@code FloatBuffer}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class VertexList {
    // *************************************************************************
    // constants

    /**
     * number of floats in a vertex
     */
    final private static int numAxes = 3;
    // *************************************************************************
    // fields

    /**
     * true if until {@code toBuffer()} is invoked
     */
    private boolean allowModification = true;
    /**
     * direct buffer to hold the {@code Float3} vertices (usually flipped, limit
     * and capacity each a multiple of 3)
     */
    private FloatBuffer buffer;
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty list.
     */
    public VertexList() {
        this.buffer = Jolt.newDirectFloatBuffer(99);
        buffer.flip();
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Count how many vertices can be held in the currently allocated storage.
     * The list is unaffected.
     *
     * @return the number of vertices (&ge;0)
     */
    public int capacity() {
        int result = buffer.capacity() / numAxes;
        return result;
    }

    /**
     * Test whether the list contains no vertices. The list is unaffected.
     *
     * @return true if empty, otherwise false
     */
    public boolean empty() {
        if (buffer.limit() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Copy the vertex at the specified index. The list is unaffected.
     *
     * @param listIndex the index from which to get the vertex
     * @return the vertex
     */
    public Float3 get(int listIndex) {
        float x = buffer.get(numAxes * listIndex);
        float y = buffer.get(numAxes * listIndex + 1);
        float z = buffer.get(numAxes * listIndex + 2);
        Float3 result = new Float3(x, y, z);

        return result;
    }

    /**
     * Append the specified vertex to the end.
     *
     * @param location the vertex to append (not null, unaffected)
     */
    public void pushBack(Float3 location) {
        int numElements = size();
        resize(numElements + 1);
        set(numElements, location);
    }

    /**
     * Expand or truncate the list, initializing any new vertices to (0,0,0).
     *
     * @param numVertices the desired size (number of vertices)
     */
    public void resize(int numVertices) {
        if (!allowModification) {
            throw new IllegalStateException(
                    "Modification is no longer allowed.");
        }

        int numFloats = numVertices * numAxes;
        if (numFloats > buffer.capacity()) {
            FloatBuffer oldBuffer = buffer.rewind();
            this.buffer = Jolt.newDirectFloatBuffer(numFloats);
            while (oldBuffer.hasRemaining()) {
                float x = oldBuffer.get();
                buffer.put(x);
            }
            buffer.flip();
        }
        buffer.limit(numFloats);
    }

    /**
     * Store the specified vertex location at the specified index.
     *
     * @param vertexIndex an index in the list (&ge;0, &lt;size)
     * @param location the data to store (not null, unaffected)
     */
    public void set(int vertexIndex, Float3 location) {
        if (!allowModification) {
            throw new IllegalStateException(
                    "Modification is no longer allowed.");
        }

        buffer.put(numAxes * vertexIndex, location.x);
        buffer.put(numAxes * vertexIndex + 1, location.y);
        buffer.put(numAxes * vertexIndex + 2, location.z);
    }

    /**
     * Count how many vertices are in the list. The list is unaffected.
     *
     * @return the number of vertices (&ge;0)
     */
    public int size() {
        int result = buffer.limit() / numAxes;
        return result;
    }

    /**
     * Access a direct Buffer containing all vertices in the list, in order. No
     * further {@code resize()} or {@code set()} is allowed.
     *
     * @return the pre-existing Buffer, flipped but possibly not rewound
     */
    public FloatBuffer toDirectBuffer() {
        this.allowModification = false;
        return buffer;
    }
}
