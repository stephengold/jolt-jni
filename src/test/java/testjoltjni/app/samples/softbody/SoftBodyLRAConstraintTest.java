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
package testjoltjni.app.samples.softbody;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import java.util.function.BiFunction;
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt Physics soft-body LRA-constraint test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/SoftBody/SoftBodyLRAConstraintTest.cpp
 */
public class SoftBodyLRAConstraintTest extends Test{
float cVertexSpacing=.5f;
int cNumVerticesX=10,cNumVerticesZ = 50;

public void Initialize()
{
	CreateFloor();

	for (int i = 0; i < 2; ++i)
	{
 		BiFunction<Integer,Integer,Float> inv_mass = (Integer inX, Integer inZ)-> { return inZ < 2? 0.0f : 1.0f; };
		BiFunction<Integer,Integer,Vec3> perturbation = (Integer inX, Integer inZ)-> { return Vec3.sZero(); };

		VertexAttributes va=new VertexAttributes();
		va.setShearCompliance ( va.setCompliance ( 1.0e-3f)); // Soften the edges a bit so that the effect of the LRA constraints is more visible
		va.setLraType ( i == 0? ELraType.None : ELraType.EuclideanDistance);

		SoftBodySharedSettingsRef cloth_settings = SoftBodyCreator.CreateCloth(cNumVerticesX, cNumVerticesZ, cVertexSpacing, inv_mass, perturbation, EBendType.None, va);

		SoftBodyCreationSettings cloth=new SoftBodyCreationSettings(cloth_settings,new RVec3(-10.0f + i * 20.0f, 25.0f, 0), Quat.sIdentity(), Layers.MOVING);
		mBodyInterface.createAndAddSoftBody(cloth, EActivation.Activate);
	}
}

public void PrePhysicsUpdate(PreUpdateParams inParams)
{
	mDebugRenderer.drawText3D(new RVec3(-10, 26, -0.5f * cNumVerticesZ * cVertexSpacing), "Without LRA constraints", Color.sWhite);
	mDebugRenderer.drawText3D(new RVec3(10, 26, -0.5f * cNumVerticesZ * cVertexSpacing), "With LRA constraints", Color.sWhite);
}
}