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
import static com.github.stephengold.joltjni.Jolt.*;
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt-Physics soft-body vertex-radius
 * test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/SoftBody/SoftBodyVertexRadiusTest.cpp
 */
public class SoftBodyVertexRadiusTest extends Test{
SoftBodySharedSettingsRef mSharedSettings=new SoftBodySharedSettingsRef();
float sVertexRadius=0.01f;

public void Initialize()
{
	// Floor
	CreateFloor();

	// Create sphere
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new SphereShape(2.0f),new RVec3(0, 0, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Create cloth with specified vertex radius
	mSharedSettings = SoftBodyCreator.CreateCloth(30, 30, 0.5f);
	SoftBodyCreationSettings cloth=new SoftBodyCreationSettings(mSharedSettings,new RVec3(0, 5, 0), Quat.sRotation(Vec3.sAxisY(), 0.25f * JPH_PI), Layers.MOVING);
	cloth.setVertexRadius ( sVertexRadius);
        mBodyInterface.createAndAddSoftBody(cloth, EActivation.Activate);
}
/*TODO

void SoftBodyVertexRadiusTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI->CreateSlider(inSubMenu, "Vertex Radius", sVertexRadius, 0.0f, 0.5f, 0.01f, [this](float inValue) { sVertexRadius = inValue; mSharedSettings->mVertexRadius = inValue; });
}
*/}