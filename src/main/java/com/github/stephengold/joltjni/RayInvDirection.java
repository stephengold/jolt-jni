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

import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * The reciprocal of a ray's direction.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class RayInvDirection extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default direction.
     */
    public RayInvDirection() {
        long dirVa = createDefault();
        setVirtualAddress(dirVa, () -> free(dirVa));
    }

    /**
     * Instantiate inverse of the specified direction.
     *
     * @param direction the direction to use (not null, unaffected)
     */
    public RayInvDirection(Vec3Arg direction) {
        float dx = direction.getX();
        float dy = direction.getY();
        float dz = direction.getZ();
        long dirVa = create(dx, dy, dz);
        setVirtualAddress(dirVa, () -> free(dirVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the inverse direction. The ray is unaffected. (native attribute:
     * mInvDirection)
     *
     * @return a new direction vector
     */
    public Vec3 getInvDirection() {
        long dirVa = va();
        FloatBuffer buffer = Temporaries.floatBuffer1.get();
        getInvDirection(dirVa, buffer);
        Vec3 result = new Vec3(buffer);

        return result;
    }

    /**
     * Specify a new direction.
     *
     * @param direction the direction to use (not null, unaffected)
     */
    public void set(Vec3Arg direction) {
        long dirVa = va();
        float dx = direction.getX();
        float dy = direction.getY();
        float dz = direction.getZ();
        set(dirVa, dx, dy, dz);
    }

    /**
     * Directly alter the inverse direction. (native attribute: mInvDirection)
     *
     * @param invDirection the desired inverse direction (not null, unaffected)
     */
    public void setInvDirection(Vec3Arg invDirection) {
        long dirVa = va();
        float ix = invDirection.getX();
        float iy = invDirection.getY();
        float iz = invDirection.getZ();
        setInvDirection(dirVa, ix, iy, iz);
    }
    // *************************************************************************
    // native private methods

    native private static long create(float dx, float dy, float dz);

    native private static long createDefault();

    native private static void free(long dirVa);

    native private static void getInvDirection(long dirVa, FloatBuffer buffer);

    native private static void set(long dirVa, float dx, float dy, float dz);

    native private static void setInvDirection(
            long dirVa, float ix, float iy, float iz);
}
