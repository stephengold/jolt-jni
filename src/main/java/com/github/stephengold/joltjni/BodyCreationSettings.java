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
     * Instantiate default settings.
     */
    public BodyCreationSettings() {
        long bodySettingsVa = createBodyCreationSettings();
        setVirtualAddress(bodySettingsVa, true);
    }

    /**
     * Instantiate settings for the specified shape.
     *
     * @param shape the desired shape (not null)
     * @param loc the desired location (not null, unaffected)
     * @param orient the desired orientation (not null, unaffected)
     * @param motionType the desired motion type (not null)
     * @param objLayer the ID of the desired object layer
     */
    public BodyCreationSettings(Shape shape, RVec3Arg loc, QuatArg orient,
            EMotionType motionType, int objLayer) {
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
    public BodyCreationSettings(ShapeSettings shapeSettings, RVec3Arg loc,
            QuatArg orient, EMotionType motionType, int objLayer) {
        long shapeSettingsVa = shapeSettings.va();
        int motionTypeOrdinal = motionType.ordinal();
        long bodySettingsVa = createBodyCreationSettingsFromShapeSettings(
                shapeSettingsVa, loc.xx(), loc.yy(), loc.zz(),
                orient.getX(), orient.getY(), orient.getZ(), orient.getW(),
                motionTypeOrdinal, objLayer);
        setVirtualAddress(bodySettingsVa, true);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the shape.
     *
     * @return the shape, or null if none
     */
    public Shape getShape() {
        long bodySettingsVa = va();
        long shapeSettingsVa = getShape(bodySettingsVa);
        Shape result = Shape.newShape(shapeSettingsVa);
        return result;
    }

    /**
     * Alter the friction ratio.
     *
     * @param friction the desired value
     */
    public void setFriction(float friction) {
        long bodySettingsVa = va();
        setFriction(bodySettingsVa, friction);
    }

    /**
     * Alter the gravity factor.
     *
     * @param factor the desired value
     */
    public void setGravityFactor(float factor) {
        long bodySettingsVa = va();
        setGravityFactor(bodySettingsVa, factor);
    }

    /**
     * Alter the motion quality.
     *
     * @param motionQuality the desired quality (not null)
     */
    public void setMotionQuality(EMotionQuality motionQuality) {
        long bodySettingsVa = va();
        int motionQualityOrdinal = motionQuality.ordinal();
        setMotionQuality(bodySettingsVa, motionQualityOrdinal);
    }

    /**
     * Alter the motion type.
     *
     * @param motionType the desired type (not null)
     */
    public void setMotionType(EMotionType motionType) {
        long bodySettingsVa = va();
        int motionTypeOrdinal = motionType.ordinal();
        setMotionType(bodySettingsVa, motionTypeOrdinal);
    }

    /**
     * Alter the object layer.
     *
     * @param objLayer the ID of the desired object layer
     */
    public void setObjectLayer(int objLayer) {
        long bodySettingsVa = va();
        setObjectLayer(bodySettingsVa, objLayer);
    }

    /**
     * Alter the location.
     *
     * @param loc the desired location (in physics-system coordinates, not null,
     * unaffected)
     */
    public void setPosition(RVec3Arg loc) {
        long bodySettingsVa = va();
        setPosition(bodySettingsVa, loc.xx(), loc.yy(), loc.zz());
    }

    /**
     * Alter the restitution ratio.
     *
     * @param restitution the desired value
     */
    public void setRestitution(float restitution) {
        long bodySettingsVa = va();
        setRestitution(bodySettingsVa, restitution);
    }

    /**
     * Alter the shape.
     *
     * @param shape the desired shape (not null)
     */
    public void setShape(Shape shape) {
        long bodySettingsVa = va();
        long shapeSettingsVa = shape.va();
        setShape(bodySettingsVa, shapeSettingsVa);
    }

    /**
     * Alter the shape settings.
     *
     * @param shapeSettings the desired shape settings (not null)
     */
    public void setShapeSettings(ShapeSettings shapeSettings) {
        long bodySettingsVa = va();
        long shapeSettingsVa = shapeSettings.va();
        setShapeSettings(bodySettingsVa, shapeSettingsVa);
    }
    // *************************************************************************
    // JoltPhysicsObject methods

    /**
     * Unassign the assigned native object, assuming there is one. Free the
     * native object if the settings instance owns it.
     */
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

    native private static long createBodyCreationSettings();

    native private static long createBodyCreationSettingsFromShape(
            long shapeVa, double locX, double locY, double locZ,
            float qx, float qy, float qz, float qw,
            int motionTypeOrdinal, int objLayer);

    native private static long createBodyCreationSettingsFromShapeSettings(
            long shapeSettingsVa, double locX, double locY, double locZ,
            float qx, float qy, float qz, float qw,
            int motionTypeOrdinal, int objLayer);

    native private static void free(long bodySettingsVa);

    native private static long getShape(long bodySettingsVa);

    native private static void setFriction(long bodySettingsVa, float friction);

    native private static void setGravityFactor(
            long bodySettingsVa, float factor);

    native private static void setMotionQuality(
            long bodySettingsVa, int motionQualityOrdinal);

    native private static void setMotionType(
            long bodySettingsVa, int motionTypeOrdinal);

    native private static void setObjectLayer(
            long bodySettingsVa, int objLayer);

    native private static void setPosition(
            long bodySettingsVa, double locX, double locY, double locZ);

    native private static void setRestitution(
            long bodySettingsVa, float restitution);

    native private static void setShape(long bodySettingsVa, long shapeVa);

    native private static void setShapeSettings(
            long bodySettingsVa, long shapeSettingsVa);
}
