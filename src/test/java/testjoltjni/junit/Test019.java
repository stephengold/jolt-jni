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

import com.github.stephengold.joltjni.CapsuleShape;
import com.github.stephengold.joltjni.CharacterContactKey;
import com.github.stephengold.joltjni.CharacterRef;
import com.github.stephengold.joltjni.CharacterRefC;
import com.github.stephengold.joltjni.CharacterSettings;
import com.github.stephengold.joltjni.CharacterVirtual;
import com.github.stephengold.joltjni.CharacterVirtualRef;
import com.github.stephengold.joltjni.CharacterVirtualRefC;
import com.github.stephengold.joltjni.CharacterVirtualSettings;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.PhysicsSystem;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.readonly.ConstCharacter;
import com.github.stephengold.joltjni.readonly.ConstCharacterContactKey;
import com.github.stephengold.joltjni.readonly.ConstCharacterVirtual;
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
        doCharacterVirtual();
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
