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
package testjoltjni.app.samples.general;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.readonly.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import static com.github.stephengold.joltjni.std.Std.*;
/**
 * A line-for-line Java translation of the Jolt-Physics shape-filter test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/ShapeFilterTest.cpp
 */
public class ShapeFilterTest extends Test{
float mElapsedTime;
ShapeRefC mCastShape;

public void Initialize()
{
	// Create geometry to cast against
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(20, 1, 3)),new RVec3(0, -1, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.Activate);
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(3)),new RVec3(0, 3, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.Activate);

	// Create shape to cast
	BoxShapeSettings box_shape = new BoxShapeSettings(Vec3.sReplicate(1));

	SphereShapeSettings sphere_shape = new SphereShapeSettings(1);

	StaticCompoundShapeSettings cast_shape=new StaticCompoundShapeSettings();
	cast_shape.addShape(new Vec3(3, 2, 0), Quat.sIdentity(), box_shape);
	cast_shape.addShape(new Vec3(0, 0, 0), Quat.sIdentity(), sphere_shape);

	mCastShape = cast_shape.create().get();
}

public void PostPhysicsUpdate(float inDeltaTime)
{
	mElapsedTime += inDeltaTime;
	float phase = mElapsedTime;

	final RVec3 cast_origin =new RVec3(cos(phase) * 10, 10, 0);
	final Vec3 cast_motion =new Vec3(0, -15, 0);

	ClosestHitCastShapeCollector cast_shape_collector=new ClosestHitCastShapeCollector();

	class MyShapeFilter extends ShapeFilter
	{
	public
		boolean	ShouldCollide(ConstShape inShape1, int inSubShapeID1, ConstShape inShape2, int inSubShapeID2)
		{
			return inShape1.getSubType().ordinal() != mUserDataOfShapeToIgnore;
		}

		// We're not interested in the other overload as it is not used by ray casts
		//using ShapeFilter.ShouldCollide;

		long			mUserDataOfShapeToIgnore = (long)EShapeSubType.Sphere.ordinal();
	};

	MyShapeFilter shape_filter=new MyShapeFilter();

	// Select which shape to ignore
	float shape_select = fmod(phase, 6.0f * JPH_PI);
	String text;
	if (shape_select < 2.0f * JPH_PI)
	{
		shape_filter.mUserDataOfShapeToIgnore = (long)EShapeSubType.Box.ordinal();
		text = "Box";
	}
	else if (shape_select < 4.0f * JPH_PI)
	{
		shape_filter.mUserDataOfShapeToIgnore = (long)EShapeSubType.Sphere.ordinal();
		text = "Sphere";
	}
	else
	{
		shape_filter.mUserDataOfShapeToIgnore = (long)EShapeSubType.StaticCompound.ordinal();
		text = "Compound";
	}
	mDebugRenderer.drawText3D(cast_origin, String.format("Ignoring shape: %s", text), Color.sWhite);

	// Do the cast
	mPhysicsSystem.getNarrowPhaseQuery().castShape(
		new RShapeCast(mCastShape, Vec3.sReplicate(1), RMat44.sTranslation(cast_origin), cast_motion),
		new ShapeCastSettings(),
		RVec3.sZero(),
		cast_shape_collector,
		new BroadPhaseLayerFilter(){ },
		new ObjectLayerFilter(){ },
		new BodyFilter(){ },
		shape_filter
	);

	// Show the result
	RVec3 cast_point;
	ConstColor color;
	if (cast_shape_collector.hadHit())
	{
		cast_point = plus(cast_origin , star(cast_motion , cast_shape_collector.getHit().getFraction()));
		color = Color.sGreen;
	}
	else
	{
		cast_point = plus(cast_origin , cast_motion);
		color = Color.sRed;
	}
	mDebugRenderer.drawArrow(cast_origin, cast_point, Color.sOrange, 0.1f);
	if(Jolt.implementsDebugRendering())mCastShape.draw(mDebugRenderer, RMat44.sTranslation(new RVec3(cast_point)), Vec3.sOne(), color, false, true);
}
}