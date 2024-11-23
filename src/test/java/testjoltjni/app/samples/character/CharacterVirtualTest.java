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
import com.github.stephengold.joltjni.readonly.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import static com.github.stephengold.joltjni.std.Std.*;
/**
 * A line-for-line Java translation of the Jolt Physics virtual-character test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Character/CharacterVirtualTest.cpp
 */
public class CharacterVirtualTest extends CharacterBaseTest{
static boolean sEnableCharacterInertia = true;
static EBackFaceMode sBackFaceMode = EBackFaceMode.CollideWithBackFaces;
static float sUpRotationX = 0;
static float sUpRotationZ = 0;
static float sMaxSlopeAngle = degreesToRadians(45.0f);
static float sMaxStrength = 100.0f;
static float sCharacterPadding = 0.02f;
static float sPenetrationRecoverySpeed = 1.0f;
static float sPredictiveContactDistance = 0.1f;
static boolean sEnableWalkStairs = true;
static boolean sEnableStickToFloor = true;
static boolean sEnhancedInternalEdgeRemoval = false;
static boolean sCreateInnerBody = false;
static boolean sPlayerCanPushOtherCharacters = true;
static boolean sOtherCharactersCanPushPlayer = true;
CharacterVirtualRef mCharacter=new CharacterVirtualRef();
Vec3 mDesiredVelocity = Vec3.sZero();
boolean mAllowSliding = false;
RVec3 GetCharacterPosition(){return mCharacter.getPosition();}

public void Initialize()
{
	super.Initialize();

	// Create 'player' character
	CharacterVirtualSettings settings = new CharacterVirtualSettings();
	settings.setMaxSlopeAngle ( sMaxSlopeAngle);
	settings.setMaxStrength ( sMaxStrength);
	settings.setShape ( mStandingShape);
	settings.setBackFaceMode ( sBackFaceMode);
	settings.setCharacterPadding ( sCharacterPadding);
	settings.setPenetrationRecoverySpeed ( sPenetrationRecoverySpeed);
	settings.setPredictiveContactDistance ( sPredictiveContactDistance);
	settings.setSupportingVolume (new Plane(Vec3.sAxisY(), -cCharacterRadiusStanding)); // Accept contacts that touch the lower sphere of the capsule
	settings.setEnhancedInternalEdgeRemoval ( sEnhancedInternalEdgeRemoval);
	settings.setInnerBodyShape ( sCreateInnerBody? mInnerStandingShape : null);
	settings.setInnerBodyLayer ( Layers.MOVING);
	mCharacter = new CharacterVirtual(settings, RVec3.sZero(), Quat.sIdentity(), 0, mPhysicsSystem).toRef();
	mCharacter.getPtr().setCharacterVsCharacterCollision(mCharacterVsCharacterCollision);
	mCharacterVsCharacterCollision.add(mCharacter);

	// Install contact listener for all characters
	for (CharacterVirtual character : mCharacterVsCharacterCollision.getCharactersAsList())
		character.setListener(new CustomCharacterContactListener() {
		    public void onContactAdded(long characterVa, long bodyId2Va, long subShapeId2Va, double contactLocationX, double contactLocationY,
			   double contactLocationZ, float contactNormalX, float contactNormalY, float contactNormalZ, long settingsVa) {
			RVec3Arg inContactPosition=new RVec3(contactLocationX,contactLocationY,contactLocationZ);
			Vec3Arg inContactNormal=new Vec3(contactNormalX,contactNormalY,contactNormalZ);
			CharacterVirtualTest.this.OnContactAdded(new CharacterVirtual(characterVa), new BodyId(bodyId2Va), new SubShapeId(subShapeId2Va), inContactPosition, inContactNormal, new CharacterContactSettings(settingsVa));
		    }});
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	super.PrePhysicsUpdate(inParams);

	// Draw character pre update (the sim is also drawn pre update)
	RMat44 com = mCharacter.getCenterOfMassTransform();
	RMat44 world_transform = mCharacter.getWorldTransform();
if(implementsDebugRendering()){
	mCharacter.getShape().draw(mDebugRenderer, com, Vec3.sReplicate(1.0f), Color.sGreen, false, true);
} // JPH_DEBUG_RENDERER

	// Draw shape including padding (only implemented for capsules right now)
	if (((RotatedTranslatedShape)mCharacter.getShape()).getInnerShape().getSubType() == EShapeSubType.Capsule)
	{
		if (mCharacter.getShape() == mStandingShape.getPtr())
			mDebugRenderer.drawCapsule(com, 0.5f * cCharacterHeightStanding, cCharacterRadiusStanding + mCharacter.getCharacterPadding(), Color.sGrey, ECastShadow.Off, EDrawMode.Wireframe);
		else
			mDebugRenderer.drawCapsule(com, 0.5f * cCharacterHeightCrouching, cCharacterRadiusCrouching + mCharacter.getCharacterPadding(), Color.sGrey, ECastShadow.Off, EDrawMode.Wireframe);
	}

	// Remember old position
	RVec3 old_position = mCharacter.getPosition();

	// Settings for our update function
	ExtendedUpdateSettings update_settings=new ExtendedUpdateSettings();
	if (!sEnableStickToFloor)
		update_settings.setStickToFloorStepDown ( Vec3.sZero());
	else
		update_settings.setStickToFloorStepDown ( star(minus(mCharacter.getUp()) , update_settings.getStickToFloorStepDown().length()));
	if (!sEnableWalkStairs)
		update_settings.setWalkStairsStepUp ( Vec3.sZero());
	else
		update_settings.setWalkStairsStepUp ( star(mCharacter.getUp() , update_settings.getWalkStairsStepUp().length()));

	// Update the character position
	mCharacter.extendedUpdate(inParams.mDeltaTime,
		star(minus(mCharacter.getUp()) , mPhysicsSystem.getGravity().length()),
		update_settings,
		mPhysicsSystem.getDefaultBroadPhaseLayerFilter(Layers.MOVING),
		mPhysicsSystem.getDefaultLayerFilter(Layers.MOVING),
		new BodyFilter(){ },
		new ShapeFilter(){ },
		mTempAllocator);

	// Calculate effective velocity
	RVec3 new_position = mCharacter.getPosition();
	Vec3 velocity = slash(minus(new_position , old_position) , inParams.mDeltaTime).toVec3();

	// Draw state of character
	DrawCharacterState(mCharacter, world_transform, velocity);

	// Draw labels on ramp blocks
	for (int i = 0; i < mRampBlocks.size(); ++i)
		mDebugRenderer.drawText3D(mBodyInterface.getPosition(mRampBlocks.get(i)), String.format("PushesPlayer: %s\nPushable: %s", (i & 1) != 0? "True" : "False", (i & 2) != 0? "True" : "False"), Color.sWhite, 0.25f);
}

void HandleInput(Vec3Arg inMovementDirection, boolean inJump, boolean inSwitchStance, float inDeltaTime)
{
	boolean player_controls_horizontal_velocity = sControlMovementDuringJump || mCharacter.isSupported();
	if (player_controls_horizontal_velocity)
	{
		// Smooth the player input
		mDesiredVelocity = sEnableCharacterInertia? plus(star(star(0.25f , inMovementDirection) , sCharacterSpeed) , star(0.75f , mDesiredVelocity)) : star(inMovementDirection , sCharacterSpeed);

		// True if the player intended to move
		mAllowSliding = !inMovementDirection.isNearZero();
	}
	else
	{
		// While in air we allow sliding
		mAllowSliding = true;
	}

	// Update the character rotation and its up vector to match the up vector set by the user settings
	Quat character_up_rotation = Quat.sEulerAngles(new Vec3(sUpRotationX, 0, sUpRotationZ));
	mCharacter.setUp(character_up_rotation.rotateAxisY());
	mCharacter.setRotation(character_up_rotation);

	// A cheaper way to update the character's ground velocity,
	// the platforms that the character is standing on may have changed velocity
	mCharacter.updateGroundVelocity();

	// Determine new basic velocity
	Vec3 current_vertical_velocity = star(mCharacter.getLinearVelocity().dot(mCharacter.getUp()) , mCharacter.getUp());
	Vec3 ground_velocity = mCharacter.getGroundVelocity();
	Vec3 new_velocity;
	boolean moving_towards_ground = (current_vertical_velocity.getY() - ground_velocity.getY()) < 0.1f;
	if (mCharacter.getGroundState() == EGroundState.OnGround	// If on ground
		&& (sEnableCharacterInertia?
			moving_towards_ground													// Inertia enabled: And not moving away from ground
			: !mCharacter.isSlopeTooSteep(mCharacter.getGroundNormal())))			// Inertia disabled: And not on a slope that is too steep
	{
		// Assume velocity of ground when on ground
		new_velocity = ground_velocity;

		// Jump
		if (inJump && moving_towards_ground)
			plusEquals(new_velocity , star(sJumpSpeed , mCharacter.getUp()));
	}
	else
		new_velocity = current_vertical_velocity;

	// Gravity
	plusEquals(new_velocity , star(star(character_up_rotation , mPhysicsSystem.getGravity()) , inDeltaTime));

	if (player_controls_horizontal_velocity)
	{
		// Player input
		plusEquals(new_velocity , star(character_up_rotation , mDesiredVelocity));
	}
	else
	{
		// Preserve horizontal velocity
		Vec3 current_horizontal_velocity = minus(mCharacter.getLinearVelocity() , current_vertical_velocity);
		plusEquals(new_velocity , current_horizontal_velocity);
	}

	// Update character velocity
	mCharacter.setLinearVelocity(new_velocity);

	// Stance switch
	if (inSwitchStance)
	{
		boolean is_standing = mCharacter.getShape() == mStandingShape.getPtr();
		ConstShape shape = is_standing? mCrouchingShape : mStandingShape;
		if (mCharacter.setShape(shape, 1.5f * mPhysicsSystem.getPhysicsSettings().getPenetrationSlop(), mPhysicsSystem.getDefaultBroadPhaseLayerFilter(Layers.MOVING), mPhysicsSystem.getDefaultLayerFilter(Layers.MOVING), new BodyFilter(){ }, new ShapeFilter(){ }, mTempAllocator))
		{
			ConstShape inner_shape = is_standing? mInnerCrouchingShape : mInnerStandingShape;
			mCharacter.setInnerBodyShape(inner_shape);
		}
	}
}
/*TODO

void CharacterVirtualTest::AddCharacterMovementSettings(DebugUI* inUI, UIElement* inSubMenu)
{
	inUI->CreateCheckBox(inSubMenu, "Enable Character Inertia", sEnableCharacterInertia, [](UICheckBox.EState inState) { sEnableCharacterInertia = inState == UICheckBox.STATE_CHECKED; });
	inUI->CreateCheckBox(inSubMenu, "Player Can Push Other Virtual Characters", sPlayerCanPushOtherCharacters, [](UICheckBox.EState inState) { sPlayerCanPushOtherCharacters = inState == UICheckBox.STATE_CHECKED; });
	inUI->CreateCheckBox(inSubMenu, "Other Virtual Characters Can Push Player", sOtherCharactersCanPushPlayer, [](UICheckBox.EState inState) { sOtherCharactersCanPushPlayer = inState == UICheckBox.STATE_CHECKED; });
}

void CharacterVirtualTest::AddConfigurationSettings(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI->CreateComboBox(inSubMenu, "Back Face Mode", { "Ignore", "Collide" }, (int)sBackFaceMode, [=](int inItem) { sBackFaceMode = (EBackFaceMode)inItem; });
	inUI->CreateSlider(inSubMenu, "Up Rotation X (degrees)", RadiansToDegrees(sUpRotationX), -90.0f, 90.0f, 1.0f, [](float inValue) { sUpRotationX = DegreesToRadians(inValue); });
	inUI->CreateSlider(inSubMenu, "Up Rotation Z (degrees)", RadiansToDegrees(sUpRotationZ), -90.0f, 90.0f, 1.0f, [](float inValue) { sUpRotationZ = DegreesToRadians(inValue); });
	inUI->CreateSlider(inSubMenu, "Max Slope Angle (degrees)", RadiansToDegrees(sMaxSlopeAngle), 0.0f, 90.0f, 1.0f, [](float inValue) { sMaxSlopeAngle = DegreesToRadians(inValue); });
	inUI->CreateSlider(inSubMenu, "Max Strength (N)", sMaxStrength, 0.0f, 500.0f, 1.0f, [](float inValue) { sMaxStrength = inValue; });
	inUI->CreateSlider(inSubMenu, "Character Padding", sCharacterPadding, 0.01f, 0.5f, 0.01f, [](float inValue) { sCharacterPadding = inValue; });
	inUI->CreateSlider(inSubMenu, "Penetration Recovery Speed", sPenetrationRecoverySpeed, 0.0f, 1.0f, 0.05f, [](float inValue) { sPenetrationRecoverySpeed = inValue; });
	inUI->CreateSlider(inSubMenu, "Predictive Contact Distance", sPredictiveContactDistance, 0.01f, 1.0f, 0.01f, [](float inValue) { sPredictiveContactDistance = inValue; });
	inUI->CreateCheckBox(inSubMenu, "Enable Walk Stairs", sEnableWalkStairs, [](UICheckBox.EState inState) { sEnableWalkStairs = inState == UICheckBox.STATE_CHECKED; });
	inUI->CreateCheckBox(inSubMenu, "Enable Stick To Floor", sEnableStickToFloor, [](UICheckBox.EState inState) { sEnableStickToFloor = inState == UICheckBox.STATE_CHECKED; });
	inUI->CreateCheckBox(inSubMenu, "Enhanced Internal Edge Removal", sEnhancedInternalEdgeRemoval, [](UICheckBox.EState inState) { sEnhancedInternalEdgeRemoval = inState == UICheckBox.STATE_CHECKED; });
	inUI->CreateCheckBox(inSubMenu, "Create Inner Body", sCreateInnerBody, [](UICheckBox.EState inState) { sCreateInnerBody = inState == UICheckBox.STATE_CHECKED; });
}
*/

void SaveState(StateRecorder inStream)
{
	super.SaveState(inStream);

	mCharacter.saveState(inStream);

	boolean is_standing = mCharacter.getShape() == mStandingShape.getPtr();
	inStream.write(is_standing);

	inStream.write(mAllowSliding);
	inStream.write(mDesiredVelocity);
}

void RestoreState(StateRecorder inStream)
{
	super.RestoreState(inStream);

	mCharacter.restoreState(inStream);

	boolean is_standing = mCharacter.getShape() == mStandingShape.getPtr(); // Initialize variable for validation mode
	is_standing=inStream.readBoolean(is_standing);
	ConstShape shape = is_standing? mStandingShape : mCrouchingShape;
	mCharacter.setShape(shape, FLT_MAX, new BroadPhaseLayerFilter(){ }, new ObjectLayerFilter(){ }, new BodyFilter(){ }, new ShapeFilter(){ }, mTempAllocator);
	ConstShape inner_shape = is_standing? mInnerStandingShape : mInnerCrouchingShape;
	mCharacter.setInnerBodyShape(inner_shape);

	mAllowSliding=inStream.readBoolean(mAllowSliding);
	inStream.readVec3(mDesiredVelocity);
}

void OnAdjustBodyVelocity(ConstCharacterVirtual inCharacter, ConstBody inBody2, Vec3 ioLinearVelocity, Vec3 ioAngularVelocity)
{
	// Apply artificial velocity to the character when standing on the conveyor belt
	if (inBody2.getId() == mConveyorBeltBody)
		plusEquals(ioLinearVelocity ,new Vec3(0, 0, 2));
}

void OnContactAdded(ConstCharacterVirtual inCharacter, ConstBodyId inBodyID2, ConstSubShapeId inSubShapeID2, RVec3Arg inContactPosition, Vec3Arg inContactNormal, CharacterContactSettings ioSettings)
{
	// Draw a box around the character when it enters the sensor
	if (inBodyID2 == mSensorBody)
	{
		AaBox box = inCharacter.getShape().getWorldSpaceBounds(inCharacter.getCenterOfMassTransform(), Vec3.sReplicate(1.0f));
		mDebugRenderer.drawBox(box, Color.sGreen, ECastShadow.Off, EDrawMode.Wireframe);
	}

	// Dynamic boxes on the ramp go through all permutations
	int i = mRampBlocks.find(inBodyID2);
	if (i != mRampBlocks.size())
	{
		int index = i;
		ioSettings.setCanPushCharacter ( (index & 1) != 0);
		ioSettings.setCanReceiveImpulses ( (index & 2) != 0);
	}

	// If we encounter an object that can push the player, enable sliding
	if (inCharacter == mCharacter.getPtr()
		&& ioSettings.getCanPushCharacter()
		&& mPhysicsSystem.getBodyInterface().getMotionType(inBodyID2) != EMotionType.Static)
		mAllowSliding = true;
}

void OnCharacterContactAdded(ConstCharacterVirtual inCharacter, ConstCharacterVirtual inOtherCharacter, ConstSubShapeId inSubShapeID2, RVec3Arg inContactPosition, Vec3Arg inContactNormal, CharacterContactSettings ioSettings)
{
	// Characters can only be pushed in their own update
	if (sPlayerCanPushOtherCharacters)
		ioSettings.setCanPushCharacter ( sOtherCharactersCanPushPlayer || inOtherCharacter == mCharacter.getPtr());
	else if (sOtherCharactersCanPushPlayer)
		ioSettings.setCanPushCharacter ( inCharacter == mCharacter.getPtr());
	else
		ioSettings.setCanPushCharacter ( false);

	// If the player can be pushed by the other virtual character, we allow sliding
	if (inCharacter == mCharacter.getPtr() && ioSettings.getCanPushCharacter())
		mAllowSliding = true;
}

void OnContactSolve(ConstCharacterVirtual inCharacter, ConstBodyId inBodyID2, ConstSubShapeId inSubShapeID2, RVec3Arg inContactPosition, Vec3Arg inContactNormal, Vec3Arg inContactVelocity, ConstPhysicsMaterial inContactMaterial, Vec3Arg inCharacterVelocity, Vec3 ioNewCharacterVelocity)
{
	// Ignore callbacks for other characters than the player
	if (inCharacter != mCharacter.getPtr())
		return;

	// Don't allow the player to slide down static not-too-steep surfaces when not actively moving and when not on a moving platform
	if (!mAllowSliding && inContactVelocity.isNearZero() && !inCharacter.isSlopeTooSteep(inContactNormal))
		ioNewCharacterVelocity = Vec3.sZero();
}
}
