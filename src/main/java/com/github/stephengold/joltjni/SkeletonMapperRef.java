/*
Copyright (c) 2025-2026 Stephen Gold

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

import com.github.stephengold.joltjni.template.Ref;

/**
 * A counted reference to a {@code SkeletonMapper}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class SkeletonMapperRef extends Ref {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public SkeletonMapperRef() {
        long refVa = create();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a reference to the specified mapper.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param mapper the mapper to target (not {@code null})
     */
    SkeletonMapperRef(long refVa, SkeletonMapper mapper) {
        assert mapper != null;

        this.ptr = mapper;
        Runnable freeingAction = () -> free(refVa);
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // Ref methods

    /**
     * Access the targeted mapper, if any.
     *
     * @return the pre-existing object, or {@code null} if the reference is
     * empty
     */
    @Override
    public SkeletonMapper getPtr() {
        SkeletonMapper result = (SkeletonMapper) ptr;
        return result;
    }

    /**
     * Return the address of the native {@code SkeletonMapper}. No objects are
     * affected.
     *
     * @return the virtual address, or zero if the reference is empty
     */
    @Override
    public long targetVa() {
        long refVa = va();
        long result = getPtr(refVa);
        assert result == (ptr == null ? 0L : getPtr().va());

        return result;
    }

    /**
     * Create an additional counted reference to the targeted mapper.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public SkeletonMapperRef toRef() {
        SkeletonMapperRef result;
        if (ptr == null) {
            result = new SkeletonMapperRef();
        } else {
            long refVa = va();
            long copyVa = copy(refVa);
            SkeletonMapper mapper = (SkeletonMapper) ptr;
            result = new SkeletonMapperRef(copyVa, mapper);
        }

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long copy(long refVa);

    native private static long create();

    native static void free(long refVa);

    native private static long getPtr(long refVa);
}
