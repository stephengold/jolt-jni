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
 * Properties of a character contact that can be overridden during certain
 * {@code CharacterContactListener} callbacks.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class CharacterContactSettings extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate settings with the specified native object assigned but not
     * owned.
     * <p>
     * For use in custom contact listeners.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    public CharacterContactSettings(long settingsVa) {
        setVirtualAddress(settingsVa, null);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether the other object can push the virtual character. The
     * settings are unaffected. (native attribute: mCanPushCharacter)
     *
     * @return true if pushing is allowed, otherwise false
     */
    public boolean getCanPushCharacter() {
        long settingsVa = va();
        boolean result = getCanPushCharacter(settingsVa);

        return result;
    }

    /**
     * Test whether the virtual character can apply impulses to the body. The
     * settings are unaffected. (native attribute: mCanReceiveImpulses)
     *
     * @return true if impulses can be applied, otherwise false
     */
    public boolean getCanReceiveImpulses() {
        long settingsVa = va();
        boolean result = getCanReceiveImpulses(settingsVa);

        return result;
    }

    /**
     * Alter whether the other object can push the virtual character. (native
     * attribute: mCanPushCharacter)
     *
     * @param setting true to allow pushing, false to prohibit it (default=true)
     */
    public void setCanPushCharacter(boolean setting) {
        long settingsVa = va();
        setCanPushCharacter(settingsVa, setting);
    }

    /**
     * Alter whether the virtual character can apply impulses to the body.
     * (native attribute: mCanReceiveImpulses)
     *
     * @param setting true to allow impulses, false to prohibit them
     * (default=true)
     */
    public void setCanReceiveImpulses(boolean setting) {
        long settingsVa = va();
        setCanReceiveImpulses(settingsVa, setting);
    }
    // *************************************************************************
    // native private methods

    native private static boolean getCanPushCharacter(long settingsVa);

    native private static boolean getCanReceiveImpulses(long settingsVa);

    native private static void setCanPushCharacter(
            long settingsVa, boolean setting);

    native private static void setCanReceiveImpulses(
            long settingsVa, boolean setting);
}
