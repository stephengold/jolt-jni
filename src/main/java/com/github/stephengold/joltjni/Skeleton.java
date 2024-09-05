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

import com.github.stephengold.joltjni.template.RefTarget;

/**
 * A joint hierarchy for skeletal animation.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Skeleton extends JoltPhysicsObject implements RefTarget {
    // *************************************************************************
    // constructors

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
     * Fill in the parent joint indices based on name.
     */
    public void calculateParentJointIndices() {
        long skeletonVa = va();
        calculateParentJointIndices(skeletonVa);
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the skeleton. The skeleton is unaffected.
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
     * Create a counted reference to the skeleton.
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
    // native private methods

    native private static void calculateParentJointIndices(long skeletonVa);

    native private static int getRefCount(long skeletonVa);

    native private static long toRef(long skeletonVa);
}
