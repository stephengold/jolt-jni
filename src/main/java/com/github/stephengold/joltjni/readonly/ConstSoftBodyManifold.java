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

import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to a {@code SoftBodyManifold}. (native type: const
 * SoftBodyManifold)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstSoftBodyManifold extends ConstJoltPhysicsObject {
    /**
     * Return the ID of the body with which the specified vertex collided. The
     * manifold is unaffected.
     *
     * @param vertex the vertex to query (not null, unaffected)
     * @return the {@code BodyID} value
     */
    int getContactBodyId(ConstSoftBodyVertex vertex);

    /**
     * Copy the contact normal direction for the specified vertex. The manifold
     * is unaffected.
     *
     * @param vertex the vertex to query (not null, unaffected)
     * @return a new vector
     */
    Vec3 getContactNormal(ConstSoftBodyVertex vertex);

    /**
     * Copy the location of the contact point for the specified vertex. The
     * manifold is unaffected.
     *
     * @param vertex the vertex to query (not null, unaffected)
     * @return a new location vector (in local coordinates)
     */
    Vec3 getLocalContactPoint(ConstSoftBodyVertex vertex);

    /**
     * Count how many sensors are in contact with the soft body. The manifold is
     * unaffected.
     *
     * @return the count (&ge;0)
     */
    int getNumSensorContacts();

    /**
     * Return the ID of the specified sensor contact. The manifold is
     * unaffected. (native method: GetSensorContactBodyID)
     *
     * @param index among the sensor contacts (&ge;0)
     * @return the {@code BodyID} value
     */
    int getSensorContactBodyId(int index);

    /**
     * Enumerate all vertices of the soft body. The manifold is unaffected.
     *
     * @return a new array of new JVM objects with pre-existing native objects
     * assigned
     */
    ConstSoftBodyVertex[] getVertices();

    /**
     * Test whether the specified vertex collided with something in this update.
     * The manifold is unaffected.
     *
     * @param vertex the vertex to query (not null, unaffected)
     * @return {@code true} if it collided, otherwise {@code false}
     */
    boolean hasContact(ConstSoftBodyVertex vertex);
}
