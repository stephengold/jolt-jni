/*
Copyright (c) 2024-2026 Stephen Gold

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
import java.util.ArrayList;
import java.util.List;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.JphMath.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt-Physics friction test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/FrictionTest.cpp
 */
public class FrictionTest extends Test{

String sScenes[] =
{
	"Ramp",
	"Continuous Impulses"
};

int sSelectedScene = 0;
List<Body>mBodies=new ArrayList<>(11);

public void Initialize()
{
	if (sSelectedScene == 0)
	{
		// Test a ramp with different friction values

		// Floor
		Body floor = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(new Vec3(100.0f, 1.0f, 100.0f), 0.0f), RVec3.sZero(), Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI), EMotionType.Static, Layers.NON_MOVING));
		floor.setFriction(1.0f);
		mBodyInterface.addBody(floor.getId(), EActivation.DontActivate);

		ShapeRefC box = new BoxShape(new Vec3(2.0f, 2.0f, 2.0f)).toRefC();
		ShapeRefC sphere = new SphereShape(2.0f).toRefC();

		// Bodies with increasing friction
		for (int i = 0; i <= 10; ++i)
		{
			Body body = mBodyInterface.createBody(new BodyCreationSettings(box,new RVec3(-50.0f + i * 10.0f, 55.0f, -50.0f), Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI), EMotionType.Dynamic, Layers.MOVING));
			body.setFriction(0.1f * i);
			mBodyInterface.addBody(body.getId(), EActivation.Activate);
		}

		for (int i = 0; i <= 10; ++i)
		{
			Body body = mBodyInterface.createBody(new BodyCreationSettings(sphere,new RVec3(-50.0f + i * 10.0f, 47.0f, -40.0f), Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI), EMotionType.Dynamic, Layers.MOVING));
			body.setFriction(0.1f * i);
			mBodyInterface.addBody(body.getId(), EActivation.Activate);
		}
	}
	else
	{
		// Test that continous impulses in a direction tied to the body will not cause the body to rotate

		// Floor
		Body floor = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(new Vec3(100.0f, 1.0f, 100.0f), 0.0f), RVec3.sZero(), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
		floor.setFriction(1.0f);
		mBodyInterface.addBody(floor.getId(), EActivation.DontActivate);

		ShapeRefC box = new BoxShape(new Vec3(3.0f, 2.0f, 2.0f)).toRefC();

		// Bodies with increasing friction
		for (int i = 0; i <= 10; ++i)
		{
			Body body = mBodyInterface.createBody(new BodyCreationSettings(box,new RVec3(-50.0f + i * 10.0f, 2.999f, -50.0f), Quat.sRotation(Vec3.sAxisY(), 0.25f * JPH_PI), EMotionType.Dynamic, Layers.MOVING));
			body.setFriction(0.1f * i);
			mBodyInterface.addBody(body.getId(), EActivation.Activate);
			mBodies.add(body);
		}
	}
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	if (sSelectedScene == 1)
		for (Body body : mBodies)
		{
			mBodyInterface.addImpulse(body.getId(), slash(star(body.getRotation() ,new Vec3(0, 0, 0.15f)) , body.getMotionProperties().getInverseMass()));
			SetBodyLabel(body.getId(), String.format("Friction: %.1f, x %.1f, orientation %.6f", (double)(body.getFriction()), (double)(body.getPosition().getX()), (double)(radiansToDegrees(body.getRotation().getRotationAngle(Vec3.sAxisY())))));
		}
}
/*
void FrictionTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI->CreateTextButton(inSubMenu, "Select Scene", [this, inUI]() {
		UIElement *scene_name = inUI->CreateMenu();
		for (uint i = 0; i < size(sScenes); ++i)
			inUI->CreateTextButton(scene_name, sScenes[i], [this, i]() { sSelectedScene = i; RestartTest(); });
		inUI->ShowMenu(scene_name);
	});
}
*/
}
