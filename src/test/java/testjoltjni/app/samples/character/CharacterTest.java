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
import testjoltjni.app.samples.*;

/**
 * A line-for-line Java translation of the Jolt Physics character test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Character/CharacterTest.cpp
 */

public class CharacterTest extends CharacterBaseTest{
static final float cCollisionTolerance = 0.05f;
CharacterRef mCharacter;
RVec3 GetCharacterPosition(){return mCharacter.getPosition();}

public void Initialize()
{
	super.Initialize();

	// Create 'player' character
	CharacterSettings settings = new CharacterSettings();
	settings.setMaxSlopeAngle ( Jolt.degreesToRadians(45.0f));
	settings.setLayer ( Layers.MOVING);
	settings.setShape ( mStandingShape);
	settings.setFriction ( 0.5f);
	settings.setSupportingVolume(new Plane(Vec3.sAxisY(), -cCharacterRadiusStanding)); // Accept contacts that touch the lower sphere of the capsule
	mCharacter = new com.github.stephengold.joltjni.Character(settings, RVec3.sZero(), Quat.sIdentity(), 0, mPhysicsSystem).toRef();
	mCharacter.getPtr().addToPhysicsSystem(EActivation.Activate);
}

void PrePhysicsUpdate(PreUpdateParams inParams)
{
	super.PrePhysicsUpdate(inParams);

	// Draw state of character
	DrawCharacterState(mCharacter, mCharacter.getWorldTransform(), mCharacter.getLinearVelocity());
}

public void PostPhysicsUpdate(float inDeltaTime)
{
	// Fetch the new ground properties
	mCharacter.getPtr().postSimulation(cCollisionTolerance);
}

void SaveState(StateRecorder inStream)
{
	super.SaveState(inStream);

	mCharacter.saveState(inStream);

	boolean is_standing = mCharacter.getShape() == mStandingShape;
	inStream.write(is_standing);
}

void RestoreState(StateRecorder inStream)
{
	super.RestoreState(inStream);

	mCharacter.getPtr().restoreState(inStream);

	boolean is_standing = mCharacter.getShape() == mStandingShape; // Initialize variable for validation mode
	is_standing = inStream.readBoolean(is_standing);
	mCharacter.getPtr().setShape(is_standing? mStandingShape : mCrouchingShape, Float.MAX_VALUE);
}

void HandleInput(Vec3Arg inMovementDirection, boolean inJump, boolean inSwitchStance, float inDeltaTime)
{
	// Cancel movement in opposite direction of normal when touching something we can't walk up
	Vec3 movement_direction =new Vec3(inMovementDirection);
	EGroundState ground_state = mCharacter.getGroundState();
	if (ground_state == EGroundState.OnSteepGround
		|| ground_state == EGroundState.NotSupported)
	{
		Vec3 normal = mCharacter.getGroundNormal();
		normal.setY(0.0f);
		float dot = normal.dot(movement_direction);
		if (dot < 0.0f)
			Op.minusEquals(movement_direction , Op.divide(Op.multiply(dot , normal) , normal.lengthSq()));
	}

	// Stance switch
	if (inSwitchStance)
		mCharacter.getPtr().setShape(mCharacter.getShape() == mStandingShape? mCrouchingShape : mStandingShape, 1.5f * mPhysicsSystem.getPhysicsSettings().getPenetrationSlop());

	if (sControlMovementDuringJump || mCharacter.isSupported())
	{
		// Update velocity
		Vec3 current_velocity =new Vec3(mCharacter.getLinearVelocity());
		Vec3 desired_velocity =new Vec3(Op.multiply(sCharacterSpeed , movement_direction));
		if (!desired_velocity.isNearZero() || current_velocity.getY() < 0.0f || !mCharacter.isSupported())
			desired_velocity.setY(current_velocity.getY());
		Vec3 new_velocity =new Vec3(Op.add(Op.multiply(0.75f , current_velocity) , Op.multiply(0.25f , desired_velocity)));

		// Jump
		if (inJump && ground_state == EGroundState.OnGround)
			Op.plusEquals(new_velocity ,new Vec3(0, sJumpSpeed, 0));

		// Update the velocity
		mCharacter.getPtr().setLinearVelocity(new_velocity);
	}
}

void OnContactAdded(ConstBody inBody1, ConstBody inBody2, ConstContactManifold inManifold, ContactSettings ioSettings)
{
	// Draw a box around the character when it enters the sensor
	if (inBody1.getId() == mSensorBody)
		mDebugRenderer.drawBox(inBody2.getWorldSpaceBounds(), Color.sGreen, ECastShadow.Off, EDrawMode.Wireframe);
	else if (inBody2.getId() == mSensorBody)
		mDebugRenderer.drawBox(inBody1.getWorldSpaceBounds(), Color.sGreen, ECastShadow.Off, EDrawMode.Wireframe);
}

void OnContactPersisted(ConstBody inBody1, ConstBody inBody2, ConstContactManifold inManifold, ContactSettings ioSettings)
{
	// Same behavior as contact added
	OnContactAdded(inBody1, inBody2, inManifold, ioSettings);
}
}