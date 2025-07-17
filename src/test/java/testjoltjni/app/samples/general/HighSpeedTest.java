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
import com.github.stephengold.joltjni.readonly.*;
import com.github.stephengold.joltjni.std.*;
import java.util.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt-Physics high-speed test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/HighSpeedTest.cpp
 */
public class HighSpeedTest extends Test{

String  sScenes[] =
{
	"Simple",
	"Convex Hull On Large Triangles",
	"Convex Hull On Terrain1",
};

int sSelectedScene = 0;

void CreateDominoBlocks(RVec3Arg inOffset, int inNumWalls, float inDensity, float inRadius)
{
	BodyCreationSettings box_settings=new BodyCreationSettings();
	BoxShape box_shape = new BoxShape(new Vec3(0.9f, 1.0f, 0.1f));
	box_shape.setDensity(inDensity); // Make box more heavy so the bouncing ball keeps a higher velocity
	box_settings.setShape(box_shape);
	box_settings.setObjectLayer ( Layers.MOVING);

	// U shaped set of thin boxes
	for (int i = 0; i < inNumWalls; ++i)
	{
		box_settings.setPosition (plus( inOffset ,new Vec3(2.0f * i, 1, -1.1f - inRadius)));
		mBodyInterface.createAndAddBody(box_settings, EActivation.DontActivate);

		box_settings.setPosition (plus( inOffset ,new Vec3(2.0f * i, 1, +1.1f + inRadius)));
		mBodyInterface.createAndAddBody(box_settings, EActivation.DontActivate);
	}

	box_settings.setPosition (plus( inOffset ,new Vec3(-1.1f - inRadius, 1, 0)));
	box_settings.setRotation ( Quat.sRotation(Vec3.sAxisY(), 0.5f * JPH_PI));
	mBodyInterface.createAndAddBody(box_settings, EActivation.DontActivate);
}

void CreateDynamicObject(RVec3Arg inPosition,Vec3Arg inVelocity,Shape inShape){CreateDynamicObject(inPosition,inVelocity,inShape,EMotionQuality.LinearCast);}
void CreateDynamicObject(RVec3Arg inPosition, Vec3Arg inVelocity, Shape inShape, EMotionQuality inMotionQuality)
{
	BodyCreationSettings creation_settings=new BodyCreationSettings();
	creation_settings.setShape(inShape);
	creation_settings.setFriction ( 0.0f);
	creation_settings.setRestitution ( 1.0f);
	creation_settings.setLinearDamping ( 0.0f);
	creation_settings.setAngularDamping ( 0.0f);
	creation_settings.setMotionQuality ( inMotionQuality);
	creation_settings.setObjectLayer ( Layers.MOVING);
	creation_settings.setPosition ( inPosition);

	Body body = mBodyInterface.createBody(creation_settings);
	body.setLinearVelocity(inVelocity);
	mBodyInterface.addBody(body.getId(), inVelocity.isNearZero()? EActivation.DontActivate : EActivation.Activate);
}

void CreateSimpleScene()
{
	// Floor
	CreateFloor();

	final float radius = 0.1f;
	final int num_walls = 5;
	final float density = 2000.0f;
	final float speed = 240.0f;

	RVec3 offset=new RVec3(0, 0, -30);

	{
		// U shaped set of thin walls
		List<Triangle> triangles=new ArrayList<>(2*num_walls+1);
		for (int i = 0; i < num_walls; ++i)
		{
			triangles.add(new Triangle(new Float3(2.0f*i-1,0,-1-radius),new Float3(2.0f*i+1,0,-1-radius),new Float3(2.0f*i,2,-1-radius)));
			triangles.add(new Triangle(new Float3(2.0f*i-1,0,1+radius),new Float3(2.0f*i,2,1+radius),new Float3(2.0f*i+1,0,1+radius)));
		}
		triangles.add(new Triangle(new Float3(-1-radius,0,-1),new Float3(-1-radius,2,0),new Float3(-1-radius,0,1)));
		Body walls = mBodyInterface.createBody(new BodyCreationSettings(new MeshShapeSettings(triangles), offset, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
		walls.setRestitution(1.0f);
		walls.setFriction(0.0f);
		mBodyInterface.addBody(walls.getId(), EActivation.DontActivate);

		// Fast moving sphere against mesh
		CreateDynamicObject(plus(offset ,new Vec3(2.0f * num_walls - 1, 1, 0)),new Vec3(-speed, 0, -speed), new SphereShape(radius));
	}

	plusEquals(offset ,new Vec3(0, 0, 5));

	{
		// Create wall of domino blocks
		CreateDominoBlocks(offset, num_walls, density, radius);

		// Fast moving sphere against domino blocks
		CreateDynamicObject(plus(offset ,new Vec3(2.0f * num_walls - 1, 1, 0)),new Vec3(-speed, 0, -speed), new SphereShape(radius));
	}

	plusEquals(offset ,new Vec3(0, 0, 5));

	{
		// Create wall of domino blocks
		CreateDominoBlocks(offset, num_walls, density, radius);

		// Fast moving scaled box against domino blocks
		CreateDynamicObject(plus(offset ,new Vec3(2.0f * num_walls - 1, 1, 0)),new Vec3(-speed, 0, -speed), new ScaledShape(new BoxShape(Vec3.sReplicate(0.5f * radius), 0.01f), Vec3.sReplicate(2.0f)));
	}

	plusEquals(offset ,new Vec3(0, 0, 5));

	{
		// Fast moving box stuck in ground moving, one moving up, one moving down
		CreateDynamicObject(plus(offset ,new Vec3(-1, 0, 0)),new Vec3(0, speed, 0), new BoxShape(Vec3.sReplicate(radius)));
		CreateDynamicObject(plus(offset ,new Vec3(1, 0, 0)),new Vec3(0, -speed, 0), new BoxShape(Vec3.sReplicate(radius)));
	}

	plusEquals(offset ,new Vec3(0, 0, 5));

	{
		// Single shape that has 4 walls to surround fast moving sphere
		BodyCreationSettings enclosing_settings=new BodyCreationSettings();
		BoxShapeSettings box_shape = new BoxShapeSettings(new Vec3(1.0f, 1.0f, 0.1f));
		StaticCompoundShapeSettings enclosing_shape = new StaticCompoundShapeSettings();
		enclosing_shape.addShape(new Vec3(0, 0, 1), Quat.sIdentity(), box_shape);
		enclosing_shape.addShape(new Vec3(0, 0, -1), Quat.sIdentity(), box_shape);
		enclosing_shape.addShape(new Vec3(1, 0, 0), Quat.sRotation(Vec3.sAxisY(), 0.5f * JPH_PI), box_shape);
		enclosing_shape.addShape(new Vec3(-1, 0, 0), Quat.sRotation(Vec3.sAxisY(), 0.5f * JPH_PI), box_shape);
		enclosing_settings.setShapeSettings(enclosing_shape);
		enclosing_settings.setMotionType ( EMotionType.Kinematic);
		enclosing_settings.setObjectLayer ( Layers.MOVING);
		enclosing_settings.setPosition (plus( offset ,new Vec3(0, 1, 0)));
		mBodyInterface.createAndAddBody(enclosing_settings, EActivation.Activate);

		// Fast moving sphere in box
		CreateDynamicObject(plus(offset ,new Vec3(0, 0.5f, 0)),new Vec3(-speed, 0, -0.5f * speed), new SphereShape(radius));
	}

	plusEquals(offset ,new Vec3(0, 0, 5));

	{
		// Two boxes on a collision course
		CreateDynamicObject(plus(offset , new Vec3(1, 0.5f, 0)),new Vec3(-speed, 0, 0), new BoxShape(Vec3.sReplicate(radius)));
		CreateDynamicObject(plus(offset , new Vec3(-1, 0.5f, 0)),new Vec3(speed, 0, 0), new BoxShape(Vec3.sReplicate(radius)));
	}

	plusEquals(offset ,new Vec3(0, 0, 5));

	{
		// Two boxes on a collision course, off center
		CreateDynamicObject(plus(offset ,new Vec3(1, 0.5f, 0)),new Vec3(-speed, 0, 0), new BoxShape(Vec3.sReplicate(radius)));
		CreateDynamicObject(plus(offset ,new Vec3(-1, 0.5f, radius)),new Vec3(speed, 0, 0), new BoxShape(Vec3.sReplicate(radius)));
	}

	plusEquals(offset ,new Vec3(0, 0, 5));

	{
		// Two boxes on a collision course, one discrete
		CreateDynamicObject(plus(offset ,new Vec3(1, 0.5f, 0)),new Vec3(-speed, 0, 0), new BoxShape(Vec3.sReplicate(radius)));
		CreateDynamicObject(plus(offset ,new Vec3(-1, 0.5f, 0)),new Vec3(60.0f, 0, 0), new BoxShape(Vec3.sReplicate(radius)), EMotionQuality.Discrete);
	}

	plusEquals(offset ,new Vec3(0, 0, 5));

	{
		// Two boxes on a collision course, one inactive
		CreateDynamicObject(plus(offset ,new Vec3(1, 0.5f, 0)),new Vec3(-speed, 0, 0), new BoxShape(Vec3.sReplicate(radius)));
		CreateDynamicObject(plus(offset ,new Vec3(0, 0.5f, 0)), Vec3.sZero(), new BoxShape(Vec3.sReplicate(radius)));
	}

	plusEquals(offset , new Vec3(0, 0, 5));

	{
		// Two boxes on a collision course, one inactive and discrete
		CreateDynamicObject(plus(offset ,new Vec3(1, 0.5f, 0)),new Vec3(-speed, 0, 0), new BoxShape(Vec3.sReplicate(radius)));
		CreateDynamicObject(plus(offset ,new Vec3(0, 0.5f, 0)), Vec3.sZero(), new BoxShape(Vec3.sReplicate(radius)), EMotionQuality.Discrete);
	}

	plusEquals(offset , new Vec3(0, 0, 5));

	{
		// Create long thin shape
		BoxShapeSettings box_settings=new BoxShapeSettings(new Vec3(0.05f, 0.8f, 0.03f), 0.015f);
		box_settings.setEmbedded();
		BodyCreationSettings body_settings=new BodyCreationSettings(box_settings, plus(offset ,new Vec3(0, 1, 0)), Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI), EMotionType.Dynamic, Layers.MOVING);
		body_settings.setMotionQuality ( EMotionQuality.LinearCast);
		body_settings.setRestitution ( 0.0f);
		body_settings.setFriction ( 1.0f);

		Body body = mPhysicsSystem.getBodyInterface().createBody(body_settings);
		body.setLinearVelocity(new Vec3(0, -100.0f, 0));
		mPhysicsSystem.getBodyInterface().addBody(body.getId(), EActivation.Activate);
	}

	plusEquals(offset ,new Vec3(0, 0, 5));

	{
		// Create long thin shape under 45 degrees
		BoxShapeSettings box_settings=new BoxShapeSettings(new Vec3(0.05f, 0.8f, 0.03f), 0.015f);
		box_settings.setEmbedded();
		BodyCreationSettings body_settings=new BodyCreationSettings(box_settings, plus(offset , new Vec3(0, 1, 0)), Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI), EMotionType.Dynamic, Layers.MOVING);
		body_settings.setMotionQuality ( EMotionQuality.LinearCast);
		body_settings.setRestitution ( 0.0f);
		body_settings.setFriction ( 1.0f);

		Body body = mPhysicsSystem.getBodyInterface().createBody(body_settings);
		body.setLinearVelocity(new Vec3(0, -100.0f, 0));
		mPhysicsSystem.getBodyInterface().addBody(body.getId(), EActivation.Activate);
	}

	plusEquals(offset ,new Vec3(0, 0, 5));

	{
		// Create long thin shape with restitution
		BoxShapeSettings box_settings=new BoxShapeSettings(new Vec3(0.05f, 0.8f, 0.03f), 0.015f);
		box_settings.setEmbedded();
		BodyCreationSettings body_settings=new BodyCreationSettings(box_settings, plus(offset , new Vec3(0, 1, 0)), Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI), EMotionType.Dynamic, Layers.MOVING);
		body_settings.setMotionQuality ( EMotionQuality.LinearCast);
		body_settings.setRestitution ( 1.0f);
		body_settings.setFriction ( 1.0f);

		Body body = mPhysicsSystem.getBodyInterface().createBody(body_settings);
		body.setLinearVelocity(new Vec3(0, -100.0f, 0));
		mPhysicsSystem.getBodyInterface().addBody(body.getId(), EActivation.Activate);
	}

	plusEquals(offset ,new Vec3(0, 0, 5));

	{
		// Create long thin shape under 45 degrees with restitution
		BoxShapeSettings box_settings=new BoxShapeSettings(new Vec3(0.05f, 0.8f, 0.03f), 0.015f);
		box_settings.setEmbedded();
		BodyCreationSettings body_settings=new BodyCreationSettings(box_settings, plus(offset , new Vec3(0, 1, 0)), Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI), EMotionType.Dynamic, Layers.MOVING);
		body_settings.setMotionQuality ( EMotionQuality.LinearCast);
		body_settings.setRestitution ( 1.0f);
		body_settings.setFriction ( 1.0f);

		Body body = mPhysicsSystem.getBodyInterface().createBody(body_settings);
		body.setLinearVelocity(new Vec3(0, -100.0f, 0));
		mPhysicsSystem.getBodyInterface().addBody(body.getId(), EActivation.Activate);
	}
}

void CreateFastSmallConvexObjects()
{
	// Create small convex hull
	Vec3[] vertices = {
		new Vec3(-0.044661f, 0.001230f, 0.003877f),
		new Vec3(-0.024743f, -0.042562f, 0.003877f),
		new Vec3(-0.012336f, -0.021073f, 0.048484f),
		new Vec3(0.016066f, 0.028121f, -0.049904f),
		new Vec3(-0.023734f, 0.043275f, -0.024153f),
		new Vec3(0.020812f, 0.036341f, -0.019530f),
		new Vec3(0.012495f, 0.021936f, 0.045288f),
		new Vec3(0.026750f, 0.001230f, 0.049273f),
		new Vec3(0.045495f, 0.001230f, -0.022077f),
		new Vec3(0.022193f, -0.036274f, -0.021126f),
		new Vec3(0.022781f, -0.037291f, 0.029558f),
		new Vec3(0.014691f, -0.023280f, 0.052897f),
		new Vec3(-0.012187f, -0.020815f, -0.040214f),
		new Vec3(0.000541f, 0.001230f, -0.056224f),
		new Vec3(-0.039882f, 0.001230f, -0.019461f),
		new Vec3(0.000541f, 0.001230f, 0.056022f),
		new Vec3(-0.020614f, -0.035411f, -0.020551f),
		new Vec3(-0.019485f, 0.035916f, 0.027001f),
		new Vec3(-0.023968f, 0.043680f, 0.003877f),
		new Vec3(-0.020051f, 0.001230f, 0.039543f),
		new Vec3(0.026213f, 0.001230f, -0.040589f),
		new Vec3(-0.010797f, 0.020868f, 0.043152f),
		new Vec3(-0.012378f, 0.023607f, -0.040876f)
	};
	ConvexHullShapeSettings convex_settings=new ConvexHullShapeSettings(vertices);
	convex_settings.setEmbedded();
	BodyCreationSettings body_settings=new BodyCreationSettings(convex_settings, RVec3.sZero(), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
	body_settings.setMotionQuality ( EMotionQuality.LinearCast);

	// Create many instances with high velocity
	DefaultRandomEngine rnd=new DefaultRandomEngine();
	UniformFloatDistribution restitution_distrib=new UniformFloatDistribution(0.0f, 0.1f);
	UniformFloatDistribution velocity_distrib=new UniformFloatDistribution(-10.0f, 10.0f);
	for (int x = -25; x < 25; ++x)
		for (int y = -25 ; y < 25; ++y)
		{
			// Cast a ray to find the terrain
			RVec3 origin=new RVec3(x, 100.0, y);
			Vec3 direction=new Vec3(0, -100.0f, 0);
			RRayCast ray =new RRayCast( origin, direction );
			RayCastResult hit=new RayCastResult();
			if (mPhysicsSystem.getNarrowPhaseQuery().castRay(ray, hit,new SpecifiedBroadPhaseLayerFilter(BroadPhaseLayers.NON_MOVING), new SpecifiedObjectLayerFilter(Layers.NON_MOVING)))
			{
				// Place 10m above terrain
				body_settings.setPosition (plus( ray.getPointOnRay(hit.getFraction()) ,new RVec3(0, 10, 0)));
				body_settings.setRotation ( Quat.sRandom(rnd));
				body_settings.setRestitution ( restitution_distrib.nextFloat(rnd));

				Body body = mPhysicsSystem.getBodyInterface().createBody(body_settings);
				body.setLinearVelocity(new Vec3(velocity_distrib.nextFloat(rnd), -100.0f, velocity_distrib.nextFloat(rnd)));
				mPhysicsSystem.getBodyInterface().addBody(body.getId(), EActivation.Activate);
			}
		}
}

void CreateConvexOnLargeTriangles()
{
	// Create floor
	CreateLargeTriangleFloor();

	CreateFastSmallConvexObjects();
}

void CreateConvexOnTerrain1()
{
if (supportsObjectStream()) {
	// Load scene
	PhysicsSceneRef scene=new PhysicsSceneRef();
	if (!ObjectStreamIn.sReadObject("Assets/terrain1.bof", scene))
		FatalError("Failed to load scene");
	for (BodyCreationSettings body : scene.getBodies())
		body.setObjectLayer ( Layers.NON_MOVING);
	scene.fixInvalidScales();
	scene.createBodies(mPhysicsSystem);
}else{
	CreateFloor();
} // JPH_OBJECT_STREAM

	CreateFastSmallConvexObjects();
}

void Initialize()
{
	switch (sSelectedScene)
	{
	case 0:
		CreateSimpleScene();
		break;

	case 1:
		CreateConvexOnLargeTriangles();
		break;

	case 2:
		CreateConvexOnTerrain1();
		break;

	default:
		assert(false);
		break;
	}
}
/*TODO

void HighSpeedTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI->CreateTextButton(inSubMenu, "Select Scene", [this, inUI]() {
		UIElement *scene_name = inUI->CreateMenu();
		for (uint i = 0; i < size(sScenes); ++i)
			inUI->CreateTextButton(scene_name, sScenes[i], [this, i]() { sSelectedScene = i; RestartTest(); });
		inUI->ShowMenu(scene_name);
	});
}
*/}