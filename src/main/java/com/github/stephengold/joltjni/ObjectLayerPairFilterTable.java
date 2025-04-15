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
 * Filter collisions between object layers using a table.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ObjectLayerPairFilterTable extends ObjectLayerPairFilter {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a filter with the specified capacity. All interactions are
     * disabled.
     *
     * @param numObjectLayers the number of object layers (&ge;1)
     */
    public ObjectLayerPairFilterTable(int numObjectLayers) {
        long filterVa = create(numObjectLayers);
        setVirtualAddress(filterVa, () -> free(filterVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Disable collisions between the specified object layers.
     *
     * @param layer1 the index of the first object layer (&ge;0,
     * &lt;numObjectLayers)
     * @param layer2 the index of the 2nd object layer (&ge;0,
     * &lt;numObjectLayers)
     * @return the modified filter, for chaining
     */
    public ObjectLayerPairFilterTable disableCollision(int layer1, int layer2) {
        long filterVa = va();
        disableCollision(filterVa, layer1, layer2);

        return this;
    }

    /**
     * Enable collisions between the specified object layers.
     *
     * @param layer1 the index of the first object layer (&ge;0,
     * &lt;numObjectLayers)
     * @param layer2 the index of the 2nd object layer (&ge;0,
     * &lt;numObjectLayers)
     * @return the modified filter, for chaining
     */
    public ObjectLayerPairFilterTable enableCollision(int layer1, int layer2) {
        long filterVa = va();
        enableCollision(filterVa, layer1, layer2);

        return this;
    }
    // *************************************************************************
    // native private methods

    native private static long create(int numObjectLayers);

    native private static void disableCollision(
            long filterVa, int layer1, int layer2);

    native private static void enableCollision(
            long filterVa, int layer1, int layer2);

    native private static void free(long filterVa);
}
