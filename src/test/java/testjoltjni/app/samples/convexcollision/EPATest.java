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
package testjoltjni.app.samples.convexcollision;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.readonly.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import static com.github.stephengold.joltjni.std.Std.*;
import static testjoltjni.app.samples.DebugRendererSP.*;


/**
 * A line-for-line Java translation of the Jolt-Physics EPA test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/ConvexCollision/EPATest.cpp
 */
public class EPATest extends Test{

public void PrePhysicsUpdate(PreUpdateParams inParams)
{
	AaBox box=new AaBox(new Vec3(1, 1, -2),new Vec3(2, 2, 2));
	Sphere sphere=new Sphere(new Vec3(4, 4, 0), sqrt(8.0f) + 0.01f);
	Mat44 matrix = Mat44.sRotationTranslation(Quat.sRotation(new Vec3(1, 1, 1).normalized(), 0.25f * JPH_PI),new Vec3(1, 2, 3));
	boolean intersecting = CollideBoxSphere(matrix, box, sphere);
	assert(intersecting);
	; // For when asserts are off
}

boolean CollideBoxSphere(Mat44Arg inMatrix, ConstAaBox inBox, Sphere inSphere)
{
	// Draw the box and shere
	DrawBoxSP(mDebugRenderer, inMatrix, inBox, Color.sGrey);
	DrawSphereSP(mDebugRenderer, star(inMatrix , inSphere.getCenter()), inSphere.getRadius(), Color.sGrey);

	// Transform the box and sphere according to inMatrix
	TransformedAaBox transformed_box=new TransformedAaBox(inMatrix, inBox);
	TransformedSphere transformed_sphere=new TransformedSphere(inMatrix, inSphere);

	// Run the EPA algorithm
	EpaPenetrationDepth epa=new EpaPenetrationDepth();
	Vec3 v1 = Vec3.sAxisX(), pa1=new Vec3(), pb1=new Vec3();
	boolean intersect1 = epa.getPenetrationDepth(transformed_box, transformed_box, 0.0f, transformed_sphere, transformed_sphere, 0.0f, 1.0e-2f, FLT_EPSILON, v1, pa1, pb1);

	// Draw iterative solution
	if (intersect1)
	{
		DrawMarkerSP(mDebugRenderer, pa1, Color.sRed, 1.0f);
		DrawMarkerSP(mDebugRenderer, pb1, Color.sGreen, 1.0f);
		DrawArrowSP(mDebugRenderer, plus(pb1 ,new Vec3(0, 1, 0)), Vec3.sum(pb1 ,new Vec3(0, 1, 0) , v1), Color.sYellow, 0.1f);
	}

	// Calculate analytical solution
	Vec3 pa2 = inBox.getClosestPoint(inSphere.getCenter());
	Vec3 v2 = minus(inSphere.getCenter() , pa2);
	boolean intersect2 = v2.lengthSq() <= square(inSphere.getRadius());

	assert(intersect1 == intersect2);
	if (intersect1 && intersect2)
	{
		Vec3 pb2 = minus(inSphere.getCenter() ,star( inSphere.getRadius() , v2.normalizedOr(Vec3.sZero())));

		// Transform analytical solution
		v2 = inMatrix.multiply3x3(v2);
		pa2 = star(inMatrix , pa2);
		pb2 = star(inMatrix , pb2);

		// Draw analytical solution
		DrawMarkerSP(mDebugRenderer, pa2, Color.sOrange, 1.0f);
		DrawMarkerSP(mDebugRenderer, pb2, Color.sYellow, 1.0f);

		// Check angle between v1 and v2
		float dot = v1.dot(v2);
		float len = v1.length() * v2.length();
		float angle = radiansToDegrees(aCos(dot / len));
		assert(angle < 0.1f);
		Trace("Angle = %.9g", (double)angle);

		// Check delta between contact on A
		Vec3 dpa = minus(pa2 , pa1);
		assert(dpa.isNearZero(square(8.0e-4f)));
		Trace("Delta A = %.9g", (double)dpa.length());

		// Check delta between contact on B
		Vec3 dpb = minus(pb2 , pb1);
		assert(dpb.isNearZero(square(8.0e-4f)));
		Trace("Delta B = %.9g", (double)dpb.length());
	}

	return intersect1;
}
}
