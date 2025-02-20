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
import com.github.stephengold.joltjni.std.Std;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.std.Std.*;
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt Physics soft-body shapes test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/SoftBody/SoftBodyShapesTest.cpp
 */
public class SoftBodyShapesTest extends Test{

public void Initialize()
{
	final Quat cCubeOrientation = Quat.sRotation(Vec3.sReplicate(sqrt(1.0f / 3.0f)), degreesToRadians(45.0f));

	// Floor
	CreateMeshTerrain();

	// Create cloth that's fixated at the corners
	SoftBodyCreationSettings cloth=new SoftBodyCreationSettings(SoftBodyCreator.CreateClothWithFixatedCorners(),new RVec3(0, 10.0f, 0), Quat.sRotation(Vec3.sAxisY(), 0.25f * JPH_PI), Layers.MOVING);
	cloth.setUpdatePosition ( false); // Don't update the position of the cloth as it is fixed to the world
	cloth.setMakeRotationIdentity ( false); // Test explicitly checks if soft bodies with a rotation collide with shapes properly
	mBodyInterface.createAndAddSoftBody(cloth, EActivation.Activate);

	// Create cube
	SoftBodyCreationSettings cube=new SoftBodyCreationSettings(SoftBodySharedSettings.sCreateCube(5, 0.5f),new RVec3(20.0f, 10.0f, 0.0f), cCubeOrientation, Layers.MOVING);
	cube.setRestitution ( 0.0f);
	mBodyInterface.createAndAddSoftBody(cube, EActivation.Activate);

	// Create pressurized sphere
	SoftBodyCreationSettings sphere=new SoftBodyCreationSettings(SoftBodyCreator.CreateSphere(),new RVec3(15.0f, 10.0f, 15.0f), Quat.sIdentity(), Layers.MOVING);
	sphere.setPressure ( 2000.0f);
	mBodyInterface.createAndAddSoftBody(sphere, EActivation.Activate);

	// Sphere below pressurized sphere
	ShapeRefC sphere_shape = new SphereShape(1.0f).toRefC();
	BodyCreationSettings bcs=new BodyCreationSettings(sphere_shape,new RVec3(15.5f, 7.0f, 15.0f), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
	bcs.setOverrideMassProperties ( EOverrideMassProperties.CalculateInertia);
	bcs.getMassPropertiesOverride().setMass ( 100.0f);
	mBodyInterface.createAndAddBody(bcs, EActivation.Activate);

	// Various shapes above cloth
	ConvexHullShapeSettings tetrahedron=new ConvexHullShapeSettings(new Vec3(-2, -2, -2),new Vec3(0, -2, 2),new Vec3(2, -2, -2),new Vec3(0, 2, 0) );
	tetrahedron.setEmbedded();

	StaticCompoundShapeSettings compound_shape=new StaticCompoundShapeSettings();
	compound_shape.setEmbedded();
	Quat rotate_x = Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI);
	compound_shape.addShape(Vec3.sZero(), rotate_x, new CapsuleShape(2, 0.5f));
	compound_shape.addShape(new Vec3(0, 0, -2), Quat.sIdentity(), new SphereShape(1));
	compound_shape.addShape(new Vec3(0, 0, 2), Quat.sIdentity(), new SphereShape(1));

	ShapeRefC shapes[] = {
		sphere_shape,
		new BoxShape(new Vec3(0.75f, 1.0f, 1.25f)).toRefC(),
		new RotatedTranslatedShape(Vec3.sZero(), rotate_x, new CapsuleShape(1, 0.5f)).toRefC(),
		new RotatedTranslatedShape(Vec3.sZero(), rotate_x,new TaperedCapsuleShapeSettings(1.0f, 1.0f, 0.5f).create().get()).toRefC(),
		new RotatedTranslatedShape(Vec3.sZero(), rotate_x, new CylinderShape(1, 0.5f)).toRefC(),
		new RotatedTranslatedShape(Vec3.sZero(), rotate_x,new TaperedCylinderShapeSettings(1, 0.5f, 1.0f).create().get()).toRefC(),
		tetrahedron.create().get(),
		compound_shape.create().get(),
	};
	int num_shapes = (int)Std.size(shapes);

	for (int i = 0; i < num_shapes; ++i)
	{
		bcs.setShape(shapes[i % num_shapes]);
		bcs.setPosition (new RVec3(-(float)(num_shapes) + 2.0f * i, 15.0f, 0));
		mBodyInterface.createAndAddBody(bcs, EActivation.Activate);
	}
}
}