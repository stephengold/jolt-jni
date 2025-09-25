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

import com.github.stephengold.joltjni.readonly.ConstTwoBodyConstraint;
import java.nio.DoubleBuffer;

/**
 * A type of {@code Constraint} that joins 2 bodies.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class TwoBodyConstraint
        extends Constraint
        implements ConstTwoBodyConstraint {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a constraint with no native object assigned.
     */
    TwoBodyConstraint() {
    }
    // *************************************************************************
    // Constraint methods

    /**
     * Create a counted reference to the native {@code TwoBodyConstraint}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public TwoBodyConstraintRef toRef() {
        long constraintVa = va();
        long refVa = toRef(constraintVa);
        TwoBodyConstraintRef result = new TwoBodyConstraintRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // ConstTwoBodyConstraint methods

    /**
     * Access the first body in the constraint. The constraint is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public Body getBody1() {
        long constraintVa = va();
        long bodyVa = getBody1(constraintVa);
        Body result = new Body(bodyVa);

        return result;
    }

    /**
     * Copy the first body's pivot location. The constraint is unaffected.
     *
     * @param storeResult storage for the location in system coordinates (not
     * null, modified)
     */
    @Override
    public void getBody1PivotLocation(DoubleBuffer storeResult) {
        long constraintVa = va();
        getBody1PivotLocation(constraintVa, storeResult);
    }

    /**
     * Access the 2nd body in the constraint. The constraint is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public Body getBody2() {
        long constraintVa = va();
        long bodyVa = getBody2(constraintVa);
        Body result = new Body(bodyVa);

        return result;
    }

    /**
     * Copy the 2nd body's pivot location. The constraint is unaffected.
     *
     * @param storeResult storage for the location in system coordinates (not
     * null, modified)
     */
    @Override
    public void getBody2PivotLocation(DoubleBuffer storeResult) {
        long constraintVa = va();
        getBody2PivotLocation(constraintVa, storeResult);
    }

    /**
     * Calculate the coordinate transform from constraint space to body 1. The
     * constraint is unaffected.
     *
     * @return a new transform matrix
     */
    @Override
    public Mat44 getConstraintToBody1Matrix() {
        long constraintVa = va();
        long matrixVa = getConstraintToBody1Matrix(constraintVa);
        Mat44 result = new Mat44(matrixVa, true);

        return result;
    }

    /**
     * Calculate the coordinate transform from constraint space to body 2. The
     * constraint is unaffected.
     *
     * @return a new transform matrix
     */
    @Override
    public Mat44 getConstraintToBody2Matrix() {
        long constraintVa = va();
        long matrixVa = getConstraintToBody2Matrix(constraintVa);
        Mat44 result = new Mat44(matrixVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native static long getBody1(long constraintVa);

    native static void getBody1PivotLocation(
            long constraintVa, DoubleBuffer storeResult);

    native static long getBody2(long constraintVa);

    native static void getBody2PivotLocation(
            long constraintVa, DoubleBuffer storeResult);

    native static long getConstraintToBody1Matrix(long constraintVa);

    native static long getConstraintToBody2Matrix(long constraintVa);

    native private static long toRef(long constraintVa);
}
