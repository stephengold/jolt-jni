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

/**
 * Restrict which bodies can collide.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CollisionGroup extends JoltPhysicsObject {
    // *************************************************************************
    // fields

    /**
     * protect the {@code GroupFilter} from garbage collection
     */
    private GroupFilter filter;
    // *************************************************************************
    // constructors

    /**
     * Instantiate a group with the specified filter and IDs.
     *
     * @param filter the collision-group filter
     * @param groupId the main group ID
     * @param subGroupId the ID of the subgroup to which the body belongs
     */
    public CollisionGroup(GroupFilter filter, int groupId, int subGroupId) {
        this.filter = filter;
        long filterVa = filter.va();
        long groupVa = createGroup(filterVa, groupId, subGroupId);
        setVirtualAddress(groupVa, () -> free(groupVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the group filter. The group is unaffected.
     *
     * @return filter the pre-existing filter (not null)
     */
    public GroupFilter getGroupFilter() {
        return filter;
    }

    /**
     * Return the main group ID. The group is unaffected.
     *
     * @return the ID value
     */
    public int getGroupId() {
        long groupVa = va();
        int result = getGroupId(groupVa);

        return result;
    }

    /**
     * Return the sub-group ID. The group is unaffected.
     *
     * @return the ID value
     */
    public int getSubGroupId() {
        long groupVa = va();
        int result = getSubGroupId(groupVa);

        return result;
    }

    /**
     * Replace the group filter.
     *
     * @param filter the desired filter (not null, alias created)
     */
    public void setGroupFilter(GroupFilter filter) {
        this.filter = filter;
        long groupVa = va();
        long filterVa = filter.va();
        setGroupFilter(groupVa, filterVa);
    }

    /**
     * Alter the main group ID.
     *
     * @param id the desired ID
     */
    public void setGroupId(int id) {
        long groupVa = va();
        setGroupId(groupVa, id);
    }

    /**
     * Alter the sub-group ID.
     *
     * @param id the desired ID
     */
    public void setSubGroupID(int id) {
        long groupVa = va();
        setSubGroupId(groupVa, id);
    }
    // *************************************************************************
    // native private methods

    native private static long createGroup(
            long filterVa, int groupId, int subGroupId);

    native private static void free(long groupVa);

    native private static int getGroupId(long groupVa);

    native private static int getSubGroupId(long groupVa);

    native private static void setGroupFilter(long groupVa, long filterVa);

    native private static void setGroupId(long groupVa, int id);

    native private static void setSubGroupId(long groupVa, int id);
}
