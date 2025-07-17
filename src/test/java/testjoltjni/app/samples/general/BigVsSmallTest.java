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
import java.util.*;
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt-Physics big-versus-small test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/BigVsSmallTest.cpp
 */
public class BigVsSmallTest extends Test{

void Initialize()
{
	// Create a big triangle
	List<Triangle> triangles=new ArrayList<>(1);
	triangles.add(new Triangle(new Float3(-100, 0, 0),new Float3(0, 0, 100),new Float3(100, 0, -100)));
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new MeshShapeSettings(triangles), RVec3.sZero(), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// A small box
	Body body = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(new Vec3(0.1f, 0.1f, 0.1f)),new RVec3(0, 1.0f, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
	body.setAllowSleeping(false);
	mBodyInterface.addBody(body.getId(), EActivation.Activate);
}
}