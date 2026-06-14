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

import com.github.stephengold.joltjni.enumerate.EBackFaceMode;

/**
 * Read-only access to a {@code ShapeCastSettings}. (native type:
 * {@code const ShapeCastSettings})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstShapeCastSettings extends ConstCollideSettingsBase {
    /**
     * Return the policy for back-facing triangles in convex shapes. The
     * settings are unaffected.
     *
     * @return the enum value (not {@code null})
     */
    EBackFaceMode getBackFaceModeConvex();

    /**
     * Return the policy for back-facing triangles in triangle-based shapes. The
     * settings are unaffected.
     *
     * @return the enum value (not {@code null})
     */
    EBackFaceMode getBackFaceModeTriangles();

    /**
     * Return the extra thickness for enlarging the query shape. The settings
     * are unaffected.
     *
     * @return the extra margin (in meters)
     */
    float getExtraConvexRadius();

    /**
     * Test whether to calculate penetration for the starting point. The
     * settings are unaffected.
     *
     * @return {@code true} to enable, {@code false} to disable
     */
    boolean getReturnDeepestPoint();

    /**
     * Test whether the shape should be shrunk and then expanded by the convex
     * radius. The settings are unaffected.
     *
     * @return {@code true} to enable, {@code false} to disable
     */
    boolean getUseShrunkenShapeAndConvexRadius();
}
