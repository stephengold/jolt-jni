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
 * Utility methods providing JNI access to Jolt Physics.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class Jolt {
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private Jolt() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the jolt-jni build-type string.
     *
     * @return either "Debug" or "Release"
     */
    native public static String buildType();

    /**
     * Destroy the factory for deserialization of saved data.
     *
     * @see newFactory()
     */
    native public static void destroyFactory();

    /**
     * Install the default assert callback.
     */
    native public static void installDefaultAssertCallback();

    /**
     * Install the default trace callback.
     */
    native public static void installDefaultTraceCallback();

    /**
     * Test whether the native library uses double-precision arithmetic.
     *
     * @return true if double-precision, otherwise false
     */
    native public static boolean isDoublePrecision();

    /**
     * Create a factory for deserialization of saved data.
     *
     * @see destroyFactory()
     */
    native public static void newFactory();

    /**
     * Register the allocation hook to use malloc/free. This must be done before
     * any other Jolt function is called.
     */
    native public static void registerDefaultAllocator();

    /**
     * Register all physics types with the factory and install their collision
     * handlers.
     *
     * @see unregisterTypes()
     */
    native public static void registerTypes();

    /**
     * Unregister all physics types with the factory.
     *
     * @see registerTypes()
     */
    native public static void unregisterTypes();

    /**
     * Return the jolt-jni version string.
     *
     * @return the version string (not null, not empty)
     */
    native public static String versionString();
}
