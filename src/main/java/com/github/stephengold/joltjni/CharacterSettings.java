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

import com.github.stephengold.joltjni.readonly.ConstCharacterSettings;
import com.github.stephengold.joltjni.template.RefTarget;

/**
 * Settings used to create a {@code Character}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CharacterSettings
        extends CharacterBaseSettings
        implements ConstCharacterSettings, RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public CharacterSettings() {
        long settingsVa = createCharacterSettings();
        setVirtualAddress(settingsVa); // not owner due to ref counting
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public CharacterSettings(ConstCharacterSettings original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa); // not owner due to ref counting
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
     * Alter the friction ratio. (native attribute: mFriction)
     *
     * @param friction the desired ratio (typically &ge;0 and &le;1,
     * default=0.2)
     */
    public void setFriction(float friction) {
        long settingsVa = va();
        setFriction(settingsVa, friction);
    }

    /**
     * Alter the gravity multiplier. (native attribute: mGravityFactor)
     *
     * @param factor the desired multiplier (default=1)
     */
    public void setGravityFactor(float factor) {
        long settingsVa = va();
        setGravityFactor(settingsVa, factor);
    }

    /**
     * Alter the object layer to which the character will be added. (native
     * attribute: mLayer)
     *
     * @param objLayer the ID of the desired object layer (&ge;0,
     * &lt;numObjectLayers, default=0)
     */
    public void setLayer(int objLayer) {
        long settingsVa = va();
        setLayer(settingsVa, objLayer);
    }

    /**
     * Alter the character's mass. (native attribute: mMass)
     *
     * @param mass the desired mass (in kilograms, default=80)
     */
    public void setMass(float mass) {
        long settingsVa = va();
        setMass(settingsVa, mass);
    }
    // *************************************************************************
    // ConstCharacterSettings methods

    /**
     * Return the friction ratio. The settings are unaffected. (native
     * attribute: mFriction)
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
     * Return the gravity factor. The settings are unaffected. (native
     * attribute: mGravityFactor)
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
     * (native attribute: mLayer)
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
     * Return the character's mass. The settings are unaffected. (native
     * attribute: mMass)
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
    // RefTarget methods

    /**
     * Mark the native {@code CharacterSettings} as embedded.
     */
    @Override
    public void setEmbedded() {
        long settingsVa = va();
        setEmbedded(settingsVa);
    }

    /**
     * Create a counted reference to the native {@code CharacterSettings}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public CharacterSettingsRef toRef() {
        long settingsVa = va();
        long refVa = toRef(settingsVa);
        CharacterSettingsRef result = new CharacterSettingsRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long createCharacterSettings();

    native private static long createCopy(long originalVa);

    native static float getFriction(long settingsVa);

    native static float getGravityFactor(long settingsVa);

    native static int getLayer(long settingsVa);

    native static float getMass(long settingsVa);

    native private static void setEmbedded(long settingsVa);

    native static void setFriction(long settingsVa, float friction);

    native static void setGravityFactor(long settingsVa, float factor);

    native static void setLayer(long settingsVa, int objLayer);

    native static void setMass(long settingsVa, float mass);

    native private static long toRef(long settingsVa);
}
