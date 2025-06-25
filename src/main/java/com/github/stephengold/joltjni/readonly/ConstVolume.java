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

/**
 * Read-only access to an {@code Volume}. (native type:
 * {@code const SoftBodySharedSettings::Volume})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstVolume extends ConstJoltPhysicsObject {
    /**
     * Return the inverse of the volume's stiffness. The volume is unaffected.
     *
     * @return the compliance value
     */
    float getCompliance();

    /**
     * Return the rest size of the volume. The volume is unaffected.
     *
     * @return 6 times the rest volume of the tetrahedron (in cubic meters)
     */
    float getSixRestVolume();

    /**
     * Return the mesh vertex at the specified corner. The volume is unaffected.
     *
     * @param indexInVolume which corner of the volume (0 or 1 or 2 or 3)
     * @return the mesh index of the vertex (&ge;0)
     */
    int getVertex(int indexInVolume);
}
