/*
Copyright (c) 2024-2025 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.ConstVertex;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * Attributes of a particle, for use during the creation of a soft body. (native
 * type: {@code SoftBodySharedSettings::Vertex})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Vertex extends JoltPhysicsObject implements ConstVertex {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default vertex.
     */
    public Vertex() {
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
    Vertex(JoltPhysicsObject container, long vertexVa) {
        super(container, vertexVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the inverse mass. (native attribute: mInvMass)
     *
     * @param invMass the desired inverse mass (in 1/kilograms, default=1)
     * @return the modified settings, for chaining
     */
    public Vertex setInvMass(float invMass) {
        long vertexVa = va();
        setInvMass(vertexVa, invMass);

        return this;
    }

    /**
     * Alter the initial location of the vertex. (native attribute: mPosition)
     *
     * @param x the desired X coordinate (default=0)
     * @param y the desired Y coordinate (default=0)
     * @param z the desired Z coordinate (default=0)
     * @return the modified settings, for chaining
     */
    public Vertex setPosition(float x, float y, float z) {
        long vertexVa = va();
        setPosition(vertexVa, x, y, z);

        return this;
    }

    /**
     * Alter the initial location of the vertex. (native attribute: mPosition)
     *
     * @param location the desired location (not null, unaffected
     * default=(0,0,0))
     * @return the modified settings, for chaining
     */
    public Vertex setPosition(Float3 location) {
        long vertexVa = va();
        float x = location.x;
        float y = location.y;
        float z = location.z;
        setPosition(vertexVa, x, y, z);

        return this;
    }

    /**
     * Alter the initial location of the vertex. (native attribute: mPosition)
     *
     * @param location the desired location (not null, unaffected
     * default=(0,0,0))
     * @return the modified settings, for chaining
     */
    public Vertex setPosition(Vec3Arg location) {
        long vertexVa = va();
        float x = location.getX();
        float y = location.getY();
        float z = location.getZ();
        setPosition(vertexVa, x, y, z);

        return this;
    }

    /**
     * Alter the initial velocity of the vertex. (native attribute: mVelocity)
     *
     * @param x the desired X component (default=0)
     * @param y the desired Y component (default=0)
     * @param z the desired Z component (default=0)
     * @return the modified settings, for chaining
     */
    public Vertex setVelocity(float x, float y, float z) {
        long vertexVa = va();
        setVelocity(vertexVa, x, y, z);

        return this;
    }

    /**
     * Alter the initial velocity of the vertex. (native attribute: mVelocity)
     *
     * @param velocity the desired velocity (in meters per second, not null,
     * unaffected default=(0,0,0))
     * @return the modified settings, for chaining
     */
    public Vertex setVelocity(Float3 velocity) {
        long vertexVa = va();
        float vx = velocity.x;
        float vy = velocity.y;
        float vz = velocity.z;
        setVelocity(vertexVa, vx, vy, vz);

        return this;
    }

    /**
     * Alter the initial velocity of the vertex. (native attribute: mVelocity)
     *
     * @param velocity the desired velocity (in meters per second, not null,
     * unaffected, default=(0,0,0))
     * @return the modified settings, for chaining
     */
    public Vertex setVelocity(Vec3Arg velocity) {
        long vertexVa = va();
        float x = velocity.getX();
        float y = velocity.getY();
        float z = velocity.getZ();
        setVelocity(vertexVa, x, y, z);

        return this;
    }
    // *************************************************************************
    // ConstVertex methods

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
     * Copy the initial location. The vertex is unaffected. (native attribute:
     * mPosition)
     *
     * @return a new location vector
     */
    @Override
    public Float3 getPosition() {
        long vertexVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getPosition(vertexVa, storeFloats);
        Float3 result = new Float3(storeFloats);

        return result;
    }

    /**
     * Copy the initial velocity. The vertex is unaffected. (native attribute:
     * mVelocity)
     *
     * @return a new velocity vector (in meters per second)
     */
    @Override
    public Float3 getVelocity() {
        long vertexVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getVelocity(vertexVa, storeFloats);
        Float3 result = new Float3(storeFloats);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createDefault();

    native private static void free(long vertexVa);

    native private static float getInvMass(long vertexVa);

    native private static void getPosition(
            long vertexVa, FloatBuffer storeFloats);

    native private static void getVelocity(
            long vertexVa, FloatBuffer storeFloats);

    native private static void setInvMass(long vertexVa, float invMass);

    native private static void setPosition(
            long vertexVa, float x, float y, float z);

    native private static void setVelocity(
            long vertexVa, float vx, float vy, float vz);
}
