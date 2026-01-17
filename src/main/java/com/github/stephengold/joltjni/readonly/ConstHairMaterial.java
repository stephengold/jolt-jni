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

/**
 * Read-only access to a {@code HairMaterial}. (native type:
 * {@code const HairSettings::Material})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstHairMaterial extends ConstJoltPhysicsObject {
    /**
     * Return the angular damping coefficient of the rods. The material is
     * unaffected.
     *
     * @return the coefficient
     */
    float getAngularDamping();

    /**
     * Return the compliance of bend constraints, the inverse of bend stiffness.
     * The material is unaffected.
     *
     * @return the compliance value
     */
    float getBendCompliance();

    /**
     * Test whether collisions between hair strands and the environment are
     * enabled. The material is unaffected.
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    boolean getEnableCollision();

    /**
     * Test whether long-range attachments are enabled to keep hair near its
     * modeled pose. The material is unaffected.
     *
     * @return {@code true} if enabled, otherwise {@code false}
     */
    boolean getEnableLra();

    /**
     * Return the friction coefficient for collisions between hair strands and
     * the environment. The material is unaffected.
     *
     * @return the friction coefficient
     */
    float getFriction();

    /**
     * Access the fraction of the neutral pose applied to hair during each
     * iteration. The material is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstGradient getGlobalPose();

    /**
     * Access the fraction of gravity to be applied to the hair during
     * simulation. The material is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstGradient getGravityFactor();

    /**
     * Return the fraction of gravity to be removed during initialization. The
     * material is unaffected.
     *
     * @return the fraction
     */
    float getGravityPreloadFactor();

    /**
     * Return the force factor pushing the hair toward neutral density. The
     * material is unaffected.
     *
     * @return the force factor
     */
    float getGridDensityForceFactor();

    /**
     * Access the fraction of grid velocity to be applied to the hair. The
     * material is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstGradient getGridVelocityFactor();

    /**
     * Access the radius of the hair strand. The material is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstGradient getHairRadius();

    /**
     * Return the inertia of each rod as a multiple of its mass. The material is
     * unaffected.
     *
     * @return the ratio
     */
    float getInertiaMultiplier();

    /**
     * Return the linear damping coefficient of the rods. The material is
     * unaffected.
     *
     * @return the damping coefficient
     */
    float getLinearDamping();

    /**
     * Return the maximum angular rate of vertices. The material is unaffected.
     *
     * @return the rate limit (in radians per second)
     */
    float getMaxAngularVelocity();

    /**
     * Return the maximum speed of vertices. The material is unaffected.
     *
     * @return the speed limit (in meters per second)
     */
    float getMaxLinearVelocity();

    /**
     * Return the fraction of strands that should be simulated. Used by
     * {@code InitRenderAndSimulationStrands} only. The material is unaffected.
     *
     * @return the fraction
     */
    float getSimulationStrandsFraction();

    /**
     * Access how strongly the scalp influences the global pose. The material is
     * unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstGradient getSkinGlobalPose();

    /**
     * Return the compliance of stretch constraints, the inverse of stretch
     * stiffness. The material is unaffected.
     *
     * @return the compliance value
     */
    float getStretchCompliance();

    /**
     * Access the influence of head rotation on hair. The material is
     * unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstGradient getWorldTransformInfluence();
}
