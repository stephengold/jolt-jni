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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RMat44;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.Wheel;

/**
 * Read-only access to {@code VehicleConstraint}. (native type: const
 * VehicleConstraint)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstVehicleConstraint extends ConstConstraint {
    /**
     * Count how many anti-roll bars the vehicle has. The constraint is
     * unaffected.
     *
     * @return the count (&ge;0)
     */
    int countAntiRollBars();

    /**
     * Count how many wheels the vehicle has. The constraint is unaffected.
     *
     * @return the count (&ge;0)
     */
    int countWheels();

    /**
     * Access the specified anti-roll bar.
     *
     * @param barIndex the index of the bar to access (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstVehicleAntiRollBar getAntiRollBar(int barIndex);

    /**
     * Copy the gravity override. The constraint is unaffected.
     *
     * @return a new vector
     */
    Vec3 getGravityOverride();

    /**
     * Copy the local "forward" direction. The constraint is unaffected.
     *
     * @return a new direction vector
     */
    Vec3 getLocalForward();

    /**
     * Copy the local "up" direction. The constraint is unaffected.
     *
     * @return a new direction vector
     */
    Vec3 getLocalUp();

    /**
     * Return the vehicle's maximum pitch/roll angle. The constraint is
     * unaffected.
     *
     * @return the limit angle (in radians)
     */
    float getMaxPitchRollAngle();

    /**
     * Return the number of simulation steps between wheel-collision tests when
     * the vehicle is active. The constraint is unaffected.
     *
     * @return the number of steps
     */
    int getNumStepsBetweenCollisionTestActive();

    /**
     * Return the number of simulation steps between collision tests when the
     * body is inactive. The constraint is unaffected.
     *
     * @return the number of steps
     */
    int getNumStepsBetweenCollisionTestInactive();

    /**
     * Copy the basis vectors for the specified wheel.
     *
     * @param wheel which wheel to query (not null)
     * @param storeForward storage for the forward vector (not null, modified)
     * @param storeUp storage for the up vector (not null, modified)
     * @param storeRight storage for the right vector (not null, modified)
     */
    void getWheelLocalBasis(
            Wheel wheel, Vec3 storeForward, Vec3 storeUp, Vec3 storeRight);

    /**
     * Copy the world transform of the specified wheel. The constraint is
     * unaffected.
     *
     * @param wheelIndex the index of the wheel to query (&ge;0)
     * @param right the wheel's axis of rotation (a unit vector in the wheel's
     * model space)
     * @param up the "up" direction (a unit vector in the wheel's model space)
     * @return a new coordinate transform matrix
     */
    RMat44 getWheelWorldTransform(
            int wheelIndex, Vec3Arg right, Vec3Arg up);

    /**
     * Copy the world transform of the specified wheel. The constraint is
     * unaffected.
     *
     * @param wheelIndex the index of the wheel to query (&ge;0)
     * @param right the wheel's axis of rotation (a unit vector in the wheel's
     * model space)
     * @param up the "up" direction (a unit vector in the wheel's model space)
     * @param storePosition storage for the translation component (not null,
     * modified)
     * @param storeRotation storage for the rotation component (not null,
     * modified)
     */
    void getWheelPositionAndRotation(int wheelIndex, Vec3Arg right,
            Vec3Arg up, RVec3 storePosition, Quat storeRotation);

    /**
     * Copy the "up" direction based on gravity. The constraint is unaffected.
     *
     * @return a new direction vector (in system coordinates)
     */
    Vec3 getWorldUp();

    /**
     * Test whether gravity is overridden. The constraint is unaffected.
     *
     * @return {@code true} if overridden, otherwise {@code false}
     */
    boolean isGravityOverridden();
}
