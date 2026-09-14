/*
Copyright (c) 2024-2026 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.ConstJointState;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * The local-space coordinate transform of an animation joint relative to its
 * parent joint. (native type: {@code SkeletonPose::JointState})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class JointState extends JoltPhysicsObject implements ConstJointState {
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
     * Copy both components from the argument.
     *
     * @param source the state to copy (not {@code null}, unaffected)
     */
    public void set(ConstJointState source) {
        long targetVa = va();
        long sourceVa = source.targetVa();
        assign(targetVa, sourceVa);
    }

    /**
     * Alter the rotation component. (native attribute: mRotation)
     *
     * @param rotation the desired rotation (not {@code null}, unaffected,
     * default=(0,0,0,1))
     */
    public void setRotation(QuatArg rotation) {
        setRotation(rotation.getX(), rotation.getY(), rotation.getZ(),
                rotation.getW());
    }

    /**
     * Alter the rotation component. (native attribute: mRotation)
     *
     * @param qx the X component of the desired rotation (default=0)
     * @param qy the Y component of the desired rotation (default=0)
     * @param qz the Z component of the desired rotation (default=0)
     * @param qw the W component of the desired rotation (default=1)
     */
    public void setRotation(float qx, float qy, float qz, float qw) {
        long stateVa = va();
        setRotation(stateVa, qx, qy, qz, qw);
    }

    /**
     * Alter the translation offset component. (native attribute: mTranslation)
     *
     * @param offset the desired offset (not {@code null}, unaffected,
     * default=(0,0,0))
     */
    public void setTranslation(Vec3Arg offset) {
        setTranslation(offset.getX(), offset.getY(), offset.getZ());
    }

    /**
     * Alter the translation offset component. (native attribute: mTranslation)
     *
     * @param x the desired X offset (default=0)
     * @param y the desired Y offset (default=0)
     * @param z the desired Z offset (default=0)
     */
    public void setTranslation(float x, float y, float z) {
        long stateVa = va();
        setTranslation(stateVa, x, y, z);
    }
    // *************************************************************************
    // ConstJointState methods

    /**
     * Copy the rotation. The state is unaffected. (native attribute: mRotation)
     *
     * @return a new rotation quaternion
     */
    @Override
    public Quat getRotation() {
        Quat result = new Quat();
        getRotation(result);
        return result;
    }

    /**
     * Copy the rotation. The state is unaffected. (native attribute: mRotation)
     *
     * @param out storage for the rotation (not {@code null}, modified)
     */
    @Override
    public void getRotation(Quat out) {
        long stateVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getRotation(stateVa, storeFloats);
        out.set(storeFloats);
    }

    /**
     * Copy the translation offset. The state is unaffected. (native attribute:
     * mTranslation)
     *
     * @return a new offset vector
     */
    @Override
    public Vec3 getTranslation() {
        Vec3 result = new Vec3();
        getTranslation(result);
        return result;
    }

    /**
     * Copy the translation offset. The state is unaffected. (native attribute:
     * mTranslation)
     *
     * @param out storage for the offset (not {@code null}, modified)
     */
    @Override
    public void getTranslation(Vec3 out) {
        long stateVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getTranslation(stateVa, storeFloats);
        out.set(storeFloats);
    }
    // *************************************************************************
    // native private methods

    native private static void assign(long targetVa, long sourceVa);

    native private static void getRotation(
            long stateVa, FloatBuffer storeFloats);

    native private static void getTranslation(
            long stateVa, FloatBuffer storeFloats);

    native private static void setRotation(
            long stateVa, float qx, float qy, float qz, float qw);

    native private static void setTranslation(
            long stateVa, float x, float y, float z);
}
