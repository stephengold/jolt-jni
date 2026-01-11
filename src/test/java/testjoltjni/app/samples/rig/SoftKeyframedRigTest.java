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
package testjoltjni.app.samples.rig;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt-Physics soft-keyframed rig test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Rig/SoftKeyframedRigTest.cpp
 */
public class SoftKeyframedRigTest extends Test{
float mTime;
RagdollSettingsRef mRagdollSettings;
RagdollRef mRagdoll;
SkeletalAnimationRef mAnimation=new SkeletalAnimationRef();
SkeletonPose mPose=new SkeletonPose();

public void Cleanup()
{
	mRagdoll.removeFromPhysicsSystem();
}

public void Initialize()
{
	// Floor
	CreateFloor();

	// Wall
	ShapeRefC box_shape = new BoxShape(new Vec3(0.2f, 0.2f, 0.2f), 0.01f).toRefC();
	for (int i = 0; i < 3; ++i)
		for (int j = i / 2; j < 10 - (i + 1) / 2; ++j)
		{
			RVec3 position=new RVec3(-2.0f + j * 0.4f + (((i & 1)!=0)? 0.2f : 0.0f), 0.2f + i * 0.4f, -2.0f);
			mBodyInterface.createAndAddBody(new BodyCreationSettings(box_shape, position, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.DontActivate);
		}

	// Bar to hit head against
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(2.0f, 0.1f, 0.1f), 0.01f),new RVec3(0, 1.5f, -2.0f), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Load ragdoll
	mRagdollSettings = RagdollLoader.sLoad("Assets/Human.tof", EMotionType.Dynamic).toRef();

	// Limit max velocity of the bodies to avoid excessive jittering when the head hits the bar
	// Note that this also limits how fast an animation can be and as a result you can see
	// the ragdolls lag behind when the animation loops.
	// Note that the velocity doesn't need to be limited at body level, it can also be done
	// by calculating the needed velocities and clamping them instead of calling DriveToPoseUsingKinematics.
	for (BodyCreationSettings bcs : mRagdollSettings.getParts())
		bcs.setMaxLinearVelocity ( 10.0f);

	// Create ragdoll
	mRagdoll = mRagdollSettings.createRagdoll(0, 0, mPhysicsSystem).toRef();
	mRagdoll.addToPhysicsSystem(EActivation.Activate);

	// Load animation
	if (!ObjectStreamIn.sReadObject("Assets/Human/walk.tof", mAnimation))
		FatalError("Could not open animation");

	// Initialize pose
	mPose.setSkeleton(mRagdollSettings.getSkeleton());

	// Position ragdoll
	mAnimation.sample(0.0f, mPose);
	mPose.calculateJointMatrices();
	mRagdoll.setPose(mPose);
}

public void PrePhysicsUpdate(PreUpdateParams inParams)
{
	// Sample previous pose and draw it (ragdoll should have achieved this position)
	mAnimation.sample(mTime, mPose);
	mPose.calculateJointMatrices();
if(Jolt.implementsDebugRendering()){
	mPose.draw(inParams.mPoseDrawSettings, mDebugRenderer);
} // JPH_DEBUG_RENDERER

	// Update time
	mTime += inParams.mDeltaTime;

	// Sample new pose
	mAnimation.sample(mTime, mPose);
	mPose.calculateJointMatrices();

	// Drive the ragdoll by setting velocities
	mRagdoll.driveToPoseUsingKinematics(mPose, inParams.mDeltaTime);

	// Cancel gravity that will be applied in the next step
	mRagdoll.addLinearVelocity(star(mPhysicsSystem.getGravity() , inParams.mDeltaTime));
}

public void SaveState(StateRecorder inStream)
{
	inStream.write(mTime);
}

public void RestoreState(StateRecorder inStream)
{
	mTime=inStream.readFloat(mTime);
}
}
