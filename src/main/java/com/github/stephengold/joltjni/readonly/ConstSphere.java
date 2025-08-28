/*
Copyright (c) 2025 Stephen Gold

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

import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to a {@code Sphere}. (native type: const Sphere)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstSphere extends ConstJoltPhysicsObject {
    /**
     * Copy the location of the sphere's center.
     *
     * @return a new location vector
     */
    Vec3 getCenter();

    /**
     * Return the radius of the sphere.
     *
     * @return the radius
     */
    float getRadius();

    /**
     * Calculate the support vector for the specified direction.
     *
     * @param direction the direction to use (not null, unaffected)
     * @return a new offset vector
     */
    Vec3 getSupport(Vec3Arg direction);
}
