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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.template.Result;

/**
 * Either an error or a {@code BodyCreationSettings}. (native type:
 * {@code Result<BodyCreationSettings>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BcsResult extends Result<BodyCreationSettings> {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a result with the specified native object assigned.
     *
     * @param resultVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    BcsResult(long resultVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(resultVa) : null;
        setVirtualAddress(resultVa, freeingAction);
    }
    // *************************************************************************
    // Result<BodyCreationSettings> methods

    /**
     * Return the settings object.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public BodyCreationSettings get() {
        long resultVa = va();
        long settingsVa = get(resultVa);
        BodyCreationSettings result
                = new BodyCreationSettings(settingsVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static void free(long resultVa);

    native private static long get(long resultVa);

    @Override
    final native protected String getError(long resultVa);

    @Override
    final native protected boolean hasError(long resultVa);

    @Override
    final native protected boolean isValid(long resultVa);
}
