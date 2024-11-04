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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to a {@code ContactSettings} object. (native type: const
 * ContactSettings)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstContactSettings extends ConstJoltPhysicsObject {
    // *************************************************************************
    // new methods exposed

    /**
     * Return the combined friction for the body pair. The settings are
     * unaffected.
     *
     * @return the combined friction
     */
    float getCombinedFriction();

    /**
     * Return the combined restitution for the body pair. The settings are
     * unaffected.
     *
     * @return the combined restitution
     */
    float getCombinedRestitution();

    /**
     * Return the scale factor for the inverse inertia of body 1. The settings
     * are unaffected.
     *
     * @return the factor (0 = infinite inertia, 1 = use original inertia)
     */
    float getInvInertiaScale1();

    /**
     * Return the scale factor for the inverse inertia of body 2. The settings
     * are unaffected.
     *
     * @return the factor (0 = infinite inertia, 1 = use original inertia)
     */
    float getInvInertiaScale2();

    /**
     * Return the scale factor for the inverse mass of body 1. The settings are
     * unaffected.
     *
     * @return the factor (0 = infinite mass, 1 = use original mass)
     */
    float getInvMassScale1();

    /**
     * Return the scale factor for the inverse mass of body 2. The settings are
     * unaffected.
     *
     * @return the factor (0 = infinite mass, 1 = use original mass)
     */
    float getInvMassScale2();

    /**
     * Test whether the contact should be treated as a sensor (no collision
     * response). The settings are unaffected.
     *
     * @return {@code true} if treated as a sensor, otherwise {@code false}
     */
    boolean getIsSensor();

    /**
     * Return the relative angular velocity (body 2 minus body 1). The settings
     * are unaffected.
     *
     * @return a new velocity vector (radians per second in system coordinates)
     */
    Vec3 getRelativeAngularSurfaceVelocity();

    /**
     * Return the relative linear velocity (body 2 minus body 1). The settings
     * are unaffected.
     *
     * @return a new velocity vector (meters per second in system coordinates)
     */
    Vec3 getRelativeLinearSurfaceVelocity();
}
