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
 * Filter collisions between objects and broad-phase layers using a table.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ObjectVsBroadPhaseLayerFilterTable
        extends ObjectVsBroadPhaseLayerFilter {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default filter with the specified capacity.
     *
     * @param bplInterface the layer interface to use (not null, unaffected)
     * @param numBpLayers the number of broad-phase layers (&ge;1)
     * @param olPairFilter the object-pair filter to use (not null, unaffected)
     * @param numObjLayers the desired capacity (number of object layers)
     * (&ge;1)
     */
    public ObjectVsBroadPhaseLayerFilterTable(
            BroadPhaseLayerInterface bplInterface, int numBpLayers,
            ObjectLayerPairFilter olPairFilter, int numObjLayers) {
        long bpliVa = bplInterface.va();
        long olpfVa = olPairFilter.va();
        long mapVa = create(bpliVa, numBpLayers, olpfVa, numObjLayers);
        setVirtualAddress(mapVa, () -> free(mapVa));
    }
    // *************************************************************************
    // native private methods

    native private static long create(
            long bpliVa, int numBpLayers, long olpfVa, int numObjLayers);

    native private static void free(long mapVa);
}
