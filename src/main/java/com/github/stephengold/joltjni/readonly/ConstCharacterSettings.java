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
package com.github.stephengold.joltjni.readonly;

/**
 * Read-only access to a {@code CharacterSettings} object.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstCharacterSettings extends ConstCharacterBaseSettings {
    // *************************************************************************
    // new methods exposed

    /**
     * Return the friction ratio. The settings are unaffected. (native field:
     * mFriction)
     *
     * @return the ratio (typically &ge;0 and &le;1)
     */
    float getFriction();

    /**
     * Return the gravity factor. The settings are unaffected. (native field:
     * mGravityFactor)
     *
     * @return the factor
     */
    float getGravityFactor();

    /**
     * Return the index of the object layer. The settings are unaffected.
     * (native field: mLayer)
     *
     * @return the layer index (&ge;0, &lt;numObjectLayers)
     */
    int getLayer();

    /**
     * Return the character's mass. The settings are unaffected. (native field:
     * mMass)
     *
     * @return the mass (in kilograms)
     */
    float getMass();
}
