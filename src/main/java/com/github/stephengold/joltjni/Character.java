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
public class Character extends CharacterBase implements ConstCharacter {
    // *************************************************************************
    // fields

    /**
     * where to add the body (not null)
     */
    final private PhysicsSystem system;
    // *************************************************************************
    // constructors

    /**
     * Instantiate a character with the specified native object assigned but not
     * owned.
     *
     * @param characterVa the virtual address of the native object to assign
     * (not zero)
     * @param physicsSystem where to add the body (not null)
     */
    Character(long characterVa, PhysicsSystem physicsSystem) {
        super(characterVa);
        this.system = physicsSystem;
    }

    /**
     * Instantiate a character with the specified properties.
     *
     * @param settings the settings to use (not null, unaffected)
     * @param location the desired initial location (in system coordinates, not
     * null, unaffected)
     * @param orientation the desired initial orientation (in system
     * coordinates, not null, unaffected)
     * @param userData the desired user-data value
     * @param system where to add the body (not null)
     */
    public Character(ConstCharacterSettings settings, RVec3Arg location,
            QuatArg orientation, long userData, PhysicsSystem system) {
        this.system = system;
        long settingsVa = settings.targetVa();
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
        setVirtualAddress(characterVa); // not owner due to ref counting
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Wake up the character using the locking body interface.
     */
    public void activate() {
        activate(true);
    }

    /**
     * Wake up the character.
     *
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     */
    public void activate(boolean lockBodies) {
        long characterVa = va();
        activate(characterVa, lockBodies);
    }

    /**
     * Apply an impulse to the character's center of mass, using the locking
     * body interface.
     *
     * @param impulse the impulse vector (kilogram.meters per second in system
     * coordinates, not null, unaffected)
     */
    public void addImpulse(Vec3Arg impulse) {
        addImpulse(impulse, true);
    }

    /**
     * Apply an impulse to the character's center of mass.
     *
     * @param impulse the impulse vector (kilogram.meters per second in system
     * coordinates, not null, unaffected)
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     */
    public void addImpulse(Vec3Arg impulse, boolean lockBodies) {
        long characterVa = va();
        float x = impulse.getX();
        float y = impulse.getY();
        float z = impulse.getZ();
        addImpulse(characterVa, x, y, z, lockBodies);
    }

    /**
     * Add to the character's linear velocity, using the locking body interface.
     *
     * @param deltaV the change in velocity (meters per second in system
     * coordinates, not null, unaffected)
     */
    public void addLinearVelocity(Vec3Arg deltaV) {
        addLinearVelocity(deltaV, true);
    }

    /**
     * Add to the character's linear velocity.
     *
     * @param deltaV the change in velocity (meters per second in system
     * coordinates, not null, unaffected)
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     */
    public void addLinearVelocity(Vec3Arg deltaV, boolean lockBodies) {
        long characterVa = va();
        float vx = deltaV.getX();
        float vy = deltaV.getY();
        float vz = deltaV.getZ();
        addLinearVelocity(characterVa, vx, vy, vz, lockBodies);
    }

    /**
     * Add the character to its {@code PhysicsSystem} and activate it, using the
     * locking body interface.
     */
    public void addToPhysicsSystem() {
        addToPhysicsSystem(EActivation.Activate);
    }

    /**
     * Add the character to its {@code PhysicsSystem} using the locking body
     * interface.
     *
     * @param activation whether to activate the character (not null,
     * default=Activate)
     */
    public void addToPhysicsSystem(EActivation activation) {
        addToPhysicsSystem(activation, true);
    }

    /**
     * Add the character to its {@code PhysicsSystem}.
     *
     * @param activation whether to activate the character (not null,
     * default=Activate)
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     */
    public void addToPhysicsSystem(EActivation activation, boolean lockBodies) {
        long characterVa = va();
        int ordinal = activation.ordinal();
        addToPhysicsSystem(characterVa, ordinal, lockBodies);
    }

    /**
     * Needs to be invoked after every physics update.
     *
     * @param maxSeparation the max distance between the floor and the character
     * for standing (in meters)
     */
    public void postSimulation(float maxSeparation) {
        postSimulation(maxSeparation, true);
    }

    /**
     * Needs to be invoked after every physics update.
     *
     * @param maxSeparation the max distance between the floor and the character
     * for standing (in meters)
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     */
    public void postSimulation(float maxSeparation, boolean lockBodies) {
        long characterVa = va();
        postSimulation(characterVa, maxSeparation, lockBodies);
    }

    /**
     * Remove the character from its {@code PhysicsSystem} using the locking
     * body interface.
     */
    public void removeFromPhysicsSystem() {
        removeFromPhysicsSystem(true);
    }

    /**
     * Remove the character from its {@code PhysicsSystem}.
     *
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     */
    public void removeFromPhysicsSystem(boolean lockBodies) {
        long characterVa = va();
        removeFromPhysicsSystem(characterVa, lockBodies);
    }

    /**
     * Alter the character's object layer, using the locking body interface.
     *
     * @param layer the index of the desired layer (default=0)
     */
    public void setLayer(int layer) {
        setLayer(layer, true);
    }

    /**
     * Alter the character's object layer.
     *
     * @param layer the index of the desired layer (default=0)
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     */
    public void setLayer(int layer, boolean lockBodies) {
        long characterVa = va();
        setLayer(characterVa, layer, lockBodies);
    }

    /**
     * Alter the character's motion using the locking body interface.
     *
     * @param linearVelocity the desired linear velocity (meters per second in
     * system coordinates, not null, unaffected)
     * @param omega the desired angular velocity (radians per second in system
     * coordinates, not null, unaffected)
     */
    public void setLinearAndAngularVelocity(
            Vec3Arg linearVelocity, Vec3Arg omega) {
        setLinearAndAngularVelocity(linearVelocity, omega, true);
    }

    /**
     * Alter the character's motion.
     *
     * @param linearVelocity the desired linear velocity (meters per second in
     * system coordinates, not null, unaffected)
     * @param omega the desired angular velocity (radians per second in system
     * coordinates, not null, unaffected)
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
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
     * Alter the character's linear velocity using the locking body interface.
     *
     * @param velocity the desired velocity (meters per second in system
     * coordinates, not null, unaffected)
     */
    public void setLinearVelocity(Vec3Arg velocity) {
        setLinearVelocity(velocity, true);
    }

    /**
     * Alter the character's linear velocity.
     *
     * @param velocity the desired velocity (meters per second in system
     * coordinates, not null, unaffected)
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     */
    public void setLinearVelocity(Vec3Arg velocity, boolean lockBodies) {
        long characterVa = va();
        float vx = velocity.getX();
        float vy = velocity.getY();
        float vz = velocity.getZ();
        setLinearVelocity(characterVa, vx, vy, vz, lockBodies);
    }

    /**
     * Relocate and activate the character using the locking body interface.
     *
     * @param location the desired location (in system coordinates, not null,
     * unaffected)
     */
    public void setPosition(RVec3Arg location) {
        setPosition(location, EActivation.Activate, true);
    }

    /**
     * Relocate the character, optionally activating it, using the locking body
     * interface.
     *
     * @param location the desired location (in system coordinates, not null,
     * unaffected)
     * @param activation whether to activate the character (not null,
     * default=Activate)
     */
    public void setPosition(RVec3Arg location, EActivation activation) {
        setPosition(location, activation, true);
    }

    /**
     * Relocate the character, optionally activating it.
     *
     * @param location the desired location (in system coordinates, not null,
     * unaffected)
     * @param activation whether to activate the character (not null,
     * default=Activate)
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
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
     * Reposition and activate the character using the locking body interface.
     *
     * @param location the desired location (in system coordinates, not null,
     * unaffected)
     * @param orientation the desired orientation (in system coordinates, not
     * null, unaffected)
     */
    public void setPositionAndRotation(RVec3Arg location, QuatArg orientation) {
        setPositionAndRotation(location, orientation, EActivation.Activate);
    }

    /**
     * Reposition the character, optionally activating it.
     *
     * @param location the desired location (in system coordinates, not null,
     * unaffected)
     * @param orientation the desired orientation (in system coordinates, not
     * null, unaffected)
     * @param activation whether to activate the character (not null,
     * default=Activate)
     */
    public void setPositionAndRotation(RVec3Arg location, QuatArg orientation,
            EActivation activation) {
        setPositionAndRotation(location, orientation, activation, true);
    }

    /**
     * Reposition the character, optionally activating it.
     *
     * @param location the desired location (in system coordinates, not null,
     * unaffected)
     * @param orientation the desired orientation (in system coordinates, not
     * null, unaffected)
     * @param activation whether to activate the character (not null,
     * default=Activate)
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
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
     * Re-orient and activate the character using the locking body interface.
     *
     * @param orientation the desired orientation (in system coordinates, not
     * null, unaffected)
     */
    public void setRotation(QuatArg orientation) {
        setRotation(orientation, EActivation.Activate);
    }

    /**
     * Re-orient the character, optionally activating it.
     *
     * @param orientation the desired orientation (in system coordinates, not
     * null, unaffected)
     * @param activation whether to activate the character (not null,
     * default=Activate)
     */
    public void setRotation(QuatArg orientation, EActivation activation) {
        setRotation(orientation, activation, true);
    }

    /**
     * Re-orient the character, optionally activating it.
     *
     * @param orientation the desired orientation (in system coordinates, not
     * null, unaffected)
     * @param activation whether to activate the character (not null,
     * default=Activate)
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
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
     * Attempt to replace the character's shape using the locking body
     * interface.
     *
     * @param shape the desired shape (not null, unaffected)
     * @param maxPenetrationDepth the maximum penetration to allow, or MAX_VALUE
     * to skip the penetration check
     * @return {@code true} if the replacement succeeded, otherwise
     * {@code false}
     */
    public boolean setShape(ConstShape shape, float maxPenetrationDepth) {
        boolean result = setShape(shape, maxPenetrationDepth, true);
        return result;
    }

    /**
     * Attempt to replace the character's shape.
     *
     * @param shape the desired shape (not null, unaffected)
     * @param maxPenetrationDepth the maximum penetration to allow, or MAX_VALUE
     * to skip the penetration check
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     * @return {@code true} if the replacement succeeded, otherwise
     * {@code false}
     */
    public boolean setShape(
            ConstShape shape, float maxPenetrationDepth, boolean lockBodies) {
        long characterVa = va();
        long shapeVa = shape.targetVa();
        boolean result = setShape(
                characterVa, shapeVa, maxPenetrationDepth, lockBodies);

        return result;
    }
    // *************************************************************************
    // CharacterBase methods

    /**
     * Create a counted reference to the native {@code Character}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public CharacterRef toRef() {
        long characterVa = va();
        long refVa = toRef(characterVa);
        CharacterRef result = new CharacterRef(refVa, system);

        return result;
    }
    // *************************************************************************
    // ConstCharacter methods

    /**
     * Return the ID of the body associated with the character. The character is
     * unaffected. (native method: GetBodyID)
     *
     * @return the {@code BodyID} value
     */
    @Override
    public int getBodyId() {
        long characterVa = va();
        int result = getBodyId(characterVa);

        return result;
    }

    /**
     * Copy the location of the rigid body's center of mass using the locking
     * body interface. The character is unaffected.
     *
     * @return a new location vector (in system coordinates)
     */
    @Override
    public RVec3 getCenterOfMassPosition() {
        RVec3 result = getCenterOfMassPosition(true);
        return result;
    }

    /**
     * Copy the location of the rigid body's center of mass. The character is
     * unaffected.
     *
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     * @return a new location vector (in system coordinates)
     */
    @Override
    public RVec3 getCenterOfMassPosition(boolean lockBodies) {
        long characterVa = va();
        double[] storeDoubles = new double[3];
        getCenterOfMassPosition(characterVa, storeDoubles, lockBodies);
        RVec3 result = new RVec3(storeDoubles);

        return result;
    }

    /**
     * Return the character's object layer, using the locking body interface.
     * The character is unaffected.
     *
     * @return a layer index (&ge;0)
     */
    @Override
    public int getLayer() {
        int result = getLayer(true);
        return result;
    }

    /**
     * Return the character's object layer. The character is unaffected.
     *
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     * @return a layer index (&ge;0)
     */
    @Override
    public int getLayer(boolean lockBodies) {
        long characterVa = va();
        int result = getLayer(characterVa);

        return result;
    }

    /**
     * Copy the linear velocity of the character using the locking body
     * interface. The character is unaffected.
     *
     * @return a new velocity vector (meters per second in system coordinates)
     */
    @Override
    public Vec3 getLinearVelocity() {
        Vec3 result = getLinearVelocity(true);
        return result;
    }

    /**
     * Copy the linear velocity of the character. The character is unaffected.
     *
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     * @return a new velocity vector (meters per second in system coordinates)
     */
    @Override
    public Vec3 getLinearVelocity(boolean lockBodies) {
        long characterVa = va();
        float[] storeFloats = new float[3];
        getLinearVelocity(characterVa, storeFloats, lockBodies);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the location of the character using the locking body interface. The
     * character is unaffected.
     *
     * @return a new location vector (in system coordinates)
     */
    @Override
    public RVec3 getPosition() {
        RVec3 result = getPosition(true);
        return result;
    }

    /**
     * Copy the location of the character. The character is unaffected.
     *
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     * @return a new location vector (in system coordinates)
     */
    @Override
    public RVec3 getPosition(boolean lockBodies) {
        long characterVa = va();
        double[] storeDoubles = new double[3];
        getPosition(characterVa, storeDoubles, lockBodies);
        RVec3 result = new RVec3(storeDoubles);

        return result;
    }

    /**
     * Copy the position of the associated body using the locking body
     * interface. The character is unaffected.
     *
     * @param storeLocation the desired location (in system coordinates, not
     * null, unaffected)
     * @param storeOrientation the desired orientation (in system coordinates,
     * not null, unaffected)
     */
    @Override
    public void getPositionAndRotation(
            RVec3 storeLocation, Quat storeOrientation) {
        getPositionAndRotation(storeLocation, storeOrientation, true);
    }

    /**
     * Copy the position of the associated body. The character is unaffected.
     *
     * @param storeLocation the desired location (in system coordinates, not
     * null, unaffected)
     * @param storeOrientation the desired orientation (in system coordinates,
     * not null, unaffected)
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     */
    @Override
    public void getPositionAndRotation(
            RVec3 storeLocation, Quat storeOrientation, boolean lockBodies) {
        long characterVa = va();
        double[] storeDoubles = new double[3];
        float[] storeFloats = new float[4];
        getPositionAndRotation(
                characterVa, storeDoubles, storeFloats, lockBodies);
        storeLocation.set(storeDoubles);
        storeOrientation.set(storeFloats);
    }

    /**
     * Copy the orientation of the character using the locking body interface.
     * The character is unaffected.
     *
     * @return a new rotation quaternion (in system coordinates)
     */
    @Override
    public Quat getRotation() {
        Quat result = getRotation(true);
        return result;
    }

    /**
     * Copy the orientation of the character. The character is unaffected.
     *
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     * @return a new rotation quaternion (in system coordinates)
     */
    @Override
    public Quat getRotation(boolean lockBodies) {
        long characterVa = va();
        float[] storeFloats = new float[4];
        getRotation(characterVa, storeFloats, lockBodies);
        Quat result = new Quat(storeFloats);

        return result;
    }

    /**
     * Return a TransformedShape that represents the volume occupied by the
     * character, using the locking body interface. The character is unaffected.
     *
     * @return a new object
     */
    @Override
    public TransformedShape getTransformedShape() {
        TransformedShape result = getTransformedShape(true);
        return result;
    }

    /**
     * Return a TransformedShape that represents the volume occupied by the
     * character. The character is unaffected.
     *
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     * @return a new object
     */
    @Override
    public TransformedShape getTransformedShape(boolean lockBodies) {
        long characterVa = va();
        long resultVa = getTransformedShape(characterVa, lockBodies);
        TransformedShape result = new TransformedShape(resultVa, true);

        return result;
    }

    /**
     * Calculate the character's local-to-system coordinate transform using the
     * locking body interface. The character is unaffected.
     *
     * @return a new coordinate transform matrix
     */
    @Override
    public RMat44 getWorldTransform() {
        RMat44 result = getWorldTransform(true);
        return result;
    }

    /**
     * Calculate the character's local-to-system coordinate transform. The
     * character is unaffected.
     *
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     * @return a new transform matrix
     */
    @Override
    public RMat44 getWorldTransform(boolean lockBodies) {
        long characterVa = va();
        long matrixVa = getWorldTransform(characterVa, lockBodies);
        RMat44 result = new RMat44(matrixVa, true);

        return result;
    }

    /**
     * Create a counted reference to the native {@code Character}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public CharacterRefC toRefC() {
        long characterVa = va();
        long refVa = toRefC(characterVa);
        CharacterRefC result = new CharacterRefC(refVa, system);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static void activate(long characterVa, boolean lockBodies);

    native private static void addImpulse(
            long characterVa, float x, float y, float z, boolean lockBodies);

    native private static void addLinearVelocity(
            long characterVa, float vx, float vy, float vz, boolean lockBodies);

    native static void addToPhysicsSystem(
            long characterVa, int ordinal, boolean lockBodies);

    native private static long createCharacter(
            long settingsVa, double locX, double locY, double locZ, float qx,
            float qy, float qz, float qw, long userData, long systemVa);

    native static int getBodyId(long characterVa);

    native static void getCenterOfMassPosition(
            long characterVa, double[] storeDoubles, boolean lockBodies);

    native static int getLayer(long characterVa);

    native static void getLinearVelocity(
            long characterVa, float[] storeFloats, boolean lockBodies);

    native static void getPosition(
            long characterVa, double[] storeDoubles, boolean lockBodies);

    native static void getPositionAndRotation(long characterVa,
            double[] storeDoubles, float[] storeFloats, boolean lockBodies);

    native static void getRotation(
            long characterVa, float[] toreFloats, boolean lockBodies);

    native static long getTransformedShape(
            long characterVa, boolean lockBodies);

    native static long getWorldTransform(long characterVa, boolean lockBodies);

    native static void postSimulation(
            long characterVa, float maxSeparation, boolean lockBodies);

    native static void removeFromPhysicsSystem(
            long characterVa, boolean lockBodies);

    native private static void setLayer(
            long characterVa, int layer, boolean lockBodies);

    native private static void setLinearAndAngularVelocity(
            long characterVa, float vx, float vy, float vz,
            float wx, float wy, float wz, boolean lockBodies);

    native static void setLinearVelocity(
            long characterVa, float vx, float vy, float vz, boolean lockBodies);

    native private static void setPosition(long characterVa, double locX,
            double locY, double locZ, int ordinal, boolean lockBodies);

    native private static void setPositionAndRotation(
            long characterVa, double locX, double locY, double locZ, float qx,
            float qy, float qz, float qw, int ordinal, boolean lockBodies);

    native private static void setRotation(long characterVa, float qx, float qy,
            float qz, float qw, int ordinal, boolean lockBodies);

    native static boolean setShape(long characterVa, long shapeVa,
            float maxPenetrationDepth, boolean lockBodies);

    native private static long toRef(long characterVa);

    native private static long toRefC(long characterVa);
}
