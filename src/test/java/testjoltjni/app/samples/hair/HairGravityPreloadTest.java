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
package testjoltjni.app.samples.hair;
import com.github.stephengold.joltjni.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.std.Std.*;
/**
 * A line-for-line Java translation of the Jolt-Physics hair gravity-preload test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Hair/HairGravityPreloadTest.cpp
 */
public class HairGravityPreloadTest extends Test{
Hair mHair;
HairSettingsRef mHairSettings=new HairSettingsRef();
HairShaders mHairShaders=new HairShaders();

String sScenes[] =
{
	"Zig Zag",
	"Helix",
	"Horizontal Bar",
};

String sSceneName = "Zig Zag";

public void Initialize()
{
	// Load shaders
	mHairShaders.init(mComputeSystem);

	SVertexList hair_vertices=new SVertexList();
	SStrandList hair_strands=new SStrandList();

	if (strcmp(sSceneName, "Zig Zag") == 0)
	{
		// Create a hanging zig zag
		final float cHoriz = 0.05f;
		final int cNumVertices = 128;
		final float cHeight = 0.5f;
		for (int j = 0; j < 2; ++j)
			for (int i = 0; i < cNumVertices; ++i)
			{
				float fraction = ((float)(i)) / (cNumVertices - 1);

				SVertex v=new SVertex();
				v.setPosition (new Float3((j == 0? -0.1f : 0.1f) + ((i & 1)!=0? cHoriz : -cHoriz), (1.0f - fraction) * cHeight, 0));
				v.setInvMass ( i == 0? 0.0f : 1.0f);
				hair_vertices.pushBack(v);
			}
		hair_strands =new SStrandList( new SStrand(0, cNumVertices, 0), new SStrand(cNumVertices, 2 * cNumVertices, 1) );
	}
	else if (strcmp(sSceneName, "Helix") == 0)
	{
		// Create a hanging helix
		final float cRadius = 0.05f;
		final int cNumVertices = 128;
		final float cHeight = 0.5f;
		final float cNumCycles = 10;
		for (int j = 0; j < 2; ++j)
			for (int i = 0; i < cNumVertices; ++i)
			{
				float fraction = ((float)(i)) / (cNumVertices - 1);

				SVertex v=new SVertex();
				float alpha = cNumCycles * 2.0f * JPH_PI * fraction;
				v.setPosition (new Float3((j == 0? -0.1f : 0.1f) + cRadius * sin(alpha), (1.0f - fraction) * cHeight, cRadius * cos(alpha)));
				v.setInvMass ( i == 0? 0.0f : 1.0f);
				hair_vertices.pushBack(v);
			}
		hair_strands =new SStrandList(new SStrand(0, cNumVertices, 0),new SStrand(cNumVertices, 2 * cNumVertices, 1) );
	}
	else if (strcmp(sSceneName, "Horizontal Bar") == 0)
	{
		// Create horizontal bar
		final int cNumVertices = 10;
		for (int j = 0; j < 2; ++j)
			for (int i = 0; i < cNumVertices; ++i)
			{
				SVertex v=new SVertex();
				v.setPosition (new Float3(j == 0? -0.1f : 0.1f, 0, 1.0f * (float)(i)));
				v.setInvMass ( i == 0? 0.0f : 1.0f);
				hair_vertices.pushBack(v);
			}

		hair_strands =new SStrandList(new SStrand(0, cNumVertices, 0), new SStrand(cNumVertices, 2 * cNumVertices, 0) );
	}

	mHairSettings = new HairSettings().toRef();
	HairMaterial m=new HairMaterial();
	m.setGlobalPose ( new Gradient(0, 0));
	m.setEnableLra ( false); // We're testing gravity preloading, so disable LRA to avoid hitting the stretch limits
	m.setBendCompliance ( 1e-8f);
	m.setStretchCompliance ( 1e-10f);
	m.setBendComplianceMultiplier (new Float4(1, 100, 100, 1 )); // Non uniform
	m.setGridVelocityFactor (new Gradient( 0.0f, 0.0f )); // Don't let the grid affect the simulation
	m.setGravityPreloadFactor ( 0.0f);
	m.setGravityFactor (new Gradient( 1.0f, 0.5f, 0.2f, 0.8f )); // Non uniform
	m.setSimulationStrandsFraction ( 1.0f);
	mHairSettings.addMaterial(m);
	m.setGravityPreloadFactor ( 1.0f);
	mHairSettings.addMaterial(m);
	mHairSettings.setSimulationBoundsPadding ( Vec3.sReplicate(1.0f));
	mHairSettings.initRenderAndSimulationStrands(hair_vertices, hair_strands);
	float[] max_dist_sq = {0.0f};
	mHairSettings.init(max_dist_sq);
	mHairSettings.initCompute(mComputeSystem);
	mHair = new Hair(mHairSettings, RVec3.sZero(), Quat.sIdentity(), Layers.MOVING); // Ensure hair is rotated
	mHair.init(mComputeSystem);
	mHair.update(0.0f, Mat44.sIdentity(), null, mPhysicsSystem, mHairShaders, mComputeSystem, mComputeQueue);
	mHair.readBackGpuState(mComputeQueue);
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
if(Jolt.implementsDebugRendering()){
	DrawSettings settings=new DrawSettings();
	settings.setDrawRods ( true);
	settings.setDrawUnloadedRods ( true);
	mHair.draw(settings, mDebugRenderer);
} // JPH_DEBUG_RENDERER

	// Update the hair
	mHair.update(inParams.mDeltaTime, Mat44.sIdentity(), null, mPhysicsSystem, mHairShaders, mComputeSystem, mComputeQueue);
	mComputeQueue.executeAndWait();
	mHair.readBackGpuState(mComputeQueue);
}

/*TODO
void HairGravityPreloadTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI->CreateTextButton(inSubMenu, "Select Scene", [this, inUI]() {
		UIElement *scene_name = inUI->CreateMenu();
		for (uint i = 0; i < size(sScenes); ++i)
			inUI->CreateTextButton(scene_name, sScenes[i], [this, i]() { sSceneName = sScenes[i]; RestartTest(); });
		inUI->ShowMenu(scene_name);
	});
}
*/}
