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

import com.github.stephengold.joltjni.readonly.ConstSphere;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * 3-D geometric description of a sphere.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Sphere extends JoltPhysicsObject implements ConstSphere {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a sphere with the specified center and radius.
     *
     * @param center the desired location for the center (not null, unaffected)
     * @param radius the desired radius
     */
    public Sphere(Float3 center, float radius) {
        long sphereVa = create(center.x, center.y, center.z, radius);
        setVirtualAddress(sphereVa, () -> free(sphereVa));
    }

    /**
     * Instantiate a sphere with the specified center and radius.
     *
     * @param center the desired location for the center (not null, unaffected)
     * @param radius the desired radius
     */
    public Sphere(Vec3Arg center, float radius) {
        float x = center.getX();
        float y = center.getY();
        float z = center.getZ();
        long sphereVa = create(x, y, z, radius);
        setVirtualAddress(sphereVa, () -> free(sphereVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Construct the smallest sphere that encapsulates the sphere and the
     * specified point.
     *
     * @param point the point to encapsulate (not null, unaffected)
     */
    public void encapsulatePoint(Vec3Arg point) {
        long sphereVa = va();
        float x = point.getX();
        float y = point.getY();
        float z = point.getZ();
        encapsulatePoint(sphereVa, x, y, z);
    }
    // *************************************************************************
    // ConstSphere methods

    /**
     * Copy the location of the sphere's center.
     *
     * @return a new location vector
     */
    @Override
    public Vec3 getCenter() {
        long sphereVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getCenter(sphereVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Return the radius of the sphere.
     *
     * @return the radius
     */
    @Override
    public float getRadius() {
        long sphereVa = va();
        float result = getRadius(sphereVa);

        return result;
    }

    /**
     * Calculate the support vector for the specified direction.
     *
     * @param direction the direction to use (not null, unaffected)
     * @return a new offset vector
     */
    @Override
    public Vec3 getSupport(Vec3Arg direction) {
        long sphereVa = va();
        float dx = direction.getX();
        float dy = direction.getY();
        float dz = direction.getZ();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getSupport(sphereVa, dx, dy, dz, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long create(
            float cx, float cy, float cz, float radius);

    native private static void encapsulatePoint(
            long sphereVa, float x, float y, float z);

    native private static void free(long sphereVa);

    native private static void getCenter(
            long sphereVa, FloatBuffer storeFloats);

    native private static float getRadius(long sphereVa);

    native private static void getSupport(long sphereVa,
            float dx, float dy, float dz, FloatBuffer storeFloats);
}
