/*
Copyright (c) 2024-2025 Stephen Gold

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
package testjoltjni.app.performancetest;
import com.github.stephengold.joltjni.*;

/**
 * A line-for-line Java translation of the Jolt Physics layer definitions for
 * performance testing.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/PerformanceTest/Layers.h
 */
/// Layer that objects can be in, determines which other objects it can collide with
class Layers
{
	static final int NON_MOVING = 0;
	static final int MOVING = 1;
	static final int NUM_LAYERS = 2;
};

/// Class that determines if two object layers can collide
class ObjectLayerPairFilterImpl extends ObjVsObjFilter
{
	ObjectLayerPairFilterImpl() {
		super(Layers.NUM_LAYERS);
		disablePair(Layers.NON_MOVING, Layers.NON_MOVING);
	}
};

/// Broadphase layers
class BroadPhaseLayers
{
	static final int NON_MOVING = 0;
	static final int MOVING = 1;
	static final int NUM_LAYERS = 2;
};

/// BroadPhaseLayerInterface implementation
class BPLayerInterfaceImpl extends BroadPhaseLayerInterfaceTable
{
									BPLayerInterfaceImpl()
	{
		super(Layers.NUM_LAYERS, BroadPhaseLayers.NUM_LAYERS);
		mapObjectToBroadPhaseLayer(Layers.NON_MOVING, BroadPhaseLayers.NON_MOVING);
		mapObjectToBroadPhaseLayer(Layers.MOVING, BroadPhaseLayers.MOVING);
	}
};

/// Class that determines if an object layer can collide with a broadphase layer
class ObjectVsBroadPhaseLayerFilterImpl extends ObjVsBpFilter
{
	ObjectVsBroadPhaseLayerFilterImpl() {
		super(Layers.NUM_LAYERS, BroadPhaseLayers.NUM_LAYERS);
		disablePair(Layers.NON_MOVING, BroadPhaseLayers.NON_MOVING);
	}
};
