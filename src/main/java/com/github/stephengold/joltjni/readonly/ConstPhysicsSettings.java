/*
Copyright (c) 2025 Stephen Gold

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
package com.github.stephengold.joltjni.readonly;

/**
 * Read-only access to a {@code PhysicsSettings} object. (native type: const
 * PhysicsSettings)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstPhysicsSettings extends ConstJoltPhysicsObject {
    /**
     * Test whether objects can fall asleep. The settings are unaffected.
     *
     * @return {@code true} if sleeping is allowed, otherwise {@code false}
     */
    boolean getAllowSleeping();

    /**
     * Return the Baumgarte stabilization factor, the fraction of position error
     * that is corrected in each iteration. The settings are unaffected.
     *
     * @return the fraction (&ge;0, &le;1)
     */
    float getBaumgarte();

    /**
     * Return the maximum rotation for reusing collision results from the
     * previous step. The settings are unaffected.
     *
     * @return the cosine of half the maximum rotation angle (&ge;0, &le;1)
     */
    float getBodyPairCacheCosMaxDeltaRotationDiv2();

    /**
     * Return the maximum translation for re-using collision results from the
     * previous step. The settings are unaffected.
     *
     * @return the square of the maximum displacement (in square meters, &ge;0)
     */
    float getBodyPairCacheMaxDeltaPositionSq();

    /**
     * Test whether collisions with non-active (shared) edges are checked. The
     * settings are unaffected.
     *
     * @return {@code true} if checked, otherwise {@code false}
     */
    boolean getCheckActiveEdges();

    /**
     * Test whether warm starting is enabled for constraints. The settings are
     * unaffected.
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    boolean getConstraintWarmStart();

    /**
     * Return the maximum angle between normals for which manifolds from
     * different sub shapes of the same body pair will be combined. The settings
     * are unaffected.
     *
     * @return the cosine of the threshold angle (&ge;-1, &le;1)
     */
    float getContactNormalCosMaxDeltaRotation();

    /**
     * Return the maximum translation of a contact point for warm starting. The
     * settings are unaffected.
     *
     * @return the square of the threshold distance (in meters squared, &ge;0)
     */
    float getContactPointPreserveLambdaMaxDistSq();

    /**
     * Test whether physics simulation is deterministic. The settings are
     * unaffected.
     *
     * @return {@code true} if it is deterministic, otherwise {@code false}
     */
    boolean getDeterministicSimulation();

    /**
     * Return the amount of penetration tolerated by a {@code LinearCast} cast.
     * The settings are unaffected.
     *
     * @return the penetration distance (as a fraction of the body's inner
     * radius, &ge;0)
     */
    float getLinearCastMaxPenetration();

    /**
     * Return the amount of translation that triggers casting for
     * {@code LinearCast} motion quality. The settings are unaffected.
     *
     * @return the translation distance per step (as a fraction of the body's
     * inner radius, &ge;0)
     */
    float getLinearCastThreshold();

    /**
     * Return the tolerance used to determine whether 2 points are coplanar when
     * calculating the contact manifold between 2 faces. The settings are
     * unaffected.
     *
     * @return the distance (in meters, &ge;0)
     */
    float getManifoldTolerance();

    /**
     * Return the maximum number of in-flight body pairs. The settings are
     * unaffected.
     *
     * @return the limit (&ge;0)
     */
    int getMaxInFlightBodyPairs();

    /**
     * Return the maximum distance to correct in a single iteration when solving
     * position constraints. The settings are unaffected.
     *
     * @return the distance per iteration (in meters, &ge;0)
     */
    float getMaxPenetrationDistance();

    /**
     * Return the minimum normal speed for elastic collision. The settings are
     * unaffected.
     *
     * @return the speed threshold (in meters per second, &ge;0)
     */
    float getMinVelocityForRestitution();

    /**
     * Return the number of solver position iterations per simulation step. The
     * settings are unaffected.
     *
     * @return the number (&ge;0)
     */
    int getNumPositionSteps();

    /**
     * Return the number of velocity iterations per simulation step. The
     * settings are unaffected.
     *
     * @return the number (&ge;0)
     */
    int getNumVelocitySteps();

    /**
     * Return the penetration slop. The settings are unaffected.
     *
     * @return the slop distance (in meters)
     */
    float getPenetrationSlop();

    /**
     * Return the point-motion threshold, below which objects can fall asleep.
     * The settings are unaffected.
     *
     * @return the speed threshold (in meters per second, &ge;0)
     */
    float getPointVelocitySleepThreshold();

    /**
     * Return the speculative contact distance. The settings are unaffected.
     *
     * @return the distance (in meters, ≥0)
     */
    float getSpeculativeContactDistance();

    /**
     * Return the maximum number of step-listener batches notified by a single
     * job. The settings are unaffected.
     *
     * @return the number of batches per job (&gt;0), or {@code INT_MAX} to
     * serialize step listeners
     */
    int getStepListenerBatchesPerJob();

    /**
     * Return the maximum number of step listeners notified in a single batch.
     * The settings are unaffected.
     *
     * @return the number of listeners (&gt;0)
     */
    int getStepListenersBatchSize();

    /**
     * Return the time interval before a body can fall asleep. The settings are
     * unaffected.
     *
     * @return the interval (in seconds, &ge;0)
     */
    float getTimeBeforeSleep();

    /**
     * Test whether the body-pair cache is enabled. The settings are unaffected.
     *
     * @return {@code true} if it is enabled, otherwise {@code false}
     */
    boolean getUseBodyPairContactCache();

    /**
     * Test whether island splitting is enabled. The settings are unaffected.
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    boolean getUseLargeIslandSplitter();

    /**
     * Test whether manifold reduction is enabled. The settings are unaffected.
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    boolean getUseManifoldReduction();
}
