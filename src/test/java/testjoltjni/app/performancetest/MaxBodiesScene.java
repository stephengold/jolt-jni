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
import com.github.stephengold.joltjni.operator.Op;

/**
 * A line-for-line Java translation of the Jolt-Physics "max-bodies scene"
 * performance test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/PerformanceTest/MaxBodiesScene.h
 */

// A scene that creates the max number of bodies that Jolt supports and simulates them
class MaxBodiesScene implements  PerformanceTestScene
{


	public String  	GetName()  
	{
		return "MaxBodies";
	}




	public void			StartTest(PhysicsSystem inPhysicsSystem, EMotionQuality inMotionQuality)
	{
		BodyInterface bi = inPhysicsSystem.getBodyInterface();

		// Reduce the solver iteration count in the interest of performance
		PhysicsSettings settings = inPhysicsSystem.getPhysicsSettings();
		settings.setNumVelocitySteps ( 4);
		settings.setNumPositionSteps ( 1);
		inPhysicsSystem.setPhysicsSettings(settings);

		// Create the bodies
		int num_bodies = inPhysicsSystem.getMaxBodies();
		int num_constraints = 0;
		BodyIdVector body_ids=new BodyIdVector();
                body_ids.reserve(num_bodies);
		int num_per_axis = (int)(Math.pow((float)(num_bodies), 1.0f / 3.0f)) + 1;
		Vec3 half_extent = Vec3.sReplicate(0.5f);
		BodyCreationSettings bcs=new BodyCreationSettings(new BoxShape(half_extent), RVec3.sZero(), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
		bcs.setOverrideMassProperties ( EOverrideMassProperties.MassAndInertiaProvided);
		bcs.getMassPropertiesOverride().setMassAndInertiaOfSolidBox(Op.star(2.0f , half_extent), 1000.0f);
		for (int z = 0; z < num_per_axis && body_ids.size() < num_bodies; ++z)
			for (int y = 0; y < num_per_axis && body_ids.size() < num_bodies; ++y)
				for (int x = 0; x < num_per_axis && body_ids.size() < num_bodies; ++x)
				{
					// When we reach the limit of contact constraints, start placing the boxes further apart so they don't collide
					bcs.setPosition  (num_constraints < PhysicsSystem.cMaxContactConstraintsLimit()? new RVec3(x,x,x) : new RVec3(2.0 * x, 2.0 * y, 2.0 * z));
					body_ids.pushBack(bi.createBody(bcs).getId());

					// From the 2nd box onwards in a row, we will get a contact constraint
					if (x > 0)
						++num_constraints;
				}

		// Add the bodies to the simulation
		long state = bi.addBodiesPrepare(body_ids.data(), num_bodies);
		bi.addBodiesFinalize(body_ids.data(), num_bodies, state, EActivation.Activate);
	}
};
