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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.StreamOut;
import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to {@code WheelSettings}. (native type: const WheelSettings)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstWheelSettings extends ConstJoltPhysicsObject {
    /**
     * Determine where to apply tire forces. The settings are unaffected.
     *
     * @return {@code true} if applied at the configured suspension-force point,
     * {@code false} if applied at the wheel's point of contact
     */
    boolean getEnableSuspensionForcePoint();

    /**
     * Copy the location of the attachment point. The settings are unaffected.
     *
     * @return a new location vector (in the body's local system)
     */
    Vec3 getPosition();

    /**
     * Return the radius of the wheel. The settings are unaffected.
     *
     * @return the radius (in meters)
     */
    float getRadius();

    /**
     * Copy the steering axis (upward direction). The settings are unaffected.
     *
     * @return a new direction vector
     */
    Vec3 getSteeringAxis();

    /**
     * Copy the downward direction of the suspension. The settings are
     * unaffected.
     *
     * @return a new direction vector
     */
    Vec3 getSuspensionDirection();

    /**
     * Copy the location where (if enabled) tire forces will be applied. The
     * settings are unaffected.
     *
     * @return a new location vector (in body coordinates)
     */
    Vec3 getSuspensionForcePoint();

    /**
     * Return the maximum displacement from the attachment point. The settings
     * are unaffected.
     *
     * @return the distance (in meters)
     */
    float getSuspensionMaxLength();

    /**
     * Return the minimum displacement from the attachment point. The settings
     * are unaffected.
     *
     * @return the distance (in meters)
     */
    float getSuspensionMinLength();

    /**
     * Return the suspension preload length. The settings are unaffected.
     *
     * @return the offset (in meters)
     */
    float getSuspensionPreloadLength();

    /**
     * Access the settings for the suspension spring. The settings are
     * unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstSpringSettings getSuspensionSpring();

    /**
     * Copy the forward direction when steering is neutral. The settings are
     * unaffected.
     *
     * @return a new direction vector
     */
    Vec3 getWheelForward();

    /**
     * Copy the "up" direction when steering is neutral. The settings are
     * unaffected.
     *
     * @return a new direction vector
     */
    Vec3 getWheelUp();

    /**
     * Return the width of the wheel. The settings are unaffected.
     *
     * @return the width (in meters)
     */
    float getWidth();

    /**
     * Save the settings to the specified binary stream. The settings are
     * unaffected.
     *
     * @param stream the stream to write to (not null)
     */
    void saveBinaryState(StreamOut stream);
}
