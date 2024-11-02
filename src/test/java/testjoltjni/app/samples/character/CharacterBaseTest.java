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
package testjoltjni.app.samples.character;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.operator.Op;
import com.github.stephengold.joltjni.readonly.*;
import java.util.*;
import testjoltjni.app.samples.*;
import testjoltjni.app.testframework.*;

/**
 * A line-for-line Java translation of the Jolt Physics abstract character
 * test class.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Character/CharacterBaseTest.cpp
 */
abstract class CharacterBaseTest extends Test{

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
float mRampBlocksTimeLeft = 0.0f;
BodyId mConveyorBeltBody = new BodyId();
BodyId mSensorBody = new BodyId();
CharacterVsCharacterCollisionSimple mCharacterVsCharacterCollision = new CharacterVsCharacterCollisionSimple();
private enum EType{Capsule, Cylinder, Box};
private EType sShapeType = EType.Capsule;
private float mTime = 0f;
private RVec3 mCameraPivot = RVec3.sZero();
private BodyId mRotatingBody = new BodyId();
private BodyId mRotatingWallBody = new BodyId();
private BodyId mRotatingAndTranslatingBody = new BodyId();
private BodyId mSmoothVerticallyMovingBody = new BodyId();
private BodyId mReversingVerticallyMovingBody = new BodyId();
private float mReversingVerticallyMovingVelocity = 1.0f;
private BodyId mHorizontallyMovingBody = new BodyId();
private CharacterRef mAnimatedCharacter;
private CharacterVirtualRef mAnimatedCharacterVirtual;
private CharacterVirtualRef mAnimatedCharacterVirtualWithInnerBody;
private Vec3 mControlInput = Vec3.sZero();
private boolean mJump = false;
private boolean mWasJump = false;
private boolean mSwitchStance = false;
private boolean mWasSwitchStance = false;

private String sScenes[] =
{
	"PerlinMesh",
	"PerlinHeightField",
	"ObstacleCourse",
	"InitiallyIntersecting",
	"Terrain1",
	"Terrain2"
};

private String sSceneName = "ObstacleCourse";

// Scene constants
static RVec3Arg cRotatingPosition=new RVec3(-5, 0.15f, 15);
static QuatArg cRotatingOrientation = Quat.sIdentity();
static RVec3Arg cRotatingWallPosition=new RVec3(5, 1.0f, 25.0f);
static QuatArg cRotatingWallOrientation = Quat.sIdentity();
static RVec3Arg cRotatingAndTranslatingPosition=new RVec3(-10, 0.15f, 27.5f);
static QuatArg cRotatingAndTranslatingOrientation = Quat.sIdentity();
static RVec3Arg cSmoothVerticallyMovingPosition=new RVec3(0, 2.0f, 15);
static QuatArg cSmoothVerticallyMovingOrientation = Quat.sIdentity();
static RVec3Arg cReversingVerticallyMovingPosition=new RVec3(0, 0.15f, 25);
static QuatArg cReversingVerticallyMovingOrientation = Quat.sIdentity();
static RVec3Arg cHorizontallyMovingPosition=new RVec3(5, 1, 15);
static QuatArg cHorizontallyMovingOrientation = Quat.sRotation(Vec3.sAxisZ(), 0.5f * Jolt.JPH_PI);
static RVec3Arg cConveyorBeltPosition=new RVec3(-10, 0.15f, 15);
static final RVec3Arg cRampPosition=new RVec3(15, 2.2f, 15);
static QuatArg cRampOrientation = Quat.sRotation(Vec3.sAxisX(), -0.25f * Jolt.JPH_PI);
static RVec3Arg cRampBlocksStart = Op.add(cRampPosition , new Vec3(-3.0f, 3.0f, 1.5f));
static Vec3Arg cRampBlocksDelta =new Vec3(2.0f, 0, 0);
static final float cRampBlocksTime = 5.0f;
static RVec3Arg cSmallBumpsPosition=new RVec3(-5.0f, 0, 2.5f);
static final float cSmallBumpHeight = 0.05f;
static final float cSmallBumpWidth = 0.01f;
static final float cSmallBumpDelta = 0.5f;
static RVec3Arg cLargeBumpsPosition=new RVec3(-10.0f, 0, 2.5f);
static final float cLargeBumpHeight = 0.3f;
static final float cLargeBumpWidth = 0.1f;
static final float cLargeBumpDelta = 2.0f;
static RVec3Arg cStairsPosition=new RVec3(-15.0f, 0, 2.5f);
static final float cStairsStepHeight = 0.3f;
static RVec3Arg cMeshStairsPosition=new RVec3(-20.0f, 0, 2.5f);
static RVec3Arg cNoStairsPosition=new RVec3(-15.0f, 0, 10.0f);
static final float cNoStairsStepHeight = 0.3f;
static final float cNoStairsStepDelta = 0.05f;
static RVec3Arg cMeshNoStairsPosition=new RVec3(-20.0f, 0, 10.0f);
static RVec3Arg cMeshWallPosition=new RVec3(-25.0f, 0, -27.0f);
static final float cMeshWallHeight = 3.0f;
static final float cMeshWallWidth = 2.0f;
static final float cMeshWallStepStart = 0.5f;
static final float cMeshWallStepEnd = 4.0f;
static final int cMeshWallSegments = 25;
static RVec3Arg cHalfCylinderPosition=new RVec3(5.0f, 0, 8.0f);
static RVec3Arg cMeshBoxPosition=new RVec3(30.0f, 1.5f, 5.0f);
static RVec3Arg cSensorPosition=new RVec3(30, 0.9f, -5);
static RVec3Arg cCharacterPosition=new RVec3(-3.5f, 0, 3.0f);
static RVec3Arg cCharacterVirtualPosition=new RVec3(-5.0f, 0, 3.0f);
static RVec3Arg cCharacterVirtualWithInnerBodyPosition=new RVec3(-6.5f, 0, 3.0f);
static Vec3Arg cCharacterVelocity=new Vec3(0, 0, 2);

void Initialize()
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
		mInnerStandingShape =new RotatedTranslatedShapeSettings(new Vec3(0, 0.5f * cCharacterHeightStanding + cCharacterRadiusStanding, 0), Quat.sIdentity(), new BoxShape(Op.multiply(cInnerShapeFraction , new Vec3(cCharacterRadiusStanding, 0.5f * cCharacterHeightStanding + cCharacterRadiusStanding, cCharacterRadiusStanding)))).create().get();
		mInnerCrouchingShape =new RotatedTranslatedShapeSettings(new Vec3(0, 0.5f * cCharacterHeightCrouching + cCharacterRadiusCrouching, 0), Quat.sIdentity(), new BoxShape(Op.multiply(cInnerShapeFraction , new Vec3(cCharacterRadiusCrouching, 0.5f * cCharacterHeightCrouching + cCharacterRadiusCrouching, cCharacterRadiusCrouching)))).create().get();
		break;
	}

	if (sSceneName.equals( "PerlinMesh") )
	{
		// Default terrain
		CreateMeshTerrain();
	}
	else if (sSceneName.equals( "PerlinHeightField") )
	{
		// Default terrain
		CreateHeightFieldTerrain();
	}
	else if (sSceneName.equals( "InitiallyIntersecting") )
	{
		CreateFloor();

		// Create a grid of boxes that are initially intersecting with the character
		ShapeRefC box = new BoxShape(new Vec3(0.1f, 0.1f, 0.1f)).toRefC();
		BodyCreationSettings settings=new BodyCreationSettings(box, new RVec3(0, 0.5f, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
		for (int x = 0; x < 4; ++x)
			for (int y = 0; y <= 10; ++y)
				for (int z = 0; z <= 10; ++z)
				{
					settings.setPosition(new RVec3(-0.5f + 0.1f * x, 0.1f + 0.1f * y, -0.5f + 0.1f * z));
					mBodyInterface.createAndAddBody(settings, EActivation.DontActivate);
				}
	}
	else if (sSceneName.equals( "ObstacleCourse") )
	{
		// Default terrain
		CreateFloor(350.0f);

		{
			// Create ramps with different inclinations
			ShapeRefC ramp =new RotatedTranslatedShapeSettings(new Vec3(0, 0, -2.5f), Quat.sIdentity(), new BoxShape(new Vec3(1.0f, 0.05f, 2.5f))).create().get();
			for (int angle = 0; angle < 18; ++angle)
				mBodyInterface.createAndAddBody(new BodyCreationSettings(ramp, new RVec3(-15.0f + angle * 2.0f, 0, -10.0f), Quat.sRotation(Vec3.sAxisX(), Jolt.degreesToRadians(10.0f * angle)), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
		}

		{
			// Create ramps with different inclinations intersecting with a steep slope
			ShapeRefC ramp =new RotatedTranslatedShapeSettings(new Vec3(0, 0, -2.5f), Quat.sIdentity(), new BoxShape(new Vec3(1.0f, 0.05f, 2.5f))).create().get();
			ShapeRefC ramp2 =new RotatedTranslatedShapeSettings(new Vec3(0, 2.0f, 0), Quat.sIdentity(), new BoxShape(new Vec3(0.05f, 2.0f, 1.0f))).create().get();
			for (int angle = 0; angle < 9; ++angle)
			{
				mBodyInterface.createAndAddBody(new BodyCreationSettings(ramp, new RVec3(-15.0f + angle * 2.0f, 0, -20.0f - angle * 0.1f), Quat.sRotation(Vec3.sAxisX(), Jolt.degreesToRadians(10.0f * angle)), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
				mBodyInterface.createAndAddBody(new BodyCreationSettings(ramp2, new RVec3(-15.0f + angle * 2.0f, 0, -21.0f), Quat.sRotation(Vec3.sAxisZ(), Jolt.degreesToRadians(20.0f)), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
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
			bcs.setMassPropertiesOverride ( bcs.getMassPropertiesOverride().setMass(10.0f));
			mBodyInterface.createAndAddBody(bcs, EActivation.Activate);
		}

		{
			// Dynamic blocks to test player pushing blocks
			ShapeRef block = new BoxShape(Vec3.sReplicate(0.5f)).toRef();
			for (int y = 0; y < 3; ++y)
			{
				BodyCreationSettings bcs=new BodyCreationSettings(block, new RVec3(5.0f, 0.5f + (float)(y), 0.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
				bcs.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
				bcs.setMassPropertiesOverride ( bcs.getMassPropertiesOverride().setMass(10.0f));
				mBodyInterface.createAndAddBody(bcs, EActivation.DontActivate);
			}
		}

		{
			// Dynamic block on a static step (to test pushing block on stairs)
			mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(0.5f, 0.15f, 0.5f)), new RVec3(10.0f, 0.15f, 0.0f), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
			BodyCreationSettings bcs=new BodyCreationSettings(new BoxShape(Vec3.sReplicate(0.5f)), new RVec3(10.0f, 0.8f, 0.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
			bcs.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
			bcs.setMassPropertiesOverride ( bcs.getMassPropertiesOverride().setMass(10.0f));
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
				bcs.setMassPropertiesOverride ( bcs.getMassPropertiesOverride().setMass(10.0f));
				mBodyInterface.createAndAddBody(bcs, EActivation.DontActivate);
			}
		}

		{
			// A seesaw to test character gravity
			BodyId b1 = mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(1.0f, 0.2f, 0.05f)), new RVec3(20.0f, 0.2f, 0.0f), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
			BodyCreationSettings bcs=new BodyCreationSettings(new BoxShape(new Vec3(1.0f, 0.05f, 5.0f)), new RVec3(20.0f, 0.45f, 0.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
			bcs.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
			bcs.setMassPropertiesOverride ( bcs.getMassPropertiesOverride().setMass(10.0f));
			BodyId b2 = mBodyInterface.createAndAddBody(bcs, EActivation.Activate);

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
			bcs.setMassPropertiesOverride ( bcs.getMassPropertiesOverride().setMass(10.0f));
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
			bcs.setMassPropertiesOverride ( bcs.getMassPropertiesOverride().setMass(10.0f));
			for (int i = 0; i < 4; ++i)
			{
				mRampBlocks.pushBack(mBodyInterface.createAndAddBody(bcs, EActivation.Activate));
				bcs.setPosition(Op.add(bcs.getPosition(), cRampBlocksDelta));
			}
		}

		// Create three funnels with walls that are too steep to climb
		ShapeRef funnel = new BoxShape(new Vec3(0.1f, 1.0f, 1.0f)).toRef();
		for (int i = 0; i < 2; ++i)
		{
			Quat rotation = Quat.sRotation(Vec3.sAxisY(), Jolt.JPH_PI * i);
			mBodyInterface.createAndAddBody(new BodyCreationSettings(funnel, Op.add(new RVec3(5.0f, 0.1f, 5.0f) , Op.rotate(rotation , new Vec3(0.2f, 0, 0))), Op.multiply(rotation , Quat.sRotation(Vec3.sAxisZ(), -Jolt.degreesToRadians(40.0f))), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
		}
		for (int i = 0; i < 3; ++i)
		{
			Quat rotation = Quat.sRotation(Vec3.sAxisY(), 2.0f / 3.0f * Jolt.JPH_PI * i);
			mBodyInterface.createAndAddBody(new BodyCreationSettings(funnel, Op.add(new RVec3(7.5f, 0.1f, 5.0f) , Op.rotate(rotation , new Vec3(0.2f, 0, 0))), Op.multiply(rotation , Quat.sRotation(Vec3.sAxisZ(), -Jolt.degreesToRadians(40.0f))), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
		}
		for (int i = 0; i < 4; ++i)
		{
			Quat rotation = Quat.sRotation(Vec3.sAxisY(), 0.5f * Jolt.JPH_PI * i);
			mBodyInterface.createAndAddBody(new BodyCreationSettings(funnel, Op.add(new RVec3(10.0f, 0.1f, 5.0f) , Op.rotate(rotation , new Vec3(0.2f, 0, 0))), Op.multiply(rotation , Quat.sRotation(Vec3.sAxisZ(), -Jolt.degreesToRadians(40.0f))), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
		}

		// Create small bumps
		{
			BodyCreationSettings step=new BodyCreationSettings(new BoxShape(new Vec3(2.0f, 0.5f * cSmallBumpHeight, 0.5f * cSmallBumpWidth), 0.0f), RVec3.sZero(), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
			for (int i = 0; i < 10; ++i)
			{
				step.setPosition ( Op.add(cSmallBumpsPosition , new Vec3(0, 0.5f * cSmallBumpHeight, cSmallBumpDelta * i)));
				mBodyInterface.createAndAddBody(step, EActivation.DontActivate);
			}
		}

		// Create large bumps
		{
			BodyCreationSettings step=new BodyCreationSettings(new BoxShape(new Vec3(2.0f, 0.5f * cLargeBumpHeight, 0.5f * cLargeBumpWidth)), RVec3.sZero(), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
			for (int i = 0; i < 5; ++i)
			{
				step.setPosition ( Op.add(cLargeBumpsPosition , new Vec3(0, 0.5f * cLargeBumpHeight, cLargeBumpDelta * i)));
				mBodyInterface.createAndAddBody(step, EActivation.DontActivate);
			}
		}

		// Create stairs
		{
			BodyCreationSettings step = new BodyCreationSettings(new BoxShape(new Vec3(2.0f, 0.5f * cStairsStepHeight, 0.5f * cStairsStepHeight)), RVec3.sZero(), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
			for (int i = 0; i < 10; ++i)
			{
				step.setPosition ( Op.add(cStairsPosition , new Vec3(0, cStairsStepHeight * (0.5f + i), cStairsStepHeight * i)));
				mBodyInterface.createAndAddBody(step, EActivation.DontActivate);
			}
		}

		// A wall beside the stairs
		mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(0.5f, 2.0f, 5.0f * cStairsStepHeight)), Op.add(cStairsPosition , new Vec3(-2.5f, 2.0f, 5.0f * cStairsStepHeight)), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

		// Create stairs from triangles
		{
			List<Triangle> triangles=new ArrayList<>();

			float rear_z = 10 * cStairsStepHeight;

			for (int i = 0; i < 10; ++i)
			{
				// Start of step
				Vec3 base=new Vec3(0, cStairsStepHeight * i, cStairsStepHeight * i);

				// Left side
				Vec3 b1 = Op.add(base , new Vec3(2.0f, 0, 0));
				Vec3 s1 = Op.add(b1 , new Vec3(0, cStairsStepHeight, 0));
				Vec3 p1 = Op.add(s1 , new Vec3(0, 0, cStairsStepHeight));

				// Right side
				Vec3 width=new Vec3(-4.0f, 0, 0);
				Vec3 b2 = Op.add(b1 , width);
				Vec3 s2 = Op.add(s1 , width);
				Vec3 p2 = Op.add(p1 , width);

				triangles.add(new Triangle(s1, b1, s2));
				triangles.add(new Triangle(b1, b2, s2));
				triangles.add(new Triangle(s1, p2, p1));
				triangles.add(new Triangle(s1, s2, p2));

				// Side of stairs
				Vec3 rb2 = b2; rb2.setZ(rear_z);
				Vec3 rs2 = s2; rs2.setZ(rear_z);

				triangles.add(new Triangle(s2, b2, rs2));
				triangles.add(new Triangle(rs2, b2, rb2));

				p1 = p2;
			}

			MeshShapeSettings mesh=new MeshShapeSettings(triangles);
			mesh.setEmbedded();
			BodyCreationSettings mesh_stairs=new BodyCreationSettings(mesh, cMeshStairsPosition, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
			mBodyInterface.createAndAddBody(mesh_stairs, EActivation.DontActivate);
		}

		// A wall to the side and behind the stairs
		mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(0.5f, 2.0f, 0.25f)), Op.add(cStairsPosition , new Vec3(-7.5f, 2.0f, 10.0f * cStairsStepHeight + 0.25f)), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

		// Create stairs with too little space between the steps
		{
			BodyCreationSettings step=new BodyCreationSettings(new BoxShape(new Vec3(2.0f, 0.5f * cNoStairsStepHeight, 0.5f * cNoStairsStepHeight)), RVec3.sZero(), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
			for (int i = 0; i < 10; ++i)
			{
				step.setPosition ( Op.add(cNoStairsPosition , new Vec3(0, cNoStairsStepHeight * (0.5f + i), cNoStairsStepDelta * i)));
				mBodyInterface.createAndAddBody(step, EActivation.DontActivate);
			}
		}

		// Create stairs with too little space between the steps consisting of triangles
		{
			List<Triangle> triangles=new ArrayList<>(40);

			for (int i = 0; i < 10; ++i)
			{
				// Start of step
				Vec3 base=new Vec3(0, cStairsStepHeight * i, cNoStairsStepDelta * i);

				// Left side
				Vec3 b1 = Op.subtract(base , new Vec3(2.0f, 0, 0));
				Vec3 s1 = Op.add(b1 , new Vec3(0, cStairsStepHeight, 0));
				Vec3 p1 = Op.add(s1 , new Vec3(0, 0, cNoStairsStepDelta));

				// Right side
				Vec3 width=new Vec3(4.0f, 0, 0);
				Vec3 b2 = Op.add(b1 , width);
				Vec3 s2 = Op.add(s1 , width);
				Vec3 p2 = Op.add(p1 , width);

				triangles.add(new Triangle(s1, s2, b1));
				triangles.add(new Triangle(b1, s2, b2));
				triangles.add(new Triangle(s1, p1, p2));
				triangles.add(new Triangle(s1, p2, s2));
				p1 = p2;
			}

			MeshShapeSettings mesh=new MeshShapeSettings(triangles);
			mesh.setEmbedded();
			BodyCreationSettings mesh_stairs=new BodyCreationSettings(mesh, cMeshNoStairsPosition, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
			mBodyInterface.createAndAddBody(mesh_stairs, EActivation.DontActivate);
		}

		// Create mesh with walls at varying angles
		{
			List<Triangle> triangles=new ArrayList<>();
			Vec3 p1=new Vec3(0.5f * cMeshWallWidth, 0, 0);
			Vec3 h=new Vec3(0, cMeshWallHeight, 0);
			for (int i = 0; i < cMeshWallSegments; ++i)
			{
				float delta = cMeshWallStepStart + i * (cMeshWallStepEnd - cMeshWallStepStart) / (cMeshWallSegments - 1);
				Vec3 p2=new Vec3(((i & 1) != 0x0)? 0.5f * cMeshWallWidth : -0.5f * cMeshWallWidth, 0, p1.getZ() + delta);
				triangles.add(new Triangle(p1, Op.add(p1 , h), Op.add(p2 , h)));
				triangles.add(new Triangle(p1, Op.add(p2 , h), p2));
				p1 = p2;
			}

			MeshShapeSettings mesh=new MeshShapeSettings(triangles);
			mesh.setEmbedded();
			BodyCreationSettings wall=new BodyCreationSettings(mesh, cMeshWallPosition, Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
			mBodyInterface.createAndAddBody(wall, EActivation.DontActivate);
		}

		// Create a half cylinder with caps for testing contact point limit
		{
			List<Float3> vertices=new ArrayList<>();
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
					float angle_rad = (-0.5f + (float)(angle) / cAngleSegments) * Jolt.JPH_PI;
					float s = (float)Math.sin(angle_rad);
					float c = (float)Math.cos(angle_rad);
					float x = cCylinderLength * (-0.5f + (float)(pos) / (cPosSegments - 1));
					float y = angle == 0 || angle == cAngleSegments - 1? 0.5f : (1.0f - c) * radius;
					float z = s * radius;
					vertices.add(new Float3(x, y, z));

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
			Float3[] vertices = {
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
			mAnimatedCharacter.getPtr().addToPhysicsSystem();
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
	}
        else if (Jolt.supportsObjectStream())
	{
		// Load scene
		PhysicsSceneRef scene=new PhysicsSceneRef();
		if (!ObjectStreamIn.sReadObject((String)("Assets/") + sSceneName + ".bof", scene))
			throw new RuntimeException("Failed to load scene");
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
	if (inParams.mKeyboard->IsKeyPressed(DIK_LEFT))		mControlInput.SetZ(-1);
	if (inParams.mKeyboard->IsKeyPressed(DIK_RIGHT))	mControlInput.SetZ(1);
	if (inParams.mKeyboard->IsKeyPressed(DIK_UP))		mControlInput.SetX(1);
	if (inParams.mKeyboard->IsKeyPressed(DIK_DOWN))		mControlInput.SetX(-1);
	if (mControlInput != Vec3::sZero())
		mControlInput = mControlInput.Normalized();

	// Rotate controls to align with the camera
	Vec3 cam_fwd = inParams.mCameraState.mForward;
	cam_fwd.SetY(0.0f);
	cam_fwd = cam_fwd.NormalizedOr(Vec3::sAxisX());
	Quat rotation = Quat::sFromTo(Vec3::sAxisX(), cam_fwd);
	mControlInput = rotation * mControlInput;

	// Check actions
	mJump = inParams.mKeyboard->IsKeyPressedAndTriggered(DIK_RCONTROL, mWasJump);
	mSwitchStance = inParams.mKeyboard->IsKeyPressedAndTriggered(DIK_RSHIFT, mWasSwitchStance);
}
*/

void PrePhysicsUpdate(PreUpdateParams inParams)
{
	// Update scene time
	mTime += inParams.mDeltaTime;

	// Update camera pivot
	mCameraPivot = GetCharacterPosition();

	// Animate bodies
	if (!mRotatingBody.isInvalid())
		mBodyInterface.moveKinematic(mRotatingBody, cRotatingPosition, Quat.sRotation(Vec3.sAxisY(), Jolt.JPH_PI * (float)Math.sin(mTime)), inParams.mDeltaTime);
	if (!mRotatingWallBody.isInvalid())
		mBodyInterface.moveKinematic(mRotatingWallBody, cRotatingWallPosition, Quat.sRotation(Vec3.sAxisY(), Jolt.JPH_PI * (float)Math.sin(mTime)), inParams.mDeltaTime);
	if (!mRotatingAndTranslatingBody.isInvalid())
		mBodyInterface.moveKinematic(mRotatingAndTranslatingBody, Op.add(cRotatingAndTranslatingPosition , Op.multiply(5.0f , new Vec3((float)Math.sin(Jolt.JPH_PI * mTime), 0, (float)Math.cos(Jolt.JPH_PI * mTime)))), Quat.sRotation(Vec3.sAxisY(), Jolt.JPH_PI * (float)Math.sin(mTime)), inParams.mDeltaTime);
	if (!mHorizontallyMovingBody.isInvalid())
		mBodyInterface.moveKinematic(mHorizontallyMovingBody, Op.add(cHorizontallyMovingPosition , new Vec3(3.0f * (float)Math.sin(mTime), 0, 0)), cHorizontallyMovingOrientation, inParams.mDeltaTime);
	if (!mSmoothVerticallyMovingBody.isInvalid())
		mBodyInterface.moveKinematic(mSmoothVerticallyMovingBody, Op.add(cSmoothVerticallyMovingPosition , new Vec3(0, 1.75f * (float)Math.sin(mTime), 0)), cSmoothVerticallyMovingOrientation, inParams.mDeltaTime);
	if (!mReversingVerticallyMovingBody.isInvalid())
	{
		RVec3 pos = mBodyInterface.getPosition(mReversingVerticallyMovingBody);
		if (pos.yy() < cReversingVerticallyMovingPosition.yy())
			mReversingVerticallyMovingVelocity = 1.0f;
		else if (pos.yy() > cReversingVerticallyMovingPosition.yy() + 5.0f)
			mReversingVerticallyMovingVelocity = -1.0f;
		mBodyInterface.moveKinematic(mReversingVerticallyMovingBody, Op.add(pos, new Vec3(0, mReversingVerticallyMovingVelocity * 3.0f * inParams.mDeltaTime, 0)), cReversingVerticallyMovingOrientation, inParams.mDeltaTime);
	}

	// Animate character
	if (mAnimatedCharacter != null)
		mAnimatedCharacter.getPtr().setLinearVelocity(Op.multiply((float)Math.sin(mTime) , cCharacterVelocity));

	// Animate character virtual
	for (CharacterVirtual character : new CharacterVirtual[]{ mAnimatedCharacterVirtual.getPtr(), mAnimatedCharacterVirtualWithInnerBody.getPtr() })
	{
	if (Jolt.implementsDebugRendering())
		character.getShape().draw(mDebugRenderer, character.getCenterOfMassTransform(), Vec3.sReplicate(1.0f), Color.sOrange, false, true);
	else
		mDebugRenderer.drawCapsule(character.getCenterOfMassTransform(), 0.5f * cCharacterHeightStanding, cCharacterRadiusStanding + character.getCharacterPadding(), Color.sOrange, ECastShadow.Off, EDrawMode.Wireframe);

		// Update velocity and apply gravity
		Vec3 velocity;
		if (character.getGroundState() == EGroundState.OnGround)
			velocity = Vec3.sZero();
		else
			velocity = Op.add(Op.multiply(character.getLinearVelocity() , mAnimatedCharacter.getUp()) , Op.multiply(mPhysicsSystem.getGravity() , inParams.mDeltaTime));
		Op.plusEquals(velocity , Op.multiply((float)Math.sin(mTime) , cCharacterVelocity));
		character.setLinearVelocity(velocity);

		// Move character
		ExtendedUpdateSettings update_settings = new ExtendedUpdateSettings();
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
			mBodyInterface.setPositionAndRotation(mRampBlocks.get(i), Op.add(cRampBlocksStart , Op.multiply((float)(i) , cRampBlocksDelta)), cRampOrientation, EActivation.Activate);
			mBodyInterface.setLinearAndAngularVelocity(mRampBlocks.get(i), Vec3.sZero(), Vec3.sZero());
		}
		mRampBlocksTimeLeft = cRampBlocksTime;
	}

	// Call handle input after new velocities have been set to avoid frame delay
	HandleInput(mControlInput, mJump, mSwitchStance, inParams.mDeltaTime);
}

/*TODO
void CreateSettingsMenu(DebugUI inUI, UIElement inSubMenu)
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

		inUI->CreateComboBox(configuration_settings, "Shape Type", { "Capsule", "Cylinder", "Box" }, (int)sShapeType, [](int inItem) { sShapeType = (EType)inItem; });
		AddConfigurationSettings(inUI, configuration_settings);
		inUI->CreateTextButton(configuration_settings, "Accept Changes", [this]() { RestartTest(); });
		inUI->ShowMenu(configuration_settings);
	});
}
*/

void GetInitialCamera(CameraState ioState)
{
	// This will become the local space offset, look down the x axis and slightly down
	ioState.mPos = RVec3.sZero();
	ioState.mForward =new Vec3(10.0f, -2.0f, 0).normalized();
}

RMat44 GetCameraPivot(float inCameraHeading, float inCameraPitch)
{
	// Pivot is center of character + distance behind based on the heading and pitch of the camera
	Vec3 fwd =new Vec3((float)Math.cos(inCameraPitch) * Math.cos(inCameraHeading), (float)Math.sin(inCameraPitch), (float)Math.cos(inCameraPitch) * Math.sin(inCameraHeading));
	return RMat44.sTranslation(Op.subtract(Op.add(mCameraPivot , new Vec3(0, cCharacterHeightStanding + cCharacterRadiusStanding, 0)) , Op.multiply(5.0f , fwd)));
}

void SaveState(StateRecorder inStream)
{
	inStream.write(mTime);
	inStream.write(mRampBlocksTimeLeft);
	inStream.write(mReversingVerticallyMovingVelocity);

	if (mAnimatedCharacterVirtual != null)
		mAnimatedCharacterVirtual.getPtr().saveState(inStream);

	if (mAnimatedCharacterVirtualWithInnerBody != null)
		mAnimatedCharacterVirtualWithInnerBody.getPtr().saveState(inStream);
}

void RestoreState(StateRecorder inStream)
{
	mTime=inStream.readFloat(mTime);
	mRampBlocksTimeLeft=inStream.readFloat(mRampBlocksTimeLeft);
	mReversingVerticallyMovingVelocity=inStream.readFloat(mReversingVerticallyMovingVelocity);

	if (mAnimatedCharacterVirtual != null)
		mAnimatedCharacterVirtual.getPtr().restoreState(inStream);

	if (mAnimatedCharacterVirtualWithInnerBody != null)
		mAnimatedCharacterVirtualWithInnerBody.getPtr().restoreState(inStream);
}

void SaveInputState(StateRecorder inStream)
{
	inStream.write(mControlInput);
	inStream.write(mJump);
	inStream.write(mSwitchStance);
}

void RestoreInputState(StateRecorder inStream)
{
	inStream.readVec3(mControlInput);
	mJump = inStream.readBoolean(mJump);
	mSwitchStance = inStream.readBoolean(mSwitchStance);
}

void DrawCharacterState(ConstCharacterBase inCharacter, RMat44Arg inCharacterTransform, Vec3Arg inCharacterVelocity)
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
		mDebugRenderer.drawArrow(ground_position, Op.add(ground_position, Op.multiply(2.0f , ground_normal)), Color.sGreen, 0.1f);

		// Draw ground velocity
		if (!ground_velocity.isNearZero())
			mDebugRenderer.drawArrow(ground_position, Op.add(ground_position , ground_velocity), Color.sBlue, 0.1f);
	}

	// Draw provided character velocity
	if (!inCharacterVelocity.isNearZero())
		mDebugRenderer.drawArrow(inCharacterTransform.getTranslation(), Op.add(inCharacterTransform.getTranslation(), inCharacterVelocity), Color.sYellow, 0.1f);

	// Draw text info
	ConstPhysicsMaterial ground_material = inCharacter.getGroundMaterial();
	Vec3 horizontal_velocity =new Vec3(inCharacterVelocity);
	horizontal_velocity.setY(0);
	mDebugRenderer.drawText3D(inCharacterTransform.getTranslation(), String.format("State: %s\nMat: %s\nHorizontal Vel: %.1f m/s\nVertical Vel: %.1f m/s", ground_state, ground_material.getDebugName(), (double)horizontal_velocity.length(), (double)inCharacterVelocity.getY()), Color.sWhite, 0.25f);
}
}
