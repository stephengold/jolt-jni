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

/**
 * A group filter implemented using a table.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class GroupFilterTable extends GroupFilter {
    // *************************************************************************
    // constructors

    /**
     * Create a default filter with no subgroups.
     */
    public GroupFilterTable() {
        this(0);
    }

    /**
     * Instantiate a copy of the specified filter.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public GroupFilterTable(GroupFilterTable original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa); // not the owner due to ref counting
    }

    /**
     * Create a default filter with the specified number of subgroups.
     * Collisions will be enabled except when the sub-group IDs are equal.
     *
     * @param numSubGroups the initial number of subgroups (&ge;0)
     */
    public GroupFilterTable(int numSubGroups) {
        long filterVa = createFilter(numSubGroups);
        setVirtualAddress(filterVa); // not the owner due to ref counting
    }

    /**
     * Instantiate a filter with the specified native object assigned but not
     * owned.
     *
     * @param filterVa the virtual address of the native object to assign (not
     * zero)
     */
    GroupFilterTable(long filterVa) {
        setVirtualAddress(filterVa); // not the owner due to ref counting
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Disable collisions between the specified subgroups.
     *
     * @param subGroup1 the ID of the first subgroup
     * @param subGroup2 the ID of the 2nd subgroup
     * @throws IllegalArgumentException if {@code subGroup1 == subGroup2}
     */
    public void disableCollision(int subGroup1, int subGroup2) {
        if (subGroup1 == subGroup2) {
            throw new IllegalArgumentException("subgroups must be distinct");
        }
        long filterVa = va();
        disableCollision(filterVa, subGroup1, subGroup2);
    }

    /**
     * Enable collisions between the specified subgroups.
     *
     * @param subGroup1 the ID of the first subgroup
     * @param subGroup2 the ID of the 2nd subgroup
     * @throws IllegalArgumentException if {@code subGroup1 == subGroup2}
     */
    public void enableCollision(int subGroup1, int subGroup2) {
        if (subGroup1 == subGroup2) {
            throw new IllegalArgumentException("subgroups must be distinct");
        }
        long filterVa = va();
        enableCollision(filterVa, subGroup1, subGroup2);
    }
    // *************************************************************************
    // GroupFilter methods

    /**
     * Create a counted reference to the native {@code GroupFilterTable}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public GroupFilterTableRef toRef() {
        long filterVa = va();
        long refVa = GroupFilter.toRef(filterVa);
        GroupFilterTableRef result = new GroupFilterTableRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long createCopy(long originalVa);

    native private static long createFilter(int numSubGroups);

    native static void disableCollision(
            long filterVa, int subGroup1, int subGroup2);

    native static void enableCollision(
            long filterVa, int subGroup1, int subGroup2);
}
