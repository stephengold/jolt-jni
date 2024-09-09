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

import com.github.stephengold.joltjni.readonly.ConstBroadPhaseLayerInterface;

/**
 * Map object layers to broad-phase layers.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class BroadPhaseLayerInterface
        extends NonCopyable
        implements ConstBroadPhaseLayerInterface {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a map with no native object assigned.
     */
    BroadPhaseLayerInterface() {
    }
    // *************************************************************************
    // ConstBroadPhaseLayerInterface methods

    /**
     * Return the broad-phase layer for the specified object layer.
     *
     * @param objectLayer the index of the object layer to query (&ge;0,
     * &lt;numObjectLayers)
     * @return the index of the corresponding broad-phase layer
     */
    @Override
    public int getBroadPhaseLayer(int objectLayer) {
        long mapVa = va();
        int result = getBroadPhaseLayer(mapVa, objectLayer);

        return result;
    }

    /**
     * Count how many broad-phase layers there are.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getNumBroadPhaseLayers() {
        long mapVa = va();
        int result = getNumBroadPhaseLayers(mapVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static int getBroadPhaseLayer(long mapVa, int objectLayer);

    native private static int getNumBroadPhaseLayers(long mapVa);
}
