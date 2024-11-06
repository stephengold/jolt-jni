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
        long refVa = createEmpty();
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
    SkeletalAnimationRef(long refVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(refVa) : null;
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
        long refVa = va();
        long animationVa = getPtr(refVa);
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
        long refVa = va();
        long animationVa = getPtr(refVa);
        long poseVa = storePose.va();
        SkeletalAnimation.sample(animationVa, time, poseVa);
    }
    // *************************************************************************
    // Ref methods

    /**
     * Temporarily access the referenced {@code SkeletalAnimation}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public SkeletalAnimation getPtr() {
        long refVa = va();
        long animationVa = getPtr(refVa);
        SkeletalAnimation result = new SkeletalAnimation(animationVa);

        return result;
    }

    /**
     * Create a counted reference to the native {@code SkeletalAnimationRef}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public SkeletalAnimationRef toRef() {
        long refVa = va();
        long copyVa = copy(refVa);
        SkeletalAnimationRef result = new SkeletalAnimationRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long copy(long refVa);

    native private static long createEmpty();

    native private static void free(long refVa);

    native private static long getPtr(long refVa);
}
