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

import com.github.stephengold.joltjni.ComputeSystem;
import com.github.stephengold.joltjni.ComputeSystemCpu;
import com.github.stephengold.joltjni.ComputeSystemRef;
import com.github.stephengold.joltjni.DrawSettings;
import com.github.stephengold.joltjni.Float3;
import com.github.stephengold.joltjni.Float4;
import com.github.stephengold.joltjni.Gradient;
import com.github.stephengold.joltjni.HairMaterial;
import com.github.stephengold.joltjni.HairSettings;
import com.github.stephengold.joltjni.HairSettingsRef;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.RStrand;
import com.github.stephengold.joltjni.SStrand;
import com.github.stephengold.joltjni.SVertex;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.ERenderStrandColor;
import com.github.stephengold.joltjni.readonly.ConstAaBox;
import com.github.stephengold.joltjni.readonly.ConstDrawSettings;
import com.github.stephengold.joltjni.readonly.ConstGradient;
import com.github.stephengold.joltjni.readonly.ConstHairMaterial;
import com.github.stephengold.joltjni.readonly.ConstHairSettings;
import com.github.stephengold.joltjni.readonly.ConstSVertex;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Automated JUnit4 tests for creation, destruction, accessors, and defaults of
 * hair-related Jolt-JNI objects.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test015 {
    // *************************************************************************
    // new methods exposed

    /**
     * Test object creation, destruction, accessors, and defaults.
     */
    @Test
    public void test015() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        if (Jolt.implementsDebugRendering()) {
            doDrawSettings();
        }
        doComputeSystem();
        doGradient();
        doHairMaterial();
        doHairSettings();
        doRStrand();
        doSStrand();
        doSVertex();

        TestUtils.cleanup();
    }
    // *************************************************************************
    // Java private methods

    /**
     * Test the {@code ComputeSystem} classes.
     */
    private static void doComputeSystem() {
        ComputeSystem system = new ComputeSystemCpu();

        ComputeSystemRef ref = system.toRef();
        ComputeSystem alias = ref.getPtr();

        TestUtils.testClose(ref);
        System.gc();
    }

    /**
     * Test the {@code DrawSettings} class.
     */
    private static void doDrawSettings() {
        DrawSettings settings = new DrawSettings();

        testDrawSettingsDefaults(settings);
        testDrawSettingsSetters(settings);

        TestUtils.testClose(settings);
        System.gc();
    }

    /**
     * Test the {@code Gradient} class.
     */
    private static void doGradient() {
        Gradient gradient = new Gradient();

        testGradientDefaults(gradient);
        testGradientSetters(gradient);

        TestUtils.testClose(gradient);
        System.gc();
    }

    /**
     * Test the {@code HairMaterial} class.
     */
    private static void doHairMaterial() {
        HairMaterial material = new HairMaterial();

        testHairMaterialDefaults(material);
        testHairMaterialSetters(material);

        TestUtils.testClose(material);
        System.gc();
    }

    /**
     * Test the {@code HairSettings} class.
     */
    private static void doHairSettings() {
        HairSettings settings = new HairSettings();

        testHairSettingsDefaults(settings);
        HairSettingsRef ref = settings.toRef();
        testHairSettingsDefaults(ref);
        testHairSettingsSetters(settings);

        TestUtils.testClose(ref, settings);
        System.gc();
    }

    /**
     * Test the {@code RStrand} class.
     */
    private static void doRStrand() {
        RStrand strand = new RStrand();
        testRStrandDefaults(strand);

        RStrand s2 = new RStrand(0, 0);
        testRStrandDefaults(s2);

        TestUtils.testClose(s2, strand);
        System.gc();
    }

    /**
     * Test the {@code RStrand} class.
     */
    private static void doSStrand() {
        SStrand strand = new SStrand();
        testSStrandDefaults(strand);

        SStrand s2 = new SStrand(0, 0, 0);
        testSStrandDefaults(s2);

        TestUtils.testClose(s2, strand);
        System.gc();
    }

    /**
     * Test the {@code SVertex} class.
     */
    private static void doSVertex() {
        SVertex vertex = new SVertex();

        testSVertexDefaults(vertex);
        testSVertexSetters(vertex);

        SVertex v2 = new SVertex(new Float3(0f, 0f, 0f), 1f);

        testSVertexDefaults(v2);
        testSVertexSetters(v2);

        TestUtils.testClose(v2, vertex);
        System.gc();
    }

    /**
     * Test the getters and defaults of the specified {@code DrawSettings}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testDrawSettingsDefaults(ConstDrawSettings settings) {
        Assert.assertFalse(settings.getDrawAngularVelocity());
        Assert.assertFalse(settings.getDrawGridDensity());
        Assert.assertFalse(settings.getDrawGridVelocity());
        Assert.assertTrue(settings.getDrawInitialGravity());
        Assert.assertFalse(settings.getDrawNeutralDensity());
        Assert.assertFalse(settings.getDrawOrientations());
        Assert.assertFalse(settings.getDrawRenderStrands());
        Assert.assertTrue(settings.getDrawRods());
        Assert.assertFalse(settings.getDrawSkinPoints());
        Assert.assertFalse(settings.getDrawUnloadedRods());
        Assert.assertFalse(settings.getDrawVertexVelocity());
        Assert.assertEquals(ERenderStrandColor.PerSimulatedStrand,
                settings.getRenderStrandColor());
        Assert.assertEquals(0, settings.getSimulationStrandBegin());
        Assert.assertEquals(-1, settings.getSimulationStrandEnd());
    }

    /**
     * Test the setters of the specified {@code DrawSettings}.
     *
     * @param settings the settings to test (not {@code null})
     */
    private static void testDrawSettingsSetters(DrawSettings settings) {
        settings.setDrawAngularVelocity(true);
        settings.setDrawGridDensity(true);
        settings.setDrawGridVelocity(true);
        settings.setDrawInitialGravity(false);
        settings.setDrawNeutralDensity(true);
        settings.setDrawOrientations(true);
        settings.setDrawRenderStrands(true);
        settings.setDrawRods(false);
        settings.setDrawSkinPoints(true);
        settings.setDrawUnloadedRods(true);
        settings.setDrawVertexVelocity(true);
        settings.setRenderStrandColor(ERenderStrandColor.GridVelocityFactor);
        settings.setSimulationStrandBegin(5);
        settings.setSimulationStrandEnd(9);

        Assert.assertTrue(settings.getDrawAngularVelocity());
        Assert.assertTrue(settings.getDrawGridDensity());
        Assert.assertTrue(settings.getDrawGridVelocity());
        Assert.assertFalse(settings.getDrawInitialGravity());
        Assert.assertTrue(settings.getDrawNeutralDensity());
        Assert.assertTrue(settings.getDrawOrientations());
        Assert.assertTrue(settings.getDrawRenderStrands());
        Assert.assertFalse(settings.getDrawRods());
        Assert.assertTrue(settings.getDrawSkinPoints());
        Assert.assertTrue(settings.getDrawUnloadedRods());
        Assert.assertTrue(settings.getDrawVertexVelocity());
        Assert.assertEquals(ERenderStrandColor.GridVelocityFactor,
                settings.getRenderStrandColor());
        Assert.assertEquals(5, settings.getSimulationStrandBegin());
        Assert.assertEquals(9, settings.getSimulationStrandEnd());
    }

    /**
     * Test the getters and defaults of the specified {@code Gradient}.
     *
     * @param gradient the gradient to test (not {@code null}, unaffected)
     */
    private static void testGradientDefaults(ConstGradient gradient) {
        Assert.assertTrue(gradient.hasAssignedNativeObject());
        Assert.assertTrue(gradient.ownsNativeObject());

        Assert.assertEquals(1f, gradient.getMax(), 0f);
        Assert.assertEquals(1f, gradient.getMaxFraction(), 0f);
        Assert.assertEquals(0f, gradient.getMin(), 0f);
        Assert.assertEquals(0f, gradient.getMinFraction(), 0f);
    }

    /**
     * Test the setters of the specified {@code Gradient}.
     *
     * @param gradient the gradient to test (not {@code null}, modified)
     */
    private static void testGradientSetters(Gradient gradient) {
        gradient.setMax(3f);
        gradient.setMaxFraction(0.9f);
        gradient.setMin(-2f);
        gradient.setMinFraction(0.1f);

        Assert.assertEquals(3f, gradient.getMax(), 0f);
        Assert.assertEquals(0.9f, gradient.getMaxFraction(), 0f);
        Assert.assertEquals(-2f, gradient.getMin(), 0f);
        Assert.assertEquals(0.1f, gradient.getMinFraction(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code HairMaterial}.
     *
     * @param material the material to test (not {@code null}, unaffected)
     */
    private static void testHairMaterialDefaults(ConstHairMaterial material) {
        Assert.assertTrue(material.hasAssignedNativeObject());
        Assert.assertTrue(material.ownsNativeObject());

        Assert.assertEquals(2f, material.getAngularDamping(), 0f);
        Assert.assertEquals(1e-7f, material.getBendCompliance(), 0f);
        TestUtils.assertEquals(
                1f, 100f, 100f, 1f, material.getBendComplianceMultiplier(), 0f);
        Assert.assertTrue(material.getEnableCollision());
        Assert.assertTrue(material.getEnableLra());
        Assert.assertEquals(0.2f, material.getFriction(), 0f);
        TestUtils.assertEquals(
                0.01f, 0f, 0f, 0.3f, material.getGlobalPose(), 0f);
        TestUtils.assertEquals(
                0.1f, 1f, 0.2f, 0.8f, material.getGravityFactor(), 0f);
        Assert.assertEquals(0f, material.getGravityPreloadFactor(), 0f);
        Assert.assertEquals(0f, material.getGridDensityForceFactor(), 0f);
        TestUtils.assertEquals(0.05f, 0.01f, 0f, 1f,
                material.getGridVelocityFactor(), 0f);
        TestUtils.assertEquals(0.001f, 0.001f, 0f, 1f,
                material.getHairRadius(), 0f);
        Assert.assertEquals(10f, material.getInertiaMultiplier(), 0f);
        Assert.assertEquals(2f, material.getLinearDamping(), 0f);
        Assert.assertEquals(50f, material.getMaxAngularVelocity(), 0f);
        Assert.assertEquals(10f, material.getMaxLinearVelocity(), 0f);
        Assert.assertEquals(0.1f, material.getSimulationStrandsFraction(), 0f);
        TestUtils.assertEquals(1f, 0f, 0f, 0.1f,
                material.getSkinGlobalPose(), 0f);
        Assert.assertEquals(1e-8f, material.getStretchCompliance(), 0f);
        TestUtils.assertEquals(0f, 1f, 0f, 1f,
                material.getWorldTransformInfluence(), 0f);
    }

    /**
     * Test the setters of the specified {@code HairMaterial}.
     *
     * @param material the material to test (not {@code null}, modified)
     */
    private static void testHairMaterialSetters(HairMaterial material) {
        material.setAngularDamping(4f);
        material.setBendCompliance(0.1f);
        material.setBendComplianceMultiplier(new Float4(2f, 3f, 4f, 6f));
        material.setEnableCollision(false);
        material.setEnableLra(false);
        material.setFriction(0.7f);
        Gradient g1 = new Gradient(0.06f, 5f, 0.4f, 0.5f);
        material.setGlobalPose(g1);
        Gradient g2 = new Gradient(0.2f, 2f, 0.3f, 0.6f);
        material.setGravityFactor(g2);
        material.setGravityPreloadFactor(0.5f);
        material.setGridDensityForceFactor(0.4f);
        Gradient g3 = new Gradient(0.1f, 0.2f, 0.3f, 0.7f);
        material.setGridVelocityFactor(g3);
        Gradient g4 = new Gradient(0.4f, 0.6f, 0.8f, 0.9f);
        material.setHairRadius(g4);
        material.setInertiaMultiplier(12f);
        material.setLinearDamping(3);
        material.setMaxAngularVelocity(80f);
        material.setMaxLinearVelocity(20f);
        material.setSimulationStrandsFraction(0.2f);
        Gradient g5 = new Gradient(0.3f, 3f, 0.2f, 0.9f);
        material.setSkinGlobalPose(g5);
        material.setStretchCompliance(1e-4f);
        Gradient g6 = new Gradient(0.2f, 3f, 0.2f, 0.9f);
        material.setWorldTransformInfluence(g6);

        Assert.assertEquals(4f, material.getAngularDamping(), 0f);
        Assert.assertEquals(0.1f, material.getBendCompliance(), 0f);
        TestUtils.assertEquals(
                2f, 3f, 4f, 6f, material.getBendComplianceMultiplier(), 0f);
        Assert.assertFalse(material.getEnableCollision());
        Assert.assertFalse(material.getEnableLra());
        Assert.assertEquals(0.7f, material.getFriction(), 0f);
        TestUtils.assertEquals(
                0.06f, 5f, 0.4f, 0.5f, material.getGlobalPose(), 0f);
        TestUtils.assertEquals(
                0.2f, 2f, 0.3f, 0.6f, material.getGravityFactor(), 0f);
        Assert.assertEquals(0.5f, material.getGravityPreloadFactor(), 0f);
        Assert.assertEquals(0.4f, material.getGridDensityForceFactor(), 0f);
        TestUtils.assertEquals(0.1f, 0.2f, 0.3f, 0.7f,
                material.getGridVelocityFactor(), 0f);
        TestUtils.assertEquals(0.4f, 0.6f, 0.8f, 0.9f,
                material.getHairRadius(), 0f);
        Assert.assertEquals(12f, material.getInertiaMultiplier(), 0f);
        Assert.assertEquals(3f, material.getLinearDamping(), 0f);
        Assert.assertEquals(80f, material.getMaxAngularVelocity(), 0f);
        Assert.assertEquals(20f, material.getMaxLinearVelocity(), 0f);
        Assert.assertEquals(0.2f, material.getSimulationStrandsFraction(), 0f);
        TestUtils.assertEquals(0.3f, 3f, 0.2f, 0.9f,
                material.getSkinGlobalPose(), 0f);
        Assert.assertEquals(1e-4f, material.getStretchCompliance(), 0f);
        TestUtils.assertEquals(0.2f, 3f, 0.2f, 0.9f,
                material.getWorldTransformInfluence(), 0f);

        TestUtils.testClose(g6, g5, g4, g3, g2, g1);
    }

    /**
     * Test the getters and defaults of the specified {@code HairSettings}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testHairSettingsDefaults(ConstHairSettings settings) {
        Assert.assertTrue(settings.hasAssignedNativeObject());
        Assert.assertTrue(settings.ownsNativeObject());

        Assert.assertEquals(0, settings.countMaterials());
        Assert.assertEquals(0, settings.countRenderStrands());
        Assert.assertEquals(0, settings.countScalpTriangles());
        Assert.assertEquals(0, settings.countScalpVertices());
        Assert.assertEquals(0, settings.countSimStrands());
        TestUtils.assertEquals(
                0f, -9.81f, 0f, settings.getInitialGravity(), 0f);
        Assert.assertEquals(HairSettings.cDefaultIterationsPerSecond,
                settings.getNumIterationsPerSecond());
        Assert.assertEquals(0, settings.getScalpNumSkinWeightsPerVertex());

        ConstAaBox bounds = settings.getSimulationBounds();
        TestUtils.assertEquals(0f, 0f, 0f, bounds.getCenter(), 0f);
        TestUtils.assertEquals(1f, 1f, 1f, bounds.getExtent(), 0f);
    }

    /**
     * Test the setters of the specified {@code HairSettings}.
     *
     * @param settings the settings to test (not {@code null}, modified)
     */
    private static void testHairSettingsSetters(HairSettings settings) {
        HairMaterial material = new HairMaterial();
        settings.addMaterial(material);

        settings.setInitialGravity(new Vec3(1f, 2f, 3f));
        settings.setNumIterationsPerSecond(99);
        settings.setScalpNumSkinWeightsPerVertex(3);

        Assert.assertEquals(1, settings.countMaterials());
        Assert.assertEquals(0, settings.countRenderStrands());
        Assert.assertEquals(0, settings.countScalpTriangles());
        Assert.assertEquals(0, settings.countScalpVertices());
        Assert.assertEquals(0, settings.countSimStrands());
        TestUtils.assertEquals(
                1f, 2f, 3f, settings.getInitialGravity(), 0f);
        Assert.assertEquals(99, settings.getNumIterationsPerSecond());
        Assert.assertEquals(3, settings.getScalpNumSkinWeightsPerVertex());

        ConstAaBox bounds = settings.getSimulationBounds();
        TestUtils.assertEquals(0f, 0f, 0f, bounds.getCenter(), 0f);
        TestUtils.assertEquals(1f, 1f, 1f, bounds.getExtent(), 0f);

        TestUtils.testClose(material);
    }

    /**
     * Test the getters and defaults of the specified {@code RStrand}.
     *
     * @param strand the material to test (not {@code null}, unaffected)
     */
    private static void testRStrandDefaults(RStrand strand) {
        Assert.assertTrue(strand.hasAssignedNativeObject());
        Assert.assertTrue(strand.ownsNativeObject());

        Assert.assertEquals(0, strand.getEndVtx());
        Assert.assertEquals(0, strand.getStartVtx());
        Assert.assertEquals(0, strand.vertexCount());
    }

    /**
     * Test the getters and defaults of the specified {@code SStrand}.
     *
     * @param strand the material to test (not {@code null}, unaffected)
     */
    private static void testSStrandDefaults(SStrand strand) {
        Assert.assertTrue(strand.hasAssignedNativeObject());
        Assert.assertTrue(strand.ownsNativeObject());

        Assert.assertEquals(0, strand.getEndVtx());
        Assert.assertEquals(0, strand.getMaterialIndex());
        Assert.assertEquals(0, strand.getStartVtx());
        Assert.assertEquals(0, strand.vertexCount());
    }

    /**
     * Test the getters and defaults of the specified {@code SVertex}.
     *
     * @param vertex the material to test (not {@code null}, unaffected)
     */
    private static void testSVertexDefaults(ConstSVertex vertex) {
        Assert.assertTrue(vertex.hasAssignedNativeObject());
        Assert.assertTrue(vertex.ownsNativeObject());

        TestUtils.assertEquals(0f, 0f, 0f, 1f, vertex.getBishop(), 0f);
        Assert.assertEquals(1f, vertex.getInvMass(), 0f);
        Assert.assertEquals(1f, vertex.getLength(), 1f);
        TestUtils.assertEquals(0f, 0f, 0f, 1f, vertex.getOmega0(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, vertex.getPosition(), 0f);
        Assert.assertEquals(0f, vertex.getStrandFraction(), 0f);
    }

    /**
     * Test the setters of the specified {@code SVertex}.
     *
     * @param vertex the material to test (not {@code null}, modified)
     */
    private static void testSVertexSetters(SVertex vertex) {
        vertex.setInvMass(4f);
        vertex.setPosition(new Float3(1f, 2f, 3f));

        TestUtils.assertEquals(0f, 0f, 0f, 1f, vertex.getBishop(), 0f);
        Assert.assertEquals(4f, vertex.getInvMass(), 0f);
        Assert.assertEquals(1f, vertex.getLength(), 1f);
        TestUtils.assertEquals(0f, 0f, 0f, 1f, vertex.getOmega0(), 0f);
        TestUtils.assertEquals(1f, 2f, 3f, vertex.getPosition(), 0f);
        Assert.assertEquals(0f, vertex.getStrandFraction(), 0f);
    }
}
