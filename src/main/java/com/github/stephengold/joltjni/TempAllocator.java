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

/**
 * A memory allocator suitable for use by
 * {@code PhysicsSystem.update()}, {@code CharacterVirtual.extendedUpdate()}, or
 * {@code CharacterVirtual.setShape()}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class TempAllocator extends NonCopyable {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an allocator.
     */
    TempAllocator() {
    }
    // *************************************************************************
    // protected methods

    /**
     * Assign a native object, assuming there's none already assigned.
     *
     * @param allocatorVa the virtual address of the native object to assign
     * (not zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    final void setVirtualAddress(long allocatorVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(allocatorVa) : null;
        setVirtualAddress(allocatorVa, freeingAction);
    }
    // *************************************************************************
    // native private methods

    native private static void free(long allocatorVa);
}
