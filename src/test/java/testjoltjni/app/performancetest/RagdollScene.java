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
package testjoltjni.app.performancetest;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.operator.Op;
import java.util.*;
import static com.github.stephengold.joltjni.Jolt.*;

/**
 * A line-for-line Java translation of the Jolt Physics "ragdoll scene"
 * performance test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/PerformanceTest/RagdollScene.h
 */

// A scene that loads a part of a Horizon Zero Dawn level and drops many ragdolls on the terrain (motors enabled)
class RagdollScene implements PerformanceTestScene
{

    RagdollScene(int inNumPilesPerAxis, int inPileSize, float inVerticalSeparation) 
                                                        {
        mNumPilesPerAxis=inNumPilesPerAxis;mPileSize=inPileSize;mVerticalSeparation=inVerticalSeparation;
    }

	public String   GetName()  
	{
		return mNumPilesPerAxis == 1? "RagdollSinglePile" : "Ragdoll";
	}

	public boolean			Load() 
	{
		// Load ragdoll
		if (!ObjectStreamIn.sReadObject("Assets/Human.tof", mRagdollSettings))
		{
			cerr .print ("Unable to load ragdoll" + endl);
			return false;
		}
		for (BodyCreationSettings body : mRagdollSettings.getParts())
			body.setObjectLayer(Layers.MOVING);

		// Init ragdoll
		mRagdollSettings.getSkeleton().calculateParentJointIndices();
		mRagdollSettings.stabilize();
		mRagdollSettings.calculateBodyIndexToConstraintIndex();
		mRagdollSettings.calculateConstraintIndexToBodyIdxPair();

		// Load animation
		if (!ObjectStreamIn.sReadObject("Assets/Human/dead_pose1.tof", mAnimation))
		{
			cerr .print ("Unable to load animation" + endl);
			return false;
		}

		// Sample pose
		mPose.setSkeleton(mRagdollSettings.getSkeleton());
		mAnimation.sample(0.0f, mPose);

		// Read the background scene
		if (!ObjectStreamIn.sReadObject("Assets/terrain2.bof", mBackground))
		{
			cerr .print ("Unable to load terrain" + endl);
			return false;
		}
		for (BodyCreationSettings body : mBackground.getBodies())
			body.setObjectLayer ( Layers.NON_MOVING);
		mBackground.fixInvalidScales();

		return true;
	}

	public void			StartTest(PhysicsSystem inPhysicsSystem, EMotionQuality inMotionQuality)
	{
		// Test configuration
		final double cHorizontalSeparation = 4.0;

		// Set motion quality on ragdoll
		for (BodyCreationSettings body : mRagdollSettings.getParts())
			body.setMotionQuality(inMotionQuality);

		// Add background geometry
		mBackground.createBodies(inPhysicsSystem);

		// Create ragdoll piles
		int group_id = 1;
		for (int row = 0; row < mNumPilesPerAxis; ++row)
			for (int col = 0; col < mNumPilesPerAxis; ++col)
			{
				// Determine start location of ray
				RVec3 start=new RVec3(cHorizontalSeparation * (col - (mNumPilesPerAxis - 1) / 2.0), 100, cHorizontalSeparation * (row - (mNumPilesPerAxis - 1) / 2.0));

				// Cast ray down to terrain
				RayCastResult hit=new RayCastResult();
				Vec3 ray_direction=new Vec3(0, -200, 0);
				RRayCast ray =new RRayCast(start, ray_direction );
				if (inPhysicsSystem.getNarrowPhaseQuery().castRay(ray, hit, new SpecifiedBroadPhaseLayerFilter(BroadPhaseLayers.NON_MOVING), new SpecifiedObjectLayerFilter(Layers.NON_MOVING)))
					start = ray.getPointOnRay(hit.getFraction());

				for (int i = 0; i < mPileSize; ++i)
				{
					// Create ragdoll
					RagdollRef ragdoll = mRagdollSettings.createRagdoll(group_id++, 0, inPhysicsSystem).toRef();

					// Override root
					SkeletonPose pose_copy =new SkeletonPose(mPose);
					pose_copy.setRootOffset(start);
					JointState root = pose_copy.getJoint(0);
					root.setTranslation (new Vec3(0, mVerticalSeparation * (i + 1), 0));
					float angle = 2.0f * JPH_PI * (float)(i) / (float)(mPileSize);
					root.setRotation ( Op.multiply(Quat.sRotation(Vec3.sAxisY(), angle) , root.getRotation()));
					pose_copy.calculateJointMatrices();

					// Drive to pose
					ragdoll.setPose(pose_copy);
					ragdoll.driveToPoseUsingMotors(pose_copy);
					ragdoll.addToPhysicsSystem(EActivation.Activate);

					// Keep reference
					mRagdolls.add(ragdoll);
				}
			}
	}

	public void			StopTest(PhysicsSystem inPhysicsSystem)
	{
		// Remove ragdolls
		for (RagdollRef ragdoll : mRagdolls)
			ragdoll.removeFromPhysicsSystem();
		mRagdolls.clear();
	}

	int						mNumPilesPerAxis;
	int						mPileSize;
	float					mVerticalSeparation;
	RagdollSettingsRef	mRagdollSettings=new RagdollSettingsRef();
	SkeletalAnimationRef	mAnimation=new SkeletalAnimationRef();
	SkeletonPose			mPose=new SkeletonPose();
	PhysicsSceneRef		mBackground=new PhysicsSceneRef();
	List<RagdollRef>		mRagdolls=new ArrayList<>();
};


