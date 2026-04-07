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
        long refVa = createDefault();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     */
    PhysicsSceneRef(long refVa) {
        assert getPtr(refVa) == 0L;

        Runnable freeingAction = () -> free(refVa);
        setVirtualAddress(refVa, freeingAction);
    }

    /**
     * Instantiate a counted reference to the specified scene.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param scene the scene to target (not {@code null})
     */
    PhysicsSceneRef(long refVa, PhysicsScene scene) {
        assert scene != null;

        this.ptr = scene;
        Runnable freeingAction = () -> free(refVa);
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Add the specified body to the scene.
     *
     * @param body the body settings (not null, unaffected)
     */
    public void addBody(ConstBodyCreationSettings body) {
        long sceneVa = targetVa();
        long bodyVa = body.targetVa();
        PhysicsScene.addBody(sceneVa, bodyVa);
    }

    /**
     * Add the specified constraint to the scene.
     *
     * @param constraint the constraint settings (not null, unaffected)
     * @param body1 the index of the first body in the bodies list
     * @param body2 the index of the 2nd body in the bodies list
     */
    public void addConstraint(
            TwoBodyConstraintSettings constraint, int body1, int body2) {
        long sceneVa = targetVa();
        long constraintVa = constraint.va();
        PhysicsScene.addConstraint(sceneVa, constraintVa, body1, body2);
    }

    /**
     * Add the specified soft body to the scene.
     *
     * @param softBody the soft-body settings (not null, unaffected)
     */
    public void addSoftBody(SoftBodyCreationSettings softBody) {
        long sceneVa = targetVa();
        long bodyVa = softBody.va();
        PhysicsScene.addSoftBody(sceneVa, bodyVa);
    }

    /**
     * Instantiate the bodies in the scene.
     *
     * @param system where to add the bodies (not null, modified)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public boolean createBodies(PhysicsSystem system) {
        long sceneVa = targetVa();
        long systemVa = system.va();
        boolean result = PhysicsScene.createBodies(sceneVa, systemVa);

        return result;
    }

    /**
     * Correct any incorrectly scaled shapes in the scene.
     *
     * @return {@code true} if successful, otherwise {@code false}
     */
    public boolean fixInvalidScales() {
        long sceneVa = targetVa();
        boolean result = PhysicsScene.fixInvalidScales(sceneVa);

        return result;
    }

    /**
     * Load the current state of the specified physics system.
     *
     * @param system the physics system to load from (not null, unaffected)
     */
    public void fromPhysicsSystem(PhysicsSystem system) {
        long sceneVa = targetVa();
        long systemVa = system.va();
        PhysicsScene.fromPhysicsSystem(sceneVa, systemVa);
    }

    /**
     * Access the body-creation settings as a Java array.
     *
     * @return a new array of new JVM objects with the pre-existing native
     * objects assigned
     */
    public BodyCreationSettings[] getBodies() {
        long sceneVa = targetVa();
        int numBodies = PhysicsScene.getNumBodies(sceneVa);
        BodyCreationSettings[] result = new BodyCreationSettings[numBodies];
        for (int bodyIndex = 0; bodyIndex < numBodies; ++bodyIndex) {
            long settingsVa = PhysicsScene.getBody(sceneVa, bodyIndex);
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
        long sceneVa = targetVa();
        int numBodies = PhysicsScene.getNumSoftBodies(sceneVa);
        SoftBodyCreationSettings[] result
                = new SoftBodyCreationSettings[numBodies];
        for (int sbIndex = 0; sbIndex < numBodies; ++sbIndex) {
            long settingsVa = PhysicsScene.getSoftBody(sceneVa, sbIndex);
            result[sbIndex] = new SoftBodyCreationSettings(this, settingsVa);
        }

        return result;
    }

    /**
     * Write the scene to the specified binary stream.
     *
     * @param stream the stream to write to (not null)
     * @param saveShapes if true, save the shapes
     * @param saveGroupFilter if true, save the group filter
     */
    public void saveBinaryState(
            StreamOut stream, boolean saveShapes, boolean saveGroupFilter) {
        long sceneVa = targetVa();
        long streamVa = stream.va();
        PhysicsScene.saveBinaryState(
                sceneVa, streamVa, saveShapes, saveGroupFilter);
    }
    // *************************************************************************
    // new protected methods

    /**
     * Update the cached target.
     */
    void updatePtr() {
        long refVa = va();
        long targetVa = getPtr(refVa);
        if (targetVa == 0L) {
            this.ptr = null;
        } else {
            this.ptr = new PhysicsScene(targetVa);
        }
    }
    // *************************************************************************
    // Ref methods

    /**
     * Access the target scene, if any.
     *
     * @return the pre-existing object, or {@code null} if the reference is
     * empty
     */
    @Override
    public PhysicsScene getPtr() {
        PhysicsScene result = (PhysicsScene) ptr;
        return result;
    }

    /**
     * Return the address of the native {@code PhysicsScene}. No objects are
     * affected.
     *
     * @return the virtual address, or zero if the reference is empty
     */
    @Override
    public long targetVa() {
        long refVa = va();
        long result = getPtr(refVa);

        return result;
    }

    /**
     * Create an additional counted reference to the targeted scene.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public PhysicsSceneRef toRef() {
        PhysicsSceneRef result;
        if (ptr == null) {
            result = new PhysicsSceneRef();
        } else {
            long refVa = va();
            long copyVa = copy(refVa);
            PhysicsScene scene = (PhysicsScene) ptr;
            result = new PhysicsSceneRef(copyVa, scene);
        }

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long copy(long refVa);

    native private static long createDefault();

    native static void free(long refVa);

    native static long getPtr(long refVa);
}
