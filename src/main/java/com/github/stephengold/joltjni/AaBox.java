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
 * An axis-aligned box.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class AaBox extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a box with min=(FLT_MAX,FLT_MAX,FLT_MAX) and
     * max=(-FLT_MAX,-FLT_MAX,-FLT_MAX).
     */
    public AaBox() {
        long boxVa = createAaBox();
        setVirtualAddress(boxVa, true);
    }

    /**
     * Instantiate a box with the specified minimum and maximum coordinates.
     *
     * @param minimum the desired minimum coordinates (not null, unaffected)
     * @param maximum the desired maximum coordinates (not null, unaffected)
     */
    public AaBox(Vec3Arg minimum, Vec3Arg maximum) {
        float minX = minimum.getX();
        float minY = minimum.getY();
        float minZ = minimum.getZ();
        float maxX = maximum.getX();
        float maxY = maximum.getY();
        float maxZ = maximum.getZ();
        long boxVa = createAaBox(minX, minY, minZ, maxX, maxY, maxZ);
        setVirtualAddress(boxVa, true);
    }
    // *************************************************************************
    // JoltPhysicsObject methods

    @Override
    public void close() {
        assert ownsNativeObject();
        long virtualAddress = va();
        free(virtualAddress);

        unassignNativeObject();
    }
    // *************************************************************************
    // native private methods

    native private static long createAaBox();

    native private static long createAaBox(float minX, float minY, float minZ,
            float maxX, float maxY, float maxZ);

    native private static void free(long virtualAddress);
}
