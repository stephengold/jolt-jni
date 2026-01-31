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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.readonly.ConstDrawSettings;
import com.github.stephengold.joltjni.readonly.ConstHairSettings;
import com.github.stephengold.joltjni.readonly.ConstPhysicsSystem;
import com.github.stephengold.joltjni.readonly.Mat44Arg;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import java.nio.FloatBuffer;

/**
 * An instance of hair simulation.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Hair extends NonCopyable {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a simulation with the specified settings.
     *
     * @param settings the settings to use (not {@code null}, unaffected)
     * @param location the location to use (not {@code null}, unaffected)
     * @param orientation the orientation to use (not {@code null}, unaffected)
     * @param objectLayer the object layer for the simulation
     */
    public Hair(ConstHairSettings settings, RVec3Arg location,
            QuatArg orientation, int objectLayer) {
        long settingsVa = settings.targetVa();
        double xx = location.xx();
        double yy = location.yy();
        double zz = location.zz();
        float qw = orientation.getW();
        float qx = orientation.getX();
        float qy = orientation.getY();
        float qz = orientation.getZ();
        long hairVa
                = create(settingsVa, xx, yy, zz, qx, qy, qz, qw, objectLayer);
        Runnable freeingAction = () -> free(hairVa);
        setVirtualAddress(hairVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Draw the hair and its simulation properties.
     *
     * @param settings the draw settings to use (not {@code null}, unaffected)
     * @param renderer the renderer to use (not {@code null})
     */
    public void draw(ConstDrawSettings settings, DebugRenderer renderer) {
        long hairVa = va();
        long settingsVa = settings.targetVa();
        long rendererVa = renderer.va();
        draw(hairVa, settingsVa, rendererVa);
    }

    /**
     * Access the settings used to create this hair.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public ConstHairSettings getHairSettings() {
        long hairVa = va();
        long resultVa = getHairSettings(hairVa);
        ConstHairSettings result = new HairSettings(resultVa);

        return result;
    }

    /**
     * Copy the render positions of all strands.
     *
     * @return a new direct buffer
     */
    public FloatBuffer getRenderPositions() {
        long hairVa = va();
        long settingsVa = getHairSettings(hairVa);
        int numFloats = 3 * HairSettings.countRenderVertices(settingsVa);
        FloatBuffer result = Jolt.newDirectFloatBuffer(numFloats);
        getRenderPositions(hairVa, result);

        return result;
    }

    /**
     * Copy the simulation results after {@code readBackGpuState()} has been
     * invoked and the buffers have been locked.
     *
     * @return a new direct buffer
     */
    public FloatBuffer getScalpVertices() {
        long hairVa = va();
        long settingsVa = getHairSettings(hairVa);
        int numFloats = 3 * HairSettings.countScalpVertices(settingsVa);
        FloatBuffer result = Jolt.newDirectFloatBuffer(numFloats);
        getScalpVertices(hairVa, result);

        return result;
    }

    /**
     * Copy the simulation's transform relative to system coordinates.
     *
     * @return a new matrix
     */
    public RMat44 getWorldTransform() {
        long hairVa = va();
        long resultVa = getWorldTransform(hairVa);
        RMat44 result = new RMat44(resultVa, true);

        return result;
    }

    /**
     * Initialize the simulation.
     *
     * @param computeSystem the compute system to use (not {@code null})
     */
    public void init(ComputeSystem computeSystem) {
        long hairVa = va();
        long systemVa = computeSystem.va();
        init(hairVa, systemVa);
    }

    /**
     * Lock the data buffers.
     */
    public void lockReadBackBuffers() {
        long hairVa = va();
        lockReadBackBuffers(hairVa);
    }

    /**
     * Read back GPU state for debugging purposes.
     *
     * @param queue the queue to use (not {@code null})
     */
    public void readBackGpuState(ComputeQueue queue) {
        long hairVa = va();
        long queueVa = queue.va();
        readBackGpuState(hairVa, queueVa);
    }

    /**
     * Relocate the hair.
     *
     * @param location the desired location (in system coordinates, not
     * {@code null}, unaffected)
     */
    public void setPosition(RVec3Arg location) {
        long hairVa = va();
        double xx = location.xx();
        double yy = location.yy();
        double zz = location.zz();
        setPosition(hairVa, xx, yy, zz);
    }

    /**
     * Reorient the hair.
     *
     * @param orientation the desired orientation (relative to system
     * coordinates, not {@code null}, unaffected)
     */
    public void setRotation(QuatArg orientation) {
        long hairVa = va();
        float x = orientation.getX();
        float y = orientation.getY();
        float z = orientation.getZ();
        float w = orientation.getW();
        setRotation(hairVa, x, y, z, w);
    }

    /**
     * Unlock the data buffers.
     */
    public void unlockReadBackBuffers() {
        long hairVa = va();
        unlockReadBackBuffers(hairVa);
    }

    /**
     * Step the hair simulation forward.
     *
     * @param deltaTime the time step
     * @param jointToHair the transform from joint space to hair local space
     * (not {@code null}, unaffected)
     * @param jointMatrices the joint matrices (in system coordinates,
     * unaffected) or {@code null} if the scalp isn't skinned
     * @param physicsSystem the physics system to use for collision detection
     * (not {@code null})
     * @param shaders the compute shaders to use (not {@code null})
     * @param computeSystem the compute system to use (not {@code null})
     * @param queue the compute queue to use (not {@code null})
     */
    public void update(float deltaTime, Mat44Arg jointToHair,
            Mat44Array jointMatrices, ConstPhysicsSystem physicsSystem,
            HairShaders shaders, ComputeSystem computeSystem,
            ComputeQueue queue) {
        long hairVa = va();
        long jointToHairVa = jointToHair.targetVa();
        long jointMatricesVa
                = (jointMatrices == null) ? 0L : jointMatrices.va();
        long physicsSystemVa = physicsSystem.targetVa();
        long shadersVa = shaders.va();
        long computeSystemVa = computeSystem.va();
        long queueVa = queue.va();
        update(hairVa, deltaTime, jointToHairVa, jointMatricesVa,
                physicsSystemVa, shadersVa, computeSystemVa, queueVa);
    }
    // *************************************************************************
    // native private methods

    native private static long create(long settingsVa, double xx, double yy,
            double zz, float qx, float qy, float qz, float qw, int objectLayer);

    native private static void draw(
            long hairVa, long settingsVa, long rendererVa);

    native private static void free(long hairVa);

    native private static long getHairSettings(long hairVa);

    native private static void getRenderPositions(
            long hairVa, FloatBuffer storeFloats);

    native private static void getScalpVertices(
            long hairVa, FloatBuffer storeFloats);

    native private static long getWorldTransform(long hairVa);

    native private static void init(long hairVa, long systemVa);

    native private static void lockReadBackBuffers(long hairVa);

    native private static void readBackGpuState(long hairVa, long queueVa);

    native private static void setPosition(
            long hairVa, double xx, double yy, double zz);

    native private static void setRotation(
            long hairVa, float x, float y, float z, float w);

    native private static void unlockReadBackBuffers(long hairVa);

    native private static void update(long hairVa, float deltaTime,
            long jointToHairVa, long jointMatricesVa, long physicsSystemVa,
            long shadersVa, long computeSystemVa, long queueVa);
}
