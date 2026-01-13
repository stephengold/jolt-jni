/*
Copyright (c) 2024-2026 Stephen Gold

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
package testjoltjni.app.samples.general;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.readonly.*;
import com.github.stephengold.joltjni.std.*;
import java.util.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt-Physics load/save scene test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/LoadSaveSceneTest.cpp
 */
public class LoadSaveSceneTest extends Test{

static final float cMaxHeight = 4.0f;

static MeshShapeSettings sCreateMesh()
{
	final int n = 10;
	final float cell_size = 2.0f;

	// Create heights
	float[][] heights=new float[n + 1][n + 1];
	for (int x = 0; x <= n; ++x)
		for (int z = 0; z <= n; ++z)
			heights[x][z] = cMaxHeight * perlinNoise3(((float)x) / n, 0, ((float)z) / n, 256, 256, 256);

	// Create 'wall' around grid
	for (int x = 0; x <= n; ++x)
	{
		heights[x][0] += 2.0f;
		heights[x][n] += 2.0f;
	}

	for (int y = 1; y < n; ++y)
	{
		heights[0][y] += 2.0f;
		heights[n][y] += 2.0f;
	}

	// Create regular grid of triangles
	int max_material_index = 0;
	List<Triangle> triangles=new ArrayList<>(2*n*n);
	for (int x = 0; x < n; ++x)
		for (int z = 0; z < n; ++z)
		{
			float center = n * cell_size / 2;

			float x1 = cell_size * x - center;
			float z1 = cell_size * z - center;
			float x2 = x1 + cell_size;
			float z2 = z1 + cell_size;

			ConstFloat3 v1 =new Float3(x1, heights[x][z], z1);
			ConstFloat3 v2 =new Float3(x2, heights[x + 1][z], z1);
			ConstFloat3 v3 =new Float3(x1, heights[x][z + 1], z2);
			ConstFloat3 v4 =new Float3(x2, heights[x + 1][z + 1], z2);

			int material_index = (int)(Vec3.sum(new Vec3(v1) ,new Vec3(v2) ,new Vec3(v3) ,new Vec3(v4)).length() / 4.0f / cell_size);
			max_material_index = Math.max(max_material_index, material_index);
			triangles.add(new Triangle(v1, v3, v4, material_index));
			triangles.add(new Triangle(v1, v4, v2, material_index));
		}

	// Create materials
	PhysicsMaterialList materials=new PhysicsMaterialList();
	for (int i = 0; i <= max_material_index; ++i)
		materials.pushBack(new PhysicsMaterialSimple("Mesh Material " + (i), Color.sGetDistinctColor(i)).toRef());

	return new MeshShapeSettings(triangles, (materials));
}

static HeightFieldShapeSettings sCreateHeightField()
{
	final int n = 32;
	final float cell_size = 1.0f;

	// Create height samples
	float[] heights=new float[n * n];
	for (int y = 0; y < n; ++y)
		for (int x = 0; x < n; ++x)
			heights[y * n + x] = cMaxHeight * perlinNoise3(((float)x) / n, 0, ((float)y) / n, 256, 256, 256);

	// Make a hole
	heights[2 * n + 2] = HeightFieldShapeConstants.cNoCollisionValue;

	// Make material indices
	int max_material_index = 0;
	byte[] material_indices=new byte[square(n - 1)];
	for (int y = 0; y < n - 1; ++y)
		for (int x = 0; x < n - 1; ++x)
		{
			int material_index = (Math.round(minus(new Vec3(x * cell_size, 0, y * cell_size) ,new Vec3(n * cell_size / 2, 0, n * cell_size / 2)).length() / 10.0f));
			max_material_index = Math.max(max_material_index, material_index);
			material_indices[y * (n - 1) + x] = (byte)material_index;
		}

	// Create materials
	PhysicsMaterialList materials=new PhysicsMaterialList();
	for (byte i = 0; i <= max_material_index; ++i)
		materials.pushBack(new PhysicsMaterialSimple("HeightField Material " + Integer.toString((i)), Color.sGetDistinctColor(i)).toRef());

	// Create height field
	return new HeightFieldShapeSettings(heights,new Vec3(-0.5f * cell_size * n, 0.0f, -0.5f * cell_size * n),new Vec3(cell_size, 1.0f, cell_size), n, material_indices, materials);
}

static int color;
static Color next_color(){return Color.sGetDistinctColor(color++);}
static RVec3 pos=new RVec3(0,cMaxHeight,0);
static RVec3 next_pos(){plusEquals(pos,new RVec3(0,1,0));return pos;}
static PhysicsSceneRef sCreateScene()
{

	// Create scene
	PhysicsSceneRef scene = new PhysicsScene().toRef();

	// A scaled mesh floor
	scene.addBody(new BodyCreationSettings(new ScaledShapeSettings(sCreateMesh(),new Vec3(2.5f, 1.0f, 1.5f)),new RVec3(0, 0, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));

	// A heightfield floor
	scene.addBody(new BodyCreationSettings(sCreateHeightField(),new RVec3(50, 0, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING));

	// Some simple primitives
	scene.addBody(new BodyCreationSettings(new TriangleShapeSettings(new Vec3(-2, 0, 0),new Vec3(0, 1, 0),new Vec3(2, 0, 0), 0.0f, new PhysicsMaterialSimple("Triangle Material", next_color())), next_pos(), Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI), EMotionType.Static, Layers.NON_MOVING));
	scene.addBody(new BodyCreationSettings(new SphereShapeSettings(0.2f, new PhysicsMaterialSimple("Sphere Material", next_color())), next_pos(), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
	scene.addBody(new BodyCreationSettings(new BoxShapeSettings(new Vec3(0.2f, 0.2f, 0.4f), 0.01f, new PhysicsMaterialSimple("Box Material", next_color())), next_pos(), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
	scene.addBody(new BodyCreationSettings(new CapsuleShapeSettings(1.5f, 0.2f, new PhysicsMaterialSimple("Capsule Material", next_color())), next_pos(), Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI), EMotionType.Dynamic, Layers.MOVING));
	scene.addBody(new BodyCreationSettings(new TaperedCapsuleShapeSettings(0.5f, 0.1f, 0.2f, new PhysicsMaterialSimple("Tapered Capsule Material", next_color())), next_pos(), Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI), EMotionType.Dynamic, Layers.MOVING));
	scene.addBody(new BodyCreationSettings(new CylinderShapeSettings(0.5f, 0.2f, cDefaultConvexRadius, new PhysicsMaterialSimple("Cylinder Material", next_color())), next_pos(), Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI), EMotionType.Dynamic, Layers.MOVING));
	scene.addBody(new BodyCreationSettings(new TaperedCylinderShapeSettings(0.5f, 0.2f, 0.4f, cDefaultConvexRadius, new PhysicsMaterialSimple("Tapered Cylinder Material", next_color())), next_pos(), Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI), EMotionType.Dynamic, Layers.MOVING));
	scene.addBody(new BodyCreationSettings(new TaperedCylinderShapeSettings(0.5f, 0.4f, 0.0f, 0.0f, new PhysicsMaterialSimple("Cone Material", next_color())), next_pos(), Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI), EMotionType.Dynamic, Layers.MOVING));
	scene.addBody(new BodyCreationSettings(new EmptyShapeSettings(), next_pos(), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));

	// Compound with sub compound and rotation
	StaticCompoundShapeSettings sub_compound = new StaticCompoundShapeSettings();
	sub_compound.addShape(new Vec3(0, 0.5f, 0), Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI), new BoxShapeSettings(new Vec3(0.5f, 0.1f, 0.2f), cDefaultConvexRadius, new PhysicsMaterialSimple("Compound Box Material", next_color())));
	sub_compound.addShape(new Vec3(0.5f, 0, 0), Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI), new CylinderShapeSettings(0.5f, 0.2f, cDefaultConvexRadius, new PhysicsMaterialSimple("Compound Cylinder Material", next_color())));
	sub_compound.addShape(new Vec3(0, 0, 0.5f), Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI), new TaperedCapsuleShapeSettings(0.5f, 0.1f, 0.2f, new PhysicsMaterialSimple("Compound Tapered Capsule Material", next_color())));
	StaticCompoundShapeSettings compound_shape = new StaticCompoundShapeSettings();
	compound_shape.addShape(new Vec3(0, 0, 0), star(Quat.sRotation(Vec3.sAxisX(), -0.25f * JPH_PI) , Quat.sRotation(Vec3.sAxisZ(), 0.25f * JPH_PI)), sub_compound);
	compound_shape.addShape(new Vec3(0, -0.1f, 0), star(Quat.sRotation(Vec3.sAxisX(), 0.25f * JPH_PI) , Quat.sRotation(Vec3.sAxisZ(), -0.75f * JPH_PI)), sub_compound);
	scene.addBody(new BodyCreationSettings(compound_shape, next_pos(), Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI), EMotionType.Dynamic, Layers.MOVING));

	// Convex hull shape
	List<Vec3Arg> tetrahedron=new ArrayList<>(4);
	tetrahedron.add(new Vec3(-0.5f, 0, -0.5f));
	tetrahedron.add(new Vec3(0, 0, 0.5f));
	tetrahedron.add(new Vec3(0.5f, 0, -0.5f));
	tetrahedron.add(new Vec3(0, -0.5f, 0));
	ConvexHullShapeSettings convex_hull = new ConvexHullShapeSettings(tetrahedron, cDefaultConvexRadius, new PhysicsMaterialSimple("Convex Hull Material", next_color()));
	scene.addBody(new BodyCreationSettings(convex_hull, next_pos(), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));

	// Rotated convex hull
	scene.addBody(new BodyCreationSettings(new RotatedTranslatedShapeSettings(Vec3.sReplicate(0.5f), Quat.sRotation(Vec3.sAxisZ(), 0.25f * JPH_PI), convex_hull), next_pos(), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));

	// Mutable compound
	MutableCompoundShapeSettings mutable_compound = new MutableCompoundShapeSettings();
	mutable_compound.addShape(new Vec3(0, 0.5f, 0), Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI), new BoxShapeSettings(new Vec3(0.5f, 0.1f, 0.2f), cDefaultConvexRadius, new PhysicsMaterialSimple("MutableCompound Box Material", next_color())));
	mutable_compound.addShape(new Vec3(0.5f, 0, 0), Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI), new CapsuleShapeSettings(0.5f, 0.1f, new PhysicsMaterialSimple("MutableCompound Capsule Material", next_color())));
	mutable_compound.addShape(new Vec3(0, 0, 0.5f), Quat.sRotation(Vec3.sAxisX(), 0.5f * JPH_PI), new TaperedCapsuleShapeSettings(0.5f, 0.2f, 0.1f, new PhysicsMaterialSimple("MutableCompound Tapered Capsule Material", next_color())));
	scene.addBody(new BodyCreationSettings(mutable_compound, next_pos(), Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI), EMotionType.Dynamic, Layers.MOVING));

	// Connect the first two dynamic bodies with a distance constraint
	DistanceConstraintSettings dist_constraint = new DistanceConstraintSettings();
	dist_constraint.setSpace ( EConstraintSpace.LocalToBodyCom);
	scene.addConstraint(dist_constraint, 3, 4);

	// Add soft body cube
	SoftBodySharedSettingsRef sb_cube_settings = SoftBodyCreator.CreateCube(5, 0.2f);
	sb_cube_settings.setMaterials (  new PhysicsMaterialSimple("Soft Body Cube Material", next_color()) );
	SoftBodyCreationSettings sb_cube=new SoftBodyCreationSettings(sb_cube_settings, next_pos(), Quat.sIdentity(), Layers.MOVING);
	scene.addSoftBody(sb_cube);

	// Add the same shape again to test sharing
	sb_cube.setPosition ( next_pos());
	scene.addSoftBody(sb_cube);

	// Add soft body sphere
	SoftBodySharedSettingsRef sb_sphere_settings = SoftBodyCreator.CreateSphere(0.5f);
	sb_sphere_settings.setMaterials (  new PhysicsMaterialSimple("Soft Body Sphere Material", next_color()) );
	SoftBodyCreationSettings sb_sphere=new SoftBodyCreationSettings(sb_sphere_settings, next_pos(), Quat.sIdentity(), Layers.MOVING);
	sb_sphere.setPressure ( 2000.0f);
	scene.addSoftBody(sb_sphere);

	return scene;
}

public void Initialize()
{
if(supportsObjectStream()){
	PhysicsSceneRef scene = sCreateScene();

	StringStream data=new StringStream();

	// Write scene
	if (!ObjectStreamOut.sWriteObject(data, EStreamType.Text, scene.getPtr()))
		FatalError("Failed to save scene");

	// Clear scene
	scene = new PhysicsSceneRef();

	// Read scene back in
	if (!ObjectStreamIn.sReadObject(data, scene))
		FatalError("Failed to load scene");

	// Ensure that the soft body shared settings have been optimized (this is not saved to a text file)
	for (SoftBodyCreationSettings soft_body : scene.getSoftBodies())
		((SoftBodySharedSettings)soft_body.getSettings()).optimize();

	// Instantiate scene
	scene.createBodies(mPhysicsSystem);
} // JPH_OBJECT_STREAM
}
}
