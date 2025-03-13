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
package com.github.stephengold.joltjni;

/**
 * A static array of 3-D vectors, backed by a fixed-length buffer. (native type:
 * {@code StaticArray<Vec3,numVectors>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class VertexArray extends JoltPhysicsObject {
    // *************************************************************************
    // constants

    /**
     * capacity (in vectors, &gt;0)
     */
    final private int capacity;
    // *************************************************************************
    // constructors

    /**
     * Instantiate with no native object assigned.
     *
     * @param numVectors the desired capacity (in vectors, &gt;0)
     */
    VertexArray(int numVectors) {
        this.capacity = numVectors;
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param vertexVa the virtual address of the native object to assign (not
     * zero)
     * @param numVectors the desired capacity (in vectors, &gt;0)
     */
    VertexArray(JoltPhysicsObject container, long vertexVa, int numVectors) {
        super(container, vertexVa);
        this.capacity = numVectors;
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the capacity of the array.
     *
     * @return the capacity (in vectors, &gt;0)
     */
    public int capacity() {
        return capacity;
    }
}
