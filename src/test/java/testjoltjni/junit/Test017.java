/*
Copyright (c) 2024-2026 Stephen Gold

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
import com.github.stephengold.joltjni.Face;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.SoftBodyCreationSettings;
import com.github.stephengold.joltjni.SoftBodyMotionProperties;
import com.github.stephengold.joltjni.SoftBodySharedSettings;
import com.github.stephengold.joltjni.Vertex;
import com.github.stephengold.joltjni.VertexAttributes;
import com.github.stephengold.joltjni.Volume;
import com.github.stephengold.joltjni.enumerate.ELraType;
import com.github.stephengold.joltjni.readonly.ConstFace;
import com.github.stephengold.joltjni.readonly.ConstSoftBodyCreationSettings;
import com.github.stephengold.joltjni.readonly.ConstSoftBodyMotionProperties;
import com.github.stephengold.joltjni.readonly.ConstSoftBodySharedSettings;
import com.github.stephengold.joltjni.readonly.ConstVertex;
import com.github.stephengold.joltjni.readonly.ConstVertexAttributes;
import com.github.stephengold.joltjni.readonly.ConstVolume;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Automated JUnit4 tests for creation, destruction, accessors, and defaults of
 * soft-body classes.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test017 {
    // *************************************************************************
    // new methods exposed

    /**
     * Test object creation, destruction, accessors, and defaults.
     */
    @Test
    public void test017() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        doFace();
        doSoftBodyCreationSettings();
        doSoftBodyMotionProperties();
        doVertex();
        doVertexAttributes();
        doVolume();

        TestUtils.cleanup();
    }
    // *************************************************************************
    // Java private methods

    /**
     * Test the {@code Face} class.
     */
    private static void doFace() {
        Face face = new Face();
        Face faceCopy = new Face(face);

        testFaceDefaults(face);
        testFaceSetters(face);

        testFaceDefaults(faceCopy);

        TestUtils.testClose(faceCopy, face);
        System.gc();
    }

    /**
     * Test the {@code SoftBodyCreationSettings} class.
     */
    private static void doSoftBodyCreationSettings() {
        { // no-arg constructor:
            SoftBodyCreationSettings sbcs = new SoftBodyCreationSettings();

            Assert.assertNull(sbcs.getSettings());
            testSbcsDefaults(sbcs);
            testSbcsSetters(sbcs);

            TestUtils.testClose(sbcs);
        }
        { // copy constructor:
            ConstSoftBodyCreationSettings original
                    = new SoftBodyCreationSettings();
            SoftBodyCreationSettings copy
                    = new SoftBodyCreationSettings(original);

            Assert.assertNull(copy.getSettings());
            testSbcsDefaults(copy);
            testSbcsSetters(copy);

            TestUtils.testClose(copy, original);
        }
        { // constructed from a SoftBodySharedSettings:
            SoftBodySharedSettings sbss = new SoftBodySharedSettings();
            RVec3Arg location = new RVec3();
            QuatArg orientation = new Quat();
            int objectLayer = 0;
            SoftBodyCreationSettings sbcs = new SoftBodyCreationSettings(
                    sbss, location, orientation, objectLayer);

            ConstSoftBodySharedSettings actualSbss = sbcs.getSettings();
            Assert.assertEquals(sbss, actualSbss);
            testSbcsDefaults(sbcs);
            testSbcsSetters(sbcs);

            TestUtils.testClose(actualSbss, sbcs, sbss);
        }

        System.gc();
    }

    /**
     * Test the {@code SoftBodyMotionProperties} class.
     */
    private static void doSoftBodyMotionProperties() {
        SoftBodyMotionProperties properties = new SoftBodyMotionProperties();

        testSoftBodyMotionPropertiesDefaults(properties);
        testSoftBodyMotionPropertiesSetters(properties);

        TestUtils.testClose(properties);
        System.gc();
    }

    /**
     * Test the {@code Vertex} class.
     */
    private static void doVertex() {
        Vertex vertex = new Vertex();
        ConstVertex vertexCopy = new Vertex(vertex);

        testVertexDefaults(vertex);
        testVertexSetters(vertex);

        testVertexDefaults(vertexCopy);

        TestUtils.testClose(vertexCopy, vertex);
        System.gc();
    }

    /**
     * Test the {@code VertexAttributes} class.
     */
    private static void doVertexAttributes() {
        VertexAttributes attr = new VertexAttributes();
        ConstVertexAttributes attrCopy = new VertexAttributes(attr);

        testVertexAttributesDefaults(attr);
        testVertexAttributesSetters(attr);

        testVertexAttributesDefaults(attrCopy);

        ConstVertexAttributes attributes
                = new VertexAttributes(0f, 0f, Float.MAX_VALUE);
        testVertexAttributesDefaults(attributes);

        TestUtils.testClose(attributes, attrCopy, attr);
        System.gc();
    }

    /**
     * Test the {@code Volume} class.
     */
    private static void doVolume() {
        Volume volume = new Volume();
        Volume volumeCopy = new Volume(volume);

        testVolumeDefaults(volume);
        testVolumeSetters(volume);

        testVolumeDefaults(volumeCopy);

        TestUtils.testClose(volumeCopy, volume);
        System.gc();
    }

    /**
     * Test the getters and defaults of the specified {@code Face}.
     *
     * @param face the face to test (not {@code null}, unaffected)
     */
    private static void testFaceDefaults(ConstFace face) {
        Assert.assertTrue(face.hasAssignedNativeObject());
        Assert.assertTrue(face.ownsNativeObject());

        Assert.assertEquals(0, face.getMaterialIndex());
        Assert.assertEquals(0, face.getVertex(0));
        Assert.assertEquals(0, face.getVertex(1));
        Assert.assertEquals(0, face.getVertex(2));
        Assert.assertTrue(face.isDegenerate());
    }

    /**
     * Test the setters of the specified {@code Face}.
     *
     * @param face the face to test (not {@code null}, modified)
     */
    private static void testFaceSetters(Face face) {
        face.setMaterialIndex(6);
        face.setVertex(0, 7);
        face.setVertex(1, 8);
        face.setVertex(2, 5);

        Assert.assertEquals(6, face.getMaterialIndex());
        Assert.assertEquals(7, face.getVertex(0));
        Assert.assertEquals(8, face.getVertex(1));
        Assert.assertEquals(5, face.getVertex(2));
        Assert.assertFalse(face.isDegenerate());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code SoftBodyCreationSettings}.
     *
     * @param sbcs the settings to test (not {@code null}, unaffected)
     */
    private static void testSbcsDefaults(ConstSoftBodyCreationSettings sbcs) {
        Assert.assertTrue(sbcs.hasAssignedNativeObject());
        Assert.assertTrue(sbcs.ownsNativeObject());

        Assert.assertTrue(sbcs.getAllowSleeping());
        Assert.assertNotNull(sbcs.getCollisionGroup());
        Assert.assertFalse(sbcs.getFacesDoubleSided());
        Assert.assertEquals(0.2f, sbcs.getFriction(), 0f);
        Assert.assertEquals(1f, sbcs.getGravityFactor(), 0f);
        Assert.assertEquals(0.1f, sbcs.getLinearDamping(), 0f);
        Assert.assertTrue(sbcs.getMakeRotationIdentity());
        Assert.assertEquals(500f, sbcs.getMaxLinearVelocity(), 0f);
        Assert.assertEquals(0, sbcs.getObjectLayer());
        TestUtils.assertEquals(0f, 0f, 0f, sbcs.getPosition(), 0f);
        Assert.assertEquals(0f, sbcs.getPressure(), 0f);
        Assert.assertEquals(0f, sbcs.getRestitution(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, 1f, sbcs.getRotation(), 0f);
        Assert.assertTrue(sbcs.getUpdatePosition());
    }

    /**
     * Test the setters of the specified {@code SoftBodyCreationSettings}.
     *
     * @param sbcs the settings to test (not {@code null}, modified)
     */
    private static void testSbcsSetters(SoftBodyCreationSettings sbcs) {
        sbcs.setAllowSleeping(false);

        CollisionGroup newGroup = new CollisionGroup();
        sbcs.setCollisionGroup(newGroup);

        sbcs.setFacesDoubleSided(true);
        sbcs.setFriction(0.02f);
        sbcs.setGravityFactor(0.06f);
        sbcs.setLinearDamping(0.07f);
        sbcs.setMakeRotationIdentity(false);
        sbcs.setMaxLinearVelocity(0.102f);
        sbcs.setObjectLayer(65_535);
        sbcs.setPosition(new RVec3(3., 4., 5.));
        sbcs.setPressure(0.09f);
        sbcs.setRestitution(0.15f);
        sbcs.setRotation(new Quat(0.5f, 0.5f, -0.5f, -0.5f));
        sbcs.setUpdatePosition(false);

        SoftBodySharedSettings newSs = new SoftBodySharedSettings();
        sbcs.setSettings(newSs);

        Assert.assertFalse(sbcs.getAllowSleeping());
        Equivalent.collisionGroup(newGroup, sbcs.getCollisionGroup());
        Assert.assertTrue(sbcs.getFacesDoubleSided());
        Assert.assertEquals(0.02f, sbcs.getFriction(), 0f);
        Assert.assertEquals(0.06f, sbcs.getGravityFactor(), 0f);
        Assert.assertEquals(0.07f, sbcs.getLinearDamping(), 0f);
        Assert.assertFalse(sbcs.getMakeRotationIdentity());
        Assert.assertEquals(0.102f, sbcs.getMaxLinearVelocity(), 0f);
        Assert.assertEquals(65_535, sbcs.getObjectLayer());
        TestUtils.assertEquals(3f, 4f, 5f, sbcs.getPosition(), 0f);
        Assert.assertEquals(0.09f, sbcs.getPressure(), 0f);
        Assert.assertEquals(0.15f, sbcs.getRestitution(), 0f);
        TestUtils.assertEquals(
                0.5f, 0.5f, -0.5f, -0.5f, sbcs.getRotation(), 0f);

        ConstSoftBodySharedSettings actualSs = sbcs.getSettings();
        Assert.assertEquals(newSs, actualSs);

        Assert.assertFalse(sbcs.getUpdatePosition());

        TestUtils.testClose(actualSs, newSs, newGroup);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code SoftBodyMotionProperties}.
     *
     * @param properties the properties to test (not {@code null}, unaffected)
     */
    private static void testSoftBodyMotionPropertiesDefaults(
            ConstSoftBodyMotionProperties properties) {
        Assert.assertTrue(properties.hasAssignedNativeObject());
        Assert.assertTrue(properties.ownsNativeObject());

        Assert.assertTrue(properties.getEnableSkinConstraints());
        Assert.assertEquals(0, properties.getNumIterations());
        Assert.assertEquals(
                1f, properties.getSkinnedMaxDistanceMultiplier(), 0f);
    }

    /**
     * Test the setters of the specified {@code SoftBodyMotionProperties}.
     *
     * @param properties the properties to test (not {@code null}, modified)
     */
    private static void testSoftBodyMotionPropertiesSetters(
            SoftBodyMotionProperties properties) {
        properties.setEnableSkinConstraints(false);
        properties.setNumIterations(2);
        properties.setSkinnedMaxDistanceMultiplier(9f);

        Assert.assertFalse(properties.getEnableSkinConstraints());
        Assert.assertEquals(2, properties.getNumIterations());
        Assert.assertEquals(
                9f, properties.getSkinnedMaxDistanceMultiplier(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code Vertex}.
     *
     * @param vertex the vertex to test (not {@code null}, unaffected)
     */
    private static void testVertexDefaults(ConstVertex vertex) {
        Assert.assertEquals(1f, vertex.getInvMass(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, vertex.getPosition(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, vertex.getVelocity(), 0f);
    }

    /**
     * Test the setters of the specified {@code Vertex}.
     *
     * @param vertex the vertex to test (not {@code null}, modified)
     */
    private static void testVertexSetters(Vertex vertex) {
        vertex.setInvMass(3f);
        vertex.setPosition(4f, 5f, 6f);
        vertex.setVelocity(7f, 8f, 9f);

        Assert.assertEquals(3f, vertex.getInvMass(), 0f);
        TestUtils.assertEquals(4f, 5f, 6f, vertex.getPosition(), 0f);
        TestUtils.assertEquals(7f, 8f, 9f, vertex.getVelocity(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code VertexAttributes}.
     *
     * @param attr the attributes to test (not {@code null}, unaffected)
     */
    private static void testVertexAttributesDefaults(
            ConstVertexAttributes attr) {
        Assert.assertEquals(Float.MAX_VALUE, attr.getBendCompliance(), 0f);
        Assert.assertEquals(0f, attr.getCompliance(), 0f);
        Assert.assertEquals(1f, attr.getLraMaxDistanceMultiplier(), 0f);
        Assert.assertEquals(ELraType.None, attr.getLraType());
        Assert.assertEquals(0f, attr.getShearCompliance(), 0f);
    }

    /**
     * Test the setters of the specified {@code VertexAttributes}.
     *
     * @param attr the attributes to test (not {@code null}, modified)
     */
    private static void testVertexAttributesSetters(VertexAttributes attr) {
        attr.setBendCompliance(6f);
        attr.setCompliance(7f);
        attr.setLraMaxDistanceMultiplier(8f);
        attr.setLraType(ELraType.GeodesicDistance);
        attr.setShearCompliance(9f);

        Assert.assertEquals(6f, attr.getBendCompliance(), 0f);
        Assert.assertEquals(7f, attr.getCompliance(), 0f);
        Assert.assertEquals(8f, attr.getLraMaxDistanceMultiplier(), 0f);
        Assert.assertEquals(ELraType.GeodesicDistance, attr.getLraType());
        Assert.assertEquals(9f, attr.getShearCompliance(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code Volume}.
     *
     * @param volume the volume to test (not {@code null}, unaffected)
     */
    private static void testVolumeDefaults(ConstVolume volume) {
        Assert.assertTrue(volume.hasAssignedNativeObject());
        Assert.assertTrue(volume.ownsNativeObject());

        Assert.assertEquals(0f, volume.getCompliance(), 0f);
        Assert.assertEquals(1f, volume.getSixRestVolume(), 0f);
        Assert.assertEquals(0, volume.getVertex(0));
        Assert.assertEquals(0, volume.getVertex(1));
        Assert.assertEquals(0, volume.getVertex(2));
        Assert.assertEquals(0, volume.getVertex(3));
    }

    /**
     * Test the setters of the specified {@code Face}.
     *
     * @param volume the volume to test (not {@code null}, modified)
     */
    private static void testVolumeSetters(Volume volume) {
        volume.setCompliance(1.5f);
        volume.setSixRestVolume(3.2f);
        volume.setVertex(0, 7);
        volume.setVertex(1, 8);
        volume.setVertex(2, 5);
        volume.setVertex(3, 9);

        Assert.assertEquals(1.5f, volume.getCompliance(), 0f);
        Assert.assertEquals(3.2f, volume.getSixRestVolume(), 0f);
        Assert.assertEquals(7, volume.getVertex(0));
        Assert.assertEquals(8, volume.getVertex(1));
        Assert.assertEquals(5, volume.getVertex(2));
        Assert.assertEquals(9, volume.getVertex(3));
    }
}
