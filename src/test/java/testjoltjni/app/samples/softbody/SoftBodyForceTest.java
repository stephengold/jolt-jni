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
import java.util.function.BiFunction;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt-Physics soft-body force test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/SoftBody/SoftBodyForceTest.cpp
 */
public class SoftBodyForceTest extends Test{
int mBodyID=cInvalidBodyId;
float mTime;

void Initialize()
{
	CreateFloor();

	final int  cGridSize = 30;

	// Create hanging cloth
	BiFunction<Integer,Integer,Float> inv_mass = (Integer inX, Integer inZ) ->{
		return (inX == 0 && inZ == 0)
			|| (inX == cGridSize - 1 && inZ == 0)? 0.0f : 1.0f;
	};
	SoftBodyCreationSettings cloth=new SoftBodyCreationSettings(SoftBodyCreator.CreateCloth(cGridSize, cGridSize, 0.75f, inv_mass),new RVec3(0, 15.0f, 0), Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI), Layers.MOVING);
	mBodyID = mBodyInterface.createAndAddSoftBody(cloth, EActivation.Activate);
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	mTime += inParams.mDeltaTime;

	// Apply a fluctuating force
	final float cMaxForce = 10000.0f;
	final float cMaxAngle = degreesToRadians(90.0f);
	Vec3 force=new Vec3(0, 0, 0.5f * cMaxForce * (1.0f + perlinNoise3(0, 0, mTime / 2.0f, 256, 256, 256)));
	force = star(Mat44.sRotationY(cMaxAngle * perlinNoise3(mTime / 10.0f, 0, 0, 256, 256, 256)) , force);
	mBodyInterface.addForce(mBodyID, force);

	// Draw the force
	RVec3 offset=new RVec3(0, 10, 0);
	DebugRenderer.sInstance().drawArrow(offset, plus(offset ,star( 10.0f , force.normalized())), Color.sGreen, 0.1f);
}

protected void SaveState(StateRecorder inStream)
{
	inStream.write(mTime);
}

protected void RestoreState(StateRecorder inStream)
{
	mTime=inStream.readFloat(mTime);
}
}
