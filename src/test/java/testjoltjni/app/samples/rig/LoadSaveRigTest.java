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
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt-Physics load-save rig test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Rig/LoadSaveRigTest.cpp
 */
public class LoadSaveRigTest extends Test{
RagdollRef mRagdoll=new RagdollRef();

public void Cleanup()
{
	mRagdoll.removeFromPhysicsSystem();
}

public void Initialize()
{
	// Floor
	CreateFloor();

	StringStream data=new StringStream();

	{
		// Load ragdoll
		RagdollSettingsRef settings = RagdollLoader.sLoad("Assets/Human.tof", EMotionType.Dynamic).toRef();

		// Add an additional constraint between the left and right arm to test loading/saving of additional constraints
		ConstSkeleton skeleton = settings.getSkeleton();
		int left_arm = skeleton.getJointIndex("L_Wrist_sjnt_0");
		int right_arm = skeleton.getJointIndex("R_Wrist_sjnt_0");
		DistanceConstraintSettings constraint = new DistanceConstraintSettings();
		constraint.setSpace ( EConstraintSpace.LocalToBodyCom);
		constraint.setMaxDistance ( 0.1f);
		constraint.setMinDistance ( 0.1f);
		settings.addAdditionalConstraint(new AdditionalConstraint(left_arm, right_arm , constraint));

		// Write ragdoll
		if (!ObjectStreamOut.sWriteObject(data, EStreamType.Text, settings.getPtr()))
			FatalError("Failed to save ragdoll");
	}

	// Read ragdoll back in
	RagdollSettingsRef settings=new RagdollSettingsRef();
	if (!ObjectStreamIn.sReadObject(data, settings))
		FatalError("Failed to load ragdoll");

	// Parent joint indices are not stored so need to be calculated again
	settings.getSkeleton().calculateParentJointIndices();

	// Create ragdoll
	mRagdoll = settings.createRagdoll(0, 0, mPhysicsSystem).toRef();
	mRagdoll.addToPhysicsSystem(EActivation.Activate);
}
}
