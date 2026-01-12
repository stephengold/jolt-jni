/*
Copyright (c) 2024-2026 Stephen Gold

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
import com.github.stephengold.joltjni.readonly.ConstPhysicsMaterial;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;
import java.util.Collection;

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
     * Instantiate settings for the specified collection of points.
     *
     * @param points a collection of point locations (not {@code null},
     * unaffected)
     */
    public ConvexHullShapeSettings(Collection<?> points) {
        this(points, Jolt.cDefaultConvexRadius);
    }

    /**
     * Instantiate settings for the specified parameters.
     *
     * @param points a collection of point locations (not {@code null},
     * unaffected)
     * @param maxConvexRadius the desired maximum convex radius (&ge;0,
     * default=0.05)
     */
    public ConvexHullShapeSettings(
            Collection<?> points, float maxConvexRadius) {
        this(points, maxConvexRadius, null);
    }

    /**
     * Instantiate settings for the specified parameters.
     *
     * @param points a collection of point locations (not {@code null},
     * unaffected)
     * @param maxConvexRadius the desired maximum convex radius (&ge;0,
     * default=0.05)
     * @param material the desired surface properties (not {@code null},
     * unaffected) or {@code null} for default properties (default=null)
     */
    public ConvexHullShapeSettings(Collection<?> points,
            float maxConvexRadius, ConstPhysicsMaterial material) {
        int numPoints = points.size();
        long settingsVa;
        if (numPoints == 0) {
            settingsVa = createDefault();
        } else {
            int numFloats = numAxes * numPoints;
            FloatBuffer buffer = Jolt.newDirectFloatBuffer(numFloats);
            for (Object element : points) {
                Vec3Arg point = (Vec3Arg) element;
                point.put(buffer);
            }
            long materialVa = (material == null) ? 0L : material.targetVa();
            settingsVa = createSettings(
                    numPoints, buffer, maxConvexRadius, materialVa);
        }
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.ConvexHull);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public ConvexHullShapeSettings(ConvexHullShapeSettings original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddressAsCoOwner(copyVa, EShapeSubType.ConvexHull);
    }

    /**
     * Instantiate settings for the points in the specified buffer.
     *
     * @param numPoints the number of points (&ge;0)
     * @param points a direct buffer containing point locations (not
     * {@code null}, unaffected)
     */
    public ConvexHullShapeSettings(int numPoints, FloatBuffer points) {
        this(numPoints, points, Jolt.cDefaultConvexRadius);
    }

    /**
     * Instantiate settings for the specified parameters.
     *
     * @param numPoints the number of points (&ge;0)
     * @param points a direct buffer containing point locations (not
     * {@code null}, unaffected)
     * @param maxConvexRadius the desired maximum convex radius (&ge;0,
     * default=0.05)
     */
    public ConvexHullShapeSettings(
            int numPoints, FloatBuffer points, float maxConvexRadius) {
        this(numPoints, points, maxConvexRadius, null);
    }

    /**
     * Instantiate settings for the specified parameters.
     *
     * @param numPoints the number of points (&ge;0)
     * @param points a direct buffer containing point locations (not
     * {@code null}, unaffected)
     * @param maxConvexRadius the desired maximum convex radius (&ge;0,
     * default=0.05)
     * @param material the desired surface properties (not {@code null},
     * unaffected) or {@code null} for default properties (default=null)
     */
    public ConvexHullShapeSettings(int numPoints, FloatBuffer points,
            float maxConvexRadius, ConstPhysicsMaterial material) {
        assert points.isDirect();
        long materialVa = (material == null) ? 0L : material.targetVa();
        long settingsVa = createSettings(
                numPoints, points, maxConvexRadius, materialVa);
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.ConvexHull);
    }

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    ConvexHullShapeSettings(long settingsVa) {
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.ConvexHull);
    }

    /**
     * Instantiate settings for the specified points.
     *
     * @param points the array of desired point locations (not {@code null},
     * unaffected)
     */
    public ConvexHullShapeSettings(Vec3Arg... points) {
        int numPoints = points.length;
        long settingsVa;
        if (points.length == 0) {
            settingsVa = createDefault();
        } else {
            int numFloats = numAxes * numPoints;
            FloatBuffer buffer = Jolt.newDirectFloatBuffer(numFloats);
            for (Vec3Arg point : points) {
                point.put(buffer);
            }
            settingsVa = createSettings(
                    numPoints, buffer, Jolt.cDefaultConvexRadius, 0L);
        }
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.ConvexHull);
    }

    /**
     * Instantiate settings for the specified parameters.
     *
     * @param points an array of point locations (not {@code null}, unaffected)
     * @param maxConvexRadius the desired maximum convex radius (&ge;0,
     * default=0.05)
     */
    public ConvexHullShapeSettings(Vec3Arg[] points, float maxConvexRadius) {
        this(points, maxConvexRadius, null);
    }

    /**
     * Instantiate settings for the specified parameters.
     *
     * @param points an array of point locations (not {@code null}, unaffected)
     * @param maxConvexRadius the desired maximum convex radius (&ge;0,
     * default=0.05)
     * @param material the desired surface properties (not {@code null},
     * unaffected) or {@code null} for default properties (default=null)
     */
    public ConvexHullShapeSettings(Vec3Arg[] points, float maxConvexRadius,
            ConstPhysicsMaterial material) {
        int numPoints = points.length;
        int numFloats = numAxes * numPoints;
        FloatBuffer buffer = Jolt.newDirectFloatBuffer(numFloats);
        for (Vec3Arg point : points) {
            point.put(buffer);
        }
        long materialVa = (material == null) ? 0L : material.targetVa();
        long settingsVa = createSettings(
                numPoints, buffer, maxConvexRadius, materialVa);
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.ConvexHull);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Add the specified point to the hull.
     *
     * @param point the coordinates of the point to add (not {@code null},
     * unaffected)
     */
    public void addPoint(Vec3Arg point) {
        long settingsVa = va();
        float x = point.getX();
        float y = point.getY();
        float z = point.getZ();
        addPoint(settingsVa, x, y, z);
    }

    /**
     * Copy one of the points to use when creating the hull. The settings are
     * unaffected.
     *
     * @param index the index of the point to copy (&ge;0)
     * @return a new location vector
     */
    public Vec3 copyPoint(int index) {
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getPoint(settingsVa, index, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the points to use when creating the hull. The settings are
     * unaffected. (native attribute: mPoints)
     *
     * @return a new array of new vectors
     */
    public Vec3[] copyPoints() {
        long settingsVa = va();
        int numPoints = countPoints(settingsVa);
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        Vec3[] result = new Vec3[numPoints];
        for (int i = 0; i < numPoints; ++i) {
            getPoint(settingsVa, i, storeFloats);
            result[i] = new Vec3(storeFloats);
        }

        return result;
    }

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
     * Alter the positional tolerance used when generating the hull. (native
     * attribute: mHullTolerance)
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

    /**
     * Replace the specified point with new coordinates.
     *
     * @param index which point to replace (&ge;0)
     * @param point the desired coordinates (not {@code null}, unaffected)
     */
    public void setPoint(int index, Vec3Arg point) {
        long settingsVa = va();
        float x = point.getX();
        float y = point.getY();
        float z = point.getZ();
        setPoint(settingsVa, index, x, y, z);
    }

    /**
     * Replace any existing points with the specified ones. (native attribute:
     * mPoints)
     *
     * @param points the array of desired point locations (not {@code null},
     * unaffected)
     */
    public void setPoints(Vec3Arg... points) {
        int numPoints = points.length;
        int numFloats = numAxes * numPoints;
        FloatBuffer buffer = Jolt.newDirectFloatBuffer(numFloats);
        for (Vec3Arg point : points) {
            point.put(buffer);
        }
        long settingsVa = va();
        setPoints(settingsVa, numPoints, buffer);
    }
    // *************************************************************************
    // native private methods

    native private static void addPoint(
            long settingsVa, float x, float y, float z);

    native private static int countPoints(long settingsVa);

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static long createSettings(int numPoints, FloatBuffer points,
            float convexRadius, long materialVa);

    native private static float getHullTolerance(long settingsVa);

    native private static float getMaxConvexRadius(long settingsVa);

    native private static float getMaxErrorConvexRadius(long settingsVa);

    native private static void getPoint(
            long settingsVa, int index, FloatBuffer storeFloats);

    native private static void setHullTolerance(
            long settingsVa, float tolerance);

    native private static void setMaxConvexRadius(
            long settingsVa, float radius);

    native private static void setMaxErrorConvexRadius(
            long settingsVa, float maxError);

    native private static void setPoint(
            long settingsVa, int index, float x, float y, float z);

    native private static void setPoints(
            long settingsVa, int numPoints, FloatBuffer buffer);
}
