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
package testjoltjni.junit;

import com.github.stephengold.joltjni.CustomBodyActivationListener;
import com.github.stephengold.joltjni.CustomCastRayCollector;
import com.github.stephengold.joltjni.CustomCastShapeCollector;
import com.github.stephengold.joltjni.CustomCharacterContactListener;
import com.github.stephengold.joltjni.CustomCollidePointCollector;
import com.github.stephengold.joltjni.CustomCollideShapeBodyCollector;
import com.github.stephengold.joltjni.CustomCollideShapeCollector;
import com.github.stephengold.joltjni.CustomContactListener;
import com.github.stephengold.joltjni.CustomPhysicsStepListener;
import com.github.stephengold.joltjni.CustomRayCastBodyCollector;
import com.github.stephengold.joltjni.CustomSoftBodyContactListener;
import com.github.stephengold.joltjni.enumerate.SoftBodyValidateResult;
import com.github.stephengold.joltjni.enumerate.ValidateResult;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Automated JUnit4 tests for creation of custom collectors and listeners.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test011 {
    // *************************************************************************
    // new methods exposed

    /**
     * Test object creation.
     */
    @Test
    public void test011() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        createCustomCollectors();
        createCustomListeners();

        TestUtils.cleanup();
    }
    // *************************************************************************
    // Java private methods

    /**
     * Test the custom collector classes.
     */
    private static void createCustomCollectors() {
        new CustomCastRayCollector() {
            @Override
            public void addHit(long resultVa) {
            }
        };

        new CustomCastShapeCollector() {
            @Override
            public void addHit(long resultVa) {
            }
        };

        new CustomCollidePointCollector() {
            @Override
            public void addHit(long resultVa) {
            }
        };

        new CustomCollideShapeBodyCollector() {
            @Override
            public void addHit(int bodyId) {
            }
        };

        new CustomCollideShapeCollector() {
            @Override
            public void addHit(long resultVa) {
            }
        };

        new CustomRayCastBodyCollector() {
            @Override
            public void addHit(long resultVa) {
            }
        };
    }

    /**
     * Test the custom listener classes.
     */
    private static void createCustomListeners() {
        new CustomBodyActivationListener() {
            @Override
            public void onBodyActivated(int bodyId, long bodyUserData) {
            }

            @Override
            public void onBodyDeactivated(int bodyId, long bodyUserData) {
            }
        };

        new CustomCharacterContactListener();

        new CustomContactListener() {
            @Override
            public void onContactAdded(long body1Va, long body2Va,
                    long manifoldVa, long settingsVa) {
            }

            @Override
            public void onContactPersisted(long body1Va, long body2Va,
                    long manifoldVa, long settingsVa) {
            }

            @Override
            public void onContactRemoved(long pairVa) {
            }

            @Override
            public int onContactValidate(long body1Va, long body2Va,
                    double baseOffsetX, double baseOffsetY, double baseOffsetZ,
                    long collisionResultVa) {
                return ValidateResult.AcceptContact.ordinal();
            }
        };

        new CustomPhysicsStepListener() {
            @Override
            public void onStep(long contextVa) {
            }
        };

        new CustomSoftBodyContactListener() {
            @Override
            public void onSoftBodyContactAdded(long bodyVa, long manifoldVa) {
            }

            @Override
            public int onSoftBodyContactValidate(
                    long softBodyVa, long otherBodyVa, long settingsVa) {
                return SoftBodyValidateResult.AcceptContact.ordinal();
            }
        };
    }
}
