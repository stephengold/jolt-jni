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

import com.github.stephengold.joltjni.PhysicsSystem;
import com.github.stephengold.joltjni.Ragdoll;
import com.github.stephengold.joltjni.StreamOut;
import com.github.stephengold.joltjni.std.IntPair;

/**
 * Read-only access to a {@code RagdollSettings} object. (native type: const
 * RagdollSettings)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstRagdollSettings extends ConstJoltPhysicsObject {
    /**
     * Generate a ragdoll instance from the settings, which are unaffected.
     *
     * @param groupId the collision group for the bodies
     * @param userData the desired user-data value
     * @param physicsSystem where to add the bodies and constraints (not
     * {@code null}, modified)
     * @return a new ragdoll instance, or {@code null} when out of bodies
     */
    Ragdoll createRagdoll(
            int groupId, long userData, PhysicsSystem physicsSystem);

    /**
     * Access the body-index pair for the specified constraint.
     *
     * @param constraintIndex the index of the constraint (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    IntPair getBodyIndicesForConstraintIndex(int constraintIndex);

    /**
     * Return the index of the constraint upstream from the specified body. The
     * settings are unaffected.
     *
     * @param bodyIndex index of the body in the ragdoll (&ge;0)
     * @return the constraint index
     */
    int getConstraintIndexForBodyIndex(int bodyIndex);

    /**
     * Count the parts. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    int getNumParts();

    /**
     * Access the part for the specified index.
     *
     * @param index index of the part (&ge;0)
     * @return a new JVM object with pre-existing native object assigned
     */
    ConstPart getPart(int index);

    /**
     * Access the skeleton.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstSkeleton getSkeleton();

    /**
     * Save the settings to the specified binary stream. The settings are
     * unaffected.
     *
     * @param stream the stream to write to (not {@code null})
     * @param saveShapes if true, save the shapes
     * @param saveGroupFilter if true, save the group filter
     */
    void saveBinaryState(
            StreamOut stream, boolean saveShapes, boolean saveGroupFilter);
}
