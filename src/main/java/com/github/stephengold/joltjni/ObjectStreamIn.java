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

import com.github.stephengold.joltjni.template.Ref;

/**
 * Utility class for reading Jolt Physics objects from files.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ObjectStreamIn {
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
     * Read an object from the specified file.
     *
     * @param fileName the path to the file (not null)
     * @param storeRef where to store the de-serialized object (not null,
     * modified)
     * @return true if successful, otherwise false
     */
    public static boolean sReadObject(String fileName, Ref storeRef) {
        long refVa = storeRef.va();
        boolean result;
        if (storeRef instanceof RagdollSettingsRef) {
            result = sReadRagdollSettings(fileName, refVa);
        } else if (storeRef instanceof PhysicsSceneRef) {
            result = sReadPhysicsScene(fileName, refVa);
        } else if (storeRef instanceof SkeletalAnimationRef) {
            result = sReadSkeletalAnimation(fileName, refVa);
        } else {
            throw new IllegalArgumentException(
                    storeRef.getClass().getSimpleName());
        }

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static boolean sReadRagdollSettings(
            String fileName, long refVa);

    native private static boolean sReadPhysicsScene(
            String fileName, long refVa);

    native private static boolean sReadSkeletalAnimation(
            String fileName, long refVa);
}
