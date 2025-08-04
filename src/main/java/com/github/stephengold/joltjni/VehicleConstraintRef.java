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

import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.template.Ref;
import java.nio.FloatBuffer;

/**
 * A counted reference to a {@code VehicleConstraint}. (native type:
 * {@code Ref<VehicleConstraint>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class VehicleConstraintRef extends Ref {
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
     * Copy the world transform of the specified wheel. The constraint is
     * unaffected.
     *
     * @param wheelIndex the index of the wheel to query (&ge;0)
     * @param right the wheel's axis of rotation (a unit vector in the wheel's
     * model space)
     * @param up the "up" direction (a unit vector in the wheel's model space)
     * @return a new coordinate transform matrix
     */
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
     * Copy the "up" direction based on gravity. The constraint is unaffected.
     *
     * @return a new direction vector (in system coordinates)
     */
    public Vec3 getWorldUp() {
        long constraintVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        VehicleConstraint.getWorldUp(constraintVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Override the vehicle's gravity vector.
     *
     * @param acceleration the desired acceleration vector (not null,
     * unaffected)
     */
    public void overrideGravity(Vec3Arg acceleration) {
        long constraintVa = targetVa();
        float ax = acceleration.getX();
        float ay = acceleration.getY();
        float az = acceleration.getZ();
        VehicleConstraint.overrideGravity(constraintVa, ax, ay, az);
    }

    /**
     * Remove the gravity override, if any.
     */
    public void resetGravityOverride() {
        long constraintVa = targetVa();
        VehicleConstraint.resetGravityOverride(constraintVa);
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
