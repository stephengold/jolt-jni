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
 * A line-for-line Java translation of the Jolt-Physics mesh-shape user-data
 * test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Shapes/MeshShapeUserDataTest.cpp
 */
public class MeshShapeUserDataTest extends Test{

@SuppressWarnings("unchecked")
void Initialize()
{
	DefaultRandomEngine random=new DefaultRandomEngine();

	// Create regular grid of triangles
	int user_data = 0;
	List[] triangles={new ArrayList<>(),new ArrayList<>()};
	for (int x = -10; x < 10; ++x)
		for (int z = -10; z < 10; ++z)
		{
			float x1 = 10.0f * x;
			float z1 = 10.0f * z;
			float x2 = x1 + 10.0f;
			float z2 = z1 + 10.0f;

			Float3 v1 =new Float3(x1, 0, z1);
			Float3 v2 =new Float3(x2, 0, z1);
			Float3 v3 =new Float3(x1, 0, z2);
			Float3 v4 =new Float3(x2, 0, z2);

			triangles[random.nextInt() & 1].add(new Triangle(v1, v3, v4, 0, user_data++));
			triangles[random.nextInt() & 1].add(new Triangle(v1, v4, v2, 0, user_data++));
		}

	// Create a compound with 2 meshes
	StaticCompoundShapeSettings compound_settings=new StaticCompoundShapeSettings();
	compound_settings.setEmbedded();
	for (List t : triangles)
	{
		// Shuffle the triangles
		shuffle(t, random);

		// Create mesh
		MeshShapeSettings mesh_settings=new MeshShapeSettings(t);
		mesh_settings.setPerTriangleUserData ( true);
		compound_settings.addShape(Vec3.sZero(), Quat.sIdentity(), mesh_settings.create().get());
	}

	// Create body
	mBodyInterface.createAndAddBody(new BodyCreationSettings(compound_settings, RVec3.sZero(), Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// 1 body with zero friction
	BodyCreationSettings bcs=new BodyCreationSettings(new BoxShape(Vec3.sReplicate(2.0f)),new RVec3(0, 55.0f, -50.0f), Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI), EMotionType.Dynamic, Layers.MOVING);
	bcs.setFriction ( 0.0f);
	bcs.setEnhancedInternalEdgeRemoval ( true); // Needed because the 2 meshes have a lot of active edges
	mBodyInterface.createAndAddBody(bcs, EActivation.Activate);
}

public void PrePhysicsUpdate(PreUpdateParams inParams)
{
	// Cast a ray
	RayCastResult hit=new RayCastResult();
	RRayCast ray=new RRayCast(inParams.mCameraState.mPos, star(inParams.mCameraState.mForward , 100.0f));
	mPhysicsSystem.getNarrowPhaseQuery().castRay(ray, hit);

	// Get body (if there was a hit)
	BodyLockRead lock=new BodyLockRead(mPhysicsSystem.getBodyLockInterface(), hit.getBodyId());
	if (lock.succeededAndIsInBroadPhase())
	{
		// Get the leaf shape (mesh shape in this case)
		int[] remainder={cEmptySubShapeId};
		ConstShape shape = lock.getBody().getShape().getLeafShape(hit.getSubShapeId2(), remainder);
		if (shape.getType() == EShapeType.Mesh)
		{
			// Get user data from the triangle that was hit
			int user_data = ((MeshShape)shape).getTriangleUserData(remainder[0]);

			// Draw it on screen
			RVec3 hit_pos = ray.getPointOnRay(hit.getFraction());
			mDebugRenderer.drawText3D(hit_pos, String.format("UserData: %d", user_data));
		}
	}
        lock.releaseLock();
}
}
