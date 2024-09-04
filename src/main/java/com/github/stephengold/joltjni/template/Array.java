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
package com.github.stephengold.joltjni.template;

import com.github.stephengold.joltjni.JoltPhysicsObject;
import java.util.ArrayList;
import java.util.List;

/**
 * A variable-length array of elements.
 *
 * @author Stephen Gold sgold@sonic.net
 * @param <T> the specific type of element
 */
abstract public class Array<T extends JoltPhysicsObject>
        extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an array with no native object assigned.
     */
    protected Array() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Count how many elements the currently allocated storage can hold.
     *
     * @return the number of elements (&ge;size)
     */
    abstract public int capacity();

    /**
     * Destroy all elements and set the size to zero.
     */
    public void clear() {
        resize(0);
    }

    /**
     * Test whether the array contains any elements.
     *
     * @return true if empty, otherwise false
     */
    public boolean empty() {
        if (size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Access or copy the element at the specified index.
     *
     * @param elementIndex the index from which to get the array (&ge;0)
     * @return a new JVM object
     */
    abstract public T get(int elementIndex);

    /**
     * Expand or truncate the array.
     *
     * @param numElements the desired size (number of elements, &ge;0)
     */
    abstract public void resize(int numElements);

    /**
     * Append the specified object to the end.
     *
     * @param object the object to append (not null)
     */
    public void pushBack(T object) {
        int numElements = size();
        set(numElements, object);
    }

    /**
     * Put or duplicate the specified object at the specified index.
     *
     * @param elementIndex the index at which to put the object (&ge;0)
     * @param object the object to put or duplicate (not null)
     */
    abstract public void set(int elementIndex, T object);

    /**
     * Count how many elements are in the array.
     *
     * @return the number of elements (&ge;0, &le;capacity)
     */
    abstract public int size();

    /**
     * Copy all the elements (in order) to a Java list.
     *
     * @return a new Java list
     */
    public List<T> toList() {
        int numElements = size();
        List<T> result = new ArrayList<>(numElements);
        for (int i = 0; i < numElements; ++i) {
            T element = get(i);
            result.add(element);
        }

        return result;
    }
}
