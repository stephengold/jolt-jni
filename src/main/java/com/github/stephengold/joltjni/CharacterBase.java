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

import com.github.stephengold.joltjni.enumerate.EGroundState;
import com.github.stephengold.joltjni.readonly.ConstCharacterBase;
import com.github.stephengold.joltjni.readonly.ConstPhysicsMaterial;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.template.RefTarget;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

/**
 * Base class to represent a player navigating a {@code PhysicsSystem}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class CharacterBase
        extends NonCopyable
        implements ConstCharacterBase, RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a character with no native object assigned.
     */
    CharacterBase() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Restore the character's state from the specified recorder.
     *
     * @param recorder the recorder to restore from (not null)
     */
    public void restoreState(StateRecorder recorder) {
        long characterVa = va();
        long recorderVa = recorder.va();
        restoreState(characterVa, recorderVa);
    }

    /**
     * Alter the maximum slope that the character can walk on.
     *
     * @param angle the desired slope angle (in radians, default=5*Pi/18)
     */
    public void setMaxSlopeAngle(float angle) {
        long characterVa = va();
        setMaxSlopeAngle(characterVa, angle);
    }

    /**
     * Alter the character's "up" direction.
     *
     * @param up the desired direction (not null, unaffected, default=(0,1,0))
     */
    public void setUp(Vec3Arg up) {
        long characterVa = va();
        float x = up.getX();
        float y = up.getY();
        float z = up.getZ();
        setUp(characterVa, x, y, z);
    }
    // *************************************************************************
    // ConstCharacterBase methods

    /**
     * Return the maximum slope that the character can walk on. The character is
     * unaffected.
     *
     * @return the cosine of the slope angle
     */
    @Override
    public float getCosMaxSlopeAngle() {
        long characterVa = va();
        float result = getCosMaxSlopeAngle(characterVa);

        return result;
    }

    /**
     * Return the body ID of the supporting surface. The character is
     * unaffected. (native method: GetGroundBodyID)
     *
     * @return the {@code BodyID} value
     */
    @Override
    public int getGroundBodyId() {
        long characterVa = va();
        int result = getGroundBodyId(characterVa);

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
        long characterVa = va();
        long materialVa = getGroundMaterial(characterVa);
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
        long characterVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getGroundNormal(characterVa, storeFloats);
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
        long characterVa = va();
        DoubleBuffer storeDoubles = Temporaries.doubleBuffer1.get();
        getGroundPosition(characterVa, storeDoubles);
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
        long characterVa = va();
        int ordinal = getGroundState(characterVa);
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
        long characterVa = va();
        int result = getGroundSubShapeId(characterVa);

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
        long characterVa = va();
        long result = getGroundUserData(characterVa);

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
        long characterVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getGroundVelocity(characterVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

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
        long characterVa = va();
        long shapeVa = getShape(characterVa);
        ConstShape result = Shape.newShape(shapeVa);

        return result;
    }

    /**
     * Copy the character's "up" direction. The character is unaffected.
     *
     * @return a new direction vector
     */
    @Override
    public Vec3 getUp() {
        long characterVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getUp(characterVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

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
        long characterVa = va();
        float nx = normal.getX();
        float ny = normal.getY();
        float nz = normal.getZ();
        boolean result = isSlopeTooSteep(characterVa, nx, ny, nz);

        return result;
    }

    /**
     * Test whether the character is supported. The character is unaffected.
     *
     * @return {@code true} if supported, otherwise {@code false}
     */
    @Override
    public boolean isSupported() {
        long characterVa = va();
        boolean result = isSupported(characterVa);

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
        long characterVa = va();
        long recorderVa = recorder.va();
        saveState(characterVa, recorderVa);
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code CharacterBase}. The
     * character is unaffected.
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
     * Mark the native {@code CharacterBase} as embedded.
     */
    @Override
    public void setEmbedded() {
        long characterVa = va();
        setEmbedded(characterVa);
    }
    // *************************************************************************
    // native methods

    native static float getCosMaxSlopeAngle(long characterVa);

    native static int getGroundBodyId(long characterVa);

    native static long getGroundMaterial(long characterVa);

    native static void getGroundNormal(
            long characterVa, FloatBuffer storeFloats);

    native static void getGroundPosition(
            long characterVa, DoubleBuffer storeDoubles);

    native static int getGroundState(long characterVa);

    native static int getGroundSubShapeId(long characterVa);

    native static long getGroundUserData(long characterVa);

    native static void getGroundVelocity(
            long characterVa, FloatBuffer storeFloats);

    native static int getRefCount(long characterVa);

    native static long getShape(long characterVa);

    native static void getUp(long characterVa, FloatBuffer storeFloats);

    native static boolean isSlopeTooSteep(
            long characterVa, float nx, float ny, float nz);

    native static boolean isSupported(long characterVa);

    native static void restoreState(long characterVa, long recorderVa);

    native static void saveState(long characterVa, long recorderVa);

    native private static void setEmbedded(long characterVa);

    native private static void setMaxSlopeAngle(long characterVa, float angle);

    native static void setUp(long characterVa, float x, float y, float z);
}
