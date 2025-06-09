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
import com.github.stephengold.joltjni.StreamOut;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.EMotionQuality;
import com.github.stephengold.joltjni.enumerate.EMotionType;
import com.github.stephengold.joltjni.enumerate.EOverrideMassProperties;
import com.github.stephengold.joltjni.streamutils.GroupFilterToIdMap;
import com.github.stephengold.joltjni.streamutils.MaterialToIdMap;
import com.github.stephengold.joltjni.streamutils.ShapeToIdMap;

/**
 * Read-only access to a {@code BodyCreationSettings} object. (native type:
 * const BodyCreationSettings)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstBodyCreationSettings extends ConstJoltPhysicsObject {
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
     * Test whether a kinematic body will collide with kinematic/static bodies.
     *
     * @return {@code true} if it will collide, otherwise {@code false}
     */
    boolean getCollideKinematicVsNonDynamic();

    /**
     * Access the collision group to which the body will belong.
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
     * Return the multiplier for inertia calculations.
     *
     * @return the factor
     */
    float getInertiaMultiplier();

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
     * Calculate the body's mass and inertia. As a side effect, this method may
     * "cook" the settings.
     *
     * @return a new JVM object with a new native object assigned, or
     * {@code null} if a shape is required but not available
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
     * Return the override for the number position iterations in the solver.
     * Applicable only to a dynamic colliding body.
     *
     * @return the number (0 &rarr; use default in {@code PhysicsSettings})
     */
    int getNumPositionStepsOverride();

    /**
     * Return the override for the number position iterations in the solver.
     * Applicable only to a dynamic colliding body.
     *
     * @return the number (0 &rarr; use default in {@code PhysicsSettings})
     */
    int getNumVelocityStepsOverride();

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
     * Acquire read-only access to the shape. As a side effect, if the
     * body-creation settings aren't already cooked, this method cooks them.
     *
     * @return a new JVM object, or {@code null} if the shape settings are
     * {@code null} and the body-creation settings aren't cooked
     */
    ConstShape getShape();

    /**
     * Acquire read-only access to the {@code ShapeSettings}. The body-creation
     * settings are unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null}
     */
    ConstShapeSettings getShapeSettings();

    /**
     * Test whether manifold reduction will be enabled. The settings are
     * unaffected. (member data: mUseManifoldReduction)
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    boolean getUseManifoldReduction();

    /**
     * Return the body's user data: can be used for anything. The settings are
     * unaffected. (member data: mUserData)
     *
     * @return the value
     */
    long getUserData();

    /**
     * Test whether mass properties are required. The settings are unaffected.
     *
     * @return {@code true} if required, otherwise {@code false}
     */
    boolean hasMassProperties();

    /**
     * Write the state of this object to the specified stream, excluding the
     * shape, materials, and group filter. The settings are unaffected.
     *
     * @param stream where to write objects (not null)
     */
    void saveBinaryState(StreamOut stream);

    /**
     * Write the state of this object to the specified stream. The settings are
     * unaffected.
     *
     * @param stream where to write objects (not null)
     * @param shapeMap track multiple uses of shapes (may be null)
     * @param materialMap track multiple uses of physics materials (may be null)
     * @param filterMap track multiple uses of group filters (may be null)
     */
    void saveWithChildren(StreamOut stream, ShapeToIdMap shapeMap,
            MaterialToIdMap materialMap, GroupFilterToIdMap filterMap);
}
