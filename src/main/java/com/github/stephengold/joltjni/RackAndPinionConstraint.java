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
 * A {@code TwoBodyConstraint} that links the rotation of the first body to the
 * linear motion of the 2nd body.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class RackAndPinionConstraint extends TwoBodyConstraint {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a constraint with the specified native object assigned but
     * not owned.
     *
     * @param constraintVa the virtual address of the native object to assign
     * (not zero)
     */
    RackAndPinionConstraint(long constraintVa) {
        super(constraintVa);
    }
    // *************************************************************************
    // new public methods

    /**
     * Specify the constraints for each body (optional).
     *
     * @param pinion the desired hinge constraint for the pinion (not null,
     * unaffected)
     * @param rack the desired slider constraint for the rack (not null,
     * unaffected)
     */
    public void setConstraints(ConstConstraint pinion, ConstConstraint rack) {
        long randpVa = va();
        long pinionVa = pinion.targetVa();
        long rackVa = rack.targetVa();
        setConstraints(randpVa, pinionVa, rackVa);
    }
    // *************************************************************************
    // native private methods

    native private static void setConstraints(
            long randpVa, long pinionVa, long rackVa);
}
