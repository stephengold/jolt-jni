/*
Copyright (c) 2024-2026 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.ConstLinearCurve;
import com.github.stephengold.joltjni.readonly.ConstWheelSettingsWv;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.template.Ref;
import java.nio.FloatBuffer;

/**
 * A counted reference to a {@code WheelSettingsWv} object. (native type:
 * {@code Ref<WheelSettingsWV>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class WheelSettingsWvRef
        extends Ref
        implements ConstWheelSettingsWv {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public WheelSettingsWvRef() {
        long refVa = createDefault();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a counted reference to the specified settings.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param settings the settings to target (not {@code null})
     */
    WheelSettingsWvRef(long refVa, WheelSettingsWv settings) {
        assert settings != null;

        this.ptr = settings;
        Runnable freeingAction = () -> free(refVa);
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter where to apply tire forces. (native attribute:
     * mEnableSuspensionForcePoint)
     *
     * @param enable {@code true} to apply at the configured suspension-force
     * point, {@code false} to apply at the wheel's point of contact
     * (default=false)
     * @return the current reference, for chaining
     */
    public WheelSettingsWvRef setEnableSuspensionForcePoint(boolean enable) {
        long settingsVa = targetVa();
        WheelSettings.setEnableSuspensionForcePoint(settingsVa, enable);

        return this;
    }

    /**
     * Copy the specified curve to the lateral-friction curve. (native
     * attribute: mLateralFriction)
     *
     * @param curve the curve to copy (not {@code null}, unaffected)
     */
    public void setLateralFriction(ConstLinearCurve curve) {
        long settingsVa = targetVa();
        long curveVa = curve.targetVa();
        WheelSettingsWv.setLateralFriction(settingsVa, curveVa);
    }

    /**
     * Copy the specified curve to the longitudinal-friction curve. (native
     * attribute: mLongitudinalFriction)
     *
     * @param curve the curve to copy (not {@code null}, unaffected)
     */
    public void setLongitudinalFriction(ConstLinearCurve curve) {
        long settingsVa = targetVa();
        long curveVa = curve.targetVa();
        WheelSettingsWv.setLongitudinalFriction(settingsVa, curveVa);
    }

    /**
     * Alter the maximum torque that the main brake can exert on the wheel.
     * (native attribute: mMaxBrakeTorque)
     *
     * @param torque the desired torque (in Newton meters, default=1500)
     * @return the current reference, for chaining
     */
    public WheelSettingsWvRef setMaxBrakeTorque(float torque) {
        long settingsVa = targetVa();
        WheelSettingsWv.setMaxBrakeTorque(settingsVa, torque);

        return this;
    }

    /**
     * Alter the maximum torque that the hand brake can exert on the wheel.
     * (native attribute: mMaxHandBrakeTorque)
     *
     * @param torque the desired torque (in Newton meters, default=4000)
     * @return the current reference, for chaining
     */
    public WheelSettingsWvRef setMaxHandBrakeTorque(float torque) {
        long settingsVa = targetVa();
        WheelSettingsWv.setMaxHandBrakeTorque(settingsVa, torque);

        return this;
    }

    /**
     * Alter the maximum steering angle. (native attribute: mMaxSteerAngle)
     *
     * @param angle the desired maximum steering angle (in radians,
     * default=7*Pi/18)
     * @return the current reference, for chaining
     */
    public WheelSettingsWvRef setMaxSteerAngle(float angle) {
        long settingsVa = targetVa();
        WheelSettingsWv.setMaxSteerAngle(settingsVa, angle);

        return this;
    }

    /**
     * Relocate the attachment point. (native attribute: mPosition)
     *
     * @param position the location of the attachment point (in the body's local
     * coordinates, not {@code null}, unaffected, default=(0,0,0))
     * @return the current reference, for chaining
     */
    public WheelSettingsWvRef setPosition(Vec3Arg position) {
        long settingsVa = targetVa();
        float x = position.getX();
        float y = position.getY();
        float z = position.getZ();
        WheelSettings.setPosition(settingsVa, x, y, z);

        return this;
    }

    /**
     * Alter the radius of the wheel. (native attribute: mRadius)
     *
     * @param radius the desired radius (in meters, default=0.3)
     * @return the current reference, for chaining
     */
    public WheelSettingsWvRef setRadius(float radius) {
        long settingsVa = targetVa();
        WheelSettings.setRadius(settingsVa, radius);

        return this;
    }

    /**
     * Alter the steering axis (upward direction). (native attribute:
     * mSteeringAxis)
     *
     * @param direction the desired direction (not {@code null}, unaffected,
     * default=(0,1,0))
     * @return the current reference, for chaining
     */
    public WheelSettingsWvRef setSteeringAxis(Vec3Arg direction) {
        long settingsVa = targetVa();
        float dx = direction.getX();
        float dy = direction.getY();
        float dz = direction.getZ();
        WheelSettings.setSteeringAxis(settingsVa, dx, dy, dz);

        return this;
    }

    /**
     * Alter the downward direction of the suspension. (native attribute:
     * mSuspensionDirection)
     *
     * @param direction the desired direction (not {@code null}, unaffected,
     * default=(0,-1,0))
     * @return the current reference, for chaining
     */
    public WheelSettingsWvRef setSuspensionDirection(Vec3Arg direction) {
        long settingsVa = targetVa();
        float dx = direction.getX();
        float dy = direction.getY();
        float dz = direction.getZ();
        WheelSettings.setSuspensionDirection(settingsVa, dx, dy, dz);

        return this;
    }

    /**
     * Alter the location where tire forces will be applied if the force-point
     * option is enabled. (native attribute: mSuspensionForcePoint)
     *
     * @param location the desired location (in the body coordinates, not
     * {@code null}, unaffected, default=(0,0,0))
     * @return the current reference, for chaining
     */
    public WheelSettingsWvRef setSuspensionForcePoint(Vec3Arg location) {
        long settingsVa = targetVa();
        float x = location.getX();
        float y = location.getY();
        float z = location.getZ();
        WheelSettings.setSuspensionForcePoint(settingsVa, x, y, z);

        return this;
    }

    /**
     * Alter the maximum displacement from the attachment point. (native
     * attribute: mSuspensionMaxLength)
     *
     * @param length the desired limit (in meters, default=0.5)
     * @return the current reference, for chaining
     */
    public WheelSettingsWvRef setSuspensionMaxLength(float length) {
        long settingsVa = targetVa();
        WheelSettings.setSuspensionMaxLength(settingsVa, length);

        return this;
    }

    /**
     * Alter the minimum displacement from the attachment point. (native
     * attribute: mSuspensionMinLength)
     *
     * @param length the desired limit (in meters, default=0.3)
     * @return the current reference, for chaining
     */
    public WheelSettingsWvRef setSuspensionMinLength(float length) {
        long settingsVa = targetVa();
        WheelSettings.setSuspensionMinLength(settingsVa, length);

        return this;
    }

    /**
     * Alter the suspension preload length. (native attribute:
     * mSuspensionPreloadLength)
     *
     * @param length the desired offset (in meters, default=0)
     * @return the current reference, for chaining
     */
    public WheelSettingsWvRef setSuspensionPreloadLength(float length) {
        long settingsVa = targetVa();
        WheelSettings.setSuspensionPreloadLength(settingsVa, length);

        return this;
    }

    /**
     * Alter the forward direction when steering is neutral. (native attribute:
     * mWheelForward)
     *
     * @param direction the desired direction (not {@code null}, unaffected,
     * default=(0,0,1))
     * @return the current reference, for chaining
     */
    public WheelSettingsWvRef setWheelForward(Vec3Arg direction) {
        long settingsVa = targetVa();
        float dx = direction.getX();
        float dy = direction.getY();
        float dz = direction.getZ();
        WheelSettings.setWheelForward(settingsVa, dx, dy, dz);

        return this;
    }

    /**
     * Alter the "up" direction when steering is neutral. (native attribute:
     * mWheelUp)
     *
     * @param direction the desired direction (not {@code null}, unaffected,
     * default=(0,1,0))
     * @return the current reference, for chaining
     */
    public WheelSettingsWvRef setWheelUp(Vec3Arg direction) {
        long settingsVa = targetVa();
        float dx = direction.getX();
        float dy = direction.getY();
        float dz = direction.getZ();
        WheelSettings.setWheelUp(settingsVa, dx, dy, dz);

        return this;
    }

    /**
     * Alter the width of the wheel. (native attribute: mWidth)
     *
     * @param width the desired width (in meters, default=0.1)
     * @return the current reference, for chaining
     */
    public WheelSettingsWvRef setWidth(float width) {
        long settingsVa = targetVa();
        WheelSettings.setWidth(settingsVa, width);

        return this;
    }
    // *************************************************************************
    // ConstWheelSettingsWv methods

    /**
     * Determine where to apply tire forces. The settings are unaffected.
     * (native attribute: mEnableSuspensionForcePoint)
     *
     * @return {@code true} if applied at the configured suspension-force point,
     * {@code false} if applied at the wheel's point of contact
     */
    @Override
    public boolean getEnableSuspensionForcePoint() {
        long settingsVa = targetVa();
        boolean result
                = WheelSettings.getEnableSuspensionForcePoint(settingsVa);

        return result;
    }

    /**
     * Access the lateral friction as a function of the slip angle (in degrees).
     * (native attribute: mLateralFriction)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public LinearCurve getLateralFriction() {
        long settingsVa = targetVa();
        long curveVa = WheelSettingsWv.getLateralFriction(settingsVa);
        LinearCurve result = new LinearCurve(this, curveVa);

        return result;
    }

    /**
     * Access the longitudinal friction as a function of slip ratio. (native
     * attribute: mLongitudinalFriction)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public LinearCurve getLongitudinalFriction() {
        long settingsVa = targetVa();
        long curveVa = WheelSettingsWv.getLongitudinalFriction(settingsVa);
        LinearCurve result = new LinearCurve(this, curveVa);

        return result;
    }

    /**
     * Return the maximum torque that the main brake can exert on the wheel. The
     * settings are unaffected. (native attribute: mMaxBrakeTorque)
     *
     * @return the maximum torque (in Newton meters)
     */
    @Override
    public float getMaxBrakeTorque() {
        long settingsVa = targetVa();
        float result = WheelSettingsWv.getMaxBrakeTorque(settingsVa);

        return result;
    }

    /**
     * Return the maximum torque that the hand brake can exert on the wheel. The
     * settings are unaffected. (native attribute: mMaxHandBrakeTorque)
     *
     * @return the maximum torque (in Newton meters)
     */
    @Override
    public float getMaxHandBrakeTorque() {
        long settingsVa = targetVa();
        float result = WheelSettingsWv.getMaxHandBrakeTorque(settingsVa);

        return result;
    }

    /**
     * Return the maximum steering angle. The settings are unaffected. (native
     * attribute: mMaxSteerAngle)
     *
     * @return the maximum steering angle (in radians)
     */
    @Override
    public float getMaxSteerAngle() {
        long settingsVa = targetVa();
        float result = WheelSettingsWv.getMaxSteerAngle(settingsVa);

        return result;
    }

    /**
     * Copy the location of the attachment point. The settings are unaffected.
     * (native attribute: mPosition)
     *
     * @return a new location vector (in the body's local coordinates)
     */
    @Override
    public Vec3 getPosition() {
        long settingsVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        WheelSettings.getPosition(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Return the radius of the wheel. The settings are unaffected. (native
     * attribute: mRadius)
     *
     * @return the radius (in meters)
     */
    @Override
    public float getRadius() {
        long settingsVa = targetVa();
        float result = WheelSettings.getRadius(settingsVa);

        return result;
    }

    /**
     * Access the type information of the target. (native function: GetRTTI)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public Rtti getRtti() {
        long jpoVa = targetVa();
        long resultVa = SerializableObject.getRtti(jpoVa);
        Rtti result = new Rtti(resultVa);

        return result;
    }

    /**
     * Copy the steering axis (upward direction). The settings are unaffected.
     * (native attribute: mSteeringAxis)
     *
     * @return a new direction vector
     */
    @Override
    public Vec3 getSteeringAxis() {
        long settingsVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        WheelSettings.getSteeringAxis(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the downward direction of the suspension. The settings are
     * unaffected. (native attribute: mSuspensionDirection)
     *
     * @return a new direction vector
     */
    @Override
    public Vec3 getSuspensionDirection() {
        long settingsVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        WheelSettings.getSuspensionDirection(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the location where tire forces will be applied if the force-point
     * option is enabled. The settings are unaffected. (native attribute:
     * mSuspensionForcePoint)
     *
     * @return a new location vector (in body coordinates)
     */
    @Override
    public Vec3 getSuspensionForcePoint() {
        long settingsVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        WheelSettings.getSuspensionForcePoint(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Return the maximum displacement from the attachment point. The settings
     * are unaffected. (native attribute: mSuspensionMaxLength)
     *
     * @return the distance (in meters)
     */
    @Override
    public float getSuspensionMaxLength() {
        long settingsVa = targetVa();
        float result = WheelSettings.getSuspensionMaxLength(settingsVa);

        return result;
    }

    /**
     * Return the minimum displacement from the attachment point. The settings
     * are unaffected. (native attribute: mSuspensionMinLength)
     *
     * @return the distance (in meters)
     */
    @Override
    public float getSuspensionMinLength() {
        long settingsVa = targetVa();
        float result = WheelSettings.getSuspensionMinLength(settingsVa);

        return result;
    }

    /**
     * Return the suspension preload length. The settings are unaffected.
     * (native attribute: mSuspensionPreloadLength)
     *
     * @return the offset (in meters)
     */
    @Override
    public float getSuspensionPreloadLength() {
        long settingsVa = targetVa();
        float result = WheelSettings.getSuspensionPreloadLength(settingsVa);

        return result;
    }

    /**
     * Access the settings for the suspension spring. (native attribute:
     * mSuspensionSpring)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public SpringSettings getSuspensionSpring() {
        long wheelSettingsVa = targetVa();
        long springSettingsVa
                = WheelSettings.getSuspensionSpring(wheelSettingsVa);
        SpringSettings result = new SpringSettings(this, springSettingsVa);

        return result;
    }

    /**
     * Copy the forward direction when steering is neutral. The settings are
     * unaffected. (native attribute: mWheelForward)
     *
     * @return a new direction vector
     */
    @Override
    public Vec3 getWheelForward() {
        long settingsVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        WheelSettings.getWheelForward(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the "up" direction when steering is neutral. The settings are
     * unaffected. (native attribute: mWheelUp)
     *
     * @return a new direction vector
     */
    @Override
    public Vec3 getWheelUp() {
        long settingsVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        WheelSettings.getWheelUp(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Return the width of the wheel. The settings are unaffected. (native
     * attribute: mWidth)
     *
     * @return the width (in meters)
     */
    @Override
    public float getWidth() {
        long settingsVa = targetVa();
        float result = WheelSettings.getWidth(settingsVa);

        return result;
    }

    /**
     * Save the settings to the specified binary stream. The settings are
     * unaffected.
     *
     * @param stream the stream to write to (not {@code null})
     */
    @Override
    public void saveBinaryState(StreamOut stream) {
        long settingsVa = targetVa();
        long streamVa = stream.va();
        WheelSettings.saveBinaryState(settingsVa, streamVa);
    }
    // *************************************************************************
    // Ref methods

    /**
     * Access the targeted settings, if any.
     *
     * @return the pre-existing object, or {@code null} if the reference is
     * empty
     */
    @Override
    public WheelSettingsWv getPtr() {
        WheelSettingsWv result = (WheelSettingsWv) ptr;
        return result;
    }

    /**
     * Return the address of the native {@code WheelSettingsWV}. No objects are
     * affected.
     *
     * @return the virtual address, or zero if the reference is empty
     */
    @Override
    public long targetVa() {
        long refVa = va();
        long result = getPtr(refVa);
        assert result == (ptr == null ? 0L : getPtr().va());

        return result;
    }

    /**
     * Create an additional counted reference to the targeted settings.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public WheelSettingsWvRef toRef() {
        WheelSettingsWvRef result;
        if (ptr == null) {
            result = new WheelSettingsWvRef();
        } else {
            long refVa = va();
            long copyVa = copy(refVa);
            WheelSettingsWv settings = (WheelSettingsWv) ptr;
            result = new WheelSettingsWvRef(copyVa, settings);
        }

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long copy(long refVa);

    native private static long createDefault();

    native static void free(long refVa);

    native private static long getPtr(long refVa);
}
