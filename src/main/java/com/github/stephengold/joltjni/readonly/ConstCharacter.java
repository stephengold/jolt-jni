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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.CharacterRefC;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RMat44;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.TransformedShape;
import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to a {@code Character}. (native type: const Character)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstCharacter extends ConstCharacterBase {
    // *************************************************************************
    // new methods exposed

    /**
     * Return the ID of the body associated with this character. The character
     * is unaffected.
     *
     * @return the ID value
     */
    int getBodyId();

    /**
     * Copy the location of the rigid body's center of mass using the locking
     * body interface. The character is unaffected.
     *
     * @return a new location vector (in system coordinates)
     */
    RVec3 getCenterOfMassPosition();

    /**
     * Copy the location of the rigid body's center of mass. The character is
     * unaffected.
     *
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     * @return a new location vector (in system coordinates)
     */
    RVec3 getCenterOfMassPosition(boolean lockBodies);

    /**
     * Return the character's object layer, using the locking body interface.
     * The character is unaffected.
     *
     * @return a layer index (&ge;0)
     */
    int getLayer();

    /**
     * Return the character's object layer. The character is unaffected.
     *
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     * @return a layer index (&ge;0)
     */
    int getLayer(boolean lockBodies);

    /**
     * Copy the linear velocity of the character using the locking body
     * interface. The character is unaffected.
     *
     * @return a new velocity vector (meters per second in system coordinates)
     */
    Vec3 getLinearVelocity();

    /**
     * Copy the linear velocity of the character. The character is unaffected.
     *
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     * @return a new velocity vector (meters per second in system coordinates)
     */
    Vec3 getLinearVelocity(boolean lockBodies);

    /**
     * Copy the location of the character using the locking body interface. The
     * character is unaffected.
     *
     * @return a new location vector (in system coordinates)
     */
    RVec3 getPosition();

    /**
     * Copy the location of the character. The character is unaffected.
     *
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     * @return a new location vector (in system coordinates)
     */
    RVec3 getPosition(boolean lockBodies);

    /**
     * Copy the position of the associated body using the locking body
     * interface. The character is unaffected.
     *
     * @param storeLocation the desired location (in system coordinates, not
     * null, unaffected)
     * @param storeOrientation the desired orientation (in system coordinates,
     * not null, unaffected)
     */
    void getPositionAndRotation(RVec3 storeLocation, Quat storeOrientation);

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
    void getPositionAndRotation(
            RVec3 storeLocation, Quat storeOrientation, boolean lockBodies);

    /**
     * Copy the orientation of the character using the locking body interface.
     * The character is unaffected.
     *
     * @return a new rotation quaternion (in system coordinates)
     */
    Quat getRotation();

    /**
     * Copy the orientation of the character. The character is unaffected.
     *
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     * @return a new rotation quaternion (in system coordinates)
     */
    Quat getRotation(boolean lockBodies);

    /**
     * Return a TransformedShape that represents the volume occupied by the
     * character, using the locking body interface. The character is unaffected.
     *
     * @return a new object
     */
    TransformedShape getTransformedShape();

    /**
     * Return a TransformedShape that represents the volume occupied by the
     * character. The character is unaffected.
     *
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     * @return a new object
     */
    TransformedShape getTransformedShape(boolean lockBodies);

    /**
     * Calculate the character's local-to-system coordinate transform using the
     * locking body interface. The character is unaffected.
     *
     * @return a new coordinate transform matrix
     */
    RMat44 getWorldTransform();

    /**
     * Calculate the character's local-to-system coordinate transform. The
     * character is unaffected.
     *
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     * @return a new transform matrix
     */
    RMat44 getWorldTransform(boolean lockBodies);

    /**
     * Create a counted reference to the native {@code Character}.
     *
     * @return a new JVM object with a new native object assigned
     */
    CharacterRefC toRefC();
}
