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

import com.github.stephengold.joltjni.enumerate.EStreamType;
import com.github.stephengold.joltjni.std.StringStream;

/**
 * Utility class for writing Jolt Physics objects to streams.
 *
 * @author Stephen Gold sgold@sonic.net
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

    native private static boolean sWritePhysicsScene(
            long streamVa, int ordinal, long sceneVa);

    native private static boolean sWriteRagdollSettings(
            long streamVa, int ordinal, long settingsVa);
}
