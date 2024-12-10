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

import com.github.stephengold.joltjni.StreamOut;

/**
 * Read-only access to a {@code Skeleton}. (native type: const Skeleton)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstSkeleton extends ConstJoltPhysicsObject {
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether the joints are correctly ordered, parents before children.
     *
     * @return {@code true} if in order, otherwise {@code false}
     */
    boolean areJointsCorrectlyOrdered();

    /**
     * Access the specified joint.
     *
     * @param jointIndex the index of the joint to access (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstJoint getJoint(int jointIndex);

    /**
     * Count how many joints are in the skeleton.
     *
     * @return the count (&ge;0)
     */
    int getJointCount();

    /**
     * Find the index of the named joint.
     *
     * @param name the name of the joint to find
     * @return the joint's index
     */
    int getJointIndex(String name);

    /**
     * Access all the joints.
     *
     * @return a new array of new JVM objects with the pre-existing native
     * objects assigned
     */
    ConstJoint[] getJoints();

    /**
     * Save the skeleton to the specified binary stream.
     *
     * @param stream the stream to write to (not null)
     */
    void saveBinaryState(StreamOut stream);
}
