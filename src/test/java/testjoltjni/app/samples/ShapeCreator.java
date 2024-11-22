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
import com.github.stephengold.joltjni.operator.Op;
import static com.github.stephengold.joltjni.Jolt.*;
/**
 * A line-for-line Java translation of the {@code ShapeCreator} namespace.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Utils/ShapeCreator.cpp
 */
public class ShapeCreator {

public static ShapeRefC CreateTorusMesh(float inTorusRadius,float inTubeRadius){return CreateTorusMesh(inTorusRadius,inTubeRadius,16);}
static ShapeRefC CreateTorusMesh(float inTorusRadius,float inTubeRadius,int inTorusSegments){return CreateTorusMesh(inTorusRadius,inTubeRadius,inTorusSegments,16);}
static ShapeRefC CreateTorusMesh(float inTorusRadius, float inTubeRadius, int inTorusSegments, int inTubeSegments)
{
	int cNumVertices = inTorusSegments * inTubeSegments;

	// Create torus
	MeshShapeSettings mesh=new MeshShapeSettings();
	mesh.reserveTriangleVertices(cNumVertices);
	mesh.reserveIndexedTriangles(cNumVertices * 2);
	for (int torus_segment = 0; torus_segment < inTorusSegments; ++torus_segment)
	{
		Mat44 rotation = Mat44.sRotation(Vec3.sAxisY(), (float)(torus_segment) * 2.0f * JPH_PI / inTorusSegments);
		for (int tube_segment = 0; tube_segment < inTubeSegments; ++tube_segment)
		{
			// Create vertices
			float tube_angle = (float)(tube_segment) * 2.0f * JPH_PI / inTubeSegments;
			Vec3 pos = Op.multiply(rotation ,new Vec3(inTorusRadius + inTubeRadius * sin(tube_angle), inTubeRadius * cos(tube_angle), 0));
			Float3 v=new Float3();
			pos.storeFloat3(v);
			mesh.addTriangleVertex(v);

			// Create indices
			int start_idx = torus_segment * inTubeSegments + tube_segment;
			mesh.addIndexedTriangle(start_idx, (start_idx + 1) % cNumVertices, (start_idx + inTubeSegments) % cNumVertices);
			mesh.addIndexedTriangle((start_idx + 1) % cNumVertices, (start_idx + inTubeSegments + 1) % cNumVertices, (start_idx + inTubeSegments) % cNumVertices);
		}
	}
	return mesh.create().get();
}

};
