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

import com.github.stephengold.joltjni.readonly.ConstShapeSettings;
import com.github.stephengold.joltjni.template.Ref;

/**
 * A counted reference to a {@code ShapeSettings}. (native type:
 * {@code Ref<ShapeSettings>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class ShapeSettingsRef extends Ref implements ConstShapeSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public ShapeSettingsRef() {
        long refVa = createDefault();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    ShapeSettingsRef(long refVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(refVa) : null;
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // ConstShapeSettings methods

    /**
     * Generate a {@code ShapeResult} from the referenced {@code ShapeSettings}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public ShapeResult create() {
        long settingsVa = targetVa();
        long resultVa = ShapeSettings.create(settingsVa);
        ShapeResult result = new ShapeResult(resultVa, true);

        return result;
    }

    /**
     * Count the active references to the native {@code ShapeSettings}. The
     * settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long settingsVa = targetVa();
        int result = ShapeSettings.getRefCount(settingsVa);

        return result;
    }

    /**
     * Access the type information of the current object. (native method:
     * getRTTI)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public Rtti getRtti() {
        long jpoVa = targetVa();
        long resultVa = SerializableObject.getRtti(jpoVa);

        Rtti result = new Rtti(resultVa);
        return result;
    }

    /**
     * Create a counted reference to the native {@code ShapeSettings}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public ShapeSettingsRefC toRefC() {
        long refVa = va();
        long copyVa = toRefC(refVa);
        ShapeSettingsRefC result = new ShapeSettingsRefC(copyVa, true);

        return result;
    }
    // *************************************************************************
    // Ref methods

    /**
     * Temporarily access the referenced {@code ShapeSettings}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public ShapeSettings getPtr() {
        long settingsVa = targetVa();
        ShapeSettings result = ShapeSettings.newShapeSettings(settingsVa);

        return result;
    }

    /**
     * Return the address of the native {@code ShapeSettings}. No objects are
     * affected.
     *
     * @return a virtual address (not zero)
     */
    @Override
    public long targetVa() {
        long refVa = va();
        long result = getPtr(refVa);

        return result;
    }

    /**
     * Create another counted reference to the native {@code ShapeSettings}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public ShapeSettingsRef toRef() {
        long refVa = va();
        long copyVa = copy(refVa);
        ShapeSettingsRef result = new ShapeSettingsRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long copy(long refVa);

    native private static long createDefault();

    native static void free(long refVa);

    native private static long getPtr(long refVa);

    native private static long toRefC(long refVa);
}
