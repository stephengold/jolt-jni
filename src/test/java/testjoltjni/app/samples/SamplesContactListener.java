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
package testjoltjni.app.samples;

import com.github.stephengold.joltjni.Body;
import com.github.stephengold.joltjni.CollideShapeResult;
import com.github.stephengold.joltjni.ContactManifold;
import com.github.stephengold.joltjni.ContactSettings;
import com.github.stephengold.joltjni.CustomContactListener;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.SubShapeIdPair;
import com.github.stephengold.joltjni.enumerate.ValidateResult;
import com.github.stephengold.joltjni.readonly.ConstBody;
import com.github.stephengold.joltjni.readonly.ConstContactManifold;
import com.github.stephengold.joltjni.readonly.ConstSubShapeIdPair;
import com.github.stephengold.joltjni.readonly.RVec3Arg;

/**
 * A {@code ContactListener} for use with Jolt-JNI "Samples" tests.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SamplesContactListener extends CustomContactListener {
    // *************************************************************************
    // fields

    /**
     * the associated test
     */
    private Test test;
    // *************************************************************************
    // constructors

    /**
     * Instantiate a listener for the specified test.
     *
     * @param test the associated {@code Test}
     */
    SamplesContactListener(Test test) {
        this.test = test;
    }
    // *************************************************************************
    // CustomContactListener methods

    /**
     * Callback invoked (by native code) each time a new contact point is
     * detected. Meant to be overridden.
     *
     * @param body1Va the virtual address of the first body in contact (not
     * zero)
     * @param body2Va the virtual address of the 2nd body in contact (not zero)
     * @param manifoldVa the virtual address of the contact manifold (not zero)
     * @param settingsVa the virtual address of the contact settings (not zero)
     */
    @Override
    public void onContactAdded(long body1Va, long body2Va,
            long manifoldVa, long settingsVa) {
        ConstBody body1 = new Body(body1Va);
        ConstBody body2 = new Body(body2Va);
        ConstContactManifold manifold = new ContactManifold(manifoldVa);
        ContactSettings settings = new ContactSettings(settingsVa);
        test.OnContactAdded(body1, body2, manifold, settings);
    }

    /**
     * Callback invoked (by native code) each time a contact is detected that
     * was also detected during the previous update. Meant to be overridden.
     *
     * @param body1Va the virtual address of the first body in contact (not
     * zero)
     * @param body2Va the virtual address of the 2nd body in contact (not zero)
     * @param manifoldVa the virtual address of the contact manifold (not zero)
     * @param settingsVa the virtual address of the contact settings (not zero)
     */
    @Override
    public void onContactPersisted(long body1Va, long body2Va,
            long manifoldVa, long settingsVa) {
        ConstBody body1 = new Body(body1Va);
        ConstBody body2 = new Body(body2Va);
        ConstContactManifold manifold = new ContactManifold(manifoldVa);
        ContactSettings settings = new ContactSettings(settingsVa);
        test.OnContactPersisted(body1, body2, manifold, settings);
    }

    /**
     * Callback invoked (by native code) each time a contact that was detected
     * during the previous update is no longer detected. Meant to be overridden.
     *
     * @param pairVa the virtual address of the {@code SubShapeIDPair} (not
     * zero)
     */
    @Override
    public void onContactRemoved(long pairVa) {
        ConstSubShapeIdPair pair = new SubShapeIdPair(pairVa);
        test.OnContactRemoved(pair);
    }

    /**
     * Callback invoked (by native code) after detecting collision between a
     * pair of bodies, but before invoking {@code onContactAdded()} and before
     * adding the contact constraint. Meant to be overridden.
     *
     * @param body1Va the virtual address of the first body in contact (not
     * zero)
     * @param body2Va the virtual address of the 2nd body in contact (not zero)
     * @param baseOffsetX the X component of the base offset
     * @param baseOffsetY the Y component of the base offset
     * @param baseOffsetZ the Z component of the base offset
     * @param collisionResultVa the virtual address of the
     * {@code CollideShapeResult} (not zero)
     * @return how to the contact should be processed (an ordinal of
     * {@code ValidateResult})
     */
    @Override
    public int onContactValidate(long body1Va, long body2Va,
            double baseOffsetX, double baseOffsetY, double baseOffsetZ,
            long collisionResultVa) {
        ConstBody body1 = new Body(body1Va);
        ConstBody body2 = new Body(body2Va);
        RVec3Arg baseOffset
                = new RVec3(baseOffsetX, baseOffsetY, baseOffsetZ);
        CollideShapeResult collisionResult
                = new CollideShapeResult(collisionResultVa);
        ValidateResult result = test.OnContactValidate(
                body1, body2, baseOffset, collisionResult);
        int ordinal = result.ordinal();

        return ordinal;
    }
}
