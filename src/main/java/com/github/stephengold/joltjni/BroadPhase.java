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

import com.github.stephengold.joltjni.readonly.ConstBroadPhaseLayerInterface;

/**
 * Coarse collision-detection phase to quickly eliminate body pairs that cannot
 * collide.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class BroadPhase extends BroadPhaseQuery {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a phase with no native object assigned.
     */
    BroadPhase() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Abort adding bodies to the phase.
     *
     * @param bodyIds the IDs of the bodies to be added (not null, unmodified
     * since the handle was created)
     * @param addState the handle returned by {@code addBodiesPrepare()}
     */
    public void addBodiesAbort(BodyIdArray bodyIds, long addState) {
        int numBodies = bodyIds.length();
        addBodiesAbort(bodyIds, numBodies, addState);
    }

    /**
     * Abort adding bodies to the phase.
     *
     * @param bodyIds the IDs of the bodies to be added (not null, unmodified
     * since the handle was created)
     * @param numBodies the number of bodies to be added (&ge;0)
     * @param addState the handle returned by {@code addBodiesPrepare()}
     */
    public void addBodiesAbort(
            BodyIdArray bodyIds, int numBodies, long addState) {
        long phaseVa = va();
        long arrayVa = bodyIds.va();
        addBodiesAbort(phaseVa, arrayVa, numBodies, addState);
    }

    /**
     * Finish adding bodies to the phase.
     *
     * @param bodyIds the IDs of the bodies to be added (not null, unmodified
     * since the handle was created)
     * @param addState the handle returned by {@code addBodiesPrepare()}
     */
    public void addBodiesFinalize(BodyIdArray bodyIds, long addState) {
        int numBodies = bodyIds.length();
        addBodiesFinalize(bodyIds, numBodies, addState);
    }

    /**
     * Finish adding bodies to the phase.
     *
     * @param bodyIds the IDs of the bodies to be added (not null, unmodified
     * since the handle was created)
     * @param numBodies the number of bodies to be added (&ge;0)
     * @param addState the handle returned by {@code addBodiesPrepare()}
     */
    public void addBodiesFinalize(
            BodyIdArray bodyIds, int numBodies, long addState) {
        long phaseVa = va();
        long arrayVa = bodyIds.va();
        addBodiesFinalize(phaseVa, arrayVa, numBodies, addState);
    }

    /**
     * Prepare to add a batch of bodies to the phase.
     *
     * @param bodyIds the IDs of the bodies to be added (not null, possibly
     * shuffled)
     * @return a handle to be passed to {@code addBodiesFinalize()} or
     * {@code addBodiesAbort()}
     */
    public long addBodiesPrepare(BodyIdArray bodyIds) {
        int numBodies = bodyIds.length();
        long result = addBodiesPrepare(bodyIds, numBodies);

        return result;
    }

    /**
     * Prepare to add a batch of bodies to the phase.
     *
     * @param bodyIds the IDs of the bodies to be added (not null, possibly
     * shuffled)
     * @param numBodies the number of bodies to be added (&ge;0)
     * @return a handle to be passed to {@code addBodiesFinalize()} or
     * {@code addBodiesAbort()}
     */
    public long addBodiesPrepare(BodyIdArray bodyIds, int numBodies) {
        long phaseVa = va();
        long arrayVa = bodyIds.va();
        long result = addBodiesPrepare(phaseVa, arrayVa, numBodies);

        return result;
    }

    /**
     * Initialize the phase.
     *
     * @param manager the manager to use (not null)
     * @param map the desired map from object layers to broad-phase layers (not
     * null, alias created)
     */
    public void init(BodyManager manager, ConstBroadPhaseLayerInterface map) {
        long phaseVa = va();
        long managerVa = manager.va();
        long mapVa = map.targetVa();
        init(phaseVa, managerVa, mapVa);
    }

    /**
     * Optimize the phase after adding objects.
     */
    public void optimize() {
        long phaseVa = va();
        optimize(phaseVa);
    }

    /**
     * Invoke whenever the bounding boxes of some bodies change.
     *
     * @param bodyIds the IDs of the bodies to be notified (not null)
     * @param numBodies the number of bodies to be notified (&ge;0)
     */
    public void notifyBodiesAabbChanged(BodyIdArray bodyIds, int numBodies) {
        notifyBodiesAabbChanged(bodyIds, numBodies, true);
    }

    /**
     * Invoke whenever the bounding boxes of some bodies change.
     *
     * @param bodyIds the IDs of the bodies to be notified (not null)
     * @param numBodies the number of bodies to be notified (&ge;0)
     * @param takeLock {@code true} to acquire a lock, otherwise {@code false}
     */
    public void notifyBodiesAabbChanged(
            BodyIdArray bodyIds, int numBodies, boolean takeLock) {
        long phaseVa = va();
        long arrayVa = bodyIds.va();
        notifyBodiesAabbChanged(phaseVa, arrayVa, numBodies, takeLock);
    }

    /**
     * Remove multiple bodies from the phase.
     *
     * @param bodyIds the IDs of the bodies to be removed (not null, possibly
     * shuffled)
     * @param numBodies the number of bodies to be removed (&ge;0)
     */
    public void removeBodies(BodyIdArray bodyIds, int numBodies) {
        long phaseVa = va();
        long arrayVa = bodyIds.va();
        removeBodies(phaseVa, arrayVa, numBodies);
    }
    // *************************************************************************
    // native private methods

    native private static void addBodiesAbort(
            long phaseVa, long arrayVa, int numBodies, long addState);

    native private static void addBodiesFinalize(
            long phaseVa, long arrayVa, int numBodies, long addState);

    native private static long addBodiesPrepare(
            long phaseVa, long arrayVa, int numBodies);

    native private static void init(long phaseVa, long managerVa, long mapVa);

    native private static void optimize(long phaseVa);

    native private static void notifyBodiesAabbChanged(
            long phaseVa, long arrayVa, int numBodies, boolean takeLock);

    native private static void removeBodies(
            long phaseVa, long arrayVa, int numBodies);
}
