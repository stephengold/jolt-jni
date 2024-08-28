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

/*
 * Author: Stephen Gold
 */
#include <Jolt/Jolt.h>
#include <Jolt/Physics/Collision/ContactListener.h>
#include "auto/com_github_stephengold_joltjni_ContactSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    createContactSettings
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ContactSettings_createContactSettings
  (JNIEnv *, jclass) {
    ContactSettings * const pSettings = new ContactSettings();
    TRACE_NEW("ContactSettings", pSettings)
    return reinterpret_cast<jlong> (pSettings);
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactSettings_free
  (JNIEnv *, jclass, jlong settingsVa) {
    ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    TRACE_DELETE("ContactSettings", pSettings)
    delete pSettings;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    getCombinedFriction
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ContactSettings_getCombinedFriction
  (JNIEnv *, jclass, jlong settingsVa) {
    const ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    const float result = pSettings->mCombinedFriction;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    getCombinedRestitution
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ContactSettings_getCombinedRestitution
  (JNIEnv *, jclass, jlong settingsVa) {
    const ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    const float result = pSettings->mCombinedRestitution;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    getInvInertiaScale1
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ContactSettings_getInvInertiaScale1
  (JNIEnv *, jclass, jlong settingsVa) {
    const ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    const float result = pSettings->mInvInertiaScale1;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    getInvInertiaScale2
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ContactSettings_getInvInertiaScale2
  (JNIEnv *, jclass, jlong settingsVa) {
    const ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    const float result = pSettings->mInvInertiaScale2;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    getInvMassScale1
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ContactSettings_getInvMassScale1
  (JNIEnv *, jclass, jlong settingsVa) {
    const ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    const float result = pSettings->mInvMassScale1;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    getInvMassScale2
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ContactSettings_getInvMassScale2
  (JNIEnv *, jclass, jlong settingsVa) {
    const ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    const float result = pSettings->mInvMassScale2;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    getIsSensor
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ContactSettings_getIsSensor
  (JNIEnv *, jclass, jlong settingsVa) {
    const ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    const bool result = pSettings->mIsSensor;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    getRelativeAngularSurfaceVelocityX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ContactSettings_getRelativeAngularSurfaceVelocityX
  (JNIEnv *, jclass, jlong settingsVa) {
    const ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    const float result = pSettings->mRelativeAngularSurfaceVelocity.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    getRelativeAngularSurfaceVelocityY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ContactSettings_getRelativeAngularSurfaceVelocityY
  (JNIEnv *, jclass, jlong settingsVa) {
    const ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    const float result = pSettings->mRelativeAngularSurfaceVelocity.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    getRelativeAngularSurfaceVelocityZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ContactSettings_getRelativeAngularSurfaceVelocityZ
  (JNIEnv *, jclass, jlong settingsVa) {
    const ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    const float result = pSettings->mRelativeAngularSurfaceVelocity.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    getRelativeLinearSurfaceVelocityX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ContactSettings_getRelativeLinearSurfaceVelocityX
  (JNIEnv *, jclass, jlong settingsVa) {
    const ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    const float result = pSettings->mRelativeLinearSurfaceVelocity.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    getRelativeLinearSurfaceVelocityY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ContactSettings_getRelativeLinearSurfaceVelocityY
  (JNIEnv *, jclass, jlong settingsVa) {
    const ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    const float result = pSettings->mRelativeLinearSurfaceVelocity.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    getRelativeLinearSurfaceVelocityZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ContactSettings_getRelativeLinearSurfaceVelocityZ
  (JNIEnv *, jclass, jlong settingsVa) {
    const ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    const float result = pSettings->mRelativeLinearSurfaceVelocity.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    setCombinedFriction
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactSettings_setCombinedFriction
  (JNIEnv *, jclass, jlong settingsVa, jfloat friction) {
    ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    pSettings->mCombinedFriction = friction;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    setCombinedRestitution
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactSettings_setCombinedRestitution
  (JNIEnv *, jclass, jlong settingsVa, jfloat restitution) {
    ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    pSettings->mCombinedRestitution = restitution;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    setInvInertiaScale1
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactSettings_setInvInertiaScale1
  (JNIEnv *, jclass, jlong settingsVa, jfloat scale) {
    ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    pSettings->mInvInertiaScale1 = scale;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    setInvInertiaScale2
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactSettings_setInvInertiaScale2
  (JNIEnv *, jclass, jlong settingsVa, jfloat scale) {
    ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    pSettings->mInvInertiaScale2 = scale;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    setInvMassScale1
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactSettings_setInvMassScale1
  (JNIEnv *, jclass, jlong settingsVa, jfloat scale) {
    ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    pSettings->mInvMassScale1 = scale;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    setInvMassScale2
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactSettings_setInvMassScale2
  (JNIEnv *, jclass, jlong settingsVa, jfloat scale) {
    ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    pSettings->mInvMassScale2 = scale;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    setIsSensor
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactSettings_setIsSensor
  (JNIEnv *, jclass, jlong settingsVa, jboolean setting) {
    ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    pSettings->mIsSensor = setting;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    setRelativeAngularSurfaceVelocity
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactSettings_setRelativeAngularSurfaceVelocity
  (JNIEnv *, jclass, jlong settingsVa, jfloat wx, jfloat wy, jfloat wz) {
    ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    const Vec3 omega(wx, wy, wz);
    pSettings->mRelativeAngularSurfaceVelocity = omega;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSettings
 * Method:    setRelativeLinearSurfaceVelocity
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactSettings_setRelativeLinearSurfaceVelocity
  (JNIEnv *, jclass, jlong settingsVa, jfloat vx, jfloat vy, jfloat vz) {
    ContactSettings * const pSettings
            = reinterpret_cast<ContactSettings *> (settingsVa);
    const Vec3 velocity(vx, vy, vz);
    pSettings->mRelativeLinearSurfaceVelocity = velocity;
}