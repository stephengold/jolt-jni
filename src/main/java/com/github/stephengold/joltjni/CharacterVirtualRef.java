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

import com.github.stephengold.joltjni.enumerate.EGroundState;
import com.github.stephengold.joltjni.readonly.ConstBodyId;
import com.github.stephengold.joltjni.readonly.ConstCharacterVirtual;
import com.github.stephengold.joltjni.readonly.ConstPhysicsMaterial;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.template.Ref;

/**
 * A counted reference to a {@code CharacterVirtual}. (native type:
 * {@code Ref<CharacterVirtual>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class CharacterVirtualRef
        extends Ref
        implements ConstCharacterVirtual {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a null reference.
     */
    public CharacterVirtualRef() {
        long refVa = createNullReference();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    CharacterVirtualRef(long refVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(refVa) : null;
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // ConstCharacterVirtual methods

    /**
     * Convert the specified velocity to one that won't climb steep slopes. The
     * character is unaffected.
     *
     * @param desiredVelocity velocity vector (in system coordinates, not null,
     * unaffected)
     * @return a new velocity vector (in system coordinates)
     */
    @Override
    public Vec3 cancelVelocityTowardsSteepSlopes(Vec3Arg desiredVelocity) {
        long refVa = va();
        long characterVa = getPtr(refVa);
        float vx = desiredVelocity.getX();
        float vy = desiredVelocity.getY();
        float vz = desiredVelocity.getZ();
        float[] storeVelocity = new float[3];
        CharacterVirtual.cancelVelocityTowardsSteepSlopes(
                characterVa, vx, vy, vz, storeVelocity);
        Vec3 result = new Vec3(storeVelocity);

        return result;
    }

    /**
     * Test whether the character has moved onto a steep slope. The character is
     * unaffected.
     *
     * @param desiredVelocity velocity vector (in system coordinates, not null,
     * unaffected)
     * @return {@code true} if too step to walk, otherwise {@code false}
     */
    @Override
    public boolean canWalkStairs(Vec3Arg desiredVelocity) {
        long refVa = va();
        long characterVa = getPtr(refVa);
        float vx = desiredVelocity.getX();
        float vy = desiredVelocity.getY();
        float vz = desiredVelocity.getZ();
        boolean result
                = CharacterVirtual.canWalkStairs(characterVa, vx, vy, vz);

        return result;
    }

    /**
     * Access the list of contacts. The character is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public ContactList getActiveContacts() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        long listVa = CharacterVirtual.getActiveContacts(characterVa);
        ContactList result = new ContactList(listVa, false);

        return result;
    }

    /**
     * Calculate the location of the character's center of mass. The character
     * is unaffected.
     *
     * @return a new location vector (in system coordinates)
     */
    @Override
    public RVec3 getCenterOfMassPosition() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        double locX = CharacterVirtual.getCenterOfMassPositionX(characterVa);
        double locY = CharacterVirtual.getCenterOfMassPositionY(characterVa);
        double locZ = CharacterVirtual.getCenterOfMassPositionZ(characterVa);
        RVec3 result = new RVec3(locX, locY, locZ);

        return result;
    }

    /**
     * Calculate the local-to-system transform of the character's center of
     * mass. The character is unaffected.
     *
     * @return a new coordinate transform matrix
     */
    @Override
    public RMat44 getCenterOfMassTransform() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        long matrixVa = CharacterVirtual.getCenterOfMassTransform(characterVa);
        RMat44 result = new RMat44(matrixVa, true);

        return result;
    }

    /**
     * Return the thickness of the character's padding. The character is
     * unaffected.
     *
     * @return the thickness (in meters)
     */
    @Override
    public float getCharacterPadding() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        float result = CharacterVirtual.getCharacterPadding(characterVa);

        return result;
    }

    /**
     * Return the maximum slope on which the character can walk. The character
     * is unaffected.
     *
     * @return the cosine of the slope angle
     */
    @Override
    public float getCosMaxSlopeAngle() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        float result = CharacterBase.getCosMaxSlopeAngle(characterVa);

        return result;
    }

    /**
     * Test whether enhanced internal edge removal is enabled. The character is
     * unaffected.
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    @Override
    public boolean getEnhancedInternalEdgeRemoval() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        boolean result
                = CharacterVirtual.getEnhancedInternalEdgeRemoval(characterVa);

        return result;
    }

    /**
     * Return the body ID of the supporting surface. The character is
     * unaffected.
     *
     * @return a new ID
     */
    @Override
    public BodyId getGroundBodyId() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        long idVa = CharacterBase.getGroundBodyId(characterVa);
        BodyId result = new BodyId(idVa, true);

        return result;
    }

    /**
     * Access the material of the supporting surface. The character is
     * unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * else {@code null}
     */
    @Override
    public ConstPhysicsMaterial getGroundMaterial() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        long materialVa = CharacterBase.getGroundMaterial(characterVa);
        ConstPhysicsMaterial result;
        if (materialVa == 0L) {
            result = null;
        } else {
            result = new PhysicsMaterial(materialVa);
        }

        return result;
    }

    /**
     * Return the normal direction at the point of contact with the supporting
     * surface. The character is unaffected.
     *
     * @return a new direction vector (in system coordinates)
     */
    @Override
    public Vec3 getGroundNormal() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        float nx = CharacterBase.getGroundNormalX(characterVa);
        float ny = CharacterBase.getGroundNormalY(characterVa);
        float nz = CharacterBase.getGroundNormalZ(characterVa);
        Vec3 result = new Vec3(nx, ny, nz);

        return result;
    }

    /**
     * Return the location of the point of contact with the supporting surface.
     * The character is unaffected.
     *
     * @return a new location vector (in system coordinates)
     */
    @Override
    public RVec3 getGroundPosition() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        double xx = CharacterBase.getGroundPositionX(characterVa);
        double yy = CharacterBase.getGroundPositionY(characterVa);
        double zz = CharacterBase.getGroundPositionZ(characterVa);
        RVec3 result = new RVec3(xx, yy, zz);

        return result;
    }

    /**
     * Return the relationship between the character and its supporting surface.
     * The character is unaffected.
     *
     * @return an enum value (not null)
     */
    @Override
    public EGroundState getGroundState() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        int ordinal = CharacterBase.getGroundState(characterVa);
        EGroundState result = EGroundState.values()[ordinal];

        return result;
    }

    /**
     * Identify the face on the supporting surface where contact is occurring.
     * The character is unaffected.
     *
     * @return a new ID
     */
    @Override
    public SubShapeId getGroundSubShapeId() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        long idVa = CharacterBase.getGroundSubShapeId(characterVa);
        SubShapeId result = new SubShapeId(idVa, true);

        return result;
    }

    /**
     * Return the user data of the supporting surface. The character is
     * unaffected.
     *
     * @return the 64-bit value
     */
    @Override
    public long getGroundUserData() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        long result = CharacterBase.getGroundUserData(characterVa);

        return result;
    }

    /**
     * Return the world-space velocity of the supporting surface. The character
     * is unaffected.
     *
     * @return a new velocity vector (meters per second in system coordinates)
     */
    @Override
    public Vec3 getGroundVelocity() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        float vx = CharacterBase.getGroundVelocityX(characterVa);
        float vy = CharacterBase.getGroundVelocityY(characterVa);
        float vz = CharacterBase.getGroundVelocityZ(characterVa);
        Vec3 result = new Vec3(vx, vy, vz);

        return result;
    }

    /**
     * Return the maximum angle for merging during hit reduction. The character
     * is unaffected.
     *
     * @return the cosine of the maximum angle, or -1 if hit reduction is
     * disabled
     */
    @Override
    public float getHitReductionCosMaxAngle() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        float result = CharacterVirtual.getHitReductionCosMaxAngle(characterVa);

        return result;
    }

    /**
     * Return the ID of the inner body. The character is unaffected.
     *
     * @return the ID, or {@code null} if none
     */
    @Override
    public BodyId getInnerBodyId() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        long idVa = CharacterVirtual.getInnerBodyId(characterVa);
        BodyId result = new BodyId(idVa, true);

        return result;
    }

    /**
     * Return the linear velocity of the character. The character is unaffected.
     *
     * @return a new velocity vector (meters per second in system coordinates)
     */
    @Override
    public Vec3 getLinearVelocity() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        float vx = CharacterVirtual.getLinearVelocityX(characterVa);
        float vy = CharacterVirtual.getLinearVelocityY(characterVa);
        float vz = CharacterVirtual.getLinearVelocityZ(characterVa);
        Vec3 result = new Vec3(vx, vy, vz);

        return result;
    }

    /**
     * Return the character's mass. The character is unaffected.
     *
     * @return the mass (in kilograms)
     */
    @Override
    public float getMass() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        float result = CharacterVirtual.getMass(characterVa);

        return result;
    }

    /**
     * Test whether the last collision check discarded one or more hits. The
     * character is unaffected.
     *
     * @return {@code true} if discarded hits, otherwise {@code false}
     */
    @Override
    public boolean getMaxHitsExceeded() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        boolean result = CharacterVirtual.getMaxHitsExceeded(characterVa);

        return result;
    }

    /**
     * Return the maximum number of hits to be collected. The character is
     * unaffected.
     *
     * @return the limit (&ge;0)
     */
    @Override
    public int getMaxNumHits() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        int result = CharacterVirtual.getMaxNumHits(characterVa);

        return result;
    }

    /**
     * Return the maximum force applied to other bodies. The character is
     * unaffected.
     *
     * @return the force (in Newtons)
     */
    @Override
    public float getMaxStrength() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        float result = CharacterVirtual.getMaxStrength(characterVa);

        return result;
    }

    /**
     * Return how quickly penetration is resolved. The character is unaffected.
     *
     * @return the resolution fraction (0=never resolved, 1=all in one update)
     */
    @Override
    public float getPenetrationRecoverySpeed() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        float result = CharacterVirtual.getPenetrationRecoverySpeed(
                characterVa);

        return result;
    }

    /**
     * Copy the location of the character. The character is unaffected.
     *
     * @return a new location vector (in system coordinates)
     */
    @Override
    public RVec3 getPosition() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        double x = CharacterVirtual.getPositionX(characterVa);
        double y = CharacterVirtual.getPositionY(characterVa);
        double z = CharacterVirtual.getPositionZ(characterVa);
        RVec3 result = new RVec3(x, y, z);

        return result;
    }

    /**
     * Copy the orientation of the character. The character is unaffected.
     *
     * @return a new rotation quaternion (in system coordinates)
     */
    @Override
    public Quat getRotation() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        float w = CharacterVirtual.getRotationW(characterVa);
        float x = CharacterVirtual.getRotationX(characterVa);
        float y = CharacterVirtual.getRotationY(characterVa);
        float z = CharacterVirtual.getRotationZ(characterVa);
        Quat result = new Quat(x, y, z, w);

        return result;
    }

    /**
     * Access the character's shape. The character is unaffected.
     *
     * @return a new immutable JVM object with the pre-existing native object
     * assigned, or {@code null} if none
     */
    @Override
    public ConstShape getShape() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        long shapeVa = CharacterBase.getShape(characterVa);
        ConstShape result = Shape.newShape(shapeVa);

        return result;
    }

    /**
     * Copy the local offset applied to the shape. The character is unaffected.
     *
     * @return a new offset vector (in local coordinates)
     */
    @Override
    public Vec3 getShapeOffset() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        float x = CharacterVirtual.getShapeOffsetX(characterVa);
        float y = CharacterVirtual.getShapeOffsetY(characterVa);
        float z = CharacterVirtual.getShapeOffsetZ(characterVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the character's "up" direction. The character is unaffected.
     *
     * @return a new direction vector
     */
    @Override
    public Vec3 getUp() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        float x = CharacterBase.getUpX(characterVa);
        float y = CharacterBase.getUpY(characterVa);
        float z = CharacterBase.getUpZ(characterVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the character's user data: can be used for anything. The character
     * is unaffected.
     *
     * @return the value
     */
    @Override
    public long getUserData() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        long result = CharacterVirtual.getUserData(characterVa);

        return result;
    }

    /**
     * Calculate the character's local-to-system coordinate transform. The
     * character is unaffected.
     *
     * @return a new transform matrix
     */
    @Override
    public RMat44 getWorldTransform() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        long matrixVa = CharacterVirtual.getWorldTransform(characterVa);
        RMat44 result = new RMat44(matrixVa, true);

        return result;
    }

    /**
     * Test whether the character is in contact with or collided with the
     * specified body during the previous time step. The character is
     * unaffected.
     *
     * @param bodyId the ID of the body to test against (not null, unaffected)
     * @return {@code true} if contact or collision, otherwise {@code false}
     */
    @Override
    public boolean hasCollidedWith(ConstBodyId bodyId) {
        long refVa = va();
        long characterVa = getPtr(refVa);
        long idVa = bodyId.va();
        boolean result = CharacterVirtual.hasCollidedWithBody(
                characterVa, idVa);

        return result;
    }

    /**
     * Test whether the character is in contact with or has collided with the
     * specified character during the previous time step. The current character
     * is unaffected.
     *
     * @param otherCharacter the character to test against (not null,
     * unaffected)
     * @return {@code true} if contact or collision, otherwise {@code false}
     */
    @Override
    public boolean hasCollidedWith(ConstCharacterVirtual otherCharacter) {
        long refVa = va();
        long characterVa = getPtr(refVa);
        long otherVa = otherCharacter.va();
        boolean result = CharacterVirtual.hasCollidedWithCharacter(
                characterVa, otherVa);

        return result;
    }

    /**
     * Test whether the specified normal direction is too steep. The character
     * is unaffected.
     *
     * @param normal the surface normal to test (not null, unaffected)
     * @return {@code true} if too steep, otherwise {@code false}
     */
    @Override
    public boolean isSlopeTooSteep(Vec3Arg normal) {
        long refVa = va();
        long characterVa = getPtr(refVa);
        float nx = normal.getX();
        float ny = normal.getY();
        float nz = normal.getZ();
        boolean result = CharacterBase.isSlopeTooSteep(characterVa, nx, ny, nz);

        return result;
    }

    /**
     * Test whether the character is supported. The character is unaffected.
     *
     * @return {@code true} if supported, otherwise {@code false}
     */
    @Override
    public boolean isSupported() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        boolean result = CharacterBase.isSupported(characterVa);

        return result;
    }

    /**
     * Save the character's state to the specified recorder.
     *
     * @param recorder the recorder to save to (not null)
     */
    @Override
    public void saveState(StateRecorder recorder) {
        long refVa = va();
        long characterVa = getPtr(refVa);
        long recorderVa = recorder.va();
        CharacterBase.saveState(characterVa, recorderVa);
    }
    // *************************************************************************
    // Ref methods

    /**
     * Temporarily access the referenced {@code CharacterVirtual}.
     *
     * @return a new JVM object that refers to the pre-existing native object
     */
    @Override
    public CharacterVirtual getPtr() {
        long refVa = va();
        long settingsVa = getPtr(refVa);
        CharacterVirtual result = new CharacterVirtual(settingsVa);

        return result;
    }

    /**
     * Create a counted reference to the native {@code CharacterVirtual}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public CharacterVirtualRef toRef() {
        long refVa = va();
        long copyVa = copy(refVa);
        CharacterVirtualRef result = new CharacterVirtualRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long copy(long refVa);

    native private static long createNullReference();

    native private static void free(long refVa);

    native private static long getPtr(long refVa);
}
