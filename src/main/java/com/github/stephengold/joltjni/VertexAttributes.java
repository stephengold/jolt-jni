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

import com.github.stephengold.joltjni.enumerate.ELraType;
import com.github.stephengold.joltjni.readonly.ConstVertexAttributes;

/**
 * Per-vertex attributes used to configure the constraints in a soft body.
 * (native type: {@code SoftBodySharedSettings::VertexAttributes})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class VertexAttributes
        extends JoltPhysicsObject
        implements ConstVertexAttributes {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default attributes.
     */
    public VertexAttributes() {
        long attributesVa = createDefault();
        setVirtualAddress(attributesVa, () -> free(attributesVa));
    }

    /**
     * Instantiate attributes as specified.
     *
     * @param compliance the desired compliance of normal/regular edges
     * (default=0)
     * @param shearCompliance the desired compliance of the shear edges
     * (default=0)
     * @param bendCompliance the desired compliance of bend edges
     * (default=MAX_VALUE)
     */
    public VertexAttributes(float compliance, float shearCompliance,
            float bendCompliance) {
        this(compliance, shearCompliance, bendCompliance, ELraType.None);
    }

    /**
     * Instantiate attributes as specified.
     *
     * @param compliance the desired compliance of normal/regular edges
     * (default=0)
     * @param shearCompliance the desired compliance of the shear edges
     * (default=0)
     * @param bendCompliance the desired compliance of bend edges
     * (default=MAX_VALUE)
     * @param lraType the desired long-range attachment (LRA) constraint (not
     * null, default=None)
     */
    public VertexAttributes(float compliance, float shearCompliance,
            float bendCompliance, ELraType lraType) {
        this(compliance, shearCompliance, bendCompliance, lraType, 1f);
    }

    /**
     * Instantiate attributes as specified.
     *
     * @param compliance the desired compliance of normal/regular edges
     * (default=0)
     * @param shearCompliance the desired compliance of the shear edges
     * (default=0)
     * @param bendCompliance the desired compliance of bend edges
     * (default=MAX_VALUE)
     * @param lraType the desired long-range attachment (LRA) constraint (not
     * null, default=None)
     * @param lraMultiplier the desired multiplier for the maximum distance of
     * the LRA constraint (relative to the rest-pose distance, default=1)
     */
    public VertexAttributes(float compliance, float shearCompliance,
            float bendCompliance, ELraType lraType, float lraMultiplier) {
        int ordinal = lraType.ordinal();
        long attributesVa = createAttributes(compliance, shearCompliance,
                bendCompliance, ordinal, lraMultiplier);
        setVirtualAddress(attributesVa, () -> free(attributesVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the compliance of normal/regular edges. (native attribute:
     * mCompliance)
     *
     * @param compliance the desired compliance value
     * @return the argument, for chaining
     */
    public float setCompliance(float compliance) {
        long attributesVa = va();
        setCompliance(attributesVa, compliance);

        return compliance;
    }

    /**
     * Alter the type of the long-range attachment (LRA) constraint. (native
     * attribute: mLRAType)
     *
     * @param type the desired type (not null)
     */
    public void setLraType(ELraType type) {
        long attributesVa = va();
        int ordinal = type.ordinal();
        setLraType(attributesVa, ordinal);
    }

    /**
     * Alter the compliance of the shear edges. (native attribute:
     * mShearCompliance)
     *
     * @param compliance the desired compliance value
     * @return the argument, for chaining
     */
    public float setShearCompliance(float compliance) {
        long attributesVa = va();
        setShearCompliance(attributesVa, compliance);

        return compliance;
    }
    // *************************************************************************
    // ConstVertexAttributes methods

    /**
     * Return the compliance of bend edges. The attributes are unaffected.
     * (native attribute: mBendCompliance)
     *
     * @return the compliance value
     */
    @Override
    public float getBendCompliance() {
        long attributesVa = va();
        float result = getBendCompliance(attributesVa);

        return result;
    }

    /**
     * Return the compliance of normal/regular edges. The attributes are
     * unaffected. (native attribute: mCompliance)
     *
     * @return the compliance value
     */
    @Override
    public float getCompliance() {
        long attributesVa = va();
        float result = getCompliance(attributesVa);

        return result;
    }

    /**
     * Return the multiplier for the maximum distance of the long-range
     * attachment (LRA) constraint. The attributes are unaffected. (native
     * attribute: mLRAMaxDistanceMultiplier)
     *
     * @return the multiplier (relative to the rest-pose distance)
     */
    @Override
    public float getLraMaxDistanceMultiplier() {
        long attributesVa = va();
        float result = getLraMaxDistanceMultiplier(attributesVa);

        return result;
    }

    /**
     * Return the type of the long-range attachment (LRA) constraint. The
     * attributes are unaffected. (native attribute: mLRAType)
     *
     * @return the enum value (not null)
     */
    @Override
    public ELraType getLraType() {
        long attributesVa = va();
        int ordinal = getLraType(attributesVa);
        ELraType result = ELraType.values()[ordinal];

        return result;
    }

    /**
     * Return the compliance of the shear edges. The attributes are unaffected.
     * (native attribute: mShearCompliance)
     *
     * @return the compliance value
     */
    @Override
    public float getShearCompliance() {
        long attributesVa = va();
        float result = getShearCompliance(attributesVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createAttributes(
            float compliance, float shearCompliance, float bendCompliance,
            int ordinal, float lraMultiplier);

    native private static long createDefault();

    native private static void free(long attributesVa);

    native private static float getBendCompliance(long attributesVa);

    native private static float getCompliance(long attributesVa);

    native private static float getLraMaxDistanceMultiplier(long attributesVa);

    native private static int getLraType(long attributesVa);

    native private static float getShearCompliance(long attributesVa);

    native private static void setCompliance(
            long attributesVa, float compliance);

    native private static void setLraType(long attributesVa, int ordinal);

    native private static void setShearCompliance(
            long attributesVa, float compliance);
}
