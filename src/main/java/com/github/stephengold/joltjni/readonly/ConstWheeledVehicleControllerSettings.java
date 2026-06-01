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

import com.github.stephengold.joltjni.VehicleDifferentialSettings;
import com.github.stephengold.joltjni.VehicleEngineSettings;
import com.github.stephengold.joltjni.VehicleTransmissionSettings;

/**
 * Read-only access to a {@code WheeledVehicleControllerSettings} object.
 * (native type: const WheeledVehicleControllerSettings)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstWheeledVehicleControllerSettings
        extends ConstVehicleControllerSettings {
    /**
     * Access the settings for the specified differential.
     *
     * @param index the index of the differential to access (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    VehicleDifferentialSettings getDifferential(int index);

    /**
     * Return the ratio of the max/min average wheel speed for each
     * differential. The settings are unaffected.
     *
     * @return the ratio
     */
    float getDifferentialLimitedSlipRatio();

    /**
     * Access the engine settings.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    VehicleEngineSettings getEngine();

    /**
     * Count how many differentials the vehicle will have. The settings are
     * unaffected.
     *
     * @return the count (&ge;0)
     */
    int getNumDifferentials();

    /**
     * Access the transmission (gearbox) settings.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    VehicleTransmissionSettings getTransmission();
}
