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
import com.github.stephengold.joltjni.readonly.*;
import com.github.stephengold.joltjni.std.*;
import java.util.*;
import testjoltjni.app.samples.*;
import testjoltjni.app.testframework.CameraState;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt Physics funnel test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/FunnelTest.cpp
 */
public class FunnelTest extends Test{

public void Initialize()
{
	ShapeRefC box = new BoxShape(new Vec3(50, 1, 50), 0.0f).toRefC();

	// Funnel
	for (int i = 0; i < 4; ++i)
	{
		Quat rotation = Quat.sRotation(Vec3.sAxisY(), 0.5f * JPH_PI * i);

		mBodyInterface.createAndAddBody(new BodyCreationSettings(box,new RVec3(star(rotation ,new Vec3(25, 25, 0))), star(rotation , Quat.sRotation(Vec3.sAxisZ(), 0.25f * JPH_PI)), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
	}

	DefaultRandomEngine random=new DefaultRandomEngine();
	UniformFloatDistribution feature_size=new UniformFloatDistribution(0.1f, 2.0f);
	UniformFloatDistribution position_variation=new UniformFloatDistribution(-40, 40);
	UniformFloatDistribution scale_variation=new UniformFloatDistribution(-1.5f, 1.5f);

	// Random scale
	Vec3 scale=new Vec3(scale_variation.nextFloat(random), scale_variation.nextFloat(random), scale_variation.nextFloat(random));

	// Make it minimally -0.5 or 0.5 depending on the sign
	plusEquals(scale , Vec3.sSelect(Vec3.sReplicate(-0.5f), Vec3.sReplicate(0.5f), Vec3.sGreaterOrEqual(scale, Vec3.sZero())));

	ShapeRefC shape=new ShapeRefC();
	for (int i = 0; i < 1000; ++i)
	{
		switch (Math.abs(random.nextInt()) % 10)
		{
		case 0:
			{
				shape = new SphereShape(feature_size.nextFloat(random)).toRefC();
				scale = scale.swizzle(SWIZZLE_X, SWIZZLE_X, SWIZZLE_X); // Only uniform scale supported
				break;
			}

		case 1:
			{
				shape = new BoxShape(new Vec3(feature_size.nextFloat(random), feature_size.nextFloat(random), feature_size.nextFloat(random))).toRefC();
				break;
			}

		case 2:
			{
				// Create random points
				List<Vec3Arg> points=new ArrayList<>(20);
				for (int j = 0; j < 20; ++j)
					points.add(star(feature_size.nextFloat(random) , Vec3.sRandom(random)));
				shape =new ConvexHullShapeSettings(points).create().get();
				break;
			}

		case 3:
			{
				shape = new CapsuleShape(0.5f * feature_size.nextFloat(random), feature_size.nextFloat(random)).toRefC();
				scale = scale.swizzle(SWIZZLE_X, SWIZZLE_X, SWIZZLE_X); // Only uniform scale supported
				break;
			}

		case 4:
			{
				float top = feature_size.nextFloat(random);
				float bottom = feature_size.nextFloat(random);
				float half_height = Math.max(0.5f * feature_size.nextFloat(random), 0.5f * Math.abs(top - bottom) + 0.001f);
				shape =new TaperedCapsuleShapeSettings(half_height, top, bottom).create().get();
				scale = scale.swizzle(SWIZZLE_X, SWIZZLE_X, SWIZZLE_X); // Only uniform scale supported
				break;
			}

		case 5:
			{
				shape = new CylinderShape(0.5f * feature_size.nextFloat(random), feature_size.nextFloat(random)).toRefC();
				scale = scale.swizzle(SWIZZLE_X, SWIZZLE_Y, SWIZZLE_X); // Scale X must be same as Z
				break;
			}

		case 6:
			{
				shape =new TaperedCylinderShapeSettings(0.5f * feature_size.nextFloat(random), feature_size.nextFloat(random), feature_size.nextFloat(random)).create().get();
				scale = scale.swizzle(SWIZZLE_X, SWIZZLE_Y, SWIZZLE_X); // Scale X must be same as Z
				break;
			}

		case 7:
			{
				shape =new TaperedCylinderShapeSettings(0.5f * feature_size.nextFloat(random), 0.0f, feature_size.nextFloat(random), 0.0f).create().get();
				scale = scale.swizzle(SWIZZLE_X, SWIZZLE_Y, SWIZZLE_X); // Scale X must be same as Z
				break;
			}

		case 8:
			{
				// Simple compound
				StaticCompoundShapeSettings compound_shape_settings=new StaticCompoundShapeSettings();
				compound_shape_settings.addShape(Vec3.sZero(), Quat.sIdentity(), new CapsuleShape(1, 0.1f));
				compound_shape_settings.addShape(new Vec3(0, -1, 0), Quat.sIdentity(), new SphereShape(0.5f));
				compound_shape_settings.addShape(new Vec3(0, 1, 0), Quat.sIdentity(), new SphereShape(0.5f));
				shape = compound_shape_settings.create().get();
				scale = scale.swizzle(SWIZZLE_X, SWIZZLE_X, SWIZZLE_X); // Only uniform scale supported
				break;
			}

		case 9:
			{
				// Compound with sub compound and rotation
				StaticCompoundShapeSettings sub_compound = new StaticCompoundShapeSettings();
				sub_compound.addShape(new Vec3(0, 0.75f, 0), Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI), new BoxShape(new Vec3(0.75f, 0.25f, 0.2f)));
				sub_compound.addShape(new Vec3(0.75f, 0, 0), Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI), new CylinderShape(0.75f, 0.2f).toRefC());
				sub_compound.addShape(new Vec3(0, 0, 0.75f), Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI), new TaperedCapsuleShapeSettings(0.75f, 0.25f, 0.2f));
				StaticCompoundShapeSettings compound_shape_settings=new StaticCompoundShapeSettings();
				compound_shape_settings.addShape(new Vec3(0, 0, 0), star(Quat.sRotation(Vec3.sAxisX(), -0.25f * JPH_PI) , Quat.sRotation(Vec3.sAxisZ(), 0.25f * JPH_PI)), sub_compound);
				compound_shape_settings.addShape(new Vec3(0, -0.1f, 0), star(Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI) , Quat.sRotation(Vec3.sAxisZ(), -0.75f * JPH_PI)), sub_compound);
				shape = compound_shape_settings.create().get();
				scale = scale.swizzle(SWIZZLE_X, SWIZZLE_X, SWIZZLE_X); // Only uniform scale supported
				break;
			}
		}

		// Scale the shape
		if ((random.nextInt() % 3) == 0)
			shape = new ScaledShape(shape, scale).toRefC();

		RVec3 position=new RVec3(position_variation.nextFloat(random), 100.0f + position_variation.nextFloat(random), position_variation.nextFloat(random));
		mBodyInterface.createAndAddBody(new BodyCreationSettings(shape, position, Quat.sRandom(random), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
	}
}

void GetInitialCamera(CameraState ioState)
{
	RVec3 cam_tgt =new RVec3(0, 50, 0);
	ioState.mPos =new RVec3(50, 100, 50);
	ioState.mForward =new Vec3(minus(cam_tgt , ioState.mPos)).normalized();
}
}
