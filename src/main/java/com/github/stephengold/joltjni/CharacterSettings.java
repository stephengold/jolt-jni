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

import com.github.stephengold.joltjni.readonly.ConstCharacterSettings;

/**
 * Settings used to create a {@code Character}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CharacterSettings
        extends CharacterBaseSettings
        implements ConstCharacterSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public CharacterSettings() {
        long settingsVa = createCharacterSettings();
        setVirtualAddress(settingsVa, null); // not owner due to ref counting
    }

    /**
     * Instantiate settings with the specified native object assigned but not
     * owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    CharacterSettings(long settingsVa) {
        super(settingsVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the friction ratio. (native field: mFriction)
     *
     * @param friction the desired ratio (typically &ge;0 and &le;1,
     * default=0.2)
     */
    public void setFriction(float friction) {
        long settingsVa = va();
        setFriction(settingsVa, friction);
    }

    /**
     * Alter the gravity multiplier. (native field: mGravityFactor)
     *
     * @param factor the desired multiplier (default=1)
     */
    public void setGravityFactor(float factor) {
        long settingsVa = va();
        setGravityFactor(settingsVa, factor);
    }

    /**
     * Alter the object layer to which the character will be added. (native
     * field: mLayer)
     *
     * @param objLayer the ID of the desired object layer (&ge;0,
     * &lt;numObjectLayers, default=0)
     */
    public void setLayer(int objLayer) {
        long settingsVa = va();
        setLayer(settingsVa, objLayer);
    }

    /**
     * Alter the character's mass. (native field: mMass)
     *
     * @param mass the desired mass (default=80)
     */
    public void setMass(float mass) {
        long settingsVa = va();
        setMass(settingsVa, mass);
    }
    // *************************************************************************
    // ConstCharacterSettings methods

    /**
     * Return the friction ratio. The settings are unaffected. (native field:
     * mFriction)
     *
     * @return the ratio (typically &ge;0 and &le;1)
     */
    @Override
    public float getFriction() {
        long settingsVa = va();
        float result = getFriction(settingsVa);

        return result;
    }

    /**
     * Return the gravity factor. The settings are unaffected. (native field:
     * mGravityFactor)
     *
     * @return the factor
     */
    @Override
    public float getGravityFactor() {
        long settingsVa = va();
        float result = getGravityFactor(settingsVa);

        return result;
    }

    /**
     * Return the index of the object layer. The settings are unaffected.
     * (native field: mLayer)
     *
     * @return the layer index (&ge;0, &lt;numObjectLayers)
     */
    @Override
    public int getLayer() {
        long settingsVa = va();
        int result = getLayer(settingsVa);

        return result;
    }

    /**
     * Return the character's mass. The settings are unaffected. (native field:
     * mMass)
     *
     * @return the mass (in kilograms)
     */
    @Override
    public float getMass() {
        long settingsVa = va();
        float result = getMass(settingsVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createCharacterSettings();

    native private static float getFriction(long settingsVa);

    native private static float getGravityFactor(long settingsVa);

    native private static int getLayer(long settingsVa);

    native private static float getMass(long settingsVa);

    native private static void setFriction(long settingsVa, float friction);

    native private static void setGravityFactor(long settingsVa, float factor);

    native private static void setLayer(long settingsVa, int objLayer);

    native private static void setMass(long settingsVa, float mass);
}
