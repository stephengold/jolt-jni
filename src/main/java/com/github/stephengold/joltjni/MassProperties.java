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
 * The mass and inertial tensor of a {@code Body}. Used only during
 * construction.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class MassProperties
        extends JoltPhysicsObject
        implements ConstMassProperties {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default properties.
     */
    public MassProperties() {
        long propertiesVa = createMassProperties();
        setVirtualAddress(propertiesVa, () -> free(propertiesVa));
    }

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param propertiesVa the virtual address of the native object to assign
     * (not zero)
     * @param owner true &rarr; make the current object the owner, false &rarr;
     * the current object isn't the owner
     */
    MassProperties(long propertiesVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(propertiesVa) : null;
        setVirtualAddress(propertiesVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Rotate the inertia by the specified 3x3 matrix.
     *
     * @param rotation the rotation to apply (not null, unaffected)
     */
    public void rotate(Mat44Arg rotation) {
        long propertiesVa = va();
        long matrixVa = rotation.va();
        rotate(propertiesVa, matrixVa);
    }

    /**
     * Scale the mass and inertia by the specified factors. Note that factors
     * can be &lt;0 to flip the shape.
     *
     * @param scaleFactors the desired scaling (not null unaffected)
     */
    public void scale(Vec3Arg scaleFactors) {
        long propertiesVa = va();
        float x = scaleFactors.getX();
        float y = scaleFactors.getY();
        float z = scaleFactors.getZ();
        scale(propertiesVa, x, y, z);
    }

    /**
     * Set the mass and scale the inertia tensor to match.
     *
     * @param mass the desired mass (in kilograms, &ge;0)
     */
    public void scaleToMass(float mass) {
        long propertiesVa = va();
        scaleToMass(propertiesVa, mass);
    }

    /**
     * Alter the inertia tensor. (native field: mIntertia)
     *
     * @param inertia the desired value (not null, unaffected, default=zero)
     */
    public void setInertia(Mat44Arg inertia) {
        long propertiesVa = va();
        long matrixVa = inertia.va();
        setInertia(propertiesVa, matrixVa);
    }

    /**
     * Alter the mass. (native field: mMass)
     *
     * @param mass the desired mass (in kilograms, &ge;0, default=0)
     */
    public void setMass(float mass) {
        long propertiesVa = va();
        setMass(propertiesVa, mass);
    }

    /**
     * Translate the inertia by the specified offset.
     *
     * @param offset the amount of translation (not null, unaffected)
     */
    public void translate(Vec3Arg offset) {
        long propertiesVa = va();
        float x = offset.getX();
        float y = offset.getY();
        float z = offset.getZ();
        translate(propertiesVa, x, y, z);
    }
    // *************************************************************************
    // ConstMassProperties methods

    /**
     * Decompose the inertia tensor into a diagonal matrix and a right-handed
     * rotation matrix.
     *
     * @param storeRotation storage for the rotation matrix (not null, modified)
     * @param storeDiagonal storage for the diagonal matrix (not null, modified)
     * @return true if successful, otherwise false
     */
    @Override
    public boolean decomposePrincipalMomentsOfInertia(
            Mat44 storeRotation, Vec3 storeDiagonal) {
        long propertiesVa = va();
        long matrixVa = storeRotation.va();
        float[] diagonal = new float[3];
        boolean result = decomposePrincipalMomentsOfInertia(
                propertiesVa, matrixVa, diagonal);
        storeDiagonal.set(diagonal);

        return result;
    }

    /**
     * Copy the inertia tensor. (native field: mIntertia)
     *
     * @return a new matrix (in kilogram.meters squared)
     */
    @Override
    public Mat44 getInertia() {
        long propertiesVa = va();
        long matrixVa = getInertia(propertiesVa);
        Mat44 result = new Mat44(matrixVa, true);

        return result;
    }

    /**
     * Return the mass. (native field: mMass)
     *
     * @return the mass (in kilograms, &ge;0)
     */
    @Override
    public float getMass() {
        long propertiesVa = va();
        float result = getMass(propertiesVa);

        return result;
    }
    // *************************************************************************
    // Object methods

    /**
     * Return a string representation of the properties object, which is
     * unaffected.
     *
     * @return the string representation (not null, not empty)
     */
    @Override
    public String toString() {
        String result = "MassProperties(mass=" + getMass()
                + " inertia=" + getInertia() + ")";
        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createMassProperties();

    native private static boolean decomposePrincipalMomentsOfInertia(
            long propertiesVa, long matrixVa, float[] diagonal);

    native private static void free(long propertiesVa);

    native private static long getInertia(long propertiesVa);

    native private static float getMass(long propertiesVa);

    native private static void rotate(long propertiesVa, long matrixVa);

    native private static void scale(
            long propertiesVa, float x, float y, float z);

    native private static void scaleToMass(long propertiesVa, float mass);

    native private static void setInertia(long propertiesVa, long matrixVa);

    native private static void setMass(long propertiesVa, float mass);

    native private static void translate(
            long propertiesVa, float x, float y, float z);
}
