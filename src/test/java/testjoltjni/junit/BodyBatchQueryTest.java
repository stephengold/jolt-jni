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

import com.github.stephengold.joltjni.BatchBodyInterface;
import com.github.stephengold.joltjni.BodyCreationSettings;
import com.github.stephengold.joltjni.BodyIdArray;
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
     * Test all batch getter, setter, and status methods
     * in {@code BodyInterface}.
     */
    @Test
    public void testBodyBatchQueries() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        final int numBodies = 5;
        PhysicsSystem physicsSystem = TestUtils.newPhysicsSystem(100);
        BatchBodyInterface bodyInterface = physicsSystem.getBodyInterface();
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

        verifyFloatSetters(bodyInterface, ids, numBodies);
        verifyVec3Setters(bodyInterface, ids, numBodies);
        verifyDoubleSetters(bodyInterface, ids, numBodies);
        verifyIntSetters(bodyInterface, ids, numBodies);
        verifyLongSetters(bodyInterface, ids, numBodies);

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
            BatchBodyInterface bi, BodyIdArray ids, int n) {
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
            BatchBodyInterface bi, BodyIdArray ids, int n) {
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
            BatchBodyInterface bi, BodyIdArray ids, int n) {
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
            BatchBodyInterface bi, BodyIdArray ids, int n) {
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
            BatchBodyInterface bi, BodyIdArray ids, int n) {
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
            BatchBodyInterface bi, BodyIdArray ids, int n) {
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
            BatchBodyInterface bi, BodyIdArray ids, int n) {
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

    /**
     * Verify batch double setters (position).
     *
     * @param bi  the interface to use (not null)
     * @param ids IDs of bodies to modify (not null)
     * @param n   the number of bodies
     */
    private void verifyDoubleSetters(
            BatchBodyInterface bi, BodyIdArray ids, int n) {
        DoubleBuffer posBuf = Jolt.newDirectDoubleBuffer(n * 3);
        for (int i = 0; i < n; ++i) {
            posBuf.put(i * 3, 100.0 + i);
            posBuf.put(i * 3 + 1, 200.0 + i);
            posBuf.put(i * 3 + 2, 300.0 + i);
        }
        bi.setPositions(ids, posBuf, EActivation.DontActivate);
        for (int i = 0; i < n; ++i) {
            RVec3 p = bi.getPosition(ids.get(i));
            Assert.assertEquals(100.0 + i, p.xx(), 1e-6);
            Assert.assertEquals(200.0 + i, p.yy(), 1e-6);
            Assert.assertEquals(300.0 + i, p.zz(), 1e-6);
        }
    }

    /**
     * Verify batch float setters (friction, gravity factor, restitution).
     *
     * @param bi  the interface to use (not null)
     * @param ids IDs of bodies to modify (not null)
     * @param n   the number of bodies
     */
    private void verifyFloatSetters(
            BatchBodyInterface bi, BodyIdArray ids, int n) {
        FloatBuffer floatBuf = Jolt.newDirectFloatBuffer(n);

        for (int i = 0; i < n; ++i) {
            floatBuf.put(i, 0.5f + 0.01f * i);
        }
        bi.setFrictions(ids, floatBuf);
        for (int i = 0; i < n; ++i) {
            Assert.assertEquals(0.5f + 0.01f * i,
                    bi.getFriction(ids.get(i)), 1e-6f);
        }

        for (int i = 0; i < n; ++i) {
            floatBuf.put(i, 2.0f + i);
        }
        bi.setGravityFactors(ids, floatBuf);
        for (int i = 0; i < n; ++i) {
            Assert.assertEquals(2.0f + i,
                    bi.getGravityFactor(ids.get(i)), 1e-6f);
        }

        for (int i = 0; i < n; ++i) {
            floatBuf.put(i, 0.3f + 0.01f * i);
        }
        bi.setRestitutions(ids, floatBuf);
        for (int i = 0; i < n; ++i) {
            Assert.assertEquals(0.3f + 0.01f * i,
                    bi.getRestitution(ids.get(i)), 1e-6f);
        }
    }

    /**
     * Verify batch integer setters (object layer).
     *
     * @param bi  the interface to use (not null)
     * @param ids IDs of bodies to modify (not null)
     * @param n   the number of bodies
     */
    private void verifyIntSetters(
            BatchBodyInterface bi, BodyIdArray ids, int n) {
        IntBuffer intBuf = Jolt.newDirectIntBuffer(n);
        for (int i = 0; i < n; ++i) {
            intBuf.put(i, TestUtils.objLayerMoving);
        }
        bi.setObjectLayers(ids, intBuf);
        for (int i = 0; i < n; ++i) {
            Assert.assertEquals(TestUtils.objLayerMoving,
                    bi.getObjectLayer(ids.get(i)));
        }
    }

    /**
     * Verify batch long setters (user data).
     *
     * @param bi  the interface to use (not null)
     * @param ids IDs of bodies to modify (not null)
     * @param n   the number of bodies
     */
    private void verifyLongSetters(
            BatchBodyInterface bi, BodyIdArray ids, int n) {
        LongBuffer dataBuf = Jolt.newDirectLongBuffer(n);
        for (int i = 0; i < n; ++i) {
            dataBuf.put(i, 9999L + i);
        }
        bi.setUserDatas(ids, dataBuf);
        for (int i = 0; i < n; ++i) {
            Assert.assertEquals(9999L + i, bi.getUserData(ids.get(i)));
        }
    }

    /**
     * Verify batch Vec3 setters (angular velocity, linear velocity).
     *
     * @param bi  the interface to use (not null)
     * @param ids IDs of bodies to modify (not null)
     * @param n   the number of bodies
     */
    private void verifyVec3Setters(
            BatchBodyInterface bi, BodyIdArray ids, int n) {
        FloatBuffer vec3Buf = Jolt.newDirectFloatBuffer(n * 3);

        for (int i = 0; i < n; ++i) {
            vec3Buf.put(i * 3, 10f + i);
            vec3Buf.put(i * 3 + 1, 20f + i);
            vec3Buf.put(i * 3 + 2, 30f + i);
        }
        bi.setLinearVelocities(ids, vec3Buf);
        for (int i = 0; i < n; ++i) {
            Vec3 v = bi.getLinearVelocity(ids.get(i));
            Assert.assertEquals(10f + i, v.getX(), 1e-6f);
            Assert.assertEquals(20f + i, v.getY(), 1e-6f);
            Assert.assertEquals(30f + i, v.getZ(), 1e-6f);
        }

        for (int i = 0; i < n; ++i) {
            vec3Buf.put(i * 3, 1f + i);
            vec3Buf.put(i * 3 + 1, 2f + i);
            vec3Buf.put(i * 3 + 2, 3f + i);
        }
        bi.setAngularVelocities(ids, vec3Buf);
        for (int i = 0; i < n; ++i) {
            Vec3 w = bi.getAngularVelocity(ids.get(i));
            Assert.assertEquals(1f + i, w.getX(), 1e-6f);
            Assert.assertEquals(2f + i, w.getY(), 1e-6f);
            Assert.assertEquals(3f + i, w.getZ(), 1e-6f);
        }
    }
}
