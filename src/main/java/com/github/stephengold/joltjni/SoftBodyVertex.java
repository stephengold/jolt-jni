/*
Copyright (c) 2025-2026 Stephen Gold

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
     * Instantiate a copy of the specified vertex.
     *
     * @param original the vertex to copy (not {@code null}, unaffected)
     */
    public SoftBodyVertex(ConstSoftBodyVertex original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa, () -> free(copyVa));
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
     * Copy the argument to the current settings.
     *
     * @param source the settings to copy (not {@code null}, unaffected)
     */
    public void set(ConstSoftBodyVertex source) {
        long targetVa = va();
        long sourceVa = source.targetVa();
        assign(targetVa, sourceVa);
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
     * @param plane the desired collision plane (not {@code null}, unaffected)
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
     * Alter the "has contact" flag. (native attribute: mHasContact)
     *
     * @param setting the desired setting
     */
    public void setHasContact(boolean setting) {
        long vertexVa = va();
        setHasContact(vertexVa, setting);
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
     * Relocate the vertex. (native attribute: mPosition)
     *
     * @param location the desired location (not {@code null}, unaffected)
     */
    public void setPosition(Vec3Arg location) {
        setPosition(location.getX(), location.getY(), location.getZ());
    }

    /**
     * Relocate the vertex. (native attribute: mPosition)
     *
     * @param x the desired X coordinate (relative to the body's center of
     * mass)
     * @param y the desired Y coordinate (relative to the body's center of
     * mass)
     * @param z the desired Z coordinate (relative to the body's center of
     * mass)
     */
    public void setPosition(float x, float y, float z) {
        long vertexVa = va();
        setPosition(vertexVa, x, y, z);
    }

    /**
     * Alter the previous location. (native attribute: mPreviousPosition)
     *
     * @param location the desired previous location (not {@code null},
     * unaffected)
     */
    public void setPreviousPosition(Vec3Arg location) {
        setPreviousPosition(location.getX(), location.getY(), location.getZ());
    }

    /**
     * Alter the previous location. (native attribute: mPreviousPosition)
     *
     * @param x the desired X coordinate (relative to the body's center of
     * mass)
     * @param y the desired Y coordinate (relative to the body's center of
     * mass)
     * @param z the desired Z coordinate (relative to the body's center of
     * mass)
     */
    public void setPreviousPosition(float x, float y, float z) {
        long vertexVa = va();
        setPreviousPosition(vertexVa, x, y, z);
    }

    /**
     * Alter the velocity of the vertex. (native attribute: mVelocity)
     *
     * @param velocity the desired velocity (in meters per second, not
     * {@code null}, unaffected)
     */
    public void setVelocity(Vec3Arg velocity) {
        setVelocity(velocity.getX(), velocity.getY(), velocity.getZ());
    }

    /**
     * Alter the velocity of the vertex. (native attribute: mVelocity)
     *
     * @param vx the desired X component of the velocity (meters per second)
     * @param vy the desired Y component of the velocity (meters per second)
     * @param vz the desired Z component of the velocity (meters per second)
     */
    public void setVelocity(float vx, float vy, float vz) {
        long vertexVa = va();
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
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
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
     * Return the amount of penetration. The vertex is unaffected. (native
     * attribute: mLargestPenetration)
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
        Vec3 result = new Vec3();
        getPosition(result);
        return result;
    }

    /**
     * Copy the location. The vertex is unaffected. (native attribute:
     * mPosition)
     *
     * @param out storage for the location (relative to the body's center of
     * mass, not {@code null}, modified)
     */
    @Override
    public void getPosition(Vec3 out) {
        long vertexVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getPosition(vertexVa, storeFloats);
        out.set(storeFloats);
    }

    /**
     * Copy the previous location. The vertex is unaffected. (native attribute:
     * mPreviousPosition)
     *
     * @return a new location vector (relative to the body's center of mass)
     */
    @Override
    public Vec3 getPreviousPosition() {
        Vec3 result = new Vec3();
        getPreviousPosition(result);
        return result;
    }

    /**
     * Copy the previous location. The vertex is unaffected. (native attribute:
     * mPreviousPosition)
     *
     * @param out storage for the location (relative to the body's center of
     * mass, not {@code null}, modified)
     */
    @Override
    public void getPreviousPosition(Vec3 out) {
        long vertexVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getPreviousPosition(vertexVa, storeFloats);
        out.set(storeFloats);
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
        Vec3 result = new Vec3();
        getVelocity(result);
        return result;
    }

    /**
     * Copy the velocity. The vertex is unaffected. (native attribute:
     * mVelocity)
     *
     * @param out storage for the velocity (relative to the body's center of
     * mass, in meters per second, not {@code null}, modified)
     */
    @Override
    public void getVelocity(Vec3 out) {
        long vertexVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getVelocity(vertexVa, storeFloats);
        out.set(storeFloats);
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
        boolean result = getHasContact(vertexVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void assign(long targetVa, long sourceVa);

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static void free(long vertexVa);

    native private static int getCollidingShapeIndex(long vertexVa);

    native private static void getCollisionPlane(
            long vertexVa, FloatBuffer storeFloats);

    native private static boolean getHasContact(long vertexVa);

    native private static float getInvMass(long vertexVa);

    native private static float getLargestPenetration(long vertexVa);

    native private static void getPosition(
            long vertexVa, FloatBuffer storeFloats);

    native private static void getPreviousPosition(
            long vertexVa, FloatBuffer storeFloats);

    native private static void getVelocity(
            long vertexVa, FloatBuffer storeFloats);

    native private static void resetCollision(long vertexVa);

    native private static void setCollidingShapeIndex(long vertexVa, int index);

    native private static void setCollisionPlane(
            long vertexVa, float nx, float ny, float nz, float c);

    native private static void setHasContact(long vertexVa, boolean setting);

    native private static void setInvMass(long vertexVa, float invMass);

    native private static void setLargestPenetration(
            long vertexVa, float penetration);

    native private static void setPosition(
            long vertexVa, float x, float y, float z);

    native private static void setPreviousPosition(
            long vertexVa, float x, float y, float z);

    native private static void setVelocity(
            long vertexVa, float vx, float vy, float vz);
}
