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
     * Instantiate a counted reference to the specified settings.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param settings the settings to target (not {@code null})
     */
    ShapeSettingsRef(long refVa, ShapeSettings settings) {
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
        if (targetVa == 0L) {
            this.ptr = null;
        } else {
            this.ptr = ShapeSettings.newShapeSettings(targetVa);
        }
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
     * Access the type information of the current object. (native function:
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
     * Create a read-only counted reference to the targeted settings.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public ShapeSettingsRefC toRefC() {
        long refVa = va();
        long copyVa = toRefC(refVa);
        ConstShapeSettings target = (ConstShapeSettings) ptr;
        ShapeSettingsRefC result = new ShapeSettingsRefC(copyVa, target);

        return result;
    }
    // *************************************************************************
    // Ref methods

    /**
     * Access the targeted settings, if any.
     *
     * @return the pre-existing object, or {@code null} if the reference is
     * empty
     */
    @Override
    public ShapeSettings getPtr() {
        ShapeSettings result = (ShapeSettings) ptr;
        return result;
    }

    /**
     * Return the address of the native {@code ShapeSettings}. No objects are
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
     * Create another counted reference to the targeted settings.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public ShapeSettingsRef toRef() {
        ShapeSettingsRef result;
        if (ptr == null) {
            result = new ShapeSettingsRef();
        } else {
            long refVa = va();
            long copyVa = copy(refVa);
            ShapeSettings settings = (ShapeSettings) ptr;
            result = new ShapeSettingsRef(copyVa, settings);
        }

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
