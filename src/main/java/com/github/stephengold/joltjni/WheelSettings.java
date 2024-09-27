/*
Copyright (c) 2024 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.template.Ref;

/**
 * Settings used to construct a {@code Wheel}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class WheelSettings extends SerializableObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate settings with no native object assigned.
     *
     * @param dummy unused argument to distinguish from the zero-arg constructor
     */
    WheelSettings(boolean dummy) {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the location of the attachment point. The settings are unaffected.
     * (native attribute: mPosition)
     *
     * @return a new location vector (in the body's local system)
     */
    public Vec3 getPosition() {
        long settingsVa = va();
        float x = getPositionX(settingsVa);
        float y = getPositionY(settingsVa);
        float z = getPositionZ(settingsVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the radius of the wheel. The settings are unaffected. (native
     * attribute: mRadius)
     *
     * @return the radius (in maters)
     */
    public float getRadius() {
        long settingsVa = va();
        float result = getRadius(settingsVa);

        return result;
    }

    /**
     * Return the width of the wheel. The settings are unaffected. (native
     * attribute: mWidth)
     *
     * @return the width (in maters)
     */
    public float getWidth() {
        long settingsVa = va();
        float result = getWidth(settingsVa);

        return result;
    }

    /**
     * Alter the location of the attachment point. (native attribute: mPosition)
     *
     * @param position the location of the attachment point (in the body's local
     * system, not null, unaffected, default=(0,0,0))
     */
    public void setPosition(Vec3Arg position) {
        long settingsVa = va();
        float x = position.getX();
        float y = position.getY();
        float z = position.getZ();
        setPosition(settingsVa, x, y, z);
    }

    /**
     * Alter the radius of the wheel. (native attribute: mRadius)
     *
     * @param radius the desired radius (in meters, default=0.3)
     */
    public void setRadius(float radius) {
        long settingsVa = va();
        setRadius(settingsVa, radius);
    }

    /**
     * Alter the width of the wheel. (native attribute: mWidth)
     *
     * @param width the desired width (in meters, default=0.1)
     */
    public void setWidth(float width) {
        long settingsVa = va();
        setWidth(settingsVa, width);
    }

    /**
     * Create a counted reference to the native {@code WheelSettings}.
     *
     * @return a new JVM object with a new native object assigned
     */
    abstract public Ref toRef();
    // *************************************************************************
    // native private methods

    native private static float getPositionX(long settingsVa);

    native private static float getPositionY(long settingsVa);

    native private static float getPositionZ(long settingsVa);

    native private static float getRadius(long settingsVa);

    native private static float getWidth(long settingsVa);

    native private static void setPosition(
            long settingsVa, float x, float y, float z);

    native private static void setRadius(long settingsVa, float radius);

    native private static void setWidth(long settingsVa, float width);
}
