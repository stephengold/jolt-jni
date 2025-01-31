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

import com.github.stephengold.joltjni.readonly.ConstJoltPhysicsObject;

/**
 * Receive callbacks when a virtual character collides with something.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface CharacterContactListener extends ConstJoltPhysicsObject {
    // *************************************************************************
    // new methods exposed

    /**
     * Callback invoked (by native code) to adjust the velocity of the specified
     * body as seen by the specified character.
     *
     * @param characterVa the virtual address of the
     * {@code ConstCharacterVirtual} (not zero)
     * @param body2Va the virtual address of the {@code ConstBody} (not zero)
     * @param velocities the components of the linear and angular velocities
     * (length&ge;6, may be modified)
     */
    void onAdjustBodyVelocity(
            long characterVa, long body2Va, float[] velocities);

    /**
     * Callback invoked (by native code) whenever 2 characters collide.
     *
     * @param characterVa the virtual address of the
     * {@code ConstCharacterVirtual} being solved (not zero)
     * @param otherCharacterVa the virtual address of the other
     * {@code ConstCharacterVirtual} (not zero)
     * @param subShapeId2Va the virtual address of the {@code ConstSubShapeId}
     * of the shape that is in contact (not zero)
     * @param contactLocationX the X component of the contact location (in
     * system coordinates)
     * @param contactLocationY the Y component of the contact location (in
     * system coordinates)
     * @param contactLocationZ the Z component of the contact location (in
     * system coordinates)
     * @param contactNormalX the X component of the contact normal (in system
     * coordinates)
     * @param contactNormalY the Y component of the contact normal (in system
     * coordinates)
     * @param contactNormalZ the Z component of the contact normal (in system
     * coordinates)
     * @param settingsVa the virtual address of the
     * {@code CharacterContactSettings} for storing the desired behavior
     */
    void onCharacterContactAdded(long characterVa, long otherCharacterVa,
            long subShapeId2Va, double contactLocationX,
            double contactLocationY, double contactLocationZ,
            float contactNormalX, float contactNormalY, float contactNormalZ,
            long settingsVa);

    /**
     * Callback invoked (by native code) whenever a contact between 2 characters
     * becomes persistent.
     *
     * @param characterVa the virtual address of the
     * {@code ConstCharacterVirtual} being solved (not zero)
     * @param otherCharacterVa the virtual address of the other
     * {@code ConstCharacterVirtual} (not zero)
     * @param subShapeId2Va the virtual address of the {@code ConstSubShapeId}
     * of the shape that is in contact (not zero)
     * @param contactLocationX the X component of the contact location (in
     * system coordinates)
     * @param contactLocationY the Y component of the contact location (in
     * system coordinates)
     * @param contactLocationZ the Z component of the contact location (in
     * system coordinates)
     * @param contactNormalX the X component of the contact normal (in system
     * coordinates)
     * @param contactNormalY the Y component of the contact normal (in system
     * coordinates)
     * @param contactNormalZ the Z component of the contact normal (in system
     * coordinates)
     * @param settingsVa the virtual address of the
     * {@code CharacterContactSettings} for storing the desired behavior
     */
    void onCharacterContactPersisted(long characterVa, long otherCharacterVa,
            long subShapeId2Va, double contactLocationX,
            double contactLocationY, double contactLocationZ,
            float contactNormalX, float contactNormalY, float contactNormalZ,
            long settingsVa);

    /**
     * Callback invoked (by native code) whenever a contact between 2 characters
     * is destroyed.
     *
     * @param characterVa the virtual address of the
     * {@code ConstCharacterVirtual} being solved (not zero)
     * @param otherCharacterVa the virtual address of the other
     * {@code ConstCharacterVirtual} (not zero)
     * @param subShapeId2Va the virtual address of the {@code ConstSubShapeId}
     * of the shape that is in contact (not zero)
     */
    void onCharacterContactRemoved(
            long characterVa, long otherCharacterVa, long subShapeId2Va);

    /**
     * Callback invoked (by native code) whenever a character-versus-character
     * contact is being solved.
     *
     * @param characterVa the virtual address of the
     * {@code ConstCharacterVirtual} being solved (not zero)
     * @param otherCharacterVa the virtual address of the other
     * {@code ConstCharacterVirtual} (not zero)
     * @param subShapeId2Va the virtual address of the {@code ConstSubShapeId}
     * of the shape that is in contact (not zero)
     * @param contactLocationX the X component of the contact location (in
     * system coordinates)
     * @param contactLocationY the Y component of the contact location (in
     * system coordinates)
     * @param contactLocationZ the Z component of the contact location (in
     * system coordinates)
     * @param contactNormalX the X component of the contact normal (in system
     * coordinates)
     * @param contactNormalY the Y component of the contact normal (in system
     * coordinates)
     * @param contactNormalZ the Z component of the contact normal (in system
     * coordinates)
     * @param contactVelocityX the X component of the velocity of the contact
     * point (meters per second in system coordinates)
     * @param contactVelocityY the Y component of the velocity of the contact
     * point (meters per second in system coordinates)
     * @param contactVelocityZ the Z component of the velocity of the contact
     * point (meters per second in system coordinates)
     * @param materialVa the virtual address of the {@code ConstPhysicsMaterial}
     * at the contact point (not zero)
     * @param characterVelocityX the X component of the character's prior
     * velocity (in system coordinates)
     * @param characterVelocityY the Y component of the character's prior
     * velocity (in system coordinates)
     * @param characterVelocityZ the Z component of the character's prior
     * velocity (in system coordinates)
     * @param newCharacterVelocity storage for the new velocity vector (in
     * system coordinates, length&ge;3)
     */
    void onCharacterContactSolve(long characterVa, long otherCharacterVa,
            long subShapeId2Va, double contactLocationX,
            double contactLocationY, double contactLocationZ,
            float contactNormalX, float contactNormalY, float contactNormalZ,
            float contactVelocityX, float contactVelocityY,
            float contactVelocityZ, long materialVa, float characterVelocityX,
            float characterVelocityY, float characterVelocityZ,
            float[] newCharacterVelocity);

    /**
     * Callback invoked (by native code) to test whether the specified
     * characters can collide.
     *
     * @param characterVa the virtual address of the
     * {@code ConstCharacterVirtual} being solved (not zero)
     * @param otherCharacterVa the virtual address of the other
     * {@code ConstCharacterVirtual} (not zero)
     * @param subShapeId2Va the virtual address of the {@code ConstSubShapeId}
     * of the shape that is in contact (not zero)
     * @return {@code true} if the contact is valid, otherwise {@code false}
     */
    boolean onCharacterContactValidate(
            long characterVa, long otherCharacterVa, long subShapeId2Va);

    /**
     * Callback invoked (by native code) whenever a character collides with a
     * body.
     *
     * @param characterVa the virtual address of the
     * {@code ConstCharacterVirtual} being solved (not zero)
     * @param bodyId2Va the virtual address of the {@code ConstBody} being
     * solved (not zero)
     * @param subShapeId2Va the virtual address of the {@code ConstSubShapeId}
     * of the shape that is in contact (not zero)
     * @param contactLocationX the X component of the contact location (in
     * system coordinates)
     * @param contactLocationY the Y component of the contact location (in
     * system coordinates)
     * @param contactLocationZ the Z component of the contact location (in
     * system coordinates)
     * @param contactNormalX the X component of the contact normal (in system
     * coordinates)
     * @param contactNormalY the Y component of the contact normal (in system
     * coordinates)
     * @param contactNormalZ the Z component of the contact normal (in system
     * coordinates)
     * @param settingsVa the virtual address of the
     * {@code CharacterContactSettings} for storing the desired behavior
     */
    void onContactAdded(long characterVa, long bodyId2Va, long subShapeId2Va,
            double contactLocationX, double contactLocationY,
            double contactLocationZ, float contactNormalX, float contactNormalY,
            float contactNormalZ, long settingsVa);

    /**
     * Callback invoked (by native code) whenever a contact between a character
     * and a body becomes persistent.
     *
     * @param characterVa the virtual address of the
     * {@code ConstCharacterVirtual} being solved (not zero)
     * @param bodyId2Va the virtual address of the {@code ConstBody} being
     * solved (not zero)
     * @param subShapeId2Va the virtual address of the {@code ConstSubShapeId}
     * of the shape that is in contact (not zero)
     * @param contactLocationX the X component of the contact location (in
     * system coordinates)
     * @param contactLocationY the Y component of the contact location (in
     * system coordinates)
     * @param contactLocationZ the Z component of the contact location (in
     * system coordinates)
     * @param contactNormalX the X component of the contact normal (in system
     * coordinates)
     * @param contactNormalY the Y component of the contact normal (in system
     * coordinates)
     * @param contactNormalZ the Z component of the contact normal (in system
     * coordinates)
     * @param settingsVa the virtual address of the
     * {@code CharacterContactSettings} for storing the desired behavior
     */
    void onContactPersisted(long characterVa, long bodyId2Va,
            long subShapeId2Va, double contactLocationX,
            double contactLocationY, double contactLocationZ,
            float contactNormalX, float contactNormalY, float contactNormalZ,
            long settingsVa);

    /**
     * Callback invoked (by native code) whenever a contact between a character
     * and a body is destroyed.
     *
     * @param characterVa the virtual address of the
     * {@code ConstCharacterVirtual} being solved (not zero)
     * @param bodyId2Va the virtual address of the {@code ConstBody} being
     * solved (not zero)
     * @param subShapeId2Va the virtual address of the {@code ConstSubShapeId}
     * of the shape that is in contact (not zero)
     */
    void onContactRemoved(long characterVa, long bodyId2Va, long subShapeId2Va);

    /**
     * Callback invoked (by native code) whenever a character-versus-body
     * contact is being solved.
     *
     * @param characterVa the virtual address of the
     * {@code ConstCharacterVirtual} being solved (not zero)
     * @param bodyId2Va the virtual address of the {@code ConstBody} being
     * solved (not zero)
     * @param subShapeId2Va the virtual address of the {@code ConstSubShapeId}
     * of the shape that is in contact (not zero)
     * @param contactLocationX the X component of the contact location (in
     * system coordinates)
     * @param contactLocationY the Y component of the contact location (in
     * system coordinates)
     * @param contactLocationZ the Z component of the contact location (in
     * system coordinates)
     * @param contactNormalX the X component of the contact normal (in system
     * coordinates)
     * @param contactNormalY the Y component of the contact normal (in system
     * coordinates)
     * @param contactNormalZ the Z component of the contact normal (in system
     * coordinates)
     * @param contactVelocityX the X component of the velocity of the contact
     * point (meters per second in system coordinates)
     * @param contactVelocityY the Y component of the velocity of the contact
     * point (meters per second in system coordinates)
     * @param contactVelocityZ the Z component of the velocity of the contact
     * point (meters per second in system coordinates)
     * @param materialVa the virtual address of the {@code ConstPhysicsMaterial}
     * at the contact point (not zero)
     * @param characterVelocityX the X component of the character's prior
     * velocity (meters per second in system coordinates)
     * @param characterVelocityY the Y component of the character's prior
     * velocity (meters per second in system coordinates)
     * @param characterVelocityZ the Z component of the character's prior
     * velocity (meters per second in system coordinates)
     * @param newCharacterVelocity storage for the new velocity vector (in
     * system coordinates, length&ge;3)
     */
    void onContactSolve(long characterVa, long bodyId2Va, long subShapeId2Va,
            double contactLocationX, double contactLocationY,
            double contactLocationZ, float contactNormalX, float contactNormalY,
            float contactNormalZ, float contactVelocityX,
            float contactVelocityY, float contactVelocityZ,
            long materialVa, float characterVelocityX, float characterVelocityY,
            float characterVelocityZ, float[] newCharacterVelocity);

    /**
     * Callback invoked (by native code) to test whether the specified character
     * can collide with the specified body.
     *
     * @param characterVa the virtual address of the
     * {@code ConstCharacterVirtual} being solved (not zero)
     * @param bodyId2Va the virtual address of the {@code ConstBody} being
     * solved (not zero)
     * @param subShapeId2Va the virtual address of the {@code ConstSubShapeId}
     * of the shape that is in contact (not zero)
     * @return {@code true} if the contact is valid, otherwise {@code false}
     */
    boolean onContactValidate(
            long characterVa, long bodyId2Va, long subShapeId2Va);
}
