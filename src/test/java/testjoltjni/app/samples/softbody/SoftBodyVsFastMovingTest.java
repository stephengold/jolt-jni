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
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt Physics soft-body versus fast-moving test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/SoftBody/SoftBodyVsFastMovingTest.cpp
 */
public class SoftBodyVsFastMovingTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	// Create sphere moving towards the cloth
	BodyCreationSettings bcs=new BodyCreationSettings(new SphereShape(1.0f),new RVec3(-2, 20, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
	bcs.setMotionQuality ( EMotionQuality.LinearCast);
	bcs.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
	bcs.getMassPropertiesOverride().setMass ( 25.0f);
	bcs.setLinearVelocity (new Vec3(0, -250, 0));
	mBodyInterface.createAndAddBody(bcs, EActivation.Activate);

	// Create cloth that's fixated at the corners
	SoftBodyCreationSettings cloth=new SoftBodyCreationSettings(SoftBodyCreator.CreateClothWithFixatedCorners(),new RVec3(0, 15, 0), star(Quat.sRotation(Vec3.sAxisX(), 0.1f * JPH_PI) , Quat.sRotation(Vec3.sAxisY(), 0.25f * JPH_PI)), Layers.MOVING);
	cloth.setUpdatePosition ( false); // Don't update the position of the cloth as it is fixed to the world
	cloth.setMakeRotationIdentity ( false); // Test explicitly checks if soft bodies with a rotation collide with shapes properly
	mBodyInterface.createAndAddSoftBody(cloth, EActivation.Activate);

	// Create another body with a higher ID than the cloth
	bcs.setPosition (new RVec3(2, 20, 0));
	mBodyInterface.createAndAddBody(bcs, EActivation.Activate);
}
}
