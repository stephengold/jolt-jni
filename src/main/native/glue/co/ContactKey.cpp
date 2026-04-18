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
#include "auto/com_github_stephengold_joltjni_ContactKey.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ContactKey
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ContactKey_createCopy
  BODYOF_CREATE_COPY(CharacterVirtual::ContactKey)

/*
 * Class:     com_github_stephengold_joltjni_ContactKey
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ContactKey_createDefault
  BODYOF_CREATE_DEFAULT(CharacterVirtual::ContactKey)

/*
 * Class:     com_github_stephengold_joltjni_ContactKey
 * Method:    createKey
 * Signature: (CII)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ContactKey_createKey
  (JNIEnv *, jclass, jchar idType, jint bodyOrCharacterId, jint subShapeId) {
    SubShapeID ssid;
    ssid.SetValue(subShapeId);
    CharacterVirtual::ContactKey *pResult;
    if ('b' == idType) {
        const BodyID bid(bodyOrCharacterId);
        pResult = new CharacterVirtual::ContactKey(bid, ssid);
    } else if ('c' == idType) {
        const CharacterID cid(bodyOrCharacterId);
        pResult = new CharacterVirtual::ContactKey(cid, ssid);
    } else {
        JPH_ASSERT(false);
    }
    TRACE_NEW("CharacterVirtual::ContactKey", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ContactKey
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactKey_free
  BODYOF_FREE(CharacterVirtual::ContactKey)

/*
 * Class:     com_github_stephengold_joltjni_ContactKey
 * Method:    getBodyB
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ContactKey_getBodyB
  (JNIEnv *, jclass, jlong keyVa) {
    const CharacterVirtual::ContactKey * const pKey
            = reinterpret_cast<CharacterVirtual::ContactKey *> (keyVa);
    const BodyID result = pKey->mBodyB;
    return result.GetIndexAndSequenceNumber();
}

/*
 * Class:     com_github_stephengold_joltjni_ContactKey
 * Method:    getCharacterIdB
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ContactKey_getCharacterIdB
  (JNIEnv *, jclass, jlong keyVa) {
    const CharacterVirtual::ContactKey * const pKey
            = reinterpret_cast<CharacterVirtual::ContactKey *> (keyVa);
    const CharacterID result = pKey->mCharacterIDB;
    return result.GetValue();
}

/*
 * Class:     com_github_stephengold_joltjni_ContactKey
 * Method:    getSubShapeIdB
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ContactKey_getSubShapeIdB
  (JNIEnv *, jclass, jlong keyVa) {
    const CharacterVirtual::ContactKey * const pKey
            = reinterpret_cast<CharacterVirtual::ContactKey *> (keyVa);
    const SubShapeID result = pKey->mSubShapeIDB;
    return result.GetValue();
}

/*
 * Class:     com_github_stephengold_joltjni_ContactKey
 * Method:    isEqual
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ContactKey_isEqual
  (JNIEnv *, jclass, jlong keyVa, jlong otherVa) {
    const CharacterVirtual::ContactKey * const pKey1
            = reinterpret_cast<CharacterVirtual::ContactKey *> (keyVa);
    const CharacterVirtual::ContactKey * const pKey2
            = reinterpret_cast<CharacterVirtual::ContactKey *> (otherVa);
    const bool result = (*pKey1) == (*pKey2);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactKey
 * Method:    restoreState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactKey_restoreState
  (JNIEnv *, jclass, jlong keyVa, jlong recorderVa) {
    CharacterVirtual::ContactKey * const pKey
            = reinterpret_cast<CharacterVirtual::ContactKey *> (keyVa);
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    pKey->RestoreState(*pRecorder);
}

/*
 * Class:     com_github_stephengold_joltjni_ContactKey
 * Method:    saveState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactKey_saveState
  (JNIEnv *, jclass, jlong keyVa, jlong recorderVa) {
    const CharacterVirtual::ContactKey * const pKey
            = reinterpret_cast<CharacterVirtual::ContactKey *> (keyVa);
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    pKey->SaveState(*pRecorder);
}