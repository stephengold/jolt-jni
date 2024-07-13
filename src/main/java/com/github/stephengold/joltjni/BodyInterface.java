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
package com.github.stephengold.joltjni;

/**
 * An interface to a {@code PhysicsSystem}, used to add, modify, query, and
 * remove bodies.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BodyInterface extends NonCopyable {
    // *************************************************************************
    // constructors

    BodyInterface(long bodyInterfaceVa) {
        setVirtualAddress(bodyInterfaceVa, false);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Activate the specified body.
     *
     * @param bodyId the ID of the body to activate (not null)
     */
    public void activateBody(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.va();
        activateBody(bodyInterfaceVa, bodyIdVa);
    }

    /**
     * Add the specified body to the physics system.
     *
     * @param bodyId the ID of the body to add (not null)
     * @param activation whether to activate the body (not null)
     */
    public void addBody(ConstBodyId bodyId, EActivation activation) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.va();
        int activationOrdinal = activation.ordinal();
        addBody(bodyInterfaceVa, bodyIdVa, activationOrdinal);
    }

    /**
     * Create a body and add it.
     *
     * @param settings the settings to use (not null)
     * @param activationMode whether to activate the body (not null)
     * @return the ID of the created body, or an invalid ID when out of bodies
     */
    public ConstBodyId createAndAddBody(
            BodyCreationSettings settings, EActivation activationMode) {
        Body body = createBody(settings);
        ConstBodyId result = body.getId();
        addBody(result, activationMode);

        return result;
    }

    /**
     * Create a body using the specified settings.
     *
     * @param settings the settings to use (not null)
     * @return the new body
     */
    public Body createBody(BodyCreationSettings settings) {
        long bodyInterfaceVa = va();
        long settingsVa = settings.va();
        long bodyVa = createBody(bodyInterfaceVa, settingsVa);
        if (bodyVa == 0L) {
            throw new IllegalStateException("ran out of bodies");
        }
        Body result = new Body(bodyVa);

        return result;
    }

    /**
     * Destroy the specified body.
     *
     * @param bodyId the ID of the body to destroy (not null)
     */
    public void destroyBody(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.va();
        destroyBody(bodyInterfaceVa, bodyIdVa);
    }

    /**
     * Locate the center of mass of the specified body.
     *
     * @param bodyId the ID of the body to locate (not null)
     * @return a new vector
     */
    public RVec3 getCenterOfMassPosition(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.va();
        double xx = getCenterOfMassPositionX(bodyInterfaceVa, bodyIdVa);
        double yy = getCenterOfMassPositionY(bodyInterfaceVa, bodyIdVa);
        double zz = getCenterOfMassPositionZ(bodyInterfaceVa, bodyIdVa);
        RVec3 result = new RVec3(xx, yy, zz);

        return result;
    }

    /**
     * Return the linear velocity of the specified body.
     *
     * @param bodyId the ID of the body (not null)
     * @return a new vector
     */
    public Vec3 getLinearVelocity(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.va();
        float x = getLinearVelocityX(bodyInterfaceVa, bodyIdVa);
        float y = getLinearVelocityY(bodyInterfaceVa, bodyIdVa);
        float z = getLinearVelocityZ(bodyInterfaceVa, bodyIdVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Remove the specified body from the physics system.
     *
     * @param bodyId the ID of the body to remove (not null)
     */
    public void removeBody(ConstBodyId bodyId) {
        long bodyIdVa = bodyId.va();
        removeBody(va(), bodyIdVa);
    }

    /**
     * Test whether the specified body is active.
     *
     * @param bodyId the ID of the body to test (not null)
     * @return true if active, otherwise false
     */
    public boolean isActive(ConstBodyId bodyId) {
        long bodyIdVa = bodyId.va();
        boolean result = isActive(va(), bodyIdVa);
        return result;
    }

    /**
     * Alter the linear velocity of the specified body.
     *
     * @param bodyId the ID of the body to test (not null)
     * @param omega the desired rates (not null, unaffected)
     */
    public void setAngularVelocity(ConstBodyId bodyId, Vec3Arg omega) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.va();
        setAngularVelocity(bodyInterfaceVa, bodyIdVa,
                omega.getX(), omega.getY(), omega.getZ());
    }

    /**
     * Alter the linear velocity of the specified body.
     *
     * @param bodyId the ID of the body to test (not null)
     * @param velocity the desired velocity (not null, unaffected)
     */
    public void setLinearVelocity(ConstBodyId bodyId, Vec3Arg velocity) {
        long bodyIdVa = bodyId.va();
        setLinearVelocity(va(), bodyIdVa,
                velocity.getX(), velocity.getY(), velocity.getZ());
    }
    // *************************************************************************
    // native private methods

    native private static void activateBody(
            long bodyInterfaceVa, long bodyIdVa);

    native private static void addBody(
            long bodyInterfaceVa, long bodyIdVa, int activationOrdinal);

    native private static long createBody(
            long bodyInterfaceVa, long settingsVa);

    native private static void destroyBody(
            long bodyInterfaceVa, long bodyIdVa);

    native private static double getCenterOfMassPositionX(
            long bodyInterfaceVa, long bodyIdVa);

    native private static double getCenterOfMassPositionY(
            long bodyInterfaceVa, long bodyIdVa);

    native private static double getCenterOfMassPositionZ(
            long bodyInterfaceVa, long bodyIdVa);

    native private static float getLinearVelocityX(
            long bodyInterfaceVa, long bodyIdVa);

    native private static float getLinearVelocityY(
            long bodyInterfaceVa, long bodyIdVa);

    native private static float getLinearVelocityZ(
            long bodyInterfaceVa, long bodyIdVa);

    native private static boolean isActive(long bodyInterfaceVa, long bodyIdVa);

    native private static void removeBody(
            long bodyInterfaceVa, long bodyIdVa);

    native private static void setAngularVelocity(
            long bodyInterfaceVa, long bodyIdVa, float wx, float wy, float wz);

    native private static void setLinearVelocity(
            long bodyInterfaceVa, long bodyIdVa, float vx, float vy, float vz);
}
