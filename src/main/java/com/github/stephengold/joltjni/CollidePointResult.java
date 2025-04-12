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
 * Information about a narrow-phase collision by a point.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CollidePointResult extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a point result with the specified container and native
     * object.
     *
     * @param container the containing object, or {@code null} if none
     * @param pointResultVa the virtual address of the native object to assign
     * (not zero)
     */
    CollidePointResult(JoltPhysicsObject container, long pointResultVa) {
        super(container, pointResultVa);
    }

    /**
     * Instantiate a point result with the specified native object assigned but
     * not owned.
     * <p>
     * For use in custom collectors.
     *
     * @param pointResultVa the virtual address of the native object to assign
     * (not zero)
     */
    public CollidePointResult(long pointResultVa) {
        setVirtualAddress(pointResultVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Identify the body that was hit. The result object is unaffected. (native
     * attribute: mBodyID)
     *
     * @return the {@code BodyID} value
     */
    public int getBodyId() {
        long pointResultVa = va();
        int result = getBodyId(pointResultVa);

        return result;
    }

    /**
     * Identify the sub-shape on the shape that was hit. The result object is
     * unaffected. (native attribute: mSubShapeID2)
     *
     * @return a {@code SubShapeID} value
     */
    public int getSubShapeId2() {
        long pointResultVa = va();
        int result = getSubShapeId2(pointResultVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static int getBodyId(long pointResultVa);

    native private static int getSubShapeId2(long pointResultVa);
}
