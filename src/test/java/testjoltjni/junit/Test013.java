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

import com.github.stephengold.joltjni.AaBox;
import com.github.stephengold.joltjni.BodyIdArray;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.operator.Op;
import com.github.stephengold.joltjni.readonly.ConstAaBox;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Test basic functionality of various Jolt-JNI classes.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test013 {
    // *************************************************************************
    // new methods exposed

    /**
     * Test basic functionality of various Jolt-JNI classes.
     */
    @Test
    public void test013() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        doAaBox();
        doBodyIdArray();

        TestUtils.cleanup();
    }
    // *************************************************************************
    // Java private methods

    /**
     * Test basic functionality of the {@code AaBox} class.
     */
    private static void doAaBox() {
        { // contains method:
            ConstAaBox box = new AaBox(new Vec3(0, 0, 0), 2);
            ConstAaBox smallBox = new AaBox(new Vec3(0, 0, 0), 1.5f);

            Vec3 point = new Vec3(0.5f, 0.5f, 0.25f);
            Assert.assertTrue(box.contains(smallBox));
            Assert.assertTrue(box.contains(point));
            Assert.assertFalse(smallBox.contains(box));

            point.set(2.1f, 0, 2);
            Assert.assertFalse(box.contains(point));
            TestUtils.testClose(box, smallBox);
        }
        { // encapsulate and getSqDistanceTo method:
            AaBox box = new AaBox(new Vec3(-1.25, -1, -0.23),
                    new Vec3(2, 1.75, 1.25));
            AaBox smallBox = new AaBox(new Vec3(0, 0, 0), 1.5f);

            smallBox.encapsulate(box);
            TestUtils.assertEquals(-1.5f, -1.5f, -1.5f, smallBox.getMin(), 0f);
            TestUtils.assertEquals(2f, 1.75f, 1.5f, smallBox.getMax(), 0f);

            Vec3 point = new Vec3(3, 3, 3);
            Assert.assertEquals(Op.minus(smallBox.getClosestPoint(point),
                    point).lengthSq(), smallBox.getSqDistanceTo(point), 0f);
            TestUtils.testClose(box, smallBox);
        }
        { // getSqDistanceTo method:
            ConstAaBox box = new AaBox(new Vec3(0, 0, 0), 2);
            Vec3 point = new Vec3(4, 4, 4);

            float distance = box.getSqDistanceTo(point);
            Assert.assertEquals(12, distance, 0f);

            point.set(2, -6, 8);
            distance = box.getSqDistanceTo(point);
            Assert.assertEquals(52, distance, 0f);
            TestUtils.testClose(box);
        }
        { // getSurfaceArea method:
            ConstAaBox box = new AaBox(new Vec3(-1, -3, -4.5f),
                    new Vec3(2.65f, 5.5f, 6.0f));
            Assert.assertEquals(317.2f, box.getSurfaceArea(), 0f);
            TestUtils.testClose(box);
        }
        { // getSupport method:
            ConstAaBox box = new AaBox(new Vec3(-1, -3, -4.5f),
                    new Vec3(2.65f, 5.5f, 6.0f));
            Vec3 support = box.getSupport(Vec3.sAxisZ());
            Vec3 manual = Vec3.sSelect(box.getMax(), box.getMin(),
                    Vec3.sLess(Vec3.sAxisZ(), Vec3.sZero()));

            TestUtils.assertEquals(2.65f, 5.5f, 6.0f, support, 0f);
            TestUtils.assertEquals(manual.getX(), manual.getY(), manual.getZ(),
                    support, 0f);
            TestUtils.testClose(box);
        }
        { // overlaps method:
            ConstAaBox boxA = new AaBox(new Vec3(0, 0, 0), 4);
            ConstAaBox boxB = new AaBox(new Vec3(-1, -3, -4.5f),
                    new Vec3(2.65f, 5.5f, 6.0f));
            ConstAaBox boxC = new AaBox(new Vec3(6, 0, 0), 1);
            ConstAaBox boxD = new AaBox(new Vec3(5, 0, 0), 0.5f);

            Assert.assertTrue(boxB.overlaps(boxA));
            Assert.assertTrue(boxA.overlaps(boxB));
            Assert.assertFalse(boxA.overlaps(boxC));
            Assert.assertFalse(boxA.overlaps(boxD));
            TestUtils.testClose(boxA, boxB, boxC, boxD);
        }
        { // ensureMinimalEdgeLength method:
            AaBox boxA = new AaBox(new Vec3(-2, -2, -2), new Vec3(0f, 0f, 0f));
            boxA.ensureMinimalEdgeLength(2.5f);
            TestUtils.assertEquals(-2f, -2f, -2f, boxA.getMin(), 0f);
            TestUtils.assertEquals(0.5f, 0.5f, 0.5f, boxA.getMax(), 0f);

            AaBox boxB = new AaBox(new Vec3(-1.5f, -2.60, -4),
                    new Vec3(0.5f, 0.4f, 0f));
            boxB.ensureMinimalEdgeLength(3.0f);
            TestUtils.assertEquals(-1.5f, -2.60f, -4f, boxB.getMin(), 0f);
            TestUtils.assertEquals(1.5f, 0.4f, 0f, boxB.getMax(), 0f);

            TestUtils.testClose(boxA, boxB);
        }

        System.gc();
    }

    /**
     * Test basic functionality of the {@code BodyIdArray} class.
     */
    private static void doBodyIdArray() {
        { // int constructor:
            BodyIdArray bodyIdArray = new BodyIdArray(3);
            Assert.assertEquals(3, bodyIdArray.length());

            bodyIdArray.set(0, 2);
            bodyIdArray.set(1, 4);
            bodyIdArray.set(2, 10);

            Assert.assertEquals(2, bodyIdArray.get(0));
            Assert.assertEquals(4, bodyIdArray.get(1));
            Assert.assertEquals(10, bodyIdArray.get(2));

            TestUtils.testClose(bodyIdArray);
        }
        { // int[] constructor:
            int[] idArray = {1, 5, 7, 19, 28, 49};
            BodyIdArray bodyIdArray = new BodyIdArray(idArray);
            int bodyIdArrayLength = bodyIdArray.length();

            Assert.assertEquals(idArray.length, bodyIdArrayLength);

            for (int i = 0; i < bodyIdArrayLength; i += 1) {
                int bodyId = idArray[i];
                int bodyIdRecovered = bodyIdArray.get(i);

                Assert.assertEquals(bodyId, bodyIdRecovered);
            }

            bodyIdArray.set(3, 501);
            bodyIdArray.set(2, 689);
            bodyIdArray.set(0, 600);

            Assert.assertEquals(501, bodyIdArray.get(3));
            Assert.assertEquals(689, bodyIdArray.get(2));
            Assert.assertEquals(600, bodyIdArray.get(0));

            TestUtils.testClose(bodyIdArray);
        }
        { // IntBuffer constructor:
            IntBuffer idBuffer = Jolt.newDirectIntBuffer(3);
            idBuffer.put(2);
            idBuffer.put(5);
            idBuffer.put(6);

            BodyIdArray bodyIdArrayA = new BodyIdArray(idBuffer);
            Assert.assertEquals(3, bodyIdArrayA.length());
            Assert.assertEquals(2, bodyIdArrayA.get(0));
            Assert.assertEquals(5, bodyIdArrayA.get(1));
            Assert.assertEquals(6, bodyIdArrayA.get(2));

            BodyIdArray bodyIdArrayB = new BodyIdArray(
                    Jolt.newDirectIntBuffer(2));
            Assert.assertEquals(2, bodyIdArrayB.length());
            Assert.assertEquals(0, bodyIdArrayB.get(0));
            Assert.assertEquals(0, bodyIdArrayB.get(1));

            bodyIdArrayB.set(0, 20);
            bodyIdArrayB.set(1, 65);

            Assert.assertEquals(20, bodyIdArrayB.get(0));
            Assert.assertEquals(65, bodyIdArrayB.get(1));

            TestUtils.testClose(bodyIdArrayA, bodyIdArrayB);
        }
        { // List<Integer> constructor:
            List<Integer> idList = new ArrayList<>();
            idList.addAll(Arrays.asList(3, 4, 6, 7, 3, 4, 6, 102));

            BodyIdArray bodyIdArray = new BodyIdArray(idList);
            int bodyIdArrayLength = bodyIdArray.length();

            Assert.assertEquals(idList.size(), bodyIdArrayLength);

            for (int i = 0; i < bodyIdArrayLength; i += 1) {
                int bodyId = idList.get(i);
                int bodyIdRecovered = bodyIdArray.get(i);

                Assert.assertEquals(bodyId, bodyIdRecovered);
            }

            bodyIdArray.set(0, 501);
            bodyIdArray.set(2, 689);
            bodyIdArray.set(5, 600);

            Assert.assertEquals(501, bodyIdArray.get(0));
            Assert.assertEquals(689, bodyIdArray.get(2));
            Assert.assertEquals(600, bodyIdArray.get(5));

            TestUtils.testClose(bodyIdArray);
        }

        System.gc();
    }
}
