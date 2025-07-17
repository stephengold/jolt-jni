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
package testjoltjni.app.samples.general;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.readonly.*;
import java.util.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt-Physics center-of-mass test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/CenterOfMassTest.cpp
 */
public class CenterOfMassTest extends Test{

void Initialize()
{
	// Floor
	CreateFloor();

	// Compound shape with center of mass offset
	StaticCompoundShapeSettings compound_shape1 = new StaticCompoundShapeSettings();
	compound_shape1.addShape(new Vec3(10, 0, 0), Quat.sIdentity(), new SphereShape(2));
	mBodyInterface.createAndAddBody(new BodyCreationSettings(compound_shape1,new RVec3(0, 10.0f, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Create box with center of mass offset
	List<Vec3Arg> box=new ArrayList<>(8);
	box.add(new Vec3(10, 10, 10));
	box.add(new Vec3(5, 10, 10));
	box.add(new Vec3(10, 5, 10));
	box.add(new Vec3(5, 5, 10));
	box.add(new Vec3(10, 10, 5));
	box.add(new Vec3(5, 10, 5));
	box.add(new Vec3(10, 5, 5));
	box.add(new Vec3(5, 5, 5));
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ConvexHullShapeSettings(box),new RVec3(0, 10.0f, 20.0f), Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Compound
	StaticCompoundShapeSettings compound_shape2 = new StaticCompoundShapeSettings();
	Quat rotation = Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI);
	compound_shape2.addShape(new Vec3(10, 0, 0), rotation, new CapsuleShape(5, 1));
	compound_shape2.addShape(star(rotation ,new Vec3(10, -5, 0)), Quat.sIdentity(), new SphereShape(4));
	compound_shape2.addShape(star(rotation ,new Vec3(10, 5, 0)), Quat.sIdentity(), new SphereShape(2));
	mBodyInterface.createAndAddBody(new BodyCreationSettings(compound_shape2,new RVec3(0, 10.0f, 40.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
}
}