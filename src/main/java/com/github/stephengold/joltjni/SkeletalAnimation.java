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
 * A skeletal animation for skinning.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SkeletalAnimation extends JoltPhysicsObject implements RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param animationVa the virtual address of the native object to assign
     * (not zero)
     */
    SkeletalAnimation(long animationVa) {
        super(animationVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the duration of the animation. The animation is unaffected.
     *
     * @return the duration (in seconds)
     */
    public float getDuration() {
        long animationVa = va();
        float result = getDuration(animationVa);

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
        long animationVa = va();
        long poseVa = storePose.va();
        sample(animationVa, time, poseVa);
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code SkeletalAnimation}. The
     * animation is unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long animationVa = va();
        int result = getRefCount(animationVa);

        return result;
    }

    /**
     * Create a counted reference to the animation.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public SkeletalAnimationRef toRef() {
        long animationVa = va();
        long refVa = toRef(animationVa);
        SkeletalAnimationRef result = new SkeletalAnimationRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native static float getDuration(long animationVa);

    native private static int getRefCount(long animationVa);

    native static void sample(long animationVa, float time, long poseVa);

    native private static long toRef(long animationVa);
}
