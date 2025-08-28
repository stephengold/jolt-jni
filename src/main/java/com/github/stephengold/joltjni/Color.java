/*
Copyright (c) 2024-2025 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.ConstColor;

/**
 * An RGBA color with 8-bit components.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class Color implements ConstColor {
    // *************************************************************************
    // constants

    /**
     * series of colors for {@code sGetDistinctColor()}, pre-generated using
     * http://phrogz.net/css/distinct-colors.html
     */
    final private static ConstColor[] sColors = {
        new Color(255, 0, 0),
        new Color(204, 143, 102),
        new Color(226, 242, 0),
        new Color(41, 166, 124),
        new Color(0, 170, 255),
        new Color(69, 38, 153),
        new Color(153, 38, 130),
        new Color(229, 57, 80),
        new Color(204, 0, 0),
        new Color(255, 170, 0),
        new Color(85, 128, 0),
        new Color(64, 255, 217),
        new Color(0, 75, 140),
        new Color(161, 115, 230),
        new Color(242, 61, 157),
        new Color(178, 101, 89),
        new Color(140, 94, 0),
        new Color(181, 217, 108),
        new Color(64, 242, 255),
        new Color(77, 117, 153),
        new Color(157, 61, 242),
        new Color(140, 0, 56),
        new Color(127, 57, 32),
        new Color(204, 173, 51),
        new Color(64, 255, 64),
        new Color(38, 145, 153),
        new Color(0, 102, 255),
        new Color(242, 0, 226),
        new Color(153, 77, 107),
        new Color(229, 92, 0),
        new Color(140, 126, 70),
        new Color(0, 179, 71),
        new Color(0, 194, 242),
        new Color(27, 0, 204),
        new Color(230, 115, 222),
        new Color(127, 0, 17)
    };
    /**
     * black
     */
    final public static ConstColor sBlack = new Color(0, 0, 0);
    /**
     * blue
     */
    final public static ConstColor sBlue = new Color(0, 0, 255);
    /**
     * cyan
     */
    final public static ConstColor sCyan = new Color(0, 255, 255);
    /**
     * dark blue
     */
    final public static ConstColor sDarkBlue = new Color(0, 0, 128);
    /**
     * dark green
     */
    final public static ConstColor sDarkGreen = new Color(0, 128, 0);
    /**
     * dark orange
     */
    final public static ConstColor sDarkOrange = new Color(128, 64, 0);
    /**
     * dark red
     */
    final public static ConstColor sDarkRed = new Color(128, 0, 0);
    /**
     * green
     */
    final public static ConstColor sGreen = new Color(0, 255, 0);
    /**
     * grey
     */
    final public static ConstColor sGrey = new Color(128, 128, 128);
    /**
     * light grey
     */
    final public static ConstColor sLightGrey = new Color(192, 192, 192);
    /**
     * orange
     */
    final public static ConstColor sOrange = new Color(255, 128, 0);
    /**
     * purple
     */
    final public static ConstColor sPurple = new Color(255, 0, 255);
    /**
     * red
     */
    final public static ConstColor sRed = new Color(255, 0, 0);
    /**
     * white
     */
    final public static ConstColor sWhite = new Color(255, 255, 255);
    /**
     * yellow
     */
    final public static ConstColor sYellow = new Color(255, 255, 0);
    // *************************************************************************
    // fields

    /**
     * the 4th (alpha or opacity) component
     */
    private byte a;
    /**
     * the 3rd (blue) component
     */
    private byte b;
    /**
     * the 2nd (green) component
     */
    private byte g;
    /**
     * the first (red) component
     */
    private byte r;
    // *************************************************************************
    // constructors

    /**
     * Instantiate an uninitialized color.
     */
    public Color() {
    }

    /**
     * Instantiate a copy of an existing color.
     *
     * @param existing the color to copy (not null, unaffected)
     */
    public Color(ConstColor existing) {
        this.a = existing.getA();
        this.b = existing.getB();
        this.g = existing.getG();
        this.r = existing.getR();
    }

    /**
     * Instantiate a color from its integer value.
     *
     * @param u32 the 32-bit value
     */
    public Color(int u32) {
        this.a = (byte) (u32 >> 24);
        this.b = (byte) (u32 >> 16);
        this.g = (byte) (u32 >> 8);
        this.r = (byte) u32;
    }

    /**
     * Instantiate an opaque color from 3 components.
     *
     * @param r the desired first (red) component
     * @param g the desired 2nd (green) component
     * @param b the desired 3rd (blue) component
     */
    public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }

    /**
     * Instantiate a color from 4 components.
     *
     * @param r the desired first (red) component
     * @param g the desired 2nd (green) component
     * @param b the desired 3rd (blue) component
     * @param a the desired 4th (alpha or opacity) component
     */
    public Color(int r, int g, int b, int a) {
        this.a = (byte) a;
        this.b = (byte) b;
        this.g = (byte) g;
        this.r = (byte) r;
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Set all 4 components to specified values.
     *
     * @param r the desired first (red) component
     * @param g the desired 2nd (green) component
     * @param b the desired 3rd (blue) component
     * @param a the desired 4th (alpha or opacity) component
     */
    public void set(byte r, byte g, byte b, byte a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    /**
     * Copy all components of the argument to the current object.
     *
     * @param source the color to copy (not null, unaffected)
     */
    public void set(ConstColor source) {
        this.r = source.getR();
        this.g = source.getG();
        this.b = source.getB();
        this.a = source.getA();
    }

    /**
     * Alter the 4th (alpha or opacity) component.
     *
     * @param a the desired component value
     */
    public void setA(int a) {
        this.a = (byte) a;
    }

    /**
     * Alter the 3rd (blue) component.
     *
     * @param b the desired component value
     */
    public void setB(int b) {
        this.b = (byte) b;
    }

    /**
     * Alter the 2nd (green) component.
     *
     * @param g the desired component value
     */
    public void setG(int g) {
        this.g = (byte) g;
    }

    /**
     * Alter the first (red) component.
     *
     * @param r the desired component value
     */
    public void setR(int r) {
        this.r = (byte) r;
    }

    /**
     * Alter all components.
     *
     * @param u32 the desired 32-bit value
     */
    public void setU32(int u32) {
        this.a = (byte) (u32 >> 24);
        this.b = (byte) (u32 >> 16);
        this.g = (byte) (u32 >> 8);
        this.r = (byte) u32;
    }

    /**
     * Create a distinctive color.
     *
     * @param index the index
     * @return a new object
     */
    public static Color sGetDistinctColor(int index) {
        int j = index % sColors.length;
        Color result = new Color(sColors[j]);

        return result;
    }
    // *************************************************************************
    // ConstColor methods

    /**
     * Return the 4th (alpha or opacity) component. The color is unaffected.
     *
     * @return the component value
     */
    @Override
    public byte getA() {
        return a;
    }

    /**
     * Return the 3rd (blue) component. The color is unaffected.
     *
     * @return the component value
     */
    @Override
    public byte getB() {
        return b;
    }

    /**
     * Return the 2nd (green) component. The color is unaffected.
     *
     * @return the component value
     */
    @Override
    public byte getG() {
        return g;
    }

    /**
     * Return the first (red) component. The color is unaffected.
     *
     * @return the component value
     */
    @Override
    public byte getR() {
        return r;
    }

    /**
     * Return the integer value. The color is unaffected.
     *
     * @return the 32-bit value
     */
    @Override
    public int getUInt32() {
        int aa = ((int) a) << 24;
        int bb = ((int) b) << 16;
        int gg = ((int) g) << 8;
        int result = aa & 0xff000000 | bb & 0xff0000 | gg & 0xff00 | r & 0xff;

        return result;
    }
}
