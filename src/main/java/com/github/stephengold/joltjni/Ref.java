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
 * A counted reference to a {@code RefTarget}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class Ref extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a counted reference with no native object assigned.
     */
    protected Ref() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Temporarily access the referenced {@code RefTarget}.
     *
     * @return a new JVM object that refers to the pre-existing native object
     */
    abstract public RefTarget getPtr();

    /**
     * Create a counted reference to the native {@code RefTarget}.
     *
     * @return a new JVM object with a new native object assigned
     */
    abstract public Ref toRef();
}
