/*
Copyright (c) 2025 Stephen Gold

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
import com.github.stephengold.joltjni.readonly.ConstCharacterVirtual;
import com.github.stephengold.joltjni.readonly.ConstContact;
import com.github.stephengold.joltjni.readonly.ConstPhysicsMaterial;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

/**
 * A counted reference to a {@code ConstCharacter}. (native type:
 * {@code RefConst<Character>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class CharacterVirtualRefC
        extends JoltPhysicsObject
        implements ConstCharacterVirtual {
    // *************************************************************************
    // fields

    /**
     * where to add the body (may be {@code null})
     */
    final private PhysicsSystem system;
    // *************************************************************************
    // constructors

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param physicsSystem where to add the body
     */
    CharacterVirtualRefC(long refVa, PhysicsSystem physicsSystem) {
        this.system = physicsSystem;
        /*
         * Passing physicsSystem to the Runnable ensures that the underlying
         * system won't get cleaned before the character.
         */
        Runnable freeingAction = () -> freeWithSystem(refVa, physicsSystem);
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Temporarily access the referenced {@code ConstCharacter}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public ConstCharacterVirtual getPtr() {
        long refVa = va();
        long characterVa = getPtr(refVa);
        ConstCharacterVirtual result
                = new CharacterVirtual(characterVa, system);

        return result;
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
        long characterVa = targetVa();
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
        long characterVa = targetVa();
        float vx = desiredVelocity.getX();
        float vy = desiredVelocity.getY();
        float vz = desiredVelocity.getZ();
        boolean result
                = CharacterVirtual.canWalkStairs(characterVa, vx, vy, vz);

        return result;
    }

    /**
     * Copy the list of active contacts. The character is unaffected.
     *
     * @return a new array of new objects
     */
    @Override
    public ConstContact[] getActiveContacts() {
        long characterVa = targetVa();
        int numContacts = CharacterVirtual.countActiveContacts(characterVa);
        ConstContact[] result = new ConstContact[numContacts];
        for (int i = 0; i < numContacts; ++i) {
            long contactVa = CharacterVirtual.getActiveContact(characterVa, i);
            result[i] = new Contact(contactVa, true);
        }

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
        long characterVa = targetVa();
        DoubleBuffer storeDoubles = Temporaries.doubleBuffer1.get();
        CharacterVirtual.getCenterOfMassPosition(characterVa, storeDoubles);
        RVec3 result = new RVec3(storeDoubles);

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
        long characterVa = targetVa();
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
        long characterVa = targetVa();
        float result = CharacterVirtual.getCharacterPadding(characterVa);

        return result;
    }

    /**
     * Generate settings to reconstruct the character. The character is
     * unaffected.
     *
     * @return a new object
     */
    @Override
    public CharacterVirtualSettings getCharacterVirtualSettings() {
        long characterVa = targetVa();
        long settingsVa
                = CharacterVirtual.getCharacterVirtualSettings(characterVa);
        CharacterVirtualSettings result
                = new CharacterVirtualSettings(settingsVa);

        return result;
    }

    /**
     * Return the maximum slope the character can walk on. The character is
     * unaffected.
     *
     * @return the cosine of the slope angle
     */
    @Override
    public float getCosMaxSlopeAngle() {
        long characterVa = targetVa();
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
        long characterVa = targetVa();
        boolean result
                = CharacterVirtual.getEnhancedInternalEdgeRemoval(characterVa);

        return result;
    }

    /**
     * Return the body ID of the supporting surface. The character is
     * unaffected.
     *
     * @return the {@code BodyID} value
     */
    @Override
    public int getGroundBodyId() {
        long characterVa = targetVa();
        int result = CharacterBase.getGroundBodyId(characterVa);

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
        long characterVa = targetVa();
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
     * Copy the normal direction at the point of contact with the supporting
     * surface. The character is unaffected.
     *
     * @return a new direction vector (in system coordinates)
     */
    @Override
    public Vec3 getGroundNormal() {
        long characterVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        CharacterBase.getGroundNormal(characterVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the location of the point of contact with the supporting surface.
     * The character is unaffected.
     *
     * @return a new location vector (in system coordinates)
     */
    @Override
    public RVec3 getGroundPosition() {
        long characterVa = targetVa();
        DoubleBuffer storeDoubles = Temporaries.doubleBuffer1.get();
        CharacterBase.getGroundPosition(characterVa, storeDoubles);
        RVec3 result = new RVec3(storeDoubles);

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
        long characterVa = targetVa();
        int ordinal = CharacterBase.getGroundState(characterVa);
        EGroundState result = EGroundState.values()[ordinal];

        return result;
    }

    /**
     * Identify the face on the supporting surface where contact is occurring.
     * The character is unaffected.
     *
     * @return a {@code SubShapeID} value
     */
    @Override
    public int getGroundSubShapeId() {
        long characterVa = targetVa();
        int result = CharacterBase.getGroundSubShapeId(characterVa);

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
        long characterVa = targetVa();
        long result = CharacterBase.getGroundUserData(characterVa);

        return result;
    }

    /**
     * Copy the world-space velocity of the supporting surface. The character is
     * unaffected.
     *
     * @return a new velocity vector (meters per second in system coordinates)
     */
    @Override
    public Vec3 getGroundVelocity() {
        long characterVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        CharacterBase.getGroundVelocity(characterVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

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
        long characterVa = targetVa();
        float result = CharacterVirtual.getHitReductionCosMaxAngle(characterVa);

        return result;
    }

    /**
     * Return the character's ID. The character is unaffected. (native method:
     * GetID)
     *
     * @return a {@code CharacterId} value
     */
    @Override
    public int getId() {
        long characterVa = targetVa();
        int result = CharacterVirtual.getId(characterVa);

        return result;
    }

    /**
     * Return the ID of the inner body. The character is unaffected. (native
     * method: GetInnerBodyID)
     *
     * @return the {@code BodyID} value
     */
    @Override
    public int getInnerBodyId() {
        long characterVa = targetVa();
        int result = CharacterVirtual.getInnerBodyId(characterVa);

        return result;
    }

    /**
     * Copy the linear velocity of the character. The character is unaffected.
     *
     * @return a new velocity vector (meters per second in system coordinates)
     */
    @Override
    public Vec3 getLinearVelocity() {
        long characterVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        CharacterVirtual.getLinearVelocity(characterVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Return the character's mass. The character is unaffected.
     *
     * @return the mass (in kilograms)
     */
    @Override
    public float getMass() {
        long characterVa = targetVa();
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
        long characterVa = targetVa();
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
        long characterVa = targetVa();
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
        long characterVa = targetVa();
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
        long characterVa = targetVa();
        float result
                = CharacterVirtual.getPenetrationRecoverySpeed(characterVa);

        return result;
    }

    /**
     * Copy the location of the character. The character is unaffected.
     *
     * @return a new location vector (in system coordinates)
     */
    @Override
    public RVec3 getPosition() {
        long characterVa = targetVa();
        DoubleBuffer storeDoubles = Temporaries.doubleBuffer1.get();
        CharacterVirtual.getPosition(characterVa, storeDoubles);
        RVec3 result = new RVec3(storeDoubles);

        return result;
    }

    /**
     * Copy the position of the character. The character is unaffected.
     *
     * @param storeLocation storage for the location (in system coordinates, not
     * null, modified)
     * @param storeOrientation storage for the orientation (in system
     * coordinates, not null, modified)
     */
    @Override
    public void getPositionAndRotation(
            RVec3 storeLocation, Quat storeOrientation) {
        long characterVa = targetVa();

        DoubleBuffer storeDoubles = Temporaries.doubleBuffer1.get();
        CharacterVirtual.getPosition(characterVa, storeDoubles);
        storeLocation.set(storeDoubles);

        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        CharacterVirtual.getRotation(characterVa, storeFloats);
        storeOrientation.set(storeFloats);
    }

    /**
     * Copy the orientation of the character. The character is unaffected.
     *
     * @return a new rotation quaternion (in system coordinates)
     */
    @Override
    public Quat getRotation() {
        long characterVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        CharacterVirtual.getRotation(characterVa, storeFloats);
        Quat result = new Quat(storeFloats);

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
        long characterVa = targetVa();
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
        long characterVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        CharacterVirtual.getShapeOffset(characterVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Generate a TransformedShape that represents the volume occupied by the
     * character. The character is unaffected.
     *
     * @return a new object
     */
    @Override
    public TransformedShape getTransformedShape() {
        long characterVa = targetVa();
        long resultVa = CharacterVirtual.getTransformedShape(characterVa);
        TransformedShape result = new TransformedShape(resultVa, true);

        return result;
    }

    /**
     * Copy the character's "up" direction. The character is unaffected.
     *
     * @return a new direction vector
     */
    @Override
    public Vec3 getUp() {
        long characterVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        CharacterBase.getUp(characterVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

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
        long characterVa = targetVa();
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
        long characterVa = targetVa();
        long matrixVa = CharacterVirtual.getWorldTransform(characterVa);
        RMat44 result = new RMat44(matrixVa, true);

        return result;
    }

    /**
     * Test whether the character is in contact with or collided with the
     * specified body during the previous time step. The character is
     * unaffected.
     *
     * @param bodyId the ID of the body to test against
     * @return {@code true} if contact or collision, otherwise {@code false}
     */
    @Override
    public boolean hasCollidedWith(int bodyId) {
        long characterVa = targetVa();
        boolean result
                = CharacterVirtual.hasCollidedWithBody(characterVa, bodyId);

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
        long characterVa = targetVa();
        long otherVa = otherCharacter.targetVa();
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
        long characterVa = targetVa();
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
        long characterVa = targetVa();
        boolean result = CharacterBase.isSupported(characterVa);

        return result;
    }

    /**
     * Save the character's state to the specified recorder. The character is
     * unaffected.
     *
     * @param recorder the recorder to save to (not null)
     */
    @Override
    public void saveState(StateRecorder recorder) {
        long characterVa = targetVa();
        long recorderVa = recorder.va();
        CharacterBase.saveState(characterVa, recorderVa);
    }

    /**
     * Return the address of the native {@code CharacterVirtual}. No objects are
     * affected.
     *
     * @return a virtual address (not zero)
     */
    @Override
    public long targetVa() {
        long refVa = va();
        long result = getPtr(refVa);

        return result;
    }

    /**
     * Create another counted reference to the native {@code CharacterVirtual}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public CharacterVirtualRefC toRefC() {
        long refVa = va();
        long copyVa = copy(refVa);
        CharacterVirtualRefC result = new CharacterVirtualRefC(copyVa, system);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long copy(long refVa);

    native private static void freeWithSystem(long refVa, PhysicsSystem unused);

    native private static long getPtr(long refVa);
}
