/*
Copyright (c) 2024-2026 Stephen Gold

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
 * A counted reference to a {@code PhysicsMaterial}. (native type:
 * {@code Ref<PhysicsMaterial>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class PhysicsMaterialRef extends Ref {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public PhysicsMaterialRef() {
        long refVa = createDefault();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     */
    PhysicsMaterialRef(long refVa) {
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
    PhysicsMaterialRef(long refVa, PhysicsMaterial material) {
        assert material != null;

        this.ptr = material;
        Runnable freeingAction = () -> free(refVa);
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the target material, assuming it is a
     * {@code PhysicsMaterialSimple}.
     *
     * @return the pre-existing object, or {@code null} if the reference is
     * empty
     */
    public PhysicsMaterialSimple getPtrAsSimple() {
        PhysicsMaterialSimple result = (PhysicsMaterialSimple) ptr;
        return result;
    }
    // *************************************************************************
    // new protected methods

    /**
     * Update the cached target.
     */
    void updatePtr() {
        long refVa = va();
        long targetVa = getPtr(refVa);
        if (targetVa == 0L) {
            this.ptr = null;
        } else {
            this.ptr = new PhysicsMaterial(targetVa);
        }
    }
    // *************************************************************************
    // Ref methods

    /**
     * Access the targeted material, if any.
     *
     * @return the pre-existing object, or {@code null} if the reference is
     * empty
     */
    @Override
    public PhysicsMaterial getPtr() {
        PhysicsMaterial result = (PhysicsMaterial) ptr;
        return result;
    }

    /**
     * Return the address of the native {@code PhysicsMaterial}. No objects are
     * affected.
     *
     * @return the virtual address, or zero if the reference is empty
     */
    @Override
    public long targetVa() {
        long refVa = va();
        long result = getPtr(refVa);
        assert result == (ptr == null ? 0L : getPtr().va());

        return result;
    }

    /**
     * Create another counted reference to the targeted material.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public PhysicsMaterialRef toRef() {
        PhysicsMaterialRef result;
        if (ptr == null) {
            result = new PhysicsMaterialRef();
        } else {
            long refVa = va();
            long copyVa = copy(refVa);
            PhysicsMaterial target = (PhysicsMaterial) ptr;
            result = new PhysicsMaterialRef(copyVa, target);
        }

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long copy(long refVa);

    native private static long createDefault();

    native static void free(long refVa);

    native static long getPtr(long refVa);
}
