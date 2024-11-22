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
package testjoltjni.app.samples.convexcollision;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.operator.Op;
import com.github.stephengold.joltjni.readonly.*;
import testjoltjni.app.samples.*;
import static testjoltjni.app.samples.DebugRendererSP.*;
/**
 * A line-for-line Java translation of the Jolt Physics closest-point test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/ClosestPointTest.cpp
 */
public class ClosestPointTest extends Test{

public void PrePhysicsUpdate(  PreUpdateParams inParams)
{
	Vec3 pos=new Vec3(inParams.mCameraState.mPos);

	{
		// Normal tetrahedron
		Vec3 a=new Vec3(2, 0, 0);
		Vec3 b=new Vec3(1, 0, 1);
		Vec3 c=new Vec3(2, 0, 1);
		Vec3 d=new Vec3(1, 1, 0);

		TestTetra(pos, a, b, c, d);
	}

	{
		// Inside out tetrahedron
		Vec3 a=new Vec3(2, -2, 0);
		Vec3 b=new Vec3(1, -2, 1);
		Vec3 c=new Vec3(2, -2, 1);
		Vec3 d=new Vec3(1, -3, 0);

		TestTetra(pos, a, b, c, d);
	}

	{
		// Degenerate tetrahedron
		Vec3 a=new Vec3(2, 3, 0);
		Vec3 b = a;
		Vec3 c=new Vec3(2, 3, 1);
		Vec3 d=new Vec3(1, 4, 0);

		TestTetra(pos, a, b, c, d);
	}

	{
		// Degenerate tetrahedron
		Vec3 a=new Vec3(2, 6, 0);
		Vec3 b=new Vec3(1, 6, 1);
		Vec3 c = a;
		Vec3 d=new Vec3(1, 7, 0);

		TestTetra(pos, a, b, c, d);
	}

	{
		// Degenerate tetrahedron
		Vec3 a=new Vec3(2, 9, 0);
		Vec3 b=new Vec3(1, 9, 1);
		Vec3 c=new Vec3(2, 9, 1);
		Vec3 d = a;

		TestTetra(pos, a, b, c, d);
	}

	{
		// Degenerate tetrahedron
		Vec3 a=new Vec3(2, 12, 0);
		Vec3 b=new Vec3(1, 12, 1);
		Vec3 c = b;
		Vec3 d=new Vec3(1, 13, 0);

		TestTetra(pos, a, b, c, d);
	}

	{
		// Degenerate tetrahedron
		Vec3 a=new Vec3(2, 15, 0);
		Vec3 b=new Vec3(1, 15, 1);
		Vec3 c=new Vec3(2, 15, 1);
		Vec3 d = b;

		TestTetra(pos, a, b, c, d);
	}

	{
		// Degenerate tetrahedron
		Vec3 a=new Vec3(2, 18, 0);
		Vec3 b=new Vec3(1, 18, 1);
		Vec3 c=new Vec3(2, 18, 1);
		Vec3 d = c;

		TestTetra(pos, a, b, c, d);
	}

	{
		// Normal tri
		Vec3 a=new Vec3(5, 0, 0);
		Vec3 b=new Vec3(4, 0, 1);
		Vec3 c=new Vec3(5, 0, 1);

		TestTri(pos, a, b, c);
	}

	{
		// Degenerate tri
		Vec3 a=new Vec3(5, 3, 0);
		Vec3 b = a;
		Vec3 c=new Vec3(5, 3, 1);

		TestTri(pos, a, b, c);
	}

	{
		// Degenerate tri
		Vec3 a=new Vec3(5, 6, 0);
		Vec3 b=new Vec3(4, 6, 1);
		Vec3 c = a;

		TestTri(pos, a, b, c);
	}

	{
		// Degenerate tri
		Vec3 a=new Vec3(5, 9, 0);
		Vec3 b=new Vec3(4, 9, 1);
		Vec3 c = b;

		TestTri(pos, a, b, c);
	}

	{
		// Normal line
		Vec3 a=new Vec3(10, 0, 0);
		Vec3 b=new Vec3(9, 0, 1);
		TestLine(pos, a, b);
	}

	{
		// Degenerate line
		Vec3 a=new Vec3(10, 3, 0);
		Vec3 b = a;
		TestLine(pos, a, b);
	}
}

void TestLine(Vec3Arg inPosition, Vec3Arg inA, Vec3Arg inB)
{
	Vec3 a = Op.minus(inA , inPosition);
	Vec3 b = Op.minus(inB , inPosition);

	int[] set=new int[1];
	Vec3 closest = Op.plus(ClosestPoint.getClosestPointOnLine(a, b, set) , inPosition);

	DrawLineSP(mDebugRenderer, inA, inB, Color.sWhite);

	DrawMarkerSP(mDebugRenderer, closest, Color.sRed, 0.1f);

	if ((set[0] & 0b0001)!=0)
		DrawMarkerSP(mDebugRenderer, inA, Color.sYellow, 0.5f);
	if ((set[0] & 0b0010)!=0)
		DrawMarkerSP(mDebugRenderer, inB, Color.sYellow, 0.5f);

	Vec3 a2 = Op.minus(inA , closest);
	Vec3 b2 = Op.minus(inB , closest);

	float[] uv=new float[2];
	ClosestPoint.getBaryCentricCoordinates(a2, b2, uv);
	DrawWireSphereSP(mDebugRenderer, Op.plus(Op.star(uv[0] , inA) , Op.star(uv[1] , inB)), 0.05f, Color.sGreen);

	DrawText3DSP(mDebugRenderer, inA, "a");
	DrawText3DSP(mDebugRenderer, inB, "b");
}

void TestTri(Vec3Arg inPosition, Vec3Arg inA, Vec3Arg inB, Vec3Arg inC)
{
	Vec3 a = Op.minus(inA , inPosition);
	Vec3 b = Op.minus(inB , inPosition);
	Vec3 c = Op.minus(inC , inPosition);

	int[] set=new int[1];
	Vec3 closest = Op.plus(ClosestPoint.getClosestPointOnTriangle(a, b, c, set) , inPosition);

	DrawLineSP(mDebugRenderer, inA, inB, Color.sWhite);
	DrawLineSP(mDebugRenderer, inA, inC, Color.sWhite);
	DrawLineSP(mDebugRenderer, inB, inC, Color.sWhite);

	DrawTriangleSP(mDebugRenderer, inA, inB, inC, Color.sGrey);

	DrawMarkerSP(mDebugRenderer, closest, Color.sRed, 0.1f);

	if ((set[0] & 0b0001)!=0)
		DrawMarkerSP(mDebugRenderer, inA, Color.sYellow, 0.5f);
	if ((set[0] & 0b0010)!=0)
		DrawMarkerSP(mDebugRenderer, inB, Color.sYellow, 0.5f);
	if ((set[0] & 0b0100)!=0)
		DrawMarkerSP(mDebugRenderer, inC, Color.sYellow, 0.5f);

	Vec3 a2 = Op.minus(inA , closest);
	Vec3 b2 = Op.minus(inB , closest);
	Vec3 c2 = Op.minus(inC , closest);

	float[] uvw=new float[3];
	ClosestPoint.getBaryCentricCoordinates(a2, b2, c2, uvw);
	DrawWireSphereSP(mDebugRenderer, Vec3.sum(Op.star(uvw[0] , inA) , Op.star(uvw[1] , inB) , Op.star(uvw[2] , inC)), 0.05f, Color.sGreen);

	DrawText3DSP(mDebugRenderer, inA, "a");
	DrawText3DSP(mDebugRenderer, inB, "b");
	DrawText3DSP(mDebugRenderer, inC, "c");
}

void TestTetra(Vec3Arg inPosition, Vec3Arg inA, Vec3Arg inB, Vec3Arg inC, Vec3Arg inD)
{
	Vec3 a = Op.minus(inA , inPosition);
	Vec3 b = Op.minus(inB , inPosition);
	Vec3 c = Op.minus(inC , inPosition);
	Vec3 d = Op.minus(inD , inPosition);

	int[] set=new int[1];
	Vec3 closest = Op.plus(ClosestPoint.getClosestPointOnTetrahedron(a, b, c, d, set) , inPosition);

	DrawLineSP(mDebugRenderer, inA, inB, Color.sWhite);
	DrawLineSP(mDebugRenderer, inA, inC, Color.sWhite);
	DrawLineSP(mDebugRenderer, inA, inD, Color.sWhite);
	DrawLineSP(mDebugRenderer, inB, inC, Color.sWhite);
	DrawLineSP(mDebugRenderer, inB, inD, Color.sWhite);
	DrawLineSP(mDebugRenderer, inC, inD, Color.sWhite);

	DrawTriangleSP(mDebugRenderer, inA, inC, inB, Color.sGrey);
	DrawTriangleSP(mDebugRenderer, inA, inD, inC, Color.sGrey);
	DrawTriangleSP(mDebugRenderer, inA, inB, inD, Color.sGrey);
	DrawTriangleSP(mDebugRenderer, inB, inC, inD, Color.sGrey);

	DrawMarkerSP(mDebugRenderer, closest, Color.sRed, 0.1f);

	if ((set[0] & 0b0001)!=0)
		DrawMarkerSP(mDebugRenderer, inA, Color.sYellow, 0.5f);
	if ((set[0] & 0b0010)!=0)
		DrawMarkerSP(mDebugRenderer, inB, Color.sYellow, 0.5f);
	if ((set[0] & 0b0100)!=0)
		DrawMarkerSP(mDebugRenderer, inC, Color.sYellow, 0.5f);
	if ((set[0] & 0b1000)!=0)
		DrawMarkerSP(mDebugRenderer, inD, Color.sYellow, 0.5f);

	DrawText3DSP(mDebugRenderer, inA, "a");
	DrawText3DSP(mDebugRenderer, inB, "b");
	DrawText3DSP(mDebugRenderer, inC, "c");
	DrawText3DSP(mDebugRenderer, inD, "d");
}
}