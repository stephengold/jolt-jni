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

import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to a {@code CollideShapeResult}. (native type: const
 * CollideShapeResult)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstCollideShapeResult extends ConstJoltPhysicsObject {
    /**
     * Identify the body to which shape 2 belongs. The object is unaffected.
     *
     * @return the {@code BodyID} value
     */
    int getBodyId2();

    /**
     * Copy the contact location on the surface of shape 1. The object is
     * unaffected.
     *
     * @return a new location vector
     */
    Vec3 getContactPointOn1();

    /**
     * Copy the contact location on the surface of shape 2. The object is
     * unaffected.
     *
     * @return a new location vector
     */
    Vec3 getContactPointOn2();

    /**
     * Copy the direction to move shape 2 out of collision along the shortest
     * path. The object is unaffected.
     *
     * @return a new direction vector in system coordinates
     */
    Vec3 getPenetrationAxis();

    /**
     * Return the distance to move shape 2 to resolve the collision. The object
     * is unaffected.
     *
     * @return the signed distance
     */
    float getPenetrationDepth();

    /**
     * Identify the face on shape 1 where the collision occurred. The object is
     * unaffected.
     *
     * @return a {@code SubShapeID} value (typically negative)
     */
    int getSubShapeId1();

    /**
     * Identify the face on shape 2 where the collision occurred. The object is
     * unaffected.
     *
     * @return a {@code SubShapeID} value (typically negative)
     */
    int getSubShapeId2();
}
