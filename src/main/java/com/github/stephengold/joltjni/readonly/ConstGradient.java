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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.StreamOut;

/**
 * Read-only access to a {@code Gradient}. (native type: HairSettings::Gradient)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstGradient extends ConstJoltPhysicsObject {
    /**
     * Return the parameter value when the fraction is {@code maxFraction}. The
     * gradient is unaffected.
     *
     * @return the parameter value
     */
    float getMax();

    /**
     * Return the fraction at which the parameter value is {@code max}. The
     * gradient is unaffected.
     *
     * @return the fraction
     */
    float getMaxFraction();

    /**
     * Return the parameter value when the fraction is {@code minFraction}. The
     * gradient is unaffected.
     *
     * @return the parameter value
     */
    float getMin();

    /**
     * Return the fraction at which the parameter value is {@code min}. The
     * gradient is unaffected.
     *
     * @return the fraction
     */
    float getMinFraction();

    /**
     * Write the state of this gradient to the specified stream. The gradient is
     * unaffected.
     *
     * @param stream where to write objects (not {@code null})
     */
    void saveBinaryState(StreamOut stream);
}
