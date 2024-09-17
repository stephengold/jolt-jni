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
 * Enumerate types of {@code Shape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public enum EShapeSubType {
    // *************************************************************************
    // values - sequence must match <Jolt/Physics/Collision/Shape/Shape.h>

    /**
     * SphereShape
     */
    Sphere,
    /**
     * BoxShape
     */
    Box,
    /**
     * TriangleShape
     */
    Triangle,
    /**
     * CapsuleShape
     */
    Capsule,
    /**
     * TaperedCapsuleShape
     */
    TaperedCapsule,
    /**
     * CylinderShape
     */
    Cylinder,
    /**
     * ConvexHullShape
     */
    ConvexHull,
    /**
     * StaticCompundShape
     */
    StaticCompound,
    /**
     * MutableCompundShape
     */
    MutableCompound,
    /**
     * RotatedTranslatedShape
     */
    RotatedTranslated,
    /**
     * ScaledShape
     */
    Scaled,
    /**
     * OffsetCenterOfMassShape
     */
    OffsetCenterOfMass,
    /**
     * MeshShape
     */
    Mesh,
    /**
     * HeightFieldShape
     */
    HeightField,
    /**
     * SoftBodyShape
     */
    SoftBody,
    /**
     * user-defined shape #1
     */
    User1,
    /**
     * user-defined shape #2
     */
    User2,
    /**
     * user-defined shape #3
     */
    User3,
    /**
     * user-defined shape #4
     */
    User4,
    /**
     * user-defined shape #5
     */
    User5,
    /**
     * user-defined shape #6
     */
    User6,
    /**
     * user-defined shape #7
     */
    User7,
    /**
     * user-defined shape #8
     */
    User8,
    /**
     * user-defined convex shape #1
     */
    UserConvex1,
    /**
     * user-defined convex shape #2
     */
    UserConvex2,
    /**
     * user-defined convex shape #3
     */
    UserConvex3,
    /**
     * user-defined convex shape #4
     */
    UserConvex4,
    /**
     * user-defined convex shape #5
     */
    UserConvex5,
    /**
     * user-defined convex shape #6
     */
    UserConvex6,
    /**
     * user-defined convex shape #7
     */
    UserConvex7,
    /**
     * user-defined convex shape #8
     */
    UserConvex8,
    /**
     * PlaneShape
     */
    Plane,
    /**
     * TaperedCylinderShape
     */
    TaperedCylinder,
    /**
     * EmptyShape
     */
    Empty
}
