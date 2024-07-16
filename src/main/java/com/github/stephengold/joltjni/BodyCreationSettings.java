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
package com.github.stephengold.joltjni;

/**
 * Settings used to create a {@code Body}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BodyCreationSettings extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public BodyCreationSettings() {
        long bodySettingsVa = createBodyCreationSettings();
        setVirtualAddress(bodySettingsVa, () -> free(bodySettingsVa));
    }

    /**
     * Instantiate settings for the specified shape.
     *
     * @param shape the desired shape (not null)
     * @param loc the desired location (not null, unaffected)
     * @param orient the desired orientation (not null, unaffected)
     * @param motionType the desired motion type (not null)
     * @param objLayer the ID of the desired object layer
     */
    public BodyCreationSettings(ConstShape shape, RVec3Arg loc, QuatArg orient,
            EMotionType motionType, int objLayer) {
        long shapeVa = shape.va();
        int motionTypeOrdinal = motionType.ordinal();
        long bodySettingsVa = createBodyCreationSettingsFromShape(
                shapeVa, loc.xx(), loc.yy(), loc.zz(),
                orient.getX(), orient.getY(), orient.getZ(), orient.getW(),
                motionTypeOrdinal, objLayer);
        setVirtualAddress(bodySettingsVa, () -> free(bodySettingsVa));
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
    public BodyCreationSettings(ShapeRefC shapeRef, RVec3Arg loc,
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
    public BodyCreationSettings(ShapeSettings shapeSettings, RVec3Arg loc,
            QuatArg orient, EMotionType motionType, int objLayer) {
        long shapeSettingsVa = shapeSettings.va();
        int motionTypeOrdinal = motionType.ordinal();
        long bodySettingsVa = createBodyCreationSettingsFromShapeSettings(
                shapeSettingsVa, loc.xx(), loc.yy(), loc.zz(),
                orient.getX(), orient.getY(), orient.getZ(), orient.getW(),
                motionTypeOrdinal, objLayer);
        setVirtualAddress(bodySettingsVa, () -> free(bodySettingsVa));
    }

    /**
     * Instantiate settings for the specified shape-settings reference.
     *
     * @param shapeSettingsRef a reference to the desired shape settings (not
     * null)
     * @param loc the desired location (not null, unaffected)
     * @param orient the desired orientation (not null, unaffected)
     * @param motionType the desired motion type (not null)
     * @param objLayer the ID of the desired object layer
     */
    public BodyCreationSettings(ShapeSettingsRef shapeSettingsRef, RVec3Arg loc,
            QuatArg orient, EMotionType motionType, int objLayer) {
        this(shapeSettingsRef.getPtr(), loc, orient, motionType, objLayer);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether the created body will be allowed to fall asleep. The
     * settings are unaffected. (native field: mAllowSleeping)
     *
     * @return true if allowed, otherwise false
     */
    public boolean getAllowSleeping() {
        long bodySettingsVa = va();
        boolean result = getAllowSleeping(bodySettingsVa);

        return result;
    }

    /**
     * Return the angular damping constant. The settings are unaffected. (native
     * field: mAngularDamping)
     *
     * @return the constant (in units of 1/second, &ge;0, &le;1)
     */
    public float getAngularDamping() {
        long bodySettingsVa = va();
        float result = getAngularDamping(bodySettingsVa);

        return result;
    }

    /**
     * Copy the (initial) angular velocity. The settings are unaffected. (native
     * field: mAngularVelocity)
     *
     * @return a new velocity vector (radians per second in physics-system
     * coordinates)
     */
    public Vec3 getAngularVelocity() {
        long bodySettingsVa = va();
        float vx = getAngularVelocityX(bodySettingsVa);
        float vy = getAngularVelocityY(bodySettingsVa);
        float vz = getAngularVelocityZ(bodySettingsVa);
        Vec3 result = new Vec3(vx, vy, vz);

        return result;
    }

    /**
     * Return the friction ratio. The settings are unaffected. (native field:
     * mFriction)
     *
     * @return the ratio (typically &ge;0 and &le;1)
     */
    public float getFriction() {
        long bodySettingsVa = va();
        float result = getFriction(bodySettingsVa);

        return result;
    }

    /**
     * Return the gravity factor. The settings are unaffected. (native field:
     * mGravityFactor)
     *
     * @return the factor
     */
    public float getGravityFactor() {
        long bodySettingsVa = va();
        float result = getGravityFactor(bodySettingsVa);

        return result;
    }

    /**
     * Return the linear damping constant. The settings are unaffected. (native
     * field: mLinearDamping)
     *
     * @return the constant (in units of 1/second, &ge;0, &le;1)
     */
    public float getLinearDamping() {
        long bodySettingsVa = va();
        float result = getLinearDamping(bodySettingsVa);

        return result;
    }

    /**
     * Copy the (initial) linear velocity. The settings are unaffected. (native
     * field: mLinearVelocity)
     *
     * @return a new velocity vector (meters per second in physics-system
     * coordinates)
     */
    public Vec3 getLinearVelocity() {
        long bodySettingsVa = va();
        float vx = getLinearVelocityX(bodySettingsVa);
        float vy = getLinearVelocityY(bodySettingsVa);
        float vz = getLinearVelocityZ(bodySettingsVa);
        Vec3 result = new Vec3(vx, vy, vz);

        return result;
    }

    /**
     * Calculate the mass and inertia.
     *
     * @return a new JVM object with a new native object assigned
     */
    public MassProperties getMassProperties() {
        long bodySettingsVa = va();
        long propertiesVa = getMassProperties(bodySettingsVa);
        MassProperties result = new MassProperties(propertiesVa);

        return result;
    }

    /**
     * Return the maximum angular speed. The settings are unaffected. (native
     * field: mMaxAngularVelocity)
     *
     * @return the maximum speed (in radians/second)
     */
    public float getMaxAngularVelocity() {
        long bodySettingsVa = va();
        float result = getMaxAngularVelocity(bodySettingsVa);

        return result;
    }

    /**
     * Return the maximum linear speed. The settings are unaffected. (native
     * field: mMaxLinearVelocity)
     *
     * @return the maximum speed (in meters/second)
     */
    public float getMaxLinearVelocity() {
        long bodySettingsVa = va();
        float result = getMaxLinearVelocity(bodySettingsVa);

        return result;
    }

    /**
     * Return the motion quality. The settings are unaffected. (native field:
     * mMotionQuality)
     *
     * @return an enum value (not null)
     */
    public EMotionQuality getMotionQuality() {
        long bodySettingsVa = va();
        int ordinal = getMotionQuality(bodySettingsVa);
        EMotionQuality result = EMotionQuality.values()[ordinal];

        return result;
    }

    /**
     * Return the motion type. The settings are unaffected. (native field:
     * mMotionType)
     *
     * @return an enum value (not null)
     */
    public EMotionType getMotionType() {
        long bodySettingsVa = va();
        int ordinal = getMotionType(bodySettingsVa);
        EMotionType result = EMotionType.values()[ordinal];

        return result;
    }

    /**
     * Return the index of the object layer. The settings are unaffected.
     * (native field: mObjectLayer)
     *
     * @return the layer index (&ge;0, &lt;numObjectLayers)
     */
    public int getObjectLayer() {
        long bodySettingsVa = va();
        int result = getObjectLayer(bodySettingsVa);

        return result;
    }

    /**
     * Return the (initial) location. The settings are unaffected. (native
     * field: mPosition)
     *
     * @return a new location vector (in physics-system coordinates, all
     * components finite)
     */
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
     * Return the restitution ratio. The settings are unaffected. (native field:
     * mRestitution)
     *
     * @return the ratio (typically &ge;0 and &le;1)
     */
    public float getRestitution() {
        long bodySettingsVa = va();
        float result = getRestitution(bodySettingsVa);
        return result;
    }

    /**
     * Copy the (initial) orientation of the body's axes. The settings are
     * unaffected. (native field: mRotation)
     *
     * @return a new rotation quaternion (relative to the physics-system axes)
     */
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
     * Access the {@code Shape}.
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null}
     */
    public ConstShape getShape() {
        long bodySettingsVa = va();
        long shapeSettingsVa = getShape(bodySettingsVa);
        ConstShape result = Shape.newShape(shapeSettingsVa);

        return result;
    }

    /**
     * Test whether the body's mass properties will be calculated. The settings
     * are unaffected.
     *
     * @return true if calculated, otherwise false
     */
    public boolean hasMassProperties() {
        long bodySettingsVa = va();
        boolean result = hasMassProperties(bodySettingsVa);
        return result;
    }

    /**
     * Alter whether the created body will be allowed to fall asleep. (native
     * field: mAllowSleeping)
     *
     * @param allow true to allow, false to inhibit (default=true)
     */
    public void setAllowSleeping(boolean allow) {
        long bodySettingsVa = va();
        setAllowSleeping(bodySettingsVa, allow);
    }

    /**
     * Alter the angular damping constant. (native field: mAngularDamping)
     *
     * @param damping the desired value (in units of 1/second, &ge;0, &le;1,
     * default=0.05)
     */
    public void setAngularDamping(float damping) {
        long bodySettingsVa = va();
        setAngularDamping(bodySettingsVa, damping);
    }

    /**
     * Alter the (initial) angular velocity. (native field: mAngularVelocity)
     *
     * @param omega the desired angular velocity (radians per second in
     * physics-system coordinates, not null, unaffected, default=(0,0,0))
     */
    public void setAngularVelocity(Vec3Arg omega) {
        long bodySettingsVa = va();
        setAngularVelocity(bodySettingsVa,
                omega.getX(), omega.getY(), omega.getZ());
    }

    /**
     * Alter the friction ratio. (native field: mFriction)
     *
     * @param friction the desired ratio (typically &ge;0 and &le;1,
     * default=0.2)
     */
    public void setFriction(float friction) {
        long bodySettingsVa = va();
        setFriction(bodySettingsVa, friction);
    }

    /**
     * Alter the gravity multiplier. (native field: mGravityFactor)
     *
     * @param factor the desired multiplier (default=1)
     */
    public void setGravityFactor(float factor) {
        long bodySettingsVa = va();
        setGravityFactor(bodySettingsVa, factor);
    }

    /**
     * Alter the linear damping constant. (native field: mLinearDamping)
     *
     * @param damping the desired value (in units of 1/second, &ge;0, &le;1,
     * default=0.05)
     */
    public void setLinearDamping(float damping) {
        long bodySettingsVa = va();
        setLinearDamping(bodySettingsVa, damping);
    }

    /**
     * Alter the (initial) linear velocity. (native field: mLinearVelocity)
     *
     * @param velocity the desired velocity (in physics-system coordinates, not
     * null, unaffected, default=(0,0,0))
     */
    public void setLinearVelocity(Vec3Arg velocity) {
        long bodySettingsVa = va();
        setLinearVelocity(bodySettingsVa,
                velocity.getX(), velocity.getY(), velocity.getZ());
    }

    /**
     * Alter the maximum angular speed. (native field: mMaxAngularVelocity)
     *
     * @param maxSpeed the desired maximum speed (in radians/second, &ge;0,
     * default=15*pi)
     */
    public void setMaxAngularVelocity(float maxSpeed) {
        long bodySettingsVa = va();
        setMaxAngularVelocity(bodySettingsVa, maxSpeed);
    }

    /**
     * Alter the maximum linear speed. (native field: mMaxLinearVelocity)
     *
     * @param maxSpeed the desired maximum speed (in meters/second, &ge;0,
     * default=500)
     */
    public void setMaxLinearVelocity(float maxSpeed) {
        long bodySettingsVa = va();
        setMaxLinearVelocity(bodySettingsVa, maxSpeed);
    }

    /**
     * Alter the motion quality. (native field: mMotionQuality)
     *
     * @param motionQuality the desired quality (not null, default=Discrete)
     */
    public void setMotionQuality(EMotionQuality motionQuality) {
        long bodySettingsVa = va();
        int motionQualityOrdinal = motionQuality.ordinal();
        setMotionQuality(bodySettingsVa, motionQualityOrdinal);
    }

    /**
     * Alter the motion type. (native field: mMotionType)
     *
     * @param motionType the desired type (not null, default=Dynamic)
     */
    public void setMotionType(EMotionType motionType) {
        long bodySettingsVa = va();
        int motionTypeOrdinal = motionType.ordinal();
        setMotionType(bodySettingsVa, motionTypeOrdinal);
    }

    /**
     * Alter the object layer. (native field: mObjectLayer)
     *
     * @param objLayer the ID of the desired object layer (&ge;0,
     * &lt;numObjectLayers, default=0)
     */
    public void setObjectLayer(int objLayer) {
        long bodySettingsVa = va();
        setObjectLayer(bodySettingsVa, objLayer);
    }

    /**
     * Alter the (initial) location of the body's origin (which might not
     * coincide with its center of mass). (native field: mPosition)
     *
     * @param loc the desired location (in physics-system coordinates, not null,
     * unaffected, default=(0,0,0))
     */
    public void setPosition(RVec3Arg loc) {
        long bodySettingsVa = va();
        setPosition(bodySettingsVa, loc.xx(), loc.yy(), loc.zz());
    }

    /**
     * Alter the restitution ratio. (native field: mRestitution)
     *
     * @param restitution the desired ratio (typically &ge;0 and &le;1,
     * default=0)
     */
    public void setRestitution(float restitution) {
        long bodySettingsVa = va();
        setRestitution(bodySettingsVa, restitution);
    }

    /**
     * Alter the (initial) orientation of the body's axes. (native field:
     * mRotation)
     *
     * @param quat the desired rotation (relative to the physics-system axes,
     * not null, unaffected, default=(0,0,0,1))
     */
    public void setRotation(QuatArg quat) {
        long bodySettingsVa = va();
        float qw = quat.getW();
        float qx = quat.getX();
        float qy = quat.getY();
        float qz = quat.getZ();
        setRotation(bodySettingsVa, qx, qy, qz, qw);
    }

    /**
     * Replace the shape.
     *
     * @param shape the desired shape (not null, unaffected)
     */
    public void setShape(ConstShape shape) {
        long bodySettingsVa = va();
        long shapeSettingsVa = shape.va();
        setShape(bodySettingsVa, shapeSettingsVa);
    }

    /**
     * Replace the shape.
     *
     * @param shapeRef a reference to the desired shape (not null)
     */
    public void setShape(ShapeRefC shapeRef) {
        ConstShape shape = shapeRef.getPtr();
        setShape(shape);
    }

    /**
     * Replace the shape settings.
     *
     * @param shapeSettings the desired shape settings (not null)
     */
    public void setShapeSettings(ShapeSettings shapeSettings) {
        long bodySettingsVa = va();
        long shapeSettingsVa = shapeSettings.va();
        setShapeSettings(bodySettingsVa, shapeSettingsVa);
    }

    /**
     * Replace the shape settings.
     *
     * @param shapeSettingsRef a reference to the desired shape settings (not
     * null)
     */
    public void setShapeSettings(ShapeSettingsRef shapeSettingsRef) {
        ShapeSettings shapeSettings = shapeSettingsRef.getPtr();
        setShapeSettings(shapeSettings);
    }
    // *************************************************************************
    // native private methods

    native private static long createBodyCreationSettings();

    native private static long createBodyCreationSettingsFromShape(
            long shapeVa, double locX, double locY, double locZ,
            float qx, float qy, float qz, float qw,
            int motionTypeOrdinal, int objLayer);

    native private static long createBodyCreationSettingsFromShapeSettings(
            long shapeSettingsVa, double locX, double locY, double locZ,
            float qx, float qy, float qz, float qw,
            int motionTypeOrdinal, int objLayer);

    native private static void free(long bodySettingsVa);

    native private static boolean getAllowSleeping(long bodySettingsVa);

    native private static float getAngularDamping(long bodySettingsVa);

    native private static float getAngularVelocityX(long bodySettingsVa);

    native private static float getAngularVelocityY(long bodySettingsVa);

    native private static float getAngularVelocityZ(long bodySettingsVa);

    native private static float getFriction(long bodySettingsVa);

    native private static float getGravityFactor(long bodySettingsVa);

    native private static float getLinearDamping(long bodySettingsVa);

    native private static float getLinearVelocityX(long bodySettingsVa);

    native private static float getLinearVelocityY(long bodySettingsVa);

    native private static float getLinearVelocityZ(long bodySettingsVa);

    native private static long getMassProperties(long bodySettingsVa);

    native private static float getMaxAngularVelocity(long bodySettingsVa);

    native private static float getMaxLinearVelocity(long bodySettingsVa);

    native private static int getMotionQuality(long bodySettingsVa);

    native private static int getMotionType(long bodySettingsVa);

    native private static int getObjectLayer(long bodySettingsVa);

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

    native private static void setAllowSleeping(
            long bodySettingsVa, boolean allow);

    native private static void setAngularDamping(
            long bodySettingsVa, float damping);

    native private static void setAngularVelocity(long bodySettingsVa,
            float wx, float wy, float wz);

    native private static void setFriction(long bodySettingsVa, float friction);

    native private static void setGravityFactor(
            long bodySettingsVa, float factor);

    native private static void setLinearDamping(
            long bodySettingsVa, float damping);

    native private static void setLinearVelocity(long bodySettingsVa,
            float vx, float vy, float vz);

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

    native private static void setPosition(
            long bodySettingsVa, double locX, double locY, double locZ);

    native private static void setRestitution(
            long bodySettingsVa, float restitution);

    native private static void setRotation(long bodySettingsVa,
            float qx, float qy, float qz, float qw);

    native private static void setShape(long bodySettingsVa, long shapeVa);

    native private static void setShapeSettings(
            long bodySettingsVa, long shapeSettingsVa);
}
