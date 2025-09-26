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

import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * The local-space coordinate transform of an animation joint relative to its
 * parent joint. (native type: {@code SkeletonPose::JointState})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class JointState extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a state with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param stateVa the virtual address of the native object to assign (not
     * zero)
     */
    JointState(JoltPhysicsObject container, long stateVa) {
        super(container, stateVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the rotation. The state is unaffected. (native attribute: mRotation)
     *
     * @return a new rotation quaternion
     */
    public Quat getRotation() {
        long stateVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getRotation(stateVa, storeFloats);
        Quat result = new Quat(storeFloats);

        return result;
    }

    /**
     * Copy the translation offset. The state is unaffected. (native attribute:
     * mTranslation)
     *
     * @return a new offset vector
     */
    public Vec3 getTranslation() {
        long stateVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getTranslation(stateVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Alter the rotation. (native attribute: mRotation)
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
     * Alter the translation offset. (native attribute: mTranslation)
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

    native private static void getRotation(
            long stateVa, FloatBuffer storeFloats);

    native private static void getTranslation(
            long stateVa, FloatBuffer storeFloats);

    native private static void setRotation(
            long stateVa, float qx, float qy, float qz, float qw);

    native private static void setTranslation(
            long stateVa, float x, float y, float z);
}
