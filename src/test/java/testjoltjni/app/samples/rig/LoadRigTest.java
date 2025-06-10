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
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt Physics load-rig test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Rig/LoadRigTest.cpp
 */
public class LoadRigTest extends Test{
RagdollSettingsRef mRagdollSettings=new RagdollSettingsRef();
RagdollRef mRagdoll=new RagdollRef();


EConstraintOverride sConstraintType = EConstraintOverride.TypeRagdoll;

public void Cleanup()
{
	mRagdoll.removeFromPhysicsSystem();
}

public void Initialize()
{
	// Floor
	CreateFloor();

	// Load ragdoll
	mRagdollSettings = RagdollLoader.sLoad("Assets/Human.tof", EMotionType.Dynamic, sConstraintType).toRef();

	// Create ragdoll
	mRagdoll = mRagdollSettings.createRagdoll(0, 0, mPhysicsSystem).toRef();
	mRagdoll.addToPhysicsSystem(EActivation.Activate);
}
/*TODO

void LoadRigTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI->CreateTextButton(inSubMenu, "Constraint Type", [this, inUI]() {
		UIElement *constraint_type = inUI->CreateMenu();
		for (uint i = 0; i < size(sTypes); ++i)
			inUI->CreateTextButton(constraint_type, sTypes[i].mName, [this, i]() { sConstraintType = sTypes[i].mType; RestartTest(); });
		inUI->ShowMenu(constraint_type);
	});
}*/
}
