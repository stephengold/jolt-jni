/*
Copyright (c) 2026 Stephen Gold

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
 * Utility class to load native libraries.
 *
 * @author David J. Morfe
 */
final public class NativeLibraryLoader {
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private NativeLibraryLoader() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Load a native library.
     *
     * @param libraryPath the file to load (not {@code null})
     * @return {@code true} after a successful load, otherwise {@code false}
     */
    public static boolean loadLibrary(String libraryPath) {
        assert libraryPath != null;
        boolean success = false;

        try {
            System.load(libraryPath);
            success = true;
        } catch (SecurityException | UnsatisfiedLinkError exception) {
            System.err.printf("libraryPath = %s%n%s", libraryPath, exception);
        }

        return success;
    }
}
