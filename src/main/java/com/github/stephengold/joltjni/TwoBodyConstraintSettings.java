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
 * Settings used to construct a {@code TwoBodyConstraint}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class TwoBodyConstraintSettings extends ConstraintSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate settings with no native object assigned.
     */
    TwoBodyConstraintSettings() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Create a constraint using the settings. The settings are unaffected.
     *
     * @param rigidBody1 the desired first body (not null, not soft)
     * @param rigidBody2 the desired 2nd body (not null, not soft)
     * @return the new constraint, ready to be added to a {@code PhysicsSystem}
     */
    public TwoBodyConstraint create(Body rigidBody1, Body rigidBody2) {
        long settingsVa = va();
        long body1Va = rigidBody1.va();
        assert !Body.isSoftBody(body1Va) : "body1 must be a rigid body.";
        long body2Va = rigidBody2.va();
        assert !Body.isSoftBody(body2Va) : "body2 must be a rigid body.";
        long constraintVa = createConstraint(settingsVa, body1Va, body2Va);
        TwoBodyConstraint result
                = (TwoBodyConstraint) Constraint.newConstraint(constraintVa);

        return result;
    }
    // *************************************************************************
    // ConstraintSettings methods

    /**
     * Create a counted reference to the native {@code ConstraintSettings}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public TwoBodyConstraintSettingsRef toRef() {
        long settingsVa = va();
        long refVa = toRef(settingsVa);
        TwoBodyConstraintSettingsRef result
                = new TwoBodyConstraintSettingsRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native static long createConstraint(
            long settingsVa, long body1Va, long body2Va);

    native private static long toRef(long settingsVa);
}
