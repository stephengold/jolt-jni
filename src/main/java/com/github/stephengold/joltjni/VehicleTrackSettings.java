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

/**
 * Settings to configure one track in a {@code TrackedVehicleController}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class VehicleTrackSettings extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    VehicleTrackSettings(JoltPhysicsObject container, long settingsVa) {
        super(container, settingsVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Add the specified wheel to the track.
     *
     * @param wheelIndex the index (among the vehicle's wheels) of the wheel to
     * add (&ge;0)
     */
    public void addWheel(int wheelIndex) {
        long settingsVa = va();
        addWheel(settingsVa, wheelIndex);
    }

    /**
     * Return the angular damping factor. The settings are unaffected. (native
     * attribute: mAngularDamping)
     *
     * @return the damping factor (per second)
     */
    public float getAngularDamping() {
        long settingsVa = va();
        float result = getAngularDamping(settingsVa);

        return result;
    }

    /**
     * Return the number of gearbox rotations per driven-wheel rotation. The
     * settings are unaffected. (native attribute: mDifferentialRatio)
     *
     * @return the ratio
     */
    public float getDifferentialRatio() {
        long settingsVa = va();
        float result = getDifferentialRatio(settingsVa);

        return result;
    }

    /**
     * Return the index of the wheel powered by the engine. The settings are
     * unaffected. (native attribute: mDrivenWheel)
     *
     * @return the index of the driven wheel (&ge;0)
     */
    public int getDrivenWheel() {
        long settingsVa = va();
        int result = getDrivenWheel(settingsVa);

        return result;
    }

    /**
     * Return the moment of inertia of the track and its wheels as seen on the
     * driven wheel. The settings are unaffected. (native attribute: mInertia)
     *
     * @return the moment (in kilogram.meter^2)
     */
    public float getInertia() {
        long settingsVa = va();
        float result = getInertia(settingsVa);

        return result;
    }

    /**
     * Count the track's wheels. The settings are unaffected. (native attribute:
     * mWheels)
     *
     * @return the count (&ge;0)
     */
    public int getNumWheels() {
        long settingsVa = va();
        int result = getNumWheels(settingsVa);

        return result;
    }

    /**
     * Alter which wheel is powered by the engine. (native attribute:
     * mDrivenWheel)
     *
     * @param wheelIndex the index of the desired wheel (&ge;0, default=0)
     */
    public void setDrivenWheel(int wheelIndex) {
        long settingsVa = va();
        setDrivenWheel(settingsVa, wheelIndex);
    }
    // *************************************************************************
    // native private methods

    native private static void addWheel(long settingsVa, int wheelIndex);

    native private static float getAngularDamping(long settingsVa);

    native private static float getDifferentialRatio(long settingsVa);

    native private static int getDrivenWheel(long settingsVa);

    native private static float getInertia(long settingsVa);

    native private static int getNumWheels(long settingsVa);

    native private static void setDrivenWheel(long settingsVa, int wheelIndex);
}
