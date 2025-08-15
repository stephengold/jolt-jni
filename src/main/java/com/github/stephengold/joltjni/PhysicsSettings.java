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

import com.github.stephengold.joltjni.readonly.ConstPhysicsSettings;

/**
 * A component of a {@code PhysicsSystem}, used to configure simulation
 * parameters.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class PhysicsSettings
        extends JoltPhysicsObject
        implements ConstPhysicsSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public PhysicsSettings() {
        long settingsVa = createDefault();
        setVirtualAddress(settingsVa, () -> free(settingsVa));
    }

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    PhysicsSettings(long settingsVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(settingsVa) : null;
        setVirtualAddress(settingsVa, freeingAction);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public PhysicsSettings(ConstPhysicsSettings original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa, () -> free(copyVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the size of the body pairs array, which corresponds to
     * the maximum number of pairs which can be in flight at a time.
     * (native attribute: mMaxInFlightBodyPairs)
     *
     * @param pairs the size (&ge;0, default=16384)
     */
    public void setMaxInFlightBodyPairs(int pairs) {
        long settingsVa = va();
        setMaxInFlightBodyPairs(settingsVa, pairs);
    }

    /**
     * Alter the number of step listeners to notify in each batch.
     * (native attribute: mStepListenersBatchSize)
     *
     * @param size the batch size (&ge;0, default=8)
     */
    public void setStepListenersBatchSize(int size) {
        long settingsVa = va();
        setStepListenersBatchSize(settingsVa, size);
    }

    /**
     * Alter the number of step listener batches that can be allocated to one
     * job before spawning another. Set to {@link Integer#MAX_VALUE} for no
     * parallelism. (native attribute: mStepListenerBatchesPerJob)
     *
     * @param batches the number (&ge;0, default=1)
     */
    public void setStepListenerBatchesPerJob(int batches) {
        long settingsVa = va();
        setStepListenerBatchesPerJob(settingsVa, batches);
    }

    /**
     * Alter the Baumgarte stabilization factor, the fraction of position error
     * to correct in each update. (native attribute: mBaumgarte)
     *
     * @param fraction the desired factor (&ge;0, &le;1, default=0.2)
     */
    public void setBaumgarte(float fraction) {
        long settingsVa = va();
        setBaumgarte(settingsVa, fraction);
    }

    /**
     * Alter the speculative contact distance.
     * (native attribute: mSpeculativeContactDistance)
     *
     * @param distance the desired distance (in meters, ≥0, default=0.02)
     */
    public void setSpeculativeContactDistance(float distance) {
        long settingsVa = va();
        setSpeculativeContactDistance(settingsVa, distance);
    }

    /**
     * Alter the penetration slop. (native attribute: mPenetrationSlop)
     *
     * @param slop the desired slop distance (in meters, default=0.02)
     */
    public void setPenetrationSlop(float slop) {
        long settingsVa = va();
        setPenetrationSlop(settingsVa, slop);
    }

    /**
     * Alter the linear cast threshold, the fraction of a body's inner radius it
     * must move per step to enable casting for the LinearCast motion quality.
     * (native attribute: mLinearCastThreshold)
     *
     * @param threshold the threshold (in meters, &ge;0, default=0.75)
     */
    public void setLinearCastThreshold(float threshold) {
        long settingsVa = va();
        setLinearCastThreshold(settingsVa, threshold);
    }

    /**
     * Alter the maximum penetration of a linear cast, which is the fraction of
     * a body's inner radius that may penetrate another body.
     * (native attribute: mLinearCastMaxPenetration)
     *
     * @param distance the distance (in meters, &ge;0, default=0.25)
     */
    public void setLinearCastMaxPenetration(float distance) {
        long settingsVa = va();
        setLinearCastMaxPenetration(settingsVa, distance);
    }

    /**
     * Alter the maximum distance used to determine if two points are on the
     * same plane for determining the contact manifold between two faces.
     * (native attribute: mManifoldTolerance)
     *
     * @param tolerance the distance (in meters, &ge;0, default=1e-3)
     */
    public void setManifoldTolerance(float tolerance) {
        long settingsVa = va();
        setManifoldTolerance(settingsVa, tolerance);
    }

    /**
     * Alter the maximum distance to correct in a single iteration when solving
     * position constraints. (native attribute: mMaxPenetrationDistance)
     *
     * @param distance the distance (in meters, &ge;0, default=0.2)
     */
    public void setMaxPenetrationDistance(float distance) {
        long settingsVa = va();
        setMaxPenetrationDistance(settingsVa, distance);
    }

    /**
     * Alter the maximum relative delta position for body pairs to be able to
     * reuse collision results from last frame.
     * (native attribute: mBodyPairCacheMaxDeltaPositionSq)
     *
     * @param delta the delta (in meters^2, &ge;0, default=0.001^2)
     */
    public void setBodyPairCacheMaxDeltaPositionSq(float delta) {
        long settingsVa = va();
        setBodyPairCacheMaxDeltaPositionSq(settingsVa, delta);
    }

    /**
     * Alter the maximum relative delta orientation for body pairs to be able to
     * reuse collision results from last frame.
     * (native attribute: mBodyPairCacheCosMaxDeltaRotationDiv2)
     *
     * @param delta the delta (as cos(max / 2), &ge;0, default=cos(2 deg / 2))
     */
    public void setBodyPairCacheCosMaxDeltaRotationDiv2(float delta) {
        long settingsVa = va();
        setBodyPairCacheCosMaxDeltaRotationDiv2(settingsVa, delta);
    }

    /**
     * Alter the maximum angle between normals that allows manifolds between
     * different sub shapes of the same body pair to be combined.
     * (native attribute: mContactNormalCosMaxDeltaRotation)
     *
     * @param delta the angle (as cos(angle), &ge;0, default=cos(5 deg))
     */
    public void setContactNormalCosMaxDeltaRotation(float delta) {
        long settingsVa = va();
        setContactNormalCosMaxDeltaRotation(settingsVa, delta);
    }

    /**
     * Alter the maximum allowed distance between old and new contact points to
     * preserve contact forces for warm start.
     * (native attribute: mContactPointPreserveLambdaMaxDistSq)
     *
     * @param distance the distance (in meters^2, &ge;0, default=0.01^2)
     */
    public void setContactPointPreserveLambdaMaxDistSq(float distance) {
        long settingsVa = va();
        setContactPointPreserveLambdaMaxDistSq(settingsVa, distance);
    }

    /**
     * Alter the number of velocity steps. (native attribute: mNumVelocitySteps)
     *
     * @param numSteps the desired number (&ge;0, default=10)
     */
    public void setNumVelocitySteps(int numSteps) {
        long settingsVa = va();
        setNumVelocitySteps(settingsVa, numSteps);
    }

    /**
     * Alter the number of solver position iterations per simulation step.
     * (native attribute: mNumPositionSteps)
     *
     * @param numSteps the desired number (&ge;0, default=2)
     */
    public void setNumPositionSteps(int numSteps) {
        long settingsVa = va();
        setNumPositionSteps(settingsVa, numSteps);
    }

    /**
     * Alter the minimum velocity needed for a collision to be elastic. If the
     * relative velocity between colliding objects in the direction of the
     * contact normal is lower than this, the restitution will be zero
     * regardless of the configured value. This lets an object settle sooner.
     * (native attribute: mMinVelocityForRestitution)
     *
     * @param velocity the velocity (in meters, &gt;0, default=1)
     */
    public void setMinVelocityForRestitution(float velocity) {
        long settingsVa = va();
        setMinVelocityForRestitution(settingsVa, velocity);
    }

    /**
     * Alter the time interval before an object can fall asleep. (native
     * attribute: mTimeBeforeSleep)
     *
     * @param interval the desired time interval (in seconds, &ge;0,
     * default=0.5)
     */
    public void setTimeBeforeSleep(float interval) {
        long settingsVa = va();
        setTimeBeforeSleep(settingsVa, interval);
    }

    /**
     * Alter the point-motion threshold, below which an object can fall asleep.
     * (native attribute: mPointVelocitySleepThreshold)
     *
     * @param speed the desired speed threshold (in meters per second, &ge;0,
     * default=0.03)
     */
    public void setPointVelocitySleepThreshold(float speed) {
        long settingsVa = va();
        setPointVelocitySleepThreshold(settingsVa, speed);
    }

    /**
     * Alter whether physics simulation is deterministic. (native attribute:
     * mDeterministicSimulation)
     *
     * @param setting {@code true} to be deterministic, {@code false} to relax
     * this policy (default=true)
     */
    public void setDeterministicSimulation(boolean setting) {
        long settingsVa = va();
        setDeterministicSimulation(settingsVa, setting);
    }

    /**
     * Alter whether warm starting for constraints (initially applying previous
     * frames impulses) should be used. (native attribute: mConstraintWarmStart)
     *
     * @param setting {@code true} to use, {@code false} to not use
     */
    public void setConstraintWarmStart(boolean setting) {
        long settingsVa = va();
        setConstraintWarmStart(settingsVa, setting);
    }

    /**
     * Alter whether to use the body pair cache, which removes the need for
     * narrow phase collision detection when orientation between two bodies
     * didn't change. (native attribute: mUseBodyPairContactCache)
     *
     * @param setting {@code true} to enable caching, {@code false} to disable
     */
    public void setUseBodyPairContactCache(boolean setting) {
        long settingsVa = va();
        setUseBodyPairContactCache(settingsVa, setting);
    }

    /**
     * Alter whether to reduce manifolds with similar contact normals into one
     * contact manifold. (native attribute: mUseManifoldReduction)
     *
     * @param setting {@code true} to enable reduction, {@code false} to disable
     */
    public void setUseManifoldReduction(boolean setting) {
        long settingsVa = va();
        setUseManifoldReduction(settingsVa, setting);
    }

    /**
     * Alter whether large islands should be split into smaller parallel batches
     * of work. (native attribute: mUseLargeIslandSplitter)
     *
     * @param setting {@code true} to enable splitting, {@code false} to disable
     */
    public void setUseLargeIslandSplitter(boolean setting) {
        long settingsVa = va();
        setUseLargeIslandSplitter(settingsVa, setting);
    }

    /**
     * Alter whether objects can go to sleep. (native attribute: mAllowSleeping)
     *
     * @param allow {@code true} to allow sleeping, {@code false} to disallow it
     * (default=true)
     */
    public void setAllowSleeping(boolean allow) {
        long settingsVa = va();
        setAllowSleeping(settingsVa, allow);
    }

    /**
     * Alter whether collision against non-active (shared) edges is allowed.
     * Mainly for debugging the algorithm. (native attribute: mCheckActiveEdges)
     *
     * @param check {@code true} to check, {@code false} to not check
     */
    public void setCheckActiveEdges(boolean check) {
        long settingsVa = va();
        setCheckActiveEdges(settingsVa, check);
    }

    // *************************************************************************
    // ConstPhysicsSettings methods

    /**
     * Get the size of the body pairs array, which corresponds to
     * the maximum number of pairs which can be in flight at a time.
     * The settings are unaffected. (native attribute: mMaxInFlightBodyPairs)
     *
     * @return the size (&ge;0)
     */
    @Override
    public int getMaxInFlightBodyPairs() {
        long settingsVa = va();
        int result = getMaxInFlightBodyPairs(settingsVa);
        assert result >= 0 : result;
        return result;
    }

    /**
     * Get the number of step listeners to notify in each batch. The settings
     * are unaffected. (native attribute: mStepListenersBatchSize)
     *
     * @return the batch size (&ge;0)
     */
    @Override
    public int getStepListenersBatchSize() {
        long settingsVa = va();
        int result = getStepListenersBatchSize(settingsVa);
        assert result >= 0 : result;
        return result;
    }

    /**
     * Get the number of step listener batches that can be allocated to one job
     * before spawning another. Set to {@link Integer#MAX_VALUE} for no
     * parallelism. The settings are unaffected.
     * (native attribute: mStepListenerBatchesPerJob)
     *
     * @return the number (&ge;0)
     */
    @Override
    public int getStepListenerBatchesPerJob() {
        long settingsVa = va();
        int result = getStepListenerBatchesPerJob(settingsVa);
        assert result >= 0 : result;
        return result;
    }

    /**
     * Return the Baumgarte stabilization factor, the fraction of position error
     * that is corrected in each update. The settings are unaffected. (native
     * attribute: mBaumgarte)
     *
     * @return the factor (&ge;0, &le;1)
     */
    @Override
    public float getBaumgarte() {
        long settingsVa = va();
        float result = getBaumgarte(settingsVa);

        assert result >= 0f && result <= 1f : result;
        return result;
    }

    /**
     * Return the speculative contact distance. The settings are unaffected.
     * (native attribute: mSpeculativeContactDistance)
     *
     * @return the distance (in meters, ≥0)
     */
    @Override
    public float getSpeculativeContactDistance() {
        long settingsVa = va();
        float result = getSpeculativeContactDistance(settingsVa);
        assert result >= 0f : result;
        return result;
    }

    /**
     * Return the penetration slop. The settings are unaffected. (native
     * attribute: mPenetrationSlop)
     *
     * @return the slop distance (in meters)
     */
    @Override
    public float getPenetrationSlop() {
        long settingsVa = va();
        float result = getPenetrationSlop(settingsVa);

        return result;
    }

    /**
     * Return the linear cast threshold, the fraction of a body's inner radius
     * it must move per step to enable casting for the LinearCast motion
     * quality. The settings are unaffected.
     * (native attribute: mLinearCastThreshold)
     *
     * @return the threshold (in meters, &ge;0)
     */
    @Override
    public float getLinearCastThreshold() {
        long settingsVa = va();
        float result = getLinearCastThreshold(settingsVa);
        assert result >= 0 : result;
        return result;
    }

    /**
     * Return the maximum penetration of a linear cast, which is the fraction of
     * a body's inner radius that may penetrate another body. The settings are
     * unaffected. (native attribute: mLinearCastMaxPenetration)
     *
     * @return the distance (in meters, &ge;0)
     */
    @Override
    public float getLinearCastMaxPenetration() {
        long settingsVa = va();
        float result = getLinearCastMaxPenetration(settingsVa);
        assert result >= 0 : result;
        return result;
    }

    /**
     * Return the maximum distance used to determine if two points are on the
     * same plane for determining the contact manifold between two faces. The
     * settings are unaffected. (native attribute: mManifoldTolerance)
     *
     * @return the distance (in meters, &ge;0)
     */
    @Override
    public float getManifoldTolerance() {
        long settingsVa = va();
        float result = getManifoldTolerance(settingsVa);
        assert result >= 0 : result;
        return result;
    }

    /**
     * Return the maximum distance to correct in a single iteration when solving
     * position constraints. The settings are unaffected.
     * (native attribute: mMaxPenetrationDistance)
     *
     * @return the distance (in meters, &ge;0)
     */
    @Override
    public float getMaxPenetrationDistance() {
        long settingsVa = va();
        float result = getMaxPenetrationDistance(settingsVa);
        assert result >= 0 : result;
        return result;
    }

    /**
     * Return the maximum relative delta position for body pairs to be able to
     * reuse collision results from last frame. The settings are unaffected.
     * (native attribute: mBodyPairCacheMaxDeltaPositionSq)
     *
     * @return the delta (in meters^2, &ge;0)
     */
    @Override
    public float getBodyPairCacheMaxDeltaPositionSq() {
        long settingsVa = va();
        float result = getBodyPairCacheMaxDeltaPositionSq(settingsVa);
        assert result >= 0 : result;
        return result;
    }

    /**
     * Return the maximum relative delta orientation for body pairs to be able
     * to reuse collision results from last frame. The settings are unaffected.
     * (native attribute: mBodyPairCacheCosMaxDeltaRotationDiv2)
     *
     * @return the delta (as cos(max angle / 2), &ge;0)
     */
    @Override
    public float getBodyPairCacheCosMaxDeltaRotationDiv2() {
        long settingsVa = va();
        float result = getBodyPairCacheCosMaxDeltaRotationDiv2(settingsVa);
        assert result >= 0 : result;
        return result;
    }

    /**
     * Return the maximum angle between normals that allows manifolds between
     * different sub shapes of the same body pair to be combined. The settings
     * are unaffected. (native attribute: mContactNormalCosMaxDeltaRotation)
     *
     * @return the angle (as cos(angle), &ge;0)
     */
    @Override
    public float getContactNormalCosMaxDeltaRotation() {
        long settingsVa = va();
        float result = getContactNormalCosMaxDeltaRotation(settingsVa);
        assert result >= 0 : result;
        return result;
    }

    /**
     * Return the maximum allowed distance between old and new contact points to
     * preserve contact forces for warm start. The settings are unaffected.
     * (native attribute: mContactPointPreserveLambdaMaxDistSq)
     *
     * @return the distance (in meters^2, &ge;0)
     */
    @Override
    public float getContactPointPreserveLambdaMaxDistSq() {
        long settingsVa = va();
        float result = getContactPointPreserveLambdaMaxDistSq(settingsVa);
        assert result >= 0 : result;
        return result;
    }

    /**
     * Return the number of velocity iterations per simulation step. The
     * settings are unaffected. (native attribute: mNumVelocitySteps)
     *
     * @return the number (&ge;0)
     */
    @Override
    public int getNumVelocitySteps() {
        long settingsVa = va();
        int result = getNumVelocitySteps(settingsVa);

        assert result >= 0 : result;
        return result;
    }

    /**
     * Return the number of solver position iterations per simulation step. The
     * settings are unaffected. (native attribute: mNumPositionSteps)
     *
     * @return the number (&ge;0)
     */
    @Override
    public int getNumPositionSteps() {
        long settingsVa = va();
        int result = getNumPositionSteps(settingsVa);

        assert result >= 0 : result;
        return result;
    }

    /**
     * Return the minimum velocity needed for a collision to be elastic. If the
     * relative velocity between colliding objects in the direction of the
     * contact normal is lower than this, the restitution will be zero
     * regardless of the configured value. This lets an object settle sooner.
     * (native attribute: mMinVelocityForRestitution)
     *
     * @return the velocity (in meters, &gt;0)
     */
    @Override
    public float getMinVelocityForRestitution() {
        long settingsVa = va();
        float result = getMinVelocityForRestitution(settingsVa);
        assert result >= 0 : result;
        return result;
    }

    /**
     * Alter the time interval before an object can fall asleep. The settings
     * are unaffected. (native attribute: mTimeBeforeSleep)
     *
     * @return the interval (in seconds, &ge;0)
     */
    @Override
    public float getTimeBeforeSleep() {
        long settingsVa = va();
        float result = getTimeBeforeSleep(settingsVa);

        assert result >= 0f : result;
        return result;
    }

    /**
     * Return the point-motion threshold, below which objects can fall asleep.
     * The settings are unaffected. (native attribute:
     * mPointVelocitySleepThreshold)
     *
     * @return the speed threshold (in meters per second, &ge;0)
     */
    @Override
    public float getPointVelocitySleepThreshold() {
        long settingsVa = va();
        float result = getPointVelocitySleepThreshold(settingsVa);

        assert result >= 0f : result;
        return result;
    }

    /**
     * Test whether physics simulation is deterministic. The settings are
     * unaffected. (native attribute: mDeterministicSimulation)
     *
     * @return {@code true} if it is deterministic, otherwise {@code false}
     */
    @Override
    public boolean getDeterministicSimulation() {
        long settingsVa = va();
        boolean result = getDeterministicSimulation(settingsVa);

        return result;
    }

    /**
     * Test whether warm starting for constraints (initially applying previous
     * frames impulses) should be used. The settings are unaffected.
     * (native attribute: mConstraintWarmStart)
     *
     * @return {@code true} if it should, otherwise {@code false}
     */
    @Override
    public boolean getConstraintWarmStart() {
        long settingsVa = va();
        boolean result = getConstraintWarmStart(settingsVa);

        return result;
    }

    /**
     * Test whether to use the body pair cache, which removes the need for
     * narrow phase collision detection when orientation between two bodies
     * didn't change. The settings are unaffected.
     * (native attribute: mUseBodyPairContactCache)
     *
     * @return {@code true} if it will be used, otherwise {@code false}
     */
    @Override
    public boolean getUseBodyPairContactCache() {
        long settingsVa = va();
        boolean result = getUseBodyPairContactCache(settingsVa);

        return result;
    }

    /**
     * Test whether to reduce manifolds with similar contact normals into one
     * contact manifold. The settings are unaffected.
     * (native attribute: mUseManifoldReduction)
     *
     * @return {@code true} if they will be reduced, otherwise {@code false}
     */
    @Override
    public boolean getUseManifoldReduction() {
        long settingsVa = va();
        boolean result = getUseManifoldReduction(settingsVa);

        return result;
    }

    /**
     * Test whether large islands should be split into smaller parallel batches
     * of work. The settings are unaffected.
     * (native attribute: mUseLargeIslandSplitter)
     *
     * @return {@code true} if they should be split, otherwise {@code false}
     */
    @Override
    public boolean getUseLargeIslandSplitter() {
        long settingsVa = va();
        boolean result = getUseLargeIslandSplitter(settingsVa);

        return result;
    }

    /**
     * Test whether objects can fall asleep. The settings are unaffected.
     * (native attribute: mAllowSleeping)
     *
     * @return {@code true} if sleeping is allowed, otherwise {@code false}
     */
    @Override
    public boolean getAllowSleeping() {
        long settingsVa = va();
        boolean result = getAllowSleeping(settingsVa);

        return result;
    }

    /**
     * Test whether collision against non-active (shared) edges is allowed.
     * Mainly for debugging the algorithm. The settings are unaffected.
     * (native attribute: mCheckActiveEdges)
     *
     * @return {@code true} if allowed, otherwise {@code false}
     */
    @Override
    public boolean getCheckActiveEdges() {
        long settingsVa = va();
        boolean result = getCheckActiveEdges(settingsVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static void free(long settingsVa);

    native private static int getMaxInFlightBodyPairs(
            long settingsVa);

    native private static int getStepListenersBatchSize(
            long settingsVa);

    native private static int getStepListenerBatchesPerJob(
            long settingsVa);

    native private static float getBaumgarte(long settingsVa);

    native private static float getSpeculativeContactDistance(long settingsVa);

    native private static float getPenetrationSlop(long settingsVa);

    native private static float getLinearCastThreshold(
            long settingsVa);

    native private static float getLinearCastMaxPenetration(
            long settingsVa);

    native private static float getManifoldTolerance(
            long settingsVa);

    native private static float getMaxPenetrationDistance(
            long settingsVa);

    native private static float getBodyPairCacheMaxDeltaPositionSq(
            long settingsVa);

    native private static float getBodyPairCacheCosMaxDeltaRotationDiv2(
            long settingsVa);

    native private static float getContactNormalCosMaxDeltaRotation(
            long settingsVa);

    native private static float getContactPointPreserveLambdaMaxDistSq(
            long settingsVa);

    native private static int getNumVelocitySteps(long settingsVa);

    native private static int getNumPositionSteps(long settingsVa);

    native private static float getMinVelocityForRestitution(
            long settingsVa);

    native private static float getTimeBeforeSleep(long settingsVa);

    native private static float getPointVelocitySleepThreshold(
            long settingsVa);

    native private static boolean getDeterministicSimulation(
            long settingsVa);

    native private static boolean getConstraintWarmStart(
            long settingsVa);

    native private static boolean getUseBodyPairContactCache(
            long settingsVa);

    native private static boolean getUseManifoldReduction(
            long settingsVa);

    native private static boolean getUseLargeIslandSplitter(
            long settingsVa);

    native private static boolean getAllowSleeping(long settingsVa);

    native private static boolean getCheckActiveEdges(
            long settingsVa);

    native private static void setMaxInFlightBodyPairs(
            long settingsVa, int pairs);

    native private static void setStepListenersBatchSize(
            long settingsVa, int size);

    native private static void setStepListenerBatchesPerJob(
            long settingsVa, int batches);

    native private static void setBaumgarte(
            long settingsVa, float fraction);

    native private static void setSpeculativeContactDistance(
            long settingsVa, float distance);

    native private static void setPenetrationSlop(
            long settingsVa, float slop);

    native private static void setLinearCastThreshold(
            long settingsVa, float threshold);

    native private static void setLinearCastMaxPenetration(
            long settingsVa, float penetration);

    native private static void setManifoldTolerance(
            long settingsVa, float tolerance);

    native private static void setMaxPenetrationDistance(
            long settingsVa, float distance);

    native private static void setBodyPairCacheMaxDeltaPositionSq(
            long settingsVa, float delta);

    native private static void setBodyPairCacheCosMaxDeltaRotationDiv2(
            long settingsVa, float delta);

    native private static void setContactNormalCosMaxDeltaRotation(
            long settingsVa, float delta);

    native private static void setContactPointPreserveLambdaMaxDistSq(
            long settingsVa, float distance);

    native private static void setNumVelocitySteps(
            long settingsVa, int numSteps);

    native private static void setNumPositionSteps(
            long settingsVa, int numSteps);

    native private static void setMinVelocityForRestitution(
            long settingsVa, float velocity);

    native private static void setTimeBeforeSleep(
            long settingsVa, float interval);

    native private static void setPointVelocitySleepThreshold(
            long settingsVa, float speed);

    native private static void setDeterministicSimulation(
            long settingsVa, boolean setting);

    native private static void setConstraintWarmStart(
            long settingsVa, boolean setting);

    native private static void setUseBodyPairContactCache(
            long settingsVa, boolean setting);

    native private static void setUseManifoldReduction(
            long settingsVa, boolean setting);

    native private static void setUseLargeIslandSplitter(
            long settingsVa, boolean setting);

    native private static void setAllowSleeping(
            long settingsVa, boolean allow);

    native private static void setCheckActiveEdges(
            long settingsVa, boolean check);
}
