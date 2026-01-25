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
 * Read-only access to a {@code MotorSettings} object. (native type: const
 * MotorSettings)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstMotorSettings extends ConstJoltPhysicsObject {
    /**
     * Access the underlying {@code Constraint}, if any. The settings are
     * unaffected.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    ConstConstraint getConstraint();

    /**
     * Access the underlying {@code ConstraintSettings}, if any. The settings
     * are unaffected.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    ConstConstraintSettings getConstraintSettings();

    /**
     * Return the maximum force to apply in a linear constraint. Ignored in an
     * angular motor. The settings are unaffected.
     *
     * @return the amount of force (in Newtons, typically positive)
     */
    float getMaxForceLimit();

    /**
     * Return the maximum torque to apply in an angular constraint. Ignored in a
     * linear motor. The settings are unaffected.
     *
     * @return the amount of torque (in Newton meters, typically positive)
     */
    float getMaxTorqueLimit();

    /**
     * Return the minimum force to apply in a linear constraint. Ignored in an
     * angular motor. The settings are unaffected.
     *
     * @return the amount of force (in Newtons, typically negative)
     */
    float getMinForceLimit();

    /**
     * Return the minimum torque to apply in an angular constraint. Ignored in a
     * linear motor. The settings are unaffected.
     *
     * @return the amount of torque (in Newton meters, typically negative)
     */
    float getMinTorqueLimit();

    /**
     * Access the settings for the spring used to drive to the position target.
     * Ignored in a velocity motor. The motor settings are unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstSpringSettings getSpringSettings();

    /**
     * Test whether the settings are valid. They are unaffected.
     *
     * @return {@code true} if valid, otherwise {@code false}
     */
    boolean isValid();
}
