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
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt Physics activate-during-update test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/ActivateDuringUpdateTest.cpp
 */
public class ActivateDuringUpdateTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	BodyCreationSettings settings=new BodyCreationSettings();
	settings.setShape(new BoxShape(Vec3.sReplicate(0.5f)));
	settings.setMotionType ( EMotionType.Dynamic);
	settings.setObjectLayer ( Layers.MOVING);

	final int cNumBodies = 3;
	final float cPenetrationSlop = mPhysicsSystem.getPhysicsSettings().getPenetrationSlop();

	for (int i = 0; i < cNumBodies; ++i)
	{
		settings.setPosition (new RVec3(i * (1.0f - cPenetrationSlop), 2.0f, 0));
		BodyId body_id = mBodyInterface.createBody(settings).getId();
		mBodyInterface.addBody(body_id, EActivation.DontActivate);
		if (i == 0)
			mBodyInterface.setLinearVelocity(body_id,new Vec3(500, 0, 0));
	}

	for (int i = 0; i < cNumBodies; ++i)
	{
		settings.setPosition (new RVec3(i * (1.0f - cPenetrationSlop), 2.0f, 2.0f));
		BodyId body_id = mBodyInterface.createBody(settings).getId();
		mBodyInterface.addBody(body_id, EActivation.DontActivate);
		if (i == cNumBodies - 1)
			mBodyInterface.setLinearVelocity(body_id,new Vec3(-500, 0, 0));
	}
}
}
