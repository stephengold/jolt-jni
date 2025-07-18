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
package testjoltjni.app.samples.general;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt-Physics pyramid test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/General/PyramidTest.cpp
 */
public class PyramidTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	final float cBoxSize = 2.0f;
	final float cBoxSeparation = 0.5f;
	final float cHalfBoxSize = 0.5f * cBoxSize;
	final int cPyramidHeight = 15;

	ShapeRefC box_shape = new BoxShape(Vec3.sReplicate(cHalfBoxSize)).toRefC();

	// Pyramid
	for (int i = 0; i < cPyramidHeight; ++i)
		for (int j = i / 2; j < cPyramidHeight - (i + 1) / 2; ++j)
			for (int k = i / 2; k < cPyramidHeight - (i + 1) / 2; ++k)
			{
				RVec3 position=new RVec3(-cPyramidHeight + cBoxSize * j + ((i & 1)!=0? cHalfBoxSize : 0.0f), 1.0f + (cBoxSize + cBoxSeparation) * i, -cPyramidHeight + cBoxSize * k + ((i & 1)!=0? cHalfBoxSize : 0.0f));
				mBodyInterface.createAndAddBody(new BodyCreationSettings(box_shape, position, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING), EActivation.Activate);
			}
}
}
