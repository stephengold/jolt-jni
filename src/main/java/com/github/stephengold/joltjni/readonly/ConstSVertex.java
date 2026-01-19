/*
Copyright (c) 2026 Stephen Gold

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
import com.github.stephengold.joltjni.Float4;

/**
 * Read-only access to an {@code SVertex}. (native type:
 * {@code const HairSettings::SVertex})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstSVertex extends ConstJoltPhysicsObject {
    /**
     * Copy the bishop frame of the strand in its modeled pose, computed by
     * {@code init()}. The vertex is unaffected.
     *
     * @return a new vector
     */
    Float4 getBishop();

    /**
     * Return the inverse of the mass. The vertex is unaffected.
     *
     * @return the value
     */
    float getInvMass();

    /**
     * Return the initial distance between this vertex from the next one in the
     * unloaded strand, computed by {@code init()}. The vertex is unaffected.
     *
     * @return the distance
     */
    float getLength();

    /**
     * Copy the rotation between the previous rod and this one in the unloaded
     * strand, computed by {@code init()}. The vertex is unaffected.
     *
     * @return a new vector
     */
    Float4 getOmega0();

    /**
     * Copy the initial location of the vertex in its modeled pose. The vertex
     * is unaffected.
     *
     * @return a new vector
     */
    Float3 getPosition();

    /**
     * Return the fraction of the way along the strand, computed by
     * {@code init()}. The vertex is unaffected.
     *
     * @return the fractional distance (0=start, 1=end)
     */
    float getStrandFraction();
}
