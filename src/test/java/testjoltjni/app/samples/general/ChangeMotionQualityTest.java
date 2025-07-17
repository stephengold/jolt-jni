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
import static com.github.stephengold.joltjni.Jolt.*;
/**
 * A line-for-line Java translation of the Jolt-Physics change motion-quality test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/ChangeMotionQualityTest.cpp
 */
public class ChangeMotionQualityTest extends Test{
Body mBody;
float mTime;

public void Initialize()
{
	// Floor
	CreateFloor();

	// Single shape that has 4 walls to surround fast moving sphere
	BodyCreationSettings enclosing_settings=new BodyCreationSettings();
	BoxShapeSettings box_shape = new BoxShapeSettings(new Vec3(5.0f, 1.0f, 0.1f));
	StaticCompoundShapeSettings  enclosing_shape = new StaticCompoundShapeSettings();
	enclosing_shape.addShape(new Vec3(0, 0, 5), Quat.sIdentity(), box_shape);
	enclosing_shape.addShape(new Vec3(0, 0, -5), Quat.sIdentity(), box_shape);
	enclosing_shape.addShape(new Vec3(5, 0, 0), Quat.sRotation(Vec3.sAxisY(), 0.5f * JPH_PI), box_shape);
	enclosing_shape.addShape(new Vec3(-5, 0, 0), Quat.sRotation(Vec3.sAxisY(), 0.5f * JPH_PI), box_shape);
	enclosing_settings.setShapeSettings(enclosing_shape);
	enclosing_settings.setMotionType ( EMotionType.Kinematic);
	enclosing_settings.setObjectLayer ( Layers.MOVING);
	enclosing_settings.setPosition (new RVec3(0, 1, 0));
	mBodyInterface.createAndAddBody(enclosing_settings, EActivation.Activate);

	// Create high speed sphere inside
	BodyCreationSettings settings=new BodyCreationSettings();
	settings.setShape(new SphereShape(1.0f));
	settings.setPosition (new RVec3(0, 0.5f, 0));
	settings.setMotionType ( EMotionType.Dynamic);
	settings.setMotionQuality ( EMotionQuality.LinearCast);
	settings.setLinearVelocity (new Vec3(-240, 0, -120));
	settings.setFriction ( 0.0f);
	settings.setRestitution ( 1.0f);
	settings.setObjectLayer ( Layers.MOVING);
	mBody = mBodyInterface.createBody(settings);
	mBodyInterface.addBody(mBody.getId(), EActivation.Activate);
}

void UpdateMotionQuality()
{
	// Calculate desired motion quality
	EMotionQuality motion_quality = ((int)(mTime) & 1) == 0? EMotionQuality.LinearCast : EMotionQuality.Discrete;
	mBodyInterface.setMotionQuality(mBody.getId(), motion_quality);
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	// Increment time
	mTime += inParams.mDeltaTime;

	UpdateMotionQuality();
}

public void SaveState(StateRecorder inStream)
{
	inStream.write(mTime);
}

public void RestoreState(StateRecorder inStream)
{
	mTime=inStream.readFloat(mTime);

	UpdateMotionQuality();
}
}