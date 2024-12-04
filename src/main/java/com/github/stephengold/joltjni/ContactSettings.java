/*
Copyright (c) 2024 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.ConstContactSettings;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Properties of a contact constraint that can be overridden during certain
 * {@code ContactListener} callbacks.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ContactSettings
        extends JoltPhysicsObject
        implements ConstContactSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate the default settings for testing.
     */
    public ContactSettings() {
        long settingsVa = createContactSettings();
        setVirtualAddress(settingsVa, () -> free(settingsVa));
    }

    /**
     * Instantiate settings with the specified native object assigned but not
     * owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    public ContactSettings(long settingsVa) {
        setVirtualAddress(settingsVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the combined friction for the body pair. (native attribute:
     * mCombinedFriction)
     *
     * @param friction the desired combined friction (default=0)
     */
    public void setCombinedFriction(float friction) {
        long settingsVa = va();
        setCombinedFriction(settingsVa, friction);
    }

    /**
     * Alter the combined restitution for the body pair. (native attribute:
     * mCombinedRestitution)
     *
     * @param restitution the desired combined restitution (default=0)
     */
    public void setCombinedRestitution(float restitution) {
        long settingsVa = va();
        setCombinedRestitution(settingsVa, restitution);
    }

    /**
     * Alter the scale factor for the inverse inertia of body 1. (native
     * attribute: mInvInertiaScale1)
     *
     * @param factor the factor (0 = infinite inertia, 1 = use original inertia,
     * default=1)
     */
    public void setInvInertiaScale1(float factor) {
        long settingsVa = va();
        setInvInertiaScale1(settingsVa, factor);
    }

    /**
     * Alter the scale factor for the inverse inertia of body 2. (native
     * attribute: mInvInertiaScale2)
     *
     * @param factor the factor (0 = infinite inertia, 1 = use original inertia,
     * default=1)
     */
    public void setInvInertiaScale2(float factor) {
        long settingsVa = va();
        setInvInertiaScale2(settingsVa, factor);
    }

    /**
     * Alter the scale factor for the inverse mass of body 1. (native attribute:
     * mInvMassScale1)
     *
     * @param factor the factor (0 = infinite mass, 1 = use original mass,
     * default=1)
     */
    public void setInvMassScale1(float factor) {
        long settingsVa = va();
        setInvMassScale1(settingsVa, factor);
    }

    /**
     * Alter the scale factor for the inverse mass of body 2. (native attribute:
     * mInvMassScale2)
     *
     * @param factor the factor (0 = infinite mass, 1 = use original mass,
     * default=1)
     */
    public void setInvMassScale2(float factor) {
        long settingsVa = va();
        setInvMassScale2(settingsVa, factor);
    }

    /**
     * Alter whether the contact should be treated as a sensor (no collision
     * response). (native attribute: mIsSensor)
     *
     * @param setting {@code true} to treat as a sensor, otherwise {@code false}
     * (default=false)
     */
    public void setIsSensor(boolean setting) {
        long settingsVa = va();
        setIsSensor(settingsVa, setting);
    }

    /**
     * Alter the relative angular velocity (body 2 minus body 1). (native
     * attribute: mRelativeAngularSurfaceVelocity)
     *
     * @param omega the desired angular velocity (radians per second in system
     * coordinates, default=(0,0,0))
     */
    public void setRelativeAngularSurfaceVelocity(Vec3Arg omega) {
        long settingsVa = va();
        float wx = omega.getX();
        float wy = omega.getY();
        float wz = omega.getZ();
        setRelativeAngularSurfaceVelocity(settingsVa, wx, wy, wz);
    }

    /**
     * Alter the relative linear velocity (body 2 minus body 1). (native
     * attribute: mRelativeLinearSurfaceVelocity)
     *
     * @param velocity the desired velocity (meters per second in system
     * coordinates, default=(0,0,0))
     */
    public void setRelativeLinearSurfaceVelocity(Vec3Arg velocity) {
        long settingsVa = va();
        float vx = velocity.getX();
        float vy = velocity.getY();
        float vz = velocity.getZ();
        setRelativeLinearSurfaceVelocity(settingsVa, vx, vy, vz);
    }
    // *************************************************************************
    // ConstContactSettings methods

    /**
     * Return the combined friction for the body pair. The settings are
     * unaffected. (native attribute: mCombinedFriction)
     *
     * @return the combined friction
     */
    @Override
    public float getCombinedFriction() {
        long settingsVa = va();
        float result = getCombinedFriction(settingsVa);

        return result;
    }

    /**
     * Return the combined restitution for the body pair. The settings are
     * unaffected. (native attribute: mCombinedRestitution)
     *
     * @return the combined restitution
     */
    @Override
    public float getCombinedRestitution() {
        long settingsVa = va();
        float result = getCombinedRestitution(settingsVa);

        return result;
    }

    /**
     * Return the scale factor for the inverse inertia of body 1. The settings
     * are unaffected. (native attribute: mInvInertiaScale1)
     *
     * @return the factor (0 = infinite inertia, 1 = use original inertia)
     */
    @Override
    public float getInvInertiaScale1() {
        long settingsVa = va();
        float result = getInvInertiaScale1(settingsVa);

        return result;
    }

    /**
     * Return the scale factor for the inverse inertia of body 2. The settings
     * are unaffected. (native attribute: mInvInertiaScale2)
     *
     * @return the factor (0 = infinite inertia, 1 = use original inertia)
     */
    @Override
    public float getInvInertiaScale2() {
        long settingsVa = va();
        float result = getInvInertiaScale2(settingsVa);

        return result;
    }

    /**
     * Return the scale factor for the inverse mass of body 1. The settings are
     * unaffected. (native attribute: mInvMassScale1)
     *
     * @return the factor (0 = infinite mass, 1 = use original mass)
     */
    @Override
    public float getInvMassScale1() {
        long settingsVa = va();
        float result = getInvMassScale1(settingsVa);

        return result;
    }

    /**
     * Return the scale factor for the inverse mass of body 2. The settings are
     * unaffected. (native attribute: mInvMassScale2)
     *
     * @return the factor (0 = infinite mass, 1 = use original mass)
     */
    @Override
    public float getInvMassScale2() {
        long settingsVa = va();
        float result = getInvMassScale2(settingsVa);

        return result;
    }

    /**
     * Test whether the contact should be treated as a sensor (no collision
     * response). The settings are unaffected. (native attribute: mIsSensor)
     *
     * @return {@code true} if treated as a sensor, otherwise {@code false}
     */
    @Override
    public boolean getIsSensor() {
        long settingsVa = va();
        boolean result = getIsSensor(settingsVa);

        return result;
    }

    /**
     * Return the relative angular velocity (body 2 minus body 1). The settings
     * are unaffected. (native attribute: mRelativeAngularSurfaceVelocity)
     *
     * @return a new velocity vector (radians per second in system coordinates)
     */
    @Override
    public Vec3 getRelativeAngularSurfaceVelocity() {
        long settingsVa = va();
        float wx = getRelativeAngularSurfaceVelocityX(settingsVa);
        float wy = getRelativeAngularSurfaceVelocityY(settingsVa);
        float wz = getRelativeAngularSurfaceVelocityZ(settingsVa);
        Vec3 result = new Vec3(wx, wy, wz);

        return result;
    }

    /**
     * Return the relative linear velocity (body 2 minus body 1). The settings
     * are unaffected. (native attribute: mRelativeLinearSurfaceVelocity)
     *
     * @return a new velocity vector (meters per second in system coordinates)
     */
    @Override
    public Vec3 getRelativeLinearSurfaceVelocity() {
        long settingsVa = va();
        float vx = getRelativeLinearSurfaceVelocityX(settingsVa);
        float vy = getRelativeLinearSurfaceVelocityY(settingsVa);
        float vz = getRelativeLinearSurfaceVelocityZ(settingsVa);
        Vec3 result = new Vec3(vx, vy, vz);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createContactSettings();

    native private static void free(long settingsVa);

    native private static float getCombinedFriction(long settingsVa);

    native private static float getCombinedRestitution(long settingsVa);

    native private static float getInvInertiaScale1(long settingsVa);

    native private static float getInvInertiaScale2(long settingsVa);

    native private static float getInvMassScale1(long settingsVa);

    native private static float getInvMassScale2(long settingsVa);

    native private static boolean getIsSensor(long settingsVa);

    native private static float getRelativeAngularSurfaceVelocityX(
            long settingsVa);

    native private static float getRelativeAngularSurfaceVelocityY(
            long settingsVa);

    native private static float getRelativeAngularSurfaceVelocityZ(
            long settingsVa);

    native private static float getRelativeLinearSurfaceVelocityX(
            long settingsVa);

    native private static float getRelativeLinearSurfaceVelocityY(
            long settingsVa);

    native private static float getRelativeLinearSurfaceVelocityZ(
            long settingsVa);

    native private static void setCombinedFriction(
            long settingsVa, float friction);

    native private static void setCombinedRestitution(
            long settingsVa, float restitution);

    native private static void setInvInertiaScale1(
            long settingsVa, float scale);

    native private static void setInvInertiaScale2(
            long settingsVa, float scale);

    native private static void setInvMassScale1(long settingsVa, float scale);

    native private static void setInvMassScale2(long settingsVa, float scale);

    native private static void setIsSensor(long settingsVa, boolean setting);

    native private static void setRelativeAngularSurfaceVelocity(
            long settingsVa, float wx, float wy, float wz);

    native private static void setRelativeLinearSurfaceVelocity(
            long settingsVa, float vx, float vy, float vz);
}
