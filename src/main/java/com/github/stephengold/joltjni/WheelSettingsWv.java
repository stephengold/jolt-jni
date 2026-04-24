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

/**
 * Settings used to construct a {@code WheelWv}. (native type: WheelSettingsWV)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class WheelSettingsWv
        extends WheelSettings
        implements ConstWheelSettingsWv {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public WheelSettingsWv() {
        long settingsVa = createDefault();
        long refVa = toRef(settingsVa);
        Runnable freeingAction = () -> WheelSettingsWvRef.free(refVa);
        setVirtualAddress(settingsVa, freeingAction);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public WheelSettingsWv(ConstWheelSettingsWv original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        long refVa = toRef(copyVa);
        Runnable freeingAction = () -> WheelSettingsWvRef.free(refVa);
        setVirtualAddress(copyVa, freeingAction);
    }

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    WheelSettingsWv(long settingsVa) {
        long refVa = toRef(settingsVa);
        Runnable freeingAction = () -> WheelSettingsWvRef.free(refVa);
        setVirtualAddress(settingsVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the angular damping coefficient. (native attribute:
     * mAngularDamping)
     *
     * @param damping the desired coefficient (in units of per second, &ge;0,
     * default=0.2)
     */
    public void setAngularDamping(float damping) {
        long settingsVa = va();
        setAngularDamping(settingsVa, damping);
    }

    /**
     * Alter the moment of inertia around the wheel's rolling axis. (native
     * attribute: mInertia)
     *
     * @param moment the desired moment of inertia (in kilogram.meters squared,
     * default=0.9)
     */
    public void setInertia(float moment) {
        long settingsVa = va();
        setInertia(settingsVa, moment);
    }

    /**
     * Copy the specified curve to the lateral-friction curve. (native
     * attribute: mLateralFriction)
     *
     * @param curve the curve to copy (not {@code null}, unaffected)
     */
    public void setLateralFriction(ConstLinearCurve curve) {
        long settingsVa = va();
        long curveVa = curve.targetVa();
        setLateralFriction(settingsVa, curveVa);
    }

    /**
     * Copy the specified curve to the longitudinal-friction curve. (native
     * attribute: mLongitudinalFriction)
     *
     * @param curve the curve to copy (not {@code null}, unaffected)
     */
    public void setLongitudinalFriction(ConstLinearCurve curve) {
        long settingsVa = va();
        long curveVa = curve.targetVa();
        setLongitudinalFriction(settingsVa, curveVa);
    }

    /**
     * Alter the maximum torque that the main brake can exert on the wheel.
     * (native attribute: mMaxBrakeTorque)
     *
     * @param torque the desired torque (in Newton meters, default=1500)
     */
    public void setMaxBrakeTorque(float torque) {
        long settingsVa = va();
        setMaxBrakeTorque(settingsVa, torque);
    }

    /**
     * Alter the maximum torque that the hand brake can exert on the wheel.
     * (native attribute: mMaxHandBrakeTorque)
     *
     * @param torque the desired torque (in Newton meters, default=4000)
     */
    public void setMaxHandBrakeTorque(float torque) {
        long settingsVa = va();
        setMaxHandBrakeTorque(settingsVa, torque);
    }

    /**
     * Alter the maximum steering angle. (native attribute: mMaxSteerAngle)
     *
     * @param angle the desired maximum steering angle (in radians,
     * default=7*Pi/18)
     */
    public void setMaxSteerAngle(float angle) {
        long settingsVa = va();
        setMaxSteerAngle(settingsVa, angle);
    }
    // *************************************************************************
    // ConstWheelSettingsWv methods

    /**
     * Return the angular damping coefficient. The settings are unaffected.
     * (native attribute: mAngularDamping)
     *
     * @return the coefficient (in units of per second, &ge;0)
     */
    @Override
    public float getAngularDamping() {
        long settingsVa = va();
        float result = getAngularDamping(settingsVa);

        return result;
    }

    /**
     * Return the moment of inertia around the wheel's rolling axis. The
     * settings are unaffected. (native attribute: mInertia)
     *
     * @return the moment of inertia (in kilogram.meters squared, &ge;0)
     */
    @Override
    public float getInertia() {
        long settingsVa = va();
        float result = getInertia(settingsVa);

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
        long settingsVa = va();
        long curveVa = getLateralFriction(settingsVa);
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
        long settingsVa = va();
        long curveVa = getLongitudinalFriction(settingsVa);
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
        long settingsVa = va();
        float result = getMaxBrakeTorque(settingsVa);

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
        long settingsVa = va();
        float result = getMaxHandBrakeTorque(settingsVa);

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
        long settingsVa = va();
        float result = getMaxSteerAngle(settingsVa);

        return result;
    }
    // *************************************************************************
    // WheelSettings methods

    /**
     * Count the active references to the native {@code WheelSettingsWv}. The
     * settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long settingsVa = va();
        int result = getRefCount(settingsVa);

        return result;
    }

    /**
     * Mark the native {@code WheelSettingsWV} as embedded.
     */
    @Override
    public void setEmbedded() {
        long settingsVa = va();
        setEmbedded(settingsVa);
    }

    /**
     * Create a counted reference to the current settings.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public WheelSettingsWvRef toRef() {
        long settingsVa = va();
        long refVa = toRef(settingsVa);
        WheelSettingsWvRef result = new WheelSettingsWvRef(refVa, this);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native static float getAngularDamping(long settingsVa);

    native static float getInertia(long settingsVa);

    native static long getLateralFriction(long settingsVa);

    native static long getLongitudinalFriction(long settingsVa);

    native static float getMaxBrakeTorque(long settingsVa);

    native static float getMaxHandBrakeTorque(long settingsVa);

    native static float getMaxSteerAngle(long settingsVa);

    native private static int getRefCount(long settingsVa);

    native static void setAngularDamping(long settingsVa, float damping);

    native static void setInertia(long settingsVa, float moment);

    native private static void setEmbedded(long settingsVa);

    native static void setLateralFriction(long settingsVa, long curveVa);

    native static void setLongitudinalFriction(long settingsVa, long curveVa);

    native static void setMaxBrakeTorque(long settingsVa, float torque);

    native static void setMaxHandBrakeTorque(long settingsVa, float torque);

    native static void setMaxSteerAngle(long settingsVa, float angle);

    native private static long toRef(long settingsVa);
}
