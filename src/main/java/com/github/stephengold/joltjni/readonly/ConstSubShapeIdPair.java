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

/**
 * Read-only access to a {@code SubShapeIdPair}. (native type: const
 * SubShapeIDPair)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstSubShapeIdPair extends ConstJoltPhysicsObject {
    /**
     * Return the ID of the first body. The pair is unaffected.
     *
     * @return the {@code BodyID} value
     */
    int getBody1Id();

    /**
     * Return the ID of the 2nd body. The pair is unaffected.
     *
     * @return the {@code BodyID} value
     */
    int getBody2Id();

    /**
     * Return the hashcode for the pair. The pair is unaffected.
     *
     * @return the value
     */
    long getHash();

    /**
     * Return the ID of the first sub-shape. The pair is unaffected.
     *
     * @return a {@code SubShapeID} value (typically negative)
     */
    int getSubShapeId1();

    /**
     * Return the ID of the 2nd sub-shape. The pair is unaffected.
     *
     * @return a {@code SubShapeID} value (typically negative)
     */
    int getSubShapeId2();
}
