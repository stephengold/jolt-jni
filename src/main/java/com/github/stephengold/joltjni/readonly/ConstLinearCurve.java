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

import com.github.stephengold.joltjni.StreamOut;

/**
 * Read-only access to a {@code LinearCurve}. (native type:
 * {@code const LinearCurve})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstLinearCurve extends ConstJoltPhysicsObject {
    /**
     * Count the points in the curve. The curve is unaffected.
     *
     * @return the count (&ge;0)
     */
    int countPoints();

    /**
     * Return the highest X value. The curve is unaffected.
     *
     * @return the highest X value
     */
    float getMaxX();

    /**
     * Return the lowest X value. The curve is unaffected.
     *
     * @return the lowest X value
     */
    float getMinX();

    /**
     * Return the X value of the specified point. The curve is unaffected.
     *
     * @param pointIndex the index of the point (&ge;0)
     * @return the point's X value
     */
    float getPointX(int pointIndex);

    /**
     * Return the Y value of the specified point. The curve is unaffected.
     *
     * @param pointIndex the index of the point (&ge;0)
     * @return the point's Y value
     */
    float getPointY(int pointIndex);

    /**
     * Return the Y value for the specified X. The curve is unaffected.
     *
     * @param x the input X value
     * @return the interpolated Y value
     */
    float getValue(float x);

    /**
     * Write the curve to the specified stream. The curve is unaffected.
     *
     * @param stream where to write objects (not {@code null})
     */
    void saveBinaryState(StreamOut stream);
}
