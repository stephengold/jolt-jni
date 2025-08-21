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
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import static com.github.stephengold.joltjni.std.Std.*;
/**
 * A line-for-line Java translation of the Jolt-Physics soft-body stress test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/SoftBody/SoftBodyStressTest.cpp
 */
public class SoftBodyStressTest extends Test{

String  sScenes[] =
{
	"SpheresVsBoxes",
	"LargeCloth"
};

String  sSceneName = "SpheresVsBoxes";

public void Initialize()
{
	if (strcmp(sSceneName, "SpheresVsBoxes") == 0)
	{
		// Floor
		CreateMeshTerrain();

		// Pressurized sphere settings
		SoftBodyCreationSettings sphere=new SoftBodyCreationSettings(SoftBodyCreator.CreateSphere(), RVec3.sZero(), Quat.sIdentity(), Layers.MOVING);
		sphere.setPressure ( 2000.0f);

		// Box settings
		BodyCreationSettings box=new BodyCreationSettings(new BoxShape(Vec3.sOne()), RVec3.sZero(), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
		box.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
		box.getMassPropertiesOverride().setMass ( 100.0f);

		for (int x = 0; x <= 10; ++x)
			for (int z = 0; z <= 10; ++z)
			{
				sphere.setPosition (new RVec3(-20.0 + 4.0 * x, 5.0, -20.0 + 4.0 * z));
				mBodyInterface.createAndAddSoftBody(sphere, EActivation.Activate);

				box.setPosition ( plus(sphere.getPosition() ,new RVec3(0, 4, 0)));
				mBodyInterface.createAndAddBody(box, EActivation.Activate);
			}
	}
	else if (strcmp(sSceneName, "LargeCloth") == 0)
	{
		// Floor
		CreateFloor();

		// Create cloth that's fixated at the corners
		SoftBodyCreationSettings cloth=new SoftBodyCreationSettings(SoftBodyCreator.CreateClothWithFixatedCorners(100, 100, 0.25f),new RVec3(0, 15.0f, 0), Quat.sIdentity(), Layers.MOVING);
		cloth.setUpdatePosition ( false); // Don't update the position of the cloth as it is fixed to the world
		mBodyInterface.createAndAddSoftBody(cloth, EActivation.Activate);

		// Box settings
		BodyCreationSettings box=new BodyCreationSettings(new BoxShape(Vec3.sReplicate(0.5f)), RVec3.sZero(), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
		box.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
		box.getMassPropertiesOverride().setMass ( 10.0f);

		// Create a number of boxes that fall on the cloth
		for (int x = 0; x <= 10; ++x)
			for (int z = 0; z <= 10; ++z)
			{
				box.setPosition (plus( cloth.getPosition() ,new RVec3(-10.0 + 2.0 * x, 2.0, -10.0 + 2.0 * z)));
				mBodyInterface.createAndAddBody(box, EActivation.Activate);
			}
	}
}
/*TODO

void SoftBodyStressTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI->CreateTextButton(inSubMenu, "Select Scene", [this, inUI]() {
		UIElement *scene_name = inUI->CreateMenu();
		for (uint i = 0; i < size(sScenes); ++i)
			inUI->CreateTextButton(scene_name, sScenes[i], [this, i]() { sSceneName = sScenes[i]; RestartTest(); });
		inUI->ShowMenu(scene_name);
	});
}*/
}