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
package testjoltjni.app.samples;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.readonly.*;

/**
 * A line-for-line Java translation of the single-precision debug-renderer
 * functions.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Utils/DebugRendererSP.h
 */
public class DebugRendererSP {
// This file contains debug renderer functions that take single precision arguments for tests that do not need to deal with large worlds.
// They're split off so that we don't accidentally call single precision versions.

public static void DrawLineSP(DebugRenderer inRenderer, Vec3Arg inFrom, Vec3Arg inTo, ConstColor inColor)
{
	inRenderer.drawLine(new RVec3(inFrom), new RVec3(inTo), inColor);
}

public static void DrawMarkerSP(DebugRenderer inRenderer, Vec3Arg inPosition, ConstColor inColor, float inSize)
{
	inRenderer.drawMarker(new RVec3(inPosition), inColor, inSize);
}

public static void DrawArrowSP(DebugRenderer inRenderer, Vec3Arg inFrom, Vec3Arg inTo, ConstColor inColor, float inSize)
{
	inRenderer.drawArrow(new RVec3(inFrom), new RVec3(inTo), inColor, inSize);
}

public static void DrawTriangleSP(DebugRenderer inRenderer, Vec3Arg inV1, Vec3Arg inV2, Vec3Arg inV3, ConstColor inColor)
{
	inRenderer.drawTriangle(new RVec3(inV1), new RVec3(inV2), new RVec3(inV3), inColor);
}

public static void DrawWireBoxSP(DebugRenderer inRenderer, Mat44Arg inMatrix, ConstAaBox inBox, ConstColor inColor)
{
	inRenderer.drawWireBox(new RMat44(inMatrix), inBox, inColor);
}

public static void DrawBoxSP(DebugRenderer inRenderer, Mat44Arg inMatrix, ConstAaBox inBox, ConstColor inColor)
{
	inRenderer.drawBox(new RMat44(inMatrix), inBox, inColor);
}

public static void DrawBoxSP(DebugRenderer inRenderer, Mat44Arg inMatrix, ConstAaBox inBox, ConstColor inColor, ECastShadow inCastShadow, EDrawMode inDrawMode)
{
	inRenderer.drawBox(new RMat44(inMatrix), inBox, inColor, inCastShadow, inDrawMode);
}

public static void DrawWireSphereSP(DebugRenderer inRenderer, Vec3Arg inCenter, float inRadius, ConstColor inColor)
{
	inRenderer.drawWireSphere(new RVec3(inCenter), inRadius, inColor);
}

public static void DrawWireSphereSP(DebugRenderer inRenderer, Vec3Arg inCenter, float inRadius, ConstColor inColor, int inLevel)
{
	inRenderer.drawWireSphere(new RVec3(inCenter), inRadius, inColor, inLevel);
}

public static void DrawSphereSP(DebugRenderer inRenderer, Vec3Arg inCenter, float inRadius, ConstColor inColor)
{
	inRenderer.drawSphere(new RVec3(inCenter), inRadius, inColor);
}

public static void DrawSphereSP(DebugRenderer inRenderer, Vec3Arg inCenter, float inRadius, ConstColor inColor, ECastShadow inCastShadow, EDrawMode inDrawMode)
{
	inRenderer.drawSphere(new RVec3(inCenter), inRadius, inColor, inCastShadow, inDrawMode);
}

public static void DrawText3DSP(DebugRenderer inRenderer, Vec3Arg inPosition, String inString)
{
	inRenderer.drawText3D(new RVec3(inPosition), inString);
}

public static void DrawText3DSP(DebugRenderer inRenderer, Vec3Arg inPosition, String inString, ConstColor inColor)
{
	inRenderer.drawText3D(new RVec3(inPosition), inString, inColor);
}

public static void DrawText3DSP(DebugRenderer inRenderer, Vec3Arg inPosition, String inString, ConstColor inColor, float inHeight)
{
	inRenderer.drawText3D(new RVec3(inPosition), inString, inColor, inHeight);
}
}