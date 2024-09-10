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

import com.github.stephengold.joltjni.Plane;
import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to a {@code CharacterBaseSettings} object. (native type:
 * const CharacterBaseSettings)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstCharacterBaseSettings extends ConstJoltPhysicsObject {
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether to make an extra effort to remove contacts with internal
     * edges. The settings are unaffected. (native field:
     * mEnhancedInternalEdgeRemoval)
     *
     * @return true to remove ghost contacts, otherwise false
     */
    boolean getEnhancedInternalEdgeRemoval();

    /**
     * Return the maximum slope on which the character can walk. The settings
     * are unaffected. (native field: mMaxSlopeAngle)
     *
     * @return the angle (in radians)
     */
    float getMaxSlopeAngle();

    /**
     * Acquire read-only access to the {@code Shape}. The settings are
     * unaffected. (native field: mShape)
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null}
     */
    ConstShape getShape();

    /**
     * Return the supporting volume. The settings are unaffected. (native field:
     * mSupportingVolume)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    Plane getSupportingVolume();

    /**
     * Return the character's "up" direction. The settings are unaffected.
     * (native field: mUp)
     *
     * @return a new direction vector (in system coordinates)
     */
    Vec3 getUp();
}
