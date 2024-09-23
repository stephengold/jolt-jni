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
 * An implementation of {@code ObjectVsBroadPhaseLayerFilter} that can be
 * configured at runtime.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ObjVsBpFilter extends ObjectVsBroadPhaseLayerFilter {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a filter with all interactions enabled.
     *
     * @param numObjectLayers the number of object layers (&ge;1)
     * @param numBpLayers the number of broad-phase layers (&ge;1)
     */
    public ObjVsBpFilter(int numObjectLayers, int numBpLayers) {
        long filterVa = createObjVsBpFilter(numObjectLayers, numBpLayers);
        setVirtualAddress(filterVa, () -> free(filterVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Disable interactions with the specified layer.
     *
     * @param bpLayer the index of the broad-phase layer (&lt; numBpLayers)
     * @return the modified filter (for chaining)
     */
    public ObjVsBpFilter disableBp(int bpLayer) {
        long filterVa = va();
        disableBp(filterVa, bpLayer);

        return this;
    }

    /**
     * Disable interactions with the specified layer.
     *
     * @param objLayer the index of the object layer (&lt; numObjectLayers)
     * @return the modified filter (for chaining)
     */
    public ObjVsBpFilter disableObj(int objLayer) {
        long filterVa = va();
        disableObj(filterVa, objLayer);

        return this;
    }

    /**
     * Disable interactions between the specified layers.
     *
     * @param objLayer the index of the object layer (&lt; numObjectLayers)
     * @param bpLayer the index of the broad-phase layer (&lt; numBpLayers)
     * @return the modified filter (for chaining)
     */
    public ObjVsBpFilter disablePair(int objLayer, int bpLayer) {
        disablePair(va(), objLayer, bpLayer);
        return this;
    }
    // *************************************************************************
    // native private methods

    native private static long createObjVsBpFilter(
            int numObjectLayers, int numBpLayers);

    native private static void disableBp(long filterVa, int bpLayer);

    native private static void disableObj(long filterVa, int objLayer);

    native private static void disablePair(
            long filterVa, int objLayer, int bpLayer);

    native private static void free(long filterVa);
}
