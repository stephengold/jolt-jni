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
 * Settings used to construct a {@code Shape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class ShapeSettings
        extends SerializableObject implements RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate settings with no native object assigned.
     */
    protected ShapeSettings() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Invoke this method after altering the settings but before generating any
     * new shapes.
     */
    public void clearCachedResult() {
        long settingsVa = va();
        clearCachedResult(settingsVa);
    }

    /**
     * Generate a {@code ShapeResult} from these settings.
     *
     * @return a new JVM object with a new native object assigned
     */
    public ShapeResult create() {
        long settingsVa = va();
        long resultVa = create(settingsVa);
        ShapeResult result = new ShapeResult(resultVa, true);

        return result;
    }

    /**
     * Generate a {@code Shape} from these settings.
     *
     * @return a new JVM object
     */
    abstract public Shape createShape();
    // *************************************************************************
    // RefTarget methods

    /**
     * Count active references to these settings.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long settingsVa = va();
        int result = getRefCount(settingsVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void clearCachedResult(long settingsVa);

    native private static long create(long settingsVa);

    native private static int getRefCount(long settingsVa);
}
