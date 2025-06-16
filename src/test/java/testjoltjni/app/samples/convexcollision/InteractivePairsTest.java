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
package testjoltjni.app.samples.convexcollision;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.readonly.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import static com.github.stephengold.joltjni.std.Std.*;
import static testjoltjni.app.samples.DebugRendererSP.*;

/**
 * A line-for-line Java translation of the Jolt Physics interactive pairs test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/ConvexCollision/InteractivePairsTest.cpp
 */
public class InteractivePairsTest extends Test{
boolean mKeyboardMode;
float mDistance = 3.0f;

/*TODO
void InteractivePairsTest::ProcessInput(const ProcessInputParams &inParams)
{
	// Keyboard controls
	if (inParams.mKeyboard->IsKeyPressed(EKey::Z))
	{
		mKeyboardMode = true;
		mDistance -= inParams.mDeltaTime;
	}
	else if (inParams.mKeyboard->IsKeyPressed(EKey::C))
	{
		mKeyboardMode = true;
		mDistance += inParams.mDeltaTime;
	}
	else if (inParams.mKeyboard->IsKeyPressed(EKey::X))
	{
		mKeyboardMode = false;
	}

	// Auto update
	if (!mKeyboardMode)
		mDistance -= inParams.mDeltaTime;

	// Clamp distance
	if (mDistance < -4.0f)
		mDistance = 4.0f;
	if (mDistance > 4.0f)
		mDistance = -4.0f;
}
*/

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	float z = 0.0f;

	final float r1 = 0.25f * JPH_PI;
	final float r2 = aTan(1.0f / (float)Math.sqrt(2.0f)); // When rotating cube by 45 degrees the one axis becomes sqrt(2) long while the other stays at length 1

	for (int i = 0; i < 2; ++i)
	{
		final float cvx_radius = i == 0? 0.0f : 0.1f; // First round without convex radius, second with
		final float edge_len = 1.0f - cvx_radius;
		AaBox b=new AaBox(new Vec3(-edge_len, -edge_len, -edge_len),new Vec3(edge_len, edge_len, edge_len));

		// Face vs face
		TestBoxVsBox(new Vec3(0, 0, z),new Vec3(0, 0, 0), cvx_radius, b,new Vec3(mDistance, 0, z),new Vec3(0, 0, 0), cvx_radius, b);
		z += 4;
		TestBoxVsBox(new Vec3(0, 0, z),new Vec3(0, 0, 0), cvx_radius, b,new Vec3(mDistance, 0, z),new Vec3(r1, 0, 0), cvx_radius, b);
		z += 4;

		// Face vs edge
		TestBoxVsBox(new Vec3(0, 0, z),new Vec3(0, 0, 0), cvx_radius, b,new Vec3(mDistance, 0, z),new Vec3(0, r1, 0), cvx_radius, b);
		z += 4;
		TestBoxVsBox(new Vec3(0, 0, z),new Vec3(0, 0, 0), cvx_radius, b,new Vec3(mDistance, 0, z),new Vec3(0, 0, r1), cvx_radius, b);
		z += 4;

		// Face vs vertex
		TestBoxVsBox(new Vec3(0, 0, z),new Vec3(0, 0, 0), cvx_radius, b,new Vec3(mDistance, 0, z),new Vec3(0, r2, r1), cvx_radius, b);
		z += 4;

		// Edge vs edge
		TestBoxVsBox(new Vec3(0, 0, z),new Vec3(0, r1, 0), cvx_radius, b,new Vec3(mDistance, 0, z),new Vec3(0, r1, 0), cvx_radius, b);
		z += 4;
		TestBoxVsBox(new Vec3(0, 0, z),new Vec3(0, 0, r1), cvx_radius, b,new Vec3(mDistance, 0, z),new Vec3(0, r1, 0), cvx_radius, b);
		z += 4;

		// Edge vs vertex
		TestBoxVsBox(new Vec3(0, 0, z),new Vec3(0, r2, r1), cvx_radius, b,new Vec3(mDistance, 0, z),new Vec3(0, r2, r1), cvx_radius, b);
		z += 4;

		// Sphere vs face
		TestSphereVsBox(new Vec3(0, 0, z), 1.0f,new Vec3(mDistance, 0, z),new Vec3(0, 0, 0), cvx_radius, b);
		z += 4;
		TestSphereVsBox(new Vec3(0, 0, z), 1.0f,new Vec3(mDistance, 0, z),new Vec3(r1, 0, 0), cvx_radius, b);
		z += 4;

		// Sphere vs edge
		TestSphereVsBox(new Vec3(0, 0, z), 1.0f,new Vec3(mDistance, 0, z),new Vec3(0, r1, 0), cvx_radius, b);
		z += 4;
		TestSphereVsBox(new Vec3(0, 0, z), 1.0f,new Vec3(mDistance, 0, z),new Vec3(0, 0, r1), cvx_radius, b);
		z += 4;

		// Sphere vs vertex
		TestSphereVsBox(new Vec3(0, 0, z), 1.0f,new Vec3(mDistance, 0, z),new Vec3(0, r2, r1), cvx_radius, b);
		z += 4;

		// Sphere vs sphere
		TestSphereVsSphere(new Vec3(0, 0, z), 1.0f,new Vec3(mDistance, 0, z), 1.0f, i == 1);
		z += 4;
	}
}

void TestBoxVsBox(Vec3Arg inTranslationA, Vec3Arg inRotationA, float inConvexRadiusA, ConstAaBox inA, Vec3Arg inTranslationB, Vec3Arg inRotationB, float inConvexRadiusB, ConstAaBox inB)
{
	Mat44 mat_a = Mat44.product(Mat44.sTranslation(inTranslationA) , Mat44.sRotationX(inRotationA.getX()) , Mat44.sRotationY(inRotationA.getY()) , Mat44.sRotationZ(inRotationA.getZ()));
	TransformedAaBox a=new TransformedAaBox(mat_a, inA);

	Mat44 mat_b = Mat44.product(Mat44.sTranslation(inTranslationB) , Mat44.sRotationX(inRotationB.getX()) , Mat44.sRotationY(inRotationB.getY()) , Mat44.sRotationZ(inRotationB.getZ()));
	TransformedAaBox b=new TransformedAaBox(mat_b, inB);

	EpaPenetrationDepth pen_depth=new EpaPenetrationDepth();
	Vec3 v = Vec3.sAxisX(), pa=new Vec3(), pb=new Vec3();

	DrawBoxSP(mDebugRenderer, mat_a, inA, Color.sWhite);

	AaBox widened_a = new AaBox(inA);
	widened_a.expandBy(Vec3.sReplicate(inConvexRadiusA));

	AaBox widened_b = new AaBox(inB);
	widened_b.expandBy(Vec3.sReplicate(inConvexRadiusB));

	DrawBoxSP(mDebugRenderer, mat_a, inA, Color.sWhite);
	if (inConvexRadiusA > 0.0f)
		DrawWireBoxSP(mDebugRenderer, mat_a, widened_a, Color.sWhite);

	AddConvexRadiusTab a_inc=new AddConvexRadiusTab(a, inConvexRadiusA);
	AddConvexRadiusTab b_inc=new AddConvexRadiusTab(b, inConvexRadiusB);

	if (pen_depth.getPenetrationDepth(a, a_inc, inConvexRadiusA, b, b_inc, inConvexRadiusB, 1.0e-4f, FLT_EPSILON, v, pa, pb))
	{
		DrawBoxSP(mDebugRenderer, mat_b, inB, Color.sRed);
		if (inConvexRadiusB > 0.0f)
			DrawWireBoxSP(mDebugRenderer, mat_b, widened_b, Color.sRed);
		DrawMarkerSP(mDebugRenderer, pa, Color.sYellow, 2.0f);
		DrawMarkerSP(mDebugRenderer, pb, Color.sCyan, 2.0f);
	}
	else
	{
		DrawBoxSP(mDebugRenderer, mat_b, inB, Color.sGreen);
		if (inConvexRadiusB > 0.0f)
			DrawWireBoxSP(mDebugRenderer, mat_b, widened_b, Color.sGreen);
	}
	DrawArrowSP(mDebugRenderer, plus(inTranslationB ,new Vec3(0, 2, 0)), Vec3.sum(inTranslationB , v ,new Vec3(0, 2, 0)), Color.sOrange, 0.05f);
}

void TestSphereVsBox(Vec3Arg inTranslationA, float inRadiusA, Vec3Arg inTranslationB, Vec3Arg inRotationB, float inConvexRadiusB, ConstAaBox inB)
{
	Sphere s=new Sphere(inTranslationA, inRadiusA);
	Mat44 mat_b = Mat44.product(Mat44.sTranslation(inTranslationB) , Mat44.sRotationX(inRotationB.getX()) , Mat44.sRotationY(inRotationB.getY()) , Mat44.sRotationZ(inRotationB.getZ()));
	TransformedAaBox b=new TransformedAaBox(mat_b, inB);

	AaBox widened_b = new AaBox(inB);
	widened_b.expandBy(Vec3.sReplicate(inConvexRadiusB));

	EpaPenetrationDepth pen_depth=new EpaPenetrationDepth();
	Vec3 v = Vec3.sAxisX(), pa=new Vec3(), pb=new Vec3();

	DrawSphereSP(mDebugRenderer, inTranslationA, inRadiusA, Color.sWhite);

	AddConvexRadiusTab b_inc=new AddConvexRadiusTab(b, inConvexRadiusB);

	if (pen_depth.getPenetrationDepth(s, s, 0.0f, b, b_inc, inConvexRadiusB, 1.0e-4f, FLT_EPSILON, v, pa, pb))
	{
		DrawBoxSP(mDebugRenderer, mat_b, inB, Color.sRed);
		if (inConvexRadiusB > 0.0f)
			DrawWireBoxSP(mDebugRenderer, mat_b, widened_b, Color.sRed);
		DrawMarkerSP(mDebugRenderer, pa, Color.sYellow, 2.0f);
		DrawMarkerSP(mDebugRenderer, pb, Color.sCyan, 2.0f);
	}
	else
	{
		DrawBoxSP(mDebugRenderer, mat_b, inB, Color.sGreen);
		if (inConvexRadiusB > 0.0f)
			DrawWireBoxSP(mDebugRenderer, mat_b, widened_b, Color.sGreen);
	}
	DrawArrowSP(mDebugRenderer, plus(inTranslationB ,new Vec3(0, 2, 0)), Vec3.sum(inTranslationB , v ,new Vec3(0, 2, 0)), Color.sOrange, 0.05f);
}

void TestSphereVsSphere(Vec3Arg inTranslationA, float inRadiusA, Vec3Arg inTranslationB, float inRadiusB, boolean inTreatSphereAsPointWithConvexRadius)
{
	Sphere s1=new Sphere(inTranslationA, inRadiusA);
	Sphere s2=new Sphere(inTranslationB, inRadiusB);

	if (inTreatSphereAsPointWithConvexRadius)
		DrawWireSphereSP(mDebugRenderer, s1.getCenter(), s1.getRadius(), Color.sWhite);
	else
		DrawSphereSP(mDebugRenderer, s1.getCenter(), s1.getRadius(), Color.sWhite);

	boolean intersects;
	EpaPenetrationDepth pen_depth=new EpaPenetrationDepth();
	Vec3 v = Vec3.sAxisX(), pa=new Vec3(), pb=new Vec3();
	if (inTreatSphereAsPointWithConvexRadius)
		intersects = pen_depth.getPenetrationDepth(new PointConvexSupport ( inTranslationA ), s1, inRadiusA,new PointConvexSupport ( inTranslationB ), s2, inRadiusB, 1.0e-4f, FLT_EPSILON, v, pa, pb);
	else
		intersects = pen_depth.getPenetrationDepth(s1, s1, 0.0f, s2, s2, 0.0f, 1.0e-4f, FLT_EPSILON, v, pa, pb);

	if (intersects)
	{
		if (inTreatSphereAsPointWithConvexRadius)
			DrawWireSphereSP(mDebugRenderer, s2.getCenter(), s2.getRadius(), Color.sRed);
		else
			DrawSphereSP(mDebugRenderer, s2.getCenter(), s2.getRadius(), Color.sRed);
		DrawMarkerSP(mDebugRenderer, pa, Color.sYellow, 2.0f);
		DrawMarkerSP(mDebugRenderer, pb, Color.sCyan, 2.0f);
	}
	else
	{
		if (inTreatSphereAsPointWithConvexRadius)
			DrawWireSphereSP(mDebugRenderer, s2.getCenter(), s2.getRadius(), Color.sGreen);
		else
			DrawSphereSP(mDebugRenderer, s2.getCenter(), s2.getRadius(), Color.sGreen);
	}
	DrawArrowSP(mDebugRenderer, plus(inTranslationB ,new Vec3(0, 2, 0)), Vec3.sum(inTranslationB , v ,new Vec3(0, 2, 0)), Color.sOrange, 0.05f);
}
}
