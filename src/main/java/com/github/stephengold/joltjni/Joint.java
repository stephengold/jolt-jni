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

/**
 * A particular joint in a skeleton. (native type: {@code Skeleton::Joint})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Joint extends JoltPhysicsObject implements ConstJoint {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default joint.
     */
    public Joint() {
        long jointVa = createDefault();
        setVirtualAddress(jointVa, () -> free(jointVa));
    }

    /**
     * Instantiate a joint with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param jointVa the virtual address of the native object to assign (not
     * zero)
     */
    Joint(JoltPhysicsObject container, long jointVa) {
        super(container, jointVa);
    }
    // *************************************************************************
    // ConstJoint methods

    /**
     * Return the name of the joint. The joint is unaffected. (native
     * property: mName)
     *
     * @return the name
     */
    @Override
    public String getName() {
        long jointVa = va();
        String result = getName(jointVa);

        return result;
    }

    /**
     * Return the index of the parent joint. The joint is unaffected. (native
     * property: mParentJointIndex)
     *
     * @return the index (&ge;0) or -1 for a root joint
     */
    @Override
    public int getParentJointIndex() {
        long jointVa = va();
        int result = getParentJointIndex(jointVa);

        return result;
    }

    /**
     * Return the name of the parent joint. The joint is unaffected (native
     * property: mParentName)
     *
     * @return the name
     */
    @Override
    public String getParentName() {
        long jointVa = va();
        String result = getParentName(jointVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createDefault();

    native private static void free(long jointVa);

    native private static String getName(long jointVa);

    native private static int getParentJointIndex(long jointVa);

    native private static String getParentName(long jointVa);
}
