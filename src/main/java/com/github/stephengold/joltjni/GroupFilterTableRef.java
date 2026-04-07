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

import com.github.stephengold.joltjni.template.Ref;

/**
 * A counted reference to a {@code GroupFilterTable} object. (native type:
 * {@code Ref<GroupFilterTable>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class GroupFilterTableRef extends Ref {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public GroupFilterTableRef() {
        long refVa = createDefault();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a reference to the specified filter.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param filter the filter to target (not {@code null})
     */
    GroupFilterTableRef(long refVa, GroupFilterTable filter) {
        assert filter != null;

        this.ptr = filter;
        Runnable freeingAction = () -> free(refVa);
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Disable collisions between the specified subgroups.
     *
     * @param subGroup1 the ID of the first subgroup
     * @param subGroup2 the ID of the 2nd subgroup
     */
    public void disableCollision(int subGroup1, int subGroup2) {
        long filterVa = targetVa();
        GroupFilterTable.disableCollision(filterVa, subGroup1, subGroup2);
    }

    /**
     * Enable collisions between the specified subgroups.
     *
     * @param subGroup1 the ID of the first subgroup
     * @param subGroup2 the ID of the 2nd subgroup
     */
    public void enableCollision(int subGroup1, int subGroup2) {
        long filterVa = targetVa();
        GroupFilterTable.enableCollision(filterVa, subGroup1, subGroup2);
    }
    // *************************************************************************
    // new protected methods

    /**
     * Update the cached target.
     */
    void updatePtr() {
        long refVa = va();
        long targetVa = getPtr(refVa);
        if (targetVa == 0L) {
            this.ptr = null;
        } else {
            this.ptr = new GroupFilterTable(targetVa);
        }
    }
    // *************************************************************************
    // Ref methods

    /**
     * Access the targeted filter, if any.
     *
     * @return the pre-existing object, or {@code null} if the reference is
     * empty
     */
    @Override
    public GroupFilterTable getPtr() {
        GroupFilterTable result = (GroupFilterTable) ptr;
        return result;
    }

    /**
     * Return the address of the native {@code GroupFilterTable}. No objects are
     * affected.
     *
     * @return the virtual address, or zero if the reference is empty
     */
    @Override
    public long targetVa() {
        long refVa = va();
        long result = getPtr(refVa);
        assert result == (ptr == null ? 0L : getPtr().va());

        return result;
    }

    /**
     * Create an additional counted reference to the targeted filter.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public GroupFilterTableRef toRef() {
        GroupFilterTableRef result;
        if (ptr == null) {
            result = new GroupFilterTableRef();
        } else {
            long refVa = va();
            long copyVa = copy(refVa);
            GroupFilterTable filter = (GroupFilterTable) ptr;
            result = new GroupFilterTableRef(copyVa, filter);
        }

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long copy(long refVa);

    native private static long createDefault();

    native private static void free(long refVa);

    native private static long getPtr(long refVa);
}
