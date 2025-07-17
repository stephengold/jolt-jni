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
package testjoltjni.app.samples.water;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.readonly.*;
import com.github.stephengold.joltjni.std.*;
import testjoltjni.app.samples.*;
import java.util.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt-Physics water-shape test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Water/WaterShapeTest.cpp
 */
public class WaterShapeTest extends Test{
    class MyCollector extends CustomCollideShapeBodyCollector{
        PhysicsSystem mSystem;RVec3 mSurfacePosition;Vec3 mSurfaceNormal;float mDeltaTime;
        public MyCollector(PhysicsSystem inSystem,RVec3Arg inSurfacePosition,Vec3Arg inSurfaceNormal,float inDeltaTime) {
            mSystem=inSystem;mSurfacePosition=new RVec3(inSurfacePosition);mSurfaceNormal=new Vec3(inSurfaceNormal);mDeltaTime=inDeltaTime;
        }
        public void addHit(int bodyId){
            BodyLockWrite lock=new BodyLockWrite(mSystem.getBodyLockInterface(), bodyId);
            Body body=lock.getBody();
            if(body.isActive())body.applyBuoyancyImpulse(mSurfacePosition,mSurfaceNormal,1.1f,0.3f,0.05f,Vec3.sZero(),mSystem.getGravity(),mDeltaTime);
            lock.releaseLock();
        }
    }

void Initialize()
{
	CreateFloor();

	// Create scaled box
	int body_id = mBodyInterface.createBody(new BodyCreationSettings(new ScaledShape(new BoxShape(new Vec3(1.0f, 2.0f, 2.5f)),new Vec3(0.5f, 0.6f, -0.7f)),new RVec3(-10, 20, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING)).getId();
	mBodyInterface.addBody(body_id, EActivation.Activate);

	// Create box
	body_id = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(new Vec3(1.0f, 2.0f, 2.5f)),new  RVec3(-7, 20, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING)).getId();
	mBodyInterface.addBody(body_id, EActivation.Activate);

	// Create sphere
	body_id = mBodyInterface.createBody(new BodyCreationSettings(new SphereShape(2.0f),new RVec3(-3, 20, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING)).getId();
	mBodyInterface.addBody(body_id, EActivation.Activate);

	// Create static compound
	StaticCompoundShapeSettings static_compound = new StaticCompoundShapeSettings();
	static_compound.addShape(new Vec3(2.0f, 0, 0), Quat.sIdentity(), new SphereShape(2.0f));
	static_compound.addShape(new Vec3(-1.0f, 0, 0), Quat.sIdentity(), new SphereShape(1.0f));

	body_id = mBodyInterface.createBody(new BodyCreationSettings(static_compound,new RVec3(3, 20, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING)).getId();
	mBodyInterface.addBody(body_id, EActivation.Activate);

	// Create tetrahedron
	List<Vec3Arg> tetrahedron=new ArrayList<>(4);
	tetrahedron.add(new Vec3(-2, 0, -2));
	tetrahedron.add(new Vec3(0, 0, 2));
	tetrahedron.add(new Vec3(2, 0, -2));
	tetrahedron.add(new Vec3(0, -2, 0));
	ConvexHullShapeSettings tetrahedron_shape = new ConvexHullShapeSettings(tetrahedron);
	body_id = mBodyInterface.createBody(new BodyCreationSettings(tetrahedron_shape,new RVec3(10, 20, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING)).getId();
	mBodyInterface.addBody(body_id, EActivation.Activate);

	// Non-uniform scaled tetrahedron
	body_id = mBodyInterface.createBody(new BodyCreationSettings(new ScaledShapeSettings(tetrahedron_shape,new Vec3(1, -1.5f, 2.0f)),new RVec3(15, 20, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING)).getId();
	mBodyInterface.addBody(body_id, EActivation.Activate);

	// Create convex hull box
	List<Vec3Arg> box=new ArrayList<>(8);
	box.add(new Vec3(1.5f, 1.0f, 0.5f));
	box.add(new Vec3(-1.5f, 1.0f, 0.5f));
	box.add(new Vec3(1.5f, -1.0f, 0.5f));
	box.add(new Vec3(-1.5f, -1.0f, 0.5f));
	box.add(new Vec3(1.5f, 1.0f, -0.5f));
	box.add(new Vec3(-1.5f, 1.0f, -0.5f));
	box.add(new Vec3(1.5f, -1.0f, -0.5f));
	box.add(new Vec3(-1.5f, -1.0f, -0.5f));
	body_id = mBodyInterface.createBody(new BodyCreationSettings(new ConvexHullShapeSettings(box),new RVec3(18, 20, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING)).getId();
	mBodyInterface.addBody(body_id, EActivation.Activate);

	// Create random convex shape
	DefaultRandomEngine random=new DefaultRandomEngine();
	UniformFloatDistribution hull_size=new UniformFloatDistribution(0.1f, 1.9f);
	List<Vec3Arg> points=new ArrayList<>(20);
	for (int j = 0; j < 20; ++j)
		points.add(star(hull_size.nextFloat(random) , Vec3.sRandom(random)));
	body_id = mBodyInterface.createBody(new BodyCreationSettings(new ConvexHullShapeSettings(points),new RVec3(21, 20, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING)).getId();
	mBodyInterface.addBody(body_id, EActivation.Activate);

	// Create mutable compound
	MutableCompoundShapeSettings mutable_compound = new MutableCompoundShapeSettings();
	mutable_compound.addShape(new Vec3(1.0f, 0, 0), Quat.sIdentity(), new BoxShape(new Vec3(0.5f, 0.75f, 1.0f)));
	mutable_compound.addShape(new Vec3(-1.0f, 0, 0), Quat.sIdentity(), new SphereShape(1.0f));

	body_id = mBodyInterface.createBody(new BodyCreationSettings(mutable_compound,new RVec3(25, 20, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING)).getId();
	mBodyInterface.addBody(body_id, EActivation.Activate);

	// Create box with center of mass offset
	body_id = mBodyInterface.createBody(new BodyCreationSettings(new OffsetCenterOfMassShapeSettings(new Vec3(-1.0f, 0.0f, 0.0f), new BoxShape(new Vec3(2.0f, 0.25f, 0.25f)).toRefC()),new RVec3(30, 20, 0), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING)).getId();
	mBodyInterface.addBody(body_id, EActivation.Activate);
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	// Draw the water surface 5mm below actual surface to avoid z fighting with intersection shapes
	RVec3 surface_point =new RVec3(0, 10, 0);
	for (int i = -20; i <= 20; ++i)
	{
		mDebugRenderer.drawLine(plus(surface_point ,new Vec3(5.0f * i, 0, -100)), plus(surface_point ,new Vec3(5.0f * i, 0, 100)), Color.sBlue);
		mDebugRenderer.drawLine(plus(surface_point ,new Vec3(-100, 0, 5.0f * i)), plus(surface_point ,new Vec3(100, 0, 5.0f * i)), Color.sBlue);
	}

	MyCollector collector=new MyCollector(mPhysicsSystem, surface_point, Vec3.sAxisY(), inParams.mDeltaTime);



	// Apply buoyancy to all bodies that intersect with the water
	AaBox water_box=new AaBox(minus(new Vec3(100, 100, 100)),new Vec3(100, 0, 100));
	water_box.translate(new Vec3(surface_point));
	mPhysicsSystem.getBroadPhaseQuery().collideAaBox(water_box, collector,new SpecifiedBroadPhaseLayerFilter(BroadPhaseLayers.MOVING),new SpecifiedObjectLayerFilter(Layers.MOVING));
}
}
