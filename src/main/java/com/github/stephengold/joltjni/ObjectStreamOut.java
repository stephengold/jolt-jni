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
import com.github.stephengold.joltjni.readonly.ConstJoltPhysicsObject;
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
     * Write the specified object to the specified file.
     *
     * @param fileName the path to the file (not null)
     * @param streamType the type of file (not null)
     * @param writeObject the object to write (not null, unaffected)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sWriteObject(String fileName,
            EStreamType streamType, ConstJoltPhysicsObject writeObject) {
        int ordinal = streamType.ordinal();
        long objVa = writeObject.targetVa();

        boolean result;
        if (writeObject instanceof ConstSerializableObject) {
            result = sWriteSerializableObjectToFile(fileName, ordinal, objVa);

        } else if (writeObject instanceof ConstBodyCreationSettings) {
            result = sWriteBcsToFile(fileName, ordinal, objVa);

        } else if (writeObject instanceof ConstSoftBodyCreationSettings) {
            result = sWriteSbcsToFile(fileName, ordinal, objVa);

        } else if (writeObject instanceof ConstSoftBodySharedSettings) {
            result = sWriteSbssToFile(fileName, ordinal, objVa);

        } else if (writeObject instanceof PhysicsScene) {
            result = sWritePhysicsSceneToFile(fileName, ordinal, objVa);

        } else if (writeObject instanceof RagdollSettings) {
            result = sWriteRagdollSettingsToFile(fileName, ordinal, objVa);

        } else { // TODO: implement for other types
            Class<?> clas = writeObject.getClass();
            String className = clas.getSimpleName();
            throw new IllegalArgumentException(className);
        }

        return result;
    }

    /**
     * Write the specified object to the specified stream.
     *
     * @param stream the stream to write to (not null)
     * @param streamType the type of stream (not null)
     * @param writeObject the object to write (not null, unaffected)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public static boolean sWriteObject(StringStream stream,
            EStreamType streamType, ConstJoltPhysicsObject writeObject) {
        long streamVa = stream.va();
        int ordinal = streamType.ordinal();
        long objVa = writeObject.targetVa();

        boolean result;
        if (writeObject instanceof ConstSerializableObject) {
            result = sWriteSerializableObject(streamVa, ordinal, objVa);

        } else if (writeObject instanceof ConstBodyCreationSettings) {
            result = sWriteBcs(streamVa, ordinal, objVa);

        } else if (writeObject instanceof ConstSoftBodyCreationSettings) {
            result = sWriteSbcs(streamVa, ordinal, objVa);

        } else if (writeObject instanceof ConstSoftBodySharedSettings) {
            result = sWriteSbss(streamVa, ordinal, objVa);

        } else if (writeObject instanceof PhysicsScene) {
            result = sWritePhysicsScene(streamVa, ordinal, objVa);

        } else if (writeObject instanceof RagdollSettings) {
            result = sWriteRagdollSettings(streamVa, ordinal, objVa);

        } else { // TODO: implement for other types
            Class clas = writeObject.getClass();
            String className = clas.getSimpleName();
            throw new IllegalArgumentException(className);
        }

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
