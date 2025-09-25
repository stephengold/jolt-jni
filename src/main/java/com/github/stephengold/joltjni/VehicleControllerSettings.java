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

import com.github.stephengold.joltjni.readonly.ConstVehicleControllerSettings;
import com.github.stephengold.joltjni.template.RefTarget;

/**
 * Settings used to construct a {@code VehicleController}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class VehicleControllerSettings
        extends SerializableObject
        implements ConstVehicleControllerSettings, RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate settings with no native object assigned.
     */
    VehicleControllerSettings() {
    }

    /**
     * Instantiate settings with the specified native object assigned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    VehicleControllerSettings(long settingsVa) {
        setVirtualAddressAsCoOwner(settingsVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Identify the type of {@code VehicleController} to be constructed.
     *
     * @return a numeric code
     */
    int controllerTypeOrdinal() {
        return VehicleController.genericType;
    }

    /**
     * Instantiate a {@code VehicleControllerSettings} given its virtual
     * address.
     *
     * @param settingsVa the virtual address of the native object, or zero
     * @return a new JVM object, or {@code null} if the argument was zero
     */
    static VehicleControllerSettings newSettings(long settingsVa) {
        if (settingsVa == 0L) {
            return null;
        }

        long rttiVa = SerializableObject.getRtti(settingsVa);
        String typeName = Rtti.getName(rttiVa);
        VehicleControllerSettings result;
        switch (typeName) {
            case "MotorcycleControllerSettings":
                result = new MotorcycleControllerSettings(settingsVa);
                break;

            case "TrackedVehicleControllerSettings":
                result = new TrackedVehicleControllerSettings(settingsVa);
                break;

            case "WheeledVehicleControllerSettings":
                result = new WheeledVehicleControllerSettings(settingsVa);
                break;

            default:
                throw new RuntimeException("typeName = " + typeName);
        }

        assert result instanceof SerializableObject;
        return result;
    }

    /**
     * Load the settings from the specified binary stream.
     *
     * @param stream the stream to read from (not null)
     */
    public void restoreBinaryState(StreamIn stream) {
        long settingsVa = va();
        long streamVa = stream.va();
        restoreBinaryState(settingsVa, streamVa);
    }
    // *************************************************************************
    // new protected methods

    /**
     * Assign a native object (assuming there's none already assigned) and
     * designate the JVM object as a co-owner.
     *
     * @param settingsVa the virtual address of the native object to assign
     * (not zero)
     */
    final protected void setVirtualAddressAsCoOwner(long settingsVa) {
        long refVa = toRef(settingsVa);
        Runnable freeingAction = () -> VehicleControllerSettingsRef.free(refVa);
        setVirtualAddress(settingsVa, freeingAction);
    }
    // *************************************************************************
    // ConstVehicleControllerSettings methods

    /**
     * Save the settings to the specified binary stream. The settings are
     * unaffected.
     *
     * @param stream the stream to write to (not null)
     */
    @Override
    public void saveBinaryState(StreamOut stream) {
        long settingsVa = va();
        long streamVa = stream.va();
        saveBinaryState(settingsVa, streamVa);
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native
     * {@code VehicleControllerSettings}. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long settingsVa = va();
        int result = getRefCount(settingsVa);

        return result;
    }

    /**
     * Mark the native {@code VehicleControllerSettings} as embedded.
     */
    @Override
    public void setEmbedded() {
        long settingsVa = va();
        setEmbedded(settingsVa);
    }

    /**
     * Create a counted reference to the native
     * {@code VehicleControllerSettings}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public VehicleControllerSettingsRef toRef() {
        long settingsVa = va();
        long refVa = toRef(settingsVa);
        VehicleControllerSettingsRef result
                = new VehicleControllerSettingsRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static int getRefCount(long settingsVa);

    native private static void restoreBinaryState(
            long settingsVa, long streamVa);

    native private static void saveBinaryState(long settingsVa, long streamVa);

    native private static void setEmbedded(long settingsVa);

    native private static long toRef(long settingsVa);
}
