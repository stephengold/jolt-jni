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

import com.github.stephengold.joltjni.readonly.ConstLod;

/**
 * A level of detail (LOD) in a Geometry. (native type:
 * {@code DebugRenderer::LOD})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Lod extends JoltPhysicsObject implements ConstLod {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default LOD.
     */
    public Lod() {
        long lodVa = createDefault();
        setVirtualAddress(lodVa, () -> free(lodVa));
    }

    /**
     * Instantiate a copy of the specified LOD.
     *
     * @param original the LOD to duplicate (not null, unaffected)
     */
    public Lod(ConstLod original) {
        long originalVa = original.targetVa();
        long lodVa = createCopy(originalVa);
        setVirtualAddress(lodVa, () -> free(lodVa));
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param lodVa the virtual address of the native object to assign (not
     * zero)
     */
    Lod(JoltPhysicsObject container, long lodVa) {
        super(container, lodVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the distance. (native member: mDistance)
     *
     * @param distance the desired distance (in meters)
     */
    public void setDistance(float distance) {
        long lodVa = va();
        setDistance(lodVa, distance);
    }
    // *************************************************************************
    // ConstLod methods

    /**
     * Return the distance. The LOD is unaffected. (native member: mDistance)
     *
     * @return the distance (in meters)
     */
    @Override
    public float getDistance() {
        long lodVa = va();
        float result = getDistance(lodVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static void free(long lodVa);

    native private static float getDistance(long lodVa);

    native private static void setDistance(long lodVa, float distance);
}
