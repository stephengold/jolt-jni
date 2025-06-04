/*
Copyright (c) 2024-2025 Stephen Gold

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
package testjoltjni;

import com.github.stephengold.joltjni.BroadPhaseLayerInterface;
import com.github.stephengold.joltjni.BroadPhaseLayerInterfaceTable;
import com.github.stephengold.joltjni.CollisionGroup;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.JoltPhysicsObject;
import com.github.stephengold.joltjni.MassProperties;
import com.github.stephengold.joltjni.ObjectLayerPairFilter;
import com.github.stephengold.joltjni.ObjectLayerPairFilterTable;
import com.github.stephengold.joltjni.ObjectVsBroadPhaseLayerFilter;
import com.github.stephengold.joltjni.ObjectVsBroadPhaseLayerFilterTable;
import com.github.stephengold.joltjni.PhysicsSystem;
import com.github.stephengold.joltjni.RMat44;
import com.github.stephengold.joltjni.StreamOutWrapper;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.readonly.ConstAaBox;
import com.github.stephengold.joltjni.readonly.ConstBodyCreationSettings;
import com.github.stephengold.joltjni.readonly.ConstBroadPhaseLayerInterface;
import com.github.stephengold.joltjni.readonly.ConstCollisionGroup;
import com.github.stephengold.joltjni.readonly.ConstColor;
import com.github.stephengold.joltjni.readonly.ConstConstraintSettings;
import com.github.stephengold.joltjni.readonly.ConstGroupFilter;
import com.github.stephengold.joltjni.readonly.ConstJoltPhysicsObject;
import com.github.stephengold.joltjni.readonly.ConstMassProperties;
import com.github.stephengold.joltjni.readonly.ConstObjectLayerPairFilter;
import com.github.stephengold.joltjni.readonly.ConstObjectVsBroadPhaseLayerFilter;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.ConstShapeSettings;
import com.github.stephengold.joltjni.readonly.ConstSpringSettings;
import com.github.stephengold.joltjni.readonly.ConstVehicleAntiRollBar;
import com.github.stephengold.joltjni.readonly.ConstVehicleConstraintSettings;
import com.github.stephengold.joltjni.readonly.ConstVehicleControllerSettings;
import com.github.stephengold.joltjni.readonly.ConstWheelSettings;
import com.github.stephengold.joltjni.readonly.Mat44Arg;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.RMat44Arg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.UVec4Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.std.StringStream;
import electrostatic4j.snaploader.platform.util.NativeVariant;
import java.io.File;
import java.io.PrintStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Assert;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

/**
 * Utility methods for automated testing of Jolt JNI.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class TestUtils {
    // *************************************************************************
    // constants

    /**
     * false to explicitly free native objects via {@code testClose()}, true to
     * rely on the automatic {@code java.lang.ref.Cleaner} instead
     */
    final public static boolean automateFreeing = true;
    /**
     * customary number of object layers
     */
    final public static int numObjLayers = 2;
    /**
     * customary object layer for moving objects
     */
    final public static int objLayerMoving = 1;
    /**
     * customary object layer for non-moving objects
     */
    final public static int objLayerNonMoving = 0;
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private TestUtils() {
        // do nothing
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Verify the equivalence of the specified axis-aligned bounding boxes to
     * within some tolerance, ignoring their types, virtual addresses, and
     * ownership.
     *
     * @param expected the expected bounds (not {@code null}, unaffected)
     * @param actual the actual bounds (not {@code null}, unaffected)
     * @param tolerance the allowable difference for each component (&ge;0)
     */
    public static void assertAaBox(
            ConstAaBox expected, ConstAaBox actual, float tolerance) {
        assertJpo(expected, actual);

        boolean isValid = expected.isValid();
        Assert.assertEquals(isValid, actual.isValid());
        if (!isValid) {
            return;
        }

        assertEquals(expected.getCenter(), actual.getCenter(), tolerance);
        assertEquals(expected.getExtent(), actual.getExtent(), tolerance);
        assertEquals(expected.getMax(), actual.getMax(), tolerance);
        assertEquals(expected.getMin(), actual.getMin(), tolerance);
        assertEquals(expected.getSize(), actual.getSize(), tolerance);
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
    public static void assertAntiRollBar(
            ConstVehicleAntiRollBar expected, ConstVehicleAntiRollBar actual) {
        assertJpo(expected, actual);

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
    public static void assertBcs(ConstBodyCreationSettings expected,
            ConstBodyCreationSettings actual) {
        assertJpo(expected, actual);

        Assert.assertEquals(expected.getAllowDynamicOrKinematic(),
                actual.getAllowDynamicOrKinematic());
        Assert.assertEquals(expected.getAllowedDofs(), actual.getAllowedDofs());
        Assert.assertEquals(
                expected.getAllowSleeping(), actual.getAllowSleeping());
        Assert.assertEquals(
                expected.getAngularDamping(), actual.getAngularDamping(), 0f);
        assertEquals(
                expected.getAngularVelocity(), actual.getAngularVelocity(), 0f);
        Assert.assertEquals(expected.getApplyGyroscopicForce(),
                actual.getApplyGyroscopicForce());
        assertCollisionGroup(
                expected.getCollisionGroup(), actual.getCollisionGroup());
        Assert.assertEquals(expected.getEnhancedInternalEdgeRemoval(),
                actual.getEnhancedInternalEdgeRemoval());
        Assert.assertEquals(expected.getFriction(), actual.getFriction(), 0f);
        Assert.assertEquals(
                expected.getGravityFactor(), actual.getGravityFactor(), 0f);
        Assert.assertEquals(expected.getIsSensor(), actual.getIsSensor());
        Assert.assertEquals(
                expected.getLinearDamping(), actual.getLinearDamping(), 0f);
        assertEquals(
                expected.getLinearVelocity(), actual.getLinearVelocity(), 0f);

        MassProperties expectedMassProperties = expected.getMassProperties();
        if (expectedMassProperties == null) {
            Assert.assertNull(actual.getMassProperties());
        } else {
            assertMassProperties(
                    expectedMassProperties, actual.getMassProperties());
        }
        Assert.assertEquals(expected.getMaxAngularVelocity(),
                actual.getMaxAngularVelocity(), 0f);
        Assert.assertEquals(expected.getMaxLinearVelocity(),
                actual.getMaxLinearVelocity(), 0f);
        Assert.assertEquals(
                expected.getMotionQuality(), actual.getMotionQuality());
        Assert.assertEquals(expected.getMotionType(), actual.getMotionType());
        Assert.assertEquals(expected.getObjectLayer(), actual.getObjectLayer());
        Assert.assertEquals(expected.getOverrideMassProperties(),
                actual.getOverrideMassProperties());
        assertEquals(expected.getPosition(), actual.getPosition(), 0f);
        Assert.assertEquals(
                expected.getRestitution(), actual.getRestitution(), 0f);
        assertEquals(expected.getRotation(), actual.getRotation(), 0f);

        ConstShape expectedShape = expected.getShape();
        if (expectedShape == null) {
            assertShapeSettings(
                    expected.getShapeSettings(), actual.getShapeSettings());
        } else {
            assertShape(expectedShape, actual.getShape());
        }

        Assert.assertEquals(
                expected.hasMassProperties(), actual.hasMassProperties());
    }

    /**
     * Verify the equivalence of the specified collision groups, ignoring their
     * types, virtual addresses, and ownership.
     *
     * @param expected the expected group (not {@code null}, unaffected)
     * @param actual the actual group (not {@code null}, unaffected)
     */
    public static void assertCollisionGroup(
            ConstCollisionGroup expected, ConstCollisionGroup actual) {
        assertJpo(expected, actual);

        ConstGroupFilter expectedFilter = expected.getGroupFilter();
        if (expectedFilter == null) {
            Assert.assertNull(actual.getGroupFilter());
        } else {
            assertGroupFilter(expectedFilter, actual.getGroupFilter());
        }

        Assert.assertEquals(expected.getGroupId(), actual.getGroupId());
        Assert.assertEquals(expected.getSubGroupId(), actual.getSubGroupId());
    }

    /**
     * Verify the equivalence of the specified constraint settings, ignoring
     * their types, virtual addresses, and ownership.
     *
     * @param expected the expected settings (not {@code null}, unaffected)
     * @param actual the actual settings (not {@code null}, unaffected)
     */
    public static void assertConstraintSettings(
            ConstConstraintSettings expected, ConstConstraintSettings actual) {
        assertJpo(expected, actual);

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
     * Verify the equivalence of the specified colors.
     *
     * @param expected the expected value (not {@code null}, unaffected)
     * @param actual the vector to test (not {@code null}, unaffected)
     */
    public static void assertEquals(
            ConstColor expected, ConstColor actual) {
        assertEquals(expected.getR(), expected.getG(), expected.getB(),
                expected.getA(), actual);
    }

    /**
     * Verify the elements of a single-precision matrix to within some
     * tolerance.
     *
     * @param e00 the expected element in row 0 of column 0
     * @param e01 the expected element in row 0 of column 1
     * @param e02 the expected element in row 0 of column 2
     * @param e03 the expected element in row 0 of column 3
     * @param e10 the expected element in row 1 of column 0
     * @param e11 the expected element in row 1 of column 1
     * @param e12 the expected element in row 1 of column 2
     * @param e13 the expected element in row 1 of column 3
     * @param e20 the expected element in row 2 of column 0
     * @param e21 the expected element in row 2 of column 1
     * @param e22 the expected element in row 2 of column 2
     * @param e23 the expected element in row 2 of column 3
     * @param e30 the expected element in row 3 of column 0
     * @param e31 the expected element in row 3 of column 1
     * @param e32 the expected element in row 3 of column 2
     * @param e33 the expected element in row 3 of column 3
     * @param actual the matrix to test (not {@code null}, unaffected)
     * @param tolerance the allowable difference for each element (&ge;0)
     */
    public static void assertEquals(float e00, float e01, float e02, float e03,
            float e10, float e11, float e12, float e13,
            float e20, float e21, float e22, float e23,
            float e30, float e31, float e32, float e33,
            Mat44Arg actual, float tolerance) {
        Assert.assertEquals("e00", e00, actual.getElement(0, 0), tolerance);
        Assert.assertEquals("e01", e01, actual.getElement(0, 1), tolerance);
        Assert.assertEquals("e02", e02, actual.getElement(0, 2), tolerance);
        Assert.assertEquals("e03", e03, actual.getElement(0, 3), tolerance);
        Assert.assertEquals("e10", e10, actual.getElement(1, 0), tolerance);
        Assert.assertEquals("e11", e11, actual.getElement(1, 1), tolerance);
        Assert.assertEquals("e12", e12, actual.getElement(1, 2), tolerance);
        Assert.assertEquals("e13", e13, actual.getElement(1, 3), tolerance);
        Assert.assertEquals("e20", e20, actual.getElement(2, 0), tolerance);
        Assert.assertEquals("e21", e21, actual.getElement(2, 1), tolerance);
        Assert.assertEquals("e22", e22, actual.getElement(2, 2), tolerance);
        Assert.assertEquals("e23", e23, actual.getElement(2, 3), tolerance);
        Assert.assertEquals("e30", e30, actual.getElement(3, 0), tolerance);
        Assert.assertEquals("e31", e31, actual.getElement(3, 1), tolerance);
        Assert.assertEquals("e32", e32, actual.getElement(3, 2), tolerance);
        Assert.assertEquals("e33", e33, actual.getElement(3, 3), tolerance);
    }

    /**
     * Verify the elements of a location-precision matrix to within some
     * tolerance.
     *
     * @param e00 the expected element in row 0 of column 0
     * @param e01 the expected element in row 0 of column 1
     * @param e02 the expected element in row 0 of column 2
     * @param e03 the expected element in row 0 of column 3
     * @param e10 the expected element in row 1 of column 0
     * @param e11 the expected element in row 1 of column 1
     * @param e12 the expected element in row 1 of column 2
     * @param e13 the expected element in row 1 of column 3
     * @param e20 the expected element in row 2 of column 0
     * @param e21 the expected element in row 2 of column 1
     * @param e22 the expected element in row 2 of column 2
     * @param e23 the expected element in row 2 of column 3
     * @param e30 the expected element in row 3 of column 0
     * @param e31 the expected element in row 3 of column 1
     * @param e32 the expected element in row 3 of column 2
     * @param e33 the expected element in row 3 of column 3
     * @param actual the matrix to test (not {@code null}, unaffected)
     * @param tolerance the allowable difference for each element (&ge;0)
     */
    public static void assertEquals(float e00, float e01, float e02, float e03,
            float e10, float e11, float e12, float e13,
            float e20, float e21, float e22, float e23,
            float e30, float e31, float e32, float e33,
            RMat44Arg actual, float tolerance) {
        Assert.assertEquals("e00", e00, actual.getElement(0, 0), tolerance);
        Assert.assertEquals("e01", e01, actual.getElement(0, 1), tolerance);
        Assert.assertEquals("e02", e02, actual.getElement(0, 2), tolerance);
        Assert.assertEquals("e03", e03, actual.getElement(0, 3), tolerance);
        Assert.assertEquals("e10", e10, actual.getElement(1, 0), tolerance);
        Assert.assertEquals("e11", e11, actual.getElement(1, 1), tolerance);
        Assert.assertEquals("e12", e12, actual.getElement(1, 2), tolerance);
        Assert.assertEquals("e13", e13, actual.getElement(1, 3), tolerance);
        Assert.assertEquals("e20", e20, actual.getElement(2, 0), tolerance);
        Assert.assertEquals("e21", e21, actual.getElement(2, 1), tolerance);
        Assert.assertEquals("e22", e22, actual.getElement(2, 2), tolerance);
        Assert.assertEquals("e23", e23, actual.getElement(2, 3), tolerance);
        Assert.assertEquals("e30", e30, actual.getElement(3, 0), tolerance);
        Assert.assertEquals("e31", e31, actual.getElement(3, 1), tolerance);
        Assert.assertEquals("e32", e32, actual.getElement(3, 2), tolerance);
        Assert.assertEquals("e33", e33, actual.getElement(3, 3), tolerance);
    }

    /**
     * Verify the components of a color.
     *
     * @param r the expected R component
     * @param g the expected G component
     * @param b the expected B component
     * @param a the expected A component
     * @param actual the color to test (not {@code null}, unaffected)
     */
    public static void assertEquals(
            byte r, byte g, byte b, byte a, ConstColor actual) {
        Assert.assertEquals("r component", r, actual.getR());
        Assert.assertEquals("h component", g, actual.getG());
        Assert.assertEquals("b component", b, actual.getB());
        Assert.assertEquals("a component", a, actual.getA());
    }

    /**
     * Verify the components of a quaternion to within some tolerance.
     *
     * @param x the expected X component
     * @param y the expected Y component
     * @param z the expected Z component
     * @param w the expected W component
     * @param actual the Quaternion to test (not {@code null}, unaffected)
     * @param tolerance the allowable difference for each component (&ge;0)
     */
    public static void assertEquals(float x, float y, float z, float w,
            QuatArg actual, float tolerance) {
        Assert.assertEquals("x component", x, actual.getX(), tolerance);
        Assert.assertEquals("y component", y, actual.getY(), tolerance);
        Assert.assertEquals("z component", z, actual.getZ(), tolerance);
        Assert.assertEquals("w component", w, actual.getW(), tolerance);
    }

    /**
     * Verify the components of a location-precision vector to within some
     * tolerance.
     *
     * @param x the expected X component
     * @param y the expected Y component
     * @param z the expected Z component
     * @param actual the vector to test (not {@code null}, unaffected)
     * @param tolerance the allowable difference for each component (&ge;0)
     */
    public static void assertEquals(
            float x, float y, float z, RVec3Arg actual, float tolerance) {
        Assert.assertEquals("x component", x, actual.x(), tolerance);
        Assert.assertEquals("y component", y, actual.y(), tolerance);
        Assert.assertEquals("z component", z, actual.z(), tolerance);
    }

    /**
     * Verify the components of a single-precision vector to within some
     * tolerance.
     *
     * @param x the expected X component
     * @param y the expected Y component
     * @param z the expected Z component
     * @param actual the vector to test (not {@code null}, unaffected)
     * @param tolerance the allowable difference for each component (&ge;0)
     */
    public static void assertEquals(
            float x, float y, float z, Vec3Arg actual, float tolerance) {
        Assert.assertEquals("x component", x, actual.getX(), tolerance);
        Assert.assertEquals("y component", y, actual.getY(), tolerance);
        Assert.assertEquals("z component", z, actual.getZ(), tolerance);
    }

    /**
     * Verify the equivalence of the specified float buffers.
     *
     * @param expected the expected buffer (not {@code null}, unaffected)
     * @param actual the actual buffer (not {@code null}, unaffected)
     */
    public static void assertEquals(FloatBuffer expected, FloatBuffer actual) {
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
     * Verify the equivalence of the specified int buffers.
     *
     * @param expected the expected buffer (not {@code null}, unaffected)
     * @param actual the actual buffer (not {@code null}, unaffected)
     */
    public static void assertEquals(IntBuffer expected, IntBuffer actual) {
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
     * Verify the components of an integer vector.
     *
     * @param x the expected X component
     * @param y the expected Y component
     * @param z the expected Z component
     * @param w the expected W component
     * @param actual the vector to test (not {@code null}, unaffected)
     */
    public static void assertEquals(
            int x, int y, int z, int w, UVec4Arg actual) {
        Assert.assertEquals("x component", x, actual.getX());
        Assert.assertEquals("y component", y, actual.getY());
        Assert.assertEquals("z component", z, actual.getZ());
        Assert.assertEquals("w component", w, actual.getW());
    }

    /**
     * Verify the equivalence of the specified matrices to within some
     * tolerance, ignoring their virtual addresses and ownership.
     *
     * @param expected the expected value (not {@code null}, unaffected)
     * @param actual the vector to test (not {@code null}, unaffected)
     * @param tolerance the allowable difference for each component (&ge;0)
     */
    public static void assertEquals(
            Mat44Arg expected, Mat44Arg actual, float tolerance) {
        assertJpo(expected, actual);

        assertEquals(expected.getAxisX(), actual.getAxisX(), tolerance);
        assertEquals(expected.getAxisY(), actual.getAxisY(), tolerance);
        assertEquals(expected.getAxisZ(), actual.getAxisZ(), tolerance);
        assertEquals(
                expected.getTranslation(), actual.getTranslation(), tolerance);
    }

    /**
     * Verify the equivalence of the specified quaternions to within some
     * tolerance.
     *
     * @param expected the expected value (not {@code null}, unaffected)
     * @param actual the vector to test (not {@code null}, unaffected)
     * @param tolerance the allowable difference for each component (&ge;0)
     */
    public static void assertEquals(
            QuatArg expected, QuatArg actual, float tolerance) {
        assertEquals(expected.getX(), expected.getY(), expected.getZ(),
                expected.getW(), actual, tolerance);
    }

    /**
     * Verify the equivalence of the specified location vectors to within some
     * tolerance.
     *
     * @param expected the expected value (not {@code null}, unaffected)
     * @param actual the vector to test (not {@code null}, unaffected)
     * @param tolerance the allowable difference for each component (&ge;0)
     */
    public static void assertEquals(
            RVec3Arg expected, RVec3Arg actual, float tolerance) {
        assertEquals(
                expected.x(), expected.y(), expected.z(), actual, tolerance);
    }

    /**
     * Verify the equivalence of the specified single-precision vectors to
     * within some tolerance.
     *
     * @param expected the expected value (not {@code null}, unaffected)
     * @param actual the vector to test (not {@code null}, unaffected)
     * @param tolerance the allowable difference for each component (&ge;0)
     */
    public static void assertEquals(
            Vec3Arg expected, Vec3Arg actual, float tolerance) {
        assertEquals(expected.getX(), expected.getY(), expected.getZ(),
                actual, tolerance);
    }

    /**
     * Verify the equivalence of the specified group filters, ignoring their
     * types, virtual addresses, and ownership.
     *
     * @param expected the expected filter (not {@code null}, unaffected)
     * @param actual the actual filter (not {@code null}, unaffected)
     */
    public static void assertGroupFilter(
            ConstGroupFilter expected, ConstGroupFilter actual) {
        assertJpo(expected, actual);

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
     * Verify the equivalence of the specified physics objects, ignoring their
     * types, virtual addresses, and ownership.
     *
     * @param expected the expected object (not {@code null}, unaffected)
     * @param actual the actual object (not {@code null}, unaffected)
     */
    public static void assertJpo(
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
    public static void assertMassProperties(ConstMassProperties expected,
            ConstMassProperties actual) {
        assertJpo(expected, actual);

        Assert.assertEquals(expected.getMass(), actual.getMass(), 0f);
        assertEquals(expected.getInertia(), actual.getInertia(), 0f);
    }

    /**
     * Verify the equivalence of the specified shapes, ignoring their virtual
     * addresses and ownership.
     *
     * @param expected the expected shape (not {@code null}, unaffected)
     * @param actual the actual shape (not {@code null}, unaffected)
     */
    public static void assertShape(ConstShape expected, ConstShape actual) {
        assertJpo(expected, actual);

        // compare the debug shapes:
        int numTriangles = expected.countDebugTriangles();
        Assert.assertEquals(numTriangles, actual.countDebugTriangles());
        int numFloats = 3 * numTriangles;
        FloatBuffer buffer1 = Jolt.newDirectFloatBuffer(numFloats);
        FloatBuffer buffer2 = Jolt.newDirectFloatBuffer(numFloats);
        expected.copyDebugTriangles(buffer1);
        actual.copyDebugTriangles(buffer2);
        Assert.assertEquals(buffer1, buffer2);

        assertEquals(expected.getCenterOfMass(), actual.getCenterOfMass(), 0f);
        Assert.assertEquals(
                expected.getInnerRadius(), actual.getInnerRadius(), 0f);
        assertAaBox(expected.getLocalBounds(), actual.getLocalBounds(), 0f);
        assertMassProperties(
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
        assertAaBox(expected.getWorldSpaceBounds(rmi, scaleI),
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
    public static void assertShapeSettings(
            ConstShapeSettings expected, ConstShapeSettings actual) {
        assertJpo(expected, actual);
    }

    /**
     * Verify the equivalence of the specified spring settings, ignoring their
     * types, virtual addresses, and ownership.
     *
     * @param expected the expected settings (not {@code null}, unaffected)
     * @param actual the actual settings (not {@code null}, unaffected)
     */
    public static void assertSpringSettings(
            ConstSpringSettings expected, ConstSpringSettings actual) {
        assertJpo(expected, actual);

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
     * Verify the equivalence of the specified vehicle-constraint settings,
     * ignoring their types, virtual addresses, and ownership.
     *
     * @param expected the expected settings (not {@code null}, unaffected)
     * @param actual the actual settings (not {@code null}, unaffected)
     */
    public static void assertVehicleConstraintSettings(
            ConstVehicleConstraintSettings expected,
            ConstVehicleConstraintSettings actual) {
        assertConstraintSettings(expected, actual);

        int numBars = expected.getNumAntiRollBars();
        Assert.assertEquals(numBars, actual.getNumAntiRollBars());
        int numWheels = expected.getNumWheels();
        Assert.assertEquals(numWheels, actual.getNumWheels());

        for (int i = 0; i < numBars; ++i) {
            assertAntiRollBar(
                    expected.getAntiRollBar(i), actual.getAntiRollBar(i));
        }
        Assert.assertEquals(
                expected.getControllerType(), actual.getControllerType());
        assertEquals(expected.getForward(), actual.getForward(), 0f);
        Assert.assertEquals(expected.getMaxPitchRollAngle(),
                actual.getMaxPitchRollAngle(), 0f);
        assertEquals(expected.getUp(), actual.getUp(), 0f);

        ConstVehicleControllerSettings controller = expected.getController();
        if (controller == null) {
            Assert.assertNull(actual.getController());
        } else {
            assertVehicleControllerSettings(controller, actual.getController());
        }

        for (int i = 0; i < numWheels; ++i) {
            assertWheelSettings(expected.getWheel(i), actual.getWheel(i));
        }
    }

    /**
     * Verify the equivalence of the specified vehicle-controller settings,
     * ignoring their types, virtual addresses, and ownership.
     *
     * @param expected the expected settings (not {@code null}, unaffected)
     * @param actual the actual settings (not {@code null}, unaffected)
     */
    public static void assertVehicleControllerSettings(
            ConstVehicleControllerSettings expected,
            ConstVehicleControllerSettings actual) {
        assertJpo(expected, actual);

        // compare serialization results:
        StringStream stream1 = new StringStream();
        StringStream stream2 = new StringStream();
        expected.saveBinaryState(new StreamOutWrapper(stream1));
        actual.saveBinaryState(new StreamOutWrapper(stream2));
        Assert.assertEquals(stream1.str(), stream2.str());
    }

    /**
     * Verify the equivalence of the specified wheel settings, ignoring their
     * types, virtual addresses, and ownership.
     *
     * @param expected the expected settings (not {@code null}, unaffected)
     * @param actual the actual settings (not {@code null}, unaffected)
     */
    public static void assertWheelSettings(
            ConstWheelSettings expected, ConstWheelSettings actual) {
        assertJpo(expected, actual);

        Assert.assertEquals(expected.getEnableSuspensionForcePoint(),
                actual.getEnableSuspensionForcePoint());
        assertEquals(expected.getPosition(), actual.getPosition(), 0f);
        Assert.assertEquals(expected.getRadius(), actual.getRadius(), 0f);
        assertEquals(expected.getSteeringAxis(), actual.getSteeringAxis(), 0f);
        assertEquals(expected.getSuspensionDirection(),
                actual.getSuspensionDirection(), 0f);
        assertEquals(expected.getSuspensionForcePoint(),
                actual.getSuspensionForcePoint(), 0f);
        Assert.assertEquals(expected.getSuspensionMaxLength(),
                actual.getSuspensionMaxLength(), 0f);
        Assert.assertEquals(expected.getSuspensionMinLength(),
                actual.getSuspensionMinLength(), 0f);
        Assert.assertEquals(expected.getSuspensionPreloadLength(),
                actual.getSuspensionPreloadLength(), 0f);
        assertSpringSettings(expected.getSuspensionSpring(),
                actual.getSuspensionSpring());
        assertEquals(expected.getWheelForward(), actual.getWheelForward(), 0f);
        assertEquals(expected.getWheelUp(), actual.getWheelUp(), 0f);
        Assert.assertEquals(expected.getWidth(), actual.getWidth(), 0f);

        // compare serialization results:
        StringStream stream1 = new StringStream();
        StringStream stream2 = new StringStream();
        expected.saveBinaryState(new StreamOutWrapper(stream1));
        actual.saveBinaryState(new StreamOutWrapper(stream2));
        Assert.assertEquals(stream1.str(), stream2.str());
    }

    /**
     * Clean up after a test.
     */
    public static void cleanup() {
        Jolt.unregisterTypes();
        Jolt.destroyFactory();

        System.gc();
    }

    /**
     * Clean up the specified {@code PhysicsSystem}.
     *
     * @param physicsSystem the system to clean up (not {@code null})
     */
    public static void cleanupPhysicsSystem(PhysicsSystem physicsSystem) {
        ConstBroadPhaseLayerInterface mapObj2Bp
                = physicsSystem.getBroadPhaseLayerInterface();
        ConstObjectLayerPairFilter ovoFilter = physicsSystem.getOvoFilter();
        ConstObjectVsBroadPhaseLayerFilter ovbFilter
                = physicsSystem.getOvbFilter();

        testClose(physicsSystem, ovbFilter, ovoFilter, mapObj2Bp);
    }

    /**
     * Initialize the loaded native library.
     */
    public static void initializeNativeLibrary() {
        printLibraryInfo(System.out);

        //Jolt.setTraceAllocations(true); // to debug native memory allocation
        if (automateFreeing) {
            JoltPhysicsObject.startCleaner();
        }

        Jolt.registerDefaultAllocator();
        Jolt.installDefaultAssertCallback();
        Jolt.installDefaultTraceCallback();

        boolean success = Jolt.newFactory();
        assert success;
        Jolt.registerTypes();
    }

    /**
     * Load some flavor of native library, preferably a Debug build.
     * <p>
     * The search order is:
     * <ol>
     * <li>DebugSp</li>
     * <li>DebugDp</li>
     * <li>ReleaseSp</li>
     * <li>ReleaseDp</li>
     * </ol>
     */
    public static void loadNativeLibrary() {
        boolean success = loadNativeLibrary("Debug", "Sp");
        if (success) {
            Assert.assertFalse(Jolt.isDoublePrecision());
        } else {
            success = loadNativeLibrary("Debug", "Dp");
            if (success) {
                Assert.assertTrue(Jolt.isDoublePrecision());
            } else {
                success = loadNativeLibrary("Release", "Sp");
                if (success) {
                    Assert.assertFalse(Jolt.isDoublePrecision());
                } else {
                    success = loadNativeLibrary("Release", "Dp");
                    if (success) {
                        Assert.assertTrue(Jolt.isDoublePrecision());
                    }
                }
            }
        }
        Assert.assertTrue(success);
    }

    /**
     * Load a specific Jolt-JNI desktop native library from the filesystem,
     * using the OSHI and jSnapLoader libraries to identify the current
     * platform.
     * <p>
     * This method assumes native libraries are stored in specific locations
     * under a "./build/libs/joltjni/shared" directory. While this is true in
     * the Jolt-JNI build environment, it is unlikely to be true for a
     * standalone application.
     *
     * @param buildType "Debug" or "Release"
     * @param flavor "Sp" or "Dp"
     * @return true after successful load, otherwise false
     */
    public static boolean loadNativeLibrary(String buildType, String flavor) {
        assert buildType.equals("Debug") || buildType.equals("Release") :
                buildType;
        assert flavor.equals("Sp") || flavor.equals("Dp") : flavor;

        File directory = new File("build/libs/joltjni/shared");
        assert directory.exists() : directory;
        assert directory.isDirectory() : directory;
        assert directory.canRead() : directory;

        String name;
        String subdirectory;
        if (NativeVariant.Os.isLinux()) {
            name = "libjoltjni.so";
            String archName = NativeVariant.OS_ARCH.getProperty();
            if (NativeVariant.Cpu.isARM()) {
                if (NativeVariant.Cpu.is64()) {
                    subdirectory = "linux_ARM64";
                } else {
                    subdirectory = "linux_ARM32hf";
                }
            } else if (archName.contains("loong")) {
                subdirectory = "linux_LoongArch64";
            } else if (hasFmaFeatures()) {
                subdirectory = "linux64_fma";
            } else {
                subdirectory = "linux64";
            }

        } else if (NativeVariant.Os.isMac()) {
            name = "libjoltjni.dylib";
            if (NativeVariant.Cpu.isARM()) {
                subdirectory = "macOSX_ARM64";
            } else {
                subdirectory = "macOSX64";
            }

        } else if (NativeVariant.Os.isWindows()) {
            name = "joltjni.dll";
            if (hasAvx2Features()) {
                subdirectory = "windows64_avx2";
            } else {
                subdirectory = "windows64";
            }

        } else {
            String osName = NativeVariant.OS_NAME.getProperty();
            throw new RuntimeException("osName = " + osName);
        }

        File file = new File(directory, subdirectory);
        String bt = firstToLower(buildType);
        file = new File(file, bt);

        String f = firstToLower(flavor);
        file = new File(file, f);

        file = new File(file, name);
        String absoluteFilename = file.getAbsolutePath();

        boolean success = false;
        if (file.exists() && file.canRead()) {
            System.load(absoluteFilename);
            success = true;
        }

        if (!success && (subdirectory.endsWith("_avx2")
                || subdirectory.endsWith("_fma"))) {
            // retry loading without that _avx2/_fma infix

            subdirectory = subdirectory.replace("_avx2", "");
            subdirectory = subdirectory.replace("_fma", "");
            file = new File(directory, subdirectory);
            file = new File(file, bt);
            file = new File(file, f);
            file = new File(file, name);
            absoluteFilename = file.getAbsolutePath();
            if (file.exists() && file.canRead()) {
                System.load(absoluteFilename);
                success = true;
            }
        }

        return success;
    }

    /**
     * Load some flavor of native library, preferably a Release build.
     * <p>
     * The search order is:
     * <ol>
     * <li>ReleaseSp</li>
     * <li>ReleaseDp</li>
     * <li>DebugSp</li>
     * <li>DebugDp</li>
     * </ol>
     */
    public static void loadNativeLibraryRelease() {
        boolean success = loadNativeLibrary("Release", "Sp");
        if (success) {
            Assert.assertFalse(Jolt.isDoublePrecision());
        } else {
            success = loadNativeLibrary("Release", "Dp");
            if (success) {
                Assert.assertTrue(Jolt.isDoublePrecision());
            } else {
                success = loadNativeLibrary("Debug", "Sp");
                if (success) {
                    Assert.assertFalse(Jolt.isDoublePrecision());
                } else {
                    success = loadNativeLibrary("Debug", "Dp");
                    if (success) {
                        Assert.assertTrue(Jolt.isDoublePrecision());
                    }
                }
            }
        }
        Assert.assertTrue(success);
        printLibraryInfo(System.out);
    }

    /**
     * Allocate and initialize a {@code PhysicsSystem} in the customary
     * configuration.
     *
     * @param maxBodies the desired number of bodies (&ge;1)
     * @return a new system
     */
    public static PhysicsSystem newPhysicsSystem(int maxBodies) {
        // broadphase layer IDs:
        int bpLayerNonMoving = 0;
        int bpLayerMoving = 1;
        int numBpLayers = 2;

        BroadPhaseLayerInterface mapObj2Bp
                = new BroadPhaseLayerInterfaceTable(numObjLayers, numBpLayers)
                        .mapObjectToBroadPhaseLayer(
                                objLayerNonMoving, bpLayerNonMoving)
                        .mapObjectToBroadPhaseLayer(
                                objLayerMoving, bpLayerMoving);
        ObjectLayerPairFilter objVsObjFilter
                = new ObjectLayerPairFilterTable(numObjLayers)
                        .enableCollision(objLayerMoving, objLayerMoving)
                        .enableCollision(objLayerMoving, objLayerNonMoving);
        ObjectVsBroadPhaseLayerFilter objVsBpFilter
                = new ObjectVsBroadPhaseLayerFilterTable(
                        mapObj2Bp, numBpLayers, objVsObjFilter, numObjLayers);

        int numBodyMutexes = 0; // 0 means "use the default value"
        int maxBodyPairs = 65_536;
        int maxContacts = 20_480;
        PhysicsSystem result = new PhysicsSystem();
        result.init(maxBodies, numBodyMutexes, maxBodyPairs, maxContacts,
                mapObj2Bp, objVsBpFilter, objVsObjFilter);

        return result;
    }

    /**
     * Return the recommended number of worker threads.
     *
     * @return the count (&ge;1)
     */
    public static int numThreads() {
        int numCpus = Runtime.getRuntime().availableProcessors();
        int result = (int) Math.floor(0.9 * numCpus);
        if (result < 1) {
            result = 1;
        }

        return result;
    }

    /**
     * Print basic library information to the specified stream during
     * initialization.
     *
     * @param printStream the stream to print to (not {@code null})
     */
    public static void printLibraryInfo(PrintStream printStream) {
        printStream.print("Jolt JNI version ");
        printStream.print(Jolt.versionString());
        printStream.print('-');

        String buildType = Jolt.buildType();
        printStream.print(buildType);

        if (Jolt.isDoublePrecision()) {
            printStream.print("Dp");
        } else {
            printStream.print("Sp");
        }

        printStream.println(" initializing...");
        printStream.println();
        printStream.flush();
    }

    /**
     * Test the {@code close()} methods of the specified physics objects.
     * However, if freeing is automated, {@code close()} is neither invoked nor
     * tested.
     *
     * @param objects the objects to test (not {@code null})
     */
    public static void testClose(ConstJoltPhysicsObject... objects) {
        if (!automateFreeing) {
            for (ConstJoltPhysicsObject object : objects) {
                boolean wasOwner = object.ownsNativeObject();
                object.close();

                if (wasOwner) {
                    Assert.assertFalse(object.hasAssignedNativeObject());
                }
                Assert.assertFalse(object.ownsNativeObject());
            }
        }
    }
    // *************************************************************************
    // private methods

    /**
     * Convert the first character of the specified text to lower case.
     *
     * @param input the input text to convert (not {@code null})
     * @return the converted text (not {@code null})
     */
    private static String firstToLower(String input) {
        String result = input;
        if (!input.isEmpty()) {
            String first = input.substring(0, 1);
            first = first.toLowerCase(Locale.ROOT);
            String rest = input.substring(1);
            result = first + rest;
        }

        return result;
    }

    /**
     * Test for the presence of four x86_64 ISA extensions that Jolt Physics can
     * exploit, including AVX2.
     *
     * @return {@code true} if those ISA extensions are present, otherwise
     * {@code false}
     */
    private static boolean hasAvx2Features() {
        boolean result = hasCpuFeatures("avx", "avx2", "sse4_1", "sse4_2");
        return result;
    }

    /**
     * Test whether all the named CPU features are present.
     *
     * @param requiredFeatures the names of the features to test for
     * @return {@code true} if all are present, otherwise {@code false}
     */
    private static boolean hasCpuFeatures(String... requiredFeatures) {
        // Obtain the list of CPU feature strings from OSHI:
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor cpu = hal.getProcessor();
        List<String> oshiList = cpu.getFeatureFlags();

        Pattern pattern = Pattern.compile("[a-z][a-z0-9_]*");

        // Convert the list to a collection of feature names:
        Collection<String> presentFeatures = new TreeSet<>();
        for (String oshiString : oshiList) {
            String lcString = oshiString.toLowerCase(Locale.ROOT);
            Matcher matcher = pattern.matcher(lcString);
            while (matcher.find()) {
                String featureName = matcher.group();
                presentFeatures.add(featureName);
            }
        }

        // Test for each required CPU feature:
        for (String featureName : requiredFeatures) {
            String linuxName = featureName.toLowerCase(Locale.ROOT);
            String windowsName = "pf_" + linuxName + "_instructions_available";
            boolean isPresent = presentFeatures.contains(linuxName)
                    || presentFeatures.contains(windowsName);
            if (!isPresent) {
                return false;
            }
        }

        return true;
    }

    /**
     * Test for the presence of seven x86_64 ISA extensions that Jolt Physics
     * can exploit, including AVX2 and FMA.
     *
     * @return {@code true} if those ISA extensions are present, otherwise
     * {@code false}
     */
    private static boolean hasFmaFeatures() {
        boolean result = hasCpuFeatures(
                "avx", "avx2", "bmi1", "f16c", "fma", "sse4_1", "sse4_2");
        return result;
    }
}
