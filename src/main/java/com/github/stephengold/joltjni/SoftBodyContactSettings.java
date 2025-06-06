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

import com.github.stephengold.joltjni.readonly.ConstSoftBodyContactSettings;

/**
 * Properties of a contact constraint that can be overridden during certain
 * {@code SoftBodyContactListener} callbacks.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SoftBodyContactSettings
        extends JoltPhysicsObject
        implements ConstSoftBodyContactSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate the default settings for testing.
     */
    public SoftBodyContactSettings() {
        long settingsVa = createDefault();
        setVirtualAddress(settingsVa, () -> free(settingsVa));
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the constraint to copy (not {@code null}, unaffected)
     */
    public SoftBodyContactSettings(ConstSoftBodyContactSettings original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa, () -> free(copyVa));
    }

    /**
     * Instantiate settings with the specified native object assigned but not
     * owned.
     * <p>
     * For use in custom contact listeners.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    public SoftBodyContactSettings(long settingsVa) {
        setVirtualAddress(settingsVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the scale factor for the inverse inertia of body 2. (native
     * attribute: mInvInertiaScale2)
     *
     * @param factor the factor (0 = infinite inertia, 1 = use original inertia,
     * default=1)
     */
    public void setInvInertiaScale2(float factor) {
        long settingsVa = va();
        setInvInertiaScale2(settingsVa, factor);
    }

    /**
     * Alter the scale factor for the inverse mass of the soft body. (native
     * attribute: mInvMassScale1)
     *
     * @param factor the factor (0 = infinite mass, 1 = use original mass,
     * default=1)
     */
    public void setInvMassScale1(float factor) {
        long settingsVa = va();
        setInvMassScale1(settingsVa, factor);
    }

    /**
     * Alter the scale factor for the inverse mass of body 2. (native attribute:
     * mInvMassScale2)
     *
     * @param factor the factor (0 = infinite mass, 1 = use original mass,
     * default=1)
     */
    public void setInvMassScale2(float factor) {
        long settingsVa = va();
        setInvMassScale2(settingsVa, factor);
    }

    /**
     * Alter whether the contact should be treated as a sensor (no collision
     * response). (native attribute: mIsSensor)
     *
     * @param setting {@code true} to treat as a sensor, otherwise {@code false}
     * (default=false)
     */
    public void setIsSensor(boolean setting) {
        long settingsVa = va();
        setIsSensor(settingsVa, setting);
    }
    // *************************************************************************
    // ConstSoftBodyContactSettings methods

    /**
     * Return the scale factor for the inverse inertia of body 2. The settings
     * are unaffected. (native attribute: mInvInertiaScale2)
     *
     * @return the factor (0 = infinite inertia, 1 = use original inertia)
     */
    @Override
    public float getInvInertiaScale2() {
        long settingsVa = va();
        float result = getInvInertiaScale2(settingsVa);

        return result;
    }

    /**
     * Return the scale factor for the inverse mass of the soft body. The
     * settings are unaffected. (native attribute: mInvMassScale1)
     *
     * @return the factor (0 = infinite mass, 1 = use original mass)
     */
    @Override
    public float getInvMassScale1() {
        long settingsVa = va();
        float result = getInvMassScale1(settingsVa);

        return result;
    }

    /**
     * Return the scale factor for the inverse mass of body 2. The settings are
     * unaffected. (native attribute: mInvMassScale2)
     *
     * @return the factor (0 = infinite mass, 1 = use original mass)
     */
    @Override
    public float getInvMassScale2() {
        long settingsVa = va();
        float result = getInvMassScale2(settingsVa);

        return result;
    }

    /**
     * Test whether the contact should be treated as a sensor (no collision
     * response). The settings are unaffected. (native attribute: mIsSensor)
     *
     * @return {@code true} if treated as a sensor, otherwise {@code false}
     */
    @Override
    public boolean getIsSensor() {
        long settingsVa = va();
        boolean result = getIsSensor(settingsVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static void free(long settingsVa);

    native private static float getInvInertiaScale2(long settingsVa);

    native private static float getInvMassScale1(long settingsVa);

    native private static float getInvMassScale2(long settingsVa);

    native private static boolean getIsSensor(long settingsVa);

    native private static void setInvInertiaScale2(
            long settingsVa, float scale);

    native private static void setInvMassScale1(long settingsVa, float scale);

    native private static void setInvMassScale2(long settingsVa, float scale);

    native private static void setIsSensor(long settingsVa, boolean setting);
}
