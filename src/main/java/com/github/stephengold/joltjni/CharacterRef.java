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
import com.github.stephengold.joltjni.readonly.ConstCharacter;
import com.github.stephengold.joltjni.readonly.ConstPhysicsMaterial;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.template.Ref;

/**
 * A counted reference to a {@code Character}. (native type:
 * {@code Ref<Character>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class CharacterRef extends Ref implements ConstCharacter {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public CharacterRef() {
        long refVa = createEmpty();
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
    CharacterRef(long refVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(refVa) : null;
        setVirtualAddress(refVa, freeingAction);
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
        long characterVa = targetVa();
        long idVa = Character.getBodyId(characterVa);
        BodyId result = new BodyId(idVa, true);

        return result;
    }

    /**
     * Return the location of the rigid body's center of mass using the locking
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
     * Return the location of the rigid body's center of mass. The character is
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
        Character.getCenterOfMassPosition(characterVa, storeDoubles, lockBodies);
        RVec3 result
                = new RVec3(storeDoubles[0], storeDoubles[1], storeDoubles[2]);

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
     * @return a new ID
     */
    @Override
    public BodyId getGroundBodyId() {
        long characterVa = targetVa();
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
     * Return the normal direction at the point of contact with the supporting
     * surface. The character is unaffected.
     *
     * @return a new direction vector (in system coordinates)
     */
    @Override
    public Vec3 getGroundNormal() {
        long characterVa = targetVa();
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
        long characterVa = targetVa();
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
        long characterVa = targetVa();
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
        long characterVa = targetVa();
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
        long characterVa = targetVa();
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
        long characterVa = targetVa();
        float vx = CharacterBase.getGroundVelocityX(characterVa);
        float vy = CharacterBase.getGroundVelocityY(characterVa);
        float vz = CharacterBase.getGroundVelocityZ(characterVa);
        Vec3 result = new Vec3(vx, vy, vz);

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
        int result = Character.getLayer(characterVa);

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
        Character.getLinearVelocity(characterVa, storeFloats, lockBodies);
        Vec3 result = new Vec3(storeFloats[0], storeFloats[1], storeFloats[2]);

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
        Character.getPosition(characterVa, storeDoubles, lockBodies);
        RVec3 result
                = new RVec3(storeDoubles[0], storeDoubles[1], storeDoubles[2]);

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
        long characterVa = targetVa();
        double[] storeDoubles = new double[3];
        float[] storeFloats = new float[4];
        Character.getPositionAndRotation(
                characterVa, storeDoubles, storeFloats, lockBodies);
        storeLocation.set(storeDoubles[0], storeDoubles[1], storeDoubles[2]);
        storeOrientation.set(
                storeFloats[0], storeFloats[1], storeFloats[2], storeFloats[3]);
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
        Character.getRotation(characterVa, storeFloats, lockBodies);
        Quat result = new Quat(
                storeFloats[0], storeFloats[1], storeFloats[2], storeFloats[3]);

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
     * Return the character's "up" direction. The character is unaffected.
     *
     * @return a new direction vector
     */
    @Override
    public Vec3 getUp() {
        long characterVa = targetVa();
        float x = CharacterBase.getUpX(characterVa);
        float y = CharacterBase.getUpY(characterVa);
        float z = CharacterBase.getUpZ(characterVa);
        Vec3 result = new Vec3(x, y, z);

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
        long matrixVa = Character.getWorldTransform(characterVa, lockBodies);
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
     * Save the character's state to the specified recorder.
     *
     * @param recorder the recorder to save to (not null)
     */
    @Override
    public void saveState(StateRecorder recorder) {
        long characterVa = targetVa();
        long recorderVa = recorder.va();
        CharacterBase.saveState(characterVa, recorderVa);
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
        long settingsVa = targetVa();
        com.github.stephengold.joltjni.Character result
                = new com.github.stephengold.joltjni.Character(settingsVa);

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
        CharacterRef result = new CharacterRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long copy(long refVa);

    native private static long createEmpty();

    native private static void free(long refVa);

    native private static long getPtr(long refVa);
}
