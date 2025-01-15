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
 * An implementation of {@code BroadPhaseLayerInterface} that can be configured
 * at runtime.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class MapObj2Bp extends BroadPhaseLayerInterface {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty map with the specified capacity.
     *
     * @param numObjLayers the desired capacity (number of object layers)
     * (&ge;1)
     * @param numBpLayers the number of broad-phase layers (&ge;1)
     */
    public MapObj2Bp(int numObjLayers, int numBpLayers) {
        long mapVa = createMapObj2Bp(numObjLayers, numBpLayers);
        setVirtualAddress(mapVa, () -> free(mapVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Add a mapping from the specified object layer to the specified
     * broad-phase layer.
     *
     * @param objLayer the index of the object layer (&ge;0, &lt;
     * numObjectLayers)
     * @param bpLayer the index of the broad-phase layer (&lt; numBpLayers)
     * @return the modified map, for chaining
     */
    public MapObj2Bp add(int objLayer, int bpLayer) {
        add(va(), objLayer, bpLayer);
        return this;
    }
    // *************************************************************************
    // native private methods

    native private static void add(long mapVa, int objLayer, int bpLayer);

    native private static long createMapObj2Bp(
            int numObjLayers, int numBpLayers);

    native private static void free(long mapVa);
}
