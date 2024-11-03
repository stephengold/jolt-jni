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

import com.github.stephengold.joltjni.template.RefTarget;

/**
 * Settings and structure used to create a {@code Ragdoll}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class RagdollSettings extends JoltPhysicsObject implements RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    RagdollSettings(long settingsVa) {
        super(settingsVa);
    }
    // *************************************************************************
    // RagdollInterface methods

    /**
     * Pre-calculate the map used by {@code getBodyIndexToConstraintIndex()}.
     */
    public void calculateBodyIndexToConstraintIndex() {
        long settingsVa = va();
        calculateBodyIndexToConstraintIndex(settingsVa);
    }

    /**
     * Pre-calculate the map used by {@code getConstraintIndexToBodyIdxPair()}.
     */
    public void calculateConstraintIndexToBodyIdxPair() {
        long settingsVa = va();
        calculateConstraintIndexToBodyIdxPair(settingsVa);
    }

    /**
     * Generate a ragdoll instance from the settings.
     *
     * @param groupId the collision group for the bodies
     * @param userData the desired user-data value
     * @param physicsSystem where to add the bodies and constraints (not null,
     * modified)
     * @return a new ragdoll instance, or {@code null} when out of bodies
     */
    public Ragdoll createRagdoll(
            int groupId, long userData, PhysicsSystem physicsSystem) {
        long settingsVa = va();
        long systemVa = physicsSystem.va();
        long resultVa = createRagdoll(settingsVa, groupId, userData, systemVa);
        Ragdoll result = new Ragdoll(resultVa);

        return result;
    }

    /**
     * Access the parts by way of a Java array. (native attribute: mParts)
     *
     * @return a new array of new JVM objects with pre-existing native objects
     * assigned
     */
    public Part[] getParts() {
        long settingsVa = va();
        int numParts = getNumParts(settingsVa);

        Part[] result = new Part[numParts];
        for (int partIndex = 0; partIndex < numParts; ++partIndex) {
            long partVa = getPart(settingsVa, partIndex);
            result[partIndex] = new Part(this, partVa);
        }

        return result;
    }

    /**
     * Access the skeleton. (native attribute: mSkeleton)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public Skeleton getSkeleton() {
        long settingsVa = va();
        long skeletonVa = getSkeleton(settingsVa);
        Skeleton result = new Skeleton(skeletonVa);

        return result;
    }

    /**
     * Stabilize the constraints.
     *
     * @return true if successful, otherwise false
     */
    public boolean stabilize() {
        long settingsVa = va();
        boolean result = stabilize(settingsVa);

        return result;
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code RagdollSettings}. The
     * settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long settingsVa = va();
        int result = getRefCount(settingsVa);

        return result;
    }

    /**
     * Mark the native {@code RagdollSettings} as embedded.
     */
    @Override
    public void setEmbedded() {
        long settingsVa = va();
        setEmbedded(settingsVa);
    }

    /**
     * Create a counted reference to the native {@code RagdollSettings}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public RagdollSettingsRef toRef() {
        long settingsVa = va();
        long refVa = toRef(settingsVa);
        RagdollSettingsRef result = new RagdollSettingsRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native static void calculateBodyIndexToConstraintIndex(long settingsVa);

    native static void calculateConstraintIndexToBodyIdxPair(long settingsVa);

    native static long createRagdoll(
            long settingsVa, int groupId, long userData, long systemVa);

    native static int getNumParts(long settingsVa);

    native static long getPart(long settingsVa, int partIndex);

    native private static int getRefCount(long settingsVa);

    native static long getSkeleton(long settingsVa);

    native private static void setEmbedded(long settingsVa);

    native static boolean stabilize(long settingsVa);

    native private static long toRef(long settingsVa);
}
