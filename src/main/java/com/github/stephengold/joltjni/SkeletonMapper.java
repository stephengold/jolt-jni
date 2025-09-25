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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.readonly.ConstSkeleton;
import com.github.stephengold.joltjni.template.RefTarget;

/**
 * Map poses between a low-detail skeleton and a high-detail one.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SkeletonMapper extends JoltPhysicsObject implements RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default mapper.
     */
    public SkeletonMapper() {
        long mapperVa = createDefault();
        long refVa = toRef(mapperVa);
        Runnable freeingAction = () -> SkeletonMapperRef.free(refVa);
        setVirtualAddress(mapperVa, freeingAction);
    }

    /**
     * Instantiate a mapper with the specified native object assigned.
     *
     * @param mapperVa the virtual address of the native object to assign (not
     * zero)
     */
    SkeletonMapper(long mapperVa) {
        long refVa = toRef(mapperVa);
        Runnable freeingAction = () -> SkeletonMapperRef.free(refVa);
        setVirtualAddress(mapperVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Initialize the skeleton mapper as specified.
     *
     * @param skeleton1 source skeleton to map from (not null, unaffected)
     * @param neutralPose1 neutral pose of the source skeleton (in model space,
     * not null, unaffected)
     * @param skeleton2 target skeleton to map to (not null, unaffected)
     * @param neutralPose2 neutral pose of the target skeleton (in model space,
     * not null, unaffected)
     */
    public void initialize(ConstSkeleton skeleton1, Mat44Array neutralPose1,
            ConstSkeleton skeleton2, Mat44Array neutralPose2) {
        long mapperVa = va();
        long skeleton1Va = skeleton1.targetVa();
        long pose1Va = neutralPose1.va();
        long skeleton2Va = skeleton2.targetVa();
        long pose2Va = neutralPose2.va();
        initialize(mapperVa, skeleton1Va, pose1Va, skeleton2Va, pose2Va);
    }

    /**
     * Lock the translations of specified joints in the target skeleton.
     *
     * @param skeleton2 the target skeleton (not null, unaffected)
     * @param neutralPose2 the desired pose (not null, unaffected)
     */
    public void lockAllTranslations(
            ConstSkeleton skeleton2, Mat44Array neutralPose2) {
        long mapperVa = va();
        long skeleton2Va = skeleton2.targetVa();
        long pose2Va = neutralPose2.va();
        lockAllTranslations(mapperVa, skeleton2Va, pose2Va);
    }

    /**
     * Map the specified pose.
     *
     * @param pose1ModelSpace the pose on skeleton 1 (in model space, not null,
     * unaffected)
     * @param pose2LocalSpace the pose on skeleton 2 (in local space, not null,
     * unaffected)
     * @param storePose2ModelSpace storage for the resulting pose on skeleton 2
     * (in model space, not null, all elements non-null, modified)
     */
    public void map(Mat44Array pose1ModelSpace, Mat44Array pose2LocalSpace,
            Mat44Array storePose2ModelSpace) {
        long mapperVa = va();
        long pose1Va = pose1ModelSpace.va();
        long pose2LocalVa = pose2LocalSpace.va();
        long storePose2ModelVa = storePose2ModelSpace.va();
        map(mapperVa, pose1Va, pose2LocalVa, storePose2ModelVa);
    }

    /**
     * Reverse map a pose.
     *
     * @param pose2ModelSpace a model-space pose on skeleton 2 (not null,
     * unaffected)
     * @param storePose1ModelSpace storage for the model space pose on skeleton
     * 1 (not null, all elements non-null, modified)
     */
    public void mapReverse(
            Mat44Array pose2ModelSpace, Mat44Array storePose1ModelSpace) {
        long mapperVa = va();
        long pose2Va = pose2ModelSpace.va();
        long storePose1Va = storePose1ModelSpace.va();
        mapReverse(mapperVa, pose2Va, storePose1Va);
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code SkeletonMapper}. The
     * mapper is unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long mapperVa = va();
        int result = getRefCount(mapperVa);

        return result;
    }

    /**
     * Mark the native {@code SkeletonMapper} as embedded.
     */
    @Override
    public void setEmbedded() {
        long mapperVa = va();
        setEmbedded(mapperVa);
    }

    /**
     * Create a counted reference to the native {@code Shape}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public SkeletonMapperRef toRef() {
        long mapperVa = va();
        long copyVa = toRef(mapperVa);
        SkeletonMapperRef result = new SkeletonMapperRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long createDefault();

    native private static int getRefCount(long skeletonVa);

    native private static void initialize(long mapperVa, long skeleton1Va,
            long pose1Va, long skeleton2Va, long pose2Va);

    native private static void lockAllTranslations(
            long mapperVa, long skeleton2Va, long pose2Va);

    native private static void map(long mapperVa, long pose1Va,
            long pose2LocalVa, long storePose2ModelVa);

    native private static void mapReverse(
            long mapperVa, long pose2Va, long storePose1Va);

    native private static void setEmbedded(long skeletonVa);

    native private static long toRef(long skeletonVa);
}
