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

import com.github.stephengold.joltjni.readonly.ConstPlane;
import com.github.stephengold.joltjni.readonly.ConstSoftBodyVertex;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * Run-time information for a single particle in a soft body. (native type:
 * {@code SoftBodyMotionProperties::Vertex})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SoftBodyVertex
        extends JoltPhysicsObject
        implements ConstSoftBodyVertex {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default vertex.
     */
    public SoftBodyVertex() {
        long vertexVa = createDefault();
        setVirtualAddress(vertexVa, () -> free(vertexVa));
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param vertexVa the virtual address of the native object to assign (not
     * zero)
     */
    SoftBodyVertex(JoltPhysicsObject container, long vertexVa) {
        super(container, vertexVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Reset the collision data.
     */
    public void resetCollision() {
        long vertexVa = va();
        resetCollision(vertexVa);
    }

    /**
     * Alter the index of the colliding shape. (native attribute:
     * mCollidingShapeIndex)
     *
     * @param index the desired index
     */
    public void setCollidingShapeIndex(int index) {
        long vertexVa = va();
        setCollidingShapeIndex(vertexVa, index);
    }

    /**
     * Alter the collision plane. (native attribute: mCollisionPlane)
     *
     * @param plane the desired collision plane (not null, unaffected)
     */
    public void setCollisionPlane(ConstPlane plane) {
        long vertexVa = va();
        float nx = plane.getNormalX();
        float ny = plane.getNormalY();
        float nz = plane.getNormalZ();
        float c = plane.getConstant();
        setCollisionPlane(vertexVa, nx, ny, nz, c);
    }

    /**
     * Alter the inverse mass. (native attribute: mInvMass)
     *
     * @param invMass the desired inverse mass (in 1/kilograms)
     */
    public void setInvMass(float invMass) {
        long vertexVa = va();
        setInvMass(vertexVa, invMass);
    }

    /**
     * Alter the amount of penetration. (native attribute: mLargestPenetration)
     *
     * @param penetration the desired amount (in meters)
     */
    public void setLargestPenetration(float penetration) {
        long vertexVa = va();
        setLargestPenetration(vertexVa, penetration);
    }

    /**
     * Alter the location of the vertex. (native attribute: mPosition)
     *
     * @param location the desired location (not null, unaffected)
     */
    public void setPosition(Vec3Arg location) {
        long vertexVa = va();
        float x = location.getX();
        float y = location.getY();
        float z = location.getZ();
        setPosition(vertexVa, x, y, z);
    }

    /**
     * Alter the velocity of the vertex. (native attribute: mVelocity)
     *
     * @param velocity the desired velocity (in meters per second, not null,
     * unaffected)
     */
    public void setVelocity(Vec3Arg velocity) {
        long vertexVa = va();
        float vx = velocity.getX();
        float vy = velocity.getY();
        float vz = velocity.getZ();
        setVelocity(vertexVa, vx, vy, vz);
    }
    // *************************************************************************
    // ConstSoftBodyVertex methods

    /**
     * Return the index of the colliding shape. The vertex is unaffected.
     * (native attribute: mCollidingShapeIndex)
     *
     * @return the index
     */
    @Override
    public int getCollidingShapeIndex() {
        long vertexVa = va();
        int result = getCollidingShapeIndex(vertexVa);

        return result;
    }

    /**
     * Copy the collision plane. The vertex is unaffected. (native attribute:
     * mCollisionPlane)
     *
     * @return a new object
     */
    @Override
    public Plane getCollisionPlane() {
        long vertexVa = va();
        FloatBuffer storeFloats = Jolt.newDirectFloatBuffer(4);
        getCollisionPlane(vertexVa, storeFloats);
        Plane result = new Plane(storeFloats);

        return result;
    }

    /**
     * Return the inverse mass. The vertex is unaffected. (native attribute:
     * mInvMass)
     *
     * @return the inverse of the mass (in 1/kilograms)
     */
    @Override
    public float getInvMass() {
        long vertexVa = va();
        float result = getInvMass(vertexVa);

        return result;
    }

    /**
     * Return the amount of penetration. (native attribute: mLargestPenetration)
     *
     * @return the depth (in meters)
     */
    @Override
    public float getLargestPenetration() {
        long vertexVa = va();
        float result = getLargestPenetration(vertexVa);

        return result;
    }

    /**
     * Copy the location. The vertex is unaffected. (native attribute:
     * mPosition)
     *
     * @return a new location vector (relative to the body's center of mass)
     */
    @Override
    public Vec3 getPosition() {
        long vertexVa = va();
        float x = getPositionX(vertexVa);
        float y = getPositionY(vertexVa);
        float z = getPositionZ(vertexVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Copy the velocity. The vertex is unaffected. (native attribute:
     * mVelocity)
     *
     * @return a new velocity vector (relative to the body's center of mass, in
     * meters per second)
     */
    @Override
    public Vec3 getVelocity() {
        long vertexVa = va();
        float x = getVelocityX(vertexVa);
        float y = getVelocityY(vertexVa);
        float z = getVelocityZ(vertexVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Test whether the vertex collided during the previous update. The vertex
     * is unaffected. (native attribute: mHasContact)
     *
     * @return {@code true} if it collided, otherwise {@code false}
     */
    @Override
    public boolean hasContact() {
        long vertexVa = va();
        boolean result = hasContact(vertexVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createDefault();

    native private static void free(long vertexVa);

    native private static int getCollidingShapeIndex(long vertexVa);

    native private static void getCollisionPlane(
            long vertexVa, FloatBuffer storeFloats);

    native private static float getInvMass(long vertexVa);

    native private static float getLargestPenetration(long vertexVa);

    native private static float getPositionX(long vertexVa);

    native private static float getPositionY(long vertexVa);

    native private static float getPositionZ(long vertexVa);

    native private static float getVelocityX(long vertexVa);

    native private static float getVelocityY(long vertexVa);

    native private static float getVelocityZ(long vertexVa);

    native private static boolean hasContact(long vertexVa);

    native private static void resetCollision(long vertexVa);

    native private static void setCollidingShapeIndex(long vertexVa, int index);

    native private static void setCollisionPlane(
            long vertexVa, float nx, float ny, float nz, float c);

    native private static void setInvMass(long vertexVa, float invMass);

    native private static void setLargestPenetration(
            long vertexVa, float penetration);

    native private static void setPosition(
            long vertexVa, float x, float y, float z);

    native private static void setVelocity(
            long vertexVa, float vx, float vy, float vz);
}
