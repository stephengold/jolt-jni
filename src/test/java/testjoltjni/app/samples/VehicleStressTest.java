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
import com.github.stephengold.joltjni.readonly.*;
import java.util.*;

/**
 * A line-for-line Java translation of the Jolt Physics vehicle stress test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Vehicle/VehicleStressTest.cpp
 */
class VehicleStressTest extends VehicleTest {
    List<ConstraintRef> mVehicles = new ArrayList<>();
    float mHandBrake, mForward, mRight;

void Initialize()
{
	CreateMeshTerrain();

	// Create walls so the vehicles don't fall off
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(50.0f, 5.0f, 0.5f)), new RVec3(0, 0, -50), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(50.0f, 5.0f, 0.5f)), new RVec3(0, 0, 50), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(0.5f, 5.0f, 50.0f)), new RVec3(-50, 0, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);
	mBodyInterface.createAndAddBody(new BodyCreationSettings(new BoxShape(new Vec3(0.5f, 5.0f, 50.0f)), new RVec3(50, 0, 0), Quat.sIdentity(), EMotionType.Static, Layers.NON_MOVING), EActivation.DontActivate);

	final float wheel_radius = 0.3f;
	final float wheel_width = 0.1f;
	final float half_vehicle_length = 2.0f;
	final float half_vehicle_width = 0.9f;
	final float half_vehicle_height = 0.2f;
	final float max_steering_angle = Jolt.degreesToRadians(30.0f);

	// Create vehicle body
	ShapeRefC car_shape = new BoxShape(new Vec3(half_vehicle_width, half_vehicle_height, half_vehicle_length)).toRefC();
	BodyCreationSettings car_body_settings = new BodyCreationSettings(car_shape, RVec3.sZero(), Quat.sIdentity(), EMotionType.Dynamic, Layers.MOVING);
	car_body_settings.setOverrideMassProperties(EOverrideMassProperties.CalculateInertia);
	car_body_settings.setMassPropertiesOverride(car_body_settings.getMassPropertiesOverride().setMass(1500.0f));

	// Create vehicle constraint
	VehicleConstraintSettings vehicle=new VehicleConstraintSettings();

	// Wheels, left front
	WheelSettingsWv w1 = new WheelSettingsWv();
	w1.setPosition (new Vec3(half_vehicle_width, -0.9f * half_vehicle_height, half_vehicle_length - 2.0f * wheel_radius));
	w1.setMaxSteerAngle ( max_steering_angle);
	w1.setMaxHandBrakeTorque ( 0.0f); // Front wheel doesn't have hand brake

	// Right front
	WheelSettingsWv w2 = new WheelSettingsWv();
	w2.setPosition (new Vec3(-half_vehicle_width, -0.9f * half_vehicle_height, half_vehicle_length - 2.0f * wheel_radius));
	w2.setMaxSteerAngle ( max_steering_angle);
	w2.setMaxHandBrakeTorque ( 0.0f); // Front wheel doesn't have hand brake

	// Left rear
	WheelSettingsWv w3 = new WheelSettingsWv();
	w3.setPosition (new Vec3(half_vehicle_width, -0.9f * half_vehicle_height, -half_vehicle_length + 2.0f * wheel_radius));
	w3.setMaxSteerAngle ( 0.0f);

	// Right rear
	WheelSettingsWv w4 = new WheelSettingsWv();
	w4.setPosition (new Vec3(-half_vehicle_width, -0.9f * half_vehicle_height, -half_vehicle_length + 2.0f * wheel_radius));
	w4.setMaxSteerAngle ( 0.0f);

	vehicle.addWheels( w1, w2, w3, w4 );

	for (WheelSettings w : vehicle.getWheels())
	{
		w.setRadius ( wheel_radius);
		w.setWidth ( wheel_width);
	}

	// Controller
	WheeledVehicleControllerSettings controller = new WheeledVehicleControllerSettings();
	vehicle.setController(controller);
	vehicle.setMaxPitchRollAngle ( Jolt.degreesToRadians(60.0f));

	// Differential
	controller.setNumDifferentials(1);
	controller.setDifferentialsLeftWheel(0 , 0);
	controller.setDifferentialsRightWheel(0 , 1);

	for (int x = 0; x < 15; ++x)
		for (int y = 0; y < 15; ++y)
		{
			// Create body
			car_body_settings.setPosition (new RVec3(-28.0f + x * 4.0f, 2.0f, -35.0f + y * 5.0f));
			Body car_body = mBodyInterface.createBody(car_body_settings);
			mBodyInterface.addBody(car_body.getId(), EActivation.Activate);

			// Create constraint
			VehicleConstraint c = new VehicleConstraint(car_body, vehicle);
			c.setNumStepsBetweenCollisionTestActive(2); // Only test collision every other step to speed up simulation
			c.setNumStepsBetweenCollisionTestInactive(0); // Disable collision testing when inactive

			// Set the collision tester
			VehicleCollisionTester tester = new VehicleCollisionTesterRay(Layers.MOVING);
			c.setVehicleCollisionTester(tester);

			// Add the vehicle
			mPhysicsSystem.addConstraint(c);
			mPhysicsSystem.addStepListener(c);
			mVehicles.add(c.toRef());
		}
}

/*TODO
void ProcessInput(const ProcessInputParams &inParams)
{
	// Determine acceleration and brake
	mForward = 0.0f;
	if (inParams.mKeyboard->IsKeyPressed(DIK_UP))
		mForward = 1.0f;
	else if (inParams.mKeyboard->IsKeyPressed(DIK_DOWN))
		mForward = -1.0f;

	// Steering
	mRight = 0.0f;
	if (inParams.mKeyboard->IsKeyPressed(DIK_LEFT))
		mRight = -1.0f;
	else if (inParams.mKeyboard->IsKeyPressed(DIK_RIGHT))
		mRight = 1.0f;

	// Hand brake will cancel gas pedal
	mHandBrake = 0.0f;
	if (inParams.mKeyboard->IsKeyPressed(DIK_Z))
	{
		mForward = 0.0f;
		mHandBrake = 1.0f;
	}
}
*/

void PrePhysicsUpdate(PreUpdateParams inParams)
{
	for (ConstraintRef cr : mVehicles)
	{
		VehicleConstraint c = (VehicleConstraint) cr.getPtr();
		// On user input, assure that the car is active
		if (mRight != 0.0f || mForward != 0.0f)
			mBodyInterface.activateBody(c.getVehicleBody().getId());

		// Pass the input on to the constraint
		WheeledVehicleController controller = (WheeledVehicleController) (c.getController());
		controller.setDriverInput(mForward, mRight, 0.0f, mHandBrake);

		// Draw our wheels (this needs to be done in the pre update since we draw the bodies too in the state before the step)
		for (int w = 0; w < 4; ++w)
		{
			ConstWheelSettings settings = c.getWheel(w).getSettings();
			RMat44 wheel_transform = c.getWheelWorldTransform(w, Vec3.sAxisY(), Vec3.sAxisX()); // The cylinder we draw is aligned with Y so we specify that as rotational axis
			mDebugRenderer.drawCylinder(wheel_transform, 0.5f * settings.getWidth(), settings.getRadius(), Color.sGreen);
		}
	}
}

void SaveInputState(StateRecorder inStream)
{
	inStream.write(mForward);
	inStream.write(mRight);
	inStream.write(mHandBrake);
}

void RestoreInputState(StateRecorder inStream)
{
	mForward = inStream.readFloat();
	mRight = inStream.readFloat();
	mHandBrake = inStream.readFloat();
}
}