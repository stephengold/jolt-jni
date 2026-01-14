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
package com.github.stephengold.joltjni.readonly;

import java.nio.FloatBuffer;

/**
 * Read-only access to a {@code Float4}. (native type: const Float4)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstFloat4 {
    /**
     * Return the specified component. The vector is unaffected.
     *
     * @param index 0, 1, 2, or 3
     * @return the X component if index=0, the Y component if index=1, the Z
     * component if index=2, or the W component if index=3
     * @throws IllegalArgumentException if index is not 0, 1, 2, or 3
     */
    float get(int index);

    /**
     * Write all 4 components to the specified buffer and advance the buffer's
     * position by 4. The vector is unaffected.
     *
     * @param storeBuffer the destination buffer (not {@code null})
     */
    void put(FloatBuffer storeBuffer);

    /**
     * Return the 4th (W) component. The vector is unaffected.
     *
     * @return the component value
     */
    float w();

    /**
     * Return the first (X) component. The vector is unaffected.
     *
     * @return the component value
     */
    float x();

    /**
     * Return the 2nd (Y) component. The vector is unaffected.
     *
     * @return the component value
     */
    float y();

    /**
     * Return the 3rd (Z) component. The vector is unaffected.
     *
     * @return the component value
     */
    float z();
}
