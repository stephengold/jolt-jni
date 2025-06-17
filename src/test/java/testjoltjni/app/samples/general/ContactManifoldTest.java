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
import com.github.stephengold.joltjni.operator.Op;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
/**
 * A line-for-line Java translation of the Jolt-Physics contact-manifold test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/ContactManifoldTest.cpp
 */
public class ContactManifoldTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	ShapeRefC big_box = new BoxShape(new Vec3(4, 4, 4), 0.0f).toRefC();
	ShapeRefC capsule = new CapsuleShape(5, 2).toRefC();
	ShapeRefC long_box = new BoxShape(new Vec3(2, 7, 2)).toRefC();

	for (int i = 0; i < 3; ++i)
		for (int j = 0; j < 2; ++j)
		{
			// Create a box
			mBodyInterface.createAndAddBody(new BodyCreationSettings(big_box,new RVec3(-20.0f + i * 10.0f, 4, -20.0f + j * 40.0f), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

			// Place a dynamic body on it
			mBodyInterface.createAndAddBody(new BodyCreationSettings(j == 0? capsule : long_box,new RVec3(-20.0f + i * 10.0f, 12, -5.0f + i * 5.0f - 20.0f + j * 40.0f), Op.star(Quat.sRotation(Vec3.sAxisY(), 0.1f * JPH_PI) , Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI)), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
		}
}
}
