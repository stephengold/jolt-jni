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
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt-Physics gyroscopic-force test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/GyroscopicForceTest.cpp
 */
public class GyroscopicForceTest extends Test{

void Initialize()
{
	// Floor
	CreateFloor();

	StaticCompoundShapeSettings compound=new StaticCompoundShapeSettings();
	compound.addShape(Vec3.sZero(), Quat.sIdentity(), new BoxShape(new Vec3(0.5f, 5.0f, 0.5f)));
	compound.addShape(new Vec3(1.5f, 0, 0), Quat.sIdentity(), new BoxShape(new Vec3(1.0f, 0.5f, 0.5f)));
	compound.setEmbedded();

	// One body without gyroscopic force
	BodyCreationSettings bcs=new BodyCreationSettings(compound,new RVec3(0, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
	bcs.setLinearDamping ( 0.0f);
	bcs.setAngularDamping ( 0.0f);
	bcs.setAngularVelocity (new Vec3(10, 1, 0));
	bcs.setGravityFactor ( 0.0f);
	mBodyInterface.createAndAddBody(bcs, EActivation.Activate);

	// One body with gyroscopic force
	bcs.setPosition (plus(bcs.getPosition(),new RVec3(10, 0, 0)));
	bcs.setApplyGyroscopicForce ( true);
	mBodyInterface.createAndAddBody(bcs, EActivation.Activate);
}
}
