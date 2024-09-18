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

import com.github.stephengold.joltjni.enumerate.EShapeSubType;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * Settings used to construct a {@code ConvexHullShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ConvexHullShapeSettings extends ConvexShapeSettings {
    // *************************************************************************
    // constants

    /**
     * number of floats in a vertex
     */
    final private static int numAxes = 3;
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    ConvexHullShapeSettings(long settingsVa) {
        super(settingsVa);
        setSubType(EShapeSubType.ConvexHull);
    }

    /**
     * Instantiate settings for the specified points.
     *
     * @param points an array of point locations (not null, unaffected)
     */
    public ConvexHullShapeSettings(Vec3Arg... points) {
        int numPoints = points.length;
        int numFloats = numAxes * numPoints;
        FloatBuffer buffer = Jolt.newDirectFloatBuffer(numFloats);
        for (Vec3Arg point : points) {
            buffer.put(point.getX());
            buffer.put(point.getY());
            buffer.put(point.getZ());
        }
        float maxConvexRadius = PhysicsSettings.cDefaultConvexRadius;
        long materialVa = 0L;
        long settingsVa = createSettings(
                numPoints, buffer, maxConvexRadius, materialVa);
        setVirtualAddress(settingsVa, null); // not owner due to ref counting
        setSubType(EShapeSubType.ConvexHull);
    }

    /**
     * Instantiate settings for the points in the specified buffer.
     *
     * @param numPoints the number of points (&ge;0)
     * @param points a direct buffer containing point locations (not null,
     * unaffected)
     */
    public ConvexHullShapeSettings(int numPoints, FloatBuffer points) {
        float maxConvexRadius = PhysicsSettings.cDefaultConvexRadius;
        long materialVa = 0L;
        long settingsVa = createSettings(
                numPoints, points, maxConvexRadius, materialVa);
        setVirtualAddress(settingsVa, null); // not owner due to ref counting
        setSubType(EShapeSubType.ConvexHull);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Count the points to use when creating the hull. The settings are
     * unaffected.
     *
     * @return the count (&ge;0)
     */
    public int countPoints() {
        long settingsVa = va();
        int result = countPoints(settingsVa);

        return result;
    }

    /**
     * Return the positional tolerance used when generating the hull. The
     * settings are unaffected. (native attribute: mHullTolerance)
     *
     * @return the distance (&ge;0)
     */
    public float getHullTolerance() {
        long settingsVa = va();
        float result = getHullTolerance(settingsVa);

        return result;
    }

    /**
     * Return the convex radius. The settings are unaffected. (native attribute:
     * mMaxConvexRadius)
     *
     * @return the convex radius (&ge;0)
     */
    public float getMaxConvexRadius() {
        long settingsVa = va();
        float result = getMaxConvexRadius(settingsVa);

        return result;
    }

    /**
     * Return the maximum separation between the shrunk hull + convex radius and
     * the actual hull. The settings are unaffected. (native attribute:
     * mMaxErrorConvexRadius)
     *
     * @return the maximum separation (&ge;0)
     */
    public float getMaxErrorConvexRadius() {
        long settingsVa = va();
        float result = getMaxErrorConvexRadius(settingsVa);

        return result;
    }

    /**
     * Alter the positional tolerance used when generating generate the hull.
     * (native attribute: mHullTolerance)
     *
     * @param tolerance the desired tolerance (&ge;0, default=0.001)
     */
    public void setHullTolerance(float tolerance) {
        long settingsVa = va();
        setHullTolerance(settingsVa, tolerance);
    }

    /**
     * Alter the convex radius. (native attribute: mMaxConvexRadius)
     *
     * @param radius the desired convex radius (&ge;0, default=0)
     */
    public void setMaxConvexRadius(float radius) {
        long settingsVa = va();
        setMaxConvexRadius(settingsVa, radius);
    }

    /**
     * Alter the maximum separation between the shrunk hull plus convex radius
     * and the actual hull. (native attribute: mMaxErrorConvexRadius)
     *
     * @param maxError the desired maximum separation (&ge;0, default=0.05)
     */
    public void setMaxErrorConvexRadius(float maxError) {
        long settingsVa = va();
        setMaxErrorConvexRadius(settingsVa, maxError);
    }
    // *************************************************************************
    // native private methods

    native private static int countPoints(long settingsVa);

    native private static long createSettings(int numPoints, FloatBuffer points,
            float convexRadius, long materialVa);

    native private static float getHullTolerance(long settingsVa);

    native private static float getMaxConvexRadius(long settingsVa);

    native private static float getMaxErrorConvexRadius(long settingsVa);

    native private static void setHullTolerance(
            long settingsVa, float tolerance);

    native private static void setMaxConvexRadius(
            long settingsVa, float radius);

    native private static void setMaxErrorConvexRadius(
            long settingsVa, float maxError);
}
