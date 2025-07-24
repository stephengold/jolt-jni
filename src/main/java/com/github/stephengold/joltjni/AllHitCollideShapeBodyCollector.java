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
 * Collect all results from a broadphase shape collision. (native type: {@code
 * AllHitCollisionCollector<CollideShapeBodyCollector>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class AllHitCollideShapeBodyCollector extends CollideShapeBodyCollector {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default collector.
     */
    public AllHitCollideShapeBodyCollector() {
        long collectorVa = createDefault();
        setVirtualAddressAsOwner(collectorVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Count the hits.
     *
     * @return the count (&ge;0)
     */
    public int countHits() {
        long collectorVa = va();
        int result = countHits(collectorVa);
        return result;
    }

    /**
     * Return the body ID of the hit with the specified index. (native
     * attribute: mHits)
     *
     * @param index (&ge;0, &lt;numHits)
     * @return the {@code BodyID} value
     */
    public int get(int index) {
        long collectorVa = va();
        int result = getHit(collectorVa, index);

        return result;
    }

    /**
     * Copy all the hits to an array. (native attribute: mHits)
     *
     * @return a new array of body IDs
     */
    public int[] getHits() {
        long collectorVa = va();
        int numHits = countHits(collectorVa);
        int[] result = new int[numHits];
        for (int i = 0; i < numHits; ++i) {
            int bodyId = getHit(collectorVa, i);
            result[i] = bodyId;
        }

        return result;
    }
    // *************************************************************************
    // CollideShapeBodyCollector methods

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

    native private static int countHits(long collectorVa);

    native private static long createDefault();

    native private static int getHit(long collectorVa, int hitIndex);

    native private static void reset(long collectorVa);
}
