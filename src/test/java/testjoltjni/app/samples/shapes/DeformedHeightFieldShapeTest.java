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
package testjoltjni.app.samples.shapes;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.operator.Op;
import java.nio.FloatBuffer;
import java.util.function.BiFunction;
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt Physics deformed heightfield-shape test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Shapes/DeformedHeightFieldShapeTest.cpp
 */
public class DeformedHeightFieldShapeTest extends Test{
BodyId mHeightFieldID=new BodyId();
float mTime;
float[]mHeightSamples;
static final int cBlockSize=4,cSampleCount=128;
static final int cBlockMask=cBlockSize-1;
ShapeRefC mHeightField;

public void Initialize()
{
	final float cCellSize = 1.0f;
	final float cMaxHeight = 2.5f;
	final float cSphereRadius = 2.0f;

	// Create height samples
	mHeightSamples=new float[cSampleCount * cSampleCount];
	for (int y = 0; y < cSampleCount; ++y)
		for (int x = 0; x < cSampleCount; ++x)
			mHeightSamples[y * cSampleCount + x] = cMaxHeight * Jolt.perlinNoise3((float)(x) * 8.0f / cSampleCount, 0, (float)(y) * 8.0f / cSampleCount, 256, 256, 256);

	// Determine scale and offset of the terrain
	Vec3 offset=new Vec3(-0.5f * cCellSize * cSampleCount, 0, -0.5f * cCellSize * cSampleCount);
	Vec3 scale=new Vec3(cCellSize, 1.0f, cCellSize);

	// Create height field
	HeightFieldShapeSettings settings=new HeightFieldShapeSettings(mHeightSamples, offset, scale, cSampleCount);
	settings.setBlockSize ( cBlockSize);
	settings.setBitsPerSample ( 8);
	settings.setMinHeightValue ( -15.0f);
	mHeightField = (settings.create().get());
	mHeightFieldID = mBodyInterface.createAndAddBody(new BodyCreationSettings(mHeightField, RVec3.sZero(), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Spheres on top of the terrain
	ShapeRefC sphere_shape = new SphereShape(cSphereRadius).toRefC();
	for (float t = 0.2f; t < 12.4f; t += 0.1f)
	{
		// Get the center of the path
		Vec3 center = Op.add(offset , GetPathCenter(t));

		// Cast a ray onto the terrain
		RShapeCast shape_cast=new RShapeCast(sphere_shape, Vec3.sReplicate(1.0f), RMat44.sTranslation(Op.add(new RVec3(0, 10, 0) , center)),new Vec3(0, -20, 0));
		ClosestHitCastShapeCollector collector=new ClosestHitCastShapeCollector();
		mPhysicsSystem.getNarrowPhaseQuery().castShape(shape_cast,new ShapeCastSettings(){ }, RVec3.sZero(), collector);
		if (collector.getHit().getBodyId2() == mHeightFieldID)
		{
			// Create sphere on terrain
			BodyCreationSettings bcs=new BodyCreationSettings(sphere_shape, shape_cast.getPointOnRay(collector.getHit().getFraction()), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
			mBodyInterface.createAndAddBody(bcs, EActivation.DontActivate);
		}
	}
}

Vec3 GetPathCenter(float inTime)
{
	final float cOffset = 5.0f;
	final float cRadiusX = 60.0f;
	final float cRadiusY = 25.0f;
	final float cFallOff = 0.1f;
	final float cAngularSpeed = 2.0f;
	final float cDisplacementSpeed = 10.0f;

	float fall_off = (float)Math.exp(-cFallOff * inTime);
	float angle = cAngularSpeed * inTime;
	return new Vec3(cRadiusX * Math.cos(angle) * fall_off + 64.0f, 0, cOffset + cDisplacementSpeed * inTime + cRadiusY * Math.sin(angle) * fall_off);
}

public void PrePhysicsUpdate(PreUpdateParams inParams)
{
	final float cPitRadius = 6.0f;
	final float cPitHeight = 1.0f;
	final float cSpeedScale = 2.0f;

	// Calculate center of pit
	Vec3 center = GetPathCenter(cSpeedScale * mTime);
	mTime += inParams.mDeltaTime;

	// Calculate affected area
	int start_x = Math.max((int)Math.floor(center.getX() - cPitRadius) & ~cBlockMask, 0);
	int start_y = Math.max((int)Math.floor(center.getZ() - cPitRadius) & ~cBlockMask, 0);
	int count_x = Math.min(((int)Math.ceil(center.getX() + cPitRadius) + cBlockMask) & ~cBlockMask, cSampleCount) - start_x;
	int count_y = Math.min(((int)Math.ceil(center.getZ() + cPitRadius) + cBlockMask) & ~cBlockMask, cSampleCount) - start_y;

	if (count_x > 0 && count_y > 0)
	{
		// Remember COM before we change the height field
		Vec3 old_com = mHeightField.getCenterOfMass();

		// A function to calculate the delta height at a certain distance from the center of the pit
		final float cHalfPi = 0.5f * Jolt.JPH_PI;
		BiFunction<Float,Float,Float> pit_shape = (Float inDistanceX, Float inDistanceY)-> { return (Float)(float)Math.cos(Math.min(Math.sqrt(Jolt.square(inDistanceX) + Jolt.square(inDistanceY)) * cHalfPi / cPitRadius, cHalfPi)); };

		AaBox affected_area=new AaBox();FloatBuffer b=Jolt.newDirectFloatBuffer(count_x*count_y);
		for (int y = 0; y < count_y; ++y)
			for (int x = 0; x < count_x; ++x)
			{
				// Update the height field
				float delta = pit_shape.apply((float)(start_x) + x - center.getX(), (float)(start_y) + y - center.getZ()) * cPitHeight;
				float h=(mHeightSamples[(start_y + y) * cSampleCount + start_x + x] -= delta);b.put(h);

				// Keep track of affected area to wake up bodies
				affected_area.encapsulate(((HeightFieldShape)mHeightField.getPtr()).getPosition(start_x + x, start_y + y));
			}
		((HeightFieldShape)mHeightField.getPtr()).setHeights(start_x, start_y, count_x, count_y, b, count_x, mTempAllocator);

		// Notify the shape that it has changed its bounding box
		mBodyInterface.notifyShapeChanged(mHeightFieldID, old_com, false, EActivation.DontActivate);

		// Activate bodies in the affected area (a change in the height field doesn't wake up bodies)
		affected_area.expandBy(Vec3.sReplicate(0.1f));
		DefaultBroadPhaseLayerFilter broadphase_layer_filter = mPhysicsSystem.getDefaultBroadPhaseLayerFilter(Layers.MOVING);
		DefaultObjectLayerFilter object_layer_filter = mPhysicsSystem.getDefaultLayerFilter(Layers.MOVING);
		mBodyInterface.activateBodiesInAaBox(affected_area, broadphase_layer_filter, object_layer_filter);
	}
}
}
