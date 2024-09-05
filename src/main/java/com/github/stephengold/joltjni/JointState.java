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

import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * The local-space coordinate transform of an animation joint relative to its
 * parent joint.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class JointState extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a state with the specified native object assigned but not
     * owned.
     *
     * @param stateVa the virtual address of the native object to assign (not
     * zero)
     */
    JointState(long stateVa) {
        super(stateVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the rotation. The state is unaffected. (native field: mRotation)
     *
     * @return a new rotation quaternion
     */
    public Quat getRotation() {
        long stateVa = va();
        float qw = getRotationW(stateVa);
        float qx = getRotationX(stateVa);
        float qy = getRotationY(stateVa);
        float qz = getRotationZ(stateVa);
        Quat result = new Quat(qx, qy, qz, qw);

        return result;
    }

    /**
     * Copy the translation offset. The state is unaffected. (native field:
     * mTranslation)
     *
     * @return a new offset vector
     */
    public Vec3 getTranslation() {
        long stateVa = va();
        float x = getTranslationX(stateVa);
        float y = getTranslationY(stateVa);
        float z = getTranslationZ(stateVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Alter the rotation. (native field: mRotation)
     *
     * @param rotation the desired rotation (not null, unaffected)
     */
    public void setRotation(QuatArg rotation) {
        long stateVa = va();
        float qw = rotation.getW();
        float qx = rotation.getX();
        float qy = rotation.getY();
        float qz = rotation.getZ();
        setRotation(stateVa, qx, qy, qz, qw);
    }

    /**
     * Alter the translation offset. (native field: mTranslation)
     *
     * @param offset the desired offset (not null, unaffected)
     */
    public void setTranslation(Vec3Arg offset) {
        long stateVa = va();
        float x = offset.getX();
        float y = offset.getY();
        float z = offset.getZ();
        setTranslation(stateVa, x, y, z);
    }
    // *************************************************************************
    // native private methods

    native private static float getRotationW(long stateVa);

    native private static float getRotationX(long stateVa);

    native private static float getRotationY(long stateVa);

    native private static float getRotationZ(long stateVa);

    native private static float getTranslationX(long stateVa);

    native private static float getTranslationY(long stateVa);

    native private static float getTranslationZ(long stateVa);

    native private static void setRotation(
            long stateVa, float qx, float qy, float qz, float qw);

    native private static void setTranslation(
            long stateVa, float x, float y, float z);
}
