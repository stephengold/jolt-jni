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
 * Read-only access to a {@code VehicleTrackSettings} object. (native type:
 * const VehicleTrackSettings)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstVehicleTrackSettings extends ConstJoltPhysicsObject {
    /**
     * Return the angular damping factor. The settings are unaffected.
     *
     * @return the damping factor (per second)
     */
    float getAngularDamping();

    /**
     * Return the number of gearbox rotations per driven-wheel rotation. The
     * settings are unaffected.
     *
     * @return the ratio
     */
    float getDifferentialRatio();

    /**
     * Return the index of the wheel powered by the engine. The settings are
     * unaffected.
     *
     * @return the index of the driven wheel (&ge;0)
     */
    int getDrivenWheel();

    /**
     * Return the moment of inertia of the track and its wheels as seen on the
     * driven wheel. The settings are unaffected.
     *
     * @return the moment (in kilogram.meter^2)
     */
    float getInertia();

    /**
     * Count the track's wheels. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    int getNumWheels();
}
