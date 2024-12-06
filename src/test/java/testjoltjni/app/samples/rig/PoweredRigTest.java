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
package testjoltjni.app.samples.rig;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
/**
 * A line-for-line Java translation of the Jolt Physics powered-rig test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Shapes/PoweredRigTest.cpp
 */
public class PoweredRigTest extends Test{
float mTime;
RagdollRef mRagdoll=new RagdollRef();
RagdollSettingsRef mRagdollSettings=new RagdollSettingsRef();
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

String sAnimationName = "sprint";

public void Cleanup()
{
	mRagdoll.removeFromPhysicsSystem();
}

public void Initialize()
{
	// Floor
	CreateFloor();

	// Load ragdoll
	mRagdollSettings = RagdollLoader.sLoad("Assets/Human.tof", EMotionType.Dynamic).toRef();

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
	// Update time
	mTime += inParams.mDeltaTime;

	// Sample new pose
	mAnimation.sample(mTime, mPose);

	// Place the root joint on the first body so that we draw the pose in the right place
	RVec3 root_offset=new RVec3();
	JointState joint = mPose.getJoint(0);
	joint.setTranslation ( Vec3.sZero()); // All the translation goes into the root offset
        Quat root_rotation=new Quat();
	mRagdoll.getRootTransform(root_offset, root_rotation);joint.setRotation(root_rotation);
	mPose.setRootOffset(root_offset);
	mPose.calculateJointMatrices();
if(implementsDebugRendering())
	mPose.draw(inParams.mPoseDrawSettings, mDebugRenderer);
// JPH_DEBUG_RENDERER

	mRagdoll.driveToPoseUsingMotors(mPose);
}
/*TODO

void PoweredRigTest.CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI.CreateTextButton(inSubMenu, "Select Animation", [this, inUI]() {
		UIElement *animation_name = inUI.CreateMenu();
		for (uint i = 0; i < size(sAnimations); ++i)
			inUI.CreateTextButton(animation_name, sAnimations[i], [this, i]() { sAnimationName = sAnimations[i]; RestartTest(); });
		inUI.ShowMenu(animation_name);
	});
}*/

public void SaveState(StateRecorder inStream)
{
	inStream.write(mTime);
}

public void RestoreState(StateRecorder inStream)
{
	mTime=inStream.readFloat(mTime);
}
}