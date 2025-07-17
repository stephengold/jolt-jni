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
import com.github.stephengold.joltjni.std.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import static com.github.stephengold.joltjni.std.Std.*;
/**
 * A line-for-line Java translation of the Jolt-Physics abstract broad-phase
 * test class.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/BroadPhase/BroadPhaseTest.cpp
 */
// Base class for a test involving only the broad phase
abstract class BroadPhaseTest extends Test{

int NUM_BODIES =	10000;

protected BPLayerInterfaceImpl mBroadPhaseLayerInterface=new BPLayerInterfaceImpl();
protected BroadPhase mBroadPhase;
protected BodyManager mBodyManager;
//#define BROAD_PHASE		BroadPhaseBruteForce()

void CreateBalancedDistribution(BodyManager inBodyManager,int inNumBodies){CreateBalancedDistribution(inBodyManager,inNumBodies,512f);}

void CreateBalancedDistribution(BodyManager inBodyManager, int inNumBodies, float inEnvironmentSize)
{
	DefaultRandomEngine random=new DefaultRandomEngine(0x1ee7c0de);
	UniformFloatDistribution zero_to_one=new UniformFloatDistribution(0.0f, 1.0f);
	float n = (float)(inNumBodies);
	Vec3 max_box_start = Vec3.sReplicate(inEnvironmentSize * (1.0f - pow(n, -1.0f / 3.0f)));
	Vec3 min_box_size = Vec3.sReplicate(1.0f / inEnvironmentSize);
	Vec3 max_box_size = minus(Vec3.sReplicate(inEnvironmentSize * pow(n, -1.0f / 3.0f)) , min_box_size);
	for (int b = 0; b < inNumBodies; ++b)
	{
		AaBox box=new AaBox();
		box.setMin ( minus(star(max_box_start, new Vec3(zero_to_one.nextFloat(random), zero_to_one.nextFloat(random), zero_to_one.nextFloat(random))) , Vec3.sReplicate(0.5f * inEnvironmentSize)));
		box.setMax ( Vec3.sum(box.getMin() , min_box_size , star(max_box_size ,new Vec3(zero_to_one.nextFloat(random), zero_to_one.nextFloat(random), zero_to_one.nextFloat(random)))));

		BodyCreationSettings s=new BodyCreationSettings();
		s.setShape(new BoxShape(box.getExtent(), 0.0f));
		s.setPosition ( new RVec3(box.getCenter()));
		s.setRotation ( Quat.sIdentity());
		s.setObjectLayer ( (random.nextInt() % 10) == 0? Layers.MOVING : Layers.NON_MOVING);
		Body body = inBodyManager.allocateBody(s);
		inBodyManager.addBody(body);
	}
}

public void Initialize()
{
	// Create body manager
	mBodyManager = new BodyManager();
	mBodyManager.init(NUM_BODIES, 0, mBroadPhaseLayerInterface);

	// Crate broadphase
	mBroadPhase = new BroadPhaseQuadTree();
	mBroadPhase.init(mBodyManager, mBroadPhaseLayerInterface);
}

public void PostPhysicsUpdate(float inDeltaTime)
{
if (implementsDebugRendering()) {
	mBodyManager.draw(new BodyManagerDrawSettings(),new PhysicsSettings(), mDebugRenderer);
} // JPH_DEBUG_RENDERER
}
}
