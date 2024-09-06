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

import com.github.stephengold.joltjni.Mat44;
import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to a {@code MassProperties} object.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstMassProperties extends ConstJoltPhysicsObject {
    // *************************************************************************
    // new methods exposed

    /**
     * Decompose the inertia tensor into a diagonal matrix and a right-handed
     * rotation matrix. The properties are unaffected.
     *
     * @param storeRotation storage for the rotation matrix (not null, modified)
     * @param storeDiagonal storage for the diagonal matrix (not null, modified)
     * @return true if successful, otherwise false
     */
    boolean decomposePrincipalMomentsOfInertia(
            Mat44 storeRotation, Vec3 storeDiagonal);

    /**
     * Copy the inertia tensor. The properties are unaffected. (native field:
     * mIntertia)
     *
     * @return a new matrix (in kilogram.meters squared)
     */
    Mat44 getInertia();

    /**
     * Return the mass. The properties are unaffected. (native field: mMass)
     *
     * @return the mass (in kilograms, &ge;0)
     */
    float getMass();
}
