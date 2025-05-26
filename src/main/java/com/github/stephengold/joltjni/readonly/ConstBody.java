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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.BodyCreationSettings;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RMat44;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.SoftBodyCreationSettings;
import com.github.stephengold.joltjni.TransformedShape;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.EBodyType;
import com.github.stephengold.joltjni.enumerate.EMotionType;

/**
 * Read-only access to a {@code Body}. (native type: const Body)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstBody extends ConstJoltPhysicsObject {
    /**
     * Test whether the body could be made kinematic or dynamic. The body is
     * unaffected.
     *
     * @return {@code true} if possible, otherwise {@code false}
     */
    boolean canBeKinematicOrDynamic();

    /**
     * Copy the net force acting on the body. The body is unaffected.
     *
     * @return a new force vector (Newtons in system coordinates)
     */
    Vec3 getAccumulatedForce();

    /**
     * Copy the net torque acting on the body. The body is unaffected.
     *
     * @return a new torque vector (Newton.meters in system coordinates)
     */
    Vec3 getAccumulatedTorque();

    /**
     * Test whether the body is allowed to fall asleep. The body is unaffected.
     *
     * @return {@code true} if allowed, otherwise {@code false}
     */
    boolean getAllowSleeping();

    /**
     * Copy the body's angular velocity. The body is unaffected.
     *
     * @return a new velocity vector (radians per second in system coordinates)
     */
    Vec3 getAngularVelocity();

    /**
     * Convert the body to a {@code BodyCreationSettings} object. The body is
     * unaffected.
     *
     * @return a new object
     */
    BodyCreationSettings getBodyCreationSettings();

    /**
     * Return the body type (rigid or soft). The body is unaffected.
     *
     * @return an enum value (not null)
     */
    EBodyType getBodyType();

    /**
     * Return the broadphase layer. The body is unaffected.
     *
     * @return the layer ID
     */
    int getBroadPhaseLayer();

    /**
     * Copy the location of the body's center of mass (which might not coincide
     * with its origin). The body is unaffected.
     *
     * @return a new location vector (in system coordinates, all components
     * finite)
     */
    RVec3 getCenterOfMassPosition();

    /**
     * Copy the location of the body's center of mass (which might not coincide
     * with its origin). The body is unaffected.
     *
     * @param storeLocation storage for the location (in system coordinates, not
     * null, modified)
     */
    void getCenterOfMassPosition(RVec3 storeLocation);

    /**
     * Copy the coordinate transform of the body's center of mass. The body is
     * unaffected.
     *
     * @return a new transform matrix (relative to system coordinates)
     */
    RMat44 getCenterOfMassTransform();

    /**
     * Access the body's collision group.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstCollisionGroup getCollisionGroup();

    /**
     * Test whether extra effort should be made to remove ghost contacts. The
     * body is unaffected.
     *
     * @return {@code true} for extra effort, otherwise {@code false}
     */
    boolean getEnhancedInternalEdgeRemoval();

    /**
     * Return the body's friction ratio. The body is unaffected.
     *
     * @return the ratio
     */
    float getFriction();

    /**
     * Return the body's ID for use with {@code BodyInterface}. The body is
     * unaffected.
     *
     * @return the {@code BodyID} value
     */
    int getId();

    /**
     * Copy the inverse coordinate transform of the body's center of mass. The
     * body is unaffected.
     *
     * @return a new transform matrix (relative to local coordinates)
     */
    RMat44 getInverseCenterOfMassTransform();

    /**
     * Copy the body's linear velocity. The body is unaffected.
     *
     * @return a new velocity vector (meters per second in system coordinates)
     */
    Vec3 getLinearVelocity();

    /**
     * Access the body's motion properties.
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if none
     */
    ConstMotionProperties getMotionProperties();

    /**
     * Return the body's motion type. The body is unaffected.
     *
     * @return an enum value (not null)
     */
    EMotionType getMotionType();

    /**
     * Return the body's object layer. The body is unaffected.
     *
     * @return a layer index (&ge;0)
     */
    int getObjectLayer();

    /**
     * Copy the location of the body's origin (which might not coincide with its
     * center of mass). The body is unaffected.
     *
     * @return a new location vector (in system coordinates, all components
     * finite)
     */
    RVec3 getPosition();

    /**
     * Copy the position of the body. The body is unaffected.
     *
     * @param storeLocation storage for the location (in system coordinates, not
     * null, modified)
     * @param storeOrientation storage for the orientation (in system
     * coordinates, not null, modified)
     */
    void getPositionAndRotation(RVec3 storeLocation, Quat storeOrientation);

    /**
     * Return the body's restitution ratio. The body is unaffected.
     *
     * @return the value (typically &ge;0 and &le;1)
     */
    float getRestitution();

    /**
     * Copy the body's orientation. The body is unaffected.
     *
     * @return a new rotation quaternion (relative to the system axes)
     */
    Quat getRotation();

    /**
     * Access the body's shape.
     *
     * @return a new immutable JVM object with the pre-existing native object
     * assigned, or {@code null} if none
     */
    ConstShape getShape();

    /**
     * Convert the body to a {@code SoftBodyCreationSettings} object. The body
     * is unaffected.
     *
     * @return a new object
     */
    SoftBodyCreationSettings getSoftBodyCreationSettings();

    /**
     * Convert the body to a {@code TransformedShape} object. The body is
     * unaffected.
     *
     * @return a new object
     */
    TransformedShape getTransformedShape();

    /**
     * Return the body's user data: can be used for anything. The body is
     * unaffected.
     *
     * @return the value
     */
    long getUserData();

    /**
     * Access the body's bounding box. The body is unaffected.
     *
     * @return a new immutable JVM object with the pre-existing native object
     * assigned
     */
    ConstAaBox getWorldSpaceBounds();

    /**
     * Copy the surface normal of the specified sub-shape at the specified
     * location. The body is unaffected.
     *
     * @param subShapeId the ID of the sub-shape to use
     * @param location the location to use (not null, unaffected)
     * @return a new direction vector
     */
    Vec3 getWorldSpaceSurfaceNormal(int subShapeId, RVec3Arg location);

    /**
     * Copy the world transform. The body is unaffected.
     *
     * @return a new matrix relative to system coordinates
     */
    RMat44 getWorldTransform();

    /**
     * Test whether the body is deactivated. The body is unaffected.
     *
     * @return {@code false} if deactivated, otherwise {@code true}
     */
    boolean isActive();

    /**
     * Test whether the body is dynamic. The body is unaffected.
     *
     * @return {@code true} if dynamic, otherwise {@code false}
     */
    boolean isDynamic();

    /**
     * Test whether the body has been added to its {@code PhysicsSystem}. The
     * body is unaffected.
     *
     * @return {@code true} if added, otherwise {@code false}
     */
    boolean isInBroadPhase();

    /**
     * Test whether the body is kinematic. It is unaffected.
     *
     * @return {@code true} if kinematic, otherwise {@code false}
     */
    boolean isKinematic();

    /**
     * Test whether the body is a rigid body. It is unaffected.
     *
     * @return {@code true} if rigid body, otherwise {@code false}
     */
    boolean isRigidBody();

    /**
     * Test whether the body is a sensor. It is unaffected.
     *
     * @return {@code true} if a sensor, otherwise {@code false}
     */
    boolean isSensor();

    /**
     * Test whether the body is soft. It is unaffected.
     *
     * @return {@code true} if soft, otherwise {@code false}
     */
    boolean isSoftBody();

    /**
     * Test whether the body is static (non-moving). It is unaffected.
     *
     * @return {@code true} if static, otherwise {@code false}
     */
    boolean isStatic();
}
