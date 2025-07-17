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
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.readonly.*;
import java.util.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import static com.github.stephengold.joltjni.std.Std.*;
import static testjoltjni.app.samples.DebugRendererSP.*;

/**
 * A line-for-line Java translation of the Jolt-Physics convex-hull shrink test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/ConvexCollision/ConvexHullShrinkTest.cpp
 */
public class ConvexHullShrinkTest extends Test{
int mIteration=0;
List<Vec3Arg[]>mPoints;

public void Initialize()
{
	// First add a list of shapes that were problematic before
	mPoints = new ArrayList<>();
		mPoints.add(new Vec3[]{
			new Vec3(1, 1, 1),
			new Vec3(1, 1, -1),
			new Vec3(1, -1, 1),
			new Vec3(1, -1, -1),
		});
		mPoints.add(new Vec3[]{
			new Vec3(1, 1, 1),
			new Vec3(1, 1, -1),
			new Vec3(1, -1, 1),
			new Vec3(1, -1, -1),
			new Vec3(-1, 1, 1),
			new Vec3(-1, 1, -1),
			new Vec3(-1, -1, 1),
			new Vec3(-1, -1, -1),
		});
		mPoints.add(new Vec3[]{
			new Vec3(0.24055352f, 0.42262089f, 0.20811508f),
			new Vec3(0.23034751f, 0.42984104f, -0.21389426f),
			new Vec3(0.21995061f, 0.43724900f, 0.20929135f),
			new Vec3(0.18619442f, 0.44122630f, 0.10257969f),
			new Vec3(-0.22997921f, 0.43706810f, 0.21128670f),
			new Vec3(0.18488347f, -0.44135576f, 0.10415942f),
			new Vec3(-0.20950880f, -0.43603044f, 0.20873074f),
			new Vec3(-0.21230474f, -0.43691945f, -0.20506332f),
			new Vec3(0.23440370f, -0.43392032f, 0.20985059f),
			new Vec3(0.22406587f, -0.43578571f, -0.21132792f),
			new Vec3(0.24845430f, -0.41821426f, -0.21033705f),
			new Vec3(0.24780219f, -0.42262548f, 0.21058462f),
			new Vec3(-0.24866026f, 0.41188520f, 0.20908103f),
			new Vec3(-0.25144735f, 0.41933101f, -0.20718251f),
			new Vec3(-0.24799588f, -0.20490804f, 0.21178717f),
			new Vec3(0.01075744f, -0.41775572f, -0.22181017f),
			new Vec3(-0.18624404f, -0.18736419f, -0.21975047f),
			new Vec3(0.22080457f, 0.01773871f, -0.22080121f),
			new Vec3(-0.17988407f, 0.40095943f, -0.21670545f),
			new Vec3(-0.23094913f, 0.42154532f, 0.21846796f),
			new Vec3(0.23783659f, 0.41114848f, -0.20812420f),
			new Vec3(0.25242796f, 0.00087111f, 0.04875314f),
			new Vec3(0.20976084f, 0.43694448f, -0.20819492f),
			new Vec3(0.21914389f, -0.42215359f, -0.21839635f),
			new Vec3(0.22120973f, 0.42172050f, 0.21581716f),
			new Vec3(0.07287904f, 0.40937370f, 0.21898652f),
			new Vec3(-0.23638439f, 0.42299985f, -0.21391643f),
			new Vec3(0.25210538f, -0.20603905f, 0.20603551f),
			new Vec3(-0.22867783f, -0.43080616f, -0.21309699f),
			new Vec3(-0.22365719f, 0.43650645f, -0.20515810f),
			new Vec3(-0.23701435f, 0.43320888f, -0.20985882f),
			new Vec3(-0.24509817f, 0.42541492f, 0.21352110f),
			new Vec3(0.22803798f, -0.41877448f, 0.21590335f),
			new Vec3(-0.21627685f, -0.41884291f, 0.21908275f),
			new Vec3(-0.24125161f, -0.13299965f, -0.21386964f),
			new Vec3(-0.22310710f, -0.43280768f, 0.21368177f),
			new Vec3(-0.23707944f, -0.41916745f, 0.21170078f),
			new Vec3(-0.23729360f, -0.42400050f, -0.20905880f),
			new Vec3(-0.23056241f, 0.44033193f, -0.00191451f),
			new Vec3(-0.24118152f, -0.41101628f, -0.20855166f),
			new Vec3(0.21646300f, 0.42087674f, -0.21763385f),
			new Vec3(0.25090047f, -0.41023433f, 0.10248772f),
			new Vec3(0.03950108f, -0.43627834f, -0.21231101f),
			new Vec3(-0.22727611f, -0.24993966f, 0.21899925f),
			new Vec3(0.24388977f, -0.07015021f, -0.21204789f)
		}
	);

	// Open the external file with hulls
	// A stream containing predefined convex hulls
	StreamInWrapper points_stream=StreamInWrapper.open("Assets/convex_hulls.bin", StreamInWrapper.binary());
	if (points_stream!=null)
	{
		for (;;)
		{
			// Read the length of the next point cloud
			int len = 0;
			len=points_stream.readInt(  );
			if (points_stream.isEof())
				break;

			// Read the points
			if (len > 0)
			{
				Vec3[] p=new Vec3[len];
				for (int i = 0; i < len; ++i)
				{
					Float3 v=new Float3();
					points_stream.readFloat3( v );
					p[i]=new Vec3(v);
				}
				mPoints.add(p);
			}
		}
	}
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	// Take one of the predefined shapes
	Vec3Arg[]  points = mIteration < mPoints.size()? mPoints.get(mIteration) : mPoints.get(mPoints.size()-1);
	mIteration++;

	// Create shape
	ConvexHullShapeSettings settings=new ConvexHullShapeSettings(points, cDefaultConvexRadius);
	ShapeResult result = settings.create();
	if (!result.isValid())
	{
		Trace("%d: %s", mIteration - 1, result.getError());
		return;
	}
	ShapeRefC shape = (result.get()).toRefC();

	// Shape creation may have reduced the convex radius, fetch the result
	final float convex_radius = ((ConvexHullShape)shape.getPtr()).getConvexRadius();
	if (convex_radius > 0.0f)
	{
		// Get the support function of the shape excluding convex radius and add the convex radius
		SupportBuffer buffer=new SupportBuffer();
		Support support = ((ConvexShape)shape.getPtr()).getSupportFunction(ESupportMode.ExcludeConvexRadius, buffer, Vec3.sReplicate(1.0f));
		AddConvexRadiusSupport add_cvx=new AddConvexRadiusSupport(support,convex_radius);

		// Calculate the error w.r.t. the original hull
		float max_error = -FLT_MAX;
		int max_error_plane = 0;
		Vec3 max_error_support_point = Vec3.sZero();
		ConstPlane[]  planes = ((ConvexHullShape)shape.getPtr()).getPlanes();
		for (int i = 0; i < (int)planes.length; ++i)
		{
			ConstPlane  plane = planes[i];
			Vec3 support_point = add_cvx.getSupport(plane.getNormal());
			float distance = plane.signedDistance(support_point);
			if (distance > max_error)
			{
				max_error = distance;
				max_error_support_point = support_point;
				max_error_plane = i;
			}
		}
		if (max_error > settings.getMaxErrorConvexRadius())
		{
			Trace("%d, %f, %f", mIteration - 1, (double)convex_radius, (double)max_error);
			DrawMarkerSP(mDebugRenderer, max_error_support_point, Color.sPurple, 0.1f);
			DrawArrowSP(mDebugRenderer, max_error_support_point, minus(max_error_support_point , star(max_error , planes[max_error_plane].getNormal())), Color.sPurple, 0.01f);
		}
	}

if (implementsDebugRendering()) {
	// Draw the hulls
	shape.draw(DebugRenderer.sInstance(), RMat44.sIdentity(), Vec3.sReplicate(1.0f), Color.sRed, false, false);
	shape.drawGetSupportFunction(DebugRenderer.sInstance(), RMat44.sIdentity(), Vec3.sReplicate(1.0f), Color.sLightGrey, false);
	((ConvexHullShape)shape.getPtr()).drawShrunkShape(DebugRenderer.sInstance(), RMat44.sIdentity(), Vec3.sReplicate(1.0f));
} // JPH_DEBUG_RENDERER
}
}