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

import com.github.stephengold.joltjni.enumerate.EConstraintSpace;
import com.github.stephengold.joltjni.enumerate.EConstraintSubType;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

/**
 * Settings used to construct a {@code ConeConstraint}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ConeConstraintSettings extends TwoBodyConstraintSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public ConeConstraintSettings() {
        long settingsVa = createDefault();
        setVirtualAddressAsCoOwner(settingsVa, EConstraintSubType.Cone);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public ConeConstraintSettings(ConeConstraintSettings original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        setVirtualAddressAsCoOwner(copyVa, EConstraintSubType.Cone);
    }

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    ConeConstraintSettings(long settingsVa) {
        setVirtualAddressAsCoOwner(settingsVa, EConstraintSubType.Cone);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return one-half the maximum angle between the twist axes of body 1 and
     * body 2. The settings are unaffected. (native attribute: mHalfConeAngle)
     *
     * @return the half angle (in radians)
     */
    public float getHalfConeAngle() {
        long settingsVa = va();
        float result = getHalfConeAngle(settingsVa);

        return result;
    }

    /**
     * Copy the constraint location for body 1. The settings are unaffected.
     * (native attribute: mPoint1)
     *
     * @return a new location vector
     */
    public RVec3 getPoint1() {
        long settingsVa = va();
        DoubleBuffer storeDoubles = Temporaries.doubleBuffer1.get();
        getPoint1(settingsVa, storeDoubles);
        RVec3 result = new RVec3(storeDoubles);

        return result;
    }

    /**
     * Copy the constraint location for body 2. The settings are unaffected.
     * (native attribute: mPoint2)
     *
     * @return a new location vector
     */
    public RVec3 getPoint2() {
        long settingsVa = va();
        DoubleBuffer storeDoubles = Temporaries.doubleBuffer1.get();
        getPoint2(settingsVa, storeDoubles);
        RVec3 result = new RVec3(storeDoubles);

        return result;
    }

    /**
     * Return which space the other properties are specified in. The settings
     * are unaffected. (native attribute: mSpace)
     *
     * @return an enum value (not null)
     */
    public EConstraintSpace getSpace() {
        long settingsVa = va();
        int ordinal = getSpace(settingsVa);
        EConstraintSpace result = EConstraintSpace.values()[ordinal];

        return result;
    }

    /**
     * Copy the twist axis of body 1. The settings are unaffected. (native
     * attribute: mTwistAxis1)
     *
     * @return a new direction vector
     */
    public Vec3 getTwistAxis1() {
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getTwistAxis1(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the twist axis of body 2. The settings are unaffected. (native
     * attribute: mTwistAxis2)
     *
     * @return a new direction vector
     */
    public Vec3 getTwistAxis2() {
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getTwistAxis2(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Alter the maximum angle between the twist axes of body 1 and body 2.
     * (native attribute: mHalfConeAngle)
     *
     * @param halfAngle the desired half angle (in radians, default=0)
     */
    public void setHalfConeAngle(float halfAngle) {
        long settingsVa = va();
        setHalfConeAngle(settingsVa, halfAngle);
    }

    /**
     * Alter the constraint location for body 1. (native attribute: mPoint1)
     *
     * @param location the desired location (not null, unaffected,
     * default=(0,0,0))
     * @return the argument, for chaining
     */
    public RVec3Arg setPoint1(RVec3Arg location) {
        long settingsVa = va();
        double x = location.xx();
        double y = location.yy();
        double z = location.zz();
        setPoint1(settingsVa, x, y, z);

        return location;
    }

    /**
     * Alter the constraint location for body 2. (native attribute: mPoint2)
     *
     * @param location the desired location (not null, unaffected,
     * default=(0,0,0))
     * @return the argument, for chaining
     */
    public RVec3Arg setPoint2(RVec3Arg location) {
        long settingsVa = va();
        double x = location.xx();
        double y = location.yy();
        double z = location.zz();
        setPoint2(settingsVa, x, y, z);

        return location;
    }

    /**
     * Alter which space the other properties are specified in. (native
     * attribute: mSpace)
     *
     * @param space enum value (not null, default=WorldSpace)
     */
    public void setSpace(EConstraintSpace space) {
        long settingsVa = va();
        int ordinal = space.ordinal();
        setSpace(settingsVa, ordinal);
    }

    /**
     * Alter the twist axis of body 1. (native attribute: mTwistAxis1)
     *
     * @param direction the desired axis direction (not null, unaffected,
     * default=(1,0,0))
     * @return the argument, for chaining
     */
    public Vec3Arg setTwistAxis1(Vec3Arg direction) {
        long settingsVa = va();
        float x = direction.getX();
        float y = direction.getY();
        float z = direction.getZ();
        setTwistAxis1(settingsVa, x, y, z);

        return direction;
    }

    /**
     * Alter the twist axis of body 2. (native attribute: mTwistAxis2)
     *
     * @param direction the desired axis direction (not null, unaffected,
     * default=(1,0,0))
     * @return the argument, for chaining
     */
    public Vec3Arg setTwistAxis2(Vec3Arg direction) {
        long settingsVa = va();
        float x = direction.getX();
        float y = direction.getY();
        float z = direction.getZ();
        setTwistAxis2(settingsVa, x, y, z);

        return direction;
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static float getHalfConeAngle(long settingsVa);

    native private static void getPoint1(
            long settingsVa, DoubleBuffer storeDoubles);

    native private static void getPoint2(
            long settingsVa, DoubleBuffer storeDoubles);

    native private static int getSpace(long settingsVa);

    native private static void getTwistAxis1(
            long settingsVa, FloatBuffer storeFloats);

    native private static void getTwistAxis2(
            long settingsVa, FloatBuffer storeFloats);

    native private static void setHalfConeAngle(long settingsVa, float angle);

    native private static void setPoint1(
            long settingsVa, double x, double y, double z);

    native private static void setPoint2(
            long settingsVa, double x, double y, double z);

    native private static void setSpace(long settingsVa, int ordinal);

    native private static void setTwistAxis1(
            long settingsVa, float x, float y, float z);

    native private static void setTwistAxis2(
            long settingsVa, float x, float y, float z);
}
