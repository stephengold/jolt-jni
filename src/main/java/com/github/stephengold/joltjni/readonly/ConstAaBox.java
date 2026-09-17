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
     * Test whether the current box contains the argument box. Both boxes are
     * unaffected.
     *
     * @param other the other box to check against (not {@code null},
     * unaffected)
     *
     * @return {@code true} if contained, otherwise {@code false}
     */
    boolean contains(ConstAaBox other);

    /**
     * Test whether the box contains the specified point. The box is unaffected.
     *
     * @param point the point to test (not {@code null}, unaffected)
     *
     * @return {@code true} if contained, otherwise {@code false}
     */
    boolean contains(Vec3Arg point);

    /**
     * Test whether the box contains the specified point. The box is unaffected.
     *
     * @param x the X coordinate of the point to test
     * @param y the Y coordinate of the point to test
     * @param z the Z coordinate of the point to test
     *
     * @return {@code true} if contained, otherwise {@code false}
     */
    boolean contains(float x, float y, float z);

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
     * @param location the starting location (not {@code null}, unaffected)
     * @return a new vector
     */
    Vec3 getClosestPoint(Vec3Arg location);

    /**
     * Locate the closest point on or in the box for the specified location. The
     * box is unaffected.
     *
     * @param x the X coordinate of the location
     * @param y the Y coordinate of the location
     * @param z the Z coordinate of the location
     *
     * @return a new vector
     */
    Vec3 getClosestPoint(float x, float y, float z);

    /**
     * Locate the closest point on or in the box for the specified location. The
     * box is unaffected.
     *
     * @param x the X coordinate of the location
     * @param y the Y coordinate of the location
     * @param z the Z coordinate of the location
     * @param out storage for the closest point (not {@code null}, modified)
     */
    void getClosestPoint(float x, float y, float z, Vec3 out);

    /**
     * Copy the (half) extent of the box. The box is unaffected.
     *
     * @return a new vector
     */
    Vec3 getExtent();

    /**
     * Copy the (half) extent of the box. The box is unaffected.
     *
     * @param out storage for the extent (not {@code null}, modified)
     */
    void getExtent(Vec3 out);

    /**
     * Copy the maximum contained coordinate on each axis. The box is
     * unaffected.
     *
     * @return a new location vector
     */
    Vec3 getMax();

    /**
     * Copy the maximum contained coordinate on each axis. The box is
     * unaffected.
     *
     * @param out storage for the maximum (not {@code null}, modified)
     */
    void getMax(Vec3 out);

    /**
     * Copy the minimum contained coordinate on each axis. The box is
     * unaffected.
     *
     * @return a new location vector
     */
    Vec3 getMin();

    /**
     * Copy the minimum contained coordinate on each axis. The box is
     * unaffected.
     *
     * @param out storage for the minimum (not {@code null}, modified)
     */
    void getMin(Vec3 out);

    /**
     * Copy the size (full extent) on each axis. The box is unaffected.
     *
     * @return a new vector
     */
    Vec3 getSize();

    /**
     * Copy the size (full extent) on each axis. The box is unaffected.
     *
     * @param out storage for the size (not {@code null}, modified)
     */
    void getSize(Vec3 out);

    /**
     * Get the squared distance between the box and the specified point.
     *
     * @param point the point to measure from (not {@code null}, unaffected)
     *
     * @return the distance, or zero if {@code point} lies inside the box
     */
    float getSqDistanceTo(Vec3Arg point);

    /**
     * Get the squared distance between the box and the specified point.
     *
     * @param x the X coordinate of the point to measure from
     * @param y the Y coordinate of the point to measure from
     * @param z the Z coordinate of the point to measure from
     *
     * @return the distance, or zero if the point lies inside the box
     */
    float getSqDistanceTo(float x, float y, float z);

    /**
     * Calculate the support vector for this convex shape.
     *
     * @param direction the direction vector
     *
     * @return the support vector
     */
    Vec3 getSupport(Vec3Arg direction);

    /**
     * Calculate the support vector for this convex shape.
     *
     * @param x the X component of the direction vector
     * @param y the Y component of the direction vector
     * @param z the Z component of the direction vector
     *
     * @return the support vector
     */
    Vec3 getSupport(float x, float y, float z);

    /**
     * Calculate the support vector for this convex shape. The shape is
     * unaffected.
     *
     * @param direction the direction vector (not {@code null}, unaffected)
     * @param out storage for the support vector (not {@code null}, modified)
     */
    void getSupport(Vec3Arg direction, Vec3 out);

    /**
     * Calculate the support vector for this convex shape. The shape is
     * unaffected.
     *
     * @param x the X component of the direction vector
     * @param y the Y component of the direction vector
     * @param z the Z component of the direction vector
     * @param out storage for the support vector (not {@code null}, modified)
     */
    void getSupport(float x, float y, float z, Vec3 out);

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
     * Test whether the current box is equivalent to the argument. Both boxes
     * are unaffected.
     *
     * @param other the box to compare with (not {@code null}, unaffected)
     * @return {@code true} if equal, {@code false} if unequal
     */
    boolean isEqual(ConstAaBox other);

    /**
     * Test whether the box is valid. It is unaffected.
     *
     * @return {@code true} if valid, otherwise {@code false}
     */
    boolean isValid();

    /**
     * Check if this box overlaps with another box.
     *
     * @param other the other box to check (not {@code null}, unaffected)
     *
     * @return {@code true} if they overlap, otherwise {@code false}
     */
    boolean overlaps(ConstAaBox other);

    /**
     * Check if this box overlaps with a plane.
     *
     * @param plane the {@code Plane} object to be checked (not {@code null},
     * unaffected)
     *
     * @return {@code true} if they overlap, otherwise {@code false}
     */
    boolean overlaps(ConstPlane plane);

    /**
     * Check if this box overlaps with a plane.
     *
     * @param constant the plane constant
     * @param normalX the X component of the plane's normal
     * @param normalY the Y component of the plane's normal
     * @param normalZ the Z component of the plane's normal
     *
     * @return {@code true} if they overlap, otherwise {@code false}
     */
    boolean overlaps(
            float constant, float normalX, float normalY, float normalZ);

    /**
     * Return a scaled copy of the box. The current box is unaffected.
     *
     * @param factors the scale factors to apply (not {@code null}, unaffected)
     * @return a new object
     */
    AaBox scaled(Vec3Arg factors);

    /**
     * Return a scaled copy of the box. The current box is unaffected.
     *
     * @param x the scale factor to apply to the local X axis
     * @param y the scale factor to apply to the local Y axis
     * @param z the scale factor to apply to the local Z axis
     * @return a new object
     */
    AaBox scaled(float x, float y, float z);

    /**
     * Return a transformed copy of the box. The current box is unaffected.
     *
     * @param matrix the transformation to apply (not {@code null}, unaffected)
     * @return a new object
     */
    AaBox transformed(Mat44Arg matrix);
}
