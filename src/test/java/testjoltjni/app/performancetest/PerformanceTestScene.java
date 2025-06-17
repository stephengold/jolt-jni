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
import com.github.stephengold.joltjni.PhysicsSystem;
import com.github.stephengold.joltjni.TempAllocator;
import com.github.stephengold.joltjni.enumerate.EMotionQuality;
/**
 * A line-for-line Java translation of the Jolt-Physics performance test-scene
 * interface.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/PerformanceTest/PerformanceTestScene.h
 */
interface PerformanceTestScene
{
	// Get name of test for debug purposes
	String   	GetName()   ;

	// Load assets for the scene
	default boolean			Load(   )	{ return true; }

	// Start a new test by adding objects to inPhysicsSystem
	void			StartTest(PhysicsSystem inPhysicsSystem, EMotionQuality inMotionQuality)  ;

	// Step the test
	default void			UpdateTest( PhysicsSystem inPhysicsSystem,  TempAllocator ioTempAllocator,  float inDeltaTime) { }

	// Update the hash with the state of the scene
	default long			UpdateHash(long  ioHash) 	{return ioHash;}

	// Stop a test and remove objects from inPhysicsSystem
	default void			StopTest(PhysicsSystem inPhysicsSystem)			{ }
};
