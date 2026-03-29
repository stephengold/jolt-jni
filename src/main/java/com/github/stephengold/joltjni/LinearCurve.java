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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.readonly.ConstLinearCurve;

/**
 * Linearly interpolate some value Y versus another value X, such as torque
 * versus RPMs or wheel friction versus slip ratio.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class LinearCurve extends JoltPhysicsObject implements ConstLinearCurve {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default curve.
     */
    public LinearCurve() {
        long curveVa = createDefault();
        Runnable freeingAction = () -> free(curveVa);
        setVirtualAddress(curveVa, freeingAction);
    }

    /**
     * Instantiate a copy of the specified curve.
     *
     * @param original the curve to copy (not {@code null}, unaffected)
     */
    public LinearCurve(ConstLinearCurve original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        Runnable freeingAction = () -> free(copyVa);
        setVirtualAddress(copyVa, freeingAction);
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param curveVa the virtual address of the native object to assign (not
     * zero)
     */
    LinearCurve(JoltPhysicsObject container, long curveVa) {
        super(container, curveVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Add the specified point to the curve.
     *
     * @param x the input (X) coordinate
     * @param y the output (Y) coordinate
     */
    public void addPoint(float x, float y) {
        long curveVa = va();
        addPoint(curveVa, x, y);
    }

    /**
     * Remove all points from the curve.
     */
    public void clear() {
        long curveVa = va();
        clear(curveVa);
    }

    /**
     * Read the state of this curve from the specified stream.
     *
     * @param stream where to read from (not {@code null})
     */
    public void restoreBinaryState(StreamIn stream) {
        long curveVa = va();
        long streamVa = stream.va();
        restoreBinaryState(curveVa, streamVa);
    }

    /**
     * Sort the points by their X values, in ascending order.
     */
    public void sort() {
        long curveVa = va();
        sort(curveVa);
    }
    // *************************************************************************
    // ConstLinearCurve methods

    /**
     * Count the points in the curve. The curve is unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countPoints() {
        long curveVa = va();
        int result = countPoints(curveVa);

        return result;
    }

    /**
     * Return the highest X value. The curve is unaffected.
     *
     * @return the highest X value
     */
    @Override
    public float getMaxX() {
        long curveVa = va();
        float result = getMaxX(curveVa);

        return result;
    }

    /**
     * Return the lowest X value. The curve is unaffected.
     *
     * @return the lowest X value
     */
    @Override
    public float getMinX() {
        long curveVa = va();
        float result = getMinX(curveVa);

        return result;
    }

    /**
     * Return the X value of the specified point. The curve is unaffected.
     *
     * @param pointIndex the index of the point (&ge;0)
     * @return the point's X value
     */
    @Override
    public float getPointX(int pointIndex) {
        long curveVa = va();
        float result = getPointX(curveVa, pointIndex);

        return result;
    }

    /**
     * Return the Y value of the specified point. The curve is unaffected.
     *
     * @param pointIndex the index of the point (&ge;0)
     * @return the point's Y value
     */
    @Override
    public float getPointY(int pointIndex) {
        long curveVa = va();
        float result = getPointY(curveVa, pointIndex);

        return result;
    }

    /**
     * Return the Y value for the specified X. The curve is unaffected.
     *
     * @param x the input X value
     * @return the interpolated Y value
     */
    @Override
    public float getValue(float x) {
        long curveVa = va();
        float result = getValue(curveVa, x);

        return result;
    }

    /**
     * Write the curve to the specified stream. The curve is unaffected.
     *
     * @param stream where to write objects (not {@code null})
     */
    @Override
    public void saveBinaryState(StreamOut stream) {
        long curveVa = va();
        long streamVa = stream.va();
        saveBinaryState(curveVa, streamVa);
    }
    // *************************************************************************
    // native private methods

    native private static void addPoint(long curveVa, float x, float y);

    native private static void clear(long curveVa);

    native private static int countPoints(long curveVa);

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static void free(long curveVa);

    native private static float getMaxX(long curveVa);

    native private static float getMinX(long curveVa);

    native private static float getPointX(long curveVa, int pointIndex);

    native private static float getPointY(long curveVa, int pointIndex);

    native private static float getValue(long curveVa, float y);

    native private static void restoreBinaryState(long curveVa, long streamVa);

    native private static void saveBinaryState(long curveVa, long streamVa);

    native private static void sort(long curveVa);
}
