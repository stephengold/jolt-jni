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
 * A line-for-line Java translation of the Jolt Physics layer mappings for the samples app.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Layers.h
 */

/// BroadPhaseLayerInterface implementation
public class BPLayerInterfaceImpl extends MapObj2Bp
{
	public BPLayerInterfaceImpl()
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
