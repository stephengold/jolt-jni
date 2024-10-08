/*
Copyright (c) 2024 Stephen Gold

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

import com.github.stephengold.joltjni.enumerate.ELraType;

/**
 * Read-only access to a {@code VertexAttributes} object. (native type: const
 * VertexAttributes)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstVertexAttributes extends ConstJoltPhysicsObject {
    // *************************************************************************
    // new methods exposed

    /**
     * Return the compliance of bend edges. The attributes are unaffected.
     *
     * @return the compliance value
     */
    float getBendCompliance();

    /**
     * Return the compliance of normal/regular edges. The attributes are
     * unaffected.
     *
     * @return the compliance value
     */
    float getCompliance();

    /**
     * Return the multiplier for the maximum distance of the long-range
     * attachment (LRA) constraint. The attributes are unaffected.
     *
     * @return the multiplier (relative to the rest-pose distance)
     */
    float getLraMaxDistanceMultiplier();

    /**
     * Return the type of the long-range attachment (LRA) constraint. T The
     * attributes are unaffected.
     *
     * @return the enum value (not null)
     */
    ELraType getLraType();

    /**
     * Return the compliance of the shear edges. The attributes are unaffected.
     *
     * @return the compliance value
     */
    float getShearCompliance();
}
