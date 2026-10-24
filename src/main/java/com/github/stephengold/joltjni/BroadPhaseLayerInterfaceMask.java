/*
Copyright (c) 2026 Stephen Gold

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
 * Map object layers to broad-phase layers using bitmasks.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BroadPhaseLayerInterfaceMask extends BroadPhaseLayerInterface {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default map with the specified capacity.
     *
     * @param numBpLayers the desired number of broad-phase layers (&ge;1)
     */
    public BroadPhaseLayerInterfaceMask(int numBpLayers) {
        long mapVa = create(numBpLayers);
        setVirtualAddressAsOwner(mapVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Configure the specified broad-phase layer.
     *
     * @param bpLayer the index of the broad-phase layer to configure (&ge;0,
     * &lt; numBpLayers)
     * @param groupsToInclude a bitmask of groups to include
     * @param groupsToExclude a bitmask of groups to exclude
     * @return the modified map, for chaining
     */
    public BroadPhaseLayerInterfaceMask configureLayer(
            int bpLayer, int groupsToInclude, int groupsToExclude) {
        long mapVa = va();
        configureLayer(mapVa, bpLayer, groupsToInclude, groupsToExclude);

        return this;
    }

    /**
     * If profiling is enabled, alter the name of the specified layer. Otherwise
     * this has no effect.
     *
     * @param bpLayer the index of the broad-phase layer to modify (&ge;0, &lt;
     * numBpLayers)
     * @param name the desired name (not {@code null})
     * @return the modified map, for chaining
     */
    public BroadPhaseLayerInterfaceMask setBroadPhaseLayerName(
            int bpLayer, String name) {
        long mapVa = va();
        setBroadPhaseLayerName(mapVa, bpLayer, name);

        return this;
    }
    // *************************************************************************
    // native private methods

    native private static void configureLayer(
            long mapVa, int bpLayer, int groupsToInclude, int groupsToExclude);

    native private static long create(int numBpLayers);

    native private static void setBroadPhaseLayerName(
            long mapVa, int bpLayer, String name);
}
