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
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.operator.Op;
import testjoltjni.app.samples.*;
import static testjoltjni.app.samples.DebugRendererSP.*;
/**
 * A line-for-line Java translation of the Jolt Physics capsule-vs-box test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/CapsuleVsBoxTest.cpp
 */
public class CapsuleVsBoxTest extends Test{

public void PrePhysicsUpdate(PreUpdateParams inParams)
{
	// Create box
	Vec3 box_min=new Vec3(-1.0f, -2.0f, 0.5f);
	Vec3 box_max=new Vec3(2.0f, -0.5f, 3.0f);
	ShapeSettingsRef box_settings = new RotatedTranslatedShapeSettings(Op.multiply(0.5f , Op.add(box_min , box_max)), Quat.sIdentity(), new BoxShapeSettings(Op.multiply(0.5f , Op.subtract(box_max , box_min)))).toRef();
	ShapeRefC box_shape = box_settings.create().get();
	Mat44 box_transform=new Mat44(new Vec4(0.516170502f, -0.803887904f, -0.295520246f, 0.0f),new Vec4(0.815010250f, 0.354940295f, 0.458012700f, 0.0f),new Vec4(-0.263298869f, -0.477264702f, 0.838386655f, 0.0f),new Vec4(-10.2214508f, -18.6808319f, 40.7468987f, 1.0f));

	// Create capsule
	float capsule_half_height = 75.0f;
	float capsule_radius = 1.5f;
	Quat capsule_compound_rotation=new Quat(0.499999970f, -0.499999970f, -0.499999970f, 0.499999970f);
	ShapeSettingsRef capsule_settings = new RotatedTranslatedShapeSettings(new Vec3(0, 0, 75), capsule_compound_rotation, new CapsuleShapeSettings(capsule_half_height, capsule_radius)).toRef();
	ShapeRefC capsule_shape = capsule_settings.create().get();
	Mat44 capsule_transform = Mat44.sTranslation(new Vec3(-9.68538570f, -18.0328083f, 41.3212280f));

	// Collision settings
	CollideShapeSettings settings=new CollideShapeSettings();
	settings.setActiveEdgeMode ( EActiveEdgeMode.CollideWithAll);
	settings.setBackFaceMode ( EBackFaceMode.CollideWithBackFaces);
	settings.setCollectFacesMode ( ECollectFacesMode.NoFaces);

	// Collide the two shapes
	AllHitCollideShapeCollector collector=new AllHitCollideShapeCollector();
	CollisionDispatch.sCollideShapeVsShape(capsule_shape, box_shape, Vec3.sReplicate(1.0f), Vec3.sReplicate(1.0f), capsule_transform, box_transform,new SubShapeIdCreator(),new SubShapeIdCreator(), settings, collector);

if (Jolt.implementsDebugRendering()) {
	// Draw the shapes
	box_shape.draw(mDebugRenderer,new RMat44(box_transform), Vec3.sReplicate(1.0f), Color.sWhite, false, false);
	capsule_shape.draw(mDebugRenderer,new RMat44(capsule_transform), Vec3.sReplicate(1.0f), Color.sWhite, false, false);
} // JPH_DEBUG_RENDERER

	// Draw contact points
	CollideShapeResult hit = collector.get(0);
	DrawMarkerSP(mDebugRenderer, hit.getContactPointOn1(), Color.sRed, 1.0f);
	DrawMarkerSP(mDebugRenderer, hit.getContactPointOn2(), Color.sGreen, 1.0f);

	// Draw penetration axis with length of the penetration
	Vec3 pen_axis = hit.getPenetrationAxis();
	float pen_axis_len = pen_axis.length();
	if (pen_axis_len > 0.0f)
	{
		Op.starEquals(pen_axis , hit.getPenetrationDepth() / pen_axis_len);
		DebugRendererSP.DrawArrowSP(mDebugRenderer, hit.getContactPointOn2(), Op.add(hit.getContactPointOn2() , pen_axis), Color.sYellow, 0.01f);

if (Jolt.implementsDebugRendering()) {
		Mat44 resolved_box = box_transform.postTranslated(pen_axis);
		box_shape.draw(mDebugRenderer,new RMat44(resolved_box), Vec3.sReplicate(1.0f), Color.sGreen, false, false);
} // JPH_DEBUG_RENDERER
	}
}
}
