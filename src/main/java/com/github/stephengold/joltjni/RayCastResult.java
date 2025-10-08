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

/**
 * Information about a broad-phase ray-cast hit.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class RayCastResult extends BroadPhaseCastResult {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a cast result with the default parameters.
     */
    public RayCastResult() {
        long castResultVa = createRayCastResult();
        setVirtualAddressAsOwner(castResultVa);
    }

    /**
     * Instantiate a cast result with the specified native object assigned but
     * not owned.
     * <p>
     * For use in custom collectors.
     *
     * @param underlying the containing object, or {@code null} if none
     * @param castResultVa the virtual address of the native object to assign
     * (not zero)
     */
    public RayCastResult(JoltPhysicsObject underlying, long castResultVa) {
        super(underlying, castResultVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the ID of the sub-shape that was hit. The result object is
     * unaffected. (native attribute: mSubShapeID2)
     *
     * @return a {@code SubShapeID} value (typically negative)
     */
    public int getSubShapeId2() {
        long castResultVa = va();
        int result = getSubShapeId2(castResultVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createRayCastResult();

    native private static int getSubShapeId2(long castResultVa);
}
