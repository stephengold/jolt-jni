/*
Copyright (c) 2024-2026 Stephen Gold

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
import java.util.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
/**
 * A line-for-line Java translation of the Jolt-Physics manifold-reduction test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/ManifoldReductionTest.cpp
 */
public class ManifoldReductionTest extends Test{

static final float cMaxHeight = 4.0f;


public void Initialize()
{
	final float cPerturbance = 0.02f;

	// Create mesh of regular grid of triangles
	List<Triangle> triangles=new ArrayList<>(800);
	for (int x = -10; x < 10; ++x)
		for (int z = -10; z < 10; ++z)
		{
			float x1 = 0.1f * x;
			float z1 = 0.1f * z;
			float x2 = x1 + 0.1f;
			float z2 = z1 + 0.1f;

			ConstFloat3 v1 =new Float3(x1, cPerturbance * perlinNoise3(x1, 0, z1, 256, 256, 256), z1);
			ConstFloat3 v2 =new Float3(x2, cPerturbance * perlinNoise3(x2, 0, z1, 256, 256, 256), z1);
			ConstFloat3 v3 =new Float3(x1, cPerturbance * perlinNoise3(x1, 0, z2, 256, 256, 256), z2);
			ConstFloat3 v4 =new Float3(x2, cPerturbance * perlinNoise3(x2, 0, z2, 256, 256, 256), z2);

			triangles.add(new Triangle(v1, v3, v4, 0));
			triangles.add(new Triangle(v1, v4, v2, 0));
		}
	PhysicsMaterialList materials=new PhysicsMaterialList();
	materials.pushBack(new PhysicsMaterialSimple().toRef());
	ShapeSettingsRef mesh_shape = new MeshShapeSettings(triangles, materials).toRef();

	// Floor
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(mesh_shape, Vec3.sReplicate(20)), RVec3.sZero(), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Create a box made of meshes
	StaticCompoundShapeSettings mesh_box_shape = new StaticCompoundShapeSettings();
	mesh_box_shape.addShape(new Vec3(0, -1, 0), Quat.sRotation(Vec3.sAxisX(), JPH_PI), mesh_shape);
	mesh_box_shape.addShape(new Vec3(0, 1, 0), Quat.sIdentity(), mesh_shape);
	mesh_box_shape.addShape(new Vec3(-1, 0, 0), Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI), mesh_shape);
	mesh_box_shape.addShape(new Vec3(1, 0, 0), Quat.sRotation(Vec3.sAxisZ(), -0.5f * JPH_PI), mesh_shape);
	mesh_box_shape.addShape(new Vec3(0, 0, -1), Quat.sRotation(Vec3.sAxisX(), -0.5f * JPH_PI), mesh_shape);
	mesh_box_shape.addShape(new Vec3(0, 0, 1), Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI), mesh_shape);

	// A convex box
	ShapeSettingsRef box_shape = new BoxShapeSettings(new Vec3(1, 1, 1), 0.0f).toRef();

	{
		// Create a compound of 3 mesh boxes
		StaticCompoundShapeSettings three_mesh_box_shape = new StaticCompoundShapeSettings();
		three_mesh_box_shape.addShape(new Vec3(-2.1f, 0, 0), Quat.sIdentity(), mesh_box_shape);
		three_mesh_box_shape.addShape(new Vec3(0, -1, 0), Quat.sIdentity(), mesh_box_shape);
		three_mesh_box_shape.addShape(new Vec3(2.1f, 0, 0), Quat.sIdentity(), mesh_box_shape);


		// Create a compound of 3 convex boxes
		StaticCompoundShapeSettings three_box_shape = new StaticCompoundShapeSettings();
		three_box_shape.addShape(new Vec3(-2.1f, 0, 0), Quat.sIdentity(), box_shape);
		three_box_shape.addShape(new Vec3(0, -1.1f, 0), Quat.sIdentity(), box_shape);
		three_box_shape.addShape(new Vec3(2.1f, 0, 0), Quat.sIdentity(), box_shape);

		// A set of 3 mesh boxes to rest on
		mBodyInterface.createAndAddBody(new BodyCreationSettings(three_mesh_box_shape,new RVec3(0, 1, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

		// A set of 3 boxes that are dynamic where the middle one penetrates more than the other two
		BodyCreationSettings box_settings=new BodyCreationSettings(three_box_shape,new RVec3(0, 2.95f, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
		box_settings.setAllowSleeping ( false);
		mBodyInterface.createAndAddBody(box_settings, EActivation.Activate);
	}

	{
		// Create a compound of 2 mesh boxes
		StaticCompoundShapeSettings two_mesh_box_shape = new StaticCompoundShapeSettings();
		two_mesh_box_shape.addShape(new Vec3(-2.1f, 0, 0), Quat.sIdentity(), mesh_box_shape);
		two_mesh_box_shape.addShape(new Vec3(0, -1, 0), Quat.sIdentity(), mesh_box_shape);

		// Create a compound of 2 convex boxes
		StaticCompoundShapeSettings two_box_shape = new StaticCompoundShapeSettings();
		two_box_shape.addShape(new Vec3(-2.1f, 0, 0), Quat.sIdentity(), box_shape);
		two_box_shape.addShape(new Vec3(0, -1, 0), Quat.sIdentity(), box_shape);


		// A set of 2 mesh boxes to rest on
		mBodyInterface.createAndAddBody(new BodyCreationSettings(two_mesh_box_shape,new RVec3(0, 1, 4), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

		// A set of 2 boxes that are dynamic, one is lower than the other
		BodyCreationSettings box_settings=new BodyCreationSettings(two_box_shape,new RVec3(0, 4, 4), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
		box_settings.setAllowSleeping ( false);
		mBodyInterface.createAndAddBody(box_settings, EActivation.Activate);
	}

	{
		// Create a compound of 2 meshes under small angle, small enough to combine the manifolds.
		StaticCompoundShapeSettings two_mesh_shape = new StaticCompoundShapeSettings();
		two_mesh_shape.addShape(new Vec3(1, 0, 0), Quat.sRotation(Vec3.sAxisZ(), degreesToRadians(2)), mesh_shape);
		two_mesh_shape.addShape(new Vec3(-1, 0, 0), Quat.sRotation(Vec3.sAxisZ(), degreesToRadians(-2)), mesh_shape);

		// A set of 2 meshes to rest on
		mBodyInterface.createAndAddBody(new BodyCreationSettings(two_mesh_shape,new RVec3(0, 1, -4), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

		// A box that is dynamic, resting on the slightly sloped surface. The surface normals are close enough so that the manifold should be merged.
		BodyCreationSettings box_settings=new BodyCreationSettings(box_shape,new RVec3(0, 4, -4), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
		box_settings.setAllowSleeping ( false);
		mBodyInterface.createAndAddBody(box_settings, EActivation.Activate);
	}

	{
		// Create a compound of 2 meshes under small angle, but bigger than the limit to combine the manifolds.
		StaticCompoundShapeSettings two_mesh_shape = new StaticCompoundShapeSettings();
		two_mesh_shape.addShape(new Vec3(1, 0, 0), Quat.sRotation(Vec3.sAxisZ(), degreesToRadians(3)), mesh_shape);
		two_mesh_shape.addShape(new Vec3(-1, 0, 0), Quat.sRotation(Vec3.sAxisZ(), degreesToRadians(-3)), mesh_shape);

		// A set of 2 meshes to rest on
		mBodyInterface.createAndAddBody(new BodyCreationSettings(two_mesh_shape,new RVec3(0, 1, -8), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

		// A box that is dynamic, resting on the slightly sloped surface. The surface normals are not close enough so that the manifold should be merged.
		BodyCreationSettings box_settings=new BodyCreationSettings(box_shape,new RVec3(0, 4, -8), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
		box_settings.setAllowSleeping ( false);
		mBodyInterface.createAndAddBody(box_settings, EActivation.Activate);
	}
}
}
