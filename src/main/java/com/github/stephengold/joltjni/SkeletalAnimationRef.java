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

import com.github.stephengold.joltjni.template.Ref;

/**
 * A counted reference to a {@code SkeletalAnimation}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class SkeletalAnimationRef extends Ref {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public SkeletalAnimationRef() {
        long refVa = createDefault();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a counted reference to the specified animation.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param animation the animation to target (not {@code null})
     */
    SkeletalAnimationRef(long refVa, SkeletalAnimation animation) {
        assert animation != null;

        this.ptr = animation;
        Runnable freeingAction = () -> free(refVa);
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the duration of the animation. The animation is unaffected.
     *
     * @return the duration (in seconds)
     */
    public float getDuration() {
        long animationVa = targetVa();
        float result = SkeletalAnimation.getDuration(animationVa);

        return result;
    }

    /**
     * Interpolate the joint transforms for the specified animation time. The
     * animation is unaffected.
     *
     * @param time the animation time to use (in seconds)
     * @param storePose storage for the interpolated pose (not null, modified)
     */
    public void sample(float time, SkeletonPose storePose) {
        long animationVa = targetVa();
        long poseVa = storePose.va();
        SkeletalAnimation.sample(animationVa, time, poseVa);
    }
    // *************************************************************************
    // new protected methods

    /**
     * Update the cached target.
     */
    void updatePtr() {
        long refVa = va();
        long targetVa = getPtr(refVa);
        if (targetVa == 0L) {
            this.ptr = null;
        } else {
            this.ptr = new SkeletalAnimation(targetVa);
        }
    }
    // *************************************************************************
    // Ref methods

    /**
     * Access the targeted animation, if any.
     *
     * @return the pre-existing object, or {@code null} if the reference is
     * empty
     */
    @Override
    public SkeletalAnimation getPtr() {
        SkeletalAnimation result = (SkeletalAnimation) ptr;
        return result;
    }

    /**
     * Return the address of the native {@code SkeletalAnimation}. No objects
     * are affected.
     *
     * @return the virtual address, or zero if the reference is empty
     */
    @Override
    public long targetVa() {
        long refVa = va();
        long result = getPtr(refVa);
        assert result == (ptr == null ? 0L : getPtr().va());

        return result;
    }

    /**
     * Create an additional counted reference to the targeted animation.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public SkeletalAnimationRef toRef() {
        SkeletalAnimationRef result;
        if (ptr == null) {
            result = new SkeletalAnimationRef();
        } else {
            long refVa = va();
            long copyVa = copy(refVa);
            SkeletalAnimation animation = (SkeletalAnimation) ptr;
            result = new SkeletalAnimationRef(copyVa, animation);
        }

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long copy(long refVa);

    native private static long createDefault();

    native static void free(long refVa);

    native private static long getPtr(long refVa);
}
