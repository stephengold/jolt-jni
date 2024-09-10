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

import com.github.stephengold.joltjni.template.Ref;

/**
 * A counted reference to a {@code RagdollSettings} object. (native type:
 * {@code Ref<RagdollSettings>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class RagdollSettingsRef extends Ref {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public RagdollSettingsRef() {
        long refVa = createEmpty();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param owner true &rarr; make the current object the owner, false &rarr;
     * the current object isn't the owner
     */
    RagdollSettingsRef(long refVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(refVa) : null;
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // RagdollInterface methods

    /**
     * Pre-calculate the map used by {@code getBodyIndexToConstraintIndex()}.
     */
    public void calculateBodyIndexToConstraintIndex() {
        long refVa = va();
        long settingsVa = getPtr(refVa);
        RagdollSettings.calculateBodyIndexToConstraintIndex(settingsVa);
    }

    /**
     * Pre-calculate the map used by {@code getConstraintIndexToBodyIdxPair()}.
     */
    public void calculateConstraintIndexToBodyIdxPair() {
        long refVa = va();
        long settingsVa = getPtr(refVa);
        RagdollSettings.calculateConstraintIndexToBodyIdxPair(settingsVa);
    }

    /**
     * Generate a ragdoll instance from the settings, which are unaffected.
     *
     * @param groupId the collision group for the bodies
     * @param userData the desired user-data value
     * @param physicsSystem where to add the bodies and constraints (not null,
     * modified)
     * @return a new ragdoll instance, or {@code null} when out of bodies
     */
    public Ragdoll createRagdoll(
            int groupId, long userData, PhysicsSystem physicsSystem) {
        long refVa = va();
        long settingsVa = getPtr(refVa);
        long systemVa = physicsSystem.va();
        long resultVa = RagdollSettings.createRagdoll(
                settingsVa, groupId, userData, systemVa);
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
        long refVa = va();
        long settingsVa = getPtr(refVa);
        int numParts = RagdollSettings.getNumParts(settingsVa);

        Part[] result = new Part[numParts];
        for (int partIndex = 0; partIndex < numParts; ++partIndex) {
            long partVa = RagdollSettings.getPart(settingsVa, partIndex);
            result[partIndex] = new Part(partVa);
        }

        return result;
    }

    /**
     * Access the skeleton. (native attribute: mSkeleton)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public Skeleton getSkeleton() {
        long refVa = va();
        long settingsVa = getPtr(refVa);
        long skeletonVa = RagdollSettings.getSkeleton(settingsVa);
        Skeleton result = new Skeleton(skeletonVa);

        return result;
    }

    /**
     * Stabilize the constraints.
     *
     * @return true if successful, otherwise false
     */
    public boolean stabilize() {
        long refVa = va();
        long settingsVa = getPtr(refVa);
        boolean result = RagdollSettings.stabilize(settingsVa);

        return result;
    }
    // *************************************************************************
    // Ref methods

    /**
     * Temporarily access the referenced {@code RagdollSettings}.
     *
     * @return a new JVM object that refers to the pre-existing native object
     */
    @Override
    public RagdollSettings getPtr() {
        long refVa = va();
        long animationVa = getPtr(refVa);
        RagdollSettings result = new RagdollSettings(animationVa);

        return result;
    }

    /**
     * Create a counted reference to the native {@code RagdollSettingsRef}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public RagdollSettingsRef toRef() {
        long refVa = va();
        long copyVa = copy(refVa);
        RagdollSettingsRef result = new RagdollSettingsRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long copy(long refVa);

    native private static long createEmpty();

    native private static void free(long refVa);

    native private static long getPtr(long refVa);
}
