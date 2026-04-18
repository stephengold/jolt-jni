/*
Copyright (c) 2026 Stephen Gold

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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.StateRecorder;

/**
 * Read-only access to a {@code ContactKey}. (native type: {@code const
 * CharacterVirtual::ContactKey})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstContactKey extends ConstJoltPhysicsObject {
    /**
     * Return the ID of the colliding body. The key is unaffected.
     *
     * @return the {@code BodyID} value
     */
    int getBodyB();

    /**
     * Return the ID of the colliding character. The key is unaffected.
     *
     * @return the {@code CharacterID} value
     */
    int getCharacterIdB();

    /**
     * Return the sub-shape ID of the colliding body. The key is unaffected.
     *
     * @return a {@code SubShapeID} value (typically negative)
     */
    int getSubShapeIdB();

    /**
     * Test for equivalence with another key. Both keys are unaffected.
     *
     * @param other the key to compare with (not {@code null})
     * @return {@code true} if equivalent, otherwise {@code false}
     */
    boolean isEqual(ConstContactKey other);

    /**
     * Test for equivalence with another key. Both keys are unaffected.
     *
     * @param other the key to compare with (not {@code null})
     * @return {@code false} if equivalent, otherwise {@code true}
     */
    boolean isNotEqual(ConstContactKey other);

    /**
     * Save the key to the specified recorder. The key is unaffected.
     *
     * @param recorder the recorder to save to (not {@code null})
     */
    void saveState(StateRecorder recorder);
}
