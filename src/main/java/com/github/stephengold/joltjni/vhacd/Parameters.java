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
package com.github.stephengold.joltjni.vhacd;

import com.github.stephengold.joltjni.JoltPhysicsObject;
import java.util.Objects;

/**
 * A set of tuning parameters for the V-HACD algorithm. (native class:
 * {@code IVHACD::Parameters})
 */
public class Parameters extends JoltPhysicsObject {
    // *************************************************************************
    // fields

    /**
     * {@code true} to enable V-HACD debug output, {@code false} to disable it
     */
    private boolean enableDebugOutput;
    // *************************************************************************
    // constructors

    /**
     * Instantiate the default tuning parameters.
     */
    public Parameters() {
        long parametersVa = createDefault();
        setVirtualAddress(parametersVa, () -> free(parametersVa));
    }

    /**
     * Instantiate a copy of the specified tuning parameters.
     *
     * @param original the parameters to copy (not {@code null}, unaffected)
     */
    public Parameters(Parameters original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa, () -> free(copyVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether V-HACD should run asynchronously, on a new thread. The
     * parameters are unaffected. (native field: m_asyncACD)
     *
     * @return {@code true} to run on a new thread, or {@code false} to run on
     * the current thread
     */
    public boolean getAsyncAcd() {
        long parametersVa = va();
        boolean result = getAsyncAcd(parametersVa);

        return result;
    }

    /**
     * Return the algorithm that fills voxels to create a solid object. The
     * parameters are unaffected. (native field: m_fillMode)
     *
     * @return an enum value (not {@code null})
     */
    public FillMode getFillMode() {
        long parametersVa = va();
        int ordinal = getFillMode(parametersVa);
        FillMode result = FillMode.values()[ordinal];

        return result;
    }

    /**
     * Test whether V-HACD should try to find the optimal location for splitting
     * hulls. The parameters are unaffected. (native field: m_findBestPlane)
     *
     * @return {@code true} to split at the optimal location, {@code false} to
     * split in the middle
     */
    public boolean getFindBestPlane() {
        long parametersVa = va();
        boolean result = getFindBestPlane(parametersVa);

        return result;
    }

    /**
     * Return the maximum number of convex hulls. The parameters are unaffected.
     * (native field: m_maxConvexHulls)
     *
     * @return the maximum number (&ge;1, &le;1024)
     */
    public int getMaxConvexHulls() {
        long parametersVa = va();
        int result = getMaxConvexHulls(parametersVa);

        return result;
    }

    /**
     * Return the maximum number of vertices per convex hull. The parameters are
     * unaffected. (native field: m_maxNumVerticesPerCH)
     *
     * @return the maximum number (&ge;4, &le;2_048)
     */
    public int getMaxNumVerticesPerCh() {
        long parametersVa = va();
        int result = getMaxNumVerticesPerCh(parametersVa);

        return result;
    }

    /**
     * Return the maximum recursion depth. The parameters are unaffected.
     * (native field: m_maxRecursionDepth)
     *
     * @return the maximum number of levels (&ge;2, &le;64)
     */
    public int getMaxRecursionDepth() {
        long parametersVa = va();
        int result = getMaxRecursionDepth(parametersVa);

        return result;
    }

    /**
     * Return the minimum edge length. The parameters are unaffected. (native
     * field: m_minEdgeLength)
     *
     * @return the minimum length (&ge;1, &le;32)
     */
    public int getMinEdgeLength() {
        long parametersVa = va();
        int result = getMinEdgeLength(parametersVa);

        return result;
    }

    /**
     * Return the tolerance for the relative difference between the volumes of
     * the voxels and the hulls. The parameters are unaffected. (native field:
     * m_minimumVolumePercentErrorAllowed)
     *
     * @return the maximum error (as a percentage, &gt;0, &lt;100)
     */
    public double getMinimumVolumePercentErrorAllowed() {
        long parametersVa = va();
        double result = getMinimumVolumePercentErrorAllowed(parametersVa);

        return result;
    }

    /**
     * Return the maximum number of voxels generated during the voxelization
     * stage. The parameters are unaffected. (native field: m_resolution)
     *
     * @return the maximum number (&ge;10_000, &le;64_000_000)
     */
    public int getResolution() {
        long parametersVa = va();
        int result = getResolution(parametersVa);

        return result;
    }

    /**
     * Test whether to shrinkwrap voxel positions to the source mesh. The
     * parameters are unaffected. (native field: m_shrinkWrap)
     *
     * @return {@code true} if shrinkwrap is enabled, otherwise {@code false}
     */
    public boolean getShrinkWrap() {
        long parametersVa = va();
        boolean result = getShrinkWrap(parametersVa);

        return result;
    }

    /**
     * Test whether debug output is enabled. The parameters are unaffected.
     *
     * @return {@code true} if it's enabled, otherwise {@code false}
     */
    public boolean isDebugOutputEnabled() {
        return enableDebugOutput;
    }

    /**
     * Alter whether V-HACD should run asynchronously, on a new thread. (native
     * field: m_asyncACD)
     *
     * @param setting {@code true} to run on a new thread, or {@code false} to
     * run on the current thread (default=true)
     * @return the modified properties, for chaining
     */
    public Parameters setAsyncAcd(boolean setting) {
        long parametersVa = va();
        setAsyncAcd(parametersVa, setting);

        return this;
    }

    /**
     * Alter whether debug output is enabled.
     *
     * @param setting {@code true} to enable, otherwise {@code false}
     * (default=false)
     * @return the modified properties, for chaining
     */
    public Parameters setDebugOutputEnabled(boolean setting) {
        this.enableDebugOutput = setting;
        return this;
    }

    /**
     * Specify the algorithm that fills voxels to create a solid object. (native
     * field: m_fillMode)
     *
     * @param mode an enum value (not {@code null}, default=FloodFill)
     * @return the modified properties, for chaining
     */
    public Parameters setFillMode(FillMode mode) {
        long parametersVa = va();
        int ordinal = mode.ordinal();
        setFillMode(parametersVa, ordinal);

        return this;
    }

    /**
     * Alter whether V-HACD should try to find the optimal location for
     * splitting hulls. (native field: m_findBestPlane)
     *
     * @param setting {@code true} to split at the optimal location,
     * {@code false} to split in the middle (default=false)
     * @return the modified properties, for chaining
     */
    public Parameters setFindBestPlane(boolean setting) {
        long parameterVa = va();
        setFindBestPlane(parameterVa, setting);

        return this;
    }

    /**
     * Alter the maximum number of convex hulls. (native field:
     * m_maxConvexHulls)
     *
     * @param number the desired maximum number (&ge;1, &le;1_024, default=64)
     * @return the modified properties, for chaining
     */
    public Parameters setMaxConvexHulls(int number) {
        assert number >= 1 && number <= 1_024 : "number = " + number;

        long parameterVa = va();
        setMaxConvexHulls(parameterVa, number);

        return this;
    }

    /**
     * Alter the maximum number of vertices per convex hull. (native field:
     * m_maxNumVerticesPerCH)
     *
     * @param number the desired maximum number (&ge;4, &le;2_048, default=64)
     * @return the modified properties, for chaining
     */
    public Parameters setMaxNumVerticesPerCh(int number) {
        assert number >= 4 && number <= 2_048 : "number = " + number;

        long parameterVa = va();
        setMaxNumVerticesPerCh(parameterVa, number);

        return this;
    }

    /**
     * Alter the maximum recursion depth. (native field: m_maxRecursionDepth)
     *
     * @param depth the desired maximum number of levels (&ge;2, &le;64,
     * default=10)
     * @return the modified properties, for chaining
     */
    public Parameters setMaxRecursionDepth(int depth) {
        assert depth >= 2 && depth <= 64 : "depth = " + depth;

        long parameterVa = va();
        setMaxRecursionDepth(parameterVa, depth);

        return this;
    }

    /**
     * Alter the minimum edge length. (native field: m_minEdgeLength)
     *
     * @param length the desired minimum (&ge;1, &le;32, default=2)
     * @return the modified properties, for chaining
     */
    public Parameters setMinEdgeLength(int length) {
        assert length >= 1 && length <= 32 : "length = " + length;

        long parametersVa = va();
        setMinEdgeLength(parametersVa, length);

        return this;
    }

    /**
     * Alter the tolerance for the relative difference between the volumes of
     * the voxels and the hulls. (native field:
     * m_minimumVolumePercentErrorAllowed)
     *
     * @param percentage the desired maximum error (as a percentage, &gt;0,
     * &lt;100, default=1)
     * @return the modified properties, for chaining
     */
    public Parameters setMinimumVolumePercentErrorAllowed(double percentage) {
        assert percentage >= 0 && percentage <= 100 : "percentage" + percentage;

        long parameterVa = va();
        setMinimumVolumePercentErrorAllowed(parameterVa, percentage);

        return this;
    }

    /**
     * Alter the maximum number of voxels generated during the voxelization
     * stage. (native field: m_resolution)
     *
     * @param maxVoxels the desired maximum number (&ge;10_000, &le; 64_000_000,
     * default=400_000)
     * @return the modified properties, for chaining
     */
    public Parameters setResolution(int maxVoxels) {
        assert maxVoxels >= 10_000 && maxVoxels <= 64_000_000 :
                "maxVoxels = " + maxVoxels;

        long parametersVa = va();
        setResolution(parametersVa, maxVoxels);

        return this;
    }

    /**
     * Alter whether to shrinkwrap voxel positions to the source mesh. (native
     * field: m_shrinkWrap)
     *
     * @param setting {@code true} to enable shrinkwrap, otherwise {@code false}
     * (default=true)
     * @return the modified properties, for chaining
     */
    public Parameters setShrinkWrap(boolean setting) {
        long parameterVa = va();
        setShrinkWrap(parameterVa, setting);

        return this;
    }
    // *************************************************************************
    // JoltPhysicsObject methods

    /**
     * Test for exact equivalence with another Object.
     *
     * @param otherObject (may be {@code null}, unaffected)
     * @return {@code true} if the objects are equivalent, otherwise
     * {@code false}
     */
    @Override
    public boolean equals(Object otherObject) {
        boolean result;
        if (otherObject == this) {
            result = true;
        } else if (otherObject != null
                && otherObject.getClass() == getClass()) {
            Parameters other = (Parameters) otherObject;
            result = getAsyncAcd() == other.getAsyncAcd()
                    && getFillMode() == other.getFillMode()
                    && getFindBestPlane() == other.getFindBestPlane()
                    && getMaxConvexHulls() == other.getMaxConvexHulls()
                    && getMaxNumVerticesPerCh()
                    == other.getMaxNumVerticesPerCh()
                    && getMaxRecursionDepth() == other.getMaxRecursionDepth()
                    && getMinEdgeLength() == other.getMinEdgeLength()
                    && getMinimumVolumePercentErrorAllowed()
                    == other.getMinimumVolumePercentErrorAllowed()
                    && getResolution() == other.getResolution()
                    && getShrinkWrap() == other.getShrinkWrap()
                    && isDebugOutputEnabled() == other.isDebugOutputEnabled();
        } else {
            result = false;
        }

        return result;
    }

    /**
     * Generate the hash code for this instance.
     *
     * @return a 32-bit value for use in hashing
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(getAsyncAcd(), getFillMode(),
                getFindBestPlane(), getMaxConvexHulls(),
                getMaxNumVerticesPerCh(), getMaxRecursionDepth(),
                getMinEdgeLength(), getMinimumVolumePercentErrorAllowed(),
                getResolution(), getShrinkWrap(), isDebugOutputEnabled());
        return result;
    }

    /**
     * Represent this instance as a String.
     *
     * @return a descriptive string of text (not {@code null}, not empty)
     */
    @Override
    public String toString() {
        String result = String.format("Parameters[%n"
                + " async=%s  debug=%s  %s  findBest=%s%n "
                + "maxHulls=%s  maxRecursion=%s  maxVerticesPH=%s  minEdge=%s%n"
                + " resolution=%s  shrink=%s  volumeErr=%s%%%n"
                + "]",
                getAsyncAcd(), isDebugOutputEnabled(), getFillMode(),
                getFindBestPlane(), getMaxConvexHulls(), getMaxRecursionDepth(),
                getMaxNumVerticesPerCh(), getMinEdgeLength(), getResolution(),
                getShrinkWrap(), getMinimumVolumePercentErrorAllowed()
        );

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static void free(long parametersVa);

    native private static boolean getAsyncAcd(long parametersVa);

    native private static int getFillMode(long parametersVa);

    native private static boolean getFindBestPlane(long parametersVa);

    native private static int getMaxConvexHulls(long parametersVa);

    native private static int getMaxNumVerticesPerCh(long parametersVa);

    native private static int getMaxRecursionDepth(long parametersVa);

    native private static int getMinEdgeLength(long parametersVa);

    native private static double getMinimumVolumePercentErrorAllowed(
            long parametersVa);

    native private static int getResolution(long parametersVa);

    native private static boolean getShrinkWrap(long parametersVa);

    native private static void setAsyncAcd(long parametersVa, boolean setting);

    native private static void setFillMode(long parametersVa, int ordinal);

    native private static void setFindBestPlane(
            long parametersVa, boolean setting);

    native private static void setMaxConvexHulls(long parametersVa, int number);

    native private static void setMaxNumVerticesPerCh(
            long parametersVa, int number);

    native private static void setMaxRecursionDepth(
            long parametersVa, int depth);

    native private static void setMinEdgeLength(long parametersVa, int length);

    native private static void setMinimumVolumePercentErrorAllowed(
            long parametersVa, double percentage);

    native private static void setResolution(long parametersVa, int maxVoxels);

    native private static void setShrinkWrap(
            long parametersVa, boolean setting);
}
