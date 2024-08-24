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
#include <Jolt/Physics/Character/Character.h>
#include "auto/com_github_stephengold_joltjni_CharacterSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_CharacterSettings
 * Method:    createCharacterSettings
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterSettings_createCharacterSettings
  (JNIEnv *, jclass) {
    CharacterSettings * const pResult = new CharacterSettings();
    TRACE_NEW("CharacterSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterSettings
 * Method:    getFriction
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterSettings_getFriction
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterSettings * const pSettings
            = reinterpret_cast<CharacterSettings *> (settingsVa);
    const float result = pSettings->mFriction;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterSettings
 * Method:    getGravityFactor
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterSettings_getGravityFactor
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterSettings * const pSettings
            = reinterpret_cast<CharacterSettings *> (settingsVa);
    const float result = pSettings->mGravityFactor;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterSettings
 * Method:    getLayer
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CharacterSettings_getLayer
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterSettings * const pSettings
            = reinterpret_cast<CharacterSettings *> (settingsVa);
    const ObjectLayer result = pSettings->mLayer;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterSettings
 * Method:    getMass
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterSettings_getMass
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterSettings * const pSettings
            = reinterpret_cast<CharacterSettings *> (settingsVa);
    const float result = pSettings->mMass;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterSettings
 * Method:    setFriction
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterSettings_setFriction
  (JNIEnv *, jclass, jlong settingsVa, jfloat friction) {
    CharacterSettings * const pSettings
            = reinterpret_cast<CharacterSettings *> (settingsVa);
    pSettings->mFriction = friction;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterSettings
 * Method:    setGravityFactor
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterSettings_setGravityFactor
  (JNIEnv *, jclass, jlong settingsVa, jfloat factor) {
    CharacterSettings * const pSettings
            = reinterpret_cast<CharacterSettings *> (settingsVa);
    pSettings->mGravityFactor = factor;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterSettings
 * Method:    setLayer
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterSettings_setLayer
  (JNIEnv *, jclass, jlong settingsVa, jint objLayer) {
    CharacterSettings * const pSettings
            = reinterpret_cast<CharacterSettings *> (settingsVa);
    const ObjectLayer layer = (ObjectLayer) objLayer;
    pSettings->mLayer = layer;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterSettings
 * Method:    setMass
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterSettings_setMass
  (JNIEnv *, jclass, jlong settingsVa, jfloat mass) {
    CharacterSettings * const pSettings
            = reinterpret_cast<CharacterSettings *> (settingsVa);
    pSettings->mMass = mass;
}