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

import com.github.stephengold.joltjni.readonly.ConstTransformedShape;
import java.util.ArrayList;
import java.util.List;

/**
 * Collect all leaf shapes from a transformed shape or narrow-phase query.
 * (native type: {@code AllHitCollisionCollector<TransformedShapeCollector>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class AllHitTransformedShapeCollector extends TransformedShapeCollector {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default collector.
     */
    public AllHitTransformedShapeCollector() {
        long collectorVa = createDefault();
        setVirtualAddress(collectorVa, true);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Count the leaf shapes.
     *
     * @return the count (&ge;0)
     */
    public int countHits() {
        long collectorVa = va();
        int numHits = countHits(collectorVa);
        return numHits;
    }

    /**
     * Access the leaf shape with the specified index.
     *
     * @param index (&ge;0, &lt;numHits)
     * @return a new object
     */
    public ConstTransformedShape get(int index) {
        long collectorVa = va();
        long hitVa = getHit(collectorVa, index);
        ConstTransformedShape result = new TransformedShape(hitVa, true);

        return result;
    }

    /**
     * Access all the leaf shapes as an array. (native attribute: mHits)
     *
     * @return a new list of new objects
     */
    public List<ConstTransformedShape> getHits() {
        long collectorVa = va();
        int numHits = countHits(collectorVa);
        List<ConstTransformedShape> result = new ArrayList<>(numHits);
        for (int i = 0; i < numHits; ++i) {
            long hitVa = getHit(collectorVa, i);
            ConstTransformedShape hit = new TransformedShape(hitVa, true);
            result.add(hit);
        }

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static int countHits(long collectorVa);

    native private static long createDefault();

    native private static long getHit(long collectorVa, int hitIndex);
}
