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

import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.Mat44Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Utilities to collect collisions between shapes.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class CollisionDispatch {
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private CollisionDispatch() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Collide the specified shapes and pass collisions to the specified
     * collector.
     *
     * @param shape1 the first colliding shape (not null, unaffected)
     * @param shape2 the 2nd colliding shape (not null, unaffected)
     * @param scale1 the scale to be applied to {@code shape1} (not null,
     * unaffected)
     * @param scale2 the scale to be applied to {@code shape2} (not null,
     * unaffected)
     * @param comTransform1 from the center-of-mass of {@code shape1} to system
     * coordinates (not null, unaffected)
     * @param comTransform2 from the center-of-mass of {@code shape2} to system
     * coordinates (not null, unaffected)
     * @param creator1 to generate sub-shape IDs {@code shape1} (not null)
     * @param creator2 to generate sub-shape IDs {@code shape2} (not null)
     * @param collideShapeSettings options for the test (not null, unaffected)
     * @param collector to collect the results (not null, modified)
     */
    public static void sCollideShapeVsShape(ConstShape shape1,
            ConstShape shape2, Vec3Arg scale1, Vec3Arg scale2,
            Mat44Arg comTransform1, Mat44Arg comTransform2,
            SubShapeIdCreator creator1, SubShapeIdCreator creator2,
            CollideShapeSettings collideShapeSettings,
            CollideShapeCollector collector) {
        sCollideShapeVsShape(shape1, shape2, scale1, scale2,
                comTransform1, comTransform2, creator1, creator2,
                collideShapeSettings, collector, new ShapeFilter());
    }

    /**
     * Collide the specified shapes and pass collisions to the specified
     * collector.
     *
     * @param shape1 the first colliding shape (not null, unaffected)
     * @param shape2 the 2nd colliding shape (not null, unaffected)
     * @param scale1 the scale to be applied to {@code shape1} (not null,
     * unaffected)
     * @param scale2 the scale to be applied to {@code shape2} (not null,
     * unaffected)
     * @param comTransform1 from the center-of-mass of {@code shape1} to system
     * coordinates (not null, unaffected)
     * @param comTransform2 from the center-of-mass of {@code shape2} to system
     * coordinates (not null, unaffected)
     * @param creator1 to generate sub-shape IDs {@code shape1} (not null)
     * @param creator2 to generate sub-shape IDs {@code shape2} (not null)
     * @param collideShapeSettings options for the test (not null, unaffected)
     * @param collector to collect the results (not null, modified)
     * @param filter to selectively ignore collisions (not null, unaffected)
     */
    public static void sCollideShapeVsShape(ConstShape shape1,
            ConstShape shape2, Vec3Arg scale1, Vec3Arg scale2,
            Mat44Arg comTransform1, Mat44Arg comTransform2,
            SubShapeIdCreator creator1, SubShapeIdCreator creator2,
            CollideShapeSettings collideShapeSettings,
            CollideShapeCollector collector, ShapeFilter filter) {
        long shape1Va = shape1.targetVa();
        long shape2Va = shape2.targetVa();
        float s1x = scale1.getX();
        float s1y = scale1.getY();
        float s1z = scale1.getZ();
        float s2x = scale2.getX();
        float s2y = scale2.getY();
        float s2z = scale2.getZ();
        long comTransform1Va = comTransform1.targetVa();
        long comTransform2Va = comTransform2.targetVa();
        long creator1Va = creator1.targetVa();
        long creator2Va = creator2.targetVa();
        long settingsVa = collideShapeSettings.targetVa();
        long collectorVa = collector.targetVa();
        long filterVa = filter.targetVa();
        sCollideShapeVsShape(shape1Va, shape2Va, s1x, s1y, s1z, s2x, s2y, s2z,
                comTransform1Va, comTransform2Va, creator1Va, creator2Va,
                settingsVa, collectorVa, filterVa);
    }
    // *************************************************************************
    // native private methods

    native private static void sCollideShapeVsShape(long shape1Va,
            long shape2Va, float s1x, float s1y, float s1z, float s2x,
            float s2y, float s2z, long comTransform1Va, long comTransform2Va,
            long creator1Va, long creator2Va, long settingsVa,
            long collectorVa, long filterVa);
}
