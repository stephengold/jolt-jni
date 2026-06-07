/*
Copyright (c) 2026 Stephen Gold

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
package testjoltjni.app.performancetest;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.lambda.TriFunction;
import com.github.stephengold.joltjni.operator.Op;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.std.Mt19937;
import java.util.*;
import static com.github.stephengold.joltjni.JphMath.*;

/**
 * A line-for-line Java translation of the Jolt-Physics "high-speed scene"
 * performance test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/PerformanceTest/HighSpeedScene.h
 */

// A scene that contains a large number of fast moving objects
class HighSpeedScene implements  PerformanceTestScene
{
	public String  	GetName()  
	{
		return "HighSpeed";
	}

	public boolean			Load(  ) 
	{
		final float shape_size = 0.5f;

		CompoundShapeSettings compound = new StaticCompoundShapeSettings();
		compound.addShape(new Vec3(0, shape_size, 0), Quat.sIdentity(), new CapsuleShapeSettings(shape_size, 0.25f * shape_size));
		compound.addShape(new Vec3(shape_size, 0, 0), Quat.sIdentity(), new BoxShapeSettings(new Vec3(shape_size, 0.25f * shape_size, 0.25f * shape_size)));

		mShapes = List.of(
			new SphereShape(shape_size),
			new BoxShape(new Vec3(0.5f * shape_size, shape_size, 1.0f * shape_size)),
			compound.create().get().getPtr()
		);

		return true;
	}

	public void			StartTest(PhysicsSystem inPhysicsSystem, EMotionQuality inMotionQuality)
	{
		BodyInterface bi = inPhysicsSystem.getBodyInterface();

		final float speed = 250.0f;
		final float wall_thickness = 0.2f;
		final float half_box_size = 50.0f;
		final float pos_range = 0.9f * half_box_size;

		// Create hollow box to enclose the objects
		BodyCreationSettings body_settings=new BodyCreationSettings(new BoxShape(new Vec3(half_box_size, wall_thickness, half_box_size)),new RVec3(0, -(half_box_size), 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
		body_settings.setRestitution ( 1.0f);
		body_settings.setFriction ( 1.0f);
		bi.createAndAddBody(body_settings, EActivation.DontActivate);
		body_settings.setPosition (new RVec3(0, (half_box_size), 0));
		bi.createAndAddBody(body_settings, EActivation.DontActivate);
		body_settings.setPosition (new RVec3(0, 0, -(half_box_size)));
		body_settings.setRotation ( Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI));
		bi.createAndAddBody(body_settings, EActivation.DontActivate);
		body_settings.setPosition (new RVec3(0, 0, (half_box_size)));
		bi.createAndAddBody(body_settings, EActivation.DontActivate);
		body_settings.setPosition (new RVec3(-(half_box_size), 0, 0));
		body_settings.setRotation ( Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI));
		bi.createAndAddBody(body_settings, EActivation.DontActivate);
		body_settings.setPosition (new RVec3((half_box_size), 0, 0));
		bi.createAndAddBody(body_settings, EActivation.DontActivate);

		// Create dynamic box
		BodyCreationSettings dynamic_body_settings=new BodyCreationSettings(mShapes.get(0), RVec3.sZero(), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
		dynamic_body_settings.setMotionQuality ( EMotionQuality.LinearCast);

		// Create many instances with high velocity (don't use std::uniform_real_distribution as that is not cross platform deterministic)
		Mt19937 rnd=new Mt19937();
		TriFunction<Mt19937,Float,Float,Float> random_float = (Mt19937 inRnd, Float inMin, Float inMax)-> { return inMin + (float)(inRnd.nextUnsigned() - inRnd.min()) * (inMax - inMin) / (float)(inRnd.max() - inRnd.min()); };
		for (int i = 0; i < 5000; ++i)
		{
			// Note that we explicitly order x, y and z. Calling this in the constructor means they get called in a different order on different platforms. This breaks cross platform determinism.
			double x = (random_float.apply(rnd, -pos_range, pos_range));
			double y = (random_float.apply(rnd, -pos_range, pos_range));
			double z = (random_float.apply(rnd, -pos_range, pos_range));

			dynamic_body_settings.setShape(mShapes.get(i % mShapes.size()));
			dynamic_body_settings.setPosition (new RVec3(x, y, z));
			dynamic_body_settings.setRotation ( Quat.sRandom(rnd));
			dynamic_body_settings.setFriction ( random_float.apply(rnd, 0.5f, 1.0f));
			dynamic_body_settings.setRestitution ( random_float.apply(rnd, 0.9f, 1.0f));
			dynamic_body_settings.setLinearVelocity ( Op.star(speed , Vec3.sRandom(rnd)));
			bi.createAndAddBody(dynamic_body_settings, EActivation.Activate);
		}
	}

	List<ConstShape>		mShapes=new ArrayList<>();
};
