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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.enumerate.EMotionQuality;
import com.github.stephengold.joltjni.enumerate.EMotionType;
import com.github.stephengold.joltjni.enumerate.EOverrideMassProperties;
import com.github.stephengold.joltjni.readonly.ConstBodyCreationSettings;
import com.github.stephengold.joltjni.readonly.ConstCollisionGroup;
import com.github.stephengold.joltjni.readonly.ConstMassProperties;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.ConstShapeSettings;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.streamutils.GroupFilterToIdMap;
import com.github.stephengold.joltjni.streamutils.IdToGroupFilterMap;
import com.github.stephengold.joltjni.streamutils.IdToMaterialMap;
import com.github.stephengold.joltjni.streamutils.IdToShapeMap;
import com.github.stephengold.joltjni.streamutils.MaterialToIdMap;
import com.github.stephengold.joltjni.streamutils.ShapeToIdMap;

/**
 * Settings used to create a rigid body.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BodyCreationSettings
        extends JoltPhysicsObject
        implements ConstBodyCreationSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public BodyCreationSettings() {
        long bodySettingsVa = createDefault();
        setVirtualAddress(bodySettingsVa, () -> free(bodySettingsVa));
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public BodyCreationSettings(ConstBodyCreationSettings original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa, () -> free(copyVa));
    }

    /**
     * Instantiate settings for the specified shape.
     *
     * @param shape the desired shape (not null)
     * @param loc the desired location (not null, unaffected)
     * @param orient the desired orientation (not null, unaffected)
     * @param motionType the desired motion type (not null)
     * @param objLayer the ID of the desired object layer (&ge;0)
     */
    public BodyCreationSettings(ConstShape shape, RVec3Arg loc, QuatArg orient,
            EMotionType motionType, int objLayer) {
        long shapeVa = shape.targetVa();
        int motionTypeOrdinal = motionType.ordinal();
        long bodySettingsVa = createFromShape(shapeVa, loc.xx(), loc.yy(),
                loc.zz(), orient.getX(), orient.getY(), orient.getZ(),
                orient.getW(), motionTypeOrdinal, objLayer);
        setVirtualAddress(bodySettingsVa, () -> free(bodySettingsVa));
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param bodySettingsVa the virtual address of the native object to assign
     * (not zero)
     */
    BodyCreationSettings(JoltPhysicsObject container, long bodySettingsVa) {
        super(container, bodySettingsVa);
    }

    /**
     * Instantiate settings with the specified native object assigned.
     *
     * @param bodySettingsVa the virtual address of the native object to assign
     * (not zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    BodyCreationSettings(long bodySettingsVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(bodySettingsVa) : null;
        setVirtualAddress(bodySettingsVa, freeingAction);
    }

    /**
     * Instantiate settings for the specified shape reference.
     *
     * @param shapeRef a reference to the desired shape (not null)
     * @param loc the desired location (not null, unaffected)
     * @param orient the desired orientation (not null, unaffected)
     * @param motionType the desired motion type (not null)
     * @param objLayer the ID of the desired object layer
     */
    public BodyCreationSettings(ShapeRef shapeRef, RVec3Arg loc,
            QuatArg orient, EMotionType motionType, int objLayer) {
        this(shapeRef.getPtr(), loc, orient, motionType, objLayer);
    }

    /**
     * Instantiate settings for the specified shape settings.
     *
     * @param shapeSettings the desired shape settings (not null)
     * @param loc the desired location (not null, unaffected)
     * @param orient the desired orientation (not null, unaffected)
     * @param motionType the desired motion type (not null)
     * @param objLayer the ID of the desired object layer
     */
    public BodyCreationSettings(ConstShapeSettings shapeSettings, RVec3Arg loc,
            QuatArg orient, EMotionType motionType, int objLayer) {
        long shapeSettingsVa = shapeSettings.targetVa();
        int motionTypeOrdinal = motionType.ordinal();
        long bodySettingsVa = createFromShapeSettings(shapeSettingsVa, loc.xx(),
                loc.yy(), loc.zz(), orient.getX(), orient.getY(), orient.getZ(),
                orient.getW(), motionTypeOrdinal, objLayer);
        setVirtualAddress(bodySettingsVa, () -> free(bodySettingsVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter whether a static body can be converted to kinematic or dynamic.
     * (native attribute: mAllowDynamicOrKinematic)
     *
     * @param setting {@code true} to allow or {@code false} to inhibit
     * (default=false)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setAllowDynamicOrKinematic(boolean setting) {
        long bodySettingsVa = va();
        setAllowDynamicOrKinematic(bodySettingsVa, setting);

        return this;
    }

    /**
     * Alter the body's degrees of freedom. (native attribute: mAllowedDOFs)
     *
     * @param bitmask the desired bitmask (see {@code EAllowedDofs} for
     * semantics, default=All)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setAllowedDofs(int bitmask) {
        long bodySettingsVa = va();
        setAllowedDofs(bodySettingsVa, bitmask);

        return this;
    }

    /**
     * Alter whether the created body will be allowed to fall asleep. (native
     * attribute: mAllowSleeping)
     *
     * @param allow {@code true} to allow, {@code false} to inhibit
     * (default=true)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setAllowSleeping(boolean allow) {
        long bodySettingsVa = va();
        setAllowSleeping(bodySettingsVa, allow);

        return this;
    }

    /**
     * Alter the angular damping constant. (native attribute: mAngularDamping)
     *
     * @param damping the desired value (in units of per second, &ge;0, &le;1,
     * default=0.05)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setAngularDamping(float damping) {
        long bodySettingsVa = va();
        setAngularDamping(bodySettingsVa, damping);

        return this;
    }

    /**
     * Alter the (initial) angular velocity. (native attribute:
     * mAngularVelocity)
     *
     * @param omega the desired angular velocity (radians per second in system
     * coordinates, not null, unaffected, default=(0,0,0))
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setAngularVelocity(Vec3Arg omega) {
        long bodySettingsVa = va();
        setAngularVelocity(bodySettingsVa,
                omega.getX(), omega.getY(), omega.getZ());

        return this;
    }

    /**
     * Alter whether gyroscopic force will be applied. (native attribute:
     * mApplyGyroscopicForce)
     *
     * @param setting {@code true} to enable the force, or {@code false} to
     * disable it (default=false)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setApplyGyroscopicForce(boolean setting) {
        long bodySettingsVa = va();
        setApplyGyroscopicForce(bodySettingsVa, setting);

        return this;
    }

    /**
     * Alter the collision group to which the body will belong. (native
     * attribute: mCollisionGroup)
     *
     * @param group the desired group (not null, unaffected)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setCollisionGroup(ConstCollisionGroup group) {
        long bodySettingsVa = va();
        long groupVa = group.targetVa();
        setCollisionGroup(bodySettingsVa, groupVa);

        return this;
    }

    /**
     * Alter whether extra effort should be made to remove ghost contacts.
     *
     * @param enhance {@code true} for extra effort, {@code false} for ordinary
     * effort (default=false)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setEnhancedInternalEdgeRemoval(
            boolean enhance) {
        long bodySettingsVa = va();
        setEnhancedInternalEdgeRemoval(bodySettingsVa, enhance);

        return this;
    }

    /**
     * Alter the friction ratio. (native attribute: mFriction)
     *
     * @param friction the desired ratio (typically &ge;0 and &le;1,
     * default=0.2)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setFriction(float friction) {
        long bodySettingsVa = va();
        setFriction(bodySettingsVa, friction);

        return this;
    }

    /**
     * Alter the gravity multiplier. (native attribute: mGravityFactor)
     *
     * @param factor the desired multiplier (default=1)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setGravityFactor(float factor) {
        long bodySettingsVa = va();
        setGravityFactor(bodySettingsVa, factor);

        return this;
    }

    /**
     * Alter whether the body will be a sensor. (native attribute: mIsSensor)
     *
     * @param setting {@code true} for a sensor, otherwise {@code false}
     * (default=false)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setIsSensor(boolean setting) {
        long bodySettingsVa = va();
        setIsSensor(bodySettingsVa, setting);

        return this;
    }

    /**
     * Alter the linear damping constant. (native attribute: mLinearDamping)
     *
     * @param damping the desired value (in units of per second, &ge;0, &le;1,
     * default=0.05)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setLinearDamping(float damping) {
        long bodySettingsVa = va();
        setLinearDamping(bodySettingsVa, damping);

        return this;
    }

    /**
     * Alter the (initial) linear velocity. (native attribute: mLinearVelocity)
     *
     * @param velocity the desired velocity (in system coordinates, not null,
     * unaffected, default=(0,0,0))
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setLinearVelocity(Vec3Arg velocity) {
        long bodySettingsVa = va();
        setLinearVelocity(bodySettingsVa,
                velocity.getX(), velocity.getY(), velocity.getZ());

        return this;
    }

    /**
     * Alter the mass-properties override. (native attribute:
     * mMassPropertiesOverride)
     *
     * @param properties the desired properties (not null, unaffected)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setMassPropertiesOverride(
            ConstMassProperties properties) {
        long bodySettingsVa = va();
        long propertiesVa = properties.targetVa();
        setMassPropertiesOverride(bodySettingsVa, propertiesVa);

        return this;
    }

    /**
     * Alter the maximum angular speed. (native attribute: mMaxAngularVelocity)
     *
     * @param maxSpeed the desired maximum speed (in radians per second, &ge;0,
     * default=15*pi)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setMaxAngularVelocity(float maxSpeed) {
        long bodySettingsVa = va();
        setMaxAngularVelocity(bodySettingsVa, maxSpeed);

        return this;
    }

    /**
     * Alter the maximum linear speed. (native attribute: mMaxLinearVelocity)
     *
     * @param maxSpeed the desired maximum speed (in meters per second, &ge;0,
     * default=500)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setMaxLinearVelocity(float maxSpeed) {
        long bodySettingsVa = va();
        setMaxLinearVelocity(bodySettingsVa, maxSpeed);

        return this;
    }

    /**
     * Alter the motion quality. (native attribute: mMotionQuality)
     *
     * @param motionQuality the desired quality (not null, default=Discrete)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setMotionQuality(EMotionQuality motionQuality) {
        long bodySettingsVa = va();
        int motionQualityOrdinal = motionQuality.ordinal();
        setMotionQuality(bodySettingsVa, motionQualityOrdinal);

        return this;
    }

    /**
     * Alter the motion type. (native attribute: mMotionType)
     *
     * @param motionType the desired type (not null, default=Dynamic)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setMotionType(EMotionType motionType) {
        long bodySettingsVa = va();
        int motionTypeOrdinal = motionType.ordinal();
        setMotionType(bodySettingsVa, motionTypeOrdinal);

        return this;
    }

    /**
     * Alter the object layer. (native attribute: mObjectLayer)
     *
     * @param objLayer the index of the desired object layer (&ge;0,
     * &lt;numObjectLayers, &lt;65536, default=0)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setObjectLayer(int objLayer) {
        assert objLayer >= 0 && objLayer < 65_536 : "objLayer = " + objLayer;

        long bodySettingsVa = va();
        setObjectLayer(bodySettingsVa, objLayer);

        return this;
    }

    /**
     * Alter how the mass-properties override will be used. (native attribute:
     * mOverrideMassProperties)
     *
     * @param setting an enum value (not null, default=CalculateMassAndInertia)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setOverrideMassProperties(
            EOverrideMassProperties setting) {
        long bodySettingsVa = va();
        int ordinal = setting.ordinal();
        setOverrideMassProperties(bodySettingsVa, ordinal);

        return this;
    }

    /**
     * Alter the (initial) location of the body's origin (which might not
     * coincide with its center of mass). (native attribute: mPosition)
     *
     * @param xx the desired X coordinate (in system coordinates, default=0)
     * @param yy the desired Y coordinate (in system coordinates, default=0)
     * @param zz the desired Z coordinate (in system coordinates, default=0)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setPosition(double xx, double yy, double zz) {
        long bodySettingsVa = va();
        setPosition(bodySettingsVa, xx, yy, zz);

        return this;
    }

    /**
     * Alter the (initial) location of the body's origin (which might not
     * coincide with its center of mass). (native attribute: mPosition)
     *
     * @param location the desired location (in system coordinates, not null,
     * unaffected, default=(0,0,0))
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setPosition(RVec3Arg location) {
        long bodySettingsVa = va();
        double xx = location.xx();
        double yy = location.yy();
        double zz = location.zz();
        setPosition(bodySettingsVa, xx, yy, zz);

        return this;
    }

    /**
     * Alter the restitution ratio for collisions. (native attribute:
     * mRestitution)
     *
     * @param restitution the desired ratio (typically &ge;0 and &le;1,
     * default=0)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setRestitution(float restitution) {
        long bodySettingsVa = va();
        setRestitution(bodySettingsVa, restitution);

        return this;
    }

    /**
     * Alter the (initial) orientation of the body's axes. (native attribute:
     * mRotation)
     *
     * @param quat the desired rotation (relative to the system axes, not null,
     * normalized, unaffected, default=(0,0,0,1))
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setRotation(QuatArg quat) {
        assert quat.isNormalized() : "length =" + quat.length();

        long bodySettingsVa = va();
        float qw = quat.getW();
        float qx = quat.getX();
        float qy = quat.getY();
        float qz = quat.getZ();
        setRotation(bodySettingsVa, qx, qy, qz, qw);

        return this;
    }

    /**
     * Replace the shape.
     *
     * @param shape the desired shape (not {@code null}, unaffected)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setShape(ConstShape shape) {
        long bodySettingsVa = va();
        long shapeVa = shape.targetVa();
        setShape(bodySettingsVa, shapeVa);

        return this;
    }

    /**
     * Replace the shape settings.
     *
     * @param shapeSettings the desired shape settings (not null)
     * @return the modified settings, for chaining
     */
    public BodyCreationSettings setShapeSettings(
            ConstShapeSettings shapeSettings) {
        long bodySettingsVa = va();
        long shapeSettingsVa = shapeSettings.targetVa();
        setShapeSettings(bodySettingsVa, shapeSettingsVa);

        return this;
    }

    /**
     * Read a settings object from the specified binary stream.
     *
     * @param stream where to read objects (not null)
     * @param shapeMap track multiple uses of shapes (not null)
     * @param materialMap track multiple uses of physics materials (not null)
     * @param filterMap track multiple uses of group filters (not null)
     * @return a new object
     */
    public static BcsResult sRestoreWithChildren(
            StreamIn stream, IdToShapeMap shapeMap,
            IdToMaterialMap materialMap, IdToGroupFilterMap filterMap) {
        long streamVa = stream.va();
        long shapeMapVa = shapeMap.va();
        long materialMapVa = materialMap.va();
        long filterMapVa = filterMap.va();
        long resultVa = sRestoreWithChildren(
                streamVa, shapeMapVa, materialMapVa, filterMapVa);
        BcsResult result = new BcsResult(resultVa, true);

        return result;
    }
    // *************************************************************************
    // ConstBodyCreationSettings methods

    /**
     * Test whether a static body can be converted to kinematic or dynamic. The
     * settings are unaffected. (native attribute: mAllowDynamicOrKinematic)
     *
     * @return {@code true} if convertible, otherwise {@code false}
     */
    @Override
    public boolean getAllowDynamicOrKinematic() {
        long bodySettingsVa = va();
        boolean result = getAllowDynamicOrKinematic(bodySettingsVa);

        return result;
    }

    /**
     * Return the body's degrees of freedom. The settings are unaffected.
     * (native attribute: mAllowedDOFs)
     *
     * @return a bitmask (see {@code EAllowedDofs} for semantics)
     */
    @Override
    public int getAllowedDofs() {
        long bodySettingsVa = va();
        int result = getAllowedDofs(bodySettingsVa);

        return result;
    }

    /**
     * Test whether the created body will be allowed to fall asleep. The
     * settings are unaffected. (native attribute: mAllowSleeping)
     *
     * @return {@code true} if allowed, otherwise {@code false}
     */
    @Override
    public boolean getAllowSleeping() {
        long bodySettingsVa = va();
        boolean result = getAllowSleeping(bodySettingsVa);

        return result;
    }

    /**
     * Return the angular damping constant. The settings are unaffected. (native
     * attribute: mAngularDamping)
     *
     * @return the constant (in units of per second, &ge;0, &le;1)
     */
    @Override
    public float getAngularDamping() {
        long bodySettingsVa = va();
        float result = getAngularDamping(bodySettingsVa);

        return result;
    }

    /**
     * Copy the (initial) angular velocity. The settings are unaffected. (native
     * attribute: mAngularVelocity)
     *
     * @return a new velocity vector (radians per second in system coordinates)
     */
    @Override
    public Vec3 getAngularVelocity() {
        long bodySettingsVa = va();
        float vx = getAngularVelocityX(bodySettingsVa);
        float vy = getAngularVelocityY(bodySettingsVa);
        float vz = getAngularVelocityZ(bodySettingsVa);
        Vec3 result = new Vec3(vx, vy, vz);

        return result;
    }

    /**
     * Test whether the gyroscopic force will be applied. The settings are
     * unaffected. (native attribute: mApplyGyroscopicForce)
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    @Override
    public boolean getApplyGyroscopicForce() {
        long bodySettingsVa = va();
        boolean result = getApplyGyroscopicForce(bodySettingsVa);

        return result;
    }

    /**
     * Access the collision group to which the body will belong. (native
     * attribute: mCollisionGroup)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public CollisionGroup getCollisionGroup() {
        long bodySettingsVa = va();
        long groupVa = getCollisionGroup(bodySettingsVa);
        CollisionGroup result = new CollisionGroup(this, groupVa);

        return result;
    }

    /**
     * Test whether extra effort should be made to remove ghost contacts. The
     * settings are unaffected.
     *
     * @return {@code true} for extra effort, otherwise {@code false}
     */
    @Override
    public boolean getEnhancedInternalEdgeRemoval() {
        long bodySettingsVa = va();
        boolean result = getEnhancedInternalEdgeRemoval(bodySettingsVa);

        return result;
    }

    /**
     * Return the friction ratio. The settings are unaffected. (native
     * attribute: mFriction)
     *
     * @return the ratio (typically &ge;0 and &le;1)
     */
    @Override
    public float getFriction() {
        long bodySettingsVa = va();
        float result = getFriction(bodySettingsVa);

        return result;
    }

    /**
     * Return the gravity factor. The settings are unaffected. (native
     * attribute: mGravityFactor)
     *
     * @return the factor
     */
    @Override
    public float getGravityFactor() {
        long bodySettingsVa = va();
        float result = getGravityFactor(bodySettingsVa);

        return result;
    }

    /**
     * Test whether the body will be a sensor. The settings are unaffected.
     * (native attribute: mIsSensor)
     *
     * @return {@code true} for a sensor, otherwise {@code false}
     */
    @Override
    public boolean getIsSensor() {
        long bodySettingsVa = va();
        boolean result = getIsSensor(bodySettingsVa);

        return result;
    }

    /**
     * Return the linear damping constant. The settings are unaffected. (native
     * attribute: mLinearDamping)
     *
     * @return the constant (in units of per second, &ge;0, &le;1)
     */
    @Override
    public float getLinearDamping() {
        long bodySettingsVa = va();
        float result = getLinearDamping(bodySettingsVa);

        return result;
    }

    /**
     * Copy the (initial) linear velocity. The settings are unaffected. (native
     * attribute: mLinearVelocity)
     *
     * @return a new velocity vector (meters per second in system coordinates)
     */
    @Override
    public Vec3 getLinearVelocity() {
        long bodySettingsVa = va();
        float vx = getLinearVelocityX(bodySettingsVa);
        float vy = getLinearVelocityY(bodySettingsVa);
        float vz = getLinearVelocityZ(bodySettingsVa);
        Vec3 result = new Vec3(vx, vy, vz);

        return result;
    }

    /**
     * Calculate the mass and inertia. The settings are unaffected.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public MassProperties getMassProperties() {
        long bodySettingsVa = va();
        long propertiesVa = getMassProperties(bodySettingsVa);
        MassProperties result = new MassProperties(propertiesVa, true);

        return result;
    }

    /**
     * Access the mass-properties override. (native attribute:
     * mMassPropertiesOverride)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public MassProperties getMassPropertiesOverride() {
        long bodySettingsVa = va();
        long propertiesVa = getMassPropertiesOverride(bodySettingsVa);
        MassProperties result = new MassProperties(this, propertiesVa);

        return result;
    }

    /**
     * Return the maximum angular speed. The settings are unaffected. (native
     * attribute: mMaxAngularVelocity)
     *
     * @return the maximum speed (in radians per second)
     */
    @Override
    public float getMaxAngularVelocity() {
        long bodySettingsVa = va();
        float result = getMaxAngularVelocity(bodySettingsVa);

        return result;
    }

    /**
     * Return the maximum linear speed. The settings are unaffected. (native
     * attribute: mMaxLinearVelocity)
     *
     * @return the maximum speed (in meters per second)
     */
    @Override
    public float getMaxLinearVelocity() {
        long bodySettingsVa = va();
        float result = getMaxLinearVelocity(bodySettingsVa);

        return result;
    }

    /**
     * Return the motion quality. The settings are unaffected. (native
     * attribute: mMotionQuality)
     *
     * @return an enum value (not null)
     */
    @Override
    public EMotionQuality getMotionQuality() {
        long bodySettingsVa = va();
        int ordinal = getMotionQuality(bodySettingsVa);
        EMotionQuality result = EMotionQuality.values()[ordinal];

        return result;
    }

    /**
     * Return the motion type. The settings are unaffected. (native attribute:
     * mMotionType)
     *
     * @return an enum value (not null)
     */
    @Override
    public EMotionType getMotionType() {
        long bodySettingsVa = va();
        int ordinal = getMotionType(bodySettingsVa);
        EMotionType result = EMotionType.values()[ordinal];

        return result;
    }

    /**
     * Return the index of the object layer. The settings are unaffected.
     * (native attribute: mObjectLayer)
     *
     * @return the layer index (&ge;0, &lt;numObjectLayers)
     */
    @Override
    public int getObjectLayer() {
        long bodySettingsVa = va();
        int result = getObjectLayer(bodySettingsVa);

        return result;
    }

    /**
     * Return how the mass-properties override will be used. The settings are
     * unaffected. (native attribute: mOverrideMassProperties)
     *
     * @return an enum value (not null)
     */
    @Override
    public EOverrideMassProperties getOverrideMassProperties() {
        long bodySettingsVa = va();
        int ordinal = getOverrideMassProperties(bodySettingsVa);
        EOverrideMassProperties result
                = EOverrideMassProperties.values()[ordinal];
        return result;
    }

    /**
     * Copy the (initial) location. The settings are unaffected. (native
     * attribute: mPosition)
     *
     * @return a new location vector (in system coordinates, all components
     * finite)
     */
    @Override
    public RVec3 getPosition() {
        long bodySettingsVa = va();

        double xx = getPositionX(bodySettingsVa);
        assert Double.isFinite(xx) : "xx = " + xx;

        double yy = getPositionY(bodySettingsVa);
        assert Double.isFinite(yy) : "yy = " + yy;

        double zz = getPositionZ(bodySettingsVa);
        assert Double.isFinite(zz) : "zz = " + zz;

        RVec3 result = new RVec3(xx, yy, zz);
        return result;
    }

    /**
     * Return the restitution ratio. The settings are unaffected. (native
     * attribute: mRestitution)
     *
     * @return the ratio (typically &ge;0 and &le;1)
     */
    @Override
    public float getRestitution() {
        long bodySettingsVa = va();
        float result = getRestitution(bodySettingsVa);

        return result;
    }

    /**
     * Copy the (initial) orientation of the body's axes. The settings are
     * unaffected. (native attribute: mRotation)
     *
     * @return a new rotation quaternion (relative to the system axes)
     */
    @Override
    public Quat getRotation() {
        long bodySettingsVa = va();
        float qw = getRotationW(bodySettingsVa);
        float qx = getRotationX(bodySettingsVa);
        float qy = getRotationY(bodySettingsVa);
        float qz = getRotationZ(bodySettingsVa);
        Quat result = new Quat(qx, qy, qz, qw);

        return result;
    }

    /**
     * Acquire read-only access to the {@code Shape}. The settings are
     * unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if the body-creation settings are uncooked
     */
    @Override
    public ConstShape getShape() {
        long bodySettingsVa = va();
        long shapeVa = getShape(bodySettingsVa);
        ConstShape result = Shape.newShape(shapeVa);

        return result;
    }

    /**
     * Test whether the body's mass properties will be calculated. The settings
     * are unaffected.
     *
     * @return {@code true} if calculated, otherwise {@code false}
     */
    @Override
    public boolean hasMassProperties() {
        long bodySettingsVa = va();
        boolean result = hasMassProperties(bodySettingsVa);
        return result;
    }

    /**
     * Write the state of this object to the specified stream.
     *
     * @param stream where to write objects (not null)
     * @param shapeMap track multiple uses of shapes (may be null)
     * @param materialMap track multiple uses of physics materials (may be null)
     * @param filterMap track multiple uses of group filters (may be null)
     */
    public void saveWithChildren(StreamOut stream, ShapeToIdMap shapeMap,
            MaterialToIdMap materialMap, GroupFilterToIdMap filterMap) {
        long bodySettingsVa = va();
        long streamVa = stream.va();
        long shapeMapVa = (shapeMap == null) ? 0L : shapeMap.va();
        long materialMapVa = (materialMap == null) ? 0L : materialMap.va();
        long filterMapVa = (filterMap == null) ? 0L : filterMap.va();
        saveWithChildren(bodySettingsVa, streamVa, shapeMapVa, materialMapVa,
                filterMapVa);
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static long createFromShape(
            long shapeVa, double locX, double locY, double locZ, float qx,
            float qy, float qz, float qw, int motionTypeOrdinal, int objLayer);

    native private static long createFromShapeSettings(
            long shapeSettingsVa, double locX, double locY, double locZ,
            float qx, float qy, float qz, float qw,
            int motionTypeOrdinal, int objLayer);

    native private static void free(long bodySettingsVa);

    native private static boolean getAllowDynamicOrKinematic(
            long bodySettingsVa);

    native private static int getAllowedDofs(long bodySettingsVa);

    native private static boolean getAllowSleeping(long bodySettingsVa);

    native private static float getAngularDamping(long bodySettingsVa);

    native private static float getAngularVelocityX(long bodySettingsVa);

    native private static float getAngularVelocityY(long bodySettingsVa);

    native private static float getAngularVelocityZ(long bodySettingsVa);

    native private static boolean getApplyGyroscopicForce(long bodySettingsVa);

    native private static long getCollisionGroup(long bodySettingsVa);

    native private static boolean getEnhancedInternalEdgeRemoval(
            long bodySettingsVa);

    native private static float getFriction(long bodySettingsVa);

    native private static float getGravityFactor(long bodySettingsVa);

    native private static boolean getIsSensor(long bodySettingsVa);

    native private static float getLinearDamping(long bodySettingsVa);

    native private static float getLinearVelocityX(long bodySettingsVa);

    native private static float getLinearVelocityY(long bodySettingsVa);

    native private static float getLinearVelocityZ(long bodySettingsVa);

    native private static long getMassProperties(long bodySettingsVa);

    native private static long getMassPropertiesOverride(long bodySettingsVa);

    native private static float getMaxAngularVelocity(long bodySettingsVa);

    native private static float getMaxLinearVelocity(long bodySettingsVa);

    native private static int getMotionQuality(long bodySettingsVa);

    native private static int getMotionType(long bodySettingsVa);

    native private static int getObjectLayer(long bodySettingsVa);

    native private static int getOverrideMassProperties(long bodySettingsVa);

    native private static double getPositionX(long bodySettingsVa);

    native private static double getPositionY(long bodySettingsVa);

    native private static double getPositionZ(long bodySettingsVa);

    native private static float getRestitution(long bodySettingsVa);

    native private static float getRotationW(long bodySettingsVa);

    native private static float getRotationX(long bodySettingsVa);

    native private static float getRotationY(long bodySettingsVa);

    native private static float getRotationZ(long bodySettingsVa);

    native private static long getShape(long bodySettingsVa);

    native private static boolean hasMassProperties(long bodySettingsVa);

    native private static void saveWithChildren(
            long bodySettingsVa, long streamVa, long shapeMapVa,
            long materialMapVa, long filterMapVa);

    native private static void setAllowDynamicOrKinematic(
            long bodySettingsVa, boolean setting);

    native private static void setAllowedDofs(long bodySettingsVa, int bitmask);

    native private static void setAllowSleeping(
            long bodySettingsVa, boolean allow);

    native private static void setAngularDamping(
            long bodySettingsVa, float damping);

    native private static void setAngularVelocity(
            long bodySettingsVa, float wx, float wy, float wz);

    native private static void setApplyGyroscopicForce(
            long bodySettingsVa, boolean setting);

    native private static void setCollisionGroup(
            long bodySettingsVa, long groupVa);

    native private static void setEnhancedInternalEdgeRemoval(
            long bodySettingsVa, boolean enhance);

    native private static void setFriction(long bodySettingsVa, float friction);

    native private static void setGravityFactor(
            long bodySettingsVa, float factor);

    native private static void setIsSensor(
            long bodySettingsVa, boolean setting);

    native private static void setLinearDamping(
            long bodySettingsVa, float damping);

    native private static void setLinearVelocity(
            long bodySettingsVa, float vx, float vy, float vz);

    native private static void setMassPropertiesOverride(
            long bodySettingsVa, long propertiesVa);

    native private static void setMaxAngularVelocity(
            long bodySettingsVa, float maxSpeed);

    native private static void setMaxLinearVelocity(
            long bodySettingsVa, float maxSpeed);

    native private static void setMotionQuality(
            long bodySettingsVa, int motionQualityOrdinal);

    native private static void setMotionType(
            long bodySettingsVa, int motionTypeOrdinal);

    native private static void setObjectLayer(
            long bodySettingsVa, int objLayer);

    native private static void setOverrideMassProperties(
            long bodySettingsVa, int ordinal);

    native private static void setPosition(
            long bodySettingsVa, double locX, double locY, double locZ);

    native private static void setRestitution(
            long bodySettingsVa, float restitution);

    native private static void setRotation(
            long bodySettingsVa, float qx, float qy, float qz, float qw);

    native private static void setShape(long bodySettingsVa, long shapeVa);

    native private static void setShapeSettings(
            long bodySettingsVa, long shapeSettingsVa);

    native private static long sRestoreWithChildren(long streamVa,
            long shapeMapVa, long materialMapVa, long filterMapVa);
}
