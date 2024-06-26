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

/**
 * Settings used to create a {@code Body}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BodyCreationSettings extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate settings for the specified shape.
     *
     * @param shape the desired shape (not null)
     * @param loc the desired location (not null, unaffected)
     * @param orient the desired orientation (not null, unaffected)
     * @param motionType the desired motion type (not null)
     * @param objLayer the ID of the desired object layer
     */
    public BodyCreationSettings(Shape shape,
            RVec3 loc, Quat orient, EMotionType motionType, int objLayer) {
        long shapeVa = shape.va();
        int motionTypeOrdinal = motionType.ordinal();
        long bodySettingsVa = createBodyCreationSettingsFromShape(
                shapeVa, loc.xx(), loc.yy(), loc.zz(),
                orient.getX(), orient.getY(), orient.getZ(), orient.getW(),
                motionTypeOrdinal, objLayer);
        setVirtualAddress(bodySettingsVa, true);
    }

    /**
     * Instantiate settings for the specified shape settings.
     *
     * @param shapeSettings the desired shape settings (not null)
     * @param loc the desired location (not null, unaffected)
     * @param orient the desired orientation (not null, unaffected)
     * @param motionType the desired motion type (not null)
     * @param objLayer the ID of the desired object layer
     */
    public BodyCreationSettings(ShapeSettings shapeSettings,
            RVec3 loc, Quat orient, EMotionType motionType, int objLayer) {
        long shapeSettingsVa = shapeSettings.va();
        int motionTypeOrdinal = motionType.ordinal();
        long bodySettingsVa = createBodyCreationSettingsFromShapeSettings(
                shapeSettingsVa, loc.xx(), loc.yy(), loc.zz(),
                orient.getX(), orient.getY(), orient.getZ(), orient.getW(),
                motionTypeOrdinal, objLayer);
        setVirtualAddress(bodySettingsVa, true);
    }
    // *************************************************************************
    // JoltPhysicsObject methods

    @Override
    public void close() {
        if (ownsNativeObject()) {
            long settingsVa = va();
            free(settingsVa);
        }

        unassignNativeObject();
    }
    // *************************************************************************
    // native private methods

    native private static long createBodyCreationSettingsFromShape(
            long shapeVa, double locX, double locY, double locZ,
            float qx, float qy, float qz, float qw,
            int motionTypeOrdinal, int objLayer);

    native private static long createBodyCreationSettingsFromShapeSettings(
            long shapeSettingsVa, double locX, double locY, double locZ,
            float qx, float qy, float qz, float qw,
            int motionTypeOrdinal, int objLayer);

    native private static void free(long bodySettingsVa);
}
