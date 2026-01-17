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

import com.github.stephengold.joltjni.enumerate.ERenderStrandColor;
import com.github.stephengold.joltjni.readonly.ConstDrawSettings;

/**
 * Configure debug visualization of some hair. (native type:
 * {@code Hair::DrawSettings})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class DrawSettings
        extends JoltPhysicsObject
        implements ConstDrawSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate settings with the default values.
     */
    public DrawSettings() {
        long settingsVa = createDefault();
        setVirtualAddress(settingsVa, () -> free(settingsVa));
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public DrawSettings(ConstDrawSettings original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        Runnable freeingAction = () -> free(copyVa);
        setVirtualAddress(copyVa, freeingAction);
    }

    /**
     * Instantiate settings with the specified native object assigned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    DrawSettings(long settingsVa) {
        Runnable freeingAction = () -> free(settingsVa);
        setVirtualAddress(settingsVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Enable or disable visualization of grid density. (native attribute:
     * mDrawGridDensity)
     *
     * @param enable {@code true} to enable, or {@code false} to disable
     * (default=false)
     * @return the modified settings, for chaining
     */
    public DrawSettings setDrawGridDensity(boolean enable) {
        long settingsVa = va();
        setDrawGridDensity(settingsVa, enable);

        return this;
    }

    /**
     * Enable or disable visualization of grid velocity. (native attribute:
     * mDrawGridVelocity)
     *
     * @param enable {@code true} to enable, or {@code false} to disable
     * (default=false)
     * @return the modified settings, for chaining
     */
    public DrawSettings setDrawGridVelocity(boolean enable) {
        long settingsVa = va();
        setDrawGridVelocity(settingsVa, enable);

        return this;
    }

    /**
     * Enable or disable visualization of the initial gravity vector. (native
     * attribute: mDrawInitialGravity)
     *
     * @param enable {@code true} to enable, or {@code false} to disable
     * (default=true)
     * @return the modified settings, for chaining
     */
    public DrawSettings setDrawInitialGravity(boolean enable) {
        long settingsVa = va();
        setDrawInitialGravity(settingsVa, enable);

        return this;
    }

    /**
     * Enable or disable visualization of the grid density of the hair in its
     * neutral pose. (native attribute: mDrawNeutralDensity)
     *
     * @param enable {@code true} to enable, or {@code false} to disable
     * (default=false)
     * @return the modified settings, for chaining
     */
    public DrawSettings setDrawNeutralDensity(boolean enable) {
        long settingsVa = va();
        setDrawNeutralDensity(settingsVa, enable);

        return this;
    }

    /**
     * Enable or disable visualization of the coordinate axes of each simulated
     * vertex. (native attribute: mDrawOrientations)
     *
     * @param enable {@code true} to enable, or {@code false} to disable
     * (default=false)
     * @return the modified settings, for chaining
     */
    public DrawSettings setDrawOrientations(boolean enable) {
        long settingsVa = va();
        setDrawOrientations(settingsVa, enable);

        return this;
    }

    /**
     * Enable or disable debug visualization of the strands. (native attribute:
     * mDrawRenderStrands)
     *
     * @param enable {@code true} to enable, or {@code false} to disable
     * (default=false)
     * @return the modified settings, for chaining
     */
    public DrawSettings setDrawRenderStrands(boolean enable) {
        long settingsVa = va();
        setDrawRenderStrands(settingsVa, enable);

        return this;
    }

    /**
     * Enable or disable visualization of the simulated rods. (native attribute:
     * mDrawRods)
     *
     * @param enable {@code true} to enable, or {@code false} to disable
     * (default=true)
     * @return the modified settings, for chaining
     */
    public DrawSettings setDrawRods(boolean enable) {
        long settingsVa = va();
        setDrawRods(settingsVa, enable);

        return this;
    }

    /**
     * Enable or disable visualization of the skinning points on the scalp.
     * (native attribute: mDrawSkinPoints)
     *
     * @param enable {@code true} to enable, or {@code false} to disable
     * (default=false)
     * @return the modified settings, for chaining
     */
    public DrawSettings setDrawSkinPoints(boolean enable) {
        long settingsVa = va();
        setDrawSkinPoints(settingsVa, enable);

        return this;
    }

    /**
     * Enable or disable visualization of rods in their unloaded pose. (native
     * attribute: mDrawUnloadedRods)
     *
     * @param enable {@code true} to enable, or {@code false} to disable
     * (default=false)
     * @return the modified settings, for chaining
     */
    public DrawSettings setDrawUnloadedRods(boolean enable) {
        long settingsVa = va();
        setDrawUnloadedRods(settingsVa, enable);

        return this;
    }

    /**
     * Enable or disable visualization of vertex velocities. (native attribute:
     * mDrawVertexVelocity)
     *
     * @param enable {@code true} to enable, or {@code false} to disable
     * (default=false)
     * @return the modified settings, for chaining
     */
    public DrawSettings setDrawVertexVelocity(boolean enable) {
        long settingsVa = va();
        setDrawVertexVelocity(settingsVa, enable);

        return this;
    }

    /**
     * Configure colors for visualizing strands. (native attribute:
     * mRenderStrandColor)
     *
     * @param color the desired coloring option (not {@code null},
     * default=PerSimulatedStrand)
     * @return the modified settings, for chaining
     */
    public DrawSettings setRenderStrandColor(ERenderStrandColor color) {
        long settingsVa = va();
        int ordinal = color.ordinal();
        setRenderStrandColor(settingsVa, ordinal);

        return this;
    }

    /**
     * Configure the start of the range of strands to visualize. (native
     * attribute: mSimulationStrandBegin)
     *
     * @param strandIndex the desired strand index (&ge;0)
     * @return the modified settings, for chaining
     */
    public DrawSettings setSimulationStrandBegin(int strandIndex) {
        long settingsVa = va();
        setSimulationStrandBegin(settingsVa, strandIndex);

        return this;
    }

    /**
     * Configure the end of the range of strands to visualize. (native
     * attribute: mSimulationStrandEnd)
     *
     * @param strandIndex the desired strand index (&ge;0)
     * @return the modified settings, for chaining
     */
    public DrawSettings setSimulationStrandEnd(int strandIndex) {
        long settingsVa = va();
        setSimulationStrandEnd(settingsVa, strandIndex);

        return this;
    }
    // *************************************************************************
    // ConstHairSettings methods

    /**
     * Test whether to visualize grid density. The settings are unaffected.
     * (native attribute: mDrawGridDensity)
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    @Override
    public boolean getDrawGridDensity() {
        long settingsVa = va();
        boolean result = getDrawGridDensity(settingsVa);

        return result;
    }

    /**
     * Test whether to visualize grid velocity. The settings are unaffected.
     * (native attribute: mDrawGridVelocity)
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    @Override
    public boolean getDrawGridVelocity() {
        long settingsVa = va();
        boolean result = getDrawGridVelocity(settingsVa);

        return result;
    }

    /**
     * Test whether to visualize the initial gravity vector. The settings are
     * unaffected. (native attribute: mDrawInitialGravity)
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    @Override
    public boolean getDrawInitialGravity() {
        long settingsVa = va();
        boolean result = getDrawInitialGravity(settingsVa);

        return result;
    }

    /**
     * Test whether to visualize the grid density of the hair in its neutral
     * pose. The settings are unaffected. (native attribute:
     * mDrawNeutralDensity)
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    @Override
    public boolean getDrawNeutralDensity() {
        long settingsVa = va();
        boolean result = getDrawNeutralDensity(settingsVa);

        return result;
    }

    /**
     * Test whether to visualize the coordinate axes of each simulated vertex.
     * The settings are unaffected. (native attribute: mDrawOrientations)
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    @Override
    public boolean getDrawOrientations() {
        long settingsVa = va();
        boolean result = getDrawOrientations(settingsVa);

        return result;
    }

    /**
     * Test whether to visualize the strands. The settings are unaffected.
     * (native attribute: mDrawRenderStrands)
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    @Override
    public boolean getDrawRenderStrands() {
        long settingsVa = va();
        boolean result = getDrawRenderStrands(settingsVa);

        return result;
    }

    /**
     * Test whether to visualize the simulated rods. The settings are
     * unaffected. (native attribute: mDrawRods)
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    @Override
    public boolean getDrawRods() {
        long settingsVa = va();
        boolean result = getDrawRods(settingsVa);

        return result;
    }

    /**
     * Test whether to visualize the skinning points on the scalp. The settings
     * are unaffected. (native attribute: mDrawSkinPoints)
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    @Override
    public boolean getDrawSkinPoints() {
        long settingsVa = va();
        boolean result = getDrawSkinPoints(settingsVa);

        return result;
    }

    /**
     * Test whether to visualize rods in their unloaded pose. The settings are
     * unaffected. (native attribute: mDrawUnloadedRods)
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    @Override
    public boolean getDrawUnloadedRods() {
        long settingsVa = va();
        boolean result = getDrawUnloadedRods(settingsVa);

        return result;
    }

    /**
     * Test whether to visualize vertex velocities. The settings are unaffected.
     * (native attribute: mDrawVertexVelocity)
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    @Override
    public boolean getDrawVertexVelocity() {
        long settingsVa = va();
        boolean result = getDrawVertexVelocity(settingsVa);

        return result;
    }

    /**
     * Return the option for coloring strands. The settings are unaffected.
     * (native attribute: mRenderStrandColor)
     *
     * @return the enum value (not {@code null})
     */
    @Override
    public ERenderStrandColor getRenderStrandColor() {
        long settingsVa = va();
        int ordinal = getRenderStrandColor(settingsVa);
        ERenderStrandColor result = ERenderStrandColor.values()[ordinal];

        return result;
    }

    /**
     * Return the start of the range of strands to visualize. The settings are
     * unaffected. (native attribute: mSimulationStrandBegin)
     *
     * @return the starting strand index
     */
    @Override
    public int getSimulationStrandBegin() {
        long settingsVa = va();
        int result = getSimulationStrandBegin(settingsVa);

        return result;
    }

    /**
     * Return the end of the range of strands to visualize. The settings are
     * unaffected. (native attribute: mSimulationStrandEnd)
     *
     * @return the ending strand index
     */
    @Override
    public int getSimulationStrandEnd() {
        long settingsVa = va();
        int result = getSimulationStrandEnd(settingsVa);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static void free(long settingsVa);

    native private static boolean getDrawGridDensity(long settingsVa);

    native private static boolean getDrawGridVelocity(long settingsVa);

    native private static boolean getDrawInitialGravity(long settingsVa);

    native private static boolean getDrawNeutralDensity(long settingsVa);

    native private static boolean getDrawOrientations(long settingsVa);

    native private static boolean getDrawRenderStrands(long settingsVa);

    native private static boolean getDrawRods(long settingsVa);

    native private static boolean getDrawSkinPoints(long settingsVa);

    native private static boolean getDrawUnloadedRods(long settingsVa);

    native private static boolean getDrawVertexVelocity(long settingsVa);

    native private static int getRenderStrandColor(long settingsVa);

    native private static int getSimulationStrandBegin(long settingsVa);

    native private static int getSimulationStrandEnd(long settingsVa);

    native private static void setDrawGridDensity(
            long settingsVa, boolean enable);

    native private static void setDrawGridVelocity(
            long settingsVa, boolean enable);

    native private static void setDrawInitialGravity(
            long settingsVa, boolean enable);

    native private static void setDrawNeutralDensity(
            long settingsVa, boolean enable);

    native private static void setDrawOrientations(
            long settingsVa, boolean enable);

    native private static void setDrawRenderStrands(
            long settingsVa, boolean enable);

    native private static void setDrawRods(
            long settingsVa, boolean enable);

    native private static void setDrawSkinPoints(
            long settingsVa, boolean enable);

    native private static void setDrawUnloadedRods(
            long settingsVa, boolean enable);

    native private static void setDrawVertexVelocity(
            long settingsVa, boolean enable);

    native private static void setRenderStrandColor(
            long settingsVa, int ordinal);

    native private static void setSimulationStrandBegin(
            long settingsVa, int strandIndex);

    native private static void setSimulationStrandEnd(
            long settingsVa, int strandIndex);
}
