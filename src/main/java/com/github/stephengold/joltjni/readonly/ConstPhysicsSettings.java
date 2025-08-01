/*
Copyright (c) 2025 Stephen Gold

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
package com.github.stephengold.joltjni.readonly;

/**
 * Read-only access to a {@code PhysicsSettings} object. (native type: const
 * PhysicsSettings)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstPhysicsSettings extends ConstJoltPhysicsObject {
    /**
     * Test whether objects can fall asleep. The settings are unaffected.
     *
     * @return {@code true} if sleeping is allowed, otherwise {@code false}
     */
    boolean getAllowSleeping();

    /**
     * Return the Baumgarte stabilization factor, the fraction of position error
     * that is corrected in each update. The settings are unaffected.
     *
     * @return the factor (&ge;0, &le;1)
     */
    float getBaumgarte();

    /**
     * Test whether physics simulation is deterministic. The settings are
     * unaffected.
     *
     * @return {@code true} if it is deterministic, otherwise {@code false}
     */
    boolean getDeterministicSimulation();

    /**
     * Return the number of solver position iterations per simulation step. The
     * settings are unaffected.
     *
     * @return the number (&ge;0)
     */
    int getNumPositionSteps();

    /**
     * Return the number of velocity iterations per simulation step. The
     * settings are unaffected.
     *
     * @return the number (&ge;0)
     */
    int getNumVelocitySteps();

    /**
     * Return the penetration slop. The settings are unaffected.
     *
     * @return the slop distance (in meters)
     */
    float getPenetrationSlop();

    /**
     * Return the point-motion threshold, below which objects can fall asleep.
     * The settings are unaffected.
     *
     * @return the speed threshold (in meters per second, &ge;0)
     */
    float getPointVelocitySleepThreshold();

    /**
     * Return the speculative contact distance. The settings are unaffected.
     *
     * @return the distance (in meters, ≥0)
     */
    float getSpeculativeContactDistance();

    /**
     * Alter the time interval before an object can fall asleep. The settings
     * are unaffected.
     *
     * @return the interval (in seconds, &ge;0)
     */
    float getTimeBeforeSleep();
}
