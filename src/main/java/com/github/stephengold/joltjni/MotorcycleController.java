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
 * Control the acceleration and balance of a motorcycle, in addition to its
 * acceleration and deceleration.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class MotorcycleController extends WheeledVehicleController {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param constraint the containing object, or {@code null} if none
     * @param controllerVa the virtual address of the native object to assign
     * (not zero)
     */
    MotorcycleController(VehicleConstraint constraint, long controllerVa) {
        super(constraint, controllerVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Enable or disable the lean spring.
     *
     * @param enable {@code true} to enable the spring, {@code false} to disable
     * it
     */
    public void enableLeanController(boolean enable) {
        long controllerVa = va();
        enableLeanController(controllerVa, enable);
    }

    /**
     * Enable or disable the lean steering limit.
     *
     * @param enable {@code true} to limit steering based on speed,
     * {@code false} to stop limiting it
     */
    public void enableLeanSteeringLimit(boolean enable) {
        long controllerVa = va();
        enableLeanSteeringLimit(controllerVa, enable);
    }

    /**
     * Return the smoothing factor for the lean angle. The vehicle controller is
     * unaffected.
     *
     * @return the factor (0=no smoothing, 1=lean angle frozen)
     */
    public float getLeanSmoothingFactor() {
        long controllerVa = va();
        float result = getLeanSmoothingFactor(controllerVa);

        return result;
    }

    /**
     * Return the stiffness of the lean spring. The vehicle controller is
     * unaffected.
     *
     * @return the spring constant
     */
    public float getLeanSpringConstant() {
        long controllerVa = va();
        float result = getLeanSpringConstant(controllerVa);

        return result;
    }

    /**
     * Return the damping constant of the lean spring. The vehicle controller is
     * unaffected.
     *
     * @return the damping constant
     */
    public float getLeanSpringDamping() {
        long controllerVa = va();
        float result = getLeanSpringDamping(controllerVa);

        return result;
    }

    /**
     * Return the coefficient of the integral term. The vehicle controller is
     * unaffected.
     *
     * @return the force coefficient
     */
    public float getLeanSpringIntegrationCoefficient() {
        long controllerVa = va();
        float result = getLeanSpringIntegrationCoefficient(controllerVa);

        return result;
    }

    /**
     * Return the decay rate of the integral term when the motorcycle is
     * unsupported. The vehicle controller is unaffected.
     *
     * @return the rate (per second)
     */
    public float getLeanSpringIntegrationCoefficientDecay() {
        long controllerVa = va();
        float result = getLeanSpringIntegrationCoefficientDecay(controllerVa);

        return result;
    }

    /**
     * Return the distance between the front and back wheels. The vehicle
     * controller is unaffected.
     *
     * @return the distance (in meters)
     */
    public float getWheelBase() {
        long controllerVa = va();
        float result = getWheelBase(controllerVa);

        return result;
    }

    /**
     * Test whether the lean spring is enabled. The vehicle controller is
     * unaffected.
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    public boolean isLeanControllerEnabled() {
        long controllerVa = va();
        boolean result = isLeanControllerEnabled(controllerVa);

        return result;
    }

    /**
     * Test whether the lean steering limit is enabled. The vehicle controller
     * is unaffected.
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    public boolean isLeanSteeringLimitEnabled() {
        long controllerVa = va();
        boolean result = isLeanSteeringLimitEnabled(controllerVa);

        return result;
    }

    /**
     * Alter the smoothing factor for the lean angle.
     *
     * @param factor the desired smoothing factor (0=no smoothing, 1=lean angle
     * frozen)
     */
    public void setLeanSmoothingFactor(float factor) {
        long controllerVa = va();
        setLeanSmoothingFactor(controllerVa, factor);
    }

    /**
     * Alter the stiffness of the lean spring.
     *
     * @param constant the desired spring constant
     */
    public void setLeanSpringConstant(float constant) {
        long controllerVa = va();
        setLeanSpringConstant(controllerVa, constant);
    }

    /**
     * Alter the damping constant of the lean spring.
     *
     * @param damping the desired damping constant
     */
    public void setLeanSpringDamping(float damping) {
        long controllerVa = va();
        setLeanSpringDamping(controllerVa, damping);
    }

    /**
     * Return the coefficient of the integral term.
     *
     * @param coefficient the desired force coefficient
     */
    public void setLeanSpringIntegrationCoefficient(float coefficient) {
        long controllerVa = va();
        setLeanSpringIntegrationCoefficient(controllerVa, coefficient);
    }

    /**
     * Alter the decay rate of the integral term when the motorcycle is
     * unsupported.
     *
     * @param decay the desired rate (per second)
     */
    public void setLeanSpringIntegrationCoefficientDecay(float decay) {
        long controllerVa = va();
        setLeanSpringIntegrationCoefficientDecay(controllerVa, decay);
    }
    // *************************************************************************
    // native private methods

    native private static void enableLeanController(
            long controllerVa, boolean enable);

    native private static void enableLeanSteeringLimit(
            long controllerVa, boolean enable);

    native private static float getLeanSmoothingFactor(long controllerVa);

    native private static float getLeanSpringConstant(long controllerVa);

    native private static float getLeanSpringDamping(long controllerVa);

    native private static float getLeanSpringIntegrationCoefficient(
            long controllerVa);

    native private static float getLeanSpringIntegrationCoefficientDecay(
            long controllerVa);

    native private static float getWheelBase(long controllerVa);

    native private static boolean isLeanControllerEnabled(long controllerVa);

    native private static boolean isLeanSteeringLimitEnabled(long controllerVa);

    native private static void setLeanSmoothingFactor(
            long controllerVa, float factor);

    native private static void setLeanSpringConstant(
            long controllerVa, float constant);

    native private static void setLeanSpringDamping(
            long controllerVa, float damping);

    native private static void setLeanSpringIntegrationCoefficient(
            long controllerVa, float coefficient);

    native private static void setLeanSpringIntegrationCoefficientDecay(
            long controllerVa, float decay);
}
