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
package testjoltjni.app.samples.tools;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt-Physics load-snapshot test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Tools/LoadSnapshotTest.cpp
 */
public class LoadSnapshotTest extends Test{
boolean sOverrideLayers;
int sUpAxis=1;

public void Initialize()
{
/*
#ifdef JPH_PLATFORM_WINDOWS
	// Let user browse for a file
	char file_name[MAX_PATH] = "";
	OPENFILENAMEA ofn;
	memset(&ofn, 0, sizeof(ofn));
	ofn.lStructSize = sizeof(ofn);
	ofn.lpstrFilter = "Snapshots\0*.bin\0";
	ofn.lpstrFile = file_name;
	ofn.nMaxFile = MAX_PATH;
	ofn.lpstrTitle = "Select a Jolt Binary Snapshot";
	ofn.Flags = OFN_DONTADDTORECENT | OFN_FILEMUSTEXIST;
	if (!GetOpenFileNameA(&ofn))
		return;
#else
*/
	// Can't browse for a file, use the default name
	String file_name = "snapshot.bin";
        if(Jolt.isDoublePrecision())file_name="snapshot-dp.bin";
//#endif

	int flags = StreamInWrapper.in() | StreamInWrapper.binary();

	StreamInWrapper wrapper=StreamInWrapper.open(file_name, flags);
	if (wrapper==null)
		FatalError("Unable to open file");
	PhysicsSceneResult result = PhysicsScene.sRestoreFromBinaryState(wrapper);
	if (result.hasError())
		FatalError(result.getError());
	PhysicsSceneRef scene = result.get();

	// Determine quaternion that rotates the world so that up is Y
	Quat up_rotation;
	switch (sUpAxis)
	{
	case 0:		up_rotation = Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI);	break;
	case 2:		up_rotation = Quat.sRotation(Vec3.sAxisX(), -0.5f * JPH_PI);	break;
	default:	up_rotation = Quat.sIdentity();								break;
	}

	// Determine if we are forced to override the object layers because one of the bodies has a layer number that is invalid in the context of this application
	boolean override_layers = sOverrideLayers;
	if (!override_layers)
		for (BodyCreationSettings settings : scene.getBodies())
			if (settings.getObjectLayer() >= Layers.NUM_LAYERS)
			{
				override_layers = true;
				break;
			}

	for (BodyCreationSettings settings : scene.getBodies())
	{
		if (override_layers)
		{
			// Override the layer so that all static objects are in the non-moving layer and everything else is in the moving layer
			if (settings.getMotionType() == EMotionType.Static)
				settings.setObjectLayer ( Layers.NON_MOVING);
			else
				settings.setObjectLayer ( Layers.MOVING);
		}

		// Rotate the body so that it matches Y is up
		settings.setPosition ( star(RMat44.sRotation(up_rotation) , settings.getPosition()));
		settings.setRotation ( star(up_rotation , settings.getRotation()));
	}

	scene.createBodies(mPhysicsSystem);
}

/*
void LoadSnapshotTest.CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI.CreateComboBox(inSubMenu, "Up Axis", { "X", "Y", "Z" }, sUpAxis, [](int inItem) { sUpAxis = inItem; });
	inUI.CreateCheckBox(inSubMenu, "Override Object Layers", sOverrideLayers, [](UICheckBox.EState inState) { sOverrideLayers = inState == UICheckBox.STATE_CHECKED; });
	inUI.CreateTextButton(inSubMenu, "Accept Changes", [this]() { RestartTest(); });
}
*/
}