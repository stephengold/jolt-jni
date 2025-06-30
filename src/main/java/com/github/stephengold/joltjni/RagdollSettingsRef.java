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

import com.github.stephengold.joltjni.template.Ref;

/**
 * A counted reference to a {@code RagdollSettings} object. (native type:
 * {@code Ref<RagdollSettings>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class RagdollSettingsRef extends Ref {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public RagdollSettingsRef() {
        long refVa = createDefault();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    RagdollSettingsRef(long refVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(refVa) : null;
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // RagdollInterface methods

    /**
     * Add the specified constraint.
     *
     * @param constraint (not null)
     */
    public void addAdditionalConstraint(AdditionalConstraint constraint) {
        long settingsVa = targetVa();
        long constraintVa = constraint.va();
        RagdollSettings.addAdditionalConstraint(settingsVa, constraintVa);
    }

    /**
     * Pre-calculate the map used by {@code getBodyIndexToConstraintIndex()}.
     */
    public void calculateBodyIndexToConstraintIndex() {
        long settingsVa = targetVa();
        RagdollSettings.calculateBodyIndexToConstraintIndex(settingsVa);
    }

    /**
     * Pre-calculate the map used by {@code getConstraintIndexToBodyIdxPair()}.
     */
    public void calculateConstraintIndexToBodyIdxPair() {
        long settingsVa = targetVa();
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
        long settingsVa = targetVa();
        long systemVa = physicsSystem.va();
        long resultVa = RagdollSettings.createRagdoll(
                settingsVa, groupId, userData, systemVa);

        Ragdoll result;
        if (resultVa == 0L) {
            result = null;
        } else {
            result = new Ragdoll(resultVa, physicsSystem);
        }
        return result;
    }

    /**
     * Create and add collision filters to all bodies in the ragdoll and
     * configure them so parents and children don't collide.
     */
    public void disableParentChildCollisions() {
        long settingsVa = targetVa();
        RagdollSettings.disableParentChildCollisions(settingsVa);
    }

    /**
     * Access the parts by way of a Java array. (native attribute: mParts)
     *
     * @return a new array of new JVM objects with pre-existing native objects
     * assigned
     */
    public Part[] getParts() {
        long settingsVa = targetVa();
        int numParts = RagdollSettings.getNumParts(settingsVa);

        Part[] result = new Part[numParts];
        for (int partIndex = 0; partIndex < numParts; ++partIndex) {
            long partVa = RagdollSettings.getPart(settingsVa, partIndex);
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
        long settingsVa = targetVa();
        long skeletonVa = RagdollSettings.getSkeleton(settingsVa);
        Skeleton result = new Skeleton(skeletonVa);

        return result;
    }

    /**
     * Resize the parts array.
     *
     * @param numParts the desired number of parts (&ge;0)
     */
    public void resizeParts(int numParts) {
        long settingsVa = targetVa();
        RagdollSettings.resizeParts(settingsVa, numParts);
    }

    /**
     * Save the settings to the specified binary stream. The settings are
     * unaffected.
     *
     * @param stream the stream to write to (not null)
     * @param saveShapes if true, save the shapes
     * @param saveGroupFilter if true, save the group filter
     */
    public void saveBinaryState(
            StreamOut stream, boolean saveShapes, boolean saveGroupFilter) {
        long settingsVa = targetVa();
        long streamVa = stream.va();
        RagdollSettings.saveBinaryState(
                settingsVa, streamVa, saveShapes, saveGroupFilter);
    }

    /**
     * Replace the skeleton. (native attribute: mSkeleton)
     *
     * @param skeleton the desired skeleton (not null)
     */
    public void setSkeleton(Skeleton skeleton) {
        long settingsVa = targetVa();
        long skeletonVa = skeleton.va();
        RagdollSettings.setSkeleton(settingsVa, skeletonVa);
    }

    /**
     * Read a ragdoll from the specified input stream.
     *
     * @param stream the stream to read from (not null)
     * @return the result of the read (not null)
     */
    public static RagdollResult sRestoreFromBinaryState(StreamIn stream) {
        long streamVa = stream.targetVa();
        long resultVa = RagdollSettings.sRestoreFromBinaryState(streamVa);
        RagdollResult result = new RagdollResult(resultVa, true);

        return result;
    }

    /**
     * Stabilize the constraints.
     *
     * @return {@code true} if successful, otherwise {@code false}
     */
    public boolean stabilize() {
        long settingsVa = targetVa();
        boolean result = RagdollSettings.stabilize(settingsVa);

        return result;
    }
    // *************************************************************************
    // Ref methods

    /**
     * Temporarily access the referenced {@code RagdollSettings}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public RagdollSettings getPtr() {
        long settingsVa = targetVa();
        RagdollSettings result = new RagdollSettings(settingsVa);

        return result;
    }

    /**
     * Return the address of the native {@code RagdollSettings}. No objects are
     * affected.
     *
     * @return a virtual address (not zero)
     */
    @Override
    public long targetVa() {
        long refVa = va();
        long result = getPtr(refVa);

        return result;
    }

    /**
     * Create another counted reference to the native {@code RagdollSettings}.
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

    native private static long createDefault();

    native private static void free(long refVa);

    native private static long getPtr(long refVa);
}
