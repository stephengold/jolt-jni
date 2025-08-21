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

import com.github.stephengold.joltjni.readonly.ConstWheelSettings;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.template.Ref;
import com.github.stephengold.joltjni.template.RefTarget;
import java.nio.FloatBuffer;

/**
 * Settings used to construct a {@code Wheel}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class WheelSettings
        extends SerializableObject
        implements ConstWheelSettings, RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with no native object assigned.
     * <p>
     * This no-arg constructor was made explicit to avoid javadoc warnings from
     * JDK 18+.
     */
    WheelSettings() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Instantiate a {@code WheelSettings} given its virtual address.
     *
     * @param settingsVa the virtual address of the native object, or zero
     * @return a new JVM object, or {@code null} if the argument was zero
     */
    static WheelSettings newSettings(long settingsVa) {
        if (settingsVa == 0L) {
            return null;
        }

        long rttiVa = SerializableObject.getRtti(settingsVa);
        String typeName = Rtti.getName(rttiVa);
        WheelSettings result;
        switch (typeName) {
            case "WheelSettingsTV":
                result = new WheelSettingsTv(settingsVa);
                break;

            case "WheelSettingsWV":
                result = new WheelSettingsWv(settingsVa);
                break;

            default:
                throw new RuntimeException("typeName = " + typeName);
        }

        assert result instanceof SerializableObject;
        return result;
    }

    /**
     * Load settings from the specified binary stream.
     *
     * @param stream the stream to read from (not null)
     */
    public void restoreBinaryState(StreamIn stream) {
        long settingsVa = va();
        long streamVa = stream.va();
        restoreBinaryState(settingsVa, streamVa);
    }

    /**
     * Alter where to apply tire forces. (native attribute:
     * mEnableSuspensionForcePoint)
     *
     * @param enable {@code true} to apply at the configured suspension-force
     * point, {@code false} to apply at the wheel's point of contact
     * (default=false)
     * @return the modified settings, for chaining
     */
    public WheelSettings setEnableSuspensionForcePoint(boolean enable) {
        long settingsVa = va();
        setEnableSuspensionForcePoint(settingsVa, enable);

        return this;
    }

    /**
     * Relocate the attachment point. (native attribute: mPosition)
     *
     * @param position the location of the attachment point (in the body's local
     * coordinates, not null, unaffected, default=(0,0,0))
     * @return the modified settings, for chaining
     */
    public WheelSettings setPosition(Vec3Arg position) {
        long settingsVa = va();
        float x = position.getX();
        float y = position.getY();
        float z = position.getZ();
        setPosition(settingsVa, x, y, z);

        return this;
    }

    /**
     * Alter the radius of the wheel. (native attribute: mRadius)
     *
     * @param radius the desired radius (in meters, default=0.3)
     * @return the modified settings, for chaining
     */
    public WheelSettings setRadius(float radius) {
        long settingsVa = va();
        setRadius(settingsVa, radius);

        return this;
    }

    /**
     * Alter the steering axis (upward direction). (native attribute:
     * mSteeringAxis)
     *
     * @param direction the desired direction (not null, unaffected,
     * default=(0,1,0))
     * @return the modified settings, for chaining
     */
    public WheelSettings setSteeringAxis(Vec3Arg direction) {
        long settingsVa = va();
        float dx = direction.getX();
        float dy = direction.getY();
        float dz = direction.getZ();
        setSteeringAxis(settingsVa, dx, dy, dz);

        return this;
    }

    /**
     * Alter the downward direction of the suspension. (native attribute:
     * mSuspensionDirection)
     *
     * @param direction the desired direction (not null, unaffected,
     * default=(0,-1,0))
     * @return the modified settings, for chaining
     */
    public WheelSettings setSuspensionDirection(Vec3Arg direction) {
        long settingsVa = va();
        float dx = direction.getX();
        float dy = direction.getY();
        float dz = direction.getZ();
        setSuspensionDirection(settingsVa, dx, dy, dz);

        return this;
    }

    /**
     * Alter the location where tire forces will be applied if the force-point
     * option is enabled. (native attribute: mSuspensionForcePoint)
     *
     * @param location the desired location (in the body coordinates, not null,
     * unaffected, default=(0,0,0))
     * @return the modified settings, for chaining
     */
    public WheelSettings setSuspensionForcePoint(Vec3Arg location) {
        long settingsVa = va();
        float x = location.getX();
        float y = location.getY();
        float z = location.getZ();
        setSuspensionForcePoint(settingsVa, x, y, z);

        return this;
    }

    /**
     * Alter the maximum displacement from the attachment point. (native
     * attribute: mSuspensionMaxLength)
     *
     * @param length the desired limit (in meters, default=0.5)
     * @return the modified settings, for chaining
     */
    public WheelSettings setSuspensionMaxLength(float length) {
        long settingsVa = va();
        setSuspensionMaxLength(settingsVa, length);

        return this;
    }

    /**
     * Alter the minimum displacement from the attachment point. (native
     * attribute: mSuspensionMinLength)
     *
     * @param length the desired limit (in meters, default=0.3)
     * @return the modified settings, for chaining
     */
    public WheelSettings setSuspensionMinLength(float length) {
        long settingsVa = va();
        setSuspensionMinLength(settingsVa, length);

        return this;
    }

    /**
     * Alter the suspension preload length. (native attribute:
     * mSuspensionPreloadLength)
     *
     * @param length the desired offset (in meters, default=0)
     * @return the modified settings, for chaining
     */
    public WheelSettings setSuspensionPreloadLength(float length) {
        long settingsVa = va();
        setSuspensionPreloadLength(settingsVa, length);

        return this;
    }

    /**
     * Alter the forward direction when steering is neutral. (native attribute:
     * mWheelForward)
     *
     * @param direction the desired direction (not null, unaffected,
     * default=(0,0,1))
     * @return the modified settings, for chaining
     */
    public WheelSettings setWheelForward(Vec3Arg direction) {
        long settingsVa = va();
        float dx = direction.getX();
        float dy = direction.getY();
        float dz = direction.getZ();
        setWheelForward(settingsVa, dx, dy, dz);

        return this;
    }

    /**
     * Alter the "up" direction when steering is neutral. (native attribute:
     * mWheelUp)
     *
     * @param direction the desired direction (not null, unaffected,
     * default=(0,1,0))
     * @return the modified settings, for chaining
     */
    public WheelSettings setWheelUp(Vec3Arg direction) {
        long settingsVa = va();
        float dx = direction.getX();
        float dy = direction.getY();
        float dz = direction.getZ();
        setWheelUp(settingsVa, dx, dy, dz);

        return this;
    }

    /**
     * Alter the width of the wheel. (native attribute: mWidth)
     *
     * @param width the desired width (in meters, default=0.1)
     * @return the modified settings, for chaining
     */
    public WheelSettings setWidth(float width) {
        long settingsVa = va();
        setWidth(settingsVa, width);

        return this;
    }
    // *************************************************************************
    // ConstWheelSettings methods

    /**
     * Determine where to apply tire forces. The settings are unaffected.
     * (native attribute: mEnableSuspensionForcePoint)
     *
     * @return {@code true} if applied at the configured suspension-force point,
     * {@code false} if applied at the wheel's point of contact
     */
    @Override
    public boolean getEnableSuspensionForcePoint() {
        long settingsVa = va();
        boolean result = getEnableSuspensionForcePoint(settingsVa);

        return result;
    }

    /**
     * Copy the location of the attachment point. The settings are unaffected.
     * (native attribute: mPosition)
     *
     * @return a new location vector (in the body's local coordinates)
     */
    @Override
    public Vec3 getPosition() {
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getPosition(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Return the radius of the wheel. The settings are unaffected. (native
     * attribute: mRadius)
     *
     * @return the radius (in meters)
     */
    @Override
    public float getRadius() {
        long settingsVa = va();
        float result = getRadius(settingsVa);

        return result;
    }

    /**
     * Copy the steering axis (upward direction). The settings are unaffected.
     * (native attribute: mSteeringAxis)
     *
     * @return a new direction vector
     */
    @Override
    public Vec3 getSteeringAxis() {
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getSteeringAxis(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the downward direction of the suspension. The settings are
     * unaffected. (native attribute: mSuspensionDirection)
     *
     * @return a new direction vector
     */
    @Override
    public Vec3 getSuspensionDirection() {
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getSuspensionDirection(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the location where tire forces will be applied if the force-point
     * option is enabled. The settings are unaffected. (native attribute:
     * mSuspensionForcePoint)
     *
     * @return a new location vector (in body coordinates)
     */
    @Override
    public Vec3 getSuspensionForcePoint() {
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getSuspensionForcePoint(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Return the maximum displacement from the attachment point. The settings
     * are unaffected. (native attribute: mSuspensionMaxLength)
     *
     * @return the distance (in meters)
     */
    @Override
    public float getSuspensionMaxLength() {
        long settingsVa = va();
        float result = getSuspensionMaxLength(settingsVa);

        return result;
    }

    /**
     * Return the minimum displacement from the attachment point. The settings
     * are unaffected. (native attribute: mSuspensionMinLength)
     *
     * @return the distance (in meters)
     */
    @Override
    public float getSuspensionMinLength() {
        long settingsVa = va();
        float result = getSuspensionMinLength(settingsVa);

        return result;
    }

    /**
     * Return the suspension preload length. The settings are unaffected.
     * (native attribute: mSuspensionPreloadLength)
     *
     * @return the offset (in meters)
     */
    @Override
    public float getSuspensionPreloadLength() {
        long settingsVa = va();
        float result = getSuspensionPreloadLength(settingsVa);

        return result;
    }

    /**
     * Access the settings for the suspension spring. (native attribute:
     * mSuspensionSpring)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public SpringSettings getSuspensionSpring() {
        long wheelSettingsVa = va();
        long springSettingsVa = getSuspensionSpring(wheelSettingsVa);
        SpringSettings result = new SpringSettings(this, springSettingsVa);

        return result;
    }

    /**
     * Copy the forward direction when steering is neutral. The settings are
     * unaffected. (native attribute: mWheelForward)
     *
     * @return a new direction vector
     */
    @Override
    public Vec3 getWheelForward() {
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getWheelForward(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the "up" direction when steering is neutral. The settings are
     * unaffected. (native attribute: mWheelUp)
     *
     * @return a new direction vector
     */
    @Override
    public Vec3 getWheelUp() {
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getWheelUp(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Return the width of the wheel. The settings are unaffected. (native
     * attribute: mWidth)
     *
     * @return the width (in meters)
     */
    @Override
    public float getWidth() {
        long settingsVa = va();
        float result = getWidth(settingsVa);

        return result;
    }

    /**
     * Save the settings to the specified binary stream. The settings are
     * unaffected.
     *
     * @param stream the stream to write to (not null)
     */
    @Override
    public void saveBinaryState(StreamOut stream) {
        long settingsVa = va();
        long streamVa = stream.va();
        saveBinaryState(settingsVa, streamVa);
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code WheelSettings}. The
     * settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    abstract public int getRefCount();

    /**
     * Mark the native {@code WheelSettings} as embedded.
     */
    @Override
    abstract public void setEmbedded();

    /**
     * Create a counted reference to the native {@code WheelSettings}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    abstract public Ref toRef();
    // *************************************************************************
    // native methods

    native static boolean getEnableSuspensionForcePoint(long settingsVa);

    native static void getPosition(long settingsVa, FloatBuffer storeFloats);

    native static float getRadius(long settingsVa);

    native static void getSteeringAxis(
            long settingsVa, FloatBuffer storeFloats);

    native static void getSuspensionDirection(
            long settingsVa, FloatBuffer storeFloats);

    native static void getSuspensionForcePoint(
            long settingsVa, FloatBuffer storeFloats);

    native static float getSuspensionMaxLength(long settingsVa);

    native static float getSuspensionMinLength(long settingsVa);

    native static float getSuspensionPreloadLength(long settingsVa);

    native static long getSuspensionSpring(long wheelSettingsVa);

    native static void getWheelForward(
            long settingsVa, FloatBuffer storeFloats);

    native static void getWheelUp(long settingsVa, FloatBuffer storeFloats);

    native static float getWidth(long settingsVa);

    native private static void restoreBinaryState(
            long settingsVa, long streamVa);

    native static void saveBinaryState(long settingsVa, long streamVa);

    native static void setEnableSuspensionForcePoint(
            long settingsVa, boolean enable);

    native static void setPosition(
            long settingsVa, float x, float y, float z);

    native static void setRadius(long settingsVa, float radius);

    native static void setSteeringAxis(
            long settingsVa, float dx, float dy, float dz);

    native static void setSuspensionDirection(
            long settingsVa, float dx, float dy, float dz);

    native static void setSuspensionForcePoint(
            long settingsVa, float x, float y, float z);

    native static void setSuspensionMaxLength(long settingsVa, float length);

    native static void setSuspensionMinLength(long settingsVa, float length);

    native static void setSuspensionPreloadLength(
            long settingsVa, float length);

    native static void setWheelForward(
            long settingsVa, float dx, float dy, float dz);

    native static void setWheelUp(
            long settingsVa, float dx, float dy, float dz);

    native static void setWidth(long settingsVa, float width);
}
