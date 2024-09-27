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

import com.github.stephengold.joltjni.enumerate.ECastShadow;
import com.github.stephengold.joltjni.enumerate.EDrawMode;
import com.github.stephengold.joltjni.readonly.ConstAaBox;
import com.github.stephengold.joltjni.readonly.ConstColor;
import com.github.stephengold.joltjni.readonly.ConstOrientedBox;
import com.github.stephengold.joltjni.readonly.RMat44Arg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Visualization for debugging purposes.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class DebugRenderer extends NonCopyable {
    // *************************************************************************
    // fields

    /**
     * only one instance (singleton class)
     */
    private static DebugRenderer instance;
    // *************************************************************************
    // constructors

    /**
     * Instantiate with no native object assigned.
     */
    DebugRenderer() {
        // The native object is a singleton.
        // If a previous instance hasn't been freed,
        // a (native) assertion failure is possible.

        if (instance != null) {
            instance.close();
        }
        instance = this;
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Draw the specified 3-D arrow.
     *
     * @param from the desired starting point (not null, unaffected)
     * @param to the desired ending point (not null, unaffected)
     * @param color the desired color (not null, unaffected)
     * @param size the desired size
     */
    public void drawArrow(
            RVec3Arg from, RVec3Arg to, ConstColor color, float size) {
        double fromX = from.xx();
        double fromY = from.yy();
        double fromZ = from.zz();
        double toX = to.xx();
        double toY = to.yy();
        double toZ = to.zz();
        int colorInt = color.getUInt32();
        drawArrow(fromX, fromY, fromZ, toX, toY, toZ, colorInt, size);
    }

    /**
     * Draw the specified 3-D box.
     *
     * @param transform the desired coordinate transform (not null, unaffected)
     * @param box the desired geometric properties (not null, unaffected)
     * @param color the desired color (not null, unaffected)
     */
    public void drawBox(RMat44Arg transform, ConstAaBox box, ConstColor color) {
        drawBox(transform, box, color, ECastShadow.On);
    }

    /**
     * Draw the specified 3-D box.
     *
     * @param transform the desired coordinate transform (not null, unaffected)
     * @param box the desired geometric properties (not null, unaffected)
     * @param color the desired color (not null, unaffected)
     * @param castShadow the desired shadow mode (not null, default=On)
     */
    public void drawBox(RMat44Arg transform, ConstAaBox box, ConstColor color,
            ECastShadow castShadow) {
        drawBox(transform, box, color, castShadow, EDrawMode.Solid);
    }

    /**
     * Draw the specified 3-D box.
     *
     * @param transform the desired coordinate transform (not null, unaffected)
     * @param box the desired geometric properties (not null, unaffected)
     * @param color the desired color (not null, unaffected)
     * @param castShadow the desired shadow mode (not null, default=On)
     * @param drawMode the desired draw mode (not null, default=Solid)
     */
    public void drawBox(RMat44Arg transform, ConstAaBox box, ConstColor color,
            ECastShadow castShadow, EDrawMode drawMode) {
        long transformVa = transform.va();
        long boxVa = box.va();
        int colorInt = color.getUInt32();
        int csOrdinal = castShadow.ordinal();
        int drawModeOrdinal = drawMode.ordinal();
        drawBox(transformVa, boxVa, colorInt, csOrdinal, drawModeOrdinal);
    }

    /**
     * Draw the specified capsule.
     *
     * @param transform the desired coordinate transform (not null, unaffected)
     * @param halfHeight half the desired height of the cylindrical portion
     * @param radius the desired radius of the capsule
     * @param color the desired color (not null, unaffected)
     */
    public void drawCapsule(RMat44Arg transform, float halfHeight, float radius,
            ConstColor color) {
        drawCapsule(transform, halfHeight, radius, color, ECastShadow.On);
    }

    /**
     * Draw the specified capsule.
     *
     * @param transform the desired coordinate transform (not null, unaffected)
     * @param halfHeight half the desired height of the cylindrical portion
     * @param radius the desired radius of the capsule
     * @param color the desired color (not null, unaffected)
     * @param castShadow the desired shadow mode (not null, default=On)
     */
    public void drawCapsule(RMat44Arg transform, float halfHeight, float radius,
            ConstColor color, ECastShadow castShadow) {
        drawCapsule(transform, halfHeight, radius, color, castShadow,
                EDrawMode.Solid);
    }

    /**
     * Draw the specified capsule.
     *
     * @param transform the desired coordinate transform (not null, unaffected)
     * @param halfHeight half the desired height of the cylindrical portion
     * @param radius the desired radius of the capsule
     * @param color the desired color (not null, unaffected)
     * @param castShadow the desired shadow mode (not null, default=On)
     * @param drawMode the desired draw mode (not null, default=Solid)
     */
    public void drawCapsule(RMat44Arg transform, float halfHeight, float radius,
            ConstColor color, ECastShadow castShadow, EDrawMode drawMode) {
        long transformVa = transform.va();
        int colorInt = color.getUInt32();
        int csOrdinal = castShadow.ordinal();
        int drawModeOrdinal = drawMode.ordinal();
        drawCapsule(transformVa, halfHeight, radius, colorInt, csOrdinal,
                drawModeOrdinal);
    }

    /**
     * Draw the specified 3-D coordinate axes.
     *
     * @param transform the desired coordinate transform (not null, unaffected)
     */
    public void drawCoordinateSystem(RMat44Arg transform) {
        drawCoordinateSystem(transform, 1f);
    }

    /**
     * Draw the specified 3-D coordinate axes.
     *
     * @param transform the desired coordinate transform (not null, unaffected)
     * @param size the desired size (default=1)
     */
    public void drawCoordinateSystem(RMat44Arg transform, float size) {
        long transformVa = transform.va();
        drawCoordinateSystem(transformVa, size);
    }

    /**
     * Draw the specified cylinder.
     *
     * @param transform the desired coordinate transform (not null, unaffected)
     * @param halfHeight half of the desired height
     * @param radius the desired radius
     * @param color the desired color (not null, unaffected)
     */
    public void drawCylinder(RMat44Arg transform, float halfHeight,
            float radius, ConstColor color) {
        drawCylinder(transform, halfHeight, radius, color, ECastShadow.On);
    }

    /**
     * Draw the specified cylinder.
     *
     * @param transform the desired coordinate transform (not null, unaffected)
     * @param halfHeight half of the desired height
     * @param radius the desired radius
     * @param color the desired color (not null, unaffected)
     * @param castShadow the desired shadow mode (not null, default=On)
     */
    public void drawCylinder(
            RMat44Arg transform, float halfHeight, float radius,
            ConstColor color, ECastShadow castShadow) {
        drawCylinder(transform, halfHeight, radius, color, castShadow,
                EDrawMode.Solid);
    }

    /**
     * Draw the specified cylinder.
     *
     * @param transform the desired coordinate transform (not null, unaffected)
     * @param halfHeight half of the desired height
     * @param radius the desired radius
     * @param color the desired color (not null, unaffected)
     * @param castShadow the desired shadow mode (not null, default=On)
     * @param drawMode the desired draw mode (not null, default=Solid)
     */
    public void drawCylinder(
            RMat44Arg transform, float halfHeight, float radius,
            ConstColor color, ECastShadow castShadow, EDrawMode drawMode) {
        long transformVa = transform.va();
        int colorInt = color.getUInt32();
        int csOrdinal = castShadow.ordinal();
        int drawModeOrdinal = drawMode.ordinal();
        drawCylinder(transformVa, halfHeight, radius, colorInt, csOrdinal,
                drawModeOrdinal);
    }

    /**
     * Draw the specified 3-D line.
     *
     * @param from the desired first endpoint (not null, unaffected)
     * @param to the desired 2nd endpoint (not null, unaffected)
     * @param color the desired color (not null, unaffected)
     */
    public void drawLine(RVec3Arg from, RVec3Arg to, ConstColor color) {
        double fromX = from.xx();
        double fromY = from.yy();
        double fromZ = from.zz();
        double toX = to.xx();
        double toY = to.yy();
        double toZ = to.zz();
        int colorInt = color.getUInt32();
        drawLine(fromX, fromY, fromZ, toX, toY, toZ, colorInt);
    }

    /**
     * Draw a marker at the specified location.
     *
     * @param location the desired location (not null, unaffected)
     * @param color the desired color (not null, unaffected)
     * @param size the desired size
     */
    public void drawMarker(RVec3Arg location, ConstColor color, float size) {
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        int colorInt = color.getUInt32();
        drawMarker(locX, locY, locZ, colorInt, size);
    }

    /**
     * Draw the specified plane.
     *
     * @param location the location of a point through which the plane passes
     * (not null, unaffected)
     * @param normal a direction normal to the plane's surface (not null,
     * unaffected)
     * @param color the desired color (not null, unaffected)
     * @param size the desired size
     */
    public void drawPlane(
            RVec3Arg location, Vec3Arg normal, ConstColor color, float size) {
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        float normX = normal.getX();
        float normY = normal.getY();
        float normZ = normal.getZ();
        int colorInt = color.getUInt32();
        drawPlane(locX, locY, locZ, normX, normY, normZ, colorInt, size);
    }

    /**
     * Draw the specified sphere.
     *
     * @param location the location of the sphere's center (not null,
     * unaffected)
     * @param radius the desired radius
     * @param color the desired color (not null, unaffected)
     */
    public void drawSphere(RVec3Arg location, float radius, ConstColor color) {
        drawSphere(location, radius, color, ECastShadow.On);
    }

    /**
     * Draw the specified sphere.
     *
     * @param location the location of the sphere's center (not null,
     * unaffected)
     * @param radius the desired radius
     * @param color the desired color (not null, unaffected)
     * @param castShadow the desired shadow mode (not null, default=On)
     */
    public void drawSphere(RVec3Arg location, float radius, ConstColor color,
            ECastShadow castShadow) {
        drawSphere(location, radius, color, castShadow, EDrawMode.Solid);
    }

    /**
     * Draw the specified sphere.
     *
     * @param location the location of the sphere's center (not null,
     * unaffected)
     * @param radius the desired radius
     * @param color the desired color (not null, unaffected)
     * @param castShadow the desired shadow mode (not null, default=On)
     * @param drawMode the desired draw mode (not null, default=Solid)
     */
    public void drawSphere(RVec3Arg location, float radius, ConstColor color,
            ECastShadow castShadow, EDrawMode drawMode) {
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        int colorInt = color.getUInt32();
        int csOrdinal = castShadow.ordinal();
        int drawModeOrdinal = drawMode.ordinal();
        drawSphere(
                locX, locY, locZ, radius, colorInt, csOrdinal, drawModeOrdinal);
    }

    /**
     * Draw the specified 3-D text.
     *
     * @param location the location of the text (not null, unaffected)
     * @param text the text to display (not null)
     */
    public void drawText3D(RVec3Arg location, String text) {
        drawText3D(location, text, Color.sWhite);
    }

    /**
     * Draw the specified 3-D text.
     *
     * @param location the location of the text (not null, unaffected)
     * @param text the text to display (not null)
     * @param color the desired text color (not null, unaffected,
     * default=sWhite)
     */
    public void drawText3D(RVec3Arg location, String text, ConstColor color) {
        drawText3D(location, text, color, 0.5f);
    }

    /**
     * Draw the specified 3-D text.
     *
     * @param location the location of the text (not null, unaffected)
     * @param text the text to display (not null)
     * @param color the desired text color (not null, unaffected,
     * default=sWhite)
     * @param height the desired text height (default=0.5)
     */
    public void drawText3D(
            RVec3Arg location, String text, ConstColor color, float height) {
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        int colorInt = color.getUInt32();
        drawText3d(locX, locY, locZ, text, colorInt, height);
    }

    /**
     * Draw the specified 3-D triangle.
     *
     * @param v1 the location of the first vertex (not null, unaffected)
     * @param v2 the location of the 2nd vertex (not null, unaffected)
     * @param v3 the location of the 3rd vertex (not null, unaffected)
     * @param color the desired color (not null, unaffected)
     */
    public void drawTriangle(
            RVec3Arg v1, RVec3Arg v2, RVec3Arg v3, ConstColor color) {
        double v1x = v1.xx();
        double v1y = v1.yy();
        double v1z = v1.zz();
        double v2x = v2.xx();
        double v2y = v2.yy();
        double v2z = v2.zz();
        double v3x = v3.xx();
        double v3y = v3.yy();
        double v3z = v3.zz();
        int colorInt = color.getUInt32();
        drawTriangle(v1x, v1y, v1z, v2x, v2y, v2z, v3x, v3y, v3z, colorInt);
    }

    /**
     * Draw a wire-frame of the specified axis-aligned box.
     *
     * @param box the desired geometric properties (not null, unaffected)
     * @param color the desired color (not null, unaffected)
     */
    public void drawWireBox(ConstAaBox box, ConstColor color) {
        long boxVa = box.va();
        int colorInt = color.getUInt32();
        drawWireBoxAligned(boxVa, colorInt);
    }

    /**
     * Draw a wire-frame of the specified 3-D box.
     *
     * @param box the desired geometric properties (not null, unaffected)
     * @param color the desired color (not null, unaffected)
     */
    public void drawWireBox(ConstOrientedBox box, ConstColor color) {
        long boxVa = box.va();
        int colorInt = color.getUInt32();
        drawWireBoxOriented(boxVa, colorInt);
    }

    /**
     * Draw a wire frame of the specified 3-D box.
     *
     * @param transform the desired coordinate transform (not null, unaffected)
     * @param box the desired geometric properties (not null, unaffected)
     * @param color the desired color (not null, unaffected)
     */
    public void drawWireBox(
            RMat44Arg transform, ConstAaBox box, ConstColor color) {
        long transformVa = transform.va();
        long boxVa = box.va();
        int colorInt = color.getUInt32();
        drawWireBoxTransformed(transformVa, boxVa, colorInt);
    }

    /**
     * Draw a wire frame of the specified sphere.
     *
     * @param location the location of the sphere's center (not null,
     * unaffected)
     * @param radius the desired radius
     * @param color the desired color (not null, unaffected)
     */
    public void drawWireSphere(
            RVec3Arg location, float radius, ConstColor color) {
        drawWireSphere(location, radius, color, 3);
    }

    /**
     * Draw a wire frame of the specified sphere.
     *
     * @param location the location of the sphere's center (not null,
     * unaffected)
     * @param radius the desired radius
     * @param color the desired color (not null, unaffected)
     * @param level the desired level of detail (default=3)
     */
    public void drawWireSphere(
            RVec3Arg location, float radius, ConstColor color, int level) {
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        int colorInt = color.getUInt32();
        drawWireSphere(locX, locY, locZ, radius, colorInt, level);
    }

    /**
     * Draw a wire frame of the specified 3-D triangle.
     *
     * @param v1 the location of the first vertex (not null, unaffected)
     * @param v2 the location of the 2nd vertex (not null, unaffected)
     * @param v3 the location of the 3rd vertex (not null, unaffected)
     * @param color the desired color (not null, unaffected)
     */
    public void drawWireTriangle(
            RVec3Arg v1, RVec3Arg v2, RVec3Arg v3, ConstColor color) {
        double v1x = v1.xx();
        double v1y = v1.yy();
        double v1z = v1.zz();
        double v2x = v2.xx();
        double v2y = v2.yy();
        double v2z = v2.zz();
        double v3x = v3.xx();
        double v3y = v3.yy();
        double v3z = v3.zz();
        int colorInt = color.getUInt32();
        drawWireTriangle(v1x, v1y, v1z, v2x, v2y, v2z, v3x, v3y, v3z, colorInt);
    }

    /**
     * Draw a wire frame of the specified unit sphere.
     *
     * @param transform the desired coordinate transform (not null, unaffected)
     * @param color the desired color (not null, unaffected)
     */
    public void drawWireUnitSphere(RMat44Arg transform, ConstColor color) {
        drawWireUnitSphere(transform, color, 3);
    }

    /**
     * Draw a wire frame of the specified unit sphere.
     *
     * @param transform the desired coordinate transform (not null, unaffected)
     * @param color the desired color (not null, unaffected)
     * @param level the desired level of detail (default=3)
     */
    public void drawWireUnitSphere(
            RMat44Arg transform, ConstColor color, int level) {
        long transformVa = transform.va();
        int colorInt = color.getUInt32();
        drawWireUnitSphere(transformVa, colorInt, level);
    }

    /**
     * Notify that the current frame is complete.
     */
    native public void nextFrame();
    // *************************************************************************
    // NonCopyable methods

    /**
     * Assign a native object, assuming there's none already assigned.
     *
     * @param rendererVa the virtual address of the native object to assign (not
     * zero)
     * @param owner true &rarr; make the JVM object the owner, false &rarr; it
     * isn't the owner
     */
    @Override
    void setVirtualAddress(long rendererVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(rendererVa) : null;
        setVirtualAddress(rendererVa, freeingAction);
    }
    // *************************************************************************
    // native private methods

    native private static void drawArrow(
            double fromX, double fromY, double fromZ, double toX, double toY,
            double toZ, int colorInt, float size);

    native private static void drawBox(long transformVa, long boxVa,
            int colorInt, int csOrdinal, int drawModeOrdinal);

    native private static void drawCapsule(long transformVa, float halfHeight,
            float radius, int colorInt, int csOrdinal, int drawModeOrdinal);

    native private static void drawCoordinateSystem(
            long transformVa, float size);

    native private static void drawCylinder(long transformVa, float halfHeight,
            float radius, int colorInt, int csOrdinal, int drawModeOrdinal);

    native private static void drawLine(double fromX, double fromY,
            double fromZ, double toX, double toY, double toZ, int colorInt);

    native private static void drawMarker(
            double locX, double locY, double locZ, int colorInt, float size);

    native private static void drawPlane(double locX, double locY, double locZ,
            float normX, float normY, float normZ, int colorInt, float size);

    native private static void drawSphere(double locX, double locY, double locZ,
            float radius, int colorInt, int csOrdinal, int drawModeOrdinal);

    native private static void drawText3d(double locX, double locY, double locZ,
            String text, int colorInt, float height);

    native private static void drawTriangle(double v1x, double v1y,
            double v1z, double v2x, double v2y, double v2z, double v3x,
            double v3y, double v3z, int colorInt);

    native private static void drawWireBoxAligned(long boxVa, int colorInt);

    native private static void drawWireBoxOriented(long boxVa, int colorInt);

    native private static void drawWireBoxTransformed(
            long transformVa, long boxVa, int colorInt);

    native private static void drawWireSphere(double locX, double locY,
            double locZ, float radius, int colorInt, int level);

    native private static void drawWireTriangle(double v1x, double v1y,
            double v1z, double v2x, double v2y, double v2z, double v3x,
            double v3y, double v3z, int colorInt);

    native private static void drawWireUnitSphere(
            long transformVa, int colorInt, int level);

    native private static void free(long rendererVa);
}
