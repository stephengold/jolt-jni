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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.readonly.ConstWheel;
import com.github.stephengold.joltjni.readonly.ConstWheelSettings;
import com.github.stephengold.joltjni.template.Ref;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

/**
 * A single wheel of a vehicle.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Wheel extends NonCopyable implements ConstWheel {
    // *************************************************************************
    // fields

    /**
     * prevent premature garbage collection of the settings
     */
    final private Ref settings;
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param wheelVa the virtual address of the native object to assign (not
     * zero)
     */
    Wheel(VehicleConstraint container, long wheelVa) {
        super(container, wheelVa);

        long settingsVa = getSettings(wheelVa);
        if (this instanceof WheelTv) {
            this.settings = new WheelSettingsTv(settingsVa).toRef();
        } else if (this instanceof WheelWv) {
            this.settings = new WheelSettingsWv(settingsVa).toRef();
        } else {
            throw new IllegalStateException(this.getClass().getSimpleName());
        }
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Instantiate a {@code Wheel} from its virtual address.
     *
     * @param wheelVa the virtual address of the native object, or zero
     * @param ordinal the type of {@code VehicleController}
     * @param container the containing object, or {@code null} if none
     * @return a new JVM object, or {@code null} if {@code wheelVa} was zero
     */
    static Wheel newWheel(
            long wheelVa, int ordinal, VehicleConstraint container) {
        if (wheelVa == 0L) {
            return null;
        }

        Wheel result;
        switch (ordinal) {
            case VehicleController.motorcycleType:
            case VehicleController.wheeledVehicleType:
                result = new WheelWv(container, wheelVa);
                break;
            case VehicleController.trackedVehicleType:
                result = new WheelTv(container, wheelVa);
                break;
            default:
                throw new IllegalArgumentException("ordinal = " + ordinal);
        }

        return result;
    }
    // *************************************************************************
    // ConstWheel methods

    /**
     * Return the wheel's angular velocity.
     *
     * @return the angular velocity (in radians per second, positive when the
     * vehicle is moving forward)
     */
    @Override
    public float getAngularVelocity() {
        long wheelVa = va();
        float result = getAngularVelocity(wheelVa);

        return result;
    }

    /**
     * Return the ID of the body that's supporting the wheel. (native method:
     * GetContactBodyID)
     *
     * @return the body ID
     */
    @Override
    public int getContactBodyId() {
        long wheelVa = va();
        int result = getContactBodyId(wheelVa);

        return result;
    }

    /**
     * Copy the wheel's lateral (sideways) direction.
     *
     * @return a new direction vector (in system coordinates)
     */
    @Override
    public Vec3 getContactLateral() {
        long wheelVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getContactLateral(wheelVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the wheel's longitudinal direction.
     *
     * @return a new direction vector (in system coordinates)
     */
    @Override
    public Vec3 getContactLongitudinal() {
        long wheelVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getContactLongitudinal(wheelVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the contact normal direction.
     *
     * @return a new direction vector (in system coordinates)
     */
    @Override
    public Vec3 getContactNormal() {
        long wheelVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getContactNormal(wheelVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the velocity of the contact point.
     *
     * @return a new velocity vector (meters per second in system coordinates)
     */
    @Override
    public Vec3 getContactPointVelocity() {
        long wheelVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getContactPointVelocity(wheelVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the location of the contact point.
     *
     * @return a new location vector (in system coordinates)
     */
    @Override
    public RVec3 getContactPosition() {
        long wheelVa = va();
        DoubleBuffer storeDoubles = Temporaries.doubleBuffer1.get();
        getContactPosition(wheelVa, storeDoubles);
        RVec3 result = new RVec3(storeDoubles);

        return result;
    }

    /**
     * Return ID of the sub-shape that's supporting the vehicle. (native method:
     * GetContactSubShapeID)
     *
     * @return the sub-shape ID
     */
    @Override
    public int getContactSubShapeId() {
        long wheelVa = va();
        int result = getContactSubShapeId(wheelVa);

        return result;
    }

    /**
     * Return the lateral (sideways) component of the impulse applied to the
     * wheel.
     *
     * @return the impulse component (in Newton seconds)
     */
    @Override
    public float getLateralLambda() {
        long wheelVa = va();
        float result = getLateralLambda(wheelVa);

        return result;
    }

    /**
     * Return the forward component of the impulse applied to the wheel.
     *
     * @return the impulse component (in Newton seconds)
     */
    @Override
    public float getLongitudinalLambda() {
        long wheelVa = va();
        float result = getLongitudinalLambda(wheelVa);

        return result;
    }

    /**
     * Return the wheel's rotation angle.
     *
     * @return the angle (in radians)
     */
    @Override
    public float getRotationAngle() {
        long wheelVa = va();
        float result = getRotationAngle(wheelVa);

        return result;
    }

    /**
     * Access the settings used to create this wheel.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public ConstWheelSettings getSettings() {
        WheelSettings result = (WheelSettings) settings.getPtr();
        return result;
    }

    /**
     * Return the steering angle.
     *
     * @return the leftward angle (in radians, &ge;-Pi, &le;Pi)
     */
    @Override
    public float getSteerAngle() {
        long wheelVa = va();
        float result = getSteerAngle(wheelVa);

        return result;
    }

    /**
     * Return the total impulse applied to the suspension.
     *
     * @return the magnitude of the impulse (in Newton seconds)
     */
    @Override
    public float getSuspensionLambda() {
        long wheelVa = va();
        float result = getSuspensionLambda(wheelVa);

        return result;
    }

    /**
     * Return the length of the suspension.
     *
     * @return the distance between the axle and the hard point (in meters)
     */
    @Override
    public float getSuspensionLength() {
        long wheelVa = va();
        float result = getSuspensionLength(wheelVa);

        return result;
    }

    /**
     * Test whether the wheel is supported.
     *
     * @return {@code true} if supported, otherwise {@code false}
     */
    @Override
    public boolean hasContact() {
        long wheelVa = va();
        boolean result = hasContact(wheelVa);

        return result;
    }

    /**
     * Test whether the suspension has hit its hard point.
     *
     * @return {@code true} if hit, otherwise {@code false}
     */
    @Override
    public boolean hasHitHardPoint() {
        long wheelVa = va();
        boolean result = hasHitHardPoint(wheelVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static float getAngularVelocity(long wheelVa);

    native private static int getContactBodyId(long wheelVa);

    native private static void getContactLateral(
            long wheelVa, FloatBuffer storeFloats);

    native private static void getContactLongitudinal(
            long wheelVa, FloatBuffer storeFloats);

    native private static void getContactNormal(
            long wheelVa, FloatBuffer storeFloats);

    native private static void getContactPointVelocity(
            long wheelVa, FloatBuffer storeFloats);

    native private static void getContactPosition(
            long wheelVa, DoubleBuffer storeDoubles);

    native private static int getContactSubShapeId(long wheelVa);

    native private static float getLateralLambda(long wheelVa);

    native private static float getLongitudinalLambda(long wheelVa);

    native private static float getRotationAngle(long wheelVa);

    native private static long getSettings(long wheelVa);

    native private static float getSteerAngle(long wheelVa);

    native private static float getSuspensionLambda(long wheelVa);

    native private static float getSuspensionLength(long wheelVa);

    native private static boolean hasContact(long wheelVa);

    native private static boolean hasHitHardPoint(long wheelVa);
}
