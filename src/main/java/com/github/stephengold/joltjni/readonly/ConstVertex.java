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

/**
 * Read-only access to a {@code Vertex}. (native type:
 * {@code const SoftBodySharedSettings::Vertex})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstVertex extends ConstJoltPhysicsObject {
    /**
     * Return the inverse mass. The vertex is unaffected.
     *
     * @return the inverse of the mass (in 1/kilograms)
     */
    float getInvMass();

    /**
     * Copy the initial location. The vertex is unaffected.
     *
     * @return a new location vector
     */
    Float3 getPosition();

    /**
     * Copy the initial velocity. The vertex is unaffected.
     *
     * @return a new velocity vector (in meters per second)
     */
    Float3 getVelocity();
}
