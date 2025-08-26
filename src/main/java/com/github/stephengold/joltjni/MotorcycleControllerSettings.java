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
        setVirtualAddress(settingsVa); // not owner due to ref counting
    }

    /**
     * Instantiate a settings object with the specified native object assigned
     * but not owned.
     *
     * @param controllerSettingsVa the virtual address of the native object to
     * assign (not zero)
     */
    MotorcycleControllerSettings(long controllerSettingsVa) {
        super(controllerSettingsVa);
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
        setVirtualAddress(copyVa); // not owner due to ref counting
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

    native private static long createCopy(long originalVa);

    native private static long createDefault();
}
