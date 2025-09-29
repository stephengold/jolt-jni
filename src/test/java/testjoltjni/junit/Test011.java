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
import com.github.stephengold.joltjni.CustomDebugRendererSimple;
import com.github.stephengold.joltjni.CustomPhysicsStepListener;
import com.github.stephengold.joltjni.CustomRayCastBodyCollector;
import com.github.stephengold.joltjni.CustomSoftBodyContactListener;
import com.github.stephengold.joltjni.DebugRenderer;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.JoltPhysicsObject;
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

        if (Jolt.implementsDebugRendering()) {
            DebugRenderer dr = new CustomDebugRendererSimple() {
                @Override
                public void drawLine(double x1, double y1, double z1,
                        double x2, double y2, double z2, int colorInt) {
                }

                @Override
                public void drawText3d(double xx, double yy, double zz,
                        String text, int colorInt, float height) {
                }

                @Override
                public void drawTriangle(double x1, double y1, double z1,
                        double x2, double y2, double z2, double x3, double y3,
                        double z3, int colorInt, int ordinal) {
                }
            };
            TestUtils.testClose(dr);
        }

        TestUtils.cleanup();
    }
    // *************************************************************************
    // Java private methods

    /**
     * Test the custom collector classes.
     */
    private static void createCustomCollectors() {
        JoltPhysicsObject castRay = new CustomCastRayCollector() {
            @Override
            public void addHit(long resultVa) {
            }
        };

        JoltPhysicsObject castShape = new CustomCastShapeCollector() {
            @Override
            public void addHit(long resultVa) {
            }
        };

        JoltPhysicsObject coPoint = new CustomCollidePointCollector() {
            @Override
            public void addHit(long resultVa) {
            }
        };

        JoltPhysicsObject coShapeBody = new CustomCollideShapeBodyCollector() {
            @Override
            public void addHit(int bodyId) {
            }
        };

        JoltPhysicsObject coShape = new CustomCollideShapeCollector() {
            @Override
            public void addHit(long resultVa) {
            }
        };

        JoltPhysicsObject rcb = new CustomRayCastBodyCollector() {
            @Override
            public void addHit(long resultVa) {
            }
        };

        TestUtils.testClose(
                castRay, castShape, coPoint, coShapeBody, coShape, rcb);
    }

    /**
     * Test the custom listener classes.
     */
    private static void createCustomListeners() {
        JoltPhysicsObject ba = new CustomBodyActivationListener();
        JoltPhysicsObject cc = new CustomCharacterContactListener();
        JoltPhysicsObject contact = new CustomContactListener();
        JoltPhysicsObject psl = new CustomPhysicsStepListener();
        JoltPhysicsObject sbc = new CustomSoftBodyContactListener();

        TestUtils.testClose(ba, cc, contact, psl, sbc);
    }
}
