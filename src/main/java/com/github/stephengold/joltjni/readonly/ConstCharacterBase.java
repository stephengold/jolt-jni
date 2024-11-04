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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.BodyId;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.StateRecorder;
import com.github.stephengold.joltjni.SubShapeId;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.EGroundState;

/**
 * Read-only access to a {@code CharacterBase}. (native type: const
 * CharacterBase)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstCharacterBase extends ConstJoltPhysicsObject {
    // *************************************************************************
    // new methods exposed

    /**
     * Return the maximum slope the character can walk on. The character is
     * unaffected.
     *
     * @return the cosine of the slope angle
     */
    float getCosMaxSlopeAngle();

    /**
     * Return the body ID of the supporting surface. The character is
     * unaffected.
     *
     * @return a new ID
     */
    BodyId getGroundBodyId();

    /**
     * Access the material of the supporting surface. The character is
     * unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * else {@code null}
     */
    ConstPhysicsMaterial getGroundMaterial();

    /**
     * Return the normal direction at the point of contact with the supporting
     * surface. The character is unaffected.
     *
     * @return a new direction vector (in system coordinates)
     */
    Vec3 getGroundNormal();

    /**
     * Return the location of the point of contact with the supporting surface.
     * The character is unaffected.
     *
     * @return a new location vector (in system coordinates)
     */
    RVec3 getGroundPosition();

    /**
     * Return the relationship between the character and its supporting surface.
     * The character is unaffected.
     *
     * @return an enum value (not null)
     */
    EGroundState getGroundState();

    /**
     * Identify the face on the supporting surface where contact is occurring.
     * The character is unaffected.
     *
     * @return a new ID
     */
    SubShapeId getGroundSubShapeId();

    /**
     * Return the user data of the supporting surface. The character is
     * unaffected.
     *
     * @return the 64-bit value
     */
    long getGroundUserData();

    /**
     * Return the world-space velocity of the supporting surface. The character
     * is unaffected.
     *
     * @return a new velocity vector (meters per second in system coordinates)
     */
    Vec3 getGroundVelocity();

    /**
     * Access the character's shape. The character is unaffected.
     *
     * @return a new immutable JVM object with the pre-existing native object
     * assigned, or {@code null} if none
     */
    ConstShape getShape();

    /**
     * Return the character's "up" direction. The character is unaffected.
     *
     * @return a new direction vector
     */
    Vec3 getUp();

    /**
     * Test whether the specified normal direction is too steep. The character
     * is unaffected.
     *
     * @param normal the surface normal to test (not null, unaffected)
     * @return {@code true} if too steep, otherwise {@code false}
     */
    boolean isSlopeTooSteep(Vec3Arg normal);

    /**
     * Test whether the character is supported. The character is unaffected.
     *
     * @return {@code true} if supported, otherwise {@code false}
     */
    boolean isSupported();

    /**
     * Save the character's state to the specified recorder.
     *
     * @param recorder the recorder to save to (not null)
     */
    void saveState(StateRecorder recorder);
}
