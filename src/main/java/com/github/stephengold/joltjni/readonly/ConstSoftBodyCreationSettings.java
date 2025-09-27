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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.StreamOut;
import com.github.stephengold.joltjni.streamutils.GroupFilterToIdMap;
import com.github.stephengold.joltjni.streamutils.MaterialToIdMap;
import com.github.stephengold.joltjni.streamutils.SharedSettingsToIdMap;

/**
 * Read-only access to a {@code SoftBodyCreationSettings} object. (native type:
 * const SoftBodyCreationSettings)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstSoftBodyCreationSettings extends ConstJoltPhysicsObject {
    /**
     * Test whether the created body will be allowed to fall asleep. The
     * settings are unaffected.
     *
     * @return {@code true} if allowed, otherwise {@code false}
     */
    boolean getAllowSleeping();

    /**
     * Access the collision group.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstCollisionGroup getCollisionGroup();

    /**
     * Test whether faces will be double-sided. The settings are unaffected.
     * (native member: mFacesDoubleSided)
     *
     * @return {@code true} if double-sided, otherwise {@code false}
     */
    boolean getFacesDoubleSided();

    /**
     * Return the friction ratio. The settings are unaffected.
     *
     * @return the ratio (typically &ge;0 and &le;1)
     */
    float getFriction();

    /**
     * Return the gravity factor. The settings are unaffected.
     *
     * @return the factor
     */
    float getGravityFactor();

    /**
     * Return the linear damping constant. The settings are unaffected.
     *
     * @return the constant (in units of per second, &ge;0, &le;1)
     */
    float getLinearDamping();

    /**
     * Test whether to bake rotation into the vertices and set the body rotation
     * to identity. The settings are unaffected.
     *
     * @return {@code true} if rotation will be baked in, otherwise
     * {@code false}
     */
    boolean getMakeRotationIdentity();

    /**
     * Return the maximum linear speed. The settings are unaffected.
     *
     * @return the maximum speed (in meters per second)
     */
    float getMaxLinearVelocity();

    /**
     * Return the number of solver iterations. The settings are unaffected.
     *
     * @return the number of iterations
     */
    int getNumIterations();

    /**
     * Return the index of the object layer. The settings are unaffected.
     *
     * @return the layer index (&ge;0, &lt;numObjectLayers)
     */
    int getObjectLayer();

    /**
     * Copy the (initial) location. The settings are unaffected.
     *
     * @return a new location vector (in system coordinates, all components
     * finite)
     */
    RVec3 getPosition();

    /**
     * Return the internal pressure. The settings are unaffected.
     *
     * @return the pressure
     */
    float getPressure();

    /**
     * Return the restitution ratio. The settings are unaffected.
     *
     * @return the ratio (typically &ge;0 and &le;1)
     */
    float getRestitution();

    /**
     * Copy the (initial) orientation of the body's axes. The settings are
     * unaffected.
     *
     * @return a new rotation quaternion (relative to the system axes)
     */
    Quat getRotation();

    /**
     * Access the shared settings.
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if none
     */
    ConstSoftBodySharedSettings getSettings();

    /**
     * Test whether to update the position of the body during simulation. The
     * settings are unaffected.
     *
     * @return {@code true} if the position will be updated, otherwise
     * {@code false}
     */
    boolean getUpdatePosition();

    /**
     * Return the user data. The settings are unaffected.
     *
     * @return the value
     */
    long getUserData();

    /**
     * Return the radius of each particle. The settings are unaffected.
     *
     * @return the radius (in meters)
     */
    float getVertexRadius();

    /**
     * Write the state of this object to the specified stream, excluding the
     * shared settings, materials, and group filter. The settings are
     * unaffected.
     *
     * @param stream where to write objects (not null)
     */
    void saveBinaryState(StreamOut stream);

    /**
     * Write the state of this object to the specified stream. The settings are
     * unaffected.
     *
     * @param stream where to write objects (not null)
     * @param sbssMap track multiple uses of shared settings (may be null)
     * @param materialMap track multiple uses of physics materials (may be null)
     * @param filterMap track multiple uses of group filters (may be null)
     */
    void saveWithChildren(
            StreamOut stream, SharedSettingsToIdMap sbssMap,
            MaterialToIdMap materialMap, GroupFilterToIdMap filterMap);
}
