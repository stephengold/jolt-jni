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

import com.github.stephengold.joltjni.enumerate.EActivation;
import com.github.stephengold.joltjni.readonly.ConstCharacter;
import com.github.stephengold.joltjni.readonly.ConstCharacterSettings;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * A character implemented using a kinematic rigid body.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Character
        extends CharacterBase
        implements ConstCharacter, RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a character with the specified native object assigned but not
     * owned.
     *
     * @param characterVa the virtual address of the native object to assign
     * (not zero)
     */
    Character(long characterVa) {
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
    public Character(ConstCharacterSettings settings, RVec3Arg location,
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
        long characterVa = createCharacter(settingsVa, locX, locY, locZ,
                qx, qy, qz, qw, userData, systemVa);
        setVirtualAddress(characterVa, null); // not owner due to ref counting
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Wake up the character.
     *
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     */
    public void activate(boolean lockBodies) {
        long characterVa = va();
        activate(characterVa, lockBodies);
    }

    /**
     * Apply an impulse to the character's center of mass.
     *
     * @param impulse the impulse vector (kilogram.meters per second in system
     * coordinates, not null, unaffected)
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     */
    public void addImpulse(Vec3Arg impulse, boolean lockBodies) {
        long characterVa = va();
        float x = impulse.getX();
        float y = impulse.getY();
        float z = impulse.getZ();
        addImpulse(characterVa, x, y, z, lockBodies);
    }

    /**
     * Add to the character's linear velocity.
     *
     * @param deltaV the change in velocity (meters per second in system
     * coordinates, not null, unaffected)
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     */
    public void addLinearVelocity(Vec3Arg deltaV, boolean lockBodies) {
        long characterVa = va();
        float vx = deltaV.getX();
        float vy = deltaV.getY();
        float vz = deltaV.getZ();
        addLinearVelocity(characterVa, vx, vy, vz, lockBodies);
    }

    /**
     * Add the character to its {@code PhysicsSystem}.
     *
     * @param activation whether to activate the character (not null)
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     */
    public void addToPhysicsSystem(EActivation activation, boolean lockBodies) {
        long characterVa = va();
        int ordinal = activation.ordinal();
        addToPhysicsSystem(characterVa, ordinal, lockBodies);
    }

    /**
     * Remove the character from its {@code PhysicsSystem}.
     *
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     */
    public void removeFromPhysicsSystem(boolean lockBodies) {
        long characterVa = va();
        removeFromPhysicsSystem(characterVa, lockBodies);
    }

    /**
     * Alter the character's object layer.
     *
     * @param layer the index of the desired layer
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     */
    public void setLayer(int layer, boolean lockBodies) {
        long characterVa = va();
        setLayer(characterVa, layer, lockBodies);
    }

    /**
     * Alter the character's motion.
     *
     * @param linearVelocity the desired linear velocity (meters per second in
     * system coordinates, not null, unaffected)
     * @param omega the desired angular velocity (radians per second in system
     * coordinates, not null, unaffected)
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     */
    public void setLinearAndAngularVelocity(Vec3Arg linearVelocity,
            Vec3Arg omega, boolean lockBodies) {
        long characterVa = va();
        float vx = linearVelocity.getX();
        float vy = linearVelocity.getY();
        float vz = linearVelocity.getZ();
        float wx = omega.getX();
        float wy = omega.getY();
        float wz = omega.getZ();
        setLinearAndAngularVelocity(
                characterVa, vx, vy, vz, wx, wy, wz, lockBodies);
    }

    /**
     * Alter the character's linear velocity.
     *
     * @param velocity the desired velocity (meters per second in system
     * coordinates, not null, unaffected)
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     */
    public void setLinearVelocity(Vec3Arg velocity, boolean lockBodies) {
        long characterVa = va();
        float vx = velocity.getX();
        float vy = velocity.getY();
        float vz = velocity.getZ();
        setLinearVelocity(characterVa, vx, vy, vz, lockBodies);
    }

    /**
     * Re-locate the character, optionally activating it.
     *
     * @param location the desired location (in system coordinates, not null,
     * unaffected)
     * @param activation whether to activate the character (not null)
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     */
    public void setPosition(
            RVec3Arg location, EActivation activation, boolean lockBodies) {
        long characterVa = va();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        int ordinal = activation.ordinal();
        setPosition(characterVa, locX, locY, locZ, ordinal, lockBodies);
    }

    /**
     * Re-position the character, optionally activating it.
     *
     * @param location the desired location (in system coordinates, not null,
     * unaffected)
     * @param orientation the desired orientation (in system coordinates, not
     * null, unaffected)
     * @param activation whether to activate the character (not null)
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     */
    public void setPositionAndRotation(RVec3Arg location, QuatArg orientation,
            EActivation activation, boolean lockBodies) {
        long characterVa = va();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        float qx = orientation.getX();
        float qy = orientation.getY();
        float qz = orientation.getZ();
        float qw = orientation.getW();
        int ordinal = activation.ordinal();
        setPositionAndRotation(characterVa, locX, locY, locZ,
                qx, qy, qz, qw, ordinal, lockBodies);
    }

    /**
     * Re-orient the character, optionally activating it.
     *
     * @param orientation the desired orientation (in system coordinates, not
     * null, unaffected)
     * @param activation whether to activate the character (not null)
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     */
    public void setRotation(QuatArg orientation, EActivation activation,
            boolean lockBodies) {
        long characterVa = va();
        float qx = orientation.getX();
        float qy = orientation.getY();
        float qz = orientation.getZ();
        float qw = orientation.getW();
        int ordinal = activation.ordinal();
        setRotation(characterVa, qx, qy, qz, qw, ordinal, lockBodies);
    }

    /**
     * Attempt to alter the character's shape.
     *
     * @param shape the desired shape (not null, unaffected)
     * @param maxPenetrationDepth the maximum penetration to allow, or MAX_VALUE
     * to skip the penetration check
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     * @return true if the alteration succeeded, otherwise false
     */
    public boolean setShape(
            ConstShape shape, float maxPenetrationDepth, boolean lockBodies) {
        long characterVa = va();
        long shapeVa = shape.va();
        boolean result = setShape(
                characterVa, shapeVa, maxPenetrationDepth, lockBodies);

        return result;
    }
    // *************************************************************************
    // ConstCharacter methods

    /**
     * Return the ID of the body associated with this character. The character
     * is unaffected.
     *
     * @return a new ID
     */
    @Override
    public BodyId getBodyId() {
        long characterVa = va();
        long idVa = getBodyId(characterVa);
        BodyId result = new BodyId(idVa, true);

        return result;
    }

    /**
     * Return the location of the rigid body's center of mass. The character is
     * unaffected.
     *
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     * @return a new location vector (in system coordinates)
     */
    @Override
    public RVec3 getCenterOfMassPosition(boolean lockBodies) {
        long characterVa = va();
        double[] storeDoubles = new double[3];
        getCenterOfMassPosition(characterVa, storeDoubles, lockBodies);
        RVec3 result
                = new RVec3(storeDoubles[0], storeDoubles[1], storeDoubles[2]);

        return result;
    }

    /**
     * Return the character's object layer. The character is unaffected.
     *
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     * @return a layer index (&ge;0)
     */
    @Override
    public int getLayer(boolean lockBodies) {
        long characterVa = va();
        int result = getLayer(characterVa);

        return result;
    }

    /**
     * Copy the linear velocity of the character. The character is unaffected.
     *
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     * @return a new velocity vector (meters per second in system coordinates)
     */
    @Override
    public Vec3 getLinearVelocity(boolean lockBodies) {
        long characterVa = va();
        float[] storeFloats = new float[3];
        getLinearVelocity(characterVa, storeFloats, lockBodies);
        Vec3 result = new Vec3(storeFloats[0], storeFloats[1], storeFloats[2]);

        return result;
    }

    /**
     * Copy the location of the character. The character is unaffected.
     *
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     * @return a new location vector (in system coordinates)
     */
    @Override
    public RVec3 getPosition(boolean lockBodies) {
        long characterVa = va();
        double[] storeDoubles = new double[3];
        getPosition(characterVa, storeDoubles, lockBodies);
        RVec3 result
                = new RVec3(storeDoubles[0], storeDoubles[1], storeDoubles[2]);

        return result;
    }

    /**
     * Copy the position of the associated body. The character is unaffected.
     *
     * @param storeLocation the desired location (in system coordinates, not
     * null, unaffected)
     * @param storeOrientation the desired orientation (in system coordinates,
     * not null, unaffected)
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     */
    @Override
    public void getPositionAndRotation(
            RVec3 storeLocation, Quat storeOrientation, boolean lockBodies) {
        long characterVa = va();
        double[] storeDoubles = new double[3];
        float[] storeFloats = new float[4];
        getPositionAndRotation(
                characterVa, storeDoubles, storeFloats, lockBodies);
        storeLocation.set(storeDoubles[0], storeDoubles[1], storeDoubles[2]);
        storeOrientation.set(
                storeFloats[0], storeFloats[1], storeFloats[2], storeFloats[3]);
    }

    /**
     * Copy the orientation of the character. The character is unaffected.
     *
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     * @return a new rotation quaternion (in system coordinates)
     */
    @Override
    public Quat getRotation(boolean lockBodies) {
        long characterVa = va();
        float[] storeFloats = new float[4];
        getRotation(characterVa, storeFloats, lockBodies);
        Quat result = new Quat(
                storeFloats[0], storeFloats[1], storeFloats[2], storeFloats[3]);

        return result;
    }

    /**
     * Calculate the character's local-to-system coordinate transform. The
     * character is unaffected.
     *
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     * @return a new transform matrix
     */
    @Override
    public RMat44 getWorldTransform(boolean lockBodies) {
        long characterVa = va();
        long matrixVa = getWorldTransform(characterVa, lockBodies);
        RMat44 result = new RMat44(matrixVa, true);

        return result;
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the character. The character is
     * unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long characterVa = va();
        int result = getRefCount(characterVa);

        return result;
    }

    /**
     * Create a counted reference to the character.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public CharacterRef toRef() {
        long characterVa = va();
        long refVa = toRef(characterVa);
        CharacterRef result = new CharacterRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void activate(long characterVa, boolean lockBodies);

    native private static void addImpulse(
            long characterVa, float x, float y, float z, boolean lockBodies);

    native private static void addLinearVelocity(
            long characterVa, float vx, float vy, float vz, boolean lockBodies);

    native private static void addToPhysicsSystem(
            long characterVa, int ordinal, boolean lockBodies);

    native private static long createCharacter(
            long settingsVa, double locX, double locY, double locZ, float qx,
            float qy, float qz, float qw, long userData, long systemVa);

    native private static long getBodyId(long characterVa);

    native private static void getCenterOfMassPosition(
            long characterVa, double[] storeDoubles, boolean lockBodies);

    native private static int getLayer(long characterVa);

    native private static void getLinearVelocity(
            long characterVa, float[] storeFloats, boolean lockBodies);

    native private static void getPosition(
            long characterVa, double[] storeDoubles, boolean lockBodies);

    native private static void getPositionAndRotation(long characterVa,
            double[] storeDoubles, float[] storeFloats, boolean lockBodies);

    native private static int getRefCount(long characterVa);

    native private static void getRotation(
            long characterVa, float[] toreFloats, boolean lockBodies);

    native private static long getWorldTransform(
            long characterVa, boolean lockBodies);

    native private static void removeFromPhysicsSystem(
            long characterVa, boolean lockBodies);

    native private static void setLayer(
            long characterVa, int layer, boolean lockBodies);

    native private static void setLinearAndAngularVelocity(
            long characterVa, float vx, float vy, float vz,
            float wx, float wy, float wz, boolean lockBodies);

    native private static void setLinearVelocity(
            long characterVa, float vx, float vy, float vz, boolean lockBodies);

    native private static void setPosition(long characterVa, double locX,
            double locY, double locZ, int ordinal, boolean lockBodies);

    native private static void setPositionAndRotation(
            long characterVa, double locX, double locY, double locZ, float qx,
            float qy, float qz, float qw, int ordinal, boolean lockBodies);

    native private static void setRotation(long characterVa, float qx, float qy,
            float qz, float qw, int ordinal, boolean lockBodies);

    native private static boolean setShape(long characterVa, long shapeVa,
            float maxPenetrationDepth, boolean lockBodies);

    native private static long toRef(long characterVa);
}
