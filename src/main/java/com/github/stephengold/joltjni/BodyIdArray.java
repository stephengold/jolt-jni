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

import java.nio.IntBuffer;
import java.util.List;

/**
 * A fixed-length array of body IDs. (native type: {@code BodyID[]})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BodyIdArray extends JoltPhysicsObject {
    // *************************************************************************
    // fields

    /**
     * length (in IDs)
     */
    final private int length;
    // *************************************************************************
    // constructors

    /**
     * Instantiate an array with the specified length.
     *
     * @param length the desired number of IDs (&ge;0)
     */
    public BodyIdArray(int length) {
        assert length >= 0 : length;

        this.length = length;
        long arrayVa = create(length);
        setVirtualAddress(arrayVa, () -> free(arrayVa));
    }

    /**
     * Instantiate an array initialized from a Java array.
     *
     * @param idArray the ID values (not null, unaffected)
     */
    public BodyIdArray(int[] idArray) {
        this.length = idArray.length;
        IntBuffer intBuffer = Jolt.newDirectIntBuffer(length);
        intBuffer.put(idArray);
        long arrayVa = createFromBuffer(intBuffer);
        setVirtualAddress(arrayVa, () -> free(arrayVa));
    }

    /**
     * Instantiate an array initialized from a direct buffer.
     *
     * @param idBuffer the ID values (not null, direct, unaffected)
     */
    public BodyIdArray(IntBuffer idBuffer) {
        assert idBuffer.isDirect();

        this.length = idBuffer.capacity();
        long arrayVa = createFromBuffer(idBuffer);
        setVirtualAddress(arrayVa, () -> free(arrayVa));
    }

    /**
     * Instantiate an array initialized from a Java list.
     *
     * @param idList the ID values (not null, unaffected)
     */
    public BodyIdArray(List<Integer> idList) {
        this.length = idList.size();
        IntBuffer intBuffer = Jolt.newDirectIntBuffer(length);
        for (int bodyId : idList) {
            intBuffer.put(bodyId);
        }

        long arrayVa = createFromBuffer(intBuffer);
        setVirtualAddress(arrayVa, () -> free(arrayVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the ID at the specified index.
     *
     * @param elementIndex the index from which to copy the ID (&ge;0)
     * @return the {@code BodyID} value
     */
    public int get(int elementIndex) {
        assert elementIndex >= 0 && elementIndex < length :
                "Out of range:  index=" + elementIndex + " length=" + length;

        long arrayVa = va();
        int result = getId(arrayVa, elementIndex);

        return result;
    }

    /**
     * Return the length of the array.
     *
     * @return the length (in IDs)
     */
    public int length() {
        return length;
    }

    /**
     * Store the specified ID at the specified index.
     *
     * @param elementIndex the index at which to store the ID (&ge;0)
     * @param bodyId the ID to store
     */
    public void set(int elementIndex, int bodyId) {
        assert elementIndex >= 0 && elementIndex < length :
                "Out of range:  index=" + elementIndex + " length=" + length;

        long arrayVa = va();
        setId(arrayVa, elementIndex, bodyId);
    }
    // *************************************************************************
    // native private methods

    native private static long create(int length);

    native private static long createFromBuffer(IntBuffer ids);

    native private static void free(long arrayVa);

    native private static int getId(long arrayVa, int elementIndex);

    native private static void setId(
            long arrayVa, int elementIndex, int bodyId);
}
