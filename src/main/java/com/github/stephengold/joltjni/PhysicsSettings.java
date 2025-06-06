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
 * A component of a {@code PhysicsSystem}, used to configure simulation
 * parameters.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class PhysicsSettings extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate the default settings.
     */
    public PhysicsSettings() {
        long settingsVa = createPhysicsSettings();
        setVirtualAddress(settingsVa, () -> free(settingsVa));
    }

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    PhysicsSettings(long settingsVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(settingsVa) : null;
        setVirtualAddress(settingsVa, freeingAction);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public PhysicsSettings(PhysicsSettings original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa, () -> free(copyVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether objects can fall asleep. The settings are unaffected.
     * (native attribute: mAllowSleeping)
     *
     * @return {@code true} if sleeping is allowed, otherwise {@code false}
     */
    public boolean getAllowSleeping() {
        long settingsVa = va();
        boolean result = getAllowSleeping(settingsVa);

        return result;
    }

    /**
     * Return the Baumgarte stabilization factor, the fraction of position error
     * that is corrected in each update. The settings are unaffected. (native
     * attribute: mBaumgarte)
     *
     * @return the factor (&ge;0, &le;1)
     */
    public float getBaumgarte() {
        long settingsVa = va();
        float result = getBaumgarte(settingsVa);

        assert result >= 0f && result <= 1f : result;
        return result;
    }

    /**
     * Test whether physics simulation is deterministic. The settings are
     * unaffected. (native attribute: mDeterministicSimulation)
     *
     * @return {@code true} if it is deterministic, otherwise {@code false}
     */
    public boolean getDeterministicSimulation() {
        long settingsVa = va();
        boolean result = getDeterministicSimulation(settingsVa);

        return result;
    }

    /**
     * Return the number of solver position iterations per simulation step. The
     * settings are unaffected. (native attribute: mNumPositionSteps)
     *
     * @return the number (&ge;0)
     */
    public int getNumPositionSteps() {
        long settingsVa = va();
        int result = getNumPositionSteps(settingsVa);

        assert result >= 0 : result;
        return result;
    }

    /**
     * Return the number of velocity iterations per simulation step. The
     * settings are unaffected. (native attribute: mNumVelocitySteps)
     *
     * @return the number (&ge;0)
     */
    public int getNumVelocitySteps() {
        long settingsVa = va();
        int result = getNumVelocitySteps(settingsVa);

        assert result >= 0 : result;
        return result;
    }

    /**
     * Return the penetration slop. The settings are unaffected. (native
     * attribute: mPenetrationSlop)
     *
     * @return the slop distance (in meters)
     */
    public float getPenetrationSlop() {
        long settingsVa = va();
        float result = getPenetrationSlop(settingsVa);

        return result;
    }

    /**
     * Return the point-motion threshold, below which objects can fall asleep.
     * The settings are unaffected. (native attribute:
     * mPointVelocitySleepThreshold)
     *
     * @return the speed threshold (in meters per second, &ge;0)
     */
    public float getPointVelocitySleepThreshold() {
        long settingsVa = va();
        float result = getPointVelocitySleepThreshold(settingsVa);

        assert result >= 0f : result;
        return result;
    }

    /**
     * Alter the time interval before an object can fall asleep. The settings
     * are unaffected. (native attribute: mTimeBeforeSleep)
     *
     * @return the interval (in seconds, &ge;0)
     */
    public float getTimeBeforeSleep() {
        long settingsVa = va();
        float result = getTimeBeforeSleep(settingsVa);

        assert result >= 0f : result;
        return result;
    }

    /**
     * Alter whether objects can go to sleep. (native attribute: mAllowSleeping)
     *
     * @param allow {@code true} to allow sleeping, {@code false} to disallow it
     * (default=true)
     */
    public void setAllowSleeping(boolean allow) {
        long settingsVa = va();
        setAllowSleeping(settingsVa, allow);
    }

    /**
     * Alter the Baumgarte stabilization factor, the fraction of position error
     * to correct in each update. (native attribute: mBaumgarte)
     *
     * @param fraction the desired factor (&ge;0, &le;1, default=0.2)
     */
    public void setBaumgarte(float fraction) {
        long settingsVa = va();
        setBaumgarte(settingsVa, fraction);
    }

    /**
     * Alter whether physics simulation is deterministic. (native attribute:
     * mDeterministicSimulation)
     *
     * @param setting {@code true} to be deterministic, {@code false} to relax
     * this policy (default=true)
     */
    public void setDeterministicSimulation(boolean setting) {
        long settingsVa = va();
        setDeterministicSimulation(settingsVa, setting);
    }

    /**
     * Alter the number of solver position iterations per simulation step.
     * (native attribute: mNumPositionSteps)
     *
     * @param numSteps the desired number (&ge;0, default=2)
     */
    public void setNumPositionSteps(int numSteps) {
        long settingsVa = va();
        setNumPositionSteps(settingsVa, numSteps);
    }

    /**
     * Alter the number of velocity steps. (native attribute: mNumVelocitySteps)
     *
     * @param numSteps the desired number (&ge;0, default=10)
     */
    public void setNumVelocitySteps(int numSteps) {
        long settingsVa = va();
        setNumVelocitySteps(settingsVa, numSteps);
    }

    /**
     * Alter the penetration slop. (native attribute: mPenetrationSlop)
     *
     * @param slop the desired slop distance (in meters, default=0.02)
     */
    public void setPenetrationSlop(float slop) {
        long settingsVa = va();
        setPenetrationSlop(settingsVa, slop);
    }

    /**
     * Alter the point-motion threshold, below which an object can fall asleep.
     * (native attribute: mPointVelocitySleepThreshold)
     *
     * @param speed the desired speed threshold (in meters per second, &ge;0,
     * default=0.03)
     */
    public void setPointVelocitySleepThreshold(float speed) {
        long settingsVa = va();
        setPointVelocitySleepThreshold(settingsVa, speed);
    }

    /**
     * Alter the time interval before an object can fall asleep. (native
     * attribute: mTimeBeforeSleep)
     *
     * @param interval the desired time interval (in seconds, &ge;0,
     * default=0.5)
     */
    public void setTimeBeforeSleep(float interval) {
        long settingsVa = va();
        setTimeBeforeSleep(settingsVa, interval);
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createPhysicsSettings();

    native private static void free(long settingsVa);

    native private static boolean getAllowSleeping(long settingsVa);

    native private static float getBaumgarte(long settingsVa);

    native private static boolean getDeterministicSimulation(
            long settingsVa);

    native private static int getNumPositionSteps(long settingsVa);

    native private static int getNumVelocitySteps(long settingsVa);

    native private static float getPenetrationSlop(long settingsVa);

    native private static float getPointVelocitySleepThreshold(
            long settingsVa);

    native private static float getTimeBeforeSleep(long settingsVa);

    native private static void setAllowSleeping(
            long settingsVa, boolean allow);

    native private static void setBaumgarte(
            long settingsVa, float fraction);

    native private static void setDeterministicSimulation(
            long settingsVa, boolean setting);

    native private static void setNumPositionSteps(
            long settingsVa, int numSteps);

    native private static void setNumVelocitySteps(
            long settingsVa, int numSteps);

    native private static void setPenetrationSlop(
            long settingsVa, float slop);

    native private static void setPointVelocitySleepThreshold(
            long settingsVa, float speed);

    native private static void setTimeBeforeSleep(
            long settingsVa, float interval);
}
