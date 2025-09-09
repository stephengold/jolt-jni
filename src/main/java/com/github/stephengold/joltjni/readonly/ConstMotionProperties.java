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

import com.github.stephengold.joltjni.Mat44;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.UVec4;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.EMotionQuality;

/**
 * Read-only access to a {@code MotionProperties} object. (native type: const
 * MotionProperties)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstMotionProperties extends ConstJoltPhysicsObject {
    /**
     * Copy the net force acting on the body. The properties are unaffected.
     *
     * @return a new force vector (Newtons in system coordinates)
     */
    Vec3 getAccumulatedForce();

    /**
     * Copy the net torque acting on the body. The properties are unaffected.
     *
     * @return a new torque vector (Newton.meters in system coordinates)
     */
    Vec3 getAccumulatedTorque();

    /**
     * Return the allowed degrees of freedom. The properties are unaffected.
     *
     * @return logical OR of values defined in {@code EAllowedDofs}
     */
    int getAllowedDofs();

    /**
     * Test whether the body is allowed to fall asleep. The properties are
     * unaffected.
     *
     * @return {@code true} if allowed, otherwise {@code false}
     */
    boolean getAllowSleeping();

    /**
     * Return the angular damping coefficient. The properties are unaffected.
     *
     * @return the coefficient value (in units of per second, &ge;0, &le;1)
     */
    float getAngularDamping();

    /**
     * Generate a vector in which the disabled angular components are set to
     * zero and enabled ones are set to -1. The properties are unaffected.
     *
     * @return a new vector
     */
    UVec4 getAngularDofsMask();

    /**
     * Copy the angular velocity. The properties are unaffected.
     *
     * @return a new velocity vector (radians per second in system coordinates)
     */
    Vec3 getAngularVelocity();

    /**
     * Return the gravity factor. The properties are unaffected.
     *
     * @return the factor
     */
    float getGravityFactor();

    /**
     * Copy the rotation that takes the inverse-inertia diagonal to local
     * coordinates. The properties are unaffected.
     *
     * @return a new object
     */
    Quat getInertiaRotation();

    /**
     * Copy the diagonal components of the inverse inertia matrix, assuming a
     * dynamic body. The properties are unaffected.
     *
     * @return a new vector (all components &ge;0)
     */
    Vec3 getInverseInertiaDiagonal();

    /**
     * Return the body's inverse mass. The properties are unaffected.
     *
     * @return the value (&ge;0)
     */
    float getInverseMass();

    /**
     * Return the body's inverse mass. The properties are unaffected.
     *
     * @return the value (&ge;0)
     */
    float getInverseMassUnchecked();

    /**
     * Return the linear damping coefficient. The properties are unaffected.
     *
     * @return the coefficient value (in units of per second, &ge;0, &le;1)
     */
    float getLinearDamping();

    /**
     * Copy the linear velocity. The properties are unaffected.
     *
     * @return a new velocity vector (meters per second in system coordinates)
     */
    Vec3 getLinearVelocity();

    /**
     * Copy the inverse-inertia matrix. The properties are unaffected.
     *
     * @return a new object
     */
    Mat44 getLocalSpaceInverseInertia();

    /**
     * Return the maximum angular speed that the body can achieve. The
     * properties are unaffected.
     *
     * @return the speed limit (in radians per second)
     */
    float getMaxAngularVelocity();

    /**
     * Return the maximum linear speed that the body can achieve. The properties
     * are unaffected.
     *
     * @return the speed limit (in meters per second)
     */
    float getMaxLinearVelocity();

    /**
     * Return the motion quality. The properties are unaffected.
     *
     * @return an enum value (not null)
     */
    EMotionQuality getMotionQuality();

    /**
     * Return the number of position iterations used in the solver. The
     * properties are unaffected.
     *
     * @return the count (&gt;0) or 0 to use number specified in the
     * {@code PhysicsSettings}
     */
    int getNumPositionStepsOverride();

    /**
     * Return the number of velocity iterations used in the solver. The
     * properties are unaffected.
     *
     * @return the count (&gt;0) or 0 to use number specified in the
     * {@code PhysicsSettings}
     */
    int getNumVelocityStepsOverride();
}
