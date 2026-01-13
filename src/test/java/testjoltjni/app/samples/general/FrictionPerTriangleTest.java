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
import static com.github.stephengold.joltjni.std.Std.*;
/**
 * A line-for-line Java translation of the Jolt-Physics friction per-triangle test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/FrictionPerTriangleTest.cpp
 */
public class FrictionPerTriangleTest extends Test{
static class MyMaterial extends PhysicsMaterialSimple{
    float mFriction,mRestitution;
    MyMaterial(String inName,ConstColor inColor,float inFriction,float inRestitution){
        super(inName, inColor);
        mFriction=inFriction;
        mRestitution=inRestitution;
    };
}

public void Initialize()
{
	final int num_sections = 5;
	final float section_size = 50.0f;

	// Create a strip of triangles
	List<Triangle> triangles=new ArrayList<>(2*num_sections+2);
	for (int z = 0; z <= num_sections; ++z)
	{
		float z1 = section_size * (z - 0.5f * num_sections);
		float z2 = z1 + section_size;

		ConstFloat3 v1 =new Float3(-100.0f, 0, z1);
		ConstFloat3 v2 =new Float3(100.0f, 0, z1);
		ConstFloat3 v3 =new Float3(-100.0f, 0, z2);
		ConstFloat3 v4 =new Float3(100.0f, 0, z2);

		triangles.add(new Triangle(v1, v3, v4, z));
		triangles.add(new Triangle(v1, v4, v2, z));
	}

	// Create materials with increasing friction
	PhysicsMaterialList materials=new PhysicsMaterialList();
	for (int i = 0; i <= num_sections; ++i)
	{
		float friction = (float)(i) / (float)(num_sections);
		materials.pushBack(new MyMaterial("Friction " + (friction), Color.sGetDistinctColor(i), friction, 0.0f).toRef());
	}

	// A ramp
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new MeshShapeSettings(triangles, materials), RVec3.sZero(), Quat.sRotation(Vec3.sAxisX(), 0.2f * JPH_PI), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// A box with friction 1 that slides down the ramp
	ShapeRef box_shape = new BoxShape(new Vec3(2.0f, 2.0f, 2.0f), cDefaultConvexRadius, new MyMaterial("Box Friction 1", Color.sYellow, 1.0f, 0.0f)).toRef();
	mBodyInterface.createAndAddBody(new BodyCreationSettings(box_shape,new RVec3(0, 60.0f, -75.0f), Quat.sRotation(Vec3.sAxisX(), 0.2f * JPH_PI), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
}

void sGetFrictionAndRestitution(ConstBody  inBody, int inSubShapeID, float []outFriction, float []outRestitution)
{
	// Get the material that corresponds to the sub shape ID
	ConstPhysicsMaterial  material = inBody.getShape().getMaterial(inSubShapeID);
	if (material.targetVa() == PhysicsMaterial.sDefault().targetVa())
	{
		// This is the default material, use the settings from the body (note all bodies in our test have a material so this should not happen)
		outFriction[0] = inBody.getFriction();
		outRestitution[0] = inBody.getRestitution();
	}
	else
	{
		// If it's not the default material we know its a material that we created so we can cast it and get the values
		MyMaterial my_material = (MyMaterial)(material);
		outFriction[0] = my_material.mFriction;
		outRestitution[0] = my_material.mRestitution;
	}
}

void sOverrideContactSettings(ConstBody inBody1, ConstBody inBody2, ContactManifold inManifold, ContactSettings ioSettings)
{
	// Get the custom friction and restitution for both bodies
	float[] friction1=new float[1], friction2=new float[1], restitution1=new float[1], restitution2=new float[1];
	sGetFrictionAndRestitution(inBody1, inManifold.getSubShapeId1(), friction1, restitution1);
	sGetFrictionAndRestitution(inBody2, inManifold.getSubShapeId2(), friction2, restitution2);

	// Use the default formulas for combining friction and restitution
	ioSettings.setCombinedFriction ( sqrt(friction1[0] * friction2[0]));
	ioSettings.setCombinedRestitution ( Math.max(restitution1[0], restitution2[0]));
}

public void OnContactAdded(ConstBody  inBody1, ConstBody  inBody2, ContactManifold inManifold, ContactSettings ioSettings)
{
	sOverrideContactSettings(inBody1, inBody2, inManifold, ioSettings);
}

public void OnContactPersisted(ConstBody  inBody1, ConstBody  inBody2, ContactManifold inManifold, ContactSettings ioSettings)
{
	sOverrideContactSettings(inBody1, inBody2, inManifold, ioSettings);
}
}
