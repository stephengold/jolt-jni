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

import com.github.stephengold.joltjni.readonly.ConstCollisionGroup;
import com.github.stephengold.joltjni.template.Ref;
import com.github.stephengold.joltjni.template.RefTarget;

/**
 * Test whether 2 collision groups can collide.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class GroupFilter extends SerializableObject implements RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a filter with no native object assigned.
     */
    GroupFilter() {
    }

    /**
     * Instantiate a filter with the specified native object assigned but not
     * owned.
     *
     * @param filterVa the virtual address of the native object to assign (not
     * zero)
     */
    GroupFilter(long filterVa) {
        super(filterVa);
    }

    /**
     * Test whether the specified groups can collide. The filter is unaffected.
     *
     * @param group1 the first group (not null, unaffected)
     * @param group2 the 2nd group (not null, unaffected)
     * @return {@code true} if they can collide, otherwise {@code false}
     */
    public boolean canCollide(
            ConstCollisionGroup group1, ConstCollisionGroup group2) {
        long filterVa = va();
        long group1Va = group1.targetVa();
        long group2Va = group2.targetVa();
        boolean result = canCollide(filterVa, group1Va, group2Va);

        return result;
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code GroupFilter}. The filter
     * is unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long filterVa = va();
        int result = getRefCount(filterVa);

        return result;
    }

    /**
     * Mark the native {@code GroupFilter} as embedded.
     */
    @Override
    public void setEmbedded() {
        long filterVa = va();
        setEmbedded(filterVa);
    }

    /**
     * Create a counted reference to the native {@code GroupFilter}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public Ref toRef() {
        long filterVa = va();
        long copyVa = toRef(filterVa);
        Ref result = new GroupFilterRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static boolean canCollide(
            long filterVa, long group1Va, long group2Va);

    native private static int getRefCount(long filterVa);

    native private static void setEmbedded(long filterVa);

    native static long toRef(long filterVa);
}
