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

import com.github.stephengold.joltjni.readonly.ConstVertexList;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * A variable-length list of 3-D vectors, implemented using a direct
 * {@code FloatBuffer}. (native type: {@code Array<Float3>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class VertexList implements ConstVertexList {
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
            // Android SDK requires the following cast:
            FloatBuffer oldBuffer = (FloatBuffer) buffer.rewind();
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
     * @param x the X-coordinate to store
     * @param y the Y-coordinate to store
     * @param z the Z-coordinate to store
     */
    public void set(int vertexIndex, float x, float y, float z) {
        if (!allowModification) {
            throw new IllegalStateException(
                    "Modification is no longer allowed.");
        }

        buffer.put(numAxes * vertexIndex, x);
        buffer.put(numAxes * vertexIndex + 1, y);
        buffer.put(numAxes * vertexIndex + 2, z);
    }

    /**
     * Store the specified vertex location at the specified index.
     *
     * @param vertexIndex an index in the list (&ge;0, &lt;size)
     * @param location the data to store (not null, unaffected)
     */
    public void set(int vertexIndex, Float3 location) {
        set(vertexIndex, location.x, location.y, location.z);
    }

    /**
     * Store the specified vertex location at the specified index.
     *
     * @param vertexIndex an index in the list (&ge;0, &lt;size)
     * @param location the data to store (not null, unaffected)
     */
    public void set(int vertexIndex, Vec3Arg location) {
        set(vertexIndex, location.getX(), location.getY(), location.getZ());
    }
    // *************************************************************************
    // ConstVertexList methods

    /**
     * Count how many vertices can be held in the currently allocated storage.
     * The list is unaffected.
     *
     * @return the number of vertices (&ge;0)
     */
    @Override
    public int capacity() {
        int result = buffer.capacity() / numAxes;
        return result;
    }

    /**
     * Test whether the list contains no vertices. The list is unaffected.
     *
     * @return {@code true} if empty, otherwise {@code false}
     */
    @Override
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
    @Override
    public Float3 get(int listIndex) {
        float x = buffer.get(numAxes * listIndex);
        float y = buffer.get(numAxes * listIndex + 1);
        float z = buffer.get(numAxes * listIndex + 2);
        Float3 result = new Float3(x, y, z);

        return result;
    }

    /**
     * Count how many vertices are in the list. The list is unaffected.
     *
     * @return the number of vertices (&ge;0)
     */
    @Override
    public int size() {
        int result = buffer.limit() / numAxes;
        return result;
    }

    /**
     * Access a direct buffer containing all vertices in the list, in order. No
     * further {@code resize()} or {@code set()} is allowed.
     *
     * @return the pre-existing Buffer, flipped but possibly not rewound
     */
    @Override
    public FloatBuffer toDirectBuffer() {
        this.allowModification = false;
        return buffer;
    }
}
