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

import com.github.stephengold.joltjni.enumerate.EBackFaceMode;
import com.github.stephengold.joltjni.readonly.ConstCharacterVirtualSettings;
import com.github.stephengold.joltjni.readonly.ConstPlane;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.template.Ref;
import java.nio.FloatBuffer;

/**
 * A counted reference to a {@code CharacterVirtualSettings} object. (native
 * type: {@code Ref<CharacterVirtualSettings>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class CharacterVirtualSettingsRef
        extends Ref
        implements ConstCharacterVirtualSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public CharacterVirtualSettingsRef() {
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
    CharacterVirtualSettingsRef(long refVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(refVa) : null;
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter whether the character will move through back facing triangles.
     * (native attribute: mBackFaceMode)
     *
     * @param mode the desired mode (not null, default=CollideWithBackFaces)
     */
    public void setBackFaceMode(EBackFaceMode mode) {
        long settingsVa = targetVa();
        int ordinal = mode.ordinal();
        CharacterVirtualSettings.setBackFaceMode(settingsVa, ordinal);
    }

    /**
     * Alter how far the character tries to stay away from the geometry. (native
     * attribute: mCharacterPadding)
     *
     * @param padding the desired distance (in meters, default=0.02)
     */
    public void setCharacterPadding(float padding) {
        long settingsVa = targetVa();
        CharacterVirtualSettings.setCharacterPadding(settingsVa, padding);
    }

    /**
     * Alter the collision tolerance. (native attribute: mCollisionTolerance)
     *
     * @param tolerance the desired penetration distance (in meters,
     * default=0.001)
     */
    public void setCollisionTolerance(float tolerance) {
        long settingsVa = targetVa();
        CharacterVirtualSettings.setCollisionTolerance(settingsVa, tolerance);
    }

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
     * Alter the maximum angle for merging during hit reduction. (native
     * attribute: mHitReductionCosMaxAngle)
     *
     * @param cosine the cosine of the maximum angle, or -1 to disable hit
     * reduction (default=0.999)
     */
    public void setHitReductionCosMaxAngle(int cosine) {
        long settingsVa = targetVa();
        CharacterVirtualSettings.setHitReductionCosMaxAngle(settingsVa, cosine);
    }

    /**
     * Alter the object layer that the inner rigid body will be added to.
     * (native attribute: mInnerBodyLayer)
     *
     * @param objectLayer the index of the desired object layer (default=0)
     */
    public void setInnerBodyLayer(int objectLayer) {
        long settingsVa = targetVa();
        CharacterVirtualSettings.setInnerBodyLayer(settingsVa, objectLayer);
    }

    /**
     * Replace the shape of the inner rigid body. (native attribute:
     * mInnerBodyShape)
     *
     * @param shape the desired shape, or {@code null} for no inner body
     * (default=null)
     */
    public void setInnerBodyShape(ConstShape shape) {
        long settingsVa = targetVa();
        long shapeVa = (shape == null) ? 0L : shape.targetVa();
        CharacterVirtualSettings.setInnerBodyShape(settingsVa, shapeVa);
    }

    /**
     * Alter the character's mass. (native attribute: mMass)
     *
     * @param mass the desired mass (in kilograms, default=70)
     */
    public void setMass(float mass) {
        long settingsVa = targetVa();
        CharacterVirtualSettings.setMass(settingsVa, mass);
    }

    /**
     * Alter the maximum number of collision iterations. (native attribute:
     * mMaxCollisionIterations)
     *
     * @param numIterations the desired number of iterations (&ge;0, default=5)
     */
    public void setMaxCollisionIterations(int numIterations) {
        long settingsVa = targetVa();
        CharacterVirtualSettings.setMaxCollisionIterations(
                settingsVa, numIterations);
    }

    /**
     * Alter how often to try stepping in the constraint solver. (native
     * attribute: mMaxConstraintIterations)
     *
     * @param numIterations the desired number of iterations (&ge;0, default=15)
     */
    public void setMaxConstraintIterations(int numIterations) {
        long settingsVa = targetVa();
        CharacterVirtualSettings.setMaxConstraintIterations(
                settingsVa, numIterations);
    }

    /**
     * Alter the maximum number of hits to collect. (native attribute:
     * mMaxNumHits)
     *
     * @param numHits the desired limit (&ge;0), default=256)
     */
    public void setMaxNumHits(int numHits) {
        long settingsVa = targetVa();
        CharacterVirtualSettings.setMaxNumHits(settingsVa, numHits);
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
     * Alter the maximum force applied to other bodies. (native attribute:
     * mMaxStrength)
     *
     * @param force the desired force (in Newtons)
     */
    public void setMaxStrength(float force) {
        long settingsVa = targetVa();
        CharacterVirtualSettings.setMaxStrength(settingsVa, force);
    }

    /**
     * Alter the early-out threshold. (native attribute: mMinTimeRemaining)
     *
     * @param interval the desired simulation time interval (in seconds,
     * default=0.0001)
     */
    public void setMinTimeRemaining(float interval) {
        long settingsVa = targetVa();
        CharacterVirtualSettings.setMinTimeRemaining(settingsVa, interval);
    }

    /**
     * Alter how quickly penetration is resolved. (native attribute:
     * mPenetrationRecoverySpeed)
     *
     * @param fraction the desired resolution fraction (0=never resolved, 1=all
     * in one update, default=1)
     */
    public void setPenetrationRecoverySpeed(float fraction) {
        long settingsVa = targetVa();
        CharacterVirtualSettings.setPenetrationRecoverySpeed(
                settingsVa, fraction);
    }

    /**
     * Alter the maximum range of predictive contacts. (native attribute:
     * mPredictiveContactDistance)
     *
     * @param distance the desired distance (in meters, default=0.1)
     */
    public void setPredictiveContactDistance(float distance) {
        long settingsVa = targetVa();
        CharacterVirtualSettings.setPredictiveContactDistance(
                settingsVa, distance);
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
     * Alter the local offset applied to the shape. (native attribute:
     * mShapeOffset)
     *
     * @param offset the desired offset (in local coordinates, not null,
     * unaffected, default=(0,0,0))
     */
    public void setShapeOffset(Vec3Arg offset) {
        long settingsVa = targetVa();
        float x = offset.getX();
        float y = offset.getY();
        float z = offset.getZ();
        CharacterVirtualSettings.setShapeOffset(settingsVa, x, y, z);
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
    // ConstCharacterVirtualSettings methods

    /**
     * Determine whether the character will move through back-facing triangles.
     * The settings are unaffected. (native attribute: mBackFaceMode)
     *
     * @return an enum value (not null)
     */
    @Override
    public EBackFaceMode getBackFaceMode() {
        long settingsVa = targetVa();
        int ordinal = CharacterVirtualSettings.getBackFaceMode(settingsVa);
        EBackFaceMode result = EBackFaceMode.values()[ordinal];

        return result;
    }

    /**
     * Return how far the character tries to stay away from the geometry. The
     * settings are unaffected. (native attribute: mCharacterPadding)
     *
     * @return the desired distance (in meters)
     */
    @Override
    public float getCharacterPadding() {
        long settingsVa = targetVa();
        float result = CharacterVirtualSettings.getCharacterPadding(settingsVa);

        return result;
    }

    /**
     * Return the collision tolerance. The settings are unaffected. (native
     * attribute: mCollisionTolerance)
     *
     * @return the allowed penetration distance (in meters)
     */
    @Override
    public float getCollisionTolerance() {
        long settingsVa = targetVa();
        float result = CharacterVirtualSettings.getCollisionTolerance(
                settingsVa);

        return result;
    }

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
     * Return the maximum angle for merging during hit reduction. The settings
     * are unaffected. (native attribute: mHitReductionCosMaxAngle)
     *
     * @return the cosine of the maximum angle, or -1 if hit reduction is
     * disabled
     */
    @Override
    public float getHitReductionCosMaxAngle() {
        long settingsVa = targetVa();
        float result = CharacterVirtualSettings.getHitReductionCosMaxAngle(
                settingsVa);

        return result;
    }

    /**
     * Return the object layer that the inner rigid body will be added to. The
     * settings are unaffected. (native attribute: mInnerBodyLayer)
     *
     * @return the index of the object layer
     */
    @Override
    public int getInnerBodyLayer() {
        long settingsVa = targetVa();
        int result = CharacterVirtualSettings.getInnerBodyLayer(settingsVa);

        return result;
    }

    /**
     * Return the shape of the inner rigid body. The settings are unaffected.
     * (native attribute: mInnerBodyShape)
     *
     * @return the shape, or {@code null} for no inner body
     */
    @Override
    public ConstShape getInnerBodyShape() {
        long settingsVa = targetVa();
        long shapeVa = CharacterVirtualSettings.getInnerBodyShape(settingsVa);
        ConstShape result = Shape.newShape(shapeVa);

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
        float result = CharacterVirtualSettings.getMass(settingsVa);

        return result;
    }

    /**
     * Return the maximum number of collision iterations. The settings are
     * unaffected. (native attribute: mMaxCollisionIterations)
     *
     * @return the number of iterations (&ge;0)
     */
    @Override
    public int getMaxCollisionIterations() {
        long settingsVa = targetVa();
        int result = CharacterVirtualSettings.getMaxCollisionIterations(
                settingsVa);

        return result;
    }

    /**
     * Return how often to try stepping in the constraint solver. The settings
     * are unaffected. (native attribute: mMaxConstraintIterations)
     *
     * @return the number of iterations (&ge;0)
     */
    @Override
    public int getMaxConstraintIterations() {
        long settingsVa = targetVa();
        int result = CharacterVirtualSettings.getMaxConstraintIterations(
                settingsVa);

        return result;
    }

    /**
     * Return the maximum number of hits to be collected. The settings are
     * unaffected. (native attribute: mMaxNumHits)
     *
     * @return the limit (&ge;0)
     */
    @Override
    public int getMaxNumHits() {
        long settingsVa = targetVa();
        int result = CharacterVirtualSettings.getMaxNumHits(settingsVa);

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
     * Return the maximum force applied to other bodies. The settings are
     * unaffected. (native attribute: mMaxStrength)
     *
     * @return the force (in Newtons)
     */
    @Override
    public float getMaxStrength() {
        long settingsVa = targetVa();
        float result = CharacterVirtualSettings.getMaxStrength(settingsVa);

        return result;
    }

    /**
     * Return the early-out threshold. The settings are unaffected. (native
     * attribute: mMinTimeRemaining)
     *
     * @return the simulation time interval (in seconds)
     */
    @Override
    public float getMinTimeRemaining() {
        long settingsVa = targetVa();
        float result = CharacterVirtualSettings.getMinTimeRemaining(settingsVa);

        return result;
    }

    /**
     * Return how quickly penetration is resolved. The settings are unaffected.
     * (native attribute: mPenetrationRecoverySpeed)
     *
     * @return the resolution fraction (0=never resolved, 1=all in one update)
     */
    @Override
    public float getPenetrationRecoverySpeed() {
        long settingsVa = targetVa();
        float result = CharacterVirtualSettings.getPenetrationRecoverySpeed(
                settingsVa);

        return result;
    }

    /**
     * Return the maximum range of predictive contacts. The settings are
     * unaffected. (native attribute: mPredictiveContactDistance)
     *
     * @return the distance (in meters)
     */
    @Override
    public float getPredictiveContactDistance() {
        long settingsVa = targetVa();
        float result = CharacterVirtualSettings.getPredictiveContactDistance(
                settingsVa);

        return result;
    }

    /**
     * Count the active references to the native
     * {@code CharacterVirtualSettings}. The settings are unaffected.
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
     * Copy the local offset applied to the shape. The settings are unaffected.
     * (native attribute: mShapeOffset)
     *
     * @return a new offset vector (in local coordinates)
     */
    @Override
    public Vec3 getShapeOffset() {
        long settingsVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        CharacterVirtualSettings.getShapeOffset(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

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
     * Temporarily access the referenced {@code CharacterVirtualSettings}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public CharacterVirtualSettings getPtr() {
        long settingsVa = targetVa();
        CharacterVirtualSettings result
                = new CharacterVirtualSettings(settingsVa);

        return result;
    }

    /**
     * Return the address of the native {@code CharacterVirtualSettings}. No
     * objects are affected.
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
     * Create another counted reference to the native
     * {@code CharacterVirtualSettings}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public CharacterVirtualSettingsRef toRef() {
        long refVa = va();
        long copyVa = copy(refVa);
        CharacterVirtualSettingsRef result
                = new CharacterVirtualSettingsRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long copy(long refVa);

    native private static long createDefault();

    native static void free(long refVa);

    native private static long getPtr(long refVa);
}
