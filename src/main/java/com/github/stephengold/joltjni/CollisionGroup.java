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
import com.github.stephengold.joltjni.readonly.ConstGroupFilter;

/**
 * Restrict which bodies can collide.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CollisionGroup
        extends JoltPhysicsObject
        implements ConstCollisionGroup {
    // *************************************************************************
    // constants

    /**
     * invalid main group
     */
    final public static int cInvalidGroup = -1;
    /**
     * invalid sub-group
     */
    final public static int cInvalidSubGroup = -1;
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default group.
     */
    public CollisionGroup() {
        long groupVa = createDefault();
        setVirtualAddress(groupVa, () -> free(groupVa));
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param groupVa the virtual address of the native object to assign (not
     * zero)
     */
    CollisionGroup(JoltPhysicsObject container, long groupVa) {
        super(container, groupVa);
    }

    /**
     * Instantiate a group with the specified filter and IDs.
     *
     * @param filter the collision-group filter (not null)
     * @param groupId the main group ID
     * @param subGroupId the ID of the subgroup to which the body belongs
     */
    public CollisionGroup(GroupFilter filter, int groupId, int subGroupId) {
        long filterVa = filter.va();
        long groupVa = createGroup(filterVa, groupId, subGroupId);
        setVirtualAddress(groupVa, () -> free(groupVa));
    }

    /**
     * Instantiate a group with the specified filter and IDs.
     *
     * @param filterRef a counted reference to the desired collision-group
     * filter (not null)
     * @param groupId the main group ID
     * @param subGroupId the ID of the subgroup to which the body belongs
     */
    public CollisionGroup(
            GroupFilterTableRef filterRef, int groupId, int subGroupId) {
        long filterVa = filterRef.targetVa();
        long groupVa = createGroup(filterVa, groupId, subGroupId);
        setVirtualAddress(groupVa, () -> free(groupVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Replace the group filter.
     *
     * @param filter the desired filter (not null, alias created)
     */
    public void setGroupFilter(GroupFilter filter) {
        long groupVa = va();
        long filterVa = filter.va();
        setGroupFilter(groupVa, filterVa);
    }

    /**
     * Alter the main group ID.
     *
     * @param id the desired ID (default=cInvalidGroup)
     */
    public void setGroupId(int id) {
        long groupVa = va();
        setGroupId(groupVa, id);
    }

    /**
     * Alter the sub-group ID.
     *
     * @param id the desired ID (default=cInvalidSubGroup)
     */
    public void setSubGroupId(int id) {
        long groupVa = va();
        setSubGroupId(groupVa, id);
    }
    // *************************************************************************
    // ConstCollisionGroup methods

    /**
     * Access the group filter.
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if none
     */
    @Override
    public ConstGroupFilter getGroupFilter() {
        long groupVa = va();
        long filterVa = getGroupFilter(groupVa);
        GroupFilter result;
        if (filterVa == 0L) {
            result = null;
        } else {
            result = new GroupFilter(filterVa);
        }

        return result;
    }

    /**
     * Return the main group ID. The group is unaffected.
     *
     * @return the ID value or {@code cInvalidGroup}
     */
    @Override
    public int getGroupId() {
        long groupVa = va();
        int result = getGroupId(groupVa);

        return result;
    }

    /**
     * Return the sub-group ID. The group is unaffected.
     *
     * @return the ID value or {@code cInvalidSubGroup}
     */
    @Override
    public int getSubGroupId() {
        long groupVa = va();
        int result = getSubGroupId(groupVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createDefault();

    native private static long createGroup(
            long filterVa, int groupId, int subGroupId);

    native private static void free(long groupVa);

    native private static long getGroupFilter(long groupVa);

    native private static int getGroupId(long groupVa);

    native private static int getSubGroupId(long groupVa);

    native private static void setGroupFilter(long groupVa, long filterVa);

    native private static void setGroupId(long groupVa, int id);

    native private static void setSubGroupId(long groupVa, int id);
}
