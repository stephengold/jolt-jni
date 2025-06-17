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
package testjoltjni.app.samples.constraints;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt-Physics pulley-constraint test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/PulleyConstraintTest.cpp
 */
public class PulleyConstraintTest extends Test{

public void Initialize()
{
	// Floor
	CreateFloor();

	// Variation 0: Max length (rope)
	// Variation 1: Fixed length (rigid rod)
	// Variation 2: Min/max length
	// Variation 3: With ratio (block and tackle)
	for (int variation = 0; variation < 4; ++variation)
	{
		RVec3 position1=new RVec3(-10, 10, -10.0f * variation);
		Body body1 = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(0.5f)), position1, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		mBodyInterface.addBody(body1.getId(), EActivation.Activate);

		RVec3 position2=new RVec3(10, 10, -10.0f * variation);
		Body body2 = mBodyInterface.createBody(new BodyCreationSettings(new BoxShape(Vec3.sReplicate(0.5f)), position2, Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING));
		mBodyInterface.addBody(body2.getId(), EActivation.Activate);

		PulleyConstraintSettings settings=new PulleyConstraintSettings();
		settings.setBodyPoint1 ( plus(position1 ,new Vec3(0, 0.5f, 0))); // Connect at the top of the block
		settings.setBodyPoint2 ( plus(position2 ,new Vec3(0, 0.5f, 0)));
		settings.setFixedPoint1 ( plus(settings.getBodyPoint1() ,new Vec3(0, 10, 0)));
		settings.setFixedPoint2 ( plus(settings.getBodyPoint2() ,new Vec3(0, 10, 0)));

		switch (variation)
		{
		case 0:
			// Can't extend but can contract
			break;

		case 1:
			// Fixed size
			settings.setMinLength ( settings.setMaxLength ( -1));
			break;

		case 2:
			// With range
			settings.setMinLength ( 18.0f);
			settings.setMaxLength ( 22.0f);
			break;

		case 3:
			// With ratio
			settings.setRatio ( 4.0f);
			break;
		}

		mPhysicsSystem.addConstraint(settings.create(body1, body2));
	}
}
}
