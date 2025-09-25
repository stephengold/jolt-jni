/*
Copyright (c) 2025 Stephen Gold

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
 * A {@code VehicleCollisionTester} that casts a spherical shape.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class VehicleCollisionTesterCastSphere extends VehicleCollisionTester {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a tester with the specified properties.
     *
     * @param objectLayer the index of the desired object layer for collisions
     * (&ge;0)
     * @param radius the desired radius
     */
    public VehicleCollisionTesterCastSphere(int objectLayer, float radius) {
        this(objectLayer, radius, Vec3.sAxisY());
    }

    /**
     * Instantiate a tester with the specified properties.
     *
     * @param objectLayer the index of the desired object layer for collisions
     * (&ge;0)
     * @param radius the desired radius
     * @param up the "up" direction (default=(0,1,0))
     */
    public VehicleCollisionTesterCastSphere(
            int objectLayer, float radius, Vec3Arg up) {
        this(objectLayer, radius, up, Jolt.degreesToRadians(80f));
    }

    /**
     * Instantiate a tester with the specified properties.
     *
     * @param objectLayer the index of the desired object layer for collisions
     * (&ge;0)
     * @param radius the desired radius
     * @param up the "up" direction (default=(0,1,0))
     * @param maxSlopeAngle the maximum slope angle considered for colliding
     * wheels (in radians, default=4*pi/9)
     */
    public VehicleCollisionTesterCastSphere(
            int objectLayer, float radius, Vec3Arg up, float maxSlopeAngle) {
        float upX = up.getX();
        float upY = up.getY();
        float upZ = up.getZ();
        long testerVa = createTester(
                objectLayer, radius, upX, upY, upZ, maxSlopeAngle);
        setVirtualAddressAsCoOwner(testerVa);
    }

    /**
     * Instantiate a tester with the specified native object assigned.
     *
     * @param testerVa the virtual address of the native object to assign (not
     * zero)
     */
    VehicleCollisionTesterCastSphere(long testerVa) {
        setVirtualAddressAsCoOwner(testerVa);
    }
    // *************************************************************************
    // VehicleCollisionTester methods

    /**
     * Create a counted reference to the native
     * {@code VehicleCollisionTesterCastSphere}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public VehicleCollisionTesterCastSphereRef toRef() {
        long testerVa = va();
        long refVa = VehicleCollisionTester.toRef(testerVa);
        VehicleCollisionTesterCastSphereRef result
                = new VehicleCollisionTesterCastSphereRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createTester(int objectLayer, float radius,
            float upX, float upY, float upZ, float maxSlopeAngle);
}
