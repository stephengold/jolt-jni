/*
Copyright (c) 2026 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.ConstConnectedConstraint;

/**
 * A constraint in a scene and which bodies it connects to. (native type:
 * {@code PhysicsScene::ConnectedConstraint})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ConnectedConstraint
        extends JoltPhysicsObject
        implements ConstConnectedConstraint {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default constraint.
     */
    public ConnectedConstraint() {
        long constraintVa = createDefault();
        setVirtualAddress(constraintVa, () -> free(constraintVa));
    }

    /**
     * Instantiate a copy of the specified box.
     *
     * @param original the box to copy (not {@code null}, unaffected)
     */
    public ConnectedConstraint(ConstConnectedConstraint original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa, () -> free(copyVa));
    }

    /**
     * Instantiate a constraint with the specified settings and bodies.
     *
     * @param settings the desired constraint settings (not {@code null},
     * unaffected)
     * @param body1 the index of the desired first body (default=0)
     * @param body2 the index of the desired 2nd body (default=0)
     */
    public ConnectedConstraint(
            TwoBodyConstraintSettings settings, int body1, int body2) {
        long settingsVa = settings.va();
        long constraintVa = create(settingsVa, body1, body2);
        setVirtualAddress(constraintVa, () -> free(constraintVa));
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param constraintVa the virtual address of the native object to assign
     * (not zero)
     */
    ConnectedConstraint(JoltPhysicsObject container, long constraintVa) {
        super(container, constraintVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the argument to the current constraint.
     *
     * @param source the constraint to copy (not {@code null}, unaffected)
     * @return the modified constraint, for chaining
     */
    public ConnectedConstraint set(ConstConnectedConstraint source) {
        long targetVa = va();
        long sourceVa = source.targetVa();
        assign(targetVa, sourceVa);

        return this;
    }

    /**
     * Alter the index of the first body. (native attribute: mBody1)
     *
     * @param body1 the index of the desired first body (default=0)
     * @return the modified constraint, for chaining
     */
    public ConnectedConstraint setBody1(int body1) {
        long constraintVa = va();
        setBody1(constraintVa, body1);

        return this;
    }

    /**
     * Alter the index of the 2nd body. (native attribute: mBody2)
     *
     * @param body2 the index of the desired 2nd body (default=0)
     * @return the modified constraint, for chaining
     */
    public ConnectedConstraint setBody2(int body2) {
        long constraintVa = va();
        setBody2(constraintVa, body2);

        return this;
    }

    /**
     * Replace the constraint settings. (native attribute: mSettings)
     *
     * @param settings the desired settings (not {@code null}, unaffected,
     * default=null)
     * @return the modified constraint, for chaining
     */
    public ConnectedConstraint setSettings(TwoBodyConstraintSettings settings) {
        long constraintVa = va();
        long settingsVa = settings.va();
        setSettings(constraintVa, settingsVa);

        return this;
    }
    // *************************************************************************
    // ConstConnectedConstraint methods

    /**
     * Return the index of the first body. (native attribute: mBody1)
     *
     * @return the index of the first body
     */
    @Override
    public int getBody1() {
        long constraintVa = va();
        int result = getBody1(constraintVa);

        return result;
    }

    /**
     * Return the index of the 2nd body. (native attribute: mBody2)
     *
     * @return the index of the 2nd body
     */
    @Override
    public int getBody2() {
        long constraintVa = va();
        int result = getBody2(constraintVa);

        return result;
    }

    /**
     * Access the constraint settings. (native attribute: mSettings)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public TwoBodyConstraintSettings getSettings() {
        long constraintVa = va();
        long settingsVa = getSettings(constraintVa);
        TwoBodyConstraintSettings result
                = (TwoBodyConstraintSettings) TwoBodyConstraintSettings
                        .newConstraintSettings(settingsVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void assign(long targetVa, long sourceVa);

    native private static long create(long settingsVa, int body1, int body2);

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static void free(long vertexVa);

    native private static int getBody1(long constraintVa);

    native private static int getBody2(long constraintVa);

    native private static long getSettings(long constraintVa);

    native private static void setBody1(long constraintVa, int body1);

    native private static void setBody2(long constraintVa, int body2);

    native private static void setSettings(long constraintVa, long settingsVa);
}
