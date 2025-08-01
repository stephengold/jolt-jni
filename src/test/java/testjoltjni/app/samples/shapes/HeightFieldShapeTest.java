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
package testjoltjni.app.samples.shapes;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import testjoltjni.app.samples.*;
import testjoltjni.app.testframework.CameraState;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import static com.github.stephengold.joltjni.std.Std.*;
import static testjoltjni.app.samples.DebugRendererSP.*;
import static testjoltjni.app.testframework.ReadData.*;
/**
 * A line-for-line Java translation of the Jolt-Physics heightfield-shape test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Shapes/HeightFieldShapeTest.cpp
 */
public class HeightFieldShapeTest extends Test{
boolean sShowOriginalTerrain;
byte[] mMaterialIndices;
float[] mTerrain;
HeightFieldShape mHeightField;
int mTerrainSize,sBitsPerSample=8,sBlockSizeShift=2;
PhysicsMaterialList mMaterials=new PhysicsMaterialList();
RVec3 mHitPos=RVec3.sZero();
Vec3 mTerrainOffset,mTerrainScale;

static int sTerrainType = 0;

static String  sTerrainTypes[] = {
	"Procedural Terrain 2^N",
	"Procedural Terrain 2^N + 1",
	"Heightfield 1",
	"Flat 2^N",
	"Flat 2^N + 1",
	"No Collision 2^N",
	"No Collision 2^N + 1"
};

public void Initialize()
{
	if (sTerrainType == 0 || sTerrainType == 1)
	{
		final int n = sTerrainType == 0? 128 : 129;
		final float cell_size = 1.0f;
		final float max_height = 5.0f;

		// Create height samples
		mTerrainSize = n;
		mTerrain=new float[(n * n)];
		for (int y = 0; y < n; ++y)
			for (int x = 0; x < n; ++x)
				mTerrain[y * n + x] = max_height * perlinNoise3((float)(x) * 8.0f / n, 0, (float)(y) * 8.0f / n, 256, 256, 256);

		// Make some holes
		mTerrain[2 * n + 2] = HeightFieldShapeConstants.cNoCollisionValue;
		for (int y = 4; y < 33; ++y)
			for (int x = 4; x < 33; ++x)
				mTerrain[y * n + x] = HeightFieldShapeConstants.cNoCollisionValue;

		// Make material indices
		byte max_material_index = 0;
		mMaterialIndices=new byte[square(n - 1)];
		for (int y = 0; y < n - 1; ++y)
			for (int x = 0; x < n - 1; ++x)
			{
				byte material_index = (byte)(Math.round(minus(new Vec3(x * cell_size, 0, y * cell_size) ,new Vec3(n * cell_size / 2, 0, n * cell_size / 2)).length() / 10.0f));
				max_material_index = (byte)Math.max(max_material_index, material_index);
				mMaterialIndices[y * (n - 1) + x] = material_index;
			}

		// Mark the corners to validate that materials and heights match
		mTerrain[0] = 0.0f;
		mTerrain[n - 1] = 10.0f;
		mTerrain[(n - 1) * n] = 20.0f;
		mTerrain[n * n - 1] = 30.0f;
		mMaterialIndices[0] = 0;
		mMaterialIndices[n - 2] = 1;
		mMaterialIndices[(n - 2) * (n - 1)] = 2;
		mMaterialIndices[square(n - 1) - 1] = 3;

		// Create materials
		for (byte i = 0; i <= max_material_index; ++i)
			mMaterials.pushBack(new PhysicsMaterialSimple("Material " + i, Color.sGetDistinctColor(i)).toRef());

		// Determine scale and offset (deliberately apply extra offset and scale in Y direction)
		mTerrainOffset =new Vec3(-0.5f * cell_size * n, -2.0f, -0.5f * cell_size * n);
		mTerrainScale =new Vec3(cell_size, 1.5f, cell_size);
	}
	else if (sTerrainType == 2)
	{
		final int n = 1024;
		final float cell_size = 0.5f;

		// Get height samples
		float[] data = ReadData("Assets/heightfield1.bin");
		if (data.length !=  n * n)
			FatalError("Invalid file size");
		mTerrainSize = n;
		mTerrain=data;

		// Determine scale and offset
		mTerrainOffset =new Vec3(-0.5f * cell_size * n, 0.0f, -0.5f * cell_size * n);
		mTerrainScale =new Vec3(cell_size, 1.0f, cell_size);
	}
	else if (sTerrainType == 3 || sTerrainType == 4)
	{
		final int n = sTerrainType == 3? 128 : 129;
		final float cell_size = 1.0f;
		final float height = JPH_PI;

		// Determine scale and offset
		mTerrainOffset =new Vec3(-0.5f * cell_size * n, 0.0f, -0.5f * cell_size * n);
		mTerrainScale =new Vec3(cell_size, 1.0f, cell_size);

		// Mark the entire terrain as single height
		mTerrainSize = n;
		mTerrain=new float[(n * n)];
		for (int i=0; i<n*n; ++i)
			mTerrain[i] = height;
	}
	else if (sTerrainType == 5 || sTerrainType == 6)
	{
		final int n = sTerrainType == 4? 128 : 129;
		final float cell_size = 1.0f;

		// Determine scale and offset
		mTerrainOffset =new Vec3(-0.5f * cell_size * n, 0.0f, -0.5f * cell_size * n);
		mTerrainScale =new Vec3(cell_size, 1.0f, cell_size);

		// Mark the entire terrain as no collision
		mTerrainSize = n;
		mTerrain=new float[(n * n)];
		for (int i=0; i<n*n; ++i)
			mTerrain[i] = HeightFieldShapeConstants.cNoCollisionValue;
	}

	// Create height field
	HeightFieldShapeSettings settings=new HeightFieldShapeSettings(mTerrain, mTerrainOffset, mTerrainScale, mTerrainSize, mMaterialIndices, mMaterials);
	settings.setBlockSize ( 1 << sBlockSizeShift);
	settings.setBitsPerSample ( sBitsPerSample);
	mHeightField = (HeightFieldShape)(settings.create().get().getPtr());
	mBodyInterface.createAndAddBody(new BodyCreationSettings(mHeightField, RVec3.sZero(), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	// Validate it
	float max_diff = -1.0f;
	int max_diff_x = 0, max_diff_y = 0;
	float min_height = FLT_MAX, max_height = -FLT_MAX, avg_diff = 0.0f;
	for (int y = 0; y < mTerrainSize; ++y)
		for (int x = 0; x < mTerrainSize; ++x)
		{
			float h1 = mTerrain[y * mTerrainSize + x];
			if (h1 != HeightFieldShapeConstants.cNoCollisionValue)
			{
				h1 = mTerrainOffset.getY() + mTerrainScale.getY() * h1;
				if (mHeightField.isNoCollision(x, y))
					FatalError("No collision where there should be");
				float h2 = mHeightField.getPosition(x, y).getY();
				float diff = Math.abs(h2 - h1);
				if (diff > max_diff)
				{
					max_diff = diff;
					max_diff_x = x;
					max_diff_y = y;
				}
				min_height = Math.min(min_height, h1);
				max_height = Math.max(max_height, h1);
				avg_diff += diff;
			}
			else
			{
				if (!mHeightField.isNoCollision(x, y))
					FatalError("Collision where there shouldn't be");
			}
		}

	// Calculate relative error
	float rel_error = min_height < max_height? 100.0f * max_diff / (max_height - min_height) : 0.0f;

	// Max error we expect given sBitsPerSample (normally the error should be much lower because we quantize relative to the block rather than the full height)
	float max_error = 0.5f * 100.0f / ((1 << sBitsPerSample) - 1);

	// Calculate average
	avg_diff /= mTerrainSize * mTerrainSize;

	// Calculate amount of memory used
	Stats stats = mHeightField.getStats();

	// Trace stats
	Trace("Block size: %d, bits per sample: %d, min height: %g, max height: %g, avg diff: %g, max diff: %g at (%d, %d), relative error: %g%%, size: %d bytes", 1 << sBlockSizeShift, sBitsPerSample, (double)min_height, (double)max_height, (double)avg_diff, (double)max_diff, max_diff_x, max_diff_y, (double)rel_error, stats.getSizeBytes());
	if (rel_error > max_error)
		FatalError("Error too big!");

	// Determine terrain height
	RayCastResult result=new RayCastResult();
	RVec3 start=new RVec3(0, 1000, 0);
	Vec3 direction=new Vec3(0, -2000, 0);
	RRayCast ray =new RRayCast(start, direction );
	if (mPhysicsSystem.getNarrowPhaseQuery().castRay(ray, result,new SpecifiedBroadPhaseLayerFilter(BroadPhaseLayers.NON_MOVING),new SpecifiedObjectLayerFilter(Layers.NON_MOVING)))
		mHitPos = ray.getPointOnRay(result.getFraction());

	// Dynamic body
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(0.5f, 1.0f, 2.0f)), plus(mHitPos ,new Vec3(0, 10, 0)), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	// Test the 'GetHeight' function and draw a marker on the surface
	Vec3 test_pos =plus(new Vec3(inParams.mCameraState.mPos) , star(10.0f , inParams.mCameraState.mForward)), surface_pos=new Vec3();
	int[] sub_shape_id={cEmptySubShapeId};
	if (mHeightField.projectOntoSurface(test_pos, surface_pos, sub_shape_id))
	{
		Vec3 surface_normal = mHeightField.getSurfaceNormal(sub_shape_id[0], surface_pos);
		DrawMarkerSP(mDebugRenderer, surface_pos, Color.sWhite, 1.0f);
		DrawArrowSP(mDebugRenderer, surface_pos, plus(surface_pos , surface_normal), Color.sRed, 0.1f);
	}

	// Draw the original uncompressed terrain
	if (sShowOriginalTerrain)
		for (int y = 0; y < mTerrainSize; ++y)
			for (int x = 0; x < mTerrainSize; ++x)
			{
				// Get original height
				float h = mTerrain[y * mTerrainSize + x];
				if (h == HeightFieldShapeConstants.cNoCollisionValue)
					continue;

				// Get original position
				Vec3 original = plus(mTerrainOffset , star(mTerrainScale ,new Vec3((float)(x), h, (float)(y))));

				// Get compressed position
				Vec3 compressed = mHeightField.getPosition(x, y);

				// Draw marker that is red when error is too big and green when not
				final float cMaxError = 0.1f;
				float error = minus(original , compressed).length();
				byte c = (byte)(Math.round(255.0f * Math.min(error / cMaxError, 1.0f)));
				DrawMarkerSP(mDebugRenderer, original,new Color(c, 255 - c, 0, 255), 0.1f);
			}
}

void GetInitialCamera(CameraState ioState)
{
	// Correct camera pos for hit position
	plusEquals(ioState.mPos , mHitPos);
}
/*TODO

void HeightFieldShapeTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI->CreateTextButton(inSubMenu, "Select Terrain", [this, inUI]() {
		UIElement *terrain_name = inUI->CreateMenu();
		for (uint i = 0; i < size(sTerrainTypes); ++i)
			inUI->CreateTextButton(terrain_name, sTerrainTypes[i], [this, i]() { sTerrainType = i; RestartTest(); });
		inUI->ShowMenu(terrain_name);
	});

	inUI->CreateTextButton(inSubMenu, "Configuration Settings", [this, inUI]() {
		UIElement *terrain_settings = inUI->CreateMenu();
		inUI->CreateComboBox(terrain_settings, "Block Size", { "2", "4", "8" }, sBlockSizeShift - 1, [=](int inItem) { sBlockSizeShift = inItem + 1; });
		inUI->CreateSlider(terrain_settings, "Bits Per Sample", (float)sBitsPerSample, 1.0f, 8.0f, 1.0f, [=](float inValue) { sBitsPerSample = (int)inValue; });
		inUI->CreateTextButton(terrain_settings, "Accept", [this]() { RestartTest(); });
		inUI->ShowMenu(terrain_settings);
	});

	inUI->CreateCheckBox(inSubMenu, "Show Original Terrain", sShowOriginalTerrain, [](UICheckBox::EState inState) { sShowOriginalTerrain = inState == UICheckBox::STATE_CHECKED; });
}
*/}
