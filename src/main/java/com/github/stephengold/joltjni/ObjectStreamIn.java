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

import com.github.stephengold.joltjni.std.StringStream;
import com.github.stephengold.joltjni.template.Ref;

/**
 * Utility class for reading Jolt Physics objects from files or streams.
 *
 * @author Stephen Gold sgold@sonic.net
 * @see com.github.stephengold.joltjni.ObjectStreamOut
 */
final public class ObjectStreamIn {
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private ObjectStreamIn() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Read a ref-counted target from the specified file.
     *
     * @param fileName the path to the file (not null)
     * @param storeRef where to store the reference to the de-serialized object
     * (not null, modified)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sReadObject(String fileName, Ref storeRef) {
        long refVa = storeRef.va();
        boolean result;
        if (storeRef instanceof ConstraintSettingsRef) {
            result = sReadConstraintSettings(fileName, refVa);
        } else if (storeRef instanceof GroupFilterTableRef) {
            result = sReadGroupFilterTable(fileName, refVa);
        } else if (storeRef instanceof PhysicsMaterialRef) {
            result = sReadPhysicsMaterial(fileName, refVa);
        } else if (storeRef instanceof PhysicsSceneRef) {
            result = sReadPhysicsScene(fileName, refVa);
        } else if (storeRef instanceof RagdollSettingsRef) {
            result = sReadRagdollSettings(fileName, refVa);
        } else if (storeRef instanceof SkeletalAnimationRef) {
            result = sReadSkeletalAnimation(fileName, refVa);
        } else if (storeRef instanceof SkeletonRef) {
            result = sReadSkeleton(fileName, refVa);
        } else if (storeRef instanceof VehicleControllerSettingsRef) {
            result = sReadVehicleControllerSettings(fileName, refVa);
        } else {
            throw new IllegalArgumentException(
                    storeRef.getClass().getSimpleName());
        }

        return result;
    }

    /**
     * Read a body-settings object from the specified stream.
     *
     * @param stream the stream to read from (not null)
     * @param storeBcs where to store the de-serialized settings (not null,
     * length&gt;0, modified)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sReadObject(
            StringStream stream, BodyCreationSettings[] storeBcs) {
        long streamVa = stream.va();
        long[] storeVa = {0L};
        boolean result = sReadBcsFromStream(streamVa, storeVa);
        long bodySettingsVa = storeVa[0];
        storeBcs[0] = new BodyCreationSettings(bodySettingsVa, true);

        return result;
    }

    /**
     * Read a constraint-settings object from the specified stream.
     *
     * @param stream the stream to read from (not null)
     * @param settingsRef where to store the reference to the de-serialized
     * settings (not null, modified)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sReadObject(
            StringStream stream, ConstraintSettingsRef settingsRef) {
        long streamVa = stream.va();
        long settingsRefVa = settingsRef.va();
        boolean result
                = sReadConstraintSettingsFromStream(streamVa, settingsRefVa);

        return result;
    }

    /**
     * Read a group-filter table from the specified stream.
     *
     * @param stream the stream to read from (not null)
     * @param filterRef where to store the reference to the de-serialized filter
     * (not null, modified)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sReadObject(
            StringStream stream, GroupFilterTableRef filterRef) {
        long streamVa = stream.va();
        long refVa = filterRef.va();
        boolean result = sReadGroupFilterTableFromStream(streamVa, refVa);

        return result;
    }

    /**
     * Read a material from the specified stream.
     *
     * @param stream the stream to read from (not null)
     * @param materialRef where to store the reference to the de-serialized
     * material (not null, modified)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sReadObject(
            StringStream stream, PhysicsMaterialRef materialRef) {
        long streamVa = stream.va();
        long refVa = materialRef.va();
        boolean result = sReadPhysicsMaterialFromStream(streamVa, refVa);

        return result;
    }

    /**
     * Read a scene from the specified stream.
     *
     * @param stream the stream to read from (not null)
     * @param sceneRef where to store the reference to the de-serialized scene
     * (not null, modified)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sReadObject(
            StringStream stream, PhysicsSceneRef sceneRef) {
        long streamVa = stream.va();
        long refVa = sceneRef.va();
        boolean result = sReadPhysicsSceneFromStream(streamVa, refVa);

        return result;
    }

    /**
     * Read a ragdoll-settings object from the specified stream.
     *
     * @param stream the stream to read from (not null)
     * @param settingsRef where to store the reference to the de-serialized
     * settings (not null, modified)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sReadObject(
            StringStream stream, RagdollSettingsRef settingsRef) {
        long streamVa = stream.va();
        long refVa = settingsRef.va();
        boolean result = sReadRagdollSettingsFromStream(streamVa, refVa);

        return result;
    }

    /**
     * Read a shape-settings object from the specified stream.
     *
     * @param stream the stream to read from (not null)
     * @param settingsRef where to store the reference to the de-serialized
     * shape settings (not null, modified)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sReadObject(
            StringStream stream, ShapeSettingsRef settingsRef) {
        long streamVa = stream.va();
        long refVa = settingsRef.va();
        boolean result = sReadShapeSettingsFromStream(streamVa, refVa);

        return result;
    }

    /**
     * Read a soft-body settings object from the specified stream.
     *
     * @param stream the stream to read from (not null)
     * @param storeSbcs where to store the de-serialized settings (not null,
     * length&gt;0, modified)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sReadObject(
            StringStream stream, SoftBodyCreationSettings[] storeSbcs) {
        long streamVa = stream.va();
        long[] storeVa = {0L};
        boolean result = sReadSbcsFromStream(streamVa, storeVa);
        long bodySettingsVa = storeVa[0];
        storeSbcs[0] = new SoftBodyCreationSettings(bodySettingsVa, true);

        return result;
    }

    /**
     * Read a soft-body shared-settings object from the specified stream.
     *
     * @param stream the stream to read from (not null)
     * @param settingsRef where to store the reference to the de-serialized
     * settings (not null, modified)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sReadObject(
            StringStream stream, SoftBodySharedSettingsRef settingsRef) {
        long streamVa = stream.va();
        long refVa = settingsRef.va();
        boolean result = sReadSbssFromStream(streamVa, refVa);

        return result;
    }

    /**
     * Read a two-body constraint settings object from the specified stream.
     *
     * @param stream the stream to read from (not null)
     * @param settingsRef where to store the reference to the de-serialized
     * settings (not null, modified)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sReadObject(
            StringStream stream, TwoBodyConstraintSettingsRef settingsRef) {
        long streamVa = stream.va();
        long refVa = settingsRef.va();
        boolean result = sReadConstraintSettingsFromStream(streamVa, refVa);

        return result;
    }

    /**
     * Read a vehicle-constraint settings object from the specified stream.
     *
     * @param stream the stream to read from (not null)
     * @param settingsRef where to store the reference to the de-serialized
     * settings (not null, modified)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sReadObject(
            StringStream stream, VehicleConstraintSettingsRef settingsRef) {
        long streamVa = stream.va();
        long refVa = settingsRef.va();
        boolean result = sReadConstraintSettingsFromStream(streamVa, refVa);

        return result;
    }

    /**
     * Read a vehicle-controller settings object from the specified stream.
     *
     * @param stream the stream to read from (not null)
     * @param settingsRef where to store the reference to the de-serialized
     * settings (not null, modified)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sReadObject(
            StringStream stream, VehicleControllerSettingsRef settingsRef) {
        long streamVa = stream.va();
        long refVa = settingsRef.va();
        boolean result = sReadControllerSettingsFromStream(streamVa, refVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static boolean sReadBcsFromStream(
            long streamVa, long[] storeVa);

    native private static boolean sReadConstraintSettings(
            String fileName, long refVa);

    native private static boolean sReadConstraintSettingsFromStream(
            long streamVa, long refVa);

    native private static boolean sReadControllerSettingsFromStream(
            long streamVa, long refVa);

    native private static boolean sReadGroupFilterTable(
            String fileName, long refVa);

    native private static boolean sReadGroupFilterTableFromStream(
            long streamVa, long refVa);

    native private static boolean sReadPhysicsMaterial(
            String fileName, long refVa);

    native private static boolean sReadPhysicsMaterialFromStream(
            long streamVa, long refVa);

    native private static boolean sReadPhysicsScene(
            String fileName, long refVa);

    native private static boolean sReadPhysicsSceneFromStream(
            long streamVa, long refVa);

    native private static boolean sReadRagdollSettings(
            String fileName, long refVa);

    native private static boolean sReadRagdollSettingsFromStream(
            long streamVa, long refVa);

    native private static boolean sReadSbcsFromStream(
            long streamVa, long[] storeVa);

    native private static boolean sReadSbssFromStream(
            long streamVa, long refVa);

    native private static boolean sReadShapeSettingsFromStream(
            long streamVa, long refVa);

    native private static boolean sReadSkeletalAnimation(
            String fileName, long refVa);

    native private static boolean sReadSkeleton(String fileName, long refVa);

    native private static boolean sReadVehicleControllerSettings(
            String fileName, long refVa);
}
