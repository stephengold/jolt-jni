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

import com.github.stephengold.joltjni.readonly.ConstGradient;
import com.github.stephengold.joltjni.readonly.ConstHairMaterial;
import java.nio.FloatBuffer;

/**
 * Simulation parameters for a hair strand. (native type:
 * {@code HairSettings::Material})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class HairMaterial
        extends JoltPhysicsObject
        implements ConstHairMaterial {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default material.
     */
    public HairMaterial() {
        long materialVa = createDefault();
        setVirtualAddress(materialVa, () -> free(materialVa));
    }

    /**
     * Instantiate a copy of the specified material.
     *
     * @param original the material to copy (not {@code null}, unaffected)
     */
    public HairMaterial(ConstHairMaterial original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        Runnable freeingAction = () -> free(copyVa);
        setVirtualAddress(copyVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the angular damping coefficient of the rods. (native attribute:
     * mAngularDamping)
     *
     * @param damping the desired coefficient (default=2)
     * @return the modified material, for chaining
     */
    public HairMaterial setAngularDamping(float damping) {
        long materialVa = va();
        setAngularDamping(materialVa, damping);

        return this;
    }

    /**
     * Alter the compliance of bend constraints, the inverse of bend stiffness.
     * (native attribute: mBendCompliance)
     *
     * @param compliance the desired compliance (default=10^-7)
     * @return the modified material, for chaining
     */
    public HairMaterial setBendCompliance(float compliance) {
        long materialVa = va();
        setBendCompliance(materialVa, compliance);

        return this;
    }

    /**
     * Alter the multiples of bend compliance to be applied to the strand at 0%,
     * 33%, 66% and 100% of its length. (native attribute:
     * mBendComplianceMultiplier)
     *
     * @param multipliers the desired multipliers (not {@code null}, unaffected,
     * default=(1,100,100,1))
     * @return the modified material, for chaining
     */
    public HairMaterial setBendComplianceMultiplier(Float4 multipliers) {
        long materialVa = va();
        float x = multipliers.x;
        float y = multipliers.y;
        float z = multipliers.z;
        float w = multipliers.w;
        setBendComplianceMultiplier(materialVa, x, y, z, w);

        return this;
    }

    /**
     * Enable or disable collisions between hair strands and the environment.
     * (native attribute: mEnableCollision)
     *
     * @param setting {@code true} to enable collisions, {@code false} to
     * disable them (default=true)
     * @return the modified material, for chaining
     */
    public HairMaterial setEnableCollision(boolean setting) {
        long materialVa = va();
        setEnableCollision(materialVa, setting);

        return this;
    }

    /**
     * Enable or disable long-range attachments to keep hair near its modeled
     * pose. (native attribute: mEnableLRA)
     *
     * @param setting {@code true} to enable attachments, {@code false} to
     * disable them (default=true)
     * @return the modified material, for chaining
     */
    public HairMaterial setEnableLra(boolean setting) {
        long materialVa = va();
        setEnableLra(materialVa, setting);

        return this;
    }

    /**
     * Alter the friction coefficient for collisions between hair strands and
     * the environment. (native attribute: mFriction)
     *
     * @param friction the desired friction coefficient (default=0.2)
     * @return the modified material, for chaining
     */
    public HairMaterial setFriction(float friction) {
        long materialVa = va();
        setFriction(materialVa, friction);

        return this;
    }

    /**
     * Alter the fraction of the neutral pose applied to hair during each
     * iteration. (native attribute: mGlobalPose)
     *
     * @param gradient the desired variation of the pose fraction along each
     * strand (not {@code null}, unaffected, default=(0.01,0,0,0.3))
     * @return the modified material, for chaining
     */
    public HairMaterial setGlobalPose(ConstGradient gradient) {
        long materialVa = va();
        long gradientVa = gradient.targetVa();
        setGlobalPose(materialVa, gradientVa);

        return this;
    }

    /**
     * Alter the fraction of gravity to be applied to the hair during
     * simulation. (native attribute: mGravityFactor)
     *
     * @param gradient the desired variation of the gravity factor along each
     * strand (not {@code null}, unaffected, default=(0.1,1,0.2,0.8))
     * @return the modified material, for chaining
     */
    public HairMaterial setGravityFactor(ConstGradient gradient) {
        long materialVa = va();
        long gradientVa = gradient.targetVa();
        setGravityFactor(materialVa, gradientVa);

        return this;
    }

    /**
     * Alter the fraction of gravity to be removed during initialization.
     * (native attribute: mGravityPreloadFactor)
     *
     * @param factor the desired fraction to remove (default=0)
     * @return the modified material, for chaining
     */
    public HairMaterial setGravityPreloadFactor(float factor) {
        long materialVa = va();
        setGravityPreloadFactor(materialVa, factor);

        return this;
    }

    /**
     * Alter the force factor pushing the hair toward neutral density. (native
     * attribute: mGridDensityForceFactor)
     *
     * @param factor the desired force factor (default=0)
     * @return the modified material, for chaining
     */
    public HairMaterial setGridDensityForceFactor(float factor) {
        long materialVa = va();
        setGridDensityForceFactor(materialVa, factor);

        return this;
    }

    /**
     * Alter the fraction of grid velocity to be applied to the hair. (native
     * attribute: mGridVelocityFactor)
     *
     * @param gradient the desired variation of the grid influence along each
     * strand (not {@code null}, unaffected, default=(0.05,0.01))
     * @return the modified material, for chaining
     */
    public HairMaterial setGridVelocityFactor(ConstGradient gradient) {
        long materialVa = va();
        long gradientVa = gradient.targetVa();
        setGridVelocityFactor(materialVa, gradientVa);

        return this;
    }

    /**
     * Alter the radius of the hair strand. (native attribute: mHairRadius)
     *
     * @param gradient the desired variation of each strand's radius along its
     * length (in meters, not {@code null}, unaffected, default=(0.001,0.001))
     * @return the modified material, for chaining
     */
    public HairMaterial setHairRadius(ConstGradient gradient) {
        long materialVa = va();
        long gradientVa = gradient.targetVa();
        setHairRadius(materialVa, gradientVa);

        return this;
    }

    /**
     * Alter the inertia of each rod as a multiple of its mass. (native
     * attribute: mInertiaMultiplier)
     *
     * @param multiplier the desired ratio (default=10)
     * @return the modified material, for chaining
     */
    public HairMaterial setInertiaMultiplier(float multiplier) {
        long materialVa = va();
        setInertiaMultiplier(materialVa, multiplier);

        return this;
    }

    /**
     * Alter the linear damping coefficient of the rods. (native attribute:
     * mLinearDamping)
     *
     * @param damping the desired coefficient (default=2)
     * @return the modified material, for chaining
     */
    public HairMaterial setLinearDamping(float damping) {
        long materialVa = va();
        setLinearDamping(materialVa, damping);

        return this;
    }

    /**
     * Alter the maximum angular rate of vertices. (native attribute:
     * mMaxAngularVelocity)
     *
     * @param angularRate the desired rate limit (in radians per second,
     * default=50)
     * @return the modified material, for chaining
     */
    public HairMaterial setMaxAngularVelocity(float angularRate) {
        long materialVa = va();
        setMaxAngularVelocity(materialVa, angularRate);

        return this;
    }

    /**
     * Alter the maximum speed of vertices. (native attribute:
     * mMaxLinearVelocity)
     *
     * @param speed the desired speed (in meters per second, default=10)
     * @return the modified material, for chaining
     */
    public HairMaterial setMaxLinearVelocity(float speed) {
        long materialVa = va();
        setMaxLinearVelocity(materialVa, speed);

        return this;
    }

    /**
     * Alter fraction of strands that should be simulated. Used by
     * {@code InitRenderAndSimulationStrands} only. (native attribute:
     * mSimulationStrandsFraction)
     *
     * @param fraction the desired fraction (default=0.1)
     * @return the modified material, for chaining
     */
    public HairMaterial setSimulationStrandsFraction(float fraction) {
        long materialVa = va();
        setSimulationStrandsFraction(materialVa, fraction);

        return this;
    }

    /**
     * Alter how strongly the scalp influences the global pose. (native
     * attribute: mSkinGlobalPose)
     *
     * @param gradient the desired variation of influence along each strand (not
     * {@code null}, unaffected, 0=no influence, 1=fully following,
     * default=(1,0,0,0.1))
     * @return the modified material, for chaining
     */
    public HairMaterial setSkinGlobalPose(ConstGradient gradient) {
        long materialVa = va();
        long gradientVa = gradient.targetVa();
        setSkinGlobalPose(materialVa, gradientVa);

        return this;
    }

    /**
     * Alter the compliance of stretch constraints, the inverse of stretch
     * stiffness. (native attribute: mStretchCompliance)
     *
     * @param compliance the desired compliance (default=10^-8)
     * @return the modified material, for chaining
     */
    public HairMaterial setStretchCompliance(float compliance) {
        long materialVa = va();
        setStretchCompliance(materialVa, compliance);

        return this;
    }

    /**
     * Alter the influence of head rotation on hair. (native attribute:
     * mWorldTransformInfluence)
     *
     * @param gradient the desired variation of influence along each strand (not
     * {@code null}, unaffected, 0=no influence, 1=fully following,
     * default=(0,1))
     * @return the modified material, for chaining
     */
    public HairMaterial setWorldTransformInfluence(ConstGradient gradient) {
        long materialVa = va();
        long gradientVa = gradient.targetVa();
        setWorldTransformInfluence(materialVa, gradientVa);

        return this;
    }
    // *************************************************************************
    // ConstHairMaterial methods

    /**
     * Return the angular damping coefficient of the rods. The material is
     * unaffected. (native attribute: mAngularDamping)
     *
     * @return the coefficient
     */
    @Override
    public float getAngularDamping() {
        long materialVa = va();
        float result = getAngularDamping(materialVa);
        return result;
    }

    /**
     * Return the compliance of bend constraints, the inverse of bend stiffness.
     * The material is unaffected. (native attribute: mBendCompliance)
     *
     * @return the compliance value
     */
    @Override
    public float getBendCompliance() {
        long materialVa = va();
        float result = getBendCompliance(materialVa);
        return result;
    }

    /**
     * Copy the multiples of bend compliance to be applied to the strand at 0%,
     * 33%, 66% and 100% of its length. (native attribute:
     * mBendComplianceMultiplier)
     *
     * @return a new object
     */
    @Override
    public Float4 getBendComplianceMultiplier() {
        long materialVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getBendComplianceMultiplier(materialVa, storeFloats);
        Float4 result = new Float4(storeFloats);

        return result;
    }

    /**
     * Test whether collisions between hair strands and the environment are
     * enabled. The material is unaffected. (native attribute: mEnableCollision)
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    @Override
    public boolean getEnableCollision() {
        long materialVa = va();
        boolean result = getEnableCollision(materialVa);
        return result;
    }

    /**
     * Test whether long-range attachments are enabled to keep hair near its
     * modeled pose. The material is unaffected. (native attribute: mEnableLRA)
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    @Override
    public boolean getEnableLra() {
        long materialVa = va();
        boolean result = getEnableLra(materialVa);
        return result;
    }

    /**
     * Return the friction coefficient for collisions between hair strands and
     * the environment. The material is unaffected. (native attribute:
     * mFriction)
     *
     * @return the friction coefficient
     */
    @Override
    public float getFriction() {
        long materialVa = va();
        float result = getFriction(materialVa);
        return result;
    }

    /**
     * Access the fraction of the neutral pose applied to hair during each
     * iteration. (native attribute: mGlobalPose)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public Gradient getGlobalPose() {
        long materialVa = va();
        long resultVa = getGlobalPose(materialVa);
        Gradient result = new Gradient(this, resultVa);

        return result;
    }

    /**
     * Access the fraction of gravity to be applied to the hair during
     * simulation. (native attribute: mGravityFactor)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public Gradient getGravityFactor() {
        long materialVa = va();
        long resultVa = getGravityFactor(materialVa);
        Gradient result = new Gradient(this, resultVa);

        return result;
    }

    /**
     * Return the fraction of gravity to be removed during initialization. The
     * material is unaffected. (native attribute: mGravityPreloadFactor)
     *
     * @return the fraction
     */
    @Override
    public float getGravityPreloadFactor() {
        long materialVa = va();
        float result = getGravityPreloadFactor(materialVa);

        return result;
    }

    /**
     * Return the force factor pushing the hair toward neutral density. The
     * material is unaffected. (native attribute: mGridDensityForceFactor)
     *
     * @return the force factor
     */
    @Override
    public float getGridDensityForceFactor() {
        long materialVa = va();
        float result = getGridDensityForceFactor(materialVa);

        return result;
    }

    /**
     * Access the fraction of grid velocity to be applied to the hair. (native
     * attribute: mGridVelocityFactor)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public Gradient getGridVelocityFactor() {
        long materialVa = va();
        long resultVa = getGridVelocityFactor(materialVa);
        Gradient result = new Gradient(this, resultVa);

        return result;
    }

    /**
     * Access the radius of the hair strand. (native attribute: mHairRadius)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public Gradient getHairRadius() {
        long materialVa = va();
        long resultVa = getHairRadius(materialVa);
        Gradient result = new Gradient(this, resultVa);

        return result;
    }

    /**
     * Return the inertia of each rod as a multiple of its mass. The material is
     * unaffected. (native attribute: mInertiaMultiplier)
     *
     * @return the ratio
     */
    @Override
    public float getInertiaMultiplier() {
        long materialVa = va();
        float result = getInertiaMultiplier(materialVa);

        return result;
    }

    /**
     * Return the linear damping coefficient of the rods. The material is
     * unaffected. (native attribute: mLinearDamping)
     *
     * @return the damping coefficient
     */
    @Override
    public float getLinearDamping() {
        long materialVa = va();
        float result = getLinearDamping(materialVa);

        return result;
    }

    /**
     * Return the maximum angular rate of vertices. The material is unaffected.
     * (native attribute: mMaxAngularVelocity)
     *
     * @return the rate limit (in radians per second)
     */
    @Override
    public float getMaxAngularVelocity() {
        long materialVa = va();
        float result = getMaxAngularVelocity(materialVa);

        return result;
    }

    /**
     * Return the maximum speed of vertices. The material is unaffected. (native
     * attribute: mMaxLinearVelocity)
     *
     * @return the speed limit (in meters per second)
     */
    @Override
    public float getMaxLinearVelocity() {
        long materialVa = va();
        float result = getMaxLinearVelocity(materialVa);

        return result;
    }

    /**
     * Return the fraction of strands that should be simulated. Used by
     * {@code InitRenderAndSimulationStrands} only. The material is unaffected.
     * (native attribute: mSimulationStrandsFraction)
     *
     * @return the fraction
     */
    @Override
    public float getSimulationStrandsFraction() {
        long materialVa = va();
        float result = getSimulationStrandsFraction(materialVa);

        return result;
    }

    /**
     * Access how strongly the scalp influences the global pose. (native
     * attribute: mSkinGlobalPose)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public Gradient getSkinGlobalPose() {
        long materialVa = va();
        long resultVa = getSkinGlobalPose(materialVa);
        Gradient result = new Gradient(this, resultVa);

        return result;
    }

    /**
     * Return the compliance of stretch constraints, the inverse of stretch
     * stiffness. The material is unaffected. (native attribute:
     * mStretchCompliance)
     *
     * @return the compliance value
     */
    @Override
    public float getStretchCompliance() {
        long materialVa = va();
        float result = getStretchCompliance(materialVa);

        return result;
    }

    /**
     * Access the influence of head rotation on hair. (native attribute:
     * mWorldTransformInfluence)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public Gradient getWorldTransformInfluence() {
        long materialVa = va();
        long resultVa = getWorldTransformInfluence(materialVa);
        Gradient result = new Gradient(this, resultVa);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static void free(long materialVa);

    native private static float getAngularDamping(long materialVa);

    native private static float getBendCompliance(long materialVa);

    native private static void getBendComplianceMultiplier(
            long materialVa, FloatBuffer storeFloats);

    native private static boolean getEnableCollision(long materialVa);

    native private static boolean getEnableLra(long materialVa);

    native private static float getFriction(long materialVa);

    native private static long getGlobalPose(long materialVa);

    native private static long getGravityFactor(long materialVa);

    native private static float getGravityPreloadFactor(long materialVa);

    native private static float getGridDensityForceFactor(long materialVa);

    native private static long getGridVelocityFactor(long materialVa);

    native private static long getHairRadius(long materialVa);

    native private static float getInertiaMultiplier(long materialVa);

    native private static float getLinearDamping(long materialVa);

    native private static float getMaxAngularVelocity(long materialVa);

    native private static float getMaxLinearVelocity(long materialVa);

    native private static float getSimulationStrandsFraction(long materialVa);

    native private static long getSkinGlobalPose(long materialVa);

    native private static float getStretchCompliance(long materialVa);

    native private static long getWorldTransformInfluence(long materialVa);

    native private static void setAngularDamping(
            long materialVa, float damping);

    native private static void setBendCompliance(
            long materialVa, float compliance);

    native private static void setBendComplianceMultiplier(
            long materialVa, float x, float y, float z, float w);

    native private static void setEnableCollision(
            long materialVa, boolean setting);

    native private static void setEnableLra(long materialVa, boolean setting);

    native private static void setFriction(long materialVa, float friction);

    native private static void setGlobalPose(long materialVa, long gradientVa);

    native private static void setGravityFactor(
            long materialVa, long gradientVa);

    native private static void setGravityPreloadFactor(
            long materialVa, float factor);

    native private static void setGridDensityForceFactor(
            long materialVa, float factor);

    native private static void setGridVelocityFactor(
            long materialVa, long gradientVa);

    native private static void setHairRadius(long materialVa, long gradientVa);

    native private static void setInertiaMultiplier(
            long materialVa, float multiplier);

    native private static void setLinearDamping(long materialVa, float damping);

    native private static void setMaxAngularVelocity(
            long materialVa, float angularRate);

    native private static void setMaxLinearVelocity(
            long materialVa, float speed);

    native private static void setSimulationStrandsFraction(
            long materialVa, float fraction);

    native private static void setSkinGlobalPose(
            long materialVa, long gradientVa);

    native private static void setStretchCompliance(
            long materialVa, float compliance);

    native private static void setWorldTransformInfluence(
            long materialVa, long gradientVa);
}
