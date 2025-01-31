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
package com.github.stephengold.joltjni;

/**
 * Identify a particular character. (native type: CharacterID)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CharacterId extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an invalid ID.
     */
    public CharacterId() {
        long idVa = createDefault();
        setVirtualAddress(idVa, () -> free(idVa));
    }

    /**
     * Instantiate with the specified native object assigned but not owned.
     * <p>
     * For use in custom listeners.
     *
     * @param idVa the virtual address of the native object to assign (not zero)
     */
    public CharacterId(long idVa) {
        super(idVa);
    }

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param idVa the virtual address of the native object to assign (not zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    CharacterId(long idVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(idVa) : null;
        setVirtualAddress(idVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Convert the ID to a 32-bit integer. The ID is unaffected.
     *
     * @return the value (&ge;0)
     */
    public int getValue() {
        long idVa = va();
        int result = getValue(idVa);

        return result;
    }

    /**
     * Test whether the current ID is equal to the argument. Both IDs are
     * unaffected.
     *
     * @param id2 the 2nd ID to test (not null, unaffected)
     * @return {@code true} if equal, {@code false} if unequal
     */
    public boolean isEqual(CharacterId id2) {
        long id1va = va();
        long id2va = id2.targetVa();
        boolean result = equals(id1va, id2va);

        return result;
    }

    /**
     * Test whether the ID is valid. It is unaffected.
     *
     * @return {@code true} if invalid, {@code false} if valid
     */
    public boolean isInvalid() {
        long idVa = va();
        boolean result = isInvalid(idVa);

        return result;
    }

    /**
     * Set the next available ID to 1.
     */
    public static void sSetNextCharacterId() {
        sSetNextCharacterId(1);
    }
    // *************************************************************************
    // native methods

    native private static long createDefault();

    native static boolean equals(long id1Va, long id2Va);

    native private static void free(long idVa);

    native private static int getValue(long idVa);

    native private static boolean isInvalid(long idVa);

    /**
     * Set the next available ID to the specified value.
     *
     * @param nextValue the desired next ID
     */
    native public static void sSetNextCharacterId(int nextValue);
}
