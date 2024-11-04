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
 * Information about a narrow-phase ray cast hitting a shape.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ShapeCastResult extends CollideShapeResult {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a result with the specified container and native object.
     *
     * @param underlying the containing object, or {@code null} if none
     * @param castResultVa the virtual address of the native object to assign
     * (not zero)
     */
    ShapeCastResult(JoltPhysicsObject underlying, long castResultVa) {
        super(underlying, castResultVa);
    }

    /**
     * Instantiate a cast result with the specified native object assigned but
     * not owned.
     * <p>
     * For use in custom collectors.
     *
     * @param castResultVa the virtual address of the native object to assign
     * (not zero)
     */
    public ShapeCastResult(long castResultVa) {
        super(castResultVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the fraction of the cast where the hit occurred. The result object
     * is unaffected. (native attribute: mFraction)
     *
     * @return the fraction (&ge;0, &le;1, 0&rarr;start, 1&rarr;end)
     */
    public float getFraction() {
        long castResultVa = va();
        float result = getFraction(castResultVa);

        return result;
    }

    /**
     * Test whether the shape was hit from its back side. The result object is
     * unaffected. (native attribute: mIsBackFaceHit)
     *
     * @return {@code true} if hit from back side, otherwise {@code false}
     */
    public boolean getIsBackFaceHit() {
        long castResultVa = va();
        boolean result = getIsBackFaceHit(castResultVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static float getFraction(long castResultVa);

    native private static boolean getIsBackFaceHit(long castResultVa);
}
