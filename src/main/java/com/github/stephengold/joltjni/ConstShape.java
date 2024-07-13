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
package com.github.stephengold.joltjni;

import java.nio.FloatBuffer;

/**
 * An immutable {@code Shape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstShape {
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the vertex coordinates of the shape's debug mesh to the specified
     * buffer. The current instance is unaffected.
     *
     * @param storeBuffer the buffer to fill with vertex coordinates (not null,
     * modified)
     */
    void copyDebugTriangles(FloatBuffer storeBuffer);

    /**
     * Return the number of triangles in the shape's debug mesh. The current
     * instance is unaffected.
     *
     * @return the count (&gt;0)
     */
    int countDebugTriangles();

    /**
     * Copy the shape's mass properties.
     *
     * @return a new instance
     */
    MassProperties getMassProperties();

    /**
     * Return the shape's subtype. The current instance is unaffected.
     *
     * @return an enum value
     */
    EShapeSubType getSubType();

    /**
     * Return the shape's type. The current instance is unaffected.
     *
     * @return an enum value
     */
    EShapeType getType();

    /**
     * Test whether the shape can be used in a dynamic/kinematic body.
     *
     * @return true if it can be only be static, otherwise false
     */
    boolean mustBeStatic();
}