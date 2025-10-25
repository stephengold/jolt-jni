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

import com.github.stephengold.joltjni.enumerate.EConstraintSubType;
import com.github.stephengold.joltjni.enumerate.EConstraintType;
import com.github.stephengold.joltjni.readonly.ConstVehicleConstraint;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.template.Ref;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

/**
 * A counted reference to a {@code VehicleConstraint}. (native type:
 * {@code Ref<VehicleConstraint>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class VehicleConstraintRef
        extends Ref
        implements ConstVehicleConstraint {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public VehicleConstraintRef() {
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
    VehicleConstraintRef(long refVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(refVa) : null;
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Override the vehicle's gravity vector.
     *
     * @param ax the X component of the desired acceleration vector
     * @param ay the Y component of the desired acceleration vector
     * @param az the Z component of the desired acceleration vector
     */
    public void overrideGravity(float ax, float ay, float az) {
        long constraintVa = targetVa();
        VehicleConstraint.overrideGravity(constraintVa, ax, ay, az);
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
        long constraintVa = targetVa();
        VehicleConstraint.resetGravityOverride(constraintVa);
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
        long constraintVa = targetVa();
        int result = VehicleConstraint.countAntiRollBars(constraintVa);

        return result;
    }

    /**
     * Count how many wheels the vehicle has. The constraint is unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countWheels() {
        long constraintVa = targetVa();
        int result = VehicleConstraint.countWheels(constraintVa);

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
        long constraintVa = targetVa();
        long barVa = VehicleConstraint.getAntiRollBar(constraintVa, barIndex);
        VehicleAntiRollBar result = new VehicleAntiRollBar(this, barVa);

        return result;
    }

    /**
     * Return the constraint's priority when solving. The constraint is
     * unaffected.
     *
     * @return the priority level
     */
    @Override
    public int getConstraintPriority() {
        long constraintVa = targetVa();
        int result = Constraint.getConstraintPriority(constraintVa);

        return result;
    }

    /**
     * Generate settings to reconstruct the constraint. The constraint is
     * unaffected.
     *
     * @return a new counted reference to a new settings object
     */
    @Override
    public ConstraintSettingsRef getConstraintSettings() {
        long constraintVa = targetVa();
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
        long constraintVa = targetVa();
        boolean result = Constraint.getEnabled(constraintVa);

        return result;
    }

    /**
     * Copy the gravity override. The constraint is unaffected.
     *
     * @return a new vector
     */
    @Override
    public Vec3 getGravityOverride() {
        long constraintVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        VehicleConstraint.getGravityOverride(constraintVa, storeFloats);
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
        long constraintVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        VehicleConstraint.getLocalForward(constraintVa, storeFloats);
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
        long constraintVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        VehicleConstraint.getLocalUp(constraintVa, storeFloats);
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
        long constraintVa = targetVa();
        float result = VehicleConstraint.getMaxPitchRollAngle(constraintVa);

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
        long constraintVa = targetVa();
        int result = Constraint.getNumPositionStepsOverride(constraintVa);

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
        long constraintVa = targetVa();
        int result = VehicleConstraint.getNumStepsBetweenCollisionTestActive(
                constraintVa);

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
        long constraintVa = targetVa();
        int result = VehicleConstraint.getNumStepsBetweenCollisionTestInactive(
                constraintVa);

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
        long constraintVa = targetVa();
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
        long constraintVa = targetVa();
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
        long constraintVa = targetVa();
        int ordinal = Constraint.getType(constraintVa);
        EConstraintType result = EConstraintType.values()[ordinal];

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
        long constraintVa = targetVa();
        long wheelVa = wheel.va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        VehicleConstraint.getWheelLocalBasis(
                constraintVa, wheelVa, storeFloats);
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
        long constraintVa = targetVa();
        float rx = right.getX();
        float ry = right.getY();
        float rz = right.getZ();
        float ux = up.getX();
        float uy = up.getY();
        float uz = up.getZ();
        long matrixVa = VehicleConstraint.getWheelWorldTransform(
                constraintVa, wheelIndex, rx, ry, rz, ux, uy, uz);
        RMat44 result = new RMat44(matrixVa, true);

        return result;
    }

    /**
     * Copy the world transform of the specified wheel. The constraint is
     * unaffected.
     *
     * @param wheelIndex the index of the wheel to query (&ge;0)
     * @param right the wheel's axis of rotation (a unit vector in the wheel's
     * model space)
     * @param up the "up" direction (a unit vector in the wheel's model space)
     * @param storePosition storage for the translation component (not null,
     * modified)
     * @param storeRotation storage for the rotation component (not null,
     * modified)
     */
    @Override
    public void getWheelPositionAndRotation(int wheelIndex, Vec3Arg right,
            Vec3Arg up, RVec3 storePosition, Quat storeRotation) {
        long constraintVa = targetVa();
        DoubleBuffer storeDoubles = Temporaries.doubleBuffer1.get();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        right.copyTo(storeFloats);
        up.copyTo(storeFloats, 3);
        VehicleConstraint.getWheelWorldTransformComponents(
                constraintVa, wheelIndex, storeDoubles, storeFloats);
        storePosition.set(storeDoubles);
        storeRotation.set(storeFloats);
    }

    /**
     * Copy the "up" direction based on gravity. The constraint is unaffected.
     *
     * @return a new direction vector (in system coordinates)
     */
    @Override
    public Vec3 getWorldUp() {
        long constraintVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        VehicleConstraint.getWorldUp(constraintVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Test whether the constraint is active. The constraint is unaffected.
     *
     * @return {@code true} if active, otherwise {@code false}
     */
    @Override
    public boolean isActive() {
        long constraintVa = targetVa();
        boolean result = Constraint.isActive(constraintVa);

        return result;
    }

    /**
     * Test whether gravity is overridden. The constraint is unaffected.
     *
     * @return {@code true} if overridden, otherwise {@code false}
     */
    @Override
    public boolean isGravityOverridden() {
        long constraintVa = targetVa();
        boolean result = VehicleConstraint.isGravityOverridden(constraintVa);

        return result;
    }

    /**
     * Save the state of the constraint, for possible replay. The constraint is
     * unaffected.
     *
     * @param recorder the recorder to write to (not null)
     */
    @Override
    public void saveState(StateRecorder recorder) {
        long constraintVa = targetVa();
        long recorderVa = recorder.va();
        Constraint.saveState(constraintVa, recorderVa);
    }
    // *************************************************************************
    // Ref methods

    /**
     * Temporarily access the referenced {@code VehicleConstraint}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public VehicleConstraint getPtr() {
        long constraintVa = targetVa();
        VehicleConstraint result = new VehicleConstraint(constraintVa);

        return result;
    }

    /**
     * Return the address of the native {@code VehicleConstraint}. No objects
     * are affected.
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
     * Create another counted reference to the native {@code VehicleConstraint}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public VehicleConstraintRef toRef() {
        long refVa = va();
        long copyVa = copy(refVa);
        VehicleConstraintRef result = new VehicleConstraintRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long copy(long refVa);

    native private static long createDefault();

    native private static void free(long refVa);

    native private static long getPtr(long refVa);
}
