/*
Copyright (c) 2026 Stephen Gold

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
 * Read-only access to a {@code VehicleEngineSettings} object. (native type:
 * {@code const VehicleEngineSettings})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstVehicleEngineSettings extends ConstJoltPhysicsObject {
    /**
     * Return the angular damping factor. The settings are unaffected.
     *
     * @return the damping factor (per second)
     */
    float getAngularDamping();

    /**
     * Return the engine's rotational inertia. The settings are unaffected.
     *
     * @return the inertia (in kilogram.meter^2)
     */
    float getInertia();

    /**
     * Return the maximum rotation rate. The settings are unaffected.
     *
     * @return the limit (in revolutions per minute)
     */
    float getMaxRpm();

    /**
     * Return the maximum torque. The settings are unaffected.
     *
     * @return the limit (in Newton meters)
     */
    float getMaxTorque();

    /**
     * Return the minimum rotation rate to avoid stalling. The settings are
     * unaffected.
     *
     * @return the limit (in revolutions per minute)
     */
    float getMinRpm();

    /**
     * Access the normalized torque as a function of normalized RPM.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstLinearCurve getNormalizedTorque();
}
