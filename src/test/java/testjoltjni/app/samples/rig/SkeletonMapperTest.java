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
package testjoltjni.app.samples.rig;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt-Physics skeleton-mapper test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Rig/SkeletonMapperTest.cpp
 */
public class SkeletonMapperTest extends Test{
boolean sLockTranslations;
float mTime;
RagdollRef mRagdoll=new RagdollRef();
RagdollSettingsRef mRagdollSettings=new RagdollSettingsRef();
SkeletalAnimationRef mAnimation=new SkeletalAnimationRef();
SkeletonMapper mRagdollToAnimated=new SkeletonMapper();
SkeletonPose mAnimatedPose=new SkeletonPose();
SkeletonPose mRagdollPose=new SkeletonPose();

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

	// Load neutral animation for ragdoll
	SkeletalAnimationRef neutral_ragdoll=new SkeletalAnimationRef();
	if (!ObjectStreamIn.sReadObject("Assets/Human/neutral.tof", neutral_ragdoll))
		FatalError("Could not open neutral animation");

	// Load animation skeleton
	SkeletonRef animation_skeleton=new SkeletonRef();
	if (!ObjectStreamIn.sReadObject("Assets/Human/skeleton_hd.tof", animation_skeleton))
		FatalError("Could not open skeleton_hd");
	animation_skeleton.getPtr().calculateParentJointIndices();

	// Load neutral animation
	SkeletalAnimationRef neutral_animation=new SkeletalAnimationRef();
	if (!ObjectStreamIn.sReadObject("Assets/Human/neutral_hd.tof", neutral_animation))
		FatalError("Could not open neutral_hd animation");

	// Load test animation
	if (!ObjectStreamIn.sReadObject("Assets/Human/jog_hd.tof", mAnimation))
		FatalError("Could not open jog_hd animation");

	// Initialize pose
	mAnimatedPose.setSkeleton(animation_skeleton.getPtr());
	mRagdollPose.setSkeleton(mRagdollSettings.getSkeleton());

	// Calculate neutral poses and initialize skeleton mapper
	neutral_ragdoll.sample(0.0f, mRagdollPose);
	mRagdollPose.calculateJointMatrices();
	neutral_animation.sample(0.0f, mAnimatedPose);
	mAnimatedPose.calculateJointMatrices();
	mRagdollToAnimated.initialize(mRagdollPose.getSkeleton(), mRagdollPose.getJointMatrices(), mAnimatedPose.getSkeleton(), mAnimatedPose.getJointMatrices());

	// Optionally lock translations (this can be used to prevent ragdolls from stretching)
	// Try wildly dragging the ragdoll by the head (using spacebar) to see how the ragdoll stretches under stress
	if (sLockTranslations)
		mRagdollToAnimated.lockAllTranslations(mAnimatedPose.getSkeleton(), mAnimatedPose.getJointMatrices());

	// Calculate initial pose and set it
	CalculateRagdollPose();
	mRagdoll.setPose(mRagdollPose);
}

void CalculateRagdollPose()
{
	// Sample new animated pose
	mAnimation.sample(mTime, mAnimatedPose);
	mAnimatedPose.calculateJointMatrices();

	// Map to ragdoll pose
	mRagdollToAnimated.mapReverse(mAnimatedPose.getJointMatrices(), mRagdollPose.getJointMatrices());
	mRagdollPose.calculateJointStates();
}

public void PrePhysicsUpdate(PreUpdateParams inParams)
{
	// Update time
	mTime += inParams.mDeltaTime;

	// Drive the ragdoll pose and drive motors to reach it
	CalculateRagdollPose();
	mRagdoll.driveToPoseUsingMotors(mRagdollPose);

	RMat44 offset=null;
if(implementsDebugRendering()) {
	// Draw animated skeleton
	mAnimatedPose.draw(inParams.mPoseDrawSettings, mDebugRenderer);
	mDebugRenderer.drawText3D(plus(mAnimatedPose.getRootOffset() , mAnimatedPose.getJointMatrix(0).getTranslation()), "Animated", Color.sWhite, 0.2f);

	// Draw mapped skeleton
	offset = RMat44.sTranslation(new RVec3(1.0f, 0, 0));
	mRagdollPose.draw(inParams.mPoseDrawSettings, mDebugRenderer, offset);
	mDebugRenderer.drawText3D(star(offset , plus(mAnimatedPose.getRootOffset() , mAnimatedPose.getJointMatrix(0).getTranslation())), "Reverse Mapped", Color.sWhite, 0.2f);
} // JPH_DEBUG_RENDERER

	// Get ragdoll pose in model space
	RVec3 root_offset=new RVec3();
	Mat44Array pose1_model=new Mat44Array(mRagdollPose.getJointCount());
	mRagdoll.getPose(root_offset, pose1_model);

	// Get animated pose in local space
	Mat44Array pose2_local=new Mat44Array(mAnimatedPose.getJointCount());
	mAnimatedPose.calculateLocalSpaceJointMatrices(pose2_local);

	// Map ragdoll to animated pose, filling in the extra joints using the local space animated pose
	SkeletonPose pose2_world=new SkeletonPose();
	pose2_world.setSkeleton(mAnimatedPose.getSkeleton());
	pose2_world.setRootOffset(root_offset);
	mRagdollToAnimated.map(pose1_model, pose2_local, pose2_world.getJointMatrices());

if(implementsDebugRendering()){
	// Draw mapped pose
	pose2_world.draw(inParams.mPoseDrawSettings, mDebugRenderer, offset);
	mDebugRenderer.drawText3D(star(offset , pose2_world.getJointMatrix(1).getTranslation()), "Mapped", Color.sWhite, 0.2f);
} // JPH_DEBUG_RENDERER
}
/*TODO

void SkeletonMapperTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI->CreateCheckBox(inSubMenu, "Lock Translations", sLockTranslations, [this](UICheckBox::EState inState) { sLockTranslations = inState == UICheckBox::STATE_CHECKED; RestartTest(); });
}
*/

public void SaveState(StateRecorder inStream)
{
	inStream.write(mTime);
}

public void RestoreState(StateRecorder inStream)
{
	mTime=inStream.readFloat(mTime);
}
}