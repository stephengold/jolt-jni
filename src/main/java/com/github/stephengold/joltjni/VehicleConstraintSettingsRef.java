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

import com.github.stephengold.joltjni.readonly.ConstVehicleConstraintSettings;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.template.Ref;
import java.nio.FloatBuffer;

/**
 * A counted reference to a {@code VehicleConstraintSettings} object. (native
 * type: {@code Ref<VehicleConstraintSettings>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class VehicleConstraintSettingsRef
        extends Ref
        implements ConstVehicleConstraintSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public VehicleConstraintSettingsRef() {
        long refVa = createDefault();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    VehicleConstraintSettingsRef(long refVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(refVa) : null;
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Append the specified wheel settings. (native attribute: mWheels)
     *
     * @param wheelSettingsArray the wheel settings to append
     */
    public void addWheels(WheelSettings... wheelSettingsArray) {
        long constraintSettingsVa = targetVa();
        for (WheelSettings wheelSettings : wheelSettingsArray) {
            long wheelSettingsVa = wheelSettings.va();
            VehicleConstraintSettings.addWheel(
                    constraintSettingsVa, wheelSettingsVa);
        }
    }

    /**
     * Alter how the vehicle accelerates and decelerates. (native attribute:
     * mController)
     *
     * @param controllerSettings the desired settings (not null, default=null)
     */
    public void setController(VehicleControllerSettings controllerSettings) {
        long constraintSettingsVa = targetVa();
        long controllerSettingsVa = controllerSettings.va();
        VehicleConstraintSettings.setController(
                constraintSettingsVa, controllerSettingsVa);

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
        long settingsVa = targetVa();
        float dx = forward.getX();
        float dy = forward.getY();
        float dz = forward.getZ();
        VehicleConstraintSettings.setForward(settingsVa, dx, dy, dz);
    }

    /**
     * Alter the vehicle's maximum pitch/roll angle. (native attribute:
     * mMaxPitchRollAngle)
     *
     * @param angle the desired limit angle (in radians, default=Pi)
     */
    public void setMaxPitchRollAngle(float angle) {
        long settingsVa = targetVa();
        VehicleConstraintSettings.setMaxPitchRollAngle(settingsVa, angle);
    }

    /**
     * Alter the number of anti-roll bars. (native attribute: mAntiRollBars)
     *
     * @param count the desired number (&ge;0, default=0)
     */
    public void setNumAntiRollBars(int count) {
        long settingsVa = targetVa();
        VehicleConstraintSettings.setNumAntiRollBars(settingsVa, count);
    }

    /**
     * Alter the up direction. (native attribute: mUp)
     *
     * @param up the desired up direction (not null, unaffected,
     * default=(0,1,0))
     */
    public void setUp(Vec3Arg up) {
        long settingsVa = targetVa();
        float dx = up.getX();
        float dy = up.getY();
        float dz = up.getZ();
        VehicleConstraintSettings.setUp(settingsVa, dx, dy, dz);
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
        long settingsVa = targetVa();
        long barVa = VehicleConstraintSettings.getAntiRollBar(
                settingsVa, barIndex);
        VehicleAntiRollBar result = new VehicleAntiRollBar(this, barVa);

        return result;
    }

    /**
     * Return the constraint's priority when solving. The settings are
     * unaffected. (native attribute: mConstraintPriority)
     *
     * @return the priority level
     */
    @Override
    public int getConstraintPriority() {
        long settingsVa = targetVa();
        int result = ConstraintSettings.getConstraintPriority(settingsVa);

        return result;
    }

    /**
     * Access the controller settings.
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if none
     */
    @Override
    public VehicleControllerSettings getController() {
        long constraintSettingsVa = targetVa();
        long controllerSettingsVa
                = VehicleConstraintSettings.getController(constraintSettingsVa);
        VehicleControllerSettings result
                = VehicleControllerSettings.newSettings(controllerSettingsVa);

        return result;
    }

    /**
     * Return the constraint's controller type. The settings are unaffected.
     *
     * @return a numeric code, such as {@code VehicleController.motorcycleType}
     */
    @Override
    public int getControllerType() {
        long settingsVa = targetVa();
        int result = ConstraintSettings.getControllerType(settingsVa);

        return result;
    }

    /**
     * Return the size of the constraint in debug renders. The settings are
     * unaffected. (native attribute: mDrawConstraintSize)
     *
     * @return the size
     */
    @Override
    public float getDrawConstraintSize() {
        long settingsVa = targetVa();
        float result = ConstraintSettings.getDrawConstraintSize(settingsVa);

        return result;
    }

    /**
     * Test whether the constraint will be enabled initially. The settings are
     * unaffected. (native attribute: mEnabled)
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    @Override
    public boolean getEnabled() {
        long settingsVa = targetVa();
        boolean result = ConstraintSettings.getEnabled(settingsVa);

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
        long settingsVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        VehicleConstraintSettings.getForward(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

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
        long settingsVa = targetVa();
        float result
                = VehicleConstraintSettings.getMaxPitchRollAngle(settingsVa);

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
        long settingsVa = targetVa();
        int result = VehicleConstraintSettings.getNumAntiRollBars(settingsVa);

        return result;
    }

    /**
     * Return the override for the number of position iterations used in the
     * solver. The settings are unaffected. (native attribute:
     * mNumPositionStepsOverride)
     *
     * @return the number of iterations, or 0 to use the default in
     * {@code PhysicsSettings}
     */
    @Override
    public int getNumPositionStepsOverride() {
        long settingsVa = targetVa();
        int result = ConstraintSettings.getNumPositionStepsOverride(settingsVa);

        return result;
    }

    /**
     * Return the override for the number of velocity iterations used in the
     * solver. The settings are unaffected. (native attribute:
     * mNumVelocityStepsOverride)
     *
     * @return the number of iterations, or 0 to use the default configured in
     * {@code PhysicsSettings}
     */
    @Override
    public int getNumVelocityStepsOverride() {
        long settingsVa = targetVa();
        int result = ConstraintSettings.getNumVelocityStepsOverride(settingsVa);

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
        long settingsVa = targetVa();
        int result = VehicleConstraintSettings.getNumWheels(settingsVa);

        return result;
    }

    /**
     * Access the type information of the current object. (native function:
     * getRTTI)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public Rtti getRtti() {
        long jpoVa = targetVa();
        long resultVa = SerializableObject.getRtti(jpoVa);

        Rtti result = new Rtti(resultVa);
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
        long settingsVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        VehicleConstraintSettings.getUp(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Access the settings of the specified wheel. (native attribute: mWheels)
     *
     * @param wheelIndex which wheel (&ge;0, &lt;numWheels)
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if none
     */
    @Override
    public WheelSettings getWheel(int wheelIndex) {
        long constraintSettingsVa = targetVa();
        long wheelSettingsVa = VehicleConstraintSettings.getWheel(
                constraintSettingsVa, wheelIndex);
        WheelSettings result = WheelSettings.newSettings(wheelSettingsVa);

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
        long constraintSettingsVa = targetVa();
        int numWheels
                = VehicleConstraintSettings.getNumWheels(constraintSettingsVa);
        WheelSettings[] result = new WheelSettings[numWheels];
        for (int wheelIndex = 0; wheelIndex < numWheels; ++wheelIndex) {
            long wheelSettingsVa = VehicleConstraintSettings
                    .getWheel(constraintSettingsVa, wheelIndex);
            WheelSettings ws = WheelSettings.newSettings(wheelSettingsVa);
            result[wheelIndex] = ws;
        }

        return result;
    }

    /**
     * Save the settings to the specified binary stream. The settings are
     * unaffected.
     *
     * @param stream the stream to write to (not null)
     */
    @Override
    public void saveBinaryState(StreamOut stream) {
        long settingsVa = targetVa();
        long streamVa = stream.va();
        ConstraintSettings.saveBinaryState(settingsVa, streamVa);
    }
    // *************************************************************************
    // Ref methods

    /**
     * Temporarily access the referenced {@code VehicleConstraintSettings}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public VehicleConstraintSettings getPtr() {
        long settingsVa = targetVa();
        VehicleConstraintSettings result
                = (VehicleConstraintSettings) ConstraintSettings
                        .newConstraintSettings(settingsVa);

        return result;
    }

    /**
     * Return the address of the native {@code VehicleConstraintSettings}. No
     * objects are affected.
     *
     * @return a virtual address (not zero)
     */
    @Override
    public long targetVa() {
        long refVa = va();
        long result = getPtr(refVa);

        return result;
    }

    /**
     * Create another counted reference to the native
     * {@code VehicleConstraintSettings}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public VehicleConstraintSettingsRef toRef() {
        long refVa = va();
        long copyVa = copy(refVa);
        VehicleConstraintSettingsRef result
                = new VehicleConstraintSettingsRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long copy(long refVa);

    native private static long createDefault();

    native private static void free(long refVa);

    native private static long getPtr(long refVa);
}
