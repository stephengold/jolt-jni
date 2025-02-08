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
package testjoltjni.app.samples.softbody;
import com.github.stephengold.joltjni.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt Physics soft-body custom-update test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/SoftBody/SoftBodyCustomUpdateTest.cpp
 */
public class SoftBodyCustomUpdateTest extends Test{
Body mBody;

public void Initialize()
{
	// Floor
	CreateFloor();

	// Create a body but do not add it to the physics system (we're updating it ourselves)
	SoftBodyCreationSettings sphere=new SoftBodyCreationSettings(SoftBodyCreator.CreateSphere(),new RVec3(0, 5, 0), Quat.sIdentity(), Layers.MOVING);
	sphere.setPressure ( 2000.0f);
	mBody = mBodyInterface.createSoftBody(sphere);
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	// Note that passing a variable delta time results in differences in behavior, usually you want to have a fixed time step.
	// For this demo we'll just clamp the delta time to 1/60th of a second and allow behavioral changes due to frame rate fluctuations.
	float dt = Math.min(inParams.mDeltaTime, 1.0f / 60.0f);

	// Call the update now
	SoftBodyMotionProperties mp = (SoftBodyMotionProperties )(mBody.getMotionProperties());
	mp.customUpdate(dt, mBody, mPhysicsSystem);

if (Jolt.implementsDebugRendering()){
	// Draw it as well since it's not added to the world
	mBody.getShape().draw(mDebugRenderer, mBody.getCenterOfMassTransform(), Vec3.sOne(), Color.sWhite, false, false);
}else{
	// Draw the vertices
	RMat44 com = mBody.getCenterOfMassTransform();
	for ( SoftBodyVertex v : mp.getVertices())
		mDebugRenderer.drawMarker(star(com , v.getPosition()), Color.sRed, 0.1f);
} // JPH_DEBUG_RENDERER
}
}
