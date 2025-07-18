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

/**
 * An interface to determine which characters (if any) should collide with other
 * characters.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class CharacterVsCharacterCollision extends NonCopyable {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with no native object assigned.
     */
    CharacterVsCharacterCollision() {
    }
    // *************************************************************************
    // protected methods

    /**
     * Assign a native object (assuming there's none already assigned) and
     * designate the JVM object as the owner.
     *
     * @param collisionVa the virtual address of the native object to assign
     * (not zero)
     */
    final void setVirtualAddressAsOwner(long collisionVa) {
        Runnable freeingAction = () -> free(collisionVa);
        setVirtualAddress(collisionVa, freeingAction);
    }
    // *************************************************************************
    // native private methods

    native private static void free(long collisionVa);
}
