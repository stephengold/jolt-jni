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
package testjoltjni.app.samples.character;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.lambda.*;
import com.github.stephengold.joltjni.readonly.*;
import java.util.*;
import testjoltjni.app.samples.*;
import testjoltjni.app.testframework.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.JphMath.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import static com.github.stephengold.joltjni.std.Std.*;

/**
 * A line-for-line Java translation of the Jolt-Physics character-test abstract class.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Character/CharacterBaseTest.cpp
 */
abstract public class CharacterBaseTest extends Test{

abstract RVec3 GetCharacterPosition();
abstract void HandleInput(Vec3Arg inMovementDirection, boolean inJump, boolean inSwitchStance, float inDeltaTime);
static final float cCharacterHeightStanding = 1.35f;
static final float cCharacterRadiusStanding = 0.3f;
static final float cCharacterHeightCrouching = 0.8f;
static final float cCharacterRadiusCrouching = 0.3f;
static final float cInnerShapeFraction = 0.9f;
boolean sControlMovementDuringJump = true;
static float sCharacterSpeed = 6.0f;
static float sJumpSpeed = 4.0f;
ShapeRefC mStandingShape = new ShapeRefC();
ShapeRefC mCrouchingShape = new ShapeRefC();
ShapeRefC mInnerCrouchingShape = new ShapeRefC();
ShapeRefC mInnerStandingShape = new ShapeRefC();
BodyIdVector mRampBlocks = new BodyIdVector();
float mRampBlocksTimeLeft;
int mConveyorBeltBody = cInvalidBodyId;
int mSensorBody = cInvalidBodyId;
CharacterVsCharacterCollisionSimple mCharacterVsCharacterCollision = new CharacterVsCharacterCollisionSimple();
public enum EType{Capsule,Cylinder,Box,Compound};
public EType sShapeType=EType.Capsule;
private float mTime;
private RVec3 mCameraPivot = RVec3.sZero();
private int mRotatingBody = cInvalidBodyId;
private int mRotatingWallBody = cInvalidBodyId;
private int mRotatingAndTranslatingBody = cInvalidBodyId;
private int mSmoothVerticallyMovingBody = cInvalidBodyId;
private int mReversingVerticallyMovingBody = cInvalidBodyId;
private float mReversingVerticallyMovingVelocity = 1.0f;
private int mHorizontallyMovingBody = cInvalidBodyId;
private CharacterRef mAnimatedCharacter=new CharacterRef();
private CharacterVirtualRef mAnimatedCharacterVirtual=new CharacterVirtualRef();
private CharacterVirtualRef mAnimatedCharacterVirtualWithInnerBody=new CharacterVirtualRef();
private Vec3 mControlInput = Vec3.sZero();
private boolean mJump;
private boolean mSwitchStance;
class MovingCorner{int mBodyID;RVec3 mPosition;MovingCorner(int b,RVec3 p){mBodyID=b;mPosition=p;}};
List<MovingCorner> mMovingCorners=new ArrayList<>();
int obcoindex=0;

public static String sScenes[] =
{
	"PerlinMesh",
	"PerlinHeightField",
	"ObstacleCourse",
	"InitiallyIntersecting",
	"InitiallyIntersecting2",
	"Terrain1",
	"Terrain2",
};

public String sSceneName = "ObstacleCourse";

// Scene constants
static final RVec3 cRotatingPosition=new RVec3(-5, 0.15f, 15);
static final Quat cRotatingOrientation = Quat.sIdentity();
static final RVec3 cRotatingWallPosition=new RVec3(5, 1.0f, 25.0f);
static final Quat cRotatingWallOrientation = Quat.sIdentity();
static final RVec3 cRotatingAndTranslatingPosition=new RVec3(-10, 0.15f, 27.5f);
static final Quat cRotatingAndTranslatingOrientation = Quat.sIdentity();
static final RVec3 cSmoothVerticallyMovingPosition=new RVec3(0, 2.0f, 15);
static final Quat cSmoothVerticallyMovingOrientation = Quat.sIdentity();
static final RVec3 cReversingVerticallyMovingPosition=new RVec3(0, 0.15f, 25);
static final Quat cReversingVerticallyMovingOrientation = Quat.sIdentity();
static final RVec3 cHorizontallyMovingPosition=new RVec3(5, 1, 15);
static final Quat cHorizontallyMovingOrientation = Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI);
static final RVec3 cConveyorBeltPosition=new RVec3(-10, 0.15f, 15);
static final RVec3 cRampPosition=new RVec3(15, 2.2f, 15);
static final Quat cRampOrientation = Quat.sRotation(Vec3.sAxisX(), -0.25f * JPH_PI);
static final RVec3 cRampBlocksStart = plus(cRampPosition , new Vec3(-3.0f, 3.0f, 1.5f));
static final Vec3 cRampBlocksDelta =new Vec3(2.0f, 0, 0);
static final float cRampBlocksTime = 5.0f;
static final RVec3 cSmallBumpsPosition=new RVec3(-5.0f, 0, 2.5f);
static final float cSmallBumpHeight = 0.05f;
static final float cSmallBumpWidth = 0.01f;
static final float cSmallBumpDelta = 0.5f;
static final RVec3 cLargeBumpsPosition=new RVec3(-10.0f, 0, 2.5f);
static final float cLargeBumpHeight = 0.3f;
static final float cLargeBumpWidth = 0.1f;
static final float cLargeBumpDelta = 2.0f;
static final RVec3 cStairsPosition=new RVec3(-15.0f, 0, 2.5f);
static final float cStairsStepHeight = 0.3f;
static final RVec3 cMeshStairsPosition=new RVec3(-20.0f, 0, 2.5f);
static final RVec3 cNoStairsPosition=new RVec3(-15.0f, 0, 10.0f);
static final float cNoStairsStepHeight = 0.3f;
static final float cNoStairsStepDelta = 0.05f;
static final RVec3 cMeshNoStairsPosition=new RVec3(-20.0f, 0, 10.0f);
static final RVec3 cMeshWallPosition=new RVec3(-25.0f, 0, -27.0f);
static final float cMeshWallHeight = 3.0f;
static final float cMeshWallWidth = 2.0f;
static final float cMeshWallStepStart = 0.5f;
static final float cMeshWallStepEnd = 4.0f;
static final int cMeshWallSegments = 25;
static final RVec3 cHalfCylinderPosition=new RVec3(5.0f, 0, 8.0f);
static final RVec3 cMeshBoxPosition=new RVec3(30.0f, 1.5f, 5.0f);
static final RVec3 cSensorPosition=new RVec3(30, 0.9f, -5);
static final RVec3 cCornersPosition=new RVec3(-20.0f, 0, -35.0f);
static final RVec3 cMovingCornersPosition=new RVec3(-20.0f, 0, -45.0f);
static final Vec3 cCornersDelta=new Vec3(7.0f, 0, 0);
static final Quat cCornersOrientation = Quat.sRotation(Vec3.sAxisY(), -0.5f * JPH_PI);
static final RVec3 cCharacterPosition=new RVec3(-3.5f, 0, 3.0f);
static final RVec3 cCharacterVirtualPosition=new RVec3(-5.0f, 0, 3.0f);
static final RVec3 cCharacterVirtualWithInnerBodyPosition=new RVec3(-6.5f, 0, 3.0f);
static final Vec3 cCharacterVelocity=new Vec3(0, 0, 2);

public void Cleanup()
{
	if (mAnimatedCharacter.getPtr() != nullptr)
		mAnimatedCharacter.removeFromPhysicsSystem();
}

public void Initialize()
{
	// Create capsule shapes for all stances
	switch (sShapeType)
	{
	case Capsule:
		mStandingShape =new RotatedTranslatedShapeSettings(new Vec3(0, 0.5f * cCharacterHeightStanding + cCharacterRadiusStanding, 0), Quat.sIdentity(), new CapsuleShape(0.5f * cCharacterHeightStanding, cCharacterRadiusStanding)).create().get();
		mCrouchingShape =new RotatedTranslatedShapeSettings(new Vec3(0, 0.5f * cCharacterHeightCrouching + cCharacterRadiusCrouching, 0), Quat.sIdentity(), new CapsuleShape(0.5f * cCharacterHeightCrouching, cCharacterRadiusCrouching)).create().get();
		mInnerStandingShape =new RotatedTranslatedShapeSettings(new Vec3(0, 0.5f * cCharacterHeightStanding + cCharacterRadiusStanding, 0), Quat.sIdentity(), new CapsuleShape(0.5f * cInnerShapeFraction * cCharacterHeightStanding, cInnerShapeFraction * cCharacterRadiusStanding)).create().get();
		mInnerCrouchingShape =new RotatedTranslatedShapeSettings(new Vec3(0, 0.5f * cCharacterHeightCrouching + cCharacterRadiusCrouching, 0), Quat.sIdentity(), new CapsuleShape(0.5f * cInnerShapeFraction * cCharacterHeightCrouching, cInnerShapeFraction * cCharacterRadiusCrouching)).create().get();
		break;

	case Cylinder:
		mStandingShape =new RotatedTranslatedShapeSettings(new Vec3(0, 0.5f * cCharacterHeightStanding + cCharacterRadiusStanding, 0), Quat.sIdentity(), new CylinderShape(0.5f * cCharacterHeightStanding + cCharacterRadiusStanding, cCharacterRadiusStanding)).create().get();
		mCrouchingShape =new RotatedTranslatedShapeSettings(new Vec3(0, 0.5f * cCharacterHeightCrouching + cCharacterRadiusCrouching, 0), Quat.sIdentity(), new CylinderShape(0.5f * cCharacterHeightCrouching + cCharacterRadiusCrouching, cCharacterRadiusCrouching)).create().get();
		mInnerStandingShape =new RotatedTranslatedShapeSettings(new Vec3(0, 0.5f * cCharacterHeightStanding + cCharacterRadiusStanding, 0), Quat.sIdentity(), new CylinderShape(cInnerShapeFraction * (0.5f * cCharacterHeightStanding + cCharacterRadiusStanding), cInnerShapeFraction * cCharacterRadiusStanding)).create().get();
		mInnerCrouchingShape =new RotatedTranslatedShapeSettings(new Vec3(0, 0.5f * cCharacterHeightCrouching + cCharacterRadiusCrouching, 0), Quat.sIdentity(), new CylinderShape(cInnerShapeFraction * (0.5f * cCharacterHeightCrouching + cCharacterRadiusCrouching), cInnerShapeFraction * cCharacterRadiusCrouching)).create().get();
		break;

	case Box:
		mStandingShape =new RotatedTranslatedShapeSettings(new Vec3(0, 0.5f * cCharacterHeightStanding + cCharacterRadiusStanding, 0), Quat.sIdentity(), new BoxShape(new Vec3(cCharacterRadiusStanding, 0.5f * cCharacterHeightStanding + cCharacterRadiusStanding, cCharacterRadiusStanding))).create().get();
		mCrouchingShape =new RotatedTranslatedShapeSettings(new Vec3(0, 0.5f * cCharacterHeightCrouching + cCharacterRadiusCrouching, 0), Quat.sIdentity(), new BoxShape(new Vec3(cCharacterRadiusCrouching, 0.5f * cCharacterHeightCrouching + cCharacterRadiusCrouching, cCharacterRadiusCrouching))).create().get();
		mInnerStandingShape =new RotatedTranslatedShapeSettings(new Vec3(0, 0.5f * cCharacterHeightStanding + cCharacterRadiusStanding, 0), Quat.sIdentity(), new BoxShape(star(cInnerShapeFraction , new Vec3(cCharacterRadiusStanding, 0.5f * cCharacterHeightStanding + cCharacterRadiusStanding, cCharacterRadiusStanding)))).create().get();
		mInnerCrouchingShape =new RotatedTranslatedShapeSettings(new Vec3(0, 0.5f * cCharacterHeightCrouching + cCharacterRadiusCrouching, 0), Quat.sIdentity(), new BoxShape(star(cInnerShapeFraction , new Vec3(cCharacterRadiusCrouching, 0.5f * cCharacterHeightCrouching + cCharacterRadiusCrouching, cCharacterRadiusCrouching)))).create().get();
		break;

	case Compound:
		{
			StaticCompoundShapeSettings standing_compound=new StaticCompoundShapeSettings();
			standing_compound.addShape(new Vec3(-0.3f, 0.5f * cCharacterHeightStanding + cCharacterRadiusStanding, 0), Quat.sIdentity(), new CapsuleShape(0.5f * cCharacterHeightStanding, cCharacterRadiusStanding));
			standing_compound.addShape(new Vec3(0.3f, 0.5f * cCharacterHeightStanding + cCharacterRadiusStanding, 0), Quat.sIdentity(), new BoxShape(new Vec3(cCharacterRadiusStanding, 0.5f * cCharacterHeightStanding + cCharacterRadiusStanding, cCharacterRadiusStanding)));
			mStandingShape = standing_compound.create().get();

			StaticCompoundShapeSettings crouching_compound=new StaticCompoundShapeSettings();
			crouching_compound.addShape(new Vec3(-0.3f, 0.5f * cCharacterHeightCrouching + cCharacterRadiusCrouching, 0), Quat.sIdentity(), new CapsuleShape(0.5f * cCharacterHeightCrouching, cCharacterRadiusCrouching));
			crouching_compound.addShape(new Vec3(0.3f, 0.5f * cCharacterHeightCrouching + cCharacterRadiusCrouching, 0), Quat.sIdentity(), new BoxShape(new Vec3(cCharacterRadiusCrouching, 0.5f * cCharacterHeightCrouching + cCharacterRadiusCrouching, cCharacterRadiusCrouching)));
			mCrouchingShape = crouching_compound.create().get();

			StaticCompoundShapeSettings inner_standing_compound=new StaticCompoundShapeSettings();
			inner_standing_compound.addShape(new Vec3(-0.3f, 0.5f * cCharacterHeightStanding + cCharacterRadiusStanding, 0), Quat.sIdentity(), new CapsuleShape(0.5f * cInnerShapeFraction * cCharacterHeightStanding, cInnerShapeFraction * cCharacterRadiusStanding));
			inner_standing_compound.addShape(new Vec3(0.3f, 0.5f * cCharacterHeightStanding + cCharacterRadiusStanding, 0), Quat.sIdentity(), new BoxShape(star(cInnerShapeFraction ,new Vec3(cCharacterRadiusStanding, 0.5f * cCharacterHeightStanding + cCharacterRadiusStanding, cCharacterRadiusStanding))));
			mInnerStandingShape = inner_standing_compound.create().get();

			StaticCompoundShapeSettings inner_crouching_compound=new StaticCompoundShapeSettings();
			inner_crouching_compound.addShape(new Vec3(-0.3f, 0.5f * cCharacterHeightCrouching + cCharacterRadiusCrouching, 0), Quat.sIdentity(), new CapsuleShape(0.5f * cInnerShapeFraction * cCharacterHeightCrouching, cInnerShapeFraction * cCharacterRadiusCrouching));
			inner_crouching_compound.addShape(new Vec3(0.3f, 0.5f * cCharacterHeightCrouching + cCharacterRadiusCrouching, 0), Quat.sIdentity(), new BoxShape(star(cInnerShapeFraction ,new Vec3(cCharacterRadiusCrouching, 0.5f * cCharacterHeightCrouching + cCharacterRadiusCrouching, cCharacterRadiusCrouching))));
			mInnerCrouchingShape = inner_crouching_compound.create().get();
		}
		break;
	}

	if (strcmp(sSceneName, "PerlinMesh") == 0)
	{
		// Default terrain
		CreateMeshTerrain();
	}
	else if (strcmp(sSceneName, "PerlinHeightField") == 0)
	{
		// Default terrain
		CreateHeightFieldTerrain();
	}
	else if (strcmp(sSceneName, "InitiallyIntersecting") == 0)
	{
		CreateFloor();

		// Create a grid of boxes that are initially intersecting with the character
		ShapeRefC box = new BoxShape(new Vec3(0.1f, 0.1f, 0.1f)).toRefC();
		BodyCreationSettings settings=new BodyCreationSettings(box, new RVec3(0, 0.5f, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
		for (int x = 0; x < 4; ++x)
			for (int y = 0; y <= 10; ++y)
				for (int z = 0; z <= 10; ++z)
				{
					settings.setPosition (new RVec3(-0.5f + 0.1f * x, 0.1f + 0.1f * y, -0.5f + 0.1f * z));
					mBodyInterface.createAndAddBody(settings, EActivation.DontActivate);
				}
	}
	else if (strcmp(sSceneName, "InitiallyIntersecting2") == 0)
	{
		CreateFloor();

		// Create two very steep sloped floors that are initially intersecting with the character
		ShapeRefC box = new BoxShape(new Vec3(1, 0.1f, 1)).toRefC();
		BodyCreationSettings settings=new BodyCreationSettings(box,new RVec3(0, 0, 0), Quat.sRotation(Vec3.sAxisZ(), degreesToRadians(75.0f)), EMotionType.Static, Layers.NON_MOVING);
		mBodyInterface.createAndAddBody(settings, EActivation.DontActivate);
		settings.setRotation ( Quat.sRotation(Vec3.sAxisZ(), degreesToRadians(-75.0f)));
		mBodyInterface.createAndAddBody(settings, EActivation.DontActivate);
	}
	else if (strcmp(sSceneName, "ObstacleCourse") == 0)
	{
		// Default terrain
		CreateFloor(1000.0f);

		{
			// Create ramps with different inclinations
			ShapeRefC ramp =new RotatedTranslatedShapeSettings(new Vec3(0, 0, -2.5f), Quat.sIdentity(), new BoxShape(new Vec3(1.0f, 0.05f, 2.5f))).create().get();
			for (int angle = 0; angle < 18; ++angle)
				mBodyInterface.createAndAddBody(new BodyCreationSettings(ramp, new RVec3(-15.0f + angle * 2.0f, 0, -10.0f), Quat.sRotation(Vec3.sAxisX(), degreesToRadians(10.0f * angle)), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
		}

		{
			// Create ramps with different inclinations intersecting with a steep slope
			ShapeRefC ramp =new RotatedTranslatedShapeSettings(new Vec3(0, 0, -2.5f), Quat.sIdentity(), new BoxShape(new Vec3(1.0f, 0.05f, 2.5f))).create().get();
			ShapeRefC ramp2 =new RotatedTranslatedShapeSettings(new Vec3(0, 2.0f, 0), Quat.sIdentity(), new BoxShape(new Vec3(0.05f, 2.0f, 1.0f))).create().get();
			for (int angle = 0; angle < 9; ++angle)
			{
				mBodyInterface.createAndAddBody(new BodyCreationSettings(ramp, new RVec3(-15.0f + angle * 2.0f, 0, -20.0f - angle * 0.1f), Quat.sRotation(Vec3.sAxisX(), degreesToRadians(10.0f * angle)), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
				mBodyInterface.createAndAddBody(new BodyCreationSettings(ramp2, new RVec3(-15.0f + angle * 2.0f, 0, -21.0f), Quat.sRotation(Vec3.sAxisZ(), degreesToRadians(20.0f)), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
			}
		}

		{
			// Create wall consisting of vertical pillars
			// Note: Convex radius 0 because otherwise it will be a bumpy wall
			ShapeRef wall = new BoxShape(new Vec3(0.1f, 2.5f, 0.1f), 0.0f).toRef();
			for (int z = 0; z < 30; ++z)
				mBodyInterface.createAndAddBody(new BodyCreationSettings(wall, new RVec3(0.0f, 2.5f, 2.0f + 0.2f * z), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
		}

		{
			// Kinematic blocks to test interacting with moving objects
			ShapeRef kinematic = new BoxShape(new Vec3(1, 0.15f, 3.0f)).toRef();
			mRotatingBody = mBodyInterface.createAndAddBody(new BodyCreationSettings(kinematic, cRotatingPosition, cRotatingOrientation, EMotionType.Kinematic, Layers.MOVING), EActivation.Activate);
			mRotatingWallBody = mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(3.0f, 1, 0.15f)), cRotatingWallPosition, cRotatingWallOrientation, EMotionType.Kinematic, Layers.MOVING), EActivation.Activate);
			mRotatingAndTranslatingBody = mBodyInterface.createAndAddBody(new BodyCreationSettings(kinematic, cRotatingAndTranslatingPosition, cRotatingAndTranslatingOrientation, EMotionType.Kinematic, Layers.MOVING), EActivation.Activate);
			mSmoothVerticallyMovingBody = mBodyInterface.createAndAddBody(new BodyCreationSettings(kinematic, cSmoothVerticallyMovingPosition, cSmoothVerticallyMovingOrientation, EMotionType.Kinematic, Layers.MOVING), EActivation.Activate);
			mReversingVerticallyMovingBody = mBodyInterface.createAndAddBody(new BodyCreationSettings(kinematic, cReversingVerticallyMovingPosition, cReversingVerticallyMovingOrientation, EMotionType.Kinematic, Layers.MOVING), EActivation.Activate);
			mHorizontallyMovingBody = mBodyInterface.createAndAddBody(new BodyCreationSettings(kinematic, cHorizontallyMovingPosition, cHorizontallyMovingOrientation, EMotionType.Kinematic, Layers.MOVING), EActivation.Activate);
		}

		{
			// Conveyor belt (only works with virtual character)
			mConveyorBeltBody = mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(1, 0.15f, 3.0f)), cConveyorBeltPosition, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.Activate);
		}

		{
			// A rolling sphere towards the player
			BodyCreationSettings bcs=new BodyCreationSettings(new SphereShape(0.2f), new RVec3(0.0f, 0.2f, -1.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
			bcs.setLinearVelocity ( new Vec3(0, 0, 2.0f));
			bcs.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
			bcs.getMassPropertiesOverride().setMass ( 10.0f);
			mBodyInterface.createAndAddBody(bcs, EActivation.Activate);
		}

		{
			// Dynamic blocks to test player pushing blocks
			ShapeRef block = new BoxShape(Vec3.sReplicate(0.5f)).toRef();
			for (int y = 0; y < 3; ++y)
			{
				BodyCreationSettings bcs=new BodyCreationSettings(block, new RVec3(5.0f, 0.5f + (float)(y), 0.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
				bcs.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
				bcs.getMassPropertiesOverride().setMass ( 10.0f);
				mBodyInterface.createAndAddBody(bcs, EActivation.DontActivate);
			}
		}

		{
			// Dynamic block on a static step (to test pushing block on stairs)
			mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(0.5f, 0.15f, 0.5f)), new RVec3(10.0f, 0.15f, 0.0f), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
			BodyCreationSettings bcs=new BodyCreationSettings(new BoxShape(Vec3.sReplicate(0.5f)), new RVec3(10.0f, 0.8f, 0.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
			bcs.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
			bcs.getMassPropertiesOverride().setMass ( 10.0f);
			mBodyInterface.createAndAddBody(bcs, EActivation.DontActivate);
		}

		{
			// Dynamic spheres to test player pushing stuff you can step on
			float h = 0.0f;
			for (int y = 0; y < 3; ++y)
			{
				float r = 0.4f - 0.1f * y;
				h += r;
				BodyCreationSettings bcs=new BodyCreationSettings(new SphereShape(r), new RVec3(15.0f, h, 0.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
				h += r;
				bcs.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
				bcs.getMassPropertiesOverride().setMass ( 10.0f);
				mBodyInterface.createAndAddBody(bcs, EActivation.DontActivate);
			}
		}

		{
			// A seesaw to test character gravity
			int b1 = mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(1.0f, 0.2f, 0.05f)), new RVec3(20.0f, 0.2f, 0.0f), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
			BodyCreationSettings bcs=new BodyCreationSettings(new BoxShape(new Vec3(1.0f, 0.05f, 5.0f)), new RVec3(20.0f, 0.45f, 0.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
			bcs.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
			bcs.getMassPropertiesOverride().setMass ( 10.0f);
			int b2 = mBodyInterface.createAndAddBody(bcs, EActivation.Activate);

			// Connect the parts with a hinge
			HingeConstraintSettings hinge=new HingeConstraintSettings();
			hinge.setPoint1 (  hinge.setPoint2 ( new RVec3(20.0f, 0.4f, 0.0f)));
			hinge.setHingeAxis1 ( hinge.setHingeAxis2 ( Vec3.sAxisX()));
			mPhysicsSystem.addConstraint(mBodyInterface.createConstraint(hinge, b1, b2));
		}

		{
			// A board above the character to crouch and jump up against
			float h = 0.5f * cCharacterHeightCrouching + cCharacterRadiusCrouching + 0.1f;
			for (int x = 0; x < 2; ++x)
				mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(1.0f, h, 0.05f)), new RVec3(25.0f, h, x == 0? -0.95f : 0.95f), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
			BodyCreationSettings bcs=new BodyCreationSettings(new BoxShape(new Vec3(1.0f, 0.05f, 1.0f)), new RVec3(25.0f, 2.0f * h + 0.05f, 0.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
			bcs.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
			bcs.getMassPropertiesOverride().setMass ( 10.0f);
			mBodyInterface.createAndAddBody(bcs, EActivation.Activate);
		}

		{
			// A floating static block
			mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(0.5f)), new RVec3(30.0f, 1.5f, 0.0f), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
		}

		{
			// Create ramp
			BodyCreationSettings ramp=new BodyCreationSettings(new BoxShape(new Vec3(4.0f, 0.1f, 3.0f)), cRampPosition, cRampOrientation, EMotionType.Static, Layers.NON_MOVING);
			mBodyInterface.createAndAddBody(ramp, EActivation.DontActivate);

			// Create blocks on ramp
			ShapeRef block = new BoxShape(Vec3.sReplicate(0.5f)).toRef();
			BodyCreationSettings bcs=new BodyCreationSettings(block, cRampBlocksStart, cRampOrientation, EMotionType.Dynamic, Layers.MOVING);
			bcs.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
			bcs.getMassPropertiesOverride().setMass ( 10.0f);
			for (int i = 0; i < 4; ++i)
			{
				mRampBlocks.pushBack(mBodyInterface.createAndAddBody(bcs, EActivation.Activate));
				bcs.setPosition(plus(bcs.getPosition() , cRampBlocksDelta));
			}
		}

		// Create three funnels with walls that are too steep to climb
		ShapeRef funnel = new BoxShape(new Vec3(0.1f, 1.0f, 1.0f)).toRef();
		for (int i = 0; i < 2; ++i)
		{
			Quat rotation = Quat.sRotation(Vec3.sAxisY(), JPH_PI * i);
			mBodyInterface.createAndAddBody(new BodyCreationSettings(funnel, plus(new RVec3(5.0f, 0.1f, 5.0f) , star(rotation , new Vec3(0.2f, 0, 0))), star(rotation , Quat.sRotation(Vec3.sAxisZ(), -degreesToRadians(40.0f))), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
		}
		for (int i = 0; i < 3; ++i)
		{
			Quat rotation = Quat.sRotation(Vec3.sAxisY(), 2.0f / 3.0f * JPH_PI * i);
			mBodyInterface.createAndAddBody(new BodyCreationSettings(funnel, plus(new RVec3(7.5f, 0.1f, 5.0f) , star(rotation , new Vec3(0.2f, 0, 0))), star(rotation , Quat.sRotation(Vec3.sAxisZ(), -degreesToRadians(40.0f))), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
		}
		for (int i = 0; i < 4; ++i)
		{
			Quat rotation = Quat.sRotation(Vec3.sAxisY(), 0.5f * JPH_PI * i);
			mBodyInterface.createAndAddBody(new BodyCreationSettings(funnel, plus(new RVec3(10.0f, 0.1f, 5.0f) , star(rotation , new Vec3(0.2f, 0, 0))), star(rotation , Quat.sRotation(Vec3.sAxisZ(), -degreesToRadians(40.0f))), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
		}

		// Create mesh with a steep slope and an acute angle with triangles that face downwards
		{
			TriangleList triangles=new TriangleList();
			triangles.pushBack(new Triangle(new Vec3(-0.5f, 0, 0),new  Vec3(0.5f, 0, 0),new  Vec3(-0.5f, 1.1f, -1)));
			triangles.pushBack(new Triangle(new Vec3(0.5f, 0, 0),new  Vec3(0.5f, 1.1f, -1),new  Vec3(-0.5f, 1.1f, -1)));
			triangles.pushBack(new Triangle(new Vec3(-0.5f, 0, 0),new  Vec3(-0.5f, 0, -1),new  Vec3(0.5f, 0, 0)));
			triangles.pushBack(new Triangle(new Vec3(0.5f, 0, 0),new  Vec3(-0.5f, 0, -1),new  Vec3(0.5f, 0, -1)));

			MeshShapeSettings mesh=new MeshShapeSettings(triangles);
			mesh.setEmbedded();
			BodyCreationSettings body=new BodyCreationSettings(mesh,new RVec3(12.5f, 0.5f, 4.0f), Quat.sRotation(Vec3.sAxisY(), JPH_PI), EMotionType.Static, Layers.NON_MOVING);
			mBodyInterface.createAndAddBody(body, EActivation.DontActivate);
		}

		// Create small bumps
		{
			BodyCreationSettings step=new BodyCreationSettings(new BoxShape(new Vec3(2.0f, 0.5f * cSmallBumpHeight, 0.5f * cSmallBumpWidth), 0.0f), RVec3.sZero(), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
			for (int i = 0; i < 10; ++i)
			{
				step.setPosition ( plus(cSmallBumpsPosition , new Vec3(0, 0.5f * cSmallBumpHeight, cSmallBumpDelta * i)));
				mBodyInterface.createAndAddBody(step, EActivation.DontActivate);
			}
		}

		// Create large bumps
		{
			BodyCreationSettings step=new BodyCreationSettings(new BoxShape(new Vec3(2.0f, 0.5f * cLargeBumpHeight, 0.5f * cLargeBumpWidth)), RVec3.sZero(), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
			for (int i = 0; i < 5; ++i)
			{
				step.setPosition ( plus(cLargeBumpsPosition , new Vec3(0, 0.5f * cLargeBumpHeight, cLargeBumpDelta * i)));
				mBodyInterface.createAndAddBody(step, EActivation.DontActivate);
			}
		}

		// Create stairs
		{
			BodyCreationSettings step = new BodyCreationSettings(new BoxShape(new Vec3(2.0f, 0.5f * cStairsStepHeight, 0.5f * cStairsStepHeight)), RVec3.sZero(), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
			for (int i = 0; i < 10; ++i)
			{
				step.setPosition ( plus(cStairsPosition , new Vec3(0, cStairsStepHeight * (0.5f + i), cStairsStepHeight * i)));
				mBodyInterface.createAndAddBody(step, EActivation.DontActivate);
			}
		}

		// A wall beside the stairs
		mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(0.5f, 2.0f, 5.0f * cStairsStepHeight)), plus(cStairsPosition , new Vec3(-2.5f, 2.0f, 5.0f * cStairsStepHeight)), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

		// Create stairs from triangles
		{
			TriangleList triangles=new TriangleList();

			float rear_z = 10 * cStairsStepHeight;

			for (int i = 0; i < 10; ++i)
			{
				// Start of step
				Vec3 base=new Vec3(0, cStairsStepHeight * i, cStairsStepHeight * i);

				// Left side
				Vec3 b1 = plus(base , new Vec3(2.0f, 0, 0));
				Vec3 s1 = plus(b1 , new Vec3(0, cStairsStepHeight, 0));
				Vec3 p1 = plus(s1 , new Vec3(0, 0, cStairsStepHeight));

				// Right side
				Vec3 width=new Vec3(-4.0f, 0, 0);
				Vec3 b2 = plus(b1 , width);
				Vec3 s2 = plus(s1 , width);
				Vec3 p2 = plus(p1 , width);

				triangles.pushBack(new Triangle(s1, b1, s2));
				triangles.pushBack(new Triangle(b1, b2, s2));
				triangles.pushBack(new Triangle(s1, p2, p1));
				triangles.pushBack(new Triangle(s1, s2, p2));

				// Side of stairs
				Vec3 rb2 = b2; rb2.setZ(rear_z);
				Vec3 rs2 = s2; rs2.setZ(rear_z);

				triangles.pushBack(new Triangle(s2, b2, rs2));
				triangles.pushBack(new Triangle(rs2, b2, rb2));
			}

			MeshShapeSettings mesh=new MeshShapeSettings(triangles);
			mesh.setEmbedded();
			BodyCreationSettings mesh_stairs=new BodyCreationSettings(mesh, cMeshStairsPosition, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
			mBodyInterface.createAndAddBody(mesh_stairs, EActivation.DontActivate);
		}

		// A wall to the side and behind the stairs
		mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(0.5f, 2.0f, 0.25f)), plus(cStairsPosition , new Vec3(-7.5f, 2.0f, 10.0f * cStairsStepHeight + 0.25f)), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

		// Create stairs with too little space between the steps
		{
			BodyCreationSettings step=new BodyCreationSettings(new BoxShape(new Vec3(2.0f, 0.5f * cNoStairsStepHeight, 0.5f * cNoStairsStepHeight)), RVec3.sZero(), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
			for (int i = 0; i < 10; ++i)
			{
				step.setPosition ( plus(cNoStairsPosition , new Vec3(0, cNoStairsStepHeight * (0.5f + i), cNoStairsStepDelta * i)));
				mBodyInterface.createAndAddBody(step, EActivation.DontActivate);
			}
		}

		// Create stairs with too little space between the steps consisting of triangles
		{
			TriangleList triangles=new TriangleList();

			for (int i = 0; i < 10; ++i)
			{
				// Start of step
				Vec3 base=new Vec3(0, cStairsStepHeight * i, cNoStairsStepDelta * i);

				// Left side
				Vec3 b1 = minus(base , new Vec3(2.0f, 0, 0));
				Vec3 s1 = plus(b1 , new Vec3(0, cStairsStepHeight, 0));
				Vec3 p1 = plus(s1 , new Vec3(0, 0, cNoStairsStepDelta));

				// Right side
				Vec3 width=new Vec3(4.0f, 0, 0);
				Vec3 b2 = plus(b1 , width);
				Vec3 s2 = plus(s1 , width);
				Vec3 p2 = plus(p1 , width);

				triangles.pushBack(new Triangle(s1, s2, b1));
				triangles.pushBack(new Triangle(b1, s2, b2));
				triangles.pushBack(new Triangle(s1, p1, p2));
				triangles.pushBack(new Triangle(s1, p2, s2));
			}

			MeshShapeSettings mesh=new MeshShapeSettings(triangles);
			mesh.setEmbedded();
			BodyCreationSettings mesh_stairs=new BodyCreationSettings(mesh, cMeshNoStairsPosition, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
			mBodyInterface.createAndAddBody(mesh_stairs, EActivation.DontActivate);
		}

		// Create mesh with walls at varying angles
		{
			TriangleList triangles=new TriangleList();
			Vec3 p1=new Vec3(0.5f * cMeshWallWidth, 0, 0);
			Vec3 h=new Vec3(0, cMeshWallHeight, 0);
			for (int i = 0; i < cMeshWallSegments; ++i)
			{
				float delta = cMeshWallStepStart + i * (cMeshWallStepEnd - cMeshWallStepStart) / (cMeshWallSegments - 1);
				Vec3 p2 =new Vec3(((i & 1) != 0x0)? 0.5f * cMeshWallWidth : -0.5f * cMeshWallWidth, 0, p1.getZ() + delta);
				triangles.pushBack(new Triangle(p1, plus(p1 , h), plus(p2 , h)));
				triangles.pushBack(new Triangle(p1, plus(p2 , h), p2));
				p1 = p2;
			}

			MeshShapeSettings mesh=new MeshShapeSettings(triangles);
			mesh.setEmbedded();
			BodyCreationSettings wall=new BodyCreationSettings(mesh, cMeshWallPosition, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
			mBodyInterface.createAndAddBody(wall, EActivation.DontActivate);
		}

		// Create a half cylinder with caps for testing contact point limit
		{
			VertexList vertices=new VertexList();
			IndexedTriangleList triangles=new IndexedTriangleList();

			// The half cylinder
			final int cPosSegments = 2;
			final int cAngleSegments = 512;
			final float cCylinderLength = 2.0f;
			for (int pos = 0; pos < cPosSegments; ++pos)
				for (int angle = 0; angle < cAngleSegments; ++angle)
				{
					int start = (int)vertices.size();

					float radius = cCharacterRadiusStanding + 0.05f;
					float angle_rad = (-0.5f + (float)(angle) / cAngleSegments) * JPH_PI;
					float s = sin(angle_rad);
					float c = cos(angle_rad);
					float x = cCylinderLength * (-0.5f + (float)(pos) / (cPosSegments - 1));
					float y = angle == 0 || angle == cAngleSegments - 1? 0.5f : (1.0f - c) * radius;
					float z = s * radius;
					vertices.pushBack(x, y, z);

					if (pos > 0 && angle > 0)
					{
						triangles.pushBack(new IndexedTriangle(start, start - 1, start - cAngleSegments));
						triangles.pushBack(new IndexedTriangle(start - 1, start - cAngleSegments - 1, start - cAngleSegments));
					}
				}

			// Add end caps
			int end = cAngleSegments * (cPosSegments - 1);
			for (int angle = 0; angle < cAngleSegments - 1; ++angle)
			{
				triangles.pushBack(new IndexedTriangle(0, angle + 1, angle));
				triangles.pushBack(new IndexedTriangle(end, end + angle, end + angle + 1));
			}

			MeshShapeSettings mesh=new MeshShapeSettings(vertices, triangles);
			mesh.setEmbedded();
			BodyCreationSettings mesh_cylinder=new BodyCreationSettings(mesh, cHalfCylinderPosition, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
			mBodyInterface.createAndAddBody(mesh_cylinder, EActivation.DontActivate);
		}

		// Create a box made out of polygons (character should not get stuck behind back facing side)
		{
			ConstFloat3[] vertices = {
				new Float3(-1,  1, -1),
				new Float3( 1,  1, -1),
				new Float3( 1,  1,  1),
				new Float3(-1,  1,  1),
				new Float3(-1, -1, -1),
				new Float3( 1, -1, -1),
				new Float3( 1, -1,  1),
				new Float3(-1, -1,  1)
			};

			IndexedTriangle[] triangles = {
				new IndexedTriangle(0, 3, 2),
				new IndexedTriangle(0, 2, 1),
				new IndexedTriangle(4, 5, 6),
				new IndexedTriangle(4, 6, 7),
				new IndexedTriangle(0, 4, 3),
				new IndexedTriangle(3, 4, 7),
				new IndexedTriangle(2, 6, 5),
				new IndexedTriangle(2, 5, 1),
				new IndexedTriangle(3, 7, 6),
				new IndexedTriangle(3, 6, 2),
				new IndexedTriangle(0, 1, 5),
				new IndexedTriangle(0, 5, 4)
			};

			MeshShapeSettings mesh=new MeshShapeSettings(vertices, triangles);
			mesh.setEmbedded();
			BodyCreationSettings box=new BodyCreationSettings(mesh, cMeshBoxPosition, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
			mBodyInterface.createAndAddBody(box, EActivation.DontActivate);
		}

		// Create a sensor
		{
			BodyCreationSettings sensor=new BodyCreationSettings(new BoxShape(Vec3.sReplicate(1.0f)), cSensorPosition, Quat.sIdentity(), EMotionType.Kinematic, Layers.SENSOR);
			sensor.setIsSensor ( true);
			mSensorBody = mBodyInterface.createAndAddBody(sensor, EActivation.Activate);
		}

		// Create Character
		{
			CharacterSettings settings=new CharacterSettings();
			settings.setLayer ( Layers.MOVING);
			settings.setShape ( mStandingShape);
			settings.setSupportingVolume (new Plane(Vec3.sAxisY(), -cCharacterRadiusStanding)); // Accept contacts that touch the lower sphere of the capsule
			mAnimatedCharacter = new com.github.stephengold.joltjni.Character(settings, cCharacterPosition, Quat.sIdentity(), 0, mPhysicsSystem).toRef();
			mAnimatedCharacter.addToPhysicsSystem();
		}

		// Create CharacterVirtual
		{
			CharacterVirtualSettings settings=new CharacterVirtualSettings();
			settings.setShape ( mStandingShape);
			settings.setSupportingVolume (new Plane(Vec3.sAxisY(), -cCharacterRadiusStanding)); // Accept contacts that touch the lower sphere of the capsule
			mAnimatedCharacterVirtual = new CharacterVirtual(settings, cCharacterVirtualPosition, Quat.sIdentity(), 0, mPhysicsSystem).toRef();
			mAnimatedCharacterVirtual.getPtr().setCharacterVsCharacterCollision(mCharacterVsCharacterCollision);
			mCharacterVsCharacterCollision.add(mAnimatedCharacterVirtual.getPtr());
		}

		// Create CharacterVirtual with inner rigid body
		{
			CharacterVirtualSettings settings=new CharacterVirtualSettings();
			settings.setShape ( mStandingShape);
			settings.setInnerBodyShape ( mInnerStandingShape);
			settings.setSupportingVolume (new Plane(Vec3.sAxisY(), -cCharacterRadiusStanding)); // Accept contacts that touch the lower sphere of the capsule
			mAnimatedCharacterVirtualWithInnerBody = new CharacterVirtual(settings, cCharacterVirtualWithInnerBodyPosition, Quat.sIdentity(), 0, mPhysicsSystem).toRef();
			mAnimatedCharacterVirtualWithInnerBody.getPtr().setCharacterVsCharacterCollision(mCharacterVsCharacterCollision);
			mCharacterVsCharacterCollision.add(mAnimatedCharacterVirtualWithInnerBody.getPtr());
		}

		// Create corners and slopes that used to make CharacterVirtual jitter (see also the TestJumpIntoCorner, TestWalkIntoCornerOnSlope and TestWalkUpSlopeIntoWall unit tests).
		// Each configuration is a single shape so that it can be moved as a whole. We create a static and a moving version of each.
		// In local space the character should approach them from +X, the shapes are rotated so that they open towards the player starting position.
		{
			// Add two vertical walls that meet at inApex with inCornerAngle between them, the corner opens towards +X
			final TriConsumer<TriangleList,Vec3Arg,Float>add_corner_walls = ( ioTriangles,  inApex,  inCornerAngle)->
			{
				final float cWallLength = 3.5f;
				Vec3 bottom = minus(inApex ,new Vec3(0, 1.5f, 0)), top = plus(inApex ,new Vec3(0, 3.0f, 0));
				float half_angle = 0.5f * inCornerAngle;
				Vec3 end = star(cWallLength ,new Vec3(cos(half_angle), 0, sin(half_angle)));
				ioTriangles.pushBack(new Triangle(bottom, top, plus(end , top)));
				ioTriangles.pushBack(new Triangle(bottom, plus(end , top), plus(end , bottom)));
				end.flipSignInPlace(1, 1, -1);
				ioTriangles.pushBack(new Triangle(bottom, plus(end , top), top));
				ioTriangles.pushBack(new Triangle(bottom, plus(end , bottom), plus(end , top)));
			};

			// Add a box to a compound shape. The center of the top surface of the box is at inTopCenter and the box is rotated by inRotation.
			final FiveConsumer<StaticCompoundShapeSettings,Vec3Arg,QuatArg,Float,Float>add_slab = ( ioCompound,  inTopCenter,  inRotation,  inHalfLength,  inHalfWidth)->
			{
				final float cHalfThickness = 0.15f;
				ioCompound.addShape(minus(inTopCenter , star(cHalfThickness , star(inRotation , Vec3.sAxisY()))), inRotation, new BoxShape(new Vec3(inHalfLength, cHalfThickness, inHalfWidth), 0.0f));
			};

			// Create a static and a moving body for a compound shape (plus optional walls), inHeight is the height at which to place the bodies
			final TriConsumer<StaticCompoundShapeSettings,TriangleList,Float>create_bodies = (ioCompound, inWalls, inHeight)->
			{
				if (!inWalls.empty())
					ioCompound.addShape(Vec3.sZero(), Quat.sIdentity(),new MeshShapeSettings(inWalls).create().get());
				ShapeRefC shape = ioCompound.create().get();

				Vec3 offset = plus(star((float)(obcoindex++) , cCornersDelta) ,new Vec3(0, inHeight, 0));
				mBodyInterface.createAndAddBody(new BodyCreationSettings(shape, plus(cCornersPosition , offset), cCornersOrientation, EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

				// Create the moving version at the position it will have at time 0
				RVec3 position = plus(cMovingCornersPosition , offset);
				BodyCreationSettings moving=new BodyCreationSettings(shape, plus(position ,new Vec3(0, 0, 2.0f)), cCornersOrientation, EMotionType.Kinematic, Layers.MOVING);
				moving.setOverrideMassProperties ( EOverrideMassProperties.MassAndInertiaProvided); // Mesh shapes have no mass properties
				moving.getMassPropertiesOverride().setMassAndInertiaOfSolidBox(Vec3.sReplicate(5.0f), 1000.0f);
				mMovingCorners.add(new MovingCorner(mBodyInterface.createAndAddBody(moving, EActivation.Activate), position));
			};

			// Walk up a 10 degree slope into a 135 degree corner
			{
				float slope = degreesToRadians(10.0f);
				Quat rotation = Quat.sRotation(Vec3.sAxisZ(), -slope);
				StaticCompoundShapeSettings compound=new StaticCompoundShapeSettings();
				add_slab.accept(compound, star(rotation ,new Vec3(1.5f, 0, 0)), rotation, 2.5f, 3.0f);
				TriangleList walls=new TriangleList();
				add_corner_walls.accept(walls, Vec3.sZero(), degreesToRadians(135.0f));
				create_bodies.accept(compound, walls, 4.0f * sin(slope) + 0.5f);
			}

			// Walk up a 10 degree slope into a wall at an angle so that the character slides along the wall
			{
				float slope = degreesToRadians(10.0f);
				Quat rotation = Quat.sRotation(Vec3.sAxisZ(), -slope);
				StaticCompoundShapeSettings compound=new StaticCompoundShapeSettings();
				add_slab.accept(compound, star(rotation ,new Vec3(1.5f, 0, 0)), rotation, 2.5f, 3.0f);
				TriangleList walls=new TriangleList();
				add_corner_walls.accept(walls, Vec3.sZero(), JPH_PI);
				create_bodies.accept(compound, walls, 4.0f * sin(slope) + 0.5f);
			}

			// Steep pocket: Walk up a 30 degree ramp that turns into a 70 degree slope inside a 120 degree corner
			{
				float ramp = degreesToRadians(30.0f), steep = degreesToRadians(70.0f);
				Quat ramp_rotation = Quat.sRotation(Vec3.sAxisZ(), -ramp);
				Quat steep_rotation = Quat.sRotation(Vec3.sAxisZ(), -steep);
				StaticCompoundShapeSettings compound=new StaticCompoundShapeSettings();
				add_slab.accept(compound, star(ramp_rotation ,new Vec3(1.0f, 0, 0)), ramp_rotation, 2.0f, 2.5f);
				Vec3 steep_start=new Vec3(-0.3f, tan(ramp) * 0.3f, 0);
				add_slab.accept(compound, plus(steep_start , star(steep_rotation ,new Vec3(-1.2f, 0, 0))), steep_rotation, 1.5f, 1.5f);
				TriangleList walls=new TriangleList();
				add_corner_walls.accept(walls,new Vec3(-1.0f, 0, 0), degreesToRadians(120.0f));
				create_bodies.accept(compound, walls, 3.0f * sin(ramp) + 0.5f);
			}

			// Steep V: Walk up a valley formed by a 55 and a 60 degree slope, the valley rises 10 degrees towards -X
			{
				float incline = degreesToRadians(10.0f);
				Quat tilt = Quat.sRotation(Vec3.sAxisZ(), -incline);
				StaticCompoundShapeSettings compound=new StaticCompoundShapeSettings();
				for (float side_angle : new float[]{ 55.0f, -60.0f })
				{
					Quat rotation = star(tilt , Quat.sRotation(Vec3.sAxisX(), degreesToRadians(side_angle)));
					add_slab.accept(compound, star(rotation ,new Vec3(0, 0, side_angle > 0.0f? -1.45f : 1.45f)), rotation, 3.0f, 1.5f);
				}
				create_bodies.accept(compound,new TriangleList(), 3.0f * sin(incline));
			}

			// Jump into a 130 and a 100 degree corner
			for (float corner_angle : new float[]{ 130.0f, 100.0f })
			{
				StaticCompoundShapeSettings compound=new StaticCompoundShapeSettings();
				add_slab.accept(compound,new Vec3(1.5f, 0, 0), Quat.sIdentity(), 2.5f, 3.0f);
				TriangleList walls=new TriangleList();
				add_corner_walls.accept(walls, Vec3.sZero(), degreesToRadians(corner_angle));
				create_bodies.accept(compound, walls, 0.5f);
			}

			// Gondola: A narrow aisle between two benches made out of multiple segments, with pillars and a console at the far end.
			// The character can walk in from +X.
			{
				// Add a box made out of triangles that face outwards
				final TriConsumer<TriangleList,Vec3Arg,Vec3Arg> add_box = ( ioTriangles,  inMin,  inMax)->
				{
					for (int axis = 0; axis < 3; ++axis)
						for (int side = 0; side < 2; ++side)
						{
							// Pick the tangent axes so that u x v points out of the box
							int u = (axis + 1) % 3, v = (axis + 2) % 3;
							if (side == 0)
								{int temp=u;u=v;v=temp;}
							Vec3 quad[]=new Vec3[4];
							for (int i = 0; i < 4; ++i)
							{
								Vec3 p = side == 0?new Vec3(inMin) :new Vec3(inMax);
								p.setComponent(u, (i == 1 || i == 2)? inMax.get(u) : inMin.get(u));
								p.setComponent(v, i >= 2? inMax.get(v) : inMin.get(v));
								quad[i] = p;
							}
							ioTriangles.pushBack(new Triangle(quad[0], quad[1], quad[2]));
							ioTriangles.pushBack(new Triangle(quad[0], quad[2], quad[3]));
						}
				};

				final float cHalfLength = 3.0f;			// Gondola goes from -cHalfLength to cHalfLength along X
				final float cAisleHalfWidth = 0.5f;		// A bit wider than the character
				final float cBenchDepth = 0.5f;
				final float cBenchHeight = 0.45f;
				final float cBenchStart = -1.8f;		// Benches go from here to cHalfLength along X
				final float cBenchSegmentLength = 1.2f;
				final float cWallThickness = 0.05f;
				final float cWallHeight = 1.0f;
				final float cPillarHalfSize = 0.04f;
				final float cPillarTop = 2.2f;
				final float cBenchOuter = cAisleHalfWidth + cBenchDepth;

				TriangleList triangles=new TriangleList();

				// Floor
				add_box.accept(triangles,new Vec3(-cHalfLength, -0.1f, -cBenchOuter - cWallThickness),new Vec3(cHalfLength, 0, cBenchOuter + cWallThickness));

				for (float sign : new float[]{ -1.0f, 1.0f })
				{
					// Benches consisting of multiple segments
					for (float x = cBenchStart; x < cHalfLength - 1.0e-3f; x += cBenchSegmentLength)
					{
						Vec3 p1=new Vec3(x, 0, sign * cAisleHalfWidth), p2=new Vec3(x + cBenchSegmentLength, cBenchHeight, sign * cBenchOuter);
						add_box.accept(triangles, Vec3.sMin(p1, p2), Vec3.sMax(p1, p2));
					}

					// Side walls
					Vec3 w1=new Vec3(-cHalfLength, 0, sign * cBenchOuter), w2=new Vec3(cHalfLength, cWallHeight, sign * (cBenchOuter + cWallThickness));
					add_box.accept(triangles, Vec3.sMin(w1, w2), Vec3.sMax(w1, w2));

					// Pillars on top of the side walls
					for (float x = -cHalfLength; x < cHalfLength + 1.0e-3f; x += cBenchSegmentLength)
					{
						Vec3 center=new Vec3(clamp(x, -cHalfLength + cPillarHalfSize, cHalfLength - cPillarHalfSize), 0, sign * (cBenchOuter + 0.5f * cWallThickness));
						add_box.accept(triangles, plus(center ,new Vec3(-cPillarHalfSize, cWallHeight, -cPillarHalfSize)), plus(center ,new Vec3(cPillarHalfSize, cPillarTop, cPillarHalfSize)));
					}
				}

				// Front wall
				add_box.accept(triangles,new Vec3(-cHalfLength - cWallThickness, 0, -cBenchOuter - cWallThickness),new Vec3(-cHalfLength, cWallHeight, cBenchOuter + cWallThickness));

				// Console against the front wall
				add_box.accept(triangles,new Vec3(-cHalfLength, 0, -0.6f),new Vec3(-cHalfLength + 0.4f, 1.0f, 0.6f));

				// Pole next to the console that goes up to the roof
				add_box.accept(triangles,new Vec3(-cHalfLength + 0.5f, 0, -0.6f),new Vec3(-cHalfLength + 0.58f, cPillarTop, -0.52f));

				// Stool in front of the console
				add_box.accept(triangles,new Vec3(-2.35f, 0, 0.1f),new Vec3(-2.05f, 0.6f, 0.4f));

				StaticCompoundShapeSettings compound=new StaticCompoundShapeSettings();
				create_bodies.accept(compound, triangles, 0.5f);
			}
		}
	}
        else if(supportsObjectStream())
	{
		// Load scene
		PhysicsSceneRef scene=new PhysicsSceneRef();
		if (!ObjectStreamIn.sReadObject((String)("Assets/") + sSceneName.toLowerCase() + ".bof", scene))
			FatalError("Failed to load scene");
		scene.fixInvalidScales();
		for (BodyCreationSettings settings : scene.getBodies())
		{
			settings.setObjectLayer ( Layers.NON_MOVING);
			settings.setFriction ( 0.5f);
		}
		scene.createBodies(mPhysicsSystem);
	}
}
/*TODO

void CharacterBaseTest::ProcessInput(const ProcessInputParams &inParams)
{
	// Determine controller input
	mControlInput = Vec3::sZero();
	if (inParams.mKeyboard->IsKeyPressed(EKey::Left))	mControlInput.SetZ(-1);
	if (inParams.mKeyboard->IsKeyPressed(EKey::Right))	mControlInput.SetZ(1);
	if (inParams.mKeyboard->IsKeyPressed(EKey::Up))		mControlInput.SetX(1);
	if (inParams.mKeyboard->IsKeyPressed(EKey::Down))	mControlInput.SetX(-1);
	if (mControlInput != Vec3::sZero())
		mControlInput = mControlInput.Normalized();

	// Rotate controls to align with the camera
	Vec3 cam_fwd = inParams.mCameraState.mForward;
	cam_fwd.SetY(0.0f);
	cam_fwd = cam_fwd.NormalizedOr(Vec3::sAxisX());
	Quat rotation = Quat::sFromTo(Vec3::sAxisX(), cam_fwd);
	mControlInput = rotation * mControlInput;

	// Check actions
	mJump = inParams.mKeyboard->IsKeyPressedAndTriggered(EKey::RControl, mWasJump);
	mSwitchStance = inParams.mKeyboard->IsKeyPressedAndTriggered(EKey::RShift, mWasSwitchStance);
}
*/

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	// Update scene time
	mTime += inParams.mDeltaTime;

	// Update camera pivot
	mCameraPivot = GetCharacterPosition();

	// Animate bodies
	if (mRotatingBody!=cInvalidBodyId)
		mBodyInterface.moveKinematic(mRotatingBody, cRotatingPosition, Quat.sRotation(Vec3.sAxisY(), JPH_PI * sin(mTime)), inParams.mDeltaTime);
	if (mRotatingWallBody!=cInvalidBodyId)
		mBodyInterface.moveKinematic(mRotatingWallBody, cRotatingWallPosition, Quat.sRotation(Vec3.sAxisY(), JPH_PI * sin(mTime)), inParams.mDeltaTime);
	if (mRotatingAndTranslatingBody!=cInvalidBodyId)
		mBodyInterface.moveKinematic(mRotatingAndTranslatingBody, plus(cRotatingAndTranslatingPosition , star(5.0f , new Vec3(sin(JPH_PI * mTime), 0, cos(JPH_PI * mTime)))), Quat.sRotation(Vec3.sAxisY(), JPH_PI * sin(mTime)), inParams.mDeltaTime);
	if (mHorizontallyMovingBody!=cInvalidBodyId)
		mBodyInterface.moveKinematic(mHorizontallyMovingBody, plus(cHorizontallyMovingPosition , new Vec3(3.0f * sin(mTime), 0, 0)), cHorizontallyMovingOrientation, inParams.mDeltaTime);
	if (mSmoothVerticallyMovingBody!=cInvalidBodyId)
		mBodyInterface.moveKinematic(mSmoothVerticallyMovingBody, plus(cSmoothVerticallyMovingPosition , new Vec3(0, 1.75f * sin(mTime), 0)), cSmoothVerticallyMovingOrientation, inParams.mDeltaTime);
	if (mReversingVerticallyMovingBody!=cInvalidBodyId)
	{
		RVec3 pos = mBodyInterface.getPosition(mReversingVerticallyMovingBody);
		if (pos.yy() < cReversingVerticallyMovingPosition.yy())
			mReversingVerticallyMovingVelocity = 1.0f;
		else if (pos.yy() > cReversingVerticallyMovingPosition.yy() + 5.0f)
			mReversingVerticallyMovingVelocity = -1.0f;
		mBodyInterface.moveKinematic(mReversingVerticallyMovingBody, plus(pos, new Vec3(0, mReversingVerticallyMovingVelocity * 3.0f * inParams.mDeltaTime, 0)), cReversingVerticallyMovingOrientation, inParams.mDeltaTime);
	}
	for (MovingCorner corner : mMovingCorners)
		mBodyInterface.moveKinematic(corner.mBodyID, plus(corner.mPosition , star(2.0f ,new Vec3(sin(0.25f * JPH_PI * mTime), 1.0f - cos(0.1f * JPH_PI * mTime), cos(0.25f * JPH_PI * mTime)))), star(Quat.sRotation(Vec3.sAxisY(), 0.25f * JPH_PI * sin(mTime)) , cCornersOrientation), inParams.mDeltaTime);

	// Animate character
	if (mAnimatedCharacter.getPtr() != nullptr)
		mAnimatedCharacter.setLinearVelocity(star(sin(mTime) , cCharacterVelocity));

	// Animate character virtual
	for (CharacterVirtualRef character : new CharacterVirtualRef[]{ mAnimatedCharacterVirtual, mAnimatedCharacterVirtualWithInnerBody })
		if (character.getPtr() != nullptr)
		{
			// Draw the character
			DrawPaddedCharacter(character.getShape(), character.getCharacterPadding(), character.getCenterOfMassTransform());

			// Update velocity and apply gravity
			Vec3 velocity;
			if (character.getGroundState() == EGroundState.OnGround)
				velocity = Vec3.sZero();
			else
				velocity = plus(star(character.getLinearVelocity() , mAnimatedCharacter.getUp()) , star(mPhysicsSystem.getGravity() , inParams.mDeltaTime));
			plusEquals(velocity , star(sin(mTime) , cCharacterVelocity));
			character.setLinearVelocity(velocity);

			// Move character
			ConstExtendedUpdateSettings update_settings = new ExtendedUpdateSettings();
			character.extendedUpdate(inParams.mDeltaTime,
				mPhysicsSystem.getGravity(),
				update_settings,
				mPhysicsSystem.getDefaultBroadPhaseLayerFilter(Layers.MOVING),
				mPhysicsSystem.getDefaultLayerFilter(Layers.MOVING),
				new BodyFilter(){ },
				new ShapeFilter(){ },
				mTempAllocator);
		}

	// Reset ramp blocks
	mRampBlocksTimeLeft -= inParams.mDeltaTime;
	if (mRampBlocksTimeLeft < 0.0f)
	{
		for (int i = 0; i < mRampBlocks.size(); ++i)
		{
			mBodyInterface.setPositionAndRotation(mRampBlocks.get(i), plus(cRampBlocksStart , star((float)(i) , cRampBlocksDelta)), cRampOrientation, EActivation.Activate);
			mBodyInterface.setLinearAndAngularVelocity(mRampBlocks.get(i), Vec3.sZero(), Vec3.sZero());
		}
		mRampBlocksTimeLeft = cRampBlocksTime;
	}

	// Call handle input after new velocities have been set to avoid frame delay
	HandleInput(mControlInput, mJump, mSwitchStance, inParams.mDeltaTime);
}
/*TODO

void CharacterBaseTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI->CreateTextButton(inSubMenu, "Select Scene", [this, inUI]() {
		UIElement *scene_name = inUI->CreateMenu();
		for (uint i = 0; i < size(sScenes); ++i)
			inUI->CreateTextButton(scene_name, sScenes[i], [this, i]() { sSceneName = sScenes[i]; RestartTest(); });
		inUI->ShowMenu(scene_name);
	});

	inUI->CreateTextButton(inSubMenu, "Character Movement Settings", [this, inUI]() {
		UIElement *movement_settings = inUI->CreateMenu();

		inUI->CreateCheckBox(movement_settings, "Control Movement During Jump", sControlMovementDuringJump, [](UICheckBox::EState inState) { sControlMovementDuringJump = inState == UICheckBox::STATE_CHECKED; });
		inUI->CreateSlider(movement_settings, "Character Speed", sCharacterSpeed, 0.1f, 10.0f, 0.1f, [](float inValue) { sCharacterSpeed = inValue; });
		inUI->CreateSlider(movement_settings, "Character Jump Speed", sJumpSpeed, 0.1f, 10.0f, 0.1f, [](float inValue) { sJumpSpeed = inValue; });
		AddCharacterMovementSettings(inUI, movement_settings);
		inUI->ShowMenu(movement_settings);
	});

	inUI->CreateTextButton(inSubMenu, "Configuration Settings", [this, inUI]() {
		UIElement *configuration_settings = inUI->CreateMenu();

		inUI->CreateComboBox(configuration_settings, "Shape Type", { "Capsule", "Cylinder", "Box", "Compound" }, (int)sShapeType, [](int inItem) { sShapeType = (EType)inItem; });
		AddConfigurationSettings(inUI, configuration_settings);
		inUI->CreateTextButton(configuration_settings, "Accept Changes", [this]() { RestartTest(); });
		inUI->ShowMenu(configuration_settings);
	});
}
*/

public void GetInitialCamera(CameraState ioState)
{
	// This will become the local space offset, look down the x axis and slightly down
	ioState.mPos = RVec3.sZero();
	ioState.mForward =new Vec3(10.0f, -2.0f, 0).normalized();
}

public RMat44 GetCameraPivot(float inCameraHeading, float inCameraPitch)
{
	// Pivot is center of character + distance behind based on the heading and pitch of the camera
	Vec3 fwd =new Vec3(cos(inCameraPitch) * cos(inCameraHeading), sin(inCameraPitch), cos(inCameraPitch) * sin(inCameraHeading));
	return RMat44.sTranslation(minus(plus(mCameraPivot , new Vec3(0, cCharacterHeightStanding + cCharacterRadiusStanding, 0)) , star(5.0f , fwd)));
}

public void SaveState(StateRecorder inStream)
{
	inStream.write(mTime);
	inStream.write(mRampBlocksTimeLeft);
	inStream.write(mReversingVerticallyMovingVelocity);

	if (mAnimatedCharacterVirtual != nullptr)
		mAnimatedCharacterVirtual.saveState(inStream);

	if (mAnimatedCharacterVirtualWithInnerBody != nullptr)
		mAnimatedCharacterVirtualWithInnerBody.saveState(inStream);
}

public void RestoreState(StateRecorder inStream)
{
	mTime=inStream.readFloat(mTime);
	mRampBlocksTimeLeft=inStream.readFloat(mRampBlocksTimeLeft);
	mReversingVerticallyMovingVelocity=inStream.readFloat(mReversingVerticallyMovingVelocity);

	if (mAnimatedCharacterVirtual != nullptr)
		mAnimatedCharacterVirtual.restoreState(inStream);

	if (mAnimatedCharacterVirtualWithInnerBody != nullptr)
		mAnimatedCharacterVirtualWithInnerBody.restoreState(inStream);
}

public void SaveInputState(StateRecorder inStream)
{
	inStream.write(mControlInput);
	inStream.write(mJump);
	inStream.write(mSwitchStance);
}

public void RestoreInputState(StateRecorder inStream)
{
	inStream.readVec3(mControlInput);
	mJump=inStream.readBoolean(mJump);
	mSwitchStance=inStream.readBoolean(mSwitchStance);
}

void DrawCharacterState(ConstCharacterBase  inCharacter, RMat44Arg inCharacterTransform, Vec3Arg inCharacterVelocity)
{
	// Draw current location
	// Drawing prior to update since the physics system state is also that prior to the simulation step (so that all detected collisions etc. make sense)
	mDebugRenderer.drawCoordinateSystem(inCharacterTransform, 0.1f);

	// Draw the state of the ground contact
	EGroundState ground_state = inCharacter.getGroundState();
	if (ground_state != EGroundState.InAir)
	{
		RVec3 ground_position = inCharacter.getGroundPosition();
		Vec3 ground_normal = inCharacter.getGroundNormal();
		Vec3 ground_velocity = inCharacter.getGroundVelocity();

		// Draw ground position
		mDebugRenderer.drawMarker(ground_position, Color.sRed, 0.1f);
		mDebugRenderer.drawArrow(ground_position, plus(ground_position , star(2.0f , ground_normal)), Color.sGreen, 0.1f);

		// Draw ground velocity
		if (!ground_velocity.isNearZero())
			mDebugRenderer.drawArrow(ground_position, plus(ground_position , ground_velocity), Color.sBlue, 0.1f);
	}

	// Draw provided character velocity
	if (!inCharacterVelocity.isNearZero())
		mDebugRenderer.drawArrow(inCharacterTransform.getTranslation(), plus(inCharacterTransform.getTranslation(), inCharacterVelocity), Color.sYellow, 0.1f);

	// Draw text info
	ConstPhysicsMaterial  ground_material = inCharacter.getGroundMaterial();
	Vec3 horizontal_velocity =new Vec3(inCharacterVelocity);
	horizontal_velocity.setY(0);
	ConstColor color = (horizontal_velocity.length() > 1.01f * sCharacterSpeed)? Color.sRed : Color.sWhite;
	mDebugRenderer.drawText3D(inCharacterTransform.getTranslation(), String.format("State: %s\nMat: %s\nHorizontal Vel: %.1f m/s\nVertical Vel: %.1f m/s", ground_state, ground_material.getDebugName(), (double)horizontal_velocity.length(), (double)inCharacterVelocity.getY()), color, 0.25f);
}

void DrawPaddedCharacter(ConstShape inShape, float inPadding, RMat44Arg inCenterOfMass)
{
	if (inShape.getSubType() == EShapeSubType.Capsule)
	{
		final CapsuleShape capsule = ( CapsuleShape )(inShape);
		mDebugRenderer.drawCapsule(inCenterOfMass, capsule.getHalfHeightOfCylinder(), capsule.getRadius() + inPadding, Color.sGrey, ECastShadow.Off, EDrawMode.Wireframe);
	}
	else if (inShape.getSubType() == EShapeSubType.Cylinder)
	{
		// Not correct as the edges should be rounded
		final CylinderShape cylinder = ( CylinderShape )(inShape);
		mDebugRenderer.drawCylinder(inCenterOfMass, cylinder.getHalfHeight() + inPadding, cylinder.getRadius() + inPadding, Color.sGrey, ECastShadow.Off, EDrawMode.Wireframe);
	}
	else if (inShape.getSubType() == EShapeSubType.Box)
	{
		// Not correct as the edges should be rounded
		final BoxShape box = ( BoxShape )(inShape);
		AaBox bounds = box.getLocalBounds();
		bounds.expandBy(Vec3.sReplicate(inPadding));
		mDebugRenderer.drawWireBox(inCenterOfMass, bounds, Color.sGrey);
	}
	else if (inShape.getSubType() == EShapeSubType.RotatedTranslated)
	{
		final RotatedTranslatedShape rt = ( RotatedTranslatedShape )(inShape);
		DrawPaddedCharacter(rt.getInnerShape(), inPadding, inCenterOfMass);
	}
	else if (inShape.getType() == EShapeType.Compound)
	{
		final CompoundShape compound = ( CompoundShape )(inShape);
		for (ConstSubShape  sub_shape : compound.getSubShapes())
			DrawPaddedCharacter(sub_shape.getShape(), inPadding, star(inCenterOfMass , sub_shape.getLocalTransformNoScale(Vec3.sOne())));
	}
}}
