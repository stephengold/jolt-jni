/*
Copyright (c) 2024-2025 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.ConstCharacterSettings;
import com.github.stephengold.joltjni.readonly.ConstPlane;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.template.Ref;
import java.nio.FloatBuffer;

/**
 * A counted reference to a {@code CharacterSettings} object. (native type:
 * {@code Ref<CharacterSettings>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class CharacterSettingsRef
        extends Ref
        implements ConstCharacterSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public CharacterSettingsRef() {
        long refVa = createDefault();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    CharacterSettingsRef(long refVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(refVa) : null;
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter whether to make an extra effort to remove contacts with internal
     * edges. (native attribute: mEnhancedInternalEdgeRemoval)
     *
     * @param remove {@code true} to remove ghost contacts (default=false)
     */
    public void setEnhancedInternalEdgeRemoval(boolean remove) {
        long settingsVa = targetVa();
        CharacterBaseSettings.setEnhancedInternalEdgeRemoval(
                settingsVa, remove);
    }

    /**
     * Alter the friction ratio. (native attribute: mFriction)
     *
     * @param friction the desired ratio (typically &ge;0 and &le;1,
     * default=0.2)
     */
    public void setFriction(float friction) {
        long settingsVa = targetVa();
        CharacterSettings.setFriction(settingsVa, friction);
    }

    /**
     * Alter the gravity multiplier. (native attribute: mGravityFactor)
     *
     * @param factor the desired multiplier (default=1)
     */
    public void setGravityFactor(float factor) {
        long settingsVa = targetVa();
        CharacterSettings.setGravityFactor(settingsVa, factor);
    }

    /**
     * Alter the object layer to which the character will be added. (native
     * attribute: mLayer)
     *
     * @param objLayer the ID of the desired object layer (&ge;0,
     * &lt;numObjectLayers, default=0)
     */
    public void setLayer(int objLayer) {
        long settingsVa = targetVa();
        CharacterSettings.setLayer(settingsVa, objLayer);
    }

    /**
     * Alter the character's mass. (native attribute: mMass)
     *
     * @param mass the desired mass (in kilograms, default=80)
     */
    public void setMass(float mass) {
        long settingsVa = targetVa();
        CharacterSettings.setMass(settingsVa, mass);
    }

    /**
     * Alter the maximum slope that the character can walk on. (native
     * attribute: mMaxSlopeAngle)
     *
     * @param angle (in radians, default=5*Pi/18)
     */
    public void setMaxSlopeAngle(float angle) {
        long settingsVa = targetVa();
        CharacterBaseSettings.setMaxSlopeAngle(settingsVa, angle);
    }

    /**
     * Replace the shape. (native attribute: mShape)
     *
     * @param shape the desired shape (not null, unaffected, default=null)
     */
    public void setShape(ConstShape shape) {
        long settingsVa = targetVa();
        long shapeVa = shape.targetVa();
        CharacterBaseSettings.setShape(settingsVa, shapeVa);
    }

    /**
     * Alter the supporting volume. (native attribute: mSupportingVolume)
     *
     * @param plane the desired plane of support (not null, unaffected,
     * default={(0,1,0),-1e10})
     */
    public void setSupportingVolume(ConstPlane plane) {
        long settingsVa = targetVa();
        float nx = plane.getNormalX();
        float ny = plane.getNormalY();
        float nz = plane.getNormalZ();
        float c = plane.getConstant();
        CharacterBaseSettings.setSupportingVolume(settingsVa, nx, ny, nz, c);
    }

    /**
     * Alter the character's "up" direction. (native attribute: mUp)
     *
     * @param direction the desired direction (not null, unaffected,
     * default=(0,1,0))
     */
    public void setUp(Vec3Arg direction) {
        long settingsVa = targetVa();
        float dx = direction.getX();
        float dy = direction.getY();
        float dz = direction.getZ();
        CharacterBaseSettings.setUp(settingsVa, dx, dy, dz);
    }
    // *************************************************************************
    // ConstCharacterSettings methods

    /**
     * Test whether to make an extra effort to remove contacts with internal
     * edges. The settings are unaffected. (native attribute:
     * mEnhancedInternalEdgeRemoval)
     *
     * @return {@code true} to remove ghost contacts, otherwise {@code false}
     */
    @Override
    public boolean getEnhancedInternalEdgeRemoval() {
        long settingsVa = targetVa();
        boolean result = CharacterBaseSettings.getEnhancedInternalEdgeRemoval(
                settingsVa);

        return result;
    }

    /**
     * Return the friction ratio. The settings are unaffected. (native
     * attribute: mFriction)
     *
     * @return the ratio (typically &ge;0 and &le;1)
     */
    @Override
    public float getFriction() {
        long settingsVa = targetVa();
        float result = CharacterSettings.getFriction(settingsVa);

        return result;
    }

    /**
     * Return the gravity factor. The settings are unaffected. (native
     * attribute: mGravityFactor)
     *
     * @return the factor
     */
    @Override
    public float getGravityFactor() {
        long settingsVa = targetVa();
        float result = CharacterSettings.getGravityFactor(settingsVa);

        return result;
    }

    /**
     * Return the index of the object layer. The settings are unaffected.
     * (native attribute: mLayer)
     *
     * @return the layer index (&ge;0, &lt;numObjectLayers)
     */
    @Override
    public int getLayer() {
        long settingsVa = targetVa();
        int result = CharacterSettings.getLayer(settingsVa);

        return result;
    }

    /**
     * Return the character's mass. The settings are unaffected. (native
     * attribute: mMass)
     *
     * @return the mass (in kilograms)
     */
    @Override
    public float getMass() {
        long settingsVa = targetVa();
        float result = CharacterSettings.getMass(settingsVa);

        return result;
    }

    /**
     * Return the maximum slope that the character can walk on. The settings are
     * unaffected. (native attribute: mMaxSlopeAngle)
     *
     * @return the angle (in radians)
     */
    @Override
    public float getMaxSlopeAngle() {
        long settingsVa = targetVa();
        float result = CharacterBaseSettings.getMaxSlopeAngle(settingsVa);

        return result;
    }

    /**
     * Count the active references to the native {@code CharacterSettings}. The
     * settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long settingsVa = targetVa();
        int result = CharacterBaseSettings.getRefCount(settingsVa);

        return result;
    }

    /**
     * Access the {@code Shape}. (native attribute: mShape)
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null}
     */
    @Override
    public ConstShape getShape() {
        long bodySettingsVa = targetVa();
        long shapeSettingsVa = CharacterBaseSettings.getShape(bodySettingsVa);
        ConstShape result = Shape.newShape(shapeSettingsVa);

        return result;
    }

    /**
     * Copy the supporting volume. The settings are unaffected. (native
     * attribute: mSupportingVolume)
     *
     * @return a new object
     */
    @Override
    public Plane getSupportingVolume() {
        long settingsVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        CharacterBaseSettings.getSupportingVolume(settingsVa, storeFloats);
        Plane result = new Plane(storeFloats);

        return result;
    }

    /**
     * Copy the character's "up" direction. The settings are unaffected. (native
     * attribute: mUp)
     *
     * @return a new direction vector (in system coordinates)
     */
    @Override
    public Vec3 getUp() {
        long settingsVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        CharacterBaseSettings.getUp(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }
    // *************************************************************************
    // Ref methods

    /**
     * Temporarily access the referenced {@code CharacterSettings}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public CharacterSettings getPtr() {
        long settingsVa = targetVa();
        CharacterSettings result = new CharacterSettings(settingsVa);

        return result;
    }

    /**
     * Return the address of the native {@code CharacterSettings}. No objects
     * are affected.
     *
     * @return a virtual address (not zero)
     */
    @Override
    public long targetVa() {
        long refVa = va();
        long result = getPtr(refVa);

        return result;
    }

    /**
     * Create another counted reference to the native {@code CharacterSettings}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public CharacterSettingsRef toRef() {
        long refVa = va();
        long copyVa = copy(refVa);
        CharacterSettingsRef result = new CharacterSettingsRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long copy(long refVa);

    native private static long createDefault();

    native static void free(long refVa);

    native private static long getPtr(long refVa);
}
