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
     * Alter whether objects can fall asleep. (native member: mAllowSleeping)
     *
     * @param allow {@code true} to allow sleeping, {@code false} to disallow it
     * (default=true)
     */
    public void setAllowSleeping(boolean allow) {
        long settingsVa = va();
        setAllowSleeping(settingsVa, allow);
    }

    /**
     * Alter the Baumgarte stabilization factor, the fraction of position error
     * to correct in each update. (native member: mBaumgarte)
     *
     * @param fraction the desired factor (&ge;0, &le;1, default=0.2)
     */
    public void setBaumgarte(float fraction) {
        assert fraction >= 0f && fraction <= 1f : fraction;

        long settingsVa = va();
        setBaumgarte(settingsVa, fraction);
    }

    /**
     * Return the maximum rotation to reuse collision results from the previous
     * step. The settings are unaffected. (native member:
     * mBodyPairCacheCosMaxDeltaRotationDiv2)
     *
     * @param cosineOfHalfAngle the cosine of half the desired maximum rotation
     * angle (&ge;0, &le;1, default=cos(pi/180))
     */
    public void setBodyPairCacheCosMaxDeltaRotationDiv2(
            float cosineOfHalfAngle) {
        assert cosineOfHalfAngle >= 0f && cosineOfHalfAngle <= 1f :
                cosineOfHalfAngle;

        long settingsVa = va();
        setBodyPairCacheCosMaxDeltaRotationDiv2(settingsVa, cosineOfHalfAngle);
    }

    /**
     * Alter the maximum translation to reuse collision results from the
     * previous step. (native member: mBodyPairCacheMaxDeltaPositionSq)
     *
     * @param squaredDistance the desired squared distance (in square meters,
     * &ge;0, default=1e-6)
     */
    public void setBodyPairCacheMaxDeltaPositionSq(float squaredDistance) {
        assert squaredDistance >= 0f : squaredDistance;

        long settingsVa = va();
        setBodyPairCacheMaxDeltaPositionSq(settingsVa, squaredDistance);
    }

    /**
     * Enable or disable collision checking with non-active (shared) edges.
     * (native member: mCheckActiveEdges)
     *
     * @param check {@code true} to enable checking, or {@code false} to disable
     * it (default=true)
     */
    public void setCheckActiveEdges(boolean check) {
        long settingsVa = va();
        setCheckActiveEdges(settingsVa, check);
    }

    /**
     * Alter whether warm starting for constraints (initially applying previous
     * frames impulses) should be used. (native member: mConstraintWarmStart)
     *
     * @param enable {@code true} to enable warm start, or {@code false} to
     * disable it (default=true)
     */
    public void setConstraintWarmStart(boolean enable) {
        long settingsVa = va();
        setConstraintWarmStart(settingsVa, enable);
    }

    /**
     * Alter the maximum angle between normals to allow combining manifolds.
     * (native member: mContactNormalCosMaxDeltaRotation)
     *
     * @param cosine the cosine of the desired rotation threshold (&ge;-1,
     * &le;1, default=cos(pi/36))
     */
    public void setContactNormalCosMaxDeltaRotation(float cosine) {
        assert cosine >= -1f && cosine <= 1f : cosine;

        long settingsVa = va();
        setContactNormalCosMaxDeltaRotation(settingsVa, cosine);
    }

    /**
     * Alter the maximum translation of a contact point for warm starting.
     * (native member: mContactPointPreserveLambdaMaxDistSq)
     *
     * @param squaredDistance the square of the desired threshold distance (in
     * meters squared, &ge;0, default=1e-4)
     */
    public void setContactPointPreserveLambdaMaxDistSq(float squaredDistance) {
        assert squaredDistance >= 0f : squaredDistance;

        long settingsVa = va();
        setContactPointPreserveLambdaMaxDistSq(settingsVa, squaredDistance);
    }

    /**
     * Alter whether physics simulation is deterministic. (native member:
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
     * Alter the amount of penetration tolerated by a {@code LinearCast} cast.
     * (native member: mLinearCastMaxPenetration)
     *
     * @param distance the penetration distance to tolerate (as a fraction of
     * the body's inner radius, &ge;0, default=0.25)
     */
    public void setLinearCastMaxPenetration(float distance) {
        assert distance >= 0f : distance;

        long settingsVa = va();
        setLinearCastMaxPenetration(settingsVa, distance);
    }

    /**
     * Alter the amount of translation to activate casting for
     * {@code LinearCast} motion quality. (native member: mLinearCastThreshold)
     *
     * @param threshold the desired translation threshold (as a fraction of the
     * body's inner radius, &ge;0, default=0.75)
     */
    public void setLinearCastThreshold(float threshold) {
        assert threshold >= 0f : threshold;

        long settingsVa = va();
        setLinearCastThreshold(settingsVa, threshold);
    }

    /**
     * Alter the tolerance for 2 points to be considered coplanar when
     * determining the contact manifold between two faces. (native member:
     * mManifoldTolerance)
     *
     * @param tolerance the distance (in meters, &ge;0, default=1e-3)
     */
    public void setManifoldTolerance(float tolerance) {
        assert tolerance >= 0f : tolerance;

        long settingsVa = va();
        setManifoldTolerance(settingsVa, tolerance);
    }

    /**
     * Alter the capacity of the in-flight body pairs array. (native member:
     * mMaxInFlightBodyPairs)
     *
     * @param maxPairs the number of pairs to allocate (&ge;0, default=16384)
     */
    public void setMaxInFlightBodyPairs(int maxPairs) {
        assert maxPairs >= 0 : maxPairs;

        long settingsVa = va();
        setMaxInFlightBodyPairs(settingsVa, maxPairs);
    }

    /**
     * Alter the maximum correction when solving position constraints. (native
     * member: mMaxPenetrationDistance)
     *
     * @param distance the desired distance threshold (in meters per iteration,
     * &ge;0, default=0.2)
     */
    public void setMaxPenetrationDistance(float distance) {
        assert distance >= 0f : distance;

        long settingsVa = va();
        setMaxPenetrationDistance(settingsVa, distance);
    }

    /**
     * Alter the minimum normal speed for elastic collision. (native member:
     * mMinVelocityForRestitution)
     *
     * @param speed the desired threshold speed (in meters per second, &ge;0,
     * default=1)
     */
    public void setMinVelocityForRestitution(float speed) {
        assert speed >= 0f : speed;

        long settingsVa = va();
        setMinVelocityForRestitution(settingsVa, speed);
    }

    /**
     * Alter the number of solver position iterations per simulation step.
     * (native member: mNumPositionSteps)
     *
     * @param numSteps the desired number (&ge;0, default=2)
     */
    public void setNumPositionSteps(int numSteps) {
        long settingsVa = va();
        setNumPositionSteps(settingsVa, numSteps);
    }

    /**
     * Alter the number of velocity steps. (native member: mNumVelocitySteps)
     *
     * @param numSteps the desired number (&ge;0, default=10)
     */
    public void setNumVelocitySteps(int numSteps) {
        long settingsVa = va();
        setNumVelocitySteps(settingsVa, numSteps);
    }

    /**
     * Alter the penetration slop. (native member: mPenetrationSlop)
     *
     * @param slop the desired slop distance (in meters, default=0.02)
     */
    public void setPenetrationSlop(float slop) {
        long settingsVa = va();
        setPenetrationSlop(settingsVa, slop);
    }

    /**
     * Alter the point-motion threshold, below which an object can fall asleep.
     * (native member: mPointVelocitySleepThreshold)
     *
     * @param speed the desired speed threshold (in meters per second, &ge;0,
     * default=0.03)
     */
    public void setPointVelocitySleepThreshold(float speed) {
        assert speed >= 0f : speed;

        long settingsVa = va();
        setPointVelocitySleepThreshold(settingsVa, speed);
    }

    /**
     * Alter the speculative contact distance. (native member:
     * mSpeculativeContactDistance)
     *
     * @param distance the desired distance (in meters, ≥0, default=0.02)
     */
    public void setSpeculativeContactDistance(float distance) {
        assert distance >= 0f : distance;

        long settingsVa = va();
        setSpeculativeContactDistance(settingsVa, distance);
    }

    /**
     * Alter the number of step-listener batches that can be notified by a
     * single job. (native member: mStepListenerBatchesPerJob)
     *
     * @param numBatches the desired number of batches, or INT_MAX to serialize
     * step listeners (&ge;0, default=1)
     */
    public void setStepListenerBatchesPerJob(int numBatches) {
        assert numBatches > 0 : numBatches;

        long settingsVa = va();
        setStepListenerBatchesPerJob(settingsVa, numBatches);
    }

    /**
     * Alter the maximum number of step listeners notified in a single batch.
     * (native member: mStepListenersBatchSize)
     *
     * @param numListeners the desired number of listeners (&ge;0, default=8)
     */
    public void setStepListenersBatchSize(int numListeners) {
        assert numListeners > 0 : numListeners;

        long settingsVa = va();
        setStepListenersBatchSize(settingsVa, numListeners);
    }

    /**
     * Alter the time interval before a body can fall asleep. (native member:
     * mTimeBeforeSleep)
     *
     * @param interval the desired time interval (in seconds, &ge;0,
     * default=0.5)
     */
    public void setTimeBeforeSleep(float interval) {
        assert interval >= 0f : interval;

        long settingsVa = va();
        setTimeBeforeSleep(settingsVa, interval);
    }

    /**
     * Enable or disable the body-pair cache. (native member:
     * mUseBodyPairContactCache)
     *
     * @param enable {@code true} to enable the cache, or {@code false} to
     * disable it (default=true)
     */
    public void setUseBodyPairContactCache(boolean enable) {
        long settingsVa = va();
        setUseBodyPairContactCache(settingsVa, enable);
    }

    /**
     * Enable or disable island splitting. (native member:
     * mUseLargeIslandSplitter)
     *
     * @param setting {@code true} to enable splitting, or {@code false} to
     * disable it (default=true)
     */
    public void setUseLargeIslandSplitter(boolean setting) {
        long settingsVa = va();
        setUseLargeIslandSplitter(settingsVa, setting);
    }

    /**
     * Enable or disable manifold reduction. (native member:
     * mUseManifoldReduction)
     *
     * @param setting {@code true} to enable reduction, or {@code false} to
     * disable it (default=true)
     */
    public void setUseManifoldReduction(boolean setting) {
        long settingsVa = va();
        setUseManifoldReduction(settingsVa, setting);
    }
    // *************************************************************************
    // ConstPhysicsSettings methods

    /**
     * Test whether objects can fall asleep. The settings are unaffected.
     * (native member: mAllowSleeping)
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
     * Return the Baumgarte stabilization factor, the fraction of position error
     * that is corrected in each iteration. The settings are unaffected. (native
     * member: mBaumgarte)
     *
     * @return the fraction (&ge;0, &le;1)
     */
    @Override
    public float getBaumgarte() {
        long settingsVa = va();
        float result = getBaumgarte(settingsVa);

        assert result >= 0f && result <= 1f : result;
        return result;
    }

    /**
     * Return the maximum rotation for reusing collision results from the
     * previous step. The settings are unaffected. (native member:
     * mBodyPairCacheCosMaxDeltaRotationDiv2)
     *
     * @return the cosine of half the maximum rotation angle (&ge;0, &le;1)
     */
    @Override
    public float getBodyPairCacheCosMaxDeltaRotationDiv2() {
        long settingsVa = va();
        float result = getBodyPairCacheCosMaxDeltaRotationDiv2(settingsVa);

        assert result >= 0f && result <= 1f : result;
        return result;
    }

    /**
     * Return the maximum translation for re-using collision results from the
     * previous step. The settings are unaffected. (native member:
     * mBodyPairCacheMaxDeltaPositionSq)
     *
     * @return the square of the maximum displacement (in square meters, &ge;0)
     */
    @Override
    public float getBodyPairCacheMaxDeltaPositionSq() {
        long settingsVa = va();
        float result = getBodyPairCacheMaxDeltaPositionSq(settingsVa);

        assert result >= 0f : result;
        return result;
    }

    /**
     * Test whether collisions with non-active (shared) edges are checked. The
     * settings are unaffected. (native member: mCheckActiveEdges)
     *
     * @return {@code true} if checked, otherwise {@code false}
     */
    @Override
    public boolean getCheckActiveEdges() {
        long settingsVa = va();
        boolean result = getCheckActiveEdges(settingsVa);

        return result;
    }

    /**
     * Test whether warm starting is enabled for constraints. The settings are
     * unaffected. (native member: mConstraintWarmStart)
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    @Override
    public boolean getConstraintWarmStart() {
        long settingsVa = va();
        boolean result = getConstraintWarmStart(settingsVa);

        return result;
    }

    /**
     * Return the maximum angle between normals for which manifolds from
     * different sub shapes of the same body pair will be combined. The settings
     * are unaffected. (native member: mContactNormalCosMaxDeltaRotation)
     *
     * @return the cosine of the threshold angle (&ge;-1, &le;1)
     */
    @Override
    public float getContactNormalCosMaxDeltaRotation() {
        long settingsVa = va();
        float result = getContactNormalCosMaxDeltaRotation(settingsVa);

        assert result >= -1f && result <= 1f : result;
        return result;
    }

    /**
     * Return the maximum translation of a contact point for warm starting. The
     * settings are unaffected. (native member:
     * mContactPointPreserveLambdaMaxDistSq)
     *
     * @return the square of the threshold distance (in meters squared, &ge;0)
     */
    @Override
    public float getContactPointPreserveLambdaMaxDistSq() {
        long settingsVa = va();
        float result = getContactPointPreserveLambdaMaxDistSq(settingsVa);

        assert result >= 0f : result;
        return result;
    }

    /**
     * Test whether physics simulation is deterministic. The settings are
     * unaffected. (native member: mDeterministicSimulation)
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
     * Return the amount of penetration tolerated by a {@code LinearCast} cast.
     * The settings are unaffected. (native member: mLinearCastMaxPenetration)
     *
     * @return the penetration distance (as a fraction of the body's inner
     * radius, &ge;0)
     */
    @Override
    public float getLinearCastMaxPenetration() {
        long settingsVa = va();
        float result = getLinearCastMaxPenetration(settingsVa);

        assert result >= 0f : result;
        return result;
    }

    /**
     * Return the amount of translation that triggers casting for
     * {@code LinearCast} motion quality. The settings are unaffected. (native
     * member: mLinearCastThreshold)
     *
     * @return the translation distance per step (as a fraction of the body's
     * inner radius, &ge;0)
     */
    @Override
    public float getLinearCastThreshold() {
        long settingsVa = va();
        float result = getLinearCastThreshold(settingsVa);

        assert result >= 0f : result;
        return result;
    }

    /**
     * Return the tolerance used to determine whether 2 points are coplanar when
     * calculating the contact manifold between 2 faces. The settings are
     * unaffected. (native member: mManifoldTolerance)
     *
     * @return the distance (in meters, &ge;0)
     */
    @Override
    public float getManifoldTolerance() {
        long settingsVa = va();
        float result = getManifoldTolerance(settingsVa);

        assert result >= 0f : result;
        return result;
    }

    /**
     * Return the maximum number of in-flight body pairs. The settings are
     * unaffected. (native member: mMaxInFlightBodyPairs)
     *
     * @return the limit (&ge;0)
     */
    @Override
    public int getMaxInFlightBodyPairs() {
        long settingsVa = va();
        int result = getMaxInFlightBodyPairs(settingsVa);

        assert result >= 0 : result;
        return result;
    }

    /**
     * Return the maximum distance to correct in a single iteration when solving
     * position constraints. The settings are unaffected. (native member:
     * mMaxPenetrationDistance)
     *
     * @return the distance per iteration (in meters, &ge;0)
     */
    @Override
    public float getMaxPenetrationDistance() {
        long settingsVa = va();
        float result = getMaxPenetrationDistance(settingsVa);

        assert result >= 0f : result;
        return result;
    }

    /**
     * Return the minimum normal speed for elastic collision. The settings are
     * unaffected. (native member: mMinVelocityForRestitution)
     *
     * @return the speed threshold (in meters per second, &ge;0)
     */
    @Override
    public float getMinVelocityForRestitution() {
        long settingsVa = va();
        float result = getMinVelocityForRestitution(settingsVa);

        assert result >= 0f : result;
        return result;
    }

    /**
     * Return the number of solver position iterations per simulation step. The
     * settings are unaffected. (native member: mNumPositionSteps)
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
     * Return the number of velocity iterations per simulation step. The
     * settings are unaffected. (native member: mNumVelocitySteps)
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
     * Return the penetration slop. The settings are unaffected. (native member:
     * mPenetrationSlop)
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
     * Return the point-motion threshold, below which objects can fall asleep.
     * The settings are unaffected. (native member:
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
     * Return the speculative contact distance. The settings are unaffected.
     * (native member: mSpeculativeContactDistance)
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
     * Return the maximum number of step-listener batches notified by a single
     * job. The settings are unaffected. (native member:
     * mStepListenerBatchesPerJob)
     *
     * @return the number of batches per job (&gt;0), or {@code INT_MAX} to
     * serialize step listeners
     */
    @Override
    public int getStepListenerBatchesPerJob() {
        long settingsVa = va();
        int result = getStepListenerBatchesPerJob(settingsVa);

        assert result > 0 : result;
        return result;
    }

    /**
     * Return the maximum number of step listeners notified in a single batch.
     * The settings are unaffected. (native member: mStepListenersBatchSize)
     *
     * @return the number of listeners (&gt;0)
     */
    @Override
    public int getStepListenersBatchSize() {
        long settingsVa = va();
        int result = getStepListenersBatchSize(settingsVa);

        assert result > 0 : result;
        return result;
    }

    /**
     * Return the time interval before a body can fall asleep. The settings are
     * unaffected. (native member: mTimeBeforeSleep)
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
     * Test whether the body-pair cache is enabled. The settings are unaffected.
     * (native member: mUseBodyPairContactCache)
     *
     * @return {@code true} if it is enabled, otherwise {@code false}
     */
    @Override
    public boolean getUseBodyPairContactCache() {
        long settingsVa = va();
        boolean result = getUseBodyPairContactCache(settingsVa);

        return result;
    }

    /**
     * Test whether island splitting is enabled. The settings are unaffected.
     * (native member: mUseLargeIslandSplitter)
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    @Override
    public boolean getUseLargeIslandSplitter() {
        long settingsVa = va();
        boolean result = getUseLargeIslandSplitter(settingsVa);

        return result;
    }

    /**
     * Test whether manifold reduction is enabled. The settings are unaffected.
     * (native member: mUseManifoldReduction)
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    @Override
    public boolean getUseManifoldReduction() {
        long settingsVa = va();
        boolean result = getUseManifoldReduction(settingsVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static void free(long settingsVa);

    native private static boolean getAllowSleeping(long settingsVa);

    native private static float getBaumgarte(long settingsVa);

    native private static float getBodyPairCacheCosMaxDeltaRotationDiv2(
            long settingsVa);

    native private static float getBodyPairCacheMaxDeltaPositionSq(
            long settingsVa);

    native private static boolean getCheckActiveEdges(long settingsVa);

    native private static boolean getConstraintWarmStart(long settingsVa);

    native private static float getContactNormalCosMaxDeltaRotation(
            long settingsVa);

    native private static float getContactPointPreserveLambdaMaxDistSq(
            long settingsVa);

    native private static boolean getDeterministicSimulation(long settingsVa);

    native private static float getLinearCastMaxPenetration(long settingsVa);

    native private static float getLinearCastThreshold(long settingsVa);

    native private static float getManifoldTolerance(long settingsVa);

    native private static int getMaxInFlightBodyPairs(long settingsVa);

    native private static float getMaxPenetrationDistance(long settingsVa);

    native private static float getMinVelocityForRestitution(long settingsVa);

    native private static int getNumPositionSteps(long settingsVa);

    native private static int getNumVelocitySteps(long settingsVa);

    native private static float getPenetrationSlop(long settingsVa);

    native private static float getPointVelocitySleepThreshold(
            long settingsVa);

    native private static float getSpeculativeContactDistance(long settingsVa);

    native private static int getStepListenerBatchesPerJob(long settingsVa);

    native private static int getStepListenersBatchSize(long settingsVa);

    native private static float getTimeBeforeSleep(long settingsVa);

    native private static boolean getUseBodyPairContactCache(long settingsVa);

    native private static boolean getUseLargeIslandSplitter(long settingsVa);

    native private static boolean getUseManifoldReduction(long settingsVa);

    native private static void setAllowSleeping(
            long settingsVa, boolean allow);

    native private static void setBaumgarte(
            long settingsVa, float fraction);

    native private static void setBodyPairCacheCosMaxDeltaRotationDiv2(
            long settingsVa, float cosineOfHalfAngle);

    native private static void setBodyPairCacheMaxDeltaPositionSq(
            long settingsVa, float squaredDistance);

    native private static void setCheckActiveEdges(
            long settingsVa, boolean check);

    native private static void setConstraintWarmStart(
            long settingsVa, boolean setting);

    native private static void setContactNormalCosMaxDeltaRotation(
            long settingsVa, float cosine);

    native private static void setContactPointPreserveLambdaMaxDistSq(
            long settingsVa, float squaredDistance);

    native private static void setDeterministicSimulation(
            long settingsVa, boolean setting);

    native private static void setLinearCastMaxPenetration(
            long settingsVa, float penetration);

    native private static void setLinearCastThreshold(
            long settingsVa, float threshold);

    native private static void setManifoldTolerance(
            long settingsVa, float tolerance);

    native private static void setMaxInFlightBodyPairs(
            long settingsVa, int maxPairs);

    native private static void setMaxPenetrationDistance(
            long settingsVa, float distance);

    native private static void setMinVelocityForRestitution(
            long settingsVa, float speed);

    native private static void setNumPositionSteps(
            long settingsVa, int numSteps);

    native private static void setNumVelocitySteps(
            long settingsVa, int numSteps);

    native private static void setPenetrationSlop(
            long settingsVa, float slop);

    native private static void setPointVelocitySleepThreshold(
            long settingsVa, float speed);

    native private static void setSpeculativeContactDistance(
            long settingsVa, float distance);

    native private static void setStepListenerBatchesPerJob(
            long settingsVa, int numBatches);

    native private static void setStepListenersBatchSize(
            long settingsVa, int numListeners);

    native private static void setTimeBeforeSleep(
            long settingsVa, float interval);

    native private static void setUseBodyPairContactCache(
            long settingsVa, boolean setting);

    native private static void setUseLargeIslandSplitter(
            long settingsVa, boolean setting);

    native private static void setUseManifoldReduction(
            long settingsVa, boolean setting);
}
