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

import com.github.stephengold.joltjni.readonly.ConstCharacterVirtual;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple implementation of the Character-versus-Character collision
 * interface.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CharacterVsCharacterCollisionSimple
        extends CharacterVsCharacterCollision {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default interface.
     */
    public CharacterVsCharacterCollisionSimple() {
        long interfaceVa = createDefault();
        setVirtualAddress(interfaceVa, true);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Add the specified character to the collision list.
     *
     * @param character the character to add (not null)
     */
    public void add(ConstCharacterVirtual character) {
        long interfaceVa = va();
        long characterVa = character.targetVa();
        add(interfaceVa, characterVa);
    }

    /**
     * Enumerate the characters in the collision list.
     *
     * @return a new, mutable list of new JVM objects with pre-existing native
     * objects assigned
     */
    public List<CharacterVirtual> getCharactersAsList() {
        long interfaceVa = va();
        int numCharacters = countCharacters(interfaceVa);
        List<CharacterVirtual> result = new ArrayList<>(numCharacters);
        for (int i = 0; i < numCharacters; ++i) {
            long characterVa = getCharacter(interfaceVa, i);
            CharacterVirtual character = new CharacterVirtual(characterVa);
            result.add(character);
        }

        return result;
    }

    /**
     * Remove the specified character from the collide list.
     *
     * @param character the character to add (not null)
     */
    public void remove(ConstCharacterVirtual character) {
        long interfaceVa = va();
        long characterVa = character.targetVa();
        remove(interfaceVa, characterVa);
    }
    // *************************************************************************
    // native private methods

    native private static void add(long interfaceVa, long characterVa);

    native private static int countCharacters(long interfaceVa);

    native private static long createDefault();

    native private static long getCharacter(long interfaceVa, int index);

    native private static void remove(long interfaceVa, long characterVa);
}
