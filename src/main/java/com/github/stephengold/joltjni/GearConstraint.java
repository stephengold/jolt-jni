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

import com.github.stephengold.joltjni.readonly.ConstConstraint;

/**
 * A {@code TwoBodyConstraint} that links the angular velocities of the bodies.
 * To be used in conjunction with 2 hinge constraints.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class GearConstraint extends TwoBodyConstraint {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a constraint with the specified native object assigned but
     * not owned.
     *
     * @param constraintVa the virtual address of the native object to assign
     * (not zero)
     */
    GearConstraint(long constraintVa) {
        super(constraintVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Specify the hinge constraints for each gear (optional).
     *
     * @param hinge1 the desired hinge constraint for the first gear (not null,
     * unaffected)
     * @param hinge2 the desired hinge constraint for the 2nd gear (not null,
     * unaffected)
     */
    public void setConstraints(ConstConstraint hinge1, ConstConstraint hinge2) {
        long gearVa = va();
        long hinge1Va = hinge1.va();
        long hinge2Va = hinge2.va();
        setConstraints(gearVa, hinge1Va, hinge2Va);
    }
    // *************************************************************************
    // native private methods

    native private static void setConstraints(
            long gearVa, long hinge1Va, long hinge2Va);
}
