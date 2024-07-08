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
package testjoltjni.junit;

import com.github.stephengold.joltjni.Body;
import com.github.stephengold.joltjni.BodyCreationSettings;
import com.github.stephengold.joltjni.BodyId;
import com.github.stephengold.joltjni.BodyInterface;
import com.github.stephengold.joltjni.BoxShapeSettings;
import com.github.stephengold.joltjni.EActivation;
import com.github.stephengold.joltjni.EMotionType;
import com.github.stephengold.joltjni.EPhysicsUpdateError;
import com.github.stephengold.joltjni.EShapeSubType;
import com.github.stephengold.joltjni.EShapeType;
import com.github.stephengold.joltjni.JobSystem;
import com.github.stephengold.joltjni.JobSystemThreadPool;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.MapObj2Bp;
import com.github.stephengold.joltjni.ObjVsBpFilter;
import com.github.stephengold.joltjni.ObjVsObjFilter;
import com.github.stephengold.joltjni.PhysicsSystem;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.Shape;
import com.github.stephengold.joltjni.SphereShape;
import com.github.stephengold.joltjni.TempAllocator;
import com.github.stephengold.joltjni.TempAllocatorImpl;
import com.github.stephengold.joltjni.Vec3;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.Utils;

/**
 * An automated test for jolt-jni using JUnit 4.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test001 {
    // *************************************************************************
    // new methods exposed

    /**
     * Drop a dynamic spherical ball onto a static box.
     * <p>
     * Derived from HelloWorld.cpp by Jorrit Rouwe.
     */
    @Test
    public void test001() {
        Utils.loadNativeLibrary();

        Jolt.registerDefaultAllocator();
        Jolt.installDefaultTraceCallback();
        Jolt.installDefaultAssertCallback();
        Jolt.newFactory();
        Jolt.registerTypes();

        // object layers:
        final int objLayerNonMoving = 0;
        final int objLayerMoving = 1;
        final int numObjLayers = 2;

        // broadphase layers:
        final int bpLayerNonMoving = 0;
        final int bpLayerMoving = 1;
        final int numBpLayers = 2;

        MapObj2Bp mapObj2Bp = new MapObj2Bp(numObjLayers, numBpLayers)
                .add(objLayerNonMoving, bpLayerNonMoving)
                .add(objLayerMoving, bpLayerMoving);

        ObjVsBpFilter objVsBpFilter
                = new ObjVsBpFilter(numObjLayers, numBpLayers)
                        .disablePair(objLayerNonMoving, bpLayerNonMoving);

        ObjVsObjFilter objVsObjFilter = new ObjVsObjFilter(numObjLayers)
                .disablePair(objLayerNonMoving, objLayerNonMoving);

        final int maxBodies = 4;
        final int numBodyMutexes = 0; // 0 means "use the default value"
        final int maxBodyPairs = 4;
        final int maxContacts = 4;
        PhysicsSystem physicsSystem = new PhysicsSystem();
        physicsSystem.init(maxBodies, numBodyMutexes, maxBodyPairs, maxContacts,
                mapObj2Bp, objVsBpFilter, objVsObjFilter);
        BodyInterface bodyInterface = physicsSystem.getBodyInterface();

        BoxShapeSettings floorShapeSettings
                = new BoxShapeSettings(new Vec3(100f, 1f, 100f));
        RVec3 floorLocation = new RVec3(0.0, -1.0, 0.0);
        Quat orientation = new Quat();
        BodyCreationSettings floorBodySettings
                = new BodyCreationSettings(floorShapeSettings, floorLocation,
                        orientation, EMotionType.Static, objLayerNonMoving);
        Body floor = bodyInterface.createBody(floorBodySettings);
        BodyId floorId = floor.getId();
        bodyInterface.addBody(floorId, EActivation.DontActivate);

        Shape ballShape = new SphereShape(0.5f);
        Assert.assertEquals(EShapeSubType.Sphere, ballShape.getSubType());
        Assert.assertEquals(EShapeType.Convex, ballShape.getType());

        RVec3 ballLocation = new RVec3(0.0, 2.0, 0.0);
        BodyCreationSettings ballSettings = new BodyCreationSettings(
                ballShape, ballLocation, orientation,
                EMotionType.Dynamic, objLayerMoving);
        BodyId ballId = bodyInterface.createAndAddBody(
                ballSettings, EActivation.Activate);
        Vec3 ballVelocity = new Vec3(0f, -5f, 0f);
        bodyInterface.setLinearVelocity(ballId, ballVelocity);

        physicsSystem.optimizeBroadPhase();

        final int numBytes = 1 << 18; // 256 KiB
        TempAllocator allocator = new TempAllocatorImpl(numBytes);

        final int maxJobs = 4_096;
        final int maxBarriers = 4;
        int numThreads = Utils.numThreads();
        JobSystem jobSystem
                = new JobSystemThreadPool(maxJobs, maxBarriers, numThreads);

        int stepCounter = 0;
        while (bodyInterface.isActive(ballId) && stepCounter < 99) {
            final float deltaTime = 1 / 60f;
            int errors
                    = physicsSystem.update(deltaTime, 1, allocator, jobSystem);
            Assert.assertEquals(EPhysicsUpdateError.None, errors);
            ++stepCounter;
        }

        Assert.assertEquals(48, stepCounter);

        ballLocation = bodyInterface.getCenterOfMassPosition(ballId);
        Utils.assertEquals(0f, 0.48f, 0f, ballLocation, 1e-5f);

        ballVelocity = bodyInterface.getLinearVelocity(ballId);
        Utils.assertEquals(0f, 0f, 0f, ballVelocity, 1e-5f);

        jobSystem.close();

        bodyInterface.removeBody(ballId);
        bodyInterface.destroyBody(ballId);

        bodyInterface.removeBody(floorId);
        bodyInterface.destroyBody(floorId);

        floorShapeSettings.close();
        physicsSystem.close();
        objVsObjFilter.close();
        objVsBpFilter.close();
        mapObj2Bp.close();
        allocator.close();

        Jolt.unregisterTypes();
        Jolt.destroyFactory();
    }
}
