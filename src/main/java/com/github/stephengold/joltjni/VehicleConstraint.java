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

import com.github.stephengold.joltjni.readonly.ConstVehicleConstraint;
import com.github.stephengold.joltjni.readonly.ConstVehicleConstraintSettings;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * A special {@code Constraint} used in vehicle simulation.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class VehicleConstraint
        extends Constraint
        implements ConstVehicleConstraint {
    // *************************************************************************
    // fields

    /**
     * cached reference to the vehicle body
     */
    final private Body body;
    /**
     * cached reference to collision tester (to preserve subtype information)
     */
    private VehicleCollisionTester tester;
    // *************************************************************************
    // constructors

    /**
     * Instantiate a constraint for the specified body and settings.
     *
     * @param body the body to which the constraint will apply (not null)
     * @param settings the desired settings (not null, unaffected)
     */
    public VehicleConstraint(
            Body body, ConstVehicleConstraintSettings settings) {
        assert settings.getController() != null : "no controller";
        assert settings.getNumWheels() > 0 : "no wheels";

        this.body = body;
        long bodyVa = body.va();
        long settingsVa = settings.targetVa();
        long constraintVa = createConstraint(bodyVa, settingsVa);
        setVirtualAddress(constraintVa); // not the owner due to ref counting
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
        long bodyVa = getVehicleBody(constraintVa);
        this.body = new Body(bodyVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the vehicle body.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public Body getVehicleBody() {
        return body;
    }

    /**
     * Access the controller for this constraint.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public VehicleController getController() {
        long constraintVa = va();
        long controllerVa = getController(constraintVa);
        VehicleController result
                = VehicleController.newController(this, controllerVa);

        return result;
    }

    /**
     * Access the vehicle's {@code PhysicsStepListener}. Since Java doesn't
     * allow multiple inheritance, the listener is managed like a contained
     * object.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public VehicleStepListener getStepListener() {
        long constraintVa = va();
        long listenerVa = getStepListener(constraintVa);
        VehicleStepListener result = new VehicleStepListener(this, listenerVa);

        return result;
    }

    /**
     * Access the underlying tester, if any.
     *
     * @return the pre-existing instance or {@code null} if none
     */
    public VehicleCollisionTester getVehicleCollisionTester() {
        assert verifyTester();
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
        int ordinal = Constraint.getControllerType(constraintVa);
        Wheel result = Wheel.newWheel(wheelVa, ordinal, this);

        return result;
    }

    /**
     * Override the vehicle's gravity vector.
     *
     * @param ax the X component of the desired acceleration vector
     * @param ay the Y component of the desired acceleration vector
     * @param az the Z component of the desired acceleration vector
     */
    public void overrideGravity(float ax, float ay, float az) {
        long constraintVa = va();
        overrideGravity(constraintVa, ax, ay, az);
    }

    /**
     * Override the vehicle's gravity vector.
     *
     * @param acceleration the desired acceleration vector (not null,
     * unaffected)
     */
    public void overrideGravity(Vec3Arg acceleration) {
        float ax = acceleration.getX();
        float ay = acceleration.getY();
        float az = acceleration.getZ();
        overrideGravity(ax, ay, az);
    }

    /**
     * Remove the gravity override, if any.
     */
    public void resetGravityOverride() {
        long constraintVa = va();
        resetGravityOverride(constraintVa);
    }

    /**
     * Alter the vehicle's maximum pitch/roll angle.
     *
     * @param angle the desired limit angle (in radians)
     */
    public void setMaxPitchRollAngle(float angle) {
        long constraintVa = va();
        setMaxPitchRollAngle(constraintVa, angle);
    }

    /**
     * Alter the number of simulation steps between wheel-collision tests when
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
     * Alter the number of simulation steps between wheel-collision tests when
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
     * @param tester the desired tester (not null, counted reference created)
     */
    public void setVehicleCollisionTester(VehicleCollisionTester tester) {
        this.tester = tester;
        long constraintVa = va();
        long testerVa = tester.va();
        setVehicleCollisionTester(constraintVa, testerVa);
    }
    // *************************************************************************
    // ConstVehicleConstraint methods

    /**
     * Count how many anti-roll bars the vehicle has. The constraint is
     * unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countAntiRollBars() {
        long constraintVa = va();
        int result = countAntiRollBars(constraintVa);

        return result;
    }

    /**
     * Count how many wheels the vehicle has. The constraint is unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countWheels() {
        long constraintVa = va();
        int result = countWheels(constraintVa);

        return result;
    }

    /**
     * Access the specified anti-roll bar.
     *
     * @param barIndex the index of the bar to access (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public VehicleAntiRollBar getAntiRollBar(int barIndex) {
        long constraintVa = va();
        long barVa = getAntiRollBar(constraintVa, barIndex);
        VehicleAntiRollBar result = new VehicleAntiRollBar(this, barVa);

        return result;
    }

    /**
     * Copy the gravity override. The constraint is unaffected.
     *
     * @return a new vector
     */
    @Override
    public Vec3 getGravityOverride() {
        long constraintVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getGravityOverride(constraintVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the local "forward" direction. The constraint is unaffected.
     *
     * @return a new direction vector
     */
    @Override
    public Vec3 getLocalForward() {
        long constraintVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getLocalForward(constraintVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the local "up" direction. The constraint is unaffected.
     *
     * @return a new direction vector
     */
    @Override
    public Vec3 getLocalUp() {
        long constraintVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getLocalUp(constraintVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Return the vehicle's maximum pitch/roll angle. The constraint is
     * unaffected.
     *
     * @return the limit angle (in radians)
     */
    @Override
    public float getMaxPitchRollAngle() {
        long constraintVa = va();
        float result = getMaxPitchRollAngle(constraintVa);

        return result;
    }

    /**
     * Return the number of simulation steps between wheel-collision tests when
     * the vehicle is active. The constraint is unaffected.
     *
     * @return the number of steps
     */
    @Override
    public int getNumStepsBetweenCollisionTestActive() {
        long constraintVa = va();
        int result = getNumStepsBetweenCollisionTestActive(constraintVa);

        return result;
    }

    /**
     * Return the number of simulation steps between collision tests when the
     * body is inactive. The constraint is unaffected.
     *
     * @return the number of steps
     */
    @Override
    public int getNumStepsBetweenCollisionTestInactive() {
        long constraintVa = va();
        int result = getNumStepsBetweenCollisionTestInactive(constraintVa);

        return result;
    }

    /**
     * Copy the basis vectors for the specified wheel.
     *
     * @param wheel which wheel to query (not null)
     * @param storeForward storage for the forward vector (not null, modified)
     * @param storeUp storage for the up vector (not null, modified)
     * @param storeRight storage for the right vector (not null, modified)
     */
    @Override
    public void getWheelLocalBasis(
            Wheel wheel, Vec3 storeForward, Vec3 storeUp, Vec3 storeRight) {
        long constraintVa = va();
        long wheelVa = wheel.va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getWheelLocalBasis(constraintVa, wheelVa, storeFloats);
        storeForward.set(storeFloats);
        storeUp.set(storeFloats, 3);
        storeRight.set(storeFloats, 6);
    }

    /**
     * Copy the world transform of the specified wheel. The constraint is
     * unaffected.
     *
     * @param wheelIndex the index of the wheel to query (&ge;0)
     * @param right the wheel's axis of rotation (a unit vector in the wheel's
     * model space)
     * @param up the "up" direction (a unit vector in the wheel's model space)
     * @return a new coordinate transform matrix
     */
    @Override
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
     * Copy the "up" direction based on gravity. The constraint is unaffected.
     *
     * @return a new direction vector (in system coordinates)
     */
    @Override
    public Vec3 getWorldUp() {
        long constraintVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getWorldUp(constraintVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Test whether gravity is overridden. The constraint is unaffected.
     *
     * @return {@code true} if overridden, otherwise {@code false}
     */
    @Override
    public boolean isGravityOverridden() {
        long constraintVa = va();
        boolean result = isGravityOverridden(constraintVa);

        return result;
    }
    // *************************************************************************
    // Constraint methods

    /**
     * Create a counted reference to the native {@code VehicleConstraint}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public VehicleConstraintRef toRef() {
        long constraintVa = va();
        long refVa = toRef(constraintVa);
        VehicleConstraintRef result = new VehicleConstraintRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // private methods

    /**
     * Verify that the cached tester matches the native reference.
     *
     * @return {@code true} if the cached object matches the native reference,
     * otherwise {@code false}
     */
    private boolean verifyTester() {
        long expectedVa = (tester == null) ? 0L : tester.targetVa();
        long constraintVa = va();
        long testerVa = getVehicleCollisionTester(constraintVa);

        if (testerVa == expectedVa) {
            return true;
        } else {
            return false;
        }
    }
    // *************************************************************************
    // native methods

    native private static int countAntiRollBars(long constraintVa);

    native private static int countWheels(long constraintVa);

    native private static long createConstraint(long bodyVa, long settingsVa);

    native private static long getAntiRollBar(long constraintVa, int barIndex);

    native private static long getController(long constraintVa);

    native private static void getGravityOverride(
            long constraintVa, FloatBuffer storeFloats);

    native private static void getLocalForward(
            long constraintVa, FloatBuffer storeFloats);

    native private static void getLocalUp(
            long constraintVa, FloatBuffer storeFloats);

    native private static float getMaxPitchRollAngle(long constraintVa);

    native private static int getNumStepsBetweenCollisionTestActive(
            long constraintVa);

    native private static int getNumStepsBetweenCollisionTestInactive(
            long constraintVa);

    native private static long getStepListener(long constraintVa);

    native private static long getVehicleBody(long constraintVa);

    native private static long getVehicleCollisionTester(long constraintVa);

    native private static long getWheel(long constraintVa, int wheelIndex);

    native private static void getWheelLocalBasis(
            long constraintVa, long wheelVa, FloatBuffer storeFloats);

    native static long getWheelWorldTransform(long constraintVa, int wheelIndex,
            float rx, float ry, float rz, float ux, float uy, float uz);

    native static void getWorldUp(long constraintVa, FloatBuffer storeFloats);

    native static boolean isGravityOverridden(long constraintVa);

    native static void overrideGravity(
            long constraintVa, float ax, float ay, float az);

    native static void resetGravityOverride(long constraintVa);

    native static void setMaxPitchRollAngle(long constraintVa, float angle);

    native private static void setNumStepsBetweenCollisionTestActive(
            long constraintVa, int numSteps);

    native private static void setNumStepsBetweenCollisionTestInactive(
            long constraintVa, int numSteps);

    native private static void setVehicleCollisionTester(
            long constraintVa, long testerVa);

    native private static long toRef(long constraintVa);
}
