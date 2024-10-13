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
 * Collect the closest result from a narrow-phase shape-cast query. (native
 * type: {@code ClosestHitCollisionCollector<CastShapeCollector>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ClosestHitCastShapeCollector extends CastShapeCollector {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default collector.
     */
    public ClosestHitCastShapeCollector() {
        long collectorVa = createDefault();
        setVirtualAddress(collectorVa, true);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the result of the latest query. The collector is unaffected.
     * (native field: mHit)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public ShapeCastResult getHit() {
        long collectorVa = va();
        long hitVa = getHit(collectorVa);
        ShapeCastResult result = new ShapeCastResult(hitVa);

        return result;
    }

    /**
     * Test whether the latest query resulted in a hit. The collector is
     * unaffected.
     *
     * @return true if there was a hit, otherwise false
     */
    public boolean hadHit() {
        long collectorVa = va();
        boolean result = hadHit(collectorVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createDefault();

    native private static long getHit(long collectorVa);

    native private static boolean hadHit(long collectorVa);
}
