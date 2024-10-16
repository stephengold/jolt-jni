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
 * Information about a broad-phase cast hit.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BroadPhaseCastResult extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with no containing object and no native object assigned.
     */
    BroadPhaseCastResult() {
    }

    /**
     * Instantiate with the specified container and native object.
     * <p>
     * For use in custom collectors.
     *
     * @param container the containing object, or {@code null} if none
     * @param castResultVa the virtual address of the native object to assign
     * (not zero)
     */
    public BroadPhaseCastResult(
            JoltPhysicsObject container, long castResultVa) {
        super(container, castResultVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the ID of the body that was hit. The result object is unaffected.
     * (native attribute: mBodyID)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public BodyId getBodyId() {
        long castResultVa = va();
        long idVa = getBodyId(castResultVa);
        BodyId result = new BodyId(idVa, false);

        return result;
    }

    /**
     * Access the underlying {@code RayCastBodyCollector}.
     *
     * @return the pre-existing instance
     */
    public RayCastBodyCollector getCollector() {
        JoltPhysicsObject container = this.getContainingObject();
        RayCastBodyCollector result;
        if (container instanceof RayCastBodyCollector) {
            result = (RayCastBodyCollector) container;
        } else {
            result = null;
        }

        return result;
    }

    /**
     * Return the location of the hit, as a fraction of the cast path. The
     * result object is unaffected. (native attribute: mFraction)
     *
     * @return the fraction (&ge;0)
     */
    public float getFraction() {
        long castResultVa = va();
        float result = getFraction(castResultVa);

        return result;
    }
    // *************************************************************************
    // protected methods

    /**
     * Assign a native object, assuming there's none already assigned.
     *
     * @param castResultVa the virtual address of the native object to assign
     * (not zero)
     * @param owner true &rarr; make the JVM object the owner, false &rarr; it
     * isn't the owner
     */
    void setVirtualAddress(long castResultVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(castResultVa) : null;
        setVirtualAddress(castResultVa, freeingAction);
    }
    // *************************************************************************
    // native private methods

    native private static void free(long castResultVa);

    native private static long getBodyId(long castResultVa);

    native private static float getFraction(long castResultVa);
}
