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
import com.github.stephengold.joltjni.readonly.ConstConstraintSettings;
import com.github.stephengold.joltjni.template.Ref;

/**
 * Settings used to construct a {@code Constraint}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class ConstraintSettings
        extends SerializableObject
        implements ConstConstraintSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate settings with no native object assigned.
     */
    ConstraintSettings() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Instantiate a {@code ConstraintSettings} given its virtual address.
     *
     * @param settingsVa the virtual address of the native object, or zero
     * @return a new JVM object, or {@code null} if the argument was zero
     */
    static ConstraintSettings newConstraintSettings(long settingsVa) {
        if (settingsVa == 0L) {
            return null;
        }

        int ordinal = getConstraintSubType(settingsVa);
        EConstraintSubType subType = EConstraintSubType.values()[ordinal];
        ConstraintSettings result;
        switch (subType) {
            case Cone:
                result = new ConeConstraintSettings(settingsVa);
                break;
            case Distance:
                result = new DistanceConstraintSettings(settingsVa);
                break;
            case Fixed:
                result = new FixedConstraintSettings(settingsVa);
                break;
            case Gear:
                result = new GearConstraintSettings(settingsVa);
                break;
            case Hinge:
                result = new HingeConstraintSettings(settingsVa);
                break;
            case Path:
                result = new PathConstraintSettings(settingsVa);
                break;
            case Point:
                result = new PointConstraintSettings(settingsVa);
                break;
            case Pulley:
                result = new PulleyConstraintSettings(settingsVa);
                break;
            case RackAndPinion:
                result = new RackAndPinionConstraintSettings(settingsVa);
                break;
            case SixDof:
                result = new SixDofConstraintSettings(settingsVa);
                break;
            case Slider:
                result = new SliderConstraintSettings(settingsVa);
                break;
            case SwingTwist:
                result = new SwingTwistConstraintSettings(settingsVa);
                break;
            case Vehicle:
                result = new VehicleConstraintSettings(settingsVa);
                break;
            default:
                throw new IllegalArgumentException("subType = " + subType);
        }
        return result;
    }

    /**
     * Alter the constraint's priority when solving. (native attribute:
     * mConstraintPriority)
     *
     * @param level the desired priority level (default=0)
     */
    public void setConstraintPriority(int level) {
        long settingsVa = va();
        setConstraintPriority(settingsVa, level);
    }

    /**
     * Alter the size of constraint in debug renders. (native attribute:
     * mDrawConstraintSize)
     *
     * @param size the desired size (default=1)
     */
    public void setDrawConstraintSize(float size) {
        long settingsVa = va();
        setDrawConstraintSize(settingsVa, size);
    }

    /**
     * Initially enable or disable the constraint. (native attribute: mEnabled)
     *
     * @param setting {@code true} to enable or {@code false} to disable
     * (default=true)
     */
    public void setEnabled(boolean setting) {
        long settingsVa = va();
        setEnabled(settingsVa, setting);
    }

    /**
     * Alter the override for the number of position iterations used in the
     * solver. (native attribute: mNumPositionStepsOverride)
     *
     * @param setting the number of iterations, or 0 to use the default in
     * {@code PhysicsSettings} (default=0)
     */
    public void setNumPositionStepsOverride(int setting) {
        long settingsVa = va();
        setNumPositionStepsOverride(settingsVa, setting);
    }

    /**
     * Alter the override for the number of velocity iterations used in the
     * solver. (native attribute: mNumVelocityStepsOverride)
     *
     * @param setting the number of iterations, or 0 to use the default in
     * {@code PhysicsSettings} (default=0)
     */
    public void setNumVelocityStepsOverride(int setting) {
        long settingsVa = va();
        setNumVelocityStepsOverride(settingsVa, setting);
    }

    /**
     * Read a settings object from the specified binary stream.
     *
     * @param stream where to read objects (not null)
     * @return a new object
     */
    public static ConstraintResult sRestoreFromBinaryState(StreamIn stream) {
        long streamVa = stream.va();
        long resultVa = sRestoreFromBinaryState(streamVa);
        ConstraintResult result = new ConstraintResult(resultVa, true);

        return result;
    }
    // *************************************************************************
    // new protected methods

    /**
     * Assign a native object (assuming there's none already assigned),
     * designate the JVM object as a co-owner, and assign a subtype.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     * @param constraintSubType the desired subtype (not null)
     */
    final protected void setVirtualAddressAsCoOwner(
            long settingsVa, EConstraintSubType constraintSubType) {
        long refVa = toRef(settingsVa);
        Runnable freeingAction = () -> ConstraintSettingsRef.free(refVa);
        setVirtualAddress(settingsVa, freeingAction);

        int ordinal = constraintSubType.ordinal();
        setConstraintSubType(settingsVa, ordinal);
    }
    // *************************************************************************
    // ConstConstraintSettings methods

    /**
     * Return the constraint's priority when solving. The settings are
     * unaffected. (native attribute: mConstraintPriority)
     *
     * @return the priority level
     */
    @Override
    public int getConstraintPriority() {
        long settingsVa = va();
        int result = getConstraintPriority(settingsVa);

        return result;
    }

    /**
     * Return the constraint's controller type. The settings are unaffected.
     *
     * @return a numeric code, such as {@code VehicleController.motorcycleType}
     */
    @Override
    public int getControllerType() {
        long settingsVa = va();
        int result = getControllerType(settingsVa);

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
        long settingsVa = va();
        float result = getDrawConstraintSize(settingsVa);

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
        long settingsVa = va();
        boolean result = getEnabled(settingsVa);

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
        long settingsVa = va();
        int result = getNumPositionStepsOverride(settingsVa);

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
        long settingsVa = va();
        int result = getNumVelocityStepsOverride(settingsVa);

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
        long settingsVa = va();
        long streamVa = stream.va();
        saveBinaryState(settingsVa, streamVa);
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code ConstraintSettings}. The
     * settings are unaffected.
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
     * Mark the native {@code ConstraintSettings} as embedded.
     */
    @Override
    public void setEmbedded() {
        long settingsVa = va();
        setEmbedded(settingsVa);
    }

    /**
     * Create a counted reference to the native {@code ConstraintSettings}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public Ref toRef() {
        long settingsVa = va();
        long refVa = toRef(settingsVa);
        ConstraintSettingsRef result = new ConstraintSettingsRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native static int getConstraintPriority(long settingsVa);

    native static int getConstraintSubType(long settingsVa);

    native static int getControllerType(long settingsVa);

    native static float getDrawConstraintSize(long settingsVa);

    native static boolean getEnabled(long settingsVa);

    native static int getNumPositionStepsOverride(long settingsVa);

    native static int getNumVelocityStepsOverride(long settingsVa);

    native private static int getRefCount(long settingsVa);

    native static void saveBinaryState(long settingsVa, long streamVa);

    native private static void setConstraintPriority(
            long settingsVa, int level);

    native private static void setConstraintSubType(
            long settingsVa, int ordinal);

    native static void setControllerType(long settingsVa, int ordinal);

    native private static void setDrawConstraintSize(
            long settingsVa, float size);

    native private static void setEmbedded(long settingsVa);

    native private static void setEnabled(long settingsVa, boolean value);

    native private static void setNumPositionStepsOverride(
            long settingsVa, int setting);

    native private static void setNumVelocityStepsOverride(
            long settingsVa, int setting);

    native private static long sRestoreFromBinaryState(long streamVa);

    native private static long toRef(long settingsVa);
}
