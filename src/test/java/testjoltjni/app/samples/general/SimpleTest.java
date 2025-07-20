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
 * A line-for-line Java translation of the Jolt-Physics simple test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/SimpleTest.cpp
 */
public class SimpleTest extends Test{
BodyActivationListener mBodyActivationListener=new CustomBodyActivationListener(){
 public void OnBodyActivated(int inBodyID,long inBodyUserData){Trace("Body %d activated",inBodyID);}
 public void OnBodyDeactivated(int inBodyID,long inBodyUserData){Trace("Body %d deactivated",inBodyID);}
};

public void Cleanup()
{
	// Unregister activation listener
	mPhysicsSystem.setBodyActivationListener(null);
}

public void Initialize()
{
	// Register activation listener
	mPhysicsSystem.setBodyActivationListener(mBodyActivationListener);

	// Floor
	CreateFloor();

	ShapeRefC box_shape = new BoxShape(new Vec3(0.5f, 1.0f, 2.0f)).toRefC();

	// Dynamic body 1
	mBodyInterface.createAndAddBody(new BodyCreationSettings(box_shape,new RVec3(0, 10, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Dynamic body 2
	mBodyInterface.createAndAddBody(new BodyCreationSettings(box_shape,new RVec3(5, 10, 0), Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Dynamic body 3
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new SphereShape(2.0f),new RVec3(10, 10, 0), Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
}
}