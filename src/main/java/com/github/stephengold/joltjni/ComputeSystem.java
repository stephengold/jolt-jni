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
 * A resource on which calculations may be performed.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class ComputeSystem extends NonCopyable implements RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a system with the specified native object assigned.
     *
     * @param systemVa the virtual address of the native object to assign (not
     * zero)
     */
    private ComputeSystem(long systemVa) {
        long refVa = toRef(systemVa);
        Runnable freeingAction = () -> ComputeSystemRef.free(refVa);
        setVirtualAddress(systemVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Create a queue for executing compute shaders.
     *
     * @return a new result object
     */
    public ComputeQueueResult createComputeQueue() {
        long systemVa = va();
        long resultVa = createComputeQueue(systemVa);
        ComputeQueueResult result = new ComputeQueueResult(resultVa, true);

        return result;
    }

    /**
     * Create a compute system that uses a GPU.
     *
     * @return a new result object
     */
    public static ComputeSystemResult createComputeSystem() {
        long resultVa = createSystemGpu();
        ComputeSystemResult result = new ComputeSystemResult(resultVa, true);

        return result;
    }

    /**
     * Create a compute system that doesn't use any GPU.
     *
     * @return a new result object
     */
    public static ComputeSystemResult createComputeSystemCpu() {
        long resultVa = createSystemCpu();
        ComputeSystemResult result = new ComputeSystemResult(resultVa, true);

        return result;
    }

    /**
     * Access the run-time type information of the current system. (native
     * function: GetRTTI)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public Rtti getRtti() {
        long systemRa = va();
        long resultVa = getRtti(systemRa);
        Rtti result = new Rtti(resultVa);

        return result;
    }

    /**
     * If the argument is a {@code ComputeSystemCPU}, register its shaders.
     *
     * @param system the system to modify (not {@code null})
     */
    public static void hairRegisterShaders(ComputeSystem system) {
        long systemVa = system.va();
        long rttiVa = getRtti(systemVa);
        if (Rtti.getName(rttiVa).equals("ComputeSystemCPU")) {
            hairRegisterShaders(systemVa);
        }
    }

    /**
     * Instantiate a system from its virtual address.
     *
     * @param systemVa the virtual address of the native object, or zero
     * @return a new JVM object, or {@code null} if the argument was zero
     */
    static ComputeSystem newSystem(long systemVa) {
        ComputeSystem result = null;
        if (systemVa != 0L) {
            result = new ComputeSystem(systemVa);
        }

        return result;
    }

    /**
     * Replace the system's shader loader. (native member: mShaderLoader)
     *
     * @param loader the loader to use (not {@code null})
     */
    public void setShaderLoader(Loader loader) {
        long systemVa = va();
        long loaderVa = loader.va();
        setShaderLoader(systemVa, loaderVa);
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code ComputeSystem}. The
     * system is unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long systemVa = va();
        int result = getRefCount(systemVa);

        return result;
    }

    /**
     * Mark the native {@code ComputeSystem} as embedded.
     */
    @Override
    public void setEmbedded() {
        long systemVa = va();
        setEmbedded(systemVa);
    }

    /**
     * Create a counted reference to the native {@code ComputeSystem}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public ComputeSystemRef toRef() {
        long systemVa = va();
        long refVa = toRef(systemVa);
        ComputeSystemRef result = new ComputeSystemRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long createComputeQueue(long systemVa);

    native private static long createSystemCpu();

    native private static long createSystemGpu();

    native private static int getRefCount(long systemVa);

    native private static long getRtti(long systemVa);

    native private static void hairRegisterShaders(long systemVa);

    native private static void setEmbedded(long systemVa);

    native private static void setShaderLoader(long systemVa, long loaderVa);

    native private static long toRef(long systemVa);
}
