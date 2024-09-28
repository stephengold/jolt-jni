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

/**
 * A line-for-line Java translation of the Jolt Physics layer definitions for
 * the samples app.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Layers.h
 */
/// Layer that objects can be in, determines which other objects it can collide with
class Layers
{
	static final int UNUSED1 = 0; // 4 unused values so that broadphase layers values don't match with object layer values (for testing purposes)
	static final int UNUSED2 = 1;
	static final int UNUSED3 = 2;
	static final int UNUSED4 = 3;
	static final int NON_MOVING = 4;
	static final int MOVING = 5;
	static final int DEBRIS = 6; // Example: Debris collides only with NON_MOVING
	static final int SENSOR = 7; // Sensors only collide with MOVING objects
	static final int NUM_LAYERS = 8;
};

/// Class that determines if two object layers can collide
class ObjectLayerPairFilterImpl extends ObjVsObjFilter
{
	ObjectLayerPairFilterImpl() {
		super(Layers.NUM_LAYERS);
		disableLayer(Layers.UNUSED1);
		disableLayer(Layers.UNUSED2);
		disableLayer(Layers.UNUSED3);
		disableLayer(Layers.UNUSED4);
		disablePair(Layers.NON_MOVING, Layers.NON_MOVING);
		disablePair(Layers.NON_MOVING, Layers.SENSOR);
		disablePair(Layers.MOVING, Layers.DEBRIS);
		disablePair(Layers.DEBRIS, Layers.DEBRIS);
		disablePair(Layers.DEBRIS, Layers.SENSOR);
		disablePair(Layers.SENSOR, Layers.SENSOR);
	}
};

/// Broadphase layers
class BroadPhaseLayers
{
	static final int NON_MOVING = 0;
	static final int MOVING = 1;
	static final int DEBRIS = 2;
	static final int SENSOR = 3;
	static final int UNUSED = 4;
	static final int NUM_LAYERS = 5;
};

/// BroadPhaseLayerInterface implementation
class BPLayerInterfaceImpl extends MapObj2Bp
{
									BPLayerInterfaceImpl()
	{
		super(Layers.NUM_LAYERS, BroadPhaseLayers.NUM_LAYERS);
		// Create a mapping table from object to broad phase layer
		add(Layers.UNUSED1, BroadPhaseLayers.UNUSED);
		add(Layers.UNUSED2, BroadPhaseLayers.UNUSED);
		add(Layers.UNUSED3, BroadPhaseLayers.UNUSED);
		add(Layers.UNUSED4, BroadPhaseLayers.UNUSED);
		add(Layers.NON_MOVING, BroadPhaseLayers.NON_MOVING);
		add(Layers.MOVING, BroadPhaseLayers.MOVING);
		add(Layers.DEBRIS, BroadPhaseLayers.DEBRIS);
		add(Layers.SENSOR, BroadPhaseLayers.SENSOR);
	}
};

/// Class that determines if an object layer can collide with a broadphase layer
class ObjectVsBroadPhaseLayerFilterImpl extends ObjVsBpFilter
{
	ObjectVsBroadPhaseLayerFilterImpl() {
		super(Layers.NUM_LAYERS, BroadPhaseLayers.NUM_LAYERS);
		disableBp(BroadPhaseLayers.UNUSED);
		disableObj(Layers.UNUSED1);
		disableObj(Layers.UNUSED2);
		disableObj(Layers.UNUSED3);
		disableObj(Layers.UNUSED4);
		disablePair(Layers.NON_MOVING, BroadPhaseLayers.NON_MOVING);
		disablePair(Layers.NON_MOVING, BroadPhaseLayers.SENSOR);
		disablePair(Layers.MOVING, BroadPhaseLayers.DEBRIS);
		disablePair(Layers.DEBRIS, BroadPhaseLayers.MOVING);
		disablePair(Layers.DEBRIS, BroadPhaseLayers.DEBRIS);
		disablePair(Layers.DEBRIS, BroadPhaseLayers.SENSOR);
		disablePair(Layers.SENSOR, BroadPhaseLayers.NON_MOVING);
		disablePair(Layers.SENSOR, BroadPhaseLayers.DEBRIS);
		disablePair(Layers.SENSOR, BroadPhaseLayers.SENSOR);
	}
};
