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
package testjoltjni.app.samples.general;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import java.util.*;
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt Physics change shape test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/ChangeShapeTest.cpp
 */
public class ChangeShapeTest extends Test{
BodyId mBodyID;
boolean mActivateAfterSwitch = true;
List<ShapeRefC> mShapes=new ArrayList<>();
float mTime = 0.0f;
int mShapeIdx = 0;

public void Initialize()
{
	// Floor
	CreateFloor();

	// Shapes
	mShapes.add(new BoxShape(new Vec3(0.5f, 1.5f, 0.5f)).toRefC());
	mShapes.add(new SphereShape(0.5f).toRefC());
	mShapes.add(new CapsuleShape(1.0f, 0.5f).toRefC());
	mShapes.add(new TaperedCapsuleShapeSettings(1.0f, 0.5f, 0.3f).create().get());

	// Compound with center of mass shifted (this requires a correction of the position in the body)
	StaticCompoundShapeSettings compound_settings=new StaticCompoundShapeSettings();
	compound_settings.addShape(new Vec3(0, 1.5f, 0), Quat.sIdentity(), new CapsuleShape(1.5f, 0.5f));
	compound_settings.addShape(new Vec3(0, 3, 0), Quat.sIdentity(), new SphereShape(1));
	ShapeRefC compound = compound_settings.create().get();
	mShapes.add(compound);

	// Create dynamic body that changes shape
	BodyCreationSettings settings=new BodyCreationSettings();
	settings.setShape(mShapes.get(mShapeIdx));
	settings.setPosition (new RVec3(0, 10, 0));
	settings.setMotionType ( EMotionType.Dynamic);
	settings.setObjectLayer ( Layers.MOVING);
	mBodyID = mBodyInterface.createBody(settings).getId();
	mBodyInterface.addBody(mBodyID, EActivation.Activate);
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	final float cSwitchTime = 3.0f;

	// Increment time
	mTime += inParams.mDeltaTime;

	// Get new shape
	int shape_idx = (int)(mTime / cSwitchTime) % mShapes.size();

	// Change shape
	if (mShapeIdx != shape_idx)
	{
		mShapeIdx = shape_idx;
		mBodyInterface.setShape(mBodyID, mShapes.get(mShapeIdx), true, mActivateAfterSwitch? EActivation.Activate : EActivation.DontActivate);
	}
}

protected void SaveState(StateRecorder inStream)
{
	inStream.write(mTime);
	inStream.write(mShapeIdx);
}

protected void RestoreState(StateRecorder inStream)
{
	mTime=inStream.readFloat(mTime);
	mShapeIdx=inStream.readInt(mShapeIdx);

	// Reset the shape to what was stored
	mBodyInterface.setShape(mBodyID, mShapes.get(mShapeIdx), true, EActivation.DontActivate);
}
/*TODO

void ChangeShapeTest.CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI->CreateCheckBox(inSubMenu, "Activate Body After Switch", mActivateAfterSwitch, [this](UICheckBox.EState inState) { mActivateAfterSwitch = inState == UICheckBox.STATE_CHECKED; });
}
*/}
