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
import static com.github.stephengold.joltjni.Jolt.*;
/**
 * A line-for-line Java translation of the Jolt-Physics friction test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/FrictionTest.cpp
 */
public class FrictionTest extends Test{

public void Initialize()
{
	// Floor
	Body floor = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(new Vec3(100.0f, 1.0f, 100.0f), 0.0f), RVec3.sZero(), Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI), EMotionType.Static, Layers.NON_MOVING));
	floor.setFriction(1.0f);
	mBodyInterface.addBody(floor.getId(), EActivation.DontActivate);

	ShapeRefC box = new BoxShape(new Vec3(2.0f, 2.0f, 2.0f)).toRefC();
	ShapeRefC sphere = new SphereShape(2.0f).toRefC();

	// Bodies with increasing friction
	for (int i = 0; i <= 10; ++i)
	{
		Body body = mBodyInterface.createBody(new BodyCreationSettings(box,new RVec3(-50.0f + i * 10.0f, 55.0f, -50.0f), Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI), EMotionType.Dynamic, Layers.MOVING));
		body.setFriction(0.1f * i);
		mBodyInterface.addBody(body.getId(), EActivation.Activate);
	}

	for (int i = 0; i <= 10; ++i)
	{
		Body body = mBodyInterface.createBody(new BodyCreationSettings(sphere,new RVec3(-50.0f + i * 10.0f, 47.0f, -40.0f), Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI), EMotionType.Dynamic, Layers.MOVING));
		body.setFriction(0.1f * i);
		mBodyInterface.addBody(body.getId(), EActivation.Activate);
	}
}
}
