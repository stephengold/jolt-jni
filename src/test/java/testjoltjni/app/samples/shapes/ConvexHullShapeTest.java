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
package testjoltjni.app.samples.shapes;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.readonly.*;
import com.github.stephengold.joltjni.std.*;
import java.util.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import static com.github.stephengold.joltjni.std.Std.*;
/**
 * A line-for-line Java translation of the Jolt Physics convex-hull shape test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Shapes/ConvexHullShapeTest.cpp
 */
public class ConvexHullShapeTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	// Create tetrahedron
	List<Vec3Arg> tetrahedron=new ArrayList<>();
	tetrahedron.add(new Vec3(-5, 0, -5));
	tetrahedron.add(new Vec3(0, 0, 5));
	tetrahedron.add(new Vec3(5, 0, -5));
	tetrahedron.add(new Vec3(0, -5, 0));

	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ConvexHullShapeSettings(tetrahedron),new RVec3(0, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Create box
	List<Vec3Arg> box=new ArrayList<>();
	box.add(new Vec3(5, 5, 5));
	box.add(new Vec3(-5, 5, 5));
	box.add(new Vec3(5, -5, 5));
	box.add(new Vec3(-5, -5, 5));
	box.add(new Vec3(5, 5, -5));
	box.add(new Vec3(-5, 5, -5));
	box.add(new Vec3(5, -5, -5));
	box.add(new Vec3(-5, -5, -5));

	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ConvexHullShapeSettings(box),new RVec3(20, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Add a sphere of many points
	List<Vec3Arg> sphere=new ArrayList<>();
	for (float theta = 0.0f; theta <= JPH_PI; theta += JPH_PI / 20.0f)
		for (float phi = 0.0f; phi <= 2.0f * JPH_PI; phi += 2.0f * JPH_PI / 20.0f)
			sphere.add(star(5.0f , Vec3.sUnitSpherical(theta, phi)));

	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ConvexHullShapeSettings(sphere),new RVec3(40, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Add a tapered cylinder of many points
	List<Vec3Arg> tapered_cylinder=new ArrayList<>();
	for (float theta = 0.0f; theta <= 2.0f * JPH_PI; theta += JPH_PI / 128.0f)
	{
		tapered_cylinder.add(star(4.0f ,new Vec3(-0.1f, sin(theta), cos(theta))));
		tapered_cylinder.add(star(4.5f ,new Vec3(0.1f, sin(theta), cos(theta))));
	}

	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ConvexHullShapeSettings(tapered_cylinder),new RVec3(60, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Create convex hull with on one side nearly coplanar faces
	List<Vec3Arg> coplanar=new ArrayList<>();
	coplanar.add(new Vec3(1.04298747f, 4.68531752f, 0.858853102f));
	coplanar.add(new Vec3(-1.00753999f, 4.63935566f, -0.959064901f));
	coplanar.add(new Vec3(-1.01861656f, 4.72096348f, 0.846121550f));
	coplanar.add(new Vec3(-2.37996006f, 1.26311386f, -1.10994697f));
	coplanar.add(new Vec3(0.213164970f, 0.0198628306f, -1.70677519f));
	coplanar.add(new Vec3(-2.27295995f, -0.899001241f, -0.472913086f));
	coplanar.add(new Vec3(-1.85078228f, -1.25204790f, 2.42339849f));
	coplanar.add(new Vec3(1.91183412f, -1.25204790f, 2.42339849f));
	coplanar.add(new Vec3(-2.75279832f, 3.25019693f, 1.67055058f));
	coplanar.add(new Vec3(-0.0697868019f, -2.78841114f, -0.422013819f));
	coplanar.add(new Vec3(2.26410985f, -0.918261647f, -0.493922710f));
	coplanar.add(new Vec3(0.765828013f, -2.82050991f, 1.91100550f));
	coplanar.add(new Vec3(2.33326006f, 1.26643038f, -1.18808103f));
	coplanar.add(new Vec3(-0.591650009f, 2.27845216f, -1.87628603f));
	coplanar.add(new Vec3(-2.22145009f, 3.04359150f, 0.234738767f));
	coplanar.add(new Vec3(-1.00753999f, 4.39097166f, -1.27783847f));
	coplanar.add(new Vec3(0.995577991f, 4.39734173f, -1.27900386f));
	coplanar.add(new Vec3(0.995577991f, 4.64572525f, -0.960230291f));
	coplanar.add(new Vec3(2.74527335f, 3.06491613f, 1.77647924f));
	coplanar.add(new Vec3(-1.53122997f, -2.18120861f, 2.31516361f));

	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ConvexHullShapeSettings(coplanar),new RVec3(80, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Bodies with random convex shapes
	DefaultRandomEngine random=new DefaultRandomEngine();
	UniformFloatDistribution hull_size=new UniformFloatDistribution(0.1f, 10.0f);
	for (int i = 0; i < 10; ++i)
	{
		// Create random points
		List<Vec3Arg> points=new ArrayList<>(20);
		for (int j = 0; j < 20; ++j)
			points.add(star(hull_size.nextFloat(random) , Vec3.sRandom(random)));

		mBodyInterface.createAndAddBody(new BodyCreationSettings(new ConvexHullShapeSettings(points),new RVec3(-90.0f + i * 18.0f, 10, 20), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
	}

	// Bodies with random convex polygons (this is not something you should be doing, but this tests the 2D convex hull shape generation and allows you to test the probe against them)
	for (int i = 0; i < 10; ++i)
	{
		// Create random points
		List<Vec3Arg> points=new ArrayList<>(20);
		for (int j = 0; j < 20; ++j)
		{
			Vec3 v = star(hull_size.nextFloat(random) , Vec3.sRandom(random));
			v.setZ(0.0f);
			points.add(v);
		}

		// Convex hull needs to be created with convex radius of 0 because the shape has no volume, so we cannot move the planes backwards to make space for the convex radius
		ShapeSettingsRef shape_settings = new ConvexHullShapeSettings(points, 0.0f).toRef();

		BodyCreationSettings creation_settings=new BodyCreationSettings(shape_settings,new RVec3(-90.0f + i * 18.0f, 10, 40), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);

		// The polygon has no volume, so we need to provide a dummy mass and inertia for this shape
		creation_settings.setOverrideMassProperties ( EOverrideMassProperties.MassAndInertiaProvided);
		creation_settings.getMassPropertiesOverride().setMass ( 1.0f);
		creation_settings.getMassPropertiesOverride().setInertia ( Mat44.sIdentity());

		mBodyInterface.createAndAddBody(creation_settings, EActivation.Activate);
	}
}
}
