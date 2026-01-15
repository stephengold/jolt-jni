/*
Copyright (c) 2026 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.ConstGradient;

/**
 * Parameterize the variation (along a hair strand) of some value, such as
 * compliance or friction. (native type: HairSettings::Gradient)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Gradient extends JoltPhysicsObject implements ConstGradient {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default gradient.
     */
    public Gradient() {
        long gradientVa = createDefault();
        Runnable freeingAction = () -> free(gradientVa);
        setVirtualAddress(gradientVa, freeingAction);
    }

    /**
     * Instantiate a copy of the specified gradient.
     *
     * @param original the gradient to copy (not {@code null}, unaffected)
     */
    public Gradient(ConstGradient original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        Runnable freeingAction = () -> free(copyVa);
        setVirtualAddress(copyVa, freeingAction);
    }

    /**
     * Instantiate a gradient between the specified parameter values.
     *
     * @param min the parameter value when the fraction is zero (default=0)
     * @param max the parameter value when the fraction is one (default=1)
     */
    public Gradient(float min, float max) {
        this(min, max, 0f);
    }

    /**
     * Instantiate a gradient between the specified parameter values.
     *
     * @param min the parameter value when the fraction is {@code minFraction}
     * (default=0)
     * @param max the parameter value when the fraction is one (default=1)
     * @param minFraction the fraction at which the parameter value is
     * {@code min} (default=0)
     */
    public Gradient(float min, float max, float minFraction) {
        this(min, max, minFraction, 1f);
    }

    /**
     * Instantiate a gradient between the specified parameter values.
     *
     * @param min the parameter value when the fraction is {@code minFraction}
     * (default=0)
     * @param max the parameter value when the fraction is {@code maxFraction}
     * (default=1)
     * @param minFraction the fraction at which the value is {@code min}
     * (default=0)
     * @param maxFraction the fraction at which the value is {@code max}
     * (default=1)
     */
    public Gradient(
            float min, float max, float minFraction, float maxFraction) {
        long gradientVa = createGradient(min, max, minFraction, maxFraction);
        Runnable freeingAction = () -> free(gradientVa);
        setVirtualAddress(gradientVa, freeingAction);
    }

    /**
     * Instantiate a gradient with the specified native object assigned.
     *
     * @param gradientVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    Gradient(long gradientVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(gradientVa) : null;
        setVirtualAddress(gradientVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Read the state of this object from the specified stream.
     *
     * @param stream where to read from (not {@code null})
     */
    public void restoreBinaryState(StreamIn stream) {
        long gradientVa = va();
        long streamVa = stream.va();
        restoreBinaryState(gradientVa, streamVa);
    }

    /**
     * Alter the parameter value when the fraction is {@code maxFraction}.
     * (native member: mMax)
     *
     * @param max the desired parameter value (default=1)
     * @return the modified settings, for chaining
     */
    public Gradient setMax(float max) {
        long gradientVa = va();
        setMax(gradientVa, max);

        return this;
    }

    /**
     * Alter the the fraction at which the parameter value reaches {@code max}.
     * (native member: mMaxFraction)
     *
     * @param fraction the desired fraction (default=1)
     * @return the modified settings, for chaining
     */
    public Gradient setMaxFraction(float fraction) {
        long gradientVa = va();
        setMaxFraction(gradientVa, fraction);

        return this;
    }

    /**
     * Alter the parameter value when the fraction is {@code minFraction}.
     * (native member: mMin)
     *
     * @param min the desired parameter value (default=0)
     * @return the modified settings, for chaining
     */
    public Gradient setMin(float min) {
        long gradientVa = va();
        setMin(gradientVa, min);

        return this;
    }

    /**
     * Alter the fraction at which the parameter value is {@code min}. (native
     * member: mMinFraction)
     *
     * @param fraction the desired fraction (default=0)
     * @return the modified settings, for chaining
     */
    public Gradient setMinFraction(float fraction) {
        long gradientVa = va();
        setMinFraction(gradientVa, fraction);

        return this;
    }
    // *************************************************************************
    // ConstGradient methods

    /**
     * Return the parameter value when the fraction is {@code maxFraction}. The
     * gradient is unaffected. (native member: mMax)
     *
     * @return the parameter value
     */
    @Override
    public float getMax() {
        long gradientVa = va();
        float result = getMax(gradientVa);

        return result;
    }

    /**
     * Return the fraction at which the parameter value is {@code max}. The
     * gradient is unaffected. (native member: mMaxFraction)
     *
     * @return the fraction
     */
    @Override
    public float getMaxFraction() {
        long gradientVa = va();
        float result = getMaxFraction(gradientVa);

        return result;
    }

    /**
     * Return the parameter value when the fraction is {@code minFraction}. The
     * gradient is unaffected. (native member: mMin)
     *
     * @return the parameter value
     */
    @Override
    public float getMin() {
        long gradientVa = va();
        float result = getMin(gradientVa);

        return result;
    }

    /**
     * Return the fraction at which the parameter value is {@code min}. The
     * gradient is unaffected. (native member: mMinFraction)
     *
     * @return the fraction
     */
    @Override
    public float getMinFraction() {
        long gradientVa = va();
        float result = getMinFraction(gradientVa);

        return result;
    }

    /**
     * Write the state of this gradient to the specified stream. The gradient is
     * unaffected.
     *
     * @param stream where to write objects (not {@code null})
     */
    @Override
    public void saveBinaryState(StreamOut stream) {
        long gradientVa = va();
        long streamVa = stream.va();
        saveBinaryState(gradientVa, streamVa);
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static long createGradient(
            float min, float max, float minFraction, float maxFraction);

    native private static void free(long gradientVa);

    native private static float getMax(long gradientVa);

    native private static float getMaxFraction(long gradientVa);

    native private static float getMin(long gradientVa);

    native private static float getMinFraction(long gradientVa);

    native private static void restoreBinaryState(
            long gradientVa, long streamVa);

    native private static void saveBinaryState(long gradientVa, long streamVa);

    native private static void setMax(long gradientVa, float max);

    native private static void setMaxFraction(long gradientVa, float fraction);

    native private static void setMin(long gradientVa, float min);

    native private static void setMinFraction(long gradientVa, float fraction);
}
