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
package testjoltjni.app.samples.softbody;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.std.Std.*;
/**
 * A line-for-line Java translation of the Jolt Physics soft-body update-position test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/SoftBody/SoftBodyUpdatePositionTest.cpp
 */
public class SoftBodyUpdatePositionTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	// Bodies with various settings for 'make rotation identity' and 'update position'
	SoftBodyCreationSettings sphere=new SoftBodyCreationSettings(SoftBodySharedSettings.sCreateCube(5, 0.5f), RVec3.sZero(), Quat.sRotation(Vec3.sReplicate(1.0f / sqrt(3.0f)), 0.25f * JPH_PI), Layers.MOVING);

	for (int update_position = 0; update_position < 2; ++update_position)
		for (int make_rotation_identity = 0; make_rotation_identity < 2; ++make_rotation_identity)
		{
			sphere.setPosition (new RVec3(update_position * 10.0f, 10.0f, make_rotation_identity * 10.0f));
			sphere.setUpdatePosition ( update_position != 0);
			sphere.setMakeRotationIdentity ( make_rotation_identity != 0);
			mBodyInterface.createAndAddSoftBody(sphere, EActivation.Activate);
		}
}
}
