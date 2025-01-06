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

import com.github.stephengold.joltjni.enumerate.EMotionType;
import com.github.stephengold.joltjni.readonly.ConstContact;

/**
 * Describe a contact between a {@code Character} and a body or another
 * character. (native type: {@code CharacterVirtual::Contact})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Contact extends JoltPhysicsObject implements ConstContact {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param contactVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    Contact(long contactVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(contactVa) : null;
        setVirtualAddress(contactVa, freeingAction);
    }
    // *************************************************************************
    // ConstContact methods

    /**
     * Return the ID of the colliding body. The contact is unaffected. (native
     * attribute: mBodyB)
     *
     * @return a new ID, or null if no colliding body
     */
    @Override
    public BodyId getBodyB() {
        long contactVa = va();
        long idVa = getBodyB(contactVa);
        BodyId result = null;
        if (idVa != 0L) {
            result = new BodyId(idVa, true);
        }

        return result;
    }

    /**
     * Test whether the velocity of the contact point can push the character.
     * The contact is unaffected. (native attribute: mCanPushCharacter)
     *
     * @return {@code true} if can push, otherwise {@code false}
     */
    @Override
    public boolean getCanPushCharacter() {
        long contactVa = va();
        boolean result = getCanPushCharacter(contactVa);

        return result;
    }

    /**
     * Return the colliding character. The contact is unaffected. (native
     * attribute: mCharacterB)
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * null if no colliding character
     */
    @Override
    public CharacterVirtual getCharacterB() {
        long contactVa = va();
        long characterVa = getCharacterB(contactVa);
        CharacterVirtual result;
        if (characterVa == 0L) {
            result = null;
        } else {
            result = new CharacterVirtual(characterVa, null);
        }

        return result;
    }

    /**
     * Return the contact normal. The contact is unaffected. (native attribute:
     * mContactNormal)
     *
     * @return a new direction vector, pointing toward the character
     */
    @Override
    public Vec3 getContactNormal() {
        long contactVa = va();
        float nx = getContactNormalX(contactVa);
        float ny = getContactNormalY(contactVa);
        float nz = getContactNormalZ(contactVa);
        Vec3 result = new Vec3(nx, ny, nz);

        return result;
    }

    /**
     * Return the contact separation. The contact is unaffected. (native
     * attribute: mDistance)
     *
     * @return the signed distance (in meters, &le;0&rarr;actual contact,
     * &gt;0&rarr;predictive contact)
     */
    @Override
    public float getDistance() {
        long contactVa = va();
        float result = getDistance(contactVa);

        return result;
    }

    /**
     * Return the fraction along the path where the contact takes place. The
     * contact is unaffected. (native attribute: mFraction)
     *
     * @return the fraction
     */
    @Override
    public float getFraction() {
        long contactVa = va();
        float result = getFraction(contactVa);

        return result;
    }

    /**
     * Test whether the character has actually collided. The contact is
     * unaffected. (native attribute: mHadCollision)
     *
     * @return {@code true} if a real collision, {@code false} for a predictive
     * contact that never became a real one
     */
    @Override
    public boolean getHadCollision() {
        long contactVa = va();
        boolean result = getHadCollision(contactVa);

        return result;
    }

    /**
     * Test whether the colliding object is a sensor. The contact is unaffected.
     * (native attribute: mIsSensorB)
     *
     * @return {@code true} for a sensor, otherwise {@code false}
     */
    @Override
    public boolean getIsSensorB() {
        long contactVa = va();
        boolean result = getIsSensorB(contactVa);

        return result;
    }

    /**
     * Return the velocity of the contact point. The contact is unaffected.
     * (native attribute: mLinearVelocity)
     *
     * @return a new velocity vector
     */
    @Override
    public Vec3 getLinearVelocity() {
        long contactVa = va();
        float vx = getLinearVelocityX(contactVa);
        float vy = getLinearVelocityY(contactVa);
        float vz = getLinearVelocityZ(contactVa);
        Vec3 result = new Vec3(vx, vy, vz);

        return result;
    }

    /**
     * Return the motion type of the colliding object. The contact is
     * unaffected. (native attribute: mGetMotionTypeB)
     *
     * @return an enum value (not null)
     */
    @Override
    public EMotionType getMotionTypeB() {
        long contactVa = va();
        int ordinal = getMotionTypeB(contactVa);
        EMotionType result = EMotionType.values()[ordinal];

        return result;
    }

    /**
     * Return the location where the contact occurs. The contact is unaffected.
     * (native attribute: mPosition)
     *
     * @return a new vector (in system coordinates)
     */
    @Override
    public RVec3 getPosition() {
        long contactVa = va();
        double locX = getPositionX(contactVa);
        double locY = getPositionY(contactVa);
        double locZ = getPositionZ(contactVa);
        RVec3 result = new RVec3(locX, locY, locZ);

        return result;
    }

    /**
     * Return the subshape ID of the colliding body. The contact is unaffected.
     * (native attribute: mSubShapeIDB)
     *
     * @return a new ID, or null if no colliding body
     */
    @Override
    public SubShapeId getSubShapeIdB() {
        long contactVa = va();
        long idVa = getSubShapeIdB(contactVa);
        SubShapeId result = null;
        if (idVa != 0L) {
            result = new SubShapeId(idVa, true);
        }

        return result;
    }

    /**
     * Return the surface normal of the contact. The contact is unaffected.
     * (native attribute: mSurfaceNormal)
     *
     * @return a new direction vector
     */
    @Override
    public Vec3 getSurfaceNormal() {
        long contactVa = va();
        float nx = getSurfaceNormalX(contactVa);
        float ny = getSurfaceNormalY(contactVa);
        float nz = getSurfaceNormalZ(contactVa);
        Vec3 result = new Vec3(nx, ny, nz);

        return result;
    }

    /**
     * Return the user data of the colliding object. The contact is unaffected.
     * (native attribute: mUserData)
     *
     * @return the data value
     */
    @Override
    public long getUserData() {
        long contactVa = va();
        long result = getUserData(contactVa);

        return result;
    }

    /**
     * Test whether the contact was discarded by the contact-validate callback.
     * The contact is unaffected. (native attribute: mWasDiscarded)
     *
     * @return {@code true} if discarded, otherwise {@code false}
     */
    @Override
    public boolean getWasDiscarded() {
        long contactVa = va();
        boolean result = getWasDiscarded(contactVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void free(long contactVa);

    native private static long getBodyB(long contactVa);

    native private static boolean getCanPushCharacter(long contactVa);

    native private static long getCharacterB(long contactVa);

    native private static float getContactNormalX(long contactVa);

    native private static float getContactNormalY(long contactVa);

    native private static float getContactNormalZ(long contactVa);

    native private static float getDistance(long contactVa);

    native private static float getFraction(long contactVa);

    native private static boolean getHadCollision(long contactVa);

    native private static boolean getIsSensorB(long contactVa);

    native private static float getLinearVelocityX(long contactVa);

    native private static float getLinearVelocityY(long contactVa);

    native private static float getLinearVelocityZ(long contactVa);

    native private static int getMotionTypeB(long contactVa);

    native private static double getPositionX(long contactVa);

    native private static double getPositionY(long contactVa);

    native private static double getPositionZ(long contactVa);

    native private static long getSubShapeIdB(long contactVa);

    native private static float getSurfaceNormalX(long contactVa);

    native private static float getSurfaceNormalY(long contactVa);

    native private static float getSurfaceNormalZ(long contactVa);

    native private static long getUserData(long contactVa);

    native private static boolean getWasDiscarded(long contactVa);
}
