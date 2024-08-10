/*
Copyright (c) 2024 Stephen Gold

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
     * Instantiate a constraint with the specified native object assigned but
     * not owned.
     *
     * @param constraintVa the virtual address of the native object to assign
     * (not zero)
     */
    protected Constraint(long constraintVa) {
        super(constraintVa);
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

        int ordinal = getType(constraintVa);
        EConstraintSubType subType = EConstraintSubType.values()[ordinal];
        Constraint result;
        switch (subType) {
            case Fixed:
                result = new FixedConstraint(constraintVa);
                break;
            case Point:
                result = new PointConstraint(constraintVa);
                break;
            default:
                throw new IllegalArgumentException("subType = " + subType);
        }
        return result;
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
     * @param setting true to enable or false to disable
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

    /**
     * Alter the constraint's user data.
     *
     * @param value the desired value (default=0)
     */
    public void setUserData(long value) {
        long constraintVa = va();
        setUserData(constraintVa, value);
    }

    /**
     * Create a counted reference to the native {@code Constraint}.
     *
     * @return a new JVM object with a new native object assigned
     */
    public ConstraintRef toRef() {
        long constraintVa = va();
        long refVa = toRef(constraintVa);
        ConstraintRef result = new ConstraintRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // ConstConstraint methods

    /**
     * Return the constraint's priority when solving.
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
     * Convert the constraint to a {@code ConstraintSettings} object.
     *
     * @return a new reference to a new settings object
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
     * Test whether the constraint is enabled.
     *
     * @return true if enabled, otherwise false
     */
    @Override
    public boolean getEnabled() {
        long constraintVa = va();
        boolean result = getEnabled(constraintVa);

        return result;
    }

    /**
     * Return the override for the number of position iterations used in the
     * solver.
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
     * solver.
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
     * Return the constraint's user data: can be used for anything. The
     * constraint is unaffected.
     *
     * @return the value
     */
    @Override
    public long getUserData() {
        long constraintVa = va();
        long result = getUserData(constraintVa);

        return result;
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count active references to the constraint.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long constraintVa = va();
        int result = getRefCount(constraintVa);

        return result;
    }
    // *************************************************************************
    // native methods

    native static int getConstraintPriority(long constraintVa);

    native static long getConstraintSettings(long constraintVa);

    native static boolean getEnabled(long constraintVa);

    native static int getNumPositionStepsOverride(long constraintVa);

    native static int getNumVelocityStepsOverride(long constraintVa);

    native static int getRefCount(long constraintVa);

    native static int getSubType(long constraintVa);

    native static int getType(long constraintVa);

    native static long getUserData(long constraintVa);

    native private static void setConstraintPriority(
            long constraintVa, int level);

    native private static void setEnabled(long constraintVa, boolean setting);

    native private static void setNumPositionStepsOverride(
            long constraintVa, int setting);

    native private static void setNumVelocityStepsOverride(
            long constraintVa, int setting);

    native private static void setUserData(long constraintVa, long value);

    native private static long toRef(long constraintVa);
}
