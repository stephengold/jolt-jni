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
 * Control the acceleration and deceleration of a wheeled vehicle.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class WheeledVehicleController extends VehicleController {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param constraint the containing object, or {@code null} if none
     * @param controllerVa the virtual address of the native object to assign
     * (not zero)
     */
    WheeledVehicleController(VehicleConstraint constraint, long controllerVa) {
        super(constraint, controllerVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return how strongly the brake pedal is pressed. The controller is
     * unaffected. (native member: mBrakeInput)
     *
     * @return the value (&ge;0, &le;1)
     */
    public float getBrakeInput() {
        long controllerVa = va();
        float result = getBrakeInput(controllerVa);

        return result;
    }

    /**
     * Access the vehicle's differentials.
     *
     * @return a new array of JVM objects, each with a pre-existing native
     * object assigned
     */
    public VehicleDifferentialSettings[] getDifferentials() {
        long controllerVa = va();
        int numDifferentials = countDifferentials(controllerVa);
        VehicleDifferentialSettings[] result
                = new VehicleDifferentialSettings[numDifferentials];
        JoltPhysicsObject container = getContainingObject();
        for (int i = 0; i < numDifferentials; ++i) {
            long settingsVa = getDifferential(controllerVa, i);
            result[i] = new VehicleDifferentialSettings(container, settingsVa);
        }

        return result;
    }

    /**
     * Access the vehicle's engine.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public VehicleEngine getEngine() {
        long controllerVa = va();
        long engineVa = getEngine(controllerVa);
        JoltPhysicsObject container = getContainingObject();
        VehicleEngine result = new VehicleEngine(container, engineVa);

        return result;
    }

    /**
     * Return the forward acceleration. The controller is unaffected. (native
     * field: mForwardInput)
     *
     * @return the value (&ge;-1, &le;1)
     */
    public float getForwardInput() {
        long controllerVa = va();
        float result = getForwardInput(controllerVa);

        return result;
    }

    /**
     * Return how strongly the hand brake is pulled. The controller is
     * unaffected. (native field: mHandBrakeInput)
     *
     * @return the value (&ge;0, &le;1)
     */
    public float getHandBrakeInput() {
        long controllerVa = va();
        float result = getHandBrakeInput(controllerVa);

        return result;
    }

    /**
     * Return the steering angle. The controller is unaffected. (native member:
     * mRightInput)
     *
     * @return the steering angle (&ge;-1, &le;1, right=1)
     */
    public float getRightInput() {
        long controllerVa = va();
        float result = getRightInput(controllerVa);

        return result;
    }

    /**
     * Access the vehicle's transmission.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public VehicleTransmission getTransmission() {
        long controllerVa = va();
        long transmissionVa = getTransmission(controllerVa);
        JoltPhysicsObject container = getContainingObject();
        VehicleTransmission result
                = new VehicleTransmission(container, transmissionVa);

        return result;
    }

    /**
     * Alter how strongly the brake pedal is pressed. (native member:
     * mBrakeInput)
     *
     * @param pressure the desired pressure (&ge;0, &le;1, default=0)
     */
    public void setBrakeInput(float pressure) {
        long controllerVa = va();
        setBrakeInput(controllerVa, pressure);
    }

    /**
     * Alter the wheel-speed ratio of each differential, measured at the clutch.
     * (native member: mDifferentialLimitedSlipRatio)
     *
     * @param ratio the desired ratio (&gt;0)
     */
    public void setDifferentialLimitedSlipRatio(float ratio) {
        long controllerVa = va();
        setDifferentialLimitedSlipRatio(controllerVa, ratio);
    }

    /**
     * Update the control inputs from the vehicle's driver.
     *
     * @param forward the desired driving direction and amount of acceleration
     * (&ge;-1, &le;1)
     * @param right the desired steering angle (&ge;-1, &le;1)
     * @param brake the desired main brake pressure (&ge;0, &le;1)
     * @param handBrake the desired amount of hand braking (&ge;0, &le;1)
     */
    public void setDriverInput(
            float forward, float right, float brake, float handBrake) {
        long controllerVa = va();
        setDriverInput(controllerVa, forward, right, brake, handBrake);
    }

    /**
     * Alter the forward acceleration. (native member: mForwardInput)
     *
     * @param forward the desired driving direction and accelerator pedal
     * pressure (&ge;-1, &le;1, default=0)
     */
    public void setForwardInput(float forward) {
        long controllerVa = va();
        setForwardInput(controllerVa, forward);
    }

    /**
     * Alter how strongly the hand brake is pulled. (native member:
     * mHandBrakeInput)
     *
     * @param pressure the desired pressure (&ge;0, &le;1, default=0)
     */
    public void setHandBrakeInput(float pressure) {
        long controllerVa = va();
        setHandBrakeInput(controllerVa, pressure);
    }

    /**
     * Alter the steering amount. (native member: mRightInput)
     *
     * @param right the desired steering amount (&ge;-1, &le;1, default=0,
     * right=1)
     */
    public void setRightInput(float right) {
        long controllerVa = va();
        setRightInput(controllerVa, right);
    }
    // *************************************************************************
    // native private methods

    native private static int countDifferentials(long controllerVa);

    native private static float getBrakeInput(long controllerVa);

    native private static long getDifferential(long controllerVa, int i);

    native private static long getEngine(long controllerVa);

    native private static float getForwardInput(long controllerVa);

    native private static float getHandBrakeInput(long controllerVa);

    native private static float getRightInput(long controllerVa);

    native private static long getTransmission(long controllerVa);

    native private static void setBrakeInput(long controllerVa, float pressure);

    native private static void setDifferentialLimitedSlipRatio(
            long controllerVa, float ratio);

    native private static void setDriverInput(long controllerVa, float forward,
            float right, float brake, float handBrake);

    native private static void setForwardInput(
            long controllerVa, float forward);

    native private static void setHandBrakeInput(
            long controllerVa, float pressure);

    native private static void setRightInput(long controllerVa, float right);
}
