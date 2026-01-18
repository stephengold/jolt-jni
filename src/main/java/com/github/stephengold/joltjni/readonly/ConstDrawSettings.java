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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.enumerate.ERenderStrandColor;

/**
 * Read-only access to a {@code DrawSettings} object. (native type:
 * {@code const Hair::DrawSettings})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstDrawSettings extends ConstJoltPhysicsObject {
    /**
     * Test whether to visualize vertex angular velocities. The settings are
     * unaffected. (native attribute: mDrawAngularVelocity)
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    boolean getDrawAngularVelocity();

    /**
     * Test whether to visualize grid density. The settings are unaffected.
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    boolean getDrawGridDensity();

    /**
     * Test whether to visualize grid velocity. The settings are unaffected.
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    boolean getDrawGridVelocity();

    /**
     * Test whether to visualize the initial gravity vector. The settings are
     * unaffected.
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    boolean getDrawInitialGravity();

    /**
     * Test whether to visualize the grid density of the hair in its neutral
     * pose. The settings are unaffected.
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    boolean getDrawNeutralDensity();

    /**
     * Test whether to visualize the coordinate axes of each simulated vertex.
     * The settings are unaffected.
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    boolean getDrawOrientations();

    /**
     * Test whether to visualize the strands. The settings are unaffected.
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    boolean getDrawRenderStrands();

    /**
     * Test whether to visualize the simulated rods. The settings are
     * unaffected.
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    boolean getDrawRods();

    /**
     * Test whether to visualize the skinning points on the scalp. The settings
     * are unaffected.
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    boolean getDrawSkinPoints();

    /**
     * Test whether to visualize rods in their unloaded pose. The settings are
     * unaffected.
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    boolean getDrawUnloadedRods();

    /**
     * Test whether to visualize vertex velocities. The settings are unaffected.
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    boolean getDrawVertexVelocity();

    /**
     * Return the option for coloring strands. The settings are unaffected.
     *
     * @return the enum value (not {@code null})
     */
    ERenderStrandColor getRenderStrandColor();

    /**
     * Return the start of the range of strands to visualize. The settings are
     * unaffected.
     *
     * @return the starting strand index
     */
    int getSimulationStrandBegin();

    /**
     * Return the end of the range of strands to visualize. The settings are
     * unaffected.
     *
     * @return the ending strand index
     */
    int getSimulationStrandEnd();
}
