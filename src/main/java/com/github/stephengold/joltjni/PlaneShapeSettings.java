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

import com.github.stephengold.joltjni.enumerate.EShapeSubType;
import com.github.stephengold.joltjni.readonly.ConstPhysicsMaterial;
import com.github.stephengold.joltjni.readonly.ConstPlane;

/**
 * Settings used to construct a {@code PlaneShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class PlaneShapeSettings extends ShapeSettings {
    // *************************************************************************
    // constants

    /**
     * default half-extent for the {@code PlaneShape}
     */
    final public static float cDefaultHalfExtent = 1_000f;
    // *************************************************************************
    // constructors

    /**
     * Instantiate settings for the specified plane.
     *
     * @param plane the desired surface plane (not null, unaffected)
     */
    public PlaneShapeSettings(ConstPlane plane) {
        this(plane, null);
    }

    /**
     * Instantiate settings for the specified plane and material.
     *
     * @param plane the desired surface plane (not null, unaffected)
     * @param material the desired surface properties (not null, unaffected) or
     * {@code null} for default properties (default=null)
     */
    public PlaneShapeSettings(ConstPlane plane, ConstPhysicsMaterial material) {
        this(plane, material, cDefaultHalfExtent);
    }

    /**
     * Instantiate settings for the specified plane, material, and extent.
     *
     * @param plane the desired surface plane (not null, unaffected)
     * @param material the desired surface properties (not null, unaffected) or
     * {@code null} for default properties (default=null)
     * @param halfExtent the desired radius of the bounding box (&gt;0,
     * default=1000)
     */
    public PlaneShapeSettings(
            ConstPlane plane, ConstPhysicsMaterial material, float halfExtent) {
        float nx = plane.getNormalX();
        float ny = plane.getNormalY();
        float nz = plane.getNormalZ();
        float planeConstant = plane.getConstant();
        long materialVa = (material == null) ? 0L : material.targetVa();
        long settingsVa = createPlaneShapeSettings(
                nx, ny, nz, planeConstant, materialVa, halfExtent);
        setVirtualAddress(settingsVa); // not owner due to ref counting
        setSubType(EShapeSubType.Plane);
    }

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    PlaneShapeSettings(long settingsVa) {
        super(settingsVa);
        setSubType(EShapeSubType.Plane);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the half extent. The settings are unaffected. (native attribute:
     * mHalfExtent)
     *
     * @return half the extent
     */
    public float getHalfExtent() {
        long settingsVa = va();
        float result = getHalfExtent(settingsVa);

        return result;
    }

    /**
     * Return the material. The settings are unaffected. (native attribute:
     * mMaterial)
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if none
     */
    public ConstPhysicsMaterial getMaterial() {
        long settingsVa = va();
        long materialVa = getMaterial(settingsVa);
        ConstPhysicsMaterial result;
        if (materialVa == 0L) {
            result = null;
        } else {
            result = new PhysicsMaterial(materialVa);
        }

        return result;
    }

    /**
     * Copy the surface equation. The settings are unaffected. (native
     * attribute: mPlane)
     *
     * @return a new {@code Plane}
     */
    public Plane getPlane() {
        long settingsVa = va();
        float nx = getPlaneX(settingsVa);
        float ny = getPlaneY(settingsVa);
        float nz = getPlaneZ(settingsVa);
        float c = getPlaneConstant(settingsVa);
        Plane result = new Plane(nx, ny, nz, c);

        return result;
    }

    /**
     * Alter the plane's extent. (native attribute: mHalfExtent)
     *
     * @param halfExtent one-half of the desired extent
     */
    public void setHalfExtent(float halfExtent) {
        long settingsVa = va();
        setHalfExtent(settingsVa, halfExtent);
    }

    /**
     * Replace the material. (native attribute: mMaterial)
     *
     * @param material the desired material, or {@code null} if none
     * (default=null)
     */
    public void setMaterial(ConstPhysicsMaterial material) {
        long settingsVa = va();
        long materialVa = (material == null) ? 0L : material.targetVa();
        setMaterial(settingsVa, materialVa);
    }

    /**
     * Alter the surface equation. (native attribute: mPlane)
     *
     * @param plane the desired surface (not null, unaffected)
     */
    public void setPlane(ConstPlane plane) {
        long settingsVa = va();
        float nx = plane.getNormalX();
        float ny = plane.getNormalY();
        float nz = plane.getNormalZ();
        float c = plane.getConstant();
        setPlane(settingsVa, nx, ny, nz, c);
    }
    // *************************************************************************
    // native private methods

    native private static long createPlaneShapeSettings(float nx, float ny,
            float nz, float planeConstant, long materialVa, float halfExtent);

    native private static float getHalfExtent(long settingsVa);

    native private static long getMaterial(long settingsVa);

    native private static float getPlaneConstant(long settingsVa);

    native private static float getPlaneX(long settingsVa);

    native private static float getPlaneY(long settingsVa);

    native private static float getPlaneZ(long settingsVa);

    native private static void setHalfExtent(long settingsVa, float halfExtent);

    native private static void setMaterial(long settingsVa, long materialVa);

    native private static void setPlane(
            long settingsVa, float nx, float ny, float nz, float c);
}
