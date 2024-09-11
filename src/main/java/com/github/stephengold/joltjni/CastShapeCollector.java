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

/**
 * Collect results from a narrow-phase shape-cast query. (native type: {@code
 * CollisionCollector<ShapeCastResult,CollisionCollectorTraitsCastShape>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class CastShapeCollector extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a collector with no native object assigned.
     */
    CastShapeCollector() {
    }
    // *************************************************************************
    // new protected methods

    /**
     * Assign a native object, assuming there's none already assigned.
     *
     * @param collectorVa the virtual address of the native object to assign
     * (not zero)
     * @param owner true &rarr; make the JVM object the owner, false &rarr; it
     * isn't the owner
     */
    void setVirtualAddress(long collectorVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(collectorVa) : null;
        setVirtualAddress(collectorVa, freeingAction);
    }
    // *************************************************************************
    // native private methods

    native private static void free(long collectorVa);
}
