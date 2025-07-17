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
package testjoltjni.app.samples.rig;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
/**
 * A line-for-line Java translation of the Jolt-Physics kinematic-rig test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Rig/RigPileTest.cpp
 */
public class KinematicRigTest extends Test{
float mTime;
RagdollSettingsRef mRagdollSettings=new RagdollSettingsRef();
RagdollRef mRagdoll=new RagdollRef();
SkeletalAnimationRef mAnimation=new SkeletalAnimationRef();
SkeletonPose mPose=new SkeletonPose();

final String sAnimations[] =
{
	"neutral",
	"walk",
	"sprint",
	"dead_pose1",
	"dead_pose2",
	"dead_pose3",
	"dead_pose4"
};

String sAnimationName = "walk";

public void Cleanup()
{
	mRagdoll.removeFromPhysicsSystem();
}

void Initialize()
{
	// Floor
	CreateFloor();

	// Wall
	ShapeRefC box_shape = new BoxShape(new Vec3(0.2f, 0.2f, 0.2f), 0.01f).toRefC();
	for (int i = 0; i < 3; ++i)
		for (int j = i / 2; j < 10 - (i + 1) / 2; ++j)
		{
			RVec3 position=new RVec3(-2.0f + j * 0.4f + ((i & 1)!=0? 0.2f : 0.0f), 0.2f + i * 0.4f, -2.0f);
			mBodyInterface.createAndAddBody(new BodyCreationSettings(box_shape, position, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.DontActivate);
		}

	// Load ragdoll
	mRagdollSettings = RagdollLoader.sLoad("Assets/Human.tof", EMotionType.Kinematic).toRef();

	// Create ragdoll
	mRagdoll = mRagdollSettings.createRagdoll(0, 0, mPhysicsSystem).toRef();
	mRagdoll.addToPhysicsSystem(EActivation.Activate);

	// Load animation
	String filename = (String)("Assets/Human/") + sAnimationName + ".tof";
	if (!ObjectStreamIn.sReadObject(filename, mAnimation))
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
if(implementsDebugRendering())
	mPose.draw(inParams.mPoseDrawSettings, mDebugRenderer);
// JPH_DEBUG_RENDERER

	// Update time
	mTime += inParams.mDeltaTime;

	// Sample new pose
	mAnimation.sample(mTime, mPose);
	mPose.calculateJointMatrices();

	mRagdoll.driveToPoseUsingKinematics(mPose, inParams.mDeltaTime);
}
/*TODO

void KinematicRigTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI->CreateTextButton(inSubMenu, "Select Animation", [this, inUI]() {
		UIElement *animation_name = inUI->CreateMenu();
		for (uint i = 0; i < size(sAnimations); ++i)
			inUI->CreateTextButton(animation_name, sAnimations[i], [this, i]() { sAnimationName = sAnimations[i]; RestartTest(); });
		inUI->ShowMenu(animation_name);
	});
}
*/

protected void SaveState(StateRecorder inStream)
{
	inStream.write(mTime);
}

protected void RestoreState(StateRecorder inStream)
{
	mTime=inStream.readFloat(mTime);
}
}