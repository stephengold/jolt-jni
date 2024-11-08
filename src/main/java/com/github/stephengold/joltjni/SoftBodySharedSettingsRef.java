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

import com.github.stephengold.joltjni.readonly.ConstSoftBodySharedSettings;
import com.github.stephengold.joltjni.template.Ref;

/**
 * A counted reference to {@code SoftBodySharedSettings}. (native type:
 * {@code Ref<SoftBodySharedSettings>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class SoftBodySharedSettingsRef
        extends Ref
        implements ConstSoftBodySharedSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public SoftBodySharedSettingsRef() {
        long refVa = createEmpty();
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
    SoftBodySharedSettingsRef(long refVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(refVa) : null;
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // ConstSoftBodySharedSettings methods

    /**
     * Count the edge constraints. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countEdgeConstraints() {
        long settingsVa = targetVa();
        int result = SoftBodySharedSettings.countEdgeConstraints(settingsVa);

        return result;
    }

    /**
     * Count the faces. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countFaces() {
        long settingsVa = targetVa();
        int result = SoftBodySharedSettings.countFaces(settingsVa);

        return result;
    }

    /**
     * Count the vertices. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countVertices() {
        long settingsVa = targetVa();
        int result = SoftBodySharedSettings.countVertices(settingsVa);

        return result;
    }

    /**
     * Count the volume constraints. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countVolumeConstraints() {
        long settingsVa = targetVa();
        int result = SoftBodySharedSettings.countVolumeConstraints(settingsVa);

        return result;
    }

    /**
     * Return the radius of each particle. The settings are unaffected. (native
     * attribute: mVertexRadius)
     *
     * @return the radius (in meters)
     */
    @Override
    public float getVertexRadius() {
        long settingsVa = targetVa();
        float result = SoftBodySharedSettings.getVertexRadius(settingsVa);

        return result;
    }
    // *************************************************************************
    // Ref methods

    /**
     * Temporarily access the referenced {@code SoftBodySharedSettings}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public SoftBodySharedSettings getPtr() {
        long settingsVa = targetVa();
        SoftBodySharedSettings result = new SoftBodySharedSettings(settingsVa);

        return result;
    }

    /**
     * Return the address of the native {@code SoftBodySharedSettings}. No
     * objects are affected.
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
     * Create another counted reference to the native
     * {@code SoftBodySharedSettings}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public SoftBodySharedSettingsRef toRef() {
        long refVa = va();
        long copyVa = copy(refVa);
        SoftBodySharedSettingsRef result
                = new SoftBodySharedSettingsRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long copy(long refVa);

    native private static long createEmpty();

    native private static void free(long refVa);

    native private static long getPtr(long refVa);
}
