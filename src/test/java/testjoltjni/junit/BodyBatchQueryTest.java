/*
Copyright (c) 2026 Stephen Gold

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

import com.github.stephengold.joltjni.BodyCreationSettings;
import com.github.stephengold.joltjni.BodyIdArray;
import com.github.stephengold.joltjni.BodyInterface;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.PhysicsSystem;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RMat44;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.ShapeRefC;
import com.github.stephengold.joltjni.SphereShape;
import com.github.stephengold.joltjni.TransformedShape;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.EActivation;
import com.github.stephengold.joltjni.enumerate.EMotionType;
import com.github.stephengold.joltjni.readonly.ConstShape;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Automated JUnit4 tests for batch query operations in {@code BodyInterface}.
 *
 * @author xI-Mx-Ix
 */
public class BodyBatchQueryTest {
    // *************************************************************************
    // new methods exposed

    /**
     * Test all batch getter and status methods in {@code BodyInterface}.
     */
    @Test
    public void testBodyBatchQueries() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        final int numBodies = 5;
        PhysicsSystem physicsSystem = TestUtils.newPhysicsSystem(100);
        BodyInterface bodyInterface = physicsSystem.getBodyInterface();
        BodyIdArray ids = new BodyIdArray(numBodies);

        for (int i = 0; i < numBodies; ++i) {
            SphereShape shape = new SphereShape(0.5f + i);
            RVec3 pos = new RVec3(i * 10.0, 5.0, 0.0);
            float angle = (float) Math.PI * i / 4.0f;
            Quat rot = Quat.sRotation(Vec3.sAxisY(), angle);

            BodyCreationSettings settings = new BodyCreationSettings(
                    shape, pos, rot, EMotionType.Dynamic,
                    TestUtils.objLayerMoving);

            settings.setFriction(0.1f * i);
            settings.setRestitution(0.05f * i);
            settings.setGravityFactor(1.0f + i);
            settings.setUserData(1000L + i);

            int bodyId = bodyInterface.createAndAddBody(
                    settings, EActivation.Activate);
            bodyInterface.setLinearVelocity(bodyId, new Vec3(i, 0, 0));
            ids.set(i, bodyId);

            TestUtils.testClose(shape, settings);
        }

        verifyStatusBatch(bodyInterface, ids, numBodies);
        verifyFloatGetters(bodyInterface, ids, numBodies);
        verifyVec3QuatGetters(bodyInterface, ids, numBodies);
        verifyDoubleGetters(bodyInterface, ids, numBodies);
        verifyMatrixGetters(bodyInterface, ids, numBodies);
        verifyIntGetters(bodyInterface, ids, numBodies);
        verifyLongGetters(bodyInterface, ids, numBodies);

        TestUtils.testClose(ids);
        TestUtils.cleanupPhysicsSystem(physicsSystem);
        TestUtils.cleanup();
    }

    // *************************************************************************
    // private methods

    /**
     * Verify batch position getters.
     *
     * @param bi  the interface to use (not null)
     * @param ids IDs of bodies to query (not null)
     * @param n   the number of bodies
     */
    private void verifyDoubleGetters(
            BodyInterface bi, BodyIdArray ids, int n) {
        DoubleBuffer posBuf = Jolt.newDirectDoubleBuffer(n * 3);
        bi.getPositions(ids, posBuf);
        for (int i = 0; i < n; ++i) {
            RVec3 p = bi.getPosition(ids.get(i));
            Assert.assertEquals(p.xx(), posBuf.get(i * 3), 1e-6);
        }
    }

    /**
     * Verify batch float getters (friction, gravity factor, restitution).
     *
     * @param bi  the interface to use (not null)
     * @param ids IDs of bodies to query (not null)
     * @param n   the number of bodies
     */
    private void verifyFloatGetters(
            BodyInterface bi, BodyIdArray ids, int n) {
        FloatBuffer floatBuf = Jolt.newDirectFloatBuffer(n);

        bi.getFrictions(ids, floatBuf);
        for (int i = 0; i < n; ++i) {
            Assert.assertEquals(bi.getFriction(ids.get(i)),
                    floatBuf.get(i), 1e-6f);
        }

        bi.getGravityFactors(ids, floatBuf);
        for (int i = 0; i < n; ++i) {
            Assert.assertEquals(bi.getGravityFactor(ids.get(i)),
                    floatBuf.get(i), 1e-6f);
        }

        bi.getRestitutions(ids, floatBuf);
        for (int i = 0; i < n; ++i) {
            Assert.assertEquals(bi.getRestitution(ids.get(i)),
                    floatBuf.get(i), 1e-6f);
        }
    }

    /**
     * Verify batch integer getters (layer, type).
     *
     * @param bi  the interface to use (not null)
     * @param ids IDs of bodies to query (not null)
     * @param n   the number of bodies
     */
    private void verifyIntGetters(
            BodyInterface bi, BodyIdArray ids, int n) {
        IntBuffer intBuf = Jolt.newDirectIntBuffer(n);
        bi.getObjectLayers(ids, intBuf);
        for (int i = 0; i < n; ++i) {
            Assert.assertEquals(bi.getObjectLayer(ids.get(i)), intBuf.get(i));
        }

        bi.getBodyTypes(ids, intBuf);
        for (int i = 0; i < n; ++i) {
            Assert.assertEquals(bi.getBodyType(ids.get(i)).ordinal(),
                    intBuf.get(i));
        }
    }

    /**
     * Verify batch long getters (user data, shape, and transformed shape).
     *
     * @param bi  the interface to use (not null)
     * @param ids IDs of bodies to query (not null)
     * @param n   the number of bodies (&ge;0)
     */
    private void verifyLongGetters(
            BodyInterface bi, BodyIdArray ids, int n) {
        LongBuffer dataBuf = Jolt.newDirectLongBuffer(n);

        // Verify UserData
        bi.getUserDatas(ids, dataBuf);
        for (int i = 0; i < n; ++i) {
            Assert.assertEquals(bi.getUserData(ids.get(i)), dataBuf.get(i));
        }

        // Verify Shapes
        ShapeRefC[] batchShapes = bi.getShapes(ids);
        for (int i = 0; i < n; ++i) {
            ShapeRefC singularRef = bi.getShape(ids.get(i));
            Assert.assertEquals(
                    singularRef.targetVa(), batchShapes[i].targetVa());
            TestUtils.testClose(singularRef, batchShapes[i]);
        }

        // Verify TransformedShapes
        TransformedShape[] batchTs = bi.getTransformedShapes(ids);
        for (int i = 0; i < n; ++i) {
            TransformedShape singularTs = bi.getTransformedShape(ids.get(i));
            ConstShape s1 = singularTs.getShape();
            ConstShape s2 = batchTs[i].getShape();

            Assert.assertEquals(s1.targetVa(), s2.targetVa());
            TestUtils.testClose(s1, s2, singularTs, batchTs[i]);
        }
    }

    /**
     * Verify batch matrix getters (COM transform).
     *
     * @param bi  the interface to use (not null)
     * @param ids IDs of bodies to query (not null)
     * @param n   the number of bodies
     */
    private void verifyMatrixGetters(
            BodyInterface bi, BodyIdArray ids, int n) {
        DoubleBuffer matBuf = Jolt.newDirectDoubleBuffer(n * 16);
        bi.getCenterOfMassTransforms(ids, matBuf);
        for (int i = 0; i < n; ++i) {
            RMat44 m = bi.getCenterOfMassTransform(ids.get(i));
            Assert.assertEquals(m.getElement(0, 0), matBuf.get(i * 16), 1e-6);
            TestUtils.testClose(m);
        }
    }

    /**
     * Verify batch status getters (active, added, sensor).
     *
     * @param bi  the interface to use (not null)
     * @param ids IDs of bodies to query (not null)
     * @param n   the number of bodies
     */
    private void verifyStatusBatch(
            BodyInterface bi, BodyIdArray ids, int n) {
        IntBuffer statusBuf = Jolt.newDirectIntBuffer(n);

        bi.areActive(ids, statusBuf);
        for (int i = 0; i < n; ++i) {
            int expected = bi.isActive(ids.get(i)) ? 1 : 0;
            Assert.assertEquals(expected, statusBuf.get(i));
        }

        bi.areAdded(ids, statusBuf);
        for (int i = 0; i < n; ++i) {
            int expected = bi.isAdded(ids.get(i)) ? 1 : 0;
            Assert.assertEquals(expected, statusBuf.get(i));
        }

        bi.areSensors(ids, statusBuf);
        for (int i = 0; i < n; ++i) {
            int expected = bi.isSensor(ids.get(i)) ? 1 : 0;
            Assert.assertEquals(expected, statusBuf.get(i));
        }
    }

    /**
     * Verify batch float component getters (velocity, rotation).
     *
     * @param bi  the interface to use (not null)
     * @param ids IDs of bodies to query (not null)
     * @param n   the number of bodies
     */
    private void verifyVec3QuatGetters(
            BodyInterface bi, BodyIdArray ids, int n) {
        FloatBuffer vec3Buf = Jolt.newDirectFloatBuffer(n * 3);
        FloatBuffer quatBuf = Jolt.newDirectFloatBuffer(n * 4);

        bi.getLinearVelocities(ids, vec3Buf);
        for (int i = 0; i < n; ++i) {
            Vec3 v = bi.getLinearVelocity(ids.get(i));
            Assert.assertEquals(v.getX(), vec3Buf.get(i * 3), 1e-6f);
        }

        bi.getRotations(ids, quatBuf);
        for (int i = 0; i < n; ++i) {
            Quat q = bi.getRotation(ids.get(i));
            Assert.assertEquals(q.getX(), quatBuf.get(i * 4 + 0), 1e-6f);
            Assert.assertEquals(q.getY(), quatBuf.get(i * 4 + 1), 1e-6f);
            Assert.assertEquals(q.getZ(), quatBuf.get(i * 4 + 2), 1e-6f);
            Assert.assertEquals(q.getW(), quatBuf.get(i * 4 + 3), 1e-6f);
        }
    }
}
