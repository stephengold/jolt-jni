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
package testjoltjni.app.samples.character;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.readonly.*;
import testjoltjni.app.samples.*;
import testjoltjni.app.testframework.CameraState;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import static com.github.stephengold.joltjni.std.Std.*;
/**
 * A line-for-line Java translation of the Jolt-Physics character-spaceship
 * test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Character/CharacterSpaceShipTest.cpp
 */
public class CharacterSpaceShipTest extends Test{
static final float cCharacterHeightStanding = 1.35f;
static final float cCharacterRadiusStanding = 0.3f;
static final float cJumpSpeed = 4.0f;
CharacterVirtualRef mCharacter=new CharacterVirtualRef();
int mSpaceShip=cInvalidBodyId;
RMat44 mSpaceShipPrevTransform=new RMat44();
Vec3 mSpaceShipLinearVelocity=new Vec3();
Vec3 mSpaceShipAngularVelocity=new Vec3();
float mTime = 0.0f;
Vec3 mDesiredVelocity = Vec3.sZero();
boolean mJump = false;

public void Initialize()
{
	// Dimensions of our space ship
	final float cSpaceShipHeight = 2.0f;
	final float cSpaceShipRingHeight = 0.2f;
	final float cSpaceShipRadius = 100.0f;
	final RVec3 cShipInitialPosition=new RVec3(-25, 15, 0);

	// Create floor for reference
	CreateFloor();

	// Create 'player' character
	CharacterVirtualSettings settings = new CharacterVirtualSettings();
	settings.setShape (new RotatedTranslatedShapeSettings(new Vec3(0, 0.5f * cCharacterHeightStanding + cCharacterRadiusStanding, 0), Quat.sIdentity(), new CapsuleShape(0.5f * cCharacterHeightStanding, cCharacterRadiusStanding)).create().get());
	settings.setSupportingVolume (new Plane(Vec3.sAxisY(), -cCharacterRadiusStanding)); // Accept contacts that touch the lower sphere of the capsule
	mCharacter = new CharacterVirtual(settings, plus(cShipInitialPosition ,new Vec3(0, cSpaceShipHeight, 0)), Quat.sIdentity(), 0, mPhysicsSystem).toRef();
	mCharacter.getPtr().setListener(new CustomCharacterContactListener() {
            public void onAdjustBodyVelocity(long characterVa, long body2Va, float[] velocities) {OnAdjustBodyVelocity(new CharacterVirtual(characterVa, mPhysicsSystem), new Body(body2Va), new Vec3(), new Vec3());}
        });

	// Create the space ship
	StaticCompoundShapeSettings compound=new StaticCompoundShapeSettings();
	compound.setEmbedded();
	for (float h = cSpaceShipRingHeight; h < cSpaceShipHeight; h += cSpaceShipRingHeight)
		compound.addShape(Vec3.sZero(), Quat.sIdentity(), new CylinderShape(h, sqrt(square(cSpaceShipRadius) - square(cSpaceShipRadius - cSpaceShipHeight - cSpaceShipRingHeight + h))));
	mSpaceShip = mBodyInterface.createAndAddBody(new BodyCreationSettings(compound, cShipInitialPosition, Quat.sIdentity(), EMotionType.Kinematic, Layers.MOVING), EActivation.Activate);
	mSpaceShipPrevTransform = mBodyInterface.getCenterOfMassTransform(mSpaceShip);
}
/*TODO

void CharacterSpaceShipTest::ProcessInput(const ProcessInputParams &inParams)
{
	// Determine controller input
	Vec3 control_input = Vec3::sZero();
	if (inParams.mKeyboard->IsKeyPressed(DIK_LEFT))		control_input.SetZ(-1);
	if (inParams.mKeyboard->IsKeyPressed(DIK_RIGHT))	control_input.SetZ(1);
	if (inParams.mKeyboard->IsKeyPressed(DIK_UP))		control_input.SetX(1);
	if (inParams.mKeyboard->IsKeyPressed(DIK_DOWN))		control_input.SetX(-1);
	if (control_input != Vec3::sZero())
		control_input = control_input.Normalized();

	// Calculate the desired velocity in local space to the ship based on the camera forward
	RMat44 new_space_ship_transform = mBodyInterface->GetCenterOfMassTransform(mSpaceShip);
	Vec3 cam_fwd = new_space_ship_transform.GetRotation().Multiply3x3Transposed(inParams.mCameraState.mForward);
	cam_fwd.SetY(0.0f);
	cam_fwd = cam_fwd.NormalizedOr(Vec3::sAxisX());
	Quat rotation = Quat::sFromTo(Vec3::sAxisX(), cam_fwd);
	control_input = rotation * control_input;

	// Smooth the player input in local space to the ship
	mDesiredVelocity = 0.25f * control_input * cCharacterSpeed + 0.75f * mDesiredVelocity;

	// Check actions
	mJump = inParams.mKeyboard->IsKeyPressedAndTriggered(DIK_RCONTROL, mWasJump);
}
*/

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	// Update scene time
	mTime += inParams.mDeltaTime;

	// Update the character so it stays relative to the space ship
	RMat44 new_space_ship_transform = mBodyInterface.getCenterOfMassTransform(mSpaceShip);
	mCharacter.setPosition(star(star(new_space_ship_transform , mSpaceShipPrevTransform.inversed()) , mCharacter.getPosition()));

	// Update the character rotation and its up vector to match the new up vector of the ship
	mCharacter.setUp(new_space_ship_transform.getAxisY());
	mCharacter.setRotation(new_space_ship_transform.getQuaternion());

	// Draw character pre update (the sim is also drawn pre update)
	// Note that we have first updated the position so that it matches the new position of the ship
if(implementsDebugRendering()){
	mCharacter.getShape().draw(mDebugRenderer, mCharacter.getCenterOfMassTransform(), Vec3.sReplicate(1.0f), Color.sGreen, false, true);
} // JPH_DEBUG_RENDERER

	// Determine new character velocity
	Vec3 current_vertical_velocity =star( mCharacter.getLinearVelocity().dot(mSpaceShipPrevTransform.getAxisY()) , mCharacter.getUp());
	Vec3 ground_velocity = mCharacter.getGroundVelocity();
	Vec3 new_velocity;
	if (mCharacter.getGroundState() == EGroundState.OnGround // If on ground
		&& (current_vertical_velocity.getY() - ground_velocity.getY()) < 0.1f) // And not moving away from ground
	{
		// Assume velocity of ground when on ground
		new_velocity = ground_velocity;

		// Jump
		if (mJump)
			plusEquals(new_velocity , star(cJumpSpeed , mCharacter.getUp()));
	}
	else
		new_velocity = current_vertical_velocity;

	// Gravity always acts relative to the ship
	Vec3 gravity = new_space_ship_transform.multiply3x3(mPhysicsSystem.getGravity());
	plusEquals(new_velocity , star(gravity , inParams.mDeltaTime));

	// Transform player input to world space
	plusEquals(new_velocity , new_space_ship_transform.multiply3x3(mDesiredVelocity));

	// Update character velocity
	mCharacter.setLinearVelocity(new_velocity);

	// Update the character position
	ExtendedUpdateSettings update_settings=new ExtendedUpdateSettings();
	mCharacter.extendedUpdate(inParams.mDeltaTime,
		gravity,
		update_settings,
		mPhysicsSystem.getDefaultBroadPhaseLayerFilter(Layers.MOVING),
		mPhysicsSystem.getDefaultLayerFilter(Layers.MOVING),
		new BodyFilter(){ },
		new ShapeFilter(){ },
		mTempAllocator);

	// Update previous transform
	mSpaceShipPrevTransform = new_space_ship_transform;

	// Calculate new velocity
	UpdateShipVelocity();
}

void UpdateShipVelocity()
{
	// Make it a rocky ride...
	mSpaceShipLinearVelocity =star(new Vec3(sin(mTime), 0, cos(mTime)) , 50.0f);
	mSpaceShipAngularVelocity =star(new Vec3(sin(2.0f * mTime), 1, cos(2.0f * mTime)) , 0.5f);

	mBodyInterface.setLinearAndAngularVelocity(mSpaceShip, mSpaceShipLinearVelocity, mSpaceShipAngularVelocity);
}

public void GetInitialCamera(CameraState ioState)
{
	// This will become the local space offset, look down the x axis and slightly down
	ioState.mPos = RVec3.sZero();
	ioState.mForward =new Vec3(10.0f, -2.0f, 0).normalized();
}

RMat44 GetCameraPivot(float inCameraHeading, float inCameraPitch)
{
	// Pivot is center of character + distance behind based on the heading and pitch of the camera
	Vec3 fwd =new Vec3(cos(inCameraPitch) * cos(inCameraHeading), sin(inCameraPitch), cos(inCameraPitch) * sin(inCameraHeading));
	return RMat44.sTranslation(minus(plus(mCharacter.getPosition() ,new Vec3(0, cCharacterHeightStanding + cCharacterRadiusStanding, 0)) , star(5.0f , fwd)));
}

public void SaveState(StateRecorder inStream)
{
	mCharacter.saveState(inStream);

	inStream.write(mTime);
	inStream.write(mSpaceShipPrevTransform);
}

public void RestoreState(StateRecorder inStream)
{
	mCharacter.restoreState(inStream);

	mTime = inStream.readFloat(mTime);
	inStream.readRMat44(mSpaceShipPrevTransform);

	// Calculate new velocity
	UpdateShipVelocity();
}

void SaveInputState(StateRecorder inStream)
{
	inStream.write(mDesiredVelocity);
	inStream.write(mJump);
}

void RestoreInputState(StateRecorder inStream)
{
	inStream.readVec3(mDesiredVelocity);
	mJump=inStream.readBoolean(mJump);
}

void OnAdjustBodyVelocity(ConstCharacterVirtual inCharacter, ConstBody inBody2, Vec3 ioLinearVelocity, Vec3 ioAngularVelocity)
{
	// Cancel out velocity of space ship, we move relative to this which means we don't feel any of the acceleration of the ship (= engage inertial dampeners!)
	minusEquals(ioLinearVelocity , mSpaceShipLinearVelocity);
	minusEquals(ioAngularVelocity , mSpaceShipAngularVelocity);
}
}
