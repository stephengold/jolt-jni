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
import com.github.stephengold.joltjni.readonly.ConstCharacterBase;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Base class to represent a player navigating a {@code PhysicsSystem}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CharacterBase extends NonCopyable implements ConstCharacterBase {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a character with no native object assigned.
     */
    CharacterBase() {
    }

    /**
     * Instantiate a character with the specified native object assigned but not
     * owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    CharacterBase(long settingsVa) {
        super(settingsVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the maximum slope that character can walk on.
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
     * Return the maximum slope the character can walk on. The character is
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
     * unaffected.
     *
     * @return a new ID
     */
    @Override
    public BodyId getGroundBodyId() {
        long characterVa = va();
        long idVa = getGroundBodyId(characterVa);
        BodyId result = new BodyId(idVa, true);

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
        long characterVa = va();
        float nx = getGroundNormalX(characterVa);
        float ny = getGroundNormalY(characterVa);
        float nz = getGroundNormalZ(characterVa);
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
        long characterVa = va();
        double xx = getGroundPositionX(characterVa);
        double yy = getGroundPositionY(characterVa);
        double zz = getGroundPositionZ(characterVa);
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
        long characterVa = va();
        int ordinal = getGroundState(characterVa);
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
        long characterVa = va();
        long idVa = getGroundSubShapeId(characterVa);
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
        long characterVa = va();
        long result = getGroundUserData(characterVa);

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
        long characterVa = va();
        float vx = getGroundVelocityX(characterVa);
        float vy = getGroundVelocityY(characterVa);
        float vz = getGroundVelocityZ(characterVa);
        Vec3 result = new Vec3(vx, vy, vz);

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
     * Return the character's "up" direction. The character is unaffected.
     *
     * @return a new direction vector
     */
    @Override
    public Vec3 getUp() {
        long characterVa = va();
        float x = getUpX(characterVa);
        float y = getUpY(characterVa);
        float z = getUpZ(characterVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Test whether the specified normal direction is too steep. The character
     * is unaffected.
     *
     * @param normal the surface normal to test (not null, unaffected)
     * @return true if too steep, otherwise false
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
     * @return true if supported, otherwise false
     */
    @Override
    public boolean isSupported() {
        long characterVa = va();
        boolean result = isSupported(characterVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static float getCosMaxSlopeAngle(long characterVa);

    native private static long getGroundBodyId(long characterVa);

    native private static float getGroundNormalX(long characterVa);

    native private static float getGroundNormalY(long characterVa);

    native private static float getGroundNormalZ(long characterVa);

    native private static double getGroundPositionX(long characterVa);

    native private static double getGroundPositionY(long characterVa);

    native private static double getGroundPositionZ(long characterVa);

    native private static int getGroundState(long characterVa);

    native private static long getGroundSubShapeId(long characterVa);

    native private static long getGroundUserData(long characterVa);

    native private static float getGroundVelocityX(long characterVa);

    native private static float getGroundVelocityY(long characterVa);

    native private static float getGroundVelocityZ(long characterVa);

    native private static long getShape(long characterVa);

    native private static float getUpX(long characterVa);

    native private static float getUpY(long characterVa);

    native private static float getUpZ(long characterVa);

    native private static boolean isSlopeTooSteep(
            long characterVa, float nx, float ny, float nz);

    native private static boolean isSupported(long characterVa);

    native private static void setMaxSlopeAngle(long characterVa, float angle);

    native private static void setUp(
            long characterVa, float x, float y, float z);
}
