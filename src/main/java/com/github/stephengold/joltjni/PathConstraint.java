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

import com.github.stephengold.joltjni.enumerate.EMotorState;
import com.github.stephengold.joltjni.readonly.ConstPathConstraintPath;

/**
 * A {@code TwoBodyConstraint} that constrains motion to a path.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class PathConstraint extends TwoBodyConstraint {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a constraint with the specified native object assigned but
     * not owned.
     *
     * @param constraintVa the virtual address of the native object to assign
     * (not zero)
     */
    PathConstraint(long constraintVa) {
        super(constraintVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the maximum friction force when not driven by a motor. The
     * constraint is unaffected.
     *
     * @return the force (in Newtons)
     */
    public float getMaxFrictionForce() {
        long constraintVa = va();
        float result = getMaxFrictionForce(constraintVa);

        return result;
    }

    /**
     * Access the path.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public ConstPathConstraintPath getPath() {
        long constraintVa = va();
        long pathVa = getPath(constraintVa);
        PathConstraintPath result = PathConstraintPath.newPath(pathVa);

        return result;
    }

    /**
     * Return how far the body is along the path. The constraint is unaffected.
     *
     * @return the amount
     */
    public float getPathFraction() {
        long constraintVa = va();
        float result = getPathFraction(constraintVa);

        return result;
    }

    /**
     * Access the settings of the position motor.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public MotorSettings getPositionMotorSettings() {
        long constraintVa = va();
        long settingsVa = getPositionMotorSettings(constraintVa);
        MotorSettings result = new MotorSettings(this, settingsVa);

        return result;
    }

    /**
     * Return the state of the position motor. The constraint is unaffected.
     *
     * @return an enum value (not null)
     */
    public EMotorState getPositionMotorState() {
        long constraintVa = va();
        int ordinal = getPositionMotorState(constraintVa);
        EMotorState result = EMotorState.values()[ordinal];

        return result;
    }

    /**
     * Return how far the body is along the path. The constraint is unaffected.
     *
     * @return the amount (&ge;0)
     */
    public float getTargetPathFraction() {
        long constraintVa = va();
        float result = getTargetPathFraction(constraintVa);

        return result;
    }

    /**
     * Return the target speed. The constraint is unaffected.
     *
     * @return the target speed (in meters per second)
     */
    public float getTargetVelocity() {
        long constraintVa = va();
        float result = getTargetVelocity(constraintVa);

        return result;
    }

    /**
     * Return the total lambda of the motor. The constraint is unaffected.
     *
     * @return the total lambda
     */
    public float getTotalLambdaMotor() {
        long constraintVa = va();
        float result = getTotalLambdaMotor(constraintVa);

        return result;
    }

    /**
     * Return the total lambda of the limits. The constraint is unaffected.
     *
     * @return the total lambda
     */
    public float getTotalLambdaPositionLimits() {
        long constraintVa = va();
        float result = getTotalLambdaPositionLimits(constraintVa);

        return result;
    }

    /**
     * Copy the total lambda of rotation. The constraint is unaffected.
     *
     * @return a new vector
     */
    public Vec3 getTotalLambdaRotation() {
        long constraintVa = va();
        float x = getTotalLambdaRotationX(constraintVa);
        float y = getTotalLambdaRotationY(constraintVa);
        float z = getTotalLambdaRotationZ(constraintVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Alter the maximum friction force when not driven by a motor.
     *
     * @param force the desired force (in Newtons)
     */
    public void setMaxFrictionForce(float force) {
        long constraintVa = va();
        setMaxFrictionForce(constraintVa, force);
    }

    /**
     * Replace the path.
     *
     * @param path the desired path (not null)
     * @param amount how far the body is along the new path
     */
    public void setPath(PathConstraintPath path, float amount) {
        long constraintVa = va();
        long pathVa = path.va();
        setPath(constraintVa, pathVa, amount);
    }

    /**
     * Alter the state of the position motor.
     *
     * @param state the desired state (not null)
     */
    public void setPositionMotorState(EMotorState state) {
        long constraintVa = va();
        int ordinal = state.ordinal();
        setPositionMotorState(constraintVa, ordinal);
    }

    /**
     * Alter how far the body is along the path.
     *
     * @param amount the desired amount (&ge;0)
     */
    public void setTargetPathFraction(float amount) {
        long constraintVa = va();
        setTargetPathFraction(constraintVa, amount);
    }

    /**
     * Alter the target speed.
     *
     * @param speed the desired speed
     */
    public void setTargetVelocity(float speed) {
        long constraintVa = va();
        setTargetVelocity(constraintVa, speed);
    }
    // *************************************************************************
    // native private methods

    native private static float getMaxFrictionForce(long constraintVa);

    native private static long getPath(long constraintVa);

    native private static float getPathFraction(long constraintVa);

    native private static long getPositionMotorSettings(long constraintVa);

    native private static int getPositionMotorState(long constraintVa);

    native private static float getTargetPathFraction(long constraintVa);

    native private static float getTargetVelocity(long constraintVa);

    native private static float getTotalLambdaMotor(long constraintVa);

    native private static float getTotalLambdaPositionLimits(long constraintVa);

    native private static float getTotalLambdaRotationX(long constraintVa);

    native private static float getTotalLambdaRotationY(long constraintVa);

    native private static float getTotalLambdaRotationZ(long constraintVa);

    native private static void setMaxFrictionForce(
            long constraintVa, float maxForce);

    native private static void setPath(
            long constraintVa, long pathVa, float amount);

    native private static void setPositionMotorState(
            long constraintVa, int state);

    native private static void setTargetPathFraction(
            long constraintVa, float amount);

    native private static void setTargetVelocity(
            long constraintVa, float speed);
}
