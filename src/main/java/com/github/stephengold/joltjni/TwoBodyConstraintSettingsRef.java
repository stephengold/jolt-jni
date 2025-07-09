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

import com.github.stephengold.joltjni.template.Ref;

/**
 * A counted reference to a {@code TwoBodyConstraintSettings} object. (native
 * type: {@code Ref<TwoBodyConstraintSettings>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class TwoBodyConstraintSettingsRef extends Ref {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public TwoBodyConstraintSettingsRef() {
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
    TwoBodyConstraintSettingsRef(long refVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(refVa) : null;
        setVirtualAddress(refVa, freeingAction);
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
        long settingsVa = targetVa();
        long body1Va = rigidBody1.va();
        assert !Body.isSoftBody(body1Va) :
                "body1 must be a rigid body.";
        long body2Va = rigidBody2.va();
        assert !Body.isSoftBody(body2Va) :
                "body2 must be a rigid body.";
        long constraintVa = TwoBodyConstraintSettings.createConstraint(
                settingsVa, body1Va, body2Va);
        TwoBodyConstraint result
                = (TwoBodyConstraint) Constraint.newConstraint(constraintVa);

        return result;
    }
    // *************************************************************************
    // Ref methods

    /**
     * Temporarily access the referenced {@code TwoBodyConstraintSettings}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public TwoBodyConstraintSettings getPtr() {
        long settingsVa = targetVa();
        TwoBodyConstraintSettings result
                = (TwoBodyConstraintSettings) ConstraintSettings
                        .newConstraintSettings(settingsVa);

        return result;
    }

    /**
     * Return the address of the native {@code TwoBodyConstraintSettings}. No
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
     * {@code TwoBodyConstraintSettings}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public TwoBodyConstraintSettingsRef toRef() {
        long refVa = va();
        long copyVa = copy(refVa);
        TwoBodyConstraintSettingsRef result
                = new TwoBodyConstraintSettingsRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long copy(long refVa);

    native private static long createDefault();

    native private static void free(long refVa);

    native private static long getPtr(long refVa);
}
