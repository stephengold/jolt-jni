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

import com.github.stephengold.joltjni.template.RefTarget;

/**
 * A collection of compute shaders for hair simulation.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class HairShaders extends JoltPhysicsObject implements RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default shaders.
     */
    public HairShaders() {
        long shadersVa = createDefault();
        setVirtualAddress(shadersVa, () -> free(shadersVa));
    }

    /**
     * Instantiate a copy of the specified shaders.
     *
     * @param original the shaders to copy (not {@code null}, unaffected)
     */
    public HairShaders(HairShaders original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        Runnable freeingAction = () -> free(copyVa);
        setVirtualAddress(copyVa, freeingAction);
    }

    /**
     * Instantiate shaders with the specified native object assigned.
     *
     * @param shadersVa the virtual address of the native object to assign (not
     * zero)
     */
    HairShaders(long shadersVa) {
        Runnable freeingAction = () -> free(shadersVa);
        setVirtualAddress(shadersVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Loads all shaders for the specified compute system. Note that if you want
     * to simulate on the CPU you must invoke {@code hairRegisterShaders()}
     * first.
     *
     * @param computeSystem the compute system to use (not {@code null})
     */
    public void init(ComputeSystem computeSystem) {
        long shadersVa = va();
        long systemVa = computeSystem.va();
        init(shadersVa, systemVa);
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code HairShaders}. The
     * shaders are unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long shadersVa = va();
        int result = getRefCount(shadersVa);

        return result;
    }

    /**
     * Mark the native {@code HairShaders} as embedded.
     */
    @Override
    public void setEmbedded() {
        long shadersVa = va();
        setEmbedded(shadersVa);
    }

    /**
     * Create a counted reference to the native {@code HairShaders}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public HairShadersRef toRef() {
        long shadersVa = va();
        long refVa = toRef(shadersVa);
        HairShadersRef result = new HairShadersRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static void free(long shadersVa);

    native private static int getRefCount(long shadersVa);

    native static void init(long shadersVa, long systemVa);

    native private static void setEmbedded(long shadersVa);

    native private static long toRef(long shadersVa);
}
