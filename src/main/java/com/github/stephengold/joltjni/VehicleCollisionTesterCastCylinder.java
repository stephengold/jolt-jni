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

/**
 * A {@code VehicleCollisionTester} that casts a cylindrical shape.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class VehicleCollisionTesterCastCylinder extends VehicleCollisionTester {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a tester for the specified layer.
     *
     * @param objectLayer the index of the desired object layer for collisions
     * (&ge;0)
     */
    public VehicleCollisionTesterCastCylinder(int objectLayer) {
        this(objectLayer, 0.1f);
    }

    /**
     * Instantiate a tester with the specified properties.
     *
     * @param objectLayer the index of the desired object layer for collisions
     * (&ge;0)
     * @param convexRadiusFraction (default=0.1)
     */
    public VehicleCollisionTesterCastCylinder(
            int objectLayer, float convexRadiusFraction) {
        long testerVa = createTester(objectLayer, convexRadiusFraction);
        setVirtualAddressAsCoOwner(testerVa);
    }

    /**
     * Instantiate a tester with the specified native object assigned.
     *
     * @param testerVa the virtual address of the native object to assign (not
     * zero)
     */
    VehicleCollisionTesterCastCylinder(long testerVa) {
        setVirtualAddressAsCoOwner(testerVa);
    }
    // *************************************************************************
    // VehicleCollisionTester methods

    /**
     * Create a counted reference to the native
     * {@code VehicleCollisionTesterCastCylinder}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public VehicleCollisionTesterCastCylinderRef toRef() {
        long testerVa = va();
        long refVa = VehicleCollisionTester.toRef(testerVa);
        VehicleCollisionTesterCastCylinderRef result
                = new VehicleCollisionTesterCastCylinderRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createTester(
            int objectLayer, float convexRadiusFraction);
}
