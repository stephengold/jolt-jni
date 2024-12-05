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
import com.github.stephengold.joltjni.std.*;
import java.util.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import static com.github.stephengold.joltjni.std.Std.*;
/**
 * A line-for-line Java translation of the Jolt Physics rig-pile test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Shapes/RigPileTest.cpp
 */
public class RigPileTest extends Test{

final String sScenes[] =
{
	"PerlinMesh",
	"PerlinHeightField",
	"Terrain1",
	"Terrain2",
};

String sSceneName=buildType().equals("Debug")?"PerlinMesh":"Terrain1";
int sPileSize=buildType().equals("Debug")?5:10;
int sNumPilesPerAxis= buildType().equals("Debug")?2:4;
List<RagdollRef>mRagdolls=new ArrayList<>();

public void Cleanup()
{
	for (RagdollRef r : mRagdolls)
		r.removeFromPhysicsSystem();
}

public void Initialize()
{
	if (strcmp(sSceneName, "PerlinMesh") == 0)
	{
		// Default terrain
		CreateMeshTerrain();
	}
	else if (strcmp(sSceneName, "PerlinHeightField") == 0)
	{
		// Default terrain
		CreateHeightFieldTerrain();
	}
	else
	{
		// Load scene
		PhysicsSceneRef scene=new PhysicsSceneRef();
		if (!ObjectStreamIn.sReadObject((String)("Assets/") + sSceneName + ".bof", scene))
			throw new RuntimeException("Failed to load scene");
		for (BodyCreationSettings body : scene.getBodies())
			body.setObjectLayer ( Layers.NON_MOVING);
		scene.fixInvalidScales();
		scene.createBodies(mPhysicsSystem);
	}

	// Load ragdoll
	RagdollSettingsRef settings = RagdollLoader.sLoad("Assets/Human.tof", EMotionType.Dynamic).toRef();

	// Load animation
	final int cAnimationCount = 4;
	SkeletalAnimationRef animation[]={new SkeletalAnimationRef(),new SkeletalAnimationRef(),new SkeletalAnimationRef(),new SkeletalAnimationRef()};
	for (int i = 0; i < cAnimationCount; ++i)
	{
		if (!ObjectStreamIn.sReadObject(String.format("Assets/Human/dead_pose%d.tof", i + 1), animation[i]))
			FatalError("Could not open animation");
	}

	final float cHorizontalSeparation = 4.0f;
	final float cVerticalSeparation = 0.6f;

	// Limit the size of the piles so we don't go over 160 ragdolls
	int pile_size = Math.min(sPileSize, 160 / square(sNumPilesPerAxis));

	// Create piles
	DefaultRandomEngine random=new DefaultRandomEngine();
	UniformFloatDistribution angle=new UniformFloatDistribution(0.0f, JPH_PI);
	int group_id = 1;
	for (int row = 0; row < sNumPilesPerAxis; ++row)
		for (int col = 0; col < sNumPilesPerAxis; ++col)
		{
			// Determine start location of ray
			RVec3 start =new RVec3(cHorizontalSeparation * (col - (sNumPilesPerAxis - 1) / 2.0f), 100, cHorizontalSeparation * (row - (sNumPilesPerAxis - 1) / 2.0f));

			// Cast ray down to terrain
			RayCastResult hit=new RayCastResult();
			Vec3 ray_direction=new Vec3(0, -200, 0);
			RRayCast ray =new RRayCast( start, ray_direction );
			if (mPhysicsSystem.getNarrowPhaseQuery().castRay(ray, hit,new SpecifiedBroadPhaseLayerFilter(BroadPhaseLayers.NON_MOVING),new SpecifiedObjectLayerFilter(Layers.NON_MOVING)))
				start = ray.getPointOnRay(hit.getFraction());

			for (int i = 0; i < pile_size; ++i)
			{
				// Create ragdoll
				RagdollRef ragdoll = settings.createRagdoll(group_id++, 0, mPhysicsSystem).toRef();

				// Sample pose
				SkeletonPose pose=new SkeletonPose();
				pose.setSkeleton(settings.getSkeleton());
				animation[random.nextInt() % cAnimationCount].sample(0.0f, pose);

				// Override root
				pose.setRootOffset(start);
				JointState root = pose.getJoint(0);
				root.setTranslation (new Vec3(0, cVerticalSeparation * (i + 1), 0));
				root.setRotation ( star(Quat.sRotation(Vec3.sAxisY(), angle.nextFloat(random)) , root.getRotation()));
				pose.calculateJointMatrices();

				// Drive to pose
				ragdoll.setPose(pose);
				ragdoll.driveToPoseUsingMotors(pose);
				ragdoll.addToPhysicsSystem(EActivation.Activate);

				mRagdolls.add(ragdoll);
			}
		}
}
/*TODO

void RigPileTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI->CreateTextButton(inSubMenu, "Select Scene", [this, inUI]() {
		UIElement *scene_name = inUI->CreateMenu();
		for (uint i = 0; i < size(sScenes); ++i)
			inUI->CreateTextButton(scene_name, sScenes[i], [this, i]() { sSceneName = sScenes[i]; RestartTest(); });
		inUI->ShowMenu(scene_name);
	});

	inUI->CreateSlider(inSubMenu, "Num Ragdolls Per Pile", float(sPileSize), 1, 160, 1, [](float inValue) { sPileSize = (int)inValue; });
	inUI->CreateSlider(inSubMenu, "Num Piles Per Axis", float(sNumPilesPerAxis), 1, 4, 1, [](float inValue) { sNumPilesPerAxis = (int)inValue; });
}*/
}