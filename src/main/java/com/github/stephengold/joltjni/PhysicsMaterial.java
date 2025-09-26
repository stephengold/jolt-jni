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

import com.github.stephengold.joltjni.readonly.ConstPhysicsMaterial;

/**
 * Surface properties of (part of) a {@code Shape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class PhysicsMaterial
        extends SerializableObject
        implements ConstPhysicsMaterial {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a material with no native object assigned.
     */
    PhysicsMaterial() {
    }

    /**
     * Instantiate a material with the specified native object assigned.
     *
     * @param materialVa the virtual address of the native object to assign (not
     * zero)
     */
    PhysicsMaterial(long materialVa) {
        setVirtualAddressAsCoOwner(materialVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the default material, used when a shape has no material defined.
     *
     * @return a new immutable JVM object with the pre-existing native object
     * assigned
     */
    public static ConstPhysicsMaterial sDefault() {
        long materialVa = sDefault(true);
        ConstPhysicsMaterial result = new PhysicsMaterial(materialVa);

        return result;
    }

    /**
     * Read a material from the specified binary stream.
     *
     * @param stream where to read objects (not null)
     * @return a new object
     */
    public static PhysicsMaterialResult sRestoreFromBinaryState(
            StreamIn stream) {
        long streamVa = stream.va();
        long resultVa = sRestoreFromBinaryState(streamVa);
        PhysicsMaterialResult result
                = new PhysicsMaterialResult(resultVa, true);

        return result;
    }
    // *************************************************************************
    // new protected methods

    /**
     * Assign a native object (assuming there's none already assigned) and
     * designate the JVM object as a co-owner.
     *
     * @param materialVa the virtual address of the native object to assign (not
     * zero)
     */
    final protected void setVirtualAddressAsCoOwner(long materialVa) {
        long refVa = toRef(materialVa);
        Runnable freeingAction = () -> PhysicsMaterialRef.free(refVa);
        setVirtualAddress(materialVa, freeingAction);
    }
    // *************************************************************************
    // ConstPhysicsMaterial methods

    /**
     * Copy the debug color. The material is unaffected.
     *
     * @return a new object
     */
    @Override
    public Color getDebugColor() {
        long materialVa = va();
        int intColor = getDebugColor(materialVa);
        Color result = new Color(intColor);

        return result;
    }

    /**
     * Return the debug name. The material is unaffected.
     *
     * @return a string of text or {@code null}
     */
    @Override
    public String getDebugName() {
        long materialVa = va();
        String result = getDebugName(materialVa);

        return result;
    }

    /**
     * Save the material to the specified binary stream. The material is
     * unaffected.
     *
     * @param stream the stream to write to (not null)
     */
    @Override
    public void saveBinaryState(StreamOut stream) {
        long materialVa = va();
        long streamVa = stream.va();
        saveBinaryState(materialVa, streamVa);
    }

    /**
     * Create a counted reference to the native {@code PhysicsMaterial}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public PhysicsMaterialRefC toRefC() {
        long materialVa = va();
        long refVa = toRefC(materialVa);
        PhysicsMaterialRefC result = new PhysicsMaterialRefC(refVa, true);

        return result;
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code PhysicsMaterial}. The
     * material is unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long materialVa = va();
        int result = getRefCount(materialVa);

        return result;
    }

    /**
     * Mark the native {@code PhysicsMaterial} as embedded.
     */
    @Override
    public void setEmbedded() {
        long materialVa = va();
        setEmbedded(materialVa);
    }

    /**
     * Create a counted reference to the native {@code PhysicsMaterial}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public PhysicsMaterialRef toRef() {
        long materialVa = va();
        long copyVa = toRef(materialVa);
        PhysicsMaterialRef result = new PhysicsMaterialRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static int getDebugColor(long materialVa);

    native private static String getDebugName(long materialVa);

    native private static int getRefCount(long materialVa);

    native private static void saveBinaryState(long materialVa, long streamVa);

    native private static long sDefault(boolean dummy);

    native private static void setEmbedded(long materialVa);

    native private static long sRestoreFromBinaryState(long streamVa);

    native private static long toRef(long materialVa);

    native private static long toRefC(long materialVa);
}
