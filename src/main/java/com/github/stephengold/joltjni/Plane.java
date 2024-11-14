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

import com.github.stephengold.joltjni.readonly.ConstPlane;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * A math object used to represent a plane in 3-dimensional space.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class Plane implements ConstPlane {
    // *************************************************************************
    // fields

    /**
     * the constant
     */
    private float c;
    /**
     * the first (X) component of the normal direction
     */
    private float nx;
    /**
     * the 2nd (Y) component of the normal direction
     */
    private float ny;
    /**
     * the 3rd (Z) component of the normal direction
     */
    private float nz;
    // *************************************************************************
    // constructors

    /**
     * Instantiate a plane with specified components.
     *
     * @param nx the X component of the desired normal direction
     * @param ny the Y component of the desired normal direction
     * @param nz the Z component of the desired normal direction
     * @param c the desired constant
     */
    public Plane(float nx, float ny, float nz, float c) {
        this.nx = nx;
        this.ny = ny;
        this.nz = nz;
        this.c = c;
    }

    /**
     * Instantiate a plane with specified components.
     *
     * @param normal the desired normal direction (not null, unaffected)
     * @param c the desired constant
     */
    public Plane(Vec3Arg normal, float c) {
        this.nx = normal.getX();
        this.ny = normal.getY();
        this.nz = normal.getZ();
        this.c = c;
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Set all 4 components to specified values.
     *
     * @param nx the desired X component of the normal direction
     * @param ny the desired Y component of the normal direction
     * @param nz the desired Z component of the normal direction
     * @param c the desired constant
     */
    public void set(float nx, float ny, float nz, float c) {
        this.nx = nx;
        this.ny = ny;
        this.nz = nz;
        this.c = c;
    }

    /**
     * Alter the constant.
     *
     * @param c the desired value
     */
    public void setConstant(float c) {
        this.c = c;
    }

    /**
     * Alter the normal direction.
     *
     * @param normal the desired direction (not null, unaffected)
     */
    public void setNormal(Vec3Arg normal) {
        this.nx = normal.getX();
        this.ny = normal.getY();
        this.nz = normal.getZ();
    }
    // *************************************************************************
    // ConstPlane methods

    /**
     * Return the constant in single precision. The plane is unaffected.
     *
     * @return the constant value
     */
    @Override
    public float getConstant() {
        return c;
    }

    /**
     * Copy the normal direction. The plane is unaffected.
     *
     * @return a new direction vector
     */
    @Override
    public Vec3 getNormal() {
        Vec3 result = new Vec3(nx, ny, nz);
        return result;
    }

    /**
     * Return the first (X) component of the normal direction. The plane is
     * unaffected.
     *
     * @return the component value
     */
    @Override
    public float getNormalX() {
        return nx;
    }

    /**
     * Return the 2nd (Y) component of the normal direction. The plane is
     * unaffected.
     *
     * @return the component value
     */
    @Override
    public float getNormalY() {
        return ny;
    }

    /**
     * Return the 3rd (Z) component of the normal direction. The plane is
     * unaffected.
     *
     * @return the component value
     */
    @Override
    public float getNormalZ() {
        return nz;
    }

    /**
     * Return the signed distance of the specified point.
     *
     * @param point the point to measure (not null, unaffected)
     * @return the signed distance
     */
    @Override
    public float signedDistance(Vec3Arg point) {
        float result
                = point.getX() * nx + point.getY() * ny + point.getZ() * nz + c;
        return result;
    }
    // *************************************************************************
    // Object methods

    /**
     * Return a string representation of the plane, which is unaffected. For
     * example:
     * <pre>
     * Plane{(0.0 1.0 0.0) 0.0}
     * </pre>
     *
     * @return the string representation (not null, not empty)
     */
    @Override
    public String toString() {
        String result = "Plane{(" + nx + " " + ny + " " + nz + ") " + c + "}";
        return result;
    }
}
