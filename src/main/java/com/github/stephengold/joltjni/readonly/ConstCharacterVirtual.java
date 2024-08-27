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

import com.github.stephengold.joltjni.BodyId;
import com.github.stephengold.joltjni.ContactList;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RMat44;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to a {@code CharacterVirtual}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstCharacterVirtual extends ConstCharacterBase {
    // *************************************************************************
    // new methods exposed

    /**
     * Convert the specified velocity to one that won't climb steep slopes. The
     * character is unaffected.
     *
     * @param desiredVelocity velocity vector (in system coordinates, not null,
     * unaffected)
     * @return a new velocity vector (in system coordinates)
     */
    Vec3 cancelVelocityTowardsSteepSlopes(Vec3Arg desiredVelocity);

    /**
     * Test whether the character has moved onto a steep slope. The character is
     * unaffected.
     *
     * @param desiredVelocity velocity vector (in system coordinates, not null,
     * unaffected)
     * @return true if too step to walk, otherwise false
     */
    boolean canWalkStairs(Vec3Arg desiredVelocity);

    /**
     * Access the list of contacts. The character is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ContactList getActiveContacts();

    /**
     * Calculate the location of the character's center of mass. The character
     * is unaffected.
     *
     * @return a new location vector (in system coordinates)
     */
    RVec3 getCenterOfMassPosition();

    /**
     * Calculate the local-to-system transform of the character's center of
     * mass. The character is unaffected.
     *
     * @return a new coordinate transform matrix
     */
    RMat44 getCenterOfMassTransform();

    /**
     * Return the thickness of the character's padding. The character is
     * unaffected.
     *
     * @return the thickness (in meters)
     */
    float getCharacterPadding();

    /**
     * Test whether enhanced internal edge removal is enabled. The character is
     * unaffected.
     *
     * @return true if enabled, otherwise false
     */
    boolean getEnhancedInternalEdgeRemoval();

    /**
     * Return the maximum angle for merging during hit reduction. The character
     * is unaffected.
     *
     * @return the cosine of the maximum angle, or -1 if hit reduction is
     * disabled
     */
    float getHitReductionCosMaxAngle();

    /**
     * Return the ID of the inner body. The character is unaffected.
     *
     * @return the ID, or {@code null} if none
     */
    BodyId getInnerBodyId();

    /**
     * Return the linear velocity of the character. The character is unaffected.
     *
     * @return a new velocity vector (meters per second in system coordinates)
     */
    Vec3 getLinearVelocity();

    /**
     * Return the character's mass. The character is unaffected.
     *
     * @return the mass (in kilograms)
     */
    float getMass();

    /**
     * Test whether the last collision check discarded one or more hits. The
     * character is unaffected.
     *
     * @return true if discarded hits, otherwise false
     */
    boolean getMaxHitsExceeded();

    /**
     * Return the maximum number of hits to be collected. The character is
     * unaffected.
     *
     * @return the limit (&ge;0)
     */
    int getMaxNumHits();

    /**
     * Return the maximum force applied to other bodies. The character is
     * unaffected.
     *
     * @return the force (in Newtons)
     */
    float getMaxStrength();

    /**
     * Return how quickly penetration is resolved. The character is unaffected.
     *
     * @return the resolution fraction (0=never resolved, 1=all in one update)
     */
    float getPenetrationRecoverySpeed();

    /**
     * Copy the location of the character. The character is unaffected.
     *
     * @return a new location vector (in system coordinates)
     */
    RVec3 getPosition();

    /**
     * Copy the orientation of the character. The character is unaffected.
     *
     * @return a new rotation quaternion (in system coordinates)
     */
    Quat getRotation();

    /**
     * Copy the local offset applied to the shape. The character is unaffected.
     *
     * @return a new offset vector (in local coordinates)
     */
    Vec3 getShapeOffset();

    /**
     * Return the character's user data: can be used for anything. The character
     * is unaffected.
     *
     * @return the value
     */
    long getUserData();

    /**
     * Calculate the character's local-to-system coordinate transform. The
     * character is unaffected.
     *
     * @return a new transform matrix
     */
    RMat44 getWorldTransform();

    /**
     * Test whether the character is in contact with or collided with the
     * specified body during the previous time step. The character is
     * unaffected.
     *
     * @param bodyId the ID of the body to test against (not null, unaffected)
     * @return true if contact or collision, otherwise false
     */
    boolean hasCollidedWith(ConstBodyId bodyId);

    /**
     * Test whether the character is in contact with or has collided with the
     * specified character during the previous time step. The current character
     * is unaffected.
     *
     * @param other the character to test against (not null, unaffected)
     * @return true if contact or collision, otherwise false
     */
    boolean hasCollidedWith(ConstCharacterVirtual other);
}
