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
import com.github.stephengold.joltjni.enumerate.*;
import java.util.function.BiFunction;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import static com.github.stephengold.joltjni.std.Std.*;
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt-Physics soft-body
 * skinned-constraint test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/SoftBody/SoftBodySkinnedConstraintTest.cpp
 */
public class SoftBodySkinnedConstraintTest extends Test{
Body mBody;
boolean sEnableSkinConstraints=true,sUpdateSkinning=true;
float cBodyPosY=20,cVertexSpacing=0.5f,mTime,sMaxDistanceMultiplier=1,sTimeScale=1;
int cNumJoints=11,cNumVerticesX=10,cNumVerticesZ=50;

Mat44[] GetWorldSpacePose(float inTime)
{
	Mat44[] pose=
	new Mat44[cNumJoints];

	// Create local space pose
	pose[0] = Mat44.sTranslation(new Vec3(0.0f, cBodyPosY, -0.5f * (cNumVerticesZ - 1) * cVertexSpacing));
	for (int i = 1; i < cNumJoints; ++i)
	{
		float amplitude = 0.25f * Math.min(inTime, 2.0f); // Fade effect in over time
		Mat44 rotation = Mat44.sRotationX(amplitude * sin(0.25f * JPH_PI * i + 2.0f * inTime));
		Mat44 translation = Mat44.sTranslation(new Vec3(0, 0, (cNumVerticesZ - 1) * cVertexSpacing / (cNumJoints - 1)));
		pose[i] = star(rotation , translation);
	}

	// Convert to world space
	for (int i = 1; i < cNumJoints; ++i)
		pose[i] = star(pose[i - 1] , pose[i]);

	return pose;
}

void SkinVertices(boolean inHardSkinAll)
{
	RMat44 com = mBody.getCenterOfMassTransform();

	// Make pose relative to the center of mass of the body
	Mat44[] pose = GetWorldSpacePose(mTime);
	Mat44 offset = com.inversedRotationTranslation().toMat44();
	for (Mat44 m : pose)
		m = star(offset , m);

	SoftBodyMotionProperties mp = (SoftBodyMotionProperties) (mBody.getMotionProperties());
	mp.setEnableSkinConstraints(sEnableSkinConstraints);
	mp.setSkinnedMaxDistanceMultiplier(sMaxDistanceMultiplier);
	if (sUpdateSkinning || inHardSkinAll)
		mp.skinVertices(com, pose, cNumJoints, inHardSkinAll, mTempAllocator);
}

public void Initialize()
{
	CreateFloor();

	// Where we'll place the body
	RVec3 body_translation=new RVec3(0.0f, cBodyPosY, 0);

	// Make first and last row kinematic
	BiFunction<Integer,Integer,Float> inv_mass = (Integer inX, Integer inZ) ->{ return (inZ == 0 || inZ == cNumVerticesZ - 1)? 0.0f : 1.0f; };
	SoftBodySharedSettingsRef settings = SoftBodyCreator.CreateCloth(cNumVerticesX, cNumVerticesZ, cVertexSpacing, inv_mass);

	// Make edges soft
	for (Edge e : settings.getPtr().getEdgeConstraints())
		e.setCompliance ( 1.0e-3f);

	// Create inverse bind matrices by moving the bind pose to the center of mass space for the body
	Mat44[] bind_pose = GetWorldSpacePose(0.0f);
	Mat44 offset = Mat44.sTranslation(new Vec3(minus(body_translation)));
	for (Mat44 m : bind_pose)
		m = star(offset , m);
	for (int i = 0; i < cNumJoints; ++i)
		settings.addInvBindMatrix(new InvBind(i, bind_pose[i].inversed()));

	// Create skinned vertices
	BiFunction<Integer,Integer,Integer> get_vertex = (Integer inX, Integer inZ) ->{ return inX + inZ * cNumVerticesX; };
	for (int z = 0; z < cNumVerticesZ; ++z)
		for (int x = 0; x < cNumVerticesX; ++x)
		{
			int vertex_idx = get_vertex.apply(x, z);
			Skinned skinned=new Skinned(vertex_idx, settings.getPtr().getVertex(vertex_idx).getInvMass() > 0.0f? 2.0f : 0.0f, 0.1f, 40.0f);

			// Find closest joints
			int closest_joint = -1, prev_closest_joint = -1;
			float closest_joint_dist = FLT_MAX, prev_closest_joint_dist = FLT_MAX;
			for (int i = 0; i < cNumJoints; ++i)
			{
				float dist = Math.abs(settings.getPtr().getVertex(vertex_idx).getPosition().z - bind_pose[i].getTranslation().getZ());
				if (dist < closest_joint_dist)
				{
					prev_closest_joint = closest_joint;
					prev_closest_joint_dist = closest_joint_dist;
					closest_joint = i;
					closest_joint_dist = dist;
				}
				else if (dist < prev_closest_joint_dist)
				{
					prev_closest_joint = i;
					prev_closest_joint_dist = dist;
				}
			}
			if (closest_joint_dist == 0.0f)
			{
				// Hard skin to closest joint
				skinned.setWeight(0 ,new SkinWeight(closest_joint, 1.0f));
			}
			else
			{
				// Skin to two closest joints
				skinned.setWeight(0 ,new SkinWeight(closest_joint, 1.0f / closest_joint_dist));
				skinned.setWeight(1 ,new SkinWeight(prev_closest_joint, 1.0f / prev_closest_joint_dist));
				skinned.normalizeWeights();
			}

			settings.addSkinnedConstraint(skinned);
		}

	// Calculate the information needed for skinned constraints
	settings.calculateSkinnedConstraintNormals();

	// Optimize the settings (note that this is the second time we call this, the first time was in SoftBodyCreator.CreateCloth,
	// this is a bit wasteful but we must do it because we added more constraints)
	settings.optimize();

	// Create the body
	SoftBodyCreationSettings cloth=new SoftBodyCreationSettings(settings, body_translation, Quat.sIdentity(), Layers.MOVING);
	mBody = mBodyInterface.createSoftBody(cloth);
	mBodyInterface.addBody(mBody.getId(), EActivation.Activate);

	// Initially hard skin all vertices to the pose
	SkinVertices(true);
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	// Draw the pose pre step
	Mat44[] pose = GetWorldSpacePose(mTime);
	for (int i = 1; i < cNumJoints; ++i)
	{
		mDebugRenderer.drawArrow(new RVec3(pose[i - 1].getTranslation()),new RVec3(pose[i].getTranslation()), Color.sGreen, 0.1f);
		mDebugRenderer.drawCoordinateSystem(new RMat44(pose[i]), 0.5f);
	}

	// Update time
	mTime += sTimeScale * inParams.mDeltaTime;

	// Calculate skinned vertices but do not hard skin them
	SkinVertices(false);
}

public void SaveState(StateRecorder inStream)
{
	inStream.write(mTime);
}

public void RestoreState(StateRecorder inStream)
{
	mTime=inStream.readFloat(mTime);
}
/*TODO

void SoftBodySkinnedConstraintTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI->CreateSlider(inSubMenu, "Time Scale", sTimeScale, 0.0f, 10.0f, 0.1f, [](float inValue) { sTimeScale = inValue; });
	inUI->CreateCheckBox(inSubMenu, "Update Skinning", sUpdateSkinning, [](UICheckBox::EState inState) { sUpdateSkinning = inState == UICheckBox::STATE_CHECKED; });
	inUI->CreateCheckBox(inSubMenu, "Enable Skin Constraints", sEnableSkinConstraints, [](UICheckBox::EState inState) { sEnableSkinConstraints = inState == UICheckBox::STATE_CHECKED; });
	inUI->CreateSlider(inSubMenu, "Max Distance Multiplier", sMaxDistanceMultiplier, 0.0f, 10.0f, 0.1f, [](float inValue) { sMaxDistanceMultiplier = inValue; });
}
*/}