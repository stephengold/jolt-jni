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

import com.github.stephengold.joltjni.enumerate.EStreamType;
import com.github.stephengold.joltjni.readonly.ConstBodyCreationSettings;
import com.github.stephengold.joltjni.readonly.ConstSerializableObject;
import com.github.stephengold.joltjni.readonly.ConstSoftBodyCreationSettings;
import com.github.stephengold.joltjni.readonly.ConstSoftBodySharedSettings;
import com.github.stephengold.joltjni.std.StringStream;

/**
 * Utility class for writing uncooked Jolt-Physics objects to files or streams.
 * Data serialized this way is more like to be compatible with future versions
 * of the library than the
 * {@code saveBinaryState()}/{@code restoreBinaryState()} methods.
 *
 * @author Stephen Gold sgold@sonic.net
 * @see com.github.stephengold.joltjni.ObjectStreamIn
 */
final public class ObjectStreamOut {
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private ObjectStreamOut() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Write the specified serializable object to the specified file.
     *
     * @param fileName the path to the file (not null)
     * @param streamType the type of file (not null)
     * @param settings the object to write (not null, unaffected)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sWriteObject(String fileName,
            EStreamType streamType, ConstSerializableObject settings) {
        int ordinal = streamType.ordinal();
        long settingsVa = settings.targetVa();
        boolean result
                = sWriteSerializableObjectToFile(fileName, ordinal, settingsVa);

        return result;
    }

    /**
     * Write the specified body-creation settings to the specified file.
     *
     * @param fileName the path to the file (not null)
     * @param streamType the type of file (not null)
     * @param settings the settings to write (not null, unaffected)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sWriteObject(String fileName,
            EStreamType streamType, ConstBodyCreationSettings settings) {
        int ordinal = streamType.ordinal();
        long settingsVa = settings.targetVa();
        boolean result = sWriteBcsToFile(fileName, ordinal, settingsVa);

        return result;
    }

    /**
     * Write the specified soft-body creation settings to the specified file.
     *
     * @param fileName the path to the file (not null)
     * @param streamType the type of file (not null)
     * @param settings the settings to write (not null, unaffected)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sWriteObject(String fileName,
            EStreamType streamType, ConstSoftBodyCreationSettings settings) {
        int ordinal = streamType.ordinal();
        long settingsVa = settings.targetVa();
        boolean result = sWriteSbcsToFile(fileName, ordinal, settingsVa);

        return result;
    }

    /**
     * Write the specified soft-body shared settings to the specified file.
     *
     * @param fileName the path to the file (not null)
     * @param streamType the type of file (not null)
     * @param settings the settings to write (not null, unaffected)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sWriteObject(String fileName,
            EStreamType streamType, ConstSoftBodySharedSettings settings) {
        int ordinal = streamType.ordinal();
        long settingsVa = settings.targetVa();
        boolean result = sWriteSbssToFile(fileName, ordinal, settingsVa);

        return result;
    }

    /**
     * Write the specified scene to the specified file.
     *
     * @param fileName the path to the file (not null)
     * @param streamType the type of file (not null)
     * @param scene the scene to write (not null, unaffected)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sWriteObject(String fileName,
            EStreamType streamType, PhysicsScene scene) {
        int ordinal = streamType.ordinal();
        long sceneVa = scene.va();
        boolean result = sWritePhysicsSceneToFile(fileName, ordinal, sceneVa);

        return result;
    }

    /**
     * Write the specified ragdoll settings to the specified file.
     *
     * @param fileName the path to the file (not null)
     * @param streamType the type of file (not null)
     * @param settings the settings to write (not null, unaffected)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sWriteObject(String fileName,
            EStreamType streamType, RagdollSettings settings) {
        int ordinal = streamType.ordinal();
        long settingsVa = settings.va();
        boolean result
                = sWriteRagdollSettingsToFile(fileName, ordinal, settingsVa);

        return result;
    }

    /**
     * Write the specified body-creation settings to the specified stream.
     *
     * @param stream the stream to write to (not null)
     * @param streamType the type of stream (not null)
     * @param settings the settings to write (not null, unaffected)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sWriteObject(StringStream stream,
            EStreamType streamType, ConstBodyCreationSettings settings) {
        long streamVa = stream.va();
        int ordinal = streamType.ordinal();
        long settingsVa = settings.targetVa();
        boolean result = sWriteBcs(streamVa, ordinal, settingsVa);

        return result;
    }

    /**
     * Write the specified serializable object to the specified stream.
     *
     * @param stream the stream to write to (not null)
     * @param streamType the type of stream (not null)
     * @param settings the object to write (not null, unaffected)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sWriteObject(StringStream stream,
            EStreamType streamType, ConstSerializableObject settings) {
        long streamVa = stream.va();
        int ordinal = streamType.ordinal();
        long settingsVa = settings.targetVa();
        boolean result
                = sWriteSerializableObject(streamVa, ordinal, settingsVa);

        return result;
    }

    /**
     * Write the specified soft-body creation settings to the specified stream.
     *
     * @param stream the stream to write to (not null)
     * @param streamType the type of stream (not null)
     * @param settings the settings to write (not null, unaffected)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sWriteObject(StringStream stream,
            EStreamType streamType, ConstSoftBodyCreationSettings settings) {
        long streamVa = stream.va();
        int ordinal = streamType.ordinal();
        long settingsVa = settings.targetVa();
        boolean result = sWriteSbcs(streamVa, ordinal, settingsVa);

        return result;
    }

    /**
     * Write the specified soft-body shared settings to the specified stream.
     *
     * @param stream the stream to write to (not null)
     * @param streamType the type of stream (not null)
     * @param settings the settings to write (not null, unaffected)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sWriteObject(StringStream stream,
            EStreamType streamType, ConstSoftBodySharedSettings settings) {
        long streamVa = stream.va();
        int ordinal = streamType.ordinal();
        long settingsVa = settings.targetVa();
        boolean result = sWriteSbss(streamVa, ordinal, settingsVa);

        return result;
    }

    /**
     * Write the specified scene to the specified stream.
     *
     * @param stream the stream to write to (not null)
     * @param streamType the type of stream (not null)
     * @param scene the scene to write (not null, unaffected)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sWriteObject(StringStream stream,
            EStreamType streamType, PhysicsScene scene) {
        long streamVa = stream.va();
        int ordinal = streamType.ordinal();
        long sceneVa = scene.va();
        boolean result = sWritePhysicsScene(streamVa, ordinal, sceneVa);

        return result;
    }

    /**
     * Write the specified ragdoll settings to the specified stream.
     *
     * @param stream the stream to write to (not null)
     * @param streamType the type of stream (not null)
     * @param settings the settings to write (not null, unaffected)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sWriteObject(StringStream stream,
            EStreamType streamType, RagdollSettings settings) {
        long streamVa = stream.va();
        int ordinal = streamType.ordinal();
        long settingsVa = settings.va();
        boolean result = sWriteRagdollSettings(streamVa, ordinal, settingsVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static boolean sWriteBcs(
            long streamVa, int ordinal, long settingsVa);

    native private static boolean sWriteBcsToFile(
            String fileName, int ordinal, long settingsVa);

    native private static boolean sWritePhysicsScene(
            long streamVa, int ordinal, long sceneVa);

    native private static boolean sWritePhysicsSceneToFile(
            String fileName, int ordinal, long sceneVa);

    native private static boolean sWriteRagdollSettings(
            long streamVa, int ordinal, long settingsVa);

    native private static boolean sWriteRagdollSettingsToFile(
            String fileName, int ordinal, long settingsVa);

    native private static boolean sWriteSbcs(
            long streamVa, int ordinal, long settingsVa);

    native private static boolean sWriteSbcsToFile(
            String fileName, int ordinal, long settingsVa);

    native private static boolean sWriteSbss(
            long streamVa, int ordinal, long settingsVa);

    native private static boolean sWriteSbssToFile(
            String fileName, int ordinal, long settingsVa);

    native private static boolean sWriteSerializableObject(
            long streamVa, int ordinal, long serializableObjectVa);

    native private static boolean sWriteSerializableObjectToFile(
            String fileName, int ordinal, long serializableObjectVa);
}
