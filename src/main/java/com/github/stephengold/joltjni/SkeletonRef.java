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

import com.github.stephengold.joltjni.readonly.ConstSkeleton;
import com.github.stephengold.joltjni.template.Ref;

/**
 * A counted reference to a {@code Skeleton}. (native type:
 * {@code Ref<Skeleton>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class SkeletonRef extends Ref implements ConstSkeleton {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public SkeletonRef() {
        long refVa = createDefault();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    SkeletonRef(long refVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(refVa) : null;
        setVirtualAddress(refVa, freeingAction);
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
        long skeletonVa = targetVa();
        boolean result = Skeleton.areJointsCorrectlyOrdered(skeletonVa);

        return result;
    }

    /**
     * Access the specified joint.
     *
     * @param jointIndex the index of the joint to access (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public Joint getJoint(int jointIndex) {
        long skeletonVa = targetVa();
        long resultVa = Skeleton.getJoint(skeletonVa, jointIndex);
        Joint result = new Joint(this, resultVa);

        return result;
    }

    /**
     * Count how many joints are in the skeleton.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getJointCount() {
        long skeletonVa = targetVa();
        int result = Skeleton.getJointCount(skeletonVa);

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
        long skeletonVa = targetVa();
        int result = Skeleton.getJointIndex(skeletonVa, name);

        return result;
    }

    /**
     * Access all the joints.
     *
     * @return a new array of new JVM objects with the pre-existing native
     * objects assigned
     */
    @Override
    public Joint[] getJoints() {
        long skeletonVa = targetVa();
        int numJoints = Skeleton.getJointCount(skeletonVa);
        Joint[] result = new Joint[numJoints];
        for (int i = 0; i < numJoints; ++i) {
            long jointVa = Skeleton.getJoint(skeletonVa, i);
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
        long skeletonVa = targetVa();
        long streamVa = stream.va();
        Skeleton.saveBinaryState(skeletonVa, streamVa);
    }
    // *************************************************************************
    // Ref methods

    /**
     * Temporarily access the referenced {@code Skeleton}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public Skeleton getPtr() {
        long skeletonVa = targetVa();
        Skeleton result = new Skeleton(skeletonVa);

        return result;
    }

    /**
     * Return the address of the native {@code Skeleton}. No objects are
     * affected.
     *
     * @return a virtual address (not zero)
     */
    @Override
    public long targetVa() {
        long refVa = va();
        long result = getPtr(refVa);

        return result;
    }

    /**
     * Create another counted reference to the native {@code Skeleton}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public SkeletonRef toRef() {
        long refVa = va();
        long copyVa = copy(refVa);
        SkeletonRef result = new SkeletonRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long copy(long refVa);

    native private static long createDefault();

    native static void free(long refVa);

    native private static long getPtr(long refVa);
}
