/*
Copyright (c) 2024-2026 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.ConstRagdollSettings;
import com.github.stephengold.joltjni.std.IntPair;
import com.github.stephengold.joltjni.template.RefTarget;

/**
 * Settings and structure used to create a {@code Ragdoll}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class RagdollSettings
        extends JoltPhysicsObject
        implements ConstRagdollSettings, RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public RagdollSettings() {
        long settingsVa = createDefault();
        long refVa = toRef(settingsVa);
        setVirtualAddress(settingsVa, () -> RagdollSettingsRef.free(refVa));
    }

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    RagdollSettings(long settingsVa) {
        long refVa = toRef(settingsVa);
        setVirtualAddress(settingsVa, () -> RagdollSettingsRef.free(refVa));
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public RagdollSettings(ConstRagdollSettings original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        long refVa = toRef(copyVa);
        setVirtualAddress(copyVa, () -> RagdollSettingsRef.free(refVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Add the specified constraint.
     *
     * @param constraint (not {@code null})
     */
    public void addAdditionalConstraint(AdditionalConstraint constraint) {
        long settingsVa = va();
        long constraintVa = constraint.va();
        addAdditionalConstraint(settingsVa, constraintVa);
    }

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
     * Initialize the constraint priorities so that constraints near the leaves
     * have priority than those near the root.
     */
    public void calculateConstraintPriorities() {
        calculateConstraintPriorities(0);
    }

    /**
     * Initialize the constraint priorities so that constraints near the leaves
     * have priority than those near the root.
     *
     * @param basePriority the lowest priority to use (default=0)
     */
    public void calculateConstraintPriorities(int basePriority) {
        long settingsVa = va();
        calculateConstraintPriorities(settingsVa, basePriority);
    }

    /**
     * Create and add collision filters to all bodies in the ragdoll and
     * configure them so parents and children don't collide.
     */
    public void disableParentChildCollisions() {
        long settingsVa = va();
        disableParentChildCollisions(settingsVa);
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
     * Resize the parts array.
     *
     * @param numParts the desired number of parts (&ge;0)
     */
    public void resizeParts(int numParts) {
        long settingsVa = va();
        resizeParts(settingsVa, numParts);
    }

    /**
     * Copy the argument to the current settings.
     *
     * @param source the settings to copy (not {@code null}, unaffected)
     */
    public void set(ConstRagdollSettings source) {
        long targetVa = va();
        long sourceVa = source.targetVa();
        assign(targetVa, sourceVa);
    }

    /**
     * Replace the skeleton. (native attribute: mSkeleton)
     *
     * @param skeleton the desired skeleton (not {@code null})
     */
    public void setSkeleton(Skeleton skeleton) {
        long settingsVa = va();
        long skeletonVa = skeleton.va();
        setSkeleton(settingsVa, skeletonVa);
    }

    /**
     * Read a ragdoll from the specified input stream.
     *
     * @param stream the stream to read from (not {@code null})
     * @return the result of the read (not {@code null})
     */
    public static RagdollResult sRestoreFromBinaryState(StreamIn stream) {
        long streamVa = stream.va();
        long resultVa = sRestoreFromBinaryState(streamVa);
        RagdollResult result = new RagdollResult(resultVa, true);

        return result;
    }

    /**
     * Stabilize the constraints.
     *
     * @return {@code true} if successful, otherwise {@code false}
     */
    public boolean stabilize() {
        long settingsVa = va();
        boolean result = stabilize(settingsVa);

        return result;
    }
    // *************************************************************************
    // ConstRagdollSettings methods

    /**
     * Generate a ragdoll instance from the settings, which are unaffected.
     *
     * @param groupId the collision group for the bodies
     * @param userData the desired user-data value
     * @param physicsSystem where to add the bodies and constraints (not
     * {@code null}, modified)
     * @return a new ragdoll instance, or {@code null} when out of bodies
     */
    @Override
    public Ragdoll createRagdoll(
            int groupId, long userData, PhysicsSystem physicsSystem) {
        long settingsVa = va();
        long systemVa = physicsSystem.va();
        long resultVa = createRagdoll(settingsVa, groupId, userData, systemVa);

        Ragdoll result;
        if (resultVa == 0L) {
            result = null;
        } else {
            result = new Ragdoll(resultVa, physicsSystem);
        }
        return result;
    }

    /**
     * Copy the body-index pair for the specified constraint.
     *
     * @param constraintIndex the index of the constraint (&ge;0)
     * @return a new object
     */
    @Override
    public IntPair getBodyIndicesForConstraintIndex(int constraintIndex) {
        long settingsVa = va();
        long pairVa
                = getBodyIndicesForConstraintIndex(settingsVa, constraintIndex);
        IntPair result = new IntPair(pairVa, true);

        return result;
    }

    /**
     * Return the index of the constraint upstream from the specified body. The
     * settings are unaffected.
     *
     * @param bodyIndex index of the body in the ragdoll (&ge;0)
     * @return the constraint index
     */
    @Override
    public int getConstraintIndexForBodyIndex(int bodyIndex) {
        long settingsVa = va();
        int result = getConstraintIndexForBodyIndex(settingsVa, bodyIndex);

        return result;
    }

    /**
     * Count the parts. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getNumParts() {
        long settingsVa = va();
        int result = getNumParts(settingsVa);

        return result;
    }

    /**
     * Access the part for the specified index.
     *
     * @param partIndex index of the part (&ge;0)
     * @return a new JVM object with pre-existing native object assigned
     */
    @Override
    public Part getPart(int partIndex) {
        long settingsVa = va();
        long partVa = getPart(settingsVa, partIndex);
        Part result = new Part(this, partVa);

        return result;
    }

    /**
     * Access the skeleton. (native attribute: mSkeleton)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public Skeleton getSkeleton() {
        long settingsVa = va();
        long skeletonVa = getSkeleton(settingsVa);
        Skeleton result = new Skeleton(skeletonVa);

        return result;
    }

    /**
     * Save the settings to the specified binary stream. The settings are
     * unaffected.
     *
     * @param stream the stream to write to (not {@code null})
     * @param saveShapes if true, save the shapes
     * @param saveGroupFilter if true, save the group filter
     */
    @Override
    public void saveBinaryState(
            StreamOut stream, boolean saveShapes, boolean saveGroupFilter) {
        long settingsVa = va();
        long streamVa = stream.va();
        saveBinaryState(settingsVa, streamVa, saveShapes, saveGroupFilter);
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
     * Create a counted reference to the current settings.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public RagdollSettingsRef toRef() {
        long settingsVa = va();
        long refVa = toRef(settingsVa);
        RagdollSettingsRef result = new RagdollSettingsRef(refVa, this);

        return result;
    }
    // *************************************************************************
    // native methods

    native static void addAdditionalConstraint(
            long settingsVa, long constraintVa);

    native private static void assign(long targetVa, long sourceVa);

    native static void calculateConstraintPriorities(
            long settingsVa, int basePriority);

    native static void calculateBodyIndexToConstraintIndex(long settingsVa);

    native static void calculateConstraintIndexToBodyIdxPair(long settingsVa);

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native static long createRagdoll(
            long settingsVa, int groupId, long userData, long systemVa);

    native static void disableParentChildCollisions(long settingsVa);

    native static long getBodyIndicesForConstraintIndex(
            long settingsVa, int constraintIndex);

    native static int getConstraintIndexForBodyIndex(
            long settingsVa, int bodyIndex);

    native static int getNumParts(long settingsVa);

    native static long getPart(long settingsVa, int partIndex);

    native private static int getRefCount(long settingsVa);

    native static long getSkeleton(long settingsVa);

    native static void resizeParts(long settingsVa, int numParts);

    native static void saveBinaryState(long settingsVa, long streamVa,
            boolean saveShapes, boolean saveGroupFilter);

    native private static void setEmbedded(long settingsVa);

    native static void setSkeleton(long settingsVa, long skeletonVa);

    native static long sRestoreFromBinaryState(long streamVa);

    native static boolean stabilize(long settingsVa);

    native private static long toRef(long settingsVa);
}
