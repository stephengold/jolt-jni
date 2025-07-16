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

import java.nio.FloatBuffer;

/**
 * Store estimates of the contact and friction impulses associated with a
 * collision, along with the resulting body velocities.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CollisionEstimationResult extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default estimate.
     */
    public CollisionEstimationResult() {
        long estimateVa = createDefault();
        setVirtualAddress(estimateVa, () -> free(estimateVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the angular velocity of body 1. The estimate is unaffected. (native
     * attribute: mAngularVelocity1)
     *
     * @return a new velocity vector (radians per second in system coordinates)
     */
    public Vec3 getAngularVelocity1() {
        long estimateVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getAngularVelocity1(estimateVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the angular velocity of body 2. The estimate is unaffected. (native
     * attribute: mAngularVelocity2)
     *
     * @return a new velocity vector (radians per second in system coordinates)
     */
    public Vec3 getAngularVelocity2() {
        long estimateVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getAngularVelocity2(estimateVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Access the impulse for every contact point, as an array. (native
     * attribute: mImpulses)
     *
     * @return a new array of new JVM objects with pre-existing native objects
     * assigned
     */
    public Impulse[] getImpulses() {
        long estimateVa = va();
        int numImpulses = countImpulses(estimateVa);
        Impulse[] result = new Impulse[numImpulses];
        for (int index = 0; index < numImpulses; ++index) {
            long impulseVa = getImpulse(estimateVa, index);
            result[index] = new Impulse(this, impulseVa);
        }

        return result;
    }

    /**
     * Copy the linear velocity of body 1. The estimate is unaffected. (native
     * attribute: mLinearVelocity1)
     *
     * @return a new velocity vector (meters per second in system coordinates)
     */
    public Vec3 getLinearVelocity1() {
        long estimateVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getLinearVelocity1(estimateVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the linear velocity of body 2. The estimate is unaffected. (native
     * attribute: mLinearVelocity1)
     *
     * @return a new velocity vector (meters per second in system coordinates)
     */
    public Vec3 getLinearVelocity2() {
        long estimateVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getLinearVelocity2(estimateVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static int countImpulses(long estimateVa);

    native private static long createDefault();

    native private static void free(long estimateVa);

    native private static void getAngularVelocity1(
            long estimateVa, FloatBuffer storeFloats);

    native private static void getAngularVelocity2(
            long estimateVa, FloatBuffer storeFloats);

    native private static long getImpulse(long estimateVa, int index);

    native private static void getLinearVelocity1(
            long estimateVa, FloatBuffer storeFloats);

    native private static void getLinearVelocity2(
            long estimateVa, FloatBuffer storeFloats);
}
