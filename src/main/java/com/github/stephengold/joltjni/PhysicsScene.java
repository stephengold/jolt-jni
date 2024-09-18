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

import com.github.stephengold.joltjni.template.RefTarget;

/**
 * The creation settings of a set of bodies.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class PhysicsScene extends JoltPhysicsObject implements RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a state with the specified native object assigned but not
     * owned.
     *
     * @param sceneVa the virtual address of the native object to assign (not
     * zero)
     */
    PhysicsScene(long sceneVa) {
        super(sceneVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Correct any incorrectly scaled shapes in the scene.
     *
     * @return true if successful, otherwise false
     */
    public boolean fixInvalidScales() {
        long sceneVa = va();
        boolean result = fixInvalidScales(sceneVa);

        return result;
    }

    /**
     * Access the body-creation settings as a Java array.
     *
     * @return a new array of new JVM objects with the pre-existing native
     * objects assigned
     */
    public BodyCreationSettings[] getBodies() {
        long sceneVa = va();
        int numBodies = getNumBodies(sceneVa);
        BodyCreationSettings[] result = new BodyCreationSettings[numBodies];
        for (int bodyIndex = 0; bodyIndex < numBodies; ++bodyIndex) {
            long settingsVa = getBody(sceneVa, bodyIndex);
            result[bodyIndex] = new BodyCreationSettings(settingsVa, false);
        }

        return result;
    }

    /**
     * Instantiate the bodies in the scene.
     *
     * @param system where to add the bodies (not null, modified)
     * @return true if successful, otherwise false
     */
    public boolean createBodies(PhysicsSystem system) {
        long sceneVa = va();
        long systemVa = system.va();
        boolean result = createBodies(sceneVa, systemVa);

        return result;
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code PhysicsScene}. The scene
     * is unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long sceneVa = va();
        int result = getRefCount(sceneVa);

        return result;
    }

    /**
     * Create a counted reference to the scene.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public PhysicsSceneRef toRef() {
        long sceneVa = va();
        long refVa = toRef(sceneVa);
        PhysicsSceneRef result = new PhysicsSceneRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native static boolean createBodies(long sceneVa, long systemVa);

    native static boolean fixInvalidScales(long sceneVa);

    native static long getBody(long sceneVa, int bodyIndex);

    native static int getNumBodies(long sceneVa);

    native private static int getRefCount(long sceneVa);

    native private static long toRef(long sceneVa);
}
