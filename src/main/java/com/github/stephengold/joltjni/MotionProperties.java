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

/**
 * Additional state for a {@code Body} that moves.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class MotionProperties extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    MotionProperties(long propertiesVa) {
        super(propertiesVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the angular damping coefficient.
     *
     * @return the coefficient value
     */
    public float getAngularDamping() {
        long propertiesVa = va();
        float result = getAngularDamping(propertiesVa);

        return result;
    }

    /**
     * Return the angular velocity.
     *
     * @return a new vector in physics-system coordinates
     */
    public Vec3 getAngularVelocity() {
        long propertiesVa = va();
        float wx = getAngularVelocityX(propertiesVa);
        float wy = getAngularVelocityY(propertiesVa);
        float wz = getAngularVelocityZ(propertiesVa);
        Vec3 result = new Vec3(wx, wy, wz);

        return result;
    }

    /**
     * Return the gravity factor.
     *
     * @return the factor
     */
    public float getGravityFactor() {
        long propertiesVa = va();
        float result = getGravityFactor(propertiesVa);

        return result;
    }

    /**
     * Return the linear damping coefficient.
     *
     * @return the coefficient value
     */
    public float getLinearDamping() {
        long propertiesVa = va();
        float result = getLinearDamping(propertiesVa);

        return result;
    }

    /**
     * Return the linear velocity.
     *
     * @return a new vector in physics-system coordinates
     */
    public Vec3 getLinearVelocity() {
        long propertiesVa = va();
        float vx = getLinearVelocityX(propertiesVa);
        float vy = getLinearVelocityY(propertiesVa);
        float vz = getLinearVelocityZ(propertiesVa);
        Vec3 result = new Vec3(vx, vy, vz);

        return result;
    }

    /**
     * Return the motion quality.
     *
     * @return an enum value (not null)
     */
    public EMotionQuality getMotionQuality() {
        long propertiesVa = va();
        int ordinal = getMotionQuality(propertiesVa);
        EMotionQuality result = EMotionQuality.values()[ordinal];

        return result;
    }

    /**
     * Alter the angular damping.
     *
     * @param damping the desired coefficient value
     */
    public void setAngularDamping(float damping) {
        long propertiesVa = va();
        setAngularDamping(propertiesVa, damping);
    }

    /**
     * Alter the angular velocity.
     *
     * @param omega the desired velocity (in physics-system coordinates)
     */
    public void setAngularVelocity(Vec3Arg omega) {
        long propertiesVa = va();
        float wx = omega.getX();
        float wy = omega.getY();
        float wz = omega.getZ();
        setAngularVelocity(propertiesVa, wx, wy, wz);
    }

    /**
     * Alter the gravity factor.
     *
     * @param factor the desired factor
     */
    public void setGravityFactor(float factor) {
        long propertiesVa = va();
        setGravityFactor(propertiesVa, factor);
    }

    /**
     * Alter the linear damping.
     *
     * @param damping the desired coefficient value
     */
    public void setLinearDamping(float damping) {
        long propertiesVa = va();
        setLinearDamping(propertiesVa, damping);
    }

    /**
     * Alter the linear velocity.
     *
     * @param velocity the desired velocity (in physics-system coordinates)
     */
    public void setLinearVelocity(Vec3Arg velocity) {
        long propertiesVa = va();
        float vx = velocity.getX();
        float vy = velocity.getY();
        float vz = velocity.getZ();
        setLinearVelocity(propertiesVa, vx, vy, vz);
    }
    // *************************************************************************
    // native private methods

    native private static float getAngularDamping(long propertiesVa);

    native private static float getAngularVelocityX(long propertiesVa);

    native private static float getAngularVelocityY(long propertiesVa);

    native private static float getAngularVelocityZ(long propertiesVa);

    native private static float getGravityFactor(long propertiesVa);

    native private static float getLinearDamping(long propertiesVa);

    native private static float getLinearVelocityX(long propertiesVa);

    native private static float getLinearVelocityY(long propertiesVa);

    native private static float getLinearVelocityZ(long propertiesVa);

    native private static int getMotionQuality(long propertiesVa);

    native private static void setAngularDamping(
            long propertiesVa, float damping);

    native private static void setAngularVelocity(
            long propertiesVa, float wx, float wy, float wz);

    native private static void setGravityFactor(
            long propertiesVa, float factor);

    native private static void setLinearDamping(
            long propertiesVa, float damping);

    native private static void setLinearVelocity(
            long propertiesVa, float vx, float vy, float vz);
}
