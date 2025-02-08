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
import com.github.stephengold.joltjni.std.*;
import java.util.function.BiFunction;
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt Physics soft-body bend-constraint test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/SoftBody/SoftBodyBendConstraintTest.cpp
 */
public class SoftBodyBendConstraintTest extends Test{
float cVertexSpacing=0.5f;
int cNumVerticesX=10,cNumVerticesZ=10;

public void Initialize()
{
	CreateFloor();

	DefaultRandomEngine random=new DefaultRandomEngine();
	UniformFloatDistribution random_float=new UniformFloatDistribution(-0.1f, 0.1f);

	BiFunction<Integer,Integer,Float> inv_mass = (Integer inX, Integer inZ)-> { return inZ < 2? 0.0f : 1.0f; };
	BiFunction<Integer,Integer,Vec3> perturbation = (Integer inX, Integer inZ)-> { return new Vec3(random_float.nextFloat(random), (inZ & 1)!=0? 0.1f : -0.1f, random_float.nextFloat(random)); };

	{
		random.seed(1234);

		// Cloth without bend constraints
		SoftBodySharedSettingsRef cloth_settings = SoftBodyCreator.CreateCloth(cNumVerticesX, cNumVerticesZ, cVertexSpacing, inv_mass, perturbation, EBendType.None);
		SoftBodyCreationSettings cloth=new SoftBodyCreationSettings(cloth_settings,new RVec3(-5.0f, 5.0f, 0), Quat.sIdentity(), Layers.MOVING);
		mBodyInterface.createAndAddSoftBody(cloth, EActivation.Activate);
	}

	{
		random.seed(1234);

		// Cloth with distance bend constraints
		SoftBodySharedSettingsRef cloth_settings = SoftBodyCreator.CreateCloth(cNumVerticesX, cNumVerticesZ, cVertexSpacing, inv_mass, perturbation, EBendType.Distance);
		SoftBodyCreationSettings cloth=new SoftBodyCreationSettings(cloth_settings,new RVec3(0.0f, 5.0f, 0), Quat.sIdentity(), Layers.MOVING);
		mBodyInterface.createAndAddSoftBody(cloth, EActivation.Activate);
	}

	{
		random.seed(1234);

		// Cloth with dihedral bend constraints
		SoftBodySharedSettingsRef cloth_settings = SoftBodyCreator.CreateCloth(cNumVerticesX, cNumVerticesZ, cVertexSpacing, inv_mass, perturbation, EBendType.Dihedral);
		SoftBodyCreationSettings cloth=new SoftBodyCreationSettings(cloth_settings,new RVec3(5.0f, 5.0f, 0), Quat.sIdentity(), Layers.MOVING);
		mBodyInterface.createAndAddSoftBody(cloth, EActivation.Activate);
	}

	{
		// Create sphere
		SoftBodyCreationSettings sphere=new SoftBodyCreationSettings(SoftBodyCreator.CreateSphere(1.0f, 10, 20, EBendType.None),new RVec3(-5.0f, 5.0f, 10.0f), Quat.sIdentity(), Layers.MOVING);
		mBodyInterface.createAndAddSoftBody(sphere, EActivation.Activate);
	}

	{
		// Create sphere with distance bend constraints
		SoftBodyCreationSettings sphere=new SoftBodyCreationSettings(SoftBodyCreator.CreateSphere(1.0f, 10, 20, EBendType.Distance),new RVec3(0.0f, 5.0f, 10.0f), Quat.sIdentity(), Layers.MOVING);
		mBodyInterface.createAndAddSoftBody(sphere, EActivation.Activate);
	}

	{
		// Create sphere with dihedral bend constraints
		SoftBodyCreationSettings sphere=new SoftBodyCreationSettings(SoftBodyCreator.CreateSphere(1.0f, 10, 20, EBendType.Dihedral),new RVec3(5.0f, 5.0f, 10.0f), Quat.sIdentity(), Layers.MOVING);
		mBodyInterface.createAndAddSoftBody(sphere, EActivation.Activate);
	}
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	mDebugRenderer.drawText3D(new RVec3(-5.0f, 7.5f, 0), "No bend constraints", Color.sWhite);
	mDebugRenderer.drawText3D(new RVec3(0.0f, 7.5f, 0), "Distance bend constraints", Color.sWhite);
	mDebugRenderer.drawText3D(new RVec3(5.0f, 7.5f, 0), "Dihedral angle bend constraints", Color.sWhite);
}
}
