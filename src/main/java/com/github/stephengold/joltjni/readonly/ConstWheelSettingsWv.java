/*
Copyright (c) 2025-2026 Stephen Gold

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

/**
 * Read-only access to a {@code WheelSettingsWv}. (native type: const
 * WheelSettingsWV)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstWheelSettingsWv extends ConstWheelSettings {
    /**
     * Return the angular damping coefficient. The settings are unaffected.
     *
     * @return the coefficient (in units of per second, &ge;0)
     */
    float getAngularDamping();

    /**
     * Return the moment of inertia around the wheel's rolling axis. The
     * settings are unaffected.
     *
     * @return the moment of inertia (in kilogram.meters squared, &ge;0)
     */
    float getInertia();

    /**
     * Access the lateral friction as a function of the slip angle (in degrees).
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstLinearCurve getLateralFriction();

    /**
     * Access the longitudinal friction as a function of slip ratio.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstLinearCurve getLongitudinalFriction();

    /**
     * Return the maximum torque that the main brake can exert on the wheel. The
     * settings are unaffected.
     *
     * @return the maximum torque (in Newton meters)
     */
    float getMaxBrakeTorque();

    /**
     * Return the maximum torque that the hand brake can exert on the wheel. The
     * settings are unaffected.
     *
     * @return the maximum torque (in Newton meters)
     */
    float getMaxHandBrakeTorque();

    /**
     * Return the maximum steering angle. The settings are unaffected.
     *
     * @return the maximum steering angle (in radians)
     */
    float getMaxSteerAngle();
}
