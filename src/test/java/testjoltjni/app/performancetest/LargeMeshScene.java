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
package testjoltjni.app.performancetest;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static testjoltjni.app.performancetest.PerformanceTest.Trace;

/**
 * A line-for-line Java translation of the Jolt-Physics "large mesh scene"
 * performance test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/PerformanceTest/LargeMeshScene.h
 */
// A scene that first finds the largest possible mesh and then simulates some objects on it
class LargeMeshScene implements PerformanceTestScene
{
public
	String	GetName()
	{
		return "LargeMeshScene";
	}

	public boolean			Load()
	{
		// Create mesh shape creation settings
		mMeshCreationSettings.setMotionType ( EMotionType.Static);
		mMeshCreationSettings.setObjectLayer ( Layers.NON_MOVING);
		mMeshCreationSettings.setPosition ( RVec3.sZero());
		mMeshCreationSettings.setFriction ( 0.5f);
		mMeshCreationSettings.setRestitution ( 0.6f);

		Trace("Finding the largest possible mesh, this will take some time!");
		Trace("N, Num Triangles, Mesh Size, Size / Triangle, SubShapeID Bits, Time");
		for (int i = 1; ; ++i)
		{
			final int n = 500 * i;
			final float cell_size = 1.0f;
			final float max_height = 50.0f;

			// Create heights
			MeshShapeSettings settings=new MeshShapeSettings();
			float center = n * cell_size / 2;
			settings.reserveTriangleVertices((n + 1)*(n + 1));
			for (int x = 0; x <= n; ++x)
				for (int z = 0; z <= n; ++z)
					settings.addTriangleVertex(new Float3(cell_size * x - center, max_height * sin((float)(x) * 50.0f / n) * cos((float)(z) * 50.0f / n), cell_size * z - center));

			// Create regular grid of triangles
			settings.reserveIndexedTriangles(2 * n * n);
			for (int x = 0; x < n; ++x)
				for (int z = 0; z < n; ++z)
				{
					settings.addIndexedTriangle(x + z * (n + 1), x + 1 + z * (n + 1), x + (z + 1)*(n + 1));
					settings.addIndexedTriangle(x + 1 + z * (n + 1), x + 1 + (z + 1)*(n + 1), x + (z + 1)*(n + 1));
				}

			// Start measuring
			long clock_start = System.nanoTime();

			// Create the mesh shape
			ShapeResult result = settings.create();

			// Stop measuring
			long clock_end = System.nanoTime();
			long duration = (clock_end - clock_start);

			if (result.hasError())
			{
				// Break when we get an error
				Trace("Mesh creation failed with error: %s", result.getError());
				break;
			}
			else
			{
				// Trace stats
				ShapeRefC shape = result.get();
				Stats stats = ((MeshShape)shape.getPtr()).getStats();
				Trace("%d, %d, %d, %.1f, %d, %.3f", n, stats.getNumTriangles(), (long)stats.getSizeBytes(), (double)(stats.getSizeBytes()) / (double)(stats.getNumTriangles()), shape.getSubShapeIdBitsRecursive(), 1.0e-9 * (double)(duration));

				// Set this shape as the best shape so far
				mMeshCreationSettings.setShape(shape);
			}
		}

		return true;
	}

	public void			StartTest(PhysicsSystem inPhysicsSystem, EMotionQuality inMotionQuality)
	{
		// Create background
		BodyInterface bi = inPhysicsSystem.getBodyInterface();
		bi.createAndAddBody(mMeshCreationSettings, EActivation.DontActivate);

		// Construct bodies
		BodyCreationSettings creation_settings=new BodyCreationSettings();
		creation_settings.setMotionType ( EMotionType.Dynamic);
		creation_settings.setMotionQuality ( inMotionQuality);
		creation_settings.setObjectLayer ( Layers.MOVING);
		creation_settings.setFriction ( 0.5f);
		creation_settings.setRestitution ( 0.6f);
		creation_settings.setShape(new BoxShape(new Vec3(0.5f, 0.75f, 1.0f)));
		for (int x = -10; x <= 10; ++x)
			for (int y = 0; y < 10; ++y)
				for (int z = -10; z <= 10; ++z)
				{
					creation_settings.setPosition (new RVec3(7.5 * x, 55.0 + 2.0 * y, 7.5 * z));
					bi.createAndAddBody(creation_settings, EActivation.Activate);
				}
	}

	BodyCreationSettings	mMeshCreationSettings=new BodyCreationSettings();
public void StopTest(PhysicsSystem inPhysicsSystem){}
};
