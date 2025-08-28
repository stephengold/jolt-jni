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

import com.github.stephengold.joltjni.Mat44;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to a {@code SubShape}. (native type:
 * {@code const CompoundShape::SubShape})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstSubShape extends ConstJoltPhysicsObject {
    /**
     * Calculate the local transform for this shape, given the scale of the
     * child. The sub-shape is unaffected.
     *
     * @param scale the scale of the child in the local space of this shape (not
     * null, unaffected)
     * @return a new transform matrix
     */
    Mat44 getLocalTransformNoScale(Vec3Arg scale);

    /**
     * Copy the center-of-mass location. The sub-shape is unaffected. (native
     * function: GetPositionCOM)
     *
     * @return a new vector
     */
    Vec3 getPositionCom();

    /**
     * Copy the rotation. The sub-shape is unaffected.
     *
     * @return a new rotation quaternion
     */
    Quat getRotation();

    /**
     * Access the child shape. The sub-shape is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstShape getShape();

    /**
     * Test whether the specified scale is valid for the sub-shape. The
     * sub-shape is unaffected.
     *
     * @param scale the scale factors to validate (not null, unaffected)
     * @return {@code true} if valid, otherwise {@code false}
     */
    boolean isValidScale(Vec3Arg scale);

    /**
     * Transform the specified scale to the local space of the child. The
     * sub-shape is unaffected.
     *
     * @param scale the scale to transform (not null, unaffected)
     * @return a new vector
     */
    Vec3 transformScale(Vec3Arg scale);
}
