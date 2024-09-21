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
     * @param bodies the bodies to be added (not null, unmodified since the
     * handle was created)
     * @param addState the handle returned by {@code addBodiesPrepare()}
     */
    public void addBodiesAbort(BodyId[] bodies, long addState) {
        addBodiesAbort(bodies, bodies.length, addState);
    }

    /**
     * Abort adding bodies to the phase.
     *
     * @param bodies the bodies to be added (not null, unmodified since the
     * handle was created)
     * @param numBodies the number of bodies to be added (&ge;0)
     * @param addState the handle returned by {@code addBodiesPrepare()}
     */
    abstract public void addBodiesAbort(
            BodyId[] bodies, int numBodies, long addState);

    /**
     * Finish adding bodies to the phase.
     *
     * @param bodies the bodies to be added (not null, unmodified since the
     * handle was created)
     * @param addState the handle returned by {@code addBodiesPrepare()}
     */
    public void addBodiesFinalize(BodyId[] bodies, long addState) {
        addBodiesFinalize(bodies, bodies.length, addState);
    }

    /**
     * Finish adding bodies to the phase.
     *
     * @param bodies the bodies to be added (not null, unmodified since the
     * handle was created)
     * @param numBodies the number of bodies to be added (&ge;0)
     * @param addState the handle returned by {@code addBodiesPrepare()}
     */
    abstract public void addBodiesFinalize(
            BodyId[] bodies, int numBodies, long addState);

    /**
     * Prepare to add a batch of bodies to the phase.
     *
     * @param bodies the bodies to be added (not null)
     * @return a handle to be passed to {@code addBodiesFinalize()} or
     * {@code addBodiesFinalize()}
     */
    public long addBodiesPrepare(BodyId[] bodies) {
        long result = addBodiesPrepare(bodies, bodies.length);
        return result;
    }

    /**
     * Prepare for adding multiple bodies to the phase.
     *
     * @param bodies the bodies to be added (not null)
     * @param numBodies the number of bodies to be added (&ge;0)
     * @return a handle to be passed to {@code addBodiesFinalize()} or
     * {@code addBodiesFinalize()}
     */
    abstract public long addBodiesPrepare(BodyId[] bodies, int numBodies);

    /**
     * Initialize the phase.
     *
     * @param bodyManager the manager to use (not null)
     * @param map the desired map from object layers to broad-phase layers (not
     * null, alias created)
     */
    abstract public void init(
            BodyManager bodyManager, ConstBroadPhaseLayerInterface map);

    /**
     * Optimize the phase after adding objects.
     */
    abstract public void optimize();
}
