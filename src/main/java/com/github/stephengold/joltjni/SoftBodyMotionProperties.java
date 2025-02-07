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
package com.github.stephengold.joltjni;

/**
 * The motion properties of a soft body.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SoftBodyMotionProperties extends MotionProperties {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param propertiesVa the virtual address of the native object to assign
     * (not zero)
     */
    SoftBodyMotionProperties(JoltPhysicsObject container, long propertiesVa) {
        super(container, propertiesVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Update the specified soft body without updating the rest of the physics
     * system.
     *
     * @param deltaTime the total time to advance (in seconds)
     * @param softBody the body to update (not null)
     * @param system the system that contains the body (not null)
     */
    public void customUpdate(
            float deltaTime, Body softBody, PhysicsSystem system) {
        long propertiesVa = va();
        long bodyVa = softBody.va();
        long systemVa = system.va();
        customUpdate(propertiesVa, deltaTime, bodyVa, systemVa);
    }

    /**
     * Access the specified face.
     *
     * @param index the index of the face (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public Face getFace(int index) {
        long propertiesVa = va();
        long faceVa = getFace(propertiesVa, index);
        Face result = new Face(this, faceVa);

        return result;
    }

    /**
     * Enumerate all faces in the soft body.
     *
     * @return a new array of new JVM objects
     */
    public Face[] getFaces() {
        long propertiesVa = va();
        int numFaces = countFaces(propertiesVa);
        Face[] result = new Face[numFaces];
        for (int i = 0; i < numFaces; ++i) {
            long faceVa = getFace(propertiesVa, i);
            result[i] = new Face(this, faceVa);
        }

        return result;
    }

    /**
     * Return the number of solver iterations. The properties are unaffected.
     *
     * @return the number of iterations (&ge;0)
     */
    public int getNumIterations() {
        long propertiesVa = va();
        int result = getNumIterations(propertiesVa);

        return result;
    }

    /**
     * Access the shared settings.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public SoftBodySharedSettings getSettings() {
        long propertiesVa = va();
        long settingsVa = getSettings(propertiesVa);
        SoftBodySharedSettings result = new SoftBodySharedSettings(settingsVa);

        return result;
    }

    /**
     * Access the specified vertex.
     *
     * @param index the index of the vertex (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public SoftBodyVertex getVertex(int index) {
        long propertiesVa = va();
        long vertexVa = getVertex(propertiesVa, index);
        SoftBodyVertex result = new SoftBodyVertex(this, vertexVa);

        return result;
    }

    /**
     * Enumerate all vertices in the soft body.
     *
     * @return a new array of new JVM objects
     */
    public SoftBodyVertex[] getVertices() {
        long propertiesVa = va();
        int numVertices = countVertices(propertiesVa);
        SoftBodyVertex[] result = new SoftBodyVertex[numVertices];
        for (int i = 0; i < numVertices; ++i) {
            long vertexVa = getVertex(propertiesVa, i);
            result[i] = new SoftBodyVertex(this, vertexVa);
        }

        return result;
    }

    /**
     * Alter the number of solver iterations.
     *
     * @param numIterations the desired number of iterations (default=?)
     */
    public void setNumIterations(int numIterations) {
        long propertiesVa = va();
        setNumIterations(propertiesVa, numIterations);
    }
    // *************************************************************************
    // native private methods

    native private static int countFaces(long propertiesVa);

    native private static int countVertices(long propertiesVa);

    native private static void customUpdate(
            long propertiesVa, float deltaTime, long bodyVa, long systemVa);

    native private static long getFace(long propertiesVa, int index);

    native private static int getNumIterations(long propertiesVa);

    native private static long getSettings(long propertiesVa);

    native private static long getVertex(long propertiesVa, int index);

    native private static void setNumIterations(
            long propertiesVa, int numInterations);
}
