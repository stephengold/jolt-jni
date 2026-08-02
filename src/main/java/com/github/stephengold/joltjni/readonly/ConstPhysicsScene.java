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
 * Read-only access to a {@code PhysicsScene}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstPhysicsScene extends ConstJoltPhysicsObject {
    /**
     * Access the body-creation settings as a Java array. The scene is
     * unaffected.
     *
     * @return a new array of new JVM objects with the pre-existing native
     * objects assigned
     */
    ConstBodyCreationSettings[] getBodies();

    /**
     * Access the constraints as a Java array. The scene is unaffected.
     *
     * @return a new array of new JVM objects with the pre-existing native
     * objects assigned
     */
    ConstConnectedConstraint[] getConstraints();

    /**
     * Count the bodies in the scene. The scene is unaffected.
     *
     * @return the count (&ge;0)
     */
    int getNumBodies();

    /**
     * Count the constraints in the scene. The scene is unaffected.
     *
     * @return the count (&ge;0)
     */
    int getNumConstraints();

    /**
     * Count the soft bodies in the scene. The scene is unaffected.
     *
     * @return the count (&ge;0)
     */
    int getNumSoftBodies();

    /**
     * Access the soft-body creation settings as a Java array. The scene is
     * unaffected.
     *
     * @return a new array of new JVM objects with pre-existing native objects
     * assigned
     */
    ConstSoftBodyCreationSettings[] getSoftBodies();

    /**
     * Save the scene to the specified binary stream. The scene is unaffected.
     *
     * @param stream the stream to write to (not {@code null})
     * @param saveShapes if true, save the shapes
     * @param saveGroupFilter if true, save the group filter
     */
    void saveBinaryState(
            StreamOut stream, boolean saveShapes, boolean saveGroupFilter);
}
