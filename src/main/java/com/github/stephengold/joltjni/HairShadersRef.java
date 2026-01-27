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

import com.github.stephengold.joltjni.template.Ref;

/**
 * A counted reference to a {@code HairShaders} object. (native type:
 * {@code Ref<HairShaders>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class HairShadersRef extends Ref {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public HairShadersRef() {
        long refVa = createDefault();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    HairShadersRef(long refVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(refVa) : null;
        setVirtualAddress(refVa, freeingAction);
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
        long shadersVa = targetVa();
        long systemVa = computeSystem.va();
        HairShaders.init(shadersVa, systemVa);
    }
    // *************************************************************************
    // Ref methods

    /**
     * Temporarily access the referenced {@code HairShaders}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public HairShaders getPtr() {
        long shadersRef = targetVa();
        HairShaders result = new HairShaders(shadersRef);

        return result;
    }

    /**
     * Return the address of the native {@code HairShaders}. No objects are
     * affected.
     *
     * @return a virtual address (not zero)
     */
    @Override
    public long targetVa() {
        long refVa = va();
        long result = getPtr(refVa);

        return result;
    }

    /**
     * Create another counted reference to the native {@code HairShaders}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public HairShadersRef toRef() {
        long refVa = va();
        long copyVa = copy(refVa);
        HairShadersRef result = new HairShadersRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long copy(long refVa);

    native private static long createDefault();

    native static void free(long refVa);

    native private static long getPtr(long refVa);
}
