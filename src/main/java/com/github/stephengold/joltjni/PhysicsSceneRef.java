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

import com.github.stephengold.joltjni.template.Ref;

/**
 * A counted reference to a {@code PhysicsScene}. (native type:
 * {@code Ref<PhysicsScene>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class PhysicsSceneRef extends Ref {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public PhysicsSceneRef() {
        long refVa = createEmpty();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    PhysicsSceneRef(long refVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(refVa) : null;
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Correct any incorrectly scaled shapes in the scene.
     *
     * @return {@code true} if successful, otherwise {@code false}
     */
    public boolean fixInvalidScales() {
        long refVa = va();
        long sceneVa = getPtr(refVa);
        boolean result = PhysicsScene.fixInvalidScales(sceneVa);

        return result;
    }

    /**
     * Access the body-creation settings as a Java array.
     *
     * @return a new array of new JVM objects with the pre-existing native
     * objects assigned
     */
    public BodyCreationSettings[] getBodies() {
        long refVa = va();
        long sceneVa = getPtr(refVa);
        int numBodies = PhysicsScene.getNumBodies(sceneVa);
        BodyCreationSettings[] result = new BodyCreationSettings[numBodies];
        for (int bodyIndex = 0; bodyIndex < numBodies; ++bodyIndex) {
            long settingsVa = PhysicsScene.getBody(sceneVa, bodyIndex);
            result[bodyIndex] = new BodyCreationSettings(this, settingsVa);
        }

        return result;
    }

    /**
     * Instantiate the bodies in the scene.
     *
     * @param system where to add the bodies (not null, modified)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public boolean createBodies(PhysicsSystem system) {
        long refVa = va();
        long sceneVa = getPtr(refVa);
        long systemVa = system.va();
        boolean result = PhysicsScene.createBodies(sceneVa, systemVa);

        return result;
    }
    // *************************************************************************
    // Ref methods

    /**
     * Temporarily access the referenced {@code PhysicsScene}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public PhysicsScene getPtr() {
        long refVa = va();
        long sceneVa = getPtr(refVa);
        PhysicsScene result = new PhysicsScene(sceneVa);

        return result;
    }

    /**
     * Create a counted reference to the native {@code PhysicsSceneRef}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public PhysicsSceneRef toRef() {
        long refVa = va();
        long copyVa = copy(refVa);
        PhysicsSceneRef result = new PhysicsSceneRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long copy(long refVa);

    native private static long createEmpty();

    native private static void free(long refVa);

    native private static long getPtr(long refVa);
}
