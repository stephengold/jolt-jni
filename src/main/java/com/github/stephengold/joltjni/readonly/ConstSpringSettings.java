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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.StreamOut;
import com.github.stephengold.joltjni.enumerate.ESpringMode;

/**
 * Read-only access to a {@code SpringSettings} object. (native type: const
 * SpringSettings)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstSpringSettings extends ConstJoltPhysicsObject {
    /**
     * Access the underlying {@code Constraint}, if any. The settings are
     * unaffected.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    ConstConstraint getConstraint();

    /**
     * Access the underlying {@code ConstraintSettings}, if any. The settings
     * are unaffected.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    ConstConstraintSettings getConstraintSettings();

    /**
     * Return the spring's damping. The settings are unaffected.
     * <p>
     * When the mode is FrequencyAndDamping, this is the damping ratio (0 = no
     * damping, 1 = critical damping). When the mode is StiffnessAndDamping,
     * this is the damping coefficient {@code c} in the spring equation:
     * {@code F = -k * x - c * v} for a linear spring or
     * {@code T = -k * theta - c * w} for an angular spring.
     *
     * @return the damping value
     */
    float getDamping();

    /**
     * Return the spring's frequency. The settings are unaffected.
     * <p>
     * Effective only when the mode is FrequencyAndDamping. If positive, the
     * constraint will have soft limits, and mFrequency specifies the
     * oscillation frequency in Hz. If negative, the constraint will have hard
     * limits.
     *
     * @return the frequency value
     */
    float getFrequency();

    /**
     * Return how the spring is specified. The settings are unaffected.
     *
     * @return an enum value (not null)
     */
    ESpringMode getMode();

    /**
     * Return the spring's stiffness. The settings are unaffected.
     * <p>
     * Effective only when the mode is StiffnessAndDamping. If positive, the
     * constraint will have soft limits, and mStiffness specifies the stiffness
     * {@code k} in the spring equation: {@code F = -k * x - c * v} for a linear
     * spring or {@code T = -k *
     * theta - c * w} for an angular spring.
     * <p>
     * If negative, the constraint will have hard limits.
     *
     * @return the stiffness value
     */
    float getStiffness();

    /**
     * Test for valid frequency/stiffness. The settings are unaffected.
     *
     * @return {@code true} if valid (the constraint will have soft limits),
     * otherwise {@code false} (hard limits)
     */
    boolean hasStiffness();

    /**
     * Save the settings to the specified binary stream. The settings are
     * unaffected.
     *
     * @param stream the stream to write to (not null)
     */
    void saveBinaryState(StreamOut stream);
}
