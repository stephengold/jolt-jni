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
import java.util.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the Jolt-Physics constraint-vs-COM change
 * test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/ConstraintVsCOMChangeTest.cpp
 */
public class ConstraintVsCOMChangeTest extends Test{
ShapeRefC mBox=new ShapeRefC();
List<Body> mBodies=new ArrayList<>();
List<TwoBodyConstraintRef> mConstraints=new ArrayList<>();
static final float cBoxSize = 2.0f;
float mTime = 0.0f;
int mNumShapes = -1;

public void Initialize()
{
	final int cChainLength = 15;
	final float cMinAngle = degreesToRadians(-10.0f);
	final float cMaxAngle = degreesToRadians(20.0f);

	// Floor
	CreateFloor();

	// Create box shape
	mBox = new BoxShape(Vec3.sReplicate(0.5f * cBoxSize)).toRefC();

	// Build a collision group filter that disables collision between adjacent bodies
	GroupFilterTableRef group_filter = new GroupFilterTable(cChainLength).toRef();
	for (int i = 0; i < cChainLength - 1; ++i)
		group_filter.disableCollision(i, i + 1);

	// Create chain of bodies
	RVec3 position=new RVec3(0, 25, 0);
	for (int i = 0; i < cChainLength; ++i)
	{
		plusEquals(position ,new Vec3(cBoxSize, 0, 0));
		Quat rotation = Quat.sIdentity();

		// Create compound shape specific for this body
		MutableCompoundShapeSettings compound_shape=new MutableCompoundShapeSettings();
		compound_shape.setEmbedded();
		compound_shape.addShape(Vec3.sZero(), Quat.sIdentity(), mBox);

		// Create body
		Body segment = mBodyInterface.createBody(new BodyCreationSettings(compound_shape, position, rotation, i == 0 ? EMotionType.Static : EMotionType.Dynamic, i == 0 ? Layers.NON_MOVING : Layers.MOVING));
		segment.setCollisionGroup(new CollisionGroup(group_filter, 0, (i)));
		mBodyInterface.addBody(segment.getId(), EActivation.Activate);

		if (i > 0)
		{
			// Create hinge
			HingeConstraintSettings settings=new HingeConstraintSettings();
			settings.setPoint1 ( settings.setPoint2 ( plus(position ,new Vec3(-0.5f * cBoxSize, -0.5f * cBoxSize, 0))));
			settings.setHingeAxis1 ( settings.setHingeAxis2 ( Vec3.sAxisZ()));
			settings.setNormalAxis1 ( settings.setNormalAxis2 ( Vec3.sAxisX()));
			settings.setLimitsMin ( cMinAngle);
			settings.setLimitsMax ( cMaxAngle);
			TwoBodyConstraint constraint = settings.create(mBodies.get(mBodies.size()-1), segment);
			mPhysicsSystem.addConstraint(constraint);

			mConstraints.add(constraint.toRef());
		}

		mBodies.add(segment);
	}
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	// Increment time
	mTime += inParams.mDeltaTime;

	UpdateShapes();
}

protected void SaveState(StateRecorder inStream)
{
	inStream.write(mTime);
}

protected void RestoreState(StateRecorder inStream)
{
	mTime=inStream.readFloat(mTime);

	UpdateShapes();
}

void UpdateShapes()
{
	// Check if we need to change the configuration
	int num_shapes = (((int)mTime & 1)==0)? 2 : 1;
	if (mNumShapes != num_shapes)
	{
		mNumShapes = num_shapes;

		// Change the COM of the bodies
		for (int i = 1; i < (int)mBodies.size(); i += 2)
		{
			Body b = mBodies.get(i);
			MutableCompoundShape s = (MutableCompoundShape)(b.getShape());

			// Remember the center of mass before the change
			Vec3 prev_com = s.getCenterOfMass();

			// First remove all existing shapes
			for (int j = s.getNumSubShapes() - 1; j >= 0; --j)
				s.removeShape(j);

			// Then create the desired number of shapes
			for (int j = 0; j < num_shapes; ++j)
				s.addShape(new Vec3(0, 0, (1.0f + cBoxSize) * j), Quat.sIdentity(), mBox);

			// Update the center of mass to account for the new box configuration
			s.adjustCenterOfMass();

			// Notify the physics system that the shape has changed
			mBodyInterface.notifyShapeChanged(b.getId(), prev_com, true, EActivation.Activate);

			// Notify the constraints that the shape has changed (this could be done more efficient as we know which constraints are affected)
			Vec3 delta_com = minus(s.getCenterOfMass() , prev_com);
			for (TwoBodyConstraintRef c : mConstraints)
				c.notifyShapeChanged(b.getId(), delta_com);
		}
	}
}
}
