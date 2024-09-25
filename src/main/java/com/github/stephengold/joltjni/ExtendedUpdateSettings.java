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

import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Settings for extended update of a virtual character. (native type:
 * {@code CharacterVirtual::ExtendedUpdateSettings})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ExtendedUpdateSettings extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public ExtendedUpdateSettings() {
        long settingsVa = createDefault();
        setVirtualAddress(settingsVa, () -> free(settingsVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the StickToFloor inStepDown parameter. The settings are unaffected.
     * (native attribute: mStickToFloorStepDown)
     *
     * @return a new offset vector
     */
    public Vec3 getStickToFloorStepDown() {
        long settingsVa = va();
        float x = getStickToFloorStepDownX(settingsVa);
        float y = getStickToFloorStepDownY(settingsVa);
        float z = getStickToFloorStepDownZ(settingsVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the cosine of the maximum angle between the ground normal and the
     * horizontal for adjusting the step-forward test. The settings are
     * unaffected. (native attribute: mWalkStairsCosAngleForwardContact)
     *
     * @return the cosine of the maximum angle
     */
    public float getWalkStairsCosAngleForwardContact() {
        long settingsVa = va();
        float result = getWalkStairsCosAngleForwardContact(settingsVa);

        return result;
    }

    /**
     * Return the StickToFloor inStepForward parameter. The settings are
     * unaffected. (native attribute: mWalkStairsMinStepForward)
     *
     * @return the distance
     */
    public float getWalkStairsMinStepForward() {
        long settingsVa = va();
        float result = getWalkStairsMinStepForward(settingsVa);

        return result;
    }

    /**
     * Return the StickToFloor inStepForwardTest parameter. The settings are
     * unaffected. (native attribute: mWalkStairsStepForwardTest)
     *
     * @return the distance
     */
    public float getWalkStairsStepForwardTest() {
        long settingsVa = va();
        float result = getWalkStairsStepForwardTest(settingsVa);

        return result;
    }

    /**
     * Copy the StickToFloor inStepUp parameter. The settings are unaffected.
     * (native attribute: mWalkStairsStepUp)
     *
     * @return a new offset vector
     */
    public Vec3 getWalkStairsStepUp() {
        long settingsVa = va();
        float x = getWalkStairsStepUpX(settingsVa);
        float y = getWalkStairsStepUpY(settingsVa);
        float z = getWalkStairsStepUpZ(settingsVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Alter the StickToFloor inStepDown parameter. (native attribute:
     * mStickToFloorStepDown)
     *
     * @param offset the desired offset (not null, unaffected, zero=off,
     * default=(0, -0.5, 0))
     */
    public void setStickToFloorStepDown(Vec3Arg offset) {
        long settingsVa = va();
        float x = offset.getX();
        float y = offset.getY();
        float z = offset.getZ();
        setStickToFloorStepDown(settingsVa, x, y, z);
    }

    /**
     * Alter the cosine of the maximum angle between the ground normal and the
     * horizontal for adjusting the step-forward test. (native attribute:
     * mWalkStairsCosAngleForwardContact)
     *
     * @param cosine the cosine of the maximum angle (default=cos(75 degrees)}
     */
    public void setWalkStairsCosAngleForwardContact(float cosine) {
        long settingsVa = va();
        setWalkStairsCosAngleForwardContact(settingsVa, cosine);
    }

    /**
     * Alter the StickToFloor inStepForward parameter. (native attribute:
     * mWalkStairsMinStepForward)
     *
     * @param distance the desired distance (default=0.02f)
     */
    public void setWalkStairsMinStepForward(float distance) {
        long settingsVa = va();
        setWalkStairsMinStepForward(settingsVa, distance);
    }

    /**
     * Alter the StickToFloor inStepForwardTest parameter. (native attribute:
     * mWalkStairsStepForwardTest)
     *
     * @param distance the desired distance (default=0.15f)
     */
    public void setWalkStairsStepForwardTest(float distance) {
        long settingsVa = va();
        setWalkStairsStepForwardTest(settingsVa, distance);
    }

    /**
     * Alter the StickToFloor inStepUp parameter. (native attribute:
     * mWalkStairsStepUp)
     *
     * @param offset the desired offset (not null, unaffected, zero=off,
     * default=(0, 0.4, 0))
     */
    public void setWalkStairsStepUp(Vec3Arg offset) {
        long settingsVa = va();
        float x = offset.getX();
        float y = offset.getY();
        float z = offset.getZ();
        setWalkStairsStepUp(settingsVa, x, y, z);
    }
    // *************************************************************************
    // native private methods

    native private static long createDefault();

    native private static void free(long settingsVa);

    native private static float getStickToFloorStepDownX(long settingsVa);

    native private static float getStickToFloorStepDownY(long settingsVa);

    native private static float getStickToFloorStepDownZ(long settingsVa);

    native private static float getWalkStairsCosAngleForwardContact(
            long settingsVa);

    native private static float getWalkStairsMinStepForward(long settingsVa);

    native private static float getWalkStairsStepForwardTest(long settingsVa);

    native private static float getWalkStairsStepUpX(long settingsVa);

    native private static float getWalkStairsStepUpY(long settingsVa);

    native private static float getWalkStairsStepUpZ(long settingsVa);

    native private static void setStickToFloorStepDown(
            long settingsVa, float x, float y, float z);

    native private static void setWalkStairsCosAngleForwardContact(
            long settingsVa, float cosine);

    native private static void setWalkStairsMinStepForward(
            long settingsVa, float distance);

    native private static void setWalkStairsStepForwardTest(
            long settingsVa, float distance);

    native private static void setWalkStairsStepUp(
            long settingsVa, float x, float y, float z);
}
