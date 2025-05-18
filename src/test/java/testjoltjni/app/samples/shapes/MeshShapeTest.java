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
import java.util.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
/**
 * A line-for-line Java translation of the Jolt Physics mesh-shape test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Shapes/MeshShapeTest.cpp
 */
public class MeshShapeTest extends Test{

public void Initialize()
{
	// Create regular grid of triangles
	int max_material_index = 0;
	List<Triangle> triangles=new ArrayList<>(800);
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

			int material_index = (int)(Vec3.sum(new Vec3(v1) ,new Vec3(v2) ,new Vec3(v3) ,new Vec3(v4)).length() / 40.0f);
			max_material_index = Math.max(max_material_index, material_index);
			triangles.add(new Triangle(v1, v3, v4, material_index));
			triangles.add(new Triangle(v1, v4, v2, material_index));
		}

	// Create materials
	PhysicsMaterialList materials=new PhysicsMaterialList();
	for (int i = 0; i <= max_material_index; ++i)
		materials.pushBack(new PhysicsMaterialSimple("Material " + i, Color.sGetDistinctColor(i)).toRef());

	// Floor
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new MeshShapeSettings(triangles, (materials)), RVec3.sZero(), Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// 1 body with zero friction to test active edge detection
	ShapeRefC box_shape = new BoxShape(new Vec3(2.0f, 2.0f, 2.0f), cDefaultConvexRadius, new PhysicsMaterialSimple("Box Material", Color.sYellow)).toRefC();
	Body body = mBodyInterface.createBody(new BodyCreationSettings(box_shape,new RVec3(0, 55.0f, -50.0f), Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI), EMotionType.Dynamic, Layers.MOVING));
	body.setFriction(0.0f);
	mBodyInterface.addBody(body.getId(), EActivation.Activate);
}
}
