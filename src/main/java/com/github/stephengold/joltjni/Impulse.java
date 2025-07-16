/*
Copyright (c) 2025 Stephen Gold

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
 * The estimated friction impulses of a collision. (native type:
 * {@code CollisionEstimationResult::Impulse})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Impulse extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default estimate.
     */
    public Impulse() {
        long estimateVa = createDefault();
        setVirtualAddress(estimateVa, () -> free(estimateVa));
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param impulseVa the virtual address of the native object to assign (not
     * zero)
     */
    Impulse(JoltPhysicsObject container, long impulseVa) {
        super(container, impulseVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the contact (normal) component. The estimate is unaffected.
     * (native attribute: mContactImpulse)
     *
     * @return the impulse component (in kilogram-meters per second)
     */
    public float getContactImpulse() {
        long estimateVa = va();
        float result = getContactImpulse(estimateVa);

        return result;
    }

    /**
     * Return the friction component in the direction of tangent 1. The estimate
     * is unaffected. (native attribute: mFrictionImpulse1)
     *
     * @return the impulse component (in kilogram-meters per second)
     */
    public float getFrictionImpulse1() {
        long estimateVa = va();
        float result = getFrictionImpulse1(estimateVa);

        return result;
    }

    /**
     * Return the friction component in the direction of tangent 2. The estimate
     * is unaffected. (native attribute: mFrictionImpulse2)
     *
     * @return the impulse component (in kilogram-meters per second)
     */
    public float getFrictionImpulse2() {
        long estimateVa = va();
        float result = getFrictionImpulse2(estimateVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createDefault();

    native private static void free(long estimateVa);

    native private static float getContactImpulse(long estimateVa);

    native private static float getFrictionImpulse1(long estimateVa);

    native private static float getFrictionImpulse2(long estimateVa);
}
