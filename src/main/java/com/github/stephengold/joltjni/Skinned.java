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
 * A constraint that skins a soft-body vertex. (native type:
 * {@code SoftBodySharedSettings::Skinned})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Skinned extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default constraint.
     */
    public Skinned() {
        long skinnedVa = createDefault();
        setVirtualAddress(skinnedVa, () -> free(skinnedVa));
    }

    /**
     * Instantiate a backstopped constraint for the specified vertex.
     *
     * @param vertexIndex the index of the vertex to constrain (&ge;0)
     * @param maxDistance the maximum distance
     * @param backstopDistance the backstop distance
     * @param backstopRadius the backstop radius
     */
    public Skinned(int vertexIndex, float maxDistance, float backstopDistance,
            float backstopRadius) {
        long skinnedVa = createBackstopped(
                vertexIndex, maxDistance, backstopDistance, backstopRadius);
        setVirtualAddress(skinnedVa, () -> free(skinnedVa));
    }

    /**
     * Instantiate a copy of the specified constraint.
     *
     * @param original the constraint to copy (not {@code null}, unaffected)
     */
    public Skinned(Skinned original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa, () -> free(copyVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the specified weight. (native attribute: mWeight)
     *
     * @param index an index into the weight array (&ge;0, &lt;4)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public SkinWeight getWeight(int index) {
        if (index < 0 || index >= 4) {
            throw new IllegalArgumentException("index must be 0, 1, 2, or 3");
        }

        long skinnedVa = va();
        long weightVa = getWeight(skinnedVa, index);
        SkinWeight result = new SkinWeight(this, weightVa);

        return result;
    }

    /**
     * Normalize the weights so that they sum to one.
     */
    public void normalizeWeights() {
        long skinnedVa = va();
        normalizeWeights(skinnedVa);
    }

    /**
     * Copy the specified weight into the array. (native attribute: mWeight)
     *
     * @param index an index into the weight array (&ge;0, &lt;4)
     * @param weight the desired weight (alias created)
     */
    public void setWeight(int index, SkinWeight weight) {
        if (index < 0 || index >= 4) {
            throw new IllegalArgumentException("index must be 0, 1, 2, or 3");
        }

        long skinnedVa = va();
        long weightVa = weight.va();
        setWeight(skinnedVa, index, weightVa);
    }
    // *************************************************************************
    // native private methods

    native private static long createBackstopped(int vertexIndex,
            float maxDistance, float backstopDistance, float backstopRadius);

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static void free(long skinnedVa);

    native private static long getWeight(long skinnedVa, int index);

    native private static void normalizeWeights(long skinnedVa);

    native private static void setWeight(
            long skinnedVa, int index, long weightVa);
}
