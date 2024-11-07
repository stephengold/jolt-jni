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

import com.github.stephengold.joltjni.readonly.ConstBodyId;
import com.github.stephengold.joltjni.readonly.ConstCharacterVirtual;
import com.github.stephengold.joltjni.readonly.ConstCharacterVirtualSettings;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * A character implemented without a rigid body.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CharacterVirtual
        extends CharacterBase
        implements ConstCharacterVirtual {
    // *************************************************************************
    // fields

    /**
     * protect the contact listener (if any) from garbage collection
     */
    private CharacterContactListener contactListener;
    /**
     * protect the char-vs-char collision interface (if any) from garbage
     * collection
     */
    private CharacterVsCharacterCollision cvcInterface;
    // *************************************************************************
    // constructors

    /**
     * Instantiate a character with the specified native object assigned but not
     * owned.
     * <p>
     * For use in custom contact listeners.
     *
     * @param characterVa the virtual address of the native object to assign
     * (not zero)
     */
    public CharacterVirtual(long characterVa) {
        super(characterVa);
    }

    /**
     * Instantiate a character with a new native object assigned.
     *
     * @param settings the settings to use (not null, unaffected)
     * @param location the desired initial location (in system coordinates, not
     * null, unaffected)
     * @param orientation the desired initial orientation (in system
     * coordinates, not null, unaffected)
     * @param userData the desired user-data value
     * @param system the system to which the character will be added (not null)
     */
    public CharacterVirtual(
            ConstCharacterVirtualSettings settings, RVec3Arg location,
            QuatArg orientation, long userData, PhysicsSystem system) {
        long settingsVa = settings.va();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        float qx = orientation.getX();
        float qy = orientation.getY();
        float qz = orientation.getZ();
        float qw = orientation.getW();
        long systemVa = system.va();
        long characterVa = createCharacterVirtual(settingsVa, locX, locY, locZ,
                qx, qy, qz, qw, userData, systemVa);
        setVirtualAddress(characterVa, null); // not owner due to ref counting
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Apply a combination of Update, StickToFloor, and WalkStairs.
     *
     * @param deltaTime the time step to simulate
     * @param gravity the gravity acceleration vector (in meters per second
     * squared, not null, unaffected)
     * @param settings settings to use (not null, unaffected)
     * @param bpFilter to test whether the character collides with a broad-phase
     * layer (not null, unaffected)
     * @param olFilter to test whether the character collides with an object
     * layer (not null, unaffected)
     * @param bodyFilter to test whether the character collides with a body (not
     * null, unaffected)
     * @param shapeFilter to test whether the character collides with a shape
     * (not null, unaffected)
     * @param allocator for temporary allocations (not null)
     */
    public void extendedUpdate(float deltaTime, Vec3Arg gravity,
            ExtendedUpdateSettings settings, BroadPhaseLayerFilter bpFilter,
            ObjectLayerFilter olFilter, BodyFilter bodyFilter,
            ShapeFilter shapeFilter, TempAllocator allocator) {
        long characterVa = va();
        float gravityX = gravity.getX();
        float gravityY = gravity.getY();
        float gravityZ = gravity.getZ();
        long settingsVa = settings.va();
        long bpFilterVa = bpFilter.va();
        long olFilterVa = olFilter.va();
        long bodyFilterVa = bodyFilter.va();
        long shapeFilterVa = shapeFilter.va();
        long allocatorVa = allocator.va();
        extendedUpdate(characterVa, deltaTime, gravityX, gravityY, gravityZ,
                settingsVa, bpFilterVa, olFilterVa, bodyFilterVa, shapeFilterVa,
                allocatorVa);
    }

    /**
     * Access the char-vs-char collision interface.
     *
     * @return the pre-existing object, or {@code null} if none
     */
    public CharacterVsCharacterCollision getCharacterVsCharacterCollision() {
        return cvcInterface;
    }

    /**
     * Access the (application-provided) contact listener.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    public CharacterContactListener getListener() {
        return contactListener;
    }

    /**
     * Replace the char-vs-char collision interface.
     *
     * @param cvcInterface the desired interface (not null)
     */
    public void setCharacterVsCharacterCollision(
            CharacterVsCharacterCollision cvcInterface) {
        this.cvcInterface = cvcInterface;
        long characterVa = va();
        long interfaceVa = cvcInterface.va();
        setCharacterVsCharacterCollision(characterVa, interfaceVa);
    }

    /**
     * Enable or disable enhanced internal edge removal.
     *
     * @param enable {@code true} to enable, {@code false} to disable
     * (default=false)
     */
    public void setEnhancedInternalEdgeRemoval(boolean enable) {
        long characterVa = va();
        setEnhancedInternalEdgeRemoval(characterVa, enable);
    }

    /**
     * Alter the maximum angle for merging during hit reduction.
     *
     * @param cosine the cosine of the desired maximum angle, or -1 to disable
     * hit reduction (default=1)
     */
    public void setHitReductionCosMaxAngle(float cosine) {
        long characterVa = va();
        setHitReductionCosMaxAngle(characterVa, cosine);
    }

    /**
     * Alter the shape of the inner body. Invoke this after a successful
     * invocation of {@code setShape()}.
     *
     * @param shape the desired shape (not null, unaffected, default=?)
     */
    public void setInnerBodyShape(ConstShape shape) {
        long characterVa = va();
        long shapeVa = shape.va();
        setInnerBodyShape(characterVa, shapeVa);
    }

    /**
     * Alter the character's linear velocity.
     *
     * @param velocity the desired velocity vector (meters per second in system
     * coordinates, default=(0,0,0))
     */
    public void setLinearVelocity(Vec3Arg velocity) {
        long characterVa = va();
        float vx = velocity.getX();
        float vy = velocity.getY();
        float vz = velocity.getZ();
        setLinearVelocity(characterVa, vx, vy, vz);
    }

    /**
     * Replace the contact listener.
     *
     * @param listener the desired listener
     */
    public void setListener(CharacterContactListener listener) {
        this.contactListener = listener;
        long characterVa = va();
        long listenerVa = listener.va();
        setListener(characterVa, listenerVa);
    }

    /**
     * Alter the character's mass.
     *
     * @param mass the desired mass (in kilograms, default=70)
     */
    public void setMass(float mass) {
        long characterVa = va();
        setMass(characterVa, mass);
    }

    /**
     * Alter the maximum number of hits to be collected.
     *
     * @param maxHits the desired limit (&ge;0, default=256)
     */
    public void setMaxNumHits(int maxHits) {
        long characterVa = va();
        setMaxNumHits(characterVa, maxHits);
    }

    /**
     * Alter the maximum force applied to bodies.
     *
     * @param force the desired force limit (in Newtons, default=100)
     */
    public void setMaxStrength(float force) {
        long characterVa = va();
        setMaxStrength(characterVa, force);
    }

    /**
     * Alter how quickly penetration is resolved.
     *
     * @param fraction the desired resolution fraction (0&rarr;never resolved,
     * 1&rarr;all in one update, default=1)
     */
    public void setPenetrationRecoverySpeed(float fraction) {
        long characterVa = va();
        setPenetrationRecoverySpeed(characterVa, fraction);
    }

    /**
     * Relocate the character.
     *
     * @param location the desired location (in system coordinates,
     * default=(0,0,0))
     */
    public void setPosition(RVec3Arg location) {
        long characterVa = va();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        setPosition(characterVa, locX, locY, locZ);
    }

    /**
     * Re-orient the character.
     *
     * @param orientation the desired orientation (in system coordinates,
     * default=(0,0,0,1))
     */
    public void setRotation(QuatArg orientation) {
        long characterVa = va();
        float qw = orientation.getW();
        float qx = orientation.getX();
        float qy = orientation.getY();
        float qz = orientation.getZ();
        setRotation(characterVa, qx, qy, qz, qw);
    }

    /**
     * Alter the character's shape.
     *
     * @param shape the desired shape (not null)
     * @param maxPenetrationDepth the maximum acceptable penetration after the
     * alteration, or {@code Float.MAX_VALUE} to skip the check
     * @param broadPhaseLayerFilter the broadphase filter used to test for
     * collisions (not null)
     * @param objectLayerFilter the object-layer filter used to test for
     * collisions (not null)
     * @param bodyFilter the body filter used to test for collisions (not null)
     * @param shapeFilter the shape filter used to test for collisions (not
     * null)
     * @param allocator the desired allocator (not null)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public boolean setShape(ConstShape shape, float maxPenetrationDepth,
            BroadPhaseLayerFilter broadPhaseLayerFilter,
            ObjectLayerFilter objectLayerFilter, BodyFilter bodyFilter,
            ShapeFilter shapeFilter, TempAllocator allocator) {
        long characterVa = va();
        long shapeVa = shape.va();
        long bplFilterVa = broadPhaseLayerFilter.va();
        long olFilterVa = objectLayerFilter.va();
        long bodyFilterVa = bodyFilter.va();
        long shapeFilterVa = shapeFilter.va();
        long allocatorVa = allocator.va();
        boolean result = setShape(
                characterVa, shapeVa, maxPenetrationDepth, bplFilterVa,
                olFilterVa, bodyFilterVa, shapeFilterVa, allocatorVa);

        return result;
    }

    /**
     * Alter the shape offset.
     *
     * @param offset the desired offset (in local coordinates, default=(0,0,0))
     */
    public void setShapeOffset(Vec3Arg offset) {
        long characterVa = va();
        float dx = offset.getX();
        float dy = offset.getY();
        float dz = offset.getZ();
        setShapeOffset(characterVa, dx, dy, dz);
    }

    /**
     * Alter the user data.
     *
     * @param userData the desired value
     */
    public void setUserData(long userData) {
        long characterVa = va();
        setUserData(characterVa, userData);
    }

    /**
     * Update the estimated ground velocity.
     */
    public void updateGroundVelocity() {
        long characterVa = va();
        updateGroundVelocity(characterVa);
    }
    // *************************************************************************
    // CharacterBase methods

    /**
     * Create a counted reference to the native {@code CharacterVirtual}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public CharacterVirtualRef toRef() {
        long characterVa = va();
        long refVa = toRef(characterVa);
        CharacterVirtualRef result = new CharacterVirtualRef(refVa, true);

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
        long characterVa = va();
        float vx = desiredVelocity.getX();
        float vy = desiredVelocity.getY();
        float vz = desiredVelocity.getZ();
        float[] storeVelocity = new float[3];
        cancelVelocityTowardsSteepSlopes(
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
        long characterVa = va();
        float vx = desiredVelocity.getX();
        float vy = desiredVelocity.getY();
        float vz = desiredVelocity.getZ();
        boolean result = canWalkStairs(characterVa, vx, vy, vz);

        return result;
    }

    /**
     * Access the list of contacts. The character is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public ContactList getActiveContacts() {
        long characterVa = va();
        long listVa = getActiveContacts(characterVa);
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
        long characterVa = va();
        double locX = getCenterOfMassPositionX(characterVa);
        double locY = getCenterOfMassPositionY(characterVa);
        double locZ = getCenterOfMassPositionZ(characterVa);
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
        long characterVa = va();
        long matrixVa = getCenterOfMassTransform(characterVa);
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
        long characterVa = va();
        float result = getCharacterPadding(characterVa);

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
        long characterVa = va();
        boolean result = getEnhancedInternalEdgeRemoval(characterVa);

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
        long characterVa = va();
        float result = getHitReductionCosMaxAngle(characterVa);

        return result;
    }

    /**
     * Return the ID of the inner body. The character is unaffected.
     *
     * @return the ID, or {@code null} if none
     */
    @Override
    public BodyId getInnerBodyId() {
        long characterVa = va();
        long idVa = getInnerBodyId(characterVa);
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
        long characterVa = va();
        float vx = getLinearVelocityX(characterVa);
        float vy = getLinearVelocityY(characterVa);
        float vz = getLinearVelocityZ(characterVa);
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
        long characterVa = va();
        float result = getMass(characterVa);

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
        long characterVa = va();
        boolean result = getMaxHitsExceeded(characterVa);

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
        long characterVa = va();
        int result = getMaxNumHits(characterVa);

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
        long characterVa = va();
        float result = getMaxStrength(characterVa);

        return result;
    }

    /**
     * Return how quickly penetration is resolved. The character is unaffected.
     *
     * @return the resolution fraction (0=never resolved, 1=all in one update)
     */
    @Override
    public float getPenetrationRecoverySpeed() {
        long characterVa = va();
        float result = getPenetrationRecoverySpeed(characterVa);

        return result;
    }

    /**
     * Copy the location of the character. The character is unaffected.
     *
     * @return a new location vector (in system coordinates)
     */
    @Override
    public RVec3 getPosition() {
        long characterVa = va();
        double x = getPositionX(characterVa);
        double y = getPositionY(characterVa);
        double z = getPositionZ(characterVa);
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
        long characterVa = va();
        float w = getRotationW(characterVa);
        float x = getRotationX(characterVa);
        float y = getRotationY(characterVa);
        float z = getRotationZ(characterVa);
        Quat result = new Quat(x, y, z, w);

        return result;
    }

    /**
     * Copy the local offset applied to the shape. The character is unaffected.
     *
     * @return a new offset vector (in local coordinates)
     */
    @Override
    public Vec3 getShapeOffset() {
        long characterVa = va();
        float x = getShapeOffsetX(characterVa);
        float y = getShapeOffsetY(characterVa);
        float z = getShapeOffsetZ(characterVa);
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
        long characterVa = va();
        long result = getUserData(characterVa);

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
        long characterVa = va();
        long matrixVa = getWorldTransform(characterVa);
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
        long characterVa = va();
        long idVa = bodyId.va();
        boolean result = hasCollidedWithBody(characterVa, idVa);

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
        long characterVa = va();
        long otherVa = otherCharacter.va();
        boolean result = hasCollidedWithCharacter(characterVa, otherVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void cancelVelocityTowardsSteepSlopes(
            long characterVa, float vx, float vy, float vz,
            float[] storeVelocity);

    native private static boolean canWalkStairs(
            long characterVa, float vx, float vy, float vz);

    native private static long createCharacterVirtual(
            long settingsVa, double locX, double locY, double locZ, float qx,
            float qy, float qz, float qw, long userData, long systemVa);

    native private static void extendedUpdate(long characterVa, float deltaTime,
            float gravityX, float gravityY, float gravityZ, long settingsVa,
            long bpFilterVa, long olFilterVa, long bodyFilterVa,
            long shapeFilterVa, long allocatorVa);

    native private static long getActiveContacts(long characterVa);

    native private static double getCenterOfMassPositionX(long characterVa);

    native private static double getCenterOfMassPositionY(long characterVa);

    native private static double getCenterOfMassPositionZ(long characterVa);

    native private static long getCenterOfMassTransform(long characterVa);

    native private static float getCharacterPadding(long characterVa);

    native private static boolean getEnhancedInternalEdgeRemoval(
            long characterVa);

    native private static float getHitReductionCosMaxAngle(long characterVa);

    native private static long getInnerBodyId(long characterVa);

    native private static float getLinearVelocityX(long characterVa);

    native private static float getLinearVelocityY(long characterVa);

    native private static float getLinearVelocityZ(long characterVa);

    native private static float getMass(long characterVa);

    native private static boolean getMaxHitsExceeded(long characterVa);

    native private static int getMaxNumHits(long characterVa);

    native private static float getMaxStrength(long characterVa);

    native private static float getPenetrationRecoverySpeed(long characterVa);

    native private static double getPositionX(long characterVa);

    native private static double getPositionY(long characterVa);

    native private static double getPositionZ(long characterVa);

    native private static float getRotationW(long characterVa);

    native private static float getRotationX(long characterVa);

    native private static float getRotationY(long characterVa);

    native private static float getRotationZ(long characterVa);

    native private static float getShapeOffsetX(long characterVa);

    native private static float getShapeOffsetY(long characterVa);

    native private static float getShapeOffsetZ(long characterVa);

    native static long getUserData(long characterVa);

    native private static long getWorldTransform(long characterVa);

    native private static boolean hasCollidedWithBody(
            long characterVa, long idVa);

    native private static boolean hasCollidedWithCharacter(
            long characterVa, long otherVa);

    native private static void setCharacterVsCharacterCollision(
            long characterVa, long interfaceVa);

    native private static void setEnhancedInternalEdgeRemoval(
            long characterVa, boolean enable);

    native private static void setHitReductionCosMaxAngle(
            long characterVa, float cosine);

    native private static void setInnerBodyShape(
            long characterVa, long shapeVa);

    native private static void setLinearVelocity(
            long characterVa, float vx, float vy, float vz);

    native private static void setListener(long characterVa, long listenerVa);

    native private static void setMass(long characterVa, float mass);

    native private static void setMaxNumHits(long characterVa, int maxHits);

    native private static void setMaxStrength(long characterVa, float force);

    native private static void setPenetrationRecoverySpeed(
            long characterVa, float fraction);

    native private static void setPosition(
            long characterVa, double locX, double locY, double locZ);

    native private static void setRotation(
            long characterVa, float qx, float qy, float qz, float qw);

    native private static boolean setShape(long characterVa, long shapeVa,
            float maxPenetrationDepth, long bplFilterVa, long olFilterVa,
            long bodyFilterVa, long shapeFilterVa, long allocatorVa);

    native private static void setShapeOffset(
            long characterVa, float dx, float dy, float dz);

    native private static void setUserData(long characterVa, long userData);

    native private static long toRef(long characterVa);

    native private static void updateGroundVelocity(long characterVa);
}
