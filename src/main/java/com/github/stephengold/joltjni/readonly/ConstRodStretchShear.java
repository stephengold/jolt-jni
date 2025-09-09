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

import com.github.stephengold.joltjni.Quat;

/**
 * Read-only access to a {@code RodStretchShear}. (native type:
 * {@code const SoftBodySharedSettings::RodStretchShear})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstRodStretchShear extends ConstJoltPhysicsObject {
    /**
     * Return the Bishop frame of the rod. The rod is unaffected.
     *
     * @return orientation of the rod
     */
    Quat getBishop();

    /**
     * Return the inverse of the spring's stiffness. The rod is unaffected.
     *
     * @return the compliance value
     */
    float getCompliance();

    /**
     * Return the inverse mass of the rod. The rod is unaffected.
     *
     * @return the inverse of the mass (in 1/kilograms)
     */
    float getInvMass();

    /**
     * Return the length of the rod. The rod is unaffected.
     *
     * @return the length (in meters)
     */
    float getLength();

    /**
     * Return the mesh vertex at the specified end. The rod is unaffected.
     *
     * @param indexInRod which end of the rod (0 or 1)
     * @return the mesh index of the vertex (&ge;0)
     */
    int getVertex(int indexInRod);
}
