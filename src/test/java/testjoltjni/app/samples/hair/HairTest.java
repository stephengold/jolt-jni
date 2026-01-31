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
package testjoltjni.app.samples.hair;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.readonly.*;
import com.github.stephengold.joltjni.std.*;
import java.io.File;
import java.nio.*;
import java.util.*;
import java.util.function.BiFunction;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import static com.github.stephengold.joltjni.std.Std.*;
import static testjoltjni.app.testframework.ReadData.*;
/**
 * A line-for-line Java translation of the Jolt-Physics hair test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Hair/HairTest.cpp
 */
public class HairTest extends Test{

    boolean sDrawAngularVelocity,sDrawGridDensity,sDrawGridVelocity,sDrawHeadMesh=true;
boolean sDrawInitialGravity,sDrawNeutralDensity,sDrawOrientations,sDrawRenderStrands=true;
boolean sDrawRods,sDrawSkinPoints,sDrawUnloadedRods,sDrawVertexVelocity;
boolean sEnableCollision=true,sEnableLRA=true,sLimitMaxStrands=true;
ERenderStrandColor sRenderStrandColor=ERenderStrandColor.PerSimulatedStrand;
float sAngularDamping=2f,sBendComplianceExponent=-7f,sFriction=.2f;
float sGravityPreloadFactor=1f,sGridDensityForceFactor,sInertiaMultiplier=10f;
float sLinearDamping=2f,sMaxAngularVelocity=50f,sMaxLinearVelocity=10f;
float sSimulationStrandsPercentage=10f,sStretchComplianceExponent=-8f;
Gradient sGlobalPose=new Gradient(0.01f,0f,0f,0.3f);
Gradient sGravityFactor=new Gradient(0.1f,1f,0.2f,0.8f);
Gradient sGridVelocityFactor=new Gradient(0.05f,0.01f);
Gradient sHairRadius=new Gradient(0.001f,0.001f);
Gradient sSkinGlobalPose=new Gradient(1f,0f,0f,0.1f);
Gradient sWorldTransformInfluence=new Gradient(0f,1f);
class Groom{Groom(){};
    Groom(String n,BiFunction<Mat44Arg,Vec3Arg,Vec3>t,boolean a){mName=n;mVertexTransform=t;mAttachToHull=a;};
    String mName;BiFunction<Mat44Arg,Vec3Arg,Vec3>mVertexTransform;boolean mAttachToHull;};
BiFunction<Mat44Arg,Vec3Arg,Vec3> tenth_of_inch_to_m = (Mat44Arg inInvNeckTransform, Vec3Arg inVertex) -> {
    return star(inInvNeckTransform,star(2.54f/1000f,inVertex.swizzle(SWIZZLE_Y,SWIZZLE_Z,SWIZZLE_X)));};
Groom sGrooms[]={
    new Groom("Straight",tenth_of_inch_to_m,false),
    new Groom("Curly",tenth_of_inch_to_m,false),
    new Groom("Wavy",tenth_of_inch_to_m,false)};
Groom sSelectedGroom=sGrooms[0];
Hair mHair;
HairSettingsRef mHairSettings=new HairSettingsRef();
HairShaders mHairShaders=new HairShaders();
int mFrame, mHeadJointIdx,sDrawSimulationStrandBegin;
int sDrawSimulationStrandCount=Std.INT_MAX;
int sMaxStrands=Jolt.buildType().equals("Debug")?500:25000;
int sNumSolverIterationsPerSecond=HairSettings.cDefaultIterationsPerSecond;
int sOverrideVerticesPerStrand=32;
class AttachedBody{AttachedBody(int j,int b){mJointIdx=j;mBodyID=b;};int mJointIdx;int mBodyID;};
List<AttachedBody>mAttachedBodies=new ArrayList<>();
Mat44Array[] mFaceAnimation;




public void Initialize()
{
	// Check groom file exists
	String groom_file = "w" + (String)(sSelectedGroom.mName) + ".hair";
	String full_path = "Assets/" + groom_file;
	if (!new File(full_path).exists())
		throw new RuntimeException(String.format("File %s not found.\n\n"+
			"wCurly.hair, wStraight.hair and wWavy.hair should be downloaded from https://www.cemyuksel.com/research/hairmodels/ (or by running Assets/download_hair.sh)", full_path));

	// Read face mesh and animation
	StreamInWrapper stream=StreamInWrapper.open("Assets/face.bin", StreamInWrapper.in() | StreamInWrapper.binary());

	// Neck joint index
	mHeadJointIdx=stream.readInt();

	// Vertices
	int num_vertices;
	num_vertices=stream.readInt();
	Float3[] vertices=new Float3[num_vertices];
        for(int i=0;i<num_vertices;++i){vertices[i]=new Float3();}
	stream.readBytes(vertices   );

	// Indices
	int num_indices;
	num_indices=stream.readInt();
	IndexedTriangleNoMaterial[] indices=new IndexedTriangleNoMaterial[num_indices];
        for(int i=0;i<num_indices;++i){indices[i]=new IndexedTriangleNoMaterial();}
	stream.readIndexedTriangles(indices   );

	// Inverse Bind Matrices
	int num_joints;
	num_joints=stream.readInt();
	Mat44[] inv_bind_pose=new Mat44[num_joints];
        for(int i=0;i<num_joints;++i){inv_bind_pose[i]=new Mat44();}
	stream.readMatrices(inv_bind_pose   );

	// Skin Weights
	int num_skin_weights_per_vertex;
	num_skin_weights_per_vertex=stream.readInt();
	HairSkinWeight[] skin_weights=new HairSkinWeight[num_skin_weights_per_vertex * num_vertices];
        for(int i=0;i<skin_weights.length;++i){skin_weights[i]=new HairSkinWeight();}
	stream.readHairSkinWeights(skin_weights   );

	// Animation
	int num_frames;
	num_frames=stream.readInt();
	mFaceAnimation=new Mat44Array[num_frames];
	for (int frame = 0; frame < num_frames; ++frame)
	{
		mFaceAnimation[frame]=new Mat44Array(num_joints);
		for (int joint = 0; joint < num_joints; ++joint)
		{
			Float3 translation=new Float3(), rotation=new Float3();
			stream.readFloat3(translation);
			stream.readFloat3(rotation);
			Quat rotation_quat=new Quat(rotation.x, rotation.y, rotation.z, sqrt(Math.max(0.0f, 1.0f -new Vec3(rotation).lengthSq())));
			mFaceAnimation[frame].set(joint , Mat44.sRotationTranslation(rotation_quat,new Vec3(translation)));
		}
	}

	// Read collision hulls
	int num_hulls;
	num_hulls=stream.readInt();
	for (int i = 0; i < num_hulls; ++i)
	{
		// Attached to joint
		int joint_index;
		joint_index=stream.readInt();

		// Read number of vertices
		int num_hull_vertices;
		num_hull_vertices=stream.readInt();

		// Read vertices
		ConvexHullShapeSettings shape_settings=new ConvexHullShapeSettings();
		shape_settings.setEmbedded();
                Vec3 tmp=new Vec3();
		for (int j = 0; j < num_hull_vertices; ++j)
			{stream.readVec3(tmp);shape_settings.addPoint(tmp);}

		Mat44 transform = joint_index != 0xffffffff? mFaceAnimation[0].get(joint_index) : Mat44.sIdentity();
		Mat44 inv_transform = transform.inversed();
		for (int j = 0; j<num_hull_vertices;++j)
			shape_settings.setPoint(j,  star(inv_transform,  shape_settings.copyPoint(j)));

		// Create the body
		BodyCreationSettings body=new BodyCreationSettings(shape_settings,new RVec3(transform.getTranslation()), transform.getQuaternion(), EMotionType.Kinematic, Layers.MOVING);
		int body_id = mBodyInterface.createAndAddBody(body, EActivation.DontActivate);

		mAttachedBodies.add(new AttachedBody( joint_index, body_id ));
	}

	// Make mesh relative to neck bind pose
	Mat44 inv_bind_neck = inv_bind_pose[mHeadJointIdx];
	Mat44 bind_neck = inv_bind_neck.inversed();
	for (int i = 0;i<num_vertices;++i)
		star(inv_bind_neck, new Vec3(vertices[i])).storeFloat3(vertices[i]);
	for (int i = 0;i<num_joints;++i)
		inv_bind_pose[i] = star(inv_bind_pose[i] , bind_neck);

	// Read hair file
	ByteBuffer data = ReadData(full_path);
	if (data.get(0) != 'H' || data.get(1) != 'A' || data.get(2) != 'I' || data.get(3) != 'R')
		FatalError("Invalid hair file");

	IntBuffer intData=data.asIntBuffer();int features = intData.get(3);
	if ((features & 0b10) != 0b10)
		FatalError("We require points to be defined");

	int num_strands = intData.get(1);
	int num_points = intData.get(2);

	int[]  num_segments = null;
	int num_segments_delta = 0;
	FloatBuffer floatData=data.asFloatBuffer();ShortBuffer shortData=data.asShortBuffer();
	ConstFloat3 [] points = null;int nextPoint=0;
	if ((features & 0b01)!=0)
	{
		// Num segments differs per strand
		num_segments = new int[num_strands];
                for(int i=0;i<num_strands;++i)num_segments[i]=0xffff&shortData.get(i+64);
		num_segments_delta = 1;
                points = new ConstFloat3[num_points];
                for(int i=0;i<num_points;++i)points[i]=new Float3(floatData,32+num_strands/2+3*i);
	}
	else
	{
		// Num segments is constant
		num_segments = new int[1];num_segments[0]=0xffff&shortData.get(8);
		num_segments_delta = 0;
                points = new ConstFloat3[num_points];for(int i=0;i<num_points;++i)points[i]=new Float3(floatData,32+3*i);
	}

	// Init strands
	if (sLimitMaxStrands)
		num_strands = Math.min(num_strands, sMaxStrands);
	SVertexList hair_vertices=new SVertexList();
	hair_vertices.resize(num_points);
	SStrandList hair_strands=new SStrandList();
	Mat44Arg neck_transform = mFaceAnimation[0].get(mHeadJointIdx);
	Mat44 inv_neck_transform = neck_transform.inversed();
	for (int strand = 0; strand < num_strands; ++strand)
	{
		// Transform relative to neck
		Vec3[] out_points=new Vec3[num_segments[0]+1];
		for (int point = 0; point < num_segments[0] + 1; ++point)
			out_points[point]=sSelectedGroom.mVertexTransform.apply(inv_neck_transform,new Vec3(points[point]));

		// Attach the first vertex to the skull collision
		if (sSelectedGroom.mAttachToHull)
		{
			final float cMaxDist = 10.0f;
			Vec3 direction = star(cMaxDist , minus(out_points[0] , out_points[1])).normalizedOr(minus(Vec3.sAxisY()));
			Vec3 origin = minus(out_points[0] , star(0.5f , direction));
			RRayCast ray=new RRayCast(new RVec3(star(neck_transform , origin)), neck_transform.multiply3x3(direction));
			RayCastResult hit=new RayCastResult();
			if (mPhysicsSystem.getNarrowPhaseQuery().castRay(ray, hit))
			{
				Vec3 delta = plus(origin , minus(star(hit.getFraction() , direction) , out_points[0]));
				for (int point=0;point<num_segments[0]+1;++point)
					plusEquals(out_points[point] , delta);
			}
		}

		// Add the strand to the hair settings
		int first_point = (int)(hair_vertices.size());
		for (int point = 0; point < (int)(out_points.length); ++point)
		{
			SVertex v=new SVertex();
			v.setPosition(out_points[point]);
			v.setInvMass ( point == 0? 0.0f : 1.0f);
			hair_vertices.pushBack(v);
		}
		hair_strands.pushBack(new SStrand(first_point, hair_vertices.size(), 0));

		nextPoint += num_segments[0] + 1;
		num_segments[0] += num_segments_delta;
	}

	// Resample if requested
	if (sOverrideVerticesPerStrand > 1)
		HairSettings.sResample(hair_vertices, hair_strands, sOverrideVerticesPerStrand);

	// Load shaders
	mHairShaders.init(mComputeSystem);

	// Init hair settings
	mHairSettings = new HairSettings().toRef();
	mHairSettings.setScalpVertices  (vertices);
	mHairSettings.setScalpTriangles  (indices);
	mHairSettings.setScalpInverseBindPose  (inv_bind_pose);
	mHairSettings.setScalpSkinWeights  (skin_weights);
	mHairSettings.setScalpNumSkinWeightsPerVertex ( num_skin_weights_per_vertex);
	mHairSettings.setNumIterationsPerSecond ( sNumSolverIterationsPerSecond);
	HairMaterial m=new HairMaterial();
	m.setEnableCollision ( sEnableCollision);
	m.setEnableLra ( sEnableLRA);
	m.setLinearDamping ( sLinearDamping);
	m.setAngularDamping ( sAngularDamping);
	m.setFriction ( sFriction);
	m.setMaxLinearVelocity ( sMaxLinearVelocity);
	m.setMaxAngularVelocity ( sMaxAngularVelocity);
	m.setGravityFactor ( sGravityFactor);
	m.setGravityPreloadFactor ( sGravityPreloadFactor);
	m.setBendCompliance ( Std.pow(10.0f, sBendComplianceExponent));
	m.setStretchCompliance ( Std.pow(10.0f, sStretchComplianceExponent));
	m.setInertiaMultiplier ( sInertiaMultiplier);
	m.setHairRadius ( sHairRadius);
	m.setWorldTransformInfluence ( sWorldTransformInfluence);
	m.setGridVelocityFactor ( sGridVelocityFactor);
	m.setGridDensityForceFactor ( sGridDensityForceFactor);
	m.setGlobalPose ( sGlobalPose);
	m.setSkinGlobalPose ( sSkinGlobalPose);
	m.setMaxLinearVelocity ( 10.0f);
	m.setSimulationStrandsFraction ( 0.01f * sSimulationStrandsPercentage);
	mHairSettings.addMaterial(m);
	mHairSettings.setSimulationBoundsPadding ( Vec3.sReplicate(0.1f));
	mHairSettings.setInitialGravity ( inv_bind_neck.multiply3x3(mPhysicsSystem.getGravity()));
	mHairSettings.initRenderAndSimulationStrands(hair_vertices, hair_strands);
	float[] max_dist_sq = {0.0f};
	mHairSettings.init(max_dist_sq);
	assert(max_dist_sq[0] < 1.0e-4f);

	// Write and read back to test SaveBinaryState
	StringStream stream_data=new StringStream();
	{
		StreamOutWrapper stream_out=new StreamOutWrapper(stream_data);
		mHairSettings.saveBinaryState(stream_out);
	}
	mHairSettings = new HairSettings().toRef();
	{
		StreamInWrapper stream_in=new StreamInWrapper(stream_data);
		mHairSettings.restoreBinaryState(stream_in);
	}
	mHairSettings.initCompute(mComputeSystem);

	mHair = new Hair(mHairSettings,new RVec3(neck_transform.getTranslation()), neck_transform.getQuaternion(), Layers.MOVING);
	mHair.init(mComputeSystem);
	mHair.update(0.0f, inv_neck_transform, mFaceAnimation[0], mPhysicsSystem, mHairShaders, mComputeSystem, mComputeQueue);
	mHair.readBackGpuState(mComputeQueue);

if(Jolt.implementsDebugRendering()){
	// Update drawing range
	sDrawSimulationStrandCount = (int)mHairSettings.countSimStrands();
}// JPH_DEBUG_RENDERER
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	BodyInterface bi = mPhysicsSystem.getBodyInterfaceNoLock();

if(Jolt.implementsDebugRendering()){
	DrawSettings settings=new DrawSettings();
	settings.setSimulationStrandBegin ( sDrawSimulationStrandBegin);
	settings.setSimulationStrandEnd ( sDrawSimulationStrandBegin + sDrawSimulationStrandCount);
	settings.setDrawRods ( sDrawRods);
	settings.setDrawUnloadedRods ( sDrawUnloadedRods);
	settings.setDrawRenderStrands ( sDrawRenderStrands);
	settings.setRenderStrandColor ( sRenderStrandColor);
	settings.setDrawVertexVelocity ( sDrawVertexVelocity);
	settings.setDrawAngularVelocity ( sDrawAngularVelocity);
	settings.setDrawOrientations ( sDrawOrientations);
	settings.setDrawGridVelocity ( sDrawGridVelocity);
	settings.setDrawGridDensity ( sDrawGridDensity);
	settings.setDrawSkinPoints ( sDrawSkinPoints);
	settings.setDrawNeutralDensity ( sDrawNeutralDensity);
	settings.setDrawInitialGravity ( sDrawInitialGravity);
	mHair.draw(settings, mDebugRenderer);
}else{
	// Draw the rods
	mHair.lockReadBackBuffers();
	FloatBuffer  positions = mHair.getRenderPositions();
	RMat44 com = mHair.getWorldTransform();
	if (sDrawRenderStrands)
	{
		Jolt.profileStart("Draw Render Strands");

		ConstColor color = Color.sWhite;
		for (ConstRStrand render_strand : mHairSettings.getRenderStrands())
		{
			RVec3 x0 = star(com ,new Vec3(positions,3*render_strand.getStartVtx()));
			for (int v = render_strand.getStartVtx() + 1; v < render_strand.getEndVtx(); ++v)
			{
				RVec3 x1 = star(com ,new Vec3(positions,3*v));
				mDebugRenderer.drawLine(x0, x1, color);
				x0 = x1;
			}
			color =new Color((int)(Jolt.hashCombine(0, color.getUInt32())) | 0xff000000);
		}
		Jolt.profileEnd();
	}
	mHair.unlockReadBackBuffers();
}

	// Get skinned vertices
	RMat44 neck_transform = mHair.getWorldTransform();

	if (sDrawHeadMesh)
	{
		Jolt.profileStart("Draw Head Mesh");

		FloatBuffer scalp_vertices = mHair.getScalpVertices();
		GeometryRef geometry = new Geometry(mDebugRenderer.createTriangleBatch(scalp_vertices, mHairSettings.getScalpTriangles()), mHairSettings.getSimulationBounds()).toRef();
		mDebugRenderer.drawGeometry(neck_transform, Color.sGrey, geometry, ECullMode.CullBackFace, ECastShadow.On, EDrawMode.Solid);
		Jolt.profileEnd();
	}

	// Select the next animation frame
	++mFrame;
	mFrame = mFrame % (int)mFaceAnimation.length;
	Mat44Array joints = mFaceAnimation[mFrame];

	// Position the collision hulls
	for ( AttachedBody ab : mAttachedBodies)
	{
		Mat44 body_transform = ab.mJointIdx != 0xffffffff? joints.get(ab.mJointIdx) : Mat44.sIdentity();
		bi.moveKinematic(ab.mBodyID,new RVec3(body_transform.getTranslation()), body_transform.getQuaternion(), inParams.mDeltaTime);
	}

	// Set the new rotation of the hair
	RVec3 position =new RVec3(joints.get(mHeadJointIdx).getTranslation());
	Quat rotation = joints.get(mHeadJointIdx).getQuaternion();
	mHair.setPosition(position);
	mHair.setRotation(rotation);

	// Update the hair
	mHair.update(inParams.mDeltaTime, joints.get(mHeadJointIdx).inversed(), joints, mPhysicsSystem, mHairShaders, mComputeSystem, mComputeQueue);
	{
		Jolt.profileStart("Hair Compute");
		mComputeQueue.executeAndWait();
		Jolt.profileEnd();
	}
	mHair.readBackGpuState(mComputeQueue);
}

public void SaveState(StateRecorder inStream)
{
	inStream.write(mFrame);
}

public void RestoreState(StateRecorder inStream)
{
	mFrame=inStream.readInt(mFrame);
}

/*
void HairTest::GradientSetting(DebugUI *inUI, UIElement *inSubMenu, const String &inName, float inMax, float inStep, HairSettings::Gradient &inStaticStorage, HairSettings::Gradient &inDynamicStorage)
{
	inUI->CreateTextButton(inSubMenu, inName, [inUI, inName, inMax, inStep, &inStaticStorage, &inDynamicStorage]() {
		UIElement *gradient_setting = inUI->CreateMenu();
		inUI->CreateSlider(gradient_setting, inName + " Min", inStaticStorage.mMin, 0.0f, inMax, inStep, [&inStaticStorage, &inDynamicStorage](float inValue) { inStaticStorage.mMin = inDynamicStorage.mMin = inValue; });
		inUI->CreateSlider(gradient_setting, inName + " Max", inStaticStorage.mMax, 0.0f, inMax, inStep, [&inStaticStorage, &inDynamicStorage](float inValue) { inStaticStorage.mMax = inDynamicStorage.mMax = inValue; });
		inUI->CreateSlider(gradient_setting, inName + " Min Fraction", inStaticStorage.mMinFraction, 0.0f, 1.0f, 0.01f, [&inStaticStorage, &inDynamicStorage](float inValue) { inStaticStorage.mMinFraction = inDynamicStorage.mMinFraction = min(inStaticStorage.mMaxFraction - 0.001f, inValue); });
		inUI->CreateSlider(gradient_setting, inName + " Max Fraction", inStaticStorage.mMaxFraction, 0.0f, 1.0f, 0.01f, [&inStaticStorage, &inDynamicStorage](float inValue) { inStaticStorage.mMaxFraction = inDynamicStorage.mMaxFraction = max(inStaticStorage.mMinFraction + 0.001f, inValue); });
		inUI->ShowMenu(gradient_setting);
	});
}

void HairTest::CreateSettingsMenu(DebugUI *inUI, UIElement *inSubMenu)
{
	inUI->CreateTextButton(inSubMenu, "Select Groom", [this, inUI]() {
		UIElement *groom_name = inUI->CreateMenu();
		for (uint i = 0; i < size(sGrooms); ++i)
			inUI->CreateTextButton(groom_name, sGrooms[i].mName, [this, i]() { sSelectedGroom = &sGrooms[i]; RestartTest(); });
		inUI->ShowMenu(groom_name);
	});
	inUI->CreateCheckBox(inSubMenu, "Limit Max Strands", sLimitMaxStrands, [](UICheckBox::EState inState) { sLimitMaxStrands = inState == UICheckBox::STATE_CHECKED; });
	inUI->CreateSlider(inSubMenu, "Max Strands", float(sMaxStrands), 1.0f, 10000.0f, 1.0f, [](float inValue) { sMaxStrands = uint(inValue); });
	inUI->CreateSlider(inSubMenu, "Simulation Strands Percentage", float(sSimulationStrandsPercentage), 1.0f, 100.0f, 1.0f, [](float inValue) { sSimulationStrandsPercentage = inValue; });
	inUI->CreateSlider(inSubMenu, "Override Vertices Per Strand", float(sOverrideVerticesPerStrand), 1.0f, 64.0f, 1.0f, [](float inValue) { sOverrideVerticesPerStrand = uint(inValue); });
	inUI->CreateSlider(inSubMenu, "Num Solver Iterations Per Second", float(sNumSolverIterationsPerSecond), 1.0f, 960.0f, 1.0f, [settings = mHairSettings](float inValue) { sNumSolverIterationsPerSecond = uint(inValue); settings->mNumIterationsPerSecond = sNumSolverIterationsPerSecond; });
	GradientSetting(inUI, inSubMenu, "Hair Radius", 0.01f, 0.001f, sHairRadius, mHairSettings->mMaterials[0].mHairRadius);
	inUI->CreateCheckBox(inSubMenu, "Enable Collision", sEnableCollision, [settings = mHairSettings](UICheckBox::EState inState) { sEnableCollision = inState == UICheckBox::STATE_CHECKED; settings->mMaterials[0].mEnableCollision = sEnableCollision; });
	inUI->CreateCheckBox(inSubMenu, "Enable LRA", sEnableLRA, [settings = mHairSettings](UICheckBox::EState inState) { sEnableLRA = inState == UICheckBox::STATE_CHECKED; settings->mMaterials[0].mEnableLRA = sEnableLRA; });
	inUI->CreateSlider(inSubMenu, "Bend Compliance (10^x)", sBendComplianceExponent, -10.0f, 0.0f, 0.01f, [settings = mHairSettings](float inValue) { sBendComplianceExponent = inValue; settings->mMaterials[0].mBendCompliance = std::pow(10.0f, inValue); });
	inUI->CreateSlider(inSubMenu, "Stretch Compliance (10^x)", sStretchComplianceExponent, -10.0f, 0.0f, 0.01f, [settings = mHairSettings](float inValue) { sStretchComplianceExponent = inValue; settings->mMaterials[0].mStretchCompliance = std::pow(10.0f, inValue); });
	inUI->CreateSlider(inSubMenu, "Inertia Multiplier", sInertiaMultiplier, 1.0f, 100.0f, 0.1f, [settings = mHairSettings](float inValue) { sInertiaMultiplier = inValue; settings->mMaterials[0].mInertiaMultiplier = inValue; });
	inUI->CreateSlider(inSubMenu, "Linear Damping", sLinearDamping, 0.0f, 5.0f, 0.01f, [settings = mHairSettings](float inValue) { sLinearDamping = inValue; settings->mMaterials[0].mLinearDamping = inValue; });
	inUI->CreateSlider(inSubMenu, "Angular Damping", sAngularDamping, 0.0f, 5.0f, 0.01f, [settings = mHairSettings](float inValue) { sAngularDamping = inValue; settings->mMaterials[0].mAngularDamping = inValue; });
	inUI->CreateSlider(inSubMenu, "Friction", sFriction, 0.0f, 1.0f, 0.01f, [settings = mHairSettings](float inValue) { sFriction = inValue; settings->mMaterials[0].mFriction = inValue; });
	inUI->CreateSlider(inSubMenu, "Max Linear Velocity", sMaxLinearVelocity, 0.01f, 10.0f, 0.01f, [settings = mHairSettings](float inValue) { sMaxLinearVelocity = inValue; settings->mMaterials[0].mMaxLinearVelocity = inValue; });
	inUI->CreateSlider(inSubMenu, "Max Angular Velocity", sMaxAngularVelocity, 0.01f, 50.0f, 0.01f, [settings = mHairSettings](float inValue) { sMaxAngularVelocity = inValue; settings->mMaterials[0].mMaxAngularVelocity = inValue; });
	GradientSetting(inUI, inSubMenu, "World Transform Influence", 1.0f, 0.01f, sWorldTransformInfluence, mHairSettings->mMaterials[0].mWorldTransformInfluence);
	GradientSetting(inUI, inSubMenu, "Gravity Factor", 1.0f, 0.01f, sGravityFactor, mHairSettings->mMaterials[0].mGravityFactor);
	inUI->CreateSlider(inSubMenu, "Gravity Preload Factor", sGravityPreloadFactor, 0.0f, 1.0f, 0.01f, [settings = mHairSettings](float inValue) { sGravityPreloadFactor = inValue; });
	GradientSetting(inUI, inSubMenu, "Grid Velocity Factor", 1.0f, 0.01f, sGridVelocityFactor, mHairSettings->mMaterials[0].mGridVelocityFactor);
	inUI->CreateSlider(inSubMenu, "Grid Density Force Factor", sGridDensityForceFactor, 0.0f, 10.0f, 0.1f, [settings = mHairSettings](float inValue) { sGridDensityForceFactor = inValue; settings->mMaterials[0].mGridDensityForceFactor = sGridDensityForceFactor; });
	GradientSetting(inUI, inSubMenu, "Global Pose", 1.0f, 0.001f, sGlobalPose, mHairSettings->mMaterials[0].mGlobalPose);
	GradientSetting(inUI, inSubMenu, "Skin Global Pose", 1.0f, 0.001f, sSkinGlobalPose, mHairSettings->mMaterials[0].mSkinGlobalPose);
#ifdef JPH_DEBUG_RENDERER
	if (mHairSettings->mSimStrands.size() > 1)
	{
		inUI->CreateSlider(inSubMenu, "Draw Simulation Strand Begin", (float)sDrawSimulationStrandBegin, 0.0f, float(mHairSettings->mSimStrands.size() - 1), 1.0f, [](float inValue) { sDrawSimulationStrandBegin = (uint)inValue; });
		inUI->CreateSlider(inSubMenu, "Draw Simulation Strand Count", (float)sDrawSimulationStrandCount, 1.0f, (float)mHairSettings->mSimStrands.size(), 1.0f, [](float inValue) { sDrawSimulationStrandCount = (uint)inValue; });
	}
	inUI->CreateCheckBox(inSubMenu, "Draw Rods", sDrawRods, [](UICheckBox::EState inState) { sDrawRods = inState == UICheckBox::STATE_CHECKED; });
	inUI->CreateCheckBox(inSubMenu, "Draw Unloaded Rods", sDrawUnloadedRods, [](UICheckBox::EState inState) { sDrawUnloadedRods = inState == UICheckBox::STATE_CHECKED; });
	inUI->CreateCheckBox(inSubMenu, "Draw Vertex Velocity", sDrawVertexVelocity, [](UICheckBox::EState inState) { sDrawVertexVelocity = inState == UICheckBox::STATE_CHECKED; });
	inUI->CreateCheckBox(inSubMenu, "Draw Angular Velocity", sDrawAngularVelocity, [](UICheckBox::EState inState) { sDrawAngularVelocity = inState == UICheckBox::STATE_CHECKED; });
	inUI->CreateCheckBox(inSubMenu, "Draw Rod Orientations", sDrawOrientations, [](UICheckBox::EState inState) { sDrawOrientations = inState == UICheckBox::STATE_CHECKED; });
	inUI->CreateCheckBox(inSubMenu, "Draw Neutral Density", sDrawNeutralDensity, [](UICheckBox::EState inState) { sDrawNeutralDensity = inState == UICheckBox::STATE_CHECKED; });
	inUI->CreateCheckBox(inSubMenu, "Draw Grid Density", sDrawGridDensity, [](UICheckBox::EState inState) { sDrawGridDensity = inState == UICheckBox::STATE_CHECKED; });
	inUI->CreateCheckBox(inSubMenu, "Draw Grid Velocity", sDrawGridVelocity, [](UICheckBox::EState inState) { sDrawGridVelocity = inState == UICheckBox::STATE_CHECKED; });
	inUI->CreateCheckBox(inSubMenu, "Draw Skin Points", sDrawSkinPoints, [](UICheckBox::EState inState) { sDrawSkinPoints = inState == UICheckBox::STATE_CHECKED; });
	inUI->CreateCheckBox(inSubMenu, "Draw Render Strands", sDrawRenderStrands, [](UICheckBox::EState inState) { sDrawRenderStrands = inState == UICheckBox::STATE_CHECKED; });
	inUI->CreateComboBox(inSubMenu, "Render Strands Color", { "PerRenderStrand", "PerSimulatedStrand", "GravityFactor", "WorldInfluence", "GridVelocityFactor", "GlobalPose", "SkinGlobalPose" }, (int)sRenderStrandColor, [](int inItem) { sRenderStrandColor = (Hair::ERenderStrandColor)inItem; });
	inUI->CreateCheckBox(inSubMenu, "Draw Initial Gravity", sDrawInitialGravity, [](UICheckBox::EState inState) { sDrawInitialGravity = inState == UICheckBox::STATE_CHECKED; });
#endif // JPH_DEBUG_RENDERER
	inUI->CreateCheckBox(inSubMenu, "Draw Head Mesh", sDrawHeadMesh, [](UICheckBox::EState inState) { sDrawHeadMesh = inState == UICheckBox::STATE_CHECKED; });
}
*/}
