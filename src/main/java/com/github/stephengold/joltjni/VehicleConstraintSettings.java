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

import com.github.stephengold.joltjni.enumerate.EConstraintSubType;
import com.github.stephengold.joltjni.readonly.ConstVehicleConstraintSettings;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.template.Ref;
import com.github.stephengold.joltjni.template.RefTarget;
import java.util.ArrayList;
import java.util.List;

/**
 * Settings used to construct a {@code VehicleConstraint}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class VehicleConstraintSettings
        extends ConstraintSettings
        implements ConstVehicleConstraintSettings {
    // *************************************************************************
    // fields

    /**
     * references to all wheel settings that have been added, in their order of
     * addition
     */
    final private List<Ref> wheelRefs = new ArrayList<>(4);
    /**
     * reference to the controller settings
     */
    private VehicleControllerSettingsRef controllerRef;
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public VehicleConstraintSettings() {
        long settingsVa = createDefault();
        setVirtualAddress(settingsVa); // not owner due to ref counting
        setSubType(EConstraintSubType.Vehicle);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public VehicleConstraintSettings(ConstVehicleConstraintSettings original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa); // not owner due to ref counting
        setSubType(EConstraintSubType.Vehicle);
    }

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    VehicleConstraintSettings(long settingsVa) {
        super(settingsVa);
        setSubType(EConstraintSubType.Vehicle);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Append the specified wheel settings. (native attribute: mWheels)
     *
     * @param wheelSettingsArray the wheel settings to append
     */
    public void addWheels(WheelSettings... wheelSettingsArray) {
        long constraintSettingsVa = va();
        for (WheelSettings wheelSettings : wheelSettingsArray) {
            Ref ref = wheelSettings.toRef();
            wheelRefs.add(ref);
            long wheelSettingsVa = wheelSettings.va();
            addWheel(constraintSettingsVa, wheelSettingsVa);
        }
    }

    /**
     * Alter how the vehicle accelerates and decelerates. (native attribute:
     * mController)
     *
     * @param controllerSettings the desired settings (not null, default=null)
     */
    public void setController(VehicleControllerSettings controllerSettings) {
        this.controllerRef = controllerSettings.toRef();
        long constraintSettingsVa = va();
        long controllerSettingsVa = controllerSettings.va();
        setController(constraintSettingsVa, controllerSettingsVa);

        int controllerType = controllerSettings.controllerTypeOrdinal();
        ConstraintSettings.setControllerType(
                constraintSettingsVa, controllerType);
    }

    /**
     * Alter the forward direction. (native attribute: mForward)
     *
     * @param forward the desired forward direction (not null, unaffected,
     * default=(0,0,1))
     */
    public void setForward(Vec3Arg forward) {
        long settingsVa = va();
        float dx = forward.getX();
        float dy = forward.getY();
        float dz = forward.getZ();
        setForward(settingsVa, dx, dy, dz);
    }

    /**
     * Alter the vehicle's maximum pitch/roll angle. (native attribute:
     * mMaxPitchRollAngle)
     *
     * @param angle the desired limit angle (in radians, default=Pi)
     */
    public void setMaxPitchRollAngle(float angle) {
        long settingsVa = va();
        setMaxPitchRollAngle(settingsVa, angle);
    }

    /**
     * Alter the number of anti-roll bars. (native attribute: mAntiRollBars)
     *
     * @param count the desired number (&ge;0, default=0)
     */
    public void setNumAntiRollBars(int count) {
        long settingsVa = va();
        setNumAntiRollBars(settingsVa, count);
    }

    /**
     * Alter the up direction. (native attribute: mUp)
     *
     * @param up the desired up direction (not null, unaffected,
     * default=(0,1,0))
     */
    public void setUp(Vec3Arg up) {
        long settingsVa = va();
        float dx = up.getX();
        float dy = up.getY();
        float dz = up.getZ();
        setUp(settingsVa, dx, dy, dz);
    }
    // *************************************************************************
    // ConstVehicleConstraintsSettings methods

    /**
     * Access the settings for the specified anti-roll bar. (native field:
     * mAntiRollBars)
     *
     * @param barIndex the index of the anti-roll bar to access (&ge;0,
     * &lt;numBars)
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public VehicleAntiRollBar getAntiRollBar(int barIndex) {
        long settingsVa = va();
        long barVa = getAntiRollBar(settingsVa, barIndex);
        VehicleAntiRollBar result = new VehicleAntiRollBar(this, barVa);

        return result;
    }

    /**
     * Access the controller settings.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public VehicleControllerSettings getController() {
        VehicleControllerSettings result;
        if (controllerRef == null) {
            result = null;
        } else {
            result = controllerRef.getPtr();
        }

        return result;
    }

    /**
     * Copy the "forward" vector. The settings are unaffected. (native
     * attribute: mForward)
     *
     * @return a new direction vector
     */
    @Override
    public Vec3 getForward() {
        long settingsVa = va();
        float dx = getForwardX(settingsVa);
        float dy = getForwardY(settingsVa);
        float dz = getForwardZ(settingsVa);
        Vec3 result = new Vec3(dx, dy, dz);

        return result;
    }

    /**
     * Return the vehicle's maximum pitch/roll angle. The settings are
     * unaffected. (native attribute: mMaxPitchRollAngle)
     *
     * @return the limit angle (in radians)
     */
    @Override
    public float getMaxPitchRollAngle() {
        long settingsVa = va();
        float result = getMaxPitchRollAngle(settingsVa);

        return result;
    }

    /**
     * Count the anti-roll bars. The settings are unaffected. (native attribute:
     * mAntiRollBars)
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getNumAntiRollBars() {
        long settingsVa = va();
        int result = getNumAntiRollBars(settingsVa);

        return result;
    }

    /**
     * Count the wheels. The settings are unaffected. (native attribute:
     * mWheels)
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getNumWheels() {
        int result = wheelRefs.size();
        return result;
    }

    /**
     * Copy the "up" vector. The settings are unaffected. (native attribute:
     * mUp)
     *
     * @return a new direction vector
     */
    @Override
    public Vec3 getUp() {
        long settingsVa = va();
        float dx = getUpX(settingsVa);
        float dy = getUpY(settingsVa);
        float dz = getUpZ(settingsVa);
        Vec3 result = new Vec3(dx, dy, dz);

        return result;
    }

    /**
     * Access the settings of the specified wheel. (native attribute: mWheels)
     *
     * @param wheelIndex which wheel (&ge;0, &lt;numWheels)
     * @return the pre-existing object
     */
    @Override
    public WheelSettings getWheel(int wheelIndex) {
        Ref r = wheelRefs.get(wheelIndex);
        WheelSettings result = (WheelSettings) r.getPtr();

        return result;
    }

    /**
     * Enumerate all wheel settings. The settings are unaffected. (native
     * attribute: mWheels)
     *
     * @return a new array of pre-existing objects
     */
    @Override
    public WheelSettings[] getWheels() {
        int numWheels = wheelRefs.size();
        WheelSettings[] result = new WheelSettings[numWheels];
        for (int i = 0; i < numWheels; ++i) {
            Ref r = wheelRefs.get(i);
            RefTarget t = r.getPtr();
            result[i] = (WheelSettings) t;
        }

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void addWheel(
            long constraintSettingsVa, long wheelSettingsVa);

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static long getAntiRollBar(long settingsVa, int index);

    native private static float getForwardX(long settingsVa);

    native private static float getForwardY(long settingsVa);

    native private static float getForwardZ(long settingsVa);

    native private static float getMaxPitchRollAngle(long settingsVa);

    native private static int getNumAntiRollBars(long settingsVa);

    native private static float getUpX(long settingsVa);

    native private static float getUpY(long settingsVa);

    native private static float getUpZ(long settingsVa);

    native private static void setController(
            long constraintSettingsVa, long controllerSettingsVa);

    native private static void setForward(
            long settingsVa, float dx, float dy, float dz);

    native private static void setMaxPitchRollAngle(
            long settingsVa, float angle);

    native private static void setNumAntiRollBars(long settingsVa, int count);

    native private static void setUp(
            long settingsVa, float dx, float dy, float dz);
}
