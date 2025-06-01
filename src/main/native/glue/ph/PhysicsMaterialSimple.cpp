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

/*
 * Author: Stephen Gold
 */
#include "Jolt/Jolt.h"
#include "Jolt/Physics/Collision/PhysicsMaterialSimple.h"
#include "auto/com_github_stephengold_joltjni_PhysicsMaterialSimple.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_PhysicsMaterialSimple
 * Method:    create
 * Signature: (Ljava/lang/String;I)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsMaterialSimple_create
  (JNIEnv *pEnv, jclass, jstring nameString, jint colorInt) {
    jboolean isCopy;
    const char * name = pEnv->GetStringUTFChars(nameString, &isCopy);
    const Color color(colorInt);
    PhysicsMaterialSimple * const pResult
            = new PhysicsMaterialSimple(name, color);
    pEnv->ReleaseStringUTFChars(nameString, name);
    TRACE_NEW("PhysicsMaterialSimple", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsMaterialSimple
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsMaterialSimple_createCopy
  BODYOF_CREATE_COPY(PhysicsMaterialSimple)

/*
 * Class:     com_github_stephengold_joltjni_PhysicsMaterialSimple
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsMaterialSimple_createDefault
  (JNIEnv *, jclass) {
    PhysicsMaterialSimple * const pResult = new PhysicsMaterialSimple();
    TRACE_NEW("PhysicsMaterialSimple", pResult)
    return reinterpret_cast<jlong> (pResult);
}