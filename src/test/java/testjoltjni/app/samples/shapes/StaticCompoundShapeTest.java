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
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt-Physics static compound-shape
 * test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Shapes/StaticCompoundShapeTest.cpp
 */
public class StaticCompoundShapeTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	// Simple compound
	StaticCompoundShapeSettings compound_shape1 = new StaticCompoundShapeSettings();
	compound_shape1.addShape(Vec3.sZero(), Quat.sIdentity(), new CapsuleShape(5, 1));
	compound_shape1.addShape(new Vec3(0, -5, 0), Quat.sIdentity(), new SphereShape(2));
	compound_shape1.addShape(new Vec3(0, 5, 0), Quat.sIdentity(), new SphereShape(2));

	// Compound with sub compound and rotation
	StaticCompoundShapeSettings sub_compound = new StaticCompoundShapeSettings();
	sub_compound.addShape(new Vec3(0, 1.5f, 0), Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI), new BoxShape(new Vec3(1.5f, 0.25f, 0.2f)));
	sub_compound.addShape(new Vec3(1.5f, 0, 0), Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI), new CylinderShape(1.5f, 0.2f));
	sub_compound.addShape(new Vec3(0, 0, 1.5f), Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI), new TaperedCapsuleShapeSettings(1.5f, 0.25f, 0.2f));

	StaticCompoundShapeSettings compound_shape2 = new StaticCompoundShapeSettings();
	compound_shape2.addShape(new Vec3(0, 0, 0), star(Quat.sRotation(Vec3.sAxisX(), -0.25f * JPH_PI) , Quat.sRotation(Vec3.sAxisZ(), 0.25f * JPH_PI)), sub_compound);
	compound_shape2.addShape(new Vec3(0, -0.1f, 0), star(Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI) , Quat.sRotation(Vec3.sAxisZ(), -0.75f * JPH_PI)), sub_compound);

	// Compound with large amount of sub shapes
	StaticCompoundShapeSettings compound_shape3 = new StaticCompoundShapeSettings();
	for (int y = -2; y <= 2; ++y)
		for (int x = -2; x <= 2; ++x)
			for (int z = -2; z <= 2; ++z)
				compound_shape3.addShape(new Vec3(0.5f * x, 0.5f * y, 0.5f * z), star(Quat.sRotation(Vec3.sAxisX(), -0.25f * JPH_PI) , Quat.sRotation(Vec3.sAxisZ(), 0.25f * JPH_PI)), new BoxShape(Vec3.sReplicate(0.5f)));

	StaticCompoundShapeSettings shapes[] = { compound_shape1, compound_shape2, compound_shape3 };

	for (int i = 0; i < 10; ++i)
		for (int j = 0; j < 3; ++j)
		{
			Quat rotation;
			if ((i & 1) == 0)
				rotation = Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI);
			else
				rotation = Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI);
			mBodyInterface.createAndAddBody(new BodyCreationSettings(shapes[j],new RVec3(0, 10.0f + 4.0f * i, j * 20.0f), rotation, EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
		}
}
}
