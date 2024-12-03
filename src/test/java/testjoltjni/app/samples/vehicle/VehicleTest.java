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
package testjoltjni.app.samples.vehicle;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.readonly.*;
import com.github.stephengold.joltjni.std.*;
import java.io.*;
import java.util.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import static com.github.stephengold.joltjni.std.Std.*;
/**
 * A line-for-line Java translation of the Jolt Physics vehicle-test abstract class.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Vehicle/VehicleTest.cpp
 */
abstract class VehicleTest extends Test{

private String sScenes[] =
{
	"Flat",
	"Flat With Slope",
	"Steep Slope",
	"Step",
	"Dynamic Step",
	"Playground",
	"Loop",
	"Terrain1",
};

String sSceneName = "Playground";
class Line {Line(RVec3 s, RVec3 e){mStart=e;mEnd=e;}
    RVec3 mStart, mEnd;
}
List<Line> mTrackData = new ArrayList<>();

public void Initialize()
{
	if (sSceneName.equals( "Flat")  )
	{
		// Flat test floor
		Body floor = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(new Vec3(1000.0f, 1.0f, 1000.0f), 0.0f), new RVec3(0.0f, -1.0f, 0.0f), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
		floor.setFriction(1.0f);
		mBodyInterface.addBody(floor.getId(), EActivation.DontActivate);

		// Load a race track to have something to assess speed and steering behavior
		LoadRaceTrack("Assets/Racetracks/Zandvoort.csv");
	}
	else if (sSceneName.equals( "Flat With Slope")  )
	{
		final float cSlopeStartDistance = 100.0f;
		final float cSlopeLength = 100.0f;
		final float cSlopeAngle = degreesToRadians(30.0f);

		// Flat test floor
		Body floor = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(new Vec3(1000.0f, 1.0f, 1000.0f), 0.0f), new RVec3(0.0f, -1.0f, 0.0f), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
		floor.setFriction(1.0f);
		mBodyInterface.addBody(floor.getId(), EActivation.DontActivate);

		Body slope_up = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(new Vec3(25.0f, 1.0f, cSlopeLength), 0.0f), new RVec3(0.0f, cSlopeLength * sin(cSlopeAngle) - 1.0f, cSlopeStartDistance + cSlopeLength * cos(cSlopeAngle)), Quat.sRotation(Vec3.sAxisX(), -cSlopeAngle), EMotionType.Static, Layers.NON_MOVING));
		slope_up.setFriction(1.0f);
		mBodyInterface.addBody(slope_up.getId(), EActivation.DontActivate);

		Body slope_down = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(new Vec3(25.0f, 1.0f, cSlopeLength), 0.0f), new RVec3(0.0f, cSlopeLength * sin(cSlopeAngle) - 1.0f, cSlopeStartDistance + 3.0f * cSlopeLength * cos(cSlopeAngle)), Quat.sRotation(Vec3.sAxisX(), cSlopeAngle), EMotionType.Static, Layers.NON_MOVING));
		slope_down.setFriction(1.0f);
		mBodyInterface.addBody(slope_down.getId(), EActivation.DontActivate);
	}
	else if (sSceneName.equals( "Steep Slope")  )
	{
		// Steep slope test floor (20 degrees = 36% grade)
		Body floor = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(new Vec3(1000.0f, 1.0f, 1000.0f), 0.0f), new RVec3(0.0f, -1.0f, 0.0f), Quat.sRotation(Vec3.sAxisX(), degreesToRadians(-20.0f)), EMotionType.Static, Layers.NON_MOVING));
		floor.setFriction(1.0f);
		mBodyInterface.addBody(floor.getId(), EActivation.DontActivate);
	}
	else if (sSceneName.equals( "Step")  )
	{
		// Flat test floor
		Body floor = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(new Vec3(1000.0f, 1.0f, 1000.0f), 0.0f), new RVec3(0.0f, -1.0f, 0.0f), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
		floor.setFriction(1.0f);
		mBodyInterface.addBody(floor.getId(), EActivation.DontActivate);

		// A 5cm step rotated under an angle
		final float cStepHeight = 0.05f;
		Body step = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(new Vec3(5.0f, 0.5f * cStepHeight, 5.0f), 0.0f), new RVec3(-2.0f, 0.5f * cStepHeight, 60.0f), Quat.sRotation(Vec3.sAxisY(), -0.3f * JPH_PI), EMotionType.Static, Layers.NON_MOVING));
		step.setFriction(1.0f);
		mBodyInterface.addBody(step.getId(), EActivation.DontActivate);
	}
	else if (sSceneName.equals( "Dynamic Step")  )
	{
		// Flat test floor
		Body floor = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(new Vec3(1000.0f, 1.0f, 1000.0f), 0.0f), new RVec3(0.0f, -1.0f, 0.0f), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
		floor.setFriction(1.0f);
		mBodyInterface.addBody(floor.getId(), EActivation.DontActivate);

		// A dynamic body that acts as a step to test sleeping behavior
		final float cStepHeight = 0.05f;
		Body step = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(new Vec3(15.0f, 0.5f * cStepHeight, 15.0f), 0.0f), new RVec3(-2.0f, 0.5f * cStepHeight, 30.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		step.setFriction(1.0f);
		mBodyInterface.addBody(step.getId(), EActivation.Activate);
	}
	else if (sSceneName.equals( "Playground")  )
	{
		// Scene with hilly terrain and some objects to drive into
		Body floor = CreateMeshTerrain();
		floor.setFriction(1.0f);

		CreateBridge();

		CreateWall();

		CreateRubble();
	}
	else if (sSceneName.equals( "Loop")  )
	{
		CreateFloor();

		List<Triangle> triangles = new ArrayList<>(800);
		final int cNumSegments = 100;
		final float cLoopWidth = 20.0f;
		final float cLoopRadius = 20.0f;
		final float cLoopThickness = 0.5f;
		Vec3 prev_center = Vec3.sZero();
		Vec3 prev_center_bottom = Vec3.sZero();
		for (int i = 0; i < cNumSegments; ++i)
		{
			float angle = i * 2.0f * JPH_PI / (cNumSegments - 1);
			Vec3 radial=new Vec3(0, -cos(angle), sin(angle));
			Vec3 center = plus(new Vec3(-i * cLoopWidth / (cNumSegments - 1), cLoopRadius, cLoopRadius) , star(cLoopRadius , radial));
			Vec3 half_width=new Vec3(0.5f * cLoopWidth, 0, 0);
			Vec3 center_bottom = plus(center , star(cLoopThickness , radial));
			if (i > 0)
			{
				// Top surface
				triangles.add(new Triangle(plus(prev_center , half_width), minus(prev_center , half_width), minus(center , half_width)));
				triangles.add(new Triangle(plus(prev_center , half_width), minus(center , half_width), plus(center , half_width)));

				// Bottom surface
				triangles.add(new Triangle(plus(prev_center_bottom , half_width), minus(center_bottom , half_width), minus(prev_center_bottom , half_width)));
				triangles.add(new Triangle(plus(prev_center_bottom , half_width), plus(center_bottom , half_width), minus(center_bottom , half_width)));

				// Sides
				triangles.add(new Triangle(plus(prev_center , half_width), plus(center , half_width), plus(prev_center_bottom , half_width)));
				triangles.add(new Triangle(plus(prev_center_bottom , half_width), plus(center , half_width), plus(center_bottom , half_width)));
				triangles.add(new Triangle(minus(prev_center , half_width), minus(prev_center_bottom , half_width), minus(center , half_width)));
				triangles.add(new Triangle(minus(prev_center_bottom , half_width), minus(center_bottom , half_width), minus(center , half_width)));
			}
			prev_center = center;
			prev_center_bottom = center_bottom;
		}
		MeshShapeSettings mesh = new MeshShapeSettings(triangles);
		mesh.setEmbedded();

		Body loop = mBodyInterface.createBody(new BodyCreationSettings(mesh, RVec3.sZero(), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));
		loop.setFriction(1.0f);
		mBodyInterface.addBody(loop.getId(), EActivation.Activate);
	}
        else if (supportsObjectStream())
	{
		// Load scene
		PhysicsSceneRef scene=new PhysicsSceneRef();
		if (!ObjectStreamIn.sReadObject("Assets/" + sSceneName + ".bof", scene))
			FatalError("Failed to load scene");
		for (BodyCreationSettings body : scene.getBodies())
			body.setObjectLayer(Layers.NON_MOVING);
		scene.fixInvalidScales();
		scene.createBodies(mPhysicsSystem);
	}
}

void CreateBridge()
{
	final int cChainLength = 20;

	// Build a collision group filter that disables collision between adjacent bodies
	GroupFilterTableRef group_filter = new GroupFilterTable(cChainLength).toRef();
	for (int i = 0; i < cChainLength - 1; ++i)
		group_filter.disableCollision(i, i + 1);

	Vec3 part_half_size =new Vec3(2.5f, 0.25f, 1.0f);
	ShapeRefC part_shape = new BoxShape(part_half_size).toRefC();

	Vec3 large_part_half_size =new Vec3(2.5f, 0.25f, 22.5f);
	ShapeRefC large_part_shape = new BoxShape(large_part_half_size).toRefC();

	Quat first_part_rot = Quat.sRotation(Vec3.sAxisX(), degreesToRadians(-10.0f));

	RVec3 prev_pos=new RVec3(-25, 7, 0);
	Body prev_part = null;

	for (int i = 0; i < cChainLength; ++i)
	{
		RVec3 pos = plus(prev_pos , new Vec3(0, 0, 2.0f * part_half_size.getZ()));

		Body part = i == 0? mBodyInterface.createBody(new BodyCreationSettings(large_part_shape, minus(pos , star(first_part_rot , new Vec3(0, large_part_half_size.getY() - part_half_size.getY(), large_part_half_size.getZ() - part_half_size.getZ()))), first_part_rot, EMotionType.Static, Layers.NON_MOVING))
					: mBodyInterface.createBody(new BodyCreationSettings(part_shape, pos, Quat.sIdentity(), i == 19? EMotionType.Static : EMotionType.Dynamic, i == 19? Layers.NON_MOVING : Layers.MOVING));
		part.setCollisionGroup(new CollisionGroup(group_filter, 1, (i)));
		part.setFriction(1.0f);
		mBodyInterface.addBody(part.getId(), EActivation.Activate);

		if (prev_part != nullptr)
		{
			DistanceConstraintSettings dc=new DistanceConstraintSettings();
			dc.setPoint1 ( plus(prev_pos , new Vec3(-part_half_size.getX(), 0, part_half_size.getZ())));
			dc.setPoint2 ( plus(pos , new Vec3(-part_half_size.getX(), 0, -part_half_size.getZ())));
			mPhysicsSystem.addConstraint(dc.create(prev_part, part));

			dc.setPoint1 ( plus(prev_pos , new Vec3(part_half_size.getX(), 0, part_half_size.getZ())));
			dc.setPoint2 ( plus(pos , new Vec3(part_half_size.getX(), 0, -part_half_size.getZ())));
			mPhysicsSystem.addConstraint(dc.create(prev_part, part));
		}

		prev_part = part;
		prev_pos = pos;
	}
}

void CreateWall()
{
	ShapeRefC box_shape = new BoxShape(new Vec3(0.5f, 0.5f, 0.5f)).toRefC();
	for (int i = 0; i < 3; ++i)
		for (int j = i / 2; j < 5 - (i + 1) / 2; ++j)
		{
			RVec3 position=new RVec3(2.0f + j * 1.0f + ((i & 1)!=0? 0.5f : 0.0f), 2.0f + i * 1.0f, 10.0f);
			mBodyInterface.createAndAddBody(new BodyCreationSettings(box_shape, position, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
		}
}

void CreateRubble()
{
	// Flat and light objects
	ShapeRefC box_shape = new BoxShape(new Vec3(0.5f, 0.1f, 0.5f)).toRefC();
	for (int i = 0; i < 5; ++i)
		for (int j = 0; j < 5; ++j)
		{
			RVec3 position=new RVec3(-5.0f + j, 2.0f + i * 0.2f, 10.0f + 0.5f * i);
			mBodyInterface.createAndAddBody(new BodyCreationSettings(box_shape, position, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
		}


	// Light convex shapes
	DefaultRandomEngine random=new DefaultRandomEngine();
	UniformFloatDistribution hull_size=new UniformFloatDistribution(0.2f, 0.4f);
	for (int i = 0; i < 10; ++i)
		for (int j = 0; j < 10; ++j)
		{
			// Create random points
			List<Vec3Arg> points=new ArrayList<>(20);
			for (int k = 0; k < 20; ++k)
				points.add(star(hull_size.nextFloat(random) , Vec3.sRandom(random)));

			mBodyInterface.createAndAddBody(new BodyCreationSettings(new ConvexHullShapeSettings(points), new RVec3(-5.0f + 0.5f * j, 2.0f, 15.0f + 0.5f * i), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
		}
}

void LoadRaceTrack(String  inFileName)
{
	// Open the track file
	FileInputStream inputStream;try{inputStream = new FileInputStream(inFileName);}catch(IOException e){return;}
        Scanner stream = new Scanner(inputStream);

	// Ignore header line
	String line;
	stream.nextLine();

	// Read coordinates
	class Segment
	{
		Segment(RVec3 c,float l,float r){mCenter=c;mWidthLeft=l;mWidthRight=r;}
		RVec3				mCenter;
		float				mWidthLeft;
		float				mWidthRight;
	};
	List<Segment> segments=new ArrayList<>();
	double x, y;
	float wl, wr;
	char c;
	RVec3 track_center = RVec3.sZero();
	while (true){try
	{
		x = stream.nextDouble();
                stream.next(",");
                y = stream.nextDouble();
                stream.next(",");
		wl = stream.nextFloat();
                stream.next(",");
		wr = stream.nextFloat();
		RVec3 center=new RVec3(x, 0, -y);
		segments.add(new Segment(center, wl, wr));
		plusEquals(track_center , center);
	}catch(NoSuchElementException e){break;}
	}
	if (!segments.isEmpty())
		slashEquals(track_center , (float)segments.size());

	// Convert to line segments
	RVec3 prev_tleft = RVec3.sZero(), prev_tright = RVec3.sZero();
	for (int i = 0; i < segments.size(); ++i)
	{
		Segment segment = segments.get(i);
		Segment next_segment = segments.get((i + 1) % segments.size());

		// Calculate left and right point of the track
		Vec3 fwd =new Vec3(minus(next_segment.mCenter , segment.mCenter));
		Vec3 right = fwd.cross(Vec3.sAxisY()).normalized();
		RVec3 tcenter = minus(segment.mCenter , plus(track_center , new Vec3(0, 0.1f, 0))); // Put a bit above the floor to avoid z fighting
		RVec3 tleft = minus(tcenter , star(right , segment.mWidthLeft));
		RVec3 tright = plus(tcenter , star(right , segment.mWidthRight));
		mTrackData.add(new Line( tleft, tright ));

		// Connect left and right point with the previous left and right point
		if (i > 0)
		{
			mTrackData.add(new Line( prev_tleft, tleft ));
			mTrackData.add(new Line( prev_tright, tright ));
		}

		prev_tleft = tleft;
		prev_tright = tright;
	}
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	// Render the track
	for (Line l : mTrackData)
		mDebugRenderer.drawLine(l.mStart, l.mEnd, Color.sBlack);
}
/*TODO

void VehicleTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI->CreateTextButton(inSubMenu, "Select Scene", [this, inUI]() {
		UIElement *scene_name = inUI->CreateMenu();
		for (uint i = 0; i < size(sScenes); ++i)
			inUI->CreateTextButton(scene_name, sScenes[i], [this, i]() { sSceneName = sScenes[i]; RestartTest(); });
		inUI->ShowMenu(scene_name);
	});
}
*/
}
