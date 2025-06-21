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

import com.github.stephengold.joltjni.readonly.ConstAaBox;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.ConstTransformedShape;
import com.github.stephengold.joltjni.readonly.RMat44Arg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

/**
 * A collision shape and a coordinate transform, typically extracted from a
 * {@code Body} or {@code Character}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class TransformedShape
        extends JoltPhysicsObject
        implements ConstTransformedShape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a shape with the specified native object assigned.
     *
     * @param shapeVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    TransformedShape(long shapeVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(shapeVa) : null;
        setVirtualAddress(shapeVa, freeingAction);
    }
    // *************************************************************************
    // ConstTransformedShape methods

    /**
     * Cast a ray and find the closest hit. The shape is unaffected.
     *
     * @param raycast the test ray (not null, unaffected)
     * @param storeResult storage for the result (not null, modified)
     * @return {@code true} if a hit was found, otherwise {@code false}
     */
    @Override
    public boolean castRay(RRayCast raycast, RayCastResult storeResult) {
        long shapeVa = va();
        long raycastVa = raycast.va();
        long resultVa = storeResult.va();
        boolean result = castRay(shapeVa, raycastVa, resultVa);

        return result;
    }

    /**
     * Cast a narrow-phase ray and collect any hits. The shape is unaffected.
     *
     * @param raycast the test ray (not null, unaffected)
     * @param settings the raycast configuration options to use (not null,
     * unaffected)
     * @param collector the hit collector to use (not null)
     * @param shapeFilter the shape filter to apply (not null, unaffected)
     */
    @Override
    public void castRay(RRayCast raycast, RayCastSettings settings,
            CastRayCollector collector, ShapeFilter shapeFilter) {
        long shapeVa = va();
        long raycastVa = raycast.va();
        long settingsVa = settings.va();
        long collectorVa = collector.va();
        long filterVa = shapeFilter.va();
        castRayAndCollect(
                shapeVa, raycastVa, settingsVa, collectorVa, filterVa);
    }

    /**
     * Cast a narrow-phase shape and collect any hits. The shape is unaffected.
     *
     * @param shapeCast the desired shape cast (not null, unaffected)
     * @param settings the collision settings to use (not null, unaffected)
     * @param base the base location for reporting hits (not null, unaffected,
     * (0,0,0)&rarr;world coordinates)
     * @param collector the hit collector to use (not null)
     * @param shapeFilter the shape filter to apply (not null, unaffected)
     */
    @Override
    public void castShape(RShapeCast shapeCast, ShapeCastSettings settings,
            RVec3Arg base, CastShapeCollector collector,
            ShapeFilter shapeFilter) {
        long shapeVa = va();
        long shapecastVa = shapeCast.va();
        long settingsVa = settings.va();
        double xx = base.xx();
        double yy = base.yy();
        double zz = base.zz();
        long collectorVa = collector.va();
        long filterVa = shapeFilter.va();
        castShape(shapeVa, shapecastVa, settingsVa, xx, yy, zz,
                collectorVa, filterVa);
    }

    /**
     * Collect transformed shapes for all leaf shapes of the current shape that
     * collide with the specified bounding box. The shape is unaffected.
     *
     * @param box the region of interest (in system coordinates)
     * @param collector the hit collector to use (not null)
     * @param shapeFilter the shape filter to apply (not null, unaffected)
     */
    @Override
    public void collectTransformedShapes(ConstAaBox box,
            TransformedShapeCollector collector, ShapeFilter shapeFilter) {
        long shapeVa = va();
        long boxVa = box.targetVa();
        long collectorVa = collector.va();
        long filterVa = shapeFilter.va();
        collectTransformedShapes(shapeVa, boxVa, collectorVa, filterVa);
    }

    /**
     * Collect collisions with the specified point. The shape is unaffected.
     *
     * @param point the location of the point to test (not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param shapeFilter the shape filter to apply (not null, unaffected)
     */
    @Override
    public void collidePoint(RVec3Arg point, CollidePointCollector collector,
            ShapeFilter shapeFilter) {
        long shapeVa = va();
        double xx = point.xx();
        double yy = point.yy();
        double zz = point.zz();
        long collectorVa = collector.va();
        long filterVa = shapeFilter.va();
        collidePoint(shapeVa, xx, yy, zz, collectorVa, filterVa);
    }

    /**
     * Collect collisions with the specified shape. The shape is unaffected.
     *
     * @param testShape the shape to test (not null, unaffected)
     * @param shapeScale the scaling vector for the test shape (not null,
     * unaffected)
     * @param comTransform the coordinate transform to apply to the test shape's
     * center of mass (not null, unaffected)
     * @param settings the collision settings to use (not null, unaffected)
     * @param base the base location for reporting hits (not null, unaffected,
     * (0,0,0)&rarr;world coordinates)
     * @param collector the hit collector to use (not null)
     */
    @Override
    public void collideShape(ConstShape testShape, Vec3Arg shapeScale,
            RMat44Arg comTransform, CollideShapeSettings settings,
            RVec3Arg base, CollideShapeCollector collector) {
        collideShape(testShape, shapeScale, comTransform, settings, base,
                collector, new ShapeFilter());
    }

    /**
     * Collect collisions with the specified shape. The shape is unaffected.
     *
     * @param testShape the shape to test (not null, unaffected)
     * @param shapeScale the scaling vector for the test shape (not null,
     * unaffected)
     * @param comTransform the coordinate transform to apply to the test shape's
     * center of mass (not null, unaffected)
     * @param settings the collision settings to use (not null, unaffected)
     * @param base the base location for reporting hits (not null, unaffected,
     * (0,0,0)&rarr;world coordinates)
     * @param collector the hit collector to use (not null)
     * @param shapeFilter the shape filter to apply (not null, unaffected)
     */
    @Override
    public void collideShape(ConstShape testShape, Vec3Arg shapeScale,
            RMat44Arg comTransform, CollideShapeSettings settings,
            RVec3Arg base, CollideShapeCollector collector,
            ShapeFilter shapeFilter) {
        long transformedShapeVa = va();
        long testShapeVa = testShape.targetVa();
        float sx = shapeScale.getX();
        float sy = shapeScale.getY();
        float sz = shapeScale.getZ();
        long transformVa = comTransform.targetVa();
        long settingsVa = settings.va();
        double xx = base.xx();
        double yy = base.yy();
        double zz = base.zz();
        long collectorVa = collector.va();
        long filterVa = shapeFilter.va();
        collideShape(transformedShapeVa, testShapeVa, sx, sy, sz, transformVa,
                settingsVa, xx, yy, zz, collectorVa, filterVa);
    }

    /**
     * Copy the vertex coordinates of the shape's debug mesh to the specified
     * buffer. The shape is unaffected.
     *
     * @param storeBuffer the buffer to fill with vertex coordinates (not null,
     * modified)
     */
    @Override
    public void copyDebugTriangles(FloatBuffer storeBuffer) {
        long transformedShapeVa = va();
        int numTriangles = storeBuffer.capacity() / 9;
        copyDebugTriangles(transformedShapeVa, numTriangles, storeBuffer);
    }

    /**
     * Count the triangles in the shape's debug mesh. The shape is unaffected.
     *
     * @return the count (&gt;0)
     */
    @Override
    public int countDebugTriangles() {
        long transformedShapeVa = va();
        int result = countDebugTriangles(transformedShapeVa);

        assert result > 0 : "result = " + result;
        return result;
    }

    /**
     * Access the underlying coordinate transform. The shape is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public RMat44Arg getCenterOfMassTransform() {
        long transformedShapeVa = va();
        long resultVa = getCenterOfMassTransform(transformedShapeVa);
        RMat44Arg result = new RMat44(this, resultVa);

        return result;
    }

    /**
     * Access the underlying collision shape. The shape is unaffected. (native
     * member: mShape)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public ConstShape getShape() {
        long transformedShapeVa = va();
        long resultVa = getShape(transformedShapeVa);
        ConstShape result = Shape.newShape(resultVa);

        return result;
    }

    /**
     * Copy the location of the center of mass. The shape is unaffected. (native
     * member: mShapePositionCOM)
     *
     * @return a new object
     */
    @Override
    public RVec3 getShapePositionCom() {
        long transformedShapeVa = va();
        DoubleBuffer storeDoubles = Temporaries.doubleBuffer1.get();
        getShapePositionCom(transformedShapeVa, storeDoubles);
        RVec3 result = new RVec3(storeDoubles);

        return result;
    }

    /**
     * Copy the rotation of the shape. The shape is unaffected. (native member:
     * mShapeRotation)
     *
     * @return a new object
     */
    @Override
    public Quat getShapeRotation() {
        long transformedShapeVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getShapeRotation(transformedShapeVa, storeFloats);
        Quat result = new Quat(storeFloats);

        return result;
    }

    /**
     * Copy the scale factors of the shape. The shape is unaffected.
     *
     * @return a new object
     */
    @Override
    public Float3 getShapeScale() {
        long transformedShapeVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getShapeScale(transformedShapeVa, storeFloats);
        Float3 result = new Float3(storeFloats);

        return result;
    }

    /**
     * Get the vertices of the face that provides support in the specified
     * direction. The shape is unaffected.
     *
     * @param subShapeId which sub-shape to use (not null, unaffected)
     * @param direction the test direction (in world coordinates, not null,
     * unaffected)
     * @param base the base location for reporting face vertices (not null,
     * unaffected, (0,0,0)&rarr;world coordinates)
     * @param storeFace storage for face vertices (not null)
     */
    @Override
    public void getSupportingFace(int subShapeId, Vec3Arg direction,
            RVec3Arg base, SupportingFace storeFace) {
        long shapeVa = va();
        float dx = direction.getX();
        float dy = direction.getY();
        float dz = direction.getZ();
        double xx = base.xx();
        double yy = base.yy();
        double zz = base.zz();
        long faceVa = storeFace.va();
        getSupportingFace(shapeVa, subShapeId, dx, dy, dz, xx, yy, zz, faceVa);
    }

    /**
     * Collect triangles for debug visualization. The shape is unaffected.
     *
     * @param storeContext storage for communication with
     * {@code getTrianglesStart()}
     * @param maxTriangles the maximum number of triangles to collect
     * @param storeVertices storage for triangle vertices
     * @return the number of triangles collected, or 0 if no more triangles are
     * available
     */
    @Override
    public int getTrianglesNext(GetTrianglesContext storeContext,
            int maxTriangles, FloatBuffer storeVertices) {
        long shapeVa = va();
        long contextVa = storeContext.va();
        int result = getTrianglesNext(
                shapeVa, contextVa, maxTriangles, storeVertices);

        return result;
    }

    /**
     * Prepare to collect triangles in the specified box for debug
     * visualization. The shape is unaffected.
     *
     * @param storeContext storage for communication with
     * {@code getTrianglesNext()}
     * @param box the region of interest (in system coordinates, not null,
     * unaffected)
     * @param base the base location for reporting triangle vertices (not null,
     * unaffected, (0,0,0)&rarr;world coordinates)
     */
    @Override
    public void getTrianglesStart(GetTrianglesContext storeContext,
            ConstAaBox box, RVec3Arg base) {
        long shapeVa = va();
        long contextVa = storeContext.va();
        long boxVa = box.targetVa();
        double xx = base.xx();
        double yy = base.yy();
        double zz = base.zz();
        getTrianglesStart(shapeVa, contextVa, boxVa, xx, yy, zz);
    }

    /**
     * Return the bounding box including convex radius. The shape is unaffected.
     *
     * @return a new, mutable box (in system coordinates)
     */
    @Override
    public AaBox getWorldSpaceBounds() {
        long shapeVa = va();
        long resultVa = getWorldSpaceBounds(shapeVa);
        AaBox result = new AaBox(resultVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static boolean castRay(
            long shapeVa, long raycastVa, long resultVa);

    native private static void castRayAndCollect(long shapeVa, long raycastVa,
            long settingsVa, long collectorVa, long filterVa);

    native private static void castShape(
            long shapeVa, long shapecastVa, long settingsVa, double xx,
            double yy, double zz, long collectorVa, long filterVa);

    native private static void collectTransformedShapes(
            long shapeVa, long boxVa, long collectorVa, long filterVa);

    native private static void collidePoint(long shapeVa, double xx, double yy,
            double zz, long collectorVa, long filterVa);

    native private static void collideShape(long transformedShapeVa,
            long testShapeVa, float sx, float sy, float sz, long transformVa,
            long settingsVa, double xx, double yy, double zz, long collectorVa,
            long filterVa);

    native private static void copyDebugTriangles(
            long transformedShapeVa, int numTriangles, FloatBuffer storeBuffer);

    native private static int countDebugTriangles(long transformedShapeVa);

    native private static void free(long shapeVa);

    native private static long getCenterOfMassTransform(
            long transformedShapeVa);

    native private static long getShape(long transformedShapeVa);

    native private static void getShapePositionCom(
            long transformedShapeVa, DoubleBuffer storeDoubles);

    native private static void getShapeRotation(
            long transformedShapeVa, FloatBuffer storeFloats);

    native private static void getShapeScale(
            long transformedShapeVa, FloatBuffer storeFloats);

    native private static void getSupportingFace(
            long shapeVa, int subShapeId, float dx, float dy, float dz,
            double xx, double yy, double zz, long faceVa);

    native private static int getTrianglesNext(long shapeVa, long contextVa,
            int maxTriangles, FloatBuffer storeVertices);

    native private static void getTrianglesStart(long shapeVa, long contextVa,
            long boxVa, double xx, double yy, double zz);

    native private static long getWorldSpaceBounds(long shapeVa);
}
