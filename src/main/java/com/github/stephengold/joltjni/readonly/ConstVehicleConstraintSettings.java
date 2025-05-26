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
     * Copy the "up" vector. The settings are unaffected. (native attribute:
     * mUp)
     *
     * @return a new direction vector
     */
    Vec3 getUp();

    /**
     * Enumerate all wheel settings. The settings are unaffected. (native
     * attribute: mWheels)
     *
     * @return a new array of pre-existing objects
     */
    ConstWheelSettings[] getWheels();
}
