/*
Copyright (c) 2026 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.ConstContactKey;

/**
 * Identify a contact between a virtual character and a body or another virtual
 * character. (native type: {@code CharacterVirtual::ContactKey})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ContactKey extends JoltPhysicsObject implements ConstContactKey {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default key.
     */
    public ContactKey() {
        long keyVa = createDefault();
        Runnable freeingAction = () -> free(keyVa);
        setVirtualAddress(keyVa, freeingAction);
    }

    /**
     * Instantiate a copy of the specified key.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public ContactKey(ContactKey original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        Runnable freeingAction = () -> free(copyVa);
        setVirtualAddress(copyVa, freeingAction);
    }

    /**
     * Instantiate a key with the specified parameters.
     *
     * @param idType "b" for body or "c" for character
     * @param bodyOrCharacterId the {@code BodyID} or {@code CharacterID} of the
     * other colliding object
     * @param subShapeId the {@code SubShapeID} of the shape that is in contact
     */
    public ContactKey(char idType, int bodyOrCharacterId, int subShapeId) {
        long keyVa = createKey(idType, bodyOrCharacterId, subShapeId);
        Runnable freeingAction = () -> free(keyVa);
        setVirtualAddress(keyVa, freeingAction);
    }

    /**
     * Instantiate a key with no native object assigned.
     *
     * @param dummy unused argument to distinguish from the zero-arg constructor
     */
    ContactKey(boolean dummy) {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Restore the key from the specified recorder.
     *
     * @param recorder the recorder to restore from (not {@code null})
     */
    public void restoreState(StateRecorder recorder) {
        long keyVa = va();
        long recorderVa = recorder.va();
        restoreState(keyVa, recorderVa);
    }
    // *************************************************************************
    // ConstContactKey methods

    /**
     * Return the ID of the colliding body. The key is unaffected. (native
     * attribute: mBodyB)
     *
     * @return the {@code BodyID} value
     */
    @Override
    public int getBodyB() {
        long keyVa = va();
        int result = getBodyB(keyVa);

        return result;
    }

    /**
     * Return the ID of the colliding character. The key is unaffected. (native
     * attribute: mCharacterIDB)
     *
     * @return the {@code CharacterID} value
     */
    @Override
    public int getCharacterIdB() {
        long keyVa = va();
        int result = getCharacterIdB(keyVa);

        return result;
    }

    /**
     * Return the sub-shape ID of the colliding body. The contact is unaffected.
     * (native attribute: mSubShapeIDB)
     *
     * @return a {@code SubShapeID} value (typically negative)
     */
    @Override
    public int getSubShapeIdB() {
        long contactVa = va();
        int result = getSubShapeIdB(contactVa);

        return result;
    }

    /**
     * Test for content equivalence with another key. The key is unaffected.
     * (native operator: binary {@code ==})
     *
     * @param other the key to compare with (not {@code null}, unaffected)
     * @return {@code true} if equivalent, otherwise {@code false}
     */
    @Override
    public boolean isEqual(ConstContactKey other) {
        long keyVa = va();
        long otherVa = other.targetVa();
        boolean result = isEqual(keyVa, otherVa);

        return result;
    }

    /**
     * Test for content equivalence with another key. The key is
     * unaffected.(native operator: binary {@code !=})
     *
     * @param other the key to compare with (not {@code null}, unaffected)
     * @return {@code false} if equivalent, otherwise {@code true}
     */
    @Override
    public boolean isNotEqual(ConstContactKey other) {
        boolean result = !isEqual(other);
        return result;
    }

    /**
     * Save the key to the specified recorder. The key is unaffected.
     *
     * @param recorder the recorder to save to (not {@code null})
     */
    @Override
    public void saveState(StateRecorder recorder) {
        long keyVa = va();
        long recorderVa = recorder.va();
        saveState(keyVa, recorderVa);
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static long createKey(
            char idType, int bodyOrCharacterId, int subShapeId);

    native private static void free(long keyVa);

    native private static int getBodyB(long keyVa);

    native private static int getCharacterIdB(long keyVa);

    native private static int getSubShapeIdB(long keyVa);

    native private static boolean isEqual(long keyVa, long otherVa);

    native private static void restoreState(long keyVa, long recorderVa);

    native private static void saveState(long keyVa, long recorderVa);
}
