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
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.template.RefTarget;
import java.nio.FloatBuffer;

/**
 * Settings used to create a {@code CharacterVirtual}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CharacterVirtualSettings
        extends CharacterBaseSettings
        implements ConstCharacterVirtualSettings, RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public CharacterVirtualSettings() {
        long settingsVa = createCharacterVirtualSettings();
        setVirtualAddress(settingsVa); // not owner due to ref counting
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public CharacterVirtualSettings(ConstCharacterVirtualSettings original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa); // not owner due to ref counting
    }

    /**
     * Instantiate settings with the specified native object assigned but not
     * owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    CharacterVirtualSettings(long settingsVa) {
        super(settingsVa);
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
        long settingsVa = va();
        int ordinal = mode.ordinal();
        setBackFaceMode(settingsVa, ordinal);
    }

    /**
     * Alter how far the character tries to stay away from the geometry. (native
     * attribute: mCharacterPadding)
     *
     * @param padding the desired distance (in meters, default=0.02)
     */
    public void setCharacterPadding(float padding) {
        long settingsVa = va();
        setCharacterPadding(settingsVa, padding);
    }

    /**
     * Alter the collision tolerance. (native attribute: mCollisionTolerance)
     *
     * @param tolerance the desired penetration distance (in meters,
     * default=0.001)
     */
    public void setCollisionTolerance(float tolerance) {
        long settingsVa = va();
        setCollisionTolerance(settingsVa, tolerance);
    }

    /**
     * Alter the maximum angle for merging during hit reduction. (native
     * attribute: mHitReductionCosMaxAngle)
     *
     * @param cosine the cosine of the maximum angle, or -1 to disable hit
     * reduction (default=0.999)
     */
    public void setHitReductionCosMaxAngle(int cosine) {
        long settingsVa = va();
        setHitReductionCosMaxAngle(settingsVa, cosine);
    }

    /**
     * Alter the object layer that the inner rigid body will be added to.
     * (native attribute: mInnerBodyLayer)
     *
     * @param objectLayer the index of the desired object layer (default=0)
     */
    public void setInnerBodyLayer(int objectLayer) {
        long settingsVa = va();
        setInnerBodyLayer(settingsVa, objectLayer);
    }

    /**
     * Replace the shape of the inner rigid body. (native attribute:
     * mInnerBodyShape)
     *
     * @param shape the desired shape, or {@code null} for no inner body
     * (default=null)
     */
    public void setInnerBodyShape(ConstShape shape) {
        long settingsVa = va();
        long shapeVa = (shape == null) ? 0L : shape.targetVa();
        setInnerBodyShape(settingsVa, shapeVa);
    }

    /**
     * Alter the character's mass. (native attribute: mMass)
     *
     * @param mass the desired mass (in kilograms, default=70)
     */
    public void setMass(float mass) {
        long settingsVa = va();
        setMass(settingsVa, mass);
    }

    /**
     * Alter the maximum number of collision iterations. (native attribute:
     * mMaxCollisionIterations)
     *
     * @param numIterations the desired number of iterations (&ge;0, default=5)
     */
    public void setMaxCollisionIterations(int numIterations) {
        long settingsVa = va();
        setMaxCollisionIterations(settingsVa, numIterations);
    }

    /**
     * Alter how often to try stepping in the constraint solver. (native
     * attribute: mMaxConstraintIterations)
     *
     * @param numIterations the desired number of iterations (&ge;0, default=15)
     */
    public void setMaxConstraintIterations(int numIterations) {
        long settingsVa = va();
        setMaxConstraintIterations(settingsVa, numIterations);
    }

    /**
     * Alter the maximum number of hits to collect. (native attribute:
     * mMaxNumHits)
     *
     * @param numHits the desired limit (&ge;0), default=256)
     */
    public void setMaxNumHits(int numHits) {
        long settingsVa = va();
        setMaxNumHits(settingsVa, numHits);
    }

    /**
     * Alter the maximum force applied to other bodies. (native attribute:
     * mMaxStrength)
     *
     * @param force the desired force (in Newtons)
     */
    public void setMaxStrength(float force) {
        long settingsVa = va();
        setMaxStrength(settingsVa, force);
    }

    /**
     * Alter the early out threshold. (native attribute: mMinTimeRemaining)
     *
     * @param interval the desired simulation time interval (in seconds,
     * default=0.0001)
     */
    public void setMinTimeRemaining(float interval) {
        long settingsVa = va();
        setMinTimeRemaining(settingsVa, interval);
    }

    /**
     * Alter how quickly penetration is resolved. (native attribute:
     * mPenetrationRecoverySpeed)
     *
     * @param fraction the desired resolution fraction (0=never resolved, 1=all
     * in one update, default=1)
     */
    public void setPenetrationRecoverySpeed(float fraction) {
        long settingsVa = va();
        setPenetrationRecoverySpeed(settingsVa, fraction);
    }

    /**
     * Alter the maximum range of predictive contacts. (native attribute:
     * mPredictiveContactDistance)
     *
     * @param distance the desired distance (in meters, default=0.1)
     */
    public void setPredictiveContactDistance(float distance) {
        long settingsVa = va();
        setPredictiveContactDistance(settingsVa, distance);
    }

    /**
     * Alter the local offset applied to the shape. (native attribute:
     * mShapeOffset)
     *
     * @param offset the desired offset (in local coordinates, not null,
     * unaffected, default=(0,0,0))
     */
    public void setShapeOffset(Vec3Arg offset) {
        long settingsVa = va();
        float x = offset.getX();
        float y = offset.getY();
        float z = offset.getZ();
        setShapeOffset(settingsVa, x, y, z);
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
        long settingsVa = va();
        int ordinal = getBackFaceMode(settingsVa);
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
        long settingsVa = va();
        float result = getCharacterPadding(settingsVa);

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
        long settingsVa = va();
        float result = getCollisionTolerance(settingsVa);

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
        long settingsVa = va();
        float result = getHitReductionCosMaxAngle(settingsVa);

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
        long settingsVa = va();
        int result = getInnerBodyLayer(settingsVa);

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
        long settingsVa = va();
        long shapeVa = getInnerBodyShape(settingsVa);
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
        long settingsVa = va();
        float result = getMass(settingsVa);

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
        long settingsVa = va();
        int result = getMaxCollisionIterations(settingsVa);

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
        long settingsVa = va();
        int result = getMaxConstraintIterations(settingsVa);

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
        long settingsVa = va();
        int result = getMaxNumHits(settingsVa);

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
        long settingsVa = va();
        float result = getMaxStrength(settingsVa);

        return result;
    }

    /**
     * Return the early out threshold. The settings are unaffected. (native
     * attribute: mMinTimeRemaining)
     *
     * @return the simulation time interval (in seconds)
     */
    @Override
    public float getMinTimeRemaining() {
        long settingsVa = va();
        float result = getMinTimeRemaining(settingsVa);

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
        long settingsVa = va();
        float result = getPenetrationRecoverySpeed(settingsVa);

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
        long settingsVa = va();
        float result = getPredictiveContactDistance(settingsVa);

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
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getShapeOffset(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native
     * {@code CharacterVirtualSettings}. The settings are unaffected.
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
     * Mark the native {@code CharacterVirtualSettings} as embedded.
     */
    @Override
    public void setEmbedded() {
        long settingsVa = va();
        setEmbedded(settingsVa);
    }

    /**
     * Create a counted reference to the native
     * {@code CharacterVirtualSettings}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public CharacterVirtualSettingsRef toRef() {
        long settingsVa = va();
        long refVa = toRef(settingsVa);
        CharacterVirtualSettingsRef result
                = new CharacterVirtualSettingsRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long createCharacterVirtualSettings();

    native private static long createCopy(long originalVa);

    native static int getBackFaceMode(long settingsVa);

    native static float getCharacterPadding(long settingsVa);

    native static float getCollisionTolerance(long settingsVa);

    native static float getHitReductionCosMaxAngle(long settingsVa);

    native static int getInnerBodyLayer(long settingsVa);

    native static long getInnerBodyShape(long settingsVa);

    native static float getMass(long settingsVa);

    native static int getMaxCollisionIterations(long settingsVa);

    native static int getMaxConstraintIterations(long settingsVa);

    native static int getMaxNumHits(long settingsVa);

    native static float getMaxStrength(long settingsVa);

    native static float getMinTimeRemaining(long settingsVa);

    native static float getPenetrationRecoverySpeed(long settingsVa);

    native static float getPredictiveContactDistance(long settingsVa);

    native private static int getRefCount(long settingsVa);

    native static void getShapeOffset(long settingsVa, FloatBuffer storeFloats);

    native static void setBackFaceMode(long settingsVa, int ordinal);

    native static void setCharacterPadding(long settingsVa, float padding);

    native static void setCollisionTolerance(long settingsVa, float tolerance);

    native private static void setEmbedded(long settingsVa);

    native static void setHitReductionCosMaxAngle(
            long settingsVa, float cosine);

    native static void setInnerBodyLayer(long settingsVa, int objectLayer);

    native static void setInnerBodyShape(long settingsVa, long shapeVa);

    native static void setMass(long settingsVa, float mass);

    native static void setMaxCollisionIterations(
            long settingsVa, int numIterations);

    native static void setMaxConstraintIterations(
            long settingsVa, int numIterations);

    native static void setMaxNumHits(long settingsVa, int numHits);

    native static void setMaxStrength(long settingsVa, float force);

    native static void setMinTimeRemaining(
            long settingsVa, float interval);

    native static void setPenetrationRecoverySpeed(
            long settingsVa, float fraction);

    native static void setPredictiveContactDistance(
            long settingsVa, float distance);

    native static void setShapeOffset(
            long settingsVa, float x, float y, float z);

    native private static long toRef(long settingsVa);
}
