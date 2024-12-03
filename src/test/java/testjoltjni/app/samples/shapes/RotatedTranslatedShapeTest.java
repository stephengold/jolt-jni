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
import com.github.stephengold.joltjni.readonly.*;
import java.util.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.std.Std.*;
/**
 * A line-for-line Java translation of the Jolt Physics rotated-translated shape test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Shapes/RotatedTranslatedShapeTest.cpp
 */
public class RotatedTranslatedShapeTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	// Create a cone centered on the origin with the point pointing upwards
	List<Vec3Arg> points=new ArrayList<>(10);
	points.add(new Vec3(0, 2.5f, 0));
	for (float a = 0; a < degreesToRadians(360); a += degreesToRadians(36))
		points.add(new Vec3(sin(a), -2.5f, cos(a)));
	ConvexHullShapeSettings convex_hull = new ConvexHullShapeSettings(points);

	// Offset and rotate so that the cone is upside down on its point
	RotatedTranslatedShapeSettings rot_trans = new RotatedTranslatedShapeSettings(new Vec3(0, 2.5f, 0), Quat.sRotation(Vec3.sAxisX(), JPH_PI), convex_hull);

	// Place at 0 so that the point touches the floor
	mBodyInterface.createAndAddBody(new BodyCreationSettings(rot_trans, RVec3.sZero(), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
}
}
