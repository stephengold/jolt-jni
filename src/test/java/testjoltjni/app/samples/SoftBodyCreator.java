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
package testjoltjni.app.samples;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.lambda.TriFunction;
import com.github.stephengold.joltjni.readonly.*;
import java.util.function.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the soft-body creator namespace.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Utils/SoftBodyCreator.cpp
 */
public class SoftBodyCreator {
static SoftBodySharedSettingsRef CreateCloth(int inGridSizeX,int inGridSizeZ,float inGridSpacing,BiFunction<Integer,Integer,Float>inVertexGetInvMass) {
    return CreateCloth(inGridSizeX,inGridSizeZ,inGridSpacing,inVertexGetInvMass,(Integer a,Integer b)->{return new Vec3();},EBendType.None,new VertexAttributes(1e-5f, 1e-5f, 1e-5f));}

static SoftBodySharedSettingsRef CreateCloth(int inGridSizeX, int inGridSizeZ, float inGridSpacing, BiFunction<Integer,Integer,Float> inVertexGetInvMass, BiFunction<Integer,Integer,Vec3> inVertexPerturbation, EBendType inBendType, ConstVertexAttributes inVertexAttributes)
{
	final float cOffsetX = -0.5f * inGridSpacing * (inGridSizeX - 1);
	final float cOffsetZ = -0.5f * inGridSpacing * (inGridSizeZ - 1);

	// Create settings
	SoftBodySharedSettings settings = new SoftBodySharedSettings();
	for (int z = 0; z < inGridSizeZ; ++z)
		for (int x = 0; x < inGridSizeX; ++x)
		{
			Vertex v=new Vertex();
			Vec3 position = plus(inVertexPerturbation.apply(x, z) , new Vec3(cOffsetX + x * inGridSpacing, 0.0f, cOffsetZ + z * inGridSpacing));
			v.setPosition(position);
			v.setInvMass ( inVertexGetInvMass.apply(x, z));
			settings.addVertex(v);
		}

	// Function to get the vertex index of a point on the cloth
	BinaryOperator<Integer> vertex_index = (Integer inX, Integer inY) ->
	{
		return inX + inY * inGridSizeX;
	};

	// Create faces
	for (int z = 0; z < inGridSizeZ - 1; ++z)
		for (int x = 0; x < inGridSizeX - 1; ++x)
		{
			Face f=new Face();
			f.setVertex(0 , vertex_index.apply(x, z));
			f.setVertex(1 , vertex_index.apply(x, z + 1));
			f.setVertex(2 , vertex_index.apply(x + 1, z + 1));
			settings.addFace(f);

			f.setVertex(1 , vertex_index.apply(x + 1, z + 1));
			f.setVertex(2 , vertex_index.apply(x + 1, z));
			settings.addFace(f);
		}

	// Create constraints
	settings.createConstraints(inVertexAttributes, 1, inBendType);

	// Optimize the settings
	settings.optimize();

	return settings.toRef();
}

public static SoftBodySharedSettingsRef CreateClothWithFixatedCorners(int inGridSizeX, int inGridSizeZ, float inGridSpacing)
{
	BiFunction<Integer,Integer,Float> inv_mass = (Integer inX, Integer inZ) -> {
		return (inX == 0 && inZ == 0)
			|| (inX == inGridSizeX - 1 && inZ == 0)
			|| (inX == 0 && inZ == inGridSizeZ - 1)
			|| (inX == inGridSizeX - 1 && inZ == inGridSizeZ - 1)? 0.0f : 1.0f;
	};

	return CreateCloth(inGridSizeX, inGridSizeZ, inGridSpacing, inv_mass);
}

public static SoftBodySharedSettingsRef CreateCube(int inGridSize, float inGridSpacing)
{
	Vec3Arg cOffset = Vec3.sReplicate(-0.5f * inGridSpacing * (inGridSize - 1));

	// Create settings
	SoftBodySharedSettings settings = new SoftBodySharedSettings();
	for (int z = 0; z < inGridSize; ++z)
		for (int y = 0; y < inGridSize; ++y)
			for (int x = 0; x < inGridSize; ++x)
			{
				Vertex v=new Vertex();
                                v.setPosition(plus(cOffset , star(Vec3.sReplicate(inGridSpacing), new Vec3((float)(x), (float)(y), (float)(z)))));
				settings.addVertex(v);
			}

	// Function to get the vertex index of a point on the cloth
        TriFunction<Integer,Integer,Integer,Integer> vertex_index = (Integer inX, Integer inY, Integer inZ) -> {
		return inX + inY * inGridSize + inZ * inGridSize * inGridSize;
	};

	// Create edges
	for (int z = 0; z < inGridSize; ++z)
		for (int y = 0; y < inGridSize; ++y)
			for (int x = 0; x < inGridSize; ++x)
			{
				Edge e=new Edge();
				e.setVertex(0 , vertex_index.apply(x, y, z));
				if (x < inGridSize - 1)
				{
					e.setVertex(1 , vertex_index.apply(x + 1, y, z));
					settings.addEdgeConstraint(e);
				}
				if (y < inGridSize - 1)
				{
					e.setVertex(1 , vertex_index.apply(x, y + 1, z));
					settings.addEdgeConstraint(e);
				}
				if (z < inGridSize - 1)
				{
					e.setVertex(1 , vertex_index.apply(x, y, z + 1));
					settings.addEdgeConstraint(e);
				}
			}
	settings.calculateEdgeLengths();

	// Tetrahedrons to fill a cube
	final int tetra_indices[][][] = {
		{ {0, 0, 0}, {0, 1, 1}, {0, 0, 1}, {1, 1, 1} },
		{ {0, 0, 0}, {0, 1, 0}, {0, 1, 1}, {1, 1, 1} },
		{ {0, 0, 0}, {0, 0, 1}, {1, 0, 1}, {1, 1, 1} },
		{ {0, 0, 0}, {1, 0, 1}, {1, 0, 0}, {1, 1, 1} },
		{ {0, 0, 0}, {1, 1, 0}, {0, 1, 0}, {1, 1, 1} },
		{ {0, 0, 0}, {1, 0, 0}, {1, 1, 0}, {1, 1, 1} }
	};

	// Create volume constraints
	for (int z = 0; z < inGridSize - 1; ++z)
		for (int y = 0; y < inGridSize - 1; ++y)
			for (int x = 0; x < inGridSize - 1; ++x)
				for (int t = 0; t < 6; ++t)
				{
					Volume v=new Volume();
					for (int i = 0; i < 4; ++i)
						v.setVertex(i , vertex_index.apply(x + tetra_indices[t][i][0], y + tetra_indices[t][i][1], z + tetra_indices[t][i][2]));
					settings.addVolumeConstraint(v);
				}

	settings.calculateVolumeConstraintVolumes();

	// Create faces
	for (int y = 0; y < inGridSize - 1; ++y)
		for (int x = 0; x < inGridSize - 1; ++x)
		{
			Face f=new Face();

			// Face 1
			f.setVertex(0 , vertex_index.apply(x, y, 0));
			f.setVertex(1 , vertex_index.apply(x, y + 1, 0));
			f.setVertex(2 , vertex_index.apply(x + 1, y + 1, 0));
			settings.addFace(f);

			f.setVertex(1 , vertex_index.apply(x + 1, y + 1, 0));
			f.setVertex(2 , vertex_index.apply(x + 1, y, 0));
			settings.addFace(f);

			// Face 2
			f.setVertex(0 , vertex_index.apply(x, y, inGridSize - 1));
			f.setVertex(1 , vertex_index.apply(x + 1, y + 1, inGridSize - 1));
			f.setVertex(2 , vertex_index.apply(x, y + 1, inGridSize - 1));
			settings.addFace(f);

			f.setVertex(1 , vertex_index.apply(x + 1, y, inGridSize - 1));
			f.setVertex(2 , vertex_index.apply(x + 1, y + 1, inGridSize - 1));
			settings.addFace(f);

			// Face 3
			f.setVertex(0 , vertex_index.apply(x, 0, y));
			f.setVertex(1 , vertex_index.apply(x + 1, 0, y + 1));
			f.setVertex(2 , vertex_index.apply(x, 0, y + 1));
			settings.addFace(f);

			f.setVertex(1 , vertex_index.apply(x + 1, 0, y));
			f.setVertex(2 , vertex_index.apply(x + 1, 0, y + 1));
			settings.addFace(f);

			// Face 4
			f.setVertex(0 , vertex_index.apply(x, inGridSize - 1, y));
			f.setVertex(1 , vertex_index.apply(x, inGridSize - 1, y + 1));
			f.setVertex(2 , vertex_index.apply(x + 1, inGridSize - 1, y + 1));
			settings.addFace(f);

			f.setVertex(1 , vertex_index.apply(x + 1, inGridSize - 1, y + 1));
			f.setVertex(2 , vertex_index.apply(x + 1, inGridSize - 1, y));
			settings.addFace(f);

			// Face 5
			f.setVertex(0 , vertex_index.apply(0, x, y));
			f.setVertex(1 , vertex_index.apply(0, x, y + 1));
			f.setVertex(2 , vertex_index.apply(0, x + 1, y + 1));
			settings.addFace(f);

			f.setVertex(1 , vertex_index.apply(0, x + 1, y + 1));
			f.setVertex(2 , vertex_index.apply(0, x + 1, y));
			settings.addFace(f);

			// Face 6
			f.setVertex(0 , vertex_index.apply(inGridSize - 1, x, y));
			f.setVertex(1 , vertex_index.apply(inGridSize - 1, x + 1, y + 1));
			f.setVertex(2 , vertex_index.apply(inGridSize - 1, x, y + 1));
			settings.addFace(f);

			f.setVertex(1 , vertex_index.apply(inGridSize - 1, x + 1, y));
			f.setVertex(2 , vertex_index.apply(inGridSize - 1, x + 1, y + 1));
			settings.addFace(f);
		}

	// Optimize the settings
	settings.optimize();

	return settings.toRef();
}

public static SoftBodySharedSettingsRef CreateSphere(float inRadius){return CreateSphere(inRadius,10,20,EBendType.None,new VertexAttributes(1e-4f,1e-4f,1e-3f));}
static SoftBodySharedSettingsRef CreateSphere(float inRadius, int inNumTheta, int inNumPhi, EBendType inBendType, ConstVertexAttributes inVertexAttributes)
{
	// Create settings
	SoftBodySharedSettings settings = new SoftBodySharedSettings();

	// Create vertices
	// NOTE: This is not how you should create a soft body sphere, we explicitly use polar coordinates to make the vertices unevenly distributed.
	// Doing it this way tests the pressure algorithm as it receives non-uniform triangles. Better is to use uniform triangles,
	// see the use of DebugRenderer::Create8thSphere for an example.
	Vertex v=new Vertex();
	v.setPosition(star(inRadius , Vec3.sUnitSpherical(0, 0)));
	settings.addVertex(v);
	v.setPosition(star(inRadius , Vec3.sUnitSpherical(JPH_PI, 0)));
	settings.addVertex(v);
	for (int theta = 1; theta < inNumTheta - 1; ++theta)
		for (int phi = 0; phi < inNumPhi; ++phi)
		{
			v.setPosition(star(inRadius , Vec3.sUnitSpherical(JPH_PI * theta / (inNumTheta - 1), 2.0f * JPH_PI * phi / inNumPhi)));
			settings.addVertex(v);
		}

	// Function to get the vertex index of a point on the sphere
        BinaryOperator<Integer> vertex_index = (Integer inTheta, Integer inPhi) ->
	{
		if (inTheta == 0)
			return 0;
		else if (inTheta == inNumTheta - 1)
			return 1;
		else
			return 2 + (inTheta - 1) * inNumPhi + inPhi % inNumPhi;
	};

	// Create faces
	Face f=new Face();
	for (int phi = 0; phi < inNumPhi; ++phi)
	{
		for (int theta = 0; theta < inNumTheta - 2; ++theta)
		{
			f.setVertex(0 , vertex_index.apply(theta, phi));
			f.setVertex(1 , vertex_index.apply(theta + 1, phi));
			f.setVertex(2 , vertex_index.apply(theta + 1, phi + 1));
			settings.addFace(f);

			if (theta > 0)
			{
				f.setVertex(1 , vertex_index.apply(theta + 1, phi + 1));
				f.setVertex(2 , vertex_index.apply(theta, phi + 1));
				settings.addFace(f);
			}
		}

		f.setVertex(0 , vertex_index.apply(inNumTheta - 2, phi + 1));
		f.setVertex(1 , vertex_index.apply(inNumTheta - 2, phi));
		f.setVertex(2 , vertex_index.apply(inNumTheta - 1, 0));
		settings.addFace(f);
	}

	// Create constraints
	settings.createConstraints(inVertexAttributes, 1, inBendType);

	// Optimize the settings
	settings.optimize();

	return settings.toRef();
}

};
