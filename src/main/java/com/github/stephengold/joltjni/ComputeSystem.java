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
abstract public class ComputeSystem extends NonCopyable implements RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a system with no native object assigned.
     */
    ComputeSystem() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Create a queue for executing compute shaders.
     *
     * @return a new object
     */
    public ComputeQueueResult createComputeQueue() {
        long systemVa = va();
        long resultVa = createComputeQueue(systemVa);
        ComputeQueueResult result = new ComputeQueueResult(resultVa, true);

        return result;
    }

    /**
     * Instantiate a system from its virtual address.
     *
     * @param systemVa the virtual address of the native object, or zero
     * @return a new JVM object, or {@code null} if the argument was zero
     */
    public static ComputeSystem newSystem(long systemVa) {
        if (systemVa == 0L) {
            return null;
        }
        long rttiVa = getRtti(systemVa);
        String typeName = Rtti.getName(rttiVa);
        ComputeSystem result;
        switch (typeName) {
            case "ComputeSystemCPU":
                result = new ComputeSystemCpu(systemVa);
                break;

            case "ComputeSystemDX12":
            case "ComputeSystemMTL":
            case "ComputeSystemVK":
            // TODO

            default:
                throw new RuntimeException("typeName = " + typeName);
        }

        return result;
    }

    /**
     * Replace the shader loader. (native member: mShaderLoader)
     *
     * @param loader the loader to use (not {@code null})
     */
    public void setShaderLoader(ShaderLoader loader) {
        long systemVa = va();
        long loaderVa = loader.va();
        setShaderLoader(systemVa, loaderVa);
    }
    // *************************************************************************
    // new protected methods

    /**
     * Assign a native object (assuming there's none already assigned) and
     * designate the JVM object as a co-owner.
     *
     * @param systemVa the virtual address of the native object to assign (not
     * zero)
     */
    final protected void setVirtualAddressAsCoOwner(long systemVa) {
        long refVa = toRef(systemVa);
        Runnable freeingAction = () -> ComputeSystemRef.free(refVa);
        setVirtualAddress(systemVa, freeingAction);
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
        long settingsVa = va();
        long refVa = toRef(settingsVa);
        ComputeSystemRef result = new ComputeSystemRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long createComputeQueue(long systemVa);

    native private static int getRefCount(long systemVa);

    native private static long getRtti(long systemVa);

    native private static void setEmbedded(long systemVa);

    native private static void setShaderLoader(long systemVa, long loaderVa);

    native private static long toRef(long systemVa);
}
