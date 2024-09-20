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
     * black
     */
    final public static ConstColor sBlack = new Color(0, 0, 0);
    /**
     * dark red
     */
    final public static ConstColor sDarkRed = new Color(128, 0, 0);
    /**
     * red
     */
    final public static ConstColor sRed = new Color(255, 0, 0);
    /**
     * dark green
     */
    final public static ConstColor sDarkGreen = new Color(0, 128, 0);
    /**
     * green
     */
    final public static ConstColor sGreen = new Color(0, 255, 0);
    /**
     * dark blue
     */
    final public static ConstColor sDarkBlue = new Color(0, 0, 128);
    /**
     * blue
     */
    final public static ConstColor sBlue = new Color(0, 0, 255);
    /**
     * yellow
     */
    final public static ConstColor sYellow = new Color(255, 255, 0);
    /**
     * purple
     */
    final public static ConstColor sPurple = new Color(255, 0, 255);
    /**
     * cyan
     */
    final public static ConstColor sCyan = new Color(0, 255, 255);
    /**
     * orange
     */
    final public static ConstColor sOrange = new Color(255, 128, 0);
    /**
     * dark orange
     */
    final public static ConstColor sDarkOrange = new Color(128, 64, 0);
    /**
     * grey
     */
    final public static ConstColor sGrey = new Color(128, 128, 128);
    /**
     * light grey
     */
    final public static ConstColor sLightGrey = new Color(192, 192, 192);
    /**
     * white
     */
    final public static ConstColor sWhite = new Color(255, 255, 255);
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
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether 2 colors are equal.
     *
     * @param c1 the first color to test (not null, unaffected)
     * @param c2 the 2nd color to test (not null, unaffected)
     * @return true if equal, false if unequal
     */
    public static boolean equals(ConstColor c1, ConstColor c2) {
        boolean result = c1.getA() == c2.getA()
                && c1.getB() == c2.getB()
                && c1.getG() == c2.getG()
                && c1.getR() == c2.getR();
        return result;
    }

    /**
     * Test whether 2 colors are unequal.
     *
     * @param c1 the first color to test (not null, unaffected)
     * @param c2 the 2nd color to test (not null, unaffected)
     * @return false if equal, true if unequal
     */
    public static boolean notEqual(ConstColor c1, ConstColor c2) {
        boolean result = c1.getA() != c2.getA()
                || c1.getB() != c2.getB()
                || c1.getG() != c2.getG()
                || c1.getR() != c2.getR();
        return result;
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
     * Alter the all components.
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
     * Create a color with the specified integer value.
     *
     * @param u32 the desired 32-bit value
     * @return a new object
     */
    public static Color sGetDistinctColor(int u32) {
        Color result = new Color(u32);
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
     * Return the 3th (blue) component. The color is unaffected.
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
