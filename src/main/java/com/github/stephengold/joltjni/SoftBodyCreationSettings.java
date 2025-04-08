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

import com.github.stephengold.joltjni.readonly.ConstCollisionGroup;
import com.github.stephengold.joltjni.readonly.ConstSoftBodyCreationSettings;
import com.github.stephengold.joltjni.readonly.ConstSoftBodySharedSettings;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;

/**
 * Settings used to create a soft body.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SoftBodyCreationSettings
        extends JoltPhysicsObject
        implements ConstSoftBodyCreationSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public SoftBodyCreationSettings() {
        long bodySettingsVa = createDefault();
        setVirtualAddress(bodySettingsVa, () -> free(bodySettingsVa));
    }

    /**
     * Instantiate settings for the specified shared settings.
     *
     * @param settings the shared settings to use (not null, unaffected)
     * @param location the desired initial location (in system coordinates, not
     * null, unaffected)
     * @param orientation the desired initial orientation (relative to system
     * axes, not null, unaffected)
     * @param objectLayer the desired object layer (&ge;0)
     */
    public SoftBodyCreationSettings(ConstSoftBodySharedSettings settings,
            RVec3Arg location, QuatArg orientation, int objectLayer) {
        long sharedSettingsVa = settings.targetVa();
        double xx = location.xx();
        double yy = location.yy();
        double zz = location.zz();
        float qw = orientation.getW();
        float qx = orientation.getX();
        float qy = orientation.getY();
        float qz = orientation.getZ();
        long bodySettingsVa = createFromSharedSettings(
                sharedSettingsVa, xx, yy, zz, qx, qy, qz, qw, objectLayer);
        setVirtualAddress(bodySettingsVa, () -> free(bodySettingsVa));
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param bodySettingsVa the virtual address of the native object to assign
     * (not zero)
     */
    SoftBodyCreationSettings(JoltPhysicsObject container, long bodySettingsVa) {
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
    SoftBodyCreationSettings(long bodySettingsVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(bodySettingsVa) : null;
        setVirtualAddress(bodySettingsVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter whether the created body will be allowed to fall asleep. (native
     * member: mAllowSleeping)
     *
     * @param allow {@code true} to allow, {@code false} to inhibit
     * (default=true)
     */
    public void setAllowSleeping(boolean allow) {
        long bodySettingsVa = va();
        setAllowSleeping(bodySettingsVa, allow);
    }

    /**
     * Alter the collision group to which the body will belong. (native member:
     * mCollisionGroup)
     *
     * @param group the desired group (not null, unaffected)
     */
    public void setCollisionGroup(ConstCollisionGroup group) {
        long bodySettingsVa = va();
        long groupVa = group.targetVa();
        setCollisionGroup(bodySettingsVa, groupVa);
    }

    /**
     * Alter the friction ratio. (native member: mFriction)
     *
     * @param friction the desired ratio (typically &ge;0 and &le;1,
     * default=0.2)
     */
    public void setFriction(float friction) {
        long bodySettingsVa = va();
        setFriction(bodySettingsVa, friction);
    }

    /**
     * Alter the gravity multiplier. (native member: mGravityFactor)
     *
     * @param factor the desired multiplier (default=1)
     */
    public void setGravityFactor(float factor) {
        long bodySettingsVa = va();
        setGravityFactor(bodySettingsVa, factor);
    }

    /**
     * Alter the linear damping constant. (native member: mLinearDamping)
     *
     * @param damping the desired value (in units of 1 per second, &ge;0, &le;1,
     * default=0.1)
     */
    public void setLinearDamping(float damping) {
        long bodySettingsVa = va();
        setLinearDamping(bodySettingsVa, damping);
    }

    /**
     * Alter whether to bake the specified rotation into the vertices and set
     * the body rotation to identity. (native member: mMakeRotationIdentity)
     *
     * @param enable {@code true} to bake and set rotation, or {@code false} to
     * skip that step (default=true)
     */
    public void setMakeRotationIdentity(boolean enable) {
        long bodySettingsVa = va();
        setMakeRotationIdentity(bodySettingsVa, enable);
    }

    /**
     * Alter the maximum speed of vertices. (native member: mMaxSpeed)
     *
     * @param maxSpeed the desired maximum speed (in meters per second, &ge;0,
     * default=500)
     */
    public void setMaxLinearVelocity(float maxSpeed) {
        long bodySettingsVa = va();
        setMaxLinearVelocity(bodySettingsVa, maxSpeed);
    }

    /**
     * Alter the number of solver iterations. (native member: mNumIterations)
     *
     * @param numIterations the desired number of iterations
     */
    public void setNumIterations(int numIterations) {
        long bodySettingsVa = va();
        setNumIterations(bodySettingsVa, numIterations);
    }

    /**
     * Alter the object layer. (native member: mObjectLayer)
     *
     * @param objLayer the index of the desired object layer (&ge;0,
     * &lt;numObjectLayers, &lt;65536, default=0)
     */
    public void setObjectLayer(int objLayer) {
        assert objLayer >= 0 && objLayer < 65_536 : "objLayer = " + objLayer;

        long bodySettingsVa = va();
        setObjectLayer(bodySettingsVa, objLayer);
    }

    /**
     * Alter the (initial) location of the body's origin. (native member:
     * mPosition)
     *
     * @param location the desired location (in physics-system coordinates, not
     * null, unaffected, default=(0,0,0))
     */
    public void setPosition(RVec3Arg location) {
        long bodySettingsVa = va();
        double xx = location.xx();
        double yy = location.yy();
        double zz = location.zz();
        setPosition(bodySettingsVa, xx, yy, zz);
    }

    /**
     * Alter the pressure. (native member: mPressure)
     *
     * @param pressure the desired pressure (default=0)
     */
    public void setPressure(float pressure) {
        long bodySettingsVa = va();
        setPressure(bodySettingsVa, pressure);
    }

    /**
     * Alter the restitution ratio for collisions. (native member: mRestitution)
     *
     * @param restitution the desired ratio (typically &ge;0 and &le;1,
     * default=0)
     */
    public void setRestitution(float restitution) {
        long bodySettingsVa = va();
        setRestitution(bodySettingsVa, restitution);
    }

    /**
     * Alter the (initial) orientation. (native member: mRotation)
     *
     * @param orientation the desired location (relative to system axes, not
     * null, unaffected, default=(0,0,0,1))
     */
    public void setRotation(QuatArg orientation) {
        long bodySettingsVa = va();
        float qw = orientation.getW();
        float qx = orientation.getX();
        float qy = orientation.getY();
        float qz = orientation.getZ();
        setRotation(bodySettingsVa, qx, qy, qz, qw);
    }

    /**
     * Replace the shared settings. (native member: mSettings)
     *
     * @param sharedSettings the desired settings (not null, alias created)
     */
    public void setSettings(ConstSoftBodySharedSettings sharedSettings) {
        long bodySettingsVa = va();
        long sharedSettingsVa = sharedSettings.targetVa();
        setSettings(bodySettingsVa, sharedSettingsVa);
    }

    /**
     * Alter whether to update the position of the body during simulation.
     * (native member: mUpdatePosition)
     *
     * @param enable {@code true} to update the position, or {@code false} to
     * skip updating (default=true)
     */
    public void setUpdatePosition(boolean enable) {
        long bodySettingsVa = va();
        setUpdatePosition(bodySettingsVa, enable);
    }
    // *************************************************************************
    // ConstSoftBodyCreationSettings methods

    /**
     * Test whether the created body will be allowed to fall asleep. The
     * settings are unaffected. (native member: mAllowSleeping)
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
     * Return the friction ratio. The settings are unaffected. (native member:
     * mFriction)
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
     * Return the gravity factor. The settings are unaffected. (native member:
     * mGravityFactor)
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
     * Return the linear damping constant. The settings are unaffected. (native
     * member: mLinearDamping)
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
     * Return the maximum linear speed. The settings are unaffected. (native
     * member: mMaxSpeed)
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
     * Return the index of the object layer. The settings are unaffected.
     * (native member: mObjectLayer)
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
     * Copy the (initial) location. The settings are unaffected. (native member:
     * mPosition)
     *
     * @return a new location vector (in physics-system coordinates, all
     * components finite)
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
     * Return the pressure. The settings are unaffected. (native member:
     * mPressure)
     *
     * @return the pressure
     */
    @Override
    public float getPressure() {
        long bodySettingsVa = va();
        float result = getPressure(bodySettingsVa);

        return result;
    }

    /**
     * Return the restitution ratio. The settings are unaffected. (native
     * member: mRestitution)
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
     * unaffected. (native member: mRotation)
     *
     * @return a new rotation quaternion (relative to the physics-system axes)
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
     * Access the shared settings. (native member: mSettings)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public ConstSoftBodySharedSettings getSettings() {
        long bodySettingsVa = va();
        long sharedSettingsVa = getSettings(bodySettingsVa);
        ConstSoftBodySharedSettings result
                = new SoftBodySharedSettings(sharedSettingsVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createDefault();

    native private static long createFromSharedSettings(
            long sharedSettingsVa, double xx, double yy, double zz, float qx,
            float qy, float qz, float qw, int objectLayer);

    native private static void free(long bodySettingsVa);

    native private static boolean getAllowSleeping(long bodySettingsVa);

    native private static float getFriction(long bodySettingsVa);

    native private static float getGravityFactor(long bodySettingsVa);

    native private static float getLinearDamping(long bodySettingsVa);

    native private static float getMaxLinearVelocity(long bodySettingsVa);

    native private static int getObjectLayer(long bodySettingsVa);

    native private static double getPositionX(long bodySettingsVa);

    native private static double getPositionY(long bodySettingsVa);

    native private static double getPositionZ(long bodySettingsVa);

    native private static float getPressure(long bodySettingsVa);

    native private static float getRestitution(long bodySettingsVa);

    native private static float getRotationW(long bodySettingsVa);

    native private static float getRotationX(long bodySettingsVa);

    native private static float getRotationY(long bodySettingsVa);

    native private static float getRotationZ(long bodySettingsVa);

    native private static long getSettings(long bodySettingsVa);

    native private static void setAllowSleeping(
            long bodySettingsVa, boolean allow);

    native private static void setCollisionGroup(
            long bodySettingsVa, long groupVa);

    native private static void setFriction(long bodySettingsVa, float friction);

    native private static void setGravityFactor(
            long bodySettingsVa, float factor);

    native private static void setLinearDamping(
            long bodySettingsVa, float damping);

    native private static void setMakeRotationIdentity(
            long bodySettingsVa, boolean enable);

    native private static void setMaxLinearVelocity(
            long bodySettingsVa, float maxSpeed);

    native private static void setNumIterations(
            long bodySettingsVa, int numIterations);

    native private static void setObjectLayer(
            long bodySettingsVa, int objLayer);

    native private static void setPosition(
            long bodySettingsVa, double xx, double yy, double zz);

    native private static void setPressure(long bodySettingsVa, float pressure);

    native private static void setRestitution(
            long bodySettingsVa, float restitution);

    native private static void setRotation(
            long bodySettingsVa, float qx, float qy, float qz, float qw);

    native private static void setSettings(
            long bodySettingsVa, long sharedSettingsVa);

    native private static void setUpdatePosition(
            long bodySettingsVa, boolean enable);
}
