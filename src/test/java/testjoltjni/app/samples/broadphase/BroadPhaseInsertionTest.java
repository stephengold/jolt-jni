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
package testjoltjni.app.samples.broadphase;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.readonly.ConstBody;
import com.github.stephengold.joltjni.std.*;
import java.util.List;
import testjoltjni.TestUtils;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import static com.github.stephengold.joltjni.std.Std.*;
import static testjoltjni.app.samples.DebugRendererSP.*;
/**
 * A line-for-line Java translation of the Jolt Physics broadphase insertion test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/BroadPhase/BroadPhaseInsertionTest.cpp
 */
public class BroadPhaseInsertionTest extends BroadPhaseTest{
DefaultRandomEngine mRandomGenerator=new DefaultRandomEngine();
int mCurrentBody;
int mDirection=1;

public void Initialize()
{
	super.Initialize();

	CreateBalancedDistribution(mBodyManager, mBodyManager.getMaxBodies());
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	// Check if we need to change direction
	if (mDirection == 1 && mCurrentBody >= mBodyManager.getMaxBodies())
		mDirection = -1;
	if (mDirection == -1 && mCurrentBody == 0)
		mDirection = 1;

	int num_this_step = (int)(mBodyManager.getMaxBodies() / 10);

	if (mDirection < 0)
		mCurrentBody -= num_this_step;

	List<Body> body_vector = mBodyManager.getBodies().toList();

	// Randomly move bodies around
	if (mCurrentBody > 0)
	{
		final int cNumBodiesToMove = 100;
		BodyIdArray bodies_to_move = new BodyIdArray (cNumBodiesToMove);
		UniformIntDistribution body_selector=new UniformIntDistribution(0, (int)mCurrentBody - 1);
		UniformRealDistribution translation_selector=new UniformRealDistribution(1.0f, 5.0f);
		for (int i = 0; i < cNumBodiesToMove; ++i)
		{
			Body body = body_vector.get(body_selector.nextInt(mRandomGenerator));
			assert(body.isInBroadPhase());
			body.setPositionAndRotationInternal(plus(body.getPosition() , star(translation_selector.nextFloat(mRandomGenerator) , Vec3.sRandom(mRandomGenerator))), Quat.sIdentity());
			bodies_to_move.set(i,  body.getId());
		}
		mBroadPhase.notifyBodiesAabbChanged(bodies_to_move, cNumBodiesToMove);
		TestUtils.testClose ( bodies_to_move);
	}

	// Create batch of bodies
	BodyIdArray bodies_to_add_or_remove = new BodyIdArray (num_this_step);
	for (int b = 0; b < num_this_step; ++b)
		bodies_to_add_or_remove.set(b,  body_vector.get(mCurrentBody + b).getId());

	// Add/remove them
	if (mDirection == 1)
	{
		// Prepare and abort
		long add_state = mBroadPhase.addBodiesPrepare(bodies_to_add_or_remove, num_this_step);
		mBroadPhase.addBodiesAbort(bodies_to_add_or_remove, num_this_step, add_state);

		// Prepare and add
		add_state = mBroadPhase.addBodiesPrepare(bodies_to_add_or_remove, num_this_step);
		mBroadPhase.addBodiesFinalize(bodies_to_add_or_remove, num_this_step, add_state);
	}
	else
		mBroadPhase.removeBodies(bodies_to_add_or_remove, num_this_step);

	// Delete temp array
	TestUtils.testClose ( bodies_to_add_or_remove);

	// Create ray
	DefaultRandomEngine random=new DefaultRandomEngine();
	Vec3 from = star(1000.0f , Vec3.sRandom(random));
	RayCast ray =new RayCast(from, star(-2.0f , from) );

	// Raycast before update
	AllHitRayCastBodyCollector hits_before=new AllHitRayCastBodyCollector();
	mBroadPhase.castRay(ray, hits_before);
	int num_before = (int)hits_before.getHits().length;
	BroadPhaseCastResult []results_before = hits_before.getHits();
	Trace("Before update: %d results found", num_before);

	// Draw results
	DrawLineSP(mDebugRenderer, ray.getOrigin(), plus(ray.getOrigin() , ray.getDirection()), Color.sRed);
	for (int i = 0; i < num_before; ++i)
		DrawMarkerSP(mDebugRenderer, ray.getPointOnRay(results_before[i].getFraction()), Color.sGreen, 10.0f);

	// Update the broadphase
	mBroadPhase.optimize();

	// Raycast after update
	AllHitRayCastBodyCollector hits_after=new AllHitRayCastBodyCollector();
	mBroadPhase.castRay(ray, hits_after);
	int num_after = (int)hits_after.getHits().length;
	BroadPhaseCastResult []results_after = hits_after.getHits();
	Trace("After update: %d results found", num_after);

	// Before update we may have some false hits, check that there are less hits after update than before
	if (num_after > num_before)
		FatalError("BroadPhaseInsertionTest: After has more hits than before");
	for (BroadPhaseCastResult ra : results_after)
	{
		boolean found = false;
		for (BroadPhaseCastResult rb : results_before)
			if (isEqual(ra.getBodyId() , rb.getBodyId()))
			{
				found = true;
				break;
			}
		if (!found)
			FatalError("BroadPhaseInsertionTest: Result after not found in result before");
	}

	// Validate with brute force approach
	for ( ConstBody b : mBodyManager.getBodies().toList())
	{
		boolean found = false;
		for (BroadPhaseCastResult r : results_after)
			if (isEqual(r.getBodyId() , b.getId()))
			{
				found = true;
				break;
			}

		if (b.isInBroadPhase()
			&& rayAaBoxHits(ray.getOrigin(), ray.getDirection(), b.getWorldSpaceBounds().getMin(), b.getWorldSpaceBounds().getMax()))
		{
			if (!found)
				FatalError("BroadPhaseInsertionTest: Is intersecting but was not found");
		}
		else
		{
			if (found)
				FatalError("BroadPhaseInsertionTest: Is not intersecting but was found");
		}
	}

	if (mDirection > 0)
		mCurrentBody += num_this_step;
}
}