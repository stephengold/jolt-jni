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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to a {@code VehicleConstraintSettings} object. (native type:
 * const VehicleConstraintSettings)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstVehicleConstraintSettings
        extends ConstConstraintSettings {
    /**
     * Access the settings for the specified anti-roll bar. The settings are
     * unaffected.
     *
     * @param barIndex the index of the anti-roll bar to access (&ge;0,
     * &lt;numBars)
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstVehicleAntiRollBar getAntiRollBar(int barIndex);

    /**
     * Access the controller settings. The constraint settings are unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstVehicleControllerSettings getController();

    /**
     * Copy the "forward" vector. The settings are unaffected.
     *
     * @return a new direction vector
     */
    Vec3 getForward();

    /**
     * Return the vehicle's maximum pitch/roll angle. The settings are
     * unaffected.
     *
     * @return the limit angle (in radians)
     */
    float getMaxPitchRollAngle();

    /**
     * Count the anti-roll bars. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    int getNumAntiRollBars();

    /**
     * Count the wheels. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    int getNumWheels();

    /**
     * Copy the "up" vector. The settings are unaffected.
     *
     * @return a new direction vector
     */
    Vec3 getUp();

    /**
     * Access the settings of the specified wheel.
     *
     * @param wheelIndex which wheel (&ge;0, &lt;numWheels)
     * @return the pre-existing object
     */
    ConstWheelSettings getWheel(int wheelIndex);

    /**
     * Enumerate all wheel settings. The settings are unaffected.
     *
     * @return a new array of pre-existing objects
     */
    ConstWheelSettings[] getWheels();
}
