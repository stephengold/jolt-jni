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

/**
 * A container for bodies.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BodyManager extends NonCopyable {
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
            idVas[i] = idArray[i].va();
        }
        activateBodies(managerVa, idVas);
    }

    /**
     * Add a body, assigning the next available ID.
     *
     * @param body (not null, modified)
     * @return true if successful, false if no ID is available
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
        long settingsVa = settings.va();
        long bodyVa = allocateBody(managerVa, settingsVa);
        Body result = new Body(bodyVa);

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
            idVas[i] = idArray[i].va();
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
            idVas[i] = idArray[i].va();
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
     * Return the maximum number of bodies the manager can support.
     *
     * @return the count (&ge;0)
     */
    public int getMaxBodies() {
        long managerVa = va();
        int result = getMaxBodies(managerVa);

        return result;
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

    native private static long getBodies(long managerVa);

    native private static int getMaxBodies(long managerVa);
}
