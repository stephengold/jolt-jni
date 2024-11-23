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
import com.github.stephengold.joltjni.std.*;
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt Physics change object-layer test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/ChangeObjectLayerTest.cpp
 */
public class ChangeObjectLayerTest extends Test{
BodyId mMoving;
BodyIdVector mDebris=new BodyIdVector();
boolean mIsDebris = true;
float mTime;

public void Initialize()
{
	// Floor
	CreateFloor();

	// A dynamic box in the MOVING layer
	mMoving = mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(5, 0.1f, 5)),new RVec3(0, 1.5f, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);

	// Lots of dynamic objects in the DEBRIS layer
	DefaultRandomEngine random=new DefaultRandomEngine();
	UniformRealDistribution position_variation=new UniformRealDistribution(-10, 10);
	for (int i = 0; i < 50; ++i)
	{
		RVec3 position=new RVec3(position_variation.nextFloat(random), 2.0f, position_variation.nextFloat(random));
		Quat rotation = Quat.sRandom(random);
		mDebris.pushBack(mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(0.1f)), position, rotation, EMotionType.Dynamic, Layers.DEBRIS), EActivation.Activate));
	}
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	final float cSwitchTime = 2.0f;

	// Increment time
	mTime += inParams.mDeltaTime;

	if (mTime >= cSwitchTime)
	{
		mIsDebris = !mIsDebris;

		// Reposition moving object
		mBodyInterface.setPosition(mMoving,new RVec3(0, 1.5f, 0), EActivation.Activate);

		DefaultRandomEngine random=new DefaultRandomEngine();
		UniformRealDistribution position_variation=new UniformRealDistribution(-7.5f, 7.5f);
		for (BodyId id : mDebris.toList())
		{
			// Reposition debris
			RVec3 position=new RVec3(position_variation.nextFloat(random), 2.0f, position_variation.nextFloat(random));
			Quat rotation = Quat.sRandom(random);
			mBodyInterface.setPositionAndRotation(id, position, rotation, EActivation.Activate);

			// And update layer
			mBodyInterface.setObjectLayer(id, mIsDebris? Layers.DEBRIS : Layers.MOVING);
		}

		mTime = 0;
	}
}

void SaveState(StateRecorder inStream)
{
	inStream.write(mTime);
	inStream.write(mIsDebris);
}

void RestoreState(StateRecorder inStream)
{
	mTime=inStream.readFloat(mTime);
	mIsDebris=inStream.readBoolean(mIsDebris);

	// Restore layer
	for (BodyId id : mDebris.toList())
		mBodyInterface.setObjectLayer(id, mIsDebris? Layers.DEBRIS : Layers.MOVING);
}
}