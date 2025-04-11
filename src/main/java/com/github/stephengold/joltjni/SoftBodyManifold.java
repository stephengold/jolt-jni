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

import com.github.stephengold.joltjni.readonly.ConstSoftBodyVertex;

/**
 * The vertices of a soft body that are colliding with other bodies.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class SoftBodyManifold extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a manifold with the specified native object assigned but not
     * owned.
     * <p>
     * For use in custom contact listeners.
     *
     * @param manifoldVa the virtual address of the native object to assign (not
     * zero)
     */
    public SoftBodyManifold(long manifoldVa) {
        setVirtualAddress(manifoldVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the ID of the body with which the specified vertex collided.
     *
     * @param vertex the vertex to query (not null, unaffected)
     * @return a new object
     */
    public BodyId getContactBodyId(ConstSoftBodyVertex vertex) {
        long manifoldVa = va();
        long vertexVa = vertex.targetVa();
        long idVa = getContactBodyId(manifoldVa, vertexVa);
        BodyId result = new BodyId(idVa, true);

        return result;
    }

    /**
     * Copy the contact normal direction for the specified vertex. The manifold
     * is unaffected.
     *
     * @param vertex the vertex to query (not null, unaffected)
     * @return a new vector
     */
    public Vec3 getContactNormal(ConstSoftBodyVertex vertex) {
        long manifoldVa = va();
        long vertexVa = vertex.targetVa();
        float nx = getLocalContactNormalX(manifoldVa, vertexVa);
        float ny = getLocalContactNormalY(manifoldVa, vertexVa);
        float nz = getLocalContactNormalZ(manifoldVa, vertexVa);
        Vec3 result = new Vec3(nx, ny, nz);

        return result;
    }

    /**
     * Copy the location of the contact point for the specified vertex. The
     * manifold is unaffected.
     *
     * @param vertex the vertex to query (not null, unaffected)
     * @return a new location vector (in local coordinates)
     */
    public Vec3 getLocalContactPoint(ConstSoftBodyVertex vertex) {
        long manifoldVa = va();
        long vertexVa = vertex.targetVa();
        float x = getLocalContactPointX(manifoldVa, vertexVa);
        float y = getLocalContactPointY(manifoldVa, vertexVa);
        float z = getLocalContactPointZ(manifoldVa, vertexVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Count how many sensors are in contact with the soft body. The manifold is
     * unaffected.
     *
     * @return the count (&ge;0)
     */
    public int getNumSensorContacts() {
        long manifoldVa = va();
        int result = getNumSensorContacts(manifoldVa);

        return result;
    }

    /**
     * Return the ID of the specified sensor contact. The manifold is
     * unaffected. (native method: GetSensorContactBodyID)
     *
     * @param index among the sensor contacts (&ge;0)
     * @return a new object
     */
    public BodyId getSensorContactBodyId(int index) {
        long manifoldVa = va();
        long idVa = getSensorContactBodyId(manifoldVa, index);
        BodyId result = new BodyId(idVa, true);

        return result;
    }

    /**
     * Enumerate all vertices of the soft body. The manifold is unaffected.
     *
     * @return a new array of new JVM objects with pre-existing native objects
     * assigned
     */
    public ConstSoftBodyVertex[] getVertices() {
        long manifoldVa = va();
        int numVertices = countVertices(manifoldVa);
        ConstSoftBodyVertex[] result = new SoftBodyVertex[numVertices];

        JoltPhysicsObject container = ownsNativeObject() ? this : null;
        for (int i = 0; i < numVertices; ++i) {
            long vertexVa = getVertex(manifoldVa, i);
            result[i] = new SoftBodyVertex(container, vertexVa);
        }

        return result;
    }

    /**
     * Test whether the specified vertex collided with something in this update.
     * The manifold is unaffected.
     *
     * @param vertex the vertex to query (not null, unaffected)
     * @return {@code true} if it collided, otherwise {@code false}
     */
    public boolean hasContact(ConstSoftBodyVertex vertex) {
        long manifoldVa = va();
        long vertexVa = vertex.targetVa();
        boolean result = hasContact(manifoldVa, vertexVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static int countVertices(long manifoldVa);

    native private static long getContactBodyId(long manifoldVa, long vertexVa);

    native private static float getLocalContactNormalX(
            long manifoldVa, long vertexVa);

    native private static float getLocalContactNormalY(
            long manifoldVa, long vertexVa);

    native private static float getLocalContactNormalZ(
            long manifoldVa, long vertexVa);

    native private static float getLocalContactPointX(
            long manifoldVa, long vertexVa);

    native private static float getLocalContactPointY(
            long manifoldVa, long vertexVa);

    native private static float getLocalContactPointZ(
            long manifoldVa, long vertexVa);

    native private static int getNumSensorContacts(long manifoldVa);

    native private static long getSensorContactBodyId(
            long manifoldVa, int index);

    native private static long getVertex(long manifoldVa, int index);

    native private static boolean hasContact(long manifoldVa, long vertexVa);
}
