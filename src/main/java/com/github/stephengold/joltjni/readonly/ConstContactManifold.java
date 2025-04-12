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

import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to a {@code ContactManifold}. (native type: const
 * ContactManifold)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstContactManifold extends ConstJoltPhysicsObject {
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the location from which all contact points are measured. The
     * manifold is unaffected.
     *
     * @return a new location vector
     */
    RVec3 getBaseOffset();

    /**
     * Return the penetration depth: the distance to move body 2 out of
     * collision. The manifold is unaffected.
     *
     * @return the signed distance (negative for a speculative contact)
     */
    float getPenetrationDepth();

    /**
     * Return the ID of the first sub-shape that formed the manifold. The
     * manifold is unaffected.
     *
     * @return a {@code SubShapeID} value
     */
    int getSubShapeId1();

    /**
     * Return the ID of the 2nd sub-shape that formed the manifold. The manifold
     * is unaffected.
     *
     * @return a {@code SubShapeID} value
     */
    int getSubShapeId2();

    /**
     * Copy the normal: the direction to move body 2 out of collision. The
     * manifold is unaffected.
     *
     * @return a new direction vector (in system coordinates)
     */
    Vec3 getWorldSpaceNormal();
}
