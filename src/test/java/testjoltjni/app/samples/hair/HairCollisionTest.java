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
import com.github.stephengold.joltjni.enumerate.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
/**
 * A line-for-line Java translation of the Jolt-Physics hair-collision test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Hair/HairCollisionTest.cpp
 */
public class HairCollisionTest extends Test{
boolean sRotating;
Hair mHair;
HairSettingsRef mHairSettings=new HairSettingsRef();
HairShaders mHairShaders=new HairShaders();
int mFrame, mMovingBodyID;

public void Initialize()
{
	// Load shaders
	mHairShaders.init(mComputeSystem);

	// Create a single strand
	mHairSettings = new HairSettings().toRef();
	HairMaterial m=new HairMaterial();
	m.setHairRadius (new Gradient(0, 0)); // Override radius to 0 so we can see it touch the moving body
	mHairSettings.addMaterial(m);
	mHairSettings.setSimulationBoundsPadding ( Vec3.sReplicate(1.0f));
	SVertexList hair_vertices = new SVertexList(new SVertex(new Float3(0, 2, 0), 0),new SVertex(new Float3(0, 0, 0), 1) );
	SStrandList hair_strands = new SStrandList(new SStrand(0, 2, 0) );
	mHairSettings.initRenderAndSimulationStrands(hair_vertices, hair_strands);
	float[] max_dist_sq = {0.0f};
	mHairSettings.init(max_dist_sq);
	mHairSettings.initCompute(mComputeSystem);

	mHair = new Hair(mHairSettings, RVec3.sZero(), Quat.sRotation(Vec3.sAxisY(), 0.5f * JPH_PI), Layers.MOVING); // Ensure hair is rotated
	mHair.init(mComputeSystem);
	mHair.update(0.0f, Mat44.sIdentity(), null, mPhysicsSystem, mHairShaders, mComputeSystem, mComputeQueue);
	mHair.readBackGpuState(mComputeQueue);

	// Create moving body that moves through the strand
	ConvexHullShapeSettings shape1=new ConvexHullShapeSettings();
	shape1.setEmbedded();
	final float cWidth = 0.01f, cHeight = 0.5f, cLength1 = 0.6f;
	shape1.setPoints  (
		new Vec3( cWidth,  cHeight,  cLength1),
		new Vec3(-cWidth,  cHeight,  cLength1),
		new Vec3( cWidth, -cHeight,  cLength1),
		new Vec3(-cWidth, -cHeight,  cLength1),
		new Vec3( cWidth,  cHeight, -cLength1),
		new Vec3(-cWidth,  cHeight, -cLength1),
		new Vec3( cWidth, -cHeight, -cLength1),
		new Vec3(-cWidth, -cHeight, -cLength1)
	);
	ConvexHullShapeSettings shape2=new ConvexHullShapeSettings();
	shape2.setEmbedded();
	final float cLength2 = 0.5f;
	shape2.setPoints  (
		new Vec3( cWidth,  cHeight,  cLength2),
		new Vec3(-cWidth,  cHeight,  cLength2),
		new Vec3( cWidth, -cHeight,  cLength2),
		new Vec3(-cWidth, -cHeight,  cLength2),
		new Vec3( cWidth,  cHeight, -cLength2),
		new Vec3(-cWidth,  cHeight, -cLength2),
		new Vec3( cWidth, -cHeight, -cLength2),
		new Vec3(-cWidth, -cHeight, -cLength2)
	);
	StaticCompoundShapeSettings compound=new StaticCompoundShapeSettings(); // Use a compound to test center of mass differences between body and shape
	compound.setEmbedded();
	compound.addShape(new Vec3(0, 0, -cLength2), Quat.sIdentity(), shape1);
	compound.addShape(new Vec3(0, 0, cLength1), Quat.sIdentity(), shape2);
	BodyCreationSettings moving_body=new BodyCreationSettings(compound,new RVec3(-1, 0, 0), Quat.sIdentity(), EMotionType.Kinematic, Layers.MOVING);
	mMovingBodyID = mBodyInterface.createAndAddBody(moving_body, EActivation.Activate);
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
if(Jolt.implementsDebugRendering()){
	DrawSettings settings=new DrawSettings();
	settings.setDrawRods ( true);
	settings.setDrawOrientations ( true);
	mHair.draw(settings, mDebugRenderer);
} // JPH_DEBUG_RENDERER

	// Set moving body velocity
	++mFrame;
	if (sRotating)
		mBodyInterface.setLinearAndAngularVelocity(mMovingBodyID, Vec3.sZero(),new Vec3(0, 1, 0));
	else
		mBodyInterface.setLinearAndAngularVelocity(mMovingBodyID, mFrame % 240 < 120?new Vec3(1, 0, 0) :new Vec3(-1, 0, 0), Vec3.sZero());

	// Update the hair
	mHair.update(inParams.mDeltaTime, Mat44.sIdentity(), null, mPhysicsSystem, mHairShaders, mComputeSystem, mComputeQueue);
	mComputeQueue.executeAndWait();
	mHair.readBackGpuState(mComputeQueue);
}

public void SaveState(StateRecorder inStream)
{
	inStream.write(mFrame);
}

public void RestoreState(StateRecorder inStream)
{
	mFrame=inStream.readInt(mFrame);
}

/*
void HairCollisionTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI->CreateCheckBox(inSubMenu, "Rotating", sRotating, [](UICheckBox::EState inState) { sRotating = inState == UICheckBox::STATE_CHECKED; });
}
*/}
