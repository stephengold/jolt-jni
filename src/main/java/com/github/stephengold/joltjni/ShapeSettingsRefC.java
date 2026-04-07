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

import com.github.stephengold.joltjni.readonly.ConstShapeSettings;

/**
 * A counted reference to a {@code ConstShapeSettings}. (native type:
 * {@code RefConst<ShapeSettings>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class ShapeSettingsRefC extends JoltPhysicsObject {
    // *************************************************************************
    // fields

    /**
     * cache the targeted settings to reduce duplication
     */
    private ConstShapeSettings ptr;
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public ShapeSettingsRefC() {
        long refVa = createDefault();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a reference to the specified target.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param target the target JVM object (not {@code null})
     */
    ShapeSettingsRefC(long refVa, ConstShapeSettings target) {
        assert target != null;

        this.ptr = target;
        Runnable freeingAction = () -> free(refVa);
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the targeted settings, if any.
     *
     * @return the pre-existing object, or {@code null} if the reference is
     * empty
     */
    public ConstShapeSettings getPtr() {
        return ptr;
    }

    /**
     * Create another read-only counted reference to the targeted settings.
     *
     * @return a new JVM object with a new native object assigned
     */
    public ShapeSettingsRefC toRefC() {
        long refVa = va();
        long copyVa = copy(refVa);
        ShapeSettingsRefC result;
        if (ptr == null) {
            result = new ShapeSettingsRefC();
        } else {
            result = new ShapeSettingsRefC(copyVa, ptr);
        }

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createDefault();

    native private static long copy(long refVa);

    native private static void free(long refVa);

    native private static long getPtr(long refVa);
}
