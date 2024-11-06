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

import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.template.RefTarget;

/**
 * The path for a path constraint.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class PathConstraintPath
        extends SerializableObject
        implements RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a path with no native object assigned.
     */
    PathConstraintPath() {
    }

    /**
     * Instantiate a path with the specified native object assigned but not
     * owned.
     *
     * @param pathVa the virtual address of the native object to assign (not
     * zero)
     */
    PathConstraintPath(long pathVa) {
        super(pathVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the path amount of the location on the path that's closest to the
     * specified location. The path is uanffected.
     *
     * @param location the input location (in system coordinates, not null,
     * unaffected)
     * @param fractionHint where to start searching
     * @return the path amount (&ge;0)
     */
    public float getClosestPoint(Vec3Arg location, float fractionHint) {
        long pathVa = va();
        float x = location.getX();
        float y = location.getY();
        float z = location.getZ();
        float result = getClosestPoint(pathVa, x, y, z, fractionHint);

        return result;
    }

    /**
     * Return the path amount of the end of the path. The path is unaffected.
     *
     * @return the path amount (&ge;0)
     */
    public float getPathMaxFraction() {
        long pathVa = va();
        float result = getPathMaxFraction(pathVa);

        return result;
    }

    /**
     * Calculate the location, normal, and binormal of the location on the path
     * with the specified path amount. The path is unaffected.
     *
     * @param amount the path amount (&ge;0)
     * @param storeLocation storage for the location (in system coordinates)
     * @param storeTangent storage for the tangent direction (in system
     * coordinates)
     * @param storeNormal storage for the normal direction (in system
     * coordinates)
     * @param storeBinormal storage for the binormal direction (in system
     * coordinates)
     */
    public void getPointOnPath(float amount, Vec3 storeLocation,
            Vec3 storeTangent, Vec3 storeNormal, Vec3 storeBinormal) {
        long pathVa = va();
        float[] storeFloats = new float[12];
        getPointOnPath(pathVa, amount, storeFloats);
        storeLocation.set(storeFloats[0], storeFloats[1], storeFloats[2]);
        storeTangent.set(storeFloats[3], storeFloats[4], storeFloats[5]);
        storeNormal.set(storeFloats[6], storeFloats[7], storeFloats[8]);
        storeBinormal.set(storeFloats[9], storeFloats[10], storeFloats[11]);
    }

    /**
     * Test whether the path is looping. The path is unaffected.
     *
     * @return {@code true} if looping, otherwise {@code false}
     */
    public boolean isLooping() {
        long pathVa = va();
        boolean result = isLooping(pathVa);

        return result;
    }

    /**
     * Alter whether the path is looping.
     *
     * @param setting {@code true} for looping, or {@code false} for no looping
     * (default=false)
     */
    public void setIsLooping(boolean setting) {
        long pathVa = va();
        setIsLooping(pathVa, setting);
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code PathConstraintPath}. The
     * path is unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long pathVa = va();
        int result = getRefCount(pathVa);

        return result;
    }

    /**
     * Mark the native {@code PathConstraintPath} as embedded.
     */
    @Override
    public void setEmbedded() {
        long pathVa = va();
        setEmbedded(pathVa);
    }

    /**
     * Create a counted reference to the native {@code PathConstraintPath}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public PathConstraintPathRef toRef() {
        long pathVa = va();
        long copyVa = toRef(pathVa);
        PathConstraintPathRef result = new PathConstraintPathRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static float getClosestPoint(
            long pathVa, float x, float y, float z, float fractionHint);

    native private static float getPathMaxFraction(long pathVa);

    native private static void getPointOnPath(
            long pathVa, float amount, float[] storeFloats);

    native private static int getRefCount(long pathVa);

    native private static boolean isLooping(long pathVa);

    native private static void setEmbedded(long pathVa);

    native private static void setIsLooping(long pathVa, boolean setting);

    native private static long toRef(long materialVa);
}
