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
import com.github.stephengold.joltjni.template.Ref;

/**
 * A counted reference to a {@code Constraint}. (native type:
 * {@code Ref<Constraint>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class ConstraintRef
        extends Ref
        implements ConstConstraint {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    ConstraintRef(long refVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(refVa) : null;
        setVirtualAddress(refVa, freeingAction);
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
        long refVa = va();
        long constraintVa = getPtr(refVa);
        int result = Constraint.getConstraintPriority(constraintVa);

        return result;
    }

    /**
     * Convert the constraint to a {@code ConstraintSettings} object. The
     * constraint is unaffected.
     *
     * @return a new reference to a new settings object
     */
    @Override
    public ConstraintSettingsRef getConstraintSettings() {
        long refVa = va();
        long constraintVa = getPtr(refVa);
        long settingsRefVa = Constraint.getConstraintSettings(constraintVa);
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
        long refVa = va();
        long constraintVa = getPtr(refVa);
        boolean result = Constraint.getEnabled(constraintVa);

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
        long refVa = va();
        long constraintVa = getPtr(refVa);
        int result = Constraint.getNumPositionStepsOverride(constraintVa);

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
        long refVa = va();
        long constraintVa = getPtr(refVa);
        int result = Constraint.getNumVelocityStepsOverride(constraintVa);

        return result;
    }

    /**
     * Return the constraint's subtype. The constraint is unaffected.
     *
     * @return an enum value (not null)
     */
    @Override
    public EConstraintSubType getSubType() {
        long refVa = va();
        long constraintVa = getPtr(refVa);
        int ordinal = Constraint.getSubType(constraintVa);
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
        long refVa = va();
        long constraintVa = getPtr(refVa);
        int ordinal = Constraint.getType(constraintVa);
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
        long refVa = va();
        long constraintVa = getPtr(refVa);
        long result = Constraint.getUserData(constraintVa);

        return result;
    }
    // *************************************************************************
    // Ref methods

    /**
     * Temporarily access the referenced {@code Constraint}.
     *
     * @return a new JVM object that refers to the pre-existing native object
     */
    @Override
    public Constraint getPtr() {
        long refVa = va();
        long constaintVa = getPtr(refVa);
        Constraint result = Constraint.newConstraint(constaintVa);

        return result;
    }

    /**
     * Create a counted reference to the native {@code Constraint}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public ConstraintRef toRef() {
        long refVa = va();
        long copyVa = copy(refVa);
        ConstraintRef result = new ConstraintRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long copy(long refVa);

    native private static void free(long refVa);

    native private static long getPtr(long refVa);
}
