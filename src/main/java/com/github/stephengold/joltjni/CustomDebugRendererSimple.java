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

/**
 * A customizable {@code DebugRendererSimple}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CustomDebugRendererSimple extends DebugRendererSimple {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a customizable renderer.
     */
    public CustomDebugRendererSimple() {
        long rendererVa = create();
        setVirtualAddressAsOwner(rendererVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Draw the specified 3-D line. Meant to be overridden.
     *
     * @param x1 the X coordinate of the first endpoint
     * @param y1 the Y coordinate of the first endpoint
     * @param z1 the Z coordinate of the first endpoint
     * @param x2 the X coordinate of the 2nd endpoint
     * @param y2 the Y coordinate of the 2nd endpoint
     * @param z2 the Z coordinate of the 2nd endpoint
     * @param colorInt the color of the line
     */
    public void drawLine(double x1, double y1, double z1,
            double x2, double y2, double z2, int colorInt) {
        // do nothing
    }

    /**
     * Draw the specified 3-D text. Meant to be overridden.
     *
     * @param xx the X coordinate of the text
     * @param yy the Y coordinate of the text
     * @param zz the Z coordinate of the text
     * @param text the text to display (not null)
     * @param colorInt the color of the text
     * @param height the height of the text
     */
    public void drawText3d(double xx, double yy, double zz, String text,
            int colorInt, float height) {
        // do nothing
    }

    /**
     * Draw the specified 3-D triangle. Meant to be overridden.
     *
     * @param x1 the X coordinate of the first vertex
     * @param y1 the Y coordinate of the first vertex
     * @param z1 the Z coordinate of the first vertex
     * @param x2 the X coordinate of the 2nd vertex
     * @param y2 the Y coordinate of the 2nd vertex
     * @param z2 the Z coordinate of the 2nd vertex
     * @param x3 the X coordinate of the 3rd vertex
     * @param y3 the Y coordinate of the 3rd vertex
     * @param z3 the Z coordinate of the 3rd vertex
     * @param colorInt the color of the triangle
     * @param ordinal the ECastShadow ordinal
     */
    public void drawTriangle(double x1, double y1, double z1,
            double x2, double y2, double z2,
            double x3, double y3, double z3, int colorInt, int ordinal) {
        // do nothing
    }
    // *************************************************************************
    // native private methods

    native private long create();
}
