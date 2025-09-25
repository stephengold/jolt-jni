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

import com.github.stephengold.joltjni.readonly.ConstSerializableObject;

/**
 * A physics object that can be serialized (saved) and de-serialized (restored).
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class SerializableObject
        extends JoltPhysicsObject
        implements ConstSerializableObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with no native object assigned.
     */
    SerializableObject() {
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param virtualAddress the virtual address of the native object to assign
     * (not zero)
     */
    SerializableObject(JoltPhysicsObject container, long virtualAddress) {
        super(container, virtualAddress);
    }

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param virtualAddress the virtual address of the native object to assign
     * (not zero)
     */
    SerializableObject(long virtualAddress) {
        super(virtualAddress);
    }
    // *************************************************************************
    // ConstSerializableObject

    /**
     * Access the run-time type information of the current object. (native
     * method: GetRTTI)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public Rtti getRtti() {
        long jpoVa = va();
        long resultVa = getRtti(jpoVa);

        Rtti result = new Rtti(resultVa);
        return result;
    }
    // *************************************************************************
    // native methods

    native static long getRtti(long objectVa);
}
