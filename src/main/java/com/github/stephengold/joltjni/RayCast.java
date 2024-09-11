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

import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * A ray cast with a single-precision start location.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class RayCast extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a ray cast with the specified endpoints.
     *
     * @param startLocation the desired start location (not null, unaffected)
     * @param offset the desired end offset from the start (not null,
     * unaffected)
     */
    public RayCast(Vec3Arg startLocation, Vec3Arg offset) {
        float startX = startLocation.getX();
        float startY = startLocation.getY();
        float startZ = startLocation.getZ();
        float dx = offset.getX();
        float dy = offset.getY();
        float dz = offset.getZ();
        long raycastVa = createRayCast(startX, startY, startZ, dx, dy, dz);
        setVirtualAddress(raycastVa, () -> free(raycastVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return a point on the ray with the specified fraction. The ray cast is
     * unaffected.
     *
     * @param fraction (0&rarr;start of ray, 1&rarr;end of ray)
     * @return a new location vector
     */
    public Vec3 getPointOnRay(float fraction) {
        long raycastVa = va();
        float x = getPointOnRayX(raycastVa, fraction);
        float y = getPointOnRayY(raycastVa, fraction);
        float z = getPointOnRayZ(raycastVa, fraction);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createRayCast(float startX, float startY,
            float startZ, float dx, float dy, float dz);

    native private static void free(long raycastVa);

    native private static float getPointOnRayX(long raycastVa, float fraction);

    native private static float getPointOnRayY(long raycastVa, float fraction);

    native private static float getPointOnRayZ(long raycastVa, float fraction);
}
