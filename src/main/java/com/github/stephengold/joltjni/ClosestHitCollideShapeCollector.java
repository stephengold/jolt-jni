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

/**
 * Collect the closest result from a narrow-phase collide-shape query. (native
 * type: {@code ClosestHitCollisionCollector<CollideShapeCollector>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ClosestHitCollideShapeCollector extends CollideShapeCollector {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default collector.
     */
    public ClosestHitCollideShapeCollector() {
        long collectorVa = createDefault();
        setVirtualAddressAsOwner(collectorVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the result of the latest query. (native field: mHit)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public CollideShapeResult getHit() {
        long collectorVa = va();
        long hitVa = getHit(collectorVa);
        CollideShapeResult result = new CollideShapeResult(this, hitVa);

        return result;
    }

    /**
     * Test whether the latest query resulted in a hit. The collector is
     * unaffected.
     *
     * @return {@code true} if there was a hit, otherwise {@code false}
     */
    public boolean hadHit() {
        long collectorVa = va();
        boolean result = hadHit(collectorVa);

        return result;
    }
    // *************************************************************************
    // CollideShapeCollector methods

    /**
     * Reset the collector so it can be reused.
     */
    @Override
    public void reset() {
        long collectorVa = va();
        reset(collectorVa);
    }
    // *************************************************************************
    // native private methods

    native private static long createDefault();

    native private static long getHit(long collectorVa);

    native private static boolean hadHit(long collectorVa);

    native private static void reset(long collectorVa);
}
