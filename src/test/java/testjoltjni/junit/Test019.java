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

import com.github.stephengold.joltjni.BoxShape;
import com.github.stephengold.joltjni.CapsuleShape;
import com.github.stephengold.joltjni.CharacterBaseSettings;
import com.github.stephengold.joltjni.CharacterContactKey;
import com.github.stephengold.joltjni.CharacterRef;
import com.github.stephengold.joltjni.CharacterRefC;
import com.github.stephengold.joltjni.CharacterSettings;
import com.github.stephengold.joltjni.CharacterSettingsRef;
import com.github.stephengold.joltjni.CharacterVirtual;
import com.github.stephengold.joltjni.CharacterVirtualRef;
import com.github.stephengold.joltjni.CharacterVirtualRefC;
import com.github.stephengold.joltjni.CharacterVirtualSettings;
import com.github.stephengold.joltjni.CharacterVirtualSettingsRef;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.JphMath;
import com.github.stephengold.joltjni.PhysicsSystem;
import com.github.stephengold.joltjni.Plane;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.EBackFaceMode;
import com.github.stephengold.joltjni.readonly.ConstCharacter;
import com.github.stephengold.joltjni.readonly.ConstCharacterBaseSettings;
import com.github.stephengold.joltjni.readonly.ConstCharacterContactKey;
import com.github.stephengold.joltjni.readonly.ConstCharacterSettings;
import com.github.stephengold.joltjni.readonly.ConstCharacterVirtual;
import com.github.stephengold.joltjni.readonly.ConstCharacterVirtualSettings;
import com.github.stephengold.joltjni.readonly.ConstShape;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Automated JUnit4 tests for creation, destruction, accessors, and defaults of
 * character-related classes.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test019 {
    // *************************************************************************
    // new methods exposed

    /**
     * Test object creation, destruction, accessors, and defaults.
     */
    @Test
    public void test019() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        doCharacter();
        doCharacterSettings();
        doCharacterVirtual();
        doCharacterVirtualSettings();
        doContactKey();

        TestUtils.cleanup();
    }
    // *************************************************************************
    // Java private methods

    /**
     * Test the {@code Character} class.
     */
    private static void doCharacter() {
        float radius = 1f; // meters
        float height = 2f; // meters
        ConstShape shape = new CapsuleShape(height / 2f, radius);

        CharacterSettings settings = new CharacterSettings();
        settings.setShape(shape);

        int maxBodies = 1;
        PhysicsSystem system = TestUtils.newPhysicsSystem(maxBodies);

        com.github.stephengold.joltjni.Character character
                = new com.github.stephengold.joltjni.Character(
                        settings, new RVec3(), new Quat(), 0L, system);
        final CharacterRef characterRef = character.toRef();
        final CharacterRefC characterRefC = character.toRefC();

        testCharacterDefaults(character);
        testCharacterDefaults(characterRef);
        testCharacterDefaults(characterRefC);

        TestUtils.testClose(characterRefC, characterRef, character);
        TestUtils.cleanupPhysicsSystem(system);
        TestUtils.testClose(settings, shape);
        System.gc();
    }

    /**
     * Test the {@code CharacterSettings} class.
     */
    private static void doCharacterSettings() {
        CharacterSettings settings = new CharacterSettings();
        testCharacterSettingsDefaults(settings);
        CharacterSettingsRef ref = settings.toRef();
        testCharacterSettingsDefaults(ref);

        CharacterSettings copy = new CharacterSettings(settings);
        testCharacterSettingsSetters(settings);
        testCharacterSettingsDefaults(copy);
        settings.set(copy);
        testCharacterSettingsDefaults(settings);

        TestUtils.testClose(copy, ref, settings);
        System.gc();
    }

    /**
     * Test the {@code CharacterVirtual} class.
     */
    private static void doCharacterVirtual() {
        CharacterVirtualSettings settings = new CharacterVirtualSettings();

        int maxBodies = 1;
        PhysicsSystem system = TestUtils.newPhysicsSystem(maxBodies);

        CharacterVirtual character = new CharacterVirtual(
                settings, new RVec3(), new Quat(), 0L, system);
        final CharacterVirtualRef characterRef = character.toRef();
        final CharacterVirtualRefC characterRefC = character.toRefC();

        testCharacterVirtualDefaults(character);
        testCharacterVirtualDefaults(characterRef);
        testCharacterVirtualDefaults(characterRefC);

        TestUtils.testClose(characterRefC, characterRef, character);
        TestUtils.cleanupPhysicsSystem(system);
        TestUtils.testClose(settings);
        System.gc();
    }

    /**
     * Test the {@code CharacterVirtualSettings} class.
     */
    private static void doCharacterVirtualSettings() {
        CharacterVirtualSettings settings = new CharacterVirtualSettings();
        testCharacterVirtualSettingsDefaults(settings);
        CharacterVirtualSettingsRef ref = settings.toRef();
        testCharacterVirtualSettingsDefaults(ref);

        CharacterVirtualSettings copy = new CharacterVirtualSettings(settings);
        testCharacterVirtualSettingsSetters(settings);
        testCharacterVirtualSettingsDefaults(copy);
        settings.set(copy);
        testCharacterVirtualSettingsDefaults(settings);

        TestUtils.testClose(copy, ref, settings);
        System.gc();
    }

    /**
     * Test the {@code CharacterContactKey} class.
     */
    private static void doContactKey() {
        CharacterContactKey key = new CharacterContactKey();
        testContactKeyDefaults(key);

        CharacterContactKey copy = new CharacterContactKey(key);
        testContactKeyDefaults(copy);

        TestUtils.testClose(copy, key);
        System.gc();
    }

    /**
     * Test the getters and defaults of the specified
     * {@code CharacterBaseSettings}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testCharacterBaseSettingsDefaults(
            ConstCharacterBaseSettings settings) {
        Assert.assertFalse(settings.getEnhancedInternalEdgeRemoval());
        Assert.assertEquals(
                5 * JphMath.JPH_PI / 18, settings.getMaxSlopeAngle(), 1e-7f);
        Assert.assertNull(settings.getShape());
        TestUtils.assertEquals(
                0f, 1f, 0f, -1e10f, settings.getSupportingVolume(), 0f);
        TestUtils.assertEquals(0f, 1f, 0f, settings.getUp(), 0f);
    }

    /**
     * Test the setters of the specified {@code CharacterBaseSettings}.
     *
     * @param settings the settings to test (not {@code null})
     */
    private static void testCharacterBaseSettingsSetters(
            CharacterBaseSettings settings) {
        settings.setEnhancedInternalEdgeRemoval(true);
        settings.setMaxSlopeAngle(0.3f);

        ConstShape shape = new BoxShape(1f);
        settings.setShape(shape);

        settings.setSupportingVolume(new Plane(0.6f, -0.8f, 0f, 2f));
        settings.setUp(new Vec3(0f, -0.6f, 0.8f));

        Assert.assertTrue(settings.getEnhancedInternalEdgeRemoval());
        Assert.assertEquals(0.3f, settings.getMaxSlopeAngle(), 0f);
        Assert.assertEquals(shape, settings.getShape());
        TestUtils.assertEquals(
                0.6f, -0.8f, 0f, 2f, settings.getSupportingVolume(), 0f);
        TestUtils.assertEquals(0f, -0.6f, 0.8f, settings.getUp(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code Character}.
     *
     * @param character the character to test (not {@code null}, unaffected)
     */
    private static void testCharacterDefaults(ConstCharacter character) {
        character.getCenterOfMassPosition();
        TestUtils.assertEquals(
                0f, 0f, 0f, character.getCenterOfMassPosition(), 0f);
        Assert.assertEquals(0, character.getLayer());
        TestUtils.assertEquals(0f, 0f, 0f, character.getLinearVelocity(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, character.getPosition(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, 1f, character.getRotation(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code CharacterSettings}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testCharacterSettingsDefaults(
            ConstCharacterSettings settings) {
        testCharacterBaseSettingsDefaults(settings);

        Assert.assertEquals(0.2f, settings.getFriction(), 0f);
        Assert.assertEquals(1f, settings.getGravityFactor(), 0f);
        Assert.assertEquals(0, settings.getLayer());
        Assert.assertEquals(80f, settings.getMass(), 0f);
    }

    /**
     * Test the setters of the specified {@code CharacterSettings}.
     *
     * @param settings the settings to test (not {@code null})
     */
    private static void testCharacterSettingsSetters(
            CharacterSettings settings) {
        settings.setFriction(0.7f);
        settings.setGravityFactor(0.17f);
        settings.setLayer(3);
        settings.setMass(110f);

        testCharacterBaseSettingsDefaults(settings);

        Assert.assertEquals(0.7f, settings.getFriction(), 0f);
        Assert.assertEquals(0.17f, settings.getGravityFactor(), 0f);
        Assert.assertEquals(3, settings.getLayer());
        Assert.assertEquals(110f, settings.getMass(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code CharacterVirtual}.
     *
     * @param character the character to test (not {@code null}, unaffected)
     */
    private static void testCharacterVirtualDefaults(
            ConstCharacterVirtual character) {
        Assert.assertFalse(character.getEnhancedInternalEdgeRemoval());
        Assert.assertEquals(1f, character.getHitReductionCosMaxAngle(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, character.getLinearVelocity(), 0f);
        Assert.assertEquals(70f, character.getMass(), 0f);
        Assert.assertEquals(256, character.getMaxNumHits());
        Assert.assertEquals(100f, character.getMaxStrength(), 0f);
        Assert.assertEquals(1f, character.getPenetrationRecoverySpeed(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, character.getPosition(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, 1f, character.getRotation(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, character.getShapeOffset(), 0f);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code CharacterVirtualSettings}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testCharacterVirtualSettingsDefaults(
            ConstCharacterVirtualSettings settings) {
        testCharacterBaseSettingsDefaults(settings);

        Assert.assertEquals(
                EBackFaceMode.CollideWithBackFaces, settings.getBackFaceMode());
        Assert.assertEquals(0.02f, settings.getCharacterPadding(), 0f);
        Assert.assertEquals(0.001f, settings.getCollisionTolerance(), 0f);
        Assert.assertEquals(0.999f, settings.getHitReductionCosMaxAngle(), 0f);
        Assert.assertEquals(0, settings.getInnerBodyLayer());
        Assert.assertNull(settings.getInnerBodyShape());
        Assert.assertEquals(70f, settings.getMass(), 0f);
        Assert.assertEquals(5, settings.getMaxCollisionIterations());
        Assert.assertEquals(15, settings.getMaxConstraintIterations());
        Assert.assertEquals(256, settings.getMaxNumHits());
        Assert.assertEquals(100f, settings.getMaxStrength(), 0f);
        Assert.assertEquals(0.0001f, settings.getMinTimeRemaining(), 0f);
        Assert.assertEquals(1f, settings.getPenetrationRecoverySpeed(), 0f);
        Assert.assertEquals(0.1f, settings.getPredictiveContactDistance(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getShapeOffset(), 0f);
    }

    /**
     * Test the setters of the specified {@code CharacterVirtualSettings}.
     *
     * @param settings the settings to test (not {@code null})
     */
    private static void testCharacterVirtualSettingsSetters(
            CharacterVirtualSettings settings) {
        settings.setBackFaceMode(EBackFaceMode.IgnoreBackFaces);
        settings.setCharacterPadding(0.05f);
        settings.setCollisionTolerance(0.004f);
        settings.setHitReductionCosMaxAngle(0.95f);
        settings.setInnerBodyLayer(5);

        ConstShape shape = new CapsuleShape(0.1f, 0.4f);
        settings.setInnerBodyShape(shape);

        settings.setMass(92f);
        settings.setMaxCollisionIterations(3);
        settings.setMaxConstraintIterations(9);
        settings.setMaxNumHits(12);
        settings.setMaxStrength(44f);
        settings.setMinTimeRemaining(0.02f);
        settings.setPenetrationRecoverySpeed(0.6f);
        settings.setPredictiveContactDistance(0.2f);
        settings.setShapeOffset(new Vec3(1f, -2f, 3f));

        testCharacterBaseSettingsSetters(settings);

        Assert.assertEquals(
                EBackFaceMode.IgnoreBackFaces, settings.getBackFaceMode());
        Assert.assertEquals(0.05f, settings.getCharacterPadding(), 0f);
        Assert.assertEquals(0.004f, settings.getCollisionTolerance(), 0f);
        Assert.assertEquals(0.95f, settings.getHitReductionCosMaxAngle(), 0f);
        Assert.assertEquals(5, settings.getInnerBodyLayer());
        Assert.assertEquals(shape, settings.getInnerBodyShape());
        Assert.assertEquals(92f, settings.getMass(), 0f);
        Assert.assertEquals(3, settings.getMaxCollisionIterations());
        Assert.assertEquals(9, settings.getMaxConstraintIterations());
        Assert.assertEquals(12, settings.getMaxNumHits());
        Assert.assertEquals(44f, settings.getMaxStrength(), 0f);
        Assert.assertEquals(0.02f, settings.getMinTimeRemaining(), 0f);
        Assert.assertEquals(0.6f, settings.getPenetrationRecoverySpeed(), 0f);
        Assert.assertEquals(0.2f, settings.getPredictiveContactDistance(), 0f);
        TestUtils.assertEquals(1f, -2f, 3f, settings.getShapeOffset(), 0f);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code CharacterContactKey}.
     *
     * @param key the key to test (not {@code null}, unaffected)
     */
    private static void testContactKeyDefaults(ConstCharacterContactKey key) {
        Assert.assertTrue(key.hasAssignedNativeObject());
        Assert.assertTrue(key.ownsNativeObject());

        Assert.assertEquals(Jolt.cInvalidBodyId, key.getBodyB());
        Assert.assertEquals(-1, key.getCharacterIdB());
        Assert.assertEquals(Jolt.cEmptySubShapeId, key.getSubShapeIdB());
        Assert.assertTrue(key.isEqual(key));
    }
}
