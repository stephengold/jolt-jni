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
package testjoltjni.app.samples.softbody;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.readonly.*;
import com.github.stephengold.joltjni.std.*;
import java.util.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static com.github.stephengold.joltjni.operator.Op.*;
import testjoltjni.app.samples.*;
/**
 * A line-for-line Java translation of the Jolt-Physics Cosserat-rod constraint
 * test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/SoftBody/SoftBodyCosseratRodConstraintTest.cpp
 */
public class SoftBodyCosseratRodConstraintTest extends Test{
BodyIdVector mSoftBodies=new BodyIdVector();

void Initialize()
{
	CreateFloor();

	// Create a hanging helix
	{
		final float cRadius = 0.5f;
		final int cNumVertices = 128;
		final float cHeight = 5.0f;
		final float cNumCycles = 10;

		SoftBodySharedSettingsRef helix_settings = new SoftBodySharedSettings().toRef();
		for (int i = 0; i < cNumVertices; ++i)
		{
			float fraction = ((float)i) / (cNumVertices - 1);

			Vertex v=new Vertex();
			float alpha = cNumCycles * 2.0f * JPH_PI * fraction;
			v.setPosition (new Float3(cRadius * sin(alpha), 0.5f * (1.0f - fraction * cHeight), cRadius * cos(alpha)));
			v.setInvMass (( i == 0)? 0.0f : 1.0e-2f);
			helix_settings.addVertex(v);

			if (i > 0)
				helix_settings.addRodStretchShearConstraint(new RodStretchShear(i - 1, i));

			if (i > 1)
				helix_settings.addRodBendTwistConstraint(new RodBendTwist(i - 2, i - 1));
		}

		helix_settings.calculateRodProperties();
		helix_settings.optimize();

		SoftBodyCreationSettings helix=new SoftBodyCreationSettings(helix_settings,new RVec3(0, 10, 0), Quat.sIdentity(), Layers.MOVING);
		mSoftBodies.pushBack(mBodyInterface.createAndAddSoftBody(helix, EActivation.Activate));
	}

	// Create a tree with a static root
	{
		// Root particle
		SoftBodySharedSettingsRef tree_settings = new SoftBodySharedSettings().toRef();
		Vertex v=new Vertex();
		v.setPosition (new Float3(0, 0, 0));
		v.setInvMass ( 0.0f);
		tree_settings.addVertex(v);

		// Create branches
		class Branch
		{
			Branch(int pv,int pr,Vec3Arg dir,int depth){mPreviousVertex=pv;mPreviousRod=pr;assign(mDirection,dir);mDepth=depth;}
			int	mPreviousVertex;
			int	mPreviousRod;
			Vec3	mDirection=new Vec3();
			int	mDepth;
		}
		Deque<Branch> branches=new ArrayDeque<>();
		branches.add(new Branch( 0, ((int)-1), Vec3.sAxisY(), 0 ));
		while (!branches.isEmpty())
		{
			// Take the next branch
			Branch branch = branches.removeFirst();

			// Create vertex
			Vertex previous_vertex = tree_settings.getPtr().getVertex(branch.mPreviousVertex);
			v.setPosition(plus(new Vec3(previous_vertex.getPosition()) , branch.mDirection));
			v.setInvMass ( branch.mDepth > 0? 2.0f * previous_vertex.getInvMass() : 1.0e-3f);
			int new_vertex = (int)(tree_settings.countVertices());
			tree_settings.addVertex(v);

			// Create rod
			int new_rod = (int)(tree_settings.countRodStretchShearConstraints());
			tree_settings.addRodStretchShearConstraint(new RodStretchShear(branch.mPreviousVertex, new_vertex));
			if (branch.mPreviousRod != (int)(-1))
				tree_settings.addRodBendTwistConstraint(new RodBendTwist(branch.mPreviousRod, new_rod));

			// Create sub branches
			if (branch.mDepth < 10)
				for (int i = 0; i < 2; ++i)
				{
					// Determine new child direction
					float angle = degreesToRadians(-15.0f + i * 30.0f);
					Vec3 new_direction = star(Quat.sRotation((branch.mDepth & 1)!=0? Vec3.sAxisZ() : Vec3.sAxisX(), angle) , branch.mDirection);

					// Create new branch
					branches.add(new Branch( new_vertex, new_rod, new_direction, branch.mDepth + 1 ));
				}
		}

		tree_settings.calculateRodProperties();
		tree_settings.optimize();

		SoftBodyCreationSettings tree=new SoftBodyCreationSettings(tree_settings,new RVec3(10, 0, 0), Quat.sIdentity(), Layers.MOVING);
		mSoftBodies.pushBack(mBodyInterface.createAndAddSoftBody(tree, EActivation.Activate));
	}

	// Create a weed like structure
	{
		// Root particle
		SoftBodySharedSettingsRef weed_settings = new SoftBodySharedSettings().toRef();

		final int cNumVertices = 64;
		final int cNumStrands = 50;

		DefaultRandomEngine random=new DefaultRandomEngine();
		UniformFloatDistribution radius_distribution=new UniformFloatDistribution(0, 1.0f);
		UniformFloatDistribution phase_distribution=new UniformFloatDistribution(0, 2.0f * JPH_PI);

		for (int strand = 0; strand < cNumStrands; ++strand)
		{
			// Place at a random location
			float radius = radius_distribution.nextFloat(random);
			float theta = phase_distribution.nextFloat(random);
			Vec3 root_pos =new Vec3(radius * sin(theta), 0, radius * cos(theta));

			// Randomize the phase of the wave
			float phase1 = phase_distribution.nextFloat(random);
			float phase2 = phase_distribution.nextFloat(random);

			int first_vertex = (int)(weed_settings.countVertices());
			for (int i = 0; i < cNumVertices; ++i)
			{
				// Generate a wavy pattern
				float amplitude = 0.1f * sin(phase1 + i * 2.0f * JPH_PI / 8);
				Vec3 pos = plus(root_pos ,new Vec3(sin(phase2) * amplitude, 0.1f * i, cos(phase2) * amplitude));

				Vertex v=new Vertex();
				v.setPosition(pos);
				v.setInvMass (( i == 0)? 0.0f : 0.1f);
				weed_settings.addVertex(v);
			}

			int first_rod = (int)(weed_settings.countRodStretchShearConstraints());
			for (int i = 0; i < cNumVertices - 1; ++i)
				weed_settings.addRodStretchShearConstraint(new RodStretchShear(first_vertex + i, first_vertex + i + 1));

			for (int i = 0; i < cNumVertices - 2; ++i)
				weed_settings.addRodBendTwistConstraint(new RodBendTwist(first_rod + i, first_rod + i + 1));
		}

		weed_settings.calculateRodProperties();
		weed_settings.optimize();

		SoftBodyCreationSettings weed=new SoftBodyCreationSettings(weed_settings,new RVec3(20, 0, 0), Quat.sIdentity(), Layers.MOVING);
		weed.setGravityFactor ( 0.8f);
		mSoftBodies.pushBack(mBodyInterface.createAndAddSoftBody(weed, EActivation.Activate));
	}
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	// Draw the soft body rods
	for (int id : mSoftBodies.toList())
	{
		BodyLockRead lock=new BodyLockRead(mPhysicsSystem.getBodyLockInterface(), id);
		if (lock.succeeded())
		{
			ConstBody body = lock.getBody();
			ConstSoftBodyMotionProperties  mp = ( SoftBodyMotionProperties )(body.getMotionProperties());
			RMat44 com = body.getCenterOfMassTransform();

			for (ConstRodStretchShear r : mp.getSettings().getRodStretchShearConstraints())
			{
				RVec3 x0 = star(com , mp.getVertex(r.getVertex(0)).getPosition());
				RVec3 x1 = star(com , mp.getVertex(r.getVertex(1)).getPosition());
				mDebugRenderer.drawLine(x0, x1, Color.sWhite);
			}
		}
		lock.releaseLock();
	}
}
}
