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

import com.github.stephengold.joltjni.MassProperties;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.EMotionQuality;
import com.github.stephengold.joltjni.enumerate.EMotionType;
import com.github.stephengold.joltjni.enumerate.EOverrideMassProperties;

/**
 * Read-only access to a {@code BodyCreationSettings} object. (native type:
 * const BodyCreationSettings)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstBodyCreationSettings extends ConstJoltPhysicsObject {
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether a static body can be converted to kinematic or dynamic. The
     * settings are unaffected.
     *
     * @return {@code true} if convertible, otherwise {@code false}
     */
    boolean getAllowDynamicOrKinematic();

    /**
     * Return the body's degrees of freedom. The settings are unaffected.
     *
     * @return a bitmask (see {@code EAllowedDofs} for semantics)
     */
    int getAllowedDofs();

    /**
     * Test whether the created body will be allowed to fall asleep. The
     * settings are unaffected.
     *
     * @return {@code true} if allowed, otherwise {@code false}
     */
    boolean getAllowSleeping();

    /**
     * Return the angular damping constant. The settings are unaffected.
     *
     * @return the constant (in units of per second, &ge;0, &le;1)
     */
    float getAngularDamping();

    /**
     * Copy the (initial) angular velocity. The settings are unaffected.
     *
     * @return a new velocity vector (radians per second in system coordinates)
     */
    Vec3 getAngularVelocity();

    /**
     * Test whether the gyroscopic force will be applied. The settings are
     * unaffected.
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    boolean getApplyGyroscopicForce();

    /**
     * Access the collision group.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstCollisionGroup getCollisionGroup();

    /**
     * Test whether extra effort should be made to remove ghost contacts. The
     * settings are unaffected.
     *
     * @return {@code true} for extra effort, otherwise {@code false}
     */
    boolean getEnhancedInternalEdgeRemoval();

    /**
     * Return the friction ratio. The settings are unaffected.
     *
     * @return the ratio (typically &ge;0 and &le;1)
     */
    float getFriction();

    /**
     * Return the gravity factor. The settings are unaffected.
     *
     * @return the factor
     */
    float getGravityFactor();

    /**
     * Test whether the body will be a sensor. The settings are unaffected.
     *
     * @return {@code true} for a sensor, otherwise {@code false}
     */
    boolean getIsSensor();

    /**
     * Return the linear damping constant. The settings are unaffected.
     *
     * @return the constant (in units of per second, &ge;0, &le;1)
     */
    float getLinearDamping();

    /**
     * Copy the (initial) linear velocity. The settings are unaffected.
     *
     * @return a new velocity vector (meters per second in system coordinates)
     */
    Vec3 getLinearVelocity();

    /**
     * Calculate the mass and inertia. The settings are unaffected.
     *
     * @return a new JVM object with a new native object assigned
     */
    MassProperties getMassProperties();

    /**
     * Access the mass-properties override.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstMassProperties getMassPropertiesOverride();

    /**
     * Return the maximum angular speed. The settings are unaffected.
     *
     * @return the maximum speed (in radians per second)
     */
    float getMaxAngularVelocity();

    /**
     * Return the maximum linear speed. The settings are unaffected.
     *
     * @return the maximum speed (in meters per second)
     */
    float getMaxLinearVelocity();

    /**
     * Return the motion quality. The settings are unaffected.
     *
     * @return an enum value (not null)
     */
    EMotionQuality getMotionQuality();

    /**
     * Return the motion type. The settings are unaffected.
     *
     * @return an enum value (not null)
     */
    EMotionType getMotionType();

    /**
     * Return the index of the object layer. The settings are unaffected.
     *
     * @return the layer index (&ge;0, &lt;numObjectLayers)
     */
    int getObjectLayer();

    /**
     * Return how the mass-properties override will be used. The settings are
     * unaffected.
     *
     * @return an enum value (not null)
     */
    EOverrideMassProperties getOverrideMassProperties();

    /**
     * Copy the (initial) location. The settings are unaffected.
     *
     * @return a new location vector (in system coordinates, all components
     * finite)
     */
    RVec3 getPosition();

    /**
     * Return the restitution ratio. The settings are unaffected.
     *
     * @return the ratio (typically &ge;0 and &le;1)
     */
    float getRestitution();

    /**
     * Copy the (initial) orientation of the body's axes. The settings are
     * unaffected.
     *
     * @return a new rotation quaternion (relative to the system axes)
     */
    Quat getRotation();

    /**
     * Acquire read-only access to the {@code Shape}. The settings are
     * unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null}
     */
    ConstShape getShape();

    /**
     * Test whether the body's mass properties will be calculated. The settings
     * are unaffected.
     *
     * @return {@code true} if calculated, otherwise {@code false}
     */
    boolean hasMassProperties();
}
