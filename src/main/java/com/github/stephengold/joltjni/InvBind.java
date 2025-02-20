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

import com.github.stephengold.joltjni.readonly.Mat44Arg;

/**
 * Transform a skinned vertex from its bind pose to joint local space. (native
 * type: {@code SoftBodySharedSettings::InvBind})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class InvBind extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default instance.
     */
    public InvBind() {
        long invBindVa = createDefault();
        setVirtualAddress(invBindVa, () -> free(invBindVa));
    }

    /**
     * Instantiate with the specified joint index and coordinate transform.
     *
     * @param jointIndex the desired joint index (&ge;0)
     * @param transform the desired inverse-bind transform (not null,
     * unaffected)
     */
    public InvBind(int jointIndex, Mat44Arg transform) {
        long transformVa = transform.targetVa();
        long invBindVa = create(jointIndex, transformVa);
        setVirtualAddress(invBindVa, () -> free(invBindVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the inverse-bind transform matrix. The current instance is
     * unaffected. (native attribute: mInvBind)
     *
     * @return a new matrix
     */
    public Mat44 getInvBind() {
        long invBindVa = va();
        long resultVa = getInvBind(invBindVa);
        Mat44 result = new Mat44(resultVa, true);

        return result;
    }

    /**
     * Return the joint index. The current instance is unaffected. (native
     * attribute: mJointIndex)
     *
     * @return the index of the joint (&ge;0)
     */
    public int getJointIndex() {
        long invBindVa = va();
        int result = getJointIndex(invBindVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long create(int jointIndex, long invBindVa);

    native private static long createDefault();

    native private static void free(long invBindVa);

    native private static long getInvBind(long invBindVa);

    native private static int getJointIndex(long invBindVa);
}
