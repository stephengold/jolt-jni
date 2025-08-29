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

import com.github.stephengold.joltjni.readonly.ConstPhysicsMaterial;
import com.github.stephengold.joltjni.readonly.ConstPlane;
import java.nio.FloatBuffer;

/**
 * A {@code Shape} to represent a surface defined by a plane.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class PlaneShape extends Shape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate the specified shape without creating a settings object.
     *
     * @param plane the plane to use (not null, unaffected)
     */
    public PlaneShape(ConstPlane plane) {
        this(plane, null);
    }

    /**
     * Instantiate the specified shape without creating a settings object.
     *
     * @param plane the plane to use (not null, unaffected)
     * @param material the desired surface properties (unaffected) or
     * {@code null} for default properties (default=null)
     */
    public PlaneShape(ConstPlane plane, ConstPhysicsMaterial material) {
        this(plane, material, PlaneShapeSettings.cDefaultHalfExtent);
    }

    /**
     * Instantiate the specified shape without creating a settings object.
     *
     * @param plane the plane to use (not null, unaffected)
     * @param material the desired surface properties (unaffected) or
     * {@code null} for default properties (default=null)
     * @param halfExtent the desired radius of the bounding box (&gt;0,
     * default=1000)
     */
    public PlaneShape(
            ConstPlane plane, ConstPhysicsMaterial material, float halfExtent) {
        float nx = plane.getNormalX();
        float ny = plane.getNormalY();
        float nz = plane.getNormalZ();
        float planeConstant = plane.getConstant();
        long materialVa = (material == null) ? 0L : material.targetVa();
        long shapeVa = createShape(
                nx, ny, nz, planeConstant, materialVa, halfExtent);
        setVirtualAddress(shapeVa); // not the owner due to ref counting
    }

    /**
     * Instantiate a shape with the specified native object assigned but not
     * owned.
     *
     * @param shapeVa the virtual address of the native object to assign (not
     * zero)
     */
    PlaneShape(long shapeVa) {
        super(shapeVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the shape's surface. The shape is unaffected.
     * <p>
     * Different semantics from the native {@code GetPlane()}, which
     * returns a const reference, not a copy.
     *
     * @return a new object
     */
    public Plane getPlane() {
        long shapeVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getPlane(shapeVa, storeFloats);
        Plane result = new Plane(storeFloats);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createShape(float nx, float ny, float nz,
            float planeConstant, long materialVa, float halfExtent);

    native private static void getPlane(long shapeVa, FloatBuffer storeFloats);
}
