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
import com.github.stephengold.joltjni.enumerate.EGroundState;
import com.github.stephengold.joltjni.readonly.ConstCharacter;
import com.github.stephengold.joltjni.readonly.ConstPhysicsMaterial;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.template.Ref;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

/**
 * A counted reference to a {@code Character}. (native type:
 * {@code Ref<Character>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class CharacterRef extends Ref implements ConstCharacter {
    // *************************************************************************
    // fields

    /**
     * where to add the body (may be {@code null})
     */
    final private PhysicsSystem system;
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public CharacterRef() {
        this.system = null;
        long refVa = createDefault();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param physicsSystem where to add the body
     */
    CharacterRef(long refVa, PhysicsSystem physicsSystem) {
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
        long characterVa = targetVa();
        int ordinal = activation.ordinal();
        com.github.stephengold.joltjni.Character.addToPhysicsSystem(
                characterVa, ordinal, lockBodies);
    }

    /**
     * Needs to be invoked after every physics update.
     *
     * @param maxSeparation the max distance between the floor and the character
     * for standing
     */
    public void postSimulation(float maxSeparation) {
        postSimulation(maxSeparation, true);
    }

    /**
     * Needs to be invoked after every physics update.
     *
     * @param maxSeparation the max distance between the floor and the character
     * for standing
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     */
    public void postSimulation(float maxSeparation, boolean lockBodies) {
        long characterVa = targetVa();
        com.github.stephengold.joltjni.Character.postSimulation(
                characterVa, maxSeparation, lockBodies);
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
        long characterVa = targetVa();
        com.github.stephengold.joltjni.Character.removeFromPhysicsSystem(
                characterVa, lockBodies);
    }

    /**
     * Restore the character's state from the specified recorder.
     *
     * @param recorder the recorder to restore from (not null)
     */
    public void restoreState(StateRecorder recorder) {
        long characterVa = targetVa();
        long recorderVa = recorder.va();
        CharacterBase.restoreState(characterVa, recorderVa);
    }

    /**
     * Alter the character's linear velocity using the locking body interface.
     *
     * @param vx the X component of the desired velocity (in meters per second,
     * default=0)
     * @param vy the Y component of the desired velocity (in meters per second,
     * default=0)
     * @param vz the Z component of the desired velocity (in meters per second,
     * default=0)
     */
    public void setLinearVelocity(float vx, float vy, float vz) {
        setLinearVelocity(vx, vy, vz, true);
    }

    /**
     * Alter the character's linear velocity.
     *
     * @param vx the X component of the desired velocity (in meters per second,
     * default=0)
     * @param vy the Y component of the desired velocity (in meters per second,
     * default=0)
     * @param vz the Z component of the desired velocity (in meters per second,
     * default=0)
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     */
    public void setLinearVelocity(
            float vx, float vy, float vz, boolean lockBodies) {
        long characterVa = targetVa();
        com.github.stephengold.joltjni.Character.setLinearVelocity(
                characterVa, vx, vy, vz, lockBodies);
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
        float vx = velocity.getX();
        float vy = velocity.getY();
        float vz = velocity.getZ();
        setLinearVelocity(vx, vy, vz, lockBodies);
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
        long characterVa = targetVa();
        long shapeVa = shape.targetVa();
        boolean result = com.github.stephengold.joltjni.Character.setShape(
                characterVa, shapeVa, maxPenetrationDepth, lockBodies);

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
        long characterVa = targetVa();
        int result = com.github.stephengold.joltjni.Character.getBodyId(
                characterVa);

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
        long characterVa = targetVa();
        double[] storeDoubles = new double[3];
        com.github.stephengold.joltjni.Character.getCenterOfMassPosition(
                characterVa, storeDoubles, lockBodies);
        RVec3 result = new RVec3(storeDoubles);

        return result;
    }

    /**
     * Generate settings to reconstruct the character, using the locking body
     * interface. The character is unaffected.
     *
     * @return a new object
     */
    @Override
    public CharacterSettings getCharacterSettings() {
        CharacterSettings result = getCharacterSettings(true);
        return result;
    }

    /**
     * Generate settings to reconstruct the character. The character is
     * unaffected.
     *
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * @return a new object
     */
    @Override
    public CharacterSettings getCharacterSettings(boolean lockBodies) {
        long characterVa = targetVa();
        long settingsVa
                = Character.getCharacterSettings(characterVa, lockBodies);
        CharacterSettings result = new CharacterSettings(settingsVa);

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
     * @return a {@code SubShapeId} value
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
        long characterVa = targetVa();
        int result = com.github.stephengold.joltjni.Character.getLayer(
                characterVa);

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
        long characterVa = targetVa();
        float[] storeFloats = new float[3];
        com.github.stephengold.joltjni.Character.getLinearVelocity(
                characterVa, storeFloats, lockBodies);
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
        long characterVa = targetVa();
        double[] storeDoubles = new double[3];
        com.github.stephengold.joltjni.Character.getPosition(
                characterVa, storeDoubles, lockBodies);
        RVec3 result = new RVec3(storeDoubles);

        return result;
    }

    /**
     * Copy the position of the associated body using the locking body
     * interface. The character is unaffected.
     *
     * @param storeLocation storage for the location (in system coordinates, not
     * null, modified)
     * @param storeOrientation storage for the orientation (in system
     * coordinates, not null, modified)
     */
    @Override
    public void getPositionAndRotation(
            RVec3 storeLocation, Quat storeOrientation) {
        getPositionAndRotation(storeLocation, storeOrientation, true);
    }

    /**
     * Copy the position of the associated body. The character is unaffected.
     *
     * @param storeLocation storage for the location (in system coordinates, not
     * null, modified)
     * @param storeOrientation storage for the orientation (in system
     * coordinates, not null, modified)
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     */
    @Override
    public void getPositionAndRotation(
            RVec3 storeLocation, Quat storeOrientation, boolean lockBodies) {
        long characterVa = targetVa();
        double[] storeDoubles = new double[3];
        float[] storeFloats = new float[4];
        com.github.stephengold.joltjni.Character.getPositionAndRotation(
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
        long characterVa = targetVa();
        float[] storeFloats = new float[4];
        com.github.stephengold.joltjni.Character.getRotation(
                characterVa, storeFloats, lockBodies);
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
     * Generate a TransformedShape that represents the volume occupied by the
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
     * Generate a TransformedShape that represents the volume occupied by the
     * character. The character is unaffected.
     *
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     * @return a new object
     */
    @Override
    public TransformedShape getTransformedShape(boolean lockBodies) {
        long characterVa = targetVa();
        long resultVa = Character.getTransformedShape(characterVa, lockBodies);
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
        long characterVa = targetVa();
        long matrixVa
                = com.github.stephengold.joltjni.Character.getWorldTransform(
                        characterVa, lockBodies);
        RMat44 result = new RMat44(matrixVa, true);

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
     * Create another counted reference to the native {@code Character}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public CharacterRefC toRefC() {
        long refVa = va();
        long copyVa = toRefC(refVa);
        CharacterRefC result = new CharacterRefC(copyVa, system);

        return result;
    }
    // *************************************************************************
    // Ref methods

    /**
     * Temporarily access the referenced {@code Character}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public com.github.stephengold.joltjni.Character getPtr() {
        long characterVa = targetVa();
        com.github.stephengold.joltjni.Character result
                = new com.github.stephengold.joltjni.Character(
                        characterVa, system);

        return result;
    }

    /**
     * Return the address of the native {@code Character}. No objects are
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
     * Create another counted reference to the native {@code Character}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public CharacterRef toRef() {
        long refVa = va();
        long copyVa = copy(refVa);
        CharacterRef result = new CharacterRef(copyVa, system);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long copy(long refVa);

    native private static long createDefault();

    native private static void free(long refVa);

    native private static void freeWithSystem(long refVa, PhysicsSystem unused);

    native private static long getPtr(long refVa);

    native private static long toRefC(long refVa);
}
