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
 * Read-only access to a {@code MotorcycleControllerSettings} object. (native
 * type: const MotorcycleControllerSettings)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstMotorcycleControllerSettings
        extends ConstWheeledVehicleControllerSettings {
    /**
     * Return the lean smoothing factor. The settings are unaffected.
     *
     * @return the factor
     */
    float getLeanSmoothingFactor();

    /**
     * Return the spring constant for the lean spring. The settings are
     * unaffected.
     *
     * @return the spring constant
     */
    float getLeanSpringConstant();

    /**
     * Return the damping constant for the lean spring. The settings are
     * unaffected.
     *
     * @return the damping constant
     */
    float getLeanSpringDamping();

    /**
     * Return the coefficient of additional force to control the lean angle. The
     * settings are unaffected.
     *
     * @return the integration coefficient
     */
    float getLeanSpringIntegrationCoefficient();

    /**
     * Return the rate of decay of the angle integral when the wheels are
     * unsupported. The settings are unaffected.
     *
     * @return the rate of decay (per second)
     */
    float getLeanSpringIntegrationCoefficientDecay();

    /**
     * Return the maximum lean angle (during turns). The settings are
     * unaffected.
     *
     * @return the angle (in radians)
     */
    float getMaxLeanAngle();
}
