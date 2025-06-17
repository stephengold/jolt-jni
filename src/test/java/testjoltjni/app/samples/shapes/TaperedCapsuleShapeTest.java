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
package testjoltjni.app.samples.shapes;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
/**
 * A line-for-line Java translation of the Jolt-Physics tapered-capsule shape
 * test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Shapes/TaperedCapsuleShapeTest.cpp
 */
public class TaperedCapsuleShapeTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	ShapeSettingsRef big_taperedcapsule = new TaperedCapsuleShapeSettings(2.0f, 1.0f, 3.0f).toRef();
	ShapeSettingsRef big_taperedcapsule2 = new TaperedCapsuleShapeSettings(2.0f, 3.0f, 1.0f).toRef();

	// Tapered capsule on outer sphere
	mBodyInterface.createAndAddBody(new BodyCreationSettings(big_taperedcapsule,new RVec3(0, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Tapered capsule on other outer sphere
	mBodyInterface.createAndAddBody(new BodyCreationSettings(big_taperedcapsule2,new RVec3(10, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Tapered capsule on side
	mBodyInterface.createAndAddBody(new BodyCreationSettings(big_taperedcapsule,new RVec3(20, 10, 0), Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	ShapeSettingsRef long_taperedcapsule = new TaperedCapsuleShapeSettings(5, 0.5f, 1.0f).toRef();

	// Tower of tapered capsules
	for (int i = 0; i < 10; ++i)
	{
		for (int j = 0; j < 2; ++j)
		{
			RVec3 position;
			Quat rotation;
			if ((i & 1)!=0)
			{
				position =new RVec3(-4.0f + 8.0f * j, 2.0f + 3.0f * i, -20.0f);
				rotation = Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI + (j & 1) * JPH_PI);
			}
			else
			{
				position =new RVec3(0, 2.0f + 3.0f * i, -20.0f - 4.0f + 8.0f * j);
				rotation = Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI + (j & 1) * JPH_PI);
			}
			mBodyInterface.createAndAddBody(new BodyCreationSettings(long_taperedcapsule, position, rotation, EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
		}
	}
}
}