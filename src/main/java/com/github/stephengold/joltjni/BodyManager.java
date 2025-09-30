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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.readonly.ConstBodyCreationSettings;
import com.github.stephengold.joltjni.readonly.ConstBroadPhaseLayerInterface;
import com.github.stephengold.joltjni.readonly.ConstPhysicsSettings;

/**
 * A container for bodies.
 * <p>
 * This class is an internal part of {@code PhysicsSystem}, exposed for test
 * purposes only.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BodyManager extends NonCopyable {
    // *************************************************************************
    // fields

    /**
     * protect the {@code BroadPhaseLayerInterface} from garbage collection
     */
    private ConstBroadPhaseLayerInterface layerMap;
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default manager.
     */
    public BodyManager() {
        long managerVa = createDefault();
        setVirtualAddress(managerVa, () -> free(managerVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Activate the specified bodies.
     *
     * @param idArray the IDs of the bodies to activate (not null, unaffected)
     */
    public void activateBodies(int... idArray) {
        long managerVa = va();
        activateBodies(managerVa, idArray);
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
    public void deactivateBodies(int... idArray) {
        long managerVa = va();
        deactivateBodies(managerVa, idArray);
    }

    /**
     * Remove the specified bodies from the manager.
     *
     * @param idArray the IDs of the bodies to destroy (not null, unaffected)
     */
    public void destroyBodies(int... idArray) {
        long managerVa = va();
        destroyBodies(managerVa, idArray);
    }

    /**
     * Draw the state of the bodies.
     *
     * @param drawSettings the draw settings to use (not null, unaffected)
     * @param physicsSettings the physics settings to use (not null, unaffected)
     * @param renderer the renderer to use (not null)
     */
    public void draw(BodyManagerDrawSettings drawSettings,
            ConstPhysicsSettings physicsSettings, DebugRenderer renderer) {
        long managerVa = va();
        long drawSettingsVa = drawSettings.va();
        long physicsSettingsVa = physicsSettings.targetVa();
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
     * Access a body using its ID.
     *
     * @param bodyId the ID of the body to access
     * @return a new JVM object with the pre-existing native object assigned
     */
    public Body getBody(int bodyId) {
        long managerVa = va();
        long resultVa = getBody(managerVa, bodyId);
        Body result = new Body(this, resultVa);

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
    // native private methods

    native private static void activateBodies(long managerVa, int[] idArray);

    native private static boolean addBody(long managerVa, long bodyVa);

    native private static long allocateBody(long managerVa, long settingsVa);

    native private static long createDefault();

    native private static void deactivateBodies(long managerVa, int[] idArray);

    native private static void destroyBodies(long managerVa, int[] idArray);

    native private static void draw(long managerVa, long drawSettingsVa,
            long physicsSettingsVa, long rendererVa);

    native private static void free(long managerVa);

    native private static long getBodies(long managerVa);

    native private static long getBody(long managerVa, int bodyId);

    native private static int getMaxBodies(long managerVa);

    native private static void init(
            long managerVa, int maxBodies, int numBodyMutexes, long mapVa);
}
