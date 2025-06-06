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

/**
 * Settings used to configure the engine of a {@code WheeledVehicleController}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class VehicleEngineSettings extends JoltPhysicsObject {
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
    public VehicleEngineSettings(VehicleEngineSettings original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa, () -> free(copyVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the angular damping factor. The settings are unaffected. (native
     * attribute: mAngularDamping)
     *
     * @return the damping factor (per second)
     */
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
    public float getMaxRpm() {
        long settingsVa = va();
        float result = getMaxRpm(settingsVa);

        return result;
    }

    /**
     * Return the maximum torque. The settings are unaffected. (native
     * attribute: mMaxTorque)
     *
     * @return the limit (in Newton.meters)
     */
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
    public float getMinRpm() {
        long settingsVa = va();
        float result = getMinRpm(settingsVa);

        return result;
    }

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
     * @param torque the desired limit (in Newton.meters, default=500)
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

    native private static void setAngularDamping(
            long settingsVa, float damping);

    native private static void setInertia(long settingsVa, float inertia);

    native private static void setMaxRpm(long settingsVa, float rpm);

    native private static void setMaxTorque(long settingsVa, float torque);

    native private static void setMinRpm(long settingsVa, float rpm);
}
