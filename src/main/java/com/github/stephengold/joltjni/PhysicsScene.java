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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.readonly.ConstBodyCreationSettings;
import com.github.stephengold.joltjni.readonly.ConstSoftBodyCreationSettings;
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
     * Instantiate an empty scene.
     */
    public PhysicsScene() {
        long sceneVa = createDefault();
        long refVa = toRef(sceneVa);
        Runnable freeingAction = () -> PhysicsSceneRef.free(refVa);
        setVirtualAddress(sceneVa, freeingAction);
    }

    /**
     * Instantiate a scene with the specified native object assigned.
     *
     * @param sceneVa the virtual address of the native object to assign (not
     * zero)
     */
    PhysicsScene(long sceneVa) {
        long refVa = toRef(sceneVa);
        Runnable freeingAction = () -> PhysicsSceneRef.free(refVa);
        setVirtualAddress(sceneVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Add the specified body to the scene.
     *
     * @param body the body settings (not {@code null}, unaffected)
     */
    public void addBody(ConstBodyCreationSettings body) {
        long sceneVa = va();
        long bodyVa = body.targetVa();
        addBody(sceneVa, bodyVa);
    }

    /**
     * Add the specified constraint to the scene.
     *
     * @param constraint the constraint settings (not {@code null}, unaffected)
     * @param body1 the index of the first body in the bodies list
     * @param body2 the index of the 2nd body in the bodies list
     */
    public void addConstraint(
            TwoBodyConstraintSettings constraint, int body1, int body2) {
        long sceneVa = va();
        long constraintVa = constraint.va();
        addConstraint(sceneVa, constraintVa, body1, body2);
    }

    /**
     * Add the specified soft body to the scene.
     *
     * @param softBody the soft-body settings (not {@code null}, unaffected)
     */
    public void addSoftBody(ConstSoftBodyCreationSettings softBody) {
        long sceneVa = va();
        long bodyVa = softBody.targetVa();
        addSoftBody(sceneVa, bodyVa);
    }

    /**
     * Instantiate the bodies in the scene.
     *
     * @param system where to add the bodies (not {@code null}, modified)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public boolean createBodies(PhysicsSystem system) {
        long sceneVa = va();
        long systemVa = system.va();
        boolean result = createBodies(sceneVa, systemVa);

        return result;
    }

    /**
     * Correct any incorrectly scaled shapes in the scene.
     *
     * @return {@code true} if successful, otherwise {@code false}
     */
    public boolean fixInvalidScales() {
        long sceneVa = va();
        boolean result = fixInvalidScales(sceneVa);

        return result;
    }

    /**
     * Load the current state of the specified physics system.
     *
     * @param system the physics system to load from (not {@code null},
     * unaffected)
     */
    public void fromPhysicsSystem(PhysicsSystem system) {
        long sceneVa = va();
        long systemVa = system.va();
        fromPhysicsSystem(sceneVa, systemVa);
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
            result[bodyIndex] = new BodyCreationSettings(this, settingsVa);
        }

        return result;
    }

    /**
     * Access the soft-body creation settings as a Java array.
     *
     * @return a new array of new JVM objects with pre-existing native objects
     * assigned
     */
    public SoftBodyCreationSettings[] getSoftBodies() {
        long sceneVa = va();
        int numBodies = getNumSoftBodies(sceneVa);
        SoftBodyCreationSettings[] result
                = new SoftBodyCreationSettings[numBodies];
        for (int sbIndex = 0; sbIndex < numBodies; ++sbIndex) {
            long settingsVa = getSoftBody(sceneVa, sbIndex);
            result[sbIndex] = new SoftBodyCreationSettings(this, settingsVa);
        }

        return result;
    }

    /**
     * Save the scene to the specified binary stream. The scene is unaffected.
     *
     * @param stream the stream to write to (not {@code null})
     * @param saveShapes if true, save the shapes
     * @param saveGroupFilter if true, save the group filter
     */
    public void saveBinaryState(
            StreamOut stream, boolean saveShapes, boolean saveGroupFilter) {
        long sceneVa = va();
        long streamVa = stream.va();
        saveBinaryState(sceneVa, streamVa, saveShapes, saveGroupFilter);
    }

    /**
     * Attempt to de-serialize a saved scene from the specified stream.
     *
     * @param stream the stream to read (not {@code null})
     * @return a new object
     */
    public static PhysicsSceneResult sRestoreFromBinaryState(StreamIn stream) {
        long streamVa = stream.va();
        long resultVa = sRestoreFromBinaryState(streamVa);
        PhysicsSceneResult result = new PhysicsSceneResult(resultVa, true);

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
     * Mark the native {@code PhysicsScene} as embedded.
     */
    @Override
    public void setEmbedded() {
        long sceneVa = va();
        setEmbedded(sceneVa);
    }

    /**
     * Create a counted reference to the native {@code PhysicsScene}.
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

    native static void addBody(long sceneVa, long bodyVa);

    native static void addConstraint(
            long sceneVa, long constraintVa, int body1, int body2);

    native static void addSoftBody(long sceneVa, long bodyVa);

    native static boolean createBodies(long sceneVa, long systemVa);

    native private static long createDefault();

    native static boolean fixInvalidScales(long sceneVa);

    native static void fromPhysicsSystem(long sceneVa, long systemVa);

    native static long getBody(long sceneVa, int bodyIndex);

    native static long getSoftBody(long sceneVa, int sbIndex);

    native static int getNumBodies(long sceneVa);

    native static int getNumSoftBodies(long sceneVa);

    native private static int getRefCount(long sceneVa);

    native static void saveBinaryState(long sceneVa, long streamVa,
            boolean saveShapes, boolean saveGroupFilter);

    native private static void setEmbedded(long sceneVa);

    native private static long sRestoreFromBinaryState(long streamVa);

    native private static long toRef(long sceneVa);
}
