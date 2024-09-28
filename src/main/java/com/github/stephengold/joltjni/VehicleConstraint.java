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

import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * A constraint used in vehicle simulation.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class VehicleConstraint
        extends Constraint
        implements PhysicsStepListener {
    // *************************************************************************
    // constructors

    /**
     * protect the collision tester (if any) from garbage collection
     */
    private VehicleCollisionTester tester;
    // *************************************************************************
    // constructors

    /**
     * Instantiate a constraint using the specified body and settings.
     *
     * @param body the body to which the constraint will apply (not null)
     * @param settings the desired settings (not null, unaffected)
     */
    public VehicleConstraint(Body body, VehicleConstraintSettings settings) {
        long bodyVa = body.va();
        long settingsVa = settings.va();
        long constraintVa = createConstraint(bodyVa, settingsVa);
        setVirtualAddress(constraintVa, false);
        // not the owner due to ref counting
    }

    /**
     * Instantiate a constraint with the specified native object assigned but
     * not owned.
     *
     * @param constraintVa the virtual address of the native object to assign
     * (not zero)
     */
    VehicleConstraint(long constraintVa) {
        super(constraintVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the controller for this constraint.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public VehicleController getController() {
        long constraintVa = va();
        long controllerVa = getController(constraintVa);
        VehicleController result = new VehicleController(this, controllerVa);

        return result;
    }

    /**
     * Access the vehicle body.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public Body getVehicleBody() {
        long constraintVa = va();
        long bodyVa = getVehicleBody(constraintVa);
        Body result = new Body(bodyVa);

        return result;
    }

    /**
     * Access the underlying tester, if any.
     *
     * @return the pre-existing instance or {@code null} if none
     */
    public VehicleCollisionTester getVehicleCollisionTester() {
        return tester;
    }

    /**
     * Access the specified wheel.
     *
     * @param wheelIndex the index of the wheel to access (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public Wheel getWheel(int wheelIndex) {
        long constraintVa = va();
        long wheelVa = getWheel(constraintVa, wheelIndex);
        Wheel result = new Wheel(wheelVa);

        return result;
    }

    /**
     * Copy the world transform of the specified wheel.
     *
     * @param wheelIndex the index of the wheel to query (&ge;0)
     * @param right the wheel's axis of rotation (a unit vector in the wheel's
     * model space)
     * @param up the "up" direction (a unit vector in the wheel's model space)
     * @return a new coordinate transform matrix
     */
    public RMat44 getWheelWorldTransform(
            int wheelIndex, Vec3Arg right, Vec3Arg up) {
        long constraintVa = va();
        float rx = right.getX();
        float ry = right.getY();
        float rz = right.getZ();
        float ux = up.getX();
        float uy = up.getY();
        float uz = up.getZ();
        long matrixVa = getWheelWorldTransform(
                constraintVa, wheelIndex, rx, ry, rz, ux, uy, uz);
        RMat44 result = new RMat44(matrixVa, true);

        return result;
    }

    /**
     * Return the number of simulation steps between wheel collision tests when
     * the vehicle is active.
     *
     * @param numSteps the desired number of steps (0=never test, 1=test every
     * step, 2=test every other step, default=1)
     */
    public void setNumStepsBetweenCollisionTestActive(int numSteps) {
        long constraintVa = va();
        setNumStepsBetweenCollisionTestActive(constraintVa, numSteps);
    }

    /**
     * Return the number of simulation steps between wheel collision tests when
     * the vehicle is inactive.
     *
     * @param numSteps the desired number of steps (0=never, 1=every step,
     * 2=every other step, default=1)
     */
    public void setNumStepsBetweenCollisionTestInactive(int numSteps) {
        long constraintVa = va();
        setNumStepsBetweenCollisionTestInactive(constraintVa, numSteps);
    }

    /**
     * Replace the collision tester.
     *
     * @param tester the desired tester (not null, alias created)
     */
    public void setVehicleCollisionTester(VehicleCollisionTester tester) {
        this.tester = tester;
        long constraintVa = va();
        long testerVa = tester.va();
        setVehicleCollisionTester(constraintVa, testerVa);
    }
    // *************************************************************************
    // native private methods

    native private static long createConstraint(long bodyVa, long settingsVa);

    native private static long getController(long constraintVa);

    native private static long getVehicleBody(long constraintVa);

    native private static long getWheel(long constraintVa, int wheelIndex);

    native private static long getWheelWorldTransform(
            long constraintVa, int wheelIndex, float rx, float ry, float rz,
            float ux, float uy, float uz);

    native private static void setNumStepsBetweenCollisionTestActive(
            long constraintVa, int numSteps);

    native private static void setNumStepsBetweenCollisionTestInactive(
            long constraintVa, int numSteps);

    native private static void setVehicleCollisionTester(
            long constraintVa, long testerVa);
}
