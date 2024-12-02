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
package com.github.stephengold.joltjni.enumerate;

/**
 * Enumerate types of {@code Constraint}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public enum EConstraintSubType {
    // *************************************************************************
    // values - sequence must match "Jolt/Physics/Constraints/Constraint.h"

    /**
     * FixedConstraint
     */
    Fixed,
    /**
     * PointConstraint
     */
    Point,
    /**
     * HingeConstraint
     */
    Hinge,
    /**
     * SliderConstraint
     */
    Slider,
    /**
     * DistanceConstraint
     */
    Distance,
    /**
     * ConeConstraint
     */
    Cone,
    /**
     * SwingTwistConstraint
     */
    SwingTwist,
    /**
     * SixDofConstraint
     */
    SixDof,
    /**
     * PathConstraint
     */
    Path,
    /**
     * VehicleConstraint
     */
    Vehicle,
    /**
     * RackAndPinionConstraint
     */
    RackAndPinion,
    /**
     * GearConstraint
     */
    Gear,
    /**
     * PulleyConstraint
     */
    Pulley,
    /**
     * user-defined constraint #1
     */
    User1,
    /**
     * user-defined constraint #2
     */
    User2,
    /**
     * user-defined constraint #3
     */
    User3,
    /**
     * user-defined constraint #4
     */
    User4
}
