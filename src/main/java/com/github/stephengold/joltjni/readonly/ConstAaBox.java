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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.AaBox;
import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to an {@code AaBox}. (native type: const AABox)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstAaBox extends ConstJoltPhysicsObject {
    /**
     * Check if the box contains the other box. The box is not affected.
     *
     * @param other the other box to check
     *
     * @return {@code true} if contained, otherwise {@code false}
     */
    boolean contains(ConstAaBox other);

    /**
     * Test whether the box contains the specified point. The box is unaffected.
     *
     * @param point the point to test (not null, unaffected)
     *
     * @return {@code true} if contained, otherwise {@code false}
     */
    boolean contains(Vec3Arg point);

    /**
     * Locate the center of the box. The box is unaffected.
     *
     * @return a new location vector
     */
    Vec3 getCenter();

    /**
     * Locate the closest point on or in the box for the specified location. The
     * box is unaffected.
     *
     * @param location the starting location (not null, unaffected)
     * @return a new vector
     */
    Vec3 getClosestPoint(Vec3Arg location);

    /**
     * Copy the (half) extent of the box. The box is unaffected.
     *
     * @return a new vector
     */
    Vec3 getExtent();

    /**
     * Copy the maximum contained coordinate on each axis. The box is
     * unaffected.
     *
     * @return a new location vector
     */
    Vec3 getMax();

    /**
     * Copy the minimum contained coordinate on each axis. The box is
     * unaffected.
     *
     * @return a new location vector
     */
    Vec3 getMin();

    /**
     * Copy the size (full extent) on each axis. The box is unaffected.
     *
     * @return a new vector
     */
    Vec3 getSize();

    /**
     * Get the squared distance between {@code point} and this box (will be 0
     * if in Point is inside the box)
     *
     * @param point The point to check
     *
     * @return the distance from this box to the given point
     */
    float getSqDistanceTo(Vec3Arg point);

    /**
     * Calculate the support vector for this convex shape.
     *
     * @param direction the direction vector
     *
     * @return the support vector
     */
    Vec3 getSupport(Vec3Arg direction);

    /**
     * Get surface area of bounding box.
     *
     * @return the area
     */
    float getSurfaceArea();

    /**
     * Return the volume of the box. The box is unaffected.
     *
     * @return the volume
     */
    float getVolume();

    /**
     * Test whether the box is valid. It is unaffected.
     *
     * @return {@code true} if valid, otherwise {@code false}
     */
    boolean isValid();

    /**
     * Check if this box overlaps with another box.
     *
     * @param other the other box to check
     *
     * @return {@code true} if they overlap, otherwise {@code false}
     */
    boolean overlaps(ConstAaBox other);

    /**
     * Check if this box overlaps with a plane.
     *
     * @param plane the {@code Plane} object to be checked
     *
     * @return {@code true} if they overlap, otherwise {@code false}
     */
    boolean overlaps(ConstPlane plane);

    /**
     * Return a scaled copy of the box. The current box is unaffected.
     *
     * @param factors the scale factors to apply (not null, unaffected)
     * @return a new object
     */
    AaBox scaled(Vec3Arg factors);

    /**
     * Return a transformed copy of the box The current box is unaffected.
     *
     * @param matrix the transformation to apply (not null, unaffected)
     * @return a new object
     */
    AaBox transformed(Mat44Arg matrix);
}
