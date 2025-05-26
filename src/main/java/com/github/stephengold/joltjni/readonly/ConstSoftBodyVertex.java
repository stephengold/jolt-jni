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

import com.github.stephengold.joltjni.Plane;
import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to {@code SoftBodyVertex}. (native type: const
 * SoftBodyMotionProperties::Vertex)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstSoftBodyVertex extends ConstJoltPhysicsObject {
    /**
     * Return the index of the colliding shape. The vertex is unaffected.
     *
     * @return the index
     */
    int getCollidingShapeIndex();

    /**
     * Copy the collision plane. The vertex is unaffected.
     *
     * @return a new object
     */
    Plane getCollisionPlane();

    /**
     * Return the inverse mass. The vertex is unaffected.
     *
     * @return the inverse of the mass (in 1/kilograms)
     */
    float getInvMass();

    /**
     * Return the amount of penetration. The vertex is unaffected.
     *
     * @return the depth (in meters)
     */
    float getLargestPenetration();

    /**
     * Copy the location. The vertex is unaffected.
     *
     * @return a new location vector (relative to the body's center of mass)
     */
    Vec3 getPosition();

    /**
     * Copy the velocity. The vertex is unaffected.
     *
     * @return a new velocity vector (relative to the body's center of mass, in
     * meters per second)
     */
    Vec3 getVelocity();

    /**
     * Test whether the vertex collided during the previous update. The vertex
     * is unaffected.
     *
     * @return {@code true} if it collided, otherwise {@code false}
     */
    boolean hasContact();
}
