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

import com.github.stephengold.joltjni.enumerate.EConstraintSubType;
import com.github.stephengold.joltjni.readonly.ConstConstraintSettings;

/**
 * Settings used to generate a rigid body in a {@code Ragdoll}. (native type:
 * {@code RagdollSettings::Part})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Part extends BodyCreationSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate settings with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param partVa the virtual address of the native object to assign (not
     * zero)
     */
    Part(JoltPhysicsObject container, long partVa) {
        super(container, partVa);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public Part(Part original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddressAsOwner(copyVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the settings to create the joint to the part's parent. The part is
     * unaffected. (native attribute: mToParent)
     *
     * @param subType the expected type of constraint (not null)
     * @return a new JVM with the pre-existing native object assigned
     */
    public ConstraintSettings getToParent(EConstraintSubType subType) {
        long partVa = va();
        int ordinal = subType.ordinal();
        long settingsVa = getToParent(partVa, ordinal);
        ConstraintSettings result
                = ConstraintSettings.newConstraintSettings(settingsVa);

        return result;
    }

    /**
     * Alter the settings to create the joint to the part's parent. (native
     * attribute: mToParent)
     *
     * @param settings the desired settings (not null)
     */
    public void setToParent(ConstConstraintSettings settings) {
        long partVa = va();
        long settingsVa = settings.targetVa();
        setToParent(partVa, settingsVa);
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long getToParent(long partVa, int ordinal);

    native private static void setToParent(long partVa, long settingsVa);
}
