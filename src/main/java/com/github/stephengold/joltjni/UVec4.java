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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.readonly.UVec4Arg;
import java.util.Objects;

/**
 * A vector composed of 4 unsigned integer components.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class UVec4 implements UVec4Arg {
    // *************************************************************************
    // fields

    /**
     * the 4th (W) component
     */
    private int w;
    /**
     * the first (X) component
     */
    private int x;
    /**
     * the 2nd (Y) component
     */
    private int y;
    /**
     * the 3rd (Z) component
     */
    private int z;
    // *************************************************************************
    // constructors

    /**
     * Instantiate an all-zero vector (0,0,0,0).
     */
    public UVec4() {
        this.w = 0;
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    /**
     * Instantiate a vector with specified components.
     *
     * @param x the desired X component
     * @param y the desired Y component
     * @param z the desired Z component
     * @param w the desired W component
     */
    public UVec4(int x, int y, int z, int w) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Instantiate a copy of the argument.
     *
     * @param vec the vector to copy (not null, unaffected)
     */
    public UVec4(UVec4Arg vec) {
        this.w = vec.getW();
        this.x = vec.getX();
        this.y = vec.getY();
        this.z = vec.getZ();
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Set all 4 components to specified values.
     *
     * @param x the desired X component
     * @param y the desired Y component
     * @param z the desired Z component
     * @param w the desired W component
     */
    public void set(int x, int y, int z, int w) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Alter the 4th (W) component.
     *
     * @param w the desired value
     */
    public void setW(int w) {
        this.w = w;
    }

    /**
     * Alter the first (X) component.
     *
     * @param x the desired value
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Alter the 2nd (Y) component.
     *
     * @param y the desired value
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Alter the 3rd (Z) component.
     *
     * @param z the desired value
     */
    public void setZ(int z) {
        this.z = z;
    }
    // *************************************************************************
    // UVec4Arg methods

    /**
     * Return the 4th (W) component. The vector is unaffected.
     *
     * @return the component value
     */
    @Override
    public int getW() {
        return w;
    }

    /**
     * Return the first (X) component. The vector is unaffected.
     *
     * @return the component value
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * Return the 2nd (Y) component. The vector is unaffected.
     *
     * @return the component value
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * Return the 3rd (Z) component. The vector is unaffected.
     *
     * @return the component value
     */
    @Override
    public int getZ() {
        return z;
    }
    // *************************************************************************
    // Object methods

    /**
     * Tests for exact equality with the argument. If {@code other} is null,
     * false is returned. Either way, the current instance is unaffected.
     *
     * @param other the object to compare (unaffected) or {@code null}
     * @return {@code true} if {@code this} and {@code other} have identical
     * values, otherwise {@code false}
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null) {
            return false;
        } else if (getClass() != other.getClass()) {
            return false;
        }

        final UVec4Arg otherVector = (UVec4Arg) other;
        if (x != otherVector.getX()) {
            return false;
        } else if (y != otherVector.getY()) {
            return false;
        } else if (z != otherVector.getZ()) {
            return false;
        } else {
            return w == otherVector.getW();
        }
    }

    /**
     * Return a hash code. If two vectors have identical values, they will have
     * the same hash code. The vector is unaffected.
     *
     * @return a 32-bit value for use in hashing
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(w, x, y, z);
        return result;
    }

    /**
     * Return a string representation of the vector, which is unaffected. For
     * example, a zero vector is represented by:
     * <pre>
     * UVec4(0 0 0 0)
     * </pre>
     *
     * @return the string representation (not null, not empty)
     */
    @Override
    public String toString() {
        String result = "UVec4(" + x + " " + y + " " + z + " " + w + ")";
        return result;
    }
}
