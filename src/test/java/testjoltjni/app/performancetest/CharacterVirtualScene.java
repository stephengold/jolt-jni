/*
Copyright (c) 2025 Stephen Gold

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
import com.github.stephengold.joltjni.readonly.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import java.util.*;

/**
 * A line-for-line Java translation of the Jolt-Physics CharacterVirtual
 * performance test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/PerformanceTest/CharacterVirtualScene.h
 */
// A scene that drops a number of virtual characters on a scene and simulates them
class CharacterVirtualScene implements PerformanceTestScene
{
public
	String	GetName()
	{
		return "CharacterVirtual";
	}

	public boolean			Load()
	{
		final int n = 100;
		final float cell_size = 0.5f;
		final float max_height = 2.0f;
		float center = n * cell_size / 2;

		// Create vertices
		final int num_vertices = (n + 1) * (n + 1);
		VertexList vertices=new VertexList();
		vertices.resize(num_vertices);
		for (int x = 0; x <= n; ++x)
			for (int z = 0; z <= n; ++z)
			{
				float height = sin((float)(x) * 20.0f / n) * cos((float)(z) * 20.0f / n);
				vertices.set(z * (n + 1) + x, new Float3(cell_size * x, max_height * height, cell_size * z));
			}

		// Create regular grid of triangles
		final int num_triangles = n * n * 2;
		IndexedTriangleList indices=new IndexedTriangleList();
		indices.resize(num_triangles);
		int next = 0;
		for (int x = 0; x < n; ++x)
			for (int z = 0; z < n; ++z)
			{
				int start = (n + 1) * z + x;

				IndexedTriangle it = indices.get(next++);
				it.setIdx(0,  start);
				it.setIdx(1,  start + n + 1);
				it.setIdx(2, start + 1);

				it = indices.get(next++);
				it.setIdx(0,  start + 1);
				it.setIdx(1,  start + n + 1);
				it.setIdx(2,  start + n + 2);
			}

		// Create mesh
		BodyCreationSettings mesh=new BodyCreationSettings(new MeshShapeSettings(vertices, indices),new RVec3((-center), 0, (-center)), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
		mWorld.add(mesh);

		// Create pyramid stairs
		for (int i = 0; i < 10; ++i)
		{
			float width = 4.0f - 0.4f * i;
			BodyCreationSettings step=new BodyCreationSettings(new BoxShape(new Vec3(width, 0.5f * cStairsStepHeight, width)),new RVec3(-4.0, -1.0 + (i * cStairsStepHeight), 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
			mWorld.add(step);
		}

		// Create wall consisting of vertical pillars
		ShapeRef wall = new BoxShape(new Vec3(0.1f, 2.5f, 0.1f), 0.0f).toRef();
		for (int z = 0; z < 10; ++z)
		{
			BodyCreationSettings bcs=new BodyCreationSettings(wall,new RVec3(2.0, 1.0, 2.0 + 0.2 * z), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING);
			mWorld.add(bcs);
		}

		// Create some dynamic boxes
		ShapeRef box = new BoxShape(Vec3.sReplicate(0.25f)).toRef();
		for (int x = 0; x < 10; ++x)
			for (int z = 0; z < 10; ++z)
			{
				BodyCreationSettings bcs=new BodyCreationSettings(box,new RVec3(4.0 * x - 20.0, 5.0, 4.0 * z - 20.0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
				bcs.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
				bcs.getMassPropertiesOverride().setMass ( 1.0f);
				mWorld.add(bcs);
			}

		return true;
	}

	public void			StartTest(PhysicsSystem inPhysicsSystem, EMotionQuality inMotionQuality)
	{
		// Construct bodies
		BodyInterface bi = inPhysicsSystem.getBodyInterface();
		for (BodyCreationSettings bcs : mWorld)
			if (bcs.getMotionType() == EMotionType.Dynamic)
			{
				bcs.setMotionQuality ( inMotionQuality);
				bi.createAndAddBody(bcs, EActivation.Activate);
			}
			else
				bi.createAndAddBody(bcs, EActivation.DontActivate);

		// Construct characters
		Jolt.sSetNextCharacterId();
		ShapeRefC standing_shape =new RotatedTranslatedShapeSettings(new Vec3(0, 0.5f * cCharacterHeightStanding + cCharacterRadiusStanding, 0), Quat.sIdentity(), new CapsuleShape(0.5f * cCharacterHeightStanding, cCharacterRadiusStanding)).create().get();
		ShapeRefC inner_standing_shape =new RotatedTranslatedShapeSettings(new Vec3(0, 0.5f * cCharacterHeightStanding + cCharacterRadiusStanding, 0), Quat.sIdentity(), new CapsuleShape(0.5f * cInnerShapeFraction * cCharacterHeightStanding, cInnerShapeFraction * cCharacterRadiusStanding)).create().get();
		for (int y = 0; y < cNumCharactersY; ++y)
			for (int x = 0; x < cNumCharactersX; ++x)
			{
				CharacterVirtualSettings settings = new CharacterVirtualSettings();
				settings.setShape ( standing_shape);
				settings.setSupportingVolume (new Plane(Vec3.sAxisY(), -cCharacterRadiusStanding)); // Accept contacts that touch the lower sphere of the capsule
				settings.setInnerBodyShape ( inner_standing_shape);
				settings.setInnerBodyLayer ( Layers.MOVING);
				CharacterVirtual character = new CharacterVirtual(settings,new RVec3(4.0 * x - 20.0, 2.0, 4.0 * y - 20.0), Quat.sIdentity(), 0, inPhysicsSystem);
				character.setCharacterVsCharacterCollision(mCharacterVsCharacterCollision);
				character.setListener(new CustomCharacterContactListener() {
    public void onCharacterContactAdded(long characterVa, long otherCharacterVa, int subShapeId2, double contactLocationX, double contactLocationY,
            double contactLocationZ, float contactNormalX, float contactNormalY, float contactNormalZ, long settingsVa) {
	RVec3Arg inContactPosition=new RVec3(contactLocationX, contactLocationY, contactLocationZ);
	Vec3Arg inContactNormal=new Vec3(contactNormalX, contactNormalY, contactNormalZ);
	CharacterVirtualScene.this.OnCharacterContactAdded(new CharacterVirtual(characterVa, mPhysicsSystem), new CharacterVirtual(otherCharacterVa, mPhysicsSystem), subShapeId2, inContactPosition, inContactNormal, new CharacterContactSettings(settingsVa));
    }
    public void onCharacterContactPersisted(long characterVa, long otherCharacterVa, int subShapeId2, double contactLocationX, double contactLocationY,
            double contactLocationZ, float contactNormalX, float contactNormalY, float contactNormalZ, long settingsVa) {
	RVec3Arg inContactPosition=new RVec3(contactLocationX, contactLocationY, contactLocationZ);
	Vec3Arg inContactNormal=new Vec3(contactNormalX, contactNormalY, contactNormalZ);
	CharacterVirtualScene.this.OnCharacterContactPersisted(new CharacterVirtual(characterVa, mPhysicsSystem), new CharacterVirtual(otherCharacterVa, mPhysicsSystem), subShapeId2, inContactPosition, inContactNormal, new CharacterContactSettings(settingsVa));
    }
    public void onCharacterContactRemoved(long characterVa, int otherCharacterId, int subShapeId2) {
	CharacterVirtualScene.this.OnCharacterContactRemoved(new CharacterVirtual(characterVa, mPhysicsSystem), otherCharacterId, subShapeId2);
    }
    public void onContactAdded(long characterVa, int bodyId2, int subShapeId2, double contactLocationX, double contactLocationY,
            double contactLocationZ, float contactNormalX, float contactNormalY, float contactNormalZ, long settingsVa) {
	RVec3Arg inContactPosition=new RVec3(contactLocationX, contactLocationY, contactLocationZ);
	Vec3Arg inContactNormal=new Vec3(contactNormalX, contactNormalY, contactNormalZ);
	CharacterVirtualScene.this.OnContactAdded(new CharacterVirtual(characterVa, mPhysicsSystem), bodyId2, subShapeId2, inContactPosition, inContactNormal, new CharacterContactSettings(settingsVa));
    }
    public void onContactPersisted(long characterVa, int bodyId2, int subShapeId2, double contactLocationX, double contactLocationY,
            double contactLocationZ, float contactNormalX, float contactNormalY, float contactNormalZ, long settingsVa) {
	RVec3Arg inContactPosition=new RVec3(contactLocationX, contactLocationY, contactLocationZ);
	Vec3Arg inContactNormal=new Vec3(contactNormalX, contactNormalY, contactNormalZ);
	CharacterVirtualScene.this.OnContactPersisted(new CharacterVirtual(characterVa, mPhysicsSystem), bodyId2, subShapeId2, inContactPosition, inContactNormal, new CharacterContactSettings(settingsVa));
    }
    public void onContactRemoved(long characterVa, int bodyId2, int subShapeId2) {
	CharacterVirtualScene.this.OnContactRemoved(new CharacterVirtual(characterVa, mPhysicsSystem), bodyId2, subShapeId2);
    }
                                });
				mCharacters.add(character.toRef());
				mCharacterVsCharacterCollision.add(character.toRef());
			}

		// Start at time 0
		mTime = 0.0f;
		mHash = hashBytes(0L, 0);
	}

	public void			UpdateTest(PhysicsSystem inPhysicsSystem, TempAllocator ioTempAllocator, float inDeltaTime)
	{
		// Change direction every 2 seconds
		mTime += inDeltaTime;
		long count = (long)(mTime / 2.0f) * cNumCharactersX * cNumCharactersY;

		for (CharacterVirtualRef ch : mCharacters)
		{
			// Calculate new vertical velocity
			Vec3 new_velocity;
			if (ch.getGroundState() == EGroundState.OnGround	// If on ground
				&& ch.getLinearVelocity().getY() < 0.1f)						// And not moving away from ground
				new_velocity = Vec3.sZero();
			else
				new_velocity = star(ch.getLinearVelocity() ,new Vec3(0, 1, 0));
			plusEquals(new_velocity , star(inPhysicsSystem.getGravity() , inDeltaTime));

			// Deterministic random input
			long hash =   (count);
			int x = (int)(hash % 10);
			int y = (int)((hash / 10) % 10);
			int speed = (int)((hash / 100) % 10);

			// Determine target position
			RVec3 target =new RVec3(4.0 * x - 20.0, 5.0, 4.0 * y - 20.0);

			// Determine new character velocity
			Vec3 direction =new Vec3(minus(target , ch.getPosition())).normalizedOr(Vec3.sZero());
			direction.setY(0);
			plusEquals(new_velocity , star((5.0f + 0.5f * speed) , direction));
			ch.setLinearVelocity(new_velocity);

			// Update the character position
			ExtendedUpdateSettings update_settings=new ExtendedUpdateSettings();
			ch.extendedUpdate(inDeltaTime,
				inPhysicsSystem.getGravity(),
				update_settings,
				inPhysicsSystem.getDefaultBroadPhaseLayerFilter(Layers.MOVING),
				inPhysicsSystem.getDefaultLayerFilter(Layers.MOVING),
				new BodyFilter(){ },
				new ShapeFilter(){ },
				ioTempAllocator);

			++count;
		}
	}

	public long			UpdateHash(long ioHash)
	{
		// Hash the contact callback hash
		ioHash=hashCombine(ioHash, mHash);

		// Hash the state of all characters
		for (CharacterVirtualRef ch : mCharacters)
			ioHash=hashCombine(ioHash, ch.getPosition());
		return ioHash;
	}

	public void			StopTest(PhysicsSystem inPhysicsSystem)
	{
		for (CharacterVirtualRef ch : mCharacters)
			mCharacterVsCharacterCollision.remove(ch);
		mCharacters.clear();
	}

	// See: CharacterContactListener
	void			OnContactAdded(ConstCharacterVirtual inCharacter, int inBodyID2, int inSubShapeID2, RVec3Arg inContactPosition, Vec3Arg inContactNormal, CharacterContactSettings ioSettings)
	{
		mHash=hashCombine(mHash, 1);
		mHash=hashCombine(mHash, inCharacter.getId());
		mHash=hashCombine(mHash, inBodyID2);
		mHash=hashCombine(mHash, inSubShapeID2);
		mHash=hashCombine(mHash, inContactPosition);
		mHash=hashCombine(mHash, inContactNormal);
	}
	void			OnContactPersisted(ConstCharacterVirtual inCharacter, int inBodyID2, int inSubShapeID2, RVec3Arg inContactPosition, Vec3Arg inContactNormal, CharacterContactSettings ioSettings)
	{
		mHash=hashCombine(mHash, 2);
		mHash=hashCombine(mHash, inCharacter.getId());
		mHash=hashCombine(mHash, inBodyID2);
		mHash=hashCombine(mHash, inSubShapeID2);
		mHash=hashCombine(mHash, inContactPosition);
		mHash=hashCombine(mHash, inContactNormal);
	}
	void			OnContactRemoved(ConstCharacterVirtual inCharacter, int inBodyID2, int inSubShapeID2)
	{
		mHash=hashCombine(mHash, 3);
		mHash=hashCombine(mHash, inCharacter.getId());
		mHash=hashCombine(mHash, inBodyID2);
		mHash=hashCombine(mHash, inSubShapeID2);
	}
	void			OnCharacterContactAdded(ConstCharacterVirtual inCharacter, ConstCharacterVirtual inOtherCharacter, int inSubShapeID2, RVec3Arg inContactPosition, Vec3Arg inContactNormal, CharacterContactSettings ioSettings)
	{
		mHash=hashCombine(mHash, 4);
		mHash=hashCombine(mHash, inCharacter.getId());
		mHash=hashCombine(mHash, inOtherCharacter.getId());
		mHash=hashCombine(mHash, inSubShapeID2);
		mHash=hashCombine(mHash, inContactPosition);
		mHash=hashCombine(mHash, inContactNormal);
	}
	void			OnCharacterContactPersisted(ConstCharacterVirtual inCharacter, ConstCharacterVirtual inOtherCharacter, int inSubShapeID2, RVec3Arg inContactPosition, Vec3Arg inContactNormal, CharacterContactSettings ioSettings)
	{
		mHash=hashCombine(mHash, 5);
		mHash=hashCombine(mHash, inCharacter.getId());
		mHash=hashCombine(mHash, inOtherCharacter.getId());
		mHash=hashCombine(mHash, inSubShapeID2);
		mHash=hashCombine(mHash, inContactPosition);
		mHash=hashCombine(mHash, inContactNormal);
	}
	void			OnCharacterContactRemoved(ConstCharacterVirtual inCharacter, int inOtherCharacterID, int inSubShapeID2)
	{
		mHash=hashCombine(mHash, 6);
		mHash=hashCombine(mHash, inCharacter.getId());
		mHash=hashCombine(mHash, inOtherCharacterID);
		mHash=hashCombine(mHash, inSubShapeID2);
	}

private
	static final int	cNumCharactersX = 10;
	static final int	cNumCharactersY = 10;
	static final float	cCharacterHeightStanding = 1.35f;
	static final float	cCharacterRadiusStanding = 0.3f;
	static final float	cInnerShapeFraction = 0.9f;
	static final float	cStairsStepHeight = 0.3f;

	float					mTime = 0.0f;
	long					mHash = 0;
	List<BodyCreationSettings> mWorld=new ArrayList<>();
	List<CharacterVirtualRef> mCharacters=new ArrayList<>();
	CharacterVsCharacterCollisionSimple mCharacterVsCharacterCollision=new CharacterVsCharacterCollisionSimple();
        PhysicsSystem mPhysicsSystem;
};