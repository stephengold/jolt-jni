/*
Copyright (c) 2026 Stephen Gold

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

import com.github.stephengold.joltjni.DebugRenderer;
import com.github.stephengold.joltjni.Mat44Array;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.SkeletonPoseDrawSettings;

/**
 * Read-only access to a {@code SkeletonPose}. (native type: const SkeletonPose)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstSkeletonPose extends ConstJoltPhysicsObject {
    /**
     * Calculate the joint matrices in local space. The pose is unaffected.
     *
     * @param storeMatrices storage for the matrices (not {@code null},
     * modified)
     */
    void calculateLocalSpaceJointMatrices(Mat44Array storeMatrices);

    /**
     * Draw the current pose using the specified settings and renderer. The pose
     * is unaffected.
     *
     * @param settings the desired settings (not {@code null}, unaffected)
     * @param renderer the renderer to use (not {@code null})
     */
    void draw(SkeletonPoseDrawSettings settings, DebugRenderer renderer);

    /**
     * Draw the current pose using the specified settings and renderer. The pose
     * is unaffected.
     *
     * @param settings the desired settings (not {@code null}, unaffected)
     * @param renderer the renderer to use (not {@code null})
     * @param transform the transform to apply (not {@code null}, unaffected,
     * default=Identity)
     */
    void draw(SkeletonPoseDrawSettings settings, DebugRenderer renderer,
            RMat44Arg transform);

    /**
     * Access the transforms of the specified joint. The pose is unaffected.
     *
     * @param jointIndex the index of the joint to access
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstJointState getJoint(int jointIndex);

    /**
     * Count how many joints are in the pose. The pose is unaffected.
     *
     * @return the count (&ge;0)
     */
    int getJointCount();

    /**
     * Access the transform matrix for the specified joint. The pose is
     * unaffected.
     *
     * @param jointIndex which joint (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    Mat44Arg getJointMatrix(int jointIndex);

    /**
     * Copy the root offset. The pose is unaffected.
     *
     * @return a new vector
     */
    RVec3 getRootOffset();

    /**
     * Access the skeleton that underlies this pose. The pose is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstSkeleton getSkeleton();
}
