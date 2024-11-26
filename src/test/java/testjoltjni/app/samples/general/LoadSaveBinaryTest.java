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
package testjoltjni.app.samples.general;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.std.StringStream;
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt Physics load/save binary test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/LoadSaveBinaryTest.cpp
 */
public class LoadSaveBinaryTest extends Test{

public void Initialize()
{
	// Create scene
	PhysicsSceneRef scene = LoadSaveSceneTest.sCreateScene();

	{
		// Create a new scene by instantiating the scene in a physics system and then converting it back to a scene
		PhysicsSystem system=new PhysicsSystem();
		BPLayerInterfaceImpl layer_interface=new BPLayerInterfaceImpl();
		ObjectVsBroadPhaseLayerFilterImpl object_vs_broadphase_layer_filter=new ObjectVsBroadPhaseLayerFilterImpl();
		ObjectLayerPairFilterImpl object_vs_object_layer_filter=new ObjectLayerPairFilterImpl();
		system.init(mPhysicsSystem.getMaxBodies(), 0, 1024, 1024, layer_interface, object_vs_broadphase_layer_filter, object_vs_object_layer_filter);
		scene.createBodies(system);
		PhysicsSceneRef scene_copy = new PhysicsScene().toRef();
		scene_copy.fromPhysicsSystem(system);

		// Replace the original scene
		scene = scene_copy;
	}

	StringStream data=new StringStream();

	// Write scene
	{
		StreamOutWrapper stream_out=new StreamOutWrapper(data);
		scene.saveBinaryState(stream_out, true, true);
	}

	// Clear scene
	scene = null;

	// Read scene back in
	{
		StreamInWrapper stream_in=new StreamInWrapper(data);
		PhysicsSceneResult result = PhysicsScene.sRestoreFromBinaryState(stream_in);
		if (result.hasError())
			FatalError(result.getError());
		scene = result.get();
	}

	// Instantiate scene
	scene.createBodies(mPhysicsSystem);
}
}