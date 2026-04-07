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
 * A counted reference to a {@code ConstraintSettings} object. (native type:
 * {@code Ref<ConstraintSettings>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class ConstraintSettingsRef extends Ref {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public ConstraintSettingsRef() {
        long refVa = createDefault();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     */
    ConstraintSettingsRef(long refVa) {
        assert getPtr(refVa) == 0L;

        Runnable freeingAction = () -> free(refVa);
        setVirtualAddress(refVa, freeingAction);
    }

    /**
     * Instantiate a counted reference to the specified settings.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param settings the settings to target (not {@code null})
     */
    ConstraintSettingsRef(long refVa, ConstraintSettings settings) {
        assert settings != null;

        this.ptr = settings;
        Runnable freeingAction = () -> free(refVa);
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // new protected methods

    /**
     * Update the cached target.
     */
    void updatePtr() {
        long refVa = va();
        long targetVa = getPtr(refVa);
        this.ptr = ConstraintSettings.newConstraintSettings(targetVa);
    }
    // *************************************************************************
    // Ref methods

    /**
     * Access the target settings, if any.
     *
     * @return the pre-existing object, or {@code null} if the reference is
     * empty
     */
    @Override
    public ConstraintSettings getPtr() {
        ConstraintSettings result = (ConstraintSettings) ptr;
        return result;
    }

    /**
     * Return the address of the native {@code ConstraintSettings}. No objects
     * are affected.
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
     * Create another counted reference to the targeted settings.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public ConstraintSettingsRef toRef() {
        ConstraintSettingsRef result;
        if (ptr == null) {
            result = new ConstraintSettingsRef();
        } else {
            long refVa = va();
            long copyVa = copy(refVa);
            ConstraintSettings settings = (ConstraintSettings) ptr;
            result = new ConstraintSettingsRef(copyVa, settings);
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
