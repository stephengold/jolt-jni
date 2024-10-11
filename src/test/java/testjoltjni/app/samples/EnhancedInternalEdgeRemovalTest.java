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
import com.github.stephengold.joltjni.operator.Op;
import com.github.stephengold.joltjni.readonly.*;
import java.util.ArrayList;
import java.util.List;
/**
 * A line-for-line Java translation of the Jolt Physics enhanced internal-edge removal test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/EnhancedInternalEdgeRemovalTest.cpp
 */
class EnhancedInternalEdgeRemovalTest extends Test{
BodyId mLevelBall;

void CreateSlidingObjects(RVec3Arg inStart)
{
	// Slide the shapes over the grid of boxes
	RVec3 pos = Op.subtract(inStart , new RVec3(0, 0, 12.0));
	for (int enhanced_removal = 0; enhanced_removal < 2; ++enhanced_removal)
	{
		// A box
		BodyCreationSettings box_bcs=new BodyCreationSettings(new BoxShape(Vec3.sReplicate(2.0f)), pos, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
		box_bcs.setLinearVelocity ( new Vec3(20, 0, 0));
		box_bcs.setEnhancedInternalEdgeRemoval ( enhanced_removal == 1);
		mBodyInterface.createAndAddBody(box_bcs, EActivation.Activate);
		Op.plusEquals(pos ,new RVec3(0, 0, 5.0));

		// A sphere
		BodyCreationSettings sphere_bcs=new BodyCreationSettings(new SphereShape(2.0f), pos, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
		sphere_bcs.setLinearVelocity ( new Vec3(20, 0, 0));
		sphere_bcs.setEnhancedInternalEdgeRemoval ( enhanced_removal == 1);
		mBodyInterface.createAndAddBody(sphere_bcs, EActivation.Activate);
		Op.plusEquals(pos ,new RVec3(0, 0, 5.0));

		// Compound
		ShapeRefC box = new BoxShape(Vec3.sReplicate(0.1f)).toRefC();
		StaticCompoundShapeSettings compound=new StaticCompoundShapeSettings();
		compound.setEmbedded();
		for (int x = 0; x < 2; ++x)
			for (int y = 0; y < 2; ++y)
				for (int z = 0; z < 2; ++z)
					compound.addShape(new Vec3(x == 0? -1.9f : 1.9f, y == 0? -1.9f : 1.9f, z == 0? -1.9f : 1.9f), Quat.sIdentity(), box);
		BodyCreationSettings compound_bcs=new BodyCreationSettings(compound, pos, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
		compound_bcs.setLinearVelocity (new Vec3(20, 0, 0));
		compound_bcs.setEnhancedInternalEdgeRemoval ( enhanced_removal == 1);
		mBodyInterface.createAndAddBody(compound_bcs, EActivation.Activate);
		Op.plusEquals(pos , new RVec3(0, 0, 7.0));
	}
}

void Initialize()
{
	// This test creates a grid of connected boxes and tests that objects don't hit the internal edges
	{
		StaticCompoundShapeSettings compound_settings=new StaticCompoundShapeSettings();
		compound_settings.setEmbedded();
		final float size = 2.0f;
		ShapeRefC box_shape = new BoxShape(Vec3.sReplicate(0.5f * size)).toRefC();
		for (int x = -10; x < 10; ++x)
			for (int z = -10; z < 10; ++z)
				compound_settings.addShape(new Vec3(size * x, 0, size * z), Quat.sIdentity(), box_shape);
		mBodyInterface.createAndAddBody(new BodyCreationSettings(compound_settings, new RVec3(0, -1, -40), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

		CreateSlidingObjects(new RVec3(-18, 1.9, -40.0));
	}

	// This tests if objects do not collide with internal edges
	{
		// Create a dense grid of triangles so that we have a large chance of hitting an internal edge
		final float size = 2.0f;
		List<Triangle> triangles=new ArrayList<>();
		for (int x = -10; x < 10; ++x)
			for (int z = -10; z < 10; ++z)
			{
				float x1 = size * x;
				float z1 = size * z;
				float x2 = x1 + size;
				float z2 = z1 + size;

				Float3 v1 =new Float3(x1, 0, z1);
				Float3 v2 =new Float3(x2, 0, z1);
				Float3 v3 =new Float3(x1, 0, z2);
				Float3 v4 =new Float3(x2, 0, z2);

				triangles.add(new Triangle(v1, v3, v4));
				triangles.add(new Triangle(v1, v4, v2));
			}

		MeshShapeSettings mesh_settings=new MeshShapeSettings(triangles);
		mesh_settings.setActiveEdgeCosThresholdAngle ( Float.MAX_VALUE); // Turn off regular active edge determination so that we only rely on the mEnhancedInternalEdgeRemoval flag
		mesh_settings.setEmbedded();
		mBodyInterface.createAndAddBody(new BodyCreationSettings(mesh_settings, RVec3.sZero(), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

		CreateSlidingObjects(new RVec3(-18, 1.9, 0));
	}

	// This test tests that we only ignore edges that are shared with voided triangles
	{
		// Create an L shape mesh lying on its back
		List<Triangle> triangles=new ArrayList<>();
		final float height = 0.5f;
		final float half_width = 5.0f;
		final float half_length = 2.0f;
		triangles.add(new Triangle(new Float3(-half_length, 0, half_width), new Float3(half_length, 0, -half_width), new Float3(-half_length, 0, -half_width)));
		triangles.add(new Triangle(new Float3(-half_length, 0, half_width), new Float3(half_length, 0, half_width), new Float3(half_length, 0, -half_width)));
		triangles.add(new Triangle(new Float3(half_length, height, half_width), new Float3(half_length, height, -half_width), new Float3(half_length, 0, half_width)));
		triangles.add(new Triangle(new Float3(half_length, 0, half_width), new Float3(half_length, height, -half_width), new Float3(half_length, 0, -half_width)));
		mBodyInterface.createAndAddBody(new BodyCreationSettings(new MeshShapeSettings(triangles), new RVec3(0, 0, 30), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

		// Roll a sphere towards the edge pointing upwards
		float z = 28.0f;
		for (int enhanced_removal = 0; enhanced_removal < 2; ++enhanced_removal)
		{
			// A sphere
			BodyCreationSettings sphere_bcs=new BodyCreationSettings(new SphereShape(1.0f), new RVec3(0, 1, z), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
			sphere_bcs.setLinearVelocity (new Vec3(20, 0, 0));
			sphere_bcs.setEnhancedInternalEdgeRemoval ( enhanced_removal == 1);
			mBodyInterface.createAndAddBody(sphere_bcs, EActivation.Activate);
			z += 4.0f;
		}
	}

	// This tests that fast moving spheres rolling over a triangle will not be affected by internal edges
	{
		// Create a flat plane
		MeshShapeSettings plane_mesh=new MeshShapeSettings(new Triangle[]{
			new Triangle(
				new Float3(-10, 0, -10),
				new Float3(-10, 0, 10),
				new Float3(10, 0, 10)
			),
			new Triangle(
				new Float3(-10, 0, -10),
				new Float3(10, 0, 10),
				new Float3(10, 0, -10)
			),
		});
		plane_mesh.setEmbedded();
		BodyCreationSettings level_plane=new BodyCreationSettings(plane_mesh, new RVec3(-10, 0, 50), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
		level_plane.setFriction ( 1);
		mBodyInterface.createAndAddBody(level_plane, EActivation.DontActivate);

		// Roll a ball over it
		BodyCreationSettings level_ball=new BodyCreationSettings(new SphereShape(0.5f), new RVec3(-10, 1, 41), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
		level_ball.setEnhancedInternalEdgeRemoval ( true);
		level_ball.setFriction ( 1);
		level_ball.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
		level_ball.getMassPropertiesOverride().setMass ( 1);
		mLevelBall = mBodyInterface.createAndAddBody(level_ball, EActivation.Activate);

		// Create a sloped plane
		BodyCreationSettings slope_plane=new BodyCreationSettings(plane_mesh, new RVec3(10, 0, 50), Quat.sRotation(Vec3.sAxisX(), Jolt.degreesToRadians(45)), EMotionType.Static, Layers.NON_MOVING);
		slope_plane.setFriction ( 1);
		mBodyInterface.createAndAddBody(slope_plane, EActivation.DontActivate);

		// Roll a ball over it
		BodyCreationSettings slope_ball=new BodyCreationSettings(new SphereShape(0.5f), new RVec3(10, 8, 44), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
		slope_ball.setEnhancedInternalEdgeRemoval ( true);
		slope_ball.setFriction ( 1);
		slope_ball.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
		slope_ball.getMassPropertiesOverride().setMass ( 1);
		mBodyInterface.createAndAddBody(slope_ball, EActivation.Activate);
	}

	// This tests a previous bug where a compound shape will fall through a box because features are voided by accident.
	// This is because both boxes of the compound shape collide with the top face of the static box. The big box will have a normal
	// that is aligned with the face so will be processed immediately. This will void the top face of the static box. The small box,
	// which collides with an edge of the top face will not be processed. This will cause the small box to penetrate the face.
	{
		// A box
		BodyCreationSettings box_bcs=new BodyCreationSettings(new BoxShape(Vec3.sReplicate(2.5f)), new RVec3(0, 0, 70), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
		mBodyInterface.createAndAddBody(box_bcs, EActivation.DontActivate);

		// Compound
		StaticCompoundShapeSettings compound=new StaticCompoundShapeSettings();
		compound.setEmbedded();
		compound.addShape(new Vec3(-2.5f, 0, 0), Quat.sIdentity(), new BoxShape(new Vec3(2.5f, 0.1f, 0.1f)));
		compound.addShape(new Vec3(0.1f, 0, 0), Quat.sIdentity(), new BoxShape(new Vec3(0.1f, 1, 1)));
		BodyCreationSettings compound_bcs=new BodyCreationSettings(compound, new RVec3(2, 5, 70), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
		compound_bcs.setEnhancedInternalEdgeRemoval ( true);
		mBodyInterface.createAndAddBody(compound_bcs, EActivation.Activate);
	}
}

void PrePhysicsUpdate(PreUpdateParams inParams)
{
	// Increase rotation speed of the ball on the flat plane
	mBodyInterface.addTorque(mLevelBall, new Vec3(Jolt.JPH_PI * 4, 0, 0));
}
}
