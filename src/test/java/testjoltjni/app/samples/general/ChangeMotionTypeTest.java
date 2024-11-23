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
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.std.Std.*;
/**
 * A line-for-line Java translation of the Jolt Physics change motion-type test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/ChangeMotionTypeTest.cpp
 */
public class ChangeMotionTypeTest extends Test{
Body mBody;
float mTime;

public void Initialize()
{
	// Floor
	CreateFloor();

	// Create body as static, but allow to become dynamic
	BodyCreationSettings settings=new BodyCreationSettings();
	settings.setShape(new BoxShape(new Vec3(0.5f, 1.0f, 2.0f)));
	settings.setPosition (new RVec3(0, 10, 0));
	settings.setMotionType ( EMotionType.Dynamic);
	settings.setObjectLayer ( Layers.MOVING); // Put in moving layer, this will result in some overhead when the body is static
	settings.setAllowDynamicOrKinematic ( true);
	mBody = mBodyInterface.createBody(settings);
	mBodyInterface.addBody(mBody.getId(), EActivation.Activate);
}

void UpdateMotionType()
{
	// Calculate desired motion type
	EMotionType cycle[] = { EMotionType.Dynamic, EMotionType.Kinematic, EMotionType.Static, EMotionType.Kinematic, EMotionType.Dynamic, EMotionType.Static };
	EMotionType motion_type = cycle[(int)(mTime) % cycle.length];

	// Update motion type and reactivate the body
	if (motion_type != mBody.getMotionType())
		mBodyInterface.setMotionType(mBody.getId(), motion_type, EActivation.Activate);
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	// Increment time
	mTime += inParams.mDeltaTime;

	UpdateMotionType();

	// Provide kinematic body a target
	if (mBody.isKinematic())
		mBody.moveKinematic(new RVec3(sin(mTime), 10, 0), Quat.sRotation(Vec3.sAxisX(), cos(mTime)), inParams.mDeltaTime);
}

void SaveState(StateRecorder inStream)
{
	inStream.write(mTime);
}

void RestoreState(StateRecorder inStream)
{
	mTime=inStream.readFloat(mTime);

	UpdateMotionType();
}
}
