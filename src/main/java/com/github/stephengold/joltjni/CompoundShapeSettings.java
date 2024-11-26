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

import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Settings used to construct a {@code CompoundShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class CompoundShapeSettings extends ShapeSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a settings object with no native object assigned.
     */
    CompoundShapeSettings() {
    }

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    CompoundShapeSettings(long settingsVa) {
        super(settingsVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Add the specified subshape at the specified position.
     *
     * @param offset (not null, unaffected)
     * @param rotation (not null, not zero, unaffected)
     * @param subShape a reference to the subshape (not null)
     */
    public void addShape(
            Vec3Arg offset, QuatArg rotation, ConstShape subShape) {
        long settingsVa = va();
        float offsetX = offset.getX();
        float offsetY = offset.getY();
        float offsetZ = offset.getZ();
        float rotW = rotation.getW();
        float rotX = rotation.getX();
        float rotY = rotation.getY();
        float rotZ = rotation.getZ();
        long subShapeVa = subShape.targetVa();
        addShape(settingsVa, offsetX, offsetY, offsetZ,
                rotX, rotY, rotZ, rotW, subShapeVa);
    }

    /**
     * Add the specified subshape at the specified position.
     *
     * @param offset (not null, unaffected)
     * @param rotation (not null, not zero, unaffected)
     * @param subSettings (not null)
     */
    public void addShape(
            Vec3Arg offset, QuatArg rotation, ShapeSettings subSettings) {
        long settingsVa = va();
        float offsetX = offset.getX();
        float offsetY = offset.getY();
        float offsetZ = offset.getZ();
        float rotW = rotation.getW();
        float rotX = rotation.getX();
        float rotY = rotation.getY();
        float rotZ = rotation.getZ();
        long subSettingsVa = subSettings.va();
        addShapeSettings(settingsVa, offsetX, offsetY, offsetZ,
                rotX, rotY, rotZ, rotW, subSettingsVa);
    }
    // *************************************************************************
    // native private methods

    native private static void addShape(long settingsVa, float offsetX,
            float offsetY, float offsetZ, float rotX, float rotY, float rotZ,
            float rotW, long subShapeVa);

    native private static void addShapeSettings(long settingsVa, float offsetX,
            float offsetY, float offsetZ, float rotX, float rotY, float rotZ,
            float rotW, long subSettingsVa);
}
