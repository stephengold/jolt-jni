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
package testjoltjni.junit;

import com.github.stephengold.joltjni.CollisionGroup;
import com.github.stephengold.joltjni.GroupFilterTable;
import com.github.stephengold.joltjni.readonly.ConstCollisionGroup;
import com.github.stephengold.joltjni.readonly.ConstGroupFilter;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Automated JUnit4 tests for creation, destruction, accessors, and defaults of
 * collision-related classes.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test018 {
    // *************************************************************************
    // new methods exposed

    /**
     * Test object creation, destruction, accessors, and defaults.
     */
    @Test
    public void test018() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        doCollisionGroup();

        TestUtils.cleanup();
    }
    // *************************************************************************
    // Java private methods

    /**
     * Test the {@code CollisionGroup} class.
     */
    private static void doCollisionGroup() {
        CollisionGroup group = new CollisionGroup();

        testCollisionGroupDefaults(group);
        CollisionGroup copy = new CollisionGroup(group);
        testCollisionGroupSetters(group);
        testCollisionGroupDefaults(copy);
        group.set(copy);
        testCollisionGroupDefaults(group);

        TestUtils.testClose(copy, group);
        System.gc();
    }

    /**
     * Test the getters and defaults of the specified {@code CollisionGroup}.
     *
     * @param group the group to test (not {@code null}, unaffected)
     */
    private static void testCollisionGroupDefaults(ConstCollisionGroup group) {
        Assert.assertNull(group.getGroupFilter());
        Assert.assertEquals(CollisionGroup.cInvalidGroup, group.getGroupId());
        Assert.assertEquals(
                CollisionGroup.cInvalidGroup, group.getSubGroupId());
    }

    /**
     * Test the setters of the specified {@code CollisionGroup}.
     *
     * @param group the group to test (not {@code null})
     */
    private static void testCollisionGroupSetters(CollisionGroup group) {
        GroupFilterTable filter = new GroupFilterTable();
        group.setGroupFilter(filter);
        group.setGroupId(101);
        group.setSubGroupId(102);

        ConstGroupFilter actualFilter = group.getGroupFilter();
        Assert.assertEquals(filter, actualFilter);
        Assert.assertEquals(101, group.getGroupId());
        Assert.assertEquals(102, group.getSubGroupId());

        TestUtils.testClose(actualFilter, filter);
    }
}
