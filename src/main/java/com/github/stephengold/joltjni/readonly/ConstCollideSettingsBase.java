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

import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.EActiveEdgeMode;
import com.github.stephengold.joltjni.enumerate.ECollectFacesMode;

/**
 * Read-only access to a {@code CollideSetingsBase}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstCollideSettingsBase extends ConstJoltPhysicsObject {
    /**
     * Return how edge collisions should be handled. The settings are
     * unaffected.
     *
     * @return an enum value (not {@code null})
     */
    EActiveEdgeMode getActiveEdgeMode();

    /**
     * Copy the movement direction for inactive edges. The settings are
     * unaffected.
     *
     * @return a new direction vector
     */
    Vec3 getActiveEdgeMovementDirection();

    /**
     * Return how face information should be handled. The settings are
     * unaffected.
     *
     * @return an enum value (not {@code null})
     */
    ECollectFacesMode getCollectFacesMode();

    /**
     * Return the collision tolerance for the GJK algorithm. The settings are
     * unaffected.
     *
     * @return an enum value (not {@code null})
     */
    float getCollisionTolerance();

    /**
     * Return the termination tolerance for calculating penetration depth. The
     * settings are unaffected.
     *
     * @return the tolerance
     */
    float getPenetrationTolerance();
}
