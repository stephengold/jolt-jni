/*
Copyright (c) 2026 Stephen Gold

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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.StreamOut;
import com.github.stephengold.joltjni.enumerate.ETransmissionMode;

/**
 * Read-only access to a {@code VehicleTransmissionSettings} object. (native
 * type: {@code const VehicleTransmissionSettings})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstVehicleTransmissionSettings
        extends ConstJoltPhysicsObject {
    /**
     * Return how long it takes to release the clutch in {@code Auto} mode. The
     * settings are unaffected.
     *
     * @return the latency (in seconds)
     */
    float getClutchReleaseTime();

    /**
     * Return the total torque applied by the clutch when fully engaged. The
     * settings are unaffected.
     *
     * @return the strength
     */
    float getClutchStrength();

    /**
     * Return the number of engine rotations per gearbox rotation for each
     * forward gear. The first element is for 1st gear. The settings are
     * unaffected.
     *
     * @return a new array
     */
    float[] getGearRatios();

    /**
     * Return the type of transmission. The settings are unaffected.
     *
     * @return an enum value (not null)
     */
    ETransmissionMode getMode();

    /**
     * Return the number of engine rotations per gearbox rotation for each
     * reverse gear. The settings are unaffected.
     *
     * @return a new array
     */
    float[] getReverseGearRatios();

    /**
     * Return the maximum engine RPMs for downshifting in {@code Auto} mode. The
     * settings are unaffected.
     *
     * @return the threshold rate (in revolutions per minute)
     */
    float getShiftDownRpm();

    /**
     * Return the minimum engine RPMs for upshifting in {@code Auto} mode. The
     * settings are unaffected.
     *
     * @return the threshold rate (in revolutions per minute)
     */
    float getShiftUpRpm();

    /**
     * Return the minimum time between shifts in {@code Auto} mode. The settings
     * are unaffected.
     *
     * @return the latency (in seconds)
     */
    float getSwitchLatency();

    /**
     * Return how long it takes to shift gears in {@code Auto} mode. The
     * settings are unaffected.
     *
     * @return the latency (in seconds)
     */
    float getSwitchTime();

    /**
     * Save the settings to the specified binary stream. The settings are
     * unaffected.
     *
     * @param stream the stream to write to (not {@code null})
     */
    void saveBinaryState(StreamOut stream);
}
