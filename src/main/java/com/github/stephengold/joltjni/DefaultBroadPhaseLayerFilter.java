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

import com.github.stephengold.joltjni.readonly.ConstObjectVsBroadPhaseLayerFilter;

/**
 * A collision filter for specific broad-phase layers.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class DefaultBroadPhaseLayerFilter extends BroadPhaseLayerFilter {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a filter based on the specified pair filter.
     *
     * @param pairFilter the pair filter to use (not null, unaffected)
     * @param objectLayer the index of the object layer to use
     */
    DefaultBroadPhaseLayerFilter(
            ConstObjectVsBroadPhaseLayerFilter pairFilter, int objectLayer) {
        super(true);
        long pairFilterVa = pairFilter.targetVa();
        long filterVa = createFromPairFilter(pairFilterVa, objectLayer);
        setVirtualAddressAsOwner(filterVa);
    }
    // *************************************************************************
    // native private methods

    native private static long createFromPairFilter(
            long pairFilterVa, int objectLayer);
}
