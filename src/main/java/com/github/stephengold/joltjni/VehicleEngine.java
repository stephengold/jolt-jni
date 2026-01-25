/*
Copyright (c) 2025-2026 Stephen Gold

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
 * The engine of a {@code VehicleController}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class VehicleEngine extends VehicleEngineSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param engineVa the virtual address of the native object to assign (not
     * zero)
     */
    VehicleEngine(JoltPhysicsObject container, long engineVa) {
        super(container, engineVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether the vehicle is allowed to sleep.
     *
     * @return {@code true} if allowed, otherwise {@code false}
     */
    public boolean allowSleep() {
        long engineVa = va();
        boolean result = allowSleep(engineVa);

        return result;
    }

    /**
     * Update the engine RPM for damping.
     *
     * @param deltaTime the amount of time between updates (in seconds, &ge;0)
     */
    public void applyDamping(float deltaTime) {
        long engineVa = va();
        applyDamping(engineVa, deltaTime);
    }

    /**
     * Apply the specified torque to the engine.
     *
     * @param torque the torque to apply (in Newton meters)
     * @param deltaTime the amount of time between updates (in seconds, &ge;0)
     */
    public void applyTorque(float torque, float deltaTime) {
        long engineVa = va();
        applyTorque(engineVa, torque, deltaTime);
    }

    /**
     * Clamp the engine's RPM between its minimum and maximum values.
     */
    public void clampRpm() {
        long engineVa = va();
        clampRpm(engineVa);
    }

    /**
     * Return the engine's rotation rate in SI units.
     *
     * @return the speed (in radians per second)
     */
    public float getAngularVelocity() {
        long engineVa = va();
        float result = getAngularVelocity(engineVa);

        return result;
    }

    /**
     * Return the engine's rotation rate in traditional units.
     *
     * @return the rate (in revolutions per minute)
     */
    public float getCurrentRpm() {
        long engineVa = va();
        float result = getCurrentRpm(engineVa);

        return result;
    }

    /**
     * Return how much torque the engine can supply for the specified
     * acceleration.
     *
     * @param acceleration the position of the accelerator pedal (&ge;0, &le;1)
     * @return the available torque (in Newton meters)
     */
    public float getTorque(float acceleration) {
        long engineVa = va();
        float result = getTorque(engineVa, acceleration);

        return result;
    }

    /**
     * Alter the engine's rotation rate.
     *
     * @param rpm the desired rate (in revolutions per minute)
     */
    public void setCurrentRpm(float rpm) {
        long engineVa = va();
        setCurrentRpm(engineVa, rpm);
    }
    // *************************************************************************
    // native private methods

    native private static boolean allowSleep(long engineVa);

    native private static void applyDamping(long engineVa, float deltaTime);

    native private static void applyTorque(
            long engineVa, float torque, float deltaTime);

    native private static void clampRpm(long engineVa);

    native private static float getAngularVelocity(long engineVa);

    native private static float getCurrentRpm(long engineVa);

    native private static float getTorque(long engineVa, float acceleration);

    native private static void setCurrentRpm(long engineVa, float rpm);
}
