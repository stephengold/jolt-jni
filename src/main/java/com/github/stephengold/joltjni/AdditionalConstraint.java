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
 * Specify an additional constraint for use in a ragdoll. (native type:
 * {@code RagdollSettings::AdditionalConstraint})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class AdditionalConstraint extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an additional constraint.
     *
     * @param bodyIndex1 the index of the first body in the list
     * @param bodyIndex2 the index of the 2nd body in the list
     * @param settings the constraint settings to use (not null)
     */
    public AdditionalConstraint(int bodyIndex1, int bodyIndex2,
            TwoBodyConstraintSettings settings) {
        long settingsVa = settings.va();
        long constraintVa
                = createConstraint(bodyIndex1, bodyIndex2, settingsVa);
        setVirtualAddress(constraintVa, () -> free(constraintVa));
    }
    // *************************************************************************
    // native private methods

    native private static long createConstraint(
            int bodyIndex1, int bodyIndex2, long settingsVa);

    native private static void free(long constraintVa);
}
