/*
Copyright (c) 2025 Stephen Gold

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
 * The transmission (gearbox) of a {@code VehicleController}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class VehicleTransmission extends VehicleTransmissionSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a settings with the specified native object assigned but not
     * owned.
     *
     * @param container the containing object, or {@code null} if none
     * @param transmissionVa the virtual address of the native object to assign
     * (not zero)
     */
    VehicleTransmission(JoltPhysicsObject container, long transmissionVa) {
        super(container, transmissionVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether to allow sleeping only when the transmission is idle.
     *
     * @return {@code true} if allowed, otherwise {@code false}
     */
    public boolean allowSleep() {
        long transmissionVa = va();
        boolean result = allowSleep(transmissionVa);

        return result;
    }

    /**
     * Return how friction the clutch gives. The settings are unaffected.
     *
     * @return the amount of friction (&ge;0, &le;1, 1=full friction)
     */
    public float getClutchFriction() {
        long transmissionVa = va();
        float result = getClutchFriction(transmissionVa);

        return result;
    }

    /**
     * Return the current gear.
     *
     * @return the gear number (-1=reverse, 0=neutral, 1=1st gear)
     */
    public int getCurrentGear() {
        long transmissionVa = va();
        int result = getCurrentGear(transmissionVa);

        return result;
    }

    /**
     * Return transmission ratio based on the current gear. The settings are
     * unaffected.
     *
     * @return the ratio between engine and differential
     */
    public float getCurrentRatio() {
        long transmissionVa = va();
        float result = getCurrentRatio(transmissionVa);

        return result;
    }

    /**
     * Test whether the transmission is currently switching gears.
     *
     * @return {@code true} if switching, otherwise {@code false}
     */
    public boolean isSwitchingGear() {
        long transmissionVa = va();
        boolean result = isSwitchingGear(transmissionVa);

        return result;
    }

    /**
     * Alter the input from the driver (only relevant in manual mode).
     *
     * @param currentGear the selected gear (-1=reverse, 0=neutral, 1=1st gear)
     * @param clutchFriction the amount of clutch friction (0=none, 1=full
     * friction)
     */
    public void set(int currentGear, float clutchFriction) {
        long transmissionVa = va();
        set(transmissionVa, currentGear, clutchFriction);
    }

    /**
     * Update the gear and clutch friction if the transmission is in auto mode.
     *
     * @param deltaTime the time step (in seconds, &ge;0)
     * @param rpm the engine's current rate (in revolutions per minute)
     * @param forwardInput positive if the driver wants to go forward, otherwise
     * negative
     * @param canShiftUp {@code true} if the driver wants to upshift, otherwise
     * {@code false}
     */
    public void update(float deltaTime, float rpm, float forwardInput,
            boolean canShiftUp) {
        long transmissionVa = va();
        update(transmissionVa, deltaTime, rpm, forwardInput, canShiftUp);
    }
    // *************************************************************************
    // native private methods

    native private static boolean allowSleep(long transmissionVa);

    native private static float getClutchFriction(long transmissionVa);

    native private static int getCurrentGear(long transmissionVa);

    native private static float getCurrentRatio(long transmissionVa);

    native private static boolean isSwitchingGear(long transmissionVa);

    native private static void set(
            long transmissionVa, int currentGear, float clutchFriction);

    native private static void update(long transmissionVa, float deltaTime,
            float rpm, float forwardInput, boolean canShiftUp);
}
