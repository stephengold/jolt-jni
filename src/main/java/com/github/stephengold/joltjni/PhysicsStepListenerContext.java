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
 * Context passed to a physics-step listener.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class PhysicsStepListenerContext extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a context with the specified native object assigned but not
     * owned.
     * <p>
     * For use in custom contact listeners.
     *
     * @param contextVa the virtual address of the native object to assign (not
     * zero)
     */
    public PhysicsStepListenerContext(long contextVa) {
        setVirtualAddress(contextVa, null);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the duration of the current step. The context is unaffected.
     * (native attribute: mDeltaTime)
     *
     * @return the duration (in seconds, &gt;0)
     */
    public float getDeltaTime() {
        long contextVa = va();
        float result = getDeltaTime(contextVa);

        return result;
    }

    /**
     * Test whether this is the first step. The context is unaffected. (native
     * attribute: mIsFirstStep)
     *
     * @return true if it's the first step, otherwise false
     */
    public boolean getIsFirstStep() {
        long contextVa = va();
        boolean result = getIsFirstStep(contextVa);

        return result;
    }

    /**
     * Test whether this is the last step. The context is unaffected. (native
     * attribute: mIsLastStep)
     *
     * @return true if it's the last step, otherwise false
     */
    public boolean getIsLastStep() {
        long contextVa = va();
        boolean result = getIsLastStep(contextVa);

        return result;
    }

    /**
     * Access the system being simulated. The context is unaffected. (native
     * attribute: mPhysicsSystem)
     *
     * @return the pre-existing object, or {@code null} if not found
     */
    public PhysicsSystem getPhysicsSystem() {
        long contextVa = va();
        long systemVa = getPhysicsSystem(contextVa);
        PhysicsSystem result = PhysicsSystem.find(systemVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static float getDeltaTime(long contextVa);

    native private static boolean getIsFirstStep(long contextVa);

    native private static boolean getIsLastStep(long contextVa);

    native private static long getPhysicsSystem(long contextVa);
}
