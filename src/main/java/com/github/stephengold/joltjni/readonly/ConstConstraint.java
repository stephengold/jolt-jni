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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.ConstraintSettingsRef;
import com.github.stephengold.joltjni.enumerate.EConstraintSubType;
import com.github.stephengold.joltjni.enumerate.EConstraintType;

/**
 * Read-only access to a {@code Constraint}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstConstraint extends ConstJoltPhysicsObject {
    // *************************************************************************
    // new methods exposed

    /**
     * Return the constraint's priority when solving.
     *
     * @return the priority level
     */
    int getConstraintPriority();

    /**
     * Convert the constraint to a {@code ConstraintSettings} object.
     *
     * @return a new reference to a new settings object
     */
    ConstraintSettingsRef getConstraintSettings();

    /**
     * Test whether the constraint is enabled. The constraint is unaffected.
     *
     * @return true if enabled, otherwise false
     */
    boolean getEnabled();

    /**
     * Return the override for the number of position iterations used in the
     * solver. The constraint is unaffected.
     *
     * @return the number of iterations, or 0 to use the default in
     * {@code PhysicsSettings}
     */
    int getNumPositionStepsOverride();

    /**
     * Return the override for the number of velocity iterations used in the
     * solver. The constraint is unaffected.
     *
     * @return the number of iterations, or 0 to use the default in
     * {@code PhysicsSettings}
     */
    int getNumVelocityStepsOverride();

    /**
     * Return the constraint's subtype. The constraint is unaffected.
     *
     * @return an enum value (not null)
     */
    EConstraintSubType getSubType();

    /**
     * Return the constraint's type. The constraint is unaffected.
     *
     * @return an enum value (not null)
     */
    EConstraintType getType();

    /**
     * Return the constraint's user data: can be used for anything. The
     * constraint is unaffected.
     *
     * @return the value
     */
    long getUserData();
}
