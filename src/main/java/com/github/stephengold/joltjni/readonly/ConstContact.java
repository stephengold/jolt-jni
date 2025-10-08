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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.EMotionType;

/**
 * Read-only access to a {@code Contact}. (native type: const Contact)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstContact extends ConstJoltPhysicsObject {
    /**
     * Return the ID of the colliding body. The contact is unaffected.
     *
     * @return the {@code BodyID} value
     */
    int getBodyB();

    /**
     * Test whether the velocity of the contact point can push the character.
     * The contact is unaffected.
     *
     * @return {@code true} if can push, otherwise {@code false}
     */
    boolean getCanPushCharacter();

    /**
     * Return the colliding character. The contact is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if no colliding character
     */
    ConstCharacterVirtual getCharacterB();

    /**
     * Copy the contact normal. The contact is unaffected.
     *
     * @return a new direction vector, pointing toward the character
     */
    Vec3 getContactNormal();

    /**
     * Return the contact separation. The contact is unaffected.
     *
     * @return the signed distance (in meters, &le;0&rarr;actual contact,
     * &gt;0&rarr;predictive contact)
     */
    float getDistance();

    /**
     * Return the fraction along the path where the contact takes place. The
     * contact is unaffected.
     *
     * @return the fraction
     */
    float getFraction();

    /**
     * Test whether the character has actually collided. The contact is
     * unaffected.
     *
     * @return {@code true} if a real collision, {@code false} for a predictive
     * contact that never became a real one
     */
    boolean getHadCollision();

    /**
     * Test whether the colliding object is a sensor. The contact is unaffected.
     *
     * @return {@code true} for a sensor, otherwise {@code false}
     */
    boolean getIsSensorB();

    /**
     * Copy the velocity of the contact point. The contact is unaffected.
     *
     * @return a new velocity vector
     */
    Vec3 getLinearVelocity();

    /**
     * Return the motion type of the colliding object. The contact is
     * unaffected.
     *
     * @return an enum value (not null)
     */
    EMotionType getMotionTypeB();

    /**
     * Copy the location where the contact occurs. The contact is unaffected.
     *
     * @return a new vector (in system coordinates)
     */
    RVec3 getPosition();

    /**
     * Return the sub-shape ID of the colliding body. The contact is unaffected.
     *
     * @return a {@code SubShapeID} value (typically negative)
     */
    int getSubShapeIdB();

    /**
     * Copy the surface normal of the contact. The contact is unaffected.
     *
     * @return a new direction vector
     */
    Vec3 getSurfaceNormal();

    /**
     * Return the user data of the colliding object. The contact is unaffected.
     *
     * @return the data value
     */
    long getUserData();

    /**
     * Test whether the contact was discarded by the contact-validate callback.
     * The contact is unaffected.
     *
     * @return {@code true} if discarded, otherwise {@code false}
     */
    boolean getWasDiscarded();
}
