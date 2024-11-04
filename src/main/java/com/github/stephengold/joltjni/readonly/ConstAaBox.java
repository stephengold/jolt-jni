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

import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to an {@code AaBox}. (native type: const AABox)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstAaBox extends ConstJoltPhysicsObject {
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether the box contains the specified point. The box is unaffected.
     *
     * @param point the point to test (not null, unaffected)
     *
     * @return {@code true} if contained, otherwise {@code false}
     */
    boolean contains(Vec3Arg point);

    /**
     * Locate the center of the box. The box is unaffected.
     *
     * @return a new location vector
     */
    Vec3 getCenter();

    /**
     * Copy the (half) extent of the box. The box is unaffected.
     *
     * @return a new vector
     */
    Vec3 getExtent();

    /**
     * Copy the maximum contained coordinate on each axis. The box is
     * unaffected.
     *
     * @return a new location vector
     */
    Vec3 getMax();

    /**
     * Copy the minimum contained coordinate on each axis. The box is
     * unaffected.
     *
     * @return a new location vector
     */
    Vec3 getMin();

    /**
     * Copy the size (full extent) on each axis. The box is unaffected.
     *
     * @return a new vector
     */
    Vec3 getSize();

    /**
     * Return the volume of the box. The box is unaffected.
     *
     * @return the volume
     */
    float getVolume();

    /**
     * Test whether the box is valid. It is unaffected.
     *
     * @return {@code true} if valid, otherwise {@code false}
     */
    boolean isValid();
}
