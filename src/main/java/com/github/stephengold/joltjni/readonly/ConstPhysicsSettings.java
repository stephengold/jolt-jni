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
     * The settings are unaffected.
     *
     * @return {@code true} if sleeping is allowed, otherwise {@code false}
     */
    boolean getAllowSleeping();

    /**
     * Return the Baumgarte stabilization factor, the fraction of position error
     * that is corrected in each update. The settings are unaffected.
     *
     * @return the factor (&ge;0, &le;1)
     */
    float getBaumgarte();

    /**
     * Return the maximum relative delta orientation for body pairs to be able
     * to reuse collision results from last frame. The settings are unaffected.
     *
     * @return the delta (as cos(max angle / 2), &ge;0, &le;1)
     */
    float getBodyPairCacheCosMaxDeltaRotationDiv2();

    /**
     * Return the maximum relative delta position for body pairs to be able to
     * reuse collision results from last frame. The settings are unaffected.
     *
     * @return the delta (in meters^2, &ge;0)
     */
    float getBodyPairCacheMaxDeltaPositionSq();

    /**
     * Test whether collision against non-active (shared) edges is allowed.
     * Mainly for debugging the algorithm. The settings are unaffected.
     *
     * @return {@code true} if allowed, otherwise {@code false}
     */
    boolean getCheckActiveEdges();

    /**
     * Test whether warm starting for constraints (initially applying previous
     * frames impulses) should be used. The settings are unaffected.
     *
     * @return {@code true} if it should, otherwise {@code false}
     */
    boolean getConstraintWarmStart();

    /**
     * Return the maximum angle between normals that allows manifolds between
     * different sub shapes of the same body pair to be combined. The settings
     * are unaffected.
     *
     * @return the angle (as cos(angle), &ge;-1, &le;1)
     */
    float getContactNormalCosMaxDeltaRotation();

    /**
     * Return the maximum allowed distance between old and new contact points to
     * preserve contact forces for warm start. The settings are unaffected.
     *
     * @return the distance (in meters^2, &ge;0)
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
     * Return the maximum penetration of a linear cast, which is the fraction
     * of a body's inner radius that may penetrate another body.
     * The settings are unaffected.
     *
     * @return the distance (in meters, &ge;0)
     */
    float getLinearCastMaxPenetration();

    /**
     * Return the linear cast threshold, the fraction of a body's inner radius
     * it must move per step to enable casting for the LinearCast motion
     * quality. The settings are unaffected.
     *
     * @return the threshold (in meters, &ge;0)
     */
    float getLinearCastThreshold();

    /**
     * Return the maximum distance used to determine if two points are on the
     * same plane for determining the contact manifold between two faces.
     * The settings are unaffected.
     *
     * @return the distance (in meters, &ge;0)
     */
    float getManifoldTolerance();

    /**
     * Get the size of the body pairs array, which corresponds to
     * the maximum number of pairs which can be in flight at a time.
     * The settings are unaffected.
     *
     * @return the size (&ge;0)
     */
    int getMaxInFlightBodyPairs();

    /**
     * Return the maximum distance to correct in a single iteration when solving
     * position constraints. The settings are unaffected.
     *
     * @return the distance (in meters, &ge;0)
     */
    float getMaxPenetrationDistance();

    /**
     * Return the minimum velocity needed for a collision to be elastic. If the
     * relative velocity between colliding objects in the direction of the
     * contact normal is lower than this, the restitution will be zero
     * regardless of theconfigured value. This lets an object settle sooner.
     * The settings are unaffected.
     *
     * @return the velocity (in meters, &gt;0)
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
     * Get the number of step listener batches that can be allocated to one job
     * before spawning another. Set to {@link Integer#MAX_VALUE} for no
     * parallelism. The settings are unaffected.
     *
     * @return the number (&gt;0)
     */
    int getStepListenerBatchesPerJob();

    /**
     * Get the number of step listeners to notify in each batch. The settings
     * are unaffected.
     *
     * @return the batch size (&gt;0)
     */
    int getStepListenersBatchSize();

    /**
     * Alter the time interval before an object can fall asleep. The settings
     * are unaffected.
     *
     * @return the interval (in seconds, &ge;0)
     */
    float getTimeBeforeSleep();

    /**
     * Test whether to use the body pair cache, which removes the need for
     * narrow phase collision detection when orientation between two bodies
     * didn't change. The settings are unaffected.
     *
     * @return {@code true} if it will be used, otherwise {@code false}
     */
    boolean getUseBodyPairContactCache();

    /**
     * Test whether large islands should be split into smaller parallel batches
     * of work. The settings are unaffected.
     *
     * @return {@code true} if they should be split, otherwise {@code false}
     */
    boolean getUseLargeIslandSplitter();

    /**
     * Test whether to reduce manifolds with similar contact normals into one
     * contact manifold. The settings are unaffected.
     *
     * @return {@code true} if they will be reduced, otherwise {@code false}
     */
    boolean getUseManifoldReduction();
}
