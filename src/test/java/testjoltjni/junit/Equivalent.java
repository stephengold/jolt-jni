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

import com.github.stephengold.joltjni.CollisionGroup;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.MotorSettings;
import com.github.stephengold.joltjni.ObjectStreamOut;
import com.github.stephengold.joltjni.PhysicsMaterial;
import com.github.stephengold.joltjni.PointConstraintSettings;
import com.github.stephengold.joltjni.RMat44;
import com.github.stephengold.joltjni.RackAndPinionConstraintSettings;
import com.github.stephengold.joltjni.StreamOutWrapper;
import com.github.stephengold.joltjni.SwingTwistConstraintSettings;
import com.github.stephengold.joltjni.TrackedVehicleControllerSettings;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.VehicleTrackSettings;
import com.github.stephengold.joltjni.enumerate.EStreamType;
import com.github.stephengold.joltjni.readonly.ConstAaBox;
import com.github.stephengold.joltjni.readonly.ConstBodyCreationSettings;
import com.github.stephengold.joltjni.readonly.ConstCollisionGroup;
import com.github.stephengold.joltjni.readonly.ConstColor;
import com.github.stephengold.joltjni.readonly.ConstConstraintSettings;
import com.github.stephengold.joltjni.readonly.ConstGroupFilter;
import com.github.stephengold.joltjni.readonly.ConstJoltPhysicsObject;
import com.github.stephengold.joltjni.readonly.ConstMassProperties;
import com.github.stephengold.joltjni.readonly.ConstSerializableObject;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.ConstShapeSettings;
import com.github.stephengold.joltjni.readonly.ConstSoftBodyCreationSettings;
import com.github.stephengold.joltjni.readonly.ConstSoftBodySharedSettings;
import com.github.stephengold.joltjni.readonly.ConstSpringSettings;
import com.github.stephengold.joltjni.readonly.ConstVehicleAntiRollBar;
import com.github.stephengold.joltjni.readonly.ConstVehicleConstraintSettings;
import com.github.stephengold.joltjni.readonly.ConstVehicleControllerSettings;
import com.github.stephengold.joltjni.readonly.ConstWheelSettings;
import com.github.stephengold.joltjni.readonly.Mat44Arg;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.RMat44Arg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.std.StringStream;
import com.github.stephengold.joltjni.streamutils.MaterialToIdMap;
import com.github.stephengold.joltjni.streamutils.SharedSettingsToIdMap;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.junit.Assert;
import testjoltjni.TestUtils;

/**
 * Utility methods to verify that specific objects are equivalent.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final class Equivalent {
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private Equivalent() {
        // do nothing
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Verify the equivalence of the specified axis-aligned bounding boxes to
     * within the specified tolerance, ignoring their types, virtual addresses,
     * and ownership.
     *
     * @param expected the expected bounds (not {@code null}, unaffected)
     * @param actual the actual bounds (not {@code null}, unaffected)
     * @param tolerance the allowable difference for each component (&ge;0)
     */
    static void aaBox(ConstAaBox expected, ConstAaBox actual, float tolerance) {
        joltPhysicsObject(expected, actual);

        boolean isValid = expected.isValid();
        Assert.assertEquals(isValid, actual.isValid());
        if (!isValid) {
            return;
        }

        vec3(expected.getCenter(), actual.getCenter(), tolerance);
        vec3(expected.getExtent(), actual.getExtent(), tolerance);
        vec3(expected.getMax(), actual.getMax(), tolerance);
        vec3(expected.getMin(), actual.getMin(), tolerance);
        vec3(expected.getSize(), actual.getSize(), tolerance);
        Assert.assertEquals(
                expected.getVolume(), actual.getVolume(), tolerance);
    }

    /**
     * Verify the equivalence of the specified anti-roll bars, ignoring their
     * types, virtual addresses, and ownership.
     *
     * @param expected the expected settings (not {@code null}, unaffected)
     * @param actual the actual settings (not {@code null}, unaffected)
     */
    static void antiRollBar(
            ConstVehicleAntiRollBar expected, ConstVehicleAntiRollBar actual) {
        joltPhysicsObject(expected, actual);

        Assert.assertEquals(expected.getLeftWheel(), actual.getLeftWheel());
        Assert.assertEquals(expected.getRightWheel(), actual.getRightWheel());
        Assert.assertEquals(expected.getStiffness(), actual.getStiffness(), 0f);

        // compare serialization results:
        StringStream stream1 = new StringStream();
        StringStream stream2 = new StringStream();
        expected.saveBinaryState(new StreamOutWrapper(stream1));
        actual.saveBinaryState(new StreamOutWrapper(stream2));
        Assert.assertEquals(stream1.str(), stream2.str());
    }

    /**
     * Verify the equivalence of the specified body-creation settings, ignoring
     * their types, virtual addresses, and ownership.
     *
     * @param expected the expected settings (not {@code null}, unaffected)
     * @param actual the actual settings (not {@code null}, unaffected)
     */
    static void bodyCreationSettings(ConstBodyCreationSettings expected,
            ConstBodyCreationSettings actual) {
        joltPhysicsObject(expected, actual);

        Assert.assertEquals(expected.getAllowDynamicOrKinematic(),
                actual.getAllowDynamicOrKinematic());
        Assert.assertEquals(expected.getAllowedDofs(), actual.getAllowedDofs());
        Assert.assertEquals(
                expected.getAllowSleeping(), actual.getAllowSleeping());
        Assert.assertEquals(
                expected.getAngularDamping(), actual.getAngularDamping(), 0f);
        vec3(expected.getAngularVelocity(), actual.getAngularVelocity(), 0f);
        Assert.assertEquals(expected.getApplyGyroscopicForce(),
                actual.getApplyGyroscopicForce());
        Assert.assertEquals(expected.getCollideKinematicVsNonDynamic(),
                actual.getCollideKinematicVsNonDynamic());
        collisionGroup(
                expected.getCollisionGroup(), actual.getCollisionGroup());
        Assert.assertEquals(expected.getEnhancedInternalEdgeRemoval(),
                actual.getEnhancedInternalEdgeRemoval());
        Assert.assertEquals(expected.getFriction(), actual.getFriction(), 0f);
        Assert.assertEquals(
                expected.getGravityFactor(), actual.getGravityFactor(), 0f);
        Assert.assertEquals(expected.getInertiaMultiplier(),
                actual.getInertiaMultiplier(), 0f);
        Assert.assertEquals(expected.getIsSensor(), actual.getIsSensor());
        Assert.assertEquals(
                expected.getLinearDamping(), actual.getLinearDamping(), 0f);
        vec3(expected.getLinearVelocity(), actual.getLinearVelocity(), 0f);

        // Invoking getMassProperties() would cook the settings.
        Assert.assertEquals(expected.getMaxAngularVelocity(),
                actual.getMaxAngularVelocity(), 0f);
        Assert.assertEquals(expected.getMaxLinearVelocity(),
                actual.getMaxLinearVelocity(), 0f);
        Assert.assertEquals(
                expected.getMotionQuality(), actual.getMotionQuality());
        Assert.assertEquals(expected.getMotionType(), actual.getMotionType());
        Assert.assertEquals(expected.getNumPositionStepsOverride(),
                actual.getNumPositionStepsOverride());
        Assert.assertEquals(expected.getNumVelocityStepsOverride(),
                actual.getNumVelocityStepsOverride());
        Assert.assertEquals(expected.getObjectLayer(), actual.getObjectLayer());
        Assert.assertEquals(expected.getOverrideMassProperties(),
                actual.getOverrideMassProperties());
        rVec3(expected.getPosition(), actual.getPosition(), 0f);
        Assert.assertEquals(
                expected.getRestitution(), actual.getRestitution(), 0f);
        quat(expected.getRotation(), actual.getRotation(), 0f);
        // Invoking getShape() would cook the settings.

        ConstShapeSettings shapeSettings = expected.getShapeSettings();
        if (shapeSettings == null) {
            Assert.assertNull(actual.getShapeSettings());
        } else {
            shapeSettings(shapeSettings, actual.getShapeSettings());
        }

        Assert.assertEquals(expected.getUseManifoldReduction(),
                actual.getUseManifoldReduction());
        Assert.assertEquals(expected.getUserData(), actual.getUserData());
        Assert.assertEquals(
                expected.hasMassProperties(), actual.hasMassProperties());

        // compare serialization results:
        StringStream stream1 = new StringStream();
        StringStream stream2 = new StringStream();
        expected.saveBinaryState(new StreamOutWrapper(stream1));
        actual.saveBinaryState(new StreamOutWrapper(stream2));
        Assert.assertEquals(stream1.str(), stream2.str());

        stream1 = new StringStream();
        stream2 = new StringStream();
        expected.saveWithChildren(
                new StreamOutWrapper(stream1), null, null, null);
        actual.saveWithChildren(
                new StreamOutWrapper(stream2), null, null, null);
        Assert.assertEquals(stream1.str(), stream2.str());
    }

    /**
     * Verify the equivalence of the specified collision groups, ignoring their
     * types, virtual addresses, and ownership.
     *
     * @param expected the expected group (not {@code null}, unaffected)
     * @param actual the actual group (not {@code null}, unaffected)
     */
    static void collisionGroup(
            ConstCollisionGroup expected, ConstCollisionGroup actual) {
        joltPhysicsObject(expected, actual);

        ConstGroupFilter filter = expected.getGroupFilter();
        if (filter == null) {
            Assert.assertNull(actual.getGroupFilter());
        } else {
            groupFilter(filter, actual.getGroupFilter());
        }

        Assert.assertEquals(expected.getGroupId(), actual.getGroupId());
        Assert.assertEquals(expected.getSubGroupId(), actual.getSubGroupId());
    }

    /**
     * Verify the equivalence of the specified colors.
     *
     * @param expected the expected value (not {@code null}, unaffected)
     * @param actual the vector to test (not {@code null}, unaffected)
     */
    static void color(ConstColor expected, ConstColor actual) {
        TestUtils.assertEquals(expected.getR(), expected.getG(),
                expected.getB(), expected.getA(), actual);
    }

    /**
     * Verify the equivalence of the specified constraint settings, ignoring
     * their types, virtual addresses, and ownership.
     *
     * @param expected the expected settings (not {@code null}, unaffected)
     * @param actual the actual settings (not {@code null}, unaffected)
     */
    static void constraintSettings(
            ConstConstraintSettings expected, ConstConstraintSettings actual) {
        serializableObject(expected, actual);

        Assert.assertEquals(expected.getConstraintPriority(),
                actual.getConstraintPriority());
        Assert.assertEquals(expected.getControllerType(),
                actual.getControllerType());
        Assert.assertEquals(expected.getDrawConstraintSize(),
                actual.getDrawConstraintSize(), 0f);
        Assert.assertEquals(expected.getEnabled(), actual.getEnabled());
        Assert.assertEquals(expected.getNumPositionStepsOverride(),
                actual.getNumPositionStepsOverride());
        Assert.assertEquals(expected.getNumVelocityStepsOverride(),
                actual.getNumVelocityStepsOverride());

        // compare serialization results:
        StringStream stream1 = new StringStream();
        StringStream stream2 = new StringStream();
        expected.saveBinaryState(new StreamOutWrapper(stream1));
        actual.saveBinaryState(new StreamOutWrapper(stream2));
        Assert.assertEquals(stream1.str(), stream2.str());
    }

    /**
     * Verify the equivalence of the specified float buffers.
     *
     * @param expected the expected buffer (not {@code null}, unaffected)
     * @param actual the actual buffer (not {@code null}, unaffected)
     */
    static void floatBuffer(FloatBuffer expected, FloatBuffer actual) {
        int numFloats = expected.capacity();
        Assert.assertEquals(numFloats, actual.capacity());
        for (int i = 0; i < numFloats; ++i) {
            Assert.assertEquals(expected.get(i), actual.get(i), 0f);
        }

        Assert.assertEquals(expected.hasArray(), actual.hasArray());
        Assert.assertEquals(expected.isDirect(), actual.isDirect());
        Assert.assertEquals(expected.limit(), actual.limit());
        Assert.assertEquals(expected.mark(), actual.mark());
        Assert.assertEquals(expected.order(), actual.order());
        Assert.assertEquals(expected.position(), actual.position());
    }

    /**
     * Verify the equivalence of the specified group filters, ignoring their
     * types, virtual addresses, and ownership.
     *
     * @param expected the expected filter (not {@code null}, unaffected)
     * @param actual the actual filter (not {@code null}, unaffected)
     */
    static void groupFilter(
            ConstGroupFilter expected, ConstGroupFilter actual) {
        serializableObject(expected, actual);

        ConstCollisionGroup g0 = new CollisionGroup();
        Assert.assertEquals(
                expected.canCollide(g0, g0), actual.canCollide(g0, g0));

        // compare serialization results:
        StringStream stream1 = new StringStream();
        StringStream stream2 = new StringStream();
        expected.saveBinaryState(new StreamOutWrapper(stream1));
        actual.saveBinaryState(new StreamOutWrapper(stream2));
        Assert.assertEquals(stream1.str(), stream2.str());
    }

    /**
     * Verify the equivalence of the specified int buffers.
     *
     * @param expected the expected buffer (not {@code null}, unaffected)
     * @param actual the actual buffer (not {@code null}, unaffected)
     */
    static void intBuffer(IntBuffer expected, IntBuffer actual) {
        int numInts = expected.capacity();
        Assert.assertEquals(numInts, actual.capacity());
        for (int i = 0; i < numInts; ++i) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }

        Assert.assertEquals(expected.hasArray(), actual.hasArray());
        Assert.assertEquals(expected.isDirect(), actual.isDirect());
        Assert.assertEquals(expected.limit(), actual.limit());
        Assert.assertEquals(expected.mark(), actual.mark());
        Assert.assertEquals(expected.order(), actual.order());
        Assert.assertEquals(expected.position(), actual.position());
    }

    /**
     * Verify the equivalence of the specified physics objects, ignoring their
     * types, virtual addresses, and ownership.
     *
     * @param expected the expected object (not {@code null}, unaffected)
     * @param actual the actual object (not {@code null}, unaffected)
     */
    static void joltPhysicsObject(
            ConstJoltPhysicsObject expected, ConstJoltPhysicsObject actual) {
        Assert.assertNotNull(expected);
        Assert.assertNotNull(actual);

        Assert.assertEquals(expected.hasAssignedNativeObject(),
                actual.hasAssignedNativeObject());
    }

    /**
     * Verify the equivalence of the specified mass properties, ignoring their
     * types, virtual addresses, and ownership.
     *
     * @param expected the expected properties (not {@code null}, unaffected)
     * @param actual the actual properties (not {@code null}, unaffected)
     */
    static void massProperties(ConstMassProperties expected,
            ConstMassProperties actual) {
        joltPhysicsObject(expected, actual);

        Assert.assertEquals(expected.getMass(), actual.getMass(), 0f);
        mat44(expected.getInertia(), actual.getInertia(), 0f);
    }

    /**
     * Verify the equivalence of the specified matrices to within some
     * tolerance, ignoring their virtual addresses and ownership.
     *
     * @param expected the expected value (not {@code null}, unaffected)
     * @param actual the vector to test (not {@code null}, unaffected)
     * @param tolerance the allowable difference for each component (&ge;0)
     */
    static void mat44(Mat44Arg expected, Mat44Arg actual, float tolerance) {
        joltPhysicsObject(expected, actual);

        vec3(expected.getAxisX(), actual.getAxisX(), tolerance);
        vec3(expected.getAxisY(), actual.getAxisY(), tolerance);
        vec3(expected.getAxisZ(), actual.getAxisZ(), tolerance);
        vec3(expected.getTranslation(), actual.getTranslation(), tolerance);
    }

    /**
     * Verify the equivalence of the specified motor settings, ignoring their
     * types, virtual addresses, and ownership.
     *
     * @param expected the expected motor settings (not {@code null},
     * unaffected)
     * @param actual the actual motor settings (not {@code null}, unaffected)
     */
    static void motorSettings(MotorSettings expected, MotorSettings actual) {
        joltPhysicsObject(expected, actual);

        Assert.assertEquals(
                expected.getMaxForceLimit(), actual.getMaxForceLimit(), 0f);
        Assert.assertEquals(
                expected.getMaxTorqueLimit(), actual.getMaxTorqueLimit(), 0f);
        Assert.assertEquals(
                expected.getMinForceLimit(), actual.getMinForceLimit(), 0f);
        Assert.assertEquals(
                expected.getMinTorqueLimit(), actual.getMinTorqueLimit(), 0f);
        springSettings(
                expected.getSpringSettings(), actual.getSpringSettings());
        Assert.assertEquals(expected.isValid(), actual.isValid());
    }

    /**
     * Verify the equivalence of the specified physics materials, ignoring their
     * types, virtual addresses, and ownership.
     *
     * @param expected the expected material (not {@code null}, unaffected)
     * @param actual the actual material (not {@code null}, unaffected)
     */
    static void physicsMaterial(
            PhysicsMaterial expected, PhysicsMaterial actual) {
        serializableObject(expected, actual);

        color(expected.getDebugColor(), actual.getDebugColor());
        Assert.assertEquals(expected.getDebugName(), actual.getDebugName());
    }

    /**
     * Verify the equivalence of the specified point-constraint settings,
     * ignoring their types, virtual addresses, and ownership.
     *
     * @param expected the expected settings (not {@code null}, unaffected)
     * @param actual the actual settings (not {@code null}, unaffected)
     */
    static void pointConstraintSettings(
            PointConstraintSettings expected,
            PointConstraintSettings actual) {
        constraintSettings(expected, actual);

        rVec3(expected.getPoint1(), actual.getPoint1(), 0f);
        rVec3(expected.getPoint2(), actual.getPoint2(), 0f);
        Assert.assertEquals(expected.getSpace(), actual.getSpace());
    }

    /**
     * Verify the equivalence of the specified quaternions to within the
     * specified tolerance.
     *
     * @param expected the expected value (not {@code null}, unaffected)
     * @param actual the vector to test (not {@code null}, unaffected)
     * @param tolerance the allowable difference for each component (&ge;0)
     */
    static void quat(QuatArg expected, QuatArg actual, float tolerance) {
        TestUtils.assertEquals(expected.getX(), expected.getY(),
                expected.getZ(), expected.getW(), actual, tolerance);
    }

    /**
     * Verify the equivalence of the specified rack-and-pinion constraint
     * settings, ignoring their types, virtual addresses, and ownership.
     *
     * @param expected the expected settings (not {@code null}, unaffected)
     * @param actual the actual settings (not {@code null}, unaffected)
     */
    static void rackAndPinionConstraintSettings(
            RackAndPinionConstraintSettings expected,
            RackAndPinionConstraintSettings actual) {
        constraintSettings(expected, actual);

        vec3(expected.getHingeAxis(), actual.getHingeAxis(), 0f);
        Assert.assertEquals(expected.getRatio(), actual.getRatio(), 0f);
        vec3(expected.getSliderAxis(), actual.getSliderAxis(), 0f);
        Assert.assertEquals(expected.getSpace(), actual.getSpace());
    }

    /**
     * Verify the equivalence of the specified location vectors to within the
     * specified tolerance.
     *
     * @param expected the expected value (not {@code null}, unaffected)
     * @param actual the vector to test (not {@code null}, unaffected)
     * @param tolerance the allowable difference for each component (&ge;0)
     */
    static void rVec3(RVec3Arg expected, RVec3Arg actual, float tolerance) {
        TestUtils.assertEquals(
                expected.x(), expected.y(), expected.z(), actual, tolerance);
    }

    /**
     * Verify the equivalence of the specified serializable objects, ignoring
     * their virtual addresses and ownership.
     *
     * @param expected the expected serializable object (not {@code null},
     * unaffected)
     * @param actual the actual serializable object (not {@code null},
     * unaffected)
     */
    static void serializableObject(ConstSerializableObject expected,
            ConstSerializableObject actual) {
        joltPhysicsObject(expected, actual);
        Assert.assertEquals(expected.getRtti().getName(),
                actual.getRtti().getName());

        // compare serialization results:
        StringStream stream1 = new StringStream();
        StringStream stream2 = new StringStream();
        ObjectStreamOut.sWriteObject(stream1, EStreamType.Text, expected);
        ObjectStreamOut.sWriteObject(stream2, EStreamType.Text, actual);
        Assert.assertEquals(stream1.str(), stream2.str());
    }

    /**
     * Verify the equivalence of the specified shapes, ignoring their virtual
     * addresses and ownership.
     *
     * @param expected the expected shape (not {@code null}, unaffected)
     * @param actual the actual shape (not {@code null}, unaffected)
     */
    static void shape(ConstShape expected, ConstShape actual) {
        joltPhysicsObject(expected, actual);

        // compare the debug shapes:
        int numTriangles = expected.countDebugTriangles();
        Assert.assertEquals(numTriangles, actual.countDebugTriangles());
        int numFloats = 3 * numTriangles;
        FloatBuffer buffer1 = Jolt.newDirectFloatBuffer(numFloats);
        FloatBuffer buffer2 = Jolt.newDirectFloatBuffer(numFloats);
        expected.copyDebugTriangles(buffer1);
        actual.copyDebugTriangles(buffer2);
        Assert.assertEquals(buffer1, buffer2);

        vec3(expected.getCenterOfMass(), actual.getCenterOfMass(), 0f);
        Assert.assertEquals(
                expected.getInnerRadius(), actual.getInnerRadius(), 0f);
        aaBox(expected.getLocalBounds(), actual.getLocalBounds(), 0f);
        massProperties(
                expected.getMassProperties(), actual.getMassProperties());
        Assert.assertEquals(
                expected.getRevisionCount(), actual.getRevisionCount(), 0f);
        //assertEquals(expected.getStats(), actual.getStats());
        Assert.assertEquals(expected.getSubShapeIdBitsRecursive(),
                actual.getSubShapeIdBitsRecursive());
        Assert.assertEquals(expected.getSubType(), actual.getSubType());
        Assert.assertEquals(expected.getType(), actual.getType());

        RMat44Arg rmi = new RMat44();
        Vec3Arg scaleI = new Vec3(1f, 1f, 1f);
        aaBox(expected.getWorldSpaceBounds(rmi, scaleI),
                actual.getWorldSpaceBounds(rmi, scaleI), 0f);

        Assert.assertEquals(expected.mustBeStatic(), actual.mustBeStatic());

        // compare serialization results:
        StringStream stream1 = new StringStream();
        StringStream stream2 = new StringStream();
        expected.saveBinaryState(new StreamOutWrapper(stream1));
        actual.saveBinaryState(new StreamOutWrapper(stream2));
        Assert.assertEquals(stream1.str(), stream2.str());
    }

    /**
     * Verify the equivalence of the specified shape settings, ignoring their
     * types, virtual addresses, and ownership.
     *
     * @param expected the expected shape (not {@code null}, unaffected)
     * @param actual the actual shape (not {@code null}, unaffected)
     */
    static void shapeSettings(
            ConstShapeSettings expected, ConstShapeSettings actual) {
        serializableObject(expected, actual);
    }

    /**
     * Verify the equivalence of the specified soft-body creation settings,
     * ignoring their types, virtual addresses, and ownership.
     *
     * @param expected the expected settings (not {@code null}, unaffected)
     * @param actual the actual settings (not {@code null}, unaffected)
     */
    static void softBodyCreationSettings(
            ConstSoftBodyCreationSettings expected,
            ConstSoftBodyCreationSettings actual) {
        joltPhysicsObject(expected, actual);

        Assert.assertEquals(
                expected.getAllowSleeping(), actual.getAllowSleeping());
        collisionGroup(
                expected.getCollisionGroup(), actual.getCollisionGroup());
        Assert.assertEquals(expected.getFriction(), actual.getFriction(), 0f);
        Assert.assertEquals(
                expected.getGravityFactor(), actual.getGravityFactor(), 0f);

        Assert.assertEquals(expected.getLinearDamping(),
                actual.getLinearDamping(), 0f);
        Assert.assertEquals(expected.getMakeRotationIdentity(),
                actual.getMakeRotationIdentity());
        Assert.assertEquals(expected.getMaxLinearVelocity(),
                actual.getMaxLinearVelocity(), 0f);
        Assert.assertEquals(
                expected.getNumIterations(), actual.getNumIterations());
        Assert.assertEquals(expected.getObjectLayer(), actual.getObjectLayer());
        rVec3(expected.getPosition(), actual.getPosition(), 0f);
        Assert.assertEquals(
                expected.getPressure(), actual.getPressure(), 0f);
        Assert.assertEquals(
                expected.getRestitution(), actual.getRestitution(), 0f);
        quat(expected.getRotation(), actual.getRotation(), 0f);

        ConstSoftBodySharedSettings sbss = expected.getSettings();
        if (sbss == null) {
            Assert.assertNull(actual.getSettings());
        } else {
            softBodySharedSettings(sbss, actual.getSettings());
        }

        Assert.assertEquals(
                expected.getUpdatePosition(), actual.getUpdatePosition());
        Assert.assertEquals(expected.getUserData(), actual.getUserData());
        Assert.assertEquals(
                expected.getVertexRadius(), actual.getVertexRadius(), 0f);

        // compare serialization results:
        StringStream stream1 = new StringStream();
        StringStream stream2 = new StringStream();
        expected.saveBinaryState(new StreamOutWrapper(stream1));
        actual.saveBinaryState(new StreamOutWrapper(stream2));
        Assert.assertEquals(stream1.str(), stream2.str());

        stream1 = new StringStream();
        stream2 = new StringStream();
        expected.saveWithChildren(
                new StreamOutWrapper(stream1), null, null, null);
        actual.saveWithChildren(
                new StreamOutWrapper(stream2), null, null, null);
        Assert.assertEquals(stream1.str(), stream2.str());
    }

    /**
     * Verify the equivalence of the specified soft-body shared settings,
     * ignoring their types, virtual addresses, and ownership.
     *
     * @param expected the expected settings (not {@code null}, unaffected)
     * @param actual the actual settings (not {@code null}, unaffected)
     */
    static void softBodySharedSettings(ConstSoftBodySharedSettings expected,
            ConstSoftBodySharedSettings actual) {
        joltPhysicsObject(expected, actual);

        int numEdges = expected.countEdgeConstraints();
        Assert.assertEquals(numEdges, actual.countEdgeConstraints());
        int numFaces = expected.countFaces();
        Assert.assertEquals(numFaces, actual.countFaces());
        Assert.assertEquals(
                expected.countPinnedVertices(), actual.countPinnedVertices());
        Assert.assertEquals(expected.countVertices(), actual.countVertices());
        Assert.assertEquals(expected.countVolumeConstraints(),
                actual.countVolumeConstraints());

        IntBuffer buffer1 = Jolt.newDirectIntBuffer(2 * numEdges);
        IntBuffer buffer2 = Jolt.newDirectIntBuffer(2 * numEdges);
        expected.putEdgeIndices(buffer1);
        actual.putEdgeIndices(buffer2);
        intBuffer(buffer1, buffer2);

        IntBuffer buffer3 = Jolt.newDirectIntBuffer(3 * numFaces);
        IntBuffer buffer4 = Jolt.newDirectIntBuffer(3 * numFaces);
        expected.putFaceIndices(buffer3);
        actual.putFaceIndices(buffer4);
        intBuffer(buffer3, buffer4);

        // compare serialization results:
        StringStream stream1 = new StringStream();
        StringStream stream2 = new StringStream();
        expected.saveBinaryState(new StreamOutWrapper(stream1));
        actual.saveBinaryState(new StreamOutWrapper(stream2));
        Assert.assertEquals(stream1.str(), stream2.str());

        stream1 = new StringStream();
        stream2 = new StringStream();
        expected.saveWithMaterials(new StreamOutWrapper(stream1),
                new SharedSettingsToIdMap(), new MaterialToIdMap());
        actual.saveWithMaterials(new StreamOutWrapper(stream2),
                new SharedSettingsToIdMap(), new MaterialToIdMap());
        Assert.assertEquals(stream1.str(), stream2.str());
    }

    /**
     * Verify the equivalence of the specified spring settings, ignoring their
     * types, virtual addresses, and ownership.
     *
     * @param expected the expected settings (not {@code null}, unaffected)
     * @param actual the actual settings (not {@code null}, unaffected)
     */
    static void springSettings(
            ConstSpringSettings expected, ConstSpringSettings actual) {
        joltPhysicsObject(expected, actual);

        Assert.assertEquals(expected.getDamping(), actual.getDamping(), 0f);
        Assert.assertEquals(expected.getFrequency(), actual.getFrequency(), 0f);
        Assert.assertEquals(expected.getMode(), actual.getMode());
        Assert.assertEquals(expected.getStiffness(), actual.getStiffness(), 0f);
        Assert.assertEquals(expected.hasStiffness(), actual.hasStiffness());

        // compare serialization results:
        StringStream stream1 = new StringStream();
        StringStream stream2 = new StringStream();
        expected.saveBinaryState(new StreamOutWrapper(stream1));
        actual.saveBinaryState(new StreamOutWrapper(stream2));
        Assert.assertEquals(stream1.str(), stream2.str());
    }

    /**
     * Verify the equivalence of the specified swing-twist constraint settings,
     * ignoring their types, virtual addresses, and ownership.
     *
     * @param expected the expected settings (not {@code null}, unaffected)
     * @param actual the actual settings (not {@code null}, unaffected)
     */
    static void swingTwistConstraintSettings(
            SwingTwistConstraintSettings expected,
            SwingTwistConstraintSettings actual) {
        constraintSettings(expected, actual);

        Assert.assertEquals(expected.getMaxFrictionTorque(),
                actual.getMaxFrictionTorque(), 0f);
        Assert.assertEquals(expected.getNormalHalfConeAngle(),
                actual.getNormalHalfConeAngle(), 0f);
        vec3(expected.getPlaneAxis1(), actual.getPlaneAxis1(), 0f);
        vec3(expected.getPlaneAxis2(), actual.getPlaneAxis2(), 0f);
        Assert.assertEquals(expected.getPlaneHalfConeAngle(),
                actual.getPlaneHalfConeAngle(), 0f);
        rVec3(expected.getPosition1(), actual.getPosition1(), 0f);
        rVec3(expected.getPosition2(), actual.getPosition2(), 0f);
        Assert.assertEquals(expected.getSpace(), actual.getSpace());
        motorSettings(expected.getSwingMotorSettings(),
                actual.getSwingMotorSettings());
        Assert.assertEquals(expected.getSwingType(), actual.getSwingType());
        vec3(expected.getTwistAxis1(), actual.getTwistAxis1(), 0f);
        vec3(expected.getTwistAxis2(), actual.getTwistAxis2(), 0f);
        Assert.assertEquals(expected.getTwistMaxAngle(),
                actual.getTwistMaxAngle(), 0f);
        Assert.assertEquals(expected.getTwistMinAngle(),
                actual.getTwistMinAngle(), 0f);
        motorSettings(expected.getTwistMotorSettings(),
                actual.getTwistMotorSettings());
    }

    /**
     * Verify the equivalence of the specified tracked-vehicle controller
     * settings, ignoring their types, virtual addresses, and ownership.
     *
     * @param expected the expected settings (not {@code null}, unaffected)
     * @param actual the actual settings (not {@code null}, unaffected)
     */
    static void trackedVehicleControllerSettings(
            TrackedVehicleControllerSettings expected,
            TrackedVehicleControllerSettings actual) {
        vehicleControllerSettings(expected, actual);

        int numTracks = expected.getNumTracks();
        Assert.assertEquals(numTracks, actual.getNumTracks());
        for (int trackIndex = 0; trackIndex < numTracks; ++trackIndex) {
            vehicleTrackSettings(
                    expected.getTrack(trackIndex), actual.getTrack(trackIndex));
        }
    }

    /**
     * Verify the equivalence of the specified single-precision vectors to
     * within the specified tolerance.
     *
     * @param expected the expected value (not {@code null}, unaffected)
     * @param actual the vector to test (not {@code null}, unaffected)
     * @param tolerance the allowable difference for each component (&ge;0)
     */
    static void vec3(Vec3Arg expected, Vec3Arg actual, float tolerance) {
        TestUtils.assertEquals(expected.getX(), expected.getY(),
                expected.getZ(), actual, tolerance);
    }

    /**
     * Verify the equivalence of the specified vehicle-constraint settings,
     * ignoring their types, virtual addresses, and ownership.
     *
     * @param expected the expected settings (not {@code null}, unaffected)
     * @param actual the actual settings (not {@code null}, unaffected)
     */
    static void vehicleConstraintSettings(
            ConstVehicleConstraintSettings expected,
            ConstVehicleConstraintSettings actual) {
        constraintSettings(expected, actual);

        int numBars = expected.getNumAntiRollBars();
        Assert.assertEquals(numBars, actual.getNumAntiRollBars());
        int numWheels = expected.getNumWheels();
        Assert.assertEquals(numWheels, actual.getNumWheels());

        for (int i = 0; i < numBars; ++i) {
            antiRollBar(
                    expected.getAntiRollBar(i), actual.getAntiRollBar(i));
        }
        Assert.assertEquals(
                expected.getControllerType(), actual.getControllerType());
        vec3(expected.getForward(), actual.getForward(), 0f);
        Assert.assertEquals(expected.getMaxPitchRollAngle(),
                actual.getMaxPitchRollAngle(), 0f);
        vec3(expected.getUp(), actual.getUp(), 0f);

        ConstVehicleControllerSettings controller = expected.getController();
        if (controller == null) {
            Assert.assertNull(actual.getController());
        } else {
            vehicleControllerSettings(controller, actual.getController());
        }

        for (int i = 0; i < numWheels; ++i) {
            wheelSettings(expected.getWheel(i), actual.getWheel(i));
        }
    }

    /**
     * Verify the equivalence of the specified vehicle-controller settings,
     * ignoring their types, virtual addresses, and ownership.
     *
     * @param expected the expected settings (not {@code null}, unaffected)
     * @param actual the actual settings (not {@code null}, unaffected)
     */
    static void vehicleControllerSettings(
            ConstVehicleControllerSettings expected,
            ConstVehicleControllerSettings actual) {
        serializableObject(expected, actual);

        // compare serialization results:
        StringStream stream1 = new StringStream();
        StringStream stream2 = new StringStream();
        expected.saveBinaryState(new StreamOutWrapper(stream1));
        actual.saveBinaryState(new StreamOutWrapper(stream2));
        Assert.assertEquals(stream1.str(), stream2.str());
    }

    /**
     * Verify the equivalence of the specified track settings, ignoring their
     * types, virtual addresses, and ownership.
     *
     * @param expected the expected settings (not {@code null}, unaffected)
     * @param actual the actual settings (not {@code null}, unaffected)
     */
    static void vehicleTrackSettings(
            VehicleTrackSettings expected, VehicleTrackSettings actual) {
        joltPhysicsObject(expected, actual);

        Assert.assertEquals(
                expected.getAngularDamping(), expected.getAngularDamping(), 0f);
        Assert.assertEquals(expected.getDifferentialRatio(),
                expected.getDifferentialRatio(), 0f);
        Assert.assertEquals(
                expected.getDrivenWheel(), expected.getDrivenWheel());
        Assert.assertEquals(expected.getInertia(), expected.getInertia(), 0f);
        Assert.assertEquals(expected.getNumWheels(), expected.getNumWheels());
    }

    /**
     * Verify the equivalence of the specified wheel settings, ignoring their
     * types, virtual addresses, and ownership.
     *
     * @param expected the expected settings (not {@code null}, unaffected)
     * @param actual the actual settings (not {@code null}, unaffected)
     */
    static void wheelSettings(
            ConstWheelSettings expected, ConstWheelSettings actual) {
        serializableObject(expected, actual);

        Assert.assertEquals(expected.getEnableSuspensionForcePoint(),
                actual.getEnableSuspensionForcePoint());
        vec3(expected.getPosition(), actual.getPosition(), 0f);
        Assert.assertEquals(expected.getRadius(), actual.getRadius(), 0f);
        vec3(expected.getSteeringAxis(), actual.getSteeringAxis(), 0f);
        vec3(expected.getSuspensionDirection(),
                actual.getSuspensionDirection(), 0f);
        vec3(expected.getSuspensionForcePoint(),
                actual.getSuspensionForcePoint(), 0f);
        Assert.assertEquals(expected.getSuspensionMaxLength(),
                actual.getSuspensionMaxLength(), 0f);
        Assert.assertEquals(expected.getSuspensionMinLength(),
                actual.getSuspensionMinLength(), 0f);
        Assert.assertEquals(expected.getSuspensionPreloadLength(),
                actual.getSuspensionPreloadLength(), 0f);
        springSettings(expected.getSuspensionSpring(),
                actual.getSuspensionSpring());
        vec3(expected.getWheelForward(), actual.getWheelForward(), 0f);
        vec3(expected.getWheelUp(), actual.getWheelUp(), 0f);
        Assert.assertEquals(expected.getWidth(), actual.getWidth(), 0f);

        // compare serialization results:
        StringStream stream1 = new StringStream();
        StringStream stream2 = new StringStream();
        expected.saveBinaryState(new StreamOutWrapper(stream1));
        actual.saveBinaryState(new StreamOutWrapper(stream2));
        Assert.assertEquals(stream1.str(), stream2.str());
    }
}
