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

import com.github.stephengold.joltjni.ChbEdge;
import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to a {@code ChbFace}. (native type:
 * {@code const ConvexHullBuilder::Face})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstChbFace extends ConstJoltPhysicsObject {
    /**
     * Copy the centroid of the face. The face is unaffected.
     *
     * @return a new location vector
     */
    Vec3 getCentroid();

    /**
     * Access the first edge of the face. The face is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if none
     */
    ChbEdge getFirstEdge();

    /**
     * Copy the normal of the face. The face is unaffected.
     *
     * @return a new vector whose length is 2 times the area of the face
     */
    Vec3 getNormal();

    /**
     * Test whether the face is facing the specified point.
     *
     * @param point the location to test (not null, unaffected)
     * @return {@code true} if facing the test point, otherwise {@code false}
     */
    boolean isFacing(Vec3Arg point);
}
