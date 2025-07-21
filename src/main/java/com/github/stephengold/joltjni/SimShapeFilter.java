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
import com.github.stephengold.joltjni.readonly.ConstShape;

/**
 * A shape filter for simulation.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SimShapeFilter extends NonCopyable {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default filter.
     */
    public SimShapeFilter() {
        long filterVa = createDefault();
        setVirtualAddressAsOwner(filterVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether the specified collision should be simulated. The filter is
     * unaffected.
     *
     * @param body1 the first colliding body (not null, unaffected)
     * @param shape1 the first colliding shape (not null, unaffected)
     * @param subShapeId1 the sub-shape ID leading from {@code body1.getShape()}
     * to {@code shape1} (not null, unaffected)
     * @param body2 the 2nd colliding body (not null, unaffected)
     * @param shape2 the 2nd colliding shape (not null, unaffected)
     * @param subShapeId2 the sub-shape ID leading from {@code body2.getShape()}
     * to {@code shape2} (not null, unaffected)
     * @return {@code true} if simulated, {@code false} if filtered out
     */
    public boolean shouldCollide(
            ConstBody body1, ConstShape shape1, int subShapeId1,
            ConstBody body2, ConstShape shape2, int subShapeId2) {
        long filterVa = va();
        long body1Va = body1.targetVa();
        long shape1Va = shape1.targetVa();
        long body2Va = body2.targetVa();
        long shape2Va = shape2.targetVa();
        boolean result = shouldCollide(filterVa, body1Va, shape1Va, subShapeId1,
                body2Va, shape2Va, subShapeId2);

        return result;
    }
    // *************************************************************************
    // new protected methods

    /**
     * Assign a native object (assuming there's none already assigned) and
     * designate the JVM object as the owner.
     *
     * @param filterVa the virtual address of the native object to assign (not
     * zero)
     */
    final void setVirtualAddressAsOwner(long filterVa) {
        Runnable freeingAction = () -> free(filterVa);
        setVirtualAddress(filterVa, freeingAction);
    }
    // *************************************************************************
    // native private methods

    native private static long createDefault();

    native private static void free(long filterVa);

    native private static boolean shouldCollide(
            long filterVa, long body1Va, long shape1Va, int subShapeId1,
            long body2Va, long shape2Va, int subShapeId2);
}
