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
package com.github.stephengold.joltjni;

/**
 * A customizable {@code TireMaxImpulseCallback}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CustomTireMaxImpulseCallback extends TireMaxImpulseCallback {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a customizable callback.
     */
    public CustomTireMaxImpulseCallback() {
        long callbackVa = create();
        setVirtualAddressAsOwner(callbackVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Calculate maximum lateral tire impulse by combining friction, slip, and
     * suspension impulses. The actual applied impulse may be lower. For
     * instance, when the vehicle is stationary on a horizontal surface the
     * applied impulse will be 0. Meant to be overridden.
     *
     * @param wheelIndex the index of the desired wheel (&ge;0)
     * @param suspensionImpulse the suspension impulse
     * @param longitudinalFriction the longitudinal friction coefficient
     * @param lateralFriction the lateral friction coefficient
     * @param longitudinalSlip the longitudinal slip velocity
     * @param lateralSlip the lateral slip angle (in radians)
     * @param deltaTime the duration of the simulation step (in seconds)
     * @return the lateral component of the maximum impulse
     */
    public float maxLateralImpulse(int wheelIndex, float suspensionImpulse,
            float longitudinalFriction, float lateralFriction,
            float longitudinalSlip, float lateralSlip, float deltaTime) {
        float result = lateralFriction * suspensionImpulse;
        return result;
    }

    /**
     * Calculate maximum longitudinal tire impulse by combining friction, slip,
     * and suspension impulses. The actual applied impulse may be lower. For
     * instance, when the vehicle is stationary on a horizontal surface the
     * applied impulse will be 0. Meant to be overridden.
     *
     * @param wheelIndex the index of the desired wheel (&ge;0)
     * @param suspensionImpulse the suspension impulse
     * @param longitudinalFriction the longitudinal friction coefficient
     * @param lateralFriction the lateral friction coefficient
     * @param longitudinalSlip the longitudinal slip velocity
     * @param lateralSlip the lateral slip angle (in radians)
     * @param deltaTime the duration of the simulation step (in seconds)
     * @return the longitudinal component of the maximum impulse
     */
    public float maxLongitudinalImpulse(int wheelIndex, float suspensionImpulse,
            float longitudinalFriction, float lateralFriction,
            float longitudinalSlip, float lateralSlip, float deltaTime) {
        float result = longitudinalFriction * suspensionImpulse;
        return result;
    }
    // *************************************************************************
    // native private methods

    native private long create();
}
