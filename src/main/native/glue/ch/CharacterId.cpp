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

/*
 * Author: Stephen Gold
 */
#include "Jolt/Jolt.h"
#include "Jolt/Core/Atomics.h"
#include "Jolt/Physics/Character/CharacterID.h"
#include "auto/com_github_stephengold_joltjni_CharacterId.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_CharacterId
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterId_createDefault
  (JNIEnv *, jclass) {
    CharacterID * const pResult = new CharacterID();
    TRACE_NEW("CharacterID", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterId
 * Method:    equals
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_CharacterId_equals
  (JNIEnv *, jclass, jlong id1Va, jlong id2Va) {
    const CharacterID * const pId1 = reinterpret_cast<CharacterID *> (id1Va);
    const CharacterID * const pId2 = reinterpret_cast<CharacterID *> (id2Va);
    const bool result = (*pId1) == (*pId2);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterId
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterId_free
  (JNIEnv *, jclass, jlong idVa) {
    CharacterID * const pId = reinterpret_cast<CharacterID *> (idVa);
    TRACE_DELETE("CharacterID", pId)
    delete pId;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterId
 * Method:    getValue
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CharacterId_getValue
  (JNIEnv *, jclass, jlong idVa) {
    const CharacterID * const pId = reinterpret_cast<CharacterID *> (idVa);
    const uint32 result = pId->GetValue();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterId
 * Method:    isInvalid
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_CharacterId_isInvalid
  (JNIEnv *, jclass, jlong idVa) {
    const CharacterID * const pId = reinterpret_cast<CharacterID *> (idVa);
    const bool result = pId->IsInvalid();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterId
 * Method:    sSetNextCharacterId
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterId_sSetNextCharacterId
  (JNIEnv *, jclass, jint nextValue) {
    CharacterID::sSetNextCharacterID(nextValue);
}