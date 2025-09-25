/*
Copyright (c) 2024-2025 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.ConstPathConstraintPath;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * The path for a path constraint.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class PathConstraintPath
        extends SerializableObject
        implements ConstPathConstraintPath {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a path with no native object assigned.
     */
    PathConstraintPath() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Instantiate a {@code PathConstraintPath} from its virtual address.
     *
     * @param pathVa the virtual address of the native object, or zero
     * @return a new JVM object, or {@code null} if the argument was zero
     */
    static PathConstraintPath newPath(long pathVa) {
        if (pathVa == 0L) {
            return null;
        }

        long rttiVa = SerializableObject.getRtti(pathVa);
        String typeName = Rtti.getName(rttiVa);
        PathConstraintPath result;
        switch (typeName) {
            case "PathConstraintPathHermite":
                result = new PathConstraintPathHermite(pathVa);
                break;
            default:
                throw new IllegalArgumentException("typeName = " + typeName);
        }

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

    /**
     * Read a path from the specified binary stream.
     *
     * @param stream where to read objects (not null)
     * @return a new object
     */
    public static PathResult sRestoreFromBinaryState(StreamIn stream) {
        long streamVa = stream.va();
        long resultVa = sRestoreFromBinaryState(streamVa);
        PathResult result = new PathResult(resultVa, true);

        return result;
    }
    // *************************************************************************
    // new protected methods

    /**
     * Assign a native object (assuming there's none already assigned) and
     * designate the JVM object as a co-owner.
     *
     * @param pathVa the virtual address of the native object to assign (not
     * zero)
     */
    final protected void setVirtualAddressAsCoOwner(long pathVa) {
        long refVa = toRef(pathVa);
        Runnable freeingAction = () -> PathConstraintPathRef.free(refVa);
        setVirtualAddress(pathVa, freeingAction);
    }
    // *************************************************************************
    // ConstPathConstraintPath methods

    /**
     * Return the path amount of the location on the path that's closest to the
     * specified location. The path is unaffected.
     *
     * @param location the input location (in system coordinates, not null,
     * unaffected)
     * @param fractionHint where to start searching
     * @return the path amount (&ge;0)
     */
    @Override
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
    @Override
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
    @Override
    public void getPointOnPath(float amount, Vec3 storeLocation,
            Vec3 storeTangent, Vec3 storeNormal, Vec3 storeBinormal) {
        long pathVa = va();
        float[] storeFloats = new float[12];
        getPointOnPath(pathVa, amount, storeFloats);
        storeLocation.set(storeFloats);
        storeTangent.set(storeFloats[3], storeFloats[4], storeFloats[5]);
        storeNormal.set(storeFloats[6], storeFloats[7], storeFloats[8]);
        storeBinormal.set(storeFloats[9], storeFloats[10], storeFloats[11]);
    }

    /**
     * Test whether the path is looping. The path is unaffected.
     *
     * @return {@code true} if looping, otherwise {@code false}
     */
    @Override
    public boolean isLooping() {
        long pathVa = va();
        boolean result = isLooping(pathVa);

        return result;
    }

    /**
     * Save the path to the specified binary stream. The path is unaffected.
     *
     * @param stream the stream to write to (not null)
     */
    @Override
    public void saveBinaryState(StreamOut stream) {
        long pathVa = va();
        long streamVa = stream.va();
        saveBinaryState(pathVa, streamVa);
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

    native private static void saveBinaryState(long pathVa, long streamVa);

    native private static void setEmbedded(long pathVa);

    native private static void setIsLooping(long pathVa, boolean setting);

    native private static long sRestoreFromBinaryState(long streamVa);

    native private static long toRef(long materialVa);
}
