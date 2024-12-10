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

import com.github.stephengold.joltjni.readonly.ConstJoint;
import com.github.stephengold.joltjni.readonly.ConstSkeleton;
import com.github.stephengold.joltjni.template.RefTarget;

/**
 * A joint hierarchy for skeletal animation.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Skeleton
        extends JoltPhysicsObject
        implements ConstSkeleton, RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default skeleton.
     */
    public Skeleton() {
        long skeletonVa = createDefault();
        setVirtualAddress(skeletonVa); // not owner due to ref counting
    }

    /**
     * Instantiate a skeleton with the specified native object assigned but not
     * owned.
     *
     * @param skeletonVa the virtual address of the native object to assign (not
     * zero)
     */
    Skeleton(long skeletonVa) {
        super(skeletonVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Add a root joint.
     *
     * @param name the desired joint name
     * @return the index of the new joint
     */
    public int addJoint(String name) {
        long skeletonVa = va();
        int result = addRootJoint(skeletonVa, name);

        return result;
    }

    /**
     * Add a non-root joint.
     *
     * @param name the desired joint name
     * @param parentIndex the index of the desired parent joint
     * @return the index of the new joint
     */
    public int addJoint(String name, int parentIndex) {
        long skeletonVa = va();
        int result = addJointWithParent(skeletonVa, name, parentIndex);

        return result;
    }

    /**
     * Fill in the parent joint indices based on name.
     */
    public void calculateParentJointIndices() {
        long skeletonVa = va();
        calculateParentJointIndices(skeletonVa);
    }
    // *************************************************************************
    // ConstSkeleton methods

    /**
     * Test whether the joints are correctly ordered, parents before children.
     *
     * @return {@code true} if in order, otherwise {@code false}
     */
    @Override
    public boolean areJointsCorrectlyOrdered() {
        long skeletonVa = va();
        boolean result = areJointsCorrectlyOrdered(skeletonVa);

        return result;
    }

    /**
     * Access the specified joint.
     *
     * @param jointIndex the index of the joint to access (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public ConstJoint getJoint(int jointIndex) {
        long skeletonVa = va();
        long resultVa = getJoint(skeletonVa, jointIndex);
        ConstJoint result = new Joint(this, resultVa);

        return result;
    }

    /**
     * Count how many joints are in the skeleton.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getJointCount() {
        long skeletonVa = va();
        int result = getJointCount(skeletonVa);

        return result;
    }

    /**
     * Find the index of the named joint.
     *
     * @param name the name of the joint to find (not null)
     * @return the joint index
     */
    @Override
    public int getJointIndex(String name) {
        long skeletonVa = va();
        int result = getJointIndex(skeletonVa, name);

        return result;
    }

    /**
     * Access all the joints.
     *
     * @return a new array of new JVM objects with the pre-existing native
     * objects assigned
     */
    @Override
    public ConstJoint[] getJoints() {
        long skeletonVa = va();
        int numJoints = getJointCount(skeletonVa);
        ConstJoint[] result = new ConstJoint[numJoints];
        for (int i = 0; i < numJoints; ++i) {
            long jointVa = getJoint(skeletonVa, i);
            result[i] = new Joint(this, jointVa);
        }

        return result;
    }

    /**
     * Save the skeleton to the specified binary stream.
     *
     * @param stream the stream to write to (not null)
     */
    @Override
    public void saveBinaryState(StreamOut stream) {
        long skeletonVa = va();
        long streamVa = stream.va();
        saveBinaryState(skeletonVa, streamVa);
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code Skeleton}. The skeleton
     * is unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long skeletonVa = va();
        int result = getRefCount(skeletonVa);

        return result;
    }

    /**
     * Mark the native {@code Skeleton} as embedded.
     */
    @Override
    public void setEmbedded() {
        long skeletonVa = va();
        setEmbedded(skeletonVa);
    }

    /**
     * Create a counted reference to the native {@code Skeleton}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public SkeletonRef toRef() {
        long skeletonVa = va();
        long refVa = toRef(skeletonVa);
        SkeletonRef result = new SkeletonRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native static int addJointWithParent(
            long skeletonVa, String name, int parentIndex);

    native static int addRootJoint(long skeletonVa, String name);

    native static boolean areJointsCorrectlyOrdered(long skeletonVa);

    native private static void calculateParentJointIndices(long skeletonVa);

    native private static long createDefault();

    native static long getJoint(long skeletonVa, int jointIndex);

    native static int getJointCount(long skeletonVa);

    native static int getJointIndex(long skeletonVa, String name);

    native private static int getRefCount(long skeletonVa);

    native static void saveBinaryState(long skeletonVa, long streamVa);

    native private static void setEmbedded(long skeletonVa);

    native private static long toRef(long skeletonVa);
}
