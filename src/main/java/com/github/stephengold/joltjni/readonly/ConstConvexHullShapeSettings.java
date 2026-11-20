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

import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to a {@code ConvexHullShapeSettings} object. (native type:
 * const ConvexHullShapeSettings)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstConvexHullShapeSettings extends ConstConvexShapeSettings {
    /**
     * Copy one of the points to use when creating the hull. The settings are
     * unaffected.
     *
     * @param index the index of the point to copy (&ge;0)
     * @return a new location vector
     */
    Vec3 copyPoint(int index);

    /**
     * Copy the points to use when creating the hull. The settings are
     * unaffected.
     *
     * @return a new array of new vectors
     */
    Vec3[] copyPoints();

    /**
     * Count the points to use when creating the hull. The settings are
     * unaffected.
     *
     * @return the count (&ge;0)
     */
    int countPoints();

    /**
     * Return the positional tolerance used when generating the hull. The
     * settings are unaffected.
     *
     * @return the distance (&ge;0)
     */
    float getHullTolerance();

    /**
     * Return the convex radius. The settings are unaffected.
     *
     * @return the convex radius (&ge;0)
     */
    float getMaxConvexRadius();

    /**
     * Return the maximum separation between the shrunk hull + convex radius and
     * the actual hull. The settings are unaffected.
     *
     * @return the maximum separation (&ge;0)
     */
    float getMaxErrorConvexRadius();
}
