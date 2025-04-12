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

import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * A {@code Shape} to represent a surface defined by a matrix of heights.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class HeightFieldShape extends Shape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a shape with the specified native object assigned but not
     * owned.
     *
     * @param shapeVa the virtual address of the native object to assign (not
     * zero)
     */
    HeightFieldShape(long shapeVa) {
        super(shapeVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the block size. The shape is unaffected.
     *
     * @return the number of height values in a block
     */
    public int getBlockSize() {
        long shapeVa = va();
        int result = getBlockSize(shapeVa);

        return result;
    }

    /**
     * Copy the location of specified sample. The shape is unaffected.
     *
     * @param x the X coordinate of the sample (&ge;0, &lt;sampleCount)
     * @param y the Y coordinate of the sample (&ge;0, &lt;sampleCount)
     * @return a new location vector
     */
    public Vec3 getPosition(int x, int y) {
        long shapeVa = va();
        float posX = getPositionX(shapeVa, x, y);
        float posY = getPositionY(shapeVa, x, y);
        float posZ = getPositionZ(shapeVa, x, y);
        Vec3 result = new Vec3(posX, posY, posZ);

        return result;
    }

    /**
     * Calculate the normal to the surface at the specified surface location.
     *
     * @param subShapeId which sub-shape to use
     * @param localLocation the location relative to the shape's center of mass
     * (not null, unaffected)
     * @return a new direction vector
     */
    public Vec3 getSurfaceNormal(int subShapeId, Vec3Arg localLocation) {
        long shapeVa = va();
        float x = localLocation.getX();
        float y = localLocation.getY();
        float z = localLocation.getZ();
        float[] storeFloats = new float[3];
        getSurfaceNormal(shapeVa, subShapeId, x, y, z, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Test whether the shape has a hole at the specified sample. The shape is
     * unaffected.
     *
     * @param x the X coordinate of the sample (&ge;0, &lt;sampleCount)
     * @param y the Y coordinate of the sample (&ge;0, &lt;sampleCount)
     * @return {@code true} if there's a hole, otherwise {@code false}
     */
    public boolean isNoCollision(int x, int y) {
        long shapeVa = va();
        boolean result = isNoCollision(shapeVa, x, y);

        return result;
    }

    /**
     * Project the specified location along the Y axis to find a location on the
     * surface.
     *
     * @param localLocation the location relative to the shape's center of mass
     * (not null, unaffected)
     * @param storeSurfaceLocation storage for the surface location (not null,
     * modified)
     * @param storeSubShapeId storage for the sub-shape ID (not null, modified)
     * @return {@code true} if a valid surface location was found, otherwise
     * {@code false}
     */
    public boolean projectOntoSurface(Vec3Arg localLocation,
            Vec3 storeSurfaceLocation, int[] storeSubShapeId) {
        long shapeVa = va();
        float x = localLocation.getX();
        float y = localLocation.getY();
        float z = localLocation.getZ();
        float[] storeFloats = new float[3];
        boolean result = projectOntoSurface(
                shapeVa, x, y, z, storeFloats, storeSubShapeId);
        storeSurfaceLocation.set(storeFloats);
        return result;
    }

    /**
     * Alter the heights for a rectangular sub-matrix.
     *
     * @param startX the index of the start row (multiple of the block size,
     * &ge;0, &lt;{@code sampleCount})
     * @param startY the index of the start column (multiple of the block size,
     * &ge;0, &lt;{@code sampleCount})
     * @param sizeX the number of affected rows (multiple of the block size,
     * &ge;0, &le;{@code sampleCount-startX})
     * @param sizeY the number of affected columns (multiple of the block size,
     * &ge;0, &le;{@code sampleCount-startY})
     * @param heights the height values to set
     * @param stride stride between consecutive rows in {@code heights} (in
     * floats)
     * @param allocator for temporary allocations (not null)
     */
    public void setHeights(int startX, int startY, int sizeX, int sizeY,
            FloatBuffer heights, int stride, TempAllocator allocator) {
        setHeights(startX, startY, sizeX, sizeY, heights, stride,
                allocator, 0.996195f);
    }

    /**
     * Alter the heights for a rectangular sub-matrix.
     *
     * @param startX the index of the start row (multiple of the block size,
     * &ge;0, &lt;{@code sampleCount})
     * @param startY the index of the start column (multiple of the block size,
     * &ge;0, &lt;{@code sampleCount})
     * @param sizeX the number of affected rows (multiple of the block size,
     * &ge;0, &le;{@code sampleCount-startX})
     * @param sizeY the number of affected columns (multiple of the block size,
     * &ge;0, &le;{@code sampleCount-startY})
     * @param heights the height values to set
     * @param stride stride between consecutive rows in {@code heights} (in
     * floats)
     * @param allocator for temporary allocations (not null)
     * @param cosThresholdAngle cosine of the threshold angle (default=0.996195)
     */
    public void setHeights(int startX, int startY, int sizeX, int sizeY,
            FloatBuffer heights, int stride,
            TempAllocator allocator, float cosThresholdAngle) {
        long shapeVa = va();
        long allocatorVa = allocator.va();
        setHeights(shapeVa, startX, startY, sizeX, sizeY, heights, stride,
                allocatorVa, cosThresholdAngle);
    }
    // *************************************************************************
    // native private methods

    native private static int getBlockSize(long shapeVa);

    native private static float getPositionX(long shapeVa, int x, int y);

    native private static float getPositionY(long shapeVa, int x, int y);

    native private static float getPositionZ(long shapeVa, int x, int y);

    native private static void getSurfaceNormal(long shapeVa, int subShapeId,
            float x, float y, float z, float[] storeFloats);

    native private static boolean isNoCollision(long shapeVa, int x, int y);

    native private static boolean projectOntoSurface(long shapeVa, float x,
            float y, float z, float[] storeFloats, int[] storeSubShapeId);

    native private static void setHeights(long shapeVa, int startX, int startY,
            int sizeX, int sizeY, FloatBuffer heights, int stride,
            long allocatorVa, float cosThresholdAngle);
}
