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
 * Bitmasks to enumerate a body's degrees of freedom among the axes of a
 * {@code PhysicsSystem}. (native type: EAllowedDOFs)
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class EAllowedDofs {
    // *************************************************************************
    // constants - values must match <Jolt/Physics/Body/AllowedDOFs.h>

    /**
     * no degrees of freedom (an invalid setting; use a static body instead)
     */
    final public static int None = 0b000000;
    /**
     * all degrees of freedom (this is the default)
     */
    final public static int All = 0b111111;
    /**
     * translation parallel to the system's X axis
     */
    final public static int TranslationX = 0b000001;
    /**
     * translation parallel to the system's Y axis
     */
    final public static int TranslationY = 0b000010;
    /**
     * translation parallel to the system's Z axis
     */
    final public static int TranslationZ = 0b000100;
    /**
     * rotation around an axis parallel to the system's X axis
     */
    final public static int RotationX = 0b001000;
    /**
     * rotation around an axis parallel to the system's Y axis
     */
    final public static int RotationY = 0b010000;
    /**
     * rotation around an axis parallel to the system's Z axis
     */
    final public static int RotationZ = 0b100000;
    /**
     * translation parallel to the X-Y plane and rotation around an axis
     * parallel to Z
     */
    final public static int Plane2D = TranslationX | TranslationY | RotationZ;
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private EAllowedDofs() {
    }
}
