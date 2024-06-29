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
 * A configurable filter to prevent specific object layers from interacting with
 * specific broadphase layers.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ObjVsBpFilter extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a filter with all interactions enabled.
     *
     * @param numObjectLayers the number of object layers
     * @param numBpLayers the number of broadphase layers
     */
    public ObjVsBpFilter(int numObjectLayers, int numBpLayers) {
        long filterVa = createObjVsBpFilter(numObjectLayers, numBpLayers);
        setVirtualAddress(filterVa, true);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Disable interactions between the specified layers.
     *
     * @param objLayer the index of the object layer (&lt; numObjectLayers)
     * @param bpLayer the index of the broadphase layer (&lt; numBpLayers)
     * @return the (modified) current instance (for chaining)
     */
    public ObjVsBpFilter disablePair(int objLayer, int bpLayer) {
        disablePair(va(), objLayer, bpLayer);
        return this;
    }
    // *************************************************************************
    // JoltPhysicsObject methods

    /**
     * Unassign the assigned native object, assuming there is one. Free the
     * native object if the filter owns it.
     */
    @Override
    public void close() {
        if (ownsNativeObject()) {
            long filterVa = va();
            free(filterVa);
        }

        unassignNativeObject();
    }
    // *************************************************************************
    // native private methods

    native private static long createObjVsBpFilter(
            int numObjectLayers, int numBpLayers);

    native private static void disablePair(
            long filterVa, int objLayer, int bpLayer);

    native private static void free(long filterVa);
}
