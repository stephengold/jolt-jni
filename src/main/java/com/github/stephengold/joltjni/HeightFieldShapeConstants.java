/*
Copyright (c) 2024-2026 Stephen Gold

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

import com.github.stephengold.joltjni.std.Std;

/**
 * Constants used with {@code HeightFieldShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class HeightFieldShapeConstants {
    // *************************************************************************
    // constants
    // - values must match Jolt/Physics/Collision/Shape/HeightFieldShape.h

    /**
     * value used to create gaps in a height field
     */
    final public static float cNoCollisionValue = Std.FLT_MAX;
    /**
     * stack size for WalkHeightField
     */
    final public static int cStackSize = 128;
    /**
     * maximum number of bits in an X or Y coordinate
     */
    final public static int cNumBitsXY = 14;
    /**
     * maximum X or Y coordinate
     */
    final public static int cMaskBitsXY = (1 << cNumBitsXY) - 1;
    /**
     * bit shift used to encode positions in the hierarchy
     */
    final public static int cLevelShift = 2 * cNumBitsXY;
    /**
     * {@code cNoCollisionValue} converted to 16 bits
     */
    final public static int cNoCollisionValue16 = 0xFFFF;
    /**
     * maximum height when converted to 16 bits
     */
    final public static int cMaxHeightValue16 = 0xFFFE;
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private HeightFieldShapeConstants() {
    }
}
