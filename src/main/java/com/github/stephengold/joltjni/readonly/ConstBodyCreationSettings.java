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

import com.github.stephengold.joltjni.EMotionQuality;
import com.github.stephengold.joltjni.EMotionType;
import com.github.stephengold.joltjni.EOverrideMassProperties;
import com.github.stephengold.joltjni.MassProperties;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to a {@code BodyCreationSettings} object.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstBodyCreationSettings extends ConstJoltPhysicsObject {
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether the created body will be allowed to fall asleep. The
     * settings are unaffected. (native field: mAllowSleeping)
     *
     * @return true if allowed, otherwise false
     */
    boolean getAllowSleeping();

    /**
     * Return the angular damping constant. The settings are unaffected. (native
     * field: mAngularDamping)
     *
     * @return the constant (in units of 1/second, &ge;0, &le;1)
     */
    float getAngularDamping();

    /**
     * Copy the (initial) angular velocity. The settings are unaffected. (native
     * field: mAngularVelocity)
     *
     * @return a new velocity vector (radians per second in physics-system
     * coordinates)
     */
    Vec3 getAngularVelocity();

    /**
     * Return the friction ratio. The settings are unaffected. (native field:
     * mFriction)
     *
     * @return the ratio (typically &ge;0 and &le;1)
     */
    float getFriction();

    /**
     * Return the gravity factor. The settings are unaffected. (native field:
     * mGravityFactor)
     *
     * @return the factor
     */
    float getGravityFactor();

    /**
     * Return the linear damping constant. The settings are unaffected. (native
     * field: mLinearDamping)
     *
     * @return the constant (in units of 1/second, &ge;0, &le;1)
     */
    float getLinearDamping();

    /**
     * Copy the (initial) linear velocity. The settings are unaffected. (native
     * field: mLinearVelocity)
     *
     * @return a new velocity vector (meters per second in physics-system
     * coordinates)
     */
    Vec3 getLinearVelocity();

    /**
     * Calculate the mass and inertia. The settings are unaffected.
     *
     * @return a new JVM object with a new native object assigned
     */
    MassProperties getMassProperties();

    /**
     * Copy the mass-properties override. The settings are unaffected. (native
     * field: mMassPropertiesOverride)
     *
     * @return a new JVM object with a new native object assigned
     */
    MassProperties getMassPropertiesOverride();

    /**
     * Return the maximum angular speed. The settings are unaffected. (native
     * field: mMaxAngularVelocity)
     *
     * @return the maximum speed (in radians/second)
     */
    float getMaxAngularVelocity();

    /**
     * Return the maximum linear speed. The settings are unaffected. (native
     * field: mMaxLinearVelocity)
     *
     * @return the maximum speed (in meters/second)
     */
    float getMaxLinearVelocity();

    /**
     * Return the motion quality. The settings are unaffected. (native field:
     * mMotionQuality)
     *
     * @return an enum value (not null)
     */
    EMotionQuality getMotionQuality();

    /**
     * Return the motion type. The settings are unaffected. (native field:
     * mMotionType)
     *
     * @return an enum value (not null)
     */
    EMotionType getMotionType();

    /**
     * Return the index of the object layer. The settings are unaffected.
     * (native field: mObjectLayer)
     *
     * @return the layer index (&ge;0, &lt;numObjectLayers)
     */
    int getObjectLayer();

    /**
     * Return how the mass-properties override will be used. (native field:
     * mOverrideMassProperties)
     *
     * @return an enum value (not null)
     */
    EOverrideMassProperties getOverrideMassProperties();

    /**
     * Return the (initial) location. The settings are unaffected. (native
     * field: mPosition)
     *
     * @return a new location vector (in physics-system coordinates, all
     * components finite)
     */
    RVec3 getPosition();

    /**
     * Return the restitution ratio. The settings are unaffected. (native field:
     * mRestitution)
     *
     * @return the ratio (typically &ge;0 and &le;1)
     */
    float getRestitution();

    /**
     * Copy the (initial) orientation of the body's axes. The settings are
     * unaffected. (native field: mRotation)
     *
     * @return a new rotation quaternion (relative to the physics-system axes)
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
     * @return true if calculated, otherwise false
     */
    boolean hasMassProperties();
}
