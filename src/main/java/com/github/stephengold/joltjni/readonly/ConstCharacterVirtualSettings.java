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

import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.EBackFaceMode;

/**
 * Read-only access to a {@code CharacterVirtualSettings} object. (native type:
 * const CharacterVirtualSettings)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstCharacterVirtualSettings
        extends ConstCharacterBaseSettings {
    // *************************************************************************
    // new methods exposed

    /**
     * Determine whether the character will move through back-facing triangles.
     * The settings are unaffected.
     *
     * @return an enum value (not null)
     */
    EBackFaceMode getBackFaceMode();

    /**
     * Return how far the character tries to stay away from the geometry. The
     * settings are unaffected.
     *
     * @return the desired distance (in meters)
     */
    float getCharacterPadding();

    /**
     * Return the collision tolerance. The settings are unaffected.
     *
     * @return the allowed penetration distance (in meters)
     */
    float getCollisionTolerance();

    /**
     * Return the maximum angle for merging during hit reduction. The settings
     * are unaffected.
     *
     * @return the cosine of the maximum angle, or -1 if hit reduction is
     * disabled
     */
    float getHitReductionCosMaxAngle();

    /**
     * Return the object layer that the inner rigid body will be added to. The
     * settings are unaffected.
     *
     * @return the index of the object layer
     */
    int getInnerBodyLayer();

    /**
     * Return the shape of the inner rigid body. The settings are unaffected.
     *
     * @return the shape, or null for no inner body
     */
    ConstShape getInnerBodyShape();

    /**
     * Return the character's mass. The settings are unaffected.
     *
     * @return the mass (in kilograms)
     */
    float getMass();

    /**
     * Return the maximum number of collision iterations. The settings are
     * unaffected.
     *
     * @return the number of iterations (&ge;0)
     */
    int getMaxCollisionIterations();

    /**
     * Return how often to try stepping in the constraint solver. The settings
     * are unaffected.
     *
     * @return the number of iterations (&ge;0)
     */
    int getMaxConstraintIterations();

    /**
     * Return the maximum number of hits to be collected. The settings are
     * unaffected.
     *
     * @return the limit (&ge;0)
     */
    int getMaxNumHits();

    /**
     * Return the maximum force applied to other bodies. The settings are
     * unaffected.
     *
     * @return the force (in Newtons)
     */
    float getMaxStrength();

    /**
     * Return the early out threshold. The settings are unaffected.
     *
     * @return the simulation time interval (in seconds)
     */
    float getMinTimeRemaining();

    /**
     * Return how quickly penetration is resolved. The settings are unaffected.
     *
     * @return the resolution fraction (0=never resolved, 1=all in one update)
     */
    float getPenetrationRecoverySpeed();

    /**
     * Return the maximum range of predictive contacts. The settings are
     * unaffected.
     *
     * @return the distance (in meters)
     */
    float getPredictiveContactDistance();

    /**
     * Copy the local offset applied to the shape. The settings are unaffected.
     *
     * @return a new offset vector (in local coordinates)
     */
    Vec3 getShapeOffset();
}
