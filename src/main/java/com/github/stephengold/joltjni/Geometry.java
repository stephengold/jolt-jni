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
import com.github.stephengold.joltjni.readonly.ConstLod;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.template.Ref;
import com.github.stephengold.joltjni.template.RefTarget;

/**
 * Triangle batches for multiple levels of detail. (native type:
 * {@code DebugRenderer::Geometry})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Geometry extends JoltPhysicsObject implements RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a geometry with the specified bounding box.
     *
     * @param bounds the desired bounding box (not null, unaffected)
     */
    public Geometry(ConstAaBox bounds) {
        long boundsVa = bounds.targetVa();
        long geometryVa = create(boundsVa);
        long refVa = toRef(geometryVa);
        Runnable freeingAction = () -> GeometryRef.free(refVa);
        setVirtualAddress(geometryVa, freeingAction);
    }

    /**
     * Instantiate a geometry with the specified native object assigned.
     *
     * @param geometryVa the virtual address of the native object to assign (not
     * zero)
     */
    Geometry(long geometryVa) {
        long refVa = toRef(geometryVa);
        Runnable freeingAction = () -> GeometryRef.free(refVa);
        setVirtualAddress(geometryVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the number of levels of detail. The geometry is unaffected.
     * (native member: mLODs)
     *
     * @return the count (&ge;0)
     */
    public int countLods() {
        long geometryVa = va();
        int result = countLods(geometryVa);

        return result;
    }

    /**
     * Access the bounding box. The geometry is unaffected. (native field:
     * mBounds)
     *
     * @return a new JVM object the pre-existing native object assigned
     */
    public ConstAaBox getBounds() {
        long geometryVa = va();
        long resultVa = getBounds(geometryVa);
        AaBox result = new AaBox(this, resultVa);

        return result;
    }

    /**
     * Access the specified level of detail. The geometry is unaffected. (native
     * field: mLODs)
     *
     * @param index the index of the level (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public ConstLod getLod(int index) {
        long geometryVa = va();
        long resultVa = getLodFromIndex(geometryVa, index);
        Lod result = new Lod(this, resultVa);

        return result;
    }

    /**
     * Access the appropriate level of detail for the specified conditions. The
     * geometry is unaffected. (native function: GetLOD)
     *
     * @param cameraPosition the location of the camera (not null, unaffected)
     * @param worldSpaceBounds bounds for this geometry (in system coordinates,
     * not null, unaffected)
     * @param lodScaleSq the squared scale of the model matrix
     * @return a pre-existing LOD
     */
    public ConstLod getLod(Vec3Arg cameraPosition, ConstAaBox worldSpaceBounds,
            float lodScaleSq) {
        long geometryVa = va();
        float camX = cameraPosition.getX();
        float camY = cameraPosition.getY();
        float camZ = cameraPosition.getZ();
        long boundsVa = worldSpaceBounds.targetVa();
        long resultVa = getLodForCamera(
                geometryVa, camX, camY, camZ, boundsVa, lodScaleSq);
        Lod result = new Lod(this, resultVa);

        return result;
    }

    /**
     * Access all the levels of detail. The geometry is unaffected.
     *
     * @return a new array of new JVM objects with pre-existing native objects
     * assigned
     */
    public ConstLod[] getLods() {
        long geometryVa = va();
        int numLods = countLods(geometryVa);
        Lod[] result = new Lod[numLods];
        for (int index = 0; index < numLods; ++index) {
            long levelVa = getLodFromIndex(geometryVa, index);
            result[index] = new Lod(this, levelVa);
        }

        return result;
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code Geometry}. The geometry
     * is unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long geometryVa = va();
        int result = getRefCount(geometryVa);

        return result;
    }

    /**
     * Mark the native {@code Geometry} as embedded.
     */
    @Override
    public void setEmbedded() {
        long geometryVa = va();
        setEmbedded(geometryVa);
    }

    /**
     * Create a counted reference to the native {@code Geometry}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public Ref toRef() {
        long geometryVa = va();
        long refVa = toRef(geometryVa);
        GeometryRef result = new GeometryRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static int countLods(long geometryVa);

    native private static long create(long boundsVa);

    native private static long getBounds(long geometryVa);

    native private static long getLodFromIndex(long geometryVa, int index);

    native private static long getLodForCamera(long geometryVa, float camX,
            float camY, float camZ, long boundsVa, float lodScaleSq);

    native private static int getRefCount(long geometryVa);

    native private static void setEmbedded(long geometryVa);

    native private static long toRef(long geometryVa);
}
