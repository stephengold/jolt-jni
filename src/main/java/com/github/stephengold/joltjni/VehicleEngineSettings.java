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
import com.github.stephengold.joltjni.readonly.ConstVehicleEngineSettings;

/**
 * Settings used to configure the engine of a {@code WheeledVehicleController}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class VehicleEngineSettings
        extends JoltPhysicsObject
        implements ConstVehicleEngineSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public VehicleEngineSettings() {
        long settingsVa = createDefault();
        setVirtualAddress(settingsVa, () -> free(settingsVa));
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    VehicleEngineSettings(JoltPhysicsObject container, long settingsVa) {
        super(container, settingsVa);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public VehicleEngineSettings(ConstVehicleEngineSettings original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa, () -> free(copyVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the angular damping factor. (native attribute: mAngularDamping)
     *
     * @param damping the desired damping factor (per second, default=0.2)
     */
    public void setAngularDamping(float damping) {
        long settingsVa = va();
        setAngularDamping(settingsVa, damping);
    }

    /**
     * Alter the engine's rotational inertia. (native attribute: mInertia)
     *
     * @param inertia the desired inertia (in kg.meter^2, default=0.5)
     */
    public void setInertia(float inertia) {
        long settingsVa = va();
        setInertia(settingsVa, inertia);
    }

    /**
     * Alter the maximum rotation rate. (native attribute: mMaxRPM)
     *
     * @param rpm the desired limit (in revolutions per minute, default=6000)
     */
    public void setMaxRpm(float rpm) {
        long settingsVa = va();
        setMaxRpm(settingsVa, rpm);
    }

    /**
     * Alter the maximum torque. (native attribute: mMaxTorque)
     *
     * @param torque the desired limit (in Newton meters, default=500)
     */
    public void setMaxTorque(float torque) {
        long settingsVa = va();
        setMaxTorque(settingsVa, torque);
    }

    /**
     * Alter the minimum rotation rate to avoid stalling. (native attribute:
     * mMinRPM)
     *
     * @param rpm the desired limit (in revolutions per minute, default=1000)
     */
    public void setMinRpm(float rpm) {
        long settingsVa = va();
        setMinRpm(settingsVa, rpm);
    }

    /**
     * Copy the specified curve to the normalized-torque curve. (native
     * attribute: mNormalizedTorque)
     *
     * @param curve the curve to copy (not {@code null}, unaffected)
     */
    public void setNormalizedTorque(ConstLinearCurve curve) {
        long settingsVa = va();
        long curveVa = curve.targetVa();
        setNormalizedTorque(settingsVa, curveVa);
    }
    // *************************************************************************
    // ConstVehicleEngineSettings methods

    /**
     * Return the angular damping factor. The settings are unaffected. (native
     * attribute: mAngularDamping)
     *
     * @return the damping factor (per second)
     */
    @Override
    public float getAngularDamping() {
        long settingsVa = va();
        float result = getAngularDamping(settingsVa);

        return result;
    }

    /**
     * Return the engine's rotational inertia. The settings are unaffected.
     * (native attribute: mInertia)
     *
     * @return the inertia (in kilogram.meter^2)
     */
    @Override
    public float getInertia() {
        long settingsVa = va();
        float result = getInertia(settingsVa);

        return result;
    }

    /**
     * Return the maximum rotation rate. The settings are unaffected. (native
     * attribute: mMaxRPM)
     *
     * @return the limit (in revolutions per minute)
     */
    @Override
    public float getMaxRpm() {
        long settingsVa = va();
        float result = getMaxRpm(settingsVa);

        return result;
    }

    /**
     * Return the maximum torque. The settings are unaffected. (native
     * attribute: mMaxTorque)
     *
     * @return the limit (in Newton meters)
     */
    @Override
    public float getMaxTorque() {
        long settingsVa = va();
        float result = getMaxTorque(settingsVa);

        return result;
    }

    /**
     * Return the minimum rotation rate to avoid stalling. The settings are
     * unaffected. (native attribute: mMinRPM)
     *
     * @return the limit (in revolutions per minute)
     */
    @Override
    public float getMinRpm() {
        long settingsVa = va();
        float result = getMinRpm(settingsVa);

        return result;
    }

    /**
     * Access the normalized torque as a function of normalized RPM. (native
     * attribute: mNormalizedTorque)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public LinearCurve getNormalizedTorque() {
        long settingsVa = va();
        long curveVa = getNormalizedTorque(settingsVa);
        LinearCurve result = new LinearCurve(this, curveVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static void free(long settingsVa);

    native private static float getAngularDamping(long settingsVa);

    native private static float getInertia(long settingsVa);

    native private static float getMaxRpm(long settingsVa);

    native private static float getMaxTorque(long settingsVa);

    native private static float getMinRpm(long settingsVa);

    native private static long getNormalizedTorque(long settingsVa);

    native private static void setAngularDamping(
            long settingsVa, float damping);

    native private static void setInertia(long settingsVa, float inertia);

    native private static void setMaxRpm(long settingsVa, float rpm);

    native private static void setMaxTorque(long settingsVa, float torque);

    native private static void setMinRpm(long settingsVa, float rpm);

    native private static void setNormalizedTorque(
            long settingsVa, long curveVa);
}
