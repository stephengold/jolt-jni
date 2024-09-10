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
 * Either an error or a {@code ShapeRefC}. (native type:
 * {@code Result<Ref<Shape>>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class ShapeResult extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a result with the specified native object assigned.
     *
     * @param resultVa the virtual address of the native object to assign (not
     * zero)
     * @param owner true &rarr; make the current object the owner, false &rarr;
     * the current object isn't the owner
     */
    ShapeResult(long resultVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(resultVa) : null;
        setVirtualAddress(resultVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the {@code ShapeRefC}.
     *
     * @return a new JVM object with a new native object assigned
     */
    public ShapeRefC get() {
        long resultVa = va();
        long shapeRefVa = get(resultVa);
        ShapeRefC result = new ShapeRefC(shapeRefVa, true);

        return result;
    }

    /**
     * Return the error string.
     *
     * @return the string
     */
    public String getError() {
        long resultVa = va();
        String result = getError(resultVa);

        return result;
    }

    /**
     * Test whether there was an error.
     *
     * @return true if error, otherwise false
     */
    public boolean hasError() {
        long resultVa = va();
        boolean result = hasError(resultVa);

        return result;
    }

    /**
     * Test whether the {@code ShapeRefC} is valid.
     *
     * @return true if valid, otherwise false
     */
    public boolean isValid() {
        long resultVa = va();
        boolean result = isValid(resultVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void free(long resultVa);

    native private static long get(long resultVa);

    native private static String getError(long resultVa);

    native private static boolean hasError(long resultVa);

    native private static boolean isValid(long resultVa);
}
