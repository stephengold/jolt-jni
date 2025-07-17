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
package testjoltjni.app.samples.broadphase;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.std.DefaultRandomEngine;
import java.util.List;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import static testjoltjni.app.samples.DebugRendererSP.*;
/**
 * A line-for-line Java translation of the Jolt-Physics broad-phase ray-cast
 * test: a simple test that casts a ray through the broadphase.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/BroadPhase/BroadPhaseCastRayTest.cpp
 */
public class BroadPhaseCastRayTest extends BroadPhaseTest{

public void Initialize()
{
	super.Initialize();

	int num_bodies = (int)(mBodyManager.getMaxBodies());

	// Create random boxes
	CreateBalancedDistribution(mBodyManager, num_bodies);

	// Add all bodies to the broadphase
	List<Body> body_vector = mBodyManager.getBodies().toList();
	BodyIdArray bodies_to_add = new BodyIdArray (num_bodies);
	for (int b = 0; b < num_bodies; ++b)
		bodies_to_add.set(b, body_vector.get(b).getId());
	long add_state = mBroadPhase.addBodiesPrepare(bodies_to_add, num_bodies);
	mBroadPhase.addBodiesFinalize(bodies_to_add, num_bodies, add_state);

	// Optimize the broadphase
	mBroadPhase.optimize();
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	// Create ray
	DefaultRandomEngine random=new DefaultRandomEngine();
	Vec3 from = star(1000.0f , Vec3.sRandom(random));
	RayCast ray = new RayCast(from, star(-2.0f , from) );

	// Raycast before update
	AllHitRayCastBodyCollector collector=new AllHitRayCastBodyCollector();
	mBroadPhase.castRay(ray, collector);
	int num_hits = (int)collector.getHits().size();
	List<BroadPhaseCastResult> results = collector.getHits();

	// Draw results
	for (int i = 0; i < num_hits; ++i)
		DrawMarkerSP(mDebugRenderer, ray.getPointOnRay(results.get(i).getFraction()), Color.sGreen, 10.0f);
	DrawLineSP(mDebugRenderer, ray.getOrigin(), plus(ray.getOrigin() , ray.getDirection()), Color.sRed);
}
}