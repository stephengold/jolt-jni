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
 * A path that follows a Hermite spline.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class PathConstraintPathHermite extends PathConstraintPath {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty path.
     */
    public PathConstraintPathHermite() {
        long pathVa = createDefault();
        setVirtualAddress(pathVa); // not the owner due to ref counting
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Add a point to the end of the path.
     *
     * @param location the location of the point (not null, unaffected)
     * @param tangent the tangent direction at the point (not null, unaffected)
     * @param normal the normal direction at the point (not null, unaffected)
     */
    public void addPoint(Vec3Arg location, Vec3Arg tangent, Vec3Arg normal) {
        long pathVa = va();
        float locX = location.getX();
        float locY = location.getY();
        float locZ = location.getZ();
        float tanX = tangent.getX();
        float tanY = tangent.getY();
        float tanZ = tangent.getZ();
        float nx = normal.getX();
        float ny = normal.getY();
        float nz = normal.getZ();
        addPoint(pathVa, locX, locY, locZ, tanX, tanY, tanZ, nx, ny, nz);
    }
    // *************************************************************************
    // native private methods

    native static void addPoint(long pathVa, float locX, float locY, float locZ,
            float tanX, float tanY, float tanZ, float nx, float ny, float nz);

    native static long createDefault();
}
