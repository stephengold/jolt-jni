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

import com.github.stephengold.joltjni.readonly.RMat44Arg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;

/**
 * A skeleton with joint transforms specifying a pose.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SkeletonPose extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default pose.
     */
    public SkeletonPose() {
        long poseVa = createSkeletonPoseDefault();
        setVirtualAddress(poseVa, () -> free(poseVa));
    }

    /**
     * Instantiate a clone of the specified pose.
     *
     * @param original the pose to clone (not null, unaffected)
     */
    public SkeletonPose(SkeletonPose original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa, () -> free(copyVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Convert the joint transforms to matrices.
     */
    public void calculateJointMatrices() {
        long poseVa = va();
        calculateJointMatrices(poseVa);
    }

    /**
     * Convert the joint matrices to joint states.
     */
    public void calculateJointStates() {
        long poseVa = va();
        calculateJointStates(poseVa);
    }

    /**
     * Draw the current pose using the specified settings and renderer. The pose
     * is unaffected.
     *
     * @param settings the desired settings (not null, unaffected)
     * @param renderer the renderer to use (not null)
     */
    public void draw(
            SkeletonPoseDrawSettings settings, DebugRenderer renderer) {
        draw(settings, renderer, RMat44.sIdentity());
    }

    /**
     * Draw the current pose using the specified settings and renderer. The pose
     * is unaffected.
     *
     * @param settings the desired settings (not null, unaffected)
     * @param renderer the renderer to use (not null)
     * @param transform the transform to apply (not null, unaffected,
     * default=Identity)
     */
    public void draw(SkeletonPoseDrawSettings settings, DebugRenderer renderer,
            RMat44Arg transform) {
        long poseVa = va();
        long settingsVa = settings.va();
        long rendererVa = renderer.va();
        long transformVa = transform.targetVa();
        draw(poseVa, settingsVa, rendererVa, transformVa);
    }

    /**
     * Access the transforms of the specified joint.
     *
     * @param jointIndex the index of the joint to access
     * @return a new JVM object with the pre-existing native object assigned
     */
    public JointState getJoint(int jointIndex) {
        long poseVa = va();
        long stateVa = getJoint(poseVa, jointIndex);
        JointState result = new JointState(stateVa);

        return result;
    }

    /**
     * Alter the root offset.
     *
     * @param offset the desired offset (not null, unaffected)
     */
    public void setRootOffset(RVec3Arg offset) {
        long poseVa = va();
        double xx = offset.xx();
        double yy = offset.yy();
        double zz = offset.zz();
        setRootOffset(poseVa, xx, yy, zz);
    }

    /**
     * Replace the skeleton.
     *
     * @param skeleton the desired skeleton (not null)
     */
    public void setSkeleton(Skeleton skeleton) {
        long poseVa = va();
        long skeletonVa = skeleton.va();
        setSkeleton(poseVa, skeletonVa);
    }
    // *************************************************************************
    // native private methods

    native private static void calculateJointMatrices(long poseVa);

    native private static void calculateJointStates(long poseVa);

    native private static long createCopy(long poseVa);

    native private static long createSkeletonPoseDefault();

    native private static void draw(
            long poseVa, long settingsVa, long rendererVa, long transformVa);

    native private static void free(long poseVa);

    native private static long getJoint(long poseVa, int jointIndex);

    native private static void setRootOffset(
            long poseVa, double xx, double yy, double zz);

    native private static void setSkeleton(long poseVa, long skeletonVa);
}
