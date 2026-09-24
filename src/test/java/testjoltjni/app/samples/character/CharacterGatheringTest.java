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
package testjoltjni.app.samples.character;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import java.util.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt-Physics character-gathering
 * test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Character/CharacterGatheringTest.cpp
 */
public class CharacterGatheringTest extends Test{
int cNumCharactersX=20, cNumCharactersY=20;
float cCharacterHeightStanding=1.35f, cCharacterRadiusStanding=.3f;
float cCollisionTolerance=.05f, sCharacterSpeed=6f;
List<CharacterRef> mCharacters=new ArrayList<>(400);

public void Cleanup()
{
	for (CharacterRef c : mCharacters)
		c.removeFromPhysicsSystem();
	mCharacters.clear();
}

public void Initialize()
{
	final int n = 128;
	final float cell_size = 1.0f;
	final float max_height = 5.0f;

	// Create height field
	float[] terrain;
	terrain=new float[n * n];
	for (int y = 0; y < n; ++y)
		for (int x = 0; x < n; ++x)
			terrain[y * n + x] = max_height * MiscUtil.perlinNoise3(((float)x) * 8.0f / n, 0, ((float)y) * 8.0f / n, 256, 256, 256);
	Vec3 terrain_offset =new Vec3(-0.5f * cell_size * n, -2.0f, -0.5f * cell_size * n);
	Vec3 terrain_scale =new Vec3(cell_size, 1.0f, cell_size);
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new HeightFieldShapeSettings(terrain, terrain_offset, terrain_scale, n), RVec3.sZero(), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Create characters
	ShapeRefC character_shape =new RotatedTranslatedShapeSettings(new Vec3(0, 0.5f * cCharacterHeightStanding + cCharacterRadiusStanding, 0), Quat.sIdentity(), new CapsuleShape(0.5f * cCharacterHeightStanding, cCharacterRadiusStanding)).create().get();
	for (int y = 0; y < cNumCharactersY; ++y)
		for (int x = 0; x < cNumCharactersX; ++x)
		{
			CharacterSettingsRef csettings = new CharacterSettings().toRef();
			csettings.setLayer ( Layers.MOVING);
			csettings.setShape ( character_shape.getPtr());
			csettings.setSupportingVolume (new Plane(Vec3.sAxisY(), -cCharacterRadiusStanding)); // Accept contacts that touch the lower sphere of the capsule

			CharacterRef c = new com.github.stephengold.joltjni.Character(csettings,new RVec3(5.0 * (x - cNumCharactersX / 2), 2.0, 5.0 * (y - cNumCharactersY / 2)), Quat.sIdentity(), 0, mPhysicsSystem).toRef();
			c.addToPhysicsSystem(EActivation.Activate);
			mCharacters.add(c);
		}
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	for (CharacterRef c : mCharacters)
		if (c.isSupported())
		{
			// Update velocity
			Vec3 current_velocity = c.getLinearVelocity();
			Vec3 movement_direction = minus(new Vec3(c.getPosition()).normalized());
			Vec3 desired_velocity = star(sCharacterSpeed , movement_direction);
			desired_velocity.setY(current_velocity.getY());
			Vec3 new_velocity = plus(star(0.75f , current_velocity) , star(0.25f , desired_velocity));
			c.setLinearVelocity(new_velocity);
		}
}

public void PostPhysicsUpdate(float inDeltaTime)
{
	// Fetch the new ground properties
	for (CharacterRef c : mCharacters)
		c.postSimulation(cCollisionTolerance);
}
}
