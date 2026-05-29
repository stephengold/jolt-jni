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

/**
 * Settings used to construct a {@code MotorcycleController}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class MotorcycleControllerSettings
        extends WheeledVehicleControllerSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public MotorcycleControllerSettings() {
        super(false);
        long settingsVa = createDefault();
        setVirtualAddressAsCoOwner(settingsVa);
    }

    /**
     * Instantiate a settings object with the specified native object assigned.
     *
     * @param controllerSettingsVa the virtual address of the native object to
     * assign (not zero)
     */
    MotorcycleControllerSettings(long controllerSettingsVa) {
        super(false);
        setVirtualAddressAsCoOwner(controllerSettingsVa);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public MotorcycleControllerSettings(
            MotorcycleControllerSettings original) {
        super(false);
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddressAsCoOwner(copyVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the lean smoothing factor. The settings are unaffected. (native
     * attribute: mLeanSmoothingFactor)
     *
     * @return the factor
     */
    public float getLeanSmoothingFactor() {
        long settingsVa = va();
        float result = getLeanSmoothingFactor(settingsVa);

        return result;
    }

    /**
     * Return the spring constant for the lean spring. The settings are
     * unaffected. (native attribute: mLeanSpringConstant)
     *
     * @return the spring constant
     */
    public float getLeanSpringConstant() {
        long settingsVa = va();
        float result = getLeanSpringConstant(settingsVa);

        return result;
    }

    /**
     * Return the damping constant for the lean spring. The settings are
     * unaffected. (native attribute: mLeanSpringDamping)
     *
     * @return the damping constant
     */
    public float getLeanSpringDamping() {
        long settingsVa = va();
        float result = getLeanSpringDamping(settingsVa);

        return result;
    }

    /**
     * Return the coefficient of additional force to control the lean angle. The
     * settings are unaffected. (native attribute:
     * mLeanSpringIntegrationCoefficient)
     *
     * @return the integration coefficient
     */
    public float getLeanSpringIntegrationCoefficient() {
        long settingsVa = va();
        float result = getLeanSpringIntegrationCoefficient(settingsVa);

        return result;
    }

    /**
     * Return the rate of decay of the angle integral when the wheels are
     * unsupported. The settings are unaffected. (native attribute:
     * mLeanSpringIntegrationCoefficientDecay)
     *
     * @return the rate of decay (per second)
     */
    public float getLeanSpringIntegrationCoefficientDecay() {
        long settingsVa = va();
        float result = getLeanSpringIntegrationCoefficientDecay(settingsVa);

        return result;
    }

    /**
     * Return the maximum lean angle (during turns). The settings are
     * unaffected. (native attribute: mMaxLeanAngle)
     *
     * @return the angle (in radians)
     */
    public float getMaxLeanAngle() {
        long settingsVa = va();
        float result = getMaxLeanAngle(settingsVa);

        return result;
    }

    /**
     * Copy the argument to the current settings.
     *
     * @param source the settings to copy (not {@code null}, unaffected)
     */
    public void set(MotorcycleControllerSettings source) {
        long targetVa = va();
        long sourceVa = source.va();
        assign(targetVa, sourceVa);
    }

    /**
     * Alter the lean smoothing factor. (native attribute: mLeanSmoothingFactor)
     *
     * @param factor the desired factor (default=0.8)
     */
    public void setLeanSmoothingFactor(float factor) {
        long settingsVa = va();
        setLeanSmoothingFactor(settingsVa, factor);
    }

    /**
     * Alter the spring constant for the lean spring. (native attribute:
     * mLeanSpringConstant)
     *
     * @param k the desired spring constant (default=5000)
     */
    public void setLeanSpringConstant(float k) {
        long settingsVa = va();
        setLeanSpringConstant(settingsVa, k);
    }

    /**
     * Alter the damping constant for the lean spring. (native attribute:
     * mLeanSpringDamping)
     *
     * @param damping the desired damping constant (default=1000)
     */
    public void setLeanSpringDamping(float damping) {
        long settingsVa = va();
        setLeanSpringDamping(settingsVa, damping);
    }

    /**
     * Alter the coefficient of additional force to control the lean angle.
     * (native attribute: mLeanSpringIntegrationCoefficient)
     *
     * @param coefficient the desired integration coefficient (default=0)
     */
    public void setLeanSpringIntegrationCoefficient(float coefficient) {
        long settingsVa = va();
        setLeanSpringIntegrationCoefficient(settingsVa, coefficient);
    }

    /**
     * Alter the rate of decay of the angle integral when the wheels are
     * unsupported. (native attribute: mLeanSpringIntegrationCoefficientDecay)
     *
     * @param decay the desired rate of decay (per second, default=4)
     */
    public void setLeanSpringIntegrationCoefficientDecay(float decay) {
        long settingsVa = va();
        setLeanSpringIntegrationCoefficientDecay(settingsVa, decay);
    }

    /**
     * Alter the maximum lean angle (during turns). (native attribute:
     * mMaxLeanAngle)
     *
     * @param angle the desired angle (in radians, default=Pi/4)
     */
    public void setMaxLeanAngle(float angle) {
        long settingsVa = va();
        setMaxLeanAngle(settingsVa, angle);
    }
    // *************************************************************************
    // WheeledVehicleControllerSettings methods

    /**
     * Identify the type of {@code VehicleController} to be constructed.
     *
     * @return a numeric code
     */
    @Override
    int controllerTypeOrdinal() {
        return VehicleController.motorcycleType;
    }
    // *************************************************************************
    // native private methods

    native private static void assign(long targetVa, long sourceVa);

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static float getLeanSmoothingFactor(long settingsVa);

    native private static float getLeanSpringConstant(long settingsVa);

    native private static float getLeanSpringDamping(long settingsVa);

    native private static float getLeanSpringIntegrationCoefficient(
            long settingsVa);

    native private static float getLeanSpringIntegrationCoefficientDecay(
            long settingsVa);

    native private static float getMaxLeanAngle(long settingsVa);

    native private static void setLeanSmoothingFactor(
            long settingsVa, float factor);

    native private static void setLeanSpringConstant(long settingsVa, float k);

    native private static void setLeanSpringDamping(
            long settingsVa, float damping);

    native private static void setLeanSpringIntegrationCoefficient(
            long settingsVa, float coefficient);

    native private static void setLeanSpringIntegrationCoefficientDecay(
            long settingsVa, float decay);

    native private static void setMaxLeanAngle(long settingsVa, float angle);
}
