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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.Body;
import com.github.stephengold.joltjni.Mat44;

/**
 * Read-only access to a {@code TwoBodyConstraint}. (native type: const
 * TwoBodyConstraint)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstTwoBodyConstraint extends ConstConstraint {
    // *************************************************************************
    // new methods exposed

    /**
     * Access the first body in the constraint. The constraint is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    Body getBody1();

    /**
     * Access the 2nd body in the constraint. The constraint is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    Body getBody2();

    /**
     * Calculate the coordinate transform from constraint space to body 1. The
     * constraint is unaffected.
     *
     * @return a new transform matrix
     */
    Mat44 getConstraintToBody1Matrix();

    /**
     * Calculate the coordinate transform from constraint space to body 2. The
     * constraint is unaffected.
     *
     * @return a new transform matrix
     */
    Mat44 getConstraintToBody2Matrix();
}
