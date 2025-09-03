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

import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to {@code Wheel}. (native type: const Wheel)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstWheel extends ConstJoltPhysicsObject {
    /**
     * Return the wheel's angular velocity.
     *
     * @return the angular velocity (in radians per second, positive when the
     * vehicle is moving forward)
     */
    float getAngularVelocity();

    /**
     * Return the ID of the body that's supporting the wheel.
     *
     * @return the body ID
     */
    int getContactBodyId();

    /**
     * Copy the wheel's lateral (sideways) direction.
     *
     * @return a new direction vector (in system coordinates)
     */
    Vec3 getContactLateral();

    /**
     * Copy the wheel's longitudinal direction.
     *
     * @return a new direction vector (in system coordinates)
     */
    Vec3 getContactLongitudinal();

    /**
     * Copy the contact normal direction.
     *
     * @return a new direction vector (in system coordinates)
     */
    Vec3 getContactNormal();

    /**
     * Copy the velocity of the contact point.
     *
     * @return a new velocity vector (meters per second in system coordinates)
     */
    Vec3 getContactPointVelocity();

    /**
     * Copy the location of the contact point.
     *
     * @return a new location vector (in system coordinates)
     */
    RVec3 getContactPosition();

    /**
     * Return ID of the sub-shape that's supporting the vehicle.
     *
     * @return the sub-shape ID
     */
    int getContactSubShapeId();

    /**
     * Return the lateral (sideways) component of the impulse applied to the
     * wheel.
     *
     * @return the impulse component (in Newton seconds)
     */
    float getLateralLambda();

    /**
     * Return the forward component of the impulse applied to the wheel.
     *
     * @return the impulse component (in Newton seconds)
     */
    float getLongitudinalLambda();

    /**
     * Return the wheel's rotation angle.
     *
     * @return the angle (in radians)
     */
    float getRotationAngle();

    /**
     * Access the settings used to create this wheel.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstWheelSettings getSettings();

    /**
     * Return the steering angle.
     *
     * @return the leftward angle (in radians, &ge;-Pi, &le;Pi)
     */
    float getSteerAngle();

    /**
     * Return the total impulse applied to the suspension.
     *
     * @return the magnitude of the impulse (in Newton seconds)
     */
    float getSuspensionLambda();

    /**
     * Return the length of the suspension.
     *
     * @return the distance between the axle and the hard point (in meters)
     */
    float getSuspensionLength();

    /**
     * Test whether the wheel is supported.
     *
     * @return {@code true} if supported, otherwise {@code false}
     */
    boolean hasContact();

    /**
     * Test whether the suspension has hit its hard point.
     *
     * @return {@code true} if hit, otherwise {@code false}
     */
    boolean hasHitHardPoint();
}
