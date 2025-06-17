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
package testjoltjni.app.testframework;
import com.github.stephengold.joltjni.*;
/**
 * A line-for-line Java translation of the Jolt-Physics camera setup for debug
 * rendering.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/TestFramework/Renderer/Renderer.h
 */
// Forward declares

/// Camera setup
public class CameraState
{
									public CameraState() { }

	public RVec3							mPos = RVec3.sZero();								///< Camera position
	public Vec3							mForward = new Vec3(0, 0, -1);							///< Camera forward vector
	public Vec3							mUp = new Vec3(0, 1, 0);							///< Camera up vector
	public float							mFOVY = Jolt.degreesToRadians(70.0f);						///< Field of view in radians in up direction
};

