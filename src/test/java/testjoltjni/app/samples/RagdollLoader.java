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
import com.github.stephengold.joltjni.enumerate.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
/**
 * A line-for-line Java translation of the {@code RagdollLoader} namespace.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Utils/RagdollLoader.cpp
 */
public class RagdollLoader {

public static RagdollSettings sLoad(String inFileName,EMotionType inMotionType){return sLoad(inFileName, inMotionType, EConstraintOverride.TypeRagdoll);}
public static RagdollSettings sLoad(String inFileName, EMotionType inMotionType, EConstraintOverride inConstraintOverride)
{
	// Read the ragdoll
	RagdollSettingsRef ragdoll = new RagdollSettingsRef();
	if (!ObjectStreamIn.sReadObject(inFileName, ragdoll))
		throw new RuntimeException("Unable to read ragdoll");

	for (Part p : ragdoll.getParts())
	{
		// Update motion type
		p.setMotionType ( inMotionType);

		// Override layer
		p.setObjectLayer ( Layers.MOVING);

		// Create new constraint
		SwingTwistConstraintSettings original = (SwingTwistConstraintSettings)(p.getToParent(EConstraintSubType.SwingTwist));
		if (original != nullptr)
			switch (inConstraintOverride)
			{
			case TypeFixed:
				{
					FixedConstraintSettings settings = new FixedConstraintSettings();
					settings.setPoint1 ( settings.setPoint2 ( original.getPosition1()));
					p.setToParent ( settings);
					break;
				}

			case TypePoint:
				{
					PointConstraintSettings settings = new PointConstraintSettings();
					settings.setPoint1 ( settings.setPoint2 ( original.getPosition1()));
					p.setToParent ( settings);
					break;
				}

			case TypeHinge:
				{
					HingeConstraintSettings settings = new HingeConstraintSettings();
					settings.setPoint1 ( original.getPosition1());
					settings.setHingeAxis1 ( original.getPlaneAxis1());
					settings.setNormalAxis1 ( original.getTwistAxis1());
					settings.setPoint2 ( original.getPosition2());
					settings.setHingeAxis2 ( original.getPlaneAxis2());
					settings.setNormalAxis2 ( original.getTwistAxis2());
					settings.setLimitsMin ( -original.getNormalHalfConeAngle());
					settings.setLimitsMax ( original.getNormalHalfConeAngle());
					settings.setMaxFrictionTorque ( original.getMaxFrictionTorque());
					p.setToParent ( settings);
					break;
				}

			case TypeSlider:
				{
					SliderConstraintSettings settings = new SliderConstraintSettings();
					settings.setPoint1 ( settings.setPoint2 ( original.getPosition1()));
					settings.setSliderAxis1 ( settings.setSliderAxis2 ( original.getTwistAxis1()));
					settings.setNormalAxis1 ( settings.setNormalAxis2 ( original.getTwistAxis1())).getNormalizedPerpendicular();
					settings.setLimitsMin ( -1.0f);
					settings.setLimitsMax ( 1.0f);
					settings.setMaxFrictionForce ( original.getMaxFrictionTorque());
					p.setToParent ( settings);
					break;
				}

			case TypeCone:
				{
					ConeConstraintSettings settings = new ConeConstraintSettings();
					settings.setPoint1 ( original.getPosition1());
					settings.setTwistAxis1 ( original.getTwistAxis1());
					settings.setPoint2 ( original.getPosition2());
					settings.setTwistAxis2 ( original.getTwistAxis2());
					settings.setHalfConeAngle ( original.getNormalHalfConeAngle());
					p.setToParent ( settings);
					break;
				}

			case TypeRagdoll:
				break;
			}
		}

	// Initialize the skeleton
	ragdoll.getSkeleton().calculateParentJointIndices();

	// Stabilize the constraints of the ragdoll
	ragdoll.stabilize();

	// Calculate body <-> constraint map
	ragdoll.calculateBodyIndexToConstraintIndex();
	ragdoll.calculateConstraintIndexToBodyIdxPair();

	return ragdoll.getPtr();
}

public static RagdollSettings sCreate()
{
	// Create skeleton
	Skeleton skeleton = new Skeleton();
	int lower_body = skeleton.addJoint("LowerBody");
	int mid_body = skeleton.addJoint("MidBody", lower_body);
	int upper_body = skeleton.addJoint("UpperBody", mid_body);
	/*uint head =*/ skeleton.addJoint("Head", upper_body);
	int upper_arm_l = skeleton.addJoint("UpperArmL", upper_body);
	int upper_arm_r = skeleton.addJoint("UpperArmR", upper_body);
	/*uint lower_arm_l =*/ skeleton.addJoint("LowerArmL", upper_arm_l);
	/*uint lower_arm_r =*/ skeleton.addJoint("LowerArmR", upper_arm_r);
	int upper_leg_l = skeleton.addJoint("UpperLegL", lower_body);
	int upper_leg_r = skeleton.addJoint("UpperLegR", lower_body);
	/*uint lower_leg_l =*/ skeleton.addJoint("LowerLegL", upper_leg_l);
	/*uint lower_leg_r =*/ skeleton.addJoint("LowerLegR", upper_leg_r);

	// Create shapes for limbs
	Shape shapes[] = {
		new CapsuleShape(0.15f, 0.10f),		// Lower Body
		new CapsuleShape(0.15f, 0.10f),		// Mid Body
		new CapsuleShape(0.15f, 0.10f),		// Upper Body
		new CapsuleShape(0.075f, 0.10f),	// Head
		new CapsuleShape(0.15f, 0.06f),		// Upper Arm L
		new CapsuleShape(0.15f, 0.06f),		// Upper Arm R
		new CapsuleShape(0.15f, 0.05f),		// Lower Arm L
		new CapsuleShape(0.15f, 0.05f),		// Lower Arm R
		new CapsuleShape(0.2f, 0.075f),		// Upper Leg L
		new CapsuleShape(0.2f, 0.075f),		// Upper Leg R
		new CapsuleShape(0.2f, 0.06f),		// Lower Leg L
		new CapsuleShape(0.2f, 0.06f),		// Lower Leg R
	};

	// Positions of body parts in world space
	RVec3 positions[] = {
		new RVec3(0, 1.15f, 0),					// Lower Body
		new RVec3(0, 1.35f, 0),					// Mid Body
		new RVec3(0, 1.55f, 0),					// Upper Body
		new RVec3(0, 1.825f, 0),				// Head
		new RVec3(-0.425f, 1.55f, 0),			// Upper Arm L
		new RVec3(0.425f, 1.55f, 0),			// Upper Arm R
		new RVec3(-0.8f, 1.55f, 0),				// Lower Arm L
		new RVec3(0.8f, 1.55f, 0),				// Lower Arm R
		new RVec3(-0.15f, 0.8f, 0),				// Upper Leg L
		new RVec3(0.15f, 0.8f, 0),				// Upper Leg R
		new RVec3(-0.15f, 0.3f, 0),				// Lower Leg L
		new RVec3(0.15f, 0.3f, 0),				// Lower Leg R
	};

	// Rotations of body parts in world space
	Quat rotations[] = {
		Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI),		 // Lower Body
		Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI),		 // Mid Body
		Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI),		 // Upper Body
		Quat.sIdentity(),									 // Head
		Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI),		 // Upper Arm L
		Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI),		 // Upper Arm R
		Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI),		 // Lower Arm L
		Quat.sRotation(Vec3.sAxisZ(), 0.5f * JPH_PI),		 // Lower Arm R
		Quat.sIdentity(),									 // Upper Leg L
		Quat.sIdentity(),									 // Upper Leg R
		Quat.sIdentity(),									 // Lower Leg L
		Quat.sIdentity()									 // Lower Leg R
	};

	// World space constraint positions
	RVec3 constraint_positions[] = {
		RVec3.sZero(),				// Lower Body (unused, there's no parent)
		new RVec3(0, 1.25f, 0),			// Mid Body
		new RVec3(0, 1.45f, 0),			// Upper Body
		new RVec3(0, 1.65f, 0),			// Head
		new RVec3(-0.225f, 1.55f, 0),	// Upper Arm L
		new RVec3(0.225f, 1.55f, 0),	// Upper Arm R
		new RVec3(-0.65f, 1.55f, 0),	// Lower Arm L
		new RVec3(0.65f, 1.55f, 0),		// Lower Arm R
		new RVec3(-0.15f, 1.05f, 0),	// Upper Leg L
		new RVec3(0.15f, 1.05f, 0),		// Upper Leg R
		new RVec3(-0.15f, 0.55f, 0),	// Lower Leg L
		new RVec3(0.15f, 0.55f, 0),		// Lower Leg R
	};

	// World space twist axis directions
	Vec3 twist_axis[] = {
		Vec3.sZero(),				// Lower Body (unused, there's no parent)
		Vec3.sAxisY(),				// Mid Body
		Vec3.sAxisY(),				// Upper Body
		Vec3.sAxisY(),				// Head
		minus(Vec3.sAxisX()),			// Upper Arm L
		Vec3.sAxisX(),				// Upper Arm R
		minus(Vec3.sAxisX()),			// Lower Arm L
		Vec3.sAxisX(),				// Lower Arm R
		minus(Vec3.sAxisY()),			// Upper Leg L
		minus(Vec3.sAxisY()),			// Upper Leg R
		minus(Vec3.sAxisY()),			// Lower Leg L
		minus(Vec3.sAxisY()),			// Lower Leg R
	};

	// Constraint limits
	float twist_angle[] = {
		0.0f,		// Lower Body (unused, there's no parent)
		5.0f,		// Mid Body
		5.0f,		// Upper Body
		90.0f,		// Head
		45.0f,		// Upper Arm L
		45.0f,		// Upper Arm R
		45.0f,		// Lower Arm L
		45.0f,		// Lower Arm R
		45.0f,		// Upper Leg L
		45.0f,		// Upper Leg R
		45.0f,		// Lower Leg L
		45.0f,		// Lower Leg R
	};

	float normal_angle[] = {
		0.0f,		// Lower Body (unused, there's no parent)
		10.0f,		// Mid Body
		10.0f,		// Upper Body
		45.0f,		// Head
		90.0f,		// Upper Arm L
		90.0f,		// Upper Arm R
		0.0f,		// Lower Arm L
		0.0f,		// Lower Arm R
		45.0f,		// Upper Leg L
		45.0f,		// Upper Leg R
		0.0f,		// Lower Leg L
		0.0f,		// Lower Leg R
	};

	float plane_angle[] = {
		0.0f,		// Lower Body (unused, there's no parent)
		10.0f,		// Mid Body
		10.0f,		// Upper Body
		45.0f,		// Head
		45.0f,		// Upper Arm L
		45.0f,		// Upper Arm R
		90.0f,		// Lower Arm L
		90.0f,		// Lower Arm R
		45.0f,		// Upper Leg L
		45.0f,		// Upper Leg R
		60.0f,		// Lower Leg L (cheating here, a knee is not symmetric, we should have rotated the twist axis)
		60.0f,		// Lower Leg R
	};

	// Create ragdoll settings
	RagdollSettings settings = new RagdollSettings();
	settings.setSkeleton ( skeleton);
	settings.resizeParts(skeleton.getJointCount());
	for (int p = 0; p < skeleton.getJointCount(); ++p)
	{
		Part part = settings.getParts()[p];
		part.setShape(shapes[p]);
		part.setPosition ( positions[p]);
		part.setRotation ( rotations[p]);
		part.setMotionType ( EMotionType.Dynamic);
		part.setObjectLayer ( Layers.MOVING);

		// First part is the root, doesn't have a parent and doesn't have a constraint
		if (p > 0)
		{
			SwingTwistConstraintSettings constraint = new SwingTwistConstraintSettings();
			constraint.setDrawConstraintSize ( 0.1f);
			constraint.setPosition1 ( constraint.setPosition2 ( constraint_positions[p]));
			constraint.setTwistAxis1 ( constraint.setTwistAxis2 ( twist_axis[p]));
			constraint.setPlaneAxis1 ( constraint.setPlaneAxis2 ( Vec3.sAxisZ()));
			constraint.setTwistMinAngle ( -degreesToRadians(twist_angle[p]));
			constraint.setTwistMaxAngle ( degreesToRadians(twist_angle[p]));
			constraint.setNormalHalfConeAngle ( degreesToRadians(normal_angle[p]));
			constraint.setPlaneHalfConeAngle ( degreesToRadians(plane_angle[p]));
			part.setToParent ( constraint);
		}
	}

	// Optional: Stabilize the inertia of the limbs
	settings.stabilize();

	// Disable parent child collisions so that we don't get collisions between constrained bodies
	settings.disableParentChildCollisions();

	// Calculate the map needed for GetBodyIndexToConstraintIndex()
	settings.calculateBodyIndexToConstraintIndex();

	return settings;
}
}
