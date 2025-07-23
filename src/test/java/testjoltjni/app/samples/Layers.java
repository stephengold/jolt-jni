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
package testjoltjni.app.samples;
/**
 * A line-for-line Java translation of the Jolt-Physics object-layer definitions
 * used in Jolt-JNI "Samples" tests.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Layers.h
 */
/// Layer that objects can be in, determines which other objects it can collide with
public class Layers
{
	static final int UNUSED1 = 0; // 4 unused values so that broadphase layers values don't match with object layer values (for testing purposes)
	static final int UNUSED2 = 1;
	static final int UNUSED3 = 2;
	static final int UNUSED4 = 3;
	public static final int NON_MOVING = 4;
	public static final int MOVING = 5;
	public static final int DEBRIS = 6; // Example: Debris collides only with NON_MOVING
	public static final int SENSOR = 7; // Sensors only collide with MOVING objects
	public static final int NUM_LAYERS = 8;
};
