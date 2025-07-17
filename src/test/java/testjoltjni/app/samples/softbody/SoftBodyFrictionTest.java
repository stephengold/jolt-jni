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
/**
 * A line-for-line Java translation of the Jolt-Physics soft-body friction test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/SoftBody/SoftBodyFrictionTest.cpp
 */
public class SoftBodyFrictionTest extends Test{

public void Initialize()
{
	// Floor
	Body floor = CreateFloor();
	floor.setFriction(1.0f);

	// Bodies with increasing friction
	SoftBodySharedSettingsRef sphere_settings = SoftBodyCreator.CreateSphere();
	for (Vertex v : sphere_settings.getPtr().getVertices())
		v.setVelocity (new Float3(0, 0, 10));
	SoftBodyCreationSettings sphere=new SoftBodyCreationSettings(sphere_settings, RVec3.sZero(), Quat.sIdentity(), Layers.MOVING);
	sphere.setPressure ( 2000.0f);

	for (int i = 0; i <= 10; ++i)
	{
		sphere.setPosition (new RVec3(-50.0f + i * 10.0f, 1.0f, 0));
		sphere.setFriction ( 0.1f * i);
		mBodyInterface.createAndAddSoftBody(sphere, EActivation.Activate);
	}

	SoftBodySharedSettingsRef cube_settings = SoftBodySharedSettings.sCreateCube(5, 0.5f);
	for (Vertex v : cube_settings.getPtr().getVertices())
		v.setVelocity (new Float3(0, 0, 10));
	SoftBodyCreationSettings cube=new SoftBodyCreationSettings(cube_settings, RVec3.sZero(), Quat.sIdentity(), Layers.MOVING);

	for (int i = 0; i <= 10; ++i)
	{
		cube.setPosition (new RVec3(-50.0f + i * 10.0f, 1.0f, -5.0f));
		cube.setFriction ( 0.1f * i);
		mBodyInterface.createAndAddSoftBody(cube, EActivation.Activate);
	}
}
}
