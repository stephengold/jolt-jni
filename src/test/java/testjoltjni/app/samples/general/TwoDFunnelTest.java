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
package testjoltjni.app.samples.general;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
/**
 * A line-for-line Java translation of the Jolt-Physics 2-D funnel test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/TwoDFunnelTest.cpp
 */
public class TwoDFunnelTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	ShapeRefC wall = new BoxShape(new Vec3(0.1f, 10, 1)).toRefC();

	// 2D funnel
	mBodyInterface.createAndAddBody(new BodyCreationSettings(wall,new RVec3(-12, 8, -5), Quat.sRotation(Vec3.sAxisZ(), 0.2f * JPH_PI), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
	mBodyInterface.createAndAddBody(new BodyCreationSettings(wall,new RVec3(12, 8, -5), Quat.sRotation(Vec3.sAxisZ(), -0.2f * JPH_PI), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Shapes falling in 2D funnel
	Shape shapes[] = {
		new SphereShape(0.5f),
		new BoxShape(Vec3.sReplicate(0.5f)),
		new CapsuleShape(0.2f, 0.3f)
	};
	BodyCreationSettings bcs=new BodyCreationSettings(shapes[0], RVec3.sZero(), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
	bcs.setAllowedDofs ( EAllowedDofs.Plane2D);
	for (int x = 0; x < 20; ++x)
		for (int y = 0; y < 10; ++y)
		{
			bcs.setShape(shapes[(x * y) % shapes.length]);
			bcs.setPosition (new RVec3(-10.0 + x, 10.0 + y, -5.0));
			mBodyInterface.createAndAddBody(bcs, EActivation.Activate);
		}
}
}
