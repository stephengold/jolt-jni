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
package testjoltjni.app.samples.general;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import java.util.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt Physics active-edges test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/ActiveEdgesTest.cpp
 */
public class ActiveEdgesTest extends Test{

public void Initialize()
{
	final float cWidth = 5.0f;
	final float cLength = 10.0f;

	// Settings for a frictionless box
	ShapeRef box_shape = new BoxShape(new Vec3(1.0f, 1.0f, 1.0f), cDefaultConvexRadius).toRef();
	BodyCreationSettings box_settings=new BodyCreationSettings(box_shape, RVec3.sZero(), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
	box_settings.setFriction ( 0.0f);
	box_settings.setLinearDamping ( 0.0f);
	box_settings.setAllowSleeping ( false);

	// Create various triangle strips
	List<Triangle> triangles=new ArrayList<>();
	for (int angle = -90; angle <= 90; angle++)
	{
		// Under which normal we want to place the block
		Vec3 desired_normal = angle < 0?new Vec3(0, 1, -1).normalized() :new Vec3(0, 1, 0);
		float best_dot = -FLT_MAX;

		// Place segments
		float x = cWidth * angle;
		Vec3 v1=new Vec3(x, 0.0f, -0.5f * cLength);
		Vec3 v2=new Vec3(x + cWidth, 0.0f, -0.5f * cLength);
		for (int total_angle = 0, cur_segment = 0; Math.abs(total_angle) <= 90 && cur_segment < 90; total_angle += angle, ++cur_segment)
		{
			// Determine positions of end of this segment
			float total_angle_rad = degreesToRadians((float)(total_angle));
			Quat rotation = Quat.sRotation(Vec3.sAxisX(), total_angle_rad);
			Vec3 delta = star(cLength , rotation.rotateAxisZ());
			Vec3 v3 = plus(v1 , delta);
			Vec3 v4 = plus(v2 , delta);

			// Check if this segment is the best segment to place the dynamic block on
			Vec3 normal = minus(v3 , v1).cross(minus(v2 , v1)).normalized();
			float dot = normal.dot(desired_normal);
			if (dot > best_dot)
			{
				best_dot = dot;
				box_settings.setPosition (new RVec3(plus(slash(Vec3.sum(v1 , v2 , v3 , v4) , 4) , normal)));
				box_settings.setRotation ( rotation);
			}

			// Add segment
			triangles.add(new Triangle(v1, v3, v4));
			triangles.add(new Triangle(v1, v4, v2));

			// Add segment mirrored in Z axis
			if (cur_segment != 0)
			{
				Vec3 flip=new Vec3(1, 1, -1);
				triangles.add(new Triangle(star(flip , v1), star(flip , v4), star(flip , v3)));
				triangles.add(new Triangle(star(flip , v1), star(flip , v2), star(flip , v4)));
			}

			// The end of the segment will be the start for the next iteration
			v1 = v3;
			v2 = v4;
		}

		// Place box on best segment
		Body body = mBodyInterface.createBody(box_settings);
		mBodyInterface.addBody(body.getId(), EActivation.Activate);

		// For convex segments give the block a push
		if (angle >= 0)
			body.setLinearVelocity(new Vec3(0, 0, 2.0f));
	}

	// Mesh shape
	MeshShapeSettings mesh_shape=new MeshShapeSettings(triangles);
	mesh_shape.setEmbedded();
	mesh_shape.setActiveEdgeCosThresholdAngle ( cos(degreesToRadians(50.0f)));

	// Mesh
	BodyCreationSettings mesh_settings=new BodyCreationSettings(mesh_shape, RVec3.sZero(), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
	// Instead of setting mActiveEdgeCosThresholdAngle you can also set: mesh_settings.mEnhancedInternalEdgeRemoval = true
	mesh_settings.setFriction ( 0.0f);
	mBodyInterface.createAndAddBody(mesh_settings, EActivation.DontActivate);
}
}
