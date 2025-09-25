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
 * Settings used to construct a {@code TrackedVehicleController}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class TrackedVehicleControllerSettings
        extends VehicleControllerSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public TrackedVehicleControllerSettings() {
        long settingsVa = createDefault();
        setVirtualAddressAsCoOwner(settingsVa);
    }

    /**
     * Instantiate a settings object with the specified native object assigned
     * but not owned.
     *
     * @param controllerSettingsVa the virtual address of the native object to
     * assign (not zero)
     */
    TrackedVehicleControllerSettings(long controllerSettingsVa) {
        setVirtualAddressAsCoOwner(controllerSettingsVa);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public TrackedVehicleControllerSettings(
            TrackedVehicleControllerSettings original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddressAsCoOwner(copyVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Count how many tracks the vehicle will have. The settings are unaffected.
     * (native attribute: mTracks)
     *
     * @return the count (&ge;0)
     */
    public int getNumTracks() {
        long vehicleSettingsVa = va();
        int result = getNumTracks(vehicleSettingsVa);

        return result;
    }

    /**
     * Access the settings for the specified track. (native field: mTracks)
     *
     * @param index the index of the track to access (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public VehicleTrackSettings getTrack(int index) {
        long vehicleSettingsVa = va();
        long trackVa = getTrack(vehicleSettingsVa, index);
        VehicleTrackSettings result = new VehicleTrackSettings(this, trackVa);

        return result;
    }
    // *************************************************************************
    // VehicleControllerSettings methods

    /**
     * Identify the type of {@code VehicleController} to be constructed.
     *
     * @return a numeric code
     */
    @Override
    int controllerTypeOrdinal() {
        return VehicleController.trackedVehicleType;
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long vehicleSettingsVa);

    native private static long createDefault();

    native private static int getNumTracks(long vehicleSettingsVa);

    native private static long getTrack(long vehicleSettingsVa, int index);
}
