/*
Copyright (c) 2026 Stephen Gold

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
#include "Jolt/Jolt.h"
#include "Jolt/Physics/Character/CharacterVirtual.h"
#include "auto/com_github_stephengold_joltjni_CharacterContactKey.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_CharacterContactKey
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterContactKey_createCopy
  BODYOF_CREATE_COPY(CharacterContactKey)

/*
 * Class:     com_github_stephengold_joltjni_CharacterContactKey
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterContactKey_createDefault
  BODYOF_CREATE_DEFAULT(CharacterContactKey)

/*
 * Class:     com_github_stephengold_joltjni_CharacterContactKey
 * Method:    createKey
 * Signature: (CII)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterContactKey_createKey
  (JNIEnv *, jclass, jchar idType, jint bodyOrCharacterId, jint subShapeId) {
    SubShapeID ssid;
    ssid.SetValue(subShapeId);
    CharacterContactKey *pResult;
    if ('b' == idType) {
        const BodyID bid(bodyOrCharacterId);
        pResult = new CharacterContactKey(bid, ssid);
    } else if ('c' == idType) {
        const CharacterID cid(bodyOrCharacterId);
        pResult = new CharacterContactKey(cid, ssid);
    } else {
        JPH_ASSERT(false);
    }
    TRACE_NEW("CharacterContactKey", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterContactKey
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterContactKey_free
  BODYOF_FREE(CharacterContactKey)

/*
 * Class:     com_github_stephengold_joltjni_CharacterContactKey
 * Method:    getBodyB
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CharacterContactKey_getBodyB
  (JNIEnv *, jclass, jlong keyVa) {
    const CharacterContactKey * const pKey
            = reinterpret_cast<CharacterContactKey *> (keyVa);
    const BodyID result = pKey->mBodyB;
    return result.GetIndexAndSequenceNumber();
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterContactKey
 * Method:    getCharacterIdB
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CharacterContactKey_getCharacterIdB
  (JNIEnv *, jclass, jlong keyVa) {
    const CharacterContactKey * const pKey
            = reinterpret_cast<CharacterContactKey *> (keyVa);
    const CharacterID result = pKey->mCharacterIDB;
    return result.GetValue();
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterContactKey
 * Method:    getSubShapeIdB
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CharacterContactKey_getSubShapeIdB
  (JNIEnv *, jclass, jlong keyVa) {
    const CharacterContactKey * const pKey
            = reinterpret_cast<CharacterContactKey *> (keyVa);
    const SubShapeID result = pKey->mSubShapeIDB;
    return result.GetValue();
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterContactKey
 * Method:    isEqual
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_CharacterContactKey_isEqual
  (JNIEnv *, jclass, jlong keyVa, jlong otherVa) {
    const CharacterContactKey * const pKey1
            = reinterpret_cast<CharacterContactKey *> (keyVa);
    const CharacterContactKey * const pKey2
            = reinterpret_cast<CharacterContactKey *> (otherVa);
    const bool result = (*pKey1) == (*pKey2);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterContactKey
 * Method:    restoreState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterContactKey_restoreState
  (JNIEnv *, jclass, jlong keyVa, jlong recorderVa) {
    CharacterContactKey * const pKey
            = reinterpret_cast<CharacterContactKey *> (keyVa);
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    pKey->RestoreState(*pRecorder);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterContactKey
 * Method:    saveState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterContactKey_saveState
  (JNIEnv *, jclass, jlong keyVa, jlong recorderVa) {
    const CharacterContactKey * const pKey
            = reinterpret_cast<CharacterContactKey *> (keyVa);
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    pKey->SaveState(*pRecorder);
}