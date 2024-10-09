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
 * Join 4 soft-body vertices into a tetrahedron with a preferred volume. (native
 * type: {@code SoftBodySharedSettings::Volume})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Volume extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default volume constraint.
     */
    public Volume() {
        long volumeVa = createDefault();
        setVirtualAddress(volumeVa, () -> free(volumeVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the inverse of the volume's stiffness. The volume is unaffected.
     * (native attribute: mCompliance)
     *
     * @return the compliance value
     */
    public float getCompliance() {
        long volumeVa = va();
        float result = getCompliance(volumeVa);

        return result;
    }

    /**
     * Return the rest size of the volume. The volume is unaffected. (native
     * attribute: mSixRestVolume)
     *
     * @return 6 times the rest volume of the tetrahedron (in cubic meters)
     */
    public float getSixRestVolume() {
        long volumeVa = va();
        float result = getSixRestVolume(volumeVa);

        return result;
    }

    /**
     * Return the mesh vertex at the specified corner. The volume is unaffected.
     * (native attribute: mVertex)
     *
     * @param indexInVolume which corner of the volume (0 or 1 or 2 or 3)
     * @return the mesh index of the vertex (&ge;0)
     */
    public int getVertex(int indexInVolume) {
        long volumeVa = va();
        int result = getVertex(volumeVa, indexInVolume);

        return result;
    }

    /**
     * Alter the stiffness of the volume. (native attribute: mCompliance)
     *
     * @param compliance the inverse of the desired stiffness (default=0)
     */
    public void setCompliance(float compliance) {
        long volumeVa = va();
        setCompliance(volumeVa, compliance);
    }

    /**
     * Alter the rest size of the volume. (native attribute: mSixRestVolume)
     *
     * @param sixVolume 6 times the desired rest volume (in cubic meters)
     */
    public void setSixRestVolume(float sixVolume) {
        long volumeVa = va();
        setSixRestVolume(volumeVa, sixVolume);
    }

    /**
     * Assign the specified mesh vertex to the volume. (native attribute:
     * mVertex)
     *
     * @param indexInVolume which corner of the tetrahedron (0 or 1 or 2 or 3)
     * @param indexInMesh the index of the vertex to assign (&ge;0)
     */
    public void setVertex(int indexInVolume, int indexInMesh) {
        long volumeVa = va();
        setVertex(volumeVa, indexInVolume, indexInMesh);
    }
    // *************************************************************************
    // native private methods

    native private static long createDefault();

    native private static void free(long volumeVa);

    native private static float getCompliance(long volumeVa);

    native private static float getSixRestVolume(long volumeVa);

    native private static int getVertex(long volumeVa, int indexInVolume);

    native private static void setCompliance(long volumeVa, float compliance);

    native private static void setSixRestVolume(long volumeVa, float restValue);

    native private static void setVertex(
            long volumeVa, int indexInVolume, int indexInMesh);
}
