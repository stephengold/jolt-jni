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

import com.github.stephengold.joltjni.readonly.ConstCharacterBaseSettings;
import com.github.stephengold.joltjni.readonly.ConstPlane;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Settings used to create a {@code CharacterBase}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CharacterBaseSettings
        extends JoltPhysicsObject
        implements ConstCharacterBaseSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate settings with no native object assigned.
     */
    CharacterBaseSettings() {
    }

    /**
     * Instantiate settings with the specified native object assigned but not
     * owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    CharacterBaseSettings(long settingsVa) {
        super(settingsVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter whether to make an extra effort to remove contacts with internal
     * edges. (native attribute: mEnhancedInternalEdgeRemoval)
     *
     * @param remove {@code true} to remove ghost contacts (default=false)
     */
    public void setEnhancedInternalEdgeRemoval(boolean remove) {
        long settingsVa = va();
        setEnhancedInternalEdgeRemoval(settingsVa, remove);
    }

    /**
     * Alter the maximum slope that the character can walk on. (native
     * attribute: mMaxSlopeAngle)
     *
     * @param angle (in radians, default=5*Pi/18)
     */
    public void setMaxSlopeAngle(float angle) {
        long settingsVa = va();
        setMaxSlopeAngle(settingsVa, angle);
    }

    /**
     * Replace the shape. (native attribute: mShape)
     *
     * @param shape the desired shape (not null, unaffected, default=null)
     */
    public void setShape(ConstShape shape) {
        long settingsVa = va();
        long shapeVa = shape.targetVa();
        setShape(settingsVa, shapeVa);
    }

    /**
     * Alter the supporting volume. (native attribute: mSupportingVolume)
     *
     * @param plane the desired plane of support (not null, unaffected,
     * default={(0,1,0),-1e10})
     */
    public void setSupportingVolume(ConstPlane plane) {
        long settingsVa = va();
        float nx = plane.getNormalX();
        float ny = plane.getNormalY();
        float nz = plane.getNormalZ();
        float c = plane.getConstant();
        setSupportingVolume(settingsVa, nx, ny, nz, c);
    }

    /**
     * Alter the character's "up" direction. (native attribute: mUp)
     *
     * @param direction the desired direction (not null, unaffected,
     * default=(0,1,0))
     */
    public void setUp(Vec3Arg direction) {
        long settingsVa = va();
        float dx = direction.getX();
        float dy = direction.getY();
        float dz = direction.getZ();
        setUp(settingsVa, dx, dy, dz);
    }
    // *************************************************************************
    // ConstCharacterBaseSettings methods

    /**
     * Test whether to make an extra effort to remove contacts with internal
     * edges. The settings are unaffected. (native attribute:
     * mEnhancedInternalEdgeRemoval)
     *
     * @return {@code true} to remove ghost contacts, otherwise {@code false}
     */
    @Override
    public boolean getEnhancedInternalEdgeRemoval() {
        long settingsVa = va();
        boolean result = getEnhancedInternalEdgeRemoval(settingsVa);

        return result;
    }

    /**
     * Return the maximum slope that the character can walk on. The settings are
     * unaffected. (native attribute: mMaxSlopeAngle)
     *
     * @return the angle (in radians)
     */
    @Override
    public float getMaxSlopeAngle() {
        long settingsVa = va();
        float result = getMaxSlopeAngle(settingsVa);

        return result;
    }

    /**
     * Access the {@code Shape}. (native attribute: mShape)
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null}
     */
    @Override
    public ConstShape getShape() {
        long bodySettingsVa = va();
        long shapeSettingsVa = getShape(bodySettingsVa);
        ConstShape result = Shape.newShape(shapeSettingsVa);

        return result;
    }

    /**
     * Copy the supporting volume. The settings are unaffected. (native
     * attribute: mSupportingVolume)
     *
     * @return a new object
     */
    @Override
    public Plane getSupportingVolume() {
        long settingsVa = va();
        float c = getSupportingVolumeC(settingsVa);
        float nx = getSupportingVolumeNx(settingsVa);
        float ny = getSupportingVolumeNy(settingsVa);
        float nz = getSupportingVolumeNz(settingsVa);
        Plane result = new Plane(c, nx, ny, nz);

        return result;
    }

    /**
     * Copy the character's "up" direction. The settings are unaffected. (native
     * attribute: mUp)
     *
     * @return a new direction vector (in system coordinates)
     */
    @Override
    public Vec3 getUp() {
        long settingsVa = va();
        float dx = getUpX(settingsVa);
        float dy = getUpY(settingsVa);
        float dz = getUpZ(settingsVa);
        Vec3 result = new Vec3(dx, dy, dz);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static boolean getEnhancedInternalEdgeRemoval(
            long settingsVa);

    native private static float getMaxSlopeAngle(long settingsVa);

    native private static long getShape(long settingsVa);

    native private static float getSupportingVolumeC(long settingsVa);

    native private static float getSupportingVolumeNx(long settingsVa);

    native private static float getSupportingVolumeNy(long settingsVa);

    native private static float getSupportingVolumeNz(long settingsVa);

    native private static float getUpX(long settingsVa);

    native private static float getUpY(long settingsVa);

    native private static float getUpZ(long settingsVa);

    native private static void setEnhancedInternalEdgeRemoval(
            long settingsVa, boolean remove);

    native private static void setMaxSlopeAngle(long settingsVa, float angle);

    native private static void setShape(long settingsVa, long shapeVa);

    native private static void setSupportingVolume(
            long settingsVa, float nx, float ny, float nz, float c);

    native private static void setUp(
            long settingsVa, float dx, float dy, float dz);
}
