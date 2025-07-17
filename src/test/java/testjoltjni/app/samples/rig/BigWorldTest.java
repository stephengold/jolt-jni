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
import com.github.stephengold.joltjni.readonly.*;
import com.github.stephengold.joltjni.std.*;
import java.util.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt-Physics big-world test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Rig/BigWorldTest.cpp
 */
public class BigWorldTest extends Test{
class Pile{String GetLabel(){return String.format("%.0f km", 1.0e-3 * mOrigin.length());}
RVec3 mOrigin;List<RagdollRef>mRagdolls;}
boolean sDrawWireframe=true;
int sDrawPileMask=~0;
RVec3 sPivot=RVec3.sZero();
List<Pile>mPiles=new ArrayList<>();

public void Cleanup()
{
	for (Pile pile : mPiles)
		for (RagdollRef r : pile.mRagdolls)
			r.removeFromPhysicsSystem();
}

void Initialize()
{
	final int cPileSize = 5;

	// Default terrain
	Body floor = CreateMeshTerrain();
	ShapeRefC shape = floor.getShape().toRefC();

	// Load ragdoll
	RagdollSettings settings = RagdollLoader.sLoad("Assets/Human.tof", EMotionType.Dynamic);

	// Load animation
	SkeletalAnimationRef animation=new SkeletalAnimationRef();
	if (!ObjectStreamIn.sReadObject("Assets/Human/dead_pose1.tof", animation))
		FatalError("Could not open animation");
	SkeletonPose pose=new SkeletonPose();
	pose.setSkeleton(settings.getSkeleton());
	animation.sample(0.0f, pose);

	// Determine rotation for each ragdoll in the pile
	DefaultRandomEngine random=new DefaultRandomEngine();
	UniformFloatDistribution angle=new UniformFloatDistribution(0.0f, JPH_PI);
	List<Quat> rotation=new ArrayList<>(cPileSize);
	for (int i = 0; i < cPileSize; ++i)
		rotation.add(star(Quat.sRotation(Vec3.sAxisY(), angle.nextFloat(random)) , pose.getJoint(0).getRotation()));

	// Create piles at various distances
	double distances[] = { 0.0, 1.0e3, 5.0e3, 1.0e4, 5.0e4, 1.0e5, 1.0e6, 1.0e7, 1.0e8 };
	for (double distance : distances)
	{
		// Calculate origin for this simulation assuming we want to be 'distance' away and the same distance along each coordinate axis
		RVec3 origin = slash(RVec3.sReplicate(distance) , Math.sqrt(3.0));

		// Create floor (floor at 0 was already created)
		if (distance != 0.0f)
			mBodyInterface.createAndAddBody(new BodyCreationSettings(shape, origin, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

		// Create pile of ragdolls
		Pile pile=new Pile();pile.mRagdolls=new ArrayList<>();
		pile.mOrigin = origin;
		for (int i = 0; i < cPileSize; ++i)
		{
			// Create ragdoll
			RagdollRef ragdoll = settings.createRagdoll(0, 0, mPhysicsSystem).toRef();

			// Override root
			JointState root = pose.getJoint(0);
			root.setTranslation ( Vec3.sZero());
			root.setRotation ( rotation.get(i));
			pose.setRootOffset(plus(origin ,new Vec3(0, 2.0f + 0.6f * i, 0)));
			pose.calculateJointMatrices();

			// Drive to pose
			ragdoll.setPose(pose);
			ragdoll.driveToPoseUsingMotors(pose);
			ragdoll.addToPhysicsSystem(EActivation.Activate);

			pile.mRagdolls.add(ragdoll);
		}

		mPiles.add(pile);
	}
}

public void PrePhysicsUpdate(PreUpdateParams inParams)
{
	int pile_idx = 0;

	for (Pile pile : mPiles)
		if (!pile.mOrigin.isNearZero()) // Pile at 0 is drawn normally
		{
			// Check if we need to draw this pile
			if ((sDrawPileMask & (1 << pile_idx)) != 0)
			{
				Color color = Color.sGetDistinctColor(pile_idx);
				boolean first = true;

				for (RagdollRef r : pile.mRagdolls)
				{
					for (int  id : r.getBodyIds())
					{
						BodyLockRead lock=new BodyLockRead(mPhysicsSystem.getBodyLockInterface(), id);
						if (lock.succeeded())
						{
							ConstBody body = lock.getBody();

							// Shift the transform back to the origin
							RMat44 transform = body.getCenterOfMassTransform();
							transform.setTranslation(minus(transform.getTranslation() , pile.mOrigin));

							// Draw distance label for the first body
							if (first)
							{
								mDebugRenderer.drawText3D(transform.getTranslation(), pile.GetLabel(), color, 0.2f);
								first = false;
							}

						if (implementsDebugRendering()){
							// Draw the shape
							body.getShape().draw(mDebugRenderer, transform, Vec3.sReplicate(1.0f), color, false, sDrawWireframe);
						} // JPH_DEBUG_RENDERER
						}
                                                lock.releaseLock();
					}
				}
			}

			pile_idx++;
		}
}
/*TODO

void BigWorldTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	// Draw in wireframe?
	inUI->CreateCheckBox(inSubMenu, "Draw distant scenes in wireframe", sDrawWireframe, [](UICheckBox::EState inState) { sDrawWireframe = inState == UICheckBox::STATE_CHECKED; });

	// Enable / disable drawing of a particular distance
	int pile_idx = 0;
	for (Pile &pile : mPiles)
		if (!pile.mOrigin.IsNearZero())
		{
			uint32 mask = 1 << pile_idx;
			inUI->CreateCheckBox(inSubMenu, "Draw pile at " + pile.GetLabel(), (sDrawPileMask & mask) != 0, [mask](UICheckBox::EState inState) { if (inState == UICheckBox::STATE_CHECKED) sDrawPileMask |= mask; else sDrawPileMask &= ~mask; });
			pile_idx++;
		}

	// Goto pile at a particular distance
	for (Pile &pile : mPiles)
		inUI->CreateTextButton(inSubMenu, "Goto pile at " + pile.GetLabel(), [this, &pile]() { sPivot = pile.mOrigin; RestartTest(); });
}
*/

RMat44 GetCameraPivot(float inCameraHeading, float inCameraPitch)
{
	return RMat44.sTranslation(sPivot);
}

RVec3 GetDrawOffset()
{
	return sPivot;
}
}
