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

import com.github.stephengold.joltjni.readonly.ConstRodBendTwist;
import com.github.stephengold.joltjni.readonly.QuatArg;
import java.nio.FloatBuffer;

/**
 * Join 2 soft-body Cosserat rods with limited bending and twisting. (native
 * type: {@code SoftBodySharedSettings::RodBendTwist})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class RodBendTwist
        extends JoltPhysicsObject
        implements ConstRodBendTwist {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default constraint.
     */
    public RodBendTwist() {
        long constraintVa = createDefault();
        setVirtualAddress(constraintVa, () -> free(constraintVa));
    }

    /**
     * Instantiate a constraint joining the specified rods with infinite
     * stiffness.
     *
     * @param rod0 the index of the first rod (&ge;0)
     * @param rod1 the index of the 2nd rod (&ge;0)
     */
    public RodBendTwist(int rod0, int rod1) {
        this(rod0, rod1, 0f);
    }

    /**
     * Instantiate a constraint joining the specified rods with the specified
     * stiffness.
     *
     * @param rod0 the index of the first rod (&ge;0)
     * @param rod1 the index of the 2nd rod (&ge;0)
     * @param compliance the inverse of the desired stiffness (default=0)
     */
    public RodBendTwist(int rod0, int rod1, float compliance) {
        long rodVa = create(rod0, rod1, compliance);
        setVirtualAddress(rodVa, () -> free(rodVa));
    }

    /**
     * Instantiate a constraint with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param constraintVa the virtual address of the native object to assign
     * (not zero)
     */
    RodBendTwist(JoltPhysicsObject container, long constraintVa) {
        super(container, constraintVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the stiffness of the constraint. (native attribute: mCompliance)
     *
     * @param compliance the inverse of the desired stiffness (default=0)
     * @return the modified settings, for chaining
     */
    public RodBendTwist setCompliance(float compliance) {
        long constraintVa = va();
        setCompliance(constraintVa, compliance);

        return this;
    }

    /**
     * Alter the Omega0 rotation of the constraint. (native attribute: mOmega0)
     *
     * @param omega0 the desired rotation (not null, unaffected)
     * @return the modified settings, for chaining
     */
    public RodBendTwist setOmega0(QuatArg omega0) {
        long constraintVa = va();
        float qx = omega0.getX();
        float qy = omega0.getY();
        float qz = omega0.getZ();
        float qw = omega0.getW();
        setOmega0(constraintVa, qx, qy, qz, qw);

        return this;
    }

    /**
     * Assign the specified rod to the constraint. (native attribute: mRod)
     *
     * @param indexInConstraint which end of the constraint (0 or 1)
     * @param indexInMesh the index of the rod to assign (&ge;0)
     * @return the modified settings, for chaining
     */
    public RodBendTwist setRod(int indexInConstraint, int indexInMesh) {
        long constraintVa = va();
        setRod(constraintVa, indexInConstraint, indexInMesh);

        return this;
    }
    // *************************************************************************
    // ConstRodBendTwist methods

    /**
     * Return the inverse of the spring's stiffness. The constraint is
     * unaffected. (native attribute: mCompliance)
     *
     * @return the compliance value
     */
    @Override
    public float getCompliance() {
        long constraintVa = va();
        float result = getCompliance(constraintVa);

        return result;
    }

    /**
     * Return the Omega0 rotation of the constraint. The constraint is
     * unaffected. (native attribute: mOmega0)
     *
     * @return a new quaternion
     */
    @Override
    public Quat getOmega0() {
        long constraintVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getOmega0(constraintVa, storeFloats);
        Quat result = new Quat(storeFloats);

        return result;
    }

    /**
     * Return the rod at the specified end. The constraint is unaffected.
     * (native attribute: mRod)
     *
     * @param indexInConstraint which end of the constraint (0 or 1)
     * @return the index of the rod (&ge;0)
     */
    @Override
    public int getRod(int indexInConstraint) {
        long constraintVa = va();
        int result = getRod(constraintVa, indexInConstraint);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long create(int rod0, int rod1, float compliance);

    native private static long createDefault();

    native private static void free(long constraintVa);

    native private static float getCompliance(long constraintVa);

    native private static void getOmega0(
            long constraintVa, FloatBuffer storeFloats);

    native private static int getRod(long constraintVa, int indexInConstraint);

    native private static void setOmega0(
            long constraintVa, float qx, float qy, float qz, float qw);

    native private static void setCompliance(
            long constraintVa, float compliance);

    native private static void setRod(
            long constraintVa, int indexInConstraint, int indexInMesh);
}
