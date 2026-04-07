/*
Copyright (c) 2025-2026 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.ConstPhysicsMaterial;

/**
 * A counted reference to a {@code ConstPhysicsMaterial}. (native type:
 * {@code RefConst<PhysicsMaterial>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class PhysicsMaterialRefC extends JoltPhysicsObject {
    // *************************************************************************
    // fields

    /**
     * cache the target to reduce duplication
     */
    private ConstPhysicsMaterial ptr;
    // *************************************************************************
    // constructors

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     */
    PhysicsMaterialRefC(long refVa) {
        assert getPtr(refVa) == 0L;

        Runnable freeingAction = () -> free(refVa);
        setVirtualAddress(refVa, freeingAction);
    }

    /**
     * Instantiate a counted reference to the specified material.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param material the material to target (not {@code null})
     */
    PhysicsMaterialRefC(long refVa, ConstPhysicsMaterial material) {
        assert material != null;

        this.ptr = material;
        Runnable freeingAction = () -> free(refVa);
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the targeted material, if any.
     *
     * @return the pre-existing object, or {@code null} if the reference is
     * empty
     */
    public ConstPhysicsMaterial getPtr() {
        return ptr;
    }
    // *************************************************************************
    // native methods

    native private static void free(long refVa);

    native static long getPtr(long refVa);
}
