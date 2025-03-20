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

import com.github.stephengold.joltjni.readonly.ConstCharacter;

/**
 * A counted reference to a {@code ConstCharacter}. (native type:
 * {@code RefConst<Character>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class CharacterRefC extends JoltPhysicsObject {
    // *************************************************************************
    // fields

    /**
     * where to add the body (may be null)
     */
    final private PhysicsSystem system;
    // *************************************************************************
    // constructors

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param physicsSystem where to add the body
     */
    CharacterRefC(long refVa, PhysicsSystem physicsSystem) {
        this.system = physicsSystem;
        /*
         * Passing physicsSystem to the Runnable ensures that the underlying
         * system won't get cleaned before the character.
         */
        Runnable freeingAction = () -> freeWithSystem(refVa, physicsSystem);
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Temporarily access the referenced {@code ConstCharacter}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public ConstCharacter getPtr() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        ConstCharacter result = new com.github.stephengold.joltjni.Character(
                characterVa, system);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void freeWithSystem(long refVa, PhysicsSystem unused);

    native private static long getPtr(long refVa);
}
