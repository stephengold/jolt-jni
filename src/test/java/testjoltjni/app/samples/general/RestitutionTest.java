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
package testjoltjni.app.samples.general;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import testjoltjni.app.samples.*;

/**
 * A line-for-line Java translation of the Jolt-Physics restitution test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/RestitutionTest.cpp
 */
public class RestitutionTest extends Test{
public void Initialize()
{
	// Floor
	CreateFloor();

	ShapeRefC sphere = new SphereShape(2.0f).toRefC();
	ShapeRefC box = new BoxShape(new Vec3(2.0f, 2.0f, 2.0f)).toRefC();

	// Bodies with increasing restitution
	for (int i = 0; i <= 10; ++i)
	{
		BodyCreationSettings settings=new BodyCreationSettings(sphere, new RVec3(-50.0f + i * 10.0f, 20.0f, -20.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
		settings.setRestitution ( 0.1f * i);
		settings.setLinearDamping ( 0.0f);
		mBodyInterface.createAndAddBody(settings, EActivation.Activate);
	}

	for (int i = 0; i <= 10; ++i)
	{
		BodyCreationSettings settings=new BodyCreationSettings(box, new RVec3(-50.0f + i * 10.0f, 20.0f, 20.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
		settings.setRestitution ( 0.1f * i);
		settings.setLinearDamping ( 0.0f);
		mBodyInterface.createAndAddBody(settings, EActivation.Activate);
	}
}
}
