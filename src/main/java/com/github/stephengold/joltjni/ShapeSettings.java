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

import com.github.stephengold.joltjni.enumerate.EShapeSubType;
import com.github.stephengold.joltjni.template.RefTarget;

/**
 * Settings used to construct a {@code Shape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class ShapeSettings
        extends SerializableObject
        implements RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate settings with no native object assigned.
     */
    ShapeSettings() {
    }

    /**
     * Instantiate settings with the specified native object assigned but not
     * owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    ShapeSettings(long settingsVa) {
        super(settingsVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Invoke this method after altering the settings but before generating any
     * new shapes.
     */
    public void clearCachedResult() {
        long settingsVa = va();
        clearCachedResult(settingsVa);
    }

    /**
     * Generate a {@code ShapeResult} from these settings.
     *
     * @return a new JVM object with a new native object assigned
     */
    public ShapeResult create() {
        long settingsVa = va();
        long resultVa = create(settingsVa);
        ShapeResult result = new ShapeResult(resultVa, true);

        return result;
    }

    /**
     * Instantiate a {@code ShapeSettings} given its virtual address.
     *
     * @param settingsVa the virtual address of the native object, or zero
     * @return a new JVM object, or {@code null} if the argument was zero
     */
    static ShapeSettings newShapeSettings(long settingsVa) {
        if (settingsVa == 0L) {
            return null;
        }

        long ordinal = getUserData(settingsVa);
        EShapeSubType subType = EShapeSubType.values()[(int) ordinal];
        ShapeSettings result;
        switch (subType) {
            case Box:
                result = new BoxShapeSettings(settingsVa);
                break;
            case Capsule:
                result = new CapsuleShapeSettings(settingsVa);
                break;
            case ConvexHull:
                result = new ConvexHullShapeSettings(settingsVa);
                break;
            case Cylinder:
                result = new CylinderShapeSettings(settingsVa);
                break;
            case Empty:
                result = new EmptyShapeSettings(settingsVa);
                break;
            case HeightField:
                result = new HeightFieldShapeSettings(settingsVa);
                break;
            case Mesh:
                result = new MeshShapeSettings(settingsVa);
                break;
            case MutableCompound:
                result = new MutableCompoundShapeSettings(settingsVa);
                break;
            case OffsetCenterOfMass:
                result = new OffsetCenterOfMassShapeSettings(settingsVa);
                break;
            case Plane:
                result = new PlaneShapeSettings(settingsVa);
                break;
            case RotatedTranslated:
                result = new RotatedTranslatedShapeSettings(settingsVa);
                break;
            case Scaled:
                result = new ScaledShapeSettings(settingsVa);
                break;
            case Sphere:
                result = new SphereShapeSettings(settingsVa);
                break;
            case StaticCompound:
                result = new StaticCompoundShapeSettings(settingsVa);
                break;
            case TaperedCapsule:
                result = new TaperedCapsuleShapeSettings(settingsVa);
                break;
            case TaperedCylinder:
                result = new TaperedCylinderShapeSettings(settingsVa);
                break;
            case Triangle:
                result = new TriangleShapeSettings(settingsVa);
                break;
            default:
                throw new IllegalArgumentException("subType = " + subType);
        }
        return result;
    }
    // *************************************************************************
    // new protected methods

    /**
     * Alter the user data, which holds the {@code EShapeSubType} ordinal.
     * (native attribute: mUserData)
     *
     * @param shapeSubType the desired value (not null)
     */
    void setSubType(EShapeSubType shapeSubType) {
        long settingsVa = va();
        long ordinal = shapeSubType.ordinal();
        setUserData(settingsVa, ordinal);
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code ShapeSettings}. The
     * settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long settingsVa = va();
        int result = getRefCount(settingsVa);

        return result;
    }

    /**
     * Mark the native {@code ShapeSettings} as embedded.
     */
    @Override
    public void setEmbedded() {
        long settingsVa = va();
        setEmbedded(settingsVa);
    }

    /**
     * Create a counted reference to the native {@code ShapeSettings}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public ShapeSettingsRef toRef() {
        long settingsVa = va();
        long refVa = toRef(settingsVa);
        ShapeSettingsRef result = new ShapeSettingsRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void clearCachedResult(long settingsVa);

    native private static long create(long settingsVa);

    native private static int getRefCount(long settingsVa);

    native private static long getUserData(long settingsVa);

    native private static void setEmbedded(long settingsVa);

    native private static void setUserData(long settingsVa, long value);

    native private static long toRef(long settingsVa);
}
