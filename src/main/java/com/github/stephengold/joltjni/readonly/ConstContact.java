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
import com.github.stephengold.joltjni.CharacterVirtual;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.SubShapeId;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.EMotionType;

/**
 * Read-only access to a {@code Contact}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstContact extends ConstJoltPhysicsObject {
    // *************************************************************************
    // new methods exposed

    /**
     * Return the ID of the colliding body. The contact is unaffected. (native
     * field: mBodyB)
     *
     * @return a new ID, or null if no colliding body
     */
    BodyId getBodyB();

    /**
     * Test whether the velocity of the contact point can push the character.
     * The contact is unaffected. (native field: mCanPushCharacter)
     *
     * @return true if can push, otherwise false
     */
    boolean getCanPushCharacter();

    /**
     * Return the colliding character. The contact is unaffected. (native field:
     * mCharacterB)
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * null if no colliding character
     */
    CharacterVirtual getCharacterB();

    /**
     * Return the contact normal. The contact is unaffected. (native field:
     * mContactNormal)
     *
     * @return a new direction vector, pointing toward the character
     */
    Vec3 getContactNormal();

    /**
     * Return the contact separation. The contact is unaffected. (native field:
     * mDistance)
     *
     * @return the signed distance (in meters, &le;0&rarr;actual contact,
     * &gt;0&rarr;predictive contact)
     */
    float getDistance();

    /**
     * Return the fraction along the path where the contact takes place. The
     * contact is unaffected. (native field: mFraction)
     *
     * @return the fraction
     */
    float getFraction();

    /**
     * Test whether the character has actually collided. The contact is
     * unaffected. (native field: mHadCollision)
     *
     * @return true if a real collision, false for a predictive contact that
     * never became a real one
     */
    boolean getHadCollision();

    /**
     * Test whether the colliding object is a sensor. The contact is unaffected.
     * (native field: mIsSensorB)
     *
     * @return true for a sensor, otherwise false
     */
    boolean getIsSensorB();

    /**
     * Return the velocity of the contact point. The contact is unaffected.
     * (native field: mLinearVelocity)
     *
     * @return a new velocity vector
     */
    Vec3 getLinearVelocity();

    /**
     * Return the motion type of the colliding object. The contact is
     * unaffected. (native field: mGetMotionTypeB)
     *
     * @return an enum value (not null)
     */
    EMotionType getMotionTypeB();

    /**
     * Return the location where the contact occurs. The contact is unaffected.
     * (native field: mPosition)
     *
     * @return a new vector (in system coordinates)
     */
    RVec3 getPosition();

    /**
     * Return the subshape ID of the colliding body. The contact is unaffected.
     * (native field: mSubShapeIDB)
     *
     * @return a new ID, or null if no colliding body
     */
    SubShapeId getSubShapeIdB();

    /**
     * Return the surface normal of the contact. The contact is unaffected.
     * (native field: mSurfaceNormal)
     *
     * @return a new direction vector
     */
    Vec3 getSurfaceNormal();

    /**
     * Return the user data of the colliding object. The contact is unaffected.
     * (native field: mUserData)
     *
     * @return the data value
     */
    long getUserData();

    /**
     * Test whether the contact was discarded by the contact-validate callback.
     * The contact is unaffected. (native field: mWasDiscarded)
     *
     * @return true if discarded, otherwise false
     */
    boolean getWasDiscarded();
}
