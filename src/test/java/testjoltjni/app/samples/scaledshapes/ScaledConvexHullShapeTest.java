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
package testjoltjni.app.samples.scaledshapes;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.readonly.*;
import java.util.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt-Physics scaled convex-hull shape
 * test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/ScaledShapes/ScaledConvexHullShapeTest.cpp
 */
public class ScaledConvexHullShapeTest extends Test{

void Initialize()
{
	// Floor
	CreateFloor();

	// Create tetrahedron
	List<Vec3> tetrahedron=new ArrayList<>();
	tetrahedron.add(Vec3.sZero());
	tetrahedron.add(new Vec3(10, 0, 12.5f));
	tetrahedron.add(new Vec3(15, 0, 2.5f));
	tetrahedron.add(new Vec3(10, -5, 5));

	// Create vertices for box
	List<Vec3> box=new ArrayList<>();
	box.add(new Vec3(1, 2, 3));
	box.add(new Vec3(-1, 2, 3));
	box.add(new Vec3(1, -2, 3));
	box.add(new Vec3(-1, -2, 3));
	box.add(new Vec3(1, 2, -3));
	box.add(new Vec3(-1, 2, -3));
	box.add(new Vec3(1, -2, -3));
	box.add(new Vec3(-1, -2, -3));

	// Rotate and translate vertices
	Mat44 m = Mat44.product(Mat44.sTranslation(new Vec3(3.0f, -2.0f, 1.0f)) , Mat44.sRotationY(0.2f * JPH_PI) , Mat44.sRotationZ(0.1f * JPH_PI));
	for (Vec3 v : box)
		assign(v , star(m , v));

	// Create convex hulls
	ConstShapeSettings[] hull_shape = { new ConvexHullShapeSettings(tetrahedron), new ConvexHullShapeSettings(box) };

	for (int i = 0; i < 2; ++i)
	{
		// Original shape
		mBodyInterface.createAndAddBody(new BodyCreationSettings(hull_shape[i],new RVec3(-40, 10, i * 20.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

		// Uniformly scaled shape
		mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(hull_shape[i], Vec3.sReplicate(0.25f)),new RVec3(-20, 10, i * 20.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

		// Non-uniform scaled shape
		mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(hull_shape[i],new Vec3(0.25f, 0.5f, 1.5f)),new RVec3(0, 10, i * 20.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

		// Flipped in 2 axis
		mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(hull_shape[i],new Vec3(-0.25f, 0.5f, -1.5f)),new RVec3(20, 10, i * 20.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

		// Inside out
		mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(hull_shape[i],new Vec3(-0.25f, 0.5f, 1.5f)),new RVec3(40, 10, i * 20.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
	}
}
}
