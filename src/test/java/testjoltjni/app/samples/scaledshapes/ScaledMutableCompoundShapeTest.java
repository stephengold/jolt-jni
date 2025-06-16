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
/**
 * A line-for-line Java translation of the Jolt-Physics scaled mutable compound-shape test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/ScaledShapes/ScaledMutableCompoundShapeTest.cpp
 */
public class ScaledMutableCompoundShapeTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	// Left end
	List<Vec3Arg> end1=new ArrayList<>();
	end1.add(new Vec3(0, 0, 0));
	end1.add(new Vec3(0, 0, 1));
	end1.add(new Vec3(2, 0, 0));
	end1.add(new Vec3(2, 0, 1));
	end1.add(new Vec3(0, 1, 0));
	end1.add(new Vec3(0, 1, 1));
	end1.add(new Vec3(2, 1, 0));
	end1.add(new Vec3(2, 1, 1));
	ShapeSettings end1_shape = new ConvexHullShapeSettings(end1);

	// Right end
	List<Vec3Arg> end2=new ArrayList<>();
	end2.add(new Vec3(0, 0, 0));
	end2.add(new Vec3(0, 0, 5));
	end2.add(new Vec3(0, 1, 0));
	end2.add(new Vec3(0, 1, 5));
	end2.add(new Vec3(1, 0, 0));
	end2.add(new Vec3(1, 0, 5));
	end2.add(new Vec3(1, 1, 0));
	end2.add(new Vec3(1, 1, 5));
	ShapeSettings end2_shape = new ConvexHullShapeSettings(end2);

	// Central part
	List<Vec3Arg> center=new ArrayList<>();
	center.add(new Vec3(0, 0, 0));
	center.add(new Vec3(0, 0, 1));
	center.add(new Vec3(0, 1, 0));
	center.add(new Vec3(0, 1, 1));
	center.add(new Vec3(10, 0, 0));
	center.add(new Vec3(10, 0, 1));
	center.add(new Vec3(10, 1, 0));
	center.add(new Vec3(10, 1, 1));
	ShapeSettings center_shape = new ConvexHullShapeSettings(center);

	// Create compound
	MutableCompoundShapeSettings compound_shape = new MutableCompoundShapeSettings();
	compound_shape.addShape(new Vec3(-5, -1.5f, -0.5f), Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI), end1_shape);
	compound_shape.addShape(new Vec3(5, -0.5f, -0.5f), Quat.sIdentity(), end2_shape);
	compound_shape.addShape(new Vec3(-5, -0.5f, -0.5f), Quat.sIdentity(), center_shape);

	// Original shape
	mBodyInterface.createAndAddBody(new BodyCreationSettings(compound_shape,new RVec3(-40, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Uniformly scaled shape
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(compound_shape, Vec3.sReplicate(0.25f)),new RVec3(-20, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Non-uniform scaled shape
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(compound_shape,new Vec3(0.25f, 0.5f, 1.5f)),new RVec3(0, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Flipped in 2 axis
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(compound_shape,new Vec3(-0.25f, 0.5f, -1.5f)),new RVec3(20, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Inside out
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new ScaledShapeSettings(compound_shape,new Vec3(-0.25f, 0.5f, 1.5f)),new RVec3(40, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
}
}