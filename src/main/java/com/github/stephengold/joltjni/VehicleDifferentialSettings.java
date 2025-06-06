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
 * Settings used to configure the differential of a
 * {@code WheeledVehicleController}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class VehicleDifferentialSettings extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public VehicleDifferentialSettings() {
        long settingsVa = createDefault();
        setVirtualAddress(settingsVa, () -> free(settingsVa));
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    VehicleDifferentialSettings(JoltPhysicsObject container, long settingsVa) {
        super(container, settingsVa);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public VehicleDifferentialSettings(VehicleDifferentialSettings original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa, () -> free(copyVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the number of gearbox rotations per wheel rotation. The settings
     * are unaffected. (native attribute: mDifferentialRatio)
     *
     * @return the ratio
     */
    public float getDifferentialRatio() {
        long settingsVa = va();
        float result = getDifferentialRatio(settingsVa);

        return result;
    }

    /**
     * Return the fraction of the engine's torque that is applied to this
     * differential. The settings are unaffected. (native attribute:
     * mEngineTorqueRatio)
     *
     * @return the fraction
     */
    public float getEngineTorqueRatio() {
        long settingsVa = va();
        float result = getEngineTorqueRatio(settingsVa);

        return result;
    }

    /**
     * Return the index of the left wheel. The settings are unaffected. (native
     * attribute: mLeftWheel)
     *
     * @return the index of the wheel (&ge;0) or -1 for none
     */
    public int getLeftWheel() {
        long settingsVa = va();
        int result = getLeftWheel(settingsVa);

        return result;
    }

    /**
     * Return the ratio of maximum wheel speed to minimum wheel speed. The
     * settings are unaffected. (native member: mLimitedSlipRatio)
     *
     * @return the ratio (&gt;1, {@code FLT_MAX} for an open differential)
     */
    public float getLimitedSlipRatio() {
        long settingsVa = va();
        float result = getLimitedSlipRatio(settingsVa);

        return result;
    }

    /**
     * Return the index of the right wheel. The settings are unaffected. (native
     * attribute: mRightWheel)
     *
     * @return the index of the wheel (&ge;0) or -1 for none
     */
    public int getRightWheel() {
        long settingsVa = va();
        int result = getRightWheel(settingsVa);

        return result;
    }

    /**
     * Alter the number of gearbox rotations per wheel rotation. (native
     * attribute: mDifferentialRatio)
     *
     * @param ratio the desired ratio (default=3.42)
     */
    public void setDifferentialRatio(float ratio) {
        long settingsVa = va();
        setDifferentialRatio(settingsVa, ratio);
    }

    /**
     * Alter the fraction of the engine's torque that is applied to this
     * differential. (native attribute: mEngineTorqueRatio)
     *
     * @param fraction the desired ratio (default=1)
     * @return the fraction, for chaining
     */
    public float setEngineTorqueRatio(float fraction) {
        long settingsVa = va();
        setEngineTorqueRatio(settingsVa, fraction);

        return fraction;
    }

    /**
     * Alter which left wheel is assigned to the differential. (native
     * attribute: mLeftWheel)
     *
     * @param wheelIndex the index of the wheel (&ge;0) or -1 for none
     * (default=-1)
     */
    public void setLeftWheel(int wheelIndex) {
        long settingsVa = va();
        setLeftWheel(settingsVa, wheelIndex);
    }

    /**
     * Alter the ratio of maximum wheel speed to minimum wheel speed. When this
     * ratio is exceeded, all torque gets distributed to the slowest wheel.
     * (native member: mLimitedSlipRatio)
     *
     * @param ratio the desired ratio (&gt;1, {@code FLT_MAX} for an open
     * differential, default=1.4)
     */
    public void setLimitedSlipRatio(float ratio) {
        long settingsVa = va();
        setLimitedSlipRatio(settingsVa, ratio);
    }

    /**
     * Alter which right wheel is assigned to the differential. (native
     * attribute: mRightWheel)
     *
     * @param wheelIndex the index of the wheel (&ge;0) or -1 for none
     * (default=-1)
     */
    public void setRightWheel(int wheelIndex) {
        long settingsVa = va();
        setRightWheel(settingsVa, wheelIndex);
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static void free(long settingsVa);

    native private static float getDifferentialRatio(long settingsVa);

    native private static float getEngineTorqueRatio(long settingsVa);

    native private static int getLeftWheel(long settingsVa);

    native private static float getLimitedSlipRatio(long settingsVa);

    native private static int getRightWheel(long settingsVa);

    native private static void setDifferentialRatio(
            long settingsVa, float ratio);

    native private static void setEngineTorqueRatio(
            long settingsVa, float fraction);

    native private static void setLeftWheel(long settingsVa, int wheelIndex);

    native private static void setLimitedSlipRatio(
            long settingsVa, float ratio);

    native private static void setRightWheel(long settingsVa, int wheelIndex);
}
