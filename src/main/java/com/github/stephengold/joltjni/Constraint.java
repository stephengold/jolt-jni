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
import com.github.stephengold.joltjni.enumerate.EConstraintType;
import com.github.stephengold.joltjni.readonly.ConstConstraint;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.template.Ref;
import com.github.stephengold.joltjni.template.RefTarget;

/**
 * The abstract base class for physics constraints.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class Constraint extends NonCopyable
        implements ConstConstraint, RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a constraint with no native object assigned.
     */
    Constraint() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Instantiate a {@code Constraint} from its virtual address.
     *
     * @param constraintVa the virtual address of the native object, or zero
     * @return a new JVM object, or {@code null} if the argument was zero
     */
    static Constraint newConstraint(long constraintVa) {
        if (constraintVa == 0L) {
            return null;
        }

        int ordinal = getSubType(constraintVa);
        EConstraintSubType subType = EConstraintSubType.values()[ordinal];
        Constraint result;
        switch (subType) {
            case Cone:
                result = new ConeConstraint(constraintVa);
                break;
            case Distance:
                result = new DistanceConstraint(constraintVa);
                break;
            case Fixed:
                result = new FixedConstraint(constraintVa);
                break;
            case Gear:
                result = new GearConstraint(constraintVa);
                break;
            case Hinge:
                result = new HingeConstraint(constraintVa);
                break;
            case Path:
                result = new PathConstraint(constraintVa);
                break;
            case Point:
                result = new PointConstraint(constraintVa);
                break;
            case Pulley:
                result = new PulleyConstraint(constraintVa);
                break;
            case RackAndPinion:
                result = new RackAndPinionConstraint(constraintVa);
                break;
            case SixDof:
                result = new SixDofConstraint(constraintVa);
                break;
            case Slider:
                result = new SliderConstraint(constraintVa);
                break;
            case SwingTwist:
                result = new SwingTwistConstraint(constraintVa);
                break;
            case Vehicle:
                result = new VehicleConstraint(constraintVa);
                break;
            default:
                throw new IllegalArgumentException("subType = " + subType);
        }

        return result;
    }

    /**
     * Notify the constraint that the shape of the specified body has changed
     * and its center of mass has shifted by {@code deltaCom}.
     *
     * @param bodyId the ID of the body that changed
     * @param deltaCom the offset of the shift (not null, unaffected)
     */
    public void notifyShapeChanged(int bodyId, Vec3Arg deltaCom) {
        long constraintVa = va();
        float dx = deltaCom.getX();
        float dy = deltaCom.getY();
        float dz = deltaCom.getZ();
        notifyShapeChanged(constraintVa, bodyId, dx, dy, dz);
    }

    /**
     * Restore the state of the constraint prior to replay.
     *
     * @param recorder to read state from (not null)
     */
    public void restoreState(StateRecorder recorder) {
        long constraintVa = va();
        long recorderVa = recorder.va();
        restoreState(constraintVa, recorderVa);
    }

    /**
     * Alter the constraint's priority when solving.
     *
     * @param level the desired priority level
     */
    public void setConstraintPriority(int level) {
        long constraintVa = va();
        setConstraintPriority(constraintVa, level);
    }

    /**
     * Enable or disable the constraint.
     *
     * @param setting {@code true} to enable or {@code false} to disable
     */
    public void setEnabled(boolean setting) {
        long constraintVa = va();
        setEnabled(constraintVa, setting);
    }

    /**
     * Alter the override for the number of position iterations used in the
     * solver.
     *
     * @param setting the number of iterations, or 0 to use the default in
     * {@code PhysicsSettings}
     */
    public void setNumPositionStepsOverride(int setting) {
        long constraintVa = va();
        setNumPositionStepsOverride(constraintVa, setting);
    }

    /**
     * Alter the override for the number of velocity iterations used in the
     * solver.
     *
     * @param setting the number of iterations, or 0 to use the default in
     * {@code PhysicsSettings}
     */
    public void setNumVelocityStepsOverride(int setting) {
        long constraintVa = va();
        setNumVelocityStepsOverride(constraintVa, setting);
    }
    // *************************************************************************
    // new protected methods

    /**
     * Assign a native object (assuming there's none already assigned) and
     * designate the JVM object as a co-owner.
     *
     * @param constraintVa the virtual address of the native object to assign
     * (not zero)
     */
    final protected void setVirtualAddressAsCoOwner(long constraintVa) {
        long refVa = toRef(constraintVa);
        Runnable freeingAction = () -> ConstraintRef.free(refVa);
        setVirtualAddress(constraintVa, freeingAction);
    }
    // *************************************************************************
    // ConstConstraint methods

    /**
     * Return the constraint's priority when solving. The constraint is
     * unaffected.
     *
     * @return the priority level
     */
    @Override
    public int getConstraintPriority() {
        long constraintVa = va();
        int result = getConstraintPriority(constraintVa);

        return result;
    }

    /**
     * Generate settings to reconstruct the constraint. The constraint is
     * unaffected.
     *
     * @return a new counted reference to a new settings object
     */
    @Override
    public ConstraintSettingsRef getConstraintSettings() {
        long constraintVa = va();
        long settingsRefVa = getConstraintSettings(constraintVa);
        ConstraintSettingsRef result
                = new ConstraintSettingsRef(settingsRefVa, true);

        return result;
    }

    /**
     * Test whether the constraint is enabled. The constraint is unaffected.
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    @Override
    public boolean getEnabled() {
        long constraintVa = va();
        boolean result = getEnabled(constraintVa);

        return result;
    }

    /**
     * Return the override for the number of position iterations used in the
     * solver. The constraint is unaffected.
     *
     * @return the number of iterations, or 0 to use the default in
     * {@code PhysicsSettings}
     */
    @Override
    public int getNumPositionStepsOverride() {
        long constraintVa = va();
        int result = getNumPositionStepsOverride(constraintVa);

        return result;
    }

    /**
     * Return the override for the number of velocity iterations used in the
     * solver. The constraint is unaffected.
     *
     * @return the number of iterations, or 0 to use the default in
     * {@code PhysicsSettings}
     */
    @Override
    public int getNumVelocityStepsOverride() {
        long constraintVa = va();
        int result = getNumVelocityStepsOverride(constraintVa);

        return result;
    }

    /**
     * Return the constraint's subtype. The constraint is unaffected.
     *
     * @return an enum value (not null)
     */
    @Override
    public EConstraintSubType getSubType() {
        long constraintVa = va();
        int ordinal = getSubType(constraintVa);
        EConstraintSubType result = EConstraintSubType.values()[ordinal];

        return result;
    }

    /**
     * Return the constraint's type. The constraint is unaffected.
     *
     * @return an enum value (not null)
     */
    @Override
    public EConstraintType getType() {
        long constraintVa = va();
        int ordinal = getType(constraintVa);
        EConstraintType result = EConstraintType.values()[ordinal];

        return result;
    }

    /**
     * Test whether the constraint is active. The constraint is unaffected.
     *
     * @return {@code true} if active, otherwise {@code false}
     */
    @Override
    public boolean isActive() {
        long constraintVa = va();
        boolean result = isActive(constraintVa);

        return result;
    }

    /**
     * Save the state of the constraint, for possible replay. The constraint is
     * unaffected.
     *
     * @param recorder the recorder to write to (not null)
     */
    @Override
    public void saveState(StateRecorder recorder) {
        long constraintVa = va();
        long recorderVa = recorder.va();
        saveState(constraintVa, recorderVa);
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code Constraint}. The
     * constraint is unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long constraintVa = va();
        int result = getRefCount(constraintVa);

        return result;
    }

    /**
     * Mark the native {@code Constraint} as embedded.
     */
    @Override
    public void setEmbedded() {
        long constraintVa = va();
        setEmbedded(constraintVa);
    }

    /**
     * Create a counted reference to the native {@code Constraint}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public Ref toRef() {
        long constraintVa = va();
        long refVa = toRef(constraintVa);
        ConstraintRef result = new ConstraintRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native static int getConstraintPriority(long constraintVa);

    native static long getConstraintSettings(long constraintVa);

    native static int getControllerType(long settingsVa);

    native static boolean getEnabled(long constraintVa);

    native static int getNumPositionStepsOverride(long constraintVa);

    native static int getNumVelocityStepsOverride(long constraintVa);

    native private static int getRefCount(long constraintVa);

    native static int getSubType(long constraintVa);

    native static int getType(long constraintVa);

    native static boolean isActive(long constraintVa);

    native static void notifyShapeChanged(
            long constraintVa, int bodyId, float dx, float dy, float dz);

    native static void restoreState(long constraintVa, long recorderVa);

    native static void saveState(long constraintVa, long recorderVa);

    native private static void setConstraintPriority(
            long constraintVa, int level);

    native private static void setEmbedded(long constraintVa);

    native private static void setEnabled(long constraintVa, boolean setting);

    native private static void setNumPositionStepsOverride(
            long constraintVa, int setting);

    native private static void setNumVelocityStepsOverride(
            long constraintVa, int setting);

    native private static long toRef(long constraintVa);
}
