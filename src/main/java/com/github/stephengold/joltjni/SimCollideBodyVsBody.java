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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.readonly.ConstBody;
import com.github.stephengold.joltjni.readonly.Mat44Arg;

/**
 * Collide 2 bodies during simulation. (native type:
 * {@code PhysicsSystem::SimCollideBodyVsBody})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SimCollideBodyVsBody extends NonCopyable {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default collider.
     */
    public SimCollideBodyVsBody() {
        long colliderVa = createDefault();
        setVirtualAddressAsOwner(colliderVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Collide the specified bodies. The collider is unaffected.
     *
     * @param body1 the first colliding body (not null, unaffected)
     * @param body2 the 2nd colliding body (not null, unaffected)
     * @param comTransform1 the center-of-mass transform of the first colliding
     * body (not null, unaffected)
     * @param comTransform2 the center-of-mass transform of the 2nd colliding
     * body (not null, unaffected)
     * @param settings the collide-shape settings (not null)
     * @param collector the collide-shape collector (not null)
     * @param filter the shape filter (not null, unaffected)
     */
    public void collide(
            ConstBody body1, ConstBody body2, Mat44Arg comTransform1,
            Mat44Arg comTransform2, CollideShapeSettings settings,
            CollideShapeCollector collector, ShapeFilter filter) {
        long colliderVa = va();
        long body1Va = body1.targetVa();
        long body2Va = body2.targetVa();
        long comTransform1Va = comTransform1.targetVa();
        long comTransform2Va = comTransform2.targetVa();
        long settingsVa = settings.va();
        long collectorVa = collector.va();
        long filterVa = filter.va();
        collide(colliderVa, body1Va, body2Va, comTransform1Va, comTransform2Va,
                settingsVa, collectorVa, filterVa);
    }
    // *************************************************************************
    // new protected methods

    /**
     * Assign a native object (assuming there's none already assigned) and
     * designate the JVM object as the owner.
     *
     * @param colliderVa the virtual address of the native object to assign (not
     * zero)
     */
    final void setVirtualAddressAsOwner(long colliderVa) {
        Runnable freeingAction = () -> free(colliderVa);
        setVirtualAddress(colliderVa, freeingAction);
    }
    // *************************************************************************
    // native private methods

    native private static void collide(long colliderVa, long body1Va,
            long body2Va, long comTransform1Va, long comTransform2Va,
            long settingsVa, long collectorVa, long filterVa);

    native private static long createDefault();

    native private static void free(long colliderVa);
}
