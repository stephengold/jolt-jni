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
/**
 * A line-for-line Java translation of the Jolt Physics dynamic-mesh test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/DynamicMeshTest.cpp
 */
public class DynamicMeshTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	final float cTorusRadius = 3.0f;
	final float cTubeRadius = 1.0f;

	// Create torus
	ShapeRefC mesh_shape = ShapeCreator.CreateTorusMesh(cTorusRadius, cTubeRadius);
	BodyCreationSettings settings=new BodyCreationSettings(mesh_shape,new RVec3(0, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);

	// Mesh cannot calculate its mass, we must provide it
	settings.setOverrideMassProperties ( EOverrideMassProperties.MassAndInertiaProvided);
	settings.getMassPropertiesOverride().setMassAndInertiaOfSolidBox(Op.multiply(2.0f ,new Vec3(cTorusRadius, cTubeRadius, cTorusRadius)), 1000.0f);

	mBodyInterface.createAndAddBody(settings, EActivation.Activate);

	// Wall of blocks
	ShapeRefC box_shape = new BoxShape(Vec3.sReplicate(0.5f)).toRefC();
	for (int i = 0; i < 7; ++i)
		for (int j = i / 2; j < 7 - (i + 1) / 2; ++j)
		{
			RVec3 position=new RVec3(-3.5f + j * 1.0f + (((i & 1)!=0)? 0.5f : 0.0f), 0.5f + i * 1.0f, 0.0f);
			Body wall = mBodyInterface.createBody(new BodyCreationSettings(box_shape, position, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
			mBodyInterface.addBody(wall.getId(), EActivation.Activate);
		}
}
}
