/*
Copyright (c) 2026 Stephen Gold

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
package testjoltjni.app.performancetest;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.std.*;
import java.util.*;

/**
 * A line-for-line Java translation of the Jolt-Physics "convex hull pile scene"
 * performance test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/PerformanceTest/ConvexHullPileScene.h
 */
// A scene that drops a number of complex convex hulls on a flat floor
class ConvexHullPileScene implements PerformanceTestScene
{
public
	String   	GetName()
	{
		return "ConvexHullPile";
	}

	public boolean			Load(  )
	{
		Mt19937 random=new Mt19937();
		UniformFloatDistribution random_float = new UniformFloatDistribution(         -1.0f,  1.0f );

		final int cNumPoints = 40;
		for (int i = 0; i < 100; ++i)
		{
			VertexList points=new VertexList(cNumPoints);
			for (int j = 0; j < cNumPoints; ++j)
				points.pushBack(1.5f * random_float.nextFloat(random), 0.5f * random_float.nextFloat(random), 0.75f * random_float.nextFloat(random));
			mShapes.pushBack(new ConvexHullShapeSettings(points).create().get());
		}

		return true;
	}

	public void			StartTest(PhysicsSystem inPhysicsSystem, EMotionQuality inMotionQuality)
	{
		BodyInterface bi = inPhysicsSystem.getBodyInterface();

		// Create floor
		BodyCreationSettings floor=new BodyCreationSettings(new BoxShape(new Vec3(50.0f, 1.0f, 50.0f)),new RVec3(0, -0.5, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
		floor.setFriction ( 1.0f);
		bi.createAndAddBody(floor, EActivation.DontActivate);

		// Construct bodies
		Mt19937 random=new Mt19937();
		int next_shape = 0;
		for (int x = 0; x < 10; ++x)
			for (int y = 0; y < 40; ++y)
				for (int z = 0; z < 10; ++z)
				{
					BodyCreationSettings bcs=new BodyCreationSettings(mShapes.get(next_shape),new RVec3(x * 2.0 - 10.0, y * 2.0 + 1.0, z * 2.0 - 10.0), Quat.sRandom(random), EMotionType.Dynamic, Layers.MOVING);
					bcs.setFriction ( 1.0f);
					bi.createAndAddBody(bcs, EActivation.Activate);
					next_shape = (next_shape + 1) % (int)mShapes.size();
				}
	}

private
	ShapeList	mShapes=new ShapeList();
};