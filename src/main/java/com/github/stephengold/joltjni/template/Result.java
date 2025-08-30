/*
Copyright (c) 2025 Stephen Gold

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
import com.github.stephengold.joltjni.readonly.ConstJoltPhysicsObject;

/**
 * The result of an operation that might fail.
 *
 * @author Stephen Gold sgold@sonic.net
 * @param <T> the const datatype produced when successful
 */
abstract public class Result<T extends ConstJoltPhysicsObject>
        extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with no native object assigned.
     * <p>
     * This no-arg constructor was made explicit to avoid javadoc warnings from
     * JDK 18+.
     */
    protected Result() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the result of a successful operation.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    abstract public T get();

    /**
     * Return the error message.
     *
     * @return a string of text
     */
    public String getError() {
        long resultVa = va();
        String result = getError(resultVa);

        return result;
    }

    /**
     * Test whether an error occurred.
     *
     * @return {@code true} if error, otherwise {@code false}
     */
    public boolean hasError() {
        long resultVa = va();
        boolean result = hasError(resultVa);

        return result;
    }

    /**
     * Test whether the value returned by {@code get()} is valid.
     *
     * @return {@code true} if valid, otherwise {@code false}
     */
    public boolean isValid() {
        long resultVa = va();
        boolean result = isValid(resultVa);

        return result;
    }
    // *************************************************************************
    // protected methods

    /**
     * Return the error message.
     *
     * @param resultVa the virtual address of the result (not zero)
     * @return a string of text
     */
    abstract protected String getError(long resultVa);

    /**
     * Test whether an error occurred.
     *
     * @param resultVa the virtual address of the result (not zero)
     * @return {@code true} if error, otherwise {@code false}
     */
    abstract protected boolean hasError(long resultVa);

    /**
     * Test whether the value returned by {@code get()} is valid.
     *
     * @param resultVa the virtual address of the result (not zero)
     * @return {@code true} if valid, otherwise {@code false}
     */
    abstract protected boolean isValid(long resultVa);
}
