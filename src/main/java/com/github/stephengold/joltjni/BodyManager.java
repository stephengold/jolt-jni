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

import com.github.stephengold.joltjni.readonly.ConstBodyCreationSettings;
import com.github.stephengold.joltjni.readonly.ConstBodyId;
import com.github.stephengold.joltjni.readonly.ConstBroadPhaseLayerInterface;

/**
 * A container for bodies.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BodyManager extends NonCopyable {
    // *************************************************************************
    // fields

    /**
     * protect the BroadPhaseLayerInterface from garbage collection
     */
    private ConstBroadPhaseLayerInterface layerMap;
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default manager.
     */
    public BodyManager() {
        long managerVa = createBodyManager();
        setVirtualAddress(managerVa, true);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Activate the specified bodies.
     *
     * @param idArray the IDs of the bodies to activate (not null, unaffected)
     */
    public void activateBodies(ConstBodyId... idArray) {
        activateBodies(idArray, idArray.length);
    }

    /**
     * Activate the specified bodies.
     *
     * @param idArray the IDs of the bodies to activate (not null, unaffected)
     * @param number the number of IDs in the array (&ge;0)
     */
    public void activateBodies(ConstBodyId[] idArray, int number) {
        long managerVa = va();
        long[] idVas = new long[number];
        for (int i = 0; i < number; ++i) {
            idVas[i] = idArray[i].targetVa();
        }
        activateBodies(managerVa, idVas);
    }

    /**
     * Add a body, assigning the next available ID.
     *
     * @param body (not null, modified)
     * @return {@code true} if successful, {@code false} if no ID is available
     */
    public boolean addBody(Body body) {
        long managerVa = va();
        long bodyVa = body.va();
        boolean result = addBody(managerVa, bodyVa);

        return result;
    }

    /**
     * Create a body using the specified settings, but do not add it.
     *
     * @param settings the settings to use (not null, unaffected)
     * @return a new body, not added to any manager
     */
    public Body allocateBody(ConstBodyCreationSettings settings) {
        long managerVa = va();
        long settingsVa = settings.targetVa();
        long bodyVa = allocateBody(managerVa, settingsVa);
        Body result = new Body(this, bodyVa);

        return result;
    }

    /**
     * Deactivate the specified bodies.
     *
     * @param idArray the IDs of the bodies to deactivate (not null, unaffected)
     */
    public void deactivateBodies(ConstBodyId... idArray) {
        deactivateBodies(idArray, idArray.length);
    }

    /**
     * Deactivate the specified bodies.
     *
     * @param idArray the IDs of the bodies to deactivate (not null, unaffected)
     * @param number the number of IDs in the array (&ge;0)
     */
    public void deactivateBodies(ConstBodyId[] idArray, int number) {
        long managerVa = va();
        long[] idVas = new long[number];
        for (int i = 0; i < number; ++i) {
            idVas[i] = idArray[i].targetVa();
        }
        deactivateBodies(managerVa, idVas);
    }

    /**
     * Remove the specified bodies from the manager.
     *
     * @param idArray the IDs of the bodies to destroy (not null, unaffected)
     */
    public void destroyBodies(ConstBodyId... idArray) {
        destroyBodies(idArray, idArray.length);
    }

    /**
     * Remove the specified bodies from the manager.
     *
     * @param idArray the IDs of the bodies to destroy (not null, unaffected)
     * @param number the number of IDs in the array (&ge;0)
     */
    public void destroyBodies(ConstBodyId[] idArray, int number) {
        long managerVa = va();
        long[] idVas = new long[number];
        for (int i = 0; i < number; ++i) {
            idVas[i] = idArray[i].targetVa();
        }
        destroyBodies(managerVa, idVas);
    }

    /**
     * Draw the state of the bodies.
     *
     * @param drawSettings the draw settings to use (not null, unaffected)
     * @param physicsSettings the physics settings to use (not null, unaffected)
     * @param renderer the renderer to use (not null)
     */
    public void draw(BodyManagerDrawSettings drawSettings,
            PhysicsSettings physicsSettings, DebugRenderer renderer) {
        long managerVa = va();
        long drawSettingsVa = drawSettings.va();
        long physicsSettingsVa = physicsSettings.va();
        long rendererVa = renderer.va();
        draw(managerVa, drawSettingsVa, physicsSettingsVa, rendererVa);
    }

    /**
     * Return all bodies.
     *
     * @return a new vector (may contain invalid body pointers)
     */
    public BodyVector getBodies() {
        long managerVa = va();
        long vectorVa = getBodies(managerVa);
        BodyVector result = new BodyVector(this, vectorVa);

        return result;
    }

    /**
     * Access the (application-provided) interface for mapping object layers to
     * broadphase layers.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    public ConstBroadPhaseLayerInterface getBroadPhaseLayerInterface() {
        return layerMap;
    }

    /**
     * Return the maximum number of bodies the manager can support.
     *
     * @return the count (&ge;0)
     */
    public int getMaxBodies() {
        long managerVa = va();
        int result = getMaxBodies(managerVa);

        return result;
    }

    /**
     * Initialize the manager.
     *
     * @param maxBodies the desired maximum number of rigid bodies that can be
     * added
     * @param numBodyMutexes the desired number of mutexes to allocate, or 0 for
     * the default number
     * @param map the desired map from object layers to broad-phase layers (not
     * null, alias created)
     */
    public void init(int maxBodies, int numBodyMutexes,
            ConstBroadPhaseLayerInterface map) {
        this.layerMap = map;
        long managerVa = va();
        long mapVa = map.targetVa();
        init(managerVa, maxBodies, numBodyMutexes, mapVa);
    }
    // *************************************************************************
    // protected methods

    /**
     * Assign a native object, assuming there's none already assigned.
     *
     * @param managerVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    final void setVirtualAddress(long managerVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(managerVa) : null;
        setVirtualAddress(managerVa, freeingAction);
    }
    // *************************************************************************
    // native private methods

    native private static void activateBodies(long managerVa, long[] idVas);

    native private static boolean addBody(long managerVa, long bodyVa);

    native private static long allocateBody(long managerVa, long settingsVa);

    native private static long createBodyManager();

    native private static void deactivateBodies(long managerVa, long[] idVas);

    native private static void destroyBodies(long managerVa, long[] idVas);

    native private static void draw(long managerVa, long drawSettingsVa,
            long physicsSettingsVa, long rendererVa);

    native private static void free(long managerVa);

    native private static long getBodies(long managerVa);

    native private static int getMaxBodies(long managerVa);

    native private static void init(
            long managerVa, int maxBodies, int numBodyMutexes, long mapVa);
}
