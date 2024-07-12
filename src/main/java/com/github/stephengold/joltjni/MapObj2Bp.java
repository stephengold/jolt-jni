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
 * A configurable one-way mapping from object layers to their corresponding
 * broadphase layers.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class MapObj2Bp extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty map with the specified capacity.
     *
     * @param numObjLayers the desired capacity (number of object layers)
     * @param numBpLayers the number of broadphase layers
     */
    public MapObj2Bp(int numObjLayers, int numBpLayers) {
        long mapVa = createMapObj2Bp(numObjLayers, numBpLayers);
        setVirtualAddress(mapVa, true);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Add a mapping from the specified object layer to the specified broadphase
     * layer.
     *
     * @param objLayer the index of the object layer (&ge;0, &lt;
     * numObjectLayers)
     * @param bpLayer the index of the broadphase layer (&lt; numBpLayers)
     * @return the (modified) current instance (for chaining)
     */
    public MapObj2Bp add(int objLayer, int bpLayer) {
        add(va(), objLayer, bpLayer);
        return this;
    }
    // *************************************************************************
    // JoltPhysicsObject methods

    /**
     * Free and unassign the native object if the current map owns it.
     */
    @Override
    public void close() {
        if (ownsNativeObject()) {
            long mapVa = va();
            free(mapVa);
            unassignNativeObject();
        }
    }
    // *************************************************************************
    // native private methods

    native private static void add(long mapVa, int objLayer, int bpLayer);

    native private static long createMapObj2Bp(
            int numObjLayers, int numBpLayers);

    native private static void free(long mapVa);
}
