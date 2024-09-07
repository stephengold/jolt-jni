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

import com.github.stephengold.joltjni.readonly.ConstPhysicsMaterial;
import com.github.stephengold.joltjni.template.RefTarget;

/**
 * Surface properties of (part of) a {@code Shape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class PhysicsMaterial extends SerializableObject
        implements ConstPhysicsMaterial, RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a material with no native object assigned.
     */
    PhysicsMaterial() {
    }

    /**
     * Instantiate a material with the specified native object assigned but not
     * owned.
     *
     * @param materialVa the virtual address of the native object to assign (not
     * zero)
     */
    protected PhysicsMaterial(long materialVa) {
        super(materialVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the default material, used when a shape has no materials defined.
     *
     * @return a new immutable JVM object with the pre-existing native object
     * assigned
     */
    public static ConstPhysicsMaterial sDefault() {
        long materialVa = sDefault(true);
        ConstPhysicsMaterial result = new PhysicsMaterial(materialVa);

        return result;
    }
    // *************************************************************************
    // ConstPhysicsMaterial methods

    /**
     * Return the debug name. The material is unaffected.
     *
     * @return a string of text or null
     */
    @Override
    public String getDebugName() {
        long materialVa = va();
        String result = getDebugName(materialVa);

        return result;
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the shape.
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
    // native methods

    native private static String getDebugName(long materialVa);

    native private static int getRefCount(long materialVa);

    native private static long sDefault(boolean dummy);

    native private static long toRef(long materialVa);
}
