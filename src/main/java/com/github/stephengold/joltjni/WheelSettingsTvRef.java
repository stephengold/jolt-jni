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

import com.github.stephengold.joltjni.readonly.ConstWheelSettingsTv;
import com.github.stephengold.joltjni.template.Ref;
import java.nio.FloatBuffer;

/**
 * A counted reference to a {@code WheelSettingsTv} object. (native type:
 * {@code Ref<WheelSettingsTV>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class WheelSettingsTvRef
        extends Ref
        implements ConstWheelSettingsTv {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public WheelSettingsTvRef() {
        long refVa = createDefault();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    WheelSettingsTvRef(long refVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(refVa) : null;
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // ConstWheelSettingsTv methods

    /**
     * Determine where to apply tire forces. The settings are unaffected.
     * (native attribute: mEnableSuspensionForcePoint)
     *
     * @return {@code true} if applied at the configured suspension-force point,
     * {@code false} if applied at the wheel's point of contact
     */
    @Override
    public boolean getEnableSuspensionForcePoint() {
        long settingsVa = targetVa();
        boolean result
                = WheelSettings.getEnableSuspensionForcePoint(settingsVa);

        return result;
    }

    /**
     * Return the tire's friction in the sideways direction. The settings are
     * unaffected. (native attribute: mLateralFriction)
     *
     * @return the friction
     */
    @Override
    public float getLateralFriction() {
        long settingsVa = targetVa();
        float result = WheelSettingsTv.getLateralFriction(settingsVa);

        return result;
    }

    /**
     * Return the tire's friction in the forward direction. The settings are
     * unaffected. (native attribute: mLongitudinalFriction)
     *
     * @return the friction
     */
    @Override
    public float getLongitudinalFriction() {
        long settingsVa = targetVa();
        float result = WheelSettingsTv.getLongitudinalFriction(settingsVa);

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
        long settingsVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        WheelSettings.getPosition(settingsVa, storeFloats);
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
        long settingsVa = targetVa();
        float result = WheelSettings.getRadius(settingsVa);

        return result;
    }

    /**
     * Access the type information of the target. (native method: getRTTI)
     *
     * @return a new object
     */
    @Override
    public Rtti getRtti() {
        long jpoVa = targetVa();
        long resultVa = SerializableObject.getRtti(jpoVa);
        Rtti result = new Rtti(resultVa);

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
        long settingsVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        WheelSettings.getSteeringAxis(settingsVa, storeFloats);
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
        long settingsVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        WheelSettings.getSuspensionDirection(settingsVa, storeFloats);
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
        long settingsVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        WheelSettings.getSuspensionForcePoint(settingsVa, storeFloats);
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
        long settingsVa = targetVa();
        float result = WheelSettings.getSuspensionMaxLength(settingsVa);

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
        long settingsVa = targetVa();
        float result = WheelSettings.getSuspensionMinLength(settingsVa);

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
        long settingsVa = targetVa();
        float result = WheelSettings.getSuspensionPreloadLength(settingsVa);

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
        long wheelSettingsVa = targetVa();
        long springSettingsVa
                = WheelSettings.getSuspensionSpring(wheelSettingsVa);
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
        long settingsVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        WheelSettings.getWheelForward(settingsVa, storeFloats);
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
        long settingsVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        WheelSettings.getWheelUp(settingsVa, storeFloats);
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
        long settingsVa = targetVa();
        float result = WheelSettings.getWidth(settingsVa);

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
        long settingsVa = targetVa();
        long streamVa = stream.va();
        WheelSettings.saveBinaryState(settingsVa, streamVa);
    }
    // *************************************************************************
    // Ref methods

    /**
     * Temporarily access the referenced {@code WheelSettingsTv}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public WheelSettingsTv getPtr() {
        long settingsVa = targetVa();
        WheelSettingsTv result = new WheelSettingsTv(settingsVa);

        return result;
    }

    /**
     * Return the address of the native {@code WheelSettingsTV}. No objects are
     * affected.
     *
     * @return a virtual address (not zero)
     */
    @Override
    public long targetVa() {
        long refVa = va();
        long result = getPtr(refVa);

        return result;
    }

    /**
     * Create another counted reference to the native {@code WheelSettingsTV}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public WheelSettingsTvRef toRef() {
        long refVa = va();
        long copyVa = copy(refVa);
        WheelSettingsTvRef result = new WheelSettingsTvRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long copy(long refVa);

    native private static long createDefault();

    native private static void free(long refVa);

    native private static long getPtr(long refVa);
}
