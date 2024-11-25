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
 * A line-for-line Java translation of the Jolt Physics heavy-on-light test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/HeavyOnLightTest.cpp
 */
public class HeavyOnLightTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	BoxShape box = new BoxShape(Vec3.sReplicate(5));
	box.setDensity(1000.0f);

	for (int i = 1; i <= 10; ++i)
	{
		mBodyInterface.createAndAddBody(new BodyCreationSettings(box,new RVec3(-75.0f + i * 15.0f, 10.0f, 0.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

		BoxShape box2 = new BoxShape(Vec3.sReplicate(5));
		box2.setDensity(5000.0f * i);

		mBodyInterface.createAndAddBody(new BodyCreationSettings(box2,new RVec3(-75.0f + i * 15.0f, 30.0f, 0.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
	}
}
}
