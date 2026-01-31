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

import java.nio.ByteBuffer;

/**
 * A customizable {@code ShaderLoader}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CustomShaderLoader extends ShaderLoader {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a customizable loader.
     */
    public CustomShaderLoader() {
        long loaderVa = create();
        setVirtualAddressAsOwner(loaderVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Callback invoked (by native code) each time a load is requested. Meant to
     * be overridden.
     *
     * @param shaderName the name of the shader to load
     * @param data storage for a reference to the loaded bytes (not
     * {@code null}, length&ge;1)
     * @param error storage for an error message (not {@code null}, length&ge;1)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public boolean onLoadRequest(
            String shaderName, ByteBuffer[] data, String[] error) {
        return false;
    }
    // *************************************************************************
    // native private methods

    native private long create();
}
