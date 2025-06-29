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

import com.github.stephengold.joltjni.enumerate.EActiveEdgeMode;
import com.github.stephengold.joltjni.enumerate.ECollectFacesMode;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Configurable options for a shape-oriented collision query.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CollideSettingsBase extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a settings object with no native object assigned.
     */
    CollideSettingsBase() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return how edge collisions should be handled. The settings are
     * unaffected. (native attribute: mActiveEdgeMode)
     *
     * @return an enum value (not null)
     */
    public EActiveEdgeMode getActiveEdgeMode() {
        long settingsVa = va();
        int ordinal = getActiveEdgeMode(settingsVa);
        EActiveEdgeMode result = EActiveEdgeMode.values()[ordinal];

        return result;
    }

    /**
     * Copy the movement direction for inactive edges. The settings are
     * unaffected. (native attribute: mActiveEdgeMovementDirection)
     *
     * @return a new direction vector
     */
    public Vec3 getActiveEdgeMovementDirection() {
        long settingsVa = va();
        float dx = getActiveEdgeMovementDirectionX(settingsVa);
        float dy = getActiveEdgeMovementDirectionY(settingsVa);
        float dz = getActiveEdgeMovementDirectionZ(settingsVa);
        Vec3 result = new Vec3(dx, dy, dz);

        return result;
    }

    /**
     * Return how face information should be handled. The settings are
     * unaffected. (native attribute: mCollectFacesMode)
     *
     * @return an enum value (not null)
     */
    public ECollectFacesMode getCollectFacesMode() {
        long settingsVa = va();
        int ordinal = getCollectFacesMode(settingsVa);
        ECollectFacesMode result = ECollectFacesMode.values()[ordinal];

        return result;
    }

    /**
     * Return the collision tolerance for the GJK algorithm. The settings are
     * unaffected. (native attribute: mCollisionTolerance)
     *
     * @return an enum value (not null)
     */
    public float getCollisionTolerance() {
        long settingsVa = va();
        float result = getCollisionTolerance(settingsVa);

        return result;
    }

    /**
     * Return the termination tolerance for calculating penetration depth. The
     * settings are unaffected. (native attribute: mPenetrationTolerance)
     *
     * @return the tolerance
     */
    public float getPenetrationTolerance() {
        long settingsVa = va();
        float result = getPenetrationTolerance(settingsVa);

        return result;
    }

    /**
     * Alter how edge collisions should be handled. (native attribute:
     * mActiveEdgeMode)
     *
     * @param mode the desired mode (not null, default=CollideOnlyWithActive)
     */
    public void setActiveEdgeMode(EActiveEdgeMode mode) {
        long settingsVa = va();
        int ordinal = mode.ordinal();
        setActiveEdgeMode(settingsVa, ordinal);
    }

    /**
     * Alter the movement direction for inactive edges. (native attribute:
     * mActiveEdgeMovementDirection)
     *
     * @param direction the desired direction (not null, unaffected,
     * default=(0,0,0))
     */
    public void setActiveEdgeMovementDirection(Vec3Arg direction) {
        long settingsVa = va();
        float dx = direction.getX();
        float dy = direction.getY();
        float dz = direction.getZ();
        setActiveEdgeMovementDirection(settingsVa, dx, dy, dz);
    }

    /**
     * Alter how face information should be handled. (native attribute:
     * mCollectFacesMode)
     *
     * @param mode the desired mode (not null, default=NoFaces)
     */
    public void setCollectFacesMode(ECollectFacesMode mode) {
        long settingsVa = va();
        int ordinal = mode.ordinal();
        setCollectFacesMode(settingsVa, ordinal);
    }

    /**
     * Alter the collision tolerance for the GJK algorithm. (native attribute:
     * mCollisionTolerance)
     *
     * @param tolerance the desired tolerance (in meters,
     * default=cDefaultCollisionTolerance)
     */
    public void setCollisionTolerance(float tolerance) {
        long settingsVa = va();
        setCollisionTolerance(settingsVa, tolerance);
    }

    /**
     * Alter the termination tolerance for calculating penetration depth.
     * (native attribute: mPenetrationTolerance)
     *
     * @param tolerance the desired tolerance
     * (default=cDefaultPenetrationTolerance)
     */
    public void setPenetrationTolerance(float tolerance) {
        long settingsVa = va();
        setPenetrationTolerance(settingsVa, tolerance);
    }
    // *************************************************************************
    // new protected methods

    /**
     * Assign a native object (assuming there's none already assigned) and
     * designate the JVM object as the owner.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    final void setVirtualAddressAsOwner(long settingsVa) {
        Runnable freeingAction = () -> free(settingsVa);
        setVirtualAddress(settingsVa, freeingAction);
    }
    // *************************************************************************
    // native private methods

    native private static void free(long settingsVa);

    native private static int getActiveEdgeMode(long settingsVa);

    native private static float getActiveEdgeMovementDirectionX(
            long settingsVa);

    native private static float getActiveEdgeMovementDirectionY(
            long settingsVa);

    native private static float getActiveEdgeMovementDirectionZ(
            long settingsVa);

    native private static int getCollectFacesMode(long settingsVa);

    native private static float getCollisionTolerance(long settingsVa);

    native private static float getPenetrationTolerance(long settingsVa);

    native private static void setActiveEdgeMode(long settingsVa, int ordinal);

    native private static void setActiveEdgeMovementDirection(
            long settingsVa, float dx, float dy, float dz);

    native private static void setCollectFacesMode(
            long settingsVa, int ordinal);

    native private static void setCollisionTolerance(
            long settingsVa, float tolerance);

    native private static void setPenetrationTolerance(
            long settingsVa, float tolerance);
}
